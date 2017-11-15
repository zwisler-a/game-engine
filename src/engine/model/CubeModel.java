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
            0.5f, (1f/3)*2,     // 1
            0.25f, (1f/3)*2,    // 2
            0, (1f/3)*2,        // 3
            0.5f, 1f,           // 4
            0.5f, (1f/3),       // 5
            0.25f, (1f/3),      // 6
            0.5f, 0f,           // 7
            0f, (1f/3),         // 8
            0.25f, 1f,          // 9
            1f, (1f/3)*2,       // 10
            0.75f, (1f/3)*2,    // 11
            0.75f, (1f/3),      // 12
            0.25f, 0f,          // 13
            1f, (1f/3)          // 14
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

    public static Model load() {

        // Create a buffer from array
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertexBufferData.length);
        buffer.put(vertexBufferData);
        buffer.flip();
        // Int Buffer for indicies
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(vertexBufferIndicies.length);
        indicesBuffer.put(vertexBufferIndicies);
        indicesBuffer.flip();
        // Float buffer for uv coords
        FloatBuffer uvBuffer = BufferUtils.createFloatBuffer(textureBufferData.length);
        uvBuffer.put(textureBufferData);
        uvBuffer.flip();

        // Create a VAO and a VBOs
        int vaoId = GL30.glGenVertexArrays();
        int vboId = GL15.glGenBuffers();
        int uvCoordsVBO = GL15.glGenBuffers();
        int indicesVboId = GL15.glGenBuffers();
        // Bind VAO
        GL30.glBindVertexArray(vaoId);
        // Put verts in VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        // Load UV Coords in VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvCoordsVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVboId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        Logger.debug("Skybox loaded - vaoId:" + vaoId + " vboId:" + vboId + " uvVboId: " + uvCoordsVBO);
        return Loader.loadToVAO(vertexBufferData, vertexBufferIndicies, textureBufferData, new float[] {});
        // return new Model(vaoId, vertexBufferIndicies.length, indicesVboId);
    }
}
