/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import java.util.Map;

import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

/**
 * Adds what a pipeline of its own wants a network of agents to know about the
 * request, to the environment every run of a network is given (see
 * {@link GNetworkEnvironmentBuilder}). Declared as a bean, it is asked on every
 * run, from a chat or from outside one, and says for itself whether the request
 * is one of its own.
 */
public interface IGNetworkEnvironmentContributor {

	/**
	 * Puts into the environment what this contributor has to say about the request;
	 * nothing when the request is none of its business.
	 */
	public void contribute(ChatPipelineExecutionRuntimeData runtimeData, Map<String, Object> environment);
}
