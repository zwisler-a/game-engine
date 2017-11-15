package physics;

import common.Logger;
import engine.Global;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.LinkedList;

public class PhysicsEngine {

    private LinkedList<PhysicsEntity> entities = new LinkedList<>();
    private HashMap<PhysicsEntity, PhysicsEntity> collisionPairs = new HashMap<>();

    public void addEntity(PhysicsEntity e) {
        this.entities.add(e);
    }

    public void detectCollisions() {
        for (PhysicsEntity entity : entities) {
            Vector3f p1 = new Vector3f(entity.getPosition()).add(entity.getHitboxOffset());
            for (PhysicsEntity controlEntity : entities) {
                Vector3f p2 = new Vector3f(controlEntity.getPosition()).add(controlEntity.getHitboxOffset());
                if (entity == controlEntity) {
                    continue;
                }
                if (intersect(
                        p1, entity.getHitboxSize(),
                        p2, controlEntity.getHitboxSize()
                ) || true) {
                    if (!collisionPairs.containsKey(entity)) {
                        collisionPairs.put(controlEntity, entity);
                    }
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
        Vector3f p1 = new Vector3f(e1.getPosition()).add(e1.getHitboxOffset());
        Vector3f p2 = new Vector3f(p1).add(e1.getHitboxSize());
        Vector3f p3 = new Vector3f(e2.getPosition()).add(e1.getHitboxOffset());
        Vector3f p4 = new Vector3f(p3).add(e2.getHitboxSize());

        // y diff
        float dy = (Math.min(p1.y, p3.y) - Math.max(p2.y, p4.y));

        if(dy < 0f){
            return;
        }
        if (e1.getVelocity().y < e2.getVelocity().y) {
            dy *= -1;

        }
        if(!e2.data2){
            Global.data = Float.toString(dy);
        } else {
            Global.data2 = Float.toString(dy);
        }
        if(!e2.isStatic()){
            e2.getVelocity().y = dy * e1.getBouncyness();
            e2.getPosition().y += dy;
        }
        if(!e1.isStatic()){
            e1.getVelocity().y = dy * e2.getBouncyness();
            e1.getPosition().y -= dy;
        }

    }

    private boolean intersect(Vector3f a1, Vector3f a2, Vector3f b1, Vector3f b2) {
        return (a1.x <= b1.x + b2.x && a1.x + a2.x >= b1.x) &&
                (a1.y >= b1.y + b2.y && a1.y + a2.y <= b1.y) &&
                (a1.z <= b1.z + b2.z && a1.z + a2.z >= b1.z);
    }

}
