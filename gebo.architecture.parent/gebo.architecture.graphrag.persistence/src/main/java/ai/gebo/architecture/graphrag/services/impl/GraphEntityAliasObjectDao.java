package ai.gebo.architecture.graphrag.services.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.model.EntityAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityAliasObjectRepository;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
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
