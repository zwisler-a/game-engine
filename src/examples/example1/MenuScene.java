package examples.example1;

import engine.entity.GuiElement;
import engine.scene.Scene;

import java.util.LinkedList;

public class MenuScene extends Scene {

    private LinkedList<GuiElement> _guiElements = new LinkedList<>();
    private Scene retrievableScene;

    public Scene loadMenu(Scene s) {
        this.entities = s.getAllEntities();
        this.camera = s.getCamera();
        this.lightSources = s.getLightSources();
        this.guiElements.clear();
        this.guiElements.addAll(this._guiElements);
        this.retrievableScene = s;
        return this;
    }

    public Scene restore() {
        Scene tmp = this.retrievableScene;
        this.retrievableScene = null;
        return tmp;
    }

    public boolean isOpen() {
        return this.retrievableScene != null;
    }

    public void addGuiElement(GuiElement g) {
        this._guiElements.add(g);
    }

    public void tick(double dt) {
        // dont call super to avoid simulating!
    }
}
