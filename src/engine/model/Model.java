package engine.model;

public class Model {
    private final int vaoId;
    private final int indiciesCount;
    private final int indicesVBO;

    public Model(int vaoId, int indiciesCount, int indicesVBO) {
        this.vaoId = vaoId;
        this.indiciesCount = indiciesCount;
        this.indicesVBO = indicesVBO;
    }

    public Model(Model model) {
        this.vaoId = model.getVaoId();
        this.indiciesCount = model.getIndiciesCount();
        this.indicesVBO = model.getIndicesVBO();
    }


    public int getVaoId() {
        return vaoId;
    }

    public int getIndiciesCount() {
        return indiciesCount;
    }

    public int getIndicesVBO() {
        return indicesVBO;
    }
}
