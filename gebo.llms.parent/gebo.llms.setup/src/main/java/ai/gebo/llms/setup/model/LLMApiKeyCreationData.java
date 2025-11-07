package ai.gebo.llms.setup.model;

import ai.gebo.llms.setup.config.ModelType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMApiKeyCreationData {
	@NotNull
	private ModelType type = null;
	@NotNull
	private String serviceHandler = null;
	@NotNull
	private String apiKeySecretContext = null;
	@NotNull
	private String newApiSecret = null;
	@NotNull
	private String newUserName = null;
	private String baseUrl = null;

}
