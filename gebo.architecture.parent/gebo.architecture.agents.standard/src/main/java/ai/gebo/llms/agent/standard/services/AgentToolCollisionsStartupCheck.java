/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.ai.model.LibraryToolCollision;
import ai.gebo.security.services.IGeboSystemUserService;
import ai.gebo.security.services.IdentityUtil;

/**
 * Writes in the log, as errors, the tool collisions of the configured agents when
 * the application is up: a tool of a library having the name of another tool the same
 * agent mounts, which the library's tool replaces. Agents entering a run write theirs
 * too, with the tools of the user they run for and the sources their network adds.
 * <p>
 * It runs on {@link ApplicationReadyEvent}, not on the refresh of a context: reading
 * the tools of the sources can start the child contexts of Spring Cloud LoadBalancer,
 * whose refresh events would call a refresh listener again until the stack overflows.
 * It runs under the platform's system identity: with no identity at all the MCP tools
 * cannot be read, and they are the likeliest to collide. A failure here is logged and
 * stops nothing.
 */
@Component
public class AgentToolCollisionsStartupCheck {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentToolCollisionsStartupCheck.class);

	private final ObjectProvider<IAgentConfigDao> agentConfigsProvider;
	private final ObjectProvider<IGAgentServiceRuntimeDao> agentServicesProvider;
	private final ObjectProvider<IGeboSystemUserService> systemUserProvider;

	public AgentToolCollisionsStartupCheck(ObjectProvider<IAgentConfigDao> agentConfigsProvider,
			ObjectProvider<IGAgentServiceRuntimeDao> agentServicesProvider,
			ObjectProvider<IGeboSystemUserService> systemUserProvider) {
		this.agentConfigsProvider = agentConfigsProvider;
		this.agentServicesProvider = agentServicesProvider;
		this.systemUserProvider = systemUserProvider;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		try {
			final IGeboSystemUserService systemUser = systemUserProvider.getIfAvailable();
			final int found = systemUser != null
					? IdentityUtil.create(systemUser.getUsername(), systemUser.getRoles()).doRunAsWithReturn(this::check)
					: check();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agents tool collisions checked at startup: " + found + " found");
			}
		} catch (Throwable th) {
			LOGGER.error("The tool collisions of the agents could not be checked at startup", th);
		}
	}

	/** Logs the collisions of every agent naming a library; how many there are. */
	int check() {
		final IAgentConfigDao configs = agentConfigsProvider.getIfAvailable();
		final IGAgentServiceRuntimeDao services = agentServicesProvider.getIfAvailable();
		if (configs == null || services == null) {
			return 0;
		}
		int found = 0;
		for (GAgentConfig config : configs.getConfigurations()) {
			if (config == null || config.getLibraryTypeIds() == null || config.getLibraryTypeIds().isEmpty()
					|| config.getAgentServiceId() == null) {
				continue;
			}
			final IGGenericAgentService service = services.findByCode(config.getAgentServiceId());
			final AgentMountedTools mounted = service != null ? service.getMountedTools(config) : null;
			for (LibraryToolCollision collision : mounted != null ? mounted.getCollisions()
					: List.<LibraryToolCollision>of()) {
				LOGGER.error("Tool name collision on agent config code:" + config.getCode() + ": " + collision.written());
				found++;
			}
		}
		return found;
	}
}
