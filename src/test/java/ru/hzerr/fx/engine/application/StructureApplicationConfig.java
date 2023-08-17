package ru.hzerr.fx.engine.application;

import ru.hzerr.fx.engine.annotation.StructureApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureApplicationConfiguration;

@StructureApplicationConfiguration
public class StructureApplicationConfig implements IStructureApplicationConfiguration {

    @Override
    public String getFXMLPackage() {
        return "fxml";
    }

    @Override
    public String getThemePackage() {
        return "theme";
    }

    @Override
    public String getLanguagePackage() {
        return "language";
    }
}
