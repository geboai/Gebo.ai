package ai.gebo.architecture.search.service;

import java.util.List;

public interface IKeywordMatcherService {
	public boolean isMatching(List<String> generatedKeywords, String chunkText);
	public boolean isMatching(List<String> generatedKeywords, String chunkText, int minHits);
}
