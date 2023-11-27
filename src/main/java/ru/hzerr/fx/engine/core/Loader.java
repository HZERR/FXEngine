package ru.hzerr.fx.engine.core;

public interface Loader<T> {

    T resolve() throws LoadException;
}
