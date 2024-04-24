package ru.hzerr.fx.engine.core.path.impl;

import ru.hzerr.fx.engine.core.interfaces.path.ILocation;
import ru.hzerr.fx.engine.core.path.Separator;

public class EngineLoggingInternationalizationFileClassPathLocation extends ClassPathLocation {
    protected EngineLoggingInternationalizationFileClassPathLocation(ILocation directoryLocation, String fileName) {
        super(directoryLocation, Separator.SLASH_SEPARATOR, fileName);
    }

    public static EngineLoggingInternationalizationFileClassPathLocation of(ILocation directoryLocation, String fileName) {
        return new EngineLoggingInternationalizationFileClassPathLocation(directoryLocation, fileName);
    }
}
