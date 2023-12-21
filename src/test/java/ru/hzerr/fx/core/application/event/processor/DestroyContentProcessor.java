package ru.hzerr.fx.core.application.event.processor;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.event.ActionEventProcessor;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

@Registered
public class DestroyContentProcessor extends ActionEventProcessor {

    private ILogProvider logProvider;

    @Override
    protected void onProcess(ActionEvent event) throws Exception {
        FXEngine.getContext().getScene().setRoot(new AnchorPane());
    }

    @Override
    protected void onFailure(Exception e) {
        logProvider.getLogger().error("DestroyContentProcessor", e);
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
