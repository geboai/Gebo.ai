package ai.gebo.ranker.model;

public interface RankerModel {
	public RankingOutput call(RankingInput input);
}
