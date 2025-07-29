package ai.gebo.security.model.oauth2;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Oauth2ClientConfig {
	@NotNull
	private String registrationId = null;
	@NotNull
	private String clientId = null;
	@NotNull
	private String issuer = null;
	@NotNull
	private String description = null;
	@NotNull
	private String tokenUri;

}
