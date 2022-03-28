package com.badlogic.gdx.backends.gwt.webxr.input;

public class XRControllerState {

    boolean[] buttonsPressed;
    float[] axes;

    public XRControllerState(XRController controller) {
        axes = new float[controller.getAxisCount()];
        buttonsPressed = new boolean[controller.getButtonsCount()];
    }

}
