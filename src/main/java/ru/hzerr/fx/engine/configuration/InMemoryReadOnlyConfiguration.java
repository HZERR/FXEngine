package ru.hzerr.fx.engine.configuration;

public interface InMemoryReadOnlyConfiguration<T extends IReadOnlyConfiguration> {

    T getReadOnlyConfiguration();
}
