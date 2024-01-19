package ru.hzerr.fx.engine.core.javafx.event;

import javafx.scene.control.TreeTableView;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class TreeTableViewEditEventProcessor<T> extends EventProcessor<TreeTableView.EditEvent<T>> {
}
