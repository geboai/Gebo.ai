package ai.gebo.architecture.graphrag.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.model.EntityAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.extraction.model.EventAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import ai.gebo.architecture.graphrag.extraction.model.RelationObject;
import ai.gebo.architecture.graphrag.extraction.model.TimeSegment;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentReference;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityAliasInDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityInDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventAliasInDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventInDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphRelationInDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphRelationObject;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionEvent;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphDocumentReferenceRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityAliasInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityAliasObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventAliasInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventAliasObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationObjectRepository;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphPersistenceService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;

@Service

public class KnowledgeGraphPersistenceServiceImpl extends AbstractGraphPersistenceService
		implements IKnowledgeGraphPersistenceService {

	public KnowledgeGraphPersistenceServiceImpl(GraphDocumentReferenceRepository docReferenceRepository,
			GraphDocumentChunkRepository docChunkRepository, GraphEntityObjectRepository entityObjectRepository,
			GraphEntityInDocumentChunkRepository entityInChunkRepository,
			GraphEventObjectRepository eventObjectRepository,
			GraphEventInDocumentChunkRepository eventInChunkRepository,
			GraphRelationObjectRepository relationObjectRepository,
			GraphRelationInDocumentChunkRepository relationInChunkRepository,
			GraphEventAliasObjectRepository eventAliasRepository,
			GraphEntityAliasObjectRepository entityAliasRepository,
			GraphEntityAliasInDocumentChunkRepository entityAliasChunkRepository,
			GraphEventAliasInDocumentChunkRepository eventAliasChunkRepository, IGraphDataExtractionService extractionService) {
		super(docReferenceRepository, docChunkRepository, entityObjectRepository, entityInChunkRepository,
				eventObjectRepository, eventInChunkRepository, relationObjectRepository, relationInChunkRepository,
				eventAliasRepository, entityAliasRepository, entityAliasChunkRepository, eventAliasChunkRepository,extractionService);

	}

	@Override
	public void knowledgeGraphDelete(GDocumentReference documentReference) {
		entityInChunkRepository.deleteByDocumentChunkDocumentCode(documentReference.getCode());
		eventInChunkRepository.deleteByDocumentChunkDocumentCode(documentReference.getCode());
		relationInChunkRepository.deleteByDocumentChunkDocumentCode(documentReference.getCode());
		docChunkRepository.deleteByDocumentCode(documentReference.getCode());
		docReferenceRepository.deleteById(documentReference.getCode());
	}

	@Override
	public void knowledgeGraphUpdate(final GDocumentReference documentReference, Stream<KnowledgeExtractionData> stream,
			Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer) {
		this.knowledgeGraphDelete(documentReference);
		GraphDocumentReference ref = new GraphDocumentReference();
		ref.setCode(documentReference.getCode());
		ref.setKnowledgeBaseCode(documentReference.getRootKnowledgebaseCode());
		ref.setProjectCode(documentReference.getParentProjectCode());
		ref.setProjectEndpointClass(documentReference.getProjectEndpointReference().getClassName());
		ref.setProjectEndpointCode(documentReference.getProjectEndpointReference().getCode());
		this.docReferenceRepository.save(ref);
		final Map<String, Object> cache = new HashMap<String, Object>();
		stream.forEach(entry -> {
			KnowledgeExtractionEvent event = saveExtraction(entry, ref, documentReference, cache);
			if (event != null)
				processingUpdatesConsumer.accept(event);
		});
	}

	private KnowledgeExtractionEvent saveExtraction(KnowledgeExtractionData extraction,GraphDocumentReference docRef,
			GDocumentReference documentReference, Map<String, Object> cache) {
		KnowledgeExtractionEvent outData = null;
		GraphDocumentChunk ref = new GraphDocumentChunk();
		ref.setChunkOf(docRef);
		ref.setId(extraction.getDocumentChunk().getId());
		ref.setDocumentCode(documentReference.getCode());
		ref.setKnowledgeBaseCode(documentReference.getRootKnowledgebaseCode());
		ref.setProjectCode(documentReference.getParentProjectCode());
		ref.setProjectEndpointClass(documentReference.getProjectEndpointReference().getClassName());
		ref.setProjectEndpointCode(documentReference.getProjectEndpointReference().getCode());
		ref.setText(extraction.getDocumentChunk().getText());
		ref.setMetaData(extraction.getDocumentChunk().getMetadata());
		Object tokenLength = ref.getMetaData() != null ? ref.getMetaData().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH)
				: null;
		if (tokenLength != null && tokenLength instanceof Number tokens) {
			outData = new KnowledgeExtractionEvent(tokens.longValue(), 0, 1, false);
		}
		docChunkRepository.save(ref);
		List<EntityObject> entities = extraction.getExtraction().getEntities();
		for (EntityObject entity : entities) {

			// check eventual cache
			GraphEntityObject hit = findMatchingEntity(entity, cache, true);
			GraphEntityInDocumentChunk entityInChunk = new GraphEntityInDocumentChunk();
			entityInChunk.setId(UUID.randomUUID().toString());
			entityInChunk.setDiscoveredEntity(hit);
			entityInChunk.setDocumentChunk(ref);
			entityInChunk.setConfidence(entity.getConfidence());
			entityInChunk.setLongDescription(entity.getLongDescription());
			entityInChunk.setType(entity.getType());
			entityInChunkRepository.save(entityInChunk);
		}
		List<EventObject> events = extraction.getExtraction().getEvents();
		for (EventObject event : events) {

			GraphEventObject hit = findMatchingEvent(event, cache, true);
			GraphEventInDocumentChunk eventInChunk = new GraphEventInDocumentChunk();
			eventInChunk.setId(UUID.randomUUID().toString());
			eventInChunk.setDiscoveredEvent(hit);
			eventInChunk.setDocumentChunk(ref);
			eventInChunk.setConfidence(event.getConfidence());
			eventInChunk.setLongDescription(event.getLongDescription());
			eventInChunk.setType(event.getType());
			TimeSegment segment = event.getTime();
			if (segment != null) {
				eventInChunk.setStartDateTime(segment.getStartDateTime());
				eventInChunk.setEndDateTime(segment.getEndDateTime());
			}
			eventInChunk.setParticipants(new ArrayList<GraphEntityObject>());
			List<EntityObject> participants = event.getParticipantEntities();
			for (EntityObject participant : participants) {
				eventInChunk.getParticipants().add(findMatchingEntity(participant, cache, true));
			}
			eventInChunkRepository.save(eventInChunk);
		}
		List<RelationObject> relations = extraction.getExtraction().getRelations();
		for (RelationObject relation : relations) {

			GraphRelationObject hit = findMatchingRelation(relation, cache, true);
			GraphRelationInDocumentChunk relationtInChunk = new GraphRelationInDocumentChunk();
			relationtInChunk.setId(UUID.randomUUID().toString());
			relationtInChunk.setDiscoveredRelation(hit);
			relationtInChunk.setDocumentChunk(ref);
			relationtInChunk.setConfidence(relation.getConfidence());
			relationtInChunk.setLongDescription(relation.getLongDescription());
			relationtInChunk.setType(relation.getType());
			relationInChunkRepository.save(relationtInChunk);
		}
		List<EntityAliasObject> entityAliases = extraction.getExtraction().getEntityAliases();
		for (EntityAliasObject entityAliasObject : entityAliases) {
			GraphEntityAliasObject alias = findMatchingEntityAlias(entityAliasObject, cache, true);
			GraphEntityAliasInDocumentChunk aliasInChunk = new GraphEntityAliasInDocumentChunk();
			aliasInChunk.setId(UUID.randomUUID().toString());
			aliasInChunk.setDocumentChunk(ref);
			aliasInChunk.setDiscoveredEntityAlias(alias);
			aliasInChunk.setConfidence(entityAliasObject.getConfidence());
			aliasInChunk.setLongDescription(alias.getLongDescription());
			aliasInChunk.setType(alias.getType());
			entityAliasChunkRepository.save(aliasInChunk);
		}
		List<EventAliasObject> eventAliases = extraction.getExtraction().getEventAliases();
		for (EventAliasObject entityAliasObject : eventAliases) {
			GraphEventAliasObject alias = findMatchingEventAlias(entityAliasObject, cache, true);
			GraphEventAliasInDocumentChunk aliasInChunk = new GraphEventAliasInDocumentChunk();
			aliasInChunk.setId(UUID.randomUUID().toString());
			aliasInChunk.setDocumentChunk(ref);
			aliasInChunk.setDiscoveredEventAlias(alias);
			aliasInChunk.setConfidence(entityAliasObject.getConfidence());
			aliasInChunk.setLongDescription(alias.getLongDescription());
			aliasInChunk.setType(alias.getType());
			eventAliasChunkRepository.save(aliasInChunk);
		}
		return outData;
	}
}
