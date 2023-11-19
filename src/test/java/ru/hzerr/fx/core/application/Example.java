package ru.hzerr.fx.core.application;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.hzerr.fx.engine.core.annotation.Redefinition;

@Redefinition(isRedefined = true)
public class Example {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
