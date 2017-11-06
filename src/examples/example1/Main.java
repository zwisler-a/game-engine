package examples.example1;

import common.Logger;
import engine.GameSettings;
import org.joml.Vector2i;
import org.joml.Vector3f;


public class Main {

    public static void main(String[] args) {
        Logger.loggingLevel = Logger.DEBUG;
        Logger.debug("Start ...");

        GameSettings settings = new GameSettings();
        settings.windowDimensions = new Vector2i(1280, 800);
        settings.backgroundColor = new Vector3f(0, 255, 0);

        new MainObject(settings);

    }

}
