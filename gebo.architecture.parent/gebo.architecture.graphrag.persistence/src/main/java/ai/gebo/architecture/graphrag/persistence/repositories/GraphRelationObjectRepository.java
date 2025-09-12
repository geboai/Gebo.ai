package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.List;

import ai.gebo.architecture.graphrag.persistence.model.GraphEntityObject;
import ai.gebo.architecture.graphrag.persistence.model.GraphRelationObject;

public interface GraphRelationObjectRepository extends AbstractGraphObjectRepository<GraphRelationObject> {

	List<GraphRelationObject> findByTypeAndFromEntityAndToEntity(String upperCase, GraphEntityObject fromEntity,
			GraphEntityObject toEntity);

	List<GraphRelationObject> findByTypeAndFromEntityIdAndToEntityId(String upperCase, String fromEntityId,
			String toEntityId);

}
