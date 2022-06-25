package com.badlogic.gdx.backends.gwt.webxr;

import com.badlogic.gdx.math.Matrix4;
import elemental2.core.Float32Array;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MatrixUtilsTest {

    @Test
    public void testBuildMatrix() {
        double[] array = new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Float32Array floatArray = mockFloat32Array(array);

        Matrix4 out = new Matrix4();
        MatrixUtils.buildMatrix4(floatArray, 0, out);

        for (int i = 0; i < out.val.length; i++) {
            assertEquals(array[i], out.val[i], 0);
        }
    }

    @Test
    public void testCheckEqualsMatrix() {
        double[] array = new double[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        Float32Array floatArray = mockFloat32Array(array);

        Matrix4 matrix = new Matrix4();
        assertTrue(MatrixUtils.checkEquals(floatArray, matrix));

        array = new double[]{
                1.0001, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        floatArray = mockFloat32Array(array);
        assertFalse(MatrixUtils.checkEquals(floatArray, matrix));
    }

    private Float32Array mockFloat32Array(double[] array) {
        Float32Array a = mock(Float32Array.class);

        for (int i = 0; i < array.length; i++) {
            when(a.getAt(i)).thenReturn(array[i]);
        }
        return a;
    }

}
