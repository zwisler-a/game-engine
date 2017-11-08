package engine.entity;


import common.Logger;
import engine.renderer.RenderOptions;
import engine.renderer.ShadowRenderer;
import engine.renderer.framebuffer.FrameBuffer;
import engine.renderer.framebuffer.FrameBufferBuilder;
import engine.scene.Scene;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;

public class LightSource {

    private static int lightSource_idCounter = 0;
    private static ShadowRenderer shadowRenderer;

    private Vector3f position;
    private Vector3f color;
    private float intensity = 1;
    private int lightSource_id;
    private FrameBuffer fbo;
    private boolean shadowEnabled = false;

    public LightSource(Vector3f position, Vector3f color, float intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
        lightSource_id = lightSource_idCounter++;
        this.fbo = new FrameBufferBuilder()
                .setDimensions(1024, 1024)
                .addDepthBuffer()
                .addDepthTexture()
                .addTexture()
                .create();
        if (LightSource.shadowRenderer == null) {
            LightSource.shadowRenderer = new ShadowRenderer();
        }
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

    public void renderShadowMap(Scene scene) {
        this.fbo.bind();
        LightSource.shadowRenderer.render(scene, Camera.createViewMatrix(this.position, new Vector3f(90,0,0)));
        this.fbo.unbind();
    }

    public FrameBuffer getFbo() {
        return this.fbo;
    }

    public void enableShadow() {
        this.shadowEnabled = true;
    }

    public boolean isShadowEnabled() {
        return shadowEnabled;
    }
}
