package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliverableIntent {
	QA("User asked direct short answer or some sinonyms of this in all languages"), // (direct answer, short)

	REPORT("User asked a structured, report, detailed report, an analisys or some sinonyms of these in all languages"), // (structured, detailed, multi-section)

	HOWTO("User asked a step or procedure explanation  or some sinonyms of this in all languages"), // (steps/procedure)

	DECISION("User asked a recommendation or tradeoff  or some sinonyms of this in all languages"), // (recommendation/tradeoffs)

	SUMMARY("User wants a synthetic outcome  or some sinonyms of this in all languages"), // (short synthesis)

	UNKNOWN("Fallback, cannot decide clearly the user intent");// (fallback → be conservative)
	
	final String explanation; 
}
