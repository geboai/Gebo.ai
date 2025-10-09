package ai.gebo.architecture.graphrag.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityObjectRepository;

@Service
public class GraphEntityObjectDao
		extends AbstractNeo4jKnowledgeGraphObjectDao<GraphEntityObject, GraphEntityObjectRepository, EntityObject> {

	public GraphEntityObjectDao(GraphEntityObjectRepository repository) {
		super(repository);

	}

	@Override
	GraphEntityObject createCopyOf(EntityObject reconcyle) {
		GraphEntityObject hit = null;
		hit = new GraphEntityObject();
		hit.setId(UUID.randomUUID().toString());
		hit.setType(reconcyle.getType().toUpperCase());
		hit.setName(reconcyle.getName().toUpperCase());
		hit.setLongDescription(reconcyle.getLongDescription());
		hit.setAttributes(reconcyle.getAttributes());
		hit.assignId();
		return hit;
	}

}
