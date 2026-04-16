package ai.gebo.architecture.fulltext.model;

import lombok.Data;

@Data
public class FullTextChunkSearchHit {
    private FullTextChunk chunk;
    private Double score;
    private String highlight;
}