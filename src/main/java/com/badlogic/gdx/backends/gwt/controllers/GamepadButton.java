package com.badlogic.gdx.backends.gwt.controllers;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * https://developer.mozilla.org/en-US/docs/Web/API/GamepadButton
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface GamepadButton {
    @JsProperty
    boolean isPressed();

    @JsProperty
    boolean isTouched();

    @JsProperty
    double getValue();
}