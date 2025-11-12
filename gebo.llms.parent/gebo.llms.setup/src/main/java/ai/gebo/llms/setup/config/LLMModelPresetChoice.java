package ai.gebo.llms.setup.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMModelPresetChoice {
	@NotNull
	String code = null;
	String description = null;
	boolean defaultChoice=false;
}
