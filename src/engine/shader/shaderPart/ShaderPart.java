package engine.shader.shaderPart;

public class ShaderPart {

    private Uniform[] uniforms;
    private ShaderAttribute[] usedAttronizes;
    private String vertexShaderPart;
    private String fragmentShaderPart;

    public ShaderPart(String vertexShaderPart, String fragmentShaderPart) {
        this.vertexShaderPart = vertexShaderPart;
        this.fragmentShaderPart = fragmentShaderPart;
    }

    public Uniform[] getUniforms() {
        return uniforms;
    }

    public ShaderAttribute[] getAttributes() {
        return usedAttronizes;
    }

    public String getVertexShaderPart() {
        return vertexShaderPart;
    }

    public String getFragmentShaderPart() {
        return fragmentShaderPart;
    }
}
