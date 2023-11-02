package ru.hzerr.fx.engine.configuration.hardcode;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ru.hzerr.fx.engine.logging.encoder.ColoredPatternLayoutEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReadOnlyLoggingConfiguration implements IReadOnlyLoggingConfiguration {

    private static final String FORMATTED_TIME = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
    private static final PatternLayout consolePatternLayout = new ColoredPatternLayoutEncoder();
    private static final String LOG_FILE_NAME = "fx-" + FORMATTED_TIME + ".log";
    private static final String CONSOLE_PATTERN = "%classname([%thread]) | %time(" + FORMATTED_TIME + ") | %author(Created By HZERR) | - %classname(%class{0}:) %highlight(%msg) %n";
    private static final String FILE_PATTERN = "[%thread] %-5level | " + FORMATTED_TIME + " | - %class{0}: %msg %n";

    @Override
    public String getLogFileName() {
        return LOG_FILE_NAME;
    }

    @Override
    public String getLoggerConsolePattern() {
        return CONSOLE_PATTERN;
    }

    @Override
    public String getLoggerFilePattern() {
        return FILE_PATTERN;
    }

    @Override
    public String getLoggerName() {
        return "FXEngine";
    }

    @Override
    public Charset getFileEncoding() {
        return StandardCharsets.UTF_8;
    }

    @Override
    public Charset getConsoleEncoding() {
        return StandardCharsets.UTF_8;
    }

    @Override
    public PatternLayout getConsolePatternLayout() {
        return consolePatternLayout;
    }

    @Override
    public Level getLoggerLevel() {
        return Level.DEBUG;
    }

    @Override
    public boolean isEngineLoggingEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isConsoleLoggingEnabled() {
        return true;
    }

    @Override
    public boolean isFileLoggingEnabled() {
        return true;
    }

    @Override
    public boolean isInternationalizationEnabled() {
        return false;
    }
}
