package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import ru.hzerr.fx.engine.configuration.application.FXRuntime;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.Loader;
import ru.hzerr.fx.engine.core.path.ILocation;

public class LocalizationLoader implements Loader<Localization> {

    private final BaseLocalizationMetaData metaData;
    private final String location;

    private final ClassLoader resourceClassLoader = FXEngine.getContext() != null ?
            FXEngine.getContext().getBean(FXRuntime.class).getResourceLoader() :
            ClassLoader.getSystemClassLoader();

    private LocalizationLoader(BaseLocalizationMetaData metaData, ILocation location) {
        this(metaData, location.getLocation());
    }

    private LocalizationLoader(BaseLocalizationMetaData metaData, String location) {
        this.metaData = metaData;
        this.location = location;
    }

    @Override
    public Localization load() {
        Config config = ConfigFactory.parseResourcesAnySyntax(resourceClassLoader, location, ConfigParseOptions.defaults().setSyntax(metaData.getSyntax()));
        return new Localization(metaData, config);
    }

    public static LocalizationLoader from(BaseLocalizationMetaData metaData, String location) {
        return new LocalizationLoader(metaData, location);
    }

    public static LocalizationLoader from(BaseLocalizationMetaData metaData, ILocation location) {
        return from(metaData, location.getLocation());
    }
}
