package ai.gebo.architecture.graphrag.extraction.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EventObject extends AbstractGraphObject {
	private String title = null;
	private List<EntityObject> participantEntities = new ArrayList<EntityObject>();
	private TimeSegment time = null;
}
