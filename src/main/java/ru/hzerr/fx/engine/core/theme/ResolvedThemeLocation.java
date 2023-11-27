package ru.hzerr.fx.engine.core.theme;

public class ResolvedThemeLocation {

    private ThemeMetaData metaData;
    private final String stylesheet;

    public ResolvedThemeLocation(ThemeMetaData metaData, String stylesheet) {
        this.metaData = metaData;
        this.stylesheet = stylesheet;
    }

    public ThemeMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ThemeMetaData metaData) {
        this.metaData = metaData;
    }

    public String getStylesheet() {
        return stylesheet;
    }
}
