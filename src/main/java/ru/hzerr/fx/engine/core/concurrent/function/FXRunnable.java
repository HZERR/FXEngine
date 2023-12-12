package ru.hzerr.fx.engine.core.concurrent.function;

import javafx.application.Platform;

public interface FXRunnable extends Runnable {
    @Override
    default void run() {
        Platform.runLater(this::onStart);
    }

    void onStart();
}
