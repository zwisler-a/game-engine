package examples.sound;

import common.Logger.LoggingLevel;
import engine.GameBuilder;
import examples.physics.PhysicsTestGame;

public class Main {
    public static void main(String[] args) {

        new GameBuilder()
                .setLoggingLevel(LoggingLevel.DEBUG)
                .setWindowDimensions(400, 300)
                .setResolution(1280, 860)
                .setBackgroundColor(0, 0, 0)
                .setTargetFps(60)
                .start(SoundTestGame.class);
    }
}
