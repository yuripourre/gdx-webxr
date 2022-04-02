package com.badlogic.gdx.backends.gwt.webxr.input;

import com.badlogic.gdx.backends.gwt.controllers.ControllerListener;
import com.badlogic.gdx.backends.gwt.controllers.GamepadButton;
import com.badlogic.gdx.backends.gwt.webxr.MatrixUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.google.gwt.webxr.XRFrame;
import com.google.gwt.webxr.XRInputSource;
import com.google.gwt.webxr.XRInputSourceEvent;
import com.google.gwt.webxr.XRInputSourcesChangeEvent;
import com.google.gwt.webxr.XRPose;
import com.google.gwt.webxr.XRReferenceSpace;
import com.google.gwt.webxr.XRSession;
import com.google.gwt.webxr.XRSpace;
import elemental2.dom.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XRControllers implements XRControllerManager {

    private Map<String, XRGwtController> controllers = new HashMap<>();
    private Map<String, XRControllerState> states = new HashMap<>();

    private Array<ControllerListener> listeners = new Array<>();

    @Override
    public void registerEvents(XRSession session) {
        session.setOninputsourceschange(onInputChanges());
        session.setOnselect(onSelect());
        session.setOnselectstart(onSelectStart());
        session.setOnselectend(onSelectEnd());

        session.setOnsqueeze(onSqueeze());
        session.setOnsqueezestart(onSqueezeStart());
        session.setOnsqueezeend(onSqueezeEnd());
    }

    @Override
    public Collection<XRGwtController> getControllers() {
        return controllers.values();
    }

    @Override
    public Array<ControllerListener> getListeners() {
        return listeners;
    }

    @Override
    public void addListener(ControllerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ControllerListener listener) {
        listeners.removeValue(listener, true);
    }

    @Override
    public void clearListeners() {
        listeners.clear();
    }

    public XRSession.OninputsourceschangeFn onInputChanges() {
        return evt -> {
            XRInputSourcesChangeEvent e = XRInputSourcesChangeEvent.fromEvent(evt);
            List<XRInputSource> added = e.added.asList();
            List<XRInputSource> removed = e.removed.asList();

            for (XRInputSource source : added) {
                addSource(source);
            }
            for (XRInputSource source : removed) {
                removeSource(source);
            }

            return null;
        };
    }

    void addSource(XRInputSource source) {
        int index = controllers.size();
        String name = source.getHandedness();

        String id = getIdentifier(source);
        XRGwtController input = new XRGwtController(index, name, source);
        controllers.put(id, input);
        states.put(id, new XRControllerState(input));

        input.connected = true;
        for (ControllerListener listener : listeners) {
            listener.connected(input);
        }
    }

    void removeSource(XRInputSource source) {
        XRGwtController toRemove = findInput(source);

        if (toRemove == null) {
            return;
        }

        toRemove.connected = false;
        for (ControllerListener listener : listeners) {
            listener.connected(toRemove);
        }
        controllers.remove(toRemove);
    }

    private XRGwtController findInput(XRInputSource source) {
        for (XRGwtController input : controllers.values()) {
            // Currently, it only removes based on handedness.
            // If for some reason there are two left or right controllers, the last one will overwrite the first one
            if (getIdentifier(input.getInputSource()).equals(getIdentifier(source))) {
                return input;
            }
        }
        return null;
    }

    private String getIdentifier(XRInputSource source) {
        return source.getHandedness();
    }

    private XRSession.OnselectFn onSelect() {
        return new XRSession.OnselectFn() {
            @Override
            public Object onInvoke(Event evt) {
                XRInputSourceEvent e = XRInputSourceEvent.fromEvent(evt);
                return null;
            }
        };
    }

    private XRSession.OnselectstartFn onSelectStart() {
        return new XRSession.OnselectstartFn() {
            @Override
            public Object onInvoke(Event evt) {
                XRInputSourceEvent e = XRInputSourceEvent.fromEvent(evt);

                XRGwtController input = findInput(e.inputSource);
                if (input != null) {
                    input.trigger = true;

                    for (ControllerListener listener : listeners) {
                        listener.triggerDown(input);
                    }
                }
                return null;
            }
        };
    }

    private XRSession.OnselectendFn onSelectEnd() {
        return new XRSession.OnselectendFn() {
            @Override
            public Object onInvoke(Event evt) {
                XRInputSourceEvent e = XRInputSourceEvent.fromEvent(evt);

                XRGwtController input = findInput(e.inputSource);
                if (input != null) {
                    input.trigger = false;
                    for (ControllerListener listener : listeners) {
                        listener.triggerUp(input);
                    }
                }
                return null;
            }
        };
    }

    private XRSession.OnsqueezeFn onSqueeze() {
        return new XRSession.OnsqueezeFn() {
            @Override
            public Object onInvoke(Event evt) {
                XRInputSourceEvent e = XRInputSourceEvent.fromEvent(evt);
                return null;
            }
        };
    }

    private XRSession.OnsqueezestartFn onSqueezeStart() {
        return new XRSession.OnsqueezestartFn() {
            @Override
            public Object onInvoke(Event evt) {
                XRInputSourceEvent e = XRInputSourceEvent.fromEvent(evt);
                XRGwtController input = findInput(e.inputSource);
                if (input != null) {
                    input.squeeze = true;
                    for (ControllerListener listener : listeners) {
                        listener.squeezeDown(input);
                    }
                }
                return null;
            }
        };
    }

    private XRSession.OnsqueezeendFn onSqueezeEnd() {
        return new XRSession.OnsqueezeendFn() {
            @Override
            public Object onInvoke(Event evt) {
                XRInputSourceEvent e = XRInputSourceEvent.fromEvent(evt);
                XRGwtController input = findInput(e.inputSource);
                if (input != null) {
                    input.squeeze = false;
                    for (ControllerListener listener : listeners) {
                        listener.squeezeUp(input);
                    }
                }
                return null;
            }
        };
    }

    /**
     * Code from https://github.com/immersive-web/webxr-samples/blob/main/input-tracking.html
     */
    public void updateInputSources(XRSession session, XRFrame frame, XRReferenceSpace refSpace) {
        for (XRInputSource inputSource : session.getInputSources().asList()) {
            XRGwtController input = findInput(inputSource);
            if (input == null) {
               return;
            }

            String id = getIdentifier(inputSource);

            XRGwtController current = controllers.get(id);
            XRControllerState controllerState = states.get(id);
            if (current == null || controllerState == null) {
                return;
            }

            XRSpace targetRaySpace = inputSource.getTargetRaySpace();
            XRPose targetRayPose = frame.getPose(targetRaySpace, refSpace);

            Matrix4 transform = MatrixUtils.buildMatrix4(targetRayPose.getTransform().matrix, input.transform);
            for (ControllerListener listener : listeners) {
                listener.updateTransform(input, transform);

                // This can be very slow with multiple listeners
                handleButtonState(inputSource, current, controllerState, listener);
                handleAxisState(inputSource, current, controllerState, listener);
            }

        }
    }

    private void handleButtonState(XRInputSource inputSource, XRGwtController current, XRControllerState controllerState, ControllerListener listener) {
        for (int i = 0; i < controllerState.buttonsPressed.length; i++) {
            GamepadButton buttonState = inputSource.getGamepad().getButtons().getAt(i);
            boolean pressed = controllerState.buttonsPressed[i];
            // Should we handle isTouched?
            if (buttonState.isPressed() != pressed) {
                // Update the old state
                controllerState.buttonsPressed[i] = buttonState.isPressed();
                if (buttonState.isPressed()) {
                    listener.buttonDown(current, i);
                } else {
                    listener.buttonUp(current, i);
                }
            }
        }
    }

    private void handleAxisState(XRInputSource xrInputSource, XRGwtController current, XRControllerState controllerState, ControllerListener listener) {
        for (int i = 0; i < controllerState.axes.length; i++) {
            Double value = xrInputSource.getGamepad().getAxes().getAt(i);
            float fValue = value.floatValue();
            float oldValue = controllerState.axes[i];
            if (fValue != oldValue) {
                controllerState.axes[i] = fValue;
                listener.axisMoved(current, i, fValue);
            }
        }
    }
}