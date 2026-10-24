/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.model;

import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Everything a run of a network of agents is about, handed to whoever builds the
 * collaboration session of that run (see
 * {@link ai.gebo.architecture.agents.services.IAgentsCollaborationSessionContextFactory}).
 * <p>
 * It exists so that the factory's signature does not have to change again every
 * time a session builder needs to know one more thing about the run: what is
 * known about a run goes in here.
 * <p>
 * Note that {@link #getChatRequestContext()} already carries a session id of its
 * own ({@link IChatRequestContext#getSessionID()}), so a factory that keys its
 * sessions on the caller's one can derive everything it needs from this context
 * alone.
 */
@AllArgsConstructor
@Builder
@Getter
public class AgentsSessionCreationContext {
	/** The network being run. */
	private final GAgentsNetwork network;
	/** The request context of the run, as the caller built it. */
	private final IChatRequestContext chatRequestContext;
	/** The environment the caller passed to the run; seeded into the session's own. */
	private final Map<String, Object> environment;
	/** The input type of the run. */
	private final Class<?> inputType;
	/** The output type of the run. */
	private final Class<?> outputType;
	/** The identity the run executes under. */
	private final ReactiveIdentityUtil runAs;
	/** The id of the network service running it ({@code IGAgentsNetworkService.getId()}). */
	private final String networkServiceId;
}
