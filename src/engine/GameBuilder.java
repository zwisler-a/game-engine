package engine;

import common.Logger.Logger;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.lang.reflect.InvocationTargetException;

public class GameBuilder {

    private GameSettings settings;

    public GameBuilder() {
        settings = new GameSettings();
    }

    public void start(Class game) {
        Logger.log("Starting game ...");
        Logger.log("Logging-Level: \t\t\t" + Logger.loggingLevel);
        Logger.log("Window-Size: \t\t\t" + this.settings.windowDimensions.x + "x" + this.settings.windowDimensions.y);
        Logger.log("Resolution: \t\t\t" + this.settings.resolutionX + "x" + this.settings.resolutionY);
        Logger.log("Target-FPS: \t\t\t" + this.settings.targetFps);
        Logger.log("Background-Color: \t\t\t" + this.settings.backgroundColor.toString());
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

    public GameBuilder setLoggingLevel(int level) {
        Logger.loggingLevel = level;
        return this;
    }

    public GameBuilder setWindowDimensions(int width, int height) {
        settings.windowDimensions = new Vector2i(width, height);
        return this;
    }

    public GameBuilder setBackgroundColor(float r, float g, float b) {
        settings.backgroundColor = new Vector3f(r, g, b);
        return this;
    }

    public GameBuilder setResolution(int width, int height) {
        settings.resolutionX = width;
        settings.resolutionY = height;
        return this;
    }


    public GameBuilder setTargetFps(int targetFps) {
        this.settings.targetFps = targetFps;
        return this;
    }
}
