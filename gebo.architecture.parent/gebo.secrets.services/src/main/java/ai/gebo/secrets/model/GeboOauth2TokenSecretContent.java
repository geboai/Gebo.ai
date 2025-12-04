package ai.gebo.secrets.model;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class GeboOauth2TokenSecretContent extends AbstractGeboSecretContent {
	@NotNull
	private String principalName;
	@NotNull
	private String registrationId;
	
	private OAuth2AccessToken accessToken;
	private OAuth2RefreshToken refreshToken;

	@Override
	public GeboSecretType type() {

		return GeboSecretType.OAUTH2_AUTHORIZED_CLIENT;
	}

	// getters + setters + no-args constructor
}
