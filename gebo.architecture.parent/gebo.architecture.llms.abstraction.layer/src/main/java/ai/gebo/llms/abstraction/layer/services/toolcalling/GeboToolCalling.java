/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.toolcalling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallLimitBehavior;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import io.micrometer.observation.ObservationRegistry;

/**
 * What the software controlling a model call adds to it, beyond what the model's
 * configuration and the chat request context already give: the tools it wants
 * declared whatever the configuration says, the interfaces it loads for the tools to
 * call back, who is calling, how the tools' results are bounded and recorded, how many
 * tool calls a turn may make. Built once with the {@link Builder} a
 * {@link GeboToolCallingManagerFactory} gives, passed to the model's call methods that
 * take one (see {@code IGConfigurableChatModel}), read back once the call returns.
 * <p>
 * It governs the tool calling loop of the call it is passed to: the call runs with its
 * own {@link GeboToolCallingAdvisor} and its own {@link ToolCallingManager}, in place of
 * the ones Spring AI registers by default, at the same place in the advisor chain. A
 * call made without one runs exactly as before.
 * <p>
 * What the last call made with it left behind is readable afterwards: how many rounds
 * the model took, whether a tool ended the call ({@link #isStoppedByTool()}: a tool
 * declared as returning directly, what a tool that suspends the work declares, see
 * {@code ToolCallbackDeclarationUtil#returningDirect()}), the turn's messages. One
 * instance may be passed to several calls in turn; each call starts that record again.
 */
