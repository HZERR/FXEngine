package ru.hzerr.fx.engine.core.theme;

public interface ICSSResolver<T> {

    ResolvedThemeLocation resolve() throws ResolveThemeException;
}
