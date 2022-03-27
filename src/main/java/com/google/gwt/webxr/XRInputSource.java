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

import com.badlogic.gdx.backends.gwt.controllers.Gamepad;
import elemental2.core.JsArray;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * https://developer.mozilla.org/en-US/docs/Web/API/XRInputSource
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface XRInputSource {
  @JsProperty Gamepad getGamepad();

  @JsProperty
  XRSpace getGripSpace();

  // TODO Update this method at the original repo
  @JsProperty
  String getHandedness();

  @JsProperty
  JsArray<String> getProfiles();

  // TODO Update this method at the original repo
  @JsProperty
  String getTargetRayMode();

  @JsProperty
  XRSpace getTargetRaySpace();
}
