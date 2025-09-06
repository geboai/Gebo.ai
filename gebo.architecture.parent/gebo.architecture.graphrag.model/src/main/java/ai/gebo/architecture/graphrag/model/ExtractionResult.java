package ai.gebo.architecture.graphrag.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ExtractionResult {
	private List<IEntityObject> entities = new ArrayList<IEntityObject>();
	private List<IEventObject> events = new ArrayList<IEventObject>();
	private List<IRelationObject> relations = new ArrayList<IRelationObject>();
}
