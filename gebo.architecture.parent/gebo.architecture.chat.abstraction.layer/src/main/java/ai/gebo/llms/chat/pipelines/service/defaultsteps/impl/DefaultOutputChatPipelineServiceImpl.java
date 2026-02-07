package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IOutputChatPipelineService;

@Component
public class DefaultOutputChatPipelineServiceImpl extends BaseOutputChatPipelineService
		implements IOutputChatPipelineService {

	public DefaultOutputChatPipelineServiceImpl(IGAIDocumentsCacheService documentsCacheService,
			IGChatStorageAreaService chatStorageAreaService, DocumentReferenceRepository docreferenceRepo,
			UserUploadContentServerSideRepository uploadsRepo, LLMGeneratedResourceRepository generatedRepo,
			IGChatSessionLifeCycleService chatSessionLifecycleService) {
		super(documentsCacheService, chatStorageAreaService, docreferenceRepo, uploadsRepo, generatedRepo,
				chatSessionLifecycleService);

	}

	public static final String DEFAULT_OUTPUT_STEP = "default-output-step";

	@Override
	public StepExecutorType getExecutorType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStepId() {

		return DEFAULT_OUTPUT_STEP;
	}

	@Override
	public GeboChatResponse execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException, GeboChatSessionLifecycleException {
		LLMChatRequestResources requestResources = super.integrateWithAISuggestedDocuments(runtimeData, chatModel,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		return null;
	}

}
