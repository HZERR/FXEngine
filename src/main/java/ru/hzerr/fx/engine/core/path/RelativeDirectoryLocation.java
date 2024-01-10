package ru.hzerr.fx.engine.core.path;

import com.google.common.base.Preconditions;

public non-sealed class RelativeDirectoryLocation implements ILocation {

    private final String location;

    private RelativeDirectoryLocation(String directoryName) {
        this.location = directoryName;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public static RelativeDirectoryLocation of(String directoryName) {
        Preconditions.checkNotNull(directoryName);

        return new RelativeDirectoryLocation(directoryName);
    }
}
