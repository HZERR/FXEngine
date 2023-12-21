package ru.hzerr.fx.core.application;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.util.SystemInfo;

import java.io.File;

@Registered
public class ExtendedStructureConfiguration implements IExtendedStructureConfiguration {

    @Override
    public BaseDirectory getProgramDirectory() {
        return new HDirectory(SystemInfo.getUserHome() + File.separator + "FXEngine Program Directory");
    }

    @Override
    public BaseDirectory getLogDirectory() {
        return getProgramDirectory().getSubDirectory("log");
    }

    @Override
    public BaseDirectory getConfigDirectory() {
        return getProgramDirectory().getSubDirectory("config");
    }

    @Override
    public BaseFile getSoftwareConfigurationFile() {
        return getConfigDirectory().getSubFile("application.json");
    }

    @Override
    public BaseDirectory getAssetsDirectory() {
        return getProgramDirectory().getSubDirectory("assets");
    }
}
