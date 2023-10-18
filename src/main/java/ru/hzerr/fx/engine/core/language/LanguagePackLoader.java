package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import ru.hzerr.fx.engine.configuration.FXConfiguration;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.Loader;
import ru.hzerr.fx.engine.core.path.ILocation;
import ru.hzerr.fx.engine.core.path.ResourceLocation;

public class LanguagePackLoader implements Loader<LanguagePack> {

    private final BaseLanguagePackMetaData metaData;
    private final String location;

    private final ClassLoader resourceClassLoader = FXEngine.getContext() != null ?
            FXEngine.getContext().getBean(FXConfiguration.class).getResourceLoader() :
            ClassLoader.getSystemClassLoader();

    public LanguagePackLoader(BaseLanguagePackMetaData metaData, ILocation location) {
        this.metaData = metaData;
        this.location = location.getLocation();
    }

    public LanguagePackLoader(BaseLanguagePackMetaData metaData, String location) {
        this.metaData = metaData;
        this.location = location;
    }

    @Override
    public LanguagePack load() {
        Config config = ConfigFactory.parseResourcesAnySyntax(resourceClassLoader, location, ConfigParseOptions.defaults().setSyntax(metaData.getSyntax()));
        return new LanguagePack(metaData, config);
    }
}
