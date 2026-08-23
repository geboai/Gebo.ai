package ai.gebo.architecture.graphrag.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentReference;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionEvent;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

public interface IKnowledgeGraphPersistenceService {

	public void knowledgeGraphDelete(GDocumentReference documentReference);

	/**
	 * Erases from the knowledge graph everything extracted from the given documents.
	 * Mirrors {@link #knowledgeGraphDelete(GDocumentReference)} for a set of codes -
	 * the erasure path invoked when documents are removed
	 * ({@code GInternalDeletionMessagePayload}).
	 */
	public void knowledgeGraphDeleteByDocumentCodes(Collection<String> documentCodes);

	/** Erases everything the knowledge graph holds for a deleted knowledge base. */
	public void knowledgeGraphDeleteByKnowledgeBaseCode(String knowledgeBaseCode);

	/** Erases everything the knowledge graph holds for a deleted project. */
	public void knowledgeGraphDeleteByProjectCode(String projectCode);

	/** Erases everything the knowledge graph holds for a deleted data-source endpoint. */
	public void knowledgeGraphDeleteByProjectEndpoint(String projectEndpointClass, String projectEndpointCode);

	public GraphDocumentReference knowledgeGraphInsertDocument(GDocumentReference documentReference);

	public void knowledgeGraphInsertChunks(GDocumentReference documentReference, GraphDocumentReference ref,
			Stream<KnowledgeExtractionData> stream, Map<String, Object> cache);

	public void knowledgeGraphUpdate(GDocumentReference documentReference, Stream<KnowledgeExtractionData> stream,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer);

	public default void knowledgeGraphUpdate(GDocumentReference documentReference, List<KnowledgeExtractionData> data,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer) {
		knowledgeGraphUpdate(documentReference, data != null ? data.stream() : Stream.of(), processingUpdatesConsumer);
	}

	

	public boolean isConfigured(WorkflowContext context);

}
