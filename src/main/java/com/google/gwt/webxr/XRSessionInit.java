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
public interface XRSessionInit {

  @JsOverlay
  static XRSessionInit create() {
    return Js.uncheckedCast(JsPropertyMap.of());
  }

  @JsProperty
  JsArray<String> getOptionalFeatures();

  @JsProperty
  JsArray<String> getRequiredFeatures();

  @JsProperty
  void setOptionalFeatures(JsArray<String> optionalFeatures);

  @JsOverlay
  default void setOptionalFeatures(String[] optionalFeatures) {
    setOptionalFeatures(Js.<JsArray<String>>uncheckedCast(optionalFeatures));
  }

  @JsProperty
  void setRequiredFeatures(JsArray<String> requiredFeatures);

  @JsOverlay
  default void setRequiredFeatures(String[] requiredFeatures) {
    setRequiredFeatures(Js.<JsArray<String>>uncheckedCast(requiredFeatures));
  }
}
