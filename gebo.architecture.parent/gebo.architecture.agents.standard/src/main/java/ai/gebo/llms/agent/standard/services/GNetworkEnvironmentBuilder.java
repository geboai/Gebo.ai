/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

/**
 * Builds the environment a run of a network of agents is given, the same for a
 * run from the chat pipelines and for one from outside them (a harness, an A2A
 * skill, an MCP tool), so that a network is told the same things about a
 * request wherever it is run from.
 * <p>
 * In order, the later entries taking the place of the earlier ones:
 * <ol>
 * <li>what the pipeline steps put in the request's shared environment
 * ({@link ChatPipelineExecutionRuntimeData#getSharedEnvironment()});</li>
 * the standard entries ({@link StandardAgentsNetworkEnvironmentEntries}): the
 * request's runtime data, the knowledge bases the run works on, the user
 * intent, the search flags and the user's language;</li>
 * <li>what the {@link IGNetworkEnvironmentContributor contributors} add;</li>
 * <li>what the caller of the run adds for this run.</li>
 * </ol>
 */
@Service
public class GNetworkEnvironmentBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(GNetworkEnvironmentBuilder.class);

	private final List<IGNetworkEnvironmentContributor> contributors;

	public GNetworkEnvironmentBuilder(ObjectProvider<IGNetworkEnvironmentContributor> contributors) {
		this.contributors = contributors != null ? contributors.stream().toList() : List.of();
	}

	/** A builder with no contributors, for the places that have no Spring context. */
	public static GNetworkEnvironmentBuilder plain() {
		return new GNetworkEnvironmentBuilder((ObjectProvider<IGNetworkEnvironmentContributor>) null);
	}

	/**
	 * The environment of a run.
	 *
	 * @param runtimeData        the request the network is run on
	 * @param knowledgeBaseCodes the knowledge bases the run works on
	 * @param callerEntries      what the caller adds for this run, null for nothing
	 */
	public Map<String, Object> build(ChatPipelineExecutionRuntimeData runtimeData, List<String> knowledgeBaseCodes,
			Map<String, Object> callerEntries) {
		final Map<String, Object> environment = new HashMap<String, Object>();
		if (runtimeData.getSharedEnvironment() != null) {
			environment.putAll(runtimeData.getSharedEnvironment());
		}
		// the request itself, for the agents that need more of it than its text
		environment.put(StandardAgentsNetworkEnvironmentEntries.CHAT_RUNTIME_DATA, runtimeData);
		final GeboChatRequest request = runtimeData.getRequestResources() != null
				? runtimeData.getRequestResources().getCurrentRequest()
				: null;
		environment.put(StandardAgentsNetworkEnvironmentEntries.KNOWLEDGE_BASES_CODE,
				knowledgeBaseCodes != null ? List.copyOf(knowledgeBaseCodes) : List.of());
		environment.put(StandardAgentsNetworkEnvironmentEntries.USER_INTENT,
				request != null && request.getUserIntent() != null ? request.getUserIntent()
						: DeliverableIntent.SUMMARY);
		// the request understanding's flag as it is: whether a search tool answers it is the
		// agents' call (a chat with no knowledge base may still mount the web searches)
		environment.put(StandardAgentsNetworkEnvironmentEntries.SEARCH_REQUESTED,
				request != null && Boolean.TRUE.equals(request.getSearchRequested()));
		environment.put(StandardAgentsNetworkEnvironmentEntries.SEARCH_FORBIDDEN,
				request != null && Boolean.TRUE.equals(request.getSearchForbidden()));
		// detected on the user's own message when the request was understood
		if (request != null && request.getUserLanguage() != null) {
			environment.put(StandardAgentsNetworkEnvironmentEntries.USER_LANGUAGE, request.getUserLanguage());
		}
		for (IGNetworkEnvironmentContributor contributor : contributors) {
			try {
				contributor.contribute(runtimeData, environment);
			} catch (RuntimeException notContributed) {
				LOGGER.error("The environment contributor " + contributor.getClass().getName()
						+ " failed: the run goes on without what it had to say", notContributed);
			}
		}
		if (callerEntries != null) {
			environment.putAll(callerEntries);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network environment built: knowledgeBases:"
					+ (knowledgeBaseCodes != null ? knowledgeBaseCodes.size() : 0) + " userIntent:"
					+ environment.get(StandardAgentsNetworkEnvironmentEntries.USER_INTENT) + " searchRequested:"
					+ environment.get(StandardAgentsNetworkEnvironmentEntries.SEARCH_REQUESTED) + " searchForbidden:"
					+ environment.get(StandardAgentsNetworkEnvironmentEntries.SEARCH_FORBIDDEN) + " userLanguage:"
					+ environment.get(StandardAgentsNetworkEnvironmentEntries.USER_LANGUAGE) + " contributors:"
					+ contributors.size() + " callerEntries:" + (callerEntries != null ? callerEntries.size() : 0)
					+ " entries:" + new ArrayList<String>(environment.keySet()));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Knowledge base codes seeded into the network environment: " + knowledgeBaseCodes);
		}
		return environment;
	}
}
