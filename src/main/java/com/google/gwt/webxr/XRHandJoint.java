package com.google.gwt.webxr;

public enum XRHandJoint {

    WRIST("wrist", 0),

    THUMB_METACARPAL("thumb-metacarpal", 1),
    THUMB_PHALANX_PROXIMAL("thumb-phalanx-proximal", 2),
    THUMB_PHALANX_DISTAL("thumb-phalanx-distal", 3),
    THUMB_TIP("thumb-tip", 4),

    INDEX_FINGER__METACARPAL("index-finger-metacarpal", 5),
    INDEX_PHALANX_PROXIMAL("index-phalanx-proximal", 6),
    INDEX_PHALANX_INTERMEDIATE("index-finger-phalanx-intermediate", 7),
    INDEX_PHALANX_DISTAL("index-phalanx-distal", 8),
    INDEX_TIP("index-tip", 9),

    MIDDLE_FINGER_METACARPAL("middle-finger-metacarpal", 10),
    MIDDLE_PHALANX_PROXIMAL("middle-phalanx-proximal", 11),
    MIDDLE_PHALANX_INTERMEDIATE("middle-finger-phalanx-intermediate", 12),
    MIDDLE_PHALANX_DISTAL("middle-phalanx-distal", 13),
    MIDDLE_TIP("middle-tip", 14),

    RING_FINGER_METACARPAL("ring-finger-metacarpal", 15),
    RING_PHALANX_PROXIMAL("ring-phalanx-proximal", 16),
    RING_PHALANX_INTERMEDIATE("ring-finger-phalanx-intermediate", 17),
    RING_PHALANX_DISTAL("ring-phalanx-distal", 18),
    RING_TIP("ring-tip", 19),

    PINKY_FINGER_METACARPAL("pinky-finger-metacarpal", 20),
    PINKY_PHALANX_PROXIMAL("pinky-phalanx-proximal", 21),
    PINKY_PHALANX_INTERMEDIATE("pinky-finger-phalanx-intermediate", 22),
    PINKY_PHALANX_DISTAL("pinky-phalanx-distal", 23),
    PINKY_TIP("pinky-tip", 24);

    private final String key;
    private final int index;

    XRHandJoint(String key, int index) {
        this.key = key;
        this.index = index;
    }

    public String key() {
        return key;
    }

    public int index() {
        return index;
    }
}
