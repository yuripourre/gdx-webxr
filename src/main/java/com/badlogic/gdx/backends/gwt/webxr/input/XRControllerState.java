package com.badlogic.gdx.backends.gwt.webxr.input;

import com.badlogic.gdx.math.Matrix4;

public class XRControllerState {

    public boolean[] buttonsPressed;
    public float[] axes;
    public Matrix4[] joints;

    public XRControllerState(XRGwtController controller) {
        axes = new float[controller.getAxisCount()];
        buttonsPressed = new boolean[controller.getButtonsCount()];
    }

}
