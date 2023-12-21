package ru.hzerr.fx.engine.core.event;

import javafx.scene.input.KeyEvent;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@SideOnly(Side.APPLICATION)
public abstract class KeyEventProcessor extends EventProcessor<KeyEvent> {
}
