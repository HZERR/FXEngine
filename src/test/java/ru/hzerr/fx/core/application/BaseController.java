package ru.hzerr.fx.core.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.hzerr.fx.core.application.event.processor.ChangeLocalizationProcessor;
import ru.hzerr.fx.core.application.event.processor.ChangeThemeProcessor;
import ru.hzerr.fx.core.application.event.processor.DestroyContentProcessor;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.interfaces.engine.IFXEnvironment;
import ru.hzerr.fx.engine.core.interfaces.localization.ILocalization;

@FXController
@FXEntity(fxml = "main.fxml", internationalization = "main.json", theme = "main.css")
public class BaseController extends Controller {

    @FXML
    private Button changeLocalization, changeTheme, destroy;

    private ChangeThemeProcessor themeProcessor;
    private ChangeLocalizationProcessor localizationProcessor;
    private DestroyContentProcessor contentProcessor;

    private IFXEnvironment environment;

    @Override
    public void onInit() {
        getLogProvider().getLogger().info("Selected assets directory: '{}'", FXEngine.getContext().getStructureConfigurationAs(IExtendedStructureConfiguration.class).getAssetsDirectory().getLocation());
        getLogProvider().getLogger().info("Current stage title: '{}'", environment.getStage().getTitle());
        destroy.setOnAction(contentProcessor);
        changeLocalization.setOnAction(localizationProcessor);
        changeTheme.setOnAction(themeProcessor);
    }

    @Override
    public void onChangeLanguage(ILocalization localization) {
        destroy.setText(localization.getConfiguration().getString("button.destroy.parent"));
        changeLocalization.setText(localization.getConfiguration().getString("button.change.localization"));
        changeTheme.setText(localization.getConfiguration().getString("button.change.theme"));
    }

    @Override
    protected String id() {
        return "main";
    }

    @Include
    public void setThemeProcessor(ChangeThemeProcessor themeProcessor) {
        this.themeProcessor = themeProcessor;
    }

    @Include
    public void setLocalizationProcessor(ChangeLocalizationProcessor localizationProcessor) {
        this.localizationProcessor = localizationProcessor;
    }

    @Include
    public void setContentProcessor(DestroyContentProcessor contentProcessor) {
        this.contentProcessor = contentProcessor;
    }

    @Include
    public void setFXEnvironment(IFXEnvironment environment) {
        this.environment = environment;
    }
}
