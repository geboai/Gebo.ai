package ai.gebo.fastsetup.llms.model;

import java.util.ArrayList;

import ai.gebo.fastsetup.llms.config.LLMSModelsPresets;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import lombok.Data;

@Data
public class LLMSSetupConfigurationData {
	private RuntimeConfigurationContainer<LLMSModelsPresets, GBaseModelConfig> configurations = new RuntimeConfigurationContainer<LLMSModelsPresets, GBaseModelConfig>();
}
