package ai.gebo.llms.setup.model;

import ai.gebo.llms.setup.config.ModelType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMModelsLookupParameter {

	@NotNull
	private ModelType type = null;
	@NotNull
	private String serviceHandler = null;
	private String secretId = null;
	private String baseUrl = null;

}
