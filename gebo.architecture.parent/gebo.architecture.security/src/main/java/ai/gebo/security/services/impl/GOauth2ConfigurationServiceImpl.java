/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2AuthorizationGrantType;
import ai.gebo.security.model.oauth2.Oauth2ClientAuthMethod;
import ai.gebo.security.model.oauth2.Oauth2ClientAuthorizativeInfo;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2ProvidersLibraryDao;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class GOauth2ConfigurationServiceImpl implements IGOauth2ConfigurationService {
	// AI generated comments
	// Autowired services for handling secrets and configurations
	@Autowired
	IGeboSecretsAccessService secretService;
	@Autowired
	IGOauth2RuntimeConfigurationDao repository;

	@Autowired
	IGOauth2ProvidersLibraryDao providersLibraryDao;

	/**
	 * Default constructor
	 */
	public GOauth2ConfigurationServiceImpl() {

	}

	/**
	 * Stores a new OAuth2 configuration in the system using provider configuration.
	 *
	 * @param providerConfiguration The provider configuration.
	 * @param oauth2ClientContent   The secret content for the OAuth2 client.
	 * @param authClientMethod      The client authentication method.
	 * @param authGrantType         The authorization grant type.
	 * @param configurationType     The list of configuration types.
	 * @return The unique ID of the stored configuration.
	 * @throws GeboOauth2Exception If an error occurs during the storage process.
	 */
	@Override
	public String insertOauth2Configuration(@NotNull @Valid Oauth2ProviderConfig providerConfiguration,
			@NotNull @Valid GeboOauth2SecretContent oauth2ClientContent, Oauth2ClientAuthMethod authClientMethod,
			Oauth2AuthorizationGrantType authGrantType, @NotNull Oauth2ConfigurationType configurationType,
			String description) throws GeboOauth2Exception {
		AuthProvider authProvider = AuthProvider.oauth2_generic;
		String uniqueId = authProvider.name() + "-" + UUID.randomUUID().toString();
		Oauth2RuntimeConfiguration configuration = new Oauth2RuntimeConfiguration();
		configuration.setAuthGrantType(authGrantType);
		configuration.setClientAuthMethod(authClientMethod);
		configuration.setConfigurationType(configurationType);
		configuration.setProvider(authProvider);
		configuration.setRegistrationId(uniqueId);
		configuration.setProviderConfig(providerConfiguration);
		try {
			// Store the secret content using a unique identifier
			String secretId = secretService.storeSecret(oauth2ClientContent,
					"Oauth2 configuration for " + authProvider.name(), "oauth2-" + authProvider.name());
			configuration.setClientSecretId(secretId);
		} catch (GeboCryptSecretException e) {
			// Handle any exceptions raised during the storage of secrets
			throw new GeboOauth2Exception("Secret layer raising an error", e);
		}
		repository.insert(configuration);
		return uniqueId;
	}

	@Override
	public String insertOauth2Configuration(AuthProvider authProvider, GeboOauth2SecretContent oauth2ClientContent,
			Oauth2ClientAuthMethod authClientMethod, Oauth2AuthorizationGrantType authGrantType,
			Oauth2ConfigurationType configurationType, String description) throws GeboOauth2Exception {

		String uniqueId = authProvider.name() + "-" + UUID.randomUUID().toString();
		Oauth2RuntimeConfiguration configuration = new Oauth2RuntimeConfiguration();
		configuration.setAuthGrantType(authGrantType);
		configuration.setClientAuthMethod(authClientMethod);
		configuration.setConfigurationType(configurationType);
		configuration.setProvider(authProvider);
		configuration.setRegistrationId(uniqueId);
		try {
			// Store the secret content using a unique identifier
			String secretId = secretService.storeSecret(oauth2ClientContent,
					"Oauth2 configuration for " + authProvider.name(), "oauth2-" + authProvider.name());
			configuration.setClientSecretId(secretId);
		} catch (GeboCryptSecretException e) {
			// Handle any exceptions raised during the storage of secrets
			throw new GeboOauth2Exception("Secret layer raising an error", e);
		}
		repository.insert(configuration);
		return uniqueId;
	}

	/**
	 * Deletes an existing OAuth2 configuration based on ID.
	 *
	 * @param id The unique ID of the configuration.
	 * @throws GeboOauth2Exception If an error occurs during the deletion process.
	 */
	@Override
	public void deleteOauth2Configuration(String id) throws GeboOauth2Exception {
		Oauth2RuntimeConfiguration data = repository.findByCode(id);
		if (data != null) {
			if (data.getReadOnly() != null && data.getReadOnly())
				throw new GeboOauth2Exception("Trying to delete a readOnly oauth2 configuration " + id);
			try {
				// Attempt to delete the associated secret
				secretService.deleteSecret(data.getClientSecretId());
			} catch (GeboCryptSecretException e) {
				// Handle any exceptions raised during the deletion of secrets
				throw new GeboOauth2Exception("Secret layer raising an error in deletion", e);
			}
			repository.delete(data);
		}
	}

	/**
	 * Finds the OAuth2 client registration using the registration ID.
	 *
	 * @param id The unique ID of the registration.
	 * @return The OAuth2 client registration, or null if not found.
	 * @throws GeboOauth2Exception If an error occurs during the retrieval process.
	 */
	@Override
	public Oauth2ClientRegistration findOauth2ClientRegistrationByRegistrationId(String id) throws GeboOauth2Exception {
		Oauth2RuntimeConfiguration data = repository.findByCode(id);
		if (data == null)
			return null;
		Oauth2RuntimeConfiguration config = data;

		return complete(config);
	}

	/**
	 * Completes the OAuth2 client registration process using the runtime
	 * configuration.
	 *
	 * @param config The runtime configuration for OAuth2.
	 * @return A complete OAuth2 client registration.
	 * @throws GeboOauth2Exception If an error occurs during the completion process.
	 */
	private Oauth2ClientRegistration complete(Oauth2RuntimeConfiguration config) throws GeboOauth2Exception {
		GeboOauth2SecretContent secretClient = null;

		Oauth2ProviderConfig providerConfig = config.getProviderConfig();
		// for standard providers take references from static YML providerConfig =
		if (providerConfig == null && config.getProvider() != AuthProvider.oauth2_generic) {
			providerConfig = providersLibraryDao.findByCode(config.getProvider().name());
			config.setProviderConfig(providerConfig);
		}

		if (config.getReadOnly() == null || !config.getReadOnly()) {
			String secretId = config.getClientSecretId();
			try {
				// Retrieve secret content using the secret ID
				AbstractGeboSecretContent secretContent = secretService.getSecretContentById(secretId);
				if (secretContent != null && secretContent instanceof GeboOauth2SecretContent) {
					secretClient = (GeboOauth2SecretContent) secretContent;
				} else {
					// Throw exception if there's an issue with the secret storage layer
					throw new GeboOauth2Exception("Problems in the underlying secret storage layer for " + secretId
							+ " is " + secretContent != null && secretContent.type() != null
									? secretContent.type().name()
									: "NULL");
				}
			} catch (GeboCryptSecretException e) {
				// Handle any exceptions raised during the retrieval of secrets
				throw new GeboOauth2Exception("Problems in the underlying secret storage layer for " + secretId, e);
			}
		} else {
			// Build a mocked up GeboOauth2SecretContent for plain text secret received from
			// YML
			secretClient = config.getClient();
		}
		return new Oauth2ClientRegistration(secretClient, config);
	}

	/**
	 * Finds the Spring Security OAuth2 ClientRegistration using the registration
	 * ID.
	 *
	 * @param id The unique ID of the registration.
	 * @return The ClientRegistration.
	 * @throws GeboOauth2Exception If an error occurs during the retrieval process.
	 */
	@Override
	public ClientRegistration findClientRegistrationByRegistrationId(String id) throws GeboOauth2Exception {
		return Oauth2ClientRegistration.toClientRegistration(findOauth2ClientRegistrationByRegistrationId(id));
	}

	/**
	 * Finds all OAuth2 client registrations by provider.
	 *
	 * @param provider The authentication provider.
	 * @return A list of OAuth2 client registrations.
	 * @throws GeboOauth2Exception If an error occurs during the retrieval process.
	 */
	@Override
	public List<Oauth2ClientRegistration> findOauth2ClientRegistrationByProvider(AuthProvider provider)
			throws GeboOauth2Exception {
		List<Oauth2ClientRegistration> out = new ArrayList<>();
		List<Oauth2RuntimeConfiguration> list = repository.findByProvider(provider);
		for (Oauth2RuntimeConfiguration config : list) {
			out.add(complete(config));
		}
		return out;
	}

	/**
	 * Finds all OAuth2 client registrations by provider and configuration type.
	 *
	 * @param provider The authentication provider.
	 * @param type     The configuration type.
	 * @return A list of OAuth2 client registrations.
	 * @throws GeboOauth2Exception If an error occurs during the retrieval process.
	 */
	@Override
	public List<Oauth2ClientRegistration> findOauth2ClientRegistrationByProviderAndConfigurationType(
			AuthProvider provider, Oauth2ConfigurationType type) throws GeboOauth2Exception {
		List<Oauth2ClientRegistration> out = new ArrayList<>();
		List<Oauth2RuntimeConfiguration> list = repository.findByProviderAndConfigurationType(provider, type);
		for (Oauth2RuntimeConfiguration config : list) {
			out.add(complete(config));
		}
		return out;
	}

	@Override
	public List<Oauth2ClientAuthorizativeInfo> findAllAauthorizativeRegistrations() {
		List<Oauth2RuntimeConfiguration> list = repository
				.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
		return list.stream().map(x -> {
			Oauth2ClientAuthorizativeInfo data = new Oauth2ClientAuthorizativeInfo(x.getRegistrationId(),
					x.getProvider().name(), x.getDescription());
			return data;
		}).toList();

	}

	@Override
	public void updateOauth2Configuration(String registrationId, Oauth2ProviderConfig providerConfiguration,
			@NotNull @Valid GeboOauth2SecretContent oauth2ClientContent, Oauth2ClientAuthMethod authClientMethod,
			Oauth2AuthorizationGrantType authGrantType, Oauth2ConfigurationType configurationType, String description)
			throws GeboOauth2Exception {
		Oauth2RuntimeConfiguration data = repository.findByCode(registrationId);
		if (data == null)
			throw new GeboOauth2Exception("Unkown registrationid:" + registrationId);
		if (data != null && data.getReadOnly() != null && data.getReadOnly()) {
			throw new GeboOauth2Exception("registrationid:" + registrationId + " is not modifiable");
		}

		data.setDescription(description);
		data.setProviderConfig(providerConfiguration);
		data.setConfigurationType(configurationType);
		data.setClientAuthMethod(authClientMethod);
		data.setAuthGrantType(authGrantType);
		repository.save(data);
		try {
			SecretInfo info = secretService.getSecretInfoById(data.getClientSecretId());
			secretService.updateSecret(oauth2ClientContent, info.getDescription(), registrationId,
					data.getClientSecretId());
		} catch (GeboCryptSecretException e) {
			throw new GeboOauth2Exception("Cannot modify secret on registrationId:" + registrationId, e);
		}

	}

}