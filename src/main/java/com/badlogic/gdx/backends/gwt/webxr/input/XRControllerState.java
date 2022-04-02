package com.badlogic.gdx.backends.gwt.webxr.input;

public class XRControllerState {

    public boolean[] buttonsPressed;
    public float[] axes;

    public XRControllerState(XRGwtController controller) {
        axes = new float[controller.getAxisCount()];
        buttonsPressed = new boolean[controller.getButtonsCount()];
    }

}
