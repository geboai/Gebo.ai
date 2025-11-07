package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LLMSModelsPresets {
	private boolean doModelsLookup = false;
	private ModelType type = null;
	private String serviceHandler = null;
	private String apiKeySecretContext=null;
	private List<LLMModelPresetChoice> choices = new ArrayList<LLMModelPresetChoice>();
}
