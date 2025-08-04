package ai.gebo.security.services;

import java.util.List;

import org.springframework.security.oauth2.client.registration.ClientRegistration;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2AuthorizationGrantType;
import ai.gebo.security.model.oauth2.Oauth2ClientAuthMethod;
import ai.gebo.security.model.oauth2.Oauth2ClientAuthorizativeInfo;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Service interface for managing OAuth2 configurations.
 * 
 * Provides methods to store, delete, and find OAuth2 configurations based on
 * specific parameters such as provider, configuration type, and client
 * registration id.
 * 
 * AI generated comments
 */
public interface IGOauth2ConfigurationService {

	/**
	 * Store OAuth2 configuration with default authentication method and grant type.
	 *
	 * @param authProvider        the authentication provider
	 * @param oauth2ClientContent the OAuth2 client content
	 * @param configurationTypes  the list of configuration types
	 * @param description         TODO
	 * @return the ID of the stored configuration
	 * @throws GeboOauth2Exception if storage fails
	 */
	public default String insertOauth2Configuration(@NotNull AuthProvider authProvider,
			@NotNull @Valid GeboOauth2SecretContent oauth2ClientContent,
			@NotNull @NotEmpty List<Oauth2ConfigurationType> configurationTypes, String description)
			throws GeboOauth2Exception {
		return insertOauth2Configuration(authProvider, oauth2ClientContent, null, null, configurationTypes,
				description);
	}

	/**
	 * Store OAuth2 configuration with default authentication method and grant type,
	 * using a specific provider configuration.
	 *
	 * @param providerConfiguration the provider configuration
	 * @param oauth2ClientContent   the OAuth2 client content
	 * @param configurationTypes    the list of configuration types
	 * @param description           TODO
	 * @return the ID of the stored configuration
	 * @throws GeboOauth2Exception if storage fails
	 */
	public default String insertOauth2Configuration(@NotNull @Valid Oauth2ProviderConfig providerConfiguration,
			@NotNull @Valid GeboOauth2SecretContent oauth2ClientContent,
			@NotNull @NotEmpty List<Oauth2ConfigurationType> configurationTypes, String description)
			throws GeboOauth2Exception {
		return insertOauth2Configuration(providerConfiguration, oauth2ClientContent, null, null, configurationTypes,
				description);
	}

	/**
	 * Store OAuth2 configuration with specified parameters.
	 * 
	 * @param authProvider        the authentication provider
	 * @param oauth2ClientContent the OAuth2 client content
	 * @param clientAuthMethod    the client authentication method
	 * @param authGrantType       the authorization grant type
	 * @param configurationTypes  the list of configuration types
	 * @param description         TODO
	 * @return the ID of the stored configuration
	 * @throws GeboOauth2Exception if storage fails
	 */
	public String insertOauth2Configuration(@NotNull AuthProvider authProvider,
			@NotNull @Valid GeboOauth2SecretContent oauth2ClientContent, Oauth2ClientAuthMethod clientAuthMethod,
			Oauth2AuthorizationGrantType authGrantType,
			@NotNull @NotEmpty List<Oauth2ConfigurationType> configurationTypes, String description)
			throws GeboOauth2Exception;

	/**
	 * Store OAuth2 configuration with specified parameters, using a specific
	 * provider configuration.
	 * 
	 * @param providerConfiguration the provider configuration
	 * @param oauth2ClientContent   the OAuth2 client content
	 * @param authClientMethod      the client authentication method
	 * @param authGrantType         the authorization grant type
	 * @param configurationTypes    the list of configuration types
	 * @param description           TODO
	 * @return the ID of the stored configuration
	 * @throws GeboOauth2Exception if storage fails
	 */
	public String insertOauth2Configuration(@Nullable @Valid Oauth2ProviderConfig providerConfiguration,
			@NotNull @Valid GeboOauth2SecretContent oauth2ClientContent, Oauth2ClientAuthMethod authClientMethod,
			Oauth2AuthorizationGrantType authGrantType,
			@NotNull @NotEmpty List<Oauth2ConfigurationType> configurationTypes, String description)
			throws GeboOauth2Exception;

	/**
	 * Delete the OAuth2 configuration by its ID.
	 * 
	 * @param id the ID of the configuration to be deleted
	 * @throws GeboOauth2Exception if the deletion fails
	 */
	public void deleteOauth2Configuration(String id) throws GeboOauth2Exception;

	/**
	 * Find an OAuth2 client registration by its registration ID.
	 * 
	 * @param id the registration ID
	 * @return the OAuth2 client registration
	 * @throws GeboOauth2Exception if retrieval fails
	 */
	public Oauth2ClientRegistration findOauth2ClientRegistrationByRegistrationId(String id) throws GeboOauth2Exception;

	/**
	 * Find a client registration by its registration ID.
	 * 
	 * @param id the registration ID
	 * @return the client registration
	 * @throws GeboOauth2Exception if retrieval fails
	 */
	public ClientRegistration findClientRegistrationByRegistrationId(String id) throws GeboOauth2Exception;

	/**
	 * Find OAuth2 client registrations by provider.
	 * 
	 * @param provider the authentication provider
	 * @return the list of OAuth2 client registrations
	 * @throws GeboOauth2Exception if retrieval fails
	 */
	public List<Oauth2ClientRegistration> findOauth2ClientRegistrationByProvider(AuthProvider provider)
			throws GeboOauth2Exception;

	/**
	 * Find OAuth2 client registrations by provider and configuration type.
	 * 
	 * @param provider the authentication provider
	 * @param type     the configuration type
	 * @return the list of OAuth2 client registrations
	 * @throws GeboOauth2Exception if retrieval fails
	 */
	public List<Oauth2ClientRegistration> findOauth2ClientRegistrationByProviderAndConfigurationType(
			AuthProvider provider, Oauth2ConfigurationType type) throws GeboOauth2Exception;

	public List<Oauth2ClientAuthorizativeInfo> findAllAauthorizativeRegistrations();

	public void updateOauth2Configuration(String registrationId, Oauth2ProviderConfig providerConfiguration,
			@NotNull @Valid GeboOauth2SecretContent oauth2ClientContent, Oauth2ClientAuthMethod authClientMethod,
			Oauth2AuthorizationGrantType authGrantType, List<Oauth2ConfigurationType> configurationTypes,
			String description) throws GeboOauth2Exception;

}