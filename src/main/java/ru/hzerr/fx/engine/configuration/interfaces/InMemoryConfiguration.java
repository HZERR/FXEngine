package ru.hzerr.fx.engine.configuration.interfaces;

public interface InMemoryConfiguration<T extends IReadOnlyConfiguration> {

    T getReadOnlyConfiguration();
}
