package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.scene.control.TableColumn;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncTableColumnCellEditEventProcessor<S, T> extends AsyncEventProcessor<TableColumn.CellEditEvent<S, T>> {
}
