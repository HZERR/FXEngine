package ru.hzerr.fx.engine.core.concurrent;

import ru.hzerr.fx.engine.core.concurrent.function.FXBiConsumer;
import ru.hzerr.fx.engine.core.concurrent.function.FXConsumer;
import ru.hzerr.fx.engine.core.concurrent.function.FXRunnable;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

public interface IExtendedCompletionStage<T> extends Future<T>, CompletionStage<T> {

    IExtendedCompletionStage<Void> thenFXRun(FXRunnable runnable);

    IExtendedCompletionStage<Void> thenFXAccept(FXConsumer<? super T> action);

    <U> IExtendedCompletionStage<Void> thenFXAcceptBoth(CompletionStage<? extends U> other, FXBiConsumer<? super T, ? super U> action);

    IExtendedCompletionStage<Void> runAfterFXBoth(CompletionStage<?> other, FXRunnable action);

    IExtendedCompletionStage<Void> acceptFXEither(CompletionStage<? extends T> other, FXConsumer<? super T> action);

    IExtendedCompletionStage<Void> runAfterFXEither(CompletionStage<?> other, FXRunnable action);

    T join();
    T getNow(T valueIfAbsent);
    T resultNow();
    Throwable exceptionNow();
    boolean complete(T value);
    boolean completeExceptionally(Throwable ex);
}