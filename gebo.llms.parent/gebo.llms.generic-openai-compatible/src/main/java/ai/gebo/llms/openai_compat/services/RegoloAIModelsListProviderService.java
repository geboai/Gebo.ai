package ai.gebo.llms.openai_compat.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.abstraction.layer.services.IGModelsListProvider;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai_compat.services.XaiEmbeddingModelsListProviderService.XaiEmbeddingModelData;
import ai.gebo.llms.openai_compat.services.XaiEmbeddingModelsListProviderService.XaiEmbeddingModelDataList;
import ai.gebo.llms.openai_compat.services.XaiEmbeddingModelsListProviderService.XaiLlmsData;
import ai.gebo.llms.openai_compat.services.XaiEmbeddingModelsListProviderService.XaiLlmsDataList;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@AllArgsConstructor
public class RegoloAIModelsListProviderService implements IGModelsListProvider {
	private static final String REGOLO_AI_MODELS_LIST = "regolo-ai-models-list";
	private static final String EMBEDDING = "embedding";
	private static final String REGOLO_AI_MODELS_INFO_URL = "https://api.regolo.ai/v1/model/info";
	final IGModelChoiceMetaInfoEnricherService enricherService;
	final RestTemplateWrapperService restTemplateWrapper;

	@Override
	public String getId() {

		return REGOLO_AI_MODELS_LIST;
	}

	@Data
	public static class RegoloAIModel {
		private String model_name = null;
		private LinkedHashMap<String, Object> model_info = new LinkedHashMap<String, Object>();
	}

	@Data
	public static class RegoloAIModelList {
		private List<RegoloAIModel> data = new ArrayList<RegoloAIModel>();
	}

	@Override
	public <ModelChoice extends GBaseModelChoice, ModelConfig extends GBaseModelConfig<ModelChoice>, ModelType extends GModelType> OperationStatus<List<ModelChoice>> geModels(
			String providerId, ModelConfig config, String clearApiKey, Class<ModelChoice> choiceType,
			ModelType modelType) {
		List<ModelChoice> models = new ArrayList<ModelChoice>();
		try {
			if (GBaseEmbeddingModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseEmbeddingModelChoice> embeddingmodels = new ArrayList<GBaseEmbeddingModelChoice>();

				HttpEntity<String> request = new HttpEntity<String>(ModelsListCommonUtils.getHeaders(clearApiKey));
				ResponseEntity<RegoloAIModelList> response = restTemplateWrapper.exchange(REGOLO_AI_MODELS_INFO_URL,
						HttpMethod.GET, request, RegoloAIModelList.class);
				RegoloAIModelList result = response.hasBody() ? response.getBody() : new RegoloAIModelList();
				if (result.getData() != null) {
					for (RegoloAIModel m : result.getData()) {
						GBaseEmbeddingModelChoice entry = (GBaseEmbeddingModelChoice) ModelsListCommonUtils
								.newInstance(choiceType);
						entry.setCode(m.getModel_name());
						entry.setContextLength(getContextWindowLength(m));
						entry.setDescription(m.getModel_name());
						entry.setNativeModelMetaInfos(m);
						String mode = getMode(m);
						if (mode != null && mode.equalsIgnoreCase(EMBEDDING)) {
							embeddingmodels.add(entry);
						}
					}
				}
				enricherService.enrichEmbeddingModelMetaInfos(providerId, embeddingmodels,
						(GBaseEmbeddingModelChoice x) -> {
							return new ModelMetaInfo();
						});
				models = new ArrayList(embeddingmodels);
			} else if (GBaseChatModelChoice.class.isAssignableFrom(choiceType)) {

				HttpEntity<String> request = new HttpEntity<String>(ModelsListCommonUtils.getHeaders(clearApiKey));
				ResponseEntity<RegoloAIModelList> response = restTemplateWrapper.exchange(REGOLO_AI_MODELS_INFO_URL,
						HttpMethod.GET, request, RegoloAIModelList.class);
				RegoloAIModelList result = response.hasBody() ? response.getBody() : new RegoloAIModelList();
				if (result.getData() != null) {
					List<GBaseChatModelChoice> chatmodels = new ArrayList<GBaseChatModelChoice>();
					for (RegoloAIModel m : result.getData()) {
						GBaseChatModelChoice entry = (GBaseChatModelChoice) ModelsListCommonUtils
								.newInstance(choiceType);
						entry.setCode(m.getModel_name());
						entry.setDescription(m.getModel_name());
						entry.setContextLength(getContextWindowLength(m));
						entry.setNativeModelMetaInfos(m);
						String mode = getMode(m);
						if (mode == null || !mode.equalsIgnoreCase(EMBEDDING)) {
							chatmodels.add(entry);
						}
					}
					enricherService.enrichChatModelMetaInfos(providerId, chatmodels, (GBaseChatModelChoice x) -> {
						return new ModelMetaInfo();
					});
					models = new ArrayList(chatmodels);
				}
			} else if (GBaseRankerModelChoice.class.isAssignableFrom(choiceType)) {
				HttpEntity<String> request = new HttpEntity<String>(ModelsListCommonUtils.getHeaders(clearApiKey));
				ResponseEntity<RegoloAIModelList> response = restTemplateWrapper.exchange(REGOLO_AI_MODELS_INFO_URL,
						HttpMethod.GET, request, RegoloAIModelList.class);
				RegoloAIModelList result = response.hasBody() ? response.getBody() : new RegoloAIModelList();
				if (result.getData() != null) {
					List<GBaseRankerModelChoice> rankermodels = new ArrayList<GBaseRankerModelChoice>();
					for (RegoloAIModel m : result.getData()) {
						GBaseRankerModelChoice entry = (GBaseRankerModelChoice) ModelsListCommonUtils
								.newInstance(choiceType);
						entry.setCode(m.getModel_name());
						entry.setDescription(m.getModel_name());
						entry.setContextLength(getContextWindowLength(m));
						entry.setNativeModelMetaInfos(m);
						String mode = getMode(m);
						if (mode == null || !mode.equalsIgnoreCase(EMBEDDING)) {
							rankermodels.add(entry);
						}
					}

					models = new ArrayList(rankermodels);
				}
			} else
				throw new RuntimeException("This service does not handle=>" + choiceType.getName());
		} catch (GeboRestIntegrationException e) {
			GUserMessage message = this.restTemplateWrapper.toMessage(e, " Regolo.ai provider ", " models list ");
			OperationStatus<List<ModelChoice>> outValue = new OperationStatus<List<ModelChoice>>();
			outValue.getMessages().add(message);
			return outValue;
		} finally {
		}
		return OperationStatus.of(models);

	}

	private Integer getContextWindowLength(RegoloAIModel model) {
		Number max_tokens = (Number) (model.getModel_info() != null ? model.getModel_info().get("max_tokens") : null);
		Number max_input_tokens = (Number) (model.getModel_info() != null
				? model.getModel_info().get("max_input_tokens")
				: null);
		if (max_input_tokens != null)
			return new Integer(max_input_tokens.intValue());
		if (max_tokens != null)
			return new Integer(max_tokens.intValue());
		return null;
	}

	private String getMode(RegoloAIModel model) {
		String mode = (String) (model.getModel_info() != null ? model.getModel_info().get("mode") : null);
		return mode;
	}
}
