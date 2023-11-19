package ru.hzerr.fx.engine.configuration.application;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.fx.engine.configuration.IReadOnlyConfiguration;
import ru.hzerr.fx.engine.core.annotation.Registered;

@Registered
public interface IStructureConfiguration extends IReadOnlyConfiguration {

    BaseDirectory getProgramDirectory();
    BaseDirectory getLogDirectory();
    BaseDirectory getConfigDirectory();
    BaseFile getSoftwareConfigurationFile();
}
