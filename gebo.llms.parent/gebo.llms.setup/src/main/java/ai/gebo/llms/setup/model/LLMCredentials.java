package ai.gebo.llms.setup.model;

import ai.gebo.llms.setup.config.ModelType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMCredentials {

	@NotNull
	private ModelType type = null;
	@NotNull
	private String serviceHandler = null;
	@NotNull
	private String secretId = null;

	private String baseUrl = null;

}
