package ru.hzerr.fx.engine.core.concurrent;

import ru.hzerr.fx.engine.core.concurrent.function.FXBiConsumer;
import ru.hzerr.fx.engine.core.concurrent.function.FXConsumer;
import ru.hzerr.fx.engine.core.concurrent.function.FXRunnable;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IExtendedCompletionStage<T> extends Future<T>, CompletionStage<T> {

    IExtendedCompletionStage<Void> thenFXRun(FXRunnable runnable);

    IExtendedCompletionStage<Void> thenFXAccept(FXConsumer<? super T> action);

    <U> IExtendedCompletionStage<Void> thenFXAcceptBoth(CompletionStage<? extends U> other, FXBiConsumer<? super T, ? super U> action);

    IExtendedCompletionStage<Void> runAfterFXBoth(CompletionStage<?> other, FXRunnable action);

    IExtendedCompletionStage<Void> acceptFXEither(CompletionStage<? extends T> other, FXConsumer<? super T> action);

    IExtendedCompletionStage<Void> runAfterFXEither(CompletionStage<?> other, FXRunnable action);
    IExtendedCompletionStage<T> exceptionally(Consumer<Throwable> fn);

    IExtendedCompletionStage<T> exceptionallyAsync(Consumer<Throwable> fn);
    IExtendedCompletionStage<T> exceptionallyAsync(Consumer<Throwable> fn, Executor executor);

    @Override
    <U> IExtendedCompletionStage<U> thenApply(Function<? super T, ? extends U> fn);

    @Override
    <U> IExtendedCompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn);

    @Override
    <U> IExtendedCompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor);

    @Override
    IExtendedCompletionStage<Void> thenAccept(Consumer<? super T> action);

    @Override
    IExtendedCompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);

    @Override
    IExtendedCompletionStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor);

    @Override
    IExtendedCompletionStage<Void> thenRun(Runnable action);

    @Override
    IExtendedCompletionStage<Void> thenRunAsync(Runnable action);

    @Override
    IExtendedCompletionStage<Void> thenRunAsync(Runnable action, Executor executor);

    @Override
    <U, V> IExtendedCompletionStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn);

    @Override
    <U, V> IExtendedCompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn);

    @Override
    <U, V> IExtendedCompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor);

    @Override
    <U> IExtendedCompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action);

    @Override
    <U> IExtendedCompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action);

    @Override
    <U> IExtendedCompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor);

    @Override
    IExtendedCompletionStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action);

    @Override
    IExtendedCompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action);

    @Override
    IExtendedCompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor);

    @Override
    <U> IExtendedCompletionStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn);

    @Override
    <U> IExtendedCompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn);

    @Override
    <U> IExtendedCompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor);

    @Override
    IExtendedCompletionStage<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action);

    @Override
    IExtendedCompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action);

    @Override
    IExtendedCompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor);

    @Override
    IExtendedCompletionStage<Void> runAfterEither(CompletionStage<?> other, Runnable action);

    @Override
    IExtendedCompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action);

    @Override
    IExtendedCompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor);

    @Override
    <U> IExtendedCompletionStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn);

    @Override
    <U> IExtendedCompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn);

    @Override
    <U> IExtendedCompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor);

    @Override
    <U> IExtendedCompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn);

    @Override
    <U> IExtendedCompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn);

    @Override
    <U> IExtendedCompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor);

    @Override
    IExtendedCompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action);

    @Override
    IExtendedCompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action);

    @Override
    IExtendedCompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor);

    @Override
    IExtendedCompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn);

    @Override
    IExtendedCompletionStage<T> exceptionallyAsync(Function<Throwable, ? extends T> fn);

    @Override
    IExtendedCompletionStage<T> exceptionallyAsync(Function<Throwable, ? extends T> fn, Executor executor);

    @Override
    IExtendedCompletionStage<T> exceptionallyCompose(Function<Throwable, ? extends CompletionStage<T>> fn);

    @Override
    IExtendedCompletionStage<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn);

    @Override
    IExtendedCompletionStage<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn, Executor executor);

    T join();
    T getNow(T valueIfAbsent);
    T resultNow();
    Throwable exceptionNow();
    boolean complete(T value);
    boolean completeExceptionally(Throwable ex);
}