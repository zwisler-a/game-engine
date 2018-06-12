package engine.shader.staticShader;

import engine.entity.Camera;
import engine.entity.LightSource;
import engine.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.LinkedList;

public class StaticShader extends Shader {

    private final static String vertexShaderFile = "src/engine/shader/staticShader/vertexShader.glsl";
    private final static String fragmentShaderFile = "src/engine/shader/staticShader/fragmentShader.glsl";
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int[] location_lightPos;
    private int[] location_lightColor;
    private int[] location_lightIntensity;
    private int location_clipPlane;

    public StaticShader() {
        super(vertexShaderFile, fragmentShaderFile);
    }

    public StaticShader(String vertexShaderFile, String fragmentShaderFile) {
        super(vertexShaderFile, fragmentShaderFile);
    }


    @Override
    protected void bindAttributes() {
        super.bindAttribut(0, "in_Position");
        super.bindAttribut(1, "in_TextureCoord");
        super.bindAttribut(2, "in_normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_clipPlane = super.getUniformLocation("clipPlane");
        location_lightPos = new int[4];
        location_lightColor = new int[4];
        location_lightIntensity = new int[4];

        for (int i = 0; i < 4; i++) {
            location_lightPos[i] = super.getUniformLocation("lightPos[" + i + "]");
            location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
            location_lightIntensity[i] = super.getUniformLocation("intensity[" + i + "]");
        }
    }


    public void loadClippingPlane(Vector4f vector4f) {
        super.loadVector(this.location_clipPlane, vector4f);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadviewMatrix(Camera c) {
        super.loadMatrix(location_viewMatrix, Camera.createViewMatrix(c));
    }

    public void loadLight(LinkedList<LightSource> l) {
        for (int i = 0; i < 4; i++) {
            if (i < l.size()) {
                super.loadVector(location_lightPos[i], l.get(i).getPosition());
                super.loadVector(location_lightColor[i], l.get(i).getColor());
                super.loadFloat(location_lightIntensity[i], l.get(i).getIntensity());
            } else {
                super.loadVector(location_lightPos[i], new Vector3f(0, 0, 0));
                super.loadVector(location_lightColor[i], new Vector3f(0, 0, 0));
                super.loadFloat(location_lightIntensity[i], 0);
            }
        }
    }

    public int shaderId() {
        return this.programId;
    }
}
