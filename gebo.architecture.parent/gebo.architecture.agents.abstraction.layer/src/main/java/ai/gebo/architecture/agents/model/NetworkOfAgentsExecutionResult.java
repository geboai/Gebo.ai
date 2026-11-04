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
	/** The id of the block the suspended run was saved as, to respawn it from; null when executed. */
	private final String suspensionId;

	public static <T> NetworkOfAgentsExecutionResult<T> of(T t) {
		return new NetworkOfAgentsExecutionResult<>(t, null, NStatus.EXECUTED, null);
	}

	public static <T> NetworkOfAgentsExecutionResult<T> suspended(
			List<AgentSuspendedWaitingCondition> suspendedConditions) {
		return suspended(null, suspendedConditions);
	}

	public static <T> NetworkOfAgentsExecutionResult<T> suspended(String suspensionId,
			List<AgentSuspendedWaitingCondition> suspendedConditions) {
		return new NetworkOfAgentsExecutionResult<T>(null, suspendedConditions, NStatus.SUSPENDED, suspensionId);
	}
}
