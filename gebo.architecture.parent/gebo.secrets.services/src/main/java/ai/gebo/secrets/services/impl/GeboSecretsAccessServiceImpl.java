/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.impl.GeboCryptingServiceImpl;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboGoogleJsonSecretContent;
import ai.gebo.secrets.model.GeboGoogleOauth2SecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboSecret;
import ai.gebo.secrets.model.GeboSshKeySecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.repository.GeboSecretRepository;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI Generated Comments
 * Service implementation of Gebo Secrets Access.
 * Provides functionality to manage secrets including storing, retrieving, and deleting.
 */
@Service
public class GeboSecretsAccessServiceImpl implements IGeboSecretsAccessService {

    @Autowired
    GeboSecretRepository repository;  // Repository for accessing secrets data
    
    @Autowired
    GeboCryptingServiceImpl cryptService;  // Service for cryptography operations

    public GeboSecretsAccessServiceImpl() {
        // Default constructor
    }

    /**
     * Retrieve secret content by its ID.
     * 
     * @param id The ID of the secret to retrieve.
     * @return The secret content as an AbstractGeboSecretContent.
     * @throws GeboCryptSecretException if the secret cannot be decrypted or does not exist.
     */
    @Override
    public AbstractGeboSecretContent getSecretContentById(String id) throws GeboCryptSecretException {
        Optional<GeboSecret> content = repository.findById(id);
        GeboSecret secret = content.isPresent() ? content.get() : null;
        if (secret == null)
            throw new GeboCryptSecretException("Unkown secret with code=>" + id);
        switch (secret.getSecretType()) {
        case SSH_KEY: {
            return uncryptContent(secret, GeboSshKeySecretContent.class);
        }

        case USERNAME_PASSWORD: {
            return uncryptContent(secret, GeboUsernamePasswordContent.class);
        }

        case TOKEN: {
            return uncryptContent(secret, GeboTokenContent.class);
        }
        case CUSTOM_SECRET: {
            return uncryptContent(secret, GeboCustomSecretContent.class);
        }
        case OAUTH2_STANDARD: {
            return uncryptContent(secret, GeboOauth2SecretContent.class);
        }
        case OAUTH2_GOOGLE: {
            return uncryptContent(secret, GeboGoogleOauth2SecretContent.class);
        }
        case GOOGLE_CLOUD_JSON_CREDENTIALS: {
            return uncryptContent(secret, GeboGoogleJsonSecretContent.class);
        }
        default:
            throw new GeboCryptSecretException("Unkown value of SecretType ");
        }

    }

    /**
     * Encrypt secret content.
     * 
     * @param secret The secret to encrypt.
     * @return The encrypted string.
     * @throws GeboCryptSecretException if encryption fails.
     */
    private <SecretType extends AbstractGeboSecretContent> String cryptContent(SecretType secret)
            throws GeboCryptSecretException {
        ObjectMapper mapper = new ObjectMapper();
        String crypted = null;
        try {
            String serialized = mapper.writeValueAsString(secret);
            crypted = cryptService.crypt(serialized);
        } catch (JsonProcessingException e) {
            throw new GeboCryptSecretException("exception while running storeSecret(...)", e);
        }
        return crypted;
    }

    /**
     * Decrypt secret content.
     * 
     * @param secret The encrypted secret.
     * @param type The type to which the secret belongs.
     * @return The decrypted secret content.
     * @throws GeboCryptSecretException if decryption fails.
     */
    private <SecretType extends AbstractGeboSecretContent> SecretType uncryptContent(GeboSecret secret,
            Class<SecretType> type) throws GeboCryptSecretException {
        String cripted = secret.getSecretContent();  // Get encrypted content
        String uncripted = cryptService.decrypt(cripted);  // Decrypt content
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(uncripted, type);  // Deserialize content
        } catch (JsonProcessingException e) {
            throw new GeboCryptSecretException("Cannot read from secret json format", e);
        }
    }

    /**
     * Store a new secret.
     * 
     * @param secret The secret content to store.
     * @param description A description of the secret.
     * @param contextCode The context code associated with the secret.
     * @return The generated unique code for the new secret.
     * @throws GeboCryptSecretException if storing fails.
     */
    @Override
    public <SecretType extends AbstractGeboSecretContent> String storeSecret(SecretType secret, String description,
            String contextCode) throws GeboCryptSecretException {
        String code = UUID.randomUUID().toString();  // Generate unique ID
        this.storeSecret(secret, description, contextCode, code);  // Use detailed store method
        return code;
    }

    /**
     * Update an existing secret.
     * 
     * @param secret The updated secret content.
     * @param description The new description.
     * @param contextCode The new context code.
     * @param code The unique code of the secret.
     * @throws GeboCryptSecretException if the secret cannot be found or updated.
     */
    @Override
    public <SecretType extends AbstractGeboSecretContent> void updateSecret(SecretType secret, String description,
            String contextCode, String code) throws GeboCryptSecretException {
        GeboSecret _secret;
        Optional<GeboSecret> foundSecret = repository.findById(code);
        if (foundSecret.isEmpty())
            throw new GeboCryptSecretException("Secret with code=>" + code + " not found");
        _secret = foundSecret.get();
        _secret.setSecretContent(cryptContent(secret)); // Update encrypted content
        _secret.setDescription(description);
        _secret.setContextCode(contextCode);
        _secret.setSecretType(secret.type());
        repository.save(_secret); // Save updated secret
    }

    /**
     * Delete a secret by its unique code.
     * 
     * @param code The unique code of the secret to delete.
     * @throws GeboCryptSecretException if the deletion fails.
     */
    @Override
    public void deleteSecret(String code) throws GeboCryptSecretException {
        repository.deleteById(code);

    }

    /**
     * Retrieve all secrets associated with a specific context code.
     * 
     * @param contextCode The context code.
     * @return A list of secret information.
     * @throws GeboCryptSecretException if the retrieval fails.
     */
    @Override
    public List<SecretInfo> getSecretInfoByContextCode(String contextCode) throws GeboCryptSecretException {
        List<GeboSecret> secrets = repository.findByContextCode(contextCode);
        Stream<SecretInfo> infoStream = secrets.stream().map(x -> {
            return new SecretInfo(x);
        });
        return infoStream.toList(); // Convert stream to list
    }

    /**
     * Retrieve secret information by secret ID.
     * 
     * @param code The unique code of the secret.
     * @return Secret information, or null if not found.
     * @throws GeboCryptSecretException if retrieval fails.
     */
    @Override
    public SecretInfo getSecretInfoById(String code) throws GeboCryptSecretException {
        Optional<GeboSecret> content = repository.findById(code);
        return content.isPresent() ? new SecretInfo(content.get()) : null;
    }

    /**
     * Store a new secret with a specified unique code.
     * 
     * @param secret The secret content to store.
     * @param description A description of the secret.
     * @param contextCode The context code associated with the secret.
     * @param secretId The unique code for the new secret.
     * @throws GeboCryptSecretException if storing fails.
     */
    @Override
    public <SecretType extends AbstractGeboSecretContent> void storeSecret(SecretType secret, String description,
            String contextCode, String secretId) throws GeboCryptSecretException {
        GeboSecret _secret = new GeboSecret();
        _secret.setSecretContent(cryptContent(secret)); // Store the encrypted content
        _secret.setDescription(description);
        _secret.setContextCode(contextCode);
        _secret.setSecretType(secret.type());
        _secret.setCreationDate(new Date()); // Set creation date
        _secret.setCode(secretId);
        _secret = repository.insert(_secret); // Insert into repository
    }

}