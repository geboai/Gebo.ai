package ai.gebo.security.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**********************************************************************
 * This data rappresents both runtime OAUTH2 provider basic config and
 * pre-defined standard configuration for worldwide standard Oauth2 providers.
 */
@Data
public class Oauth2ProviderConfig {
	/**
	 * The authentication provider.
	 */
	@NotNull
	private AuthProvider provider;
	/**
	 * The authorization URI.
	 */
	@NotNull
	private String authorizationUri;
	/**
	 * The token URI.
	 */
	@NotNull
	private String tokenUri;
	/**
	 * The user info URI.
	 */
	@NotNull
	private String userInfoUri;
	/**
	 * The user name attribute.
	 */
	@NotNull
	private String userNameAttribute;
}
