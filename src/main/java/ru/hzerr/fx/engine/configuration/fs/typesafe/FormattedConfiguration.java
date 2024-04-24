package ru.hzerr.fx.engine.configuration.fs.typesafe;

import com.typesafe.config.*;
import org.apache.commons.lang3.StringUtils;
import ru.hzerr.fx.engine.core.interfaces.localization.IFormattedConfiguration;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class FormattedConfiguration implements IFormattedConfiguration {

    protected Config config;

    public FormattedConfiguration(Config config) {
        this.config = config;
    }

    @Override
    public String getString(String path, Object o) {
        return rethrowIfNeeded(() -> StringUtils.replaceOnce(config.getString(path), "{}", o.toString()));
    }

    @Override
    public String getString(String path, Object... args) {
        return rethrowIfNeeded(() -> replace(config.getString(path), args));
    }

    @Override
    public ConfigObject root() {
        return config.root();
    }

    @Override
    public ConfigOrigin origin() {
        return config.origin();
    }

    @Override
    public IFormattedConfiguration withFallback(ConfigMergeable other) {
        return new FormattedConfiguration(config.withFallback(((FormattedConfiguration) other).config));
    }

    @Override
    public IFormattedConfiguration resolve() {
        return new FormattedConfiguration(config.resolve());
    }

    @Override
    public IFormattedConfiguration resolve(ConfigResolveOptions options) {
        return new FormattedConfiguration(config.resolve(options));
    }

    @Override
    public boolean isResolved() {
        return config.isResolved();
    }

    @Override
    public IFormattedConfiguration resolveWith(Config source) {
        return new FormattedConfiguration(config.resolveWith(((FormattedConfiguration) source).config));
    }

    @Override
    public IFormattedConfiguration resolveWith(Config source, ConfigResolveOptions options) {
        return new FormattedConfiguration(config.resolveWith(source, options));
    }

    @Override
    public void checkValid(Config reference, String... restrictToPaths) {
        config.checkValid(reference, restrictToPaths);
    }

    @Override
    public boolean hasPath(String path) {
        return rethrowIfNeeded(() -> config.hasPath(path));
    }

    @Override
    public boolean hasPathOrNull(String path) {
        return rethrowIfNeeded(() -> config.hasPathOrNull(path));
    }

    @Override
    public boolean isEmpty() {
        return config.isEmpty();
    }

    @Override
    public Set<Map.Entry<String, ConfigValue>> entrySet() {
        return config.entrySet();
    }

    @Override
    public boolean getIsNull(String path) {
        return rethrowIfNeeded(() -> config.getIsNull(path));
    }

    @Override
    public boolean getBoolean(String path) {
        return rethrowIfNeeded(() -> config.getBoolean(path));
    }

    @Override
    public Number getNumber(String path) {
        return rethrowIfNeeded(() -> config.getNumber(path));
    }

    @Override
    public int getInt(String path) {
        return rethrowIfNeeded(() -> config.getInt(path));
    }

    @Override
    public long getLong(String path) {
        return rethrowIfNeeded(() -> config.getLong(path));
    }

    @Override
    public double getDouble(String path) {
        return rethrowIfNeeded(() -> config.getDouble(path));
    }

    @Override
    public String getString(String path) {
        return rethrowIfNeeded(() -> config.getString(path));
    }

    @Override
    public <T extends Enum<T>> T getEnum(Class<T> enumClass, String path) {
        return rethrowIfNeeded(() -> config.getEnum(enumClass, path));
    }

    @Override
    public ConfigObject getObject(String path) {
        return rethrowIfNeeded(() -> config.getObject(path));
    }

    @Override
    public IFormattedConfiguration getConfig(String path) {
        return new FormattedConfiguration(rethrowIfNeeded(() -> config.getConfig(path)));
    }

    @Override
    public Object getAnyRef(String path) {
        return rethrowIfNeeded(() -> config.getAnyRef(path));
    }

    @Override
    public ConfigValue getValue(String path) {
        return rethrowIfNeeded(() -> config.getValue(path));
    }

    @Override
    public Long getBytes(String path) {
        return rethrowIfNeeded(() -> config.getBytes(path));
    }

    @Override
    public ConfigMemorySize getMemorySize(String path) {
        return rethrowIfNeeded(() -> config.getMemorySize(path));
    }

    @Override
    @Deprecated
    public Long getMilliseconds(String path) {
        return rethrowIfNeeded(() -> config.getMilliseconds(path));
    }

    @Override
    @Deprecated
    public Long getNanoseconds(String path) {
        return rethrowIfNeeded(() -> config.getNanoseconds(path));
    }

    @Override
    public long getDuration(String path, TimeUnit unit) {
        return rethrowIfNeeded(() -> config.getDuration(path, unit));
    }

    @Override
    public Duration getDuration(String path) {
        return rethrowIfNeeded(() -> config.getDuration(path));
    }

    @Override
    public Period getPeriod(String path) {
        return rethrowIfNeeded(() -> config.getPeriod(path));
    }

    @Override
    public TemporalAmount getTemporal(String path) {
        return rethrowIfNeeded(() -> config.getTemporal(path));
    }

    @Override
    public ConfigList getList(String path) {
        return rethrowIfNeeded(() -> config.getList(path));
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return rethrowIfNeeded(() -> config.getBooleanList(path));
    }

    @Override
    public List<Number> getNumberList(String path) {
        return rethrowIfNeeded(() -> config.getNumberList(path));
    }

    @Override
    public List<Integer> getIntList(String path) {
        return rethrowIfNeeded(() -> config.getIntList(path));
    }

    @Override
    public List<Long> getLongList(String path) {
        return rethrowIfNeeded(() -> config.getLongList(path));
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return rethrowIfNeeded(() -> config.getDoubleList(path));
    }

    @Override
    public List<String> getStringList(String path) {
        return rethrowIfNeeded(() -> config.getStringList(path));
    }

    @Override
    public <T extends Enum<T>> List<T> getEnumList(Class<T> enumClass, String path) {
        return rethrowIfNeeded(() -> config.getEnumList(enumClass, path));
    }

    @Override
    public List<? extends ConfigObject> getObjectList(String path) {
        return rethrowIfNeeded(() -> config.getObjectList(path));
    }

    @Override
    public List<? extends IFormattedConfiguration> getConfigList(String path) {
        return rethrowIfNeeded(() -> config.getConfigList(path))
                .stream()
                .map(FormattedConfiguration::new)
                .toList();
    }

    @Override
    public List<? extends Object> getAnyRefList(String path) {
        return rethrowIfNeeded(() -> config.getAnyRefList(path));
    }

    @Override
    public List<Long> getBytesList(String path) {
        return rethrowIfNeeded(() -> config.getBytesList(path));
    }

    @Override
    public List<ConfigMemorySize> getMemorySizeList(String path) {
        return rethrowIfNeeded(() -> config.getMemorySizeList(path));
    }

    @Override
    @Deprecated
    public List<Long> getMillisecondsList(String path) {
        return rethrowIfNeeded(() -> config.getMillisecondsList(path));
    }

    @Override
    @Deprecated
    public List<Long> getNanosecondsList(String path) {
        return rethrowIfNeeded(() -> config.getNanosecondsList(path));
    }

    @Override
    public List<Long> getDurationList(String path, TimeUnit unit) {
        return rethrowIfNeeded(() -> config.getDurationList(path, unit));
    }

    @Override
    public List<Duration> getDurationList(String path) {
        return rethrowIfNeeded(() -> config.getDurationList(path));
    }

    @Override
    public IFormattedConfiguration withOnlyPath(String path) {
        return new FormattedConfiguration(config.withOnlyPath(path));
    }

    @Override
    public IFormattedConfiguration withoutPath(String path) {
        return new FormattedConfiguration(config.withoutPath(path));
    }

    @Override
    public IFormattedConfiguration atPath(String path) {
        return new FormattedConfiguration(config.atPath(path));
    }

    @Override
    public IFormattedConfiguration atKey(String key) {
        return new FormattedConfiguration(config.atKey(key));
    }

    @Override
    public IFormattedConfiguration withValue(String path, ConfigValue value) {
        return new FormattedConfiguration(config.withValue(path, value));
    }

    private <T> T rethrowIfNeeded(Supplier<T> action) {
        try {
            return action.get();
        } catch (ConfigException.BadPath e) {
            throw new UnresolvablePathException(e.getMessage());
        } catch (ConfigException.WrongType e) {
            throw new ClassCastException(e.getMessage());
        } catch (ConfigException.Missing e) {
            throw new MissingValueException(e.getMessage());
        } catch (ConfigException.BadValue e) {
            throw new IncorrectValueException(e.getMessage());
        }
    }

    private String replace(String msg, Object... args) {
        StringBuilder formattedMessage = new StringBuilder();
        int msgIndex = 0;
        int argsIndex = 0;
        while (msgIndex < msg.length() - 1) {
            if (msg.charAt(msgIndex) == '{' && msg.charAt(msgIndex + 1) == '}') {
                if (argsIndex < args.length) {
                    formattedMessage.append(args[argsIndex]);
                    msgIndex = msgIndex + 2;
                    argsIndex = argsIndex + 1;
                } else
                    throw new FormatException("Invalid arguments length. The FXEngine has identified " + (args.length + 1) + " places to insert. Check your message: " + msg);
            } else
                formattedMessage.append(msg.charAt(msgIndex++));
        }

        if (msgIndex != msg.length()) {
            formattedMessage.append(msg.charAt(msg.length() - 1));
        }

        return formattedMessage.toString();
    }
}
