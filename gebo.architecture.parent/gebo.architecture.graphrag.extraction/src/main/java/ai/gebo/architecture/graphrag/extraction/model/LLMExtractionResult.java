package ai.gebo.architecture.graphrag.extraction.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LLMExtractionResult {
	private List<EntityObject> entities = new ArrayList<EntityObject>();
	private List<EventObject> events = new ArrayList<EventObject>();
	private List<RelationObject> relations = new ArrayList<RelationObject>();
}
