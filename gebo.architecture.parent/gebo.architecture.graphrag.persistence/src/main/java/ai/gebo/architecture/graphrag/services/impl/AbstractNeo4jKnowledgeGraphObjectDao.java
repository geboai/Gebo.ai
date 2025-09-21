package ai.gebo.architecture.graphrag.services.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.AbstractGraphObject;
import ai.gebo.architecture.graphrag.persistence.repositories.AbstractGraphObjectRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractNeo4jKnowledgeGraphObjectDao<Type extends ai.gebo.architecture.graphrag.persistence.model.AbstractGraphObject, RepositoryType extends AbstractGraphObjectRepository<Type>, ReconcileObject extends AbstractGraphObject> {
	protected final RepositoryType repository;

	abstract String createLogicalKey(ReconcileObject reconcyle);

	abstract Type findByCacheLookup(ReconcileObject reconcyle, Map<String, Object> cache);

	@Transactional
	abstract Type findOrCreateUncached(ReconcileObject reconcyle, Map<String, Object> cache);

	public final Type findOrCreateMatching(ReconcileObject reconcyle, Map<String, Object> cache,
			boolean insertIfNotFound) {
		String key = createLogicalKey(reconcyle);
		Type result = findByCacheLookup(reconcyle, cache);
		if (result == null && insertIfNotFound) {
			result = findOrCreateUncached(reconcyle, cache);
			cache.put(key, result);
		}
		return result;
	}
}
