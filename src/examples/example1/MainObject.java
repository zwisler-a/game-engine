package examples.example1;

import engine.Game;
import engine.GameSettings;
import engine.entity.*;
import engine.input.KeyboardHandler;
import engine.model.FontAtlas;
import engine.model.TexturedModel;
import engine.model.animation.Animation;
import engine.model.collada.ColladaLoader;
import engine.model.loaders.FontLoader;
import engine.model.loaders.TerrainGenerator;
import engine.model.loaders.TextureLoader;
import engine.renderer.AnimatedModelRenderer;
import engine.scene.Scene;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;

public class MainObject extends Game {

    static Entity arrow;
    public static Text text;
    private LightSource sun;
    private Text text2;
    private Text text3;
    private TexturedModel cubi;
    private LightSource ls2;

    public MainObject(GameSettings gameSettings) {
        super(gameSettings);
    }


    @Override
    public void load() {
        this.currentScene = new Scene();

        // Splashscreen tests


        Terrain t = TerrainGenerator.generate(
                "res/heightmap.png", 500, 500, 50,
                TextureLoader.loadTexture("res/terrainTexture.png"));
        t.setPosition(new Vector3f(-250, -50, -250));
        this.currentScene.add(t);


        WaterTile water = new WaterTile(new Vector3f(0, -10, 0), 300, this.currentScene);
        this.currentScene.add(water);


        new Player(this.currentScene, true, -5);

        FontAtlas font = FontLoader.loadFromSys("Monaco");
        text = new Text(font);

        GuiElement guiElement = new GuiElement(text.getFbo().getTexture(), new Vector2f(-0.9f, 0.96f), new Vector2f(0.08f, 0.03f));
        this.currentScene.add(guiElement);


        sun = new LightSource(new Vector3f(0, 255, 0), new Vector3f(255, 255, 255), 3);
        this.currentScene.add(sun);


        TexturedModel cowBoyModel = new TexturedModel(
                ColladaLoader.loadColladaFileAnimated("res/cowboy.dae"),
                TextureLoader.loadTexture("res/cowboy.png")
        );
        AnimatedEntity cowBoy = new AnimatedEntity();
        cowBoy.setModel(cowBoyModel);
        cowBoy.setRenderer(AnimatedModelRenderer.class);
        Animation walkingAnimation = ColladaLoader.loadAnimation("res/cowboy.dae", cowBoy);
        cowBoy.getAnimator().doAnimation(walkingAnimation);
        this.currentScene.registerSimulation(cowBoy);
        this.currentScene.add(cowBoy);


        // ----------------------------------------------------------------------
        // ----------------------------------------------------------------------
        // ----------------------------------------------------------------------

    }

    public void tick(double deltaT) throws Exception {
        super.tick(deltaT);
        //this.currentScene.getCamera().checkMovementInput(deltaT);

        if (KeyboardHandler.isKeyDown(GLFW_KEY_O)) {
            this.sun.setIntensity(this.sun.getIntensity() + .1f);
        }
        if (KeyboardHandler.isKeyDown(GLFW_KEY_L)) {
            this.sun.setIntensity(this.sun.getIntensity() - .1f);
        }

        text.setText("FPS:" + this.fps);
    }
}
