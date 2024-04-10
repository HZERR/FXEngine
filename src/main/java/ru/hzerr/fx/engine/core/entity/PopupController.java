package ru.hzerr.fx.engine.core.entity;

import javafx.stage.Popup;
import org.apache.commons.lang3.RandomStringUtils;
import ru.hzerr.fx.engine.configuration.environment.IFXEnvironment;
import ru.hzerr.fx.engine.core.annotation.Include;

public abstract class PopupController extends Controller implements Viewable {

    protected final Popup popup = new Popup();
    protected final String id = RandomStringUtils.randomAlphanumeric(12);

    private IFXEnvironment environment;

    @Override
    public void onConnectDestroyEvent() {
        popup.showingProperty().subscribe(nValue -> {
            if (!nValue) {
                onDestroy();
            }
        });
    }

    @Override
    public void view() {
        popup.show(environment.getStage());
    }

    @Override
    public void view(double anchorX, double anchorY) {
        popup.show(environment.getStage(), anchorX, anchorY);
    }

    public Popup getPopup() {
        return popup;
    }

    @Override
    protected String id() {
        return id;
    }

    @Include
    private void setEnvironment(IFXEnvironment environment) {
        this.environment = environment;
    }
}
