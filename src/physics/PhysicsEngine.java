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
    }

    private void resolveCollisionPairs(double dt) {
        for (PhysicsEntity e1 : this.collisionPairs.keySet()) {
            PhysicsEntity e2 = this.collisionPairs.get(e1);

            this.calculateVelocities(e1, e2);

        }
        this.collisionPairs.clear();
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
        float dx = (Math.min(p1.y, p3.y) - Math.max(p2.y, p4.y));
        float dz = (Math.min(p1.y, p3.y) - Math.max(p2.y, p4.y));

        if (e1.getVelocity().y < e2.getVelocity().y) {
            dy *= -1;

        }

        float threshold = 0f;

        Global.data = Float.toString(dy);

        if (!e2.isStatic()) {
            if (dy * e1.getElasticity() > threshold) {
                e2.getVelocity().y = dy * e1.getElasticity() * 0.5f;
            } else {
                e2.getVelocity().y = 0;
            }
            e2.getPosition().y += dy + 0.1f;
        }
        if (!e1.isStatic()) {
            if (dy * e2.getElasticity() > threshold) {
                e1.getVelocity().y = dy * e1.getElasticity() * 0.5f;
            } else {
                e1.getVelocity().y = 0;
            }
            e1.getPosition().y -= dy + 0.1f;
        }

    }

    private void applyPhysics(PhysicsEntity entity, double dt) {
        entity.getPosition().x += entity.getVelocity().x * dt;
        entity.getPosition().y += entity.getVelocity().y * dt;
        entity.getPosition().z += entity.getVelocity().z * dt;

        entity.getRotation().x += entity.getRotationalVelocity().x * dt;
        entity.getRotation().y += entity.getRotationalVelocity().y * dt;
        entity.getRotation().z += entity.getRotationalVelocity().z * dt;

        if (!entity.isStatic()) {
            entity.getVelocity().y += this.config.gravity * dt;
        }

    }
}
