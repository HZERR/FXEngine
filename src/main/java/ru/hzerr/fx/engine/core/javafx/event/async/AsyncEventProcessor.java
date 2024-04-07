package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.event.Event;
import javafx.event.EventHandler;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SideOnly(Side.EVERYWHERE)
public abstract class AsyncEventProcessor<T extends Event> implements EventHandler<T>, AutoCloseable {

    private ILogProvider logProvider;
    private final ExecutorService executorService = executorService();

    @Override
    public void handle(T event) {
        executorService.execute(() -> {
            try {
                onProcess(event);
            } catch (Exception e) {
                onFailure(e);
            }
        });
    }

    protected abstract void onProcess(T event) throws Exception;

    protected abstract void onFailure(Exception e);

    protected ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

    @Override
    public void close() {
        executorService.close();
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    public ILogProvider getLogProvider() {
        return logProvider;
    }
}
