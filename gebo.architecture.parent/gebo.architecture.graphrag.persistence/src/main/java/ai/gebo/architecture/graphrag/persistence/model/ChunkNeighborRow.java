package ai.gebo.architecture.graphrag.persistence.model;

public interface ChunkNeighborRow {
    String getChunkId();
    String getDocumentReferenceId();
    Long getNeighborCount();
}