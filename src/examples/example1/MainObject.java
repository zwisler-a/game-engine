package examples.example1;

import engine.Game;
import engine.GameSettings;
import engine.Global;
import engine.entity.*;
import engine.input.KeyboardHandler;
import engine.model.*;
import engine.model.loaders.FontLoader;
import engine.model.loaders.ObjLoader;
import engine.model.loaders.TerrainGenerator;
import engine.model.loaders.TextureLoader;
import engine.model.store.TexturedModelStore;
import engine.renderer.StaticRenderer;
import engine.scene.Scene;
import org.joml.Vector2f;
import org.joml.Vector3f;
import physics.PhysicsEngine;
import physics.PhysicsEntity;

public class MainObject extends Game {

    static Entity arrow;
    public static Text text;
    private LightSource sun;
    private PhysicsEntity cube1;
    private PhysicsEntity plane;
    private Text text2;
    private Text text3;
    private TexturedModel cubi;
    private PhysicsEntity cube2;

    public MainObject(GameSettings gameSettings) {
        super(gameSettings);
    }


    @Override
    public void load() {
        this.currentScene = new Scene();

        Terrain t = TerrainGenerator.generate(
                "res/heightmap.png", 500, 500, 50,
                TextureLoader.loadTexture("res/terrainTexture.png"));
        t.setPosition(new Vector3f(-250, -50, -250));
        this.currentScene.add(t);

        WaterTile water = new WaterTile(new Vector3f(0, -10, 0), 300, this.currentScene);
        this.currentScene.add(water);

        FontAtlas font = FontLoader.loadFromSys("Monaco");
        text = new Text(font);

        GuiElement guiElement = new GuiElement(text.getFbo().getTexture(), new Vector2f(-0.9f, 0.96f), new Vector2f(0.08f, 0.03f));
        this.currentScene.add(guiElement);

        text2 = new Text(font);
        GuiElement guiElement2 = new GuiElement(text2.getFbo().getTexture(), new Vector2f(-0.7f, 0.90f), new Vector2f(0.15f, 0.03f));
        this.currentScene.add(guiElement2);

        text3 = new Text(font);
        GuiElement guiElement3 = new GuiElement(text3.getFbo().getTexture(), new Vector2f(-0.7f, 0.90f), new Vector2f(0.15f, 0.03f));
        this.currentScene.add(guiElement3);


        sun = new LightSource(new Vector3f(0, 255, 0), new Vector3f(255, 255, 255), 3);
        this.currentScene.add(sun);

        LightSource ls2 = new LightSource(new Vector3f(0, 1, 20), new Vector3f(255, 0, 255), .2f);
        this.currentScene.add(ls2);

        // ----------------------------------------------------------------------
        // ----------------------------------------------------------------------
        // ----------------------------------------------------------------------

        Model cubeModel = ObjLoader.loadObjFile("res/cube.obj");
        Texture cubeTexture = TextureLoader.loadTexture("res/cubeTextur.png");
        TexturedModelStore.getInstance().add("test-cube", new TexturedModel(cubeModel, cubeTexture));

        cube1 = new TestCube(this.physicsEngine);
        this.currentScene.add(cube1);

        cube2 = new TestCube(this.physicsEngine);
        this.currentScene.add(cube2);


        plane = new PhysicsEntity(new Vector3f(-5f, 0.5f, -5f), new Vector3f(10, -5, 10));
        plane.setRenderer(StaticRenderer.class);
        plane.setModel(new TexturedModel(
                PlaneModel.load(),
                TextureLoader.loadTexture("res/cubeTextur.png")
        ));
        plane.setStatic(true);
        plane.setScale(5);
        plane.setElasticity(0);
        plane.setPosition(new Vector3f(20, 10, 0));
        this.physicsEngine.addEntity(plane);
        this.currentScene.add(plane);

    }

    public void tick(double deltaT) {
        super.tick(deltaT);
        this.currentScene.getCamera().checkMovementInput(deltaT);

        if (KeyboardHandler.isKeyDown(79)) {
            cube1.setPosition(new Vector3f(20, 50, 0));
            cube1.setVelocity(new Vector3f(0));
            cube2.setPosition(new Vector3f(25, 50, 0));
            cube2.setVelocity(new Vector3f(0));
        }
        if (KeyboardHandler.isKeyDown(80)) {
            cube1.setVelocity(new Vector3f(0, 0.1f, 0));
            cube2.setVelocity(new Vector3f(0, 0.1f, 0));
        }

        text.setText("FPS:" + this.fps);
        text2.setText("PD1: " + Global.data);
        text3.setText("PD2: " + Global.data2);
    }

    class TestCube extends PhysicsEntity {

        public TestCube(PhysicsEngine physicsEngine) {
            super(new Vector3f(-1f, 1f, -1f), new Vector3f(2, -2, 2));
            TexturedModel tm = TexturedModelStore.getInstance().get("test-cube");
            if (tm == null) {
                throw new Error("Textured Model test-cube not loaded!");
            }
            this.setRenderer(StaticRenderer.class);
            this.setModel(tm);
            physicsEngine.addEntity(this);
            this.setPosition(new Vector3f(20, -10, 0));
        }
    }

}
