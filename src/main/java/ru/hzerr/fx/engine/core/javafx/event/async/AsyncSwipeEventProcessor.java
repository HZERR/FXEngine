package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.scene.input.SwipeEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncSwipeEventProcessor extends AsyncEventProcessor<SwipeEvent> {
}
