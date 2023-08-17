package ru.hzerr.fx.engine.configuration;

import javax.annotation.Nullable;

public interface IStructureApplicationConfiguration {

    @Nullable String getFXMLPackage();
    @Nullable String getThemePackage();
    @Nullable String getLanguagePackage();
}
