package ai.gebo.llms.openai_compat.modeltypes;

import ai.gebo.llms.abstraction.layer.model.GTextToSpeechModelType;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITextToSpeechModelConfig;
import lombok.Data;
@Data
public class GenericOpenAITextToSpeechModelType extends GTextToSpeechModelType {

	/** Base URL for the OpenAI-compatible API endpoint */
	private String baseUrl = null;
	/** Provider for the list of available models */
	private String modelsListProvider = null;
	/** Identifier for the service provider */
	private String providerId = null;
	/** Flag indicating whether authentication is optional */
	private boolean optionalAuthentication = false;

	public GenericOpenAITextToSpeechModelType() {
		setModelConfigurationClass(GenericOpenAIAPITextToSpeechModelConfig.class.getName());
	}

}
