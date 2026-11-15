/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.model.ToolCallConfirmationOptions;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.agents.tools.ToolCallConfirmations.Decision;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.abstraction.layer.services.toolcalling.IToolCallbacksDecorator;

/**
 * A tool an agent may run only once a person allows the call: the administrator lists
 * it in the agent's configuration ({@code toolsNeedingConfirmation}), whatever its source,
 * MCP tools included.
 * <p>
 * Called by the model, it does not run the tool: it puts the call - the tool and the
 * arguments the model gave - to a person as a question ({@link ToolCallConfirmationOptions},
 * {@value ToolCallConfirmationOptions#ALLOW} or {@value ToolCallConfirmationOptions#DENY},
 * with room for a message), declared to the loaded {@link IGWaitExternalCondition} as the
 * questions of the agents are, and the declaration ends the call: the network suspends
 * until the person answers. The agent is then executed again from the start, told the
 * answer ({@link #answered(AgentSuspendedWaitingCondition)}); when the model calls the
 * tool again with the same arguments, an allowed call runs and a refused one gets the
 * person's message as its result, without running. A call with other arguments is put to
 * a person again.
 * <p>
 * Where nothing can wait for a person the tool is not run: the model is told it needs a
 * confirmation that cannot be asked here.
 */
public class ConfirmingToolCallback implements ToolCallback {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmingToolCallback.class);
	/** What the model reads of the tool on top of its own description. */
	static final String NEEDS_CONFIRMATION = " Each call of this tool is put to a person, who allows or refuses it"
			+ " before it runs: the work stops until they answer.";
	static final String WAITING = "The call was put to a person, who allows or refuses it: the work stops here and"
			+ " goes on when they answer.";
	static final String NOT_AVAILABLE = "This tool runs only once a person allows the call, and nobody can be asked"
			+ " here: it was not run. Go on without it, or say what you would have run.";

	private final ToolCallback delegate;
	private final ToolCallConfirmations confirmations;
	private final ToolDefinition definition;

	public ConfirmingToolCallback(ToolCallback delegate, ToolCallConfirmations confirmations) {
		this.delegate = delegate;
		this.confirmations = confirmations != null ? confirmations : ToolCallConfirmations.NONE;
		final ToolDefinition own = delegate.getToolDefinition();
		this.definition = ToolDefinition.builder().name(own.name())
				.description((own.description() != null ? own.description() : "") + NEEDS_CONFIRMATION)
				.inputSchema(own.inputSchema()).build();
	}

	/**
	 * The decorator wrapping, among the tools of a model call, the ones named: an agent's
	 * tools needing a person's confirmation, with the decisions its respawn was given.
	 */
	public static IToolCallbacksDecorator decorator(Collection<String> toolsNeedingConfirmation,
			ToolCallConfirmations confirmations) {
		final Set<String> names = Set.copyOf(toolsNeedingConfirmation);
		return tools -> tools.stream().map(tool -> tool != null && tool.getToolDefinition() != null
				&& names.contains(tool.getToolDefinition().name()) && !(tool instanceof ConfirmingToolCallback)
						? new ConfirmingToolCallback(tool, confirmations)
						: tool)
				.toList();
	}

	@Override
	public ToolDefinition getToolDefinition() {
		return definition;
	}

	@Override
	public ToolMetadata getToolMetadata() {
		return delegate.getToolMetadata();
	}

	@Override
	public String call(String toolInput) {
		return call(toolInput, null);
	}

	@Override
	public String call(String toolInput, ToolContext toolContext) {
		final String name = definition.name();
		final Decision decision = confirmations.decisionFor(name, toolInput);
		if (decision != null) {
			if (decision.allowed()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The call to " + name + " was allowed by a person: it runs");
				}
				return delegate.call(toolInput, toolContext);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The call to " + name + " was refused by a person: it does not run");
			}
			return refused(name, decision.message());
		}
		final IGWaitExternalCondition wait = toolContext != null
				? GeboToolCallingManagerFactory.control(toolContext, IGWaitExternalCondition.class)
				: null;
		if (wait == null) {
			LOGGER.info("The call to " + name + " needs a person's confirmation where nobody can be asked: not run");
			return NOT_AVAILABLE;
		}
		final ToolCallConfirmationOptions options = new ToolCallConfirmationOptions();
		options.setToolName(name);
		options.setArguments(toolInput);
		options.setQuestion("Allow the agent to run the tool \"" + name + "\" with these arguments?\n"
				+ (toolInput != null ? toolInput : "{}"));
		options.setOptions(new ArrayList<ClarificationOption>(List.of(
				ClarificationOption.of(ToolCallConfirmationOptions.ALLOW, "The tool runs with these arguments"),
				ClarificationOption.of(ToolCallConfirmationOptions.DENY,
						"The tool does not run; you may tell the agent why or what to do instead"))));
		options.setMultipleChoice(false);
		options.setCustomAnswerAllowed(true);
		final AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> condition = new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(
				ClarificationAnswer.class, options.getQuestion(), delegate.getToolDefinition().description(), null, true,
				ClarificationOptions.class, options);
		condition.setHumanInTheLoopProcessTreatment(true);
		wait.waitFor(condition);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The call to " + name + " was put to a person (condition:" + condition.getSuspendedConditionId()
					+ ")");
		}
		return WAITING;
	}

	private static String refused(String name, String message) {
		return "A person refused this call to " + name + ": it was not run."
				+ (message != null ? " Their message: " + message : "");
	}

	/**
	 * What a respawned agent is told of a call it put to a person: allowed, call the tool
	 * again with the same arguments to run it; refused, with the person's message; left
	 * unanswered, not run. Null when the condition is not a tool call confirmation.
	 */
	public static String answered(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition == null || !(condition.getLlmChosenOptions() instanceof ToolCallConfirmationOptions options)) {
			return null;
		}
		final String call = "the call to the tool " + options.getToolName() + " with the arguments "
				+ (options.getArguments() != null ? options.getArguments() : "{}");
		if (condition.getInstanceValue() == null) {
			return "Nobody confirmed " + call + ": it was not run. Go on without it.";
		}
		final Decision decision = ToolCallConfirmations.decisionOf(condition);
		if (decision.allowed()) {
			return "A person allowed " + call
					+ ". Call the tool again with exactly these arguments: this time it runs."
					+ (decision.message() != null ? " Their message: " + decision.message() : "");
		}
		return "A person refused " + call + ": it was not run, do not call it again with these arguments."
				+ (decision.message() != null ? " Their message: " + decision.message() : "");
	}
}
