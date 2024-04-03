package ru.hzerr.fx.engine.core.concurrent;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.util.Subscription;
import ru.hzerr.fx.engine.core.annotation.Preview;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Preview(version = "1.2.7E")
public class ConcurrentObjectProperty<T> extends SimpleObjectProperty<T> {

    private final transient Object mutex = new Object();

    public ConcurrentObjectProperty() {
        super();
    }

    public ConcurrentObjectProperty(T initialValue) {
        super(initialValue);
    }

    public ConcurrentObjectProperty(Object bean, String name) {
        super(bean, name);
    }

    public ConcurrentObjectProperty(Object bean, String name, T initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public Object getBean() {
        synchronized (mutex) {
            return super.getBean();
        }
    }

    @Override
    public String getName() {
        synchronized (mutex) {
            return super.getName();
        }
    }

    @Override
    public void addListener(InvalidationListener listener) {
        synchronized (mutex) {
            super.addListener(listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        synchronized (mutex) {
            super.removeListener(listener);
        }
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        synchronized (mutex) {
            super.addListener(listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        synchronized (mutex) {
            super.removeListener(listener);
        }
    }

    @Override
    protected void fireValueChangedEvent() {
        synchronized (mutex) {
            super.fireValueChangedEvent();
        }
    }

    @Override
    protected void invalidated() {
        synchronized (mutex) {
            super.invalidated();
        }
    }

    @Override
    public T get() {
        synchronized (mutex) {
            return super.get();
        }
    }

    @Override
    public void set(T newValue) {
        synchronized (mutex) {
            super.set(newValue);
        }
    }

    @Override
    public boolean isBound() {
        synchronized (mutex) {
            return super.isBound();
        }
    }

    @Override
    public void bind(ObservableValue<? extends T> newObservable) {
        synchronized (mutex) {
            super.bind(newObservable);
        }
    }

    @Override
    public void unbind() {
        synchronized (mutex) {
            super.unbind();
        }
    }

    @Override
    public String toString() {
        synchronized (mutex) {
            return super.toString();
        }
    }

    @Override
    public void setValue(T v) {
        synchronized (mutex) {
            super.setValue(v);
        }
    }

    @Override
    public void bindBidirectional(Property<T> other) {
        synchronized (mutex) {
            super.bindBidirectional(other);
        }
    }

    @Override
    public void unbindBidirectional(Property<T> other) {
        synchronized (mutex) {
            super.unbindBidirectional(other);
        }
    }

    @Override
    public T getValue() {
        synchronized (mutex) {
            return super.getValue();
        }
    }

    @Override
    public BooleanBinding isEqualTo(ObservableObjectValue<?> other) {
        synchronized (mutex) {
            return super.isEqualTo(other);
        }
    }

    @Override
    public BooleanBinding isEqualTo(Object other) {
        synchronized (mutex) {
            return super.isEqualTo(other);
        }
    }

    @Override
    public BooleanBinding isNotEqualTo(ObservableObjectValue<?> other) {
        synchronized (mutex) {
            return super.isNotEqualTo(other);
        }
    }

    @Override
    public BooleanBinding isNotEqualTo(Object other) {
        synchronized (mutex) {
            return super.isNotEqualTo(other);
        }
    }

    @Override
    public BooleanBinding isNull() {
        synchronized (mutex) {
            return super.isNull();
        }
    }

    @Override
    public BooleanBinding isNotNull() {
        synchronized (mutex) {
            return super.isNotNull();
        }
    }

    @Override
    public StringBinding asString() {
        synchronized (mutex) {
            return super.asString();
        }
    }

    @Override
    public StringBinding asString(String format) {
        synchronized (mutex) {
            return super.asString(format);
        }
    }

    @Override
    public StringBinding asString(Locale locale, String format) {
        synchronized (mutex) {
            return super.asString(locale, format);
        }
    }

    @Override
    public <U> ObservableValue<U> map(Function<? super T, ? extends U> mapper) {
        synchronized (mutex) {
            return super.map(mapper);
        }
    }

    @Override
    public ObservableValue<T> orElse(T constant) {
        synchronized (mutex) {
            return super.orElse(constant);
        }
    }

    @Override
    public <U> ObservableValue<U> flatMap(Function<? super T, ? extends ObservableValue<? extends U>> mapper) {
        synchronized (mutex) {
            return super.flatMap(mapper);
        }
    }

    @Override
    public ObservableValue<T> when(ObservableValue<Boolean> condition) {
        synchronized (mutex) {
            return super.when(condition);
        }
    }

    @Override
    public Subscription subscribe(BiConsumer<? super T, ? super T> changeSubscriber) {
        synchronized (mutex) {
            return super.subscribe(changeSubscriber);
        }
    }

    @Override
    public Subscription subscribe(Consumer<? super T> valueSubscriber) {
        synchronized (mutex) {
            return super.subscribe(valueSubscriber);
        }
    }

    @Override
    public Subscription subscribe(Runnable invalidationSubscriber) {
        synchronized (mutex) {
            return super.subscribe(invalidationSubscriber);
        }
    }

    @Override
    public int hashCode() {
        synchronized (mutex) {
            return super.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        synchronized (mutex) {
            return super.equals(obj);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        synchronized (mutex) {
            return super.clone();
        }
    }
}
