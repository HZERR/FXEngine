package ru.hzerr.fx.engine.core.context;

import org.apache.commons.lang3.StringUtils;
import ru.hzerr.fx.engine.core.FXEngine;

public class AutomaticExtendedAnnotationConfigApplicationContextProvider extends ExtendedAnnotationConfigApplicationContextProvider {
    public AutomaticExtendedAnnotationConfigApplicationContextProvider(Class<? extends FXEngine> startupApplicationClass) {
        super(new String[] {StringUtils.substringBefore(startupApplicationClass.getPackageName(), ".")}, true);
    }
}
