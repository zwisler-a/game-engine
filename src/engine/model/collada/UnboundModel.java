package engine.model.collada;

import engine.model.Loader;
import engine.model.Model;

public class UnboundModel {
    protected float[] verticies;
    protected float[] uvCoords;
    protected float[] normals;
    protected int[] indicies;


    public void order(int indicieCount, int[] order, int vertexPos, int normalPos, int uvPos, int stride) {
        float[] verticies = new float[indicieCount * 3 * 3];
        float[] normals = new float[indicieCount * 3 * 3];
        float[] uvs = new float[indicieCount * 3 * 2];
        int[] indicies = new int[indicieCount * 3];

        int verPointer = 0;
        int normPointer = 0;
        int uvPointer = 0;
        int indiPointer = 0;

        for (int i = 0; i < indicieCount * stride * 3; i += stride) {
            verticies[verPointer++] = this.verticies[(order[i + vertexPos] * 3)];
            verticies[verPointer++] = this.verticies[(order[i + vertexPos] * 3) + 1];
            verticies[verPointer++] = this.verticies[(order[i + vertexPos] * 3) + 2];

            normals[normPointer++] = this.normals[(order[i + normalPos] * 3)];
            normals[normPointer++] = this.normals[(order[i + normalPos] * 3) + 1];
            normals[normPointer++] = this.normals[(order[i + normalPos] * 3) + 2];

            if (uvPos != -1) {
                uvs[uvPointer++] = uvCoords[(order[i + uvPos] * 2)];
                uvs[uvPointer++] = 1 - uvCoords[(order[i + uvPos] * 2) + 1];
            }

            indicies[indiPointer] = indiPointer++;
        }

        this.verticies = verticies;
        this.indicies = indicies;
        this.normals = normals;
        this.uvCoords = uvs;
    }


    public Model load() {
        return Loader.loadToVAO(this.verticies, this.indicies, this.uvCoords, this.normals);
    }


    public void setVeticies(float[] veticies) {
        this.verticies = veticies;
    }

    public void setUvCoords(float[] uvCoords) {
        this.uvCoords = uvCoords;
    }

    public void setNormals(float[] normals) {
        this.normals = normals;
    }

}
