package physics.shapes;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Cube extends Body {


    private Vector3f size;

    public Cube(Vector3f size) {
        this.size = size;
    }

    void calcPoints() {
        Vector3f origin = this.getPosition();
        points = new Vector3f[8];
        points[0] = new Vector3f(origin).sub(size.x / 2, size.y / 2, size.z / 2);
        points[1] = new Vector3f(origin).sub(size.x / 2, size.y / 2, -size.z / 2);
        points[2] = new Vector3f(origin).sub(size.x / 2, -size.y / 2, size.z / 2);
        points[3] = new Vector3f(origin).sub(size.x / 2, -size.y / 2, -size.z / 2);
        points[4] = new Vector3f(origin).sub(-size.x / 2, size.y / 2, size.z / 2);
        points[5] = new Vector3f(origin).sub(-size.x / 2, size.y / 2, -size.z / 2);
        points[6] = new Vector3f(origin).sub(-size.x / 2, -size.y / 2, size.z / 2);
        points[7] = new Vector3f(origin).sub(-size.x / 2, -size.y / 2, -size.z / 2);


        Vector3f center = new Vector3f(origin);
        Matrix4f mat = new Matrix4f().translate(center);
        mat.rotateX((float) Math.toRadians(this.getRotation().x));
        mat.rotateY((float) Math.toRadians(this.getRotation().y));
        mat.rotateZ((float) Math.toRadians(this.getRotation().z));
        mat.translate(center.negate());
        for (Vector3f point : points) {

            mat.transformPosition(point);
        }
    }

    @Override
    void calcNormals() {
        normals = new Vector3f[3];
        normals[0] = new Vector3f(0, 0, 1);
        normals[1] = new Vector3f(0, 1, 0);
        normals[2] = new Vector3f(1, 0, 0);

        Matrix4f mat = new Matrix4f();
        mat.rotateX((float) Math.toRadians(this.getRotation().x));
        mat.rotateY((float) Math.toRadians(this.getRotation().y));
        mat.rotateZ((float) Math.toRadians(this.getRotation().z));
        for (Vector3f point : normals) {
            mat.transformPosition(point);
        }
        // Logger.log(this.getRotation().x + "||" + normals[1].x + " , " + normals[1].y + " , " + normals[1].z);
    }

    @Override
    public ArrayList<Vector3f> getFaceOfNormal(Vector3f normal) {
        ArrayList<Vector3f> face = new ArrayList<>();
        for (Vector3f point : this.points) {
            if (normal.dot(new Vector3f(point).sub(this.getPosition())) > 0) {
                face.add(point);
            }
        }

        return face;
    }

    @Override
    void calcInertialTensor() {
        float width = this.size.x;
        float height = this.size.y;
        float depth = this.size.z;

        float xTensor = 0.083f * this.mass * (height * height + depth * depth);
        float yTensor = 0.083f * this.mass * (width * width + depth * depth);
        float zTensor = 0.083f * this.mass * (width * width + height * height);

        this.inertiaTensor = new Matrix3f();
        this.inertiaTensor.m00 = xTensor;
        this.inertiaTensor.m11 = yTensor;
        this.inertiaTensor.m22 = zTensor;
        this.inertiaTensor.invert();
    }


}
