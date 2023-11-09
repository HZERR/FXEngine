package ru.hzerr.fx.core.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.engine.annotation.FXEntity;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.LanguagePack;
import ru.hzerr.fx.engine.core.theme.Theme;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

@FXEntity(fxml = "fxml/main.fxml", internationalization = "main.json", theme = "main.css")
public class FXController extends Controller {
    @FXML
    private Button click;
    @FXML
    private AnchorPane root;

    private ILogProvider logProvider = FXEngine.getContext().getApplicationLogProvider();


    @Override
    public void onInit() {
        click.setText("РАБОТАЕТ");
        logProvider.getLogger().info("FXController has been successfully initialized");
    }

    @Override
    public void onDestroy() {
        logProvider.getLogger().info("FXController has been successfully destroyed");
    }

    @Override
    protected void onChangeLanguage(LanguagePack languagePack) {
        click.setText(languagePack.getConfiguration().getString("button.click"));
    }

    @Override
    protected void onChangeUI(Theme theme) {

    }
}
