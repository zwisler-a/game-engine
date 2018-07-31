package engine;

import common.Logger.Logger;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.lang.reflect.InvocationTargetException;

/**
 * Helper class to build a new Game
 */
public class GameBuilder {

    private GameSettings settings;

    public GameBuilder() {
        settings = new GameSettings();
    }

    /**
     * Executes the new game with the predefined settings
     * @param game Entry class of the new game
     */
    public void start(Class game) {
        Logger.log("Starting game ...");
        Logger.log("Logging-Level: \t\t\t" + Logger.loggingLevel);
        Logger.log("Window-Size: \t\t\t" + this.settings.windowDimensions.x + "x" + this.settings.windowDimensions.y);
        Logger.log("Resolution: \t\t\t" + this.settings.resolutionX + "x" + this.settings.resolutionY);
        Logger.log("Target-FPS: \t\t\t" + this.settings.targetFps);
        Logger.log("Background-Color: \t\t\t" + this.settings.backgroundColor.toString());
        Logger.log("\n\n");
        try {
            game.getConstructor(GameSettings.class).newInstance(settings);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the logging level of the game
     * @param level {@see LoggingLevel}
     * @return this
     */
    public GameBuilder setLoggingLevel(int level) {
        Logger.loggingLevel = level;
        return this;
    }

    /**
     * Sets the dimensions of the displayed window
     * Window will always be centered on the main screen
     * @param width Width of the window
     * @param height Height of the window
     * @return this
     */
    public GameBuilder setWindowDimensions(int width, int height) {
        settings.windowDimensions = new Vector2i(width, height);
        return this;
    }

    /**
     * In case there is nothing to draw over previous render call its resets to this color
     * @param r Red
     * @param g Green
     * @param b Blue
     * @return this
     */
    public GameBuilder setBackgroundColor(float r, float g, float b) {
        settings.backgroundColor = new Vector3f(r, g, b);
        return this;
    }

    /**
     * Sets the resolution of the game
     * @param width Width
     * @param height Height
     * @return
     */
    public GameBuilder setResolution(int width, int height) {
        settings.resolutionX = width;
        settings.resolutionY = height;
        return this;
    }

    /**
     * Sets the fps that are wanted the game to be run at
     * @param targetFps FPS
     * @return this
     */
    public GameBuilder setTargetFps(int targetFps) {
        this.settings.targetFps = targetFps;
        return this;
    }
}
