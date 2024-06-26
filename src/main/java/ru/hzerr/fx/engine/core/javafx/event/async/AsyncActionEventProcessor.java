package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.event.ActionEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncActionEventProcessor extends AsyncEventProcessor<ActionEvent> {
}
