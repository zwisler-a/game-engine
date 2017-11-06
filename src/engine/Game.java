package engine;

import common.Logger;
import engine.renderer.RenderOptions;
import engine.renderer.Renderer;
import engine.scene.Scene;

import static org.lwjgl.opengl.GL11.*;

public abstract class Game extends GameLoop {

    private final GameSettings gameSettings;
    protected Renderer renderer;
    protected Scene currentScene;
    protected RenderOptions renderOptions;

    public Game(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.renderOptions = new RenderOptions();
        this.startGameLoop();
    }

    @Override
    protected void init() {
        WindowManager.createWindow(gameSettings.windowDimensions.x, gameSettings.windowDimensions.y, gameSettings.backgroundColor);
        Logger.debug("OpenGL: " + glGetString(GL_VERSION));
        renderer = new Renderer();
        this.load();
    }

    @Override
    public void tick(double deltaT) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        if (this.currentScene != null) {
            this.currentScene.tick(deltaT);
        }
        this.renderer.render(currentScene, this.renderOptions);
        WindowManager.update();
        int glErrorCode = glGetError();
        if (glErrorCode != GL_NO_ERROR) {
            Logger.error("GL Error: " + glErrorCode);
        }
        if(WindowManager.shouldClose()){
            this.stopGameLoop();
        }
    }

    public abstract void load();
}
