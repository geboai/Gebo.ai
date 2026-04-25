package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GRankerModelType;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GAbstractRankerModelConfigurationSupportService<ModelChoice extends GBaseRankerModelChoice, ModelConfig extends GBaseRankerModelConfig<ModelChoice>, RankerModelType extends GRankerModelType>
		implements IGRankerModelConfigurationSupportService<ModelChoice, ModelConfig> {
	protected final IGeboSecretsAccessService secretAccessService;
	protected final RankerModelType type;
	protected final String defaultModel;
	protected final ModelRuntimeConfigureHandler configureHandler;
	protected final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	protected final boolean optionalAuthentication;

	@Override
	public RankerModelType getType() {

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
				secretAccessService, serviceClientsProviderFactory, defaultModel, optionalAuthentication);
		model.initialize(config, type);
		return model;
	}
}
