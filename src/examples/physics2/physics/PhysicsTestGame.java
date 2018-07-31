package examples.physics2.physics;

import engine.Game;
import engine.GameSettings;
import engine.entity.LightSource;
import engine.entity.Simulated;
import engine.input.KeyboardHandler;
import engine.model.Model;
import engine.model.Texture;
import engine.model.TexturedModel;
import engine.model.loaders.ObjLoader;
import engine.model.loaders.TextureLoader;
import engine.model.store.TexturedModelStore;
import engine.renderer.StaticRenderer;
import engine.scene.Scene;
import org.joml.Vector3f;
import physics.PhysicsEngine;
import physics.shapes.Cube;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class PhysicsTestGame extends Game {

    private TestCube cube[];
    private DisplayCube contactCube;

    public PhysicsTestGame(GameSettings gameSettings) {
        super(gameSettings);
    }

    public void load() {

        this.currentScene = new Scene();


        LightSource sun = new LightSource(new Vector3f(0, 255, 0), new Vector3f(255, 255, 255), 3);
        this.currentScene.add(sun);

        new Player(this.currentScene, true, 0);

        Model cubeModel = ObjLoader.loadObjFile("res/cube.obj");
        Texture cubeTexture = TextureLoader.loadTexture("res/cubeTextur.png");
        TexturedModelStore.getInstance().add("test-cube", new TexturedModel(cubeModel, cubeTexture));

        cube = new TestCube[3];
        for (int i = 0; i < cube.length; i++) {
            cube[i] = new TestCube(this.physicsEngine, this.currentScene);
        }
        cube[0].isStatic(true);
        cube[0].setMass(5);
        cube[0].setScale(5);
        cube[0].setPosition(new Vector3f(0));

        cube[2].isStatic(true);
        cube[2].setMass(5000000);
        cube[2].setPosition(new Vector3f(-7, 12, 0));


        cube[1].setRotation(new Vector3f(0, 0, 0));
        cube[1].setMass(1);
        cube[1].setPosition(new Vector3f(0, 10, 0));


    }

    private ArrayList<DisplayCube> currCubes = new ArrayList<>();

    public void tick(double deltaT) throws Exception {
        super.tick(deltaT);
        //this.currentScene.getCamera().checkMovementInput(deltaT);


        if (PhysicsEngine.wtf != null) {
            for (DisplayCube cub : this.currCubes) {
                this.currentScene.remove(cub);
            }
            this.currCubes.clear();
            for (int i = 0; i < PhysicsEngine.wtf.size(); i++) {
                Vector3f c = PhysicsEngine.wtf.get(i);
                DisplayCube cub = new DisplayCube(c, 0.1f);
                if (PhysicsEngine.wtfC != null) {
                    if (PhysicsEngine.wtfC.size() > i) {
                        cub.setColor(PhysicsEngine.wtfC.get(i));
                    }
                }
                this.currentScene.add(cub);
                this.currCubes.add(cub);
            }
        }

        //Physiscs controlling

        if (KeyboardHandler.isKeyDown(GLFW_KEY_O)) {
            cube[1].getRotation().x = 0;
            cube[1].getRotation().y = 0;
            cube[0].setPosition(new Vector3f(0));
            cube[1].setPosition(new Vector3f(0, 25, 0));
            cube[1].setAngularVelocity(new Vector3f(0, 0, 0));
            cube[1].setLinearMomentum(new Vector3f());
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_P)) {
            cube[0].setPosition(new Vector3f(0));
            cube[1].setPosition(new Vector3f(0, 15, 0));
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_X)) {
            cube[1].getPosition().x += 0.1;
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_T)) {
            cube[1].getRotation().x = 45;
            cube[1].getRotation().y = 45;
            cube[1].getRotation().z = 0;
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_F)) {
            cube[1].setPosition(new Vector3f(8, 10, 0));
            cube[1].setLinearMomentum(new Vector3f(-0.2f, 0, 0));
            cube[1].setAngularMomentum(new Vector3f());
            cube[1].setRotation(new Vector3f(45, 0, 0));
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_E)) {
            cube[1].setPosition(new Vector3f(-8, 10, 0));
            cube[1].setLinearMomentum(new Vector3f(0.2f, 0, 0));
            cube[1].setAngularMomentum(new Vector3f());
            cube[1].setRotation(new Vector3f(45, 0, 45));
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_G)) {
            cube[1].setPosition(new Vector3f(0, 10, 8));
            cube[1].setLinearMomentum(new Vector3f(0, 0, -0.2f));
            cube[1].setAngularMomentum(new Vector3f());
            cube[1].setRotation(new Vector3f(45, 0, 45));
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_R)) {
            cube[1].setPosition(new Vector3f(0, 10, -8));
            cube[1].setLinearMomentum(new Vector3f(0, 0, 0.2f));
            cube[1].setAngularMomentum(new Vector3f());
            cube[1].setRotation(new Vector3f(45, 0, 45));
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_C)) {
            cube[1].setLinearMomentum(new Vector3f());
            cube[1].setAngularMomentum(new Vector3f());
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_V)) {
            cube[1].setLinearMomentum(new Vector3f(0,-0.1f,0));
            cube[1].setAngularMomentum(new Vector3f());
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_UP)) {
            cube[1].getPosition().y += 0.1;
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_DOWN)) {
            cube[1].getPosition().y -= 0.1;
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_LEFT)) {
            cube[1].getRotation().y -= 0.5;
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_RIGHT)) {
            cube[1].getRotation().z += 0.5;
        }
    }

    class TestCube extends Cube implements Simulated {


        private DisplayCube[] displayCube;

        public TestCube(PhysicsEngine physicsEngine, Scene scene) {
            super(new Vector3f(2, 2, 2));
            TexturedModel tm = TexturedModelStore.getInstance().get("test-cube");
            if (tm == null) {
                throw new Error("Textured Model test-cube not loaded!");
            }
            this.setRenderer(StaticRenderer.class);
            this.setModel(tm);
            physicsEngine.addEntity(this);
            this.setPosition(new Vector3f(20, -10, 0));
            scene.add(this);

            this.setMass(1);

            this.displayCube = new DisplayCube[8];
            for (int i = 0; i < this.displayCube.length; i++) {
                this.displayCube[i] = new DisplayCube();
                scene.add(this.displayCube[i]);
            }

            scene.registerSimulation(this);
        }

        public void setScale(float scale) {
            super.setScale(scale);
            this.setSize(new Vector3f(2 * scale, 2 * scale, 2 * scale));
        }

        @Override
        public void simulate(double dt) {
            for (int i = 0; i < this.displayCube.length; i++) {
                this.displayCube[i].setPosition(this.getPoints()[i]);
            }
        }
    }


}


