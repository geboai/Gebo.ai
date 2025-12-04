package ai.gebo.architecture.graphrag.persistence.model;

import lombok.Data;

@Data
public class ChunkMeta {
	private String chunkId=null,documentReferenceId=null,knowledgebaseCode=null;
}
