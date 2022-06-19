/**
 * https://developer.mozilla.org/en-US/docs/Web/API/Gamepad
 */
/*
 *       Copyright 2018 Dmitrii Tikhomirov
 *
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 */
package com.badlogic.gdx.backends.gwt.controllers;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * WARNING !!! THIS PACKAGE WILL BE REMOVED AFTER RELEASE OF ORIGINAL ELEMENTAL2
 * <p>
 * The Gamepad interface of the Gamepad API defines an individual gamepad or other controller, allowing access to information such as button presses, axis positions, and id.
 * <p>
 * A Gamepad object can be returned in one of two ways: via the gamepad property of the gamepadconnected and gamepaddisconnected events, or by grabbing any position in the array returned by the Navigator.getGamepads() method.
 *
 * @author Dmitrii Tikhomirov
 * Created by treblereel on 5/29/18.
 * https://github.com/treblereel/elemental2-gamepad/blob/master/src/main/java/elemental2/gamepad/Gamepad.java
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Gamepad {


    private Gamepad() {

    }

    /**
     * The axes property of the Gamepad interface returns an array representing the controls with axes present on the device (e.g. analog thumb sticks).-
     * <p>
     * Each entry in the array is a floating point value in the range -1.0 – 1.0, representing the axis position from the lowest value (-1.0) to the highest value (1.0).
     *
     * @return array of double
     */
    @JsProperty
    public native double[] getAxes();

    /**
     * The Gamepad.buttons property of the Gamepad interface returns an array of gamepadButton objects representing the buttons present on the device.
     * <p>
     * Each entry in the array is 0 if the button is not pressed, and non-zero (typically 1.0) if the button is pressed. Each gamepadButton object has two properties: pressed and value:
     * <p>
     * The pressed property is a boolean indicating whether the button is currently pressed (true) or unpressed (false).
     * The value property is a floating point value used to enable representing analog buttons, such as the triggers on many modern gamepads. The values are normalized to the range 0.0 – 1.0, with 0.0 representing a button that is not pressed, and 1.0 representing a button that is fully pressed.
     *
     * @return array of buttons
     */
    @JsProperty
    public native GamepadButton[] getButtons();

    /**
     * The connected property of the Gamepad interface returns a boolean indicating whether the gamepad is still connected to the system.
     *
     * @return If the gamepad is connected, the value is true; if not, it is false.
     */
    @JsProperty
    public native boolean isConnected();


    /**
     * This is an experimental technology
     * <p>
     * The displayId read-only property of the Gamepad interface returns the VRDisplay.displayId of the associated VRDisplay — the VRDisplay that the gamepad is controlling the displayed scene of.     *
     *
     * @return A Gamepad is considered to be associated with a VRDisplay if it reports a pose that is in the same space as the VRDisplay.pose.
     */
    @JsProperty(name = "displayId")
    public native int getDisplayId();

    /**
     * The id property of the Gamepad interface returns a string containing some information about the controller.
     * <p>
     * The exact syntax is not strictly specified, but in Firefox it will contain three pieces of information separated by dashes (-):
     * <p>
     * Two 4-digit hexadecimal strings containing the USB vendor and product id of the controller
     * The name of the controller as provided by the driver.
     * For example, a PS2 controller returned 810-3-USB Gamepad.
     * <p>
     * This information is intended to allow you to find a mapping for the controls on the device as well as display useful feedback to the user.
     *
     * @return Gamepad id
     */
    @JsProperty
    public native String getId();

    /**
     * The index property of the Gamepad interface returns an integer that is auto-incremented to be unique for each device currently connected to the system.
     * <p>
     * This can be used to distinguish multiple controllers; a gamepad that is disconnected and reconnected will retain the same index.
     *
     * @return Gamepad index
     */
    @JsProperty
    public native int getIndex();

    /**
     * The mapping property of the Gamepad interface returns a string indicating whether the browser has remapped the controls on the device to a known layout.
     * <p>
     * Currently there is only one supported known layout–the standard gamepad. If the browser is able to map controls on the device to that layout the mapping property will be set to the string standard.
     *
     * @return as String value
     */
    @JsProperty
    public native String getMapping();

    /**
     * Note: This property is not currently supported anywhere.
     * <p>
     * The timestamp property of the Gamepad interface returns a DOMHighResTimeStamp representing the last time the data for this gamepad was updated.
     * <p>
     * The idea behind this is to allow developers to determine if the axes and button data have been updated from the hardware. The value must be relative to the navigationStart attribute of the PerformanceTiming interface. Values are monotonically increasing, meaning that they can be compared to determine the ordering of updates, as newer values will always be greater than or equal to older values.
     *
     * @return as double value
     */
    @JsProperty
    public native double getTimestamp();

    // Experimental extensions to Gamepad
    // The following interfaces are defined in the Gamepad Extensions specification, and provide access to experimental features like haptic feedback and WebVR controller pose information.

    /**
     * left — the left hand.
     * right — the right hand.
     *
     * @return hope, it's work
     */
    //@JsProperty
    //public native GamepadHand getHand();

    /**
     * This is an experimental technology
     * Check the Browser compatibility table carefully before using this in production.
     * <p>
     * The hapticActuators read-only property of the Gamepad interface returns an array containing GamepadHapticActuator objects, each of which represents haptic feedback hardware available on the controller.
     *
     * @return array of GamepadHapticActuator
     */
    @JsProperty
    public native GamepadHapticActuator[] getHapticActuators();

    /**
     * This is an experimental technology
     * Check the Browser compatibility table carefully before using this in production.
     *
     * The pose read-only property of the Gamepad interface returns a GamepadPose object representing the pose information associated with a WebVR controller (e.g. its position and orientation in 3D space).
     * @return instance of GamepadPose
     */
    //@JsProperty
    //public native GamepadPose getPose();

}
