package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ToolCallsListener {
	@AllArgsConstructor
	@Getter
	public static class ToolCallExecuted {
		private final String uniqueCallId = UUID.randomUUID().toString();
		private final String name;
		private final String toolDescription;
		private final String toolInput;
		private final String result;
	}

	private final Vector<ToolCallExecuted> execs = new Vector<>();

	/**
	 * Invoked once for every tool execution recorded, or {@code null}. It is how a
	 * caller turns tool activity into user-facing progress without this low-level
	 * collector having to know anything about notifications: the agent that owns the
	 * listener supplies a callback that emits onto its own INotificationSink.
	 */
	private final Consumer<ToolCallExecuted> onCall;

	public ToolCallsListener() {
		this.onCall = null;
	}

	public ToolCallsListener(Consumer<ToolCallExecuted> onCall) {
		this.onCall = onCall;
	}

	public void addCall(String toolName, String toolDescription, String toolInput, String result) {
		final ToolCallExecuted executed = new ToolCallExecuted(toolName, toolDescription, toolInput, result);
		execs.add(executed);
		if (onCall != null) {
			try {
				onCall.accept(executed);
			} catch (RuntimeException e) {
				// A progress notification must never be able to break the tool-execution loop
				// it is only reporting on.
			}
		}
	}

	public List<ToolCallExecuted> getCalls() {
		return new ArrayList<>(execs);
	}
}
