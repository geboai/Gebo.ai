package ai.gebo.llms.setup.model;

import ai.gebo.llms.setup.config.ModelType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMCredentialsCreationData {
	@NotNull
	private ModelType type = null;
	private Boolean doModelsLookup = false;
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
