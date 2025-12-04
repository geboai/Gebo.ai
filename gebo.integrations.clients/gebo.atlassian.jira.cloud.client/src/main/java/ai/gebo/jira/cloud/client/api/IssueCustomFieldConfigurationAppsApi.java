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
 * This class provides access to the Jira Cloud API for custom field configurations managed by apps.
 * It enables retrieving and updating configurations for custom fields that were created by Forge apps.
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
import ai.gebo.jira.cloud.client.model.ConfigurationsListParameters;
import ai.gebo.jira.cloud.client.model.CustomFieldConfigurations;
import ai.gebo.jira.cloud.client.model.PageBeanBulkContextualConfiguration;
import ai.gebo.jira.cloud.client.model.PageBeanContextualConfiguration;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.IssueCustomFieldConfigurationAppsApi")
public class IssueCustomFieldConfigurationAppsApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes with a new ApiClient
     */
    public IssueCustomFieldConfigurationAppsApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that takes an ApiClient
     * @param apiClient The API client to use for API requests
     */
    //@Autowired
    public IssueCustomFieldConfigurationAppsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the configured ApiClient
     * @return The current ApiClient
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the ApiClient to use for API requests
     * @param apiClient The ApiClient to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get custom field configurations
     * Returns a [paginated](#pagination) list of configurations for a custom field of a [type](https://developer.atlassian.com/platform/forge/manifest-reference/modules/jira-custom-field-type/) created by a [Forge app](https://developer.atlassian.com/platform/forge/).  The result can be filtered by one of these criteria:   *  &#x60;id&#x60;.  *  &#x60;fieldContextId&#x60;.  *  &#x60;issueId&#x60;.  *  &#x60;projectKeyOrId&#x60; and &#x60;issueTypeId&#x60;.  Otherwise, all configurations are returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg). Jira permissions are not required for the Forge app that provided the custom field type.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user is not a Jira admin or the request is not authenticated as from the app that provided the field.
     * <p><b>404</b> - Returned if the custom field is not found.
     * @param fieldIdOrKey The ID or key of the custom field, for example &#x60;customfield_10000&#x60;. (required)
     * @param id The list of configuration IDs. To include multiple configurations, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. Can&#x27;t be provided with &#x60;fieldContextId&#x60;, &#x60;issueId&#x60;, &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param fieldContextId The list of field context IDs. To include multiple field contexts, separate IDs with an ampersand: &#x60;fieldContextId&#x3D;10000&amp;fieldContextId&#x3D;10001&#x60;. Can&#x27;t be provided with &#x60;id&#x60;, &#x60;issueId&#x60;, &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param issueId The ID of the issue to filter results by. If the issue doesn&#x27;t exist, an empty list is returned. Can&#x27;t be provided with &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param projectKeyOrId The ID or key of the project to filter results by. Must be provided with &#x60;issueTypeId&#x60;. Can&#x27;t be provided with &#x60;issueId&#x60;. (optional)
     * @param issueTypeId The ID of the issue type to filter results by. Must be provided with &#x60;projectKeyOrId&#x60;. Can&#x27;t be provided with &#x60;issueId&#x60;. (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 100)
     * @return PageBeanContextualConfiguration
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanContextualConfiguration getCustomFieldConfiguration(String fieldIdOrKey, List<Long> id, List<Long> fieldContextId, Long issueId, String projectKeyOrId, String issueTypeId, Long startAt, Integer maxResults) throws RestClientException {
        return getCustomFieldConfigurationWithHttpInfo(fieldIdOrKey, id, fieldContextId, issueId, projectKeyOrId, issueTypeId, startAt, maxResults).getBody();
    }

    /**
     * Get custom field configurations
     * Returns a [paginated](#pagination) list of configurations for a custom field of a [type](https://developer.atlassian.com/platform/forge/manifest-reference/modules/jira-custom-field-type/) created by a [Forge app](https://developer.atlassian.com/platform/forge/).  The result can be filtered by one of these criteria:   *  &#x60;id&#x60;.  *  &#x60;fieldContextId&#x60;.  *  &#x60;issueId&#x60;.  *  &#x60;projectKeyOrId&#x60; and &#x60;issueTypeId&#x60;.  Otherwise, all configurations are returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg). Jira permissions are not required for the Forge app that provided the custom field type.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user is not a Jira admin or the request is not authenticated as from the app that provided the field.
     * <p><b>404</b> - Returned if the custom field is not found.
     * @param fieldIdOrKey The ID or key of the custom field, for example &#x60;customfield_10000&#x60;. (required)
     * @param id The list of configuration IDs. To include multiple configurations, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. Can&#x27;t be provided with &#x60;fieldContextId&#x60;, &#x60;issueId&#x60;, &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param fieldContextId The list of field context IDs. To include multiple field contexts, separate IDs with an ampersand: &#x60;fieldContextId&#x3D;10000&amp;fieldContextId&#x3D;10001&#x60;. Can&#x27;t be provided with &#x60;id&#x60;, &#x60;issueId&#x60;, &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param issueId The ID of the issue to filter results by. If the issue doesn&#x27;t exist, an empty list is returned. Can&#x27;t be provided with &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param projectKeyOrId The ID or key of the project to filter results by. Must be provided with &#x60;issueTypeId&#x60;. Can&#x27;t be provided with &#x60;issueId&#x60;. (optional)
     * @param issueTypeId The ID of the issue type to filter results by. Must be provided with &#x60;projectKeyOrId&#x60;. Can&#x27;t be provided with &#x60;issueId&#x60;. (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 100)
     * @return ResponseEntity&lt;PageBeanContextualConfiguration&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanContextualConfiguration> getCustomFieldConfigurationWithHttpInfo(String fieldIdOrKey, List<Long> id, List<Long> fieldContextId, Long issueId, String projectKeyOrId, String issueTypeId, Long startAt, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'fieldIdOrKey' is set
        if (fieldIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fieldIdOrKey' when calling getCustomFieldConfiguration");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fieldIdOrKey", fieldIdOrKey);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/app/field/{fieldIdOrKey}/context/configuration").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "id", id));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "fieldContextId", fieldContextId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "issueId", issueId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "projectKeyOrId", projectKeyOrId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "issueTypeId", issueTypeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanContextualConfiguration> returnType = new ParameterizedTypeReference<PageBeanContextualConfiguration>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk get custom field configurations
     * Returns a [paginated](#pagination) list of configurations for list of custom fields of a [type](https://developer.atlassian.com/platform/forge/manifest-reference/modules/jira-custom-field-type/) created by a [Forge app](https://developer.atlassian.com/platform/forge/).  The result can be filtered by one of these criteria:   *  &#x60;id&#x60;.  *  &#x60;fieldContextId&#x60;.  *  &#x60;issueId&#x60;.  *  &#x60;projectKeyOrId&#x60; and &#x60;issueTypeId&#x60;.  Otherwise, all configurations for the provided list of custom fields are returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg). Jira permissions are not required for the Forge app that provided the custom field type.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user is not a Jira admin or the request is not authenticated as from the app that provided the field.
     * <p><b>404</b> - Returned if the custom field is not found.
     * @param body  (required)
     * @param id The list of configuration IDs. To include multiple configurations, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. Can&#x27;t be provided with &#x60;fieldContextId&#x60;, &#x60;issueId&#x60;, &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param fieldContextId The list of field context IDs. To include multiple field contexts, separate IDs with an ampersand: &#x60;fieldContextId&#x3D;10000&amp;fieldContextId&#x3D;10001&#x60;. Can&#x27;t be provided with &#x60;id&#x60;, &#x60;issueId&#x60;, &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param issueId The ID of the issue to filter results by. If the issue doesn&#x27;t exist, an empty list is returned. Can&#x27;t be provided with &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param projectKeyOrId The ID or key of the project to filter results by. Must be provided with &#x60;issueTypeId&#x60;. Can&#x27;t be provided with &#x60;issueId&#x60;. (optional)
     * @param issueTypeId The ID of the issue type to filter results by. Must be provided with &#x60;projectKeyOrId&#x60;. Can&#x27;t be provided with &#x60;issueId&#x60;. (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 100)
     * @return PageBeanBulkContextualConfiguration
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanBulkContextualConfiguration getCustomFieldsConfigurations(ConfigurationsListParameters body, List<Long> id, List<Long> fieldContextId, Long issueId, String projectKeyOrId, String issueTypeId, Long startAt, Integer maxResults) throws RestClientException {
        return getCustomFieldsConfigurationsWithHttpInfo(body, id, fieldContextId, issueId, projectKeyOrId, issueTypeId, startAt, maxResults).getBody();
    }

    /**
     * Bulk get custom field configurations
     * Returns a [paginated](#pagination) list of configurations for list of custom fields of a [type](https://developer.atlassian.com/platform/forge/manifest-reference/modules/jira-custom-field-type/) created by a [Forge app](https://developer.atlassian.com/platform/forge/).  The result can be filtered by one of these criteria:   *  &#x60;id&#x60;.  *  &#x60;fieldContextId&#x60;.  *  &#x60;issueId&#x60;.  *  &#x60;projectKeyOrId&#x60; and &#x60;issueTypeId&#x60;.  Otherwise, all configurations for the provided list of custom fields are returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg). Jira permissions are not required for the Forge app that provided the custom field type.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user is not a Jira admin or the request is not authenticated as from the app that provided the field.
     * <p><b>404</b> - Returned if the custom field is not found.
     * @param body  (required)
     * @param id The list of configuration IDs. To include multiple configurations, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. Can&#x27;t be provided with &#x60;fieldContextId&#x60;, &#x60;issueId&#x60;, &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param fieldContextId The list of field context IDs. To include multiple field contexts, separate IDs with an ampersand: &#x60;fieldContextId&#x3D;10000&amp;fieldContextId&#x3D;10001&#x60;. Can&#x27;t be provided with &#x60;id&#x60;, &#x60;issueId&#x60;, &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param issueId The ID of the issue to filter results by. If the issue doesn&#x27;t exist, an empty list is returned. Can&#x27;t be provided with &#x60;projectKeyOrId&#x60;, or &#x60;issueTypeId&#x60;. (optional)
     * @param projectKeyOrId The ID or key of the project to filter results by. Must be provided with &#x60;issueTypeId&#x60;. Can&#x27;t be provided with &#x60;issueId&#x60;. (optional)
     * @param issueTypeId The ID of the issue type to filter results by. Must be provided with &#x60;projectKeyOrId&#x60;. Can&#x27;t be provided with &#x60;issueId&#x60;. (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 100)
     * @return ResponseEntity&lt;PageBeanBulkContextualConfiguration&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanBulkContextualConfiguration> getCustomFieldsConfigurationsWithHttpInfo(ConfigurationsListParameters body, List<Long> id, List<Long> fieldContextId, Long issueId, String projectKeyOrId, String issueTypeId, Long startAt, Integer maxResults) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getCustomFieldsConfigurations");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/app/field/context/configuration/list").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "id", id));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "fieldContextId", fieldContextId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "issueId", issueId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "projectKeyOrId", projectKeyOrId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "issueTypeId", issueTypeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanBulkContextualConfiguration> returnType = new ParameterizedTypeReference<PageBeanBulkContextualConfiguration>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update custom field configurations
     * Update the configuration for contexts of a custom field of a [type](https://developer.atlassian.com/platform/forge/manifest-reference/modules/jira-custom-field-type/) created by a [Forge app](https://developer.atlassian.com/platform/forge/).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg). Jira permissions are not required for the Forge app that created the custom field type.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user is not a Jira admin or the request is not authenticated as from the app that provided the field.
     * <p><b>404</b> - Returned if the custom field is not found.
     * @param body  (required)
     * @param fieldIdOrKey The ID or key of the custom field, for example &#x60;customfield_10000&#x60;. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object updateCustomFieldConfiguration(CustomFieldConfigurations body, String fieldIdOrKey) throws RestClientException {
        return updateCustomFieldConfigurationWithHttpInfo(body, fieldIdOrKey).getBody();
    }

    /**
     * Update custom field configurations
     * Update the configuration for contexts of a custom field of a [type](https://developer.atlassian.com/platform/forge/manifest-reference/modules/jira-custom-field-type/) created by a [Forge app](https://developer.atlassian.com/platform/forge/).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg). Jira permissions are not required for the Forge app that created the custom field type.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user is not a Jira admin or the request is not authenticated as from the app that provided the field.
     * <p><b>404</b> - Returned if the custom field is not found.
     * @param body  (required)
     * @param fieldIdOrKey The ID or key of the custom field, for example &#x60;customfield_10000&#x60;. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> updateCustomFieldConfigurationWithHttpInfo(CustomFieldConfigurations body, String fieldIdOrKey) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateCustomFieldConfiguration");
        }
        // verify the required parameter 'fieldIdOrKey' is set
        if (fieldIdOrKey == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fieldIdOrKey' when calling updateCustomFieldConfiguration");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fieldIdOrKey", fieldIdOrKey);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/app/field/{fieldIdOrKey}/context/configuration").buildAndExpand(uriVariables).toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}