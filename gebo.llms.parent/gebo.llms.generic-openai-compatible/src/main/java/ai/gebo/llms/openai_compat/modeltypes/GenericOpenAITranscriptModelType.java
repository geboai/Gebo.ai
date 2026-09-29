package ai.gebo.llms.openai_compat.modeltypes;

import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITranscriptModelConfig;
import lombok.Data;
@Data
public class GenericOpenAITranscriptModelType extends GTranscriptModelType {

	/** Base URL for the OpenAI-compatible API endpoint */
	private String baseUrl = null;
	/** Provider for the list of available models */
	private String modelsListProvider = null;
	// providerId is inherited from GModelType and bound from providers.yml.
	/** Flag indicating whether authentication is optional */
	private boolean optionalAuthentication = false;

	public GenericOpenAITranscriptModelType() {
		setModelConfigurationClass(GenericOpenAIAPITranscriptModelConfig.class.getName());
	}
}
