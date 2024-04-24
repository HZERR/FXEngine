package ru.hzerr.fx.engine.core.theme;

import ru.hzerr.fx.engine.core.exception.LoadThemeException;

public interface ICSSLoader {

    LoadedThemeData resolve() throws LoadThemeException;
}
