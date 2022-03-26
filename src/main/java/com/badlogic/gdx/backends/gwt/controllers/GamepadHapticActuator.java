package com.badlogic.gdx.backends.gwt.controllers;

import elemental2.promise.Promise;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * https://developer.mozilla.org/en-US/docs/Web/API/GamepadButton
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface GamepadHapticActuator {

    Promise<Boolean> pulse(double value, double duration);

}