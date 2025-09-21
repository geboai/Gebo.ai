package ai.gebo.architecture.graphrag.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.RelationObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphRelationObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationObjectRepository;

@Service
public class GraphRelationObjectDao extends
		AbstractNeo4jKnowledgeGraphObjectDao<ai.gebo.architecture.graphrag.persistence.model.GraphRelationObject, ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationObjectRepository, RelationObject> {
	private final GraphEntityObjectDao entityDao;

	public GraphRelationObjectDao(GraphRelationObjectRepository repository, GraphEntityObjectDao entityDao) {
		super(repository);
		this.entityDao = entityDao;
	}

	@Override
	String createLogicalKey(RelationObject relation) {
		String key = relation.getClass().getName() + "-" + relation.getType().toUpperCase()
				+ entityDao.createLogicalKey(relation.getFromEntity()) + "-"
				+ entityDao.createLogicalKey(relation.getToEntity());
		return key;
	}

	@Override
	GraphRelationObject findByCacheLookup(RelationObject reconcyle, Map<String, Object> cache) {
		String key = createLogicalKey(reconcyle);
		return (GraphRelationObject) cache.get(key);
	}

	@Transactional
	@Override
	GraphRelationObject findOrCreateUncached(RelationObject reconcyle, Map<String, Object> cache) {
		String key = createLogicalKey(reconcyle);
		GraphEntityObject fromEntity = entityDao.findOrCreateMatching(reconcyle.getFromEntity(), cache, true);
		GraphEntityObject toEntity = entityDao.findOrCreateMatching(reconcyle.getToEntity(), cache, true);
		GraphRelationObject hit = null;
		List<GraphRelationObject> matches = repository.findByTypeAndFromEntityIdAndToEntityId(
				reconcyle.getType().toUpperCase(), fromEntity.getId(), toEntity.getId());
		// if found load in cache and consider it
		if (matches != null && !matches.isEmpty()) {
			hit = matches.get(0);
			cache.put(key, hit);
		}
		// if not found insert in graph db
		if (hit == null) {
			hit = new GraphRelationObject();
			hit.setId(UUID.randomUUID().toString());
			hit.setType(reconcyle.getType().toUpperCase());
			hit.setFromEntity(fromEntity);
			hit.setToEntity(toEntity);
			hit.setLongDescription(reconcyle.getLongDescription());
			hit.setAttributes(reconcyle.getAttributes());
			repository.save(hit);
			cache.put(key, hit);
		}
		return hit;
	}

}
