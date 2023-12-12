package ru.hzerr.fx.engine.core.concurrent;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.core.concurrent.function.FXBiConsumer;
import ru.hzerr.fx.engine.core.concurrent.function.FXConsumer;
import ru.hzerr.fx.engine.core.concurrent.function.FXRunnable;

import java.util.concurrent.*;
import java.util.function.*;

public class ExtendedCompletableFuture<T> implements IExtendedCompletionStage<T> {

    private CompletableFuture<T> wrapped;

    public ExtendedCompletableFuture(CompletableFuture<T> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public IExtendedCompletionStage<Void> thenFXRun(FXRunnable runnable) {
        return new ExtendedCompletableFuture<>(wrapped.thenRun(runnable));
    }

    @Override
    public IExtendedCompletionStage<Void> thenFXAccept(FXConsumer<? super T> action) {
        return new ExtendedCompletableFuture<>(wrapped.thenAccept(action));
    }

    @Override
    public <U> IExtendedCompletionStage<Void> thenFXAcceptBoth(CompletionStage<? extends U> other, FXBiConsumer<? super T, ? super U> action) {
        return new ExtendedCompletableFuture<>(wrapped.thenAcceptBoth(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> runAfterFXBoth(CompletionStage<?> other, FXRunnable action) {
        return new ExtendedCompletableFuture<>(wrapped.runAfterBoth(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> acceptFXEither(CompletionStage<? extends T> other, FXConsumer<? super T> action) {
        return new ExtendedCompletableFuture<>(wrapped.acceptEither(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> runAfterFXEither(CompletionStage<?> other, FXRunnable action) {
        return new ExtendedCompletableFuture<>(wrapped.runAfterEither(other, action));
    }

    @Override
    public <U> IExtendedCompletionStage<U> thenApply(Function<? super T, ? extends U> fn) {
        return new ExtendedCompletableFuture<>(wrapped.thenApply(fn));
    }

    @Override
    public <U> IExtendedCompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return new ExtendedCompletableFuture<>(wrapped.thenApplyAsync(fn));
    }

    @Override
    public <U> IExtendedCompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.thenApplyAsync(fn, executor));
    }

    @Override
    public IExtendedCompletionStage<Void> thenAccept(Consumer<? super T> action) {
        return new ExtendedCompletableFuture<>(wrapped.thenAccept(action));
    }

    @Override
    public IExtendedCompletionStage<Void> thenAcceptAsync(Consumer<? super T> action) {
        return new ExtendedCompletableFuture<>(wrapped.thenAcceptAsync(action));
    }

    @Override
    public IExtendedCompletionStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.thenAcceptAsync(action, executor));
    }

    @Override
    public IExtendedCompletionStage<Void> thenRun(Runnable action) {
        return new ExtendedCompletableFuture<>(wrapped.thenRun(action));
    }

    @Override
    public IExtendedCompletionStage<Void> thenRunAsync(Runnable action) {
        return new ExtendedCompletableFuture<>(wrapped.thenRunAsync(action));
    }

    @Override
    public IExtendedCompletionStage<Void> thenRunAsync(Runnable action, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.thenRunAsync(action, executor));
    }

    @Override
    public <U, V> IExtendedCompletionStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return new ExtendedCompletableFuture<>(wrapped.thenCombine(other, fn));
    }

    @Override
    public <U, V> IExtendedCompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return new ExtendedCompletableFuture<>(wrapped.thenCombineAsync(other, fn));
    }

    @Override
    public <U, V> IExtendedCompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.thenCombineAsync(other, fn, executor));
    }

