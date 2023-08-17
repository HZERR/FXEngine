package ru.hzerr.fx.engine.core.language;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.configuration.FXConfiguration;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.Loader;

public class LanguagePackLoader implements Loader<LanguagePack> {

    private final BaseLanguageMetaData metaData;
    private final String folderNameWithAllLanguages = FXEngine.getContext().getStructureApplicationConfiguration().getLanguagePackage();
    private final StringBuilder folderNameCurrentLanguage;
    private final StringBuilder relativeConfigurationPath;

    private ClassLoader resourceClassLoader = FXEngine.getContext().getBean(FXConfiguration.class).getResourceLoader();

    public LanguagePackLoader(@NotNull BaseLanguageMetaData metaData,
                              @NotNull String relativeConfigurationPath) {
        this.metaData = metaData;
        this.folderNameCurrentLanguage = new StringBuilder(metaData.getRelativePath());
        this.relativeConfigurationPath = new StringBuilder(relativeConfigurationPath);
    }

    @Override
    public LanguagePack load() {
        normalize();
        String path = new StringBuilder(folderNameWithAllLanguages != null ? folderNameWithAllLanguages + '/' : "")
                .append(folderNameCurrentLanguage)
                .append('/')
                .append(relativeConfigurationPath)
                .toString();

        return new LanguagePack(metaData, ConfigFactory.parseResources(resourceClassLoader, path, ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES)));
    }

    private void normalize() {
        if (folderNameCurrentLanguage.charAt(0) == '/') {
            folderNameCurrentLanguage.deleteCharAt(0);
        }
        if (folderNameCurrentLanguage.charAt(folderNameCurrentLanguage.length() - 1) == '/') {
            folderNameCurrentLanguage.deleteCharAt(folderNameCurrentLanguage.length() - 1);
        }

        if (relativeConfigurationPath.charAt(0) == '/') {
            relativeConfigurationPath.deleteCharAt(0);
        }
        if (relativeConfigurationPath.charAt(relativeConfigurationPath.length() - 1) == '/') {
            relativeConfigurationPath.deleteCharAt(relativeConfigurationPath.length() - 1);
        }
    }
}
