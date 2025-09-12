package ai.gebo.architecture.graphrag.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ai.gebo.architecture.graphrag.extraction.model.AbstractAliasObject.EquivalenceType;
import ai.gebo.architecture.graphrag.extraction.model.EntityAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.extraction.model.EventAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.model.RelationObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphExtractionMatching;
import ai.gebo.architecture.graphrag.persistence.model.GraphRelationObject;
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
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AbstractGraphPersistenceService {
	protected final GraphDocumentReferenceRepository docReferenceRepository;
	protected final GraphDocumentChunkRepository docChunkRepository;
	protected final GraphEntityObjectRepository entityObjectRepository;
	protected final GraphEntityInDocumentChunkRepository entityInChunkRepository;
	protected final GraphEventObjectRepository eventObjectRepository;
	protected final GraphEventInDocumentChunkRepository eventInChunkRepository;
	protected final GraphRelationObjectRepository relationObjectRepository;
	protected final GraphRelationInDocumentChunkRepository relationInChunkRepository;
	protected final GraphEventAliasObjectRepository eventAliasRepository;
	protected final GraphEntityAliasObjectRepository entityAliasRepository;
	protected final GraphEntityAliasInDocumentChunkRepository entityAliasChunkRepository;
	protected final GraphEventAliasInDocumentChunkRepository eventAliasChunkRepository;

	protected GraphEntityObject findMatchingEntity(EntityObject entity, Map<String, Object> cache,
			boolean insertIfNotFound) {
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
			if (hit == null && insertIfNotFound) {
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

	protected GraphEntityAliasObject findMatchingEntityAlias(EntityAliasObject alias, Map<String, Object> cache,
			boolean insertIfNotFound) {
		GraphEntityObject referenceObject = findMatchingEntity(alias.getReferenceObject(), cache, insertIfNotFound);
		GraphEntityObject aliasObject = findMatchingEntity(alias.getAliasObject(), cache, insertIfNotFound);
		if (referenceObject == null || aliasObject == null)
			return null;
		String key = alias.getClass().getName() + "-" + referenceObject.getId() + "-" + aliasObject.getId();
		GraphEntityAliasObject hit = (GraphEntityAliasObject) cache.get(key);
		if (hit == null) {
			key = alias.getClass().getName() + "-" + aliasObject.getId() + "-" + referenceObject.getId();
			hit = (GraphEntityAliasObject) cache.get(key);
		}
		if (hit == null) {
			// retrieve from existing entities
			List<GraphEntityAliasObject> matches = entityAliasRepository
					.findByReferenceObjectIdAndAliasObjectId(referenceObject.getId(), aliasObject.getId());
			// if found load in cache and consider it
			if (matches != null && !matches.isEmpty()) {
				hit = matches.get(0);
				cache.put(key, hit);
			}
			if (hit == null) {
				matches = entityAliasRepository.findByReferenceObjectIdAndAliasObjectId(aliasObject.getId(),
						referenceObject.getId());
				if (matches != null && !matches.isEmpty()) {
					hit = matches.get(0);
					cache.put(key, hit);
				}
			}
			// if not found insert in graph db
			if (hit == null && insertIfNotFound) {
				hit = new GraphEntityAliasObject();
				hit.setId(UUID.randomUUID().toString());
				hit.setType(alias.getType().toUpperCase());
				hit.setReferenceObject(referenceObject);
				hit.setEquivalenceType(alias.getEquivalenceType() != EquivalenceType.SYNONYM
						? ai.gebo.architecture.graphrag.persistence.model.AbstractGraphAliasObject.EquivalenceType.ALIAS
						: ai.gebo.architecture.graphrag.persistence.model.AbstractGraphAliasObject.EquivalenceType.SYNONYM);
				hit.setAliasObject(aliasObject);
				hit.setLongDescription(alias.getLongDescription());
				hit.setAttributes(alias.getAttributes());
				entityAliasRepository.save(hit);
				cache.put(key, hit);
			}
		}
		return hit;
	}

	protected GraphEventObject findMatchingEvent(EventObject event, Map<String, Object> cache,
			boolean insertIfNotFound) {
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
			if (hit == null && insertIfNotFound) {
				hit = new GraphEventObject();
				hit.setId(UUID.randomUUID().toString());
				hit.setType(event.getType().toUpperCase());
				hit.setTitle(event.getTitle().toUpperCase());
				hit.setLongDescription(event.getLongDescription());
				hit.setAttributes(event.getAttributes());
				eventObjectRepository.save(hit);
				cache.put(key, hit);
			}
		}

		return hit;
	}

	protected GraphEventAliasObject findMatchingEventAlias(EventAliasObject alias, Map<String, Object> cache,
			boolean insertIfNotFound) {
		GraphEventObject referenceObject = findMatchingEvent(alias.getReferenceObject(), cache, insertIfNotFound);
		GraphEventObject aliasObject = findMatchingEvent(alias.getAliasObject(), cache, insertIfNotFound);
		if (referenceObject == null || aliasObject == null)
			return null;
		String key = alias.getClass().getName() + "-" + referenceObject.getId() + "-" + aliasObject.getId();
		GraphEventAliasObject hit = (GraphEventAliasObject) cache.get(key);
		if (hit == null) {
			key = alias.getClass().getName() + "-" + aliasObject.getId() + "-" + referenceObject.getId();
			hit = (GraphEventAliasObject) cache.get(key);
		}
		if (hit == null) {
			// retrieve from existing entities
			List<GraphEventAliasObject> matches = eventAliasRepository
					.findByReferenceObjectIdAndAliasObjectId(referenceObject.getId(), aliasObject.getId());
			// if found load in cache and consider it
			if (matches != null && !matches.isEmpty()) {
				hit = matches.get(0);
				cache.put(key, hit);
			}
			if (hit == null) {
				matches = eventAliasRepository.findByReferenceObjectIdAndAliasObjectId(aliasObject.getId(),
						referenceObject.getId());
				if (matches != null && !matches.isEmpty()) {
					hit = matches.get(0);
					cache.put(key, hit);
				}
			}
			// if not found insert in graph db
			if (hit == null && insertIfNotFound) {
				hit = new GraphEventAliasObject();
				hit.setId(UUID.randomUUID().toString());
				hit.setType(alias.getType().toUpperCase());
				hit.setEquivalenceType(alias.getEquivalenceType() != EquivalenceType.SYNONYM
						? ai.gebo.architecture.graphrag.persistence.model.AbstractGraphAliasObject.EquivalenceType.ALIAS
						: ai.gebo.architecture.graphrag.persistence.model.AbstractGraphAliasObject.EquivalenceType.SYNONYM);
				hit.setReferenceObject(referenceObject);
				hit.setAliasObject(aliasObject);
				hit.setLongDescription(alias.getLongDescription());
				hit.setAttributes(alias.getAttributes());
				eventAliasRepository.save(hit);
				cache.put(key, hit);
			}
		}
		return hit;
	}

	protected GraphRelationObject findMatchingRelation(RelationObject relation, Map<String, Object> cache,
			boolean insertIfNotFound) {
		GraphEntityObject fromEntity = findMatchingEntity(relation.getFromEntity(), cache, insertIfNotFound);
		GraphEntityObject toEntity = findMatchingEntity(relation.getToEntity(), cache, insertIfNotFound);
		if (fromEntity == null || toEntity == null)
			return null;
		String key = relation.getClass().getName() + "-" + relation.getType().toUpperCase() + "-" + fromEntity.getId()
				+ "-" + toEntity.getId();
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
			if (hit == null && insertIfNotFound) {
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
		}
		return hit;
	}

	protected GraphExtractionMatching searchMatches(LLMExtractionResult extraction) {
		GraphExtractionMatching out = null;
		final Map<String, Object> cache = new HashMap<String, Object>();
		List<GraphEntityObject> entities = extraction.getEntities().stream().map(x -> {
			return this.findMatchingEntity(x, cache, false);
		}).filter(ent -> ent != null).toList();
		List<GraphEventObject> events = extraction.getEvents().stream().map(x -> {
			return findMatchingEvent(x, cache, false);
		}).filter(y -> y != null).toList();
		List<GraphRelationObject> relations = extraction.getRelations().stream().map(x -> {
			return findMatchingRelation(x, cache, false);
		}).filter(y -> y != null).toList();
		List<GraphEntityAliasObject> entityAliases = extraction.getEntityAliases().stream().map(x -> {
			return findMatchingEntityAlias(x, cache, false);
		}).filter(y -> y != null).toList();
		List<GraphEventAliasObject> eventAliases = extraction.getEventAliases().stream().map(x -> {
			return findMatchingEventAlias(x, cache, false);
		}).filter(y -> y != null).toList();
		out = new GraphExtractionMatching(entities, events, relations, entityAliases, eventAliases);
		return out;
	}

}
