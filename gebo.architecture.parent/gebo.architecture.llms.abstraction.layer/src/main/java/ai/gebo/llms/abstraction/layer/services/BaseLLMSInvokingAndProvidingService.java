package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.base.GObjectRef;
import lombok.AllArgsConstructor;

/*******************************************************************************************************************
 * General base class for services using large language models
 */
@AllArgsConstructor
public class BaseLLMSInvokingAndProvidingService extends BaseLLMSInvokingService {
	protected final IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	protected final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao;
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

}
