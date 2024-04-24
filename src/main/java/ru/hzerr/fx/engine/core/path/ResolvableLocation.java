package ru.hzerr.fx.engine.core.path;

import ru.hzerr.fx.engine.core.interfaces.path.ILocation;

public class ResolvableLocation {
    private String location;
    private NullSafeResolveLocationOptions nullSafeOptions;

    public ResolvableLocation(ILocation location, NullSafeResolveLocationOptions nullSafeOptions) {
        this.location = location != null ? location.getLocation() : null;
        this.nullSafeOptions = nullSafeOptions;
    }

    public ResolvableLocation(ILocation location) {
        this.location = location.getLocation();
        this.nullSafeOptions = NullSafeResolveLocationOptions.DEFAULT;
    }

    public ResolvableLocation(String location, NullSafeResolveLocationOptions nullSafeOptions) {
        this.location = location;
        this.nullSafeOptions = nullSafeOptions;
    }

    public ResolvableLocation(String location) {
        this.location = location;
        this.nullSafeOptions = NullSafeResolveLocationOptions.DEFAULT;
    }

    public String getLocation() {
        return location;
    }

    public NullSafeResolveLocationOptions getNullSafeOptions() {
        return nullSafeOptions;
    }

    public static ResolvableLocation of(ILocation location, NullSafeResolveLocationOptions nullSafeOptions) {
        return new ResolvableLocation(location, nullSafeOptions);
    }

    public static ResolvableLocation of(String location, NullSafeResolveLocationOptions nullSafeOptions) {
        return new ResolvableLocation(location, nullSafeOptions);
    }
}
