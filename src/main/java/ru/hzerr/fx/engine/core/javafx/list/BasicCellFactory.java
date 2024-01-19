package ru.hzerr.fx.engine.core.javafx.list;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import ru.hzerr.fx.engine.core.annotation.Preview;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

@Preview(version = "1.1.3E")
public abstract class BasicCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

    private ILogProvider logProvider;

    @Override
    public ListCell<T> call(ListView<T> param) {
        return new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                onUpdateItem(item, empty);
            }
        };
    }

    public abstract void onUpdateItem(T item, boolean empty);

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    protected ILogProvider getLogProvider() {
        return logProvider;
    }
}
