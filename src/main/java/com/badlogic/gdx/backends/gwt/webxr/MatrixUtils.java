package com.badlogic.gdx.backends.gwt.webxr;

import com.badlogic.gdx.math.Matrix4;
import elemental2.core.Float32Array;

public class MatrixUtils {

    // https://www.w3.org/TR/webxr/#matrices
    public static Matrix4 buildMatrix4(Float32Array array, Matrix4 out) {
        return buildMatrix4(array, 0, out);
    }

    public static Matrix4 buildMatrix4(Float32Array array, int offset, Matrix4 out) {
        fillArray(array, offset, out.val);
        return out;
    }

    public static float[] fillArray(Float32Array matrix, float[] out) {
        return fillArray(matrix, 0, out);
    }

    public static float[] fillArray(Float32Array matrix, int offset, float[] out) {
        for (int p = offset; p < out.length; p++) {
            out[p] = matrix.getAt(p).floatValue();
        }
        return out;
    }

    public static boolean checkEquals(Float32Array matrix, Matrix4 matrix4) {
        for (int p = 0; p < matrix4.val.length; p++) {
            float value = matrix.getAt(p).floatValue();
            // TODO Use a better tolerance
            if (value != matrix4.val[p]) {
                return false;
            }
        }
        return true;
    }
}
