package ai.gebo.architecture.graphrag.persistence.model;

public interface ChunkMeta {
    String getChunkId();
    String getDocumentReferenceId();
    String getKnowledgebaseCode();
    Integer getTokenCount();
    Long getDocumentEpochMillis();
}
