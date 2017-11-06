package engine.shader;

import common.Logger;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;

public abstract class Shader {
    private int vertexShaderId;
    private int fragmentShaderId;
    protected int programId;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public Shader(String vertexShaderFile, String fragmentShaderFile) {
        Logger.debug("Loading verterx shader: " + vertexShaderFile);
        Logger.debug("Loading fragment shader: " + fragmentShaderFile);
        vertexShaderId = loadShader(vertexShaderFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentShaderFile, GL20.GL_FRAGMENT_SHADER);
        programId = GL20.glCreateProgram();
        Logger.debug("Used in shader programm: " + programId);
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);
        bindAttributes();
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);
        getAllUniformLocations();
    }


    protected abstract void getAllUniformLocations();


    protected int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(programId, name);
    }

    protected abstract void bindAttributes();

    protected void bindAttribut(int attribut, String name) {
        GL20.glBindAttribLocation(programId, attribut, name);
    }


    public void start() {
        GL20.glUseProgram(programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }


    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadVector(int location, Vector4f vector) {
        GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    protected void loadVector(int location, Vector2f vector) {
        GL20.glUniform2f(location, vector.x, vector.y);
    }

    protected void loadBool(int location, boolean bool) {
        float toload = 0;
        if (bool) toload = 1;
        GL20.glUniform1f(location, toload);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        matrix.get(matrixBuffer);
        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }


    public void cleanUp() {
        stop();
        GL20.glDetachShader(programId, vertexShaderId);
        GL20.glDetachShader(programId, fragmentShaderId);
        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
        GL20.glDeleteProgram(programId);
    }


    public static int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource.toString());
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL_FALSE) {
            Logger.warning("Error while compiling shader!");
            Logger.warning(GL20.glGetShaderInfoLog(shaderID));
        }

        GL20.glLinkProgram(shaderID);
        GL20.glValidateProgram(shaderID);

        return shaderID;
    }

    public void loadInt(int location, int i) {
        GL20.glUniform1i(location, i);
    }
}
