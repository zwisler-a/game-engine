package examples.example1;

import engine.Game;
import engine.GameSettings;
import engine.entity.Entity;
import engine.entity.LightSource;
import engine.entity.Terrain;
import engine.entity.WaterTile;
import engine.model.TexturedModel;
import engine.model.loaders.ObjLoader;
import engine.model.loaders.TerrainGenerator;
import engine.model.loaders.TextureLoader;
import engine.renderer.StaticRenderer;
import engine.scene.Scene;
import org.joml.Vector3f;

public class MainObject extends Game {

    private Entity arrow;

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

        this.currentScene.add(new WaterTile(new Vector3f(0, -15, 0), 300, this.currentScene));

        //DataDisplay.showWindow();

        LightSource sun = new LightSource(new Vector3f(0, 255, 0), new Vector3f(255, 255, 255), 3);
        this.currentScene.add(sun);

        LightSource ls2 = new LightSource(new Vector3f(0, 1, 20), new Vector3f(255, 0, 255), .2f);
        this.currentScene.add(ls2);
    }

    public void tick(double deltaT) {
        super.tick(deltaT);
        this.renderer.guiRenderer.render(this.currentScene.getGuiElements());
        this.currentScene.getCamera().checkMovementInput(deltaT);
    }
}
