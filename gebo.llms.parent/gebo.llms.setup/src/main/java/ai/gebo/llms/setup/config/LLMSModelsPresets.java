package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMSModelsPresets {
	private boolean doModelsLookup = false;
	@NotNull
	private ModelType type = null;
	@NotNull
	private String serviceHandler = null;
	private List<LLMModelPresetChoice> choices = new ArrayList<LLMModelPresetChoice>();
}
