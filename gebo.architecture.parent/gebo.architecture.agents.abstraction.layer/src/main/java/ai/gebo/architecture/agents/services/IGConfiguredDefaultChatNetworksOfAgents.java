/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.PipelineType;

/**
 * The default chat networks of agents of the application configuration, the last
 * option when neither the chat profile nor an administrator chose one.
 */
public interface IGConfiguredDefaultChatNetworksOfAgents {
	/**
	 * @param pipelineType the chat pipeline type
	 * @return the code of the configured default network, null when none is
	 */
	public String getConfiguredDefaultChatNetworkOfAgents(PipelineType pipelineType);
}
