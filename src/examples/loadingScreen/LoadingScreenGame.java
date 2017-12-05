package examples.loadingScreen;

import common.Logger.Logger;
import engine.Game;
import engine.GameSettings;
import engine.model.loaders.TextureLoader;

public class LoadingScreenGame extends Game {


    public LoadingScreenGame(GameSettings gameSettings) {
        super(gameSettings);
    }

    public void load() {

        String[] screens = new String[]{
                "res/splash/screen0.jpg",
                "res/splash/screen25.jpg",
                "res/splash/screen50.jpg",
                "res/splash/screen75.jpg",
                "res/splash/screen100.jpg",
        };


        for (int i = 0; i < screens.length; i++) {
            this.renderer.renderOneImageScreen(TextureLoader.loadTexture(screens[i]));
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
        }

        Logger.debug("Loading done! i guess :D");
        System.exit(0);

    }
}
