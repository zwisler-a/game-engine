package engine.scene;

import common.Logger.Logger;
import engine.entity.*;
import engine.model.Texture;
import engine.model.TexturedModel;
import engine.model.loaders.TextureLoader;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.LinkedList;

public class Scene {

    protected HashMap<Class, HashMap<TexturedModel, LinkedList<Entity>>> entities = new HashMap<>();
    protected Camera camera = new Camera(new Vector3f(), 0, 0, 0);
    protected LinkedList<LightSource> lightSources = new LinkedList<>();
    protected LinkedList<Simulated> simulatedEntities = new LinkedList<>();
    protected LinkedList<GuiElement> guiElements = new LinkedList<>();
    protected Texture skyboxTexture;

    public Scene() {
        this.skyboxTexture = TextureLoader.loadTexture("res/skybox1.png");
    }

    public Scene(Texture skyboxTexture) {
        this.skyboxTexture = skyboxTexture;
    }


    public void add(Entity e) {
        if (e.getRenderer() == null) {
            Logger.warning("No renderer set for entity " + e.toString());
        }
        HashMap<TexturedModel, LinkedList<Entity>> rendererMap;
        if (this.entities.containsKey(e.getRenderer())) {
            rendererMap = this.entities.get(e.getRenderer());
        } else {
            rendererMap = new HashMap<>();
            this.entities.put(e.getRenderer(), rendererMap);
        }
        this.putInRendererList(rendererMap, e);
    }

    public void remove(Entity e) {
        this.entities.get(e.getRenderer()).get(e.getModel()).remove(e);
    }

    public void add(LightSource ls) {
        this.lightSources.add(ls);
    }

    private HashMap putInRendererList(HashMap<TexturedModel, LinkedList<Entity>> list, Entity e) {
        if (list.containsKey(e.getModel())) {
            list.get(e.getModel()).add(e);
        } else {
            LinkedList<Entity> rendererList = new LinkedList<>();
            rendererList.add(e);
            list.put(e.getModel(), rendererList);
        }
        return list;
    }

    public HashMap<TexturedModel, LinkedList<Entity>> getEntities(Class renderer) {
        return this.entities.get(renderer);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public LinkedList<LightSource> getLightSources() {
        return lightSources;
    }

    public void registerSimulation(Simulated sim) {
        this.simulatedEntities.add(sim);
    }

    public void tick(double dt) {
        for (Simulated sim : this.simulatedEntities) {
            sim.simulate(dt);
        }
    }

    public LinkedList<GuiElement> getGuiElements() {
        return guiElements;
    }

    public void add(GuiElement guiElement) {
        this.guiElements.add(guiElement);
    }

    public Texture getSkyboxTexture() {
        return skyboxTexture;
    }

    public HashMap<Class, HashMap<TexturedModel, LinkedList<Entity>>> getAllEntities() {
        return entities;
    }
}
