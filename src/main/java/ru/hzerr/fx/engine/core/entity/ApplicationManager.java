package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.annotation.Include;
import ru.hzerr.fx.engine.annotation.IncludeAs;
import ru.hzerr.fx.engine.configuration.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.entity.exception.LanguagePackMetaDataNotFoundException;
import ru.hzerr.fx.engine.core.language.ApplicationLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.BaseLocalizationMetaData;
import ru.hzerr.fx.engine.core.language.LanguagePackLoader;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.core.theme.Theme;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationManager implements IApplicationManager {

    protected final Map<String, Controller> controllers = new ConcurrentHashMap<>();

    @IncludeAs("engineLoggingLocalizationProvider")
    protected ILocalizationProvider localizationProvider;

    @Include
    protected IResourceStructureConfiguration resourceStructureConfiguration;

    @Override
    public void register(String id, Controller controller) {
        controllers.put(id, controller);
    }

    @Override
    public void unregister(String id) {
        controllers.remove(id);
    }

    @Override
    public void setLanguage(Locale locale) {
        controllers.forEach((id, controller) -> controller.onChangeLanguage(getControllerInternationalizationFile(controller, locale)));
    }

    @Override
    public void setTheme(Theme theme) {
        controllers.forEach((id, controller) -> controller.onChangeUI(theme));
    }

    private Localization getControllerInternationalizationFile(Controller controller, Locale locale) {
        BaseLocalizationMetaData currentLanguageMetaData = getApplicationLanguageMetaData(locale);
        String currentLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        resourceStructureConfiguration.getApplicationInternationalizationPackage(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                        currentLanguageMetaData.getILocation(),
                        NullSafeResolveLocationOptions.INSERT_EVERYWHERE
                ),
                SeparatorResolveLocationOptions.INSERT_END,
                Separator.SLASH_SEPARATOR
        );

        String currentLanguagePackLocation = LocationTools.resolve(
                ResolvableLocation.of(currentLanguagePackageLocation, NullSafeResolveLocationOptions.THROW_EXCEPTION),
                ResolvableLocation.of(controller.getMetaData().internationalization(), NullSafeResolveLocationOptions.THROW_EXCEPTION),
                SeparatorResolveLocationOptions.DEFAULT,
                Separator.SLASH_SEPARATOR
        );

        LanguagePackLoader languagePackLoader = new LanguagePackLoader(currentLanguageMetaData, currentLanguagePackLocation);
        return languagePackLoader.load();
    }

    private BaseLocalizationMetaData getApplicationLanguageMetaData(Locale locale) {
        for (BaseLocalizationMetaData metaData: FXEngine.getContext().getBeansOfType(ApplicationLocalizationMetaData.class).values()) {
            if (metaData.getLocale().equals(locale)) {
                return metaData;
            }
        }

        throw new LanguagePackMetaDataNotFoundException(localizationProvider.getLocalization().getConfiguration().getString(
                "fx.engine.controller.getApplicationLanguageMetaData.languageMetaDataNotFoundException", locale.getLanguage() + "/" + locale.getCountry()
        ));
    }
}
