package ai.gebo.llms.openai_compat.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.GAbstractRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIRankerModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIRankerModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIRankerModelTypeConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

public class GenericOpenAIAPIRankerModelConfigurationSupportService extends
		GAbstractRankerModelConfigurationSupportService<GenericOpenAIAPIRankerModelChoice, GenericOpenAIAPIRankerModelConfig, GenericOpenAIRankerModelTypeConfig> {
	final ModelsListProviderProxyService modelsListProxyService;
	final IGOpenAIApiUtil openaiApiUtil;

	public GenericOpenAIAPIRankerModelConfigurationSupportService(GenericOpenAIRankerModelTypeConfig type,
			IGeboSecretsAccessService secretAccessService, ModelRuntimeConfigureHandler configureHandler,
			IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory,
			ModelsListProviderProxyService modelsListProxyService, IGOpenAIApiUtil openaiApiUtil) {
		super(secretAccessService, type, type.getDefaultModel(), configureHandler, serviceClientsProviderFactory,
				type.isOptionalAuthentication());
		this.modelsListProxyService = modelsListProxyService;
		this.openaiApiUtil = openaiApiUtil;
	}

	@Override
	public OperationStatus<List<GenericOpenAIAPIRankerModelChoice>> getRawModelChoices(
			GenericOpenAIAPIRankerModelConfig config) {
		OperationStatus<List<GenericOpenAIAPIRankerModelChoice>> result = null;
		OpenAIApiConfig providerConfig = OpenAIApiConfig.of(config, false);
		providerConfig.setProviderId(type.getProviderId());
		if (providerConfig.getBasePath() == null)
			providerConfig.setBasePath(type.getBaseUrl());
		if (type.getModelsListProvider() != null && type.getModelsListProvider().trim().length() > 0) {
			result = this.modelsListProxyService.geModels(type.getModelsListProvider(), config,
					GenericOpenAIAPIRankerModelChoice.class, type);
		} else
			result = this.openaiApiUtil.getRankerModels(GenericOpenAIAPIRankerModelChoice.class, providerConfig, config,
					(choice) -> {
						return new ModelMetaInfo();
					}, type);

		return result;

	}

	class GenericOpenAIConfigurableRankerModel
			extends GAbstractConfigurableRankerModel<GenericOpenAIAPIRankerModelConfig> {

		public GenericOpenAIConfigurableRankerModel(IGeboSecretsAccessService secretAccessService,
				IGLlmsServiceClientsProviderFactory serviceClientsProviderFactor, String defaultModel,
				boolean optionalAuthentication) {
			super(secretAccessService, serviceClientsProviderFactor, defaultModel, optionalAuthentication);
		}

		@Override
		protected String getEndpointCompleteUrl() {
			String baseUrl = type.getBaseUrl();
			String partialUrl = IGRankerModelConfigurationSupportService.STANDARD_RERANK_RELATIVE_URL;
			if (baseUrl == null)
				return null;
			if (baseUrl.endsWith("/")) {
				return baseUrl.substring(0, baseUrl.length() - 1) + partialUrl;
			} else {
				return baseUrl + partialUrl;
			}

		}

	}

	@Override
	public String getId() {
		return type.getCode();
	}

	@Override
	public GenericOpenAIAPIRankerModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAIAPIRankerModelConfig clean = new GenericOpenAIAPIRankerModelConfig();
		clean.setChoosedModel(new GenericOpenAIAPIRankerModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription(getType().getProviderId() + " ranker generation model " + presetModel);
		clean.setDescription(getType().getProviderId() + " ranker generation model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;

	}

	@Override
	public IGConfigurableRankerModel create(GenericOpenAIAPIRankerModelConfig config) throws LLMConfigException {

		GenericOpenAIConfigurableRankerModel model= new GenericOpenAIConfigurableRankerModel(secretAccessService, serviceClientsProviderFactory,
				defaultModel, optionalAuthentication);
		model.initialize(config, type);
		return model;
	}

}
