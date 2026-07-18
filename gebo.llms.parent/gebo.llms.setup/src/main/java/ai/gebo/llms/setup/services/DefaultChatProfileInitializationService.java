/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.setup.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileManagementService;

/**
 * Ensures the default chat profile exists on every service that carries the
 * actual chat/RAG stack (monolith, brain), instead of being created inline by
 * the fast-installation flow. {@link IGChatProfileManagementService#getOrCreateDefaultChatProfile()}
 * is already get-or-create, so this is a no-op once the profile exists.
 *
 * Previously this call lived in
 * {@code ai.gebo.architecture.fastsetup.system.services.GeboFastInstallationSetupService},
 * which pulled {@code gebo.architecture.chat.abstraction.layer} (and transitively
 * {@code gebo.architecture.llms.abstraction.layer} + {@code gebo.architecture.documents.cache})
 * onto every consumer of {@code gebo.architecture.fastsetup.system} - including
 * heimdall, which has no chat/RAG procedure of its own and crashed on an
 * unrelated deep-search bean dragged in by that chain.
 */
@Component
@Scope("singleton")
public class DefaultChatProfileInitializationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultChatProfileInitializationService.class);

	private final IGChatProfileManagementService chatProfileManagementService;

	public DefaultChatProfileInitializationService(IGChatProfileManagementService chatProfileManagementService) {
		this.chatProfileManagementService = chatProfileManagementService;
	}

	@Scheduled(initialDelay = 20000)
	public void onTick() {
		try {
			chatProfileManagementService.getOrCreateDefaultChatProfile();
		} catch (Throwable th) {
			LOGGER.error("Error ensuring the default chat profile exists", th);
		}
	}
}
