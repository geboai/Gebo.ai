package ai.gebo.llms.setup.model;

import lombok.Data;

@Data
public class LLMSSetupModificationResult {
	private LLMSSetupConfigurationData modificationResult = null;
	private LLMSSetupConfigurationModificationData modification = null;
}
