package ru.hzerr.fx.engine.core.javafx.subscriber;

import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.metadata.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.interfaces.logging.ILogProvider;

public abstract class Subscriber {

    private ILogProvider logProvider;

    protected ILogProvider getLogProvider() {
        return logProvider;
    }

    @Include
    public void setLogProvider(@ApplicationLogProvider ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
