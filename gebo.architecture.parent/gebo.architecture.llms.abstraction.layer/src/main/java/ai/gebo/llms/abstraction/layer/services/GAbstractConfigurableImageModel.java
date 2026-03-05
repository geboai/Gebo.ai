package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.image.ImageModel;

import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GImageModelType;

public abstract class GAbstractConfigurableImageModel<ModelConfig extends GBaseImageModelConfig, ModelObjectType extends ImageModel>
		implements IGConfigurableImageModel<ModelConfig> {
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
