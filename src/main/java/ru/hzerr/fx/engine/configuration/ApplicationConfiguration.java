package ru.hzerr.fx.engine.configuration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.hzerr.file.HFile;
import ru.hzerr.fx.engine.core.FXEngine;

/**
 * Класс прдставлящий из себя конфигурацию приложения.
 * Он хранит в себе информацию о том, какие на текущий момент используются: тема, язык и прочие элементы, необходимые приложению
 * Если информация еще не установлена приложением, то этот класс берет информацию из базовой захардкоженной конфигурации(использует конфигурацию по-умолчанию)
 * Данная конфигурация не является In Memory конфигурацией
 * Пользователь ядра должен сам предоставить данному классу конфигурацию по-умолчанию
 * Конфигурация по-умолчанию должна быть загружена в контекст до того момента, как начнется загрузка данной конфигурации
 */
@Component
@Qualifier("application.configuration")
public class ApplicationConfiguration implements IApplicationConfiguration {

    private Config applicationConfig;
    private IApplicationConfiguration baseConfiguration;

    @Autowired
    public ApplicationConfiguration(@Qualifier("base.application.configuration") IApplicationConfiguration baseConfiguration, IStructureConfiguration configuration) {
        this.baseConfiguration = baseConfiguration;
        this.applicationConfig = ConfigFactory.parseFile(configuration.getApplicationConfigurationFile().asIOFile());
    }

    @Override
    public @NotNull String getLanguageID() {
        if (applicationConfig.hasPath("language.name")) {
            return applicationConfig.getString("language.name");
        } else
            return baseConfiguration.getLanguageID();
    }
}
