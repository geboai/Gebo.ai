package ai.gebo.llms.setup.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.setup.model.RuntimeConfigurationContainer;
import ai.gebo.llms.setup.config.LLMSModelsPresets;
import ai.gebo.llms.setup.config.LLMSVendorInfo;
import lombok.Data;

@Data
public class LLMSSetupConfigurationData {
	public static class LLMSSetupConfiguration extends
			RuntimeConfigurationContainer<List<LLMSModelsPresets>, LLMSVendorInfo, LLMExistingConfiguration> {
	};

	private List<LLMSSetupConfiguration> configurations = new ArrayList<LLMSSetupConfigurationData.LLMSSetupConfiguration>();
}
