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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.services.IGAgentCommands;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The messages an agent may send to the agents of its communication list, given to
 * its model as tools: one tool for each agent it may reach, named after it and
 * described by what that agent does. Calling one hands the agent the work written
 * in the call: the tool gives the command to the {@link IGAgentCommands} the agent
 * loaded, which ends the model's call; the network delivers the command and
 * executes the agent again once the agents it commanded answered.
 * <p>
 * Only the agents taking a text as input are given a tool: a model writes its
 * request in words, an agent commanded with a structure of its own is left to the
 * agents that route with a plan.
 */
public final class AgentCommandTools {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentCommandTools.class);
	/** How the tool of an agent is named: this, then the agent's network name. */
	public static final String TOOL_NAME_PREFIX = "askAgent_";
	private static final int MAX_TOOL_NAME_LENGTH = 64;
	static final String NOT_AVAILABLE = "Handing work to another agent is not available here: do the work with your own tools.";
	static final String NO_REQUEST = "The request is missing: write in it everything the agent needs to do the work.";
	static final String HANDED_OVER = "The work was handed to the agent: your work stops here and goes on when it answered.";
	/** How every tool's description ends: what the agent must be given and what calling the tool does to the model's work. */
	static final String HOW_TO_ASK = " The agent sees nothing of this conversation nor of what you found: write in the"
			+ " request everything it needs - the content to use, word for word when it matters, the name and format"
			+ " of what to produce, the files and templates to use, what to leave as it is."
			+ " You will not see what this tool returns: your work stops here, and you are executed again when the"
			+ " agent answered, told its answer. To hand work to several agents at once, call their tools together"
			+ " in the same step.";

	private AgentCommandTools() {
	}

	/** What the model gives the tool. */
	@Data
	@NoArgsConstructor
	@JsonClassDescription("A piece of work handed to another agent")
	public static class Param {
		@JsonPropertyDescription("Everything the agent needs to do the work, in words: it sees nothing else")
		private String request = null;
	}

	/**
	 * The tools of an agent: one for each agent of its communication list that is
	 * allocated in the network and takes a text as input.
	 *
	 * @param persona   the agent in the network; null, or one reaching nobody, has no tool
	 * @param agentsDao the agents allocated in the network; null gives no tool
	 */
	public static List<ToolCallback> of(AgentNetworkParticipant persona, IGAgentsNetworkRuntimeDao agentsDao) {
		final List<ToolCallback> tools = new ArrayList<ToolCallback>();
		for (Map.Entry<String, String> named : toolNames(reachable(persona, agentsDao)).entrySet()) {
			final String agentName = named.getValue();
			tools.add(ToolCallbackDeclarationUtil.declare(
					(Param param, ToolContext toolContext) -> ask(agentName, param, toolContext), named.getKey(),
					description(agentName, agentsDao), Param.class, String.class, null));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agent:" + (persona != null ? persona.getNetworkAgentName() : null) + " is given "
					+ tools.size() + " tool(s) to hand work to other agents: "
					+ tools.stream().map(x -> x.getToolDefinition().name()).toList());
		}
		return tools;
	}

	/**
	 * The network names of the agents the given one may hand work to through a tool:
	 * the ones of its communication list allocated in the network that take a text.
	 */
	public static List<String> reachable(AgentNetworkParticipant persona, IGAgentsNetworkRuntimeDao agentsDao) {
		final List<String> reachable = new ArrayList<String>();
		if (persona == null || persona.getCommunicationList() == null || agentsDao == null) {
			return reachable;
		}
		for (String agentName : persona.getCommunicationList()) {
			if (agentName == null || reachable.contains(agentName)) {
				continue;
			}
			final RuntimeAgentInfos agent = find(agentName, agentsDao);
			if (agent == null || agent.getService() == null) {
				LOGGER.warn("Agent:" + persona.getNetworkAgentName() + " lists the agent:" + agentName
						+ " which is not allocated in the network: no tool to hand it work");
				continue;
			}
			final Class<?> inputType = agent.getService().getInputType();
			if (inputType == null || !inputType.isAssignableFrom(String.class)) {
				LOGGER.info("Agent:" + persona.getNetworkAgentName() + " lists the agent:" + agentName
						+ " whose input is not a text (" + (inputType != null ? inputType.getName() : null)
						+ "): no tool to hand it work");
				continue;
			}
			reachable.add(agentName);
		}
		return reachable;
	}

	/** The tool names of the agents given, each to its agent: no two the same, none longer than a model takes. */
	static Map<String, String> toolNames(List<String> agentNames) {
		final Map<String, String> names = new LinkedHashMap<String, String>();
		for (String agentName : agentNames) {
			String base = TOOL_NAME_PREFIX + agentName.replaceAll("[^A-Za-z0-9_-]", "_");
			if (base.length() > MAX_TOOL_NAME_LENGTH) {
				base = base.substring(0, MAX_TOOL_NAME_LENGTH);
			}
			String name = base;
			for (int suffix = 2; names.containsKey(name); suffix++) {
				final String tail = "_" + suffix;
				name = base.substring(0, Math.min(base.length(), MAX_TOOL_NAME_LENGTH - tail.length())) + tail;
			}
			names.put(name, agentName);
		}
		return names;
	}

	/** What the model reads to decide to hand work to the agent: what the agent is and does. */
	static String description(String agentName, IGAgentsNetworkRuntimeDao agentsDao) {
		final RuntimeAgentInfos agent = find(agentName, agentsDao);
		final StringBuilder text = new StringBuilder("Hands a piece of work to the agent ").append(agentName);
		final String described = agent == null ? null
				: agent.getConfig() != null && agent.getConfig().getDescription() != null
						? agent.getConfig().getDescription()
						: agent.getService() != null ? agent.getService().getDescription() : null;
		if (described != null && !described.isBlank()) {
			text.append(": ").append(described.strip());
		}
		if (text.charAt(text.length() - 1) != '.') {
			text.append('.');
		}
		final List<String> capabilities = capabilities(agent);
		if (!capabilities.isEmpty()) {
			text.append(" It can: ").append(String.join("; ", capabilities)).append('.');
		}
		return text.append(HOW_TO_ASK).toString();
	}

	private static List<String> capabilities(RuntimeAgentInfos agent) {
		if (agent == null || agent.getService() == null) {
			return List.of();
		}
		try {
			final AgentCapabilities capabilities = agent.getService().getAgentCapabilities(agent.getConfig());
			return capabilities != null ? capabilities.getCapabilities().stream()
					.filter(x -> x != null && !x.isBlank()).map(String::strip).toList() : List.of();
		} catch (Throwable th) {
			LOGGER.warn("Cannot read the capabilities of the agent service " + agent.getService().getId()
					+ " to describe its tool", th);
			return List.of();
		}
	}

	private static RuntimeAgentInfos find(String agentName, IGAgentsNetworkRuntimeDao agentsDao) {
		try {
			return agentsDao != null ? agentsDao.findAgentByCode(agentName) : null;
		} catch (Exception e) {
			LOGGER.warn("Cannot resolve the agent '" + agentName + "' of the network", e);
			return null;
		}
	}

	/** Gives the command to the loaded {@link IGAgentCommands}: taken, it ends the model's call. */
	static String ask(String agentName, Param param, ToolContext toolContext) {
		final IGAgentCommands commands = GeboToolCallingManagerFactory.control(toolContext, IGAgentCommands.class);
		if (commands == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The tool handing work to agent:" + agentName + " runs where no agent can be commanded");
			}
			return NOT_AVAILABLE;
		}
		if (param == null || param.getRequest() == null || param.getRequest().isBlank()) {
			return NO_REQUEST;
		}
		final String refused = commands.command(agentName, param.getRequest().strip());
		return refused != null ? refused : HANDED_OVER;
	}
}
