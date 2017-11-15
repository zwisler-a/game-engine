package engine.model.store;

import java.util.HashMap;

public abstract class Store<T> {
    private HashMap<String,T> storeHashMap = new HashMap<>();

    public void add(String identifier,T object) {
        this.storeHashMap.put(identifier, object);
    }

    public T get(String identifier) {
        return this.storeHashMap.get(identifier);
    }

}
