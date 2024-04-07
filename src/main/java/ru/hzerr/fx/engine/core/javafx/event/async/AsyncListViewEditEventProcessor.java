package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.scene.control.ListView;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncListViewEditEventProcessor<T> extends AsyncEventProcessor<ListView.EditEvent<T>> {
}
