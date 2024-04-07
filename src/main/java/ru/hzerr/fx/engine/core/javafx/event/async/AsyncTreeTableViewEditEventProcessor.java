package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.scene.control.TreeTableView;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncTreeTableViewEditEventProcessor<T> extends AsyncEventProcessor<TreeTableView.EditEvent<T>> {
}
