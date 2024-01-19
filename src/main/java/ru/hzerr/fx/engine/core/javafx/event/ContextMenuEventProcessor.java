package ru.hzerr.fx.engine.core.javafx.event;

import javafx.scene.input.ContextMenuEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class ContextMenuEventProcessor extends EventProcessor<ContextMenuEvent> {
}
