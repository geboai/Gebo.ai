package ai.gebo.architecture.search.service;

import java.util.Collection;
import java.util.List;

public interface IKeywordMatcherService {
	public boolean isMatching(List<String> generatedKeywords, String chunkText);
	public boolean isMatching(List<String> generatedKeywords, String chunkText, int minHits);

	/**
	 * The same, the stop words ignored in the keywords being the ones of the given
	 * languages (ISO 639-1 codes, as the ingestion's language detector writes them);
	 * the configured fallback languages' when none of them is known.
	 */
	public boolean isMatching(List<String> generatedKeywords, String chunkText, int minHits,
			Collection<String> languages);
}
