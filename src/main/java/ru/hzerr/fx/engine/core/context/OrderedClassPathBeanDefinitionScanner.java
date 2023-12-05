package ru.hzerr.fx.engine.core.context;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class OrderedClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private BeanNameGenerator beanNameGenerator = AnnotationBeanNameGenerator.INSTANCE;

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    public OrderedClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public OrderedClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public OrderedClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
        super(registry, useDefaultFilters, environment);
    }

    public OrderedClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment, ResourceLoader resourceLoader) {
        super(registry, useDefaultFilters, environment, resourceLoader);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            List<BeanDefinition> candidates = findCandidateComponents(basePackage)
                    .stream()
                    .sorted(new OrderedBeanDefinitionComparator().reversed())
                    .toList();

            for (BeanDefinition candidate : candidates) {
                ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
                candidate.setScope(scopeMetadata.getScopeName());
                String beanName = this.beanNameGenerator.generateBeanName(candidate, getRegistry());
                if (candidate instanceof AbstractBeanDefinition abstractBeanDefinition) {
                    postProcessBeanDefinition(abstractBeanDefinition, beanName);
                }
                if (candidate instanceof AnnotatedBeanDefinition annotatedBeanDefinition) {
                    AnnotationConfigUtils.processCommonDefinitionAnnotations(annotatedBeanDefinition);
                }
                if (checkCandidate(beanName, candidate)) {
                    BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
                    definitionHolder =
                            applyScopedProxyMode(scopeMetadata, definitionHolder, getRegistry());
                    beanDefinitions.add(definitionHolder);
                    registerBeanDefinition(definitionHolder, getRegistry());
                }
            }
        }

        return beanDefinitions;
    }

    private BeanDefinitionHolder applyScopedProxyMode(ScopeMetadata metadata, BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {
        try {
            Method applyScopedProxyModeMethod =
                    AnnotationConfigUtils.class.getDeclaredMethod("applyScopedProxyMode", ScopeMetadata.class, BeanDefinitionHolder.class, BeanDefinitionRegistry.class);
            applyScopedProxyModeMethod.setAccessible(true);

            return (BeanDefinitionHolder) applyScopedProxyModeMethod.invoke(null, metadata, definition, registry);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static class OrderedBeanDefinitionComparator implements Comparator<BeanDefinition> {
        @Override
        public int compare(BeanDefinition o1, BeanDefinition o2) {
            if (o1 instanceof AnnotatedBeanDefinition annotatedBeanDefinition1) {
                if (o2 instanceof AnnotatedBeanDefinition annotatedBeanDefinition2) {
                    MergedAnnotation<Ordered> order1 = annotatedBeanDefinition1.getMetadata().getAnnotations().get(Ordered.class);
                    if (order1.isPresent()) {
                        int value1 = order1.getInt("value");

                        MergedAnnotation<Ordered> order2 = annotatedBeanDefinition2.getMetadata().getAnnotations().get(Ordered.class);
                        if (order2.isPresent()) {
                            int value2 = order2.getInt("value");

                            return Integer.compare(value2, value1);
                        }

                        return 1;
                    }

                    return -1;
                }

                return 1;
            }

            return -1;
        }
    }
}
