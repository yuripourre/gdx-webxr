/*
 * Copyright 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.webxr;

import com.google.gwt.webgl.client.WebGLFramebuffer;
import com.google.gwt.webgl.client.WebGLRenderingContext;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class XRWebGLLayer implements XRLayer {
  public boolean antialias;
  public WebGLFramebuffer framebuffer;
  public double framebufferHeight;
  public double framebufferWidth;
  public boolean ignoreDepthValues;

  public XRWebGLLayer(XRSession session, WebGLRenderingContext context, XRWebGLLayerInit layerInit) {}

  public XRWebGLLayer(XRSession session, WebGLRenderingContext context) {}

  public native void addEventListener(
      String type, EventListener listener, AddEventListenerOptionsUnionType options);

  public native void addEventListener(String type, EventListener listener);

  public native boolean dispatchEvent(Event evt);

  public native double getNativeFramebufferScaleFactor();

  // TODO Update this method at the original repo
  public native XRViewport getViewport(XRView view);

  public native void removeEventListener(
      String type, EventListener listener, RemoveEventListenerOptionsUnionType options);

  public native void removeEventListener(String type, EventListener listener);
}
