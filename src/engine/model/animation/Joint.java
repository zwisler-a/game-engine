package engine.model.animation;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Joint {

    private int id;
    private String name;
    private List<Joint> children;
    private Matrix4f transform; // Current pos


    private Matrix4f inverseBindTransform;


    public Joint(int id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public Joint(Joint joint) {
        this.id = joint.getId();
        this.name = joint.getName();
        this.children = new ArrayList<>();
        this.inverseBindTransform = joint.getInverseBindTransform();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Joint> getChildren() {
        return children;
    }

    public Matrix4f getTransform() {
        if (this.transform == null) {
            return new Matrix4f();
        }
        return transform;
    }

    public Matrix4f getInverseBindTransform() {
        return inverseBindTransform;
    }

    public void setInverseBindTransform(Matrix4f inverseBindTransform) {
        this.inverseBindTransform = inverseBindTransform;
    }

    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }
}
