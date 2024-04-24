package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.collections.functions.map.ProtectedConsumer;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.Type;
import ru.hzerr.fx.engine.core.annotation.Concurrent;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.exception.LoadThemeException;
import ru.hzerr.fx.engine.core.interfaces.entity.IController;
import ru.hzerr.fx.engine.core.theme.LoadedThemeData;
import ru.hzerr.fx.engine.reflection.ReflectionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

@SideOnly(Side.CORE)
@Concurrent
@Registered
public class ControllerManagedRepository implements IControllerManagedRepository {

    private final HMap<String, IController> controllers = HMap.create(Type.SYNCHRONIZED);

    @Override
    public void register(String id, IController controller) {
        controllers.put(id, controller);
    }

    @Override
    public void unregister(String id) {
        controllers.remove(id);
    }

    @Override
    public void changeTheme(LoadedThemeData loadedThemeData) {
        controllers.forEachOfValues(controller -> {
            try {
                Method applyThemeMethod = controller.getClass().getDeclaredMethod("applyTheme", LoadedThemeData.class);
                applyThemeMethod.setAccessible(true);
                applyThemeMethod.invoke(controller, loadedThemeData);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new ReflectionException(e.getMessage(), e);
            }
        });
    }

    @Override
    public void destroy(String id) {
        controllers.get(id).onDestroy();
    }

    @Override
    public void destroyAll() {
        for (IController controller : controllers.values().toArray(IController[]::new)) {
            // calls the 'unregister' method under the hood
            controller.onDestroy();
        }
    }

    @Override
    public void processEveryone(BiConsumer<String, IController> action) {
        controllers.forEach(action);
    }

    @Override
    public <T extends Exception> void processEveryone(ProtectedConsumer<String, IController, T> action, Class<T> type) throws T {
        controllers.forEach(action, type);
    }
}
