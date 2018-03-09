package physics;

import org.joml.Vector3f;
import physics.shapes.Body;

import java.util.Arrays;
import java.util.LinkedList;

public class SATCollisionDetection {

    public LinkedList<Collision> check(LinkedList<Body> entities) {
        LinkedList<Collision> collisions = new LinkedList<>();
        for (Body controlledEntity : entities) {
            if(controlledEntity.isStatic()){
                continue;
            }
            collisions.addAll(this.checkPolyhedron(controlledEntity, entities));
        }
        return collisions;
    }

    public LinkedList<Collision> checkPolyhedron(Body controlledEntity, LinkedList<Body> entities) {
        LinkedList<Collision> collisions = new LinkedList<>();
        for (Body entity : entities) {

            if (!controlledEntity.equals(entity)) {
                Collision c = this.checkPolyhedronPair(controlledEntity, entity);
                if (c != null) {
                    collisions.add(c);
                }
            }
        }
        return collisions;
    }


    public Collision checkPolyhedronPair(Body p1, Body p2) {
        LinkedList<Vector3f> axes = new LinkedList<>(Arrays.asList(p2.getNormals()));
        // axes.addAll(Arrays.asList(p2.getNormals()));
        Collision collision = new Collision(p1, p2);
        for (Vector3f axis : axes) {
            float depth = this.checkAxis(p1, p2, axis);
            if (depth < 0.000000000000000000000000000000000000000000001f && depth > -0.000000000000000000000000000000000000000000001f) {
                return null;
            }
            collision.addAxis(axis, depth);
        }
        // TODO : avoid multiple min checks
        collision.addContacts(ClippingContact.getContactPoints(p1,p2,collision.getMinimalDepth().getAxis()));
        return collision;
    }

    /**
     * return depth of collision >0 => collision
     *
     * @param p1
     * @param p2
     * @param axis
     * @return
     */
    public float checkAxis(Body p1, Body p2, Vector3f axis) {
        float p1min = p1.getMin(axis);
        float p1max = p1.getMax(axis);
        float p2min = p2.getMin(axis);
        float p2max = p2.getMax(axis);
        if (p2min < p1max && p1min < p2max) {
            if (p1max < p2max) {
                return p1max - p2min;
            } else {
                return -(p2max - p1min);
            }

            //return Math.min(p1max, p2max) - Math.max(p1min, p2min);
        }
        return 0;
    }




}
