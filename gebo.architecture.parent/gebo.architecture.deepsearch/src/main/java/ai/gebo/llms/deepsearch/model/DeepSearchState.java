package ai.gebo.llms.deepsearch.model;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import lombok.Data;

@Data
public class DeepSearchState {
	RagDocumentsCachedDaoResult documentSearchResults = null;
	int ragDocumentsPointer = 0;
	int ragDocumentFragmentPointer = 0;
	int fragmentsCount = 0;
	int elaboratedFragmentsCount = 0;

	public double calculateProcessedPercent() {
		double total = fragmentsCount;
		double processed = elaboratedFragmentsCount;
		return total == 0.0 ? 0.0 : processed / total * 100.0;
	}

}
