package ai.gebo.architecture.graphrag.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KnowledgeExtractionEvent {
	private long processedTokens;
	private long processedBytes;
	private long processedSegments;
	private boolean error;

	public void incrementBy(KnowledgeExtractionEvent event) {
		this.processedTokens += event.processedTokens;
		this.processedBytes += event.processedBytes;
		this.processedSegments += event.processedSegments;
		this.error = this.error || event.error;
	}
}