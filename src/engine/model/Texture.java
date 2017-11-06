package engine.model;

public class Texture {
    private int textureID;
    private int textureID2;

    public Texture(int textureID) {
        this.textureID = textureID;
    }

    public Texture(int textureID, int textureID2) {
        this.textureID = textureID;
        this.textureID2 = textureID2;
    }

    public int getTextureId() {
        return this.textureID;
    }

    public int getTextureId2() {
        return this.textureID2;
    }
}
