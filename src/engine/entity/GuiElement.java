package engine.entity;

import engine.model.Texture;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GuiElement {

    private Texture texture;
    private Vector2f position;
    private Vector2f size;
    private boolean upsideDown = false;

    public GuiElement(Texture texture, Vector2f position, Vector2f size) {
        this.texture = texture;
        this.position = position;
        this.size = size;
    }

    public GuiElement(Texture texture, Vector2f position, Vector2f size, boolean upsideDown) {
        this.texture = texture;
        this.position = position;
        this.size = size;
        this.upsideDown = upsideDown;
    }


    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public Matrix4f createTransformationMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(new Vector3f(position.x, position.y, 0));
        matrix.rotate((float) Math.toRadians((double) 90), new Vector3f(1.0F, 0.0F, 0.0F));
        matrix.rotate((float) Math.toRadians((double) (upsideDown ? 180 : 0)), new Vector3f(0.0F, 1.0F, 0.0F));
        matrix.rotate((float) Math.toRadians((double) (upsideDown ? 180 : 0)), new Vector3f(0.0F, 0.0F, 1.0F));
        matrix.scale(new Vector3f(this.size.x, 1, this.size.y));
        return matrix;
    }
}
