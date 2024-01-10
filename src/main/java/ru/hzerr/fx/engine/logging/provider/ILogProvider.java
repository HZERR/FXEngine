package ru.hzerr.fx.engine.logging.provider;

import org.slf4j.Logger;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.logging.StartupException;

import java.io.Closeable;

public interface ILogProvider extends Closeable {

    Logger getLogger();
    IReadOnlyLoggingConfiguration getConfiguration();

    void start() throws StartupException;
}
