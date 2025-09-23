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
