package ru.hzerr.fx.engine.core.theme;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResolvedThemeLocation that = (ResolvedThemeLocation) o;
        return Objects.equals(metaData, that.metaData) && Objects.equals(stylesheet, that.stylesheet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaData, stylesheet);
    }
}
