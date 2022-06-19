package com.google.gwt.webxr;

import elemental2.core.JsIterable;
import elemental2.core.JsIteratorIterable;
import elemental2.core.JsMap;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface XRHand extends JsIterable<JsMap.JsIterableTypeParameterArrayUnionType<XRHandJoint, XRJointSpace>[]> {
    XRJointSpace get(String key);

    @JsProperty
    double getSize();

    JsIteratorIterable<XRJointSpace> values();
}
