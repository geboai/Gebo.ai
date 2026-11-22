/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.a2aclients.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * The settings of the conversation's files sent to the remote A2A agents a network
 * calls. Off when not set: a file then leaves for a remote agent only through what
 * the network writes in its message. The files sent are among the ones the request
 * carries for the models, so the kinds of file the installation sends to its models
 * (ai.gebo.images.chat-enabled and the like) apply before these limits.
 */
@Configuration
public class A2AOutboundFilesConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(A2AOutboundFilesConfig.class);

	@Value("${ai.gebo.a2a.clients.send-files-enabled:false}")
	boolean sendFilesEnabled;
	@Value("${ai.gebo.a2a.clients.send-max-file-mb:2}")
	int sendMaxFileMb;
	@Value("${ai.gebo.a2a.clients.send-max-total-mb:4}")
	int sendMaxTotalMb;

	@PostConstruct
	public void apply() {
		RemoteA2ANetworkAgentService.setOutboundFiles(sendFilesEnabled, Math.max(0, sendMaxFileMb) * 1024L * 1024L,
				Math.max(0, sendMaxTotalMb) * 1024L * 1024L);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Conversation files sent to remote A2A agents: enabled=" + sendFilesEnabled + " max file "
					+ sendMaxFileMb + " MB, max total " + sendMaxTotalMb + " MB");
		}
	}
}
