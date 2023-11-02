package ru.hzerr.fx.engine.application;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.IStructureConfiguration;
import ru.hzerr.util.SystemInfo;

import java.io.File;

@Registered
public class StructureConfiguration implements IStructureConfiguration {

    private final HDirectory applicationDirectory = new HDirectory(SystemInfo.getUserHome() + File.separator + "FXEngine Inspection");

    @Override
    public BaseDirectory getApplicationDirectory() {
        return applicationDirectory;
    }

    @Override
    public BaseDirectory getLoggingDirectory() {
        return applicationDirectory.getSubDirectory("logging");
    }

    @Override
    public BaseDirectory getConfigurationDirectory() {
        return applicationDirectory.getSubDirectory("configuration");
    }

    @Override
    public BaseFile getApplicationConfigurationFile() {
        return getConfigurationDirectory().getSubFile("application.json");
    }
}
