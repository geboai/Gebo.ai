package ai.gebo.llms.openai_compat.modeltypes;

import ai.gebo.llms.abstraction.layer.model.GImageModelType;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelConfig;
import lombok.Data;

@Data
public class GenericOpenAIImageModelTypeConfig extends GImageModelType {

	/** Base URL for the OpenAI-compatible API endpoint */
	private String baseUrl = null;
	/** Provider for the list of available models */
	private String modelsListProvider = null;
	// providerId is inherited from GModelType and bound from providers.yml.
	/** Flag indicating whether authentication is optional */
	private boolean optionalAuthentication = false;

	public GenericOpenAIImageModelTypeConfig() {
		setModelConfigurationClass(GenericOpenAIAPIImageModelConfig.class.getName());
	}

}
