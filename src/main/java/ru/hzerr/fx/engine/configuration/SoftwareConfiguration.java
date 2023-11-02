package ru.hzerr.fx.engine.configuration;

import ru.hzerr.fx.engine.annotation.Include;
import ru.hzerr.fx.engine.annotation.RegisteredAs;
import ru.hzerr.fx.engine.configuration.interfaces.ILoggingConfiguration;
import ru.hzerr.fx.engine.configuration.interfaces.ISoftwareConfiguration;

/**
 * Класс представлящий из себя конфигурацию приложения.
 * Он хранит в себе информацию о том, какие на текущий момент используются: тема, язык и прочие элементы, необходимые приложению
 * Если информация еще не установлена приложением, то этот класс берет информацию из базовой захардкоженной конфигурации(использует конфигурацию по-умолчанию)
 * Данная конфигурация не является In Memory конфигурацией
 * Пользователь ядра должен сам предоставить данному классу конфигурацию по-умолчанию
 * Конфигурация по-умолчанию должна быть загружена в контекст до того момента, как начнется загрузка данной конфигурации
 */
@RegisteredAs("softwareConfiguration")
public abstract class SoftwareConfiguration implements ISoftwareConfiguration {

    protected ILoggingConfiguration loggingConfiguration;

    @Include
    public SoftwareConfiguration(ILoggingConfiguration loggingConfiguration) {
        this.loggingConfiguration = loggingConfiguration;
    }

    public ILoggingConfiguration getLoggingConfiguration() {
        return loggingConfiguration;
    }
}
