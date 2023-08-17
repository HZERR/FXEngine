package ru.hzerr.fx.engine.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ru.hzerr.fx.engine.logging.encoder.ColoredPatternLayoutEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BaseLoggingConfiguration implements ILoggingConfiguration {

    private static final String FORMATTED_TIME = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
    private PatternLayout consolePatternLayout = new ColoredPatternLayoutEncoder();
    private String logFileName = "fx-" + FORMATTED_TIME + ".log";
    private String consolePattern = "%classname([%thread]) | %time(" + FORMATTED_TIME + ") | %author(Created By HZERR) | - %classname(%class{0}:) %highlight(%msg) %n";
    private String filePattern = "[%thread] %-5level | " + FORMATTED_TIME + " | - %class{0}: %msg %n";
    private String loggerName = "FXEngine";
    private Charset fileEncoding = StandardCharsets.UTF_8;
    private Charset consoleEncoding = StandardCharsets.UTF_8;
    private Level loggerLevel = Level.DEBUG;
    private boolean enabled = true;

    private boolean frameworkEnabled = true;
    private boolean consoleEnabled = true;
    private boolean fileLoggingEnabled = true;

    @Override
    public String getLogFileName() {
        return logFileName;
    }

    @Override
    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    @Override
    public String getLoggerConsolePattern() {
        return consolePattern;
    }

    @Override
    public void setLoggerConsolePattern(String consolePattern) {
        this.consolePattern = consolePattern;
    }

    @Override
    public String getLoggerFilePattern() {
        return filePattern;
    }

    @Override
    public void setLoggerFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    @Override
    public String getLoggerName() {
        return loggerName;
    }

    @Override
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    @Override
    public Charset getFileEncoding() {
        return fileEncoding;
    }

    @Override
    public void setFileEncoding(Charset fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    @Override
    public Charset getConsoleEncoding() {
        return consoleEncoding;
    }

    @Override
    public void setConsoleEncoding(Charset consoleEncoding) {
        this.consoleEncoding = consoleEncoding;
    }

    @Override
    public Level getLoggerLevel() {
        return loggerLevel;
    }

    @Override
    public void setLoggerLevel(Level loggerLevel) {
        this.loggerLevel = loggerLevel;
    }

    @Override
    public boolean isFrameworkLoggingEnabled() {
        return frameworkEnabled;
    }

    @Override
    public void setFrameworkLoggingEnabled(boolean frameworkEnabled) {
        this.frameworkEnabled = frameworkEnabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isConsoleLoggingEnabled() {
        return consoleEnabled;
    }

    @Override
    public void setEnabledConsole(boolean consoleEnabled) {
        this.consoleEnabled = consoleEnabled;
    }

    @Override
    public boolean isFileLoggingEnabled() {
        return fileLoggingEnabled;
    }

    @Override
    public void setEnabledFileLogging(boolean fileLoggingEnabled) {
        this.fileLoggingEnabled = fileLoggingEnabled;
    }

    @Override
    public PatternLayout getConsolePatternLayout() {
        return consolePatternLayout;
    }

    @Override
    public void setConsolePatternLayout(PatternLayout consolePatternLayout) {
        this.consolePatternLayout = consolePatternLayout;
    }
}
