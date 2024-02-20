package ru.hzerr.fx.engine.core.entity;

import javafx.stage.Popup;
import org.apache.commons.lang3.RandomStringUtils;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.Preview;

public abstract class PopupController extends Controller implements Viewable {

    protected final Popup popup = new Popup();
    protected final String id = RandomStringUtils.randomAlphanumeric(12);

    @Override
    public void onConnectDestroyEvent() {
        popup.onCloseRequestProperty().addListener((observable, o, n) -> onDestroy());
    }

    @Preview(version = "1.2.2.2E")
    protected void initPopup() {
        popup.setAutoFix(true);
        popup.getContent().add(getContentAsParent());
    }

    @Override
    public void view() {
        popup.show(FXEngine.getContext().getStage());
    }

    @Override
    public void view(double anchorX, double anchorY) {
        popup.show(FXEngine.getContext().getStage(), anchorX, anchorY);
    }

    public Popup getPopup() {
        return popup;
    }

    @Override
    protected String id() {
        return id;
    }
}
