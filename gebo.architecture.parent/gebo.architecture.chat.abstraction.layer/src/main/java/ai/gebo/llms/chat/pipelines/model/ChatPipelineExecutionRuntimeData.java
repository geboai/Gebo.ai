package ai.gebo.llms.chat.pipelines.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatPipelineExecutionRuntimeData {
	/**
	 * The runtime data of a request, with what the controlling software adds to
	 * every model call made for it (see {@link #getToolCalling()}): the entry point
	 * of the request builds it, and a run without one is refused by the networks.
	 */
	public ChatPipelineExecutionRuntimeData(ChatPipelineConfiguration configuration, int contextWindowSize,
			LLMChatRequestResources requestResources, GeboChatResponse chatResponse,
			MinimalChatContext minimalChatContext, boolean streamingOutput, GeboToolCalling toolCalling) {
		this(configuration, contextWindowSize, contextWindowSize, new ArrayList(), chatResponse, new ArrayList(),
				streamingOutput, requestResources, minimalChatContext, toolCalling);
	}

	/** The code of the pipeline of this run; null for a run outside a pipeline. */
	public String getPipelineCode() {
		return configuration != null ? configuration.getCode() : null;
	}

	private final ChatPipelineConfiguration configuration;
	private final int contextWindowSize;
	private int remainingTokens = 0;
	private List<IChatPipelineStepRuntimeData> executedSteps = new ArrayList<IChatPipelineStepRuntimeData>();
	private GeboChatResponse chatResponse = null;
	private List<RoutingDecision> routingDecisions = new ArrayList<RoutingDecision>();
	private final boolean streamingOutput;
	private final LLMChatRequestResources requestResources;
	private final Map<String, Object> sharedEnvironment = new HashMap<String, Object>();
	private final MinimalChatContext minimalChatContext;
	/**
	 * What the controlling software adds to every model call made for the request
	 * (see {@link GeboToolCalling}): who the caller is, the limits of the tool calling
	 * loop, what the tools are provided with. The steps calling a model or a network of
	 * agents for the request pass it on, as it is or with their own merged into it.
	 * Null for a request made without one, which runs as it always has.
	 */
	private final GeboToolCalling toolCalling;

	/**
	 * The current request, the one the run serves: held by the request resources
	 * and by the minimal context alike, the same instance, read from here.
	 */
	public GeboChatRequest getCurrentRequest() {
		if (requestResources != null && requestResources.getCurrentRequest() != null) {
			return requestResources.getCurrentRequest();
		}
		return minimalChatContext != null ? minimalChatContext.getCurrentRequest() : null;
	}

}
