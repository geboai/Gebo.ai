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

	

	


	@Override
	GraphEntityAliasObject createCopyOf(EntityAliasObject alias) {
		GraphEntityAliasObject hit = new GraphEntityAliasObject();
		GraphEntityObject referenceObject = entityDao.createCopyOf(alias.getReferenceObject());
		GraphEntityObject aliasObject = entityDao.createCopyOf(alias.getAliasObject());
		hit.setType(alias.getType().toUpperCase());
		hit.setReferenceObject(referenceObject);
		hit.setAliasObject(aliasObject);
		hit.setLongDescription(alias.getLongDescription());
		hit.setAttributes(alias.getAttributes());
		hit.assignId();
		return hit;
	}
}
