package ru.hzerr.fx.core.application.event.processor;

import javafx.event.ActionEvent;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.metadata.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.interfaces.logging.ILogProvider;
import ru.hzerr.fx.engine.core.javafx.event.ActionEventProcessor;

import java.util.Locale;

@Registered
public class ChangeLocalizationProcessor extends ActionEventProcessor {

    private ILogProvider logProvider;

    @Override
    protected void onProcess(ActionEvent event) throws Exception {
        logProvider.getLogger().debug("Текущий язык '" + FXEngine.getContext().getApplicationConfiguration().getLocale().getLanguage() + "'");
        if (FXEngine.getContext().getApplicationConfiguration().getLocale().equals(Locale.ENGLISH)) {
            FXEngine.getContext().getApplicationManager().changeLanguage(Locale.of("ru", "RU"));
            logProvider.getLogger().debug("Язык приложения изменен на 'Русский'");
        } else {
            FXEngine.getContext().getApplicationManager().changeLanguage(Locale.ENGLISH);
            logProvider.getLogger().debug("Язык приложения изменен на 'English'");
        }
    }

    @Override
    protected void onFailure(Exception e) {
        logProvider.getLogger().error("ChangeLocalizationProcessor", e);
    }

    @Include
    public void setLogProvider(@ApplicationLogProvider ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
