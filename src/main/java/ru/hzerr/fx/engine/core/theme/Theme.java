package ru.hzerr.fx.engine.core.theme;

import ru.hzerr.fx.engine.core.entity.Controller;

import java.util.Map;

public class Theme {

    private ThemeMetaData metaData;
    private final Map<Class<? extends Controller>, String> stylesheets;

    public Theme(ThemeMetaData metaData, Map<Class<? extends Controller>, String> stylesheets) {
        this.metaData = metaData;
        this.stylesheets = stylesheets;
    }

    public ThemeMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ThemeMetaData metaData) {
        this.metaData = metaData;
    }

    public Map<Class<? extends Controller>, String> getStylesheets() {
        return stylesheets;
    }

    public String getStylesheet(Class<? extends Controller> clazz) {
        return stylesheets.get(clazz);
    }
}
