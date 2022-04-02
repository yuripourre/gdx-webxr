package com.badlogic.gdx.backends.gwt.webxr.input;

import com.badlogic.gdx.backends.gwt.controllers.ControllerListener;
import com.badlogic.gdx.utils.Array;
import com.google.gwt.webxr.XRFrame;
import com.google.gwt.webxr.XRReferenceSpace;
import com.google.gwt.webxr.XRSession;

import java.util.Collection;

public interface XRControllerManager {

    void registerEvents(XRSession session);

    void updateInputSources(XRSession session, XRFrame frame, XRReferenceSpace refSpace);

    Collection<XRGwtController> getControllers();

    void addListener(ControllerListener listener);

    void removeListener(ControllerListener listener);

    Array<ControllerListener> getListeners();

    /** Clear all listeners */
    void clearListeners();
}
