package ru.hzerr.fx.engine.core.path.impl;

import com.google.common.base.Preconditions;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

/**
 * A helper class for combining a {@link RelativeDirectoryLocation} with a next directory name.
 * The resulting directory location is represented as a string.
 */
public non-sealed class FXMLCombineRelativeDirectoryLocation extends SlashCombineRelativeDirectoryLocation {

    /**
     * Creates a new instance of {@link FXMLCombineRelativeDirectoryLocation} with the given
     * {@link RelativeDirectoryLocation} and next directory name.
     *
     * @param relativeDirectoryLocation the relative directory location
     * @param nextDirectoryName         the next directory name
     */
    protected FXMLCombineRelativeDirectoryLocation(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        super(relativeDirectoryLocation, nextDirectoryName);
    }

    /**
     * Creates a new instance of {@link FXMLCombineRelativeDirectoryLocation} with the given
     * {@link FXMLCombineRelativeDirectoryLocation} and next directory name.
     *
     * @param combineRelativeDirectoryLocation the combined relative directory location
     * @param nextDirectoryName                the next directory name
     */
    protected FXMLCombineRelativeDirectoryLocation(FXMLCombineRelativeDirectoryLocation combineRelativeDirectoryLocation, String nextDirectoryName) {
        super(combineRelativeDirectoryLocation, nextDirectoryName);
    }

    /**
     * Combines the current instance with the given next directory name.
     *
     * @param nextDirectoryName the next directory name
     * @return a new instance of {@link FXMLCombineRelativeDirectoryLocation} that is the result of the combination
     */
    public FXMLCombineRelativeDirectoryLocation combine(String nextDirectoryName) {
        Preconditions.checkNotNull(nextDirectoryName);

        return new FXMLCombineRelativeDirectoryLocation(this, nextDirectoryName);
    }

    /**
     * Combines the given {@link RelativeDirectoryLocation} with the given next directory name.
     *
     * @param relativeDirectoryLocation the relative directory location
     * @param nextDirectoryName         the next directory name
     * @return a new instance of {@link FXMLCombineRelativeDirectoryLocation} that is the result of the combination
     */
    public static FXMLCombineRelativeDirectoryLocation combine(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        Preconditions.checkNotNull(relativeDirectoryLocation);
        Preconditions.checkNotNull(nextDirectoryName);

        return new FXMLCombineRelativeDirectoryLocation(relativeDirectoryLocation, nextDirectoryName);
    }
}
