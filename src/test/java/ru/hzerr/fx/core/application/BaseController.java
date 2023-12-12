package ru.hzerr.fx.core.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.core.application.theme.DarkThemeMetaData;
import ru.hzerr.fx.core.application.theme.WhiteThemeMetaData;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.theme.ResolveThemeException;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import java.util.Locale;

@Registered
@FXController
@FXEntity(fxml = "main.fxml", internationalization = "main.json", theme = "main.css")
public class BaseController extends Controller {

    @FXML
    private Button changeLocalization;

    @FXML
    private Button changeTheme;

    @FXML
    private Button destroy;

    private ILogProvider logProvider;

    @Override
    public void onInit() {
        destroy.setOnAction(e -> FXEngine.getContext().getScene().setRoot(new AnchorPane()));
        changeLocalization.setOnAction(e -> {
            logProvider.getLogger().debug("Текущий язык '" + FXEngine.getContext().getApplicationConfiguration().getLocale().getLanguage() + "'");
            if (FXEngine.getContext().getApplicationConfiguration().getLocale().equals(Locale.ENGLISH)) {
                FXEngine.getContext().getApplicationManager().setLanguage(Locale.of("ru", "RU"));
                logProvider.getLogger().debug("Язык приложения изменен на 'Русский'");
            } else {
                FXEngine.getContext().getApplicationManager().setLanguage(Locale.ENGLISH);
                logProvider.getLogger().debug("Язык приложения изменен на 'English'");
            }
        });
        changeTheme.setOnAction(e -> {
            // TODO: 12.11.2023 ДОПИСАТь
            if (FXEngine.getContext().getApplicationConfiguration().getThemeName().equals("White")) {
                try {
                    FXEngine.getContext().getApplicationManager().changeTheme(DarkThemeMetaData.class);
                } catch (ResolveThemeException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    FXEngine.getContext().getApplicationManager().changeTheme(WhiteThemeMetaData.class);
                } catch (ResolveThemeException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    public void onChangeLanguage(Localization languagePack) {
        destroy.setText(languagePack.getConfiguration().getString("button.destroy.parent"));
        changeLocalization.setText(languagePack.getConfiguration().getString("button.change.localization"));
        changeTheme.setText(languagePack.getConfiguration().getString("button.change.theme"));
    }

    @Override
    protected String id() {
        return "main";
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
