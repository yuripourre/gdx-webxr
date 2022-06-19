package com.badlogic.gdx.backends.gwt.webxr;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.google.gwt.webxr.DOMPointReadOnly;
import com.google.gwt.webxr.XRRigidTransform;
import com.google.gwt.webxr.XRView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CameraUtilsTest {

    @Test
    public void testSetupCameraPosition() {
        XRView view = mock(XRView.class);

        XRRigidTransform transform = new XRRigidTransform();
        transform.position = new DOMPointReadOnly();
        transform.position.x = 2;
        transform.position.y = 3;
        transform.position.z = 4;

        when(view.getTransform()).thenReturn(transform);
        PerspectiveCamera camera = new PerspectiveCamera();

        CameraUtils.setupCameraPosition(view, camera, new Vector3());

        assertEquals(2, camera.position.x, 0);
        assertEquals(3, camera.position.y, 0);
        assertEquals(4, camera.position.z, 0);
    }

    @Test
    public void testSetupCameraPosition_InitialOffset() {
        XRView view = mock(XRView.class);

        XRRigidTransform transform = new XRRigidTransform();
        transform.position = new DOMPointReadOnly();
        transform.position.x = 2;
        transform.position.y = 3;
        transform.position.z = 4;

        when(view.getTransform()).thenReturn(transform);
        PerspectiveCamera camera = new PerspectiveCamera();

        CameraUtils.setupCameraPosition(view, camera, new Vector3(1,2,3));

        assertEquals(3, camera.position.x, 0);
        assertEquals(5, camera.position.y, 0);
        assertEquals(7, camera.position.z, 0);
    }

    @Test
    public void testSetupCameraDirection() {
        XRView view = mock(XRView.class);

        XRRigidTransform transform = new XRRigidTransform();

        transform.orientation = new DOMPointReadOnly();
        transform.orientation.x = 0;
        transform.orientation.y = 0;
        transform.orientation.z = 0;
        transform.orientation.w = 1;

        when(view.getTransform()).thenReturn(transform);

        PerspectiveCamera camera = new PerspectiveCamera();

        Quaternion offsetRotation = new Quaternion().setEulerAngles(0, 0, 0);
        CameraUtils.setupCameraDirection(view, camera, offsetRotation);

        assertEquals(0, camera.direction.x, 0f);
        assertEquals(0, camera.direction.y, 0);
        assertEquals(-1, camera.direction.z, 0);
    }

    @Test
    public void testSetupCameraDirection_InitialOffset() {
        XRView view = mock(XRView.class);

        XRRigidTransform transform = new XRRigidTransform();

        transform.orientation = new DOMPointReadOnly();
        transform.orientation.x = 0;
        transform.orientation.y = 0;
        transform.orientation.z = 0;
        transform.orientation.w = 1;

        when(view.getTransform()).thenReturn(transform);

        PerspectiveCamera camera = new PerspectiveCamera();

        Quaternion offsetRotation = new Quaternion().setEulerAngles(90, 0, 0);
        CameraUtils.setupCameraDirection(view, camera, offsetRotation);

        assertEquals(-1, camera.direction.x, 0.1f);
        assertEquals(0, camera.direction.y, 0);
        assertEquals(0, camera.direction.z, 0);
    }

    @Test
    public void testSetupCameraDirection_InitialAndOriginalOffset() {
        XRView view = mock(XRView.class);

        XRRigidTransform transform = new XRRigidTransform();

        Quaternion orientation = new Quaternion().setEulerAngles(45, 0, 0);
        transform.orientation = new DOMPointReadOnly();
        transform.orientation.x = orientation.x;
        transform.orientation.y = orientation.y;
        transform.orientation.z = orientation.z;
        transform.orientation.w = orientation.w;

        when(view.getTransform()).thenReturn(transform);

        PerspectiveCamera camera = new PerspectiveCamera();

        Quaternion offsetRotation = new Quaternion().setEulerAngles(90, 0, 0);
        CameraUtils.setupCameraDirection(view, camera, offsetRotation);

        assertEquals(-0.7071067, camera.direction.x, 0.1f);
        assertEquals(0, camera.direction.y, 0);
        assertEquals(0.7071067, camera.direction.z, 0.1f);
    }

}
