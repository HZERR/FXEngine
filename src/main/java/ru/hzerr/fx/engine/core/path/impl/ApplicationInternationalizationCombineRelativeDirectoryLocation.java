package ru.hzerr.fx.engine.core.path.impl;

import com.google.common.base.Preconditions;
import ru.hzerr.fx.engine.core.path.CombineRelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

public non-sealed class ApplicationInternationalizationCombineRelativeDirectoryLocation extends SlashCombineRelativeDirectoryLocation {
    protected ApplicationInternationalizationCombineRelativeDirectoryLocation(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        super(relativeDirectoryLocation, nextDirectoryName);
    }

    protected ApplicationInternationalizationCombineRelativeDirectoryLocation(CombineRelativeDirectoryLocation combineRelativeDirectoryLocation, String nextDirectoryName) {
        super(combineRelativeDirectoryLocation, nextDirectoryName);
    }

    public ApplicationInternationalizationCombineRelativeDirectoryLocation combine(String nextDirectoryName) {
        Preconditions.checkNotNull(nextDirectoryName);

        return new ApplicationInternationalizationCombineRelativeDirectoryLocation(this, nextDirectoryName);
    }

    public static ApplicationInternationalizationCombineRelativeDirectoryLocation combine(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        Preconditions.checkNotNull(relativeDirectoryLocation);
        Preconditions.checkNotNull(nextDirectoryName);

        return new ApplicationInternationalizationCombineRelativeDirectoryLocation(relativeDirectoryLocation, nextDirectoryName);
    }
}
