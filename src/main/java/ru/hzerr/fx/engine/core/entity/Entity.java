package ru.hzerr.fx.engine.core.entity;

import ru.hzerr.fx.engine.core.interfaces.entity.IEntity;

public class Entity<CONTROLLER, NODE> implements IEntity<CONTROLLER, NODE> {

    private final CONTROLLER controller;
    private final NODE node;

    public Entity(CONTROLLER controller, NODE node) {
        this.controller = controller;
        this.node = node;
    }

    public CONTROLLER getController() {
        return controller;
    }

    public NODE getNode() {
        return node;
    }
}
