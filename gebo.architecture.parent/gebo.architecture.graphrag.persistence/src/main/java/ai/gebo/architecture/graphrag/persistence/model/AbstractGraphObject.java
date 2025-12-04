package ai.gebo.architecture.graphrag.persistence.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Id;

import lombok.Data;

@Data
public abstract class AbstractGraphObject {
	@Id
	protected String id = null;
	protected String type = null;
	@CompositeProperty(prefix = "attributes")
	protected Map<String, Object> attributes = new HashMap<String, Object>();
	protected String longDescription = "";

	public abstract String computeId();

	public final void assignId() {
		this.id = computeId();
	}
}
