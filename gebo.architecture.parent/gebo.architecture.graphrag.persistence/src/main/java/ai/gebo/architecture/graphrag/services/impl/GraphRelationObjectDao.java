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
	@Transactional
	@Override
	public GraphRelationObject findOrCreateMatching(RelationObject relation, Map<String, Object> cache,
			boolean insertIfNotFound) {
		GraphEntityObject fromEntity = entityDao.findOrCreateMatching(relation.getFromEntity(), cache,
				insertIfNotFound);
		GraphEntityObject toEntity = entityDao.findOrCreateMatching(relation.getToEntity(), cache, insertIfNotFound);
		if (fromEntity == null || toEntity == null)
			return null;
		String key = relation.getClass().getName() + "-" + relation.getType().toUpperCase() + "-" + fromEntity.getId()
				+ "-" + toEntity.getId();
		GraphRelationObject hit = (GraphRelationObject) cache.get(key);
		if (hit == null) {
			// retrieve from existing entities
			List<GraphRelationObject> matches = repository.findByTypeAndFromEntityIdAndToEntityId(
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
				repository.save(hit);
				cache.put(key, hit);
			}
		}
		return hit;
	}

}
