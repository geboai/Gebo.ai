package ai.gebo.llms.chat.pipelines.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatPipelineExecutionRuntimeData {
	public ChatPipelineExecutionRuntimeData(ChatPipelineConfiguration configuration, int contextWindowSize,
			LLMChatRequestResources requestResources, GeboChatResponse chatResponse,
			MinimalChatContext minimalChatContext, boolean streamingOutput) {
		this(configuration, contextWindowSize, contextWindowSize, new ArrayList(), chatResponse, new ArrayList(),
				streamingOutput, requestResources, minimalChatContext);
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

}
