package ru.hzerr.fx.engine.core.javafx.subscriber;

import ru.hzerr.fx.engine.core.annotation.Preview;

import java.util.function.Consumer;

@Preview(version = "1.1.3E")
public abstract class ValueSubscriber<T> extends Subscriber implements Consumer<T> {

    @Override
    public void accept(T nValue) {
        onAccept(nValue);
    }

    public abstract void onAccept(T nValue);
}
