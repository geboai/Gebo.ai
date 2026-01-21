package ai.gebo.llms.chat.pipelines.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService.RoutingDecision;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatPipelineExecutionRuntimeData {
	public ChatPipelineExecutionRuntimeData(ChatPipelineConfiguration configuration, int contextWindowSize,
			GeboChatRequest chatRequest, GUserChatContext userChatContext, boolean streamingOutput) {
		this(configuration, contextWindowSize, contextWindowSize, chatRequest, userChatContext, new ArrayList(), null,
				new ArrayList(), streamingOutput);
	}

	private final ChatPipelineConfiguration configuration;
	private final int contextWindowSize;
	private int remainingTokens = 0;
	private GeboChatRequest chatRequest = null;
	private GUserChatContext userChatContext = null;
	private List<IChatPipelineStepRuntimeData> executedSteps = new ArrayList<IChatPipelineStepRuntimeData>();
	private GeboChatResponse chatResponse = null;
	private List<RoutingDecision> routingDecisions = new ArrayList<RoutingDecision>();
	private final boolean streamingOutput;
}
