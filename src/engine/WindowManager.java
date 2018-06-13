package engine;

import engine.input.KeyboardHandler;
import engine.input.MouseHandler;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowManager {

    private static GLFWKeyCallback keyCallback;
    private static long window;
    private static int height;
    private static int width;
    private static GLFWWindowSizeCallback windowSizeCallback;

    public static void createWindow(int width, int height, Vector3f backgroundColor) {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();
        WindowManager.height = height;
        WindowManager.width = width;

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, "", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
        MouseHandler.setWindowId(window);

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);


        // Make the window visible
        glfwShowWindow(window);


        GL.createCapabilities();

        // Set the clear color
        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 0f);

        glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                WindowManager.width = width;
                WindowManager.height = height;


                glViewport(0, 0, width, height);
            }
        });
    }

    public static void update() {

        glfwSwapBuffers(window); // swap the color buffers
        glfwPollEvents();
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
