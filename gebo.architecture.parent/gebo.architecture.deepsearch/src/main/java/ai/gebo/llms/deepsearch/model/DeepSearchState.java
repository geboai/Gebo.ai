package ai.gebo.llms.deepsearch.model;

import java.util.HashMap;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import lombok.Data;

@Data
public class DeepSearchState {
	public static enum DeepSearchPhase {
		BEFORE_KNOWLEDGE_BASE_SEARCH, KNOWLEDGE_BASE_SEARCH, AFTER_KNOWLEDGE_BASE_SEARCH
	}

	DeepSearchPhase phase = DeepSearchPhase.BEFORE_KNOWLEDGE_BASE_SEARCH;
	RagDocumentsCachedDaoResult documentSearchResults = null;
	String currentDataSourceHandlerRunning = null;
	int ragDocumentsPointer = 0;
	int ragDocumentFragmentPointer = 0;
	int fragmentsCount = 0;
	int elaboratedFragmentsCount = 0;
	HashMap<String, Object> dataSourcesStatus = new HashMap<String, Object>();

	public double calculateProcessedPercent() {
		double total = fragmentsCount;
		double processed = elaboratedFragmentsCount;
		return total == 0.0 ? 0.0 : processed / total * 100.0;
	}

}
