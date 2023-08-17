package ru.hzerr.fx.engine.logging.converter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class ClassNameConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        return ANSIConstants.BOLD + ANSIConstants.MAGENTA_FG;
    }
}
