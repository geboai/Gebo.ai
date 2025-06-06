package ai.gebo.security.model.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AI generated comments Represents OAuth2 client authorization information with
 * registration ID and provider name.
 */
@AllArgsConstructor
@Getter
public class Oauth2ClientAuthorizativeInfo {
	// The unique identifier for the OAuth2 client's registration.
	private final String registrationId;

	// The name of the provider associated with the OAuth2 client.
	private final String providerName;
	// The description of the provider registration
	private final String description;
	

}