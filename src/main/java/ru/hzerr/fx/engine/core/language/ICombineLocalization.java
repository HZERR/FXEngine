package ru.hzerr.fx.engine.core.language;

import ru.hzerr.collections.list.HList;

public interface ICombineLocalization extends Configurable {

    HList<ILocalization> getLocalizations();
}
