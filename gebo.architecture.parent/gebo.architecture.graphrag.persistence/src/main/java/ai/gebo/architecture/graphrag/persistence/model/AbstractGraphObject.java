package ai.gebo.architecture.graphrag.persistence.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.neo4j.core.schema.Id;

import lombok.Data;

@Data
public class AbstractGraphObject {
	@Id
	private String Id = null;
	private String type = null;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private String longDescription = null;
}
