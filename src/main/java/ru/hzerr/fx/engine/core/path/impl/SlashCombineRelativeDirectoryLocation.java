package ru.hzerr.fx.engine.core.path.impl;

import ru.hzerr.fx.engine.core.path.CombineRelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;
import ru.hzerr.fx.engine.core.path.Separator;

public sealed class SlashCombineRelativeDirectoryLocation extends CombineRelativeDirectoryLocation permits ApplicationInternationalizationCombineRelativeDirectoryLocation, ApplicationLoggingInternationalizationCombineRelativeDirectoryLocation, EngineLoggingInternationalizationCombineRelativeDirectoryLocation, FXMLCombineRelativeDirectoryLocation, LoggingLocalizationCombineRelativeDirectoryLocation, ThemeCombineRelativeDirectoryLocation {

    protected SlashCombineRelativeDirectoryLocation(RelativeDirectoryLocation relativeDirectoryLocation, String nextDirectoryName) {
        super(relativeDirectoryLocation, Separator.SLASH_SEPARATOR, nextDirectoryName);
    }

    protected SlashCombineRelativeDirectoryLocation(CombineRelativeDirectoryLocation combineRelativeDirectoryLocation, String nextDirectoryName) {
        super(combineRelativeDirectoryLocation, Separator.SLASH_SEPARATOR, nextDirectoryName);
    }
}
