/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * A network of agents run outside a chat: a text request in, the text of the
 * network's final answer out. It is how a network, or a single agent, is served
 * to the outside as an A2A skill or as an MCP tool.
 */
public class GTextAgentsNetworkService extends GAbstractReactiveOutputAgentsNetworkService<String, String> {
	static final String SERVICE_ID = "TEXT_NETWORK_SERVICE";
	private static final String SERVICE_DESCRIPTION = "Text-to-text network of agents, served outside a chat (A2A skills, MCP tools)";

	public GTextAgentsNetworkService(IGAgentServiceRuntimeDao agentsServicesRepository, IAgentRoleDao rolesDao,
			IGeboThreadManager threadManager, GAgentsNetwork network, INotificationSink notificationSink,
			Class<String> inputType, Class<String> outputType, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao, AdapterWithFlux<?, String> adapterWithFlux) {
		super(agentsServicesRepository, rolesDao, threadManager, network, notificationSink, inputType, outputType,
				runAs, agentsDao, adapterWithFlux);
	}

	@Override
	protected <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental) {
		return incremental != null ? incremental : actualOutput;
	}

	@Override
	public void dispose() {
	}

	@Override
	public String getId() {
		return SERVICE_ID;
	}

	@Override
	public String getDescription() {
		return SERVICE_DESCRIPTION;
	}
}
