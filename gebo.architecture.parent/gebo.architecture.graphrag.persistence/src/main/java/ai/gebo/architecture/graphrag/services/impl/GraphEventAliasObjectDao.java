package ai.gebo.architecture.graphrag.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.model.EventAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventAliasObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventAliasObjectRepository;

@Service
public class GraphEventAliasObjectDao extends
		AbstractNeo4jKnowledgeGraphObjectDao<GraphEventAliasObject, GraphEventAliasObjectRepository, EventAliasObject> {
	private final GraphEventObjectDao eventDao;

	public GraphEventAliasObjectDao(GraphEventAliasObjectRepository repository, GraphEventObjectDao eventDao) {
		super(repository);
		this.eventDao = eventDao;

	}

	@Override
	GraphEventAliasObject createCopyOf(EventAliasObject alias) {
		GraphEventObject referenceObject = eventDao.createCopyOf(alias.getReferenceObject());
		GraphEventObject aliasObject = eventDao.createCopyOf(alias.getAliasObject());
		GraphEventAliasObject hit = null;
		hit = new GraphEventAliasObject();
		hit.setType(alias.getType().toUpperCase());
		hit.setReferenceObject(referenceObject);
		hit.setAliasObject(aliasObject);
		hit.setLongDescription(alias.getLongDescription());
		hit.setAttributes(alias.getAttributes());
		hit.assignId();
		return hit;
	}

}
