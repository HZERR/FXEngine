package ru.hzerr.fx.engine.core.event;

import javafx.event.ActionEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class ActionEventProcessor extends EventProcessor<ActionEvent> {
}
