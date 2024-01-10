package ru.hzerr.fx.engine.core.theme;

public interface ICSSLoader {

    LoadedThemeData resolve() throws LoadThemeException;
}
