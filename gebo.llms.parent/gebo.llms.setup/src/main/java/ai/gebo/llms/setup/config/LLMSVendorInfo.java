package ai.gebo.llms.setup.config;

import ai.gebo.secrets.model.GeboSecretType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class LLMSVendorInfo {
	@NotNull
	private String vendorId = null;
	private boolean requiresCustomUrl = false;
	private String defaultCustomUrl = null;
	private boolean requiresApiKey = false;
	private GeboSecretType secretType = GeboSecretType.TOKEN;
	@NotNull
	private String description = null;
	@NotNull
	private String name = null;
	@NotNull
	private String webSite = null;
	private String acquireKeyUrl = null;
	private String apiKeySecretContext=null;

}
