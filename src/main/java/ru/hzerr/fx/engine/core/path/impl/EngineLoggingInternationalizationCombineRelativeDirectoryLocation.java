package ru.hzerr.fx.engine.core.path.impl;

import com.google.common.base.Preconditions;
import ru.hzerr.fx.engine.core.path.CombineRelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

public non-sealed class EngineLoggingInternationalizationCombineRelativeDirectoryLocation extends SlashCombineRelativeDirectoryLocation {
    protected EngineLoggingInternationalizationCombineRelativeDirectoryLocation(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        super(relativeDirectoryLocation, nextDirectoryName);
    }

    protected EngineLoggingInternationalizationCombineRelativeDirectoryLocation(CombineRelativeDirectoryLocation combineRelativeDirectoryLocation, String nextDirectoryName) {
        super(combineRelativeDirectoryLocation, nextDirectoryName);
    }

    public EngineLoggingInternationalizationCombineRelativeDirectoryLocation combine(String nextDirectoryName) {
        Preconditions.checkNotNull(nextDirectoryName);

        return new EngineLoggingInternationalizationCombineRelativeDirectoryLocation(this, nextDirectoryName);
    }

    public static EngineLoggingInternationalizationCombineRelativeDirectoryLocation combine(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        Preconditions.checkNotNull(relativeDirectoryLocation);
        Preconditions.checkNotNull(nextDirectoryName);

        return new EngineLoggingInternationalizationCombineRelativeDirectoryLocation(relativeDirectoryLocation, nextDirectoryName);
    }
}
