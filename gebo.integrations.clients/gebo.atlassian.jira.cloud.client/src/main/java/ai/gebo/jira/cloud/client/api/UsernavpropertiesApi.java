/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * API client for managing user navigation properties in Jira Cloud.
 * This class provides methods to retrieve and set user navigation preferences
 * stored in the Identity service.
 */
package ai.gebo.jira.cloud.client.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.UserNavPropertyJsonBean;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class UsernavpropertiesApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes with a new ApiClient instance
     */
    public UsernavpropertiesApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that takes a specific ApiClient instance
     * @param apiClient The API client to use for making requests
     */
    //@Autowired
    public UsernavpropertiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current API client
     * @return The current API client
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets a new API client
     * @param apiClient The new API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get user nav property
     * Returns the value of a user nav preference.  Note: This operation fetches the property key value directly from RbacClient.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg), to get a property from any user.  *  Access to Jira, to get a property from the calling user&#x27;s record.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if &#x60;accountId&#x60; is missing or &#x60;propertyKey&#x60; is missing.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission or is not accessing their user record.
     * <p><b>404</b> - Returned if either the user or property key is not found.
     * @param propertyKey The key of the user&#x27;s property. (required)
     * @param accountId The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. (optional)
     * @return UserNavPropertyJsonBean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserNavPropertyJsonBean getUserNavProperty(String propertyKey, String accountId) throws RestClientException {
        return getUserNavPropertyWithHttpInfo(propertyKey, accountId).getBody();
    }

    /**
     * Get user nav property
     * Returns the value of a user nav preference.  Note: This operation fetches the property key value directly from RbacClient.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg), to get a property from any user.  *  Access to Jira, to get a property from the calling user&#x27;s record.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if &#x60;accountId&#x60; is missing or &#x60;propertyKey&#x60; is missing.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission or is not accessing their user record.
     * <p><b>404</b> - Returned if either the user or property key is not found.
     * @param propertyKey The key of the user&#x27;s property. (required)
     * @param accountId The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. (optional)
     * @return ResponseEntity&lt;UserNavPropertyJsonBean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserNavPropertyJsonBean> getUserNavPropertyWithHttpInfo(String propertyKey, String accountId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'propertyKey' is set
        if (propertyKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'propertyKey' when calling getUserNavProperty");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("propertyKey", propertyKey);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/user/nav4-opt-property/{propertyKey}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "accountId", accountId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<UserNavPropertyJsonBean> returnType = new ParameterizedTypeReference<UserNavPropertyJsonBean>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Set user nav property
     * Sets the value of a Nav4 preference. Use this resource to store Nav4 preference data against a user in the Identity service.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg), to set a property on any user.  *  Access to Jira, to set a property on the calling user&#x27;s record.
     * <p><b>200</b> - Returned if the user property is updated/created.
     * <p><b>400</b> - Returned if &#x60;accountId&#x60; is missing.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission or is not accessing their user record.
     * <p><b>404</b> - Returned if the user is not found.
     * <p><b>405</b> - Returned if the property key is not specified.
     * @param body The value of the property. The value has to be a boolean [JSON](https://tools.ietf.org/html/rfc4627) value. The maximum length of the property value is 32768 bytes. (required)
     * @param propertyKey The key of the nav property. The maximum length is 255 characters. (required)
     * @param accountId The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. (optional)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object setUserNavProperty(Object body, String propertyKey, String accountId) throws RestClientException {
        return setUserNavPropertyWithHttpInfo(body, propertyKey, accountId).getBody();
    }

    /**
     * Set user nav property
     * Sets the value of a Nav4 preference. Use this resource to store Nav4 preference data against a user in the Identity service.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg), to set a property on any user.  *  Access to Jira, to set a property on the calling user&#x27;s record.
     * <p><b>200</b> - Returned if the user property is updated/created.
     * <p><b>400</b> - Returned if &#x60;accountId&#x60; is missing.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission or is not accessing their user record.
     * <p><b>404</b> - Returned if the user is not found.
     * <p><b>405</b> - Returned if the property key is not specified.
     * @param body The value of the property. The value has to be a boolean [JSON](https://tools.ietf.org/html/rfc4627) value. The maximum length of the property value is 32768 bytes. (required)
     * @param propertyKey The key of the nav property. The maximum length is 255 characters. (required)
     * @param accountId The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. (optional)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> setUserNavPropertyWithHttpInfo(Object body, String propertyKey, String accountId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling setUserNavProperty");
        }
        // verify the required parameter 'propertyKey' is set
        if (propertyKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'propertyKey' when calling setUserNavProperty");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("propertyKey", propertyKey);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/user/nav4-opt-property/{propertyKey}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "accountId", accountId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}