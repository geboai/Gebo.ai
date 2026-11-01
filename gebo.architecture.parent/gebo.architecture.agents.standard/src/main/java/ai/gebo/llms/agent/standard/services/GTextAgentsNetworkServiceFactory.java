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

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * Builds {@link GTextAgentsNetworkService}s: any network of agents run as
 * text-to-text, the way {@link GTextAgentsNetworkRunner} serves networks and
 * agents outside a chat.
 */
@Service
public class GTextAgentsNetworkServiceFactory
		extends GAbstractReactiveOutputAgentsNetworkServiceFactory<String, String, GTextAgentsNetworkService> {
	public static final String FACTORY_ID = "TEXT_NETWORK";
	private static final String FACTORY_DESCRIPTION = "Text-to-text network of agents factory (A2A skills, MCP tools)";
	private final IGeboThreadManager threadManager;

	public GTextAgentsNetworkServiceFactory(
			IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern reactiveAgentAdapterFactoryRepo,
			IGeboThreadManager threadManager, IGRuntimeBinder runtimeBinder) {
		super(FACTORY_ID, FACTORY_DESCRIPTION, GTextAgentsNetworkService.class, reactiveAgentAdapterFactoryRepo,
				runtimeBinder);
		this.threadManager = threadManager;
	}

	@Override
	protected GTextAgentsNetworkService createAgentsNetworkService(GAgentsNetwork network,
			INotificationSink notificationSink, Class<String> inputType, Class<String> outputType,
			ReactiveIdentityUtil runAs, Map<String, RuntimeAgentInfos> agentsCache, AdapterWithFlux adapter) {
		IGAgentServiceRuntimeDao agentServiceRuntimeDao = runtimeBinder
				.getImplementationOf(IGAgentServiceRuntimeDao.class);
		IAgentRoleDao agentRoleDao = runtimeBinder.getImplementationOf(IAgentRoleDao.class);
		return new GTextAgentsNetworkService(agentServiceRuntimeDao, agentRoleDao, threadManager, network,
				notificationSink, inputType, outputType, runAs, IGAgentsNetworkRuntimeDao.of(agentsCache), adapter);
	}

	@Override
	public Class<String> getInputType() {
		return String.class;
	}

	@Override
	public Class<String> getOutputType() {
		return String.class;
	}
}
