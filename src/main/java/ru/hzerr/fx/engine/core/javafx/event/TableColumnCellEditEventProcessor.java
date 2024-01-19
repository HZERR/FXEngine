package ru.hzerr.fx.engine.core.javafx.event;

import javafx.scene.control.TableColumn;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class TableColumnCellEditEventProcessor<S, T> extends EventProcessor<TableColumn.CellEditEvent<S, T>> {
}
