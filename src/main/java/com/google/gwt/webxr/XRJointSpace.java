package com.google.gwt.webxr;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface XRJointSpace extends XRSpace {
    @JsProperty
    String getJointName();
}
