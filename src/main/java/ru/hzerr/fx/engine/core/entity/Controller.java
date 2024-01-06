package ru.hzerr.fx.engine.core.entity;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.*;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.fx.engine.core.theme.ResolveThemeException;
import ru.hzerr.fx.engine.core.theme.ResolvedThemeLocation;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;


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

    /**
     * This field represents the localization service.
     */
    private final AtomicReference<ILocalization> localization = new AtomicReference<>();

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
     * @throws ResolveThemeException if there was an error resolving the theme
     */
    public final void initialize() throws ResolveThemeException {
        onConnectDestroyEvent();
        FXEngine.getContext().getApplicationManager().register(id(), this);
        engineLogProvider.getLogger().debug("fxEngine.controller.initialize.controllerSuccessfullyRegistered", this.getClass().getSimpleName());
        Locale currentLocale = FXEngine.getContext().getApplicationConfiguration().getLocale();
        FXEngine.getContext().getApplicationManager().setLanguage(currentLocale);
        onInit();
        FXEngine.getContext().getApplicationManager().applyTheme(this);
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
    protected void onDestroy() {
        FXEngine.getContext().getApplicationManager().unregister(id());
        engineLogProvider.getLogger().debug("fxEngine.controller.onDestroy", getClass().getSimpleName());
    }

    /**
     * This method applies the specified theme to the controller. It removes any existing stylesheets from the content
     * node and adds the specified theme stylesheet to the beginning of the list.
     *
     * @param theme the theme to apply
     */
    public final void applyTheme(ResolvedThemeLocation theme) {
        getContentAsParent().getStylesheets().clear();
        getContentAsParent().getStylesheets().addFirst(theme.getStylesheet());
    }

    /**
     * This method is called when the language of the application is changed. It is intended to be overridden by
     * subclasses to perform any tasks that are necessary when the language is changed.
     *
     * @param localization the localization service
     */
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

    /**
     * This method sets the engine's log provider. It is intended to be called by the FXEngine to set the log provider.
     *
     * @param engineLogProvider the engine's log provider
     */
    void setEngineLogProvider(ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }

    @SideOnly(Side.CORE)
    @ApplicationLogProvider
    private void setApplicationLogProvider(ILogProvider applicationLogProvider) {
        this.applicationLogProvider = applicationLogProvider;
    }

    protected ILogProvider getLogProvider() {
        if (applicationLogProvider != null) {
            return applicationLogProvider;
        }

        throw new UnsupportedOperationException("Application log provider is not set. Please load the controller via ExtendedAnnotationConfigApplicationContext. " +
                "See the EntityLoader.load(SpringLoadMetaData metaData) method for more information");
    }

    /**
     * This method returns the localization service.
     *
     * @return the localization service
     */
    @Preview
    public ILocalization getLocalization() {
        return localization.getAcquire();
    }

    /**
     * This method sets the localization service. It is intended to be called by the FXEngine to set the localization service.
     *
     * @param localization the localization service
     */
    @SideOnly(Side.CORE)
    public void setLocalization(ILocalization localization) {
        this.localization.set(localization);
    }
}
