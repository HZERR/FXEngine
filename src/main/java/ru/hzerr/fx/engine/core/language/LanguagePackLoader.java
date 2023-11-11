package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import ru.hzerr.fx.engine.configuration.FXConfiguration;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.interfaces.Loader;
import ru.hzerr.fx.engine.core.path.ILocation;

public class LanguagePackLoader implements Loader<Localization> {

    private final BaseLocalizationMetaData metaData;
    private final String location;

    private final ClassLoader resourceClassLoader = FXEngine.getContext() != null ?
            FXEngine.getContext().getBean(FXConfiguration.class).getResourceLoader() :
            ClassLoader.getSystemClassLoader();

    public LanguagePackLoader(BaseLocalizationMetaData metaData, ILocation location) {
        this.metaData = metaData;
        this.location = location.getLocation();
    }

    public LanguagePackLoader(BaseLocalizationMetaData metaData, String location) {
        this.metaData = metaData;
        this.location = location;
    }

    @Override
    public Localization load() {
        Config config = ConfigFactory.parseResourcesAnySyntax(resourceClassLoader, location, ConfigParseOptions.defaults().setSyntax(metaData.getSyntax()));
        return new Localization(metaData, config);
    }
}
