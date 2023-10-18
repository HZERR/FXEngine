package ru.hzerr.fx.engine.core.path;

public class ResourceLocation extends BaseLocation {
    public ResourceLocation(String location, String fileName, Separator separator) {
        super(location + separator.getSeparator() + fileName);
    }

    public ResourceLocation(BaseLocation packageLocation, String fileName, Separator separator) {
        this(packageLocation.getLocation(), fileName, separator);
    }
}
