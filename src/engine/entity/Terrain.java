package engine.entity;

import engine.model.Model;
import engine.model.Texture;
import engine.model.TexturedModel;
import engine.renderer.StaticRenderer;
import org.joml.Vector3f;

public class Terrain extends Entity {

    public Terrain(Model model, Texture texture, Vector3f position) {
        this.setModel(new TexturedModel(model, texture));
        this.setPosition(position);
        this.setRenderer(StaticRenderer.class);
    }
}
