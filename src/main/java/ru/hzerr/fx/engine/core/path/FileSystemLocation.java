package ru.hzerr.fx.engine.core.path;

public class FileSystemLocation extends ResourceLocation {

    public FileSystemLocation(String location, String fileName) {
        super(location, fileName, Separator.SYSTEM_PLATFORM_INDEPENDENT_SEPARATOR);
    }

    public FileSystemLocation(BaseLocation packageLocation, String fileName) {
        this(packageLocation.getLocation(), fileName);
    }
}
