package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.function.Consumer;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
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
	/**
	 * The listener every call recorded here is forwarded to, or {@code null}: an agent
	 * keeps its own calls (its loop history, its notifications) while the user request
	 * it works for collects the calls of every agent and step.
	 */
	private final ToolCallsListener parent;

	public ToolCallsListener() {
		this(null, null);
	}

	public ToolCallsListener(Consumer<ToolCallExecuted> onCall) {
		this(null, onCall);
	}

	public ToolCallsListener(ToolCallsListener parent, Consumer<ToolCallExecuted> onCall) {
		this.parent = parent;
		this.onCall = onCall;
	}

	/**
	 * A listener recording its own calls and forwarding each of them to this one.
	 */
	public ToolCallsListener child(Consumer<ToolCallExecuted> onCall) {
		return new ToolCallsListener(this, onCall);
	}

	/** A listener recording its own calls and forwarding them to the given parent, when any. */
	public static ToolCallsListener childOf(ToolCallsListener parent, Consumer<ToolCallExecuted> onCall) {
		return parent != null ? parent.child(onCall) : new ToolCallsListener(onCall);
	}

	public void addCall(String toolName, String toolDescription, String toolInput, String result) {
		record(new ToolCallExecuted(toolName, toolDescription, toolInput, result));
	}

	/** Records the call here, then forwards the same call up the chain. */
	private void record(ToolCallExecuted executed) {
		execs.add(executed);
		if (onCall != null) {
			try {
				onCall.accept(executed);
			} catch (RuntimeException e) {
				// A progress notification must never be able to break the tool-execution loop
				// it is only reporting on.
			}
		}
		if (parent != null) {
			parent.record(executed);
		}
	}

	public List<ToolCallExecuted> getCalls() {
		return new ArrayList<>(execs);
	}

	/**
	 * A request level recorder: every call it receives, from any step or agent of the
	 * request, is appended to the given list as it happens - typically the
	 * {@code calledFunctions} of the response being built, so the response carries its
	 * calls whenever it is saved or streamed.
	 */
	public static ToolCallsListener appendingTo(List<CalledFunction> target) {
		return new ToolCallsListener(executed -> {
			if (executed != null && target != null) {
				synchronized (target) {
					target.add(toCalledFunction(executed));
				}
			}
		});
	}

	/**
	 * The call as shown to the user: the tool, its description and the input the model
	 * gave it. The result is left out, it can be a large content.
	 */
	public static CalledFunction toCalledFunction(ToolCallExecuted call) {
		return new CalledFunction(call.getName(), call.getToolDescription(),
				call.getToolInput() != null ? List.of(call.getToolInput()) : List.of(), List.of());
	}
}
