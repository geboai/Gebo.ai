package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import org.springframework.ai.image.ImageModel;

import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GImageModelType;

public abstract class GAbstractConfigurableImageModel<ModelConfig extends GBaseImageModelConfig, ModelObjectType extends ImageModel>
		implements IGConfigurableImageModel<ModelConfig>, IGProviderDealPricedModel {

	/**
	 * Prices this model by its provider deal; attached by the runtime DAO, null
	 * leaving it priced by its configuration.
	 */
	private volatile IGProviderDealService providerDealService = null;

	@Override
	public void setProviderDealService(IGProviderDealService providerDealService) {
		this.providerDealService = providerDealService;
	}

	/**
	 * The price the provider deal covering this model's API key gives to its
	 * configuration, else the configured one; read from an in-memory snapshot.
	 */
	@Override
	public GModelPricingConditions getPricingConditions() {
		return IGProviderDealPricedModel.dealOrConfiguredPricing(providerDealService, this);
	}

	// Configuration specific to the text-to-speech model.
	protected ModelConfig config = null;

	// The instantiated text-to-speech model object.
	protected ModelObjectType model = null;

	// The type of text-to-speech model being used.
	protected GImageModelType type = null;

	protected ImageModel imageModel = null;

	@Override
	public String getCode() {

		return config != null ? config.getCode() : null;
	}

	@Override
	public String getDescription() {

		return config != null ? config.getDescription() : null;
	}

	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		this.initialize(config, type);

	}

	@Override
	public ModelConfig getConfig() {

		return config;
	}

	@Override
	public void delete() throws LLMConfigException {

	}

	@Override
	public ImageModel getImageModel() {

		return imageModel;
	}

	@Override
	public GImageModelType getType() {

		return type;
	}

	@Override
	public void initialize(ModelConfig config, GImageModelType type) throws LLMConfigException {
		this.config = config;
		this.type = type;
		this.imageModel = configureModel(config, type);
	}

	protected abstract ModelObjectType configureModel(ModelConfig config, GImageModelType type)
			throws LLMConfigException;
}
