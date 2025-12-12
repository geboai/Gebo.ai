package ai.gebo.llms.deepsearch.model;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import lombok.Data;

@Data
public class DeepSearchState {
	RagDocumentsCachedDaoResult semanticDaoResults = null;
	int ragDocumentsPointer = 0;
	int ragDocumentFragmentPointer = 0;

}
