package ai.gebo.architecture.graphrag.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.AbstractAliasObject.EquivalenceType;
import ai.gebo.architecture.graphrag.extraction.model.EntityAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityAliasObjectRepository;

@Service

public class GraphEntityAliasObjectDao extends
		AbstractNeo4jKnowledgeGraphObjectDao<GraphEntityAliasObject, GraphEntityAliasObjectRepository, EntityAliasObject> {
	private final GraphEntityObjectDao entityDao;

	public GraphEntityAliasObjectDao(GraphEntityAliasObjectRepository repository, GraphEntityObjectDao entityDao) {
		super(repository);
		this.entityDao = entityDao;
	}
	@Transactional
	@Override
	public GraphEntityAliasObject findOrCreateMatching(EntityAliasObject alias, Map<String, Object> cache,
			boolean insertIfNotFound) {
		GraphEntityObject referenceObject = entityDao.findOrCreateMatching(alias.getReferenceObject(), cache,
				insertIfNotFound);
		GraphEntityObject aliasObject = entityDao.findOrCreateMatching(alias.getAliasObject(), cache, insertIfNotFound);
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
			List<GraphEntityAliasObject> matches = repository
					.findByReferenceObjectIdAndAliasObjectId(referenceObject.getId(), aliasObject.getId());
			// if found load in cache and consider it
			if (matches != null && !matches.isEmpty()) {
				hit = matches.get(0);
				cache.put(key, hit);
			}
			if (hit == null) {
				matches = repository.findByReferenceObjectIdAndAliasObjectId(aliasObject.getId(),
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
				repository.save(hit);
				cache.put(key, hit);
			}
		}
		return hit;
	}
}
