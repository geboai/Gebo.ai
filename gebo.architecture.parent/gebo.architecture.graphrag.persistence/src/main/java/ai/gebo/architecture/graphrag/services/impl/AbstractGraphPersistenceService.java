package ai.gebo.architecture.graphrag.services.impl;

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
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
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
	protected final GraphEntityObjectDao entityObjectDao;
	protected final GraphEntityInDocumentChunkRepository entityInChunkRepository;
	protected final GraphEventObjectDao eventObjectDao;
	protected final GraphEventInDocumentChunkRepository eventInChunkRepository;
	protected final GraphRelationObjectDao relationObjectDao;
	protected final GraphRelationInDocumentChunkRepository relationInChunkRepository;
	protected final GraphEventAliasObjectDao eventAliasDao;
	protected final GraphEntityAliasObjectDao entityAliasDao;
	protected final GraphEntityAliasInDocumentChunkRepository entityAliasChunkRepository;
	protected final GraphEventAliasInDocumentChunkRepository eventAliasChunkRepository;
	protected final IGraphDataExtractionService extractionService;

	protected GraphEntityObject findOrCreateMatchingEntity(EntityObject entity, Map<String, Object> cache,
			boolean insertIfNotFound) {
		return this.entityObjectDao.findOrCreateMatching(entity, cache, insertIfNotFound);
	}

	protected GraphEntityAliasObject findOrCreateMatchingEntityAlias(EntityAliasObject alias, Map<String, Object> cache,
			boolean insertIfNotFound) {
		return this.entityAliasDao.findOrCreateMatching(alias, cache, insertIfNotFound);
	}

	protected GraphEventObject findOrCreateMatchingEvent(EventObject event, Map<String, Object> cache,
			boolean insertIfNotFound) {
		return eventObjectDao.findOrCreateMatching(event, cache, insertIfNotFound);
	}

	protected GraphEventAliasObject findOrCreateMatchingEventAlias(EventAliasObject alias, Map<String, Object> cache,
			boolean insertIfNotFound) {
		return eventAliasDao.findOrCreateMatching(alias, cache, insertIfNotFound);
	}

	protected GraphRelationObject findOrCreateMatchingRelation(RelationObject relation, Map<String, Object> cache,
			boolean insertIfNotFound) {
		return relationObjectDao.findOrCreateMatching(relation, cache, insertIfNotFound);
	}

	protected GraphExtractionMatching searchMatches(LLMExtractionResult extraction) {
		GraphExtractionMatching out = null;
		final Map<String, Object> cache = new HashMap<String, Object>();
		List<GraphEntityObject> entities = extraction.getEntities().stream().map(x -> {
			return this.findOrCreateMatchingEntity(x, cache, false);
		}).filter(ent -> ent != null).toList();
		List<GraphEventObject> events = extraction.getEvents().stream().map(x -> {
			return findOrCreateMatchingEvent(x, cache, false);
		}).filter(y -> y != null).toList();
		List<GraphRelationObject> relations = extraction.getRelations().stream().map(x -> {
			return findOrCreateMatchingRelation(x, cache, false);
		}).filter(y -> y != null).toList();
		List<GraphEntityAliasObject> entityAliases = extraction.getEntityAliases().stream().map(x -> {
			return findOrCreateMatchingEntityAlias(x, cache, false);
		}).filter(y -> y != null).toList();
		List<GraphEventAliasObject> eventAliases = extraction.getEventAliases().stream().map(x -> {
			return findOrCreateMatchingEventAlias(x, cache, false);
		}).filter(y -> y != null).toList();
		out = new GraphExtractionMatching(entities, events, relations, entityAliases, eventAliases);
		return out;
	}

	public boolean isConfigured() {

		return extractionService.isConfigured();
	}

}
