package engine.shader.skyboxShader;

import common.Maths;
import engine.entity.Camera;
import engine.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SkyboxShader extends Shader {

    private final static String vertexShaderFile = "src/engine/shader/skyboxShader/vertexShader.glsl";
    private final static String fragmentShaderFile = "src/engine/shader/skyboxShader/fragmentShader.glsl";
    private int location_scale;
    private int location_viewMatrix;
    private int location_projectionMatrix;

    public SkyboxShader() {
        super(vertexShaderFile, fragmentShaderFile);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribut(0, "in_Position");
        super.bindAttribut(1, "in_texCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        location_scale = super.getUniformLocation("scale");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
    }


    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera c) {
        super.loadMatrix(location_viewMatrix, Camera.createViewMatrixWithoutPosition(c));
    }

    public void loadScale(float scale) {
        super.loadMatrix(this.location_scale, Maths.createTransformationMatrix(new Vector3f(), 0, 0, 0, scale));
    }

}
