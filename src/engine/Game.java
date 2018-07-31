package engine;

import common.Logger.Logger;
import engine.renderer.RenderOptions;
import engine.renderer.Renderer;
import engine.scene.Scene;
import physics.PhysicsEngine;

import static org.lwjgl.opengl.GL11.*;

public abstract class Game extends GameLoop {

    public static GameSettings gameSettings;
    protected Renderer renderer;
    protected PhysicsEngine physicsEngine;
    protected Scene currentScene;
    protected RenderOptions renderOptions;

    public Game(GameSettings gameSettings) {
        Game.gameSettings = gameSettings;
        this.renderOptions = new RenderOptions(true, true, true, true, true, true);
        this.startGameLoop(gameSettings.targetFps);
    }

    /**
     * Initiates a new game. Opens a window and creates a new Renderer
     * Also handles the first rendering for the display of a splashscreen
     */
    @Override
    protected void init() {
        WindowManager.createWindow(gameSettings.windowDimensions.x, gameSettings.windowDimensions.y, gameSettings.backgroundColor);
        renderer = new Renderer();
        physicsEngine = new PhysicsEngine();
        this.load();
        this.renderer.init(gameSettings);
    }

    /**
     * Updates the game and triggers the renderer
     * @param deltaT Time passed since last call
     * @throws Exception Catches and logs thrown errors. Game stops
     */
    @Override
    public void tick(double deltaT) throws Exception {
        // ------ Main Game Loop -------
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        if (this.currentScene != null) {
            this.currentScene.tick(deltaT);
        }
        this.renderer.render(currentScene, this.renderOptions);
        WindowManager.update();
        this.physicsEngine.tick(deltaT);
        // ------ Main Game Loop -------


        // Check openGl errors
        int glErrorCode = glGetError();
        if (glErrorCode != GL_NO_ERROR) {
            Logger.error("GL Error: " + glErrorCode);
        }

        // Check if its still supposed to run
        if (WindowManager.shouldClose()) {
            this.stopGameLoop();
        }
    }

    /**
     * Initialisation of the game data.
     * Loading models/textures ...
     */
    public abstract void load();
}
