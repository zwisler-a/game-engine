package physics;

import engine.Global;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.LinkedList;

public class PhysicsEngine {

    private Intersect intersect;
    private LinkedList<PhysicsEntity> entities = new LinkedList<>();
    private HashMap<PhysicsEntity, PhysicsEntity> collisionPairs = new HashMap<>();
    private PhysicsConfiguration config;

    public PhysicsEngine(PhysicsConfiguration config) {
        this.config = config;
        this.intersect = new Intersect();
    }

    public PhysicsEngine() {
        this.config = new PhysicsConfiguration();
        this.intersect = new Intersect();
    }

    public void addEntity(PhysicsEntity e) {
        this.entities.add(e);
    }


    public void tick(double dt) {
        for (PhysicsEntity entity : this.entities) {
            this.applyPhysics(entity, dt);
            this.detectCollisions(entity);
            this.resolveCollisionPairs(dt);
        }
    }

    private void resolveCollisionPairs(double dt) {

    }

    private void detectCollisions(PhysicsEntity entity) {
        Vector3f p1 = new Vector3f(entity.getPosition()).add(entity.getHitBox().getOffset());
        for (PhysicsEntity controlEntity : entities) {
            Vector3f p2 = new Vector3f(controlEntity.getPosition()).add(controlEntity.getHitBox().getOffset());
            if (entity == controlEntity) {
                continue;
            }
            if (this.intersect.CubeCube(
                    p1, entity.getHitBox().getSize(),
                    p2, controlEntity.getHitBox().getSize()
            )) {
                if (!collisionPairs.containsKey(entity)) {
                    collisionPairs.put(controlEntity, entity);
                }
            }

        }
        this.calculateCollisions();
    }

    private void calculateCollisions() {
        for (PhysicsEntity e1 : this.collisionPairs.keySet()) {
            PhysicsEntity e2 = this.collisionPairs.get(e1);

            this.calculateVelocities(e1, e2);
            this.correctPositions(e1, e2);

        }
        this.collisionPairs.clear();
    }

    private void correctPositions(PhysicsEntity e1, PhysicsEntity e2) {

    }

    private void calculateVelocities(PhysicsEntity e1, PhysicsEntity e2) {
        // Calc real coordinates
        HitBox hitBoxE1 = e1.getHitBox();
        HitBox hitBoxE2 = e2.getHitBox();
        Vector3f p1 = new Vector3f(e1.getPosition()).add(hitBoxE1.getOffset());
        Vector3f p2 = new Vector3f(p1).add(hitBoxE1.getSize());
        Vector3f p3 = new Vector3f(e2.getPosition()).add(hitBoxE2.getOffset());
        Vector3f p4 = new Vector3f(p3).add(hitBoxE2.getSize());

        // y diff
        float dy = (Math.min(p1.y, p3.y) - Math.max(p2.y, p4.y));

        if (dy < 0f) {
            return;
        }
        if (e1.getVelocity().y < e2.getVelocity().y) {
            dy *= -1;

        }
        if (!e2.isStatic()) {
            e2.getVelocity().y = dy * e1.getElasticity();
            e2.getPosition().y += dy;
        }
        if (!e1.isStatic()) {
            e1.getVelocity().y = dy * e2.getElasticity();
            e1.getPosition().y -= dy;
        }

    }

    private void applyPhysics(PhysicsEntity entity, double dt) {
        entity.getPosition().x += entity.getVelocity().x * dt;
        entity.getPosition().y += entity.getVelocity().y * dt;
        entity.getPosition().z += entity.getVelocity().z * dt;

        entity.getRotation().x += entity.getRotationalVelocity().x * dt;
        entity.getRotation().y += entity.getRotationalVelocity().y * dt;
        entity.getRotation().z += entity.getRotationalVelocity().z * dt;


        entity.getVelocity().y += this.config.gravity * dt;

    }
}
