package ai.gebo.fastsetup.llms.model;

import lombok.Data;

@Data
public class LLMSSetupModificationResult {
	private LLMSSetupConfigurationData modificationResult = null;
	private LLMSSetupConfigurationModificationData modification = null;
}
