package ru.hzerr.fx.engine.core;

public interface Loader<T> {

    T load() throws LoadException;
}
