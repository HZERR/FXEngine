package ru.hzerr.fx.engine.core.path;

import com.google.common.base.Preconditions;
import ru.hzerr.fx.engine.core.interfaces.path.ILocation;

public class RelativeDirectoryLocation implements ILocation {

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
