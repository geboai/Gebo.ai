package ai.gebo.llms.chat.pipelines.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRequestResourcesBuilder;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.model.PipelineRoutingInfos;
import ai.gebo.llms.chat.pipelines.model.PipelineRoutingInfosMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IChatPipelinesExecutor;
import ai.gebo.llms.chat.pipelines.service.IInputChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IIntermediateProcessingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component

@AllArgsConstructor
public class ChatPipelinesExecutorImpl implements IChatPipelinesExecutor {
	protected final IChatPipelineStepServiceRepositoryPattern stepsServiceRepoPattern;
	protected final ChatPipelinesConfiguration pipelinesConfiguration;
	protected final IGChatSessionStateService sessionStateService;
	protected final IGChatRequestResourcesBuilder chatRequestResourcesBuilder;
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatPipelinesExecutorImpl.class);

	protected void add(ChatPipelineExecutionRuntimeData runtimeData, IChatPipelineStepRuntimeData stepdata) {
		runtimeData.getExecutedSteps().add(stepdata);
		List<IStepContribution> enrichings = stepdata.getContextEnrichingContribution();
		int budget = runtimeData.getRemainingTokens();
		if (enrichings != null) {
			for (IStepContribution abstractContextEnrichingData : enrichings) {
				budget -= abstractContextEnrichingData.getRenderedTokensLength() != null
						? abstractContextEnrichingData.getRenderedTokensLength().intValue()
						: 0;

			}
			runtimeData.setRemainingTokens(budget);
		}
	}

	protected ChatPipelineExecutionRuntimeData executeUntillOutput(GeboChatRequest request, GeboChatResponse response,
			GUserChatContext context, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String pipelineCode, boolean streaming) throws ChatPipelineException, IOException, LLMConfigException {
		ChatPipelineConfiguration config = getCfgOrDefault(pipelineCode);
		IChatPipelineStepService firstService = getStep(config.getStepInputId());
		IChatPipelineStepService routerService = getStep(config.getStepRouterId());
		int tokensBudget = Math.min(serviceModel != null ? serviceModel.getContextLength() : Integer.MAX_VALUE,
				chatModel != null ? chatModel.getContextLength() : Integer.MAX_VALUE) / 2;
		LLMChatRequestResources resources = null;
		try {
			resources = chatRequestResourcesBuilder.buildRequestResources(request, context, tokensBudget);
		} catch (IOException | GeboPersistenceException | GeboContentHandlerSystemException
				| GeboIngestionException e) {
			final String msg = "Exception while building request resources";
			LOGGER.error(msg, e);
			throw new ChatPipelineException(msg, e);
		}
		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(config,
				chatModel.getContextLength(), resources, response, context, streaming);
		// putting a sintetic routing decision for the first 2 steps to mantain the
		// routing coherency
		runtimeData.getRoutingDecisions()
				.add(new RoutingDecision(List.of(config.getStepInputId(), config.getStepRouterId()), null, null));
		if (firstService instanceof IInputChatPipelineStepService inputService) {
			IChatPipelineStepRuntimeData data = inputService.execute(runtimeData, chatModel, serviceModel);

			if (data.getEnvironmentContributions() != null) {
				runtimeData.getSharedEnvironment().putAll(data.getEnvironmentContributions());
			}
			add(runtimeData, data);
		} else
			throw new ChatPipelineException("The step service " + firstService.getStepId() + " is not an input one");
		if (routerService instanceof IRoutingChatPipelineStepService routing) {
			ai.gebo.llms.chat.pipelines.model.RoutingDecision routeData = routing.execute(runtimeData, chatModel,
					serviceModel);
			if (runtimeData.getChatResponse() != null) {
				runtimeData.getChatResponse().setPipelineRouterDecisionCode(routeData.getPipelineRouterDecisionCode());
			}
			add(runtimeData, routeData.getProcessedOutput());
			if (routeData.getProcessedOutput() != null
					&& routeData.getProcessedOutput().getEnvironmentContributions() != null) {
				runtimeData.getSharedEnvironment().putAll(routeData.getProcessedOutput().getEnvironmentContributions());
			}
			runtimeData.getRoutingDecisions().add(routeData);
		} else
			throw new ChatPipelineException("The step service " + firstService.getStepId() + " is not a routing one");
		IChatPipelineStepService nextService = null;
		do {
			nextService = getNextStep(runtimeData);
			if (nextService.getStepType() != IChatPipelineStepService.StepType.OUTPUT) {
				if (nextService instanceof IRoutingChatPipelineStepService router) {
					RoutingDecision routeData = router.execute(runtimeData, chatModel, serviceModel);
					if (runtimeData.getChatResponse() != null) {
						runtimeData.getChatResponse()
								.setPipelineRouterDecisionCode(routeData.getPipelineRouterDecisionCode());
					}
					add(runtimeData, routeData.getProcessedOutput());
					if (routeData.getProcessedOutput() != null
							&& routeData.getProcessedOutput().getEnvironmentContributions() != null) {
						runtimeData.getSharedEnvironment()
								.putAll(routeData.getProcessedOutput().getEnvironmentContributions());
					}
					runtimeData.getRoutingDecisions().add(routeData);
				} else if (nextService instanceof IIntermediateProcessingChatPipelineStepService intermediateStep) {
					IChatPipelineStepRuntimeData data = intermediateStep.execute(runtimeData, chatModel, serviceModel);
					add(runtimeData, data);
					if (data.getEnvironmentContributions() != null) {
						runtimeData.getSharedEnvironment().putAll(data.getEnvironmentContributions());
					}
				} else {
					throw new ChatPipelineException(
							"The step service " + nextService.getStepId() + " is not an intermediate service");
				}
			}
		} while (nextService != null && nextService.getStepType() != IChatPipelineStepService.StepType.OUTPUT);
		return runtimeData;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamingExecute(GeboChatRequest request, GUserChatContext context,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException, LLMConfigException {
		GeboChatResponse response = createResponse(request, context);
		ChatPipelineExecutionRuntimeData runtimeData = executeUntillOutput(request, response, context, chatModel,
				serviceModel, pipelineCode, true);
		IChatPipelineStepService nextStep = getNextStep(runtimeData);
		if (nextStep instanceof IStreamingOutputChatPipelineService streamingOutputService) {
			Flux<GeboChatMessageEnvelope> first = Flux.just(buildRoutingInfos(runtimeData, response));
			Flux<GeboChatMessageEnvelope> out = streamingOutputService.execute(runtimeData, chatModel, serviceModel);
			return Flux.concat(first, out);
		}
		throw new ChatPipelineException("The step service " + nextStep.getStepId() + " is not a streaming one");
	}

	protected PipelineRoutingInfosMessageEnvelope buildRoutingInfos(ChatPipelineExecutionRuntimeData runtimeData,
			GeboChatResponse response) {
		PipelineRoutingInfosMessageEnvelope data = new PipelineRoutingInfosMessageEnvelope();
		PipelineRoutingInfos content = new PipelineRoutingInfos();
		content.setPipelineRouterDecisionCode(response.getPipelineRouterDecisionCode());
		data.setContent(content);
		runtimeData.getRoutingDecisions().forEach(x -> {
			x.getFutureRoute().forEach(stepId -> {
				data.getContent().getStepIds().add(stepId);
			});
		});
		return data;
	}

	@Override
	public GeboChatResponse execute(GeboChatRequest request, GUserChatContext context,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException, LLMConfigException {
		GeboChatResponse response = createResponse(request, context);
		ChatPipelineExecutionRuntimeData runtimeData = executeUntillOutput(request, response, context, chatModel,
				serviceModel, pipelineCode, false);
		IChatPipelineStepService nextStep = getNextStep(runtimeData);
		if (nextStep instanceof IOutputChatPipelineService outputService) {
			return outputService.execute(runtimeData, chatModel, serviceModel);
		}
		throw new ChatPipelineException(
				"The step service " + nextStep.getStepId() + " is not an IOutputChatPipelineService");
	}

	protected GeboChatResponse createResponse(GeboChatRequest request, GUserChatContext context) {
		GeboChatResponse response = new GeboChatResponse();
		response.setQuery(request.getQuery());
		response.setUserChatContextCode(context.getCode());
		return response;
	}

	private IChatPipelineStepService getStep(String id) throws ChatPipelineException {
		IChatPipelineStepService service = this.stepsServiceRepoPattern.findByCode(id);
		if (service == null)
			throw new ChatPipelineException("The step service " + id + " does not exist");
		return service;
	}

	private IChatPipelineStepService getNextStep(ChatPipelineExecutionRuntimeData runtimeData)
			throws ChatPipelineException {
		if (runtimeData.getRoutingDecisions() == null || runtimeData.getRoutingDecisions().isEmpty())
			throw new ChatPipelineException("No routing informations in this pipeline");
		List<IChatPipelineStepRuntimeData> stepsDone = runtimeData.getExecutedSteps();
		IChatPipelineStepRuntimeData lastStep = stepsDone != null && !stepsDone.isEmpty()
				? stepsDone.get(stepsDone.size() - 1)
				: null;
		if (lastStep == null)
			throw new ChatPipelineException("executing with no steps done in the runtime infos");

		String stepId = lastStep.getStepId();
		int routingDecisionIndex = 0;
		RoutingDecision currentRoutingThread = null;
		for (RoutingDecision r : runtimeData.getRoutingDecisions()) {
			if (r.getFutureRoute().contains(stepId)) {
				currentRoutingThread = r;
				break;
			} else {
				routingDecisionIndex++;
			}
		}
		if (currentRoutingThread == null)
			throw new ChatPipelineException("The actual executed step " + stepId + " is not any routing perspective");
		int index = currentRoutingThread.getFutureRoute().indexOf(stepId);
		if (index < 0)
			throw new ChatPipelineException(
					"The actual executed step " + stepId + " is not in the actual routing perspective");
		if (index + 1 < currentRoutingThread.getFutureRoute().size()) {
			return getStep(currentRoutingThread.getFutureRoute().get(index + 1));
		} else {
			routingDecisionIndex++;
			index = 0;
			if (routingDecisionIndex < runtimeData.getRoutingDecisions().size()) {
				currentRoutingThread = runtimeData.getRoutingDecisions().get(routingDecisionIndex);
				if (index < currentRoutingThread.getFutureRoute().size()) {
					return getStep(currentRoutingThread.getFutureRoute().get(index));
				}
			}
		}
		throw new ChatPipelineException("Reaching out of execution line without any output stage");
	}

	private ChatPipelineConfiguration getCfgOrDefault(String code) throws ChatPipelineException {
		if (code != null) {
			Optional<ChatPipelineConfiguration> matching = pipelinesConfiguration.getPipelines().stream()
					.filter(x -> x.getCode() != null && x.getCode().equals(code)).findFirst();
			if (matching.isPresent())
				return matching.get();
			throw new ChatPipelineException("cannot find chat pipeline config with code=" + code);
		} else {
			Optional<ChatPipelineConfiguration> matching = pipelinesConfiguration.getPipelines().stream()
					.filter(x -> x.isDefaultPipeline()).findFirst();
			if (matching.isPresent())
				return matching.get();
			throw new ChatPipelineException("cannot find default chat pipeline config");
		}
	}

}
