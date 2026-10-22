/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline.steps;

import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.openchat.pipeline.OpenChatConstants;

/**
 * Open-chat streaming output step: the reactive agents-network streaming machinery of
 * {@link ReactiveChatAgentsNetworkStreamingOutputChatPipelineService} with the
 * open-chat KB-free network. The request is ended by the pipelines executor, as for
 * every streaming step.
 */
public class OpenChatAgentsNetworkStreamingStepService
		extends ReactiveChatAgentsNetworkStreamingOutputChatPipelineService {

	public OpenChatAgentsNetworkStreamingStepService(
			IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IDynamicAgentsNetworkDataSource agentsNetworkDataSource, IGChatSessionLifeCycleService lifeCycleService,
			IGAgenticChatDefaultNetworkOfAgentsService defaultNetworksService) {
		super(factory, agentsNetworkDataSource, lifeCycleService, OpenChatConstants.OPEN_CHAT_NETWORK_STREAMING_STEP,
				PipelineType.PURE_CHAT_PIPELINE, defaultNetworksService);
	}
}
