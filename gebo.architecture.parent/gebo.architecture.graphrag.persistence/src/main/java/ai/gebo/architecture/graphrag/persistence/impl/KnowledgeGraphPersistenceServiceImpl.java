package ai.gebo.architecture.graphrag.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import ai.gebo.architecture.graphrag.extraction.model.RelationObject;
import ai.gebo.architecture.graphrag.persistence.IKnowledgeGraphPersistenceService;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentReference;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityInDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventInDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphRelationInDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphRelationObject;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphDocumentReferenceRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationObjectRepository;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KnowledgeGraphPersistenceServiceImpl implements IKnowledgeGraphPersistenceService {
	private final GraphDocumentReferenceRepository docReferenceRepository;
	private final GraphDocumentChunkRepository docChunkRepository;
	private final GraphEntityObjectRepository entityObjectRepository;
	private final GraphEntityInDocumentChunkRepository entityInChunkRepository;
	private final GraphEventObjectRepository eventObjectRepository;
	private final GraphEventInDocumentChunkRepository eventInChunkRepository;
	private final GraphRelationObjectRepository relationObjectRepository;
	private final GraphRelationInDocumentChunkRepository relationInChunkRepository;

	@Override
	public void knowledgeGraphDelete(GDocumentReference documentReference) {
		entityInChunkRepository.deleteByDocumentChunkDocumentCode(documentReference.getCode());
		eventInChunkRepository.deleteByDocumentChunkDocumentCode(documentReference.getCode());
		relationInChunkRepository.deleteByDocumentChunkDocumentCode(documentReference.getCode());
		docChunkRepository.deleteByDocumentCode(documentReference.getCode());
		docReferenceRepository.deleteById(documentReference.getCode());
	}

	@Override
	public void knowledgeGraphUpdate(final GDocumentReference documentReference,
			Stream<KnowledgeExtractionData> stream) {
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
			saveExtraction(entry, documentReference, cache);
		});
	}

	private void saveExtraction(KnowledgeExtractionData extraction, GDocumentReference documentReference,
			Map<String, Object> cache) {
		GraphDocumentChunk ref = new GraphDocumentChunk();
		ref.setId(extraction.getDocumentChunk().getId());
		ref.setDocumentCode(documentReference.getCode());
		ref.setKnowledgeBaseCode(documentReference.getRootKnowledgebaseCode());
		ref.setProjectCode(documentReference.getParentProjectCode());
		ref.setProjectEndpointClass(documentReference.getProjectEndpointReference().getClassName());
		ref.setProjectEndpointCode(documentReference.getProjectEndpointReference().getCode());
		ref.setText(extraction.getDocumentChunk().getText());
		ref.setMetaData(extraction.getDocumentChunk().getMetadata());
		docChunkRepository.save(ref);
		List<EntityObject> entities = extraction.getExtraction().getEntities();
		for (EntityObject entity : entities) {
			String key = entity.getClass().getName() + "-" + entity.getType().toUpperCase() + "-"
					+ entity.getName().toUpperCase();
			// check eventual cache
			GraphEntityObject hit = findMatchingEntity(entity, cache);
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
			String key = event.getClass().getName() + "-" + event.getType().toUpperCase() + "-"
					+ event.getTitle().toUpperCase();
			GraphEventObject hit = (GraphEventObject) cache.get(key);
			if (hit == null) {
				// retrieve from existing entities
				List<GraphEventObject> matches = eventObjectRepository.findByTypeAndTitle(event.getType().toUpperCase(),
						event.getTitle().toUpperCase());
				// if found load in cache and consider it
				if (matches != null && !matches.isEmpty()) {
					hit = matches.get(0);
					cache.put(key, hit);
				}
				// if not found insert in graph db
				if (hit == null) {
					hit = new GraphEventObject();
					hit.setId(UUID.randomUUID().toString());
					hit.setType(event.getType().toUpperCase());
					hit.setTitle(event.getTitle().toUpperCase());
					hit.setLongDescription(event.getLongDescription());
					hit.setAttributes(event.getAttributes());
					eventObjectRepository.save(hit);
					cache.put(key, hit);
				}
				GraphEventInDocumentChunk eventInChunk = new GraphEventInDocumentChunk();
				eventInChunk.setId(UUID.randomUUID().toString());
				eventInChunk.setDiscoveredEvent(hit);
				eventInChunk.setDocumentChunk(ref);
				eventInChunk.setConfidence(event.getConfidence());
				eventInChunk.setLongDescription(event.getLongDescription());
				eventInChunk.setType(event.getType());
				eventInChunkRepository.save(eventInChunk);
			}
		}
		List<RelationObject> relations = extraction.getExtraction().getRelations();
		for (RelationObject relation : relations) {
			GraphEntityObject fromEntity = findMatchingEntity(relation.getFromEntity(), cache);
			GraphEntityObject toEntity = findMatchingEntity(relation.getToEntity(), cache);
			if (fromEntity == null || toEntity == null)
				continue;
			String key = relation.getClass().getName() + "-" + relation.getType().toUpperCase() + "-"
					+ fromEntity.getId() + "-" + toEntity.getId();
			GraphRelationObject hit = (GraphRelationObject) cache.get(key);
			if (hit == null) {

				// retrieve from existing entities
				List<GraphRelationObject> matches = relationObjectRepository.findByTypeAndFromEntityIdAndToEntityId(
						relation.getType().toUpperCase(), fromEntity.getId(), toEntity.getId());
				// if found load in cache and consider it
				if (matches != null && !matches.isEmpty()) {
					hit = matches.get(0);
					cache.put(key, hit);
				}
				// if not found insert in graph db
				if (hit == null) {
					hit = new GraphRelationObject();
					hit.setId(UUID.randomUUID().toString());
					hit.setType(relation.getType().toUpperCase());
					hit.setFromEntity(fromEntity);
					hit.setToEntity(toEntity);
					hit.setLongDescription(relation.getLongDescription());
					hit.setAttributes(relation.getAttributes());
					relationObjectRepository.save(hit);
					cache.put(key, hit);
				}
				GraphRelationInDocumentChunk relationtInChunk = new GraphRelationInDocumentChunk();
				relationtInChunk.setId(UUID.randomUUID().toString());
				relationtInChunk.setDiscoveredRelation(hit);
				relationtInChunk.setDocumentChunk(ref);
				relationtInChunk.setConfidence(relation.getConfidence());
				relationtInChunk.setLongDescription(relation.getLongDescription());
				relationtInChunk.setType(relation.getType());
				relationInChunkRepository.save(relationtInChunk);
			}
		}
	}

	private GraphEntityObject findMatchingEntity(EntityObject entity, Map<String, Object> cache) {
		String key = entity.getClass().getName() + "-" + entity.getType().toUpperCase() + "-"
				+ entity.getName().toUpperCase();
		// check eventual cache
		GraphEntityObject hit = (GraphEntityObject) cache.get(key);
		if (hit == null) {
			// retrieve from existing entities
			List<GraphEntityObject> matches = entityObjectRepository.findByTypeAndName(entity.getType().toUpperCase(),
					entity.getName().toUpperCase());
			// if found load in cache and consider it
			if (matches != null && !matches.isEmpty()) {
				hit = matches.get(0);
				cache.put(key, hit);
			}
			// if not found insert in graph db
			if (hit == null) {
				hit = new GraphEntityObject();
				hit.setId(UUID.randomUUID().toString());
				hit.setType(entity.getType().toUpperCase());
				hit.setName(entity.getName().toUpperCase());
				hit.setLongDescription(entity.getLongDescription());
				hit.setAttributes(entity.getAttributes());
				entityObjectRepository.save(hit);
				cache.put(key, hit);
			}
		}
		return hit;
	}
}
