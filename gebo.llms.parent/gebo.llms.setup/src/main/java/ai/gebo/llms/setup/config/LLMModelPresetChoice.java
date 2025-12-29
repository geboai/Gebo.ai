package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMModelPresetChoice {
	@NotNull
	String code = null;
	String description = null;
	boolean defaultChoice = false;
	List<ChatModelsUses> uses = null;
}
