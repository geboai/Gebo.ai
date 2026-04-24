package ai.gebo.llms.abstraction.layer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GRankerModelType;
import ai.gebo.ranker.model.RankerModel;
import ai.gebo.ranker.standard.client.GeboStandardRankerClient;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

public class GBaseConfigurableRankerModel<ModelConfig extends GBaseRankerModelConfig>
		implements IGConfigurableRankerModel<ModelConfig> {
	private final IGeboSecretsAccessService secretAccessService;
	private final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	private final String baseUrl;
	private final String relativeServiceUrl;
	private final String defaultModel;

	private GRankerModelType type;
	private ModelConfig config = null;
	private RankerModel model = null;

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public GBaseConfigurableRankerModel(IGeboSecretsAccessService secretAccessService,
			IGLlmsServiceClientsProviderFactory serviceClientsProviderFactor, String baseUrl, String relativeServiceUrl,
			String defaultModel) {
		this.secretAccessService = secretAccessService;
		this.baseUrl = baseUrl;
		this.relativeServiceUrl = relativeServiceUrl;
		this.defaultModel = defaultModel;
		this.serviceClientsProviderFactory = serviceClientsProviderFactor;
	}

	@Override
	public String getCode() {

		return config.getCode();
	}

	@Override
	public String getDescription() {

		return config.getDescription();
	}

	@Override
	public GRankerModelType getType() {

		return type;
	}

	@Override
	public void initialize(ModelConfig config, GRankerModelType type) throws LLMConfigException {
		this.config = config;
		this.type = type;
		this.reconfigure(config);
	}

	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		try {
			this.config = config;
			String apiKey = null;
			if (config.getApiSecretCode() != null) {
				AbstractGeboSecretContent secret;

				secret = this.secretAccessService.getSecretContentById(config.getApiSecretCode());

				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent token = (GeboTokenContent) secret;
					apiKey = token.getToken();
				}
			}
			String thisBaseUrl = this.baseUrl;
			if (thisBaseUrl == null)
				thisBaseUrl = config.getBaseUrl();
			String thisRelativeUrl = this.relativeServiceUrl;
			if (thisRelativeUrl == null)
				thisRelativeUrl = "/v1/ranker";
			String thisCompleteUrl = thisBaseUrl + thisRelativeUrl;
			this.model = new GeboStandardRankerClient(apiKey, thisCompleteUrl,
					config.getChoosedModel() != null ? config.getChoosedModel().getCode() : this.defaultModel);
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException("Exception configuring ranker module", e);
		}
	}

	@Override
	public ModelConfig getConfig() {
		return this.config;
	}

	@Override
	public void delete() throws LLMConfigException {
		this.model = null;
	}

	@Override
	public RankerModel getRankerModel() {

		return this.model;
	}

}
