package examples.example1;

import engine.Game;
import engine.GameSettings;
import engine.entity.*;
import engine.model.TexturedModel;
import engine.model.loaders.FontLoader;
import engine.model.loaders.ObjLoader;
import engine.model.loaders.TerrainGenerator;
import engine.model.loaders.TextureLoader;
import engine.renderer.StaticRenderer;
import engine.scene.Scene;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class MainObject extends Game {

    static Entity arrow;
    public static Text text;

    public MainObject(GameSettings gameSettings) {
        super(gameSettings);
    }


    @Override
    public void load() {


        this.currentScene = new Scene();
        Dragon d = new Dragon();
        d.setPosition(new Vector3f(0, 0, 0));
        this.currentScene.add(d);
        this.currentScene.registerSimulation(d);


        arrow = new Entity();
        arrow.setRenderer(StaticRenderer.class);
        arrow.setModel(new TexturedModel(
                ObjLoader.loadObjFile("res/arrow.obj"),
                TextureLoader.loadTexture("res/cubeTextur.png")
        ));
        arrow.setPosition(new Vector3f(0, 0, 20));
        this.currentScene.add(arrow);

        Terrain t = TerrainGenerator.generate(
                "res/heightmap.png", 500, 500, 50,
                TextureLoader.loadTexture("res/terrainTexture.png"));
        t.setPosition(new Vector3f(-250, -50, -250));
        this.currentScene.add(t);

        WaterTile water = new WaterTile(new Vector3f(0, -10, 0), 300, this.currentScene);
        this.currentScene.add(water);

        text = new Text(FontLoader.loadFromSys("Monaco"));

        GuiElement guiElement = new GuiElement(text.getFbo().getTexture(), new Vector2f(-0.9f, 0.96f), new Vector2f(0.08f, 0.03f));
        this.currentScene.add(guiElement);

        LightSource sun = new LightSource(new Vector3f(0, 255, 0), new Vector3f(255, 255, 255), 3);
        this.currentScene.add(sun);

        LightSource ls2 = new LightSource(new Vector3f(0, 1, 20), new Vector3f(255, 0, 255), .2f);
        this.currentScene.add(ls2);
    }

    public void tick(double deltaT) {
        super.tick(deltaT);
        this.currentScene.getCamera().checkMovementInput(deltaT);
    }
}
