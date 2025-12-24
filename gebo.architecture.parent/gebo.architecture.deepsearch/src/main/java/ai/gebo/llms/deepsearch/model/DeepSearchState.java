package ai.gebo.llms.deepsearch.model;

import java.util.HashMap;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import lombok.Data;

@Data
public class DeepSearchState {
	private DeepSearchPhase phase = DeepSearchPhase.BEFORE_KNOWLEDGE_BASE_SEARCH;
	private RagDocumentsCachedDaoResult documentSearchResults = null;
	private String currentDataSourceHandlerRunning = null;
	private int ragDocumentsPointer = 0;
	private int ragDocumentFragmentPointer = 0;
	private int fragmentsCount = 0;
	private int elaboratedFragmentsCount = 0;
	private HashMap<String, Object> dataSourcesStatus = new HashMap<String, Object>();
	private String consolidatedResult = null;
	private int dataSourceAlreadyConsolidatedIndex = 0;

	public double calculateProcessedPercent() {
		double total = fragmentsCount;
		double processed = elaboratedFragmentsCount;
		return total == 0.0 ? 0.0 : processed / total * 100.0;
	}

}
