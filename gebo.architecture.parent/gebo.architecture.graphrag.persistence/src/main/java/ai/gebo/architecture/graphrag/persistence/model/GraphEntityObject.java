package ai.gebo.architecture.graphrag.persistence.model;

import org.springframework.data.neo4j.core.schema.Node;

import ai.gebo.architecture.graphrag.services.FastHashUtil;
import lombok.Data;

@Node("entity_object")
@Data
public class GraphEntityObject extends AbstractGraphObject {
	private String name = null;

	@Override
	public String computeId() {
		if (name == null || type == null)
			throw new RuntimeException("Name or Type must not be null");

		return FastHashUtil.hash(type, name);
	}
}
