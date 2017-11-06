package examples.example1;

import engine.entity.Entity;
import engine.entity.Simulated;
import engine.model.Texture;
import engine.model.TexturedModel;
import engine.model.loaders.ObjLoader;
import engine.model.loaders.TextureLoader;
import engine.renderer.StaticRenderer;

public class Dragon extends Entity implements Simulated{

    public Dragon(){
        Texture texture = TextureLoader.loadTexture("res/cubeTextur.png");
        this.setModel(new TexturedModel(ObjLoader.loadObjFile("res/dragon.obj"), texture));
        this.setRenderer(StaticRenderer.class);
    }

    @Override
    public void simulate(double dt) {
        this.getRotation().y++;
    }
}
