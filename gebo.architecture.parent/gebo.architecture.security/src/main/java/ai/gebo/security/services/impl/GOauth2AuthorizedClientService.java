/**
 * AI generated comments
 * 
 * Service implementation for managing OAuth2 authorized clients and their tokens.
 * It interacts with a secrets management service to store, retrieve, and remove token data.
 */
package ai.gebo.security.services.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2TokenSecretContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.secrets.services.IGeboSecretsAccessService.SecretInfo;

/**
 * Implements the OAuth2AuthorizedClientService to manage storing and retrieving
 * OAuth2 tokens using a secret management service.
 */
public class GOauth2AuthorizedClientService implements OAuth2AuthorizedClientService {

	private final ClientRegistrationRepository clientRegistrationRepository;
	private final IGeboSecretsAccessService secrets;

	/**
	 * Constructs the service with the required client registration repository and
	 * secrets access service.
	 * 
	 * @param clientRegistrationRepository the repository for client registrations
	 * @param secrets                      the secret access service to store and
	 *                                     retrieve token information
	 */
	public GOauth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository,
			IGeboSecretsAccessService secrets) {
		this.clientRegistrationRepository = clientRegistrationRepository;
		this.secrets = secrets;
	}

	/**
	 * Builds a unique secret ID for storing OAuth2 tokens based on principal and
	 * registration ID.
	 * 
	 * @param principal      the principal (user) name
	 * @param registrationId the client registration ID
	 * @return a unique secret ID
	 */
	private String buildSecretId(String principal, String registrationId) {
		return "oauth2-client-token-" + registrationId + "-" + principal;
	}

	/**
	 * Saves an OAuth2 client by storing its associated tokens in the secret
	 * management service.
	 * 
	 * @param client    the authorized OAuth2 client containing the tokens
	 * @param principal the authentication object representing the current principal
	 *                  (user)
	 */
	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient client, Authentication principal) {
		GeboOauth2TokenSecretContent content = new GeboOauth2TokenSecretContent();
		// Store principal name and client registration details
		content.setPrincipalName(principal.getName());
		content.setRegistrationId(client.getClientRegistration().getRegistrationId());
		content.setAccessToken(client.getAccessToken());
		content.setRefreshToken(client.getRefreshToken());

		String secretId = buildSecretId(principal.getName(), client.getClientRegistration().getRegistrationId());
		try {
			secrets.storeSecret(content, "OAuth2 token", secretId);
		} catch (GeboCryptSecretException e) {
			throw new RuntimeException("Cannot store secret token", e);
		}
	}

	/**
	 * Loads an authorized OAuth2 client using the stored secret information.
	 * 
	 * @param clientRegistrationId the client registration ID
	 * @param principalName        the principal (user) name
	 * @return the loaded OAuth2AuthorizedClient
	 */
	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
			String principalName) {
		String secretId = buildSecretId(principalName, clientRegistrationId);
		try {
			AbstractGeboSecretContent content = secrets.getSecretContentById(secretId);
			if (content instanceof GeboOauth2TokenSecretContent tokenContent) {
				// Retrieve client registration and create OAuth2AuthorizedClient from secret
				// content
				ClientRegistration registration = clientRegistrationRepository
						.findByRegistrationId(clientRegistrationId);
				return (T) new OAuth2AuthorizedClient(registration, principalName, tokenContent.getAccessToken(),
						tokenContent.getRefreshToken());
			} else
				throw new RuntimeException(
						"The secret context:" + secretId + " is not a valid GeboOauth2TokenSecretContent");
		} catch (GeboCryptSecretException e) {
			throw new RuntimeException("Token load failure from secrets layer", e);
		}
	}

	/**
	 * Removes an authorized OAuth2 client by deleting its associated tokens from
	 * the secret management service.
	 * 
	 * @param registrationId the client registration ID
	 * @param principalName  the principal (user) name
	 */
	@Override
	public void removeAuthorizedClient(String registrationId, String principalName) {
		String secretId = buildSecretId(principalName, registrationId);
		try {
			List<SecretInfo> secretsList = secrets.getSecretInfoByContextCode(secretId);
			for (SecretInfo secretInfo : secretsList) {
				// Delete each secret associated with this OAuth2 client
				secrets.deleteSecret(secretInfo.getCode());
			}
		} catch (GeboCryptSecretException e) {
			throw new RuntimeException("Token deletion failure from secrets layer", e);
		}
	}
}