package ru.hzerr.fx.engine.core.javafx.subscriber;

import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

public abstract class Subscriber {

    private ILogProvider logProvider;

    protected ILogProvider getLogProvider() {
        return logProvider;
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
