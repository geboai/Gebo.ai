package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliverableIntent {
	QA("User asked direct short answer or some sinonyms of this in all languages", "direct short answer", 1), // (direct

	HOWTO("User asked a step or procedure explanation  or some sinonyms of this in all languages",
			"process step or procedure explanation", 2), // (steps/procedure)
	DECISION("User asked a recommendation or tradeoff  or some sinonyms of this in all languages",
			"recommendation or tradeoff evaluation", 3), // (recommendation/tradeoffs)
	SUMMARY("User wants a synthetic outcome  or some sinonyms of this in all languages", "synthetic outcome", 4), // (short
																													// synthesis)
	PURE_SEARCH(
			"User asked a search, a research, find documents, find files, or some sinonyms of this in all languages (excluding the rules for REPORT)",
			"pure search outcome, files or document files search", 5),
	ANALISYS("*User asked a structured report,a report, detailed report, an analisys or some sinonyms of these in all languages\r\n"
			+ "* report / detailed report / in-depth / deep-search / deep analysis / detailed analysis\r\n"
			+ "* citations/sources across documents\r\n" + "* compare/analyze a broad corpus", "detailed report", 6), // (structured,
																														// detailed,
																														// multi-section)
	UNKNOWN("Fallback, cannot decide clearly the user intent", "synthetic outcome", 7);// (fallback → be

	final String explanation;
	final String agentDeliverableCompleteness;
	final int order;
}
