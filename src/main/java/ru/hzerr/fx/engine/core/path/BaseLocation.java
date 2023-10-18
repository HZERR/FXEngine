package ru.hzerr.fx.engine.core.path;

public class BaseLocation implements ILocation {

    private String location;

    public BaseLocation(String location) {
        this.location = location;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
