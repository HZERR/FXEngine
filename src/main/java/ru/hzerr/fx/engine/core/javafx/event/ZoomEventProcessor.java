package ru.hzerr.fx.engine.core.javafx.event;

import javafx.scene.input.ZoomEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class ZoomEventProcessor extends EventProcessor<ZoomEvent> {
}
