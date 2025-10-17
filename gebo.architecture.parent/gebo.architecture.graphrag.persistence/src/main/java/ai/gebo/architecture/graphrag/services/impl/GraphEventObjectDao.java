package ai.gebo.architecture.graphrag.services.impl;

import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventObjectRepository;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
@Service
public class GraphEventObjectDao
		extends AbstractNeo4jKnowledgeGraphObjectDao<GraphEventObject, GraphEventObjectRepository, EventObject> {

	public GraphEventObjectDao(GraphEventObjectRepository repository) {
		super(repository);

	}

	


	@Override
	GraphEventObject createCopyOf(EventObject event) {
		GraphEventObject hit = null;
		hit = new GraphEventObject();
		hit.setId(UUID.randomUUID().toString());
		hit.setType(event.getType().toUpperCase());
		hit.setName(event.getTitle().toUpperCase());
		hit.setLongDescription(event.getLongDescription());
		hit.setAttributes(event.getAttributes());
		hit.assignId();
		return hit;
	}
}
