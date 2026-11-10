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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.definition.ToolDefinition;

/**
 * The manager executing the tool calls of a call made with a {@link GeboToolCalling}:
 * Spring AI's own, within the container's limits, plus the end of the call the loaded
 * controls decide. After each round of tool calls it asks the container's
 * {@link IToolCallEndingControl}s whether what the tools told them ends the call: when
 * one says so, the round's result is returned directly, the loop stops there and the
 * model is not called again, as it does for a tool declared as returning directly. A
 * round no control ends is returned as the delegate computed it, so a tool declared
 * direct by its own metadata keeps its stop, and a round with no ending control loaded
 * is untouched.
 * <p>
 * Everything else is the delegate's: the tool definitions, the limits and what happens
 * when they are exceeded, the exceptions it throws, which pass through unchanged.
 * <p>
 * Spring AI's default manager is final, so it is wrapped rather than extended, as
 * Spring AI's own provider-specific managers do. Spring AI 2.1 adds a default
 * {@code executeToolCalls(Prompt, ChatResponse, ToolCallDecisions)} to the interface,
 * the resumption of a batch paused for a person's confirmation, throwing unless
 * overridden: at the upgrade this class delegates it too and reads the ending controls
 * on its result the same way.
 */
public final class GeboToolCallingManager implements ToolCallingManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboToolCallingManager.class);
	private final ToolCallingManager delegate;
	private final List<IToolCallEndingControl> endingControls;

	/**
	 * @param delegate the manager executing the calls
	 * @param controls the instances the controlling software loaded for the call, of
	 *                 which the ending ones are read; the others are ignored
	 */
	public GeboToolCallingManager(ToolCallingManager delegate, Collection<?> controls) {
		if (delegate == null) {
			throw new IllegalArgumentException("The delegate manager is required");
		}
		this.delegate = delegate;
		final List<IToolCallEndingControl> ending = new ArrayList<>();
		if (controls != null) {
			for (Object control : controls) {
				if (control instanceof IToolCallEndingControl endingControl && !ending.contains(endingControl)) {
					ending.add(endingControl);
				}
			}
		}
		this.endingControls = List.copyOf(ending);
	}

	/** The manager executing the calls. */
	public ToolCallingManager getDelegate() {
		return delegate;
	}

	/** The controls read after each round, in the order they were loaded. */
	public List<IToolCallEndingControl> getEndingControls() {
		return endingControls;
	}

	@Override
	public List<ToolDefinition> resolveToolDefinitions(ToolCallingChatOptions chatOptions) {
		return delegate.resolveToolDefinitions(chatOptions);
	}

	@Override
	public ToolExecutionResult executeToolCalls(Prompt prompt, ChatResponse chatResponse) {
		return endedByAControl(delegate.executeToolCalls(prompt, chatResponse));
	}

	/**
	 * The round's result as the delegate computed it, or returned directly when a
	 * loaded control says what the tools told it ends the call.
	 */
	private ToolExecutionResult endedByAControl(ToolExecutionResult result) {
		if (result == null || result.returnDirect() || endingControls.isEmpty()) {
			return result;
		}
		for (IToolCallEndingControl control : endingControls) {
			if (control.endsTheCall()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The call ends at this round of tool calls: " + control.getClass().getSimpleName()
							+ " says what the tools told it ends it");
				}
				return ToolExecutionResult.builder().conversationHistory(result.conversationHistory())
						.returnDirect(true).build();
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "GeboToolCallingManager[delegate=" + delegate.getClass().getSimpleName() + ", endingControls="
				+ endingControls.stream().map(control -> control.getClass().getSimpleName()).toList() + "]";
	}
}
