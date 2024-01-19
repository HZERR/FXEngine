package ru.hzerr.fx.engine.core.javafx.event;

import javafx.scene.control.TreeView;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class TreeViewEditEventProcessor<T> extends EventProcessor<TreeView.EditEvent<T>> {
}
