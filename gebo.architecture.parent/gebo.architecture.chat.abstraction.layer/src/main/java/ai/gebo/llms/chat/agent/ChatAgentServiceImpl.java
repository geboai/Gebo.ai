package ai.gebo.llms.chat.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.impl.GAbstractReactiveAgentService;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener.ToolCallExecuted;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

@Service
public class ChatAgentServiceImpl extends
		GAbstractReactiveAgentService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope, GeboChatResponse>
		implements IChatAgentService {

	private static final String END_AGENT_LOOP = "END_AGENT-LOOP-";
	private static final String TOOL_CALLED = "TOOL-CALLED-";
	private static final String RESPONSE = "RESPONSE: ";
	private static final String BEGIN_AGENT_LOOP = "BEGIN_AGENT-LOOP-";
	private static final String NEWLINE = "\r\n";
	public static final String AGENT_CONTROL_CONTINUE_PROMPT_PARAM = "AGENT_CONTROL_CONTINUE_PROMPT_PARAM";
	public static final String AGENT_CONTROL_FINISHED_PROMPT_PARAM = "AGENT_CONTROL_FINISHED_PROMPT_PARAM";
	private static final String MAX_ITERATIONS_PROMPT_PARAM = "MAX_ITERATIONS";
	public static final String CURRENT_ITERATION_PROMPT_PARAM = "CURRENT_ITERATION";
	public static final String AGENT_SESSION_STORY_PROMPT_PARAM = "AGENT_SESSION_STORY";
	private static final String CHAT_AGENT_SERVICE_DESC = "Chat agent service";
	public static final String CHAT_AGENT_SERVICE = "ChatAgentService";
	public static final String AGENT_CONTROL_FINISHED = "<AGENT-CONTROL-STOP/>";
	public static final String AGENT_CONTROL_MORE_TOOLS = "<AGENT-CONTROL-MORE-TOOLS/>";

	public ChatAgentServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IAgentConfigDao configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao);

	}

	@Override
	public String getId() {

		return CHAT_AGENT_SERVICE;
	}

	@Override
	public String getDescription() {

		return CHAT_AGENT_SERVICE_DESC;
	}

	@Override
	protected Flux<IGPartialOperation<GeboChatMessageEnvelope>> createResponse(
			ChatPipelineExecutionRuntimeData runtimeData, List<GeboChatResponse> pastResponses,
			IGConfigurableChatModel agentModel, GAgentConfig agentConfig, GAgentRole agentRole, int i, int maxLoop,
			GPromptTemplateConfig agentPrompt, ReactiveIdentityUtil runAs, ToolCallsListener callsListener)
			throws LLMConfigException {
		String loopHistory = createCycleHistoryVariable(pastResponses, runtimeData);
		final GeboChatResponse response = runtimeData.getChatResponse();
		Map<String, Object> params = new HashMap<>();
		params.put(AGENT_SESSION_STORY_PROMPT_PARAM, loopHistory);
		params.put(CURRENT_ITERATION_PROMPT_PARAM, "" + i);
		params.put(MAX_ITERATIONS_PROMPT_PARAM, "" + maxLoop);
		params.put(AGENT_CONTROL_FINISHED_PROMPT_PARAM, AGENT_CONTROL_FINISHED);
		params.put(AGENT_CONTROL_CONTINUE_PROMPT_PARAM, AGENT_CONTROL_MORE_TOOLS);

		final IChatRequestContext sampledContext = runtimeData.getRequestResources().createChatRequestContext();
		return runAs.doRunAsWithReturnAndException(() -> {
			final StringBuffer cumulatedContent = new StringBuffer();

			final IChatRequestContext context = sampledContext.withToolCallListener(callsListener);
			Flux<ChatResponse> stream = agentModel.streamResponse(agentPrompt, params, context);
			final Vector<Object> cumulatedToolCalls = new Vector<>();
			Flux<IGPartialOperation<GeboChatMessageEnvelope>> bodyStream = stream.map(cr -> {
				String contentExtracted = extractContent(cr);
				cumulatedContent.append(contentExtracted);
				// inspectToolCalls(cr, cumulatedToolCalls);
				return IGPartialOperation.of(new GeboChatMessageEnvelope(contentExtracted), false);
			});
			Flux<IGPartialOperation<GeboChatMessageEnvelope>> lastItem = null;

			lastItem = Flux.defer(() -> {
				String queryResponse = cumulatedContent.toString();
				boolean lastMessage = (i == maxLoop) || (queryResponse.indexOf(AGENT_CONTROL_FINISHED) >= 0);
				response.setQueryResponse(
						queryResponse.replace(AGENT_CONTROL_FINISHED, "").replace(AGENT_CONTROL_MORE_TOOLS, ""));
				response.setCalledFunctions(renderFunctions(callsListener.getCalls()));
				GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
				envelope.setLastMessage(lastMessage);
				return Flux.just(IGPartialOperation.of(envelope, lastMessage));
			});

			return Flux.concat(bodyStream, lastItem);

		});
	}

	protected List<CalledFunction> renderFunctions(List<ToolCallExecuted> calls) {

		return calls != null ? calls.stream().map(x -> new CalledFunction(x.getName(), x.getToolDescription(),
				List.of(), x.getToolInput() != null ? List.of(x.getToolInput()) : List.of())).toList() : List.of();
	}

	private String createCycleHistoryVariable(List<GeboChatResponse> pastResponses,
			ChatPipelineExecutionRuntimeData runtimeData) {
		StringBuffer buffer = new StringBuffer();
		int index = 0;
		for (GeboChatResponse geboChatResponse : pastResponses) {
			buffer.append(BEGIN_AGENT_LOOP + index);
			buffer.append(NEWLINE);

			if (geboChatResponse.getCalledFunctions() != null && !geboChatResponse.getCalledFunctions().isEmpty()) {
				int tcIndex = 0;
				for (CalledFunction callF : geboChatResponse.getCalledFunctions()) {
					buffer.append(
							TOOL_CALLED + tcIndex + ": " + callF.getFunctionName() + " params:" + callF.getParams());
					buffer.append(NEWLINE);
					tcIndex++;
				}
			}
			buffer.append(RESPONSE + geboChatResponse.getQueryResponse());
			buffer.append(NEWLINE);
			buffer.append(END_AGENT_LOOP + index);
			buffer.append(NEWLINE);
			index++;
		}
		return buffer.toString();
	}

	@Override
	protected Function<IGPartialOperation<GeboChatMessageEnvelope>, IGPartialOperation<GeboChatMessageEnvelope>> createRAggregator(
			AtomicReference<List<GeboChatResponse>> aggregatorList) {
		Function<IGPartialOperation<GeboChatMessageEnvelope>, IGPartialOperation<GeboChatMessageEnvelope>> aggregator = new Function<IGPartialOperation<GeboChatMessageEnvelope>, IGPartialOperation<GeboChatMessageEnvelope>>() {

			@Override
			public IGPartialOperation<GeboChatMessageEnvelope> apply(IGPartialOperation<GeboChatMessageEnvelope> t) {
				if (t != null && t.getData() != null && t.getData().getContent() instanceof GeboChatResponse response) {
					aggregatorList.get().add(response.jsonClone());
				}
				return t;
			}
		};
		return aggregator;
	}

}
