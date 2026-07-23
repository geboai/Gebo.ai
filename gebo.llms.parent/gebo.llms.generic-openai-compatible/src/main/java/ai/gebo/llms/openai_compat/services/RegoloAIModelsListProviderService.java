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
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelChice;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelChoice;
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
	// LiteLLM model_info.mode values (regolo.ai is served through a LiteLLM proxy).
	private static final String EMBEDDING = "embedding";
	private static final String RERANK = "rerank";
	private static final String IMAGE_GENERATION = "image_generation";
	private static final String AUDIO_SPEECH = "audio_speech";
	private static final String AUDIO_TRANSCRIPTION = "audio_transcription";
	private static final String MODERATION = "moderation";
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
			HttpEntity<String> request = new HttpEntity<String>(ModelsListCommonUtils.getHeaders(clearApiKey));
			ResponseEntity<RegoloAIModelList> response = restTemplateWrapper.exchange(REGOLO_AI_MODELS_INFO_URL,
					HttpMethod.GET, request, RegoloAIModelList.class);
			RegoloAIModelList result = response.hasBody() ? response.getBody() : new RegoloAIModelList();
			List<RegoloAIModel> data = result.getData() != null ? result.getData() : new ArrayList<RegoloAIModel>();

			if (GBaseEmbeddingModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseEmbeddingModelChoice> list = new ArrayList<GBaseEmbeddingModelChoice>();
				for (RegoloAIModel m : data) {
					if (isMode(m, EMBEDDING))
						list.add((GBaseEmbeddingModelChoice) toChoice(choiceType, m));
				}
				enricherService.enrichEmbeddingModelMetaInfos(providerId, list,
						(GBaseEmbeddingModelChoice x) -> new ModelMetaInfo());
				models = new ArrayList(list);
			} else if (GBaseRankerModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseRankerModelChoice> list = new ArrayList<GBaseRankerModelChoice>();
				for (RegoloAIModel m : data) {
					if (isMode(m, RERANK))
						list.add((GBaseRankerModelChoice) toChoice(choiceType, m));
				}
				models = new ArrayList(list);
			} else if (GBaseImageModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseImageModelChoice> list = new ArrayList<GBaseImageModelChoice>();
				for (RegoloAIModel m : data) {
					if (isMode(m, IMAGE_GENERATION))
						list.add((GBaseImageModelChoice) toChoice(choiceType, m));
				}
				models = new ArrayList(list);
			} else if (GBaseTranscriptModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseTranscriptModelChoice> list = new ArrayList<GBaseTranscriptModelChoice>();
				for (RegoloAIModel m : data) {
					if (isMode(m, AUDIO_TRANSCRIPTION))
						list.add((GBaseTranscriptModelChoice) toChoice(choiceType, m));
				}
				models = new ArrayList(list);
			} else if (GBaseTextToSpeachModelChice.class.isAssignableFrom(choiceType)) {
				List<GBaseTextToSpeachModelChice> list = new ArrayList<GBaseTextToSpeachModelChice>();
				for (RegoloAIModel m : data) {
					if (isMode(m, AUDIO_SPEECH))
						list.add((GBaseTextToSpeachModelChice) toChoice(choiceType, m));
				}
				models = new ArrayList(list);
			} else if (GBaseChatModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseChatModelChoice> list = new ArrayList<GBaseChatModelChoice>();
				for (RegoloAIModel m : data) {
					if (isChatMode(m))
						list.add((GBaseChatModelChoice) toChoice(choiceType, m));
				}
				enricherService.enrichChatModelMetaInfos(providerId, list,
						(GBaseChatModelChoice x) -> new ModelMetaInfo());
				models = new ArrayList(list);
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

	private boolean isMode(RegoloAIModel model, String targetMode) {
		String mode = getMode(model);
		return mode != null && mode.equalsIgnoreCase(targetMode);
	}

	/**
	 * A chat model is anything that is not one of the specialised modes. Models with
	 * no declared mode are kept as chat, preserving the previous lenient behaviour.
	 */
	private boolean isChatMode(RegoloAIModel model) {
		String mode = getMode(model);
		if (mode == null)
			return true;
		return !(mode.equalsIgnoreCase(EMBEDDING) || mode.equalsIgnoreCase(RERANK)
				|| mode.equalsIgnoreCase(IMAGE_GENERATION) || mode.equalsIgnoreCase(AUDIO_SPEECH)
				|| mode.equalsIgnoreCase(AUDIO_TRANSCRIPTION) || mode.equalsIgnoreCase(MODERATION));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private GBaseModelChoice toChoice(Class choiceType, RegoloAIModel model) {
		GBaseModelChoice entry = ModelsListCommonUtils.newInstance(choiceType);
		entry.setCode(model.getModel_name());
		entry.setDescription(model.getModel_name());
		entry.setContextLength(getContextWindowLength(model));
		entry.setNativeModelMetaInfos(model);
		return entry;
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
