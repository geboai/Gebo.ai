package ai.gebo.architecture.graphrag.persistence.repositories;

import java.util.List;

import ai.gebo.architecture.graphrag.persistence.model.GraphEventObject;

public interface GraphEventObjectRepository extends AbstractGraphObjectRepository<GraphEventObject>{

	List<GraphEventObject> findByTypeAndTitle(String upperCase, String upperCase2);

}
