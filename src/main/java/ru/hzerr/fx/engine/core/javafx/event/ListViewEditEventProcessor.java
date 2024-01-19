package ru.hzerr.fx.engine.core.javafx.event;

import javafx.scene.control.ListView;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class ListViewEditEventProcessor<T> extends EventProcessor<ListView.EditEvent<T>> {
}
