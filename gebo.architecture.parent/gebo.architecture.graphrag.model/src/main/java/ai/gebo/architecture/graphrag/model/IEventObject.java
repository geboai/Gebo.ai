package ai.gebo.architecture.graphrag.model;

import java.util.List;

public interface IEventObject extends IGraphObject {
	public String getTitle();

	public List<String> getParticipantEntityIds();

	public TimeSegment getTime();
}
