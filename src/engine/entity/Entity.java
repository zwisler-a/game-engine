package engine.entity;

import engine.model.TexturedModel;
import engine.scene.Scene;
import org.joml.Vector3f;

public class Entity {

    private static int id_counter = 0;

    private Vector3f position = new Vector3f();
    private Vector3f rotation = new Vector3f();
    private TexturedModel model;
    private int id = id_counter++;
    private Class renderer;
    private float scale = 1;

    public Entity() {

    }


    public Class getRenderer() {
        return this.renderer;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setRenderer(Class renderer) {
        this.renderer = renderer;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
