package ru.hzerr.fx.engine.core.runtime;

import javafx.event.EventHandler;

public abstract class ActionEvent implements EventHandler<javafx.event.ActionEvent> {

    @Override
    public void handle(javafx.event.ActionEvent event) {
        try {
            onProcess();
        } catch (Exception e) {
            onFailure(e);
        }
    }

    protected abstract void onProcess() throws Exception;
    protected abstract void onFailure(Exception e);
}
