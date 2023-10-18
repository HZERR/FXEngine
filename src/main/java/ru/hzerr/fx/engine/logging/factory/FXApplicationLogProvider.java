package ru.hzerr.fx.engine.logging.factory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.SizeType;
import ru.hzerr.fx.engine.annotation.LogProvider;
import ru.hzerr.fx.engine.configuration.ILoggingConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureApplicationConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureConfiguration;
import ru.hzerr.fx.engine.core.language.LanguagePack;
import ru.hzerr.fx.engine.core.language.LanguagePackLoader;
import ru.hzerr.fx.engine.core.language.MergedLanguagePack;
import ru.hzerr.fx.engine.core.path.*;
import ru.hzerr.fx.engine.logging.ConfigurableException;
import ru.hzerr.fx.engine.logging.FactoryCloseableException;
import ru.hzerr.fx.engine.logging.policy.CancelRollingPolicy;

import java.io.IOException;

@LogProvider("application.log.provider")
public class FXApplicationLogProvider implements ILogProvider {

    private ILoggingConfiguration applicationLoggingConfiguration;
    private IStructureApplicationConfiguration structureApplicationConfiguration;

    private final BaseFile sessionLogFile;
    private final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    private final PatternLayout consolePatternLayout;
    private final LayoutWrappingEncoder<ILoggingEvent> consoleEncoder = new LayoutWrappingEncoder<>();
    private final ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    private final CancelRollingPolicy filePolicy = new CancelRollingPolicy();
    private final PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
    private final RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();

    private Logger log;

    @Autowired
    public FXApplicationLogProvider(@NotNull ILoggingConfiguration applicationLoggingConfiguration,
                                    @NotNull IStructureConfiguration structureConfiguration,
                                    @NotNull IStructureApplicationConfiguration structureApplicationConfiguration) {

        this.applicationLoggingConfiguration = applicationLoggingConfiguration;
        this.structureApplicationConfiguration = structureApplicationConfiguration;
        sessionLogFile = structureConfiguration.getLoggingDirectory().getSubFile(applicationLoggingConfiguration.getNextLogFileName());
        consolePatternLayout = applicationLoggingConfiguration.getConsolePatternLayout();
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public void close() throws IOException {
        filePolicy.stop();
        fileEncoder.stop();
        fileAppender.stop();
        consolePatternLayout.stop();
        consoleEncoder.stop();
        consoleAppender.stop();
        lc.stop();
        if (sessionLogFile != null) {
            if (sessionLogFile.sizeOf(SizeType.BYTE) == 0D) {
                sessionLogFile.delete();
            }
        }
    }

    @Override
    public void configure() throws ConfigurableException {
        ch.qos.logback.classic.Logger logbackLogger = lc.getLogger(applicationLoggingConfiguration.getLoggerName());
        if (applicationLoggingConfiguration.isEnabled()) {
            if (applicationLoggingConfiguration.isConsoleLoggingEnabled()) {
                consolePatternLayout.setContext(lc);
                consolePatternLayout.setPattern(applicationLoggingConfiguration.getLoggerConsolePattern());
                consolePatternLayout.start();

                consoleEncoder.setLayout(consolePatternLayout);
                consoleEncoder.setCharset(applicationLoggingConfiguration.getConsoleEncoding());

                consoleAppender.setContext(lc);
                consoleAppender.setEncoder(consoleEncoder);
                consoleAppender.start();

                logbackLogger.addAppender(consoleAppender);
            }

            if (applicationLoggingConfiguration.isFileLoggingEnabled()) {
                if (sessionLogFile.notExists()) {
                    try {
                        sessionLogFile.create();
                    } catch (IOException io) {
                        throw new ConfigurableException("Unable to create a log file for the current session", io);
                    }
                }

                filePolicy.setContext(lc);
                filePolicy.setParent(fileAppender);
                filePolicy.setFileNamePattern("%d{yyyy-MM-dd HH-mm-ss}.log");
                filePolicy.start();

                fileEncoder.setContext(lc);
                fileEncoder.setCharset(applicationLoggingConfiguration.getFileEncoding());
                fileEncoder.setPattern(applicationLoggingConfiguration.getLoggerFilePattern());
                fileEncoder.start();

                fileAppender.setContext(lc);
                fileAppender.setEncoder(fileEncoder);
                fileAppender.setAppend(true);
                fileAppender.setFile(sessionLogFile.getLocation());
                fileAppender.setRollingPolicy(filePolicy);
                fileAppender.start();

                logbackLogger.addAppender(fileAppender);
            }

            logbackLogger.setLevel(applicationLoggingConfiguration.getLoggerLevel());
        } else
            logbackLogger.setLevel(Level.OFF);

        logbackLogger.setAdditive(false);
        Runtime.getRuntime().addShutdownHook(new Thread(this::safelyClose));
        // initialize target logger
        if (applicationLoggingConfiguration.isInternationalizationEnabled()) {
            log = new ru.hzerr.fx.engine.logging.Logger(logbackLogger, new MergedLanguagePack(
                    getLoadedEngineLanguagePack(),
                    getLoadedApplicationLanguagePack()
            ));
        } else
            log = logbackLogger;
    }

    private LanguagePack getLoadedEngineLanguagePack() {
        LanguagePackLoader loader = new LanguagePackLoader(
                applicationLoggingConfiguration.getEngineLoggingLanguageMetaData(),
                applicationLoggingConfiguration.getEngineLoggingLanguageMetaData().getILocation().getLocation()
        );

        return loader.load();
    }

    private LanguagePack getLoadedApplicationLanguagePack() {
        String applicationLanguagePackageLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        structureApplicationConfiguration.getApplicationLoggingInternationalizationPackage(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                        applicationLoggingConfiguration.getApplicationLoggingLanguageMetaData().getILocation(),
                        NullSafeResolveLocationOptions.INSERT_EVERYWHERE
                ),
                SeparatorResolveLocationOptions.INSERT_EVERYWHERE,
                Separator.SLASH_SEPARATOR
        );

        String applicationLanguagePackLocation = LocationTools.resolve(
                ResolvableLocation.of(
                        applicationLanguagePackageLocation,
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                ResolvableLocation.of(
                        applicationLoggingConfiguration.getApplicationLoggingLanguageFileName(),
                        NullSafeResolveLocationOptions.THROW_EXCEPTION
                ),
                SeparatorResolveLocationOptions.INSERT_START,
                Separator.SLASH_SEPARATOR
        );

        LanguagePackLoader loader = new LanguagePackLoader(applicationLoggingConfiguration.getApplicationLoggingLanguageMetaData(), applicationLanguagePackLocation);
        return loader.load();
    }

    private void safelyClose() {
        try {
            close();
        } catch (IOException io) {
            throw new FactoryCloseableException(io);
        }
    }

    @Override
    public ILoggingConfiguration getConfiguration() {
        return applicationLoggingConfiguration;
    }
}
