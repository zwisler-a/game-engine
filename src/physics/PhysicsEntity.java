package physics;

import engine.entity.Entity;
import org.joml.Vector3f;

public class PhysicsEntity extends Entity {

    private HitBox hitBox;
    private Vector3f velocity;
    private Vector3f rotationalVelocity;
    private float mass = 1;
    private float elasticity = 1;
    private boolean isStatic = false;

    public PhysicsEntity(Vector3f hitboxOffset, Vector3f hitboxSize) {
        this.hitBox = new HitBox(hitboxOffset,hitboxSize);
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

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public float getElasticity() {
        return elasticity;
    }

    public void setElasticity(float elasticity) {
        this.elasticity = elasticity;
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
    }
}
