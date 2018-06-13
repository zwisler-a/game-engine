package engine.input;

import org.joml.Vector2d;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseHandler {

    private static long windowId;
    private static double newX;
    private static double newY;
    private static double prevX;
    private static double prevY;
    private static boolean mouseLocked;

    private static int[] width = new int[1];
    private static int[] height = new int[1];

    public static void setWindowId(long windowId) {
        MouseHandler.windowId = windowId;
    }

    public static Vector2d get() {

        if (glfwGetMouseButton(windowId, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
            mouseLocked = true;
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }
        if (glfwGetMouseButton(windowId, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
            mouseLocked = false;
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }

        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(windowId, x, y);
        x.rewind();
        y.rewind();

        newX = x.get();
        newY = y.get();
        glfwGetWindowSize(windowId, width, height);
        double deltaX = newX - width[0] / 2;
        double deltaY = newY - height[0] / 2;

        boolean rotX = newX != prevX;
        boolean rotY = newY != prevY;

        prevX = newX;
        prevY = newY;

        if (mouseLocked) {
            glfwSetCursorPos(windowId, width[0] / 2, height[0] / 2);
        }

        if ((rotX || rotY) && mouseLocked) {
            return new Vector2d(deltaX, deltaY);
        } else {
            return new Vector2d();
        }
    }

}
