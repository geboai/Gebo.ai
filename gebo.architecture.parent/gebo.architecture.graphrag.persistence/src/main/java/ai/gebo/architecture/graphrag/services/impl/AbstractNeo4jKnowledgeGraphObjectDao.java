package ai.gebo.architecture.graphrag.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import ai.gebo.architecture.graphrag.extraction.model.AbstractGraphObject;
import ai.gebo.architecture.graphrag.persistence.repositories.AbstractGraphObjectRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractNeo4jKnowledgeGraphObjectDao<Type extends ai.gebo.architecture.graphrag.persistence.model.AbstractGraphObject, RepositoryType extends AbstractGraphObjectRepository<Type>, ReconcileObject extends AbstractGraphObject> {
	protected final RepositoryType repository;

	abstract Type createCopyOf(ReconcileObject object);

	@Transactional
	public void merge(List<GraphObjectReference<Type, ReconcileObject>> data, Map<String, Object> cache) {
		final Map<String, Type> byIds = new HashMap<>();
		data.stream().forEach(x -> {
			byIds.put(x.getGraphObject().getId(), x.getGraphObject());
		});
		List<Type> found = repository.findAllById(byIds.keySet());
		found.stream().forEach(x -> {
			byIds.remove(x.getId());
		});
		if (!byIds.isEmpty()) {
			repository.saveAll(byIds.values());
		}
	}
}
