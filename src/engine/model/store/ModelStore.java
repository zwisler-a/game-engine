package engine.model.store;

import engine.model.Model;

public class ModelStore extends Store<Model> {
    private static ModelStore ourInstance = new ModelStore();

    public static ModelStore getInstance() {
        return ourInstance;
    }

    private ModelStore() {
    }
}
