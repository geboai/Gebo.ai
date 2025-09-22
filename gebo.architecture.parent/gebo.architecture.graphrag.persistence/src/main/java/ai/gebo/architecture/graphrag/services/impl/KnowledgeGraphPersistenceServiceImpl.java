package ai.gebo.architecture.graphrag.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.EntityAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.extraction.model.EventAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import ai.gebo.architecture.graphrag.extraction.model.RelationObject;
import ai.gebo.architecture.graphrag.extraction.model.TimeSegment;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.architecture.graphrag.persistence.model.AbstractGraphObject;
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
import lombok.AllArgsConstructor;

@Service

public class KnowledgeGraphPersistenceServiceImpl extends AbstractGraphPersistenceService
		implements IKnowledgeGraphPersistenceService {

	public KnowledgeGraphPersistenceServiceImpl(GraphDocumentReferenceRepository docReferenceRepository,
			GraphDocumentChunkRepository docChunkRepository, GraphEntityObjectDao entityObjectDao,
			GraphEntityInDocumentChunkRepository entityInChunkRepository, GraphEventObjectDao eventObjectDao,
			GraphEventInDocumentChunkRepository eventInChunkRepository, GraphRelationObjectDao relationObjectDao,
			GraphRelationInDocumentChunkRepository relationInChunkRepository, GraphEventAliasObjectDao eventAliasDao,
			GraphEntityAliasObjectDao entityAliasDao,
			GraphEntityAliasInDocumentChunkRepository entityAliasChunkRepository,
			GraphEventAliasInDocumentChunkRepository eventAliasChunkRepository,
			IGraphDataExtractionService extractionService) {
		super(docReferenceRepository, docChunkRepository, entityObjectDao, entityInChunkRepository, eventObjectDao,
				eventInChunkRepository, relationObjectDao, relationInChunkRepository, eventAliasDao, entityAliasDao,
				entityAliasChunkRepository, eventAliasChunkRepository, extractionService);

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
		GraphDocumentReference ref = knowledgeGraphInsertDocument(documentReference);
		knowledgeGraphInsertChunks(documentReference, ref, stream, processingUpdatesConsumer, new HashMap<>());
	}

	@Override
	public KnowledgeExtractionEvent saveExtraction(KnowledgeExtractionData extraction, GraphDocumentReference docRef,
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
			GraphEntityObject hit = findOrCreateMatchingEntity(entity, cache, true);
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

			GraphEventObject hit = findOrCreateMatchingEvent(event, cache, true);
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
				eventInChunk.getParticipants().add(findOrCreateMatchingEntity(participant, cache, true));
			}
			eventInChunkRepository.save(eventInChunk);
		}
		List<RelationObject> relations = extraction.getExtraction().getRelations();
		for (RelationObject relation : relations) {

			GraphRelationObject hit = findOrCreateMatchingRelation(relation, cache, true);
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
			GraphEntityAliasObject alias = findOrCreateMatchingEntityAlias(entityAliasObject, cache, true);
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
			GraphEventAliasObject alias = findOrCreateMatchingEventAlias(entityAliasObject, cache, true);
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

	@Override
	public GraphDocumentReference knowledgeGraphInsertDocument(GDocumentReference documentReference) {
		GraphDocumentReference ref = new GraphDocumentReference();
		ref.setCode(documentReference.getCode());
		ref.setKnowledgeBaseCode(documentReference.getRootKnowledgebaseCode());
		ref.setProjectCode(documentReference.getParentProjectCode());
		ref.setProjectEndpointClass(documentReference.getProjectEndpointReference().getClassName());
		ref.setProjectEndpointCode(documentReference.getProjectEndpointReference().getCode());
		this.docReferenceRepository.save(ref);
		return ref;
	}

	@AllArgsConstructor
	class GraphObjectsMap<Neo4JType extends AbstractGraphObject, ExtractedType extends ai.gebo.architecture.graphrag.extraction.model.AbstractGraphObject>
			extends HashMap<String, GraphObjectReference<Neo4JType, ExtractedType>> {
		private final Function<ExtractedType, String> keyExtractor;
		private final BiFunction<ExtractedType, Map<String, Object>, Neo4JType> cacheLookup;
		private final Map<String, Object> cache;

		public void add(ExtractedType object, String chunkId) {
			String key = keyExtractor.apply(object);
			GraphObjectReference<Neo4JType, ExtractedType> data = this.get(key);
			if (data == null) {
				data = new GraphObjectReference<>();
				data.setLogicKey(key);
				data.setExtractedObject(object);
				this.put(key, data);
			}
			if (data.getGraphObject() == null) {
				data.setGraphObject(cacheLookup.apply(data.getExtractedObject(), cache));
			}
			data.getChunkIds().put(chunkId, object);
		}

		public void add(List<ExtractedType> objects, String chunkId) {
			objects.forEach(x -> {
				this.add(x, chunkId);
			});
		}
	}

	class GraphSaveStructure {
		final Map<String, Object> cache;
		final GraphObjectsMap<GraphEntityObject, EntityObject> entities;
		final GraphObjectsMap<GraphEventObject, EventObject> events;
		final GraphObjectsMap<GraphRelationObject, RelationObject> relations;
		final GraphObjectsMap<GraphEntityAliasObject, EntityAliasObject> entityAlias;
		final GraphObjectsMap<GraphEventAliasObject, EventAliasObject> eventsAlias;
		final Map<String, GraphDocumentChunk> chunks = new HashMap<>();

		GraphSaveStructure(Map<String, Object> _cache) {
			this.cache = _cache;
			entities = new GraphObjectsMap<>((x) -> entityObjectDao.createLogicalKey(x),
					(x, dcache) -> entityObjectDao.findByCacheLookup(x, dcache), this.cache);
			events = new GraphObjectsMap<>((x) -> eventObjectDao.createLogicalKey(x),
					(x, dcache) -> eventObjectDao.findByCacheLookup(x, dcache), this.cache);
			relations = new GraphObjectsMap<>((x) -> relationObjectDao.createLogicalKey(x),
					(x, dcache) -> relationObjectDao.findByCacheLookup(x, dcache), this.cache);
			entityAlias = new GraphObjectsMap<>((x) -> entityAliasDao.createLogicalKey(x),
					(x, dcache) -> entityAliasDao.findByCacheLookup(x, dcache), this.cache);
			eventsAlias = new GraphObjectsMap<>((x) -> eventAliasDao.createLogicalKey(x),
					(x, dcache) -> eventAliasDao.findByCacheLookup(x, dcache), this.cache);

		}

	}

	@Override
	public void knowledgeGraphInsertChunks(GDocumentReference documentReference, GraphDocumentReference ref,
			Stream<KnowledgeExtractionData> stream, Consumer<KnowledgeExtractionEvent> processingUpdatesConsumer,
			Map<String, Object> cache) {
		final GraphSaveStructure saveStructure = new GraphSaveStructure(cache);
		stream.forEach(data -> {
			final String chunkId = data.getDocumentChunk().getId();
			saveStructure.entities.add(data.getExtraction().getEntities(), chunkId);
			saveStructure.events.add(data.getExtraction().getEvents(), chunkId);
			data.getExtraction().getEvents().stream().forEach(x -> {
				saveStructure.entities.add(x.getParticipantEntities(), chunkId);
			});
			saveStructure.relations.add(data.getExtraction().getRelations(), chunkId);
			data.getExtraction().getRelations().stream().forEach(x -> {
				saveStructure.entities.add(x.getFromEntity(), chunkId);
				saveStructure.entities.add(x.getToEntity(), chunkId);
			});
			saveStructure.entityAlias.add(data.getExtraction().getEntityAliases(), chunkId);
			data.getExtraction().getEntityAliases().stream().forEach(x -> {
				saveStructure.entities.add(x.getAliasObject(), chunkId);
				saveStructure.entities.add(x.getReferenceObject(), chunkId);
			});
			saveStructure.eventsAlias.add(data.getExtraction().getEventAliases(), chunkId);
			data.getExtraction().getEventAliases().stream().forEach(x -> {
				saveStructure.events.add(x.getAliasObject(), chunkId);
				saveStructure.events.add(x.getReferenceObject(), chunkId);
			});
			GraphDocumentChunk chunk = new GraphDocumentChunk();
			chunk.setId(chunkId);
			chunk.setDocumentCode(documentReference.getCode());
			chunk.setKnowledgeBaseCode(documentReference.getRootKnowledgebaseCode());
			chunk.setProjectCode(documentReference.getParentProjectCode());
			chunk.setMetaData(data.getDocumentChunk().getMetadata());
			chunk.setText(data.getDocumentChunk().getText());
			chunk.setProjectEndpointCode(documentReference.getProjectEndpointReference().getCode());
			chunk.setProjectEndpointClass(documentReference.getProjectEndpointReference().getClassName());
			chunk.setChunkOf(ref);
			long processedTokens = 0l;
			long processedBytes = 0l;
			long processedSegments = 1l;
			if (chunk.getMetaData() != null && chunk.getMetaData().containsKey(DocumentMetaInfos.GEBO_BYTES_LENGTH)
					&& chunk.getMetaData().get(DocumentMetaInfos.GEBO_BYTES_LENGTH) instanceof Number bytesSize) {
				processedBytes += bytesSize.longValue();
			}
			if (chunk.getMetaData() != null && chunk.getMetaData().containsKey(DocumentMetaInfos.GEBO_TOKEN_LENGTH)
					&& chunk.getMetaData().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH) instanceof Number tokensSize) {
				processedTokens += tokensSize.longValue();
			}
			boolean error = false;
			KnowledgeExtractionEvent event = new KnowledgeExtractionEvent(processedTokens, processedBytes,
					processedSegments, error);
			processingUpdatesConsumer.accept(event);
			saveStructure.chunks.put(chunkId, chunk);
		});
		reconcileObjects(saveStructure, cache);
	}

	static boolean notInCache(GraphObjectReference reference) {
		return reference.getGraphObject() == null;
	}

	void reconcileObjects(final GraphSaveStructure saveStructure, final Map<String, Object> cache) {
		if (saveStructure.chunks.isEmpty())
			return;
		List<GraphObjectReference<GraphEntityObject, EntityObject>> entitiesToReconcile = saveStructure.entities
				.values().stream().filter(KnowledgeGraphPersistenceServiceImpl::notInCache).toList();
		List<GraphObjectReference<GraphEventObject, EventObject>> eventsToReconcile = saveStructure.events.values()
				.stream().filter(KnowledgeGraphPersistenceServiceImpl::notInCache).toList();
		List<GraphObjectReference<GraphRelationObject, RelationObject>> relationsToReconcile = saveStructure.relations
				.values().stream().filter(KnowledgeGraphPersistenceServiceImpl::notInCache).toList();
		List<GraphObjectReference<GraphEntityAliasObject, EntityAliasObject>> entityAliasToReconcile = saveStructure.entityAlias
				.values().stream().filter(KnowledgeGraphPersistenceServiceImpl::notInCache).toList();
		List<GraphObjectReference<GraphEventAliasObject, EventAliasObject>> eventsAliasToReconcile = saveStructure.eventsAlias
				.values().stream().filter(KnowledgeGraphPersistenceServiceImpl::notInCache).toList();
		// merge objects by reference so on next step the GraphObjectReference have
		// conciled neo4j entry and its logic extraction info
		super.entityObjectDao.merge(entitiesToReconcile, cache);
		super.eventObjectDao.merge(eventsToReconcile, cache);
		super.relationObjectDao.merge(relationsToReconcile, cache);
		super.entityAliasDao.merge(entityAliasToReconcile, cache);
		super.eventAliasDao.merge(eventsAliasToReconcile, cache);
		saveChunks(saveStructure);
		saveEntityChunksRelated(saveStructure);
		saveRelationChunksRelated(saveStructure);
		saveEventsChunkRelated(saveStructure);
		saveEntitiesAliasChunksRelated(saveStructure);
		saveEventsAliasChunkRelated(saveStructure);

	}

	private void saveEventsAliasChunkRelated(GraphSaveStructure saveStructure) {
		final List<GraphEventAliasInDocumentChunk> aliasInChunk = new ArrayList<>();
		Set<Entry<String, GraphObjectReference<GraphEventAliasObject, EventAliasObject>>> entries = saveStructure.eventsAlias
				.entrySet();
		for (Entry<String, GraphObjectReference<GraphEventAliasObject, EventAliasObject>> entry : entries) {
			Set<Entry<String, EventAliasObject>> chunkPresences = entry.getValue().getChunkIds().entrySet();
			for (Entry<String, EventAliasObject> chunkPresence : chunkPresences) {
				GraphEventAliasInDocumentChunk dc = new GraphEventAliasInDocumentChunk();
				dc.setId(UUID.randomUUID().toString());
				dc.setAttributes(chunkPresence.getValue().getAttributes());
				dc.setConfidence(chunkPresence.getValue().getConfidence());
				dc.setLongDescription(chunkPresence.getValue().getLongDescription());
				dc.setDiscoveredEventAlias(entry.getValue().getGraphObject());
				dc.setDocumentChunk(saveStructure.chunks.get(chunkPresence.getKey()));
				dc.setType(chunkPresence.getValue().getType());
				aliasInChunk.add(dc);
			}
		}
		if (!aliasInChunk.isEmpty())
			this.eventAliasChunkRepository.saveAll(aliasInChunk);
	}

	@Transactional
	private void saveChunks(GraphSaveStructure saveStructure) {
		Collection<GraphDocumentChunk> chunks = saveStructure.chunks.values();
		this.docChunkRepository.saveAll(chunks);
	}

	@Transactional
	private void saveEntitiesAliasChunksRelated(GraphSaveStructure saveStructure) {
		final List<GraphEntityAliasInDocumentChunk> aliasInChunk = new ArrayList<>();
		Set<Entry<String, GraphObjectReference<GraphEntityAliasObject, EntityAliasObject>>> entries = saveStructure.entityAlias
				.entrySet();
		for (Entry<String, GraphObjectReference<GraphEntityAliasObject, EntityAliasObject>> entry : entries) {
			Set<Entry<String, EntityAliasObject>> chunkPresences = entry.getValue().getChunkIds().entrySet();
			for (Entry<String, EntityAliasObject> chunkPresence : chunkPresences) {
				GraphEntityAliasInDocumentChunk dc = new GraphEntityAliasInDocumentChunk();
				dc.setId(UUID.randomUUID().toString());
				dc.setAttributes(chunkPresence.getValue().getAttributes());
				dc.setConfidence(chunkPresence.getValue().getConfidence());
				dc.setLongDescription(chunkPresence.getValue().getLongDescription());
				dc.setDiscoveredEntityAlias(entry.getValue().getGraphObject());
				dc.setDocumentChunk(saveStructure.chunks.get(chunkPresence.getKey()));
				dc.setType(chunkPresence.getValue().getType());
				aliasInChunk.add(dc);
			}
		}
		if (!aliasInChunk.isEmpty())
			this.entityAliasChunkRepository.saveAll(aliasInChunk);
	}

	@Transactional
	private void saveEventsChunkRelated(GraphSaveStructure saveStructure) {

		final List<GraphEventInDocumentChunk> eventInChunk = new ArrayList<>();
		Set<Entry<String, GraphObjectReference<GraphEventObject, EventObject>>> entries = saveStructure.events
				.entrySet();
		for (Entry<String, GraphObjectReference<GraphEventObject, EventObject>> entry : entries) {
			Set<Entry<String, EventObject>> chunkPresences = entry.getValue().getChunkIds().entrySet();
			for (Entry<String, EventObject> chunkPresence : chunkPresences) {
				GraphEventInDocumentChunk dc = new GraphEventInDocumentChunk();
				dc.setId(UUID.randomUUID().toString());
				dc.setAttributes(chunkPresence.getValue().getAttributes());
				dc.setConfidence(chunkPresence.getValue().getConfidence());
				dc.setLongDescription(chunkPresence.getValue().getLongDescription());
				dc.setDiscoveredEvent(entry.getValue().getGraphObject());
				dc.setParticipants(entry.getValue().getExtractedObject().getParticipantEntities().stream()
						.map(x -> entityObjectDao.findByCacheLookup(x, saveStructure.cache)).toList());
				dc.setDocumentChunk(saveStructure.chunks.get(chunkPresence.getKey()));
				dc.setType(chunkPresence.getValue().getType());
				eventInChunk.add(dc);
			}
		}
		if (!eventInChunk.isEmpty())
			this.eventInChunkRepository.saveAll(eventInChunk);
	}

	@Transactional
	private void saveRelationChunksRelated(GraphSaveStructure saveStructure) {
		final List<GraphRelationInDocumentChunk> relationInChunk = new ArrayList<>();
		Set<Entry<String, GraphObjectReference<GraphRelationObject, RelationObject>>> entries = saveStructure.relations
				.entrySet();
		for (Entry<String, GraphObjectReference<GraphRelationObject, RelationObject>> entry : entries) {
			Set<Entry<String, RelationObject>> chunkPresences = entry.getValue().getChunkIds().entrySet();
			for (Entry<String, RelationObject> chunkPresence : chunkPresences) {
				GraphRelationInDocumentChunk dc = new GraphRelationInDocumentChunk();
				dc.setId(UUID.randomUUID().toString());
				dc.setAttributes(chunkPresence.getValue().getAttributes());
				dc.setConfidence(chunkPresence.getValue().getConfidence());
				dc.setLongDescription(chunkPresence.getValue().getLongDescription());
				dc.setDiscoveredRelation(entry.getValue().getGraphObject());
				dc.setDocumentChunk(saveStructure.chunks.get(chunkPresence.getKey()));
				dc.setType(chunkPresence.getValue().getType());
				relationInChunk.add(dc);
			}
		}
		if (!relationInChunk.isEmpty())
			this.relationInChunkRepository.saveAll(relationInChunk);
	}

	@Transactional
	private void saveEntityChunksRelated(GraphSaveStructure saveStructure) {
		final List<GraphEntityInDocumentChunk> entityInChunk = new ArrayList<>();
		Set<Entry<String, GraphObjectReference<GraphEntityObject, EntityObject>>> entries = saveStructure.entities
				.entrySet();
		for (Entry<String, GraphObjectReference<GraphEntityObject, EntityObject>> entry : entries) {
			Set<Entry<String, EntityObject>> chunkPresences = entry.getValue().getChunkIds().entrySet();
			for (Entry<String, EntityObject> chunkPresence : chunkPresences) {
				GraphEntityInDocumentChunk dc = new GraphEntityInDocumentChunk();
				dc.setId(UUID.randomUUID().toString());
				dc.setAttributes(chunkPresence.getValue().getAttributes());
				dc.setConfidence(chunkPresence.getValue().getConfidence());
				dc.setLongDescription(chunkPresence.getValue().getLongDescription());
				dc.setDiscoveredEntity(entry.getValue().getGraphObject());
				dc.setDocumentChunk(saveStructure.chunks.get(chunkPresence.getKey()));
				dc.setType(chunkPresence.getValue().getType());
				entityInChunk.add(dc);
			}
		}
		if (!entityInChunk.isEmpty())
			this.entityInChunkRepository.saveAll(entityInChunk);

	}
}
