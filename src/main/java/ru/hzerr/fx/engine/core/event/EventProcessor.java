package ru.hzerr.fx.engine.core.event;

import javafx.event.Event;
import javafx.event.EventHandler;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.EVERYWHERE)
public abstract class EventProcessor<T extends Event> implements EventHandler<T> {

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
}
