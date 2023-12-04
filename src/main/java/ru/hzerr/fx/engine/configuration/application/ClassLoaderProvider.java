package ru.hzerr.fx.engine.configuration.application;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.ClassUtils;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;

@Registered
public class ClassLoaderProvider implements IClassLoaderProvider {

    private ClassLoader applicationClassLoader = getDefaultClassLoader();
    private ClassLoader springContextClassLoader = getDefaultClassLoader();
    private ClassLoader applicationResourceClassLoader = getDefaultClassLoader();

    private final IExtendedAnnotationConfigApplicationContext context;

    @Include
    public ClassLoaderProvider(IExtendedAnnotationConfigApplicationContext context) {
        this.context = context;
        this.context.setClassLoader(springContextClassLoader);
    }

    @NotNull
    @Override
    public ClassLoader getApplicationClassLoader() {
        return applicationClassLoader;
    }

    @NotNull
    @Override
    public ClassLoader getSpringContextClassLoader() {
        return springContextClassLoader;
    }

    @NotNull
    @Override
    public ClassLoader getApplicationResourceClassLoader() {
        return applicationResourceClassLoader;
    }

    @Override
    public void setApplicationClassLoader(@NotNull ClassLoader applicationClassLoader) {
        this.applicationClassLoader = applicationClassLoader;
    }

    @Override
    public void setSpringContextClassLoader(@NotNull ClassLoader springContextClassLoader) {
        this.springContextClassLoader = springContextClassLoader;
        context.setClassLoader(springContextClassLoader);
    }

    @Override
    public void setApplicationResourceClassLoader(@NotNull ClassLoader applicationResourceClassLoader) {
        this.applicationResourceClassLoader = applicationResourceClassLoader;
    }

    private @NotNull ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                cl = ClassLoader.getSystemClassLoader();
            }
        }
        return cl;
    }
}
