package ru.hzerr.fx.engine.core.event;

import javafx.scene.input.InputMethodEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class InputMethodEventProcessor extends EventProcessor<InputMethodEvent> {
}
