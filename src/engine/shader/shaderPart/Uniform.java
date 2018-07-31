package engine.shader.shaderPart;

public class Uniform {

    private int location;
    private String type;
    private String name;

    public Uniform(String type, String name) {
        this.type = type;
        this.name = name;
    }


    public int getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
