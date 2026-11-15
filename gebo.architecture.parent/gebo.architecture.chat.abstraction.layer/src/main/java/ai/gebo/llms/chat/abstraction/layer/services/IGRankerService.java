package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

public interface IGRankerService {
	/**
	 * Ranks the fragments against the query with the default ranker model, keeping the
	 * best {@code topK}, then drops the ones the internal services model judges
	 * irrelevant to the query (fail-open: when that filter cannot run, the ranked
	 * fragments are returned as they are).
	 */
	public AIDocumentsSet rankAndRemoveIrrelevant(AIDocumentsSet input, String query, int topK) throws LLMConfigException;

	/** The same, on a list of fragments, returned best ranked first. */
	public List<Document> rankAndRemoveIrrelevant(List<Document> input, String query, int topK) throws LLMConfigException;

	/**
	 * The same, the irrelevance filter's model call made with what the controlling
	 * software adds to the call the ranking serves (see {@link GeboToolCalling}); null
	 * for nothing, which is the ranking as it always has been.
	 */
	public default AIDocumentsSet rankAndRemoveIrrelevant(AIDocumentsSet input, String query, int topK,
			GeboToolCalling toolCalling) throws LLMConfigException {
		return rankAndRemoveIrrelevant(input, query, topK);
	}

	/** The same, on a list of fragments, returned best ranked first. */
	public default List<Document> rankAndRemoveIrrelevant(List<Document> input, String query, int topK,
			GeboToolCalling toolCalling) throws LLMConfigException {
		return rankAndRemoveIrrelevant(input, query, topK);
	}

	/**
	 * Ranks the fragments against the query with the default ranker model, keeping the
	 * best {@code topK}, without the irrelevance filter: no fragment is judged, none is
	 * dropped beyond the ranker's own {@code topK}.
	 */
	public AIDocumentsSet rank(AIDocumentsSet input, String query, int topK) throws LLMConfigException;

	/** The same, on a list of fragments, returned best ranked first. */
	public List<Document> rank(List<Document> input, String query, int topK) throws LLMConfigException;

	public int getRankerConfiguredChunkSize();

	public boolean isRankerConfigured();
}
