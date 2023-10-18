package ru.hzerr.fx.engine.core.path.typesafe;

import ru.hzerr.fx.engine.core.path.ResourceLocation;
import ru.hzerr.fx.engine.core.path.Separator;

public class ConfigResourceLocation extends ResourceLocation {
    public ConfigResourceLocation(String location, String fileName) {
        super(location, fileName, Separator.SLASH_SEPARATOR);
    }
}
