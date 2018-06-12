package engine.model;


import common.Logger.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Loader {


    private static ArrayList<Integer> vaos = new ArrayList<Integer>();
    private static ArrayList<Integer> vbos = new ArrayList<Integer>();

    public static Model loadToVAO(float[] vertices, int[] indices, float[] uvCoords, float[] normals) {
        // Create
        int vaoId = GL30.glGenVertexArrays();
        vaos.add(vaoId);
        int indicesVBO = GL15.glGenBuffers();
        vbos.add(indicesVBO);

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        GL30.glBindVertexArray(vaoId);

        loadVBO(vertices, GL15.GL_ARRAY_BUFFER, 0, 3);
        loadVBO(uvCoords, GL15.GL_ARRAY_BUFFER, 1, 2);
        loadVBO(normals, GL15.GL_ARRAY_BUFFER, 2, 3);

        GL30.glBindVertexArray(0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        return new Model(vaoId, indices.length, indicesVBO);
    }

    public static void loadVBO(float[] data, int type, int index, int size) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        GL15.glBindBuffer(type, vboId);
        GL15.glBufferData(type, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 4 * size, 0);
        GL15.glBindBuffer(type, 0);
    }

    public static void loadVBO(int[] data, int type, int index, int size) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);

        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        GL15.glBindBuffer(type, vboId);
        GL15.glBufferData(type, buffer, GL15.GL_STATIC_DRAW);
        GL30.glVertexAttribIPointer(index, size, GL11.GL_INT, 4 * size, 0);
        GL15.glBindBuffer(type, 0);
    }

}