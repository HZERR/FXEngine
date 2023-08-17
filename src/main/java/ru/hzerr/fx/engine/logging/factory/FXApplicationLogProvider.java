package ru.hzerr.fx.engine.logging.factory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.SizeType;
import ru.hzerr.fx.engine.annotation.LogProvider;
import ru.hzerr.fx.engine.configuration.ILoggingConfiguration;
import ru.hzerr.fx.engine.configuration.IStructureConfiguration;
import ru.hzerr.fx.engine.logging.ConfigurableException;
import ru.hzerr.fx.engine.logging.FactoryCloseableException;
import ru.hzerr.fx.engine.logging.policy.CancelRollingPolicy;

import java.io.IOException;

@LogProvider
@Qualifier("application.log.provider")
public class FXApplicationLogProvider implements ILogProvider {

    private ILoggingConfiguration applicationLoggingConfiguration;

    private final BaseFile sessionLogFile;
    private final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    private final PatternLayout consolePatternLayout;
    private final LayoutWrappingEncoder<ILoggingEvent> consoleEncoder = new LayoutWrappingEncoder<>();
    private final ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    private final CancelRollingPolicy filePolicy = new CancelRollingPolicy();
    private final PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
    private final RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();

    private final Logger log;

    @Autowired
    public FXApplicationLogProvider(@NotNull ILoggingConfiguration applicationLoggingConfiguration, @NotNull IStructureConfiguration structureConfiguration) {
        this.applicationLoggingConfiguration = applicationLoggingConfiguration;
        sessionLogFile = structureConfiguration.getLoggingDirectory().getSubFile(applicationLoggingConfiguration.getLogFileName());
        consolePatternLayout = applicationLoggingConfiguration.getConsolePatternLayout();
        log = lc.getLogger(applicationLoggingConfiguration.getLoggerName());
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

                log.addAppender(consoleAppender);
            }

            if (applicationLoggingConfiguration.isFileLoggingEnabled()) {
                if (sessionLogFile.notExists()) {
                    try {
                        sessionLogFile.create();
                    } catch (IOException io) {
                        throw new ConfigurableException("Невозможно создать файл журнала текущей сессии", io);
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

                log.addAppender(fileAppender);
            }

            log.setLevel(applicationLoggingConfiguration.getLoggerLevel());
        } else
            log.setLevel(Level.OFF);

        log.setAdditive(false);
        Runtime.getRuntime().addShutdownHook(new Thread(this::safelyClose));
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
