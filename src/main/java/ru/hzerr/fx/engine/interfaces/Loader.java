package ru.hzerr.fx.engine.interfaces;

public interface Loader<T> {

    T load() throws LoadException;
}
