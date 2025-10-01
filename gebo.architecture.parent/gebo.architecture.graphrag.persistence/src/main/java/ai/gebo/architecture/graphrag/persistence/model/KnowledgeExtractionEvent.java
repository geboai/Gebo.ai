package ai.gebo.architecture.graphrag.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KnowledgeExtractionEvent {
	private long processedTokens = 0l;
	private long processedBytes = 0l;
	private long processedSegments = 0l;
	long errorChunks = 0l;
	long errorTokens = 0l;
	private boolean error;

	public void incrementBy(KnowledgeExtractionEvent event) {
		this.processedTokens += event.processedTokens;
		this.processedBytes += event.processedBytes;
		this.processedSegments += event.processedSegments;
		this.errorChunks += event.errorChunks;
		this.errorTokens += event.errorTokens;
		this.error = this.error || event.error;
	}
}