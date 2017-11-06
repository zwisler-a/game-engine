package engine.entity;


import org.joml.Vector3f;

public class LightSource {

    private static int lightSource_idCounter = 0;

    private Vector3f position;
    private Vector3f color;
    private float intensity = 1;
    private int lightSource_id;

    public LightSource(Vector3f position, Vector3f color, float intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
        lightSource_id = lightSource_idCounter++;
    }

    public LightSource(float x, float y, float z, float r, float g, float b, float intensity) {
        this.position = new Vector3f(x, y, z);
        this.color = new Vector3f(r, g, b);
        this.intensity = intensity;
        lightSource_id = lightSource_idCounter++;
    }

    public int getLightSourceId() {
        return lightSource_id;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

}
