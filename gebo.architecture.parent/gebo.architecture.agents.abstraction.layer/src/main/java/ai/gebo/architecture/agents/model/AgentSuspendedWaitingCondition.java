package ai.gebo.architecture.agents.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentSuspendedWaitingCondition<T> {
	private final String suspendedConditionId = UUID.randomUUID().toString();
	private Class<T> type;
	private String waitingConditionStatement;
	private String waitedConditionInstanceLLMExplanation;
	private T instanceValue = null;
}
