package ai.gebo.architecture.graphrag.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventObjectRepository;

@Service
public class GraphEventObjectDao
		extends AbstractNeo4jKnowledgeGraphObjectDao<GraphEventObject, GraphEventObjectRepository, EventObject> {

	public GraphEventObjectDao(GraphEventObjectRepository repository) {
		super(repository);

	}

	

	@Override
	String createLogicalKey(EventObject event) {
		String key = event.getClass().getName() + "-" + event.getType().toUpperCase() + "-"
				+ event.getTitle().toUpperCase();
		return key;
	}

	@Override
	GraphEventObject findByCacheLookup(EventObject reconcyle, Map<String, Object> cache) {

		return (GraphEventObject) cache.get(createLogicalKey(reconcyle));
	}
	@Transactional
	@Override
	GraphEventObject findOrCreateUncached(EventObject event, Map<String, Object> cache) {
		String key = createLogicalKey(event);
		GraphEventObject hit = null;
		List<GraphEventObject> matches = repository.findByTypeAndTitle(event.getType().toUpperCase(),
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
			repository.save(hit);
			cache.put(key, hit);
		}
		return hit;
	}
}
