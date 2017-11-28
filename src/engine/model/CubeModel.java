package engine.model;

import common.Logger.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class CubeModel {
    private static float[] vertexBufferData = {
            5f, -5f, 5f,    // p1
            -5f, -5f, 5f,   // p2
            -5f, -5f, -5f,  // p3
            5f, -5f, -5f,   // p4
            5f, 5f, 5f,     // p5
            -5f, 5f, 5f,    // p6
            5f, 5f, -5f,    // p7
            -5f, 5f, -5f,   // p8
            -5f, -5f, -5f,  // p3
            -5f, -5f, -5f,  // p3
            5f, -5f, -5f,   // p4
            5f, 5f, -5f,    // p7
            -5f, 5f, -5f,   // p8
            -5f, 5f, -5f    // p8
    };
    private static float[] textureBufferData = {
            0.5f, (1f / 3) * 2,     // 1
            0.25f, (1f / 3) * 2,    // 2
            0, (1f / 3) * 2,        // 3
            0.5f, 1f,           // 4
            0.5f, (1f / 3),       // 5
            0.25f, (1f / 3),      // 6
            0.5f, 0f,           // 7
            0f, (1f / 3),         // 8
            0.25f, 1f,          // 9
            1f, (1f / 3) * 2,       // 10
            0.75f, (1f / 3) * 2,    // 11
            0.75f, (1f / 3),      // 12
            0.25f, 0f,          // 13
            1f, (1f / 3)          // 14
    };
    private static int[] vertexBufferIndicies = {
            0, 4, 10,
            11, 10, 4,
            10, 11, 9,
            11, 13, 9,
            7, 5, 2,
            5, 1, 2,
            4, 6, 12,
            12, 5, 4,
            0, 3, 8,
            8, 1, 0,
            4, 5, 1,
            0, 4, 1
    };

    // TODO
    private static float[] normalBufferData = {
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0,
        0,1,0
    };

    private static Model model;

    public static Model load() {
        if (model == null) {
            Logger.debug("Loading coded CubeModel");
            model = Loader.loadToVAO(vertexBufferData, vertexBufferIndicies, textureBufferData, normalBufferData);
        }
        return model;
    }
}
