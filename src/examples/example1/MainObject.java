package examples.example1;

import engine.Game;
import engine.GameSettings;
import engine.entity.*;
import engine.input.KeyboardHandler;
import engine.model.Model;
import engine.model.PlaneModel;
import engine.model.Texture;
import engine.model.TexturedModel;
import engine.model.loaders.FontLoader;
import engine.model.loaders.ObjLoader;
import engine.model.loaders.TerrainGenerator;
import engine.model.loaders.TextureLoader;
import engine.renderer.StaticRenderer;
import engine.scene.Scene;
import org.joml.Vector2f;
import org.joml.Vector3f;
import physics.PhysicsEntity;

public class MainObject extends Game {

    static Entity arrow;
    public static Text text;
    private LightSource sun;
    private PhysicsEntity cube1;
    private PhysicsEntity plane;

    public MainObject(GameSettings gameSettings) {
        super(gameSettings);
    }


    @Override
    public void load() {
        this.currentScene = new Scene();
        Dragon d = new Dragon();
        //this.currentScene.add(d);
        this.currentScene.registerSimulation(d);

        arrow = new Entity();
        arrow.setRenderer(StaticRenderer.class);
        arrow.setModel(new TexturedModel(
                ObjLoader.loadObjFile("res/arrow.obj"),
                TextureLoader.loadTexture("res/cubeTextur.png")
        ));
        arrow.setPosition(new Vector3f(0, 0, 20));
        //this.currentScene.add(arrow);

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

        sun = new LightSource(new Vector3f(0, 255, 0), new Vector3f(255, 255, 255), 3);
        this.currentScene.add(sun);
        sun.enableShadow();

        GuiElement shadowMapGui = new GuiElement(sun.getFbo().getDepthTexture(), new Vector2f(-0.5f, -0.5f), new Vector2f(0.5f, 0.5f));
        //this.currentScene.add(shadowMapGui);

        LightSource ls2 = new LightSource(new Vector3f(0, 1, 20), new Vector3f(255, 0, 255), .2f);
        this.currentScene.add(ls2);

        // ----------------------------------------------------------------------
        // ----------------------------------------------------------------------
        // ----------------------------------------------------------------------

        Model cubeModel = ObjLoader.loadObjFile("res/cube.obj");
        Texture cubeTexture = TextureLoader.loadTexture("res/cubeTextur.png");
        TexturedModel cubi = new TexturedModel(cubeModel, cubeTexture);

        cube1 = new PhysicsEntity(new Vector3f(-1f, 1f, -1f), new Vector3f(2, -2, 2));
        cube1.setRenderer(StaticRenderer.class);
        cube1.setModel(cubi);
        cube1.setPosition(new Vector3f(20, 10, 0));
        this.currentScene.registerSimulation(cube1);
        this.physicsEngine.addEntity(cube1);
        this.currentScene.add(cube1);


        plane = new PhysicsEntity(new Vector3f(-5f, 0.5f, -5f), new Vector3f(10, -5, 10));
        plane.setRenderer(StaticRenderer.class);
        plane.setModel(new TexturedModel(
                PlaneModel.load(),
                TextureLoader.loadTexture("res/cubeTextur.png")
        ));
        plane.setStatic(true);
        plane.setScale(5);
        plane.setPosition(new Vector3f(20, 10, 0));
        this.currentScene.registerSimulation(plane);
        this.physicsEngine.addEntity(plane);
        this.currentScene.add(plane);


    }

    public void tick(double deltaT) {
        super.tick(deltaT);
        this.currentScene.getCamera().checkMovementInput(deltaT);

        if (KeyboardHandler.isKeyDown(79)) {
            cube1.setPosition(new Vector3f(20, 50, 0));
            cube1.setVelocity(new Vector3f(0));
        }
        if (KeyboardHandler.isKeyDown(80)) {
            cube1.setVelocity(new Vector3f(0, -0.1f, 0));
        }

        text.setText("FPS:" + this.fps);
    }
}
