package engine.model.store;

import engine.model.TexturedModel;

public class TexturedModelStore extends Store<TexturedModel> {
    private static TexturedModelStore ourInstance = new TexturedModelStore();

    public static TexturedModelStore getInstance() {
        return ourInstance;
    }

    private TexturedModelStore() {
    }
}
