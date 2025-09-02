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
 * Class that provides API operations for managing fields within screen tabs in Jira Cloud.
 * This class handles the CRUD operations for screen tab fields including adding, retrieving,
 * moving, and removing fields from screen tabs.
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
import ai.gebo.jira.cloud.client.model.AddFieldBean;
import ai.gebo.jira.cloud.client.model.MoveFieldBean;
import ai.gebo.jira.cloud.client.model.ScreenableField;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class ScreenTabFieldsApi {
    private ApiClient apiClient;

    /**
     * Constructor for ScreenTabFieldsApi that accepts an ApiClient.
     * 
     * @param apiClient The client used to make API requests to Jira Cloud
     */
    public ScreenTabFieldsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Returns the API client instance used by this API.
     * 
     * @return The API client
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to be used by this API.
     * 
     * @param apiClient The API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add screen tab field
     * Adds a field to a screen tab.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the screen, screen tab, or field is not found.
     * @param body  (required)
     * @param screenId The ID of the screen. (required)
     * @param tabId The ID of the screen tab. (required)
     * @return ScreenableField
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ScreenableField addScreenTabField(AddFieldBean body, Long screenId, Long tabId) throws RestClientException {
        return addScreenTabFieldWithHttpInfo(body, screenId, tabId).getBody();
    }

    /**
     * Add screen tab field
     * Adds a field to a screen tab.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the screen, screen tab, or field is not found.
     * @param body  (required)
     * @param screenId The ID of the screen. (required)
     * @param tabId The ID of the screen tab. (required)
     * @return ResponseEntity&lt;ScreenableField&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ScreenableField> addScreenTabFieldWithHttpInfo(AddFieldBean body, Long screenId, Long tabId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addScreenTabField");
        }
        // verify the required parameter 'screenId' is set
        if (screenId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'screenId' when calling addScreenTabField");
        }
        // verify the required parameter 'tabId' is set
        if (tabId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tabId' when calling addScreenTabField");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("screenId", screenId);
        uriVariables.put("tabId", tabId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/screens/{screenId}/tabs/{tabId}/fields").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<ScreenableField> returnType = new ParameterizedTypeReference<ScreenableField>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get all screen tab fields
     * Returns all fields for a screen tab.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer projects* [project permission](https://confluence.atlassian.com/x/yodKLg) when the project key is specified, providing that the screen is associated with the project through a Screen Scheme and Issue Type Screen Scheme.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the screen or screen tab is not found.
     * @param screenId The ID of the screen. (required)
     * @param tabId The ID of the screen tab. (required)
     * @param projectKey The key of the project. (optional)
     * @return List&lt;ScreenableField&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ScreenableField> getAllScreenTabFields(Long screenId, Long tabId, String projectKey) throws RestClientException {
        return getAllScreenTabFieldsWithHttpInfo(screenId, tabId, projectKey).getBody();
    }

    /**
     * Get all screen tab fields
     * Returns all fields for a screen tab.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer projects* [project permission](https://confluence.atlassian.com/x/yodKLg) when the project key is specified, providing that the screen is associated with the project through a Screen Scheme and Issue Type Screen Scheme.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the screen or screen tab is not found.
     * @param screenId The ID of the screen. (required)
     * @param tabId The ID of the screen tab. (required)
     * @param projectKey The key of the project. (optional)
     * @return ResponseEntity&lt;List&lt;ScreenableField&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ScreenableField>> getAllScreenTabFieldsWithHttpInfo(Long screenId, Long tabId, String projectKey) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'screenId' is set
        if (screenId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'screenId' when calling getAllScreenTabFields");
        }
        // verify the required parameter 'tabId' is set
        if (tabId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tabId' when calling getAllScreenTabFields");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("screenId", screenId);
        uriVariables.put("tabId", tabId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/screens/{screenId}/tabs/{tabId}/fields").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "projectKey", projectKey));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<List<ScreenableField>> returnType = new ParameterizedTypeReference<List<ScreenableField>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Move screen tab field
     * Moves a screen tab field.  If &#x60;after&#x60; and &#x60;position&#x60; are provided in the request, &#x60;position&#x60; is ignored.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the screen, screen tab, or field is not found or the field can&#x27;t be moved to the requested position.
     * @param body  (required)
     * @param screenId The ID of the screen. (required)
     * @param tabId The ID of the screen tab. (required)
     * @param id The ID of the field. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object moveScreenTabField(MoveFieldBean body, Long screenId, Long tabId, String id) throws RestClientException {
        return moveScreenTabFieldWithHttpInfo(body, screenId, tabId, id).getBody();
    }

    /**
     * Move screen tab field
     * Moves a screen tab field.  If &#x60;after&#x60; and &#x60;position&#x60; are provided in the request, &#x60;position&#x60; is ignored.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the screen, screen tab, or field is not found or the field can&#x27;t be moved to the requested position.
     * @param body  (required)
     * @param screenId The ID of the screen. (required)
     * @param tabId The ID of the screen tab. (required)
     * @param id The ID of the field. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> moveScreenTabFieldWithHttpInfo(MoveFieldBean body, Long screenId, Long tabId, String id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling moveScreenTabField");
        }
        // verify the required parameter 'screenId' is set
        if (screenId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'screenId' when calling moveScreenTabField");
        }
        // verify the required parameter 'tabId' is set
        if (tabId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tabId' when calling moveScreenTabField");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling moveScreenTabField");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("screenId", screenId);
        uriVariables.put("tabId", tabId);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/screens/{screenId}/tabs/{tabId}/fields/{id}/move").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Remove screen tab field
     * Removes a field from a screen tab.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the screen, screen tab, or field is not found.
     * @param screenId The ID of the screen. (required)
     * @param tabId The ID of the screen tab. (required)
     * @param id The ID of the field. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void removeScreenTabField(Long screenId, Long tabId, String id) throws RestClientException {
        removeScreenTabFieldWithHttpInfo(screenId, tabId, id);
    }

    /**
     * Remove screen tab field
     * Removes a field from a screen tab.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the screen, screen tab, or field is not found.
     * @param screenId The ID of the screen. (required)
     * @param tabId The ID of the screen tab. (required)
     * @param id The ID of the field. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> removeScreenTabFieldWithHttpInfo(Long screenId, Long tabId, String id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'screenId' is set
        if (screenId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'screenId' when calling removeScreenTabField");
        }
        // verify the required parameter 'tabId' is set
        if (tabId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tabId' when calling removeScreenTabField");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling removeScreenTabField");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("screenId", screenId);
        uriVariables.put("tabId", tabId);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/screens/{screenId}/tabs/{tabId}/fields/{id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}