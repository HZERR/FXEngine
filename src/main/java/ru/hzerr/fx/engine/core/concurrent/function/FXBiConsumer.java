package ru.hzerr.fx.engine.core.concurrent.function;

import javafx.application.Platform;

import java.util.function.BiConsumer;

public interface FXBiConsumer<T, U> extends BiConsumer<T, U> {

    @Override
    default void accept(T t, U u) {
        Platform.runLater(() -> onAccept(t, u));
    }

    void onAccept(T t, U u);
}