    @Override
    public <U> IExtendedCompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return new ExtendedCompletableFuture<>(wrapped.thenAcceptBoth(other, action));
    }

    @Override
    public <U> IExtendedCompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return new ExtendedCompletableFuture<>(wrapped.thenAcceptBothAsync(other, action));
    }

    @Override
    public <U> IExtendedCompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.thenAcceptBothAsync(other, action, executor));
    }

    @Override
    public IExtendedCompletionStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return new ExtendedCompletableFuture<>(wrapped.runAfterBoth(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return new ExtendedCompletableFuture<>(wrapped.runAfterBothAsync(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.runAfterBothAsync(other, action, executor));
    }

    @Override
    public <U> IExtendedCompletionStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return new ExtendedCompletableFuture<>(wrapped.applyToEither(other, fn));
    }

    @Override
    public <U> IExtendedCompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return new ExtendedCompletableFuture<>(wrapped.applyToEitherAsync(other, fn));
    }

    @Override
    public <U> IExtendedCompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.applyToEitherAsync(other, fn, executor));
    }

    @Override
    public IExtendedCompletionStage<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return new ExtendedCompletableFuture<>(wrapped.acceptEither(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return new ExtendedCompletableFuture<>(wrapped.acceptEitherAsync(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.acceptEitherAsync(other, action, executor));
    }

    @Override
    public IExtendedCompletionStage<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return new ExtendedCompletableFuture<>(wrapped.runAfterEither(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return new ExtendedCompletableFuture<>(wrapped.runAfterEitherAsync(other, action));
    }

    @Override
    public IExtendedCompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.runAfterEitherAsync(other, action, executor));
    }

    @Override
    public <U> IExtendedCompletionStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return new ExtendedCompletableFuture<>(wrapped.thenCompose(fn));
    }

    @Override
    public <U> IExtendedCompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return new ExtendedCompletableFuture<>(wrapped.thenComposeAsync(fn));
    }

    @Override
    public <U> IExtendedCompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.thenComposeAsync(fn, executor));
    }

    @Override
    public <U> IExtendedCompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return new ExtendedCompletableFuture<>(wrapped.handle(fn));
    }

    @Override
    public <U> IExtendedCompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return new ExtendedCompletableFuture<>(wrapped.handleAsync(fn));
    }

    @Override
    public <U> IExtendedCompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.handleAsync(fn, executor));
    }

    @Override
    public IExtendedCompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return new ExtendedCompletableFuture<>(wrapped.whenComplete(action));
    }

    @Override
    public IExtendedCompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        return new ExtendedCompletableFuture<>(wrapped.whenCompleteAsync(action));
    }

    @Override
    public IExtendedCompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.whenCompleteAsync(action, executor));
    }

    @Override
    public IExtendedCompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return new ExtendedCompletableFuture<>(wrapped.exceptionally(fn));
    }

    @Override
    public IExtendedCompletionStage<T> exceptionallyAsync(Function<Throwable, ? extends T> fn) {
        return new ExtendedCompletableFuture<>(wrapped.exceptionallyAsync(fn));
    }

    @Override
    public IExtendedCompletionStage<T> exceptionallyAsync(Function<Throwable, ? extends T> fn, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.exceptionallyAsync(fn, executor));
    }

    @Override
    public IExtendedCompletionStage<T> exceptionallyCompose(Function<Throwable, ? extends CompletionStage<T>> fn) {
        return new ExtendedCompletableFuture<>(wrapped.exceptionallyCompose(fn));
    }

    @Override
    public IExtendedCompletionStage<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn) {
        return new ExtendedCompletableFuture<>(wrapped.exceptionallyComposeAsync(fn));
    }

    @Override
    public IExtendedCompletionStage<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn, Executor executor) {
        return new ExtendedCompletableFuture<>(wrapped.exceptionallyComposeAsync(fn, executor));
    }

    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return wrapped.toCompletableFuture();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return wrapped.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return wrapped.isCancelled();
    }

    @Override
    public boolean isDone() {
        return wrapped.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return wrapped.get();
    }

    @Override
    public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return wrapped.get(timeout, unit);
    }

    @Override
    public T join() {
        return wrapped.join();
    }

    @Override
    public T getNow(T valueIfAbsent) {
        return wrapped.getNow(valueIfAbsent);
    }

    @Override
    public T resultNow() {
        return wrapped.resultNow();
    }

    @Override
    public Throwable exceptionNow() {
        return wrapped.exceptionNow();
    }

    @Override
    public boolean complete(T value) {
        return wrapped.complete(value);
    }

    @Override
    public boolean completeExceptionally(Throwable ex) {
        return wrapped.completeExceptionally(ex);
    }

    public static ExtendedCompletableFuture<Void> allOf(CompletableFuture<?>... cfs) {
        return new ExtendedCompletableFuture<>(CompletableFuture.allOf(cfs));
    }

    public static ExtendedCompletableFuture<Void> anyOf(CompletableFuture<?>... cfs) {
        return new ExtendedCompletableFuture<>(CompletableFuture.allOf(cfs));
    }

    public static IExtendedCompletionStage<Void> runAsync(Runnable runnable) {
        return new ExtendedCompletableFuture<>(CompletableFuture.runAsync(runnable));
    }

    public static IExtendedCompletionStage<Void> runAsync(Runnable runnable, Executor executor) {
        return new ExtendedCompletableFuture<>(CompletableFuture.runAsync(runnable, executor));
    }

    public static <U> IExtendedCompletionStage<U> supplyAsync(Supplier<U> action) {
        return new ExtendedCompletableFuture<>(CompletableFuture.supplyAsync(action));
    }

    public static <U> IExtendedCompletionStage<U> supplyAsync(Supplier<U> action, Executor executor) {
        return new ExtendedCompletableFuture<>(CompletableFuture.supplyAsync(action, executor));
    }

    public static <T> IExtendedCompletionStage<T> from(CompletableFuture<T> wrapped) {
        return new ExtendedCompletableFuture<>(wrapped);
    }
}
