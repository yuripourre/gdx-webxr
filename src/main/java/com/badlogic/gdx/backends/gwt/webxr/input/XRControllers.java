package com.badlogic.gdx.backends.gwt.webxr.input;

import com.badlogic.gdx.backends.gwt.GwtXRApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.controllers.ControllerListener;
import com.badlogic.gdx.backends.gwt.controllers.GamepadButton;
import com.badlogic.gdx.backends.gwt.webxr.MatrixUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.google.gwt.webxr.*;
import elemental2.core.Float32Array;
import elemental2.core.JsIteratorIterable;
import elemental2.dom.Event;
import jsinterop.base.Js;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XRControllers implements XRControllerManager {

    public static final int NUM_FINGERS = 25;
    private Map<String, XRGwtController> controllers = new HashMap<>();
    private Map<String, XRControllerState> states = new HashMap<>();

    private Array<ControllerListener> listeners = new Array<>();

    // Hand Tracking
    private boolean handtrackingEnabled = false;
    private static final Float32Array radii = new Float32Array(NUM_FINGERS);
    private static final Float32Array transforms = new Float32Array(16 * NUM_FINGERS);

    public XRControllers(GwtXRApplicationConfiguration configuration) {
        handtrackingEnabled = configuration.hasFeature(XRFeatures.HAND_TRACKING);
    }

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

        XRControllerState state = new XRControllerState(input);
        if (handtrackingEnabled) {
            state.joints = new Matrix4[NUM_FINGERS];
            for (int i = 0; i < NUM_FINGERS; i++) {
                state.joints[i] = new Matrix4();
            }
        }

        states.put(id, state);

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

            if (!handtrackingEnabled) {
                Matrix4 transform = MatrixUtils.buildMatrix4(targetRayPose.getTransform().matrix, input.transform);
                handleUpdateTransform(input, transform, listeners);
                // This can be very slow with multiple listeners
                handleButtonState(inputSource, current, controllerState, listeners);
                handleAxisState(inputSource, current, controllerState, listeners);
            } else {
                if (targetRayPose == null) {
                    return;
                }
                // Update hand position
                updateHandJoints(inputSource, current, controllerState, frame, refSpace, listeners);
            }
        }
    }

    private void handleUpdateTransform(XRGwtController current, Matrix4 matrix4, Array<ControllerListener> listeners) {
        for (ControllerListener listener : listeners) {
            listener.updateTransform(current, matrix4);
        }
    }

    private void updateHandJoints(XRInputSource inputSource, XRGwtController current, XRControllerState controllerState, XRFrame frame, XRReferenceSpace refSpace, Array<ControllerListener> listeners) {
        if (inputSource.getHand() == null) {
            return;
        }

        if (!frame.fillJointRadii(inputSource.getHand().values(), radii)) {
            //console.log("no fillJointRadii");
            return;
        }

        JsIteratorIterable<XRSpace> values = Js.uncheckedCast(inputSource.getHand().values());
        if (!frame.fillPoses(values, refSpace, transforms)) {
            //console.log("no fillPoses");
            return;
        }

        for (int m = 0; m < NUM_FINGERS; m++) {
            Matrix4 matrix = controllerState.joints[m];
            MatrixUtils.buildMatrix4(transforms, m * 16, matrix);
        }

        // Notify listeners
        for (ControllerListener listener : listeners) {
            listener.updateHand(current, controllerState.joints);
        }
    }

    private void handleButtonState(XRInputSource inputSource, XRGwtController current, XRControllerState controllerState, Array<ControllerListener> listeners) {
        for (int i = 0; i < controllerState.buttonsPressed.length; i++) {
            GamepadButton buttonState = inputSource.getGamepad().getButtons()[i];
            boolean pressed = controllerState.buttonsPressed[i];
            // Should we handle isTouched?
            if (buttonState.isPressed() != pressed) {
                // Update the old state
                controllerState.buttonsPressed[i] = buttonState.isPressed();
                if (buttonState.isPressed()) {
                    for (ControllerListener listener : listeners) {
                        listener.buttonDown(current, i);
                    }
                } else {
                    for (ControllerListener listener : listeners) {
                        listener.buttonUp(current, i);
                    }
                }
            }
        }
    }

    private void handleAxisState(XRInputSource xrInputSource, XRGwtController current, XRControllerState controllerState, Array<ControllerListener> listeners) {
        for (int i = 0; i < controllerState.axes.length; i++) {
            double value = xrInputSource.getGamepad().getAxes()[i];
            float fValue = (float) value;
            float oldValue = controllerState.axes[i];
            if (fValue != oldValue) {
                controllerState.axes[i] = fValue;
                for (ControllerListener listener : listeners) {
                    listener.axisMoved(current, i, fValue);
                }
            }
        }
    }
}