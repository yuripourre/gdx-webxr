package com.badlogic.gdx.backends.gwt.controllers;

import com.badlogic.gdx.math.Matrix4;

/** Registered with an {@link InputHandler} instance to receive events.
  * @author Nathan Sweet
  * */
public interface ControllerListener {
    /** A {@link Controller} got connected.
     * @param controller */
    void connected (Controller controller);

    /** A {@link Controller} got disconnected.
     * @param controller */
    void disconnected (Controller controller);

    /** A button on the {@link Controller} was pressed. The buttonCode is controller specific. The
     * <code>com.badlogic.gdx.controllers.mapping</code> package hosts button constants for known controllers.
     * @param controller
     * @param buttonCode
     * @return whether to hand the event to other listeners. */
    boolean buttonDown (Controller controller, int buttonCode);

    /** A button on the {@link Controller} was released. The buttonCode is controller specific. The
     * <code>com.badlogic.gdx.controllers.mapping</code> package hosts button constants for known controllers.
     * @param controller
     * @param buttonCode
     * @return whether to hand the event to other listeners. */
    boolean buttonUp (Controller controller, int buttonCode);

    /** An axis on the {@link Controller} moved. The axisCode is controller specific. The axis value is in the range [-1, 1]. The
     * <code>com.badlogic.gdx.controllers.mapping</code> package hosts axes constants for known controllers.
     * @param controller
     * @param axisCode
     * @param value the axis value, -1 to 1
     * @return whether to hand the event to other listeners. */
    boolean axisMoved (Controller controller, int axisCode, float value);

    /** The position of the {@link Controller} moved. This method is specific for VR is controllers.
     *
     * @param controller
     * @param transform of the controller in meters
     *
     * @return whether to hand the event to other listeners. */
    boolean updateTransform(Controller controller, Matrix4 transform);

    /** The position of the {@link com.google.gwt.webxr.XRHand} changed. This method is specific for hand tracking.
     *
     * @param controller
     * @param transforms of the hand joints in meters
     *
     * @return whether to hand the event to other listeners. */
    boolean updateHand(Controller controller, Matrix4[] transforms);

    /** The trigger on the {@link Controller} was pressed. The trigger is controller specific.
     *
     * @param controller
     * @return whether to hand the event to other listeners. */
    boolean triggerDown (Controller controller);

    /** The trigger on the {@link Controller} was released. The trigger is controller specific.
     *
     * @param controller
     * @return whether to hand the event to other listeners. */
    boolean triggerUp (Controller controller);

    /** The squeeze on the {@link Controller} was pressed. The squeeze is controller specific.
     *
     * @param controller
     * @return whether to hand the event to other listeners. */
    boolean squeezeDown (Controller controller);

    /** The squeeze on the {@link Controller} was released. The squeeze is controller specific.
     *
     * @param controller
     * @return whether to hand the event to other listeners. */
    boolean squeezeUp (Controller controller);

}
