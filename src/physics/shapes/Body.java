package physics.shapes;

import engine.entity.Entity;
import org.joml.Matrix3f;
import org.joml.Vector3f;

import java.util.ArrayList;

public abstract class Body extends Entity {


    protected Vector3f[] points;
    protected Vector3f[] normals;
    protected Matrix3f inertiaTensor;
    protected float mass = 1;
    protected boolean isStatic;

    Vector3f linearMomentum, angularMomentum;

    Vector3f linearVelocity, angularVelocity;


    abstract void calcPoints();

    abstract void calcNormals();

    abstract void calcInertialTensor();

    public abstract ArrayList<Vector3f> getFaceOfNormal(Vector3f contactNormal);

    public Body() {
        this.linearMomentum = new Vector3f();
        this.angularMomentum = new Vector3f();
        this.linearVelocity = new Vector3f();
        this.angularVelocity = new Vector3f();
    }

    public void updateMomentum() {
        linearVelocity = linearMomentum.mul(1 / this.getMass());
        this.angularVelocity = this.getInertiaTensor().transform(new Vector3f(this.angularMomentum));

          /*      c->a->omega = c->a->Iinv * c->a->L;
        c->b->omega = c->b->Iinv * c->b->L;*/
    }

    public void applyPhysics() {
        this.getRotation().add(this.angularVelocity);
        this.getPosition().add(linearVelocity);
    }

    public Matrix3f getInertiaTensor() {
        if (this.inertiaTensor == null) {
            this.calcInertialTensor();
        }
        return this.inertiaTensor;
    }

    /**
     * Select min with dot product of axis and corners
     *
     * @param axis
     * @return
     */
    public float getMin(Vector3f axis) {
        this.calcPoints();
        float currentMin = this.points[0].dot(axis);
        int currentMinIdx = 0;
        for (int i = 1; i < this.points.length; i++) {
            float currentPointDot = this.points[i].dot(axis);
            if (currentPointDot < currentMin) {
                currentMin = currentPointDot;
                currentMinIdx = i;
            }
        }
        return currentMin;
    }

    public Vector3f getMinVector(Vector3f axis) {
        this.calcPoints();
        float currentMin = this.points[0].dot(axis);
        int currentMinIdx = 0;
        for (int i = 1; i < this.points.length; i++) {
            float currentPointDot = this.points[i].dot(axis);
            if (currentPointDot < currentMin) {
                currentMin = currentPointDot;
                currentMinIdx = i;
            }
        }
        return this.points[currentMinIdx];
    }

    /**
     * Select max with dot product of axis and corners
     *
     * @param axis
     * @return
     */
    public float getMax(Vector3f axis) {
        this.calcPoints();
        float currentMax = this.points[0].dot(axis);
        int currentMaxIdx = 0;
        for (int i = 1; i < this.points.length; i++) {
            float currentPointDot = this.points[i].dot(axis);
            if (currentPointDot > currentMax) {
                currentMax = currentPointDot;
                currentMaxIdx = i;
            }
        }
        return currentMax;
    }

    public Vector3f[] getNormals() {
        this.calcNormals();
        return this.normals;
    }


    public boolean isStatic() {
        return this.isStatic;
    }

    public void isStatic(boolean b) {
        this.isStatic = b;
    }

    public Vector3f[] getPoints() {
        this.calcPoints();
        return points;
    }

    public Vector3f getLinearMomentum() {
        return linearMomentum;
    }

    public void setLinearMomentum(Vector3f linearMomentum) {
        this.updateMomentum();
        this.linearMomentum = linearMomentum;
    }

    public Vector3f getAngularMomentum() {
        return angularMomentum;
    }

    public void setAngularMomentum(Vector3f angularMomentum) {
        this.updateMomentum();
        this.angularMomentum = angularMomentum;
    }

    public Vector3f getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector3f linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public Vector3f getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(Vector3f angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public float getMass() {
        return mass;
    }


    public void setMass(float mass) {
        this.mass = mass;
    }


    public abstract ArrayList<ArrayList<Vector3f>> getFacesPointingTo(Vector3f vector3f);
}
