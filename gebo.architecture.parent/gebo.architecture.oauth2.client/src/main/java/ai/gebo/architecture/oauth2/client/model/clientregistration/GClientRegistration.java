/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model.clientregistration;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails;
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails.UserInfoEndpoint;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import ai.gebo.architecture.oauth2.client.model.EnumToStaticMapperUtil;

/**
 * A class representing a client registration, which includes details
 * such as client ID, client secret, authentication methods, and provider details.
 * AI generated comments
 */
public class GClientRegistration {
    // ID used to identify the registration
    private String registrationId;

    // Client ID issued by the provider
    private String clientId;

    // Client secret associated with the client ID
    private String clientSecret;

    // The authentication method used by the client
    private GClientAuthenticationMethod clientAuthenticationMethod = null;

    // The grant type used for authorization
    private GAuthorizationGrantType authorizationGrantType = null;

    // The URI to redirect to post authentication
    private String redirectUri;

    // A set of scopes requesting access to
    private Set<String> scopes = new HashSet<String>();

    // Details about the authentication provider
    private GProviderDetails providerDetails = new GProviderDetails();

    // Name associated with the client
    private String clientName;

    /**
     * Default constructor for GClientRegistration.
     */
    public GClientRegistration() {

    }

    /**
     * @return the registration ID
     */
    public String getRegistrationId() {
        return registrationId;
    }

    /**
     * @param registrationId the registration ID to set
     */
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    /**
     * @return the client ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the client ID to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the client secret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * @param clientSecret the client secret to set
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * @return the client authentication method
     */
    public GClientAuthenticationMethod getClientAuthenticationMethod() {
        return clientAuthenticationMethod;
    }

    /**
     * @param clientAuthenticationMethod the client authentication method to set
     */
    public void setClientAuthenticationMethod(GClientAuthenticationMethod clientAuthenticationMethod) {
        this.clientAuthenticationMethod = clientAuthenticationMethod;
    }

    /**
     * @return the authorization grant type
     */
    public GAuthorizationGrantType getAuthorizationGrantType() {
        return authorizationGrantType;
    }

    /**
     * @param authorizationGrantType the authorization grant type to set
     */
    public void setAuthorizationGrantType(GAuthorizationGrantType authorizationGrantType) {
        this.authorizationGrantType = authorizationGrantType;
    }

    /**
     * @return the redirect URI
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * @param redirectUri the redirect URI to set
     */
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    /**
     * @return the set of scopes
     */
    public Set<String> getScopes() {
        return scopes;
    }

    /**
     * @param scopes the scopes to set
     */
    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    /**
     * @return the provider details
     */
    public GProviderDetails getProviderDetails() {
        return providerDetails;
    }

    /**
     * @param providerDetails the provider details to set
     */
    public void setProviderDetails(GProviderDetails providerDetails) {
        this.providerDetails = providerDetails;
    }

    /**
     * @return the client name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the client name to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Converts a GClientRegistration object to a ClientRegistration object.
     * 
     * @param r GClientRegistration object to convert
     * @return a ClientRegistration object
     */
    public static ClientRegistration to(GClientRegistration r) {
        Builder builder = ClientRegistration.withRegistrationId(r.getRegistrationId());
        builder.authorizationUri(r.providerDetails.getAuthorizationUri());
        builder.clientId(r.clientId);
        builder.clientName(r.clientName);
        builder.clientSecret(r.clientSecret);
        builder.issuerUri(r.providerDetails.getIssuerUri());
        builder.jwkSetUri(r.providerDetails.getJwkSetUri());
        builder.providerConfigurationMetadata(r.providerDetails.getConfigurationMetadata());
        builder.redirectUri(r.redirectUri);
        builder.scope(r.scopes);
        builder.tokenUri(r.providerDetails.getTokenUri());
        builder.userInfoUri(r.getProviderDetails().getUserInfoEndpoint().getUri());
        builder.userNameAttributeName(r.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
        // Map authorization grant type to static type 
        builder.authorizationGrantType(
                EnumToStaticMapperUtil.enumToStatic(r.authorizationGrantType, AuthorizationGrantType.class));
        // Map client authentication method to static type
        builder.clientAuthenticationMethod(EnumToStaticMapperUtil.enumToStatic(r.clientAuthenticationMethod,
                org.springframework.security.oauth2.core.ClientAuthenticationMethod.class));
        // Map user info authentication method to static type
        builder.userInfoAuthenticationMethod(EnumToStaticMapperUtil.enumToStatic(
                r.providerDetails.getUserInfoEndpoint().getAuthenticationMethod(), AuthenticationMethod.class));
        return builder.build();
    }

    /**
     * Converts a ClientRegistration object to a GClientRegistration object.
     * 
     * @param r ClientRegistration object to convert
     * @return a GClientRegistration object
     */
    public static GClientRegistration from(ClientRegistration r) {
        GClientRegistration cr = new GClientRegistration();
        cr.setClientId(r.getClientId());
        cr.setClientName(r.getClientName());
        cr.setClientSecret(r.getClientSecret());
        cr.setRedirectUri(r.getRedirectUri());
        cr.setRegistrationId(r.getRegistrationId());
        cr.setScopes(r.getScopes());
        // Map static authorization grant type to enum
        cr.authorizationGrantType = EnumToStaticMapperUtil.staticToEnum(GAuthorizationGrantType.class,
                AuthorizationGrantType.class, r.getAuthorizationGrantType());
        // Map static client authentication method to enum
        cr.clientAuthenticationMethod = EnumToStaticMapperUtil.staticToEnum(GClientAuthenticationMethod.class,
                ClientAuthenticationMethod.class, r.getClientAuthenticationMethod());
        cr.providerDetails = to(r.getProviderDetails());
        return cr;
    }

    /**
     * Converts ProviderDetails to GProviderDetails.
     * 
     * @param p Provider details to convert
     * @return converted GProviderDetails
     */
    private static GProviderDetails to(ProviderDetails p) {
        GProviderDetails details = new GProviderDetails();
        details.setAuthorizationUri(p.getAuthorizationUri());
        details.setConfigurationMetadata(p.getConfigurationMetadata());
        details.setIssuerUri(p.getIssuerUri());
        details.setJwkSetUri(p.getJwkSetUri());
        details.setTokenUri(p.getTokenUri());
        details.setUserInfoEndpoint(to(p.getUserInfoEndpoint()));
        return details;
    }

    /**
     * Converts UserInfoEndpoint to GUserInfoEndpoint.
     * 
     * @param u User info endpoint to convert
     * @return converted GUserInfoEndpoint
     */
    private static GUserInfoEndpoint to(UserInfoEndpoint u) {
        GUserInfoEndpoint e = new GUserInfoEndpoint();
        e.setUri(u.getUri());
        e.setUserNameAttributeName(u.getUserNameAttributeName());
        // Map static authentication method to enum
        e.setAuthenticationMethod(EnumToStaticMapperUtil.staticToEnum(GAuthenticationMethod.class,
                AuthenticationMethod.class, u.getAuthenticationMethod()));
        return e;
    }
}