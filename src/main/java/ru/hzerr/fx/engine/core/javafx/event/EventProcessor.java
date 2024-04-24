package ru.hzerr.fx.engine.core.javafx.event;

import javafx.event.Event;
import javafx.event.EventHandler;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.annotation.metadata.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.interfaces.logging.ILogProvider;

@SideOnly(Side.EVERYWHERE)
public abstract class EventProcessor<T extends Event> implements EventHandler<T> {

    private ILogProvider logProvider;

    @Override
    public void handle(T event) {
        try {
            onProcess(event);
        } catch (Exception e) {
            onFailure(e);
        }
    }

    protected abstract void onProcess(T event) throws Exception;
    protected abstract void onFailure(Exception e);

    @Include
    public void setLogProvider(@ApplicationLogProvider ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    public ILogProvider getLogProvider() {
        return logProvider;
    }
}
