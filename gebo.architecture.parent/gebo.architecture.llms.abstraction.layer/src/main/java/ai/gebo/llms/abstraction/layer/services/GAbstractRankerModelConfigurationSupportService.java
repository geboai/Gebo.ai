package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GRankerModelType;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GAbstractRankerModelConfigurationSupportService<ModelChoice extends GBaseRankerModelChoice, ModelConfig extends GBaseRankerModelConfig<ModelChoice>>
		implements IGRankerModelConfigurationSupportService<ModelChoice, ModelConfig> {
	private final IGeboSecretsAccessService secretAccessService;
	private final GRankerModelType type;
	private final String baseUrl;
	private final String relativeServiceUrl;
	private final String defaultModel;
	private final ModelRuntimeConfigureHandler configureHandler;
	private final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	@Override
	public GRankerModelType getType() {

		return type;
	}

	@Override
	public OperationStatus<ModelConfig> insertAndConfigure(ModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}

	@Override
	public IGConfigurableRankerModel<ModelConfig> create(ModelConfig config) throws LLMConfigException {
		IGConfigurableRankerModel<ModelConfig> model = new GBaseConfigurableRankerModel<ModelConfig>(
				secretAccessService, serviceClientsProviderFactory, baseUrl, relativeServiceUrl, defaultModel);
		model.initialize(config, type);
		return model;
	}
}
