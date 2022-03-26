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

import elemental2.dom.Event;
import elemental2.dom.EventInit;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;


@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class XRInputSourceEvent extends Event {
  public XRFrame frame;
  public XRInputSource inputSource;

  public XRInputSourceEvent(String type, XRInputSourceEventInit eventInitDict) {
    // This super call is here only for the code to compile; it is never executed.
    super((String) null, (EventInit) null);
  }

  @JsOverlay
  public static XRInputSourceEvent fromEvent(Event evt) {
    return Js.<XRInputSourceEvent>uncheckedCast(evt);
  }
}
