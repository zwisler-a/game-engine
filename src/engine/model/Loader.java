package engine.model;


import common.Logger;
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
    private static ArrayList<Integer> textures = new ArrayList<Integer>();

    public static Model loadToVAO(float[] vertices, int[] indices, float[] uvCoords, float[] normals) {

        Logger.debug("Load model");

        // Create
        int vaoId = GL30.glGenVertexArrays();
        vaos.add(vaoId);
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
        int indicesVBO = GL15.glGenBuffers();
        vbos.add(indicesVBO);
        int uvCoordsVBO = GL15.glGenBuffers();
        vbos.add(uvCoordsVBO);
        int normalsVBO = GL15.glGenBuffers();
        vbos.add(normalsVBO);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        FloatBuffer uvBuffer = BufferUtils.createFloatBuffer(uvCoords.length);
        uvBuffer.put(uvCoords);
        uvBuffer.flip();

        FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
        normalsBuffer.put(normals);
        normalsBuffer.flip();

        GL30.glBindVertexArray(vaoId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvCoordsVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalsVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalsBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        Logger.debug("Loaded!" + vaoId + " " + indices.length);

        return new Model(vaoId, indices.length, indicesVBO);

    }


   /* public static int loadTexture(String file) {

        try {
            Texture texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/" + file + ".png"));
            int textureId = texture.getTextureID();
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
            textures.add(textureId);
            return textureId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }*/


    public static void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vao : vaos) {
            GL15.glDeleteBuffers(vao);
        }

    }
}