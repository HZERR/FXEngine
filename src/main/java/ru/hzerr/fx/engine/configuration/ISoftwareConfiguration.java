package ru.hzerr.fx.engine.configuration;

import ru.hzerr.fx.engine.configuration.application.IApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.logging.ILoggingConfiguration;

public interface ISoftwareConfiguration {

    ILoggingConfiguration getLoggingConfiguration();
    IApplicationConfiguration getApplicationConfiguration();
}
