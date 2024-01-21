package ru.hzerr.fx.engine.core.javafx.list;

import javafx.scene.control.ListCell;
import ru.hzerr.fx.engine.core.annotation.Preview;

@Preview(version = "1.1.3.2E")
public abstract class BasicListCell<T> extends ListCell<T> {

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        onUpdateItem(item, empty);
    }

    public abstract void onUpdateItem(T item, boolean empty);
}
