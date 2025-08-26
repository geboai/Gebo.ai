package ai.gebo.fastsetup.llms.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.fastsetup.llms.config.LLMSModelsPresets;
import ai.gebo.fastsetup.llms.config.LLMSVendor;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import lombok.Data;

@Data
public class LLMSSetupConfigurationData {
	public static class LLMSSetupConfiguration
			extends RuntimeConfigurationContainer<LLMSModelsPresets, LLMSVendor, GBaseModelConfig> {
	}; 

	private List<LLMSSetupConfiguration> configurations = new ArrayList<LLMSSetupConfigurationData.LLMSSetupConfiguration>();
}
