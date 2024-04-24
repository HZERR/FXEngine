package ru.hzerr.fx.core.application;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.fx.engine.core.interfaces.engine.IStructureConfiguration;

public interface IExtendedStructureConfiguration extends IStructureConfiguration {

    BaseDirectory getAssetsDirectory();
}
