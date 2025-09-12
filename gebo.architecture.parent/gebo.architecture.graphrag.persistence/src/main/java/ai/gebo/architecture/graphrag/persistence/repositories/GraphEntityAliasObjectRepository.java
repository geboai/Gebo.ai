package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.List;

import ai.gebo.architecture.graphrag.persistence.model.GraphEntityAliasObject;

public interface GraphEntityAliasObjectRepository extends AbstractGraphObjectRepository<GraphEntityAliasObject> {

	List<GraphEntityAliasObject> findByReferenceObjectIdAndAliasObjectId(String id, String id2);

}
