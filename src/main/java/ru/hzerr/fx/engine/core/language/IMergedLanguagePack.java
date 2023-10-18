package ru.hzerr.fx.engine.core.language;

import ru.hzerr.collections.list.HList;

public interface IMergedLanguagePack extends Configurable {

    HList<ILanguagePack> getLanguagePacks();
}
