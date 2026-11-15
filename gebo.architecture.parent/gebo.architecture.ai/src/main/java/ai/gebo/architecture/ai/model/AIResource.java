package ai.gebo.architecture.ai.model;

import java.util.Map;

import lombok.Data;

@Data
public class AIResource {
	private AIResourceType resourceType = null;
	private String id, description;
	private Map<String, Object> metaData = null;
}
