package ai.gebo.llms.chat.pipelines.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatPipelineExecutionRuntimeData {
	public ChatPipelineExecutionRuntimeData(ChatPipelineConfiguration configuration, int contextWindowSize,
			LLMChatRequestResources requestResources, boolean streamingOutput) {
		this(configuration, contextWindowSize, contextWindowSize, new ArrayList(), null, new ArrayList(),
				streamingOutput, requestResources);
	}

	private final ChatPipelineConfiguration configuration;
	private final int contextWindowSize;
	private int remainingTokens = 0;
	private List<IChatPipelineStepRuntimeData> executedSteps = new ArrayList<IChatPipelineStepRuntimeData>();
	private GeboChatResponse chatResponse = null;
	private List<ai.gebo.llms.chat.pipelines.model.RoutingDecision> routingDecisions = new ArrayList<ai.gebo.llms.chat.pipelines.model.RoutingDecision>();
	private final boolean streamingOutput;
	private final LLMChatRequestResources requestResources;
	private final Map<String, Object> sharedEnvironment = new HashMap<String, Object>();

}
