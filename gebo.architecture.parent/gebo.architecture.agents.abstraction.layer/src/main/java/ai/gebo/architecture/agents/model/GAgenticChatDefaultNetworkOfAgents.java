/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * The system default network of agents the chats of a pipeline type are handed to,
 * chosen by an administrator: it overrides the default of the application
 * configuration, and is overridden by the network a chat profile chooses. One per
 * pipeline type, identified by it.
 */
@Data
@Document
public class GAgenticChatDefaultNetworkOfAgents {
	/** The pipeline type's name. */
	@Id
	private String id = null;
	private PipelineType pipelineType = null;
	/** The code of the network of agents. */
	private String defaultChatNetworkOfAgents = null;
	private Date dateModified = null;
}
