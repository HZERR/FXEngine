package ru.hzerr.fx.core.application;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;

public interface IExtendedStructureConfiguration extends IStructureConfiguration {

    BaseDirectory getAssetsDirectory();
}
