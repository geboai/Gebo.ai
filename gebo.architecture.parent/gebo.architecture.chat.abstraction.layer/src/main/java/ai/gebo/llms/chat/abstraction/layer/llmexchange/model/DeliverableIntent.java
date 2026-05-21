package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliverableIntent {
	QA("User asked direct short answer or some sinonyms of this in all languages", "direct short answer"), // (direct
	PURE_SEARCH(
			"User asked a search or some sinonyms of this in all languages",
			"pure search outcome"),
	REPORT("*User asked a structured, report, detailed report, an analisys or some sinonyms of these in all languages\r\n"
			+ "* report / detailed report / in-depth / deep-search / deep analysis / detailed analysis\r\n"
			+ "* citations/sources across documents\r\n" + "* compare/analyze a broad corpus", "detailed report"), // (structured,
																													// detailed,
																													// multi-section)
	HOWTO("User asked a step or procedure explanation  or some sinonyms of this in all languages",
			"process step or procedure explanation"), // (steps/procedure)
	DECISION("User asked a recommendation or tradeoff  or some sinonyms of this in all languages",
			"recommendation or tradeoff evaluation"), // (recommendation/tradeoffs)
	SUMMARY("User wants a synthetic outcome  or some sinonyms of this in all languages", "synthetic outcome"), // (short
																												// synthesis)
	
	UNKNOWN("Fallback, cannot decide clearly the user intent", "synthetic outcome");// (fallback → be

	final String explanation;
	final String agentDeliverableCompleteness;
}
