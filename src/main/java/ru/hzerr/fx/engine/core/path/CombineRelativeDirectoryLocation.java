package ru.hzerr.fx.engine.core.path;

import com.google.common.base.Preconditions;
import ru.hzerr.fx.engine.core.path.impl.SlashCombineRelativeDirectoryLocation;

public sealed class CombineRelativeDirectoryLocation implements ILocation permits SlashCombineRelativeDirectoryLocation {

    private String location;
    private Separator separator;

    protected CombineRelativeDirectoryLocation(RelativeDirectoryLocation relativeDirectoryLocation, Separator separator, String nextDirectoryName) {
        this.separator = separator;
        this.location = relativeDirectoryLocation.getLocation() + separator.getSeparator() + nextDirectoryName;
    }

    protected CombineRelativeDirectoryLocation(CombineRelativeDirectoryLocation combineRelativeDirectoryLocation, Separator separator, String nextDirectoryName) {
        this.separator = separator;
        this.location = combineRelativeDirectoryLocation.getLocation() + separator.getSeparator() + nextDirectoryName;
    }
    @Override
    public String getLocation() {
        return location;
    }

    public String getSeparator() { return separator.getSeparator(); }

    public CombineRelativeDirectoryLocation combine(Separator separator, String nextDirectoryName) {
        Preconditions.checkNotNull(separator);
        Preconditions.checkNotNull(nextDirectoryName);

        return new CombineRelativeDirectoryLocation(this, separator, nextDirectoryName);
    }

    public static CombineRelativeDirectoryLocation combine(RelativeDirectoryLocation relativeDirectoryLocation, Separator separator, String nextDirectoryName) {
        Preconditions.checkNotNull(relativeDirectoryLocation);
        Preconditions.checkNotNull(separator);
        Preconditions.checkNotNull(nextDirectoryName);

        return new CombineRelativeDirectoryLocation(relativeDirectoryLocation, separator, nextDirectoryName);
    }
}
