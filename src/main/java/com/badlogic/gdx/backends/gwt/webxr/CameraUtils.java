package com.badlogic.gdx.backends.gwt.webxr;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.google.gwt.webxr.XRView;
import com.google.gwt.webxr.XRViewport;

import static com.badlogic.gdx.backends.gwt.webxr.MatrixUtils.buildMatrix4;

public class CameraUtils {

    private static final Matrix4 tmpMatrix = new Matrix4();
    private static final Quaternion tmpQuaternion = new Quaternion();
    private static final Vector3 tmpPosition = new Vector3();
    private static final Vector3 tmpUp = new Vector3();

    private static final Vector3 tmpOffsetPosition = new Vector3();
    private static final Quaternion tmpQuaternionOffset = new Quaternion();

    public static void updateCamera(XRView view, XRViewport viewport, PerspectiveCamera camera) {
        tmpOffsetPosition.set(0, 0, 0);
        tmpQuaternionOffset.idt();
        updateCamera(view, viewport, camera, tmpOffsetPosition, tmpQuaternionOffset);
    }

    public static void updateCamera(XRView view, XRViewport viewport, PerspectiveCamera camera, Vector3 offsetPosition, Quaternion offsetRotation) {
        camera.viewportWidth = viewport.getWidth();
        camera.viewportHeight = viewport.getHeight();

        setupCameraDirection(view, camera, offsetRotation);
        setupCameraPosition(view, camera, offsetPosition);

        // I don't know exactly why but if I set z as -1
        // Direction has to be 0, 0, 1 otherwise it doesn't work
        tmpUp.set(0,1,0).mul(tmpQuaternion);
        camera.up.set(tmpUp);

        Matrix4 projection = buildMatrix4(view.getProjectionMatrix(), tmpMatrix);

        float fov = (float) (2 * Math.atan(1/projection.val[5]) * 180 / Math.PI);
        //float aspect = viewport.getWidth() / (float)viewport.getHeight();
        float near = projection.val[14] / (projection.val[10] - 1.0f);
        float far = projection.val[14] / (projection.val[10] + 1.0f);
        camera.near = near;
        camera.far = far;
        camera.fieldOfView = fov;

        //camera.update(true);
        // Extracted from camera.update(true)
        camera.projection.set(projection);
        camera.view.setToLookAt(camera.position, tmpPosition.set(camera.position).add(camera.direction), tmpUp);
        camera.combined.set(projection);
        Matrix4.mul(camera.combined.val, camera.view.val);

        // Update frustrum
        camera.invProjectionView.set(camera.combined);
        Matrix4.inv(camera.invProjectionView.val);
        camera.frustum.update(camera.invProjectionView);
    }

    static void setupCameraPosition(XRView view, PerspectiveCamera camera, Vector3 offsetPosition) {
        tmpPosition.set((float) view.getTransform().position.x,
                (float) view.getTransform().position.y,
                (float) view.getTransform().position.z);
        tmpPosition.add(offsetPosition);

        camera.position.set(tmpPosition);
    }

    static void setupCameraDirection(XRView view, PerspectiveCamera camera, Quaternion offsetRotation) {
        tmpQuaternion.set((float) view.getTransform().orientation.x,
                (float) view.getTransform().orientation.y,
                (float) view.getTransform().orientation.z,
                (float) view.getTransform().orientation.w);

        if (offsetRotation.x != 0 || offsetRotation.y != 0 || offsetRotation.z != 0) {
            tmpQuaternion.mulLeft(offsetRotation);
        }

        // Set direction (by default libgdx uses z=-1)
        camera.direction.set(0, 0, -1).mul(tmpQuaternion);
    }

}
