package com.badlogic.gdx.backends.gwt;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class XRFrameBuffer extends FrameBuffer {
    
    public XRFrameBuffer(Pixmap.Format format, int width, int height, boolean hasDepth) {
        super(format, width, height, hasDepth);
    }
    
    public static void unbind() {
        GwtXRApplication.unbind();
    }

    @Override
    public void end() {
        GwtXRApplication.unbind();
        GwtXRApplication.resetViewport();
    }
}
