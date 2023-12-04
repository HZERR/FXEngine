package ru.hzerr.fx.engine.core.theme;

public interface ICSSResolver {

    ResolvedThemeLocation resolve() throws ResolveThemeException;
}
