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
	ANALISYS("User EXPLICITLY requested a structured/detailed report or a multi-source analysis (e.g. a report, detailed report, in-depth analysis, or comparison across many documents). Do NOT choose this for ordinary questions that can be answered directly.", "detailed analisys report", 6), // (structured,
																														// detailed,
																														// multi-section)
	UNKNOWN("Fallback, cannot decide clearly the user intent", "synthetic outcome", 7);// (fallback → be

	final String explanation;
	final String agentDeliverableCompleteness;
	final int order;
}
