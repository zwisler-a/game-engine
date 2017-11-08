package engine.renderer;

import org.joml.Vector4f;

public class RenderOptions {

    public boolean staticRenderer;
    public boolean skyboxRenderer;
    public boolean waterRenderer;
    public boolean guiRenderer;
    public boolean queuedRenderer;

    public Vector4f clippingPlane = new Vector4f(0,-1,0,50000);


    public RenderOptions() {
        staticRenderer = true;
        skyboxRenderer = true;
        waterRenderer = true;
        guiRenderer = true;
        queuedRenderer = false;
    }


    public RenderOptions(boolean staticRenderer, boolean skyboxRenderer, boolean waterRenderer, boolean guiRenderer, boolean queuedRenderer) {
        this.staticRenderer = staticRenderer;
        this.skyboxRenderer = skyboxRenderer;
        this.waterRenderer = waterRenderer;
        this.guiRenderer = guiRenderer;
        this.queuedRenderer = queuedRenderer;
    }

}
