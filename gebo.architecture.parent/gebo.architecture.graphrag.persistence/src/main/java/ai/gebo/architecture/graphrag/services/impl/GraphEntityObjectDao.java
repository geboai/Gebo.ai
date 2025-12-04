package ai.gebo.architecture.graphrag.services.impl;

import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityObjectRepository;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
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
		hit.setType(reconcyle.getType().toUpperCase());
		hit.setName(reconcyle.getName().toUpperCase());
		hit.setLongDescription(reconcyle.getLongDescription());
		hit.setAttributes(reconcyle.getAttributes());
		hit.assignId();
		return hit;
	}

}
