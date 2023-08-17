package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.core.language.LanguagePack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractControlManager {

    protected final Map<String, Controller> controllers = new ConcurrentHashMap<>();

    public abstract void register(String id, Controller controller);
    public abstract void unregister(String id);

    public abstract void setTheme();
    public abstract void setLanguage(LanguagePack languagePack);
}
