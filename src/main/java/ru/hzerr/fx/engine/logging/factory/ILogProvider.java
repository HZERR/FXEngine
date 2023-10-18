package ru.hzerr.fx.engine.logging.factory;

import org.slf4j.Logger;

import java.io.Closeable;

public interface ILogProvider extends Configurable, Closeable {

    Logger getLogger();
}
