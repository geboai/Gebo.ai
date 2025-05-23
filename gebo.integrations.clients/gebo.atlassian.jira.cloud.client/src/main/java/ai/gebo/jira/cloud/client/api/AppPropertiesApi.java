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
 * 
 * This class provides an API for managing app properties in Jira Cloud.
 * It handles operations for both Connect and Forge apps, allowing them to
 * store, retrieve, and delete custom property data.
 */
package ai.gebo.jira.cloud.client.api;

import ai.gebo.jira.cloud.client.invoker.ApiClient;

import ai.gebo.jira.cloud.client.model.EntityProperty;
import ai.gebo.jira.cloud.client.model.OperationMessage;
import ai.gebo.jira.cloud.client.model.PropertyKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.AppPropertiesApi")
public class AppPropertiesApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes with a new ApiClient
     */
    public AppPropertiesApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that allows injecting a custom ApiClient
     * @param apiClient The API client to use for API calls
     */
    //@Autowired
    public AppPropertiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Returns the current ApiClient instance
     * @return The ApiClient used by this API
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the ApiClient to use for API calls
     * @param apiClient The ApiClient to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete app property
     * Deletes an app&#x27;s property.  **[Permissions](#permissions) required:** Only a Connect app whose key matches &#x60;addonKey&#x60; can make this request. Additionally, Forge apps can access Connect app properties (stored against the same &#x60;app.connect.key&#x60;).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the property key is longer than 127 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if the property is not found or doesn&#x27;t belong to the app.
     * @param addonKey The key of the app, as defined in its descriptor. (required)
     * @param propertyKey The key of the property. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void addonPropertiesResourceDeleteAddonPropertyDelete(String addonKey, String propertyKey) throws RestClientException {
        addonPropertiesResourceDeleteAddonPropertyDeleteWithHttpInfo(addonKey, propertyKey);
    }

    /**
     * Delete app property
     * Deletes an app&#x27;s property.  **[Permissions](#permissions) required:** Only a Connect app whose key matches &#x60;addonKey&#x60; can make this request. Additionally, Forge apps can access Connect app properties (stored against the same &#x60;app.connect.key&#x60;).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the property key is longer than 127 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if the property is not found or doesn&#x27;t belong to the app.
     * @param addonKey The key of the app, as defined in its descriptor. (required)
     * @param propertyKey The key of the property. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> addonPropertiesResourceDeleteAddonPropertyDeleteWithHttpInfo(String addonKey, String propertyKey) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'addonKey' is set
        if (addonKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'addonKey' when calling addonPropertiesResourceDeleteAddonPropertyDelete");
        }
        // verify the required parameter 'propertyKey' is set
        if (propertyKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'propertyKey' when calling addonPropertiesResourceDeleteAddonPropertyDelete");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("addonKey", addonKey);
        uriVariables.put("propertyKey", propertyKey);
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/addons/{addonKey}/properties/{propertyKey}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get app properties
     * Gets all the properties of an app.  **[Permissions](#permissions) required:** Only a Connect app whose key matches &#x60;addonKey&#x60; can make this request. Additionally, Forge apps can access Connect app properties (stored against the same &#x60;app.connect.key&#x60;).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param addonKey The key of the app, as defined in its descriptor. (required)
     * @return PropertyKeys
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PropertyKeys addonPropertiesResourceGetAddonPropertiesGet(String addonKey) throws RestClientException {
        return addonPropertiesResourceGetAddonPropertiesGetWithHttpInfo(addonKey).getBody();
    }

    /**
     * Get app properties
     * Gets all the properties of an app.  **[Permissions](#permissions) required:** Only a Connect app whose key matches &#x60;addonKey&#x60; can make this request. Additionally, Forge apps can access Connect app properties (stored against the same &#x60;app.connect.key&#x60;).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param addonKey The key of the app, as defined in its descriptor. (required)
     * @return ResponseEntity&lt;PropertyKeys&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PropertyKeys> addonPropertiesResourceGetAddonPropertiesGetWithHttpInfo(String addonKey) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'addonKey' is set
        if (addonKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'addonKey' when calling addonPropertiesResourceGetAddonPropertiesGet");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("addonKey", addonKey);
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/addons/{addonKey}/properties").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PropertyKeys> returnType = new ParameterizedTypeReference<PropertyKeys>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get app property
     * Returns the key and value of an app&#x27;s property.  **[Permissions](#permissions) required:** Only a Connect app whose key matches &#x60;addonKey&#x60; can make this request. Additionally, Forge apps can access Connect app properties (stored against the same &#x60;app.connect.key&#x60;).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the property key is longer than 127 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if the property is not found or doesn&#x27;t belong to the app.
     * @param addonKey The key of the app, as defined in its descriptor. (required)
     * @param propertyKey The key of the property. (required)
     * @return EntityProperty
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public EntityProperty addonPropertiesResourceGetAddonPropertyGet(String addonKey, String propertyKey) throws RestClientException {
        return addonPropertiesResourceGetAddonPropertyGetWithHttpInfo(addonKey, propertyKey).getBody();
    }

    /**
     * Get app property
     * Returns the key and value of an app&#x27;s property.  **[Permissions](#permissions) required:** Only a Connect app whose key matches &#x60;addonKey&#x60; can make this request. Additionally, Forge apps can access Connect app properties (stored against the same &#x60;app.connect.key&#x60;).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the property key is longer than 127 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if the property is not found or doesn&#x27;t belong to the app.
     * @param addonKey The key of the app, as defined in its descriptor. (required)
     * @param propertyKey The key of the property. (required)
     * @return ResponseEntity&lt;EntityProperty&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EntityProperty> addonPropertiesResourceGetAddonPropertyGetWithHttpInfo(String addonKey, String propertyKey) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'addonKey' is set
        if (addonKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'addonKey' when calling addonPropertiesResourceGetAddonPropertyGet");
        }
        // verify the required parameter 'propertyKey' is set
        if (propertyKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'propertyKey' when calling addonPropertiesResourceGetAddonPropertyGet");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("addonKey", addonKey);
        uriVariables.put("propertyKey", propertyKey);
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/addons/{addonKey}/properties/{propertyKey}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<EntityProperty> returnType = new ParameterizedTypeReference<EntityProperty>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Set app property
     * Sets the value of an app&#x27;s property. Use this resource to store custom data for your app.  The value of the request body must be a [valid](http://tools.ietf.org/html/rfc4627), non-empty JSON blob. The maximum length is 32768 characters.  **[Permissions](#permissions) required:** Only a Connect app whose key matches &#x60;addonKey&#x60; can make this request. Additionally, Forge apps can access Connect app properties (stored against the same &#x60;app.connect.key&#x60;).
     * <p><b>200</b> - Returned if the property is updated.
     * <p><b>201</b> - Returned is the property is created.
     * <p><b>400</b> - Returned if:   * the property key is longer than 127 characters.   * the value is not valid JSON.   * the value is longer than 32768 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param body  (required)
     * @param addonKey The key of the app, as defined in its descriptor. (required)
     * @param propertyKey The key of the property. (required)
     * @return OperationMessage
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationMessage addonPropertiesResourcePutAddonPropertyPut(Object body, String addonKey, String propertyKey) throws RestClientException {
        return addonPropertiesResourcePutAddonPropertyPutWithHttpInfo(body, addonKey, propertyKey).getBody();
    }

    /**
     * Set app property
     * Sets the value of an app&#x27;s property. Use this resource to store custom data for your app.  The value of the request body must be a [valid](http://tools.ietf.org/html/rfc4627), non-empty JSON blob. The maximum length is 32768 characters.  **[Permissions](#permissions) required:** Only a Connect app whose key matches &#x60;addonKey&#x60; can make this request. Additionally, Forge apps can access Connect app properties (stored against the same &#x60;app.connect.key&#x60;).
     * <p><b>200</b> - Returned if the property is updated.
     * <p><b>201</b> - Returned is the property is created.
     * <p><b>400</b> - Returned if:   * the property key is longer than 127 characters.   * the value is not valid JSON.   * the value is longer than 32768 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * @param body  (required)
     * @param addonKey The key of the app, as defined in its descriptor. (required)
     * @param propertyKey The key of the property. (required)
     * @return ResponseEntity&lt;OperationMessage&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationMessage> addonPropertiesResourcePutAddonPropertyPutWithHttpInfo(Object body, String addonKey, String propertyKey) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addonPropertiesResourcePutAddonPropertyPut");
        }
        // verify the required parameter 'addonKey' is set
        if (addonKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'addonKey' when calling addonPropertiesResourcePutAddonPropertyPut");
        }
        // verify the required parameter 'propertyKey' is set
        if (propertyKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'propertyKey' when calling addonPropertiesResourcePutAddonPropertyPut");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("addonKey", addonKey);
        uriVariables.put("propertyKey", propertyKey);
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/addons/{addonKey}/properties/{propertyKey}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<OperationMessage> returnType = new ParameterizedTypeReference<OperationMessage>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete app property (Forge)
     * Deletes a Forge app&#x27;s property.  **[Permissions](#permissions) required:** Only Forge apps can make this request.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the property key is longer than 127 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the request isn&#x27;t made directly by an app or if it&#x27;s an impersonated request.
     * <p><b>404</b> - Returned if the property isn&#x27;t found or doesn&#x27;t belong to the app.
     * @param propertyKey The key of the property. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteForgeAppProperty(String propertyKey) throws RestClientException {
        deleteForgeAppPropertyWithHttpInfo(propertyKey);
    }

    /**
     * Delete app property (Forge)
     * Deletes a Forge app&#x27;s property.  **[Permissions](#permissions) required:** Only Forge apps can make this request.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the property key is longer than 127 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the request isn&#x27;t made directly by an app or if it&#x27;s an impersonated request.
     * <p><b>404</b> - Returned if the property isn&#x27;t found or doesn&#x27;t belong to the app.
     * @param propertyKey The key of the property. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteForgeAppPropertyWithHttpInfo(String propertyKey) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'propertyKey' is set
        if (propertyKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'propertyKey' when calling deleteForgeAppProperty");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("propertyKey", propertyKey);
        String path = UriComponentsBuilder.fromPath("/rest/forge/1/app/properties/{propertyKey}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Set app property (Forge)
     * Sets the value of a Forge app&#x27;s property. These values can be retrieved in [Jira expressions](/cloud/jira/platform/jira-expressions/) through the &#x60;app&#x60; [context variable](/cloud/jira/platform/jira-expressions/#context-variables). They are also available in [entity property display conditions](/platform/forge/manifest-reference/display-conditions/entity-property-conditions/).  For other use cases, use the [Storage API](/platform/forge/runtime-reference/storage-api/).  The value of the request body must be a [valid](http://tools.ietf.org/html/rfc4627), non-empty JSON blob. The maximum length is 32768 characters.  **[Permissions](#permissions) required:** Only Forge apps can make this request.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>200</b> - Returned if the property is updated.
     * <p><b>201</b> - Returned is the property is created.
     * <p><b>400</b> - Returned if:   * the property key is longer than 127 characters.   * the value isn&#x27;t valid JSON.   * the value is longer than 32768 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the request isn&#x27;t made directly by an app or if it&#x27;s an impersonated request.
     * @param body  (required)
     * @param propertyKey The key of the property. (required)
     * @return OperationMessage
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationMessage putForgeAppProperty(Object body, String propertyKey) throws RestClientException {
        return putForgeAppPropertyWithHttpInfo(body, propertyKey).getBody();
    }

    /**
     * Set app property (Forge)
     * Sets the value of a Forge app&#x27;s property. These values can be retrieved in [Jira expressions](/cloud/jira/platform/jira-expressions/) through the &#x60;app&#x60; [context variable](/cloud/jira/platform/jira-expressions/#context-variables). They are also available in [entity property display conditions](/platform/forge/manifest-reference/display-conditions/entity-property-conditions/).  For other use cases, use the [Storage API](/platform/forge/runtime-reference/storage-api/).  The value of the request body must be a [valid](http://tools.ietf.org/html/rfc4627), non-empty JSON blob. The maximum length is 32768 characters.  **[Permissions](#permissions) required:** Only Forge apps can make this request.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>200</b> - Returned if the property is updated.
     * <p><b>201</b> - Returned is the property is created.
     * <p><b>400</b> - Returned if:   * the property key is longer than 127 characters.   * the value isn&#x27;t valid JSON.   * the value is longer than 32768 characters.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the request isn&#x27;t made directly by an app or if it&#x27;s an impersonated request.
     * @param body  (required)
     * @param propertyKey The key of the property. (required)
     * @return ResponseEntity&lt;OperationMessage&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationMessage> putForgeAppPropertyWithHttpInfo(Object body, String propertyKey) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling putForgeAppProperty");
        }
        // verify the required parameter 'propertyKey' is set
        if (propertyKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'propertyKey' when calling putForgeAppProperty");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("propertyKey", propertyKey);
        String path = UriComponentsBuilder.fromPath("/rest/forge/1/app/properties/{propertyKey}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<OperationMessage> returnType = new ParameterizedTypeReference<OperationMessage>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}