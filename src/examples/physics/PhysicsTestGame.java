package examples.physics;

import engine.Game;
import engine.GameSettings;
import engine.Global;
import engine.entity.GuiElement;
import engine.entity.LightSource;
import engine.entity.Text;
import engine.input.KeyboardHandler;
import engine.model.*;
import engine.model.loaders.FontLoader;
import engine.model.loaders.ObjLoader;
import engine.model.loaders.TextureLoader;
import engine.model.store.TexturedModelStore;
import engine.renderer.StaticRenderer;
import engine.scene.Scene;
import org.joml.Vector2f;
import org.joml.Vector3f;
import physics.PhysicsEngine;
import physics.PhysicsEntity;
import sound.Player;

import java.io.File;

import static examples.example1.MainObject.text;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;

public class PhysicsTestGame extends Game {

    private TestCube cube1;
    private TestCube cube2;
    private PhysicsEntity plane;

    public PhysicsTestGame(GameSettings gameSettings) {
        super(gameSettings);
    }

    public void load() {

        this.currentScene = new Scene();


        LightSource sun = new LightSource(new Vector3f(0, 255, 0), new Vector3f(255, 255, 255), 3);
        this.currentScene.add(sun);


        Model cubeModel = ObjLoader.loadObjFile("res/cube.obj");
        Texture cubeTexture = TextureLoader.loadTexture("res/cubeTextur.png");
        TexturedModelStore.getInstance().add("test-cube", new TexturedModel(cubeModel, cubeTexture));

        cube1 = new TestCube(this.physicsEngine);
        this.currentScene.add(cube1);

        // cube2 = new TestCube(this.physicsEngine);
        // this.currentScene.add(cube2);


        plane = new PhysicsEntity(new Vector3f(-5f, 0.5f, -5f), new Vector3f(10, -5, 10));
        plane.setRenderer(StaticRenderer.class);
        plane.setModel(new TexturedModel(
                PlaneModel.load(),
                TextureLoader.loadTexture("res/cubeTextur.png")
        ));
        plane.setStatic(true);
        plane.setScale(5);
        plane.setElasticity(1);
        plane.setPosition(new Vector3f(0, 0, 0));
        this.physicsEngine.addEntity(plane);
        this.currentScene.add(plane);


        FontAtlas font = FontLoader.loadFromSys("Monaco");
        text = new Text(font);

        GuiElement guiElement = new GuiElement(text.getFbo().getTexture(), new Vector2f(-0.8f, 0.92f), new Vector2f(0.16f, 0.06f));
        this.currentScene.add(guiElement);

    }

    public void tick(double deltaT) throws Exception {
        super.tick(deltaT);
        this.currentScene.getCamera().checkMovementInput(deltaT);

        text.setText("PD: " + Global.data);

        //Physiscs controlling

        if (KeyboardHandler.isKeyDown(GLFW_KEY_O)) {
            cube1.setPosition(new Vector3f(2.5f, 50, 0));
            cube1.setVelocity(new Vector3f(0));
            // cube2.setPosition(new Vector3f(-2.5f, 50, 0));
            // cube2.setVelocity(new Vector3f(0));
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_P)) {
            cube1.setVelocity(new Vector3f(0, 0.5f, 0));
            cube2.setVelocity(new Vector3f(0, 0.5f, 0));
        }
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


