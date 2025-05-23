/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.oauth2.client.model.ClientRegistrationHolder;
import ai.gebo.architecture.oauth2.client.model.ClientRegistrationSecretWrapper;
import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapper;
import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapperId;
import ai.gebo.architecture.oauth2.client.model.Oauth2SaveModel;
import ai.gebo.architecture.oauth2.client.model.clientregistration.GClientRegistration;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;

/**
 * AI generated comments
 * Service class to handle the encryption and decryption of OAuth2 data, 
 * including client registrations and authorized clients.
 */
@Service
class SecretOauth2DataHandler {

    @Autowired
    IGClientRegistrationSecretWrapperRuntimeDao clientRegRuntimeDao;
    
    @Autowired
    IGOauth2ModelSecretWrapperRuntimeDao oauth2RuntimeDao;
    
    @Autowired
    IGeboCryptingService cryptService;
    
    static ObjectMapper mapper = new ObjectMapper();

    /**
     * Loads an Oauth2SaveModel based on the provided client registration ID and principal name.
     *
     * @param registrationClientId the client registration ID
     * @param principalName the name of the principal
     * @return an Optional containing the Oauth2SaveModel if found, otherwise an empty Optional
     */
    Optional<Oauth2SaveModel> loadBy(String registrationClientId, String principalName) {
        Oauth2ModelSecretWrapper wrapper = oauth2RuntimeDao.findByCode(registrationClientId + "::" + principalName);
        return wrapper != null ? Optional.of(decrypt(wrapper)) : Optional.empty();
    }

    /**
     * Decrypts an Oauth2ModelSecretWrapper into an Oauth2SaveModel.
     *
     * @param wrapper the Oauth2ModelSecretWrapper to decrypt
     * @return the decrypted Oauth2SaveModel
     */
    private Oauth2SaveModel decrypt(Oauth2ModelSecretWrapper wrapper) {
        try {
            String json = wrapper.getJsonContent();
            if (wrapper.getCrypted() != null && wrapper.getCrypted()) {
                json = cryptService.decrypt(json);
            }
            return mapper.readValue(json, Oauth2SaveModel.class);
        } catch (JsonProcessingException | GeboCryptSecretException e) {
            throw new IllegalStateException("Cannot deserialize json", e);
        }
    }

    /**
     * Encrypts an Oauth2SaveModel into a Oauth2ModelSecretWrapper.
     *
     * @param model the Oauth2SaveModel to encrypt
     * @param registrationClientId the client registration ID
     * @param principalName the principal's name
     * @return the encrypted Oauth2ModelSecretWrapper
     */
    private Oauth2ModelSecretWrapper encrypt(Oauth2SaveModel model, String registrationClientId, String principalName) {
        Oauth2ModelSecretWrapper wrapper = new Oauth2ModelSecretWrapper();
        wrapper.setId(new Oauth2ModelSecretWrapperId());
        wrapper.getId().setClientRegistrationId(registrationClientId);
        wrapper.getId().setPrincipalName(principalName);
        try {
            String json = mapper.writeValueAsString(model);
            String value = cryptService.crypt(json);
            wrapper.setCrypted(true);
            wrapper.setJsonContent(value);
            return wrapper;
        } catch (JsonProcessingException | GeboCryptSecretException e) {
            throw new IllegalStateException("Cannot serialize json", e);
        }
    }

    /**
     * Decrypts a ClientRegistrationSecretWrapper into a ClientRegistration.
     *
     * @param wrapper the ClientRegistrationSecretWrapper to decrypt
     * @return the decrypted ClientRegistration
     */
    private ClientRegistration decrypt(ClientRegistrationSecretWrapper wrapper) {
        String json = wrapper.getJsonContent();
        try {
            if (wrapper.getCrypted() != null && wrapper.getCrypted()) {
                json = cryptService.decrypt(json);
            }
            ClientRegistrationHolder holder = mapper.readValue(json, ClientRegistrationHolder.class);
            return GClientRegistration.to(holder.getClientRegistration());
        } catch (JsonProcessingException | GeboCryptSecretException e) {
            throw new IllegalStateException("Cannot serialize json", e);
        }
    }

    /**
     * Encrypts a ClientRegistration into a ClientRegistrationSecretWrapper.
     *
     * @param registration the ClientRegistration to encrypt
     * @param registrationClientId the registration client ID
     * @return the encrypted ClientRegistrationSecretWrapper
     */
    private ClientRegistrationSecretWrapper encrypt(ClientRegistration registration, String registrationClientId) {
        ClientRegistrationSecretWrapper wrapper = new ClientRegistrationSecretWrapper();
        ClientRegistrationHolder holder = new ClientRegistrationHolder();
        wrapper.setClientRegistrationId(registrationClientId);
        holder.setClientRegistration(GClientRegistration.from(registration));
        try {
            String json = mapper.writeValueAsString(holder);
            String value = cryptService.crypt(json);
            wrapper.setCrypted(true);
            wrapper.setJsonContent(value);
        } catch (JsonProcessingException | GeboCryptSecretException e) {
            throw new IllegalStateException("Cannot serialize json", e);
        }
        return wrapper;
    }

    /**
     * Finds a ClientRegistration by its registration ID.
     *
     * @param registrationId the ID of the client registration
     * @return the ClientRegistration if found, otherwise null
     */
    ClientRegistration findByRegistrationId(String registrationId) {
        ClientRegistrationSecretWrapper data = clientRegRuntimeDao.findByCode(registrationId);
        return data != null ? decrypt(data) : null;
    }

    /**
     * Loads an OAuth2AuthorizedClient based on the client registration ID and principal name.
     *
     * @param clientRegistrationId the client registration ID
     * @param principalName the principal's name
     * @return the OAuth2AuthorizedClient
     */
    OAuth2AuthorizedClient loadAuthorizedClient(String clientRegistrationId, String principalName) {
        Oauth2ModelSecretWrapper wrapper = oauth2RuntimeDao.findByCode(clientRegistrationId + "::" + principalName);
        Oauth2SaveModel value = decrypt(wrapper);
        return new OAuth2AuthorizedClient(value.getClientRegistration(), value.getPrincipalName(),
                value.getAccessToken());
    }

    /**
     * Saves an OAuth2AuthorizedClient in a secure, encrypted manner using the provided principal.
     *
     * @param authorizedClient the OAuth2AuthorizedClient to save
     * @param principal the authentication principal associated with the client
     */
    void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Oauth2SaveModel model = new Oauth2SaveModel();
        model.setAccessToken(authorizedClient.getAccessToken());
        model.setAuthenticationPrincipal(principal);
        model.setClientRegistration(authorizedClient.getClientRegistration());
        model.setRefreshToken(authorizedClient.getRefreshToken());
        model.setPrincipalName(authorizedClient.getPrincipalName());
        Oauth2ModelSecretWrapper wrapper = encrypt(model, authorizedClient.getClientRegistration().getRegistrationId(),
                principal.getName());
        oauth2RuntimeDao.save(wrapper);
    }

    /**
     * Removes an authorized client based on the client registration ID and principal name.
     *
     * @param clientRegistrationId the ID of the client registration
     * @param principalName the principal's name
     */
    void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        oauth2RuntimeDao.remove(clientRegistrationId + "::" + principalName);
    }

}