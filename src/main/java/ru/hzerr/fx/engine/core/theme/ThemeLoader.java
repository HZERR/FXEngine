package ru.hzerr.fx.engine.core.theme;

import org.apache.commons.lang3.StringUtils;
import ru.hzerr.collections.list.HList;
import ru.hzerr.fx.engine.core.LoadException;
import ru.hzerr.fx.engine.core.Loader;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

import javax.annotation.Nullable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ThemeLoader implements Loader<Theme> {

    private final ThemeMetaData themeMetaData;
    private final HList<Class<? extends Controller>> controllers;

    @IncludeAs("engineLogProvider")
    private ILogProvider engineLogProvider;

    @IncludeAs("engineLoggingLocalizationProvider")
    private ILocalizationProvider engineLoggingLocalizationProvider;

    @Nullable
    private ILocation initialLocation;

    private ClassLoader resourceLoader = ClassLoader.getSystemClassLoader();

    @SafeVarargs
    public ThemeLoader(ThemeMetaData themeMetaData, Class<? extends Controller>... controllers) {
        this.themeMetaData = themeMetaData;
        this.controllers = HList.of(controllers);
    }

    public ThemeLoader(ThemeMetaData themeMetaData, HList<Class<? extends Controller>> controllers) {
        this.themeMetaData = themeMetaData;
        this.controllers = controllers;
    }

    @Override
    public Theme load() throws LoadException {
        engineLogProvider.getLogger().debug("fxEngine.themeLoader.loadingTheme", themeMetaData.getName());

        Map<Class<? extends Controller>, String> stylesheets = new HashMap<>();
        for (Class<? extends Controller> controller : controllers) {
            if (controller.isAnnotationPresent(FXEntity.class)) {
                FXEntity entity = controller.getAnnotation(FXEntity.class);
                if (StringUtils.isNotEmpty(entity.theme())) {
                    String themeFileLocation = resolve(initialLocation, new BaseLocation(themeMetaData.getPackage()), new BaseLocation(entity.theme()));
                    URL themeFileLocationAsURL = resourceLoader.getResource(themeFileLocation);

                    if (themeFileLocationAsURL != null) {
                        stylesheets.put(controller, themeFileLocationAsURL.toExternalForm());
                        engineLogProvider.getLogger().debug("fxEngine.themeLoader.themeOnTargetControllerSuccessfullySettingUp", themeMetaData.getName(), controller.getSimpleName());
                    } else
                        throw new LoadException(engineLoggingLocalizationProvider.getLocalization().getConfiguration().getString("fxEngine.themeLoader.targetThemeFileLocationNotFoundException", themeMetaData.getName(), themeFileLocation));
                } else
                    engineLogProvider.getLogger().debug("fxEngine.themeLoader.skippingUseThemeOnTargetController", themeMetaData.getName(), controller.getSimpleName());
            } else
                engineLogProvider.getLogger().debug("fxEngine.themeLoader.skippingUseThemeOnTargetController", themeMetaData.getName(), controller.getSimpleName());
        }

        engineLogProvider.getLogger().debug("fxEngine.themeLoader.themeSuccessfullySettingUp", themeMetaData.getName());
        return new Theme(themeMetaData, stylesheets);
    }

    private String resolve(@Nullable ILocation initialLocation, ILocation themeFolder, ILocation relativePath) {
        String currentThemeLocation = resolveCurrentThemeLocation(initialLocation, themeFolder);

        return LocationTools.resolve(
                ResolvableLocation.of(currentThemeLocation, NullSafeResolveLocationOptions.THROW_EXCEPTION),
                ResolvableLocation.of(relativePath, NullSafeResolveLocationOptions.THROW_EXCEPTION),
                SeparatorResolveLocationOptions.DEFAULT,
                Separator.SLASH_SEPARATOR
        );
    }

    private String resolveCurrentThemeLocation(@Nullable ILocation initialLocation, ILocation themeFolder) {
        if (initialLocation != null) {
            return LocationTools.resolve(
                    ResolvableLocation.of(
                            initialLocation,
                            NullSafeResolveLocationOptions.THROW_EXCEPTION
                    ),
                    ResolvableLocation.of(
                            themeFolder,
                            NullSafeResolveLocationOptions.REMOVE_EVERYWHERE
                    ),
                    SeparatorResolveLocationOptions.REMOVE_EVERYWHERE,
                    Separator.SLASH_SEPARATOR
            );
        } else
            return themeFolder.getLocation();
    }

    public void setInitialLocation(ILocation initialLocation) {
        this.initialLocation = initialLocation;
    }

    public void setResourceLoader(ClassLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
