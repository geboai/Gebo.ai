package ai.gebo.llms.setup.config;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.security.model.AuthProvider;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMSVendorInfo {
	@NotNull
	private String vendorId = null;
	private boolean requiresCustomUrl = false;
	private String defaultCustomUrl = null;
	private boolean requiresApiKey = false;
	/**
	 * The kinds of secret the vendor's models authenticate with, the first being the one
	 * new credentials are created as: an api key (TOKEN) unless the vendor says
	 * otherwise, e.g. AWS_CONNECTION (GeboAwsConnectionCredentials: access key, secret
	 * key and region) for AWS Bedrock.
	 */
	private List<GeboSecretType> secretTypes = new ArrayList<>(List.of(GeboSecretType.TOKEN));
	private AuthProvider authProvider = null;
	@NotNull
	private String description = null;
	@NotNull
	private String name = null;
	@NotNull
	private String webSite = null;
	private String acquireKeyUrl = null;
	@NotNull
	private String apiKeySecretContext = null;
	private Integer minContextWindow = null;
	private Boolean supportsAutoconfig = null;

}
