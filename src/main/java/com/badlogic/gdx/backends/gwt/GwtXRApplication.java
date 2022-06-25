package com.badlogic.gdx.backends.gwt;

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import com.badlogic.gdx.Gdx;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.webgl.client.WebGLFramebuffer;
import com.google.gwt.webgl.client.WebGLRenderingContext;
import com.google.gwt.webxr.*;
import elemental2.promise.Promise;

import static elemental2.dom.DomGlobal.document;
import static elemental2.dom.DomGlobal.navigator;

public abstract class GwtXRApplication extends GwtApplication {

    private static boolean xrSessionActive = false;

    protected static WebGLRenderingContext gl;
    protected static WebGLFramebuffer currentFrameBuffer;
    protected static XRViewport currentViewport;

    protected WebXRNavigator xrNavigator;
    protected XRReferenceSpace refSpace;

    @Override
    public void onModuleLoad() {
        super.onModuleLoad();
        setupXRButton();
    }

    @Override
    public GwtXRApplicationConfiguration getConfig () {
        return getXRConfig();
    }

    public abstract GwtXRApplicationConfiguration getXRConfig();

    public void setupXRButton() {
        try {
            GwtXRApplicationConfiguration configuration = (GwtXRApplicationConfiguration) config;
            xrNavigator = WebXRNavigator.of(navigator);
            xrNavigator.xr.isSessionSupported(configuration.immersiveMode).then(isSupported -> {
                document.querySelector(configuration.xrButtonSelector).addEventListener("click", evt -> initXR());
                return null;
            }, p0 -> {
                onNoXRDevice();
                return null;
            }).catch_(error -> {
                onNoXRDevice();
                return null;
            });
        } catch (ClassCastException e) {
            onNoXRDevice();
        }
    }

    private void initXR() {
        try {
            GwtXRApplicationConfiguration configuration = (GwtXRApplicationConfiguration) config;

            Promise<XRSession> sessionPromise;
            if (configuration.requiredFeatures == null && configuration.optionalFeatures == null) {
                sessionPromise = xrNavigator.xr.requestSession(configuration.immersiveMode);
            } else {
                XRSessionInit xrSessionInit = XRSessionInit.create();
                xrSessionInit.setRequiredFeatures(configuration.requiredFeatures);
                xrSessionInit.setOptionalFeatures(configuration.optionalFeatures);
                sessionPromise = xrNavigator.xr.requestSession(configuration.immersiveMode, xrSessionInit);
            }

            sessionPromise.then(session -> {
                startSession(session);
                return null;
            }, error -> {
                onNoXRDevice();
                return null;
            }).catch_(error -> {
                onNoXRDevice();
                return null;
            });
        } catch (Exception e) {
            onNoXRDevice();
        }
    }

    private void startSession(XRSession session) {
        xrSessionActive = true;
        final GwtGraphics graphics = (GwtGraphics) Gdx.graphics;
        gl = graphics.getContext();

        XRRenderStateInit state = XRRenderStateInit.create();
        state.setBaseLayer(new XRWebGLLayer(session, gl));

        session.updateRenderState(state);

        session.requestReferenceSpace("local").then(refSpace -> {
            this.refSpace = refSpace;

            onSessionStarted(session);
            session.requestAnimationFrame((timestamp, xrFrame) -> onXRFrame(timestamp, xrFrame));
            return null;
        });

        // Do not work properly
        /*session.addEventListener("end", evt -> {
            onSessionEnded(session);
            xrSessionActive = false;
        });*/
    }

    public void onXRFrame(double time, XRFrame frame) {
        // This line makes a call to getParameter that is causing everything to run very slow
        XRSession session = frame.getSession();

        XRViewerPose pose = frame.getViewerPose(refSpace);

        if (pose != null) {
            XRWebGLLayer glLayer = session.getRenderState().getBaseLayer();
            currentFrameBuffer = glLayer.framebuffer;

            // Bind Frame Buffer provided by the XR device
            unbind();
            onBindBuffer(gl);

            mainLoop();

            for (int i = 0; i < pose.getViews().length; i++) {
                XRView view = pose.getViews().getAt(i);
                XRViewport viewport = glLayer.getViewport(view);
                currentViewport = viewport;

                // Set the viewport based on the XR view
                resetViewport();

                onView(view, viewport);
                getApplicationListener().render();
            }

            postRender();
        }

        // Queue up the next frame
        session.requestAnimationFrame((timestamp, xrFrame) -> onXRFrame(timestamp, xrFrame));

        onFrame(time, session, frame);
    }

    public static void unbind() {
        gl.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, currentFrameBuffer);
    }

    public static void resetViewport() {
        gl.viewport(currentViewport.getX(), currentViewport.getY(), currentViewport.getWidth(), currentViewport.getHeight());
    }

    protected abstract void onSessionStarted(XRSession session);

    protected abstract void onSessionEnded(XRSession session);

    protected abstract void onView(XRView view, XRViewport viewport);

    protected abstract void onFrame(double time, XRSession session, XRFrame frame);

    protected void onNoXRDevice() {}

    protected void onBindBuffer(WebGLRenderingContext gl) {}

    protected void postRender() {}

    @Override
    protected void setupMainLoop() {
        final GwtGraphics graphics = (GwtGraphics) Gdx.graphics;
        //gl = graphics.getContext();

        // This is necessary to fix the current size of the original application
        int width = graphics.getWidth();
        int height = graphics.getHeight();
        Gdx.gl.glViewport(0, 0, width, height);
        getApplicationListener().resize(width, height);

        AnimationScheduler.get().requestAnimationFrame(new AnimationScheduler.AnimationCallback() {
            @Override
            public void execute(double timestamp) {
                try {
                    mainLoop();
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
                // When the xrSession starts, we do not update this anymore
                if (!xrSessionActive) {
                    AnimationScheduler.get().requestAnimationFrame(this, graphics.getContext().getCanvas());
                }
            }
        }, graphics.getContext().getCanvas());
    }

    protected void mainLoop () {
        GwtGraphics graphics = (GwtGraphics) Gdx.graphics;
        graphics.update();

        runnablesHelper.addAll(runnables);
        runnables.clear();
        for (int i = 0; i < runnablesHelper.size; i++) {
            runnablesHelper.get(i).run();
        }
        runnablesHelper.clear();
        //graphics.frameId++;
        if (!xrSessionActive) {
            getApplicationListener().render();
        }
        ((GwtInput) Gdx.input).reset();
    }

}
