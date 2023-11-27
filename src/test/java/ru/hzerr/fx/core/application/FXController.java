package ru.hzerr.fx.core.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.core.application.theme.DarkThemeMetaData;
import ru.hzerr.fx.core.application.theme.WhiteThemeMetaData;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.theme.ResolveThemeException;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import java.util.Locale;

@FXEntity(fxml = "fxml/main.fxml", internationalization = "main.json", theme = "main.css")
public class FXController extends Controller {

    @FXML
    private Button changeLocalization;

    @FXML
    private Button changeTheme;

    @FXML
    private Button destroy;

    private final ILogProvider logProvider = FXEngine.getContext().getApplicationLogProvider();

    @Override
    public void onInit() {
        destroy.setOnAction(event -> FXEngine.getContext().getScene().setRoot(new AnchorPane()));
        changeLocalization.setOnAction(event -> {
            logProvider.getLogger().debug("Текущий язык '" + FXEngine.getContext().getApplicationConfiguration().getLocale().getLanguage() + "'");
            if (FXEngine.getContext().getApplicationConfiguration().getLocale().equals(Locale.ENGLISH)) {
                FXEngine.getContext().getApplicationManager().setLanguage(new Locale("ru", "RU"));
                logProvider.getLogger().debug("Язык приложения изменен на 'Русский'");
            } else {
                FXEngine.getContext().getApplicationManager().setLanguage(Locale.ENGLISH);
                logProvider.getLogger().debug("Язык приложения изменен на 'English'");
            }
        });
        changeTheme.setOnAction((e) -> {
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
}
