package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.scene.control.TreeTableColumn;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncTreeTableColumnCellEditEventProcessor<S, T> extends AsyncEventProcessor<TreeTableColumn.CellEditEvent<S, T>> {
}
