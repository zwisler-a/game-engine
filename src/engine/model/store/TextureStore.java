package engine.model.store;

import engine.model.Texture;

public class TextureStore extends Store<Texture>{
    private static TextureStore ourInstance = new TextureStore();

    public static TextureStore getInstance() {
        return ourInstance;
    }

    private TextureStore() {
    }
}
