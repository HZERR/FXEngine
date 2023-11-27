package ru.hzerr.fx.engine.core.theme;

import ru.hzerr.fx.engine.configuration.application.FXRuntime;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.core.LoadException;
import ru.hzerr.fx.engine.core.Loader;

import java.net.URL;

public class StylesheetLoader implements Loader<String> {

    private final ILocation location;

    private ILocalizationProvider engineLocalizationProvider = FXEngine.getContext().getEngineLocalizationProvider();

    public StylesheetLoader(ILocation location) {
        this.location = location;
    }

    @Override
    public String resolve() throws LoadException {
//        String currentThemeLocation = LocationTools.resolve(
//                ResolvableLocation.of(
//                        initialPackage,
//                        NullSafeResolveLocationOptions.THROW_EXCEPTION
//                ),
//                ResolvableLocation.of(
//                        theme.,
//                        NullSafeResolveLocationOptions.INSERT_EVERYWHERE
//                ),
//                SeparatorResolveLocationOptions.INSERT_END,
//                Separator.SLASH_SEPARATOR
//        );
//
//        String stylesheetLocation = LocationTools.resolve(
//                ResolvableLocation.of(currentThemeLocation, NullSafeResolveLocationOptions.THROW_EXCEPTION),
//                ResolvableLocation.of(relativePath, NullSafeResolveLocationOptions.THROW_EXCEPTION),
//                SeparatorResolveLocationOptions.DEFAULT,
//                Separator.SLASH_SEPARATOR
//        );

        URL stylesheetLocation = FXEngine.getContext().getBean(FXRuntime.class).getResourceLoader().getResource(location.getLocation());

        if (stylesheetLocation != null) {
            return stylesheetLocation.toExternalForm();
        } else
            throw new LoadException(engineLocalizationProvider.getLocalization().getConfiguration().getString("fxEngine.stylesheetLoader.stylesheetLocationNotFoundException", location));
    }
}
