package com.badlogic.gdx.backends.gwt.webxr;

import com.badlogic.gdx.math.Matrix4;
import elemental2.core.Float32Array;

public class MatrixUtils {

    // https://www.w3.org/TR/webxr/#matrices
    public static Matrix4 buildMatrix4(Float32Array array, Matrix4 out) {
        fillArray(array, out.val);
        return out;
    }

    public static float[] fillArray(Float32Array matrix, float[] out) {
        for (int p = 0; p < out.length; p++) {
            out[p] = matrix.getAt(p).floatValue();
        }
        return out;
    }

}
