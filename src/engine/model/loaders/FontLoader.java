package engine.model.loaders;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import common.Logger;
import engine.model.FontAtlas;

public class FontLoader {

    public static FontAtlas load(String path) {
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            return createFontAtlas(f.deriveFont(Font.PLAIN, 24));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FontAtlas loadFromSys(String name) {
        Font f = new Font(name, Font.PLAIN, 24);
        return createFontAtlas(f);
    }

    private static FontAtlas createFontAtlas(Font font) {
        FontMetrics metrics = getFontMetrics(font, false);
        int imageWidth = 0;
        int[] charPosition = new int[256];
        for (int i = 46; i < 256; i++) {
            if (i == 127) {
                continue;
            }
            charPosition[i] = imageWidth;
            imageWidth += metrics.charWidth(i);
        }
        BufferedImage fontAtlas = new BufferedImage(imageWidth, metrics.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = fontAtlas.createGraphics();
        g.setFont(font);
        for (int i = 46; i < 256; i++) {
            if (i == 127) {
                continue;
            }
            g.drawString(String.valueOf((char)i), charPosition[i], metrics.getAscent());
        }
        g.setColor(Color.RED);
        g.dispose();

        return new FontAtlas(fontAtlas, charPosition, metrics);
    }

    private static FontMetrics getFontMetrics(Font font, boolean antiAlias) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();
        return metrics;
    }



}
