package com.badlogic.gdx.backends.gwt.webxr.input;

import com.badlogic.gdx.backends.gwt.controllers.GamepadButton;
import com.badlogic.gdx.backends.gwt.controllers.GamepadHapticActuator;
import com.badlogic.gdx.backends.gwt.controllers.GwtController;
import com.badlogic.gdx.math.Matrix4;
import com.google.gwt.webxr.XRHand;
import com.google.gwt.webxr.XRInputSource;
import elemental2.core.JsArray;

public class XRGwtController extends GwtController {

    private final XRInputSource inputSource;

    public boolean trigger = false;
    public boolean squeeze = false;
    public Matrix4 transform = new Matrix4();

    public XRGwtController(int index, String name, XRInputSource inputSource) {
        super(index, name, inputSource.getGamepad());
        this.inputSource = inputSource;
    }

    public XRInputSource getInputSource() {
        return inputSource;
    }

    public int getButtonsCount() {
        return inputSource.getGamepad().getButtons().length;
    }

    public GamepadButton getButtonState(int button) {
        return inputSource.getGamepad().getButtons()[button];
    }

    public boolean isButtonTouched(int button) {
        return inputSource.getGamepad().getButtons()[button].isTouched();
    }

    public void setButtonState(int button, GamepadButton state) {
        inputSource.getGamepad().getButtons()[button] = state;
    }

    public void setAxis(int axis, Double value) {
        inputSource.getGamepad().getAxes()[axis] = value;
    }


    public GamepadHapticActuator getActuator() {
        GamepadHapticActuator[] actuators = inputSource.getGamepad().getHapticActuators();
        if (actuators == null || actuators.length == 0) {
            return null;
        }
        return actuators[0];
    }

    private boolean hasActuators() {
        GamepadHapticActuator[] actuators = inputSource.getGamepad().getHapticActuators();
        if (actuators == null || actuators.length == 0) {
            return false;
        }
        return true;
    }

    public XRHand getHand() {
        return inputSource.getHand();
    }

}
