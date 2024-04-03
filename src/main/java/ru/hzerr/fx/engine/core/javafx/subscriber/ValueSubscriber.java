package ru.hzerr.fx.engine.core.javafx.subscriber;

import java.util.function.Consumer;

public abstract class ValueSubscriber<T> extends Subscriber implements Consumer<T> {

    @Override
    public void accept(T nValue) {
        onAccept(nValue);
    }

    public abstract void onAccept(T nValue);
}
