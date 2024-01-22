package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.core.theme.LoadedThemeData;

public interface IThemeManagedRepository {
    LoadedThemeData getFromCache(String themeName, Class<? extends Controller> type);

    void putInCache(String themeName, Class<? extends Controller> type, LoadedThemeData loadedThemeData);
}
