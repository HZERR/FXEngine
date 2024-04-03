package ru.hzerr.fx.engine.core.entity;

import com.google.common.util.concurrent.Monitor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import ru.hzerr.fx.engine.configuration.application.IApplicationConfiguration;
import ru.hzerr.fx.engine.core.annotation.*;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationManager;
import ru.hzerr.fx.engine.core.annotation.as.EngineLogProvider;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.fx.engine.core.language.localization.EntityLocalization;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.theme.LoadThemeException;
import ru.hzerr.fx.engine.core.theme.LoadedThemeData;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is an abstract class that serves as a base class for all controllers in the application.
 * It provides a set of common methods and properties that can be used by all controllers.
 *
 * @author HZERR
 */
public abstract class Controller {

    /**
     * This field represents the parent node that contains all the UI elements of the controller.
     */
    @FXML
    private Parent content;

    /**
     * This field represents the engine's log provider.
     */
    private ILogProvider engineLogProvider;

    /**
     * This field represents the application's log provider.
     */
    private ILogProvider applicationLogProvider;

    private IApplicationManager applicationManager;
    private IApplicationConfiguration applicationConfiguration;

    private final BooleanProperty destroy = new SimpleBooleanProperty(false);

    /**
     * This field represents the localization service.
     */
    private final ObjectProperty<ILocalizationProvider<EntityLocalization>> localizationProvider = new SimpleObjectProperty<>();
    private final Monitor monitor = new Monitor();

    /**
     * This method is called when the controller is initialized. It performs the following tasks:
     * <ul>
     *     <li>Registers the controller with the application manager.</li>
     *     <li>Logs a debug message indicating that the controller was successfully registered.</li>
     *     <li>Sets the current locale to the locale specified in the application configuration.</li>
     *     <li>Sets the localization service to the localization service associated with the current locale.</li>
     *     <li>Calls the onInit method to allow the controller to perform any initialization tasks.</li>
     *     <li>Applies the current theme to the controller.</li>
     *     <li>Logs a debug message indicating that the controller initialization was successful.</li>
     * </ul>
     *
     * @throws LoadThemeException if there was an error resolving the theme
     */
    public final void initialize() throws LoadThemeException {
        onConnectDestroyEvent();
        applicationManager.register(id(), this);
        engineLogProvider.getLogger().debug("fxEngine.controller.initialize.controllerSuccessfullyRegistered", this.getClass().getSimpleName());
        applicationManager.changeLanguage(applicationConfiguration.getLocale());
        onInit();
        applicationManager.applyTheme(this);
        engineLogProvider.getLogger().debug("fxEngine.controller.initialize.success", getClass().getSimpleName());
    }

    /**
     * This method is called when the controller is initialized. It is intended to be overridden by subclasses to
     * perform any initialization tasks that are specific to their implementation.
     */
    protected abstract void onInit();

    /**
     * This method is called when the controller is connected to the scene graph. It is intended to be overridden by
     * subclasses to perform any tasks that should occur when the controller is added to the scene graph.
     */
    protected void onConnectDestroyEvent() {
        content.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                onDestroy();
            }
        });
    }

    /**
     * This method is called when the controller is being destroyed. It is intended to be overridden by subclasses to
     * perform any cleanup tasks that are specific to their implementation.
     */
    public void onDestroy() {
        applicationManager.unregister(id());
        destroy.set(true);
        engineLogProvider.getLogger().debug("fxEngine.controller.onDestroy", getClass().getSimpleName());
    }

    /**
     * This method applies the specified theme to the controller. It removes any existing stylesheets from the content
     * node and adds the specified theme stylesheet to the beginning of the list.
     *
     * @param theme the theme to apply
     */
    @SideOnly(Side.CORE)
    public final void applyTheme(LoadedThemeData theme) {
        getContentAsParent().getStylesheets().clear();
        getContentAsParent().getStylesheets().addFirst(theme.getStylesheet());
    }

    /**
     * This method is called when the language of the application is changed. It is intended to be overridden by
     * subclasses to perform any tasks that are necessary when the language is changed.
     *
     * @param localization the localization service
     */
    @SideOnly(Side.CORE)
    protected abstract void onChangeLanguage(ILocalization localization);

    /**
     * This method returns the ID of the controller. It is intended to be overridden by subclasses to return a unique ID
     * for the controller.
     *
     * @return the ID of the controller
     */
    protected abstract String id();

    /**
     * This method returns the metadata associated with the controller. It is intended to be overridden by subclasses to
     * return the metadata associated with the controller.
     *
     * @return the metadata associated with the controller
     */
    public FXEntity getMetaData() {
        return this.getClass().getAnnotation(FXEntity.class);
    }

    /**
     * This method returns the content node of the controller. It is intended to be overridden by subclasses to return
     * the content node of the controller.
     *
     * @param cls the class of the content node
     * @return the content node of the controller
     */
    public <T extends Parent> T getContent(Class<T> cls) {
        //noinspection unchecked
        return (T) content;
    }

    /**
     * This method returns the content node of the controller as a Parent. It is intended to be overridden by
     * subclasses to return the content node of the controller.
     *
     * @return the content node of the controller
     */
    public Parent getContentAsParent() {
        return content;
    }

    public BooleanProperty destroyProperty() {
        return destroy;
    }

    public boolean isDestroy() {
        return destroy.get();
    }

    @EngineLogProvider
    private void setEngineLogProvider(ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }

    @ApplicationLogProvider
    private void setApplicationLogProvider(ILogProvider applicationLogProvider) {
        this.applicationLogProvider = applicationLogProvider;
    }

    @SideOnly(Side.APPLICATION)
    protected ILogProvider getLogProvider() {
        if (applicationLogProvider != null) {
            return applicationLogProvider;
        }

        throw new UnsupportedOperationException("Application log provider is not set. Please load the controller via ExtendedAnnotationConfigApplicationContext. " +
                "See the EntityLoader.load(SpringLoadMetaData metaData) method for more information");
    }

    @ApplicationManager
    private void setApplicationManager(IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    public IApplicationManager getApplicationManager() {
        return applicationManager;
    }

    @Include
    private void setApplicationConfiguration(IApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    public IApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    @Preview(version = "1.2.7E")
    public void acceptLocalizationProvider(Consumer<? super ILocalizationProvider<EntityLocalization>> action) {
        monitor.enter();
        try {
            action.accept(localizationProvider.get());
        } finally {
            monitor.leave();
        }
    }

    @Preview(version = "1.2.7E")
    public <T> T applyLocalizationProvider(Function<? super ILocalizationProvider<EntityLocalization>, T> action) {
        monitor.enter();
        try {
            return action.apply(localizationProvider.get());
        } finally {
            monitor.leave();
        }
    }

    @Preview(version = "1.2.7E")
    public void addLocalizationChangeListener(ChangeListener<? super ILocalizationProvider<EntityLocalization>> action) {
        monitor.enter();
        try {
            localizationProvider.addListener(action);
        } finally {
            monitor.leave();
        }
    }

    @Preview(version = "1.2.7E")
    public void removeLocalizationChangeListener(ChangeListener<? super ILocalizationProvider<EntityLocalization>> action) {
        monitor.enter();
        try {
            localizationProvider.removeListener(action);
        } finally {
            monitor.leave();
        }
    }

    @SideOnly(Side.CORE)
    public void setLocalizationProvider(ILocalizationProvider<EntityLocalization> localizationProvider) {
        monitor.enter();
        try {
            this.localizationProvider.set(localizationProvider);
        } finally {
            monitor.leave();
        }
    }
}
