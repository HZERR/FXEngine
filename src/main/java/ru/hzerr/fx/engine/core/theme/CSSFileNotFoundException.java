package ru.hzerr.fx.engine.core.theme;

import ru.hzerr.fx.engine.core.exception.LoadThemeException;

public class CSSFileNotFoundException extends LoadThemeException {
    public CSSFileNotFoundException(String message) {
        super(message);
    }
}
