package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.collections.functions.map.ProtectedConsumer;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.theme.LoadedThemeData;

import java.util.function.BiConsumer;

@SideOnly(Side.CORE)
public interface IControllerManagedRepository {

    void register(String id, Controller controller);
    void unregister(String id);
    void changeTheme(LoadedThemeData loadedThemeData);
    void destroy(String id);
    void destroyAll();

    void processEveryone(BiConsumer<String, Controller> action);
    <T extends Exception>
    void processEveryone(ProtectedConsumer<String, Controller, T> action, Class<T> type) throws T;
}
