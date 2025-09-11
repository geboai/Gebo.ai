package ai.gebo.architecture.graphrag.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionEvent;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IKnowledgeGraphPersistenceService {
	
	
	public void knowledgeGraphDelete(GDocumentReference documentReference);

	public void knowledgeGraphUpdate(GDocumentReference documentReference, Stream<KnowledgeExtractionData> stream,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer);

	public default void knowledgeGraphUpdate(GDocumentReference documentReference, List<KnowledgeExtractionData> data,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer) {
		knowledgeGraphUpdate(documentReference, data != null ? data.stream() : Stream.of(), processingUpdatesConsumer);
	}

}
