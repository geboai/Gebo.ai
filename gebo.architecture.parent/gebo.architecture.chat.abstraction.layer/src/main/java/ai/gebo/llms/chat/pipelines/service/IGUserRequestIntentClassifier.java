/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

/**
 * Rewrites the current user request against the chat history (resolving references
 * such as "it" or "as before") and classifies the kind of deliverable the user
 * expects (a direct answer, a procedure, an analysis...), so every router shapes the
 * answer the same way.
 */
public interface IGUserRequestIntentClassifier {
	/**
	 * Classifies the current request, setting its rewritten query and its user intent.
	 *
	 * @return the deliverable the user expects
	 */
	public DeliverableIntent classifyUserRequest(ChatPipelineExecutionRuntimeData runtimeData, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException;
}
