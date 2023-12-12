package ru.hzerr.fx.engine.core.concurrent;

import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class JavaFXApplicationExecutorService extends AbstractListeningExecutorService {
    private final Object lock;
    @GuardedBy("lock")
    private int runningTasks;
    @GuardedBy("lock")
    private boolean shutdown;

    public JavaFXApplicationExecutorService() {
        this.lock = new Object();
        this.runningTasks = 0;
        this.shutdown = false;
    }

    public void execute(@NotNull Runnable command) {
        this.startTask();

        try {
            Platform.runLater(command);
        } finally {
            this.endTask();
        }
    }

    public boolean isShutdown() {
        synchronized(this.lock) {
            return this.shutdown;
        }
    }

    public void shutdown() {
        synchronized(this.lock) {
            this.shutdown = true;
            if (this.runningTasks == 0) {
                this.lock.notifyAll();
            }
        }
    }

    @NotNull
    public List<Runnable> shutdownNow() {
        this.shutdown();
        return Collections.emptyList();
    }

    public boolean isTerminated() {
        synchronized(this.lock) {
            return this.shutdown && this.runningTasks == 0;
        }
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        synchronized(this.lock) {
            while(!this.shutdown || this.runningTasks != 0) {
                if (nanos <= 0L) {
                    return false;
                }

                long now = System.nanoTime();
                TimeUnit.NANOSECONDS.timedWait(this.lock, nanos);
                nanos -= System.nanoTime() - now;
            }

            return true;
        }
    }

    private void startTask() {
        synchronized(this.lock) {
            if (this.shutdown) {
                throw new RejectedExecutionException("Executor already shutdown");
            } else {
                ++this.runningTasks;
            }
        }
    }

    private void endTask() {
        synchronized(this.lock) {
            int numRunning = --this.runningTasks;
            if (numRunning == 0) {
                this.lock.notifyAll();
            }
        }
    }
}
