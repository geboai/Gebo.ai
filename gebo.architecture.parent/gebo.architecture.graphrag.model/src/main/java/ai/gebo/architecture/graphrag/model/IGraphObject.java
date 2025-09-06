package ai.gebo.architecture.graphrag.model;

import java.util.Map;

public interface IGraphObject {
	public String getId();

	public String getType();

	public Map<String, Object> getAttributes();

	public ISourceReference getSourceReference();

	public Double getConfidence();

}
