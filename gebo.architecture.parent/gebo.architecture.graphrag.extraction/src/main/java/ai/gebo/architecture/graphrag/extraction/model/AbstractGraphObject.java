package ai.gebo.architecture.graphrag.extraction.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class AbstractGraphObject {
	private String type = null;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private String longDescription = null;
	private Double confidence = null;
}
