/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.toolcalling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.model.tool.ToolCallLimitBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.micrometer.observation.ObservationRegistry;

/**
 * Where the software controlling a model call gets what it adds to the call, and
 * where the tools of that call get what the controlling software loaded for them.
 * <p>
 * The controlling software - an agent, a network, a harness, a service - builds a
 * {@link GeboToolCalling} with {@link #builder()}: the tools the call must declare, the
 * interfaces the tools may call back (its own implementations, storing what they are
 * told for it to read after the call), who is calling, the tools' budget, the limits
 * of the tool calling loop. The defaults of the loop come from the configuration:
 * {@code gebo.tools.calling.max-calls-per-tool}, {@code gebo.tools.calling.max-total-calls}
 * and {@code gebo.tools.calling.on-limit-exceeded} ({@code THROW} or
 * {@code RETURN_ERROR_RESPONSE}); unset, Spring AI's own apply (40, 150, THROW).
 * <p>
 * A tool reads the interfaces loaded for the call it runs in from its tool context
 * with {@link #controlsOf(ToolContext)} or {@link #control(ToolContext, Class)}, the
 * same way whatever software is behind the call.
 */
@Service
public class GeboToolCallingManagerFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboToolCallingManagerFactory.class);

	private final ObservationRegistry observationRegistry;
	private final GeboToolCalling.Limits defaultLimits;

	public GeboToolCallingManagerFactory(@Autowired(required = false) ObservationRegistry observationRegistry,
			@Value("${gebo.tools.calling.max-calls-per-tool:#{null}}") Integer maxCallsPerTool,
			@Value("${gebo.tools.calling.max-total-calls:#{null}}") Integer maxTotalToolCalls,
			@Value("${gebo.tools.calling.on-limit-exceeded:#{null}}") ToolCallLimitBehavior onLimitExceeded) {
		this.observationRegistry = observationRegistry != null ? observationRegistry : ObservationRegistry.NOOP;
		this.defaultLimits = new GeboToolCalling.Limits(maxCallsPerTool, null, null, maxTotalToolCalls,
				onLimitExceeded);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tool calling defaults: max calls per tool " + maxCallsPerTool + ", max total calls "
					+ maxTotalToolCalls + ", on limit exceeded " + onLimitExceeded
					+ " (null where Spring AI's own default applies)");
		}
	}

	/** The factory of an installation without configuration: Spring AI's defaults. */
	public GeboToolCallingManagerFactory() {
		this(null, null, null, null);
	}

	/** What a model call is made with, starting from the configured defaults. */
	public GeboToolCalling.Builder builder() {
		return GeboToolCalling.builder().limits(defaultLimits);
	}

	public GeboToolCalling.Limits getDefaultLimits() {
		return defaultLimits;
	}

	public ObservationRegistry getObservationRegistry() {
		return observationRegistry;
	}

	/** The tool calling loop of a call made with the given {@link GeboToolCalling}. */
	public GeboToolCallingAdvisor advisorOf(GeboToolCalling toolCalling) {
		return toolCalling.advisor(observationRegistry);
	}

	/**
	 * What the controlling software loaded for the tools of the call a tool runs in,
	 * {@link IToolCallingControlsProvider#NONE} outside a call made with a
	 * {@link GeboToolCalling}.
	 */
	public static IToolCallingControlsProvider controlsOf(ToolContext toolContext) {
		return IToolCallingControlsProvider.from(toolContext);
	}

	/**
	 * The instance the controlling software loaded for the given interface, null when
	 * it loaded none: the tool then tells the model that what it was asked is not
	 * available here.
	 */
	public static <T> T control(ToolContext toolContext, Class<T> contract) {
		return IToolCallingControlsProvider.from(toolContext).get(contract);
	}
}
