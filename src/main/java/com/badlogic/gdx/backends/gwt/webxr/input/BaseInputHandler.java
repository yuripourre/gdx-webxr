package com.badlogic.gdx.backends.gwt.webxr.input;

import com.badlogic.gdx.backends.gwt.controllers.Controller;
import com.badlogic.gdx.backends.gwt.controllers.ControllerListener;
import com.badlogic.gdx.backends.gwt.controllers.GamepadButton;
import com.badlogic.gdx.backends.gwt.webxr.MatrixUtils;
import com.badlogic.gdx.math.Matrix4;
import com.google.gwt.webxr.XRFrame;
import com.google.gwt.webxr.XRInputSource;
import com.google.gwt.webxr.XRInputSourceEvent;
import com.google.gwt.webxr.XRInputSourcesChangeEvent;
import com.google.gwt.webxr.XRPose;
import com.google.gwt.webxr.XRReferenceSpace;
import com.google.gwt.webxr.XRSession;
import com.google.gwt.webxr.XRSpace;
import elemental2.dom.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseInputHandler implements InputHandler {

    private Map<String, XRController> inputSources = new HashMap<>();

    private ControllerListener listener = DUMMY_CONTROLLER_LISTENER;

    public void registerEvents(XRSession session) {
        session.setOninputsourceschange(onInputChanges());
        session.setOnselect(onSelect());
        session.setOnselectstart(onSelectStart());
        session.setOnselectend(onSelectEnd());

        session.setOnsqueeze(onSqueeze());
        session.setOnsqueezestart(onSqueezeStart());
        session.setOnsqueezeend(onSqueezeEnd());
    }

    public ControllerListener getListener() {
        return listener;
    }

    public void setListener(ControllerListener listener) {
        this.listener = listener;
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
        int index = inputSources.size();
        String name = source.getHandedness();

        String id = getIdentifier(source);
        XRController input = new XRController(index, name, source);
        inputSources.put(id, input);

        input.connected = true;
        listener.connected(input);
    }

    void removeSource(XRInputSource source) {
        XRController toRemove = findInput(source);

        if (toRemove == null) {
            return;
        }

        toRemove.connected = false;
        listener.disconnected(toRemove);
        inputSources.remove(toRemove);
    }

    private XRController findInput(XRInputSource source) {
        for (XRController input : inputSources.values()) {
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

                XRController input = findInput(e.inputSource);
                if (input != null) {
                    input.trigger = true;
                    listener.triggerDown(input);
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

                XRController input = findInput(e.inputSource);
                if (input != null) {
                    input.trigger = false;
                    listener.triggerUp(input);
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
                XRController input = findInput(e.inputSource);
                if (input != null) {
                    input.squeeze = true;
                    listener.squeezeDown(input);
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
                XRController input = findInput(e.inputSource);
                if (input != null) {
                    input.squeeze = false;
                    listener.squeezeUp(input);
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
            XRController input = findInput(inputSource);
            if (input == null) {
               return;
            }

            XRSpace targetRaySpace = inputSource.getTargetRaySpace();
            XRPose targetRayPose = frame.getPose(targetRaySpace, refSpace);

            Matrix4 transform = MatrixUtils.buildMatrix4(targetRayPose.getTransform().matrix, input.transform);
            listener.updateTransform(input, transform);
            handleButtonState(inputSource);
            handleAxisState(inputSource);
        }
    }

    private void handleButtonState(XRInputSource xrInputSource) {
        String id = getIdentifier(xrInputSource);
        XRController oldState = inputSources.get(id);

        if (oldState == null || listener == null) {
            return;
        }

        for (int i = 0; i < oldState.getButtonsSize(); i++) {
            GamepadButton state = xrInputSource.getGamepad().getButtons().getAt(i);
            GamepadButton oldButtonState = oldState.getButtonState(i);
            // Should we handle isTouched?
            if (state.isPressed() != oldButtonState.isPressed()) {
                // Update the old state
                oldState.setButtonState(i, state);
                if (state.isPressed()) {
                    listener.buttonDown(oldState, i);
                } else {
                    listener.buttonUp(oldState, i);
                }
            }
        }
    }

    private void handleAxisState(XRInputSource xrInputSource) {
        String id = getIdentifier(xrInputSource);
        XRController oldState = inputSources.get(id);

        if (oldState == null || listener == null) {
            return;
        }

        for (int i = 0; i < oldState.getAxisCount(); i++) {
            Double value = xrInputSource.getGamepad().getAxes().getAt(i);
            if (value != oldState.getAxis(i)) {
                // Update the old state
                oldState.setAxis(i, value);
                listener.axisMoved(oldState, i, value.floatValue());
            }
        }
    }

    private static final ControllerListener DUMMY_CONTROLLER_LISTENER = new ControllerListener() {

        @Override
        public void connected(Controller controller) {

        }

        @Override
        public void disconnected(Controller controller) {

        }

        @Override
        public boolean buttonDown(Controller controller, int buttonCode) {
            return false;
        }

        @Override
        public boolean buttonUp(Controller controller, int buttonCode) {
            return false;
        }

        @Override
        public boolean axisMoved(Controller controller, int axisCode, float value) {
            return false;
        }

        @Override
        public boolean updateTransform(Controller controller, Matrix4 transform) {
            return false;
        }

        @Override
        public boolean triggerDown(Controller controller) {
            return false;
        }

        @Override
        public boolean triggerUp(Controller controller) {
            return false;
        }

        @Override
        public boolean squeezeDown(Controller controller) {
            return false;
        }

        @Override
        public boolean squeezeUp(Controller controller) {
            return false;
        }
    };
}