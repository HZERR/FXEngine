package ru.hzerr.fx.engine.logging.encoder;

import ch.qos.logback.classic.PatternLayout;
import ru.hzerr.fx.engine.logging.converter.*;

public class ColoredPatternLayoutEncoder extends PatternLayout {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    static {
        DEFAULT_CONVERTER_MAP.put("highlight", ColoredConverter.class.getName());
        DEFAULT_CONVERTER_MAP.put("time", TimeConverter.class.getName());
        DEFAULT_CONVERTER_MAP.put("classname", ClassNameConverter.class.getName());
        DEFAULT_CONVERTER_MAP.put("author", AuthorConverter.class.getName());
        DEFAULT_CONVERTER_MAP.put("caller", CallerConverter.class.getName());
    }
}
