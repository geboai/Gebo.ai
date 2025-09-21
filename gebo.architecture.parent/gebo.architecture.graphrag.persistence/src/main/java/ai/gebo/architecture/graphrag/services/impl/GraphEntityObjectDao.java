package ai.gebo.architecture.graphrag.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityObjectRepository;

@Service
public class GraphEntityObjectDao
		extends AbstractNeo4jKnowledgeGraphObjectDao<GraphEntityObject, GraphEntityObjectRepository, EntityObject> {

	public GraphEntityObjectDao(GraphEntityObjectRepository repository) {
		super(repository);

	}
	@Transactional
	@Override
	public GraphEntityObject findOrCreateMatching(EntityObject reconcyle, Map<String, Object> cache,
			boolean insertIfNotFound) {
		String key = reconcyle.getClass().getName() + "-" + reconcyle.getType().toUpperCase() + "-"
				+ reconcyle.getName().toUpperCase();
		// check eventual cache
		GraphEntityObject hit = (GraphEntityObject) cache.get(key);
		if (hit == null) {
			// retrieve from existing entities
			List<GraphEntityObject> matches = repository.findByTypeAndName(reconcyle.getType().toUpperCase(),
					reconcyle.getName().toUpperCase());
			// if found load in cache and consider it
			if (matches != null && !matches.isEmpty()) {
				hit = matches.get(0);
				cache.put(key, hit);
			}
			// if not found insert in graph db
			if (hit == null && insertIfNotFound) {
				hit = new GraphEntityObject();
				hit.setId(UUID.randomUUID().toString());
				hit.setType(reconcyle.getType().toUpperCase());
				hit.setName(reconcyle.getName().toUpperCase());
				hit.setLongDescription(reconcyle.getLongDescription());
				hit.setAttributes(reconcyle.getAttributes());
				repository.save(hit);
				cache.put(key, hit);
			}
		}
		return hit;

	}

}
