package ai.gebo.llms.deepsearch.model;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class DeepSearchKnowledgebasesResultStep extends GBaseObject implements IDeepSearchResult {
	String response = null;
	Boolean searchResultsEmpty = null;
	String deepsearchCode = null;
	String dataSourceDescription = null;
	private double processPercentage=0.0;

}
