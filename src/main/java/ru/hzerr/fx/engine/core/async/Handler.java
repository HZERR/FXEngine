package ru.hzerr.fx.engine.core.async;

import java.util.concurrent.CompletionException;
import java.util.function.Supplier;

public interface Handler<T> extends Supplier<T> {

    @Override
    default T get() {
        try {
            return onReceive();
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    T onReceive() throws Exception;
}
