package com.google.gwt.webxr;

import elemental2.core.JsMap;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class XRHand extends JsMap<String, XRJointSpace> {
}
