package ai.gebo.llms.abstraction.layer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient.Builder;

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

public abstract class GAbstractConfigurableRankerModel<ModelConfig extends GBaseRankerModelConfig>
		implements IGConfigurableRankerModel<ModelConfig> {
	private final IGeboSecretsAccessService secretAccessService;
	private final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	private final String defaultModel;
	private final boolean optionalAuthentication;
	private GRankerModelType type;
	private ModelConfig config = null;
	private RankerModel model = null;

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public GAbstractConfigurableRankerModel(IGeboSecretsAccessService secretAccessService,
			IGLlmsServiceClientsProviderFactory serviceClientsProviderFactor, String defaultModel,
			boolean optionalAuthentication) {
		this.secretAccessService = secretAccessService;
		this.defaultModel = defaultModel;
		this.serviceClientsProviderFactory = serviceClientsProviderFactor;
		this.optionalAuthentication = optionalAuthentication;
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
			if (!optionalAuthentication) {
				if (apiKey == null)
					throw new LLMConfigException("ApiKey is required in this ranker client");
			}

			String thisCompleteUrl = config.generateEndpointUrl();
			if (thisCompleteUrl == null)
				thisCompleteUrl = getEndpointCompleteUrl();
			String model = config.getChoosedModel() != null ? config.getChoosedModel().getCode() : this.defaultModel;
			IGLlmsServiceClientsProvider serviceClientProvider = this.serviceClientsProviderFactory
					.get(config.getModelTypeCode());
			Builder restClientBuilder = serviceClientProvider.getRestClientBuilder();
			RetryTemplate retryTemplate = serviceClientProvider.getRetryTemplate();
			org.springframework.web.reactive.function.client.WebClient.Builder webClientBuilder = serviceClientProvider
					.getWebClientBuilder();
			this.model = new GeboStandardRankerClient(apiKey, thisCompleteUrl, model, restClientBuilder, retryTemplate,
					config.getContextLength(), config.getMaxDocumentsPerRequest(), config.getMaxDocumentTokens(),
					config.getResponseReserveTokens());
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException("Exception configuring ranker module", e);
		}
	}

	protected abstract String getEndpointCompleteUrl();

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
