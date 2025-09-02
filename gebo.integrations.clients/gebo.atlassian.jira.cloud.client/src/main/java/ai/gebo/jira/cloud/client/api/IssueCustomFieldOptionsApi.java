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
 * This class provides API operations for working with custom field options in Jira Cloud.
 * It allows for creating, retrieving, updating, and deleting custom field options within contexts.
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
import ai.gebo.jira.cloud.client.model.BulkCustomFieldOptionCreateRequest;
import ai.gebo.jira.cloud.client.model.BulkCustomFieldOptionUpdateRequest;
import ai.gebo.jira.cloud.client.model.CustomFieldCreatedContextOptionsList;
import ai.gebo.jira.cloud.client.model.CustomFieldOption;
import ai.gebo.jira.cloud.client.model.CustomFieldUpdatedContextOptionsList;
import ai.gebo.jira.cloud.client.model.OrderOfCustomFieldOptions;
import ai.gebo.jira.cloud.client.model.PageBeanCustomFieldContextOption;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.IssueCustomFieldOptionsApi")
public class IssueCustomFieldOptionsApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes the API client
     */
    public IssueCustomFieldOptionsApi() {
        this(new ApiClient());
    }

    /**
     * Constructor with specific API client
     * @param apiClient The API client to use for requests
     */
    //@Autowired
    public IssueCustomFieldOptionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get the API client instance
     * @return The current API client
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Set the API client instance
     * @param apiClient The API client to set
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create custom field options (context)
     * Creates options and, where the custom select field is of the type Select List (cascading), cascading options for a custom select field. The options are added to a context of the field.  The maximum number of options that can be created per request is 1000 and each field can have a maximum of 10000 options.  This operation works for custom field options created in Jira or the operations from this resource. **To work with issue field select list options created for Connect apps use the [Issue custom field options (apps)](#api-group-issue-custom-field-options--apps-) operations.**  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the custom field is not found or the context doesn&#x27;t match the custom field.
     * @param body  (required)
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context. (required)
     * @return CustomFieldCreatedContextOptionsList
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CustomFieldCreatedContextOptionsList createCustomFieldOption(BulkCustomFieldOptionCreateRequest body, String fieldId, Long contextId) throws RestClientException {
        return createCustomFieldOptionWithHttpInfo(body, fieldId, contextId).getBody();
    }

    /**
     * Create custom field options (context)
     * Creates options and, where the custom select field is of the type Select List (cascading), cascading options for a custom select field. The options are added to a context of the field.  The maximum number of options that can be created per request is 1000 and each field can have a maximum of 10000 options.  This operation works for custom field options created in Jira or the operations from this resource. **To work with issue field select list options created for Connect apps use the [Issue custom field options (apps)](#api-group-issue-custom-field-options--apps-) operations.**  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the custom field is not found or the context doesn&#x27;t match the custom field.
     * @param body  (required)
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context. (required)
     * @return ResponseEntity&lt;CustomFieldCreatedContextOptionsList&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CustomFieldCreatedContextOptionsList> createCustomFieldOptionWithHttpInfo(BulkCustomFieldOptionCreateRequest body, String fieldId, Long contextId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createCustomFieldOption");
        }
        // verify the required parameter 'fieldId' is set
        if (fieldId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fieldId' when calling createCustomFieldOption");
        }
        // verify the required parameter 'contextId' is set
        if (contextId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'contextId' when calling createCustomFieldOption");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fieldId", fieldId);
        uriVariables.put("contextId", contextId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/field/{fieldId}/context/{contextId}/option").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<CustomFieldCreatedContextOptionsList> returnType = new ParameterizedTypeReference<CustomFieldCreatedContextOptionsList>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete custom field options (context)
     * Deletes a custom field option.  Options with cascading options cannot be deleted without deleting the cascading options first.  This operation works for custom field options created in Jira or the operations from this resource. **To work with issue field select list options created for Connect apps use the [Issue custom field options (apps)](#api-group-issue-custom-field-options--apps-) operations.**  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the option is deleted.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the field, the context, or the option is not found.
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context from which an option should be deleted. (required)
     * @param optionId The ID of the option to delete. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteCustomFieldOption(String fieldId, Long contextId, Long optionId) throws RestClientException {
        deleteCustomFieldOptionWithHttpInfo(fieldId, contextId, optionId);
    }

    /**
     * Delete custom field options (context)
     * Deletes a custom field option.  Options with cascading options cannot be deleted without deleting the cascading options first.  This operation works for custom field options created in Jira or the operations from this resource. **To work with issue field select list options created for Connect apps use the [Issue custom field options (apps)](#api-group-issue-custom-field-options--apps-) operations.**  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the option is deleted.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the field, the context, or the option is not found.
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context from which an option should be deleted. (required)
     * @param optionId The ID of the option to delete. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteCustomFieldOptionWithHttpInfo(String fieldId, Long contextId, Long optionId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'fieldId' is set
        if (fieldId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fieldId' when calling deleteCustomFieldOption");
        }
        // verify the required parameter 'contextId' is set
        if (contextId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'contextId' when calling deleteCustomFieldOption");
        }
        // verify the required parameter 'optionId' is set
        if (optionId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'optionId' when calling deleteCustomFieldOption");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fieldId", fieldId);
        uriVariables.put("contextId", contextId);
        uriVariables.put("optionId", optionId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/field/{fieldId}/context/{contextId}/option/{optionId}").buildAndExpand(uriVariables).toUriString();
        
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
     * Get custom field option
     * Returns a custom field option. For example, an option in a select list.  Note that this operation **only works for issue field select list options created in Jira or using operations from the [Issue custom field options](#api-group-Issue-custom-field-options) resource**, it cannot be used with issue field select list options created by Connect apps.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** The custom field option is returned as follows:   *  if the user has the *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  if the user has the *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for at least one project the custom field is used in, and the field is visible in at least one layout the user has permission to view.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  the custom field option is not found.  *  the user does not have permission to view the custom field.
     * @param id The ID of the custom field option. (required)
     * @return CustomFieldOption
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CustomFieldOption getCustomFieldOption(String id) throws RestClientException {
        return getCustomFieldOptionWithHttpInfo(id).getBody();
    }

    /**
     * Get custom field option
     * Returns a custom field option. For example, an option in a select list.  Note that this operation **only works for issue field select list options created in Jira or using operations from the [Issue custom field options](#api-group-Issue-custom-field-options) resource**, it cannot be used with issue field select list options created by Connect apps.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** The custom field option is returned as follows:   *  if the user has the *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  if the user has the *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for at least one project the custom field is used in, and the field is visible in at least one layout the user has permission to view.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if:   *  the custom field option is not found.  *  the user does not have permission to view the custom field.
     * @param id The ID of the custom field option. (required)
     * @return ResponseEntity&lt;CustomFieldOption&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CustomFieldOption> getCustomFieldOptionWithHttpInfo(String id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getCustomFieldOption");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/customFieldOption/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<CustomFieldOption> returnType = new ParameterizedTypeReference<CustomFieldOption>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get custom field options (context)
     * Returns a [paginated](#pagination) list of all custom field option for a context. Options are returned first then cascading options, in the order they display in Jira.  This operation works for custom field options created in Jira or the operations from this resource. **To work with issue field select list options created for Connect apps use the [Issue custom field options (apps)](#api-group-issue-custom-field-options--apps-) operations.**  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg). *Edit Workflow* [edit workflow permission](https://support.atlassian.com/jira-cloud-administration/docs/permissions-for-company-managed-projects/#Edit-Workflows)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the custom field is not found or the context doesn&#x27;t match the custom field.
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context. (required)
     * @param optionId The ID of the option. (optional)
     * @param onlyOptions Whether only options are returned. (optional, default to false)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 100)
     * @return PageBeanCustomFieldContextOption
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanCustomFieldContextOption getOptionsForContext(String fieldId, Long contextId, Long optionId, Boolean onlyOptions, Long startAt, Integer maxResults) throws RestClientException {
        return getOptionsForContextWithHttpInfo(fieldId, contextId, optionId, onlyOptions, startAt, maxResults).getBody();
    }

    /**
     * Get custom field options (context)
     * Returns a [paginated](#pagination) list of all custom field option for a context. Options are returned first then cascading options, in the order they display in Jira.  This operation works for custom field options created in Jira or the operations from this resource. **To work with issue field select list options created for Connect apps use the [Issue custom field options (apps)](#api-group-issue-custom-field-options--apps-) operations.**  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg). *Edit Workflow* [edit workflow permission](https://support.atlassian.com/jira-cloud-administration/docs/permissions-for-company-managed-projects/#Edit-Workflows)
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the custom field is not found or the context doesn&#x27;t match the custom field.
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context. (required)
     * @param optionId The ID of the option. (optional)
     * @param onlyOptions Whether only options are returned. (optional, default to false)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 100)
     * @return ResponseEntity&lt;PageBeanCustomFieldContextOption&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanCustomFieldContextOption> getOptionsForContextWithHttpInfo(String fieldId, Long contextId, Long optionId, Boolean onlyOptions, Long startAt, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'fieldId' is set
        if (fieldId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fieldId' when calling getOptionsForContext");
        }
        // verify the required parameter 'contextId' is set
        if (contextId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'contextId' when calling getOptionsForContext");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fieldId", fieldId);
        uriVariables.put("contextId", contextId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/field/{fieldId}/context/{contextId}/option").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optionId", optionId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "onlyOptions", onlyOptions));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanCustomFieldContextOption> returnType = new ParameterizedTypeReference<PageBeanCustomFieldContextOption>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Reorder custom field options (context)
     * Changes the order of custom field options or cascading options in a context.  This operation works for custom field options created in Jira or the operations from this resource. **To work with issue field select list options created for Connect apps use the [Issue custom field options (apps)](#api-group-issue-custom-field-options--apps-) operations.**  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if options are reordered.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the field, the context, or one or more of the options is not found..
     * @param body  (required)
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object reorderCustomFieldOptions(OrderOfCustomFieldOptions body, String fieldId, Long contextId) throws RestClientException {
        return reorderCustomFieldOptionsWithHttpInfo(body, fieldId, contextId).getBody();
    }

    /**
     * Reorder custom field options (context)
     * Changes the order of custom field options or cascading options in a context.  This operation works for custom field options created in Jira or the operations from this resource. **To work with issue field select list options created for Connect apps use the [Issue custom field options (apps)](#api-group-issue-custom-field-options--apps-) operations.**  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if options are reordered.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the field, the context, or one or more of the options is not found..
     * @param body  (required)
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> reorderCustomFieldOptionsWithHttpInfo(OrderOfCustomFieldOptions body, String fieldId, Long contextId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling reorderCustomFieldOptions");
        }
        // verify the required parameter 'fieldId' is set
        if (fieldId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fieldId' when calling reorderCustomFieldOptions");
        }
        // verify the required parameter 'contextId' is set
        if (contextId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'contextId' when calling reorderCustomFieldOptions");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fieldId", fieldId);
        uriVariables.put("contextId", contextId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/field/{fieldId}/context/{contextId}/option/move").buildAndExpand(uriVariables).toUriString();
        
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
    /**
     * Replace custom field options
     * Replaces the options of a custom field.  Note that this operation **only works for issue field select list options created in Jira or using operations from the [Issue custom field options](#api-group-Issue-custom-field-options) resource**, it cannot be used with issue field select list options created by Connect or Forge apps.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>303</b> - Returned if the long-running task to deselect the option is started.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the field is not found or does not support options, or the options to be replaced are not found.
     * @param fieldId The ID of the custom field. (required)
     * @param optionId The ID of the option to be deselected. (required)
     * @param contextId The ID of the context. (required)
     * @param replaceWith The ID of the option that will replace the currently selected option. (optional)
     * @param jql A JQL query that specifies the issues to be updated. For example, *project&#x3D;10000*. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void replaceCustomFieldOption(String fieldId, Long optionId, Long contextId, Long replaceWith, String jql) throws RestClientException {
        replaceCustomFieldOptionWithHttpInfo(fieldId, optionId, contextId, replaceWith, jql);
    }

    /**
     * Replace custom field options
     * Replaces the options of a custom field.  Note that this operation **only works for issue field select list options created in Jira or using operations from the [Issue custom field options](#api-group-Issue-custom-field-options) resource**, it cannot be used with issue field select list options created by Connect or Forge apps.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>303</b> - Returned if the long-running task to deselect the option is started.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the field is not found or does not support options, or the options to be replaced are not found.
     * @param fieldId The ID of the custom field. (required)
     * @param optionId The ID of the option to be deselected. (required)
     * @param contextId The ID of the context. (required)
     * @param replaceWith The ID of the option that will replace the currently selected option. (optional)
     * @param jql A JQL query that specifies the issues to be updated. For example, *project&#x3D;10000*. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> replaceCustomFieldOptionWithHttpInfo(String fieldId, Long optionId, Long contextId, Long replaceWith, String jql) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'fieldId' is set
        if (fieldId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fieldId' when calling replaceCustomFieldOption");
        }
        // verify the required parameter 'optionId' is set
        if (optionId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'optionId' when calling replaceCustomFieldOption");
        }
        // verify the required parameter 'contextId' is set
        if (contextId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'contextId' when calling replaceCustomFieldOption");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fieldId", fieldId);
        uriVariables.put("optionId", optionId);
        uriVariables.put("contextId", contextId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/field/{fieldId}/context/{contextId}/option/{optionId}/issue").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "replaceWith", replaceWith));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "jql", jql));

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
     * Update custom field options (context)
     * Updates the options of a custom field.  If any of the options are not found, no options are updated. Options where the values in the request match the current values aren&#x27;t updated and aren&#x27;t reported in the response.  Note that this operation **only works for issue field select list options created in Jira or using operations from the [Issue custom field options](#api-group-Issue-custom-field-options) resource**, it cannot be used with issue field select list options created by Connect apps.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the field, context, or one or more options is not found.
     * @param body  (required)
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context. (required)
     * @return CustomFieldUpdatedContextOptionsList
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CustomFieldUpdatedContextOptionsList updateCustomFieldOption(BulkCustomFieldOptionUpdateRequest body, String fieldId, Long contextId) throws RestClientException {
        return updateCustomFieldOptionWithHttpInfo(body, fieldId, contextId).getBody();
    }

    /**
     * Update custom field options (context)
     * Updates the options of a custom field.  If any of the options are not found, no options are updated. Options where the values in the request match the current values aren&#x27;t updated and aren&#x27;t reported in the response.  Note that this operation **only works for issue field select list options created in Jira or using operations from the [Issue custom field options](#api-group-Issue-custom-field-options) resource**, it cannot be used with issue field select list options created by Connect apps.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the field, context, or one or more options is not found.
     * @param body  (required)
     * @param fieldId The ID of the custom field. (required)
     * @param contextId The ID of the context. (required)
     * @return ResponseEntity&lt;CustomFieldUpdatedContextOptionsList&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CustomFieldUpdatedContextOptionsList> updateCustomFieldOptionWithHttpInfo(BulkCustomFieldOptionUpdateRequest body, String fieldId, Long contextId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateCustomFieldOption");
        }
        // verify the required parameter 'fieldId' is set
        if (fieldId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fieldId' when calling updateCustomFieldOption");
        }
        // verify the required parameter 'contextId' is set
        if (contextId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'contextId' when calling updateCustomFieldOption");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fieldId", fieldId);
        uriVariables.put("contextId", contextId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/field/{fieldId}/context/{contextId}/option").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<CustomFieldUpdatedContextOptionsList> returnType = new ParameterizedTypeReference<CustomFieldUpdatedContextOptionsList>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}