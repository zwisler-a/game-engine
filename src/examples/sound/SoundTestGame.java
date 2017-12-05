package examples.sound;

import common.Logger.Logger;
import engine.Game;
import engine.GameSettings;
import engine.input.KeyboardHandler;
import engine.scene.Scene;
import sound.Player;

import javax.sound.sampled.Clip;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;

public class SoundTestGame extends Game {

    private Clip clip = null;

    public SoundTestGame(GameSettings gameSettings) {
        super(gameSettings);
    }

    public void load() {
        this.currentScene = new Scene();
        Player.load("res/sound/test.wav");
    }


    public void tick(double deltaT) throws Exception {
        super.tick(deltaT);
        if (KeyboardHandler.isKeyDown(GLFW_KEY_P)) {
            if (clip == null) {
                clip = Player.load("res/sound/test.wav");
                clip.start();
            }
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            if (clip != null)
                clip.stop();
            clip = null;
        }

    }
}
