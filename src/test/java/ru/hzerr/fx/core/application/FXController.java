package ru.hzerr.fx.core.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.engine.annotation.FXEntity;
import ru.hzerr.fx.engine.configuration.FXConfiguration;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.theme.Theme;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

@FXEntity(fxml = "fxml/main.fxml", internationalization = "main.json", theme = "main.css")
public class FXController extends Controller {

    @FXML
    private Button click;

    private final ILogProvider logProvider = FXEngine.getContext().getApplicationLogProvider();

    @Override
    public void onInit() {
        click.setText("РАБОТАЕТ");
        click.setOnAction(event -> FXEngine.getContext().getBean(FXConfiguration.class).getScene().setRoot(new AnchorPane()));
    }

    @Override
    protected void onChangeLanguage(Localization languagePack) {
        click.setText(languagePack.getConfiguration().getString("button.click"));
    }

    @Override
    protected void onChangeUI(Theme theme) {

    }

    @Override
    protected String id() {
        return "main";
    }
}
