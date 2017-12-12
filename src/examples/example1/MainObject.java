package examples.example1;

import common.Logger.Logger;
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
import org.lwjgl.glfw.GLFW;
import physics.PhysicsEngine;
import physics.PhysicsEntity;

import static org.lwjgl.glfw.GLFW.*;

public class MainObject extends Game {

    public static Text text;
    private LightSource sun;
    private Text text2;
    private Text text3;
    private MenuScene menu;

    public MainObject(GameSettings gameSettings) {
        super(gameSettings);
    }


    @Override
    public void load() {
        this.currentScene = new Scene();


        this.menu = new MenuScene();
        GuiElement menuPoint = new GuiElement(
                TextureLoader.loadTexture("res/close.png"),
                new Vector2f(0, 0),
                new Vector2f(0.5f, 0.1f), true);
        this.menu.addGuiElement(menuPoint);


        Terrain t = TerrainGenerator.generate(
                "res/heightmap.png", 500, 500, 50,
                TextureLoader.loadTexture("res/terrainTexture.png"));
        t.setPosition(new Vector3f(-250, -50, -250));
        this.currentScene.add(t);

        TexturedModel aliceModel = new TexturedModel(
                ObjLoader.loadObjFile("res/aliceV1.obj"),
                TextureLoader.loadTexture("res/aliceV1.png")
        );
        Entity e = new Entity();
        e.setRenderer(StaticRenderer.class);
        e.setModel(aliceModel);
        e.setPosition(new Vector3f(0, 0, 0));
        this.currentScene.add(e);

        WaterTile water = new WaterTile(new Vector3f(0, -10, 0), 300, this.currentScene);
        this.currentScene.add(water);

        /*
        GuiElement guiElementWater = new GuiElement(water.getRefractionFbo().getDepthTexture(),
                new Vector2f(-0.5f, -0.5f),
                new Vector2f(0.25f, 0.25f));
        this.currentScene.add(guiElementWater);
        */

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

    }

    public void tick(double deltaT) throws Exception {
        super.tick(deltaT);
        if (!this.menu.isOpen()) {
            this.currentScene.getCamera().checkMovementInput(deltaT);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE) && !this.menu.isOpen()) {
            this.currentScene = this.menu.loadMenu(this.currentScene);
        } else if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE) && this.menu.isOpen()) {
            this.currentScene = this.menu.restore();
        }

        text.setText("FPS:" + this.fps);
        text2.setText("PD1: " + Global.data);
        text3.setText("PD2: " + Global.data2);
    }
}
