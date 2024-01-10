package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import ru.hzerr.fx.engine.configuration.application.IClassLoaderProvider;
import ru.hzerr.fx.engine.core.Loader;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.path.ILocation;

public abstract class LocalizationLoader<T> implements Loader<T> {

    protected final BaseLocalizationMetaData metaData;
    protected final String location;

    protected IClassLoaderProvider classLoaderProvider;

    protected LocalizationLoader(BaseLocalizationMetaData metaData, ILocation location) {
        this(metaData, location.getLocation());
    }

    protected LocalizationLoader(BaseLocalizationMetaData metaData, String location) {
        this.metaData = metaData;
        this.location = location;
    }

    protected Config loadConfig() {
        return ConfigFactory.parseResourcesAnySyntax(classLoaderProvider.getApplicationResourceClassLoader(), location, ConfigParseOptions.defaults().setSyntax(metaData.getSyntax()));
    }

    @Include
    public void setClassLoaderProvider(IClassLoaderProvider classLoaderProvider) {
        this.classLoaderProvider = classLoaderProvider;
    }
}
