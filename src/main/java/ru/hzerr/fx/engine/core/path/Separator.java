package ru.hzerr.fx.engine.core.path;

import java.io.File;

public enum Separator {

    SYSTEM_PLATFORM_INDEPENDENT_SEPARATOR(File.separator),
    SLASH_SEPARATOR("/"),
    POINT_SEPARATOR(".");

    private String separator;

    Separator(String separator) {
        this.separator = separator;
    }

    public String getSeparator() {
        return separator;
    }
}
