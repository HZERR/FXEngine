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
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.ILocalization;

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
    private ChangeThemeProcessor themeProcessor;
    private ChangeLocalizationProcessor localizationProcessor;
    private DestroyContentProcessor contentProcessor;

    @Override
    public void onInit() {
        getLogProvider().getLogger().info("Selected assets directory: '{}'", FXEngine.getContext().getStructureConfigurationAs(IExtendedStructureConfiguration.class).getAssetsDirectory().getLocation());
        destroy.setOnAction(contentProcessor);
        changeLocalization.setOnAction(localizationProcessor);
        changeTheme.setOnAction(themeProcessor);
    }

    @Override
    public void onChangeLanguage(ILocalization languagePack) {
        destroy.setText(languagePack.getConfiguration().getString("button.destroy.parent"));
        changeLocalization.setText(languagePack.getConfiguration().getString("button.change.localization"));
        changeTheme.setText(languagePack.getConfiguration().getString("button.change.theme"));
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
}
