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
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.SizeType;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.MetaData;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;
import ru.hzerr.fx.engine.configuration.logging.IReadOnlyLoggingConfiguration;
import ru.hzerr.fx.engine.core.language.CombineLocalization;
import ru.hzerr.fx.engine.core.language.localization.ILocalizationProvider;
import ru.hzerr.fx.engine.logging.ConfigurableException;
import ru.hzerr.fx.engine.logging.FactoryCloseableException;
import ru.hzerr.fx.engine.logging.decorator.MultiLanguageLogger;
import ru.hzerr.fx.engine.logging.decorator.PlainLogger;
import ru.hzerr.fx.engine.logging.policy.CancelRollingPolicy;

import java.io.IOException;

public class FXApplicationLogProvider implements ILogProvider {

    private final IReadOnlyLoggingConfiguration readOnlyLoggingConfiguration;
    private final ILocalizationProvider engineLocalizationProvider;
    private final ILocalizationProvider applicationLocalizationProvider;

    private final BaseFile sessionLogFile;
    private final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    private final PatternLayout consolePatternLayout;
    private final LayoutWrappingEncoder<ILoggingEvent> consoleEncoder = new LayoutWrappingEncoder<>();
    private final ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    private final CancelRollingPolicy filePolicy = new CancelRollingPolicy();
    private final PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
    private final RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();

    private Logger log;

    @Include
    public FXApplicationLogProvider(@NotNull IReadOnlyLoggingConfiguration readOnlyLoggingConfiguration,
                                    @NotNull IStructureConfiguration structureConfiguration,
                                    @NotNull @MetaData("engineLoggingLocalizationProvider") ILocalizationProvider engineLocalizationProvider,
                                    @NotNull @MetaData("applicationLoggingLocalizationProvider") ILocalizationProvider applicationLocalizationProvider) {

        this.readOnlyLoggingConfiguration = readOnlyLoggingConfiguration;
        this.engineLocalizationProvider = engineLocalizationProvider;
        this.applicationLocalizationProvider = applicationLocalizationProvider;
        sessionLogFile = structureConfiguration.getLogDirectory().getSubFile(readOnlyLoggingConfiguration.getLogFileName());
        consolePatternLayout = readOnlyLoggingConfiguration.getConsolePatternLayout();
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
            if (sessionLogFile.exists()) {
                if (sessionLogFile.sizeOf(SizeType.BYTE) == 0D) {
                    sessionLogFile.delete();
                }
            }
        }
    }

    @Override
    public void configure() throws ConfigurableException {
        ch.qos.logback.classic.Logger logbackLogger = lc.getLogger(readOnlyLoggingConfiguration.getLoggerName());
        if (readOnlyLoggingConfiguration.isEnabled()) {
            if (readOnlyLoggingConfiguration.isConsoleLoggingEnabled()) {
                consolePatternLayout.setContext(lc);
                consolePatternLayout.setPattern(readOnlyLoggingConfiguration.getLoggerConsolePattern());
                consolePatternLayout.start();

                consoleEncoder.setLayout(consolePatternLayout);
                consoleEncoder.setCharset(readOnlyLoggingConfiguration.getConsoleEncoding());

                consoleAppender.setContext(lc);
                consoleAppender.setEncoder(consoleEncoder);
                consoleAppender.start();

                logbackLogger.addAppender(consoleAppender);
            }

            if (readOnlyLoggingConfiguration.isFileLoggingEnabled()) {
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
                fileEncoder.setCharset(readOnlyLoggingConfiguration.getFileEncoding());
                fileEncoder.setPattern(readOnlyLoggingConfiguration.getLoggerFilePattern());
                fileEncoder.start();

                fileAppender.setContext(lc);
                fileAppender.setEncoder(fileEncoder);
                fileAppender.setAppend(true);
                fileAppender.setFile(sessionLogFile.getLocation());
                fileAppender.setRollingPolicy(filePolicy);
                fileAppender.start();

                logbackLogger.addAppender(fileAppender);
            }

            logbackLogger.setLevel(readOnlyLoggingConfiguration.getLoggerLevel());
        } else
            logbackLogger.setLevel(Level.OFF);

        logbackLogger.setAdditive(false);

        /**
         * internationalization -> engine + languagePack | application + languagePack
         * not internationalization -> engine + languagePack | PLAINTEXT
         */

        // initialize target logger
        if (readOnlyLoggingConfiguration.isInternationalizationEnabled()) {
            log = new MultiLanguageLogger(logbackLogger, new CombineLocalization(
                    engineLocalizationProvider.getLocalization(),
                    applicationLocalizationProvider.getLocalization()
            ));
        } else
            log = new PlainLogger(logbackLogger, engineLocalizationProvider.getLocalization());

        Runtime.getRuntime().addShutdownHook(new Thread(this::closeQuietly));
    }

    private void closeQuietly() {
        try {
            close();
        } catch (IOException io) {
            throw new FactoryCloseableException(io);
        }
    }

    @Override
    public IReadOnlyLoggingConfiguration getConfiguration() {
        return readOnlyLoggingConfiguration;
    }
}
