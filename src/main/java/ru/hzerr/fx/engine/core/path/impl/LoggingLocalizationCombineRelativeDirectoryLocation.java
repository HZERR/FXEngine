package ru.hzerr.fx.engine.core.path.impl;

import com.google.common.base.Preconditions;
import ru.hzerr.fx.engine.core.path.CombineRelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

public non-sealed class LoggingLocalizationCombineRelativeDirectoryLocation extends SlashCombineRelativeDirectoryLocation {
    protected LoggingLocalizationCombineRelativeDirectoryLocation(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        super(relativeDirectoryLocation, nextDirectoryName);
    }

    protected LoggingLocalizationCombineRelativeDirectoryLocation(CombineRelativeDirectoryLocation combineRelativeDirectoryLocation, String nextDirectoryName) {
        super(combineRelativeDirectoryLocation, nextDirectoryName);
    }

    public LoggingLocalizationCombineRelativeDirectoryLocation combine(String nextDirectoryName) {
        Preconditions.checkNotNull(nextDirectoryName);

        return new LoggingLocalizationCombineRelativeDirectoryLocation(this, nextDirectoryName);
    }

    public static LoggingLocalizationCombineRelativeDirectoryLocation combine(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        Preconditions.checkNotNull(relativeDirectoryLocation);
        Preconditions.checkNotNull(nextDirectoryName);

        return new LoggingLocalizationCombineRelativeDirectoryLocation(relativeDirectoryLocation, nextDirectoryName);
    }
}
