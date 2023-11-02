package ru.hzerr.fx.engine.configuration.interfaces;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.fx.engine.annotation.Registered;

@Registered
public interface IStructureConfiguration extends IReadOnlyConfiguration {

    BaseDirectory getProgramDirectory();
    BaseDirectory getLogDirectory();
    BaseDirectory getConfigDirectory();
    BaseFile getSoftwareConfigurationFile();
}
