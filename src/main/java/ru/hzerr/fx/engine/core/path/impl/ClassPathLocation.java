package ru.hzerr.fx.engine.core.path.impl;

import ru.hzerr.fx.engine.core.path.ILocation;
import ru.hzerr.fx.engine.core.path.Separator;

public non-sealed class ClassPathLocation implements ILocation {

    private String location;

    protected ClassPathLocation(ILocation directoryLocation, Separator separator, String fileName) {
        this.location = directoryLocation.getLocation() + separator.getSeparator() + fileName;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
