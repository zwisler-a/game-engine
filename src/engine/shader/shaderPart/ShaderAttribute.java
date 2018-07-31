package engine.shader.shaderPart;

public class ShaderAttribute {
    private String name;
    private int position;
    private String type;

    public ShaderAttribute(String name, int position, String type) {
        this.name = name;
        this.position = position;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
