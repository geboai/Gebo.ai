package ai.gebo.bingsearch.handler.model;

import java.util.List;

import lombok.Data;

@Data
public class BingNews {
	Long totalEstimatedMatches;
	List<BingNewsArticle> value;
}