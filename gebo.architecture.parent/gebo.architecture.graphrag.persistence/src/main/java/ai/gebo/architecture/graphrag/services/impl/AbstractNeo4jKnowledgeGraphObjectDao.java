package ai.gebo.architecture.graphrag.services.impl;

import java.util.Map;

import ai.gebo.architecture.graphrag.extraction.model.AbstractGraphObject;
import ai.gebo.architecture.graphrag.persistence.repositories.AbstractGraphObjectRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractNeo4jKnowledgeGraphObjectDao<Type extends ai.gebo.architecture.graphrag.persistence.model.AbstractGraphObject, RepositoryType extends AbstractGraphObjectRepository<Type>, ReconcileObject extends AbstractGraphObject> {
	protected final RepositoryType repository;
	
	public abstract Type findOrCreateMatching(ReconcileObject reconcyle, Map<String, Object> cache, boolean insertIfNotFound);
}
