package examples.example1;

import engine.entity.AnimatedEntity;
import engine.entity.Camera;
import engine.input.KeyboardHandler;
import engine.input.MouseHandler;
import engine.model.TexturedModel;
import engine.model.animation.Animation;
import engine.model.collada.ColladaLoader;
import engine.model.loaders.TextureLoader;
import engine.renderer.AnimatedModelRenderer;
import engine.scene.Scene;
import org.joml.Vector2d;
import org.joml.Vector3f;

public class Player extends AnimatedEntity {

    private final boolean captureCamera;
    private final float distance;
    private Animation walkinAnimation;
    private Camera camera;
    private double speed = 0.5;

    public Player(Scene scene, boolean captureCamera, float distance) {
        TexturedModel model = new TexturedModel(
                ColladaLoader.loadColladaFileAnimated("res/cowboy.dae"),
                TextureLoader.loadTexture("res/cowboy.png")
        );
        this.setModel(model);
        this.setRenderer(AnimatedModelRenderer.class);
        this.walkinAnimation = ColladaLoader.loadAnimation("res/cowboy.dae", this);
        scene.registerSimulation(this);
        scene.add(this);
        if (captureCamera) {
            this.camera = scene.getCamera();
            this.camera.setRotY(180);
        }
        this.distance = distance;
        this.captureCamera = captureCamera;
    }

    private void walk(boolean should) {
        if (should && !this.animator.isAnimatring()) {
            this.animator.doAnimation(walkinAnimation);
        } else if (!should && this.animator.isAnimatring()) {
            this.animator.doAnimation(null);
        }
    }

    @Override
    public void simulate(double dt) {
        super.simulate(dt);

        if (KeyboardHandler.isKeyDown(87)) {
            this.getPosition().x += (float) (Math.sin((double) (this.getRotation().y) * 3.141592653589793D / 180.0D) * (double) this.speed * dt);
            this.getPosition().z += (float) (Math.cos((double) (this.getRotation().y) * 3.141592653589793D / 180.0D) * (double) this.speed * dt);
        }

        if (KeyboardHandler.isKeyDown(83)) {
            this.getPosition().x -= (float) (Math.sin((double) (this.getRotation().y) * 3.141592653589793D / 180.0D) * (double) this.speed * dt);
            this.getPosition().z -= (float) (Math.cos((double) (this.getRotation().y) * 3.141592653589793D / 180.0D) * (double) this.speed * dt);
        }

        if (KeyboardHandler.isKeyDown(65)) {
            this.getPosition().x -= (float) (Math.sin((double) (this.getRotation().y - 90.0F) * 3.141592653589793D / 180.0D) * (double) this.speed * dt);
            this.getPosition().z -= (float) (Math.cos((double) (this.getRotation().y - 90.0F) * 3.141592653589793D / 180.0D) * (double) this.speed * dt);
        }

        if (KeyboardHandler.isKeyDown(68)) {
            this.getPosition().x -= (float) (Math.sin((double) (this.getRotation().y + 90.0F) * 3.141592653589793D / 180.0D) * (double) this.speed * dt);
            this.getPosition().z -= (float) (Math.cos((double) (this.getRotation().y + 90.0F) * 3.141592653589793D / 180.0D) * (double) this.speed * dt);
        }

        if (KeyboardHandler.isKeyDown(87) || KeyboardHandler.isKeyDown(83) || KeyboardHandler.isKeyDown(65) || KeyboardHandler.isKeyDown(68)) {
            this.walk(true);
        } else {
            this.walk(false);
        }

        if (KeyboardHandler.isKeyDown(32)) {
            this.getPosition().y += this.speed * dt;
        }

        if (KeyboardHandler.isKeyDown(341)) {
            this.getPosition().y -= this.speed * dt;
        }

        Vector2d deltas = MouseHandler.get();
        float mouseDX = (float) deltas.y * -0.128F;
        float mouseDY = (float) deltas.x * -0.128F;
        if (this.camera.getRotX() - mouseDX >= 360.0F) {
            this.camera.setRotX(this.camera.getRotX() - mouseDX - 360.0F);
        } else if (this.camera.getRotX() - mouseDX < 0.0F) {
            this.camera.setRotX(this.camera.getRotX() - mouseDX + 360.0F);
        } else {
            this.camera.setRotX(this.camera.getRotX() - mouseDX);
        }

        this.getRotation().y += mouseDY;
        this.camera.setRotY(180 - this.getRotation().y);

        this.camera.setPosition(new Vector3f(this.getPosition()).add(
                (float) Math.sin(Math.toRadians(this.getRotation().y)) * this.distance,
                -this.distance + 5,
                (float) Math.cos(Math.toRadians(this.getRotation().y)) * this.distance));

    }
}
