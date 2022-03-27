package com.badlogic.gdx.backends.gwt.webxr.input;

import com.badlogic.gdx.backends.gwt.controllers.GamepadButton;
import com.badlogic.gdx.backends.gwt.controllers.GwtController;
import com.badlogic.gdx.math.Matrix4;
import com.google.gwt.webxr.XRInputSource;

public class XRController extends GwtController {

    private final XRInputSource inputSource;

    public boolean trigger = false;
    public boolean squeeze = false;
    public Matrix4 transform = new Matrix4();

    public XRController(int index, String name, XRInputSource inputSource) {
        super(index, name, inputSource.getGamepad());
        this.inputSource = inputSource;
    }

    public XRInputSource getInputSource() {
        return inputSource;
    }

    public int getButtonsSize() {
        return inputSource.getGamepad().getButtons().length;
    }

    public GamepadButton getButtonState(int button) {
        return inputSource.getGamepad().getButtons().getAt(button);
    }

    public boolean isButtonTouched(int button) {
        return inputSource.getGamepad().getButtons().getAt(button).isTouched();
    }

    public void setButtonState(int button, GamepadButton state) {
        inputSource.getGamepad().getButtons().setAt(button, state);
    }

    public void setAxis(int axis, Double value) {
        inputSource.getGamepad().getAxes().setAt(axis, value);
    }
}
