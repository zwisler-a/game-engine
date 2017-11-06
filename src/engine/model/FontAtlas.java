package engine.model;

import engine.model.loaders.TextureLoader;
import org.joml.Vector4f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FontAtlas {
    private final FontMetrics metrics;
    public Texture fontAtlasTexture;
    private int textureWidth;
    public int[] charPosition;

    public FontAtlas(BufferedImage fontAtlas, int[] charPosition, FontMetrics metrics) {
        textureWidth = fontAtlas.getWidth();
        this.fontAtlasTexture = TextureLoader.loadTexture(fontAtlas);
        this.charPosition = charPosition;
        this.metrics = metrics;
    }

    public Vector4f getCharOffset(char a) {
        Vector4f offset = new Vector4f();
        offset.x = this.charPosition[a] > 0 ? (float) this.charPosition[a] / textureWidth : 0;
        offset.y = 0;
        float charWidth = this.metrics.charWidth(a);
        offset.z = charWidth / textureWidth;
        offset.w = 1;
        return offset;
    }

    public float getSize(String s) {
        float size = 0;
        for (int i = 0; i < s.length(); i++) {
            size += this.metrics.charWidth(s.charAt(i));
        }
        return size;
    }
}