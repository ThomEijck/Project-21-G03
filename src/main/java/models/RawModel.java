package models;

public class RawModel {
    private int vaoID;
    private int vertexCount;
    private int indexCount;

    public RawModel(int vaoID, int vertexCount, int indexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.indexCount = indexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getIndexCount() {
        return indexCount;
    }
}
