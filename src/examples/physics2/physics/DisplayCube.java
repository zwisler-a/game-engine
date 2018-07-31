package examples.physics2.physics;

import engine.entity.Entity;
import engine.model.Model;
import engine.model.Texture;
import engine.model.TexturedModel;
import engine.model.loaders.ObjLoader;
import engine.model.loaders.TextureLoader;
import engine.model.store.TexturedModelStore;
import engine.renderer.StaticRenderer;
import org.joml.Vector3f;

class DisplayCube extends Entity {

    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;
    private static TexturedModel rCube;
    private static TexturedModel gCube;
    private static TexturedModel bCube;

    private static void init() {
        Model cubeModel = ObjLoader.loadObjFile("res/cube.obj");
        Texture rTexture = TextureLoader.loadTexture("res/rgb/r.png");
        Texture gTexture = TextureLoader.loadTexture("res/rgb/g.png");
        Texture bTexture = TextureLoader.loadTexture("res/rgb/b.png");
        rCube = new TexturedModel(cubeModel, rTexture);
        gCube = new TexturedModel(cubeModel, gTexture);
        bCube = new TexturedModel(cubeModel, bTexture);
    }

    public DisplayCube() {
        this.setScale(0.01f);
        if (rCube == null) {
            init();
        }
        this.setRenderer(StaticRenderer.class);
        this.setModel(gCube);
    }

    public DisplayCube(Vector3f pos, float size) {
        this();
        this.setPosition(pos);
        this.setScale(size);
    }

    public void setColor(int color) {
        if (color == RED) {
            this.setModel(rCube);
        }
        if (color == GREEN) {
            this.setModel(gCube);
        }
        if (color == BLUE) {
            this.setModel(bCube);
        }
    }
}
