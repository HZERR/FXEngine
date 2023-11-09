package ru.hzerr.fx.engine.logging.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.commons.lang3.StringUtils;

public class CallerConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return StringUtils.substringAfterLast(Thread.currentThread().getStackTrace()[21].getClassName(), '.');
    }
}
