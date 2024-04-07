package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.scene.control.TreeView;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncTreeViewEditEventProcessor<T> extends AsyncEventProcessor<TreeView.EditEvent<T>> {
}
