package physics;

import engine.entity.Entity;
import engine.entity.Simulated;
import org.joml.Vector3f;

public class PhysicsEntity extends Entity implements Simulated {

    private Vector3f velocity;
    private Vector3f rotationalVelocity;
    private float mass = 1;
    private float bouncyness = 2;

    private boolean isStatic = false;

    private static float GRAVITY = -0.1f;

    private Vector3f hitboxOffset;
    private Vector3f hitboxSize;

    public PhysicsEntity(Vector3f hitboxOffset, Vector3f hitboxSize) {
        this.hitboxOffset = hitboxOffset;
        this.hitboxSize = hitboxSize;
        this.velocity = new Vector3f();
        this.rotationalVelocity = new Vector3f(0);
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getRotationalVelocity() {
        return rotationalVelocity;
    }

    public void setRotationalVelocity(Vector3f rotationalVelocity) {
        this.rotationalVelocity = rotationalVelocity;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getBouncyness() {
        return bouncyness;
    }

    public void setBouncyness(float bouncyness) {
        this.bouncyness = bouncyness;
    }

    @Override
    public void simulate(double dt) {
        if (this.isStatic) {
            return;
        }
        this.getPosition().x += this.velocity.x * dt;
        this.getPosition().y += this.velocity.y * dt;
        this.getPosition().z += this.velocity.z * dt;

        this.getRotation().x += this.rotationalVelocity.x * dt;
        this.getRotation().y += this.rotationalVelocity.y * dt;
        this.getRotation().z += this.rotationalVelocity.z * dt;


        this.velocity.y += GRAVITY * dt * 0.05f;

        if (this.getPosition().y < 0) {
            this.getPosition().y = 20;
            this.velocity = new Vector3f(0);
        }

    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public Vector3f getHitboxOffset() {
        return hitboxOffset;
    }

    public Vector3f getHitboxSize() {
        return hitboxSize;
    }
}
