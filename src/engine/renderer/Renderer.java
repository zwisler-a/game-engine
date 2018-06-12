package engine.renderer;

import engine.Game;
import engine.GameSettings;
import engine.WindowManager;
import engine.entity.GuiElement;
import engine.model.Texture;
import engine.renderer.queued.QueuedRenderer;
import engine.scene.Scene;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.util.LinkedList;

public class Renderer {
    private GameSettings gameSettings;
    public StaticRenderer staticRenderer;
    public SkyboxRenderer skyboxRenderer;
    public WaterRenderer waterRenderer;
    public GuiRenderer guiRenderer;
    private long lastLoopTime;
    private AnimatedModelRenderer animatedModelRenderer;


    public Renderer() {
        this.guiRenderer = new GuiRenderer();
    }

    public void init(GameSettings settings) {

        GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glCullFace(GL11.GL_BACK);

        Matrix4f projectionMatrix = Renderer.createProjectionMatrix(Game.gameSettings.resolutionX, Game.gameSettings.resolutionY);
        this.staticRenderer = new StaticRenderer(projectionMatrix);
        this.animatedModelRenderer = new AnimatedModelRenderer(projectionMatrix);
        this.skyboxRenderer = new SkyboxRenderer(projectionMatrix);
        this.waterRenderer = new WaterRenderer(projectionMatrix);

        this.gameSettings = settings;


        this.lastLoopTime = System.nanoTime();
    }

    public void renderOneImageScreen(Texture image) {
        GuiElement guiElement = new GuiElement(
                image,
                new Vector2f(0f, 0f),
                new Vector2f(1, 1), true);
        LinkedList<GuiElement> guiElements = new LinkedList<>();
        guiElements.add(guiElement);
        this.guiRenderer.render(guiElements);
        WindowManager.update();
    }

    public void render(Scene scene, RenderOptions options) {
        if (scene == null) {
            throw new Error("No Scene defined!");
        }
        if (options.queuedRenderer) {
            // Render as much as the left time allows you too
            // TODO: Take avg of other renderers into account ...
            //  -- Maybe able to run queued renderer at the end?
            do {
                QueuedRenderer.renderOnce();
            } while (this.getTimeLeft() > 10 && QueuedRenderer.hasQueue());
        }
        if (options.skyboxRenderer) {
            this.skyboxRenderer.render(scene.getCamera(), scene.getSkyboxTexture());
        }
        if (options.staticRenderer) {
            this.staticRenderer.render(scene.getEntities(StaticRenderer.class), scene.getLightSources(), scene.getCamera(), options);
        }
        if (options.animatedModelRenderer) {
            this.animatedModelRenderer.render(scene.getEntities(AnimatedModelRenderer.class), scene.getLightSources(), scene.getCamera(), options);
        }
        if (options.waterRenderer) {
            this.waterRenderer.render(scene, this);
        }
        if (options.guiRenderer) {
            this.guiRenderer.render(scene.getGuiElements());
        }

        lastLoopTime = System.nanoTime();
    }

    public static Matrix4f createProjectionMatrix(float width, float height) {
        Matrix4f projectionMatrix = new Matrix4f();
        float fieldOfView = 70f;
        float aspectRatio = width / height;
        float near_plane = 0.1f;
        float far_plane = 1000f;

        float y_scale = (float) Math.tan(Math.toRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((far_plane + near_plane) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * near_plane * far_plane) / frustum_length));
        projectionMatrix.m33(0);

        return projectionMatrix;
    }

    public long getTimeLeft() {
        return (lastLoopTime - System.nanoTime() + (1000000000 / gameSettings.targetFps)) / 1000000;
    }
}
