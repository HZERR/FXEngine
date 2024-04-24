package ru.hzerr.fx.engine.core.javafx.list;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Preview;
import ru.hzerr.fx.engine.core.annotation.metadata.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.interfaces.logging.ILogProvider;

@Preview(version = "1.1.3E")
public abstract class BasicCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

    private ILogProvider logProvider;

    @Override
    public ListCell<T> call(ListView<T> param) {
        return createListCell();
    }

    public abstract BasicListCell<T> createListCell();

    @Include
    public void setLogProvider(@ApplicationLogProvider ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    protected ILogProvider getLogProvider() {
        return logProvider;
    }
}
