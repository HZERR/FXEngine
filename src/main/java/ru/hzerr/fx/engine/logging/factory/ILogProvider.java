package ru.hzerr.fx.engine.logging.factory;

import ch.qos.logback.classic.Logger;

import java.io.Closeable;

public interface ILogProvider extends Configurable, Closeable {

    Logger getLogger();
}
