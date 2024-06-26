package ru.hzerr.fx.core.application.theme;

import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.interfaces.engine.ThemeMetaData;

@Registered
public class WhiteThemeMetaData implements ThemeMetaData {
    @Override
    public String getName() {
        return "White";
    }

    @Override
    public String getPackage() {
        return "white";
    }
}
