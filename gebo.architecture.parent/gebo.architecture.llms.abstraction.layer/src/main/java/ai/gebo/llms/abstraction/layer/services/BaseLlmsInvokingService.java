package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.base.GObjectRef;
import lombok.AllArgsConstructor;

/*******************************************************************************************************************
 * General base class for services using large language models
 */
@AllArgsConstructor
public class BaseLlmsInvokingService {
	protected final IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	protected final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao;
	public static final String CONSOLIDATED_TEMPLATE_VARIABLE = "consolidated";
	public static final String DOCUMENTS_TEMPLATE_VARIABLE = "documents";
	public static final String USER_QUESTION_TEMPLATE_VARIABLE = "question";

	protected IGConfigurableEmbeddingModel getDefaultEmbeddingModel() {
		return embeddingModelsRuntimeDao.defaultHandler();
	}

	protected List<IGConfigurableEmbeddingModel> getEmbeddingModelsListByKnowledgeBases(
			List<GKnowledgeBase> knowledgeBases) {
		return this.getEmbeddingModelsListByKnowledgeBases(knowledgeBases, true);
	}

	protected List<IGConfigurableEmbeddingModel> getEmbeddingModelsListByKnowledgeBases(
			List<GKnowledgeBase> knowledgeBases, boolean includeDefaultEmbeddingModel) {
		List<IGConfigurableEmbeddingModel> embeddingModels = new ArrayList<IGConfigurableEmbeddingModel>();
		IGConfigurableEmbeddingModel defaultEmbeddingModel = embeddingModelsRuntimeDao.defaultHandler();
		if (defaultEmbeddingModel != null && includeDefaultEmbeddingModel) {
			embeddingModels.add(defaultEmbeddingModel);
		}

		knowledgeBases.stream().map(x -> x.getEmbeddingModelReferences()).filter(y -> y != null && !y.isEmpty())
				.forEach(modelsList -> {
					modelsList.forEach(modelReference -> {
						IGConfigurableEmbeddingModel model = embeddingModelsRuntimeDao
								.findByModelReference(modelReference);
						if (model != null && model != defaultEmbeddingModel) {
							if (!embeddingModels.stream()
									.anyMatch(x -> (x == model || model.getCode().equals(x.getCode())))) {
								embeddingModels.add(model);
							}
						}
					});
				});
		return embeddingModels;
	}

	protected IGConfigurableChatModel getChatModel(GObjectRef<GBaseChatModelConfig> chatModelReference)
			throws LLMConfigException {

		IGConfigurableChatModel chatModel = null;
		if (chatModelReference != null) {
			chatModel = chatModelsConfigDao.findByModelReference(chatModelReference);
		}
		if (chatModel == null) {
			chatModel = chatModelsConfigDao.defaultHandler();
		}
		if (chatModel == null)
			throw new LLMConfigException("No ChatModel configured");
		return chatModel;
	}

	protected String callLLMWithDocuments(IGConfigurableChatModel chatModel, String prompt, Object documents,
			String question) {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
		String result = response.getResult().getOutput().getText();
		return result;
	}

	protected String callLLMWithDocumentsAndConsolidation(IGConfigurableChatModel chatModel, String prompt,
			Object documents, String question, String consolidated) {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		promptTemplate.add(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
		String result = response.getResult().getOutput().getText();
		return result;
	}

	protected <T> T callLLMWithConsolidationStructuredReturn(IGConfigurableChatModel chatModel, String prompt,
			String question, Object consolidated, Class<T> type) throws LLMConfigException {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);

		return chatModel.getChatClient().prompt(promptTemplate.create()).call().entity(type);
	}

	protected <T> T callLLMWithDocumentsAndConsolidationStructuredReturn(IGConfigurableChatModel chatModel,
			String prompt, Object documents, String question, Object consolidated, Class<T> type)
			throws LLMConfigException {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add(CONSOLIDATED_TEMPLATE_VARIABLE, consolidated);
		promptTemplate.add(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		return chatModel.getChatClient().prompt(promptTemplate.create()).call().entity(type);
	}

	protected <T> T callLLMWithDocumentsStructuredReturn(IGConfigurableChatModel chatModel, String prompt,
			Object documents, String question, Class<T> type) throws LLMConfigException {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);

		promptTemplate.add(DOCUMENTS_TEMPLATE_VARIABLE, documents);
		promptTemplate.add(USER_QUESTION_TEMPLATE_VARIABLE, question);
		return chatModel.getChatClient().prompt(promptTemplate.create()).call().entity(type);
	}
}
