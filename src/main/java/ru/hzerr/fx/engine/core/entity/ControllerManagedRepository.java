package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.collections.functions.map.ProtectedConsumer;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.Type;
import ru.hzerr.fx.engine.core.annotation.Concurrent;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.theme.LoadedThemeData;

import java.util.Iterator;
import java.util.function.BiConsumer;

@SideOnly(Side.CORE)
@Concurrent
@Registered
public class ControllerManagedRepository implements IControllerManagedRepository {

    private final HMap<String, Controller> controllers = HMap.create(Type.SYNCHRONIZED);

    @Override
    public void register(String id, Controller controller) {
        controllers.put(id, controller);
    }

    @Override
    public void unregister(String id) {
        controllers.remove(id);
    }

    @Override
    public void changeTheme(LoadedThemeData loadedThemeData) {
        controllers.forEachOfValues(controller -> controller.applyTheme(loadedThemeData));
    }

    @Override
    public void destroy(String id) {
        controllers.get(id).onDestroy();
    }

    @Override
    public void destroyAll() {
        Iterator<Controller> iterator = controllers.values().iterator();
        while (iterator.hasNext()) {
            // calls the 'unregister' method under the hood
            iterator.next().onDestroy();
        }
    }

    @Override
    public void processEveryone(BiConsumer<String, Controller> action) {
        controllers.forEach(action);
    }

    @Override
    public <T extends Exception> void processEveryone(ProtectedConsumer<String, Controller, T> action, Class<T> type) throws T {
        controllers.forEach(action, type);
    }
}
