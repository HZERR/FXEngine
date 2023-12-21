package ru.hzerr.fx.core.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.hzerr.fx.core.application.event.processor.ChangeLocalizationProcessor;
import ru.hzerr.fx.core.application.event.processor.ChangeThemeProcessor;
import ru.hzerr.fx.core.application.event.processor.DestroyContentProcessor;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.*;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

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
    private ChangeThemeProcessor themeProcessor;
    private ChangeLocalizationProcessor localizationProcessor;
    private DestroyContentProcessor contentProcessor;

    @Override
    public void onInit() {
        logProvider.getLogger().info("Selected assets directory: '{}'", FXEngine.getContext().getStructureConfigurationAs(IExtendedStructureConfiguration.class).getAssetsDirectory().getLocation());
        destroy.setOnAction(contentProcessor);
        changeLocalization.setOnAction(localizationProcessor);
        changeTheme.setOnAction(themeProcessor);
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
