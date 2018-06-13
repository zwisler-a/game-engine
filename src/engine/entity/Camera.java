//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package engine.entity;


import engine.SwingWindows.DataDisplay;
import engine.input.KeyboardHandler;
import engine.input.MouseHandler;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private float rotX;
    private float rotY;
    private float rotZ;
    private float speed = 0.5f;

    public Camera(Vector3f position, float rotX, float rotY, float rotZ) {
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    public Camera(Camera c) {
        this.position = new Vector3f(c.position);
        this.rotX = c.getRotX();
        this.rotY = c.getRotY();
        this.rotZ = c.getRotZ();
    }

    public void checkMovementInput(double dt) {





    }

    public Vector3f getPosition() {
        return this.position;
    }

    public float getRotX() {
        return this.rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return this.rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return this.rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public void setPosition(Vector3f pos) {
        this.position = pos;
    }

    public static Matrix4f createViewMatrix(Camera c) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians((double) c.getRotX()), new Vector3f(1.0F, 0.0F, 0.0F));
        viewMatrix.rotate((float) Math.toRadians((double) c.getRotY()), new Vector3f(0.0F, 1.0F, 0.0F));
        viewMatrix.rotate((float) Math.toRadians((double) c.getRotZ()), new Vector3f(0.0F, 0.0F, 1.0F));
        Vector3f pos = c.getPosition();
        Vector3f negPos = new Vector3f(-pos.x, -pos.y, -pos.z);
        viewMatrix.translate(negPos);
        return viewMatrix;
    }

    public static Matrix4f createViewMatrix(Vector3f position, Vector3f rotation) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians((double) rotation.x), new Vector3f(1.0F, 0.0F, 0.0F));
        viewMatrix.rotate((float) Math.toRadians((double) rotation.y), new Vector3f(0.0F, 1.0F, 0.0F));
        viewMatrix.rotate((float) Math.toRadians((double) rotation.z), new Vector3f(0.0F, 0.0F, 1.0F));
        Vector3f pos = position;
        Vector3f negPos = new Vector3f(-pos.x, -pos.y, -pos.z);
        viewMatrix.translate(negPos);
        return viewMatrix;
    }


    public static Matrix4f createViewMatrixWithoutPosition(Camera c) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians((double) c.getRotX()), new Vector3f(1.0F, 0.0F, 0.0F));
        viewMatrix.rotate((float) Math.toRadians((double) c.getRotY()), new Vector3f(0.0F, 1.0F, 0.0F));
        viewMatrix.rotate((float) Math.toRadians((double) c.getRotZ()), new Vector3f(0.0F, 0.0F, 1.0F));
        return viewMatrix;
    }
}
