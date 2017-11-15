package examples.example1;

import common.Logger.LoggingLevel;
import engine.GameBuilder;

public class Main {

    public static void main(String[] args) {

        new GameBuilder()
                .setLoggingLevel(LoggingLevel.DEBUG)
                .setWindowDimensions(400,300)
                .setResolution(1280,860)
                .setBackgroundColor(0,0,0)
                .setTargetFps(60)
                .start(MainObject.class);
    }

}
