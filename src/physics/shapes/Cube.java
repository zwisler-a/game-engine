package physics.shapes;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import physics.PhysicsEngine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Cube extends Body {


    private Vector3f size;

    public Cube(Vector3f size) {
        this.size = size;
    }

    public void setSize(Vector3f size){
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
        normals = new Vector3f[6];
        normals[0] = new Vector3f(0, 0, 1);
        normals[1] = new Vector3f(0, 1, 0);
        normals[2] = new Vector3f(1, 0, 0);

        normals[3] = new Vector3f(0, 0, -1);
        normals[4] = new Vector3f(0, -1, 0);
        normals[5] = new Vector3f(-1, 0, 0);

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
    public ArrayList<ArrayList<Vector3f>> getFacesPointingTo(Vector3f refNormal) {
        this.calcNormals();

        ArrayList<Vector3f> norm = new ArrayList<>();
        for (Vector3f normal : normals) {
            if (normal.dot(refNormal) > 0) {
                norm.add(normal);
            }
        }
        ArrayList<ArrayList<Vector3f>> faces = new ArrayList<>();
        for (Vector3f normal : norm) {
            faces.add(this.getFaceOfNormal(normal));
        }

        // Display

        /*
        ArrayList<Vector3f> vecs = new ArrayList<>();
        int k = 0;
        PhysicsEngine.wtfC = new ArrayList<>();


        for (List<Vector3f> vecsL : faces) {
            for (Vector3f v : vecsL) {
                vecs.add(v);
                PhysicsEngine.wtfC.add((k) % 3);
            }
            k++;
        }
        PhysicsEngine.wtf = vecs;


        for (Vector3f normal : norm) {
            for (int i = 0; i < 5; i++) {
                Vector3f dm = new Vector3f(normal).normalize();
                vecs.add(new Vector3f(dm).mul(i));
            }

        }

        */
        // -----------
        return faces;
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
