package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliverableIntent {
	QA("User wants direct answer, short"), // (direct answer, short)

	REPORT("User asked a structured, detailed, multi-section"), // (structured, detailed, multi-section)

	HOWTO("User asked a step or procedure explanation"), // (steps/procedure)

	DECISION("User wants a recommendation/tradeoff"), // (recommendation/tradeoffs)

	SUMMARY("User wants a synthetic outcome"), // (short synthesis)

	UNKNOWN("Fallback, cannot decide clearly the user intent");// (fallback → be conservative)
	
	final String explanation; 
}
