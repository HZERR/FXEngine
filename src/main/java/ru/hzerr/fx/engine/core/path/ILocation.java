package ru.hzerr.fx.engine.core.path;

import ru.hzerr.fx.engine.core.path.impl.ClassPathLocation;

public sealed interface ILocation permits CombineRelativeDirectoryLocation, RelativeDirectoryLocation, ClassPathLocation {

    String getLocation();
}
