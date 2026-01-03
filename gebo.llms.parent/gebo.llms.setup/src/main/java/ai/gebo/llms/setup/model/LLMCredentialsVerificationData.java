package ai.gebo.llms.setup.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMCredentialsVerificationData {
	@NotNull
	String vendorId = null;
	@NotNull
	String secretId = null;
	String baseUrl = null;
}
