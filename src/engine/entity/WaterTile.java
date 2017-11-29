package engine.entity;

import engine.model.PlaneModel;
import engine.model.Texture;
import engine.model.TexturedModel;
import engine.renderer.WaterRenderer;
import engine.renderer.framebuffer.FrameBuffer;
import engine.renderer.framebuffer.FrameBufferBuilder;
import engine.scene.Scene;
import org.joml.Vector3f;

public class WaterTile extends Entity implements Simulated {

    private FrameBuffer reflectionFbo;
    private FrameBuffer refractionFbo;
    private float waterDistortion = 0.02f;

    private float timeFactor = 0;
    private float waveSpeed = 0.003f;

    public WaterTile(Vector3f position, float scale, Scene s) {
        this.setPosition(position);
        this.reflectionFbo = new FrameBufferBuilder()
                .setDimensions(400, 400)
                .addTexture()
                .addDepthBuffer()
                .create();
        this.refractionFbo = new FrameBufferBuilder()
                .setDimensions(400, 400)
                .addTexture()
                .addDepthBuffer()
                .addDepthTexture()
                .create();

        this.setRenderer(WaterRenderer.class);
        this.setRotation(new Vector3f(0, 0, 0));
        this.setScale(scale);
        s.registerSimulation(this);
    }

    public float getTimeFactor() {

        return this.timeFactor;
    }

    public FrameBuffer getReflectionFbo() {
        return this.reflectionFbo;
    }

    public FrameBuffer getRefractionFbo() {
        return this.refractionFbo;
    }

    public float getWaterDistortion() {
        return waterDistortion;
    }

    @Override
    public void simulate(double dt) {
        this.timeFactor += this.waveSpeed * dt;
        this.timeFactor %= 1;
    }
}
