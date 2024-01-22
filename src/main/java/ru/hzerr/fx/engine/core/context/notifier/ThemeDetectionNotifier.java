package ru.hzerr.fx.engine.core.context.notifier;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

@Registered
public class ThemeDetectionNotifier implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ILogProvider engineLogProvider = ((IExtendedAnnotationConfigApplicationContext) event.getApplicationContext()).getFXEngineLogProvider();
        event.getApplicationContext().getBeansOfType(ThemeMetaData.class).values().forEach(themeMetaData -> engineLogProvider.getLogger().info("fxEngine.themeNotificationListener.detectTheme", themeMetaData.getName()));
    }
}
