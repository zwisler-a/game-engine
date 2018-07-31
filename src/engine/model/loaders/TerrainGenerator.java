package engine.model.loaders;

import common.Maths;
import engine.entity.Terrain;
import engine.model.Loader;
import engine.model.Model;
import engine.model.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TerrainGenerator {

    /**
     * Generates a new terrain with the given properties
     *
     * @param heightmap        Location of the heightmap to be used to generate the Terrain
     * @param sizeX            Size on the x axis
     * @param sizeZ            Size on the y acis
     * @param verteciesPerSide How many verticies there should be on each side
     * @param texture          Texture of the map
     * @return A generated terrain
     */
    public static Terrain generate(String heightmap, float sizeX, float sizeZ, int verteciesPerSide, Texture texture) {

        float xStride = sizeX / verteciesPerSide;
        float zStride = sizeZ / verteciesPerSide;


        float uvStride = 1f / verteciesPerSide;

        BufferedImage heightMap;
        try {
            heightMap = ImageIO.read(new File(heightmap));
        } catch (IOException e) {
            e.printStackTrace();
            return new Terrain(null, null, null);
        }

        float heightmapSampleStride = (float) heightMap.getWidth() / verteciesPerSide;

        Vector3f[] vertecies = new Vector3f[verteciesPerSide * verteciesPerSide];
        Vector3f[] normals = new Vector3f[verteciesPerSide * verteciesPerSide];
        Vector2f[] uvCoords = new Vector2f[verteciesPerSide * verteciesPerSide];

        for (int x = 0; x < verteciesPerSide; x++) {
            for (int z = 0; z < verteciesPerSide; z++) {
                // Vert
                Color yValColor = new Color(heightMap.getRGB((int) (x * heightmapSampleStride), (int) (z * heightmapSampleStride)));
                vertecies[x * verteciesPerSide + z] = new Vector3f(
                        x * xStride, yValColor.getBlue(), z * zStride
                );
                // Uv
                uvCoords[x * verteciesPerSide + z] = new Vector2f(
                        uvStride * x, uvStride * z
                );
            }
        }

        Vector3i[] indicies = new Vector3i[((verteciesPerSide - 1) * (verteciesPerSide - 1)) * 2];
        int indiciesPointer = 0;
        for (int x = 0; x < verteciesPerSide - 1; x++) {
            for (int z = 0; z < verteciesPerSide - 1; z++) {
                indicies[indiciesPointer] = new Vector3i(
                        (x * verteciesPerSide + z) + verteciesPerSide,
                        x * verteciesPerSide + z,
                        (x * verteciesPerSide + z) + 1
                );
                Vector3f normal = Maths.calculateSurfaceNormal(
                        vertecies[indicies[indiciesPointer].x],
                        vertecies[indicies[indiciesPointer].y],
                        vertecies[indicies[indiciesPointer].z]
                );
                indiciesPointer++;
                normals[x * verteciesPerSide + z] = normal;
                normals[(x * verteciesPerSide + z) + verteciesPerSide] = normal;
                normals[(x * verteciesPerSide + z) + 1] = normal;
            }
        }
        for (int x = 0; x < verteciesPerSide - 1; x++) {
            for (int z = 1; z < verteciesPerSide; z++) {
                indicies[indiciesPointer] = new Vector3i(
                        ((x * verteciesPerSide + z) + verteciesPerSide) - 1,
                        x * verteciesPerSide + z,
                        (x * verteciesPerSide + z) + verteciesPerSide
                );
                Vector3f normal = Maths.calculateSurfaceNormal(
                        vertecies[indicies[indiciesPointer].x],
                        vertecies[indicies[indiciesPointer].y],
                        vertecies[indicies[indiciesPointer].z]
                );
                indiciesPointer++;
                normals[x * verteciesPerSide + z] = normal;
                normals[((x * verteciesPerSide + z) + verteciesPerSide) - 1] = normal;
                normals[(x * verteciesPerSide + z) + verteciesPerSide] = normal;
            }
        }

        float[] verteciesArray = Vec3fToFloatArray(vertecies);
        float[] uvCoordsArray = Vec2fToFloatArray(uvCoords);
        float[] normalsArray = Vec3fToFloatArray(normals);
        int[] indiciesArray = Vec3iToIntArray(indicies);

        Model model = Loader.loadToVAO(verteciesArray, indiciesArray, uvCoordsArray, normalsArray);

        Terrain terrain = new Terrain(model, texture, new Vector3f());

        return terrain;
    }

    // Helper classes

    private static int[] Vec3iToIntArray(Vector3i[] source) {
        int[] target = new int[source.length * 3];
        for (int i = 0; i < source.length; i++) {
            if (source[i] != null) {
                target[(i * 3)] = source[i].x;
                target[(i * 3) + 1] = source[i].y;
                target[(i * 3) + 2] = source[i].z;
            }
        }
        return target;
    }

    private static float[] Vec3fToFloatArray(Vector3f[] source) {
        float[] target = new float[source.length * 3];
        for (int i = 0; i < source.length; i++) {
            target[(i * 3)] = source[i].x;
            target[(i * 3) + 1] = source[i].y;
            target[(i * 3) + 2] = source[i].z;
        }
        return target;
    }

    private static float[] Vec2fToFloatArray(Vector2f[] source) {
        float[] target = new float[source.length * 2];
        for (int i = 0; i < source.length; i++) {
            target[(i * 2)] = source[i].x;
            target[(i * 2) + 1] = source[i].y;
        }
        return target;
    }

}
