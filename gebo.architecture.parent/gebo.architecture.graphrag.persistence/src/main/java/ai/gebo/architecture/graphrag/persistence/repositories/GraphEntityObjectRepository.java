package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.List;

import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;

public interface GraphEntityObjectRepository extends AbstractGraphObjectRepository<GraphEntityObject> {
	public List<GraphEntityObject> findByTypeAndName(String type, String name);
}
