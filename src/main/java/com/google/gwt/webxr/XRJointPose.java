package com.google.gwt.webxr;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface XRJointPose extends XRPose {
    @JsProperty
    double getRadius();
}
