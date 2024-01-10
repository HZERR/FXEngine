package ru.hzerr.fx.engine.core.theme;

import ru.hzerr.fx.engine.configuration.application.IClassLoaderProvider;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.RegisteredPrototype;
import ru.hzerr.fx.engine.core.annotation.as.EngineLogProvider;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.localization.EngineLoggingLocalizationProvider;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import javax.annotation.Nullable;
import java.net.URL;

@RegisteredPrototype
public class CSSLoader implements ICSSLoader {

    private final ThemeMetaData themeMetaData;
    private final Controller controller;

    private ILogProvider engineLogProvider;
    private EngineLoggingLocalizationProvider engineLoggingLocalizationProvider;
    private IClassLoaderProvider classLoaderProvider;

    @Nullable
    private ILocation initialLocation;

    public CSSLoader(ThemeMetaData themeMetaData, Controller controller) {
        this.themeMetaData = themeMetaData;
        this.controller = controller;
    }

    @Override
    public LoadedThemeData resolve() throws LoadThemeException {
        engineLogProvider.getLogger().debug("fxEngine.themeLoader.prepareTheme", themeMetaData.getName(), controller.getClass().getSimpleName());

        String theme = controller.getMetaData().theme();
//        if (StringUtils.isNotEmpty(entity.theme())) {
        String themeFileLocation = resolve(initialLocation, themeMetaData.getPackage(), theme);
        URL themeFileLocationAsURL = classLoaderProvider.getApplicationResourceClassLoader().getResource(themeFileLocation);

        if (themeFileLocationAsURL != null) {
            String resolvedThemeLocation = themeFileLocationAsURL.toExternalForm();
            engineLogProvider.getLogger().debug("fxEngine.themeLoader.themeOnTargetControllerSuccessfullySettingUp", themeMetaData.getName(), controller.getClass().getSimpleName());
            return new LoadedThemeData(themeMetaData, resolvedThemeLocation);
        } else
            throw new CSSFileNotFoundException(engineLoggingLocalizationProvider.getLocalization().getConfiguration().getString("fxEngine.themeLoader.targetThemeFileLocationNotFoundException", themeMetaData.getName(), themeFileLocation));
//        } else
//            engineLogProvider.getLogger().debug("fxEngine.themeLoader.skippingUseThemeOnTargetController", themeMetaData.getName(), controller.getClass().getSimpleName());
    }

    private String resolve(@Nullable ILocation initialLocation, String themeFolder, String relativePath) {
        String currentThemeLocation = resolveCurrentThemeLocation(initialLocation, themeFolder);

        return LocationTools.resolve(
                ResolvableLocation.of(currentThemeLocation, NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION),
                ResolvableLocation.of(relativePath, NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION),
                SeparatorResolveLocationOptions.DEFAULT,
                Separator.SLASH_SEPARATOR
        );
    }

    private String resolveCurrentThemeLocation(@Nullable ILocation initialLocation, String themeFolder) {
        if (initialLocation != null) {
            return LocationTools.resolve(
                    ResolvableLocation.of(
                            initialLocation,
                            NullSafeResolveLocationOptions.THROW_ILLEGAL_ARGUMENT_EXCEPTION
                    ),
                    ResolvableLocation.of(
                            themeFolder,
                            NullSafeResolveLocationOptions.REMOVE_EVERYWHERE
                    ),
                    SeparatorResolveLocationOptions.REMOVE_EVERYWHERE,
                    Separator.SLASH_SEPARATOR
            );
        } else
            return themeFolder;
    }

    public void setInitialLocation(ILocation initialLocation) {
        this.initialLocation = initialLocation;
    }

    @EngineLogProvider
    public void setEngineLogProvider(ILogProvider engineLogProvider) {
        this.engineLogProvider = engineLogProvider;
    }

    @ru.hzerr.fx.engine.core.annotation.as.EngineLoggingLocalizationProvider
    public void setEngineLoggingLocalizationProvider(EngineLoggingLocalizationProvider engineLoggingLocalizationProvider) {
        this.engineLoggingLocalizationProvider = engineLoggingLocalizationProvider;
    }

    @Include
    public void setClassLoaderProvider(IClassLoaderProvider classLoaderProvider) {
        this.classLoaderProvider = classLoaderProvider;
    }
}
