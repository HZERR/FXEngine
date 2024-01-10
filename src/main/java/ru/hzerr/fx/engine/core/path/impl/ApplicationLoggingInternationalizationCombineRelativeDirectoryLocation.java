package ru.hzerr.fx.engine.core.path.impl;

import com.google.common.base.Preconditions;
import ru.hzerr.fx.engine.core.path.CombineRelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

public non-sealed class ApplicationLoggingInternationalizationCombineRelativeDirectoryLocation extends SlashCombineRelativeDirectoryLocation {
    protected ApplicationLoggingInternationalizationCombineRelativeDirectoryLocation(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        super(relativeDirectoryLocation, nextDirectoryName);
    }

    protected ApplicationLoggingInternationalizationCombineRelativeDirectoryLocation(CombineRelativeDirectoryLocation combineRelativeDirectoryLocation, String nextDirectoryName) {
        super(combineRelativeDirectoryLocation, nextDirectoryName);
    }

    public ApplicationLoggingInternationalizationCombineRelativeDirectoryLocation combine(String nextDirectoryName) {
        Preconditions.checkNotNull(nextDirectoryName);

        return new ApplicationLoggingInternationalizationCombineRelativeDirectoryLocation(this, nextDirectoryName);
    }

    public static ApplicationLoggingInternationalizationCombineRelativeDirectoryLocation combine(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        Preconditions.checkNotNull(relativeDirectoryLocation);
        Preconditions.checkNotNull(nextDirectoryName);

        return new ApplicationLoggingInternationalizationCombineRelativeDirectoryLocation(relativeDirectoryLocation, nextDirectoryName);
    }
}
