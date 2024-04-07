package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.scene.input.RotateEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncRotateEventProcessor extends AsyncEventProcessor<RotateEvent> {
}
