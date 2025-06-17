/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.model.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the OAuth2 client registration configuration.
 * 
 * Consists of a secret content part, and runtime configuration part.
 * 
 * AI generated comments
 */
@AllArgsConstructor
@Getter
public class Oauth2ClientRegistration {
    // Contains the secret content necessary for OAuth2 client registration.
    private final GeboOauth2SecretContent clientRegistration;
    
    // Contains the runtime configuration for OAuth2 client registration.
    private final Oauth2RuntimeConfiguration runtimeConfiguration;

    /**
     * Converts an Oauth2ClientRegistration to a ClientRegistration object.
     *
     * @param registration The Oauth2ClientRegistration to convert.
     * @return The corresponding ClientRegistration object, or null if the input is null.
     */
    public static ClientRegistration toClientRegistration(Oauth2ClientRegistration registration) {
        if (registration == null)
            return null;
        
        // Create a new ClientRegistration.Builder using the registration ID from runtime configuration.
        Builder builder = ClientRegistration
                .withRegistrationId(registration.getRuntimeConfiguration().getRegistrationId());

        // Build and return the ClientRegistration.
        return builder.build();
    }
}