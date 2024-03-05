package ru.hzerr.fx.engine.configuration.environment;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.Side;
import ru.hzerr.fx.engine.core.annotation.SideOnly;

@Registered
public class FXEnvironment implements IFXEnvironment {

    private Stage stage;

    @SideOnly(Side.CORE)
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public Window getWindow() { return stage; }

    @Override
    public Scene getScene() {
        return stage.getScene();
    }
}
