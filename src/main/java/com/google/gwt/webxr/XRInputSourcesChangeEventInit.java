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
import elemental2.dom.EventInit;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface XRInputSourcesChangeEventInit extends EventInit {
  @JsOverlay
  static XRInputSourcesChangeEventInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  JsArray<XRInputSource> getAdded();

  @JsProperty
  JsArray<XRInputSource> getRemoved();

  @JsProperty
  XRSession getSession();

  @JsProperty
  void setAdded(JsArray<XRInputSource> added);

  @JsOverlay
  default void setAdded(XRInputSource[] added) {
    setAdded(Js.<JsArray<XRInputSource>>uncheckedCast(added));
  }

  @JsProperty
  void setRemoved(JsArray<XRInputSource> removed);

  @JsOverlay
  default void setRemoved(XRInputSource[] removed) {
    setRemoved(Js.<JsArray<XRInputSource>>uncheckedCast(removed));
  }

  @JsProperty
  void setSession(XRSession session);
}
