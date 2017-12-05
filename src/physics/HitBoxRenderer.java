package physics;

import common.Logger.Logger;
import org.lwjgl.opengl.GL11;

import java.util.LinkedList;

public class HitBoxRenderer {


    public void render(LinkedList<PhysicsEntity> entities) {
        for (PhysicsEntity entity : entities) {
            GL11.glLineWidth(2.5f);
            GL11.glColor3f(1.0f, 0.0f, 0.0f);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(15, 0, 0);
            GL11.glEnd();
        }
    }

}
