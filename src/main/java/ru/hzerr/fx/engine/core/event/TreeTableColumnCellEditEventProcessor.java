package ru.hzerr.fx.engine.core.event;

import javafx.scene.control.TreeTableColumn;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class TreeTableColumnCellEditEventProcessor<S, T> extends EventProcessor<TreeTableColumn.CellEditEvent<S, T>> {
}
