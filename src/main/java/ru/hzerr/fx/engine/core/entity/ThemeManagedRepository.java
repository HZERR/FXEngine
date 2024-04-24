package ru.hzerr.fx.engine.core.entity;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import ru.hzerr.fx.engine.core.annotation.Concurrent;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.interfaces.entity.IController;
import ru.hzerr.fx.engine.core.theme.LoadedThemeData;

import java.util.concurrent.locks.ReentrantLock;

@SideOnly(Side.CORE)
@Concurrent
@Registered
public class ThemeManagedRepository implements IThemeManagedRepository {

    private final Table<Class<? extends IController>, String, LoadedThemeData> loadedData = HashBasedTable.create();
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public LoadedThemeData getFromCache(String themeName, Class<? extends IController> type) {
        return loadedData.get(type, themeName);
    }

    @Override
    public void putInCache(String themeName, Class<? extends IController> type, LoadedThemeData loadedThemeData) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            loadedData.put(type, themeName, loadedThemeData);
        } finally {
            lock.unlock();
        }
    }
}
