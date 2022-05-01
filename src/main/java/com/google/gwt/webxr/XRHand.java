package com.google.gwt.webxr;

import elemental2.core.JsIteratorIterable;
import elemental2.core.JsMap;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

// TODO Add this class to the original repo
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class XRHand {

    public int size;

    public native JsIteratorIterable<JsMap.EntriesJsIteratorIterableTypeParameterArrayUnionType<String, XRJointSpace>[]> entries();

    public native Object forEach(JsMap.ForEachCallbackFn<? super String, ? super XRJointSpace> callback, Object thisArg);

    public native Object forEach(JsMap.ForEachCallbackFn<? super String, ? super XRJointSpace> callback);

    public native XRJointSpace get(String key);

    public native JsIteratorIterable<String> keys();
    public native JsIteratorIterable<XRJointSpace> values();

}
