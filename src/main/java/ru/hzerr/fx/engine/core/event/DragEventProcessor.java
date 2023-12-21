package ru.hzerr.fx.engine.core.event;

import javafx.scene.input.DragEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class DragEventProcessor extends EventProcessor<DragEvent> {
}
