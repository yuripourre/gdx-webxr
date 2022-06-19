package com.google.gwt.webxr;

public class XRFeatures {

    /**
     * Enable use of XRAnchor objects.
     */
    public static final String ANCHOR = "anchor";

    /**
     * Similar to the local type, except the user is not expected to move outside a predetermined boundary, given by the boundsGeometry in the returned object.
     */
    public static final String BOUNDED_FLOOR = "bounded-floor";

    /**
     * Enable the ability to obtain depth information using XRDepthInformation objects.
     */
    public static final String DEPTH_SENSING = "depth-sensing";

    /**
     * Enable allowing to specify a DOM overlay element that will be displayed to the user.
     */
    public static final String DOM_OVERLAY = "dom-overlay";

    /**
     * Enable articulated hand pose information from hand-based input controllers (see XRHand and XRInputSource.hand).
     */
    public static final String HAND_TRACKING = "hand-tracking";

    /**
     * Enable hit testing features for performing hit tests against real world geometry.
     */
    public static final String HIT_TEST = "hit-test";

    /**
     * Enable the ability to create various layer types (other than XRProjectionLayer).
     */
    public static final String LAYERS = "layers";

    /**
     * Enable the ability to estimate environmental lighting conditions using XRLightEstimate objects.
     */
    public static final String LIGHT_ESTIMATION = "light-estimation";

    /**
     * Enable a tracking space whose native origin is located near the viewer's position at the time the session was created. The exact position depends on the underlying platform and implementation. The user isn't expected to move much if at all beyond their starting position, and tracking is optimized for this use case.
     */
    public static final String LOCAL = "local";

    /**
     * Similar to the local type, except the starting position is placed in a safe location for the viewer to stand, where the value of the y axis is 0 at floor level. If that floor level isn't known, the user agent will estimate the floor level. If the estimated floor level is non-zero, the browser is expected to round it such a way as to avoid fingerprinting (likely to the nearest centimeter).
     */
    public static final String LOCAL_FLOOR = "local-floor";

    /**
     * Enable XRView objects to be secondary views. This can be used for first-person observer views used for video capture, or "quad views" where there are two views per eye, with differing resolution and fields of view.
     */
    public static final String SECONDARY_VIEWS = "secondary-views";

    /**
     * Enable a tracking space which allows the user total freedom of movement, possibly over extremely long distances from their origin point. The viewer isn't tracked at all; tracking is optimized for stability around the user's current position, so the native origin may drift as needed to accommodate that need.
     */
    public static final String UNBOUNDED = "unbounded";

    /**
     * Enable a tracking space whose native origin tracks the viewer's position and orientation.
     */
    public static final String VIEWER = "viewer";

}
