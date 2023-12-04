package ru.hzerr.fx.engine.core.entity;

import javafx.stage.Popup;
import ru.hzerr.fx.engine.core.FXEngine;

public abstract class PopupController extends Controller implements Viewable {

    protected final Popup popup = new Popup();

    @Override
    protected void onConnectDestroyEvent() {
        popup.onCloseRequestProperty().addListener((_, _, _) -> onDestroy());
    }

//    protected void preInitPopup() {
//        popup.setAutoFix(true);
//        popup.getContent().add(getContentAsParent());
//    }

    @Override
    public void view() {
        popup.show(FXEngine.getContext().getStage().getOwner());
    }

    @Override
    public void view(double anchorX, double anchorY) {
        popup.show(FXEngine.getContext().getStage().getOwner(), anchorX, anchorY);
    }

    public Popup getPopup() {
        return popup;
    }
}
