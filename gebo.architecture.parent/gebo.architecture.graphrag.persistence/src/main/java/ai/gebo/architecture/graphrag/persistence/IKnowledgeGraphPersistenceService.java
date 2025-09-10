package ai.gebo.architecture.graphrag.persistence;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

public interface IKnowledgeGraphPersistenceService {
	

	public void knowledgeGraphDelete(GDocumentReference documentReference);

	public Consumer<KnowledgeExtractionData> knowledgeGraphUpdater(GDocumentReference documentReference);

	public default void knowledgeGraphUpdate(GDocumentReference documentReference, Stream<KnowledgeExtractionData> data) {
		Consumer<KnowledgeExtractionData> consumer = knowledgeGraphUpdater(documentReference);
		knowledgeGraphDelete(documentReference);
		data.forEach(consumer);
	}

	public default void knowledgeGraphUpdate(GDocumentReference documentReference, List<KnowledgeExtractionData> data) {
		knowledgeGraphUpdate(documentReference, data != null ? data.stream() : Stream.of());
	}

}
