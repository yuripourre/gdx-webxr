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

import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface XRRenderStateInit {
  @JsOverlay
  static XRRenderStateInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  XRWebGLLayer getBaseLayer();

  @JsProperty
  double getDepthFar();

  @JsProperty
  double getDepthNear();

  @JsProperty
  double getInlineVerticalFieldOfView();

  @JsProperty
  JsArray<XRLayer> getLayers();

  @JsProperty
  void setBaseLayer(XRWebGLLayer baseLayer);

  @JsProperty
  void setDepthFar(double depthFar);

  @JsProperty
  void setDepthNear(double depthNear);

  @JsProperty
  void setInlineVerticalFieldOfView(double inlineVerticalFieldOfView);

  @JsProperty
  void setLayers(JsArray<XRLayer> layers);

  @JsOverlay
  default void setLayers(XRLayer[] layers) {
    setLayers(Js.<JsArray<XRLayer>>uncheckedCast(layers));
  }
}
