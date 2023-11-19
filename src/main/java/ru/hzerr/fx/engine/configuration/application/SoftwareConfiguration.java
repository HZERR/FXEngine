package ru.hzerr.fx.engine.configuration.application;

import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.RegisteredAs;
import ru.hzerr.fx.engine.configuration.logging.ILoggingConfiguration;

/**
 * Класс представлящий из себя конфигурацию приложения.
 * Он хранит в себе информацию о том, какие на текущий момент используются: тема, язык и прочие элементы, необходимые приложению
 * Если информация еще не установлена приложением, то берется информация из конфигурации по-умолчанию
 * Данная конфигурация не является In Memory конфигурацией
 * Пользователь ядра должен сам предоставить данному классу конфигурации по-умолчанию
 * Конфигурации по-умолчанию должны быть загружены в контекст до того момента, как начнется загрузка данной конфигурации
 */
@RegisteredAs("softwareConfiguration")
public class SoftwareConfiguration implements ISoftwareConfiguration {

    private ILoggingConfiguration loggingConfiguration;
    private IApplicationConfiguration applicationConfiguration;

    @Include
    public SoftwareConfiguration(ILoggingConfiguration loggingConfiguration, IApplicationConfiguration applicationConfiguration) {
        this.loggingConfiguration = loggingConfiguration;
        this.applicationConfiguration = applicationConfiguration;
    }

    public ILoggingConfiguration getLoggingConfiguration() {
        return loggingConfiguration;
    }

    public IApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }
}
