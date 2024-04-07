package ru.hzerr.fx.engine.core.javafx.event.async;

import javafx.scene.input.ScrollEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class AsyncScrollEventProcessor extends AsyncEventProcessor<ScrollEvent> {
}
