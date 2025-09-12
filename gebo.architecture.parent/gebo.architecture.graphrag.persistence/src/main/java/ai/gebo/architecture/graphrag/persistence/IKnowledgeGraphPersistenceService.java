package ai.gebo.architecture.graphrag.persistence;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionEvent;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

public interface IKnowledgeGraphPersistenceService {
	
	
	public void knowledgeGraphDelete(GDocumentReference documentReference);

	public void knowledgeGraphUpdate(GDocumentReference documentReference, Stream<KnowledgeExtractionData> stream,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer);

	public default void knowledgeGraphUpdate(GDocumentReference documentReference, List<KnowledgeExtractionData> data,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer) {
		knowledgeGraphUpdate(documentReference, data != null ? data.stream() : Stream.of(), processingUpdatesConsumer);
	}
	public boolean isConfigured();

}
