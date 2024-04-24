package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.core.interfaces.entity.IController;
import ru.hzerr.fx.engine.core.theme.LoadedThemeData;

public interface IThemeManagedRepository {
    LoadedThemeData getFromCache(String themeName, Class<? extends IController> type);

    void putInCache(String themeName, Class<? extends IController> type, LoadedThemeData loadedThemeData);
}
