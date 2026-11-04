package ai.gebo.architecture.agents.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NetworkOfAgentsExecutionResult<T> {
	public static enum NStatus {
		EXECUTED, SUSPENDED
	}
	private final T result;
	private final List<AgentSuspendedWaitingCondition> waitingConditions;
	private final NStatus executedStatus;

	public static <T> NetworkOfAgentsExecutionResult<T> of(T t) {
		return new NetworkOfAgentsExecutionResult<>(t, null, NStatus.EXECUTED);
	}

	public static <T> NetworkOfAgentsExecutionResult<T> suspended(
			List<AgentSuspendedWaitingCondition> suspendedConditions) {
		return new NetworkOfAgentsExecutionResult<T>(null, suspendedConditions, NStatus.SUSPENDED);
	}
}
