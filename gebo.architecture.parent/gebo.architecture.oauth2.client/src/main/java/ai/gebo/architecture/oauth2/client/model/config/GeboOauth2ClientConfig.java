/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.oauth2.client.model.ClientRegistrationSecretWrapper;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;

/**
 * Gebo.ai Commentor signature: Gebo.ai comment agent
 *
 * Configuration class for managing OAuth2 client properties in the Gebo Architecture.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.oauth2.clients.library")
@PropertySource(value = "classpath:/bundled-oauth2/oauth2-clients.yml", factory = GeboYamlPropertySourceFactory.class)
public class GeboOauth2ClientConfig {

    // List to hold client registration details with encrypted secrets
    private List<ClientRegistrationSecretWrapper> cryptedClientRegistrations = new ArrayList<ClientRegistrationSecretWrapper>();

    /**
     * Default constructor.
     */
    public GeboOauth2ClientConfig() {
    }

    /**
     * Returns the list of encrypted client registrations.
     * 
     * @return list of ClientRegistrationSecretWrapper objects
     */
    public List<ClientRegistrationSecretWrapper> getCryptedClientRegistrations() {
        return cryptedClientRegistrations;
    }

    /**
     * Sets the list of encrypted client registrations.
     * 
     * @param cryptedClientRegistrations a list of ClientRegistrationSecretWrapper objects
     */
    public void setCryptedClientRegistrations(List<ClientRegistrationSecretWrapper> cryptedClientRegistrations) {
        this.cryptedClientRegistrations = cryptedClientRegistrations;
    }

}