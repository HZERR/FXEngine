package ru.hzerr.fx.engine.core.entity;

import javafx.scene.Parent;
import ru.hzerr.fx.engine.core.concurrent.IExtendedCompletionStage;
import ru.hzerr.fx.engine.core.entity.exception.LoadControllerException;

import java.io.IOException;

public interface IEntityLoader {
    <C extends Controller, P extends Parent>
    Entity<C, P> load(SpringLoadMetaData<C> loadData, Class<P> parent) throws LoadControllerException, IOException;
    <C extends Controller, P extends Parent>
    IExtendedCompletionStage<Entity<C, P>> loadAsync(SpringLoadMetaData<C> loadData, Class<P> parent);
    <C extends PopupController, P extends Parent>
    Entity<C, P> view(SpringLoadMetaData<C> loadData, Class<P> parent) throws LoadControllerException, IOException;
}
