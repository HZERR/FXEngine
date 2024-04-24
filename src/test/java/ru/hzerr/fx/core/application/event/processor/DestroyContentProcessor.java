package ru.hzerr.fx.core.application.event.processor;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.metadata.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.interfaces.engine.IFXEnvironment;
import ru.hzerr.fx.engine.core.interfaces.logging.ILogProvider;
import ru.hzerr.fx.engine.core.javafx.event.ActionEventProcessor;

@Registered
public class DestroyContentProcessor extends ActionEventProcessor {

    private ILogProvider logProvider;
    private IFXEnvironment environment;

    @Override
    protected void onProcess(ActionEvent event) throws Exception {
        environment.getScene().setRoot(new AnchorPane());
    }

    @Override
    protected void onFailure(Exception e) {
        logProvider.getLogger().error("DestroyContentProcessor", e);
    }

    @Include
    public void setLogProvider(@ApplicationLogProvider ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    @Include
    public void setEnvironment(IFXEnvironment environment) {
        this.environment = environment;
    }
}
