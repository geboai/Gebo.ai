package ai.gebo.architecture.graphrag.persistence;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IKnowledgeGraphPersistenceService {
	@AllArgsConstructor
	@Getter
	public static class KnowledgeExtractionEvent {
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

	public void knowledgeGraphDelete(GDocumentReference documentReference);

	public void knowledgeGraphUpdate(GDocumentReference documentReference, Stream<KnowledgeExtractionData> stream,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer);

	public default void knowledgeGraphUpdate(GDocumentReference documentReference, List<KnowledgeExtractionData> data,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer) {
		knowledgeGraphUpdate(documentReference, data != null ? data.stream() : Stream.of(), processingUpdatesConsumer);
	}

}
