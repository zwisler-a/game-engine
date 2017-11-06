package common;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Random;

public class Maths {
    private static Random r = new Random();

    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(translation);
        matrix.rotate((float) Math.toRadians((double) rx), new Vector3f(1.0F, 0.0F, 0.0F));
        matrix.rotate((float) Math.toRadians((double) ry), new Vector3f(0.0F, 1.0F, 0.0F));
        matrix.rotate((float) Math.toRadians((double) rz), new Vector3f(0.0F, 0.0F, 1.0F));
        matrix.scale(new Vector3f(scale, scale, scale));
        return matrix;
    }

    public static Vector3f calculateSurfaceNormal(Vector3f p1, Vector3f p2, Vector3f p3) {

        Vector3f u = new Vector3f(p2).sub(p1);
        Vector3f v = new Vector3f(p3).sub(p1);
        Vector3f res = u.cross(v).normalize();
        return res;
    }

    public static Float random() {
        return r.nextFloat();
    }
}
