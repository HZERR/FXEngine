package ru.hzerr.fx.engine.core.theme;

import org.springframework.context.annotation.Scope;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import javax.annotation.Nullable;
import java.net.URL;

@Registered
@Scope("prototype")
public class CSSResolver implements ICSSResolver {

    private final ThemeMetaData themeMetaData;
    private final Controller controller;

    @IncludeAs("engineLogProvider")
    private ILogProvider engineLogProvider;

    @IncludeAs("engineLoggingLocalizationProvider")
    private ILocalizationProvider engineLoggingLocalizationProvider;

    @Nullable
    private ILocation initialLocation;

    private ClassLoader resourceLoader = ClassLoader.getSystemClassLoader();

    public CSSResolver(ThemeMetaData themeMetaData, Controller controller) {
        this.themeMetaData = themeMetaData;
        this.controller = controller;
    }

    @Override
    public ResolvedThemeLocation resolve() throws ResolveThemeException {
        engineLogProvider.getLogger().debug("fxEngine.themeLoader.prepareTheme", themeMetaData.getName(), controller.getClass().getSimpleName());

        String theme = controller.getMetaData().theme();
//        if (StringUtils.isNotEmpty(entity.theme())) {
            String themeFileLocation = resolve(initialLocation, new BaseLocation(themeMetaData.getPackage()), new BaseLocation(theme));
            URL themeFileLocationAsURL = resourceLoader.getResource(themeFileLocation);

            if (themeFileLocationAsURL != null) {
                String resolvedThemeLocation = themeFileLocationAsURL.toExternalForm();
                engineLogProvider.getLogger().debug("fxEngine.themeLoader.themeOnTargetControllerSuccessfullySettingUp", themeMetaData.getName(), controller.getClass().getSimpleName());
                return new ResolvedThemeLocation(themeMetaData, resolvedThemeLocation);
            } else
                throw new CSSFileNotFoundException(engineLoggingLocalizationProvider.getLocalization().getConfiguration().getString("fxEngine.themeLoader.targetThemeFileLocationNotFoundException", themeMetaData.getName(), themeFileLocation));
//        } else
//            engineLogProvider.getLogger().debug("fxEngine.themeLoader.skippingUseThemeOnTargetController", themeMetaData.getName(), controller.getClass().getSimpleName());
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
