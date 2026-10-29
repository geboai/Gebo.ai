/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;

/**
 * What the chat pipelines understand of a request before answering it, for
 * whoever runs a request outside the chat pipelines - a harness, an A2A skill,
 * an MCP tool - so that a network of agents is told the same things about a
 * request wherever it is run from: the language the message is written in, the
 * request rewritten against the history, the deliverable the user expects and
 * whether a search is asked for or forbidden.
 */
public interface IGRequestUnderstandingService {

	/**
	 * Sets on the request the language its message is written in, when the
	 * platform's language detector is deployed and trusts its detection; nothing
	 * otherwise, and nothing when the request has it already. No model is called.
	 */
	public void detectLanguage(GeboChatRequest request);

	/**
	 * The understanding the default routing step runs first: the request's
	 * rewritten query, user intent, search flags and answer language are set on the
	 * current request of the runtime data. One service-model call.
	 *
	 * @param sink where the progress is told, null for nowhere
	 * @return the deliverable the user expects, null when no classifier is deployed
	 */
	public DeliverableIntent understand(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, INotificationSink sink)
			throws ChatPipelineException;
}
