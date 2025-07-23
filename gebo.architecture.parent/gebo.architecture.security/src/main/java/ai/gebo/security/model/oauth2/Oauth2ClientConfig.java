package ai.gebo.security.model.oauth2;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Oauth2ClientConfig {
	@NotNull
	String registrationId = null;
	@NotNull
	String clientId = null;
	@NotNull
	String issuer = null;
	@NotNull
	String description = null;

}
