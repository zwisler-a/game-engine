package examples.physics2.physics;

import common.Logger.LoggingLevel;
import engine.GameBuilder;

public class Main {
    public static void main(String[] args) {
        new GameBuilder()
                .setLoggingLevel(LoggingLevel.DEBUG)
                .setWindowDimensions(1280, 860)
                .setResolution(1280, 860)
                .setBackgroundColor(0, 0, 0)
                .setTargetFps(60)
                .start(PhysicsTestGame.class);
    }
}