public final class GeboToolCalling {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboToolCalling.class);

	/**
	 * How many tool calls a turn may make, each value left null where Spring AI's own
	 * default applies (40 calls per tool, 150 per turn, the turn failed when exceeded).
	 */
	public static record Limits(Integer maxCallsPerTool, Map<String, Integer> maxCallsOfTool,
			Set<String> toolsExcludedFromLimit, Integer maxTotalToolCalls, ToolCallLimitBehavior onLimitExceeded) {
		public static final Limits SPRING_DEFAULTS = new Limits(null, Map.of(), Set.of(), null, null);

		public Limits {
			maxCallsOfTool = maxCallsOfTool != null ? Map.copyOf(maxCallsOfTool) : Map.of();
			toolsExcludedFromLimit = toolsExcludedFromLimit != null ? Set.copyOf(toolsExcludedFromLimit) : Set.of();
		}
	}

	private final List<ToolCallback> mandatoryTools;
	private final Map<Class<?>, Object> controls;
	private final String caller;
	private final ToolCallsListener listener;
	private final Integer toolsBudget;
	private final Limits limits;
	private final IToolCallingControlsProvider provider = new Provider();

	// what the last call made with this left behind
	private final AtomicInteger rounds = new AtomicInteger();
	private volatile List<String> lastRoundToolCalls = List.of();
	private volatile boolean stoppedByTool = false;
	private volatile List<String> stoppingTools = List.of();
	private volatile List<Message> turnHistory = List.of();

	private GeboToolCalling(Builder builder) {
		this.mandatoryTools = List.copyOf(builder.mandatoryTools);
		this.controls = Collections.unmodifiableMap(new LinkedHashMap<>(builder.controls));
		this.caller = builder.caller;
		this.listener = builder.listener;
		this.toolsBudget = builder.toolsBudget;
		this.limits = builder.limits != null ? builder.limits : Limits.SPRING_DEFAULTS;
	}

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * The tools declared to the model whatever its configuration enables, each the
	 * instance the controlling software gave (bound to this call's state): a tool of
	 * the same name the configuration enables is replaced by it.
	 */
	public List<ToolCallback> getMandatoryTools() {
		return mandatoryTools;
	}

	/** What the tools receive of the controlling software. */
	public IToolCallingControlsProvider getProvider() {
		return provider;
	}

	public String getCaller() {
		return caller;
	}

	/**
	 * The listener recording every tool call of the model call, null to leave the
	 * recording to the one the chat request context carries.
	 */
	public ToolCallsListener getListener() {
		return listener;
	}

	/** The tokens the tools' results may take in the call, null for the model's own room. */
	public Integer getToolsBudget() {
		return toolsBudget;
	}

	public Limits getLimits() {
		return limits;
	}

	/**
	 * The tools context of a model call made with this: the given one, with the
	 * provider the tools find there and the tools' budget when one was given (capping
	 * a budget the context already carries, never replacing it, as the model's room
	 * does).
	 */
	public Map<String, Object> toolsContext(Map<String, Object> toolsContext) {
		final Map<String, Object> out = toolsContext != null ? new HashMap<>(toolsContext) : new HashMap<>();
		out.put(IToolCallingControlsProvider.CONTEXT_KEY, provider);
		if (toolsBudget != null) {
			final ToolsTokenBudget shared = ToolsTokenBudget.from(out);
			if (shared != null) {
				shared.capTo(toolsBudget);
			} else {
				out.put(ToolsTokenBudget.TOOLS_CONTEXT_KEY, new ToolsTokenBudget(toolsBudget));
			}
		}
		return out;
	}

	/**
	 * The manager executing the tool calls of a call made with this, within its
	 * limits, observed through the given registry.
	 */
	public ToolCallingManager manager(ObservationRegistry observationRegistry) {
		final DefaultToolCallingManager.Builder builder = ToolCallingManager.builder()
				.observationRegistry(observationRegistry != null ? observationRegistry : ObservationRegistry.NOOP);
		if (limits.maxCallsPerTool() != null) {
			if (limits.maxCallsPerTool() > 0) {
				builder.maxCallsPerTool(limits.maxCallsPerTool());
			} else {
				builder.unlimitedCallsPerTool();
			}
		}
		for (Map.Entry<String, Integer> ofTool : limits.maxCallsOfTool().entrySet()) {
			if (ofTool.getValue() != null && ofTool.getValue() > 0) {
				builder.maxCallsPerTool(ofTool.getKey(), ofTool.getValue());
			}
		}
		for (String excluded : limits.toolsExcludedFromLimit()) {
			builder.excludeToolFromLimit(excluded);
		}
		if (limits.maxTotalToolCalls() != null) {
			if (limits.maxTotalToolCalls() > 0) {
				builder.maxTotalToolCalls(limits.maxTotalToolCalls());
			} else {
				builder.unlimitedTotalToolCalls();
			}
		}
		if (limits.onLimitExceeded() != null) {
			builder.onLimitExceeded(limits.onLimitExceeded());
		}
		return builder.build();
	}

	/** The advisor running the tool calling loop of a call made with this. */
	public GeboToolCallingAdvisor advisor(ObservationRegistry observationRegistry) {
		return new GeboToolCallingAdvisor(this, manager(observationRegistry));
	}

	// ---- what the last call left behind

	/** The model rounds of the last call: one, plus one per round of tool calls. */
	public int getRounds() {
		return rounds.get();
	}

	/**
	 * Whether a tool ended the last call: a tool declared as returning directly was
	 * called, the loop stopped at it and its result is the call's answer.
	 */
	public boolean isStoppedByTool() {
		return stoppedByTool;
	}

	/** The tools of the round that ended the last call, empty when none did. */
	public List<String> getStoppingTools() {
		return stoppingTools;
	}

	/**
	 * The messages of the last call's turn as the tool calling loop left them: the
	 * request, each round's tool calls and their results. What a call suspended by a
	 * tool is resumed from.
	 */
	public List<Message> getTurnHistory() {
		return turnHistory;
	}

	void callStarted() {
		rounds.set(0);
		lastRoundToolCalls = List.of();
		stoppedByTool = false;
		stoppingTools = List.of();
		turnHistory = List.of();
	}

	void roundStarted() {
		rounds.incrementAndGet();
	}

	void roundAnswered(ChatResponse response) {
		if (response == null || response.getResults() == null) {
			return;
		}
		final List<String> called = new ArrayList<>();
		for (Generation generation : response.getResults()) {
			final AssistantMessage output = generation != null ? generation.getOutput() : null;
			if (output != null && output.hasToolCalls()) {
				for (AssistantMessage.ToolCall call : output.getToolCalls()) {
					if (call != null && call.name() != null && !called.contains(call.name())) {
						called.add(call.name());
					}
				}
			}
		}
		if (!called.isEmpty()) {
			lastRoundToolCalls = List.copyOf(called);
		}
	}

	void turnContinued(ToolExecutionResult toolExecutionResult) {
		if (toolExecutionResult != null && toolExecutionResult.conversationHistory() != null) {
			turnHistory = List.copyOf(toolExecutionResult.conversationHistory());
		}
	}

	void callEnded(ChatResponse response) {
		final Generation result = response != null ? response.getResult() : null;
		final String finishReason = result != null && result.getMetadata() != null
				? result.getMetadata().getFinishReason()
				: null;
		if (ToolExecutionResult.FINISH_REASON.equals(finishReason)) {
			stoppedByTool = true;
			stoppingTools = lastRoundToolCalls;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The model call was ended by its tools " + stoppingTools + " after " + rounds.get()
						+ " round(s)" + (caller != null ? ", caller " + caller : ""));
			}
		}
	}

	@Override
	public String toString() {
		return "GeboToolCalling[caller=" + caller + ", mandatoryTools="
				+ mandatoryTools.stream().map(x -> x.getToolDefinition().name()).toList() + ", controls="
				+ controls.keySet().stream().map(Class::getSimpleName).toList() + ", budget=" + toolsBudget + "]";
	}

	/** What the tools see: the loaded interfaces, read-only. */
	private final class Provider implements IToolCallingControlsProvider {
		@Override
		@SuppressWarnings("unchecked")
		public <T> T get(Class<T> contract) {
			return contract != null ? (T) controls.get(contract) : null;
		}

		@Override
		public Set<Class<?>> contracts() {
			return controls.keySet();
		}

		@Override
		public String caller() {
			return caller;
		}

		@Override
		public String toString() {
			return "controls of " + GeboToolCalling.this;
		}
	}

	/**
	 * Builds what the controlling software adds to a model call. Loading an interface
	 * is giving the tools a deferred callback: the implementation is the controlling
	 * software's own, it stores what the tools tell it, the controlling software reads
	 * it after the call.
	 */
	public static final class Builder {
		private final List<ToolCallback> mandatoryTools = new ArrayList<>();
		private final Map<Class<?>, Object> controls = new LinkedHashMap<>();
		private String caller = null;
		private ToolCallsListener listener = null;
		private Integer toolsBudget = null;
		private Limits limits = null;

		private Builder() {
		}

		/** A tool declared to the model whatever its configuration enables. */
		public Builder mandatoryTool(ToolCallback tool) {
			if (tool != null) {
				final String name = tool.getToolDefinition().name();
				mandatoryTools.removeIf(x -> name.equals(x.getToolDefinition().name()));
				mandatoryTools.add(tool);
			}
			return this;
		}

		public Builder mandatoryTools(Collection<ToolCallback> tools) {
			if (tools != null) {
				tools.forEach(this::mandatoryTool);
			}
			return this;
		}

		/**
		 * Loads the instance the tools get when they ask for the given interface: the
		 * controlling software's own implementation, storing what the tools tell it.
		 *
		 * @throws IllegalArgumentException when the contract is not an interface or the
		 *                                  implementation is not an instance of it
		 */
		public <T> Builder provide(Class<T> contract, T implementation) {
			if (contract == null || implementation == null) {
				throw new IllegalArgumentException("A control is an interface and its implementation");
			}
			if (!contract.isInterface()) {
				throw new IllegalArgumentException("A control is loaded by its interface: " + contract.getName()
						+ " is not one");
			}
			if (!contract.isInstance(implementation)) {
				throw new IllegalArgumentException(implementation.getClass().getName() + " does not implement "
						+ contract.getName());
			}
			controls.put(contract, implementation);
			return this;
		}

		/** Who is calling the model, as the tools will be told. */
		public Builder caller(String caller) {
			this.caller = caller != null && !caller.isBlank() ? caller : null;
			return this;
		}

		/** Records every tool call of the model call, in place of the chat request's recorder. */
		public Builder listener(ToolCallsListener listener) {
			this.listener = listener;
			return this;
		}

		/** The tokens the tools' results may take in the call. */
		public Builder toolsBudget(int tokens) {
			this.toolsBudget = Math.max(0, tokens);
			return this;
		}

		public Builder limits(Limits limits) {
			this.limits = limits;
			return this;
		}

		/** How many times a turn may call each tool; 0 or less for no limit. */
		public Builder maxCallsPerTool(int maxCallsPerTool) {
			final Limits current = limits != null ? limits : Limits.SPRING_DEFAULTS;
			this.limits = new Limits(maxCallsPerTool, current.maxCallsOfTool(), current.toolsExcludedFromLimit(),
					current.maxTotalToolCalls(), current.onLimitExceeded());
			return this;
		}

		/** How many times a turn may call the given tool. */
		public Builder maxCallsOfTool(String toolName, int maxCalls) {
			final Limits current = limits != null ? limits : Limits.SPRING_DEFAULTS;
			final Map<String, Integer> ofTool = new LinkedHashMap<>(current.maxCallsOfTool());
			ofTool.put(toolName, maxCalls);
			this.limits = new Limits(current.maxCallsPerTool(), ofTool, current.toolsExcludedFromLimit(),
					current.maxTotalToolCalls(), current.onLimitExceeded());
			return this;
		}

		/** A tool a turn may call without limit. */
		public Builder excludeToolFromLimit(String toolName) {
			final Limits current = limits != null ? limits : Limits.SPRING_DEFAULTS;
			final Set<String> excluded = new LinkedHashSet<>(current.toolsExcludedFromLimit());
			excluded.add(toolName);
			this.limits = new Limits(current.maxCallsPerTool(), current.maxCallsOfTool(), excluded,
					current.maxTotalToolCalls(), current.onLimitExceeded());
			return this;
		}

		/** How many tool calls a turn may make in all; 0 or less for no limit. */
		public Builder maxTotalToolCalls(int maxTotalToolCalls) {
			final Limits current = limits != null ? limits : Limits.SPRING_DEFAULTS;
			this.limits = new Limits(current.maxCallsPerTool(), current.maxCallsOfTool(),
					current.toolsExcludedFromLimit(), maxTotalToolCalls, current.onLimitExceeded());
			return this;
		}

		/** What happens when a limit is exceeded: the turn fails, or the model is told. */
		public Builder onLimitExceeded(ToolCallLimitBehavior behavior) {
			final Limits current = limits != null ? limits : Limits.SPRING_DEFAULTS;
			this.limits = new Limits(current.maxCallsPerTool(), current.maxCallsOfTool(),
					current.toolsExcludedFromLimit(), current.maxTotalToolCalls(), behavior);
			return this;
		}

		public GeboToolCalling build() {
			return new GeboToolCalling(this);
		}
	}
}
