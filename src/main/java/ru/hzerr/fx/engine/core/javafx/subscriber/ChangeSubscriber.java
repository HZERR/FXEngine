package ru.hzerr.fx.engine.core.javafx.subscriber;

import java.util.function.BiConsumer;

public abstract class ChangeSubscriber<T, U> extends Subscriber implements BiConsumer<T, U> {

    @Override
    public void accept(T oValue, U nValue) {
        onAccept(oValue, nValue);
    }

    public abstract void onAccept(T oValue, U nValue);
}
