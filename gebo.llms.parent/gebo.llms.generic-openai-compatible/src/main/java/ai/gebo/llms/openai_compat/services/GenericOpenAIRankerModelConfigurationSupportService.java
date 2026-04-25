package ai.gebo.llms.openai_compat.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.services.GAbstractRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai_compat.model.GenericOpenAIRankerModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIRankerModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIRankerModelTypeConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

public class GenericOpenAIRankerModelConfigurationSupportService extends
		GAbstractRankerModelConfigurationSupportService<GenericOpenAIRankerModelChoice, GenericOpenAIRankerModelConfig, GenericOpenAIRankerModelTypeConfig> {
	final ModelsListProviderProxyService modelsListProxyService;
	final IGOpenAIApiUtil openaiApiUtil;

	public GenericOpenAIRankerModelConfigurationSupportService(GenericOpenAIRankerModelTypeConfig type,
			IGeboSecretsAccessService secretAccessService, ModelRuntimeConfigureHandler configureHandler,
			IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory,
			ModelsListProviderProxyService modelsListProxyService, IGOpenAIApiUtil openaiApiUtil) {
		super(secretAccessService, type, type.getDefaultModel(), configureHandler, serviceClientsProviderFactory,
				type.isOptionalAuthentication());
		this.modelsListProxyService = modelsListProxyService;
		this.openaiApiUtil = openaiApiUtil;
	}

	@Override
	public OperationStatus<List<GenericOpenAIRankerModelChoice>> getRawModelChoices(
			GenericOpenAIRankerModelConfig config) {
		OperationStatus<List<GenericOpenAIRankerModelChoice>> result = null;
		OpenAIApiConfig providerConfig = OpenAIApiConfig.of(config, false);
		providerConfig.setProviderId(type.getProviderId());
		if (providerConfig.getBasePath() == null)
			providerConfig.setBasePath(type.getBaseUrl());
		if (type.getModelsListProvider() != null && type.getModelsListProvider().trim().length() > 0) {
			result = this.modelsListProxyService.geModels(type.getModelsListProvider(), config,
					GenericOpenAIRankerModelChoice.class, type);
		} else
			result = this.openaiApiUtil.getRankerModels(GenericOpenAIRankerModelChoice.class, providerConfig, config,
					(choice) -> {
						return new ModelMetaInfo();
					}, type);

		return result;

	}

	@Override
	public String getId() {
		return type.getCode();
	}

	@Override
	public GenericOpenAIRankerModelConfig createBaseConfiguration(String presetModel) {
		
		return null;
	}

}
