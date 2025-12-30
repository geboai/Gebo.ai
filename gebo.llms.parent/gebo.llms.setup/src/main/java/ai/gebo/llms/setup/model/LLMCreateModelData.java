package ai.gebo.llms.setup.model;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.setup.config.ModelType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMCreateModelData {

	@NotNull
	private ModelType type = null;
	private Boolean doModelsLookup = false;
	@NotNull
	private String serviceHandler = null;
	private Boolean setAsDefaultModel = null;
	private Boolean enableAllFunctions = null;
	private String secretId = null;
	@NotNull
	private String modelCode = null;
	private String baseUrl = null;
	private Integer contextWindow = null;
	private List<ChatModelsUses> uses = null;

}
