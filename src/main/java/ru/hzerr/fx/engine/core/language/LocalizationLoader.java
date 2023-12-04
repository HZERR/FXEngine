package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import org.springframework.context.annotation.Scope;
import ru.hzerr.fx.engine.configuration.application.IClassLoaderProvider;
import ru.hzerr.fx.engine.core.Loader;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.path.ILocation;

@Registered
@Scope("prototype")
public class LocalizationLoader implements Loader<Localization> {

    private final BaseLocalizationMetaData metaData;
    private final String location;

    private IClassLoaderProvider classLoaderProvider;

    private LocalizationLoader(BaseLocalizationMetaData metaData, ILocation location) {
        this(metaData, location.getLocation());
    }

    private LocalizationLoader(BaseLocalizationMetaData metaData, String location) {
        this.metaData = metaData;
        this.location = location;
    }

    @Override
    public Localization load() {
        Config config = ConfigFactory.parseResourcesAnySyntax(classLoaderProvider.getApplicationResourceClassLoader(), location, ConfigParseOptions.defaults().setSyntax(metaData.getSyntax()));
        return new Localization(metaData, config);
    }

    @Include
    public void setClassLoaderProvider(IClassLoaderProvider classLoaderProvider) {
        this.classLoaderProvider = classLoaderProvider;
    }
}
