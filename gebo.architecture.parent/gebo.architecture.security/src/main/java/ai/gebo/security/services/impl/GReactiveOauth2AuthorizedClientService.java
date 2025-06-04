/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2TokenSecretContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import reactor.core.publisher.Mono;

/**
 * AI generated comments
 *
 * This class implements the ReactiveOAuth2AuthorizedClientService interface, providing methods
 * to save, load, and remove OAuth2 authorized clients by interacting with a secret storage service.
 */
public class GReactiveOauth2AuthorizedClientService implements ReactiveOAuth2AuthorizedClientService {

    // Repository for client registration used to retrieve client configurations.
    private final ReactiveClientRegistrationRepository clientRegistrationRepository;

    // Service for accessing stored secrets including OAuth2 token data.
    private final IGeboSecretsAccessService secrets;

    /**
     * Constructor to initialize the service with required dependencies.
     *
     * @param clientRegistrationRepository Repository for OAuth2 client registrations.
     * @param secrets Service for managing secrets including OAuth2 tokens.
     */
    public GReactiveOauth2AuthorizedClientService(ReactiveClientRegistrationRepository clientRegistrationRepository,
                                                  IGeboSecretsAccessService secrets) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.secrets = secrets;
    }

    /**
     * Builds a secret identifier using the principal and registration ID.
     *
     * @param principal The principal name.
     * @param registrationId The registration ID of the OAuth2 client.
     * @return A string representing the secret ID.
     */
    private String buildSecretId(String principal, String registrationId) {
        return "oauth2-client-token-" + registrationId + "-" + principal;
    }

    /**
     * Saves an OAuth2 authorized client by storing its tokens as a secret.
     *
     * @param client The OAuth2AuthorizedClient to save.
     * @param principal The authentication principal associated with the client.
     * @return A Mono that completes when the operation has finished.
     */
    @Override
    public Mono<Void> saveAuthorizedClient(OAuth2AuthorizedClient client, Authentication principal) {
        return Mono.fromRunnable(() -> {
            GeboOauth2TokenSecretContent content = new GeboOauth2TokenSecretContent();
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
        });
    }

    /**
     * Loads an OAuth2 authorized client from the secret storage based on registration ID and principal name.
     *
     * @param registrationId The registration ID of the OAuth2 client.
     * @param principalName The principal name associated with the client.
     * @return A Mono emitting the OAuth2AuthorizedClient if found, else null.
     */
    @Override
    public Mono<OAuth2AuthorizedClient> loadAuthorizedClient(String registrationId, String principalName) {
        String secretId = buildSecretId(principalName, registrationId);
        return Mono.fromCallable(() -> {
            try {
                AbstractGeboSecretContent content = secrets.getSecretContentById(secretId);
                if (content instanceof GeboOauth2TokenSecretContent tokenContent) {
                    return new OAuth2AuthorizedClient(
                            clientRegistrationRepository.findByRegistrationId(registrationId).block(), principalName,
                            tokenContent.getAccessToken(), tokenContent.getRefreshToken());
                }
                return null;
            } catch (GeboCryptSecretException e) {
                throw new RuntimeException("Token load failure from secrets layer", e);
            }
        });
    }

    /**
     * Removes an OAuth2 authorized client by deleting its corresponding secret.
     *
     * @param registrationId The registration ID of the OAuth2 client.
     * @param principalName The principal name associated with the client.
     * @return A Mono that completes when the client has been removed.
     */
    @Override
    public Mono<Void> removeAuthorizedClient(String registrationId, String principalName) {
        return Mono.fromRunnable(() -> {
            try {
                String secretId = buildSecretId(principalName, registrationId);
                secrets.deleteSecret(secretId);
            } catch (GeboCryptSecretException e) {
                throw new RuntimeException("Token deletion failure from secrets layer", e);
            }
        });
    }
}