package ai.gebo.architecture.graphrag.persistence.model;

import java.util.List;

public interface ChunkHitRow {
    String getChunkId();
    String getDocumentReferenceId();
    List<String> getMatchedIds();
    Long getOccurrences();
}