package com.badlogic.gdx.backends.gwt.controllers;

import elemental2.core.Float32Array;
import elemental2.core.JsArray;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * https://developer.mozilla.org/en-US/docs/Web/API/Gamepad
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface Gamepad {
    @JsProperty
    boolean isConnected();

    @JsProperty
    String getId();

    @JsProperty
    Float32Array getAxes();

    @JsProperty
    JsArray<GamepadButton> getButtons();

    @JsProperty
    String getMapping();

    @JsProperty
    JsArray<GamepadHapticActuator> getHapticActuators();
}