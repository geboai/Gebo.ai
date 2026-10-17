/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An agent of the network a chat is handed to, with the tools it mounts: told to
 * the user in the chat infos, so the tools shown are the ones that can be called.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentToolsInfo {
	/** The code of the network the agent takes part in. */
	private String networkCode = null;
	/** The name of the agent in the network. */
	private String agentName = null;
	/** What the agent does, from its configuration. */
	private String agentDescription = null;
	/** The id of the service running the agent. */
	private String agentServiceId = null;
	/** Whether every registered tool is mounted (AUTO) or the chosen ones (SELECTED). */
	private String mountMode = null;
	/** The tools the agent mounts, by category. */
	private List<ToolCategoriesTree> tools = new ArrayList<ToolCategoriesTree>();
}
