package ru.hzerr.fx.engine.core.concurrent.function;

import javafx.application.Platform;

import java.util.function.Consumer;

public interface FXConsumer<T> extends Consumer<T> {

    @Override
    default void accept(T t) {
        Platform.runLater(() -> onAccept(t));
    }

    void onAccept(T t);
}
