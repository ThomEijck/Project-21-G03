package GUI3D.maths;

import GUI3D.entities.Camera;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

public class Maths {

// Bary Centric function: will return the height of the triangle of the player position

    @Contract(pure = true)
    public static float barryCentric(@NotNull Vector3f p1, @NotNull Vector3f p2, @NotNull Vector3f p3, @NotNull Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }
// creation of a transformation Matrix

    public static @NotNull Matrix4f createTransformationMatrix(Vector2f translation, @NotNull Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
        return matrix;
    }


    public static @NotNull Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(translation);
        matrix.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
        matrix.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
        matrix.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
        matrix.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }
// creation of a view Matrix


    public static @NotNull Matrix4f createViewMatrix(@NotNull Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
                viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }

}
