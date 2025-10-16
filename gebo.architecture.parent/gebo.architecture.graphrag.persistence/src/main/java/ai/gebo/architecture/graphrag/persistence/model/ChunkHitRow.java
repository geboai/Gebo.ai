package ai.gebo.architecture.graphrag.persistence.model;

import java.beans.ConstructorProperties;
import java.util.List;

import lombok.Data;

@Data
public class ChunkHitRow {
	public ChunkHitRow() {
	}

	@ConstructorProperties({ "chunkId", "documentReferenceId", "matchedIds", "occurrences" })
	public ChunkHitRow(String chunkId, String documentReferenceId, java.util.List<String> matchedIds,
			Long occurrences) {
		this.chunkId = chunkId;
		this.documentReferenceId = documentReferenceId;
		this.matchedIds = matchedIds;
		this.occurrences = occurrences;
	}

	String chunkId = null;
	String documentReferenceId = null;
	List<String> matchedIds = null;
	Long occurrences = null;
}