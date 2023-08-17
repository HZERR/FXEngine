package ru.hzerr.fx.engine.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.engine.annotation.FXEntity;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.LanguagePack;
import ru.hzerr.fx.engine.core.theme.Theme;
import ru.hzerr.fx.engine.logging.factory.FXApplicationLogProvider;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

@FXEntity(fxml = "fxml/main.fxml", internationalization = "main.conf", theme = "main.css")
public class FXController extends Controller {

    @FXML
    private Button click;

    @FXML
    private AnchorPane root;

    private ILogProvider logProvider = FXEngine.getContext().getBeanByQualifier(FXApplicationLogProvider.class);


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
        click.setText(languagePack.getConfiguration().getString("buttonclick"));
    }

    @Override
    protected void onChangeUI(Theme theme) {

    }
}
