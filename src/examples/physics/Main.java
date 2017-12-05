package examples.physics;

import common.Logger.LoggingLevel;
import engine.GameBuilder;
import sound.Player;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        new GameBuilder()
                .setLoggingLevel(LoggingLevel.DEBUG)
                .setWindowDimensions(400, 300)
                .setResolution(1280, 860)
                .setBackgroundColor(0, 0, 0)
                .setTargetFps(60)
                .start(PhysicsTestGame.class);
    }
}
