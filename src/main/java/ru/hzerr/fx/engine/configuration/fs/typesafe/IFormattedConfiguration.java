package ru.hzerr.fx.engine.configuration.fs.typesafe;

import com.typesafe.config.*;

import java.util.List;

public interface IFormattedConfiguration extends Config {

    String getString(String msg, Object o);
    String getString(String msg, Object... objects);

    @Override
    IFormattedConfiguration withFallback(ConfigMergeable other);
    @Override
    IFormattedConfiguration resolve();
    @Override
    IFormattedConfiguration resolve(ConfigResolveOptions options);
    @Override
    IFormattedConfiguration resolveWith(Config source);
    @Override
    IFormattedConfiguration resolveWith(Config source, ConfigResolveOptions options);
    @Override
    IFormattedConfiguration getConfig(String path);
    @Override
    IFormattedConfiguration withOnlyPath(String path);
    @Override
    IFormattedConfiguration withoutPath(String path);
    @Override
    IFormattedConfiguration atPath(String path);
    @Override
    IFormattedConfiguration atKey(String key);
    @Override
    IFormattedConfiguration withValue(String path, ConfigValue value);

    @Override
    List<? extends IFormattedConfiguration> getConfigList(String path);
}
