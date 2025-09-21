package ai.gebo.architecture.graphrag.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.EventAliasObject;
import ai.gebo.architecture.graphrag.extraction.model.AbstractAliasObject.EquivalenceType;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventAliasObjectRepository;

@Service
public class GraphEventAliasObjectDao extends
		AbstractNeo4jKnowledgeGraphObjectDao<GraphEventAliasObject, GraphEventAliasObjectRepository, EventAliasObject> {
	private final GraphEventObjectDao eventDao;

	public GraphEventAliasObjectDao(GraphEventAliasObjectRepository repository, GraphEventObjectDao eventDao) {
		super(repository);
		this.eventDao = eventDao;

	}

	

	@Override
	String createLogicalKey(EventAliasObject alias) {
		String key = alias.getClass().getName() + "-" + eventDao.createLogicalKey(alias.getAliasObject()) + "-"
				+ eventDao.createLogicalKey(alias.getReferenceObject());
		return key;
	}

	@Override
	GraphEventAliasObject findByCacheLookup(EventAliasObject reconcyle, Map<String, Object> cache) {
		String key = createLogicalKey(reconcyle);
		return (GraphEventAliasObject) cache.get(key);
	}
	@Transactional
	@Override
	GraphEventAliasObject findOrCreateUncached(EventAliasObject alias, Map<String, Object> cache) {
		String key = createLogicalKey(alias);
		GraphEventObject referenceObject = eventDao.findOrCreateMatching(alias.getReferenceObject(), cache, true);
		GraphEventObject aliasObject = eventDao.findOrCreateMatching(alias.getAliasObject(), cache, true);

		GraphEventAliasObject hit = null;

		// retrieve from existing entities
		List<GraphEventAliasObject> matches = repository
				.findByReferenceObjectIdAndAliasObjectId(referenceObject.getId(), aliasObject.getId());
		// if found load in cache and consider it
		if (matches != null && !matches.isEmpty()) {
			hit = matches.get(0);
			cache.put(key, hit);
		}
		if (hit == null) {
			matches = repository.findByReferenceObjectIdAndAliasObjectId(aliasObject.getId(), referenceObject.getId());
			if (matches != null && !matches.isEmpty()) {
				hit = matches.get(0);
				cache.put(key, hit);
			}
		}
		// if not found insert in graph db
		if (hit == null) {
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
			repository.save(hit);
			cache.put(key, hit);
		}
		return hit;
	}

}
