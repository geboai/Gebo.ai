/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model.clientregistration;

import java.io.Serializable;

/**
 * Gebo.ai comment agent
 * Represents a user information endpoint typically used in OAuth2 client registrations.
 * This class holds configuration details for accessing user information, such as
 * the URI, authentication method, and the user name attribute name.
 */
public class GUserInfoEndpoint implements Serializable {

    // The URI of the user information endpoint.
    private String uri;

    // The method used for authentication with the user information endpoint, default is HEADER.
    private GAuthenticationMethod authenticationMethod = GAuthenticationMethod.HEADER;

    // The name of the attribute used to retrieve the user's name from the endpoint.
    private String userNameAttributeName;

    /**
     * Default constructor.
     */
    public GUserInfoEndpoint() {
    }

    /**
     * Gets the URI of the user information endpoint.
     *
     * @return the URI of the user information endpoint.
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the URI of the user information endpoint.
     *
     * @param uri the new URI of the user information endpoint.
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Gets the authentication method used with the user information endpoint.
     *
     * @return the authentication method.
     */
    public GAuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

    /**
     * Sets the authentication method for the user information endpoint.
     *
     * @param authenticationMethod the new authentication method.
     */
    public void setAuthenticationMethod(GAuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    /**
     * Gets the name of the user name attribute.
     *
     * @return the user name attribute name.
     */
    public String getUserNameAttributeName() {
        return userNameAttributeName;
    }

    /**
     * Sets the name of the user name attribute.
     *
     * @param userNameAttributeName the new user name attribute name.
     */
    public void setUserNameAttributeName(String userNameAttributeName) {
        this.userNameAttributeName = userNameAttributeName;
    }
}