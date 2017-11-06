package engine.model.loaders;

import engine.model.Loader;
import engine.model.Model;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ObjLoader {

    private static float[] verticesArray;
    private static int[] indicesArray;

    public static Model loadObjFile(String file) {
        try {

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
            ArrayList<Vector2f> textureCoords = new ArrayList<Vector2f>();
            ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
            ArrayList<Integer> indices = new ArrayList<Integer>();
            float[] textureArray;
            float[] normalsArray;
            String line;
            String[] details = new String[]{};

            while ((line = br.readLine()) != null) {
                details = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f();
                    vertex.x = Float.parseFloat(details[1]);
                    vertex.y = Float.parseFloat(details[2]);
                    vertex.z = Float.parseFloat(details[3]);
                    vertices.add(vertex);
                } else if (line.startsWith("vt")) {
                    Vector2f vertex = new Vector2f();
                    vertex.x = Float.parseFloat(details[1]);
                    vertex.y = Float.parseFloat(details[2]);
                    textureCoords.add(vertex);
                } else if (line.startsWith("vn")) {
                    Vector3f vertex = new Vector3f();
                    vertex.x = Float.parseFloat(details[1]);
                    vertex.y = Float.parseFloat(details[2]);
                    vertex.z = Float.parseFloat(details[3]);
                    normals.add(vertex);
                } else if (line.startsWith("f")) {

                    break;
                }

            }

            textureArray = new float[vertices.size() * 2];
            normalsArray = new float[vertices.size() * 3];

            processOrder(details[1].split("/"), indices, textureCoords, normals, textureArray, normalsArray);
            processOrder(details[2].split("/"), indices, textureCoords, normals, textureArray, normalsArray);
            processOrder(details[3].split("/"), indices, textureCoords, normals, textureArray, normalsArray);

            while ((line = br.readLine()) != null) {
                details = line.split(" ");
                if (details[0].equals("f")) {
                    processOrder(details[1].split("/"), indices, textureCoords, normals, textureArray, normalsArray);
                    processOrder(details[2].split("/"), indices, textureCoords, normals, textureArray, normalsArray);
                    processOrder(details[3].split("/"), indices, textureCoords, normals, textureArray, normalsArray);

                }
            }

            verticesArray = new float[vertices.size() * 3];
            indicesArray = new int[indices.size()];


            int vertexPointer = 0;
            for (Vector3f v : vertices) {
                verticesArray[vertexPointer++] = v.x;
                verticesArray[vertexPointer++] = v.y;
                verticesArray[vertexPointer++] = v.z;
            }


            for (int i = 0; i < indices.size(); i++) {
                indicesArray[i] = indices.get(i);
            }
            br.close();
            return Loader.loadToVAO(verticesArray, indicesArray, textureArray, normalsArray);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void processOrder(String[] vertex, ArrayList<Integer> indices, ArrayList<Vector2f> textures,
                                     ArrayList<Vector3f> normals, float[] textureArray, float[] normalsArray) {

        int currentVertexPointer = Integer.parseInt(vertex[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currTexture = textures.get(Integer.parseInt(vertex[1]) - 1);
        textureArray[currentVertexPointer * 2] = currTexture.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currTexture.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertex[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
    }

}