package ru.hzerr.fx.engine.application;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.interfaces.IStructureConfiguration;
import ru.hzerr.util.SystemInfo;

import java.io.File;

@Registered
public class StructureConfiguration implements IStructureConfiguration {

    private final HDirectory applicationDirectory = new HDirectory(SystemInfo.getUserHome() + File.separator + "FXEngine Inspection");

    @Override
    public BaseDirectory getProgramDirectory() {
        return applicationDirectory;
    }

    @Override
    public BaseDirectory getLogDirectory() {
        return applicationDirectory.getSubDirectory("logging");
    }

    @Override
    public BaseDirectory getConfigDirectory() {
        return applicationDirectory.getSubDirectory("configuration");
    }

    @Override
    public BaseFile getSoftwareConfigurationFile() {
        return getConfigDirectory().getSubFile("application.json");
    }
}
