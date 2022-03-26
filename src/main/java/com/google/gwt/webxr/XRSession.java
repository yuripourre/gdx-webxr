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
import elemental2.dom.Event;
import elemental2.dom.EventTarget;
import elemental2.promise.Promise;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface XRSession extends EventTarget {
  @JsFunction
  public interface OnendFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OninputsourceschangeFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnselectFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnselectendFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnselectstartFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnsqueezeFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnsqueezeendFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnsqueezestartFn {
    Object onInvoke(Event p0);
  }

  @JsFunction
  public interface OnvisibilitychangeFn {
    Object onInvoke(Event p0);
  }

  void cancelAnimationFrame(double handle);

  Promise<Void> end();

  // TODO method changed comparing to the original repo
  @JsProperty
  JsArray<XRInputSource> getInputSources();

  @JsProperty
  OnendFn getOnend();

  @JsProperty
  OninputsourceschangeFn getOninputsourceschange();

  @JsProperty
  OnselectFn getOnselect();

  @JsProperty
  OnselectendFn getOnselectend();

  @JsProperty
  OnselectstartFn getOnselectstart();

  @JsProperty
  OnsqueezeFn getOnsqueeze();

  @JsProperty
  OnsqueezeendFn getOnsqueezeend();

  @JsProperty
  OnsqueezestartFn getOnsqueezestart();

  @JsProperty
  OnvisibilitychangeFn getOnvisibilitychange();

  @JsProperty
  XRRenderState getRenderState();

  // TODO Changed on the original code
  @JsProperty
  String getVisibilityState();

  double requestAnimationFrame(XRFrameRequestCallback callback);

  Promise<XRReferenceSpace> requestReferenceSpace(String referenceSpaceType);

  @JsProperty
  void setOnend(OnendFn onend);

  @JsProperty
  void setOninputsourceschange(OninputsourceschangeFn oninputsourceschange);

  @JsProperty
  void setOnselect(OnselectFn onselect);

  @JsProperty
  void setOnselectend(OnselectendFn onselectend);

  @JsProperty
  void setOnselectstart(OnselectstartFn onselectstart);

  @JsProperty
  void setOnsqueeze(OnsqueezeFn onsqueeze);

  @JsProperty
  void setOnsqueezeend(OnsqueezeendFn onsqueezeend);

  @JsProperty
  void setOnsqueezestart(OnsqueezestartFn onsqueezestart);

  @JsProperty
  void setOnvisibilitychange(OnvisibilitychangeFn onvisibilitychange);

  void updateRenderState();

  void updateRenderState(XRRenderStateInit state);
}
