package engine.model.collada;

import engine.model.Loader;
import engine.model.Model;
import engine.model.AnimatedModel;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class UnboundAnimatedModel extends UnboundModel {

    private int[] jointIds;
    private float[] weights;

    private Skeleton skeleton;

    public UnboundAnimatedModel(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public void order(int indicieCount, int[] order, int vertexPos, int normalPos, int uvPos, int stride) {
        float[] verticies = new float[indicieCount * 3 * 3];
        int[] jointIds = new int[indicieCount * 3 * 3];
        float[] weights = new float[indicieCount * 3 * 3];
        float[] normals = new float[indicieCount * 3 * 3];
        float[] uvs = new float[indicieCount * 3 * 2];
        int[] indicies = new int[indicieCount * 3];

        int verPointer = 0;
        int jointPointer = 0;
        int weightPointer = 0;
        int normPointer = 0;
        int uvPointer = 0;
        int indiPointer = 0;

        for (int i = 0; i < indicieCount * stride * 3; i += stride) {
            verticies[verPointer++] = this.verticies[(order[i + vertexPos] * 3)];
            verticies[verPointer++] = this.verticies[(order[i + vertexPos] * 3) + 1];
            verticies[verPointer++] = this.verticies[(order[i + vertexPos] * 3) + 2];

            jointIds[jointPointer++] = this.skeleton.getJointIds()[(order[i + vertexPos] * 3)];
            jointIds[jointPointer++] = this.skeleton.getJointIds()[(order[i + vertexPos] * 3) + 1];
            jointIds[jointPointer++] = this.skeleton.getJointIds()[(order[i + vertexPos] * 3) + 2];

            weights[weightPointer++] = this.skeleton.getJointWights()[(order[i + vertexPos] * 3)];
            weights[weightPointer++] = this.skeleton.getJointWights()[(order[i + vertexPos] * 3) + 1];
            weights[weightPointer++] = this.skeleton.getJointWights()[(order[i + vertexPos] * 3) + 2];

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
        this.jointIds = jointIds;
        this.weights = weights;
        this.indicies = indicies;
        this.normals = normals;
        this.uvCoords = uvs;
    }


    public AnimatedModel load() {
        Model model = Loader.loadToVAO(this.verticies, this.indicies, this.uvCoords, this.normals);
        GL30.glBindVertexArray(model.getVaoId());
        Loader.loadVBO(this.jointIds, GL15.GL_ARRAY_BUFFER, 3, 3);
        Loader.loadVBO(this.weights, GL15.GL_ARRAY_BUFFER, 4, 3);
        GL30.glBindVertexArray(0);
        return new AnimatedModel(model, this.skeleton.getJoints(),this.skeleton.getRootJoint());
    }

}
