package ai.gebo.ranker.model;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;

public interface RankerModel {
	public RankingOutput call(RankingInput input);

	public AIDocumentsSet call(AIDocumentsSet input, String query, int topK);
}
