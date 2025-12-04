package ai.gebo.architecture.graphrag.extraction.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class AbstractGraphObject {
	private String type = null;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private List<String> chunkIds = new ArrayList<>();
	private String longDescription = null;
	private Double confidence = null;
}
