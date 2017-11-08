package examples.example1;

import common.Logger;
import engine.GameBuilder;

public class Main {

    public static void main(String[] args) {

        new GameBuilder()
                .setLoggingLevel(Logger.DEBUG)
                .setWindowDimensions(1200,800)
                .setResolution(1280,860)
                .setBackgroundColor(0,0,0)
                .setTargetFps(30)
                .start(MainObject.class);
    }

}
