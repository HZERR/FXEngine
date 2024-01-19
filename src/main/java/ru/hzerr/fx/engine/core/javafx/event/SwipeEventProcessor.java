package ru.hzerr.fx.engine.core.javafx.event;

import javafx.scene.input.SwipeEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class SwipeEventProcessor extends EventProcessor<SwipeEvent> {
}
