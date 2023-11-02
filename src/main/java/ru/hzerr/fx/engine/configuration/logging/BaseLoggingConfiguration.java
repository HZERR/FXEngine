package ru.hzerr.fx.engine.configuration.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import org.apache.commons.configuration2.PropertiesConfiguration;
import ru.hzerr.fx.engine.annotation.Include;
import ru.hzerr.fx.engine.annotation.MetaData;
import ru.hzerr.fx.engine.annotation.Registered;
import ru.hzerr.fx.engine.configuration.logging.naming.strategy.LoggingConfigurationNamingStrategy;
import ru.hzerr.fx.engine.configuration.interfaces.hardcode.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.configuration.interfaces.ILoggingConfiguration;
import ru.hzerr.fx.engine.configuration.interfaces.ILoggingConfigurationNamingStrategy;

import java.nio.charset.Charset;
import java.util.Locale;

@Registered
public class BaseLoggingConfiguration implements ILoggingConfiguration {

    protected static final Locale LOCALE_RU = new Locale("ru", "RU");

    private final PropertiesConfiguration configuration;
    private final IReadOnlyLoggingConfiguration readOnlyLoggingConfiguration;
    private final ILoggingConfigurationNamingStrategy namingStrategy = new LoggingConfigurationNamingStrategy();

    @Include
    public BaseLoggingConfiguration(@MetaData("applicationFileBasedConfiguration") PropertiesConfiguration configuration, IReadOnlyLoggingConfiguration readOnlyLoggingConfiguration) {
        this.readOnlyLoggingConfiguration = readOnlyLoggingConfiguration;
        this.configuration = configuration;
    }

    @Override
    public String getLogFileName() {
        return configuration.getString(namingStrategy.fileName(), readOnlyLoggingConfiguration.getLogFileName());
    }

    @Override
    public void setLogFileName(String logFileName) {
         configuration.setProperty(namingStrategy.fileName(), logFileName);
    }

    @Override
    public String getLoggerConsolePattern() {
        return configuration.getString(namingStrategy.consolePattern(), readOnlyLoggingConfiguration.getLoggerConsolePattern());
    }

    @Override
    public void setLoggerConsolePattern(String consolePattern) {
        configuration.setProperty(namingStrategy.consolePattern(), consolePattern);
    }

    @Override
    public String getLoggerFilePattern() {
        return configuration.getString(namingStrategy.filePattern(), readOnlyLoggingConfiguration.getLoggerFilePattern());
    }

    @Override
    public void setLoggerFilePattern(String filePattern) {
        configuration.setProperty(namingStrategy.filePattern(), filePattern);
    }

    @Override
    public String getLoggerName() {
        return configuration.getString(namingStrategy.loggerName(), readOnlyLoggingConfiguration.getLoggerName());
    }

    @Override
    public void setLoggerName(String loggerName) {
        configuration.setProperty(namingStrategy.loggerName(), loggerName);
    }

    @Override
    public Charset getFileEncoding() {
        return configuration.get(Charset.class, namingStrategy.fileEncoding(), readOnlyLoggingConfiguration.getFileEncoding());
    }

    @Override
    public void setFileEncoding(Charset fileEncoding) {
        configuration.setProperty(namingStrategy.fileEncoding(), fileEncoding);
    }

    @Override
    public Charset getConsoleEncoding() {
        return configuration.get(Charset.class, namingStrategy.consoleEncoding(), readOnlyLoggingConfiguration.getConsoleEncoding());
    }

    @Override
    public void setConsoleEncoding(Charset consoleEncoding) {
        configuration.setProperty(namingStrategy.consoleEncoding(), consoleEncoding);
    }

    @Override
    public Level getLoggerLevel() {
        return configuration.get(Level.class, namingStrategy.loggerLevel(), readOnlyLoggingConfiguration.getLoggerLevel());
    }

    @Override
    public void setLoggerLevel(Level loggerLevel) {
        configuration.setProperty(namingStrategy.loggerLevel(), loggerLevel);
    }

    @Override
    public String getApplicationLoggingLanguageFileName() {
        return configuration.getString(namingStrategy.applicationLoggingInternationalizationFileName(), readOnlyLoggingConfiguration.getApplicationLoggingLanguageFileName());
    }

    @Override
    public void setApplicationLoggingLanguageFileName(String applicationLoggingLanguageFileName) {
        configuration.setProperty(namingStrategy.applicationLoggingInternationalizationFileName(), applicationLoggingLanguageFileName);
    }

    @Override
    public boolean isEngineLoggingEnabled() {
        return configuration.getBoolean(namingStrategy.engineLoggingEnabled(), readOnlyLoggingConfiguration.isEngineLoggingEnabled());
    }

    @Override
    public void setEngineLoggingEnabled(boolean engineLoggingEnabled) {
        configuration.setProperty(namingStrategy.engineLoggingEnabled(), engineLoggingEnabled);
    }

    @Override
    public boolean isEnabled() {
        return configuration.getBoolean(namingStrategy.loggingEnabled(), readOnlyLoggingConfiguration.isEnabled());
    }

    @Override
    public void setEnabled(boolean enabled) {
        configuration.setProperty(namingStrategy.loggingEnabled(), enabled);
    }

    @Override
    public boolean isConsoleLoggingEnabled() {
        return configuration.getBoolean(namingStrategy.consoleLoggingEnabled(), readOnlyLoggingConfiguration.isConsoleLoggingEnabled());
    }

    @Override
    public void setConsoleLoggingEnabled(boolean consoleLoggingEnabled) {
        configuration.setProperty(namingStrategy.consoleLoggingEnabled(), consoleLoggingEnabled);
    }

    @Override
    public boolean isFileLoggingEnabled() {
        return configuration.getBoolean(namingStrategy.fileLoggingEnabled(), readOnlyLoggingConfiguration.isFileLoggingEnabled());
    }

    @Override
    public void setFileLoggingEnabled(boolean fileLoggingEnabled) {
        configuration.setProperty(namingStrategy.fileLoggingEnabled(), fileLoggingEnabled);
    }

    @Override
    public PatternLayout getConsolePatternLayout() {
        return configuration.get(PatternLayout.class, namingStrategy.consolePatternLayout(), readOnlyLoggingConfiguration.getConsolePatternLayout());
    }

    @Override
    public void setConsolePatternLayout(PatternLayout consolePatternLayout) {
        configuration.setProperty(namingStrategy.consolePatternLayout(), consolePatternLayout);
    }

    public boolean isInternationalizationEnabled() {
        return configuration.getBoolean(namingStrategy.internationalizationEnabled(), readOnlyLoggingConfiguration.isInternationalizationEnabled());
    }

    public void setInternationalizationEnabled(boolean internationalizationEnabled) {
        configuration.setProperty(namingStrategy.internationalizationEnabled(), internationalizationEnabled);
    }

    @Override
    public Locale getApplicationLocale() {
        return LOCALE_RU;
    }

    @Override
    public Locale getEngineLocale() {
        return Locale.ENGLISH;
    }
}
