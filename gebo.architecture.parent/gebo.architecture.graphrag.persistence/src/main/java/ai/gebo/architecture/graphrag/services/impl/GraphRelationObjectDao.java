package ai.gebo.architecture.graphrag.services.impl;

import org.springframework.stereotype.Service;

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
	GraphRelationObject createCopyOf(RelationObject reconcyle) {
		GraphEntityObject fromEntity = entityDao.createCopyOf(reconcyle.getFromEntity());
		GraphEntityObject toEntity = entityDao.createCopyOf(reconcyle.getToEntity());
		GraphRelationObject hit = new GraphRelationObject();
		hit.setType(reconcyle.getType().toUpperCase());
		hit.setFromEntity(fromEntity);
		hit.setToEntity(toEntity);
		hit.setLongDescription(reconcyle.getLongDescription());
		hit.setAttributes(reconcyle.getAttributes());
		hit.assignId();
		return hit;
	}

}
