package ru.hzerr.fx.core.application.event.processor;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.engine.configuration.environment.IFXEnvironment;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.javafx.event.ActionEventProcessor;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

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

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    @Include
    public void setEnvironment(IFXEnvironment environment) {
        this.environment = environment;
    }
}
