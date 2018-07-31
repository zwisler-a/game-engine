package physics;

import org.joml.Vector3f;
import physics.shapes.Body;

import java.util.ArrayList;
import java.util.LinkedList;

public class PhysicsEngine {

    private final SATCollisionDetection sat;
    private PhysicsConfiguration config;
    private LinkedList<Body> bodies = new LinkedList<>();
    public static boolean frist = true;

    public PhysicsEngine(PhysicsConfiguration config) {
        this.config = config;
        this.sat = new SATCollisionDetection();
    }

    public PhysicsEngine() {
        this(new PhysicsConfiguration());
    }

    public void addEntity(Body e) {
        this.bodies.add(e);
    }


    public void tick(double dt) {
        for (Body ph : this.bodies) {
            ph.applyPhysics();
            if (!ph.isStatic()) {
                ph.getLinearMomentum().y -= 0.01f;
                ph.updateMomentum();
            }
        }

        this.resolveCollision(this.sat.check(this.bodies));

        /*for (PhysicsEntity entity : this.entities) {
            this.applyPhysics(entity, dt);
            this.detectCollisions(entity, this.collisionPairs);
        }
        this.resolveCollisionPairs(this.collisionPairs, dt);
        this.hitboxRenderer.render(this.entities);*/
    }

    public static ArrayList<Vector3f> wtf;
    public static ArrayList<Integer> wtfC;

    private void resolveCollision(LinkedList<Collision> collisions) {
        LinkedList<Collision> cols = sat.check(bodies);
        for (Collision col : cols) {
            AxisTest min = col.getMinimalDepth();
            Vector3f undo = new Vector3f(min.getAxis()).normalize(min.getDepth());
            if (!col.getPoly1().isStatic()) {
                //this.resolveCollision(this.sat.checkPolyhedron(col.getPoly1(), this.bodies));


                Vector3f contactNormal = new Vector3f(
                        -min.getAxis().x,
                        -min.getAxis().y,
                        -min.getAxis().z);
                ArrayList<Vector3f> contactPoints = col.getContactPoints();
                for (Vector3f point : contactPoints) {
                    this.calcImpulseMagnitude(col.getPoly1(),
                            col.getPoly2(),
                            point,
                            contactNormal);
                }


                wtf = col.getContactPoints();


                 col.getPoly1().getPosition().sub(undo);
            }

        }
    }


    //TODO move to collision
    private float calcImpulseMagnitude(Body p1, Body p2, Vector3f contactPoint, Vector3f contactNormal) {
        // https://en.wikipedia.org/wiki/Collision_response#Impulse-Based_Contact_Model

        // coefficient of restitution
        float e = 0.5f;

        // r1,r2 Offset center-contact
        Vector3f r1 = new Vector3f(contactPoint).sub(p1.getPosition());
        Vector3f r2 = new Vector3f(contactPoint).sub(p2.getPosition());
        // Vp1,Vp2 - Velocity in point
        Vector3f vp1 = new Vector3f(p1.getLinearVelocity()).add(new Vector3f(p1.getAngularVelocity()).cross(r1));
        Vector3f vp2 = new Vector3f(p2.getLinearVelocity()).add(new Vector3f(p2.getAngularVelocity()).cross(r2));
        // Vrel
        float vrel = contactNormal.dot(new Vector3f(vp2).sub(vp1));
        float upperPart = -(1 + e) * vrel;

        double term1 = 1 / p1.getMass(),
                term2 = 1 / p2.getMass();

        double term3 = contactNormal.dot(p1.getInertiaTensor().transform(new Vector3f(r1).cross(contactNormal)).cross(r1));
        double term4 = contactNormal.dot(p2.getInertiaTensor().transform(new Vector3f(r2).cross(contactNormal)).cross(r2));

        float j = (float) (upperPart / (term1 + term2 + term3 + term4));

        Vector3f force = new Vector3f(contactNormal).mul(j);
        //Vector3f v1 = new Vector3f(p1.getVelocity()).add(force.cross(r1));

        p1.getLinearMomentum().sub(force);

        p1.getAngularMomentum().sub(new Vector3f(r1).cross(force).mul(2));

        p1.updateMomentum();

       /* Vector3f v1 = new Vector3f(p1.getVelocity())
                .sub(new Vector3f(contactNormal).mul(1 / p1.getMass()));

        Vector3f n1 = new Vector3f(p1.getVelocity()).mul(p1.getMass());
        Vector3f n2 = new Vector3f(p2.getVelocity()).mul(p2.getMass());
        Vector3f n3 = new Vector3f(p1.getVelocity()).sub(p2.getVelocity()).mul(p2.getMass()).mul(e);
        Vector3f v12 = new Vector3f(n1).add(n2).sub(n3).div(p1.getMass() + p2.getMass());
        Vector3f r = new Vector3f(v12).sub(
                new Vector3f(contactNormal).mul(2 * v12.dot(contactNormal))
        );

        p1.setVelocity(v12);

        Vector3f h4 = new Vector3f(r1).cross(contactNormal);
        p1.getInertiaTensor().transform(h4);
        Vector3f h3 = h4.mul(1);
        Vector3f rv1 = new Vector3f(p1.getAngularVelocity()).add(
                h3
        );

        // p1.setAngularVelocity(rv1);
        */
        return 0;
    }

}
