package com.badlogic.gdx.backends.gwt;

import com.google.gwt.webxr.XRSessionMode;

public class GwtXRApplicationConfiguration extends GwtApplicationConfiguration {

    public String immersiveMode = XRSessionMode.IMMERSIVE_VR;

    public String xrButtonSelector = "#enter-vr";

    public GwtXRApplicationConfiguration () {
        this(false);
    }

    /** Creates configuration for a resizable application, using available browser window space minus padding (see
     * {@link #padVertical}, {@link #padHorizontal}). Also see {@link #usePhysicalPixels} documentation. */
    public GwtXRApplicationConfiguration (boolean usePhysicalPixels) {
        this(0, 0, usePhysicalPixels);
    }

    /** Creates configuration for a fixed size application */
    public GwtXRApplicationConfiguration (int width, int height) {
        this(width, height, false);
    }

    /** Creates configuration for a fixed size application Also see {@link #usePhysicalPixels} documentation. */
    public GwtXRApplicationConfiguration (int width, int height, boolean usePhysicalPixels) {
        super(width, height, usePhysicalPixels);
    }

}
