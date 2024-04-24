package ru.hzerr.fx.core.application.event.processor;

import javafx.event.ActionEvent;
import ru.hzerr.fx.core.application.theme.DarkThemeMetaData;
import ru.hzerr.fx.core.application.theme.WhiteThemeMetaData;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.metadata.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.interfaces.logging.ILogProvider;
import ru.hzerr.fx.engine.core.javafx.event.ActionEventProcessor;

@Registered
public class ChangeThemeProcessor extends ActionEventProcessor {

    private ILogProvider logProvider;

    @Override
    protected void onProcess(ActionEvent event) throws Exception {
        switch (FXEngine.getContext().getApplicationConfiguration().getThemeName()) {
            case "White" -> FXEngine.getContext().getApplicationManager().changeTheme(DarkThemeMetaData.class);
            case null, default -> FXEngine.getContext().getApplicationManager().changeTheme(WhiteThemeMetaData.class);
        }
    }

    @Override
    protected void onFailure(Exception e) {
        logProvider.getLogger().error("ChangeThemeProcessor", e);
    }

    @Include
    public void setLogProvider(@ApplicationLogProvider ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
