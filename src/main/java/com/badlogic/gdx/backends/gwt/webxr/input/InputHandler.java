package com.badlogic.gdx.backends.gwt.webxr.input;

import com.badlogic.gdx.backends.gwt.controllers.ControllerListener;
import com.google.gwt.webxr.XRFrame;
import com.google.gwt.webxr.XRReferenceSpace;
import com.google.gwt.webxr.XRSession;

public interface InputHandler {

    void registerEvents(XRSession session);

    void updateInputSources(XRSession session, XRFrame frame, XRReferenceSpace refSpace);

    void setListener(ControllerListener listener);
}
