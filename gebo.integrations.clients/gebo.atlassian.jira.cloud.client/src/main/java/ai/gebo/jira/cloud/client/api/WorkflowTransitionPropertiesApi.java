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
 * This class provides an API for managing workflow transition properties in JIRA Cloud.
 * Transition properties are used to modify the behavior of workflow transitions.
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
import ai.gebo.jira.cloud.client.model.WorkflowTransitionProperty;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class WorkflowTransitionPropertiesApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes with a new ApiClient
     */
    public WorkflowTransitionPropertiesApi() {
        this(new ApiClient());
    }

    /**
     * Constructor that accepts a custom ApiClient
     * @param apiClient The API client to use for requests
     */
    //@Autowired
    public WorkflowTransitionPropertiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the current API client
     * @return The current ApiClient instance
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to use
     * @param apiClient The API client to use for requests
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create workflow transition property
     * Adds a property to a workflow transition. Transition properties are used to change the behavior of a transition. For more information, see [Transition properties](https://confluence.atlassian.com/x/zIhKLg#Advancedworkflowconfiguration-transitionproperties) and [Workflow properties](https://confluence.atlassian.com/x/JYlKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - 200 response
     * <p><b>400</b> - Returned if a workflow property with the same key is present on the transition.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow transition is not found.
     * @param body  (required)
     * @param key The key of the property being added, also known as the name of the property. Set this to the same value as the &#x60;key&#x60; defined in the request body. (required)
     * @param workflowName The name of the workflow that the transition belongs to. (required)
     * @param transitionId The ID of the transition. To get the ID, view the workflow in text mode in the Jira admin settings. The ID is shown next to the transition. (required)
     * @param workflowMode The workflow status. Set to *live* for inactive workflows or *draft* for draft workflows. Active workflows cannot be edited. (optional, default to live)
     * @return WorkflowTransitionProperty
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowTransitionProperty createWorkflowTransitionProperty(Map<String, Object> body, String key, String workflowName, Long transitionId, String workflowMode) throws RestClientException {
        return createWorkflowTransitionPropertyWithHttpInfo(body, key, workflowName, transitionId, workflowMode).getBody();
    }

    /**
     * Create workflow transition property
     * Adds a property to a workflow transition. Transition properties are used to change the behavior of a transition. For more information, see [Transition properties](https://confluence.atlassian.com/x/zIhKLg#Advancedworkflowconfiguration-transitionproperties) and [Workflow properties](https://confluence.atlassian.com/x/JYlKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - 200 response
     * <p><b>400</b> - Returned if a workflow property with the same key is present on the transition.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow transition is not found.
     * @param body  (required)
     * @param key The key of the property being added, also known as the name of the property. Set this to the same value as the &#x60;key&#x60; defined in the request body. (required)
     * @param workflowName The name of the workflow that the transition belongs to. (required)
     * @param transitionId The ID of the transition. To get the ID, view the workflow in text mode in the Jira admin settings. The ID is shown next to the transition. (required)
     * @param workflowMode The workflow status. Set to *live* for inactive workflows or *draft* for draft workflows. Active workflows cannot be edited. (optional, default to live)
     * @return ResponseEntity&lt;WorkflowTransitionProperty&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowTransitionProperty> createWorkflowTransitionPropertyWithHttpInfo(Map<String, Object> body, String key, String workflowName, Long transitionId, String workflowMode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createWorkflowTransitionProperty");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'key' when calling createWorkflowTransitionProperty");
        }
        // verify the required parameter 'workflowName' is set
        if (workflowName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowName' when calling createWorkflowTransitionProperty");
        }
        // verify the required parameter 'transitionId' is set
        if (transitionId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'transitionId' when calling createWorkflowTransitionProperty");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("transitionId", transitionId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/transitions/{transitionId}/properties").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key", key));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowName", workflowName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowMode", workflowMode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowTransitionProperty> returnType = new ParameterizedTypeReference<WorkflowTransitionProperty>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete workflow transition property
     * Deletes a property from a workflow transition. Transition properties are used to change the behavior of a transition. For more information, see [Transition properties](https://confluence.atlassian.com/x/zIhKLg#Advancedworkflowconfiguration-transitionproperties) and [Workflow properties](https://confluence.atlassian.com/x/JYlKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - 200 response
     * <p><b>304</b> - Returned if no changes were made by the request. For example, trying to delete a property that cannot be found.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow transition is not found.
     * @param transitionId The ID of the transition. To get the ID, view the workflow in text mode in the Jira admin settings. The ID is shown next to the transition. (required)
     * @param key The name of the transition property to delete, also known as the name of the property. (required)
     * @param workflowName The name of the workflow that the transition belongs to. (required)
     * @param workflowMode The workflow status. Set to &#x60;live&#x60; for inactive workflows or &#x60;draft&#x60; for draft workflows. Active workflows cannot be edited. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteWorkflowTransitionProperty(Long transitionId, String key, String workflowName, String workflowMode) throws RestClientException {
        deleteWorkflowTransitionPropertyWithHttpInfo(transitionId, key, workflowName, workflowMode);
    }

    /**
     * Delete workflow transition property
     * Deletes a property from a workflow transition. Transition properties are used to change the behavior of a transition. For more information, see [Transition properties](https://confluence.atlassian.com/x/zIhKLg#Advancedworkflowconfiguration-transitionproperties) and [Workflow properties](https://confluence.atlassian.com/x/JYlKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - 200 response
     * <p><b>304</b> - Returned if no changes were made by the request. For example, trying to delete a property that cannot be found.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow transition is not found.
     * @param transitionId The ID of the transition. To get the ID, view the workflow in text mode in the Jira admin settings. The ID is shown next to the transition. (required)
     * @param key The name of the transition property to delete, also known as the name of the property. (required)
     * @param workflowName The name of the workflow that the transition belongs to. (required)
     * @param workflowMode The workflow status. Set to &#x60;live&#x60; for inactive workflows or &#x60;draft&#x60; for draft workflows. Active workflows cannot be edited. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteWorkflowTransitionPropertyWithHttpInfo(Long transitionId, String key, String workflowName, String workflowMode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'transitionId' is set
        if (transitionId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'transitionId' when calling deleteWorkflowTransitionProperty");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'key' when calling deleteWorkflowTransitionProperty");
        }
        // verify the required parameter 'workflowName' is set
        if (workflowName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowName' when calling deleteWorkflowTransitionProperty");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("transitionId", transitionId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/transitions/{transitionId}/properties").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key", key));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowName", workflowName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowMode", workflowMode));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get workflow transition properties
     * Returns the properties on a workflow transition. Transition properties are used to change the behavior of a transition. For more information, see [Transition properties](https://confluence.atlassian.com/x/zIhKLg#Advancedworkflowconfiguration-transitionproperties) and [Workflow properties](https://confluence.atlassian.com/x/JYlKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - 200 response
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have admin permission
     * <p><b>404</b> - Returned if the workflow transition or property is not found.
     * @param transitionId The ID of the transition. To get the ID, view the workflow in text mode in the Jira administration console. The ID is shown next to the transition. (required)
     * @param workflowName The name of the workflow that the transition belongs to. (required)
     * @param includeReservedKeys Some properties with keys that have the *jira.* prefix are reserved, which means they are not editable. To include these properties in the results, set this parameter to *true*. (optional, default to false)
     * @param key The key of the property being returned, also known as the name of the property. If this parameter is not specified, all properties on the transition are returned. (optional)
     * @param workflowMode The workflow status. Set to *live* for active and inactive workflows, or *draft* for draft workflows. (optional, default to live)
     * @return WorkflowTransitionProperty
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowTransitionProperty getWorkflowTransitionProperties(Long transitionId, String workflowName, Boolean includeReservedKeys, String key, String workflowMode) throws RestClientException {
        return getWorkflowTransitionPropertiesWithHttpInfo(transitionId, workflowName, includeReservedKeys, key, workflowMode).getBody();
    }

    /**
     * Get workflow transition properties
     * Returns the properties on a workflow transition. Transition properties are used to change the behavior of a transition. For more information, see [Transition properties](https://confluence.atlassian.com/x/zIhKLg#Advancedworkflowconfiguration-transitionproperties) and [Workflow properties](https://confluence.atlassian.com/x/JYlKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - 200 response
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have admin permission
     * <p><b>404</b> - Returned if the workflow transition or property is not found.
     * @param transitionId The ID of the transition. To get the ID, view the workflow in text mode in the Jira administration console. The ID is shown next to the transition. (required)
     * @param workflowName The name of the workflow that the transition belongs to. (required)
     * @param includeReservedKeys Some properties with keys that have the *jira.* prefix are reserved, which means they are not editable. To include these properties in the results, set this parameter to *true*. (optional, default to false)
     * @param key The key of the property being returned, also known as the name of the property. If this parameter is not specified, all properties on the transition are returned. (optional)
     * @param workflowMode The workflow status. Set to *live* for active and inactive workflows, or *draft* for draft workflows. (optional, default to live)
     * @return ResponseEntity&lt;WorkflowTransitionProperty&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowTransitionProperty> getWorkflowTransitionPropertiesWithHttpInfo(Long transitionId, String workflowName, Boolean includeReservedKeys, String key, String workflowMode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'transitionId' is set
        if (transitionId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'transitionId' when calling getWorkflowTransitionProperties");
        }
        // verify the required parameter 'workflowName' is set
        if (workflowName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowName' when calling getWorkflowTransitionProperties");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("transitionId", transitionId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/transitions/{transitionId}/properties").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "includeReservedKeys", includeReservedKeys));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key", key));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowName", workflowName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowMode", workflowMode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowTransitionProperty> returnType = new ParameterizedTypeReference<WorkflowTransitionProperty>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update workflow transition property
     * Updates a workflow transition by changing the property value. Trying to update a property that does not exist results in a new property being added to the transition. Transition properties are used to change the behavior of a transition. For more information, see [Transition properties](https://confluence.atlassian.com/x/zIhKLg#Advancedworkflowconfiguration-transitionproperties) and [Workflow properties](https://confluence.atlassian.com/x/JYlKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - 200 response
     * <p><b>304</b> - Returned if no changes were made by the request. For example, attempting to update a property with its current value.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow transition is not found.
     * @param body  (required)
     * @param key The key of the property being updated, also known as the name of the property. Set this to the same value as the &#x60;key&#x60; defined in the request body. (required)
     * @param workflowName The name of the workflow that the transition belongs to. (required)
     * @param transitionId The ID of the transition. To get the ID, view the workflow in text mode in the Jira admin settings. The ID is shown next to the transition. (required)
     * @param workflowMode The workflow status. Set to &#x60;live&#x60; for inactive workflows or &#x60;draft&#x60; for draft workflows. Active workflows cannot be edited. (optional)
     * @return WorkflowTransitionProperty
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowTransitionProperty updateWorkflowTransitionProperty(Map<String, Object> body, String key, String workflowName, Long transitionId, String workflowMode) throws RestClientException {
        return updateWorkflowTransitionPropertyWithHttpInfo(body, key, workflowName, transitionId, workflowMode).getBody();
    }

    /**
     * Update workflow transition property
     * Updates a workflow transition by changing the property value. Trying to update a property that does not exist results in a new property being added to the transition. Transition properties are used to change the behavior of a transition. For more information, see [Transition properties](https://confluence.atlassian.com/x/zIhKLg#Advancedworkflowconfiguration-transitionproperties) and [Workflow properties](https://confluence.atlassian.com/x/JYlKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - 200 response
     * <p><b>304</b> - Returned if no changes were made by the request. For example, attempting to update a property with its current value.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if the workflow transition is not found.
     * @param body  (required)
     * @param key The key of the property being updated, also known as the name of the property. Set this to the same value as the &#x60;key&#x60; defined in the request body. (required)
     * @param workflowName The name of the workflow that the transition belongs to. (required)
     * @param transitionId The ID of the transition. To get the ID, view the workflow in text mode in the Jira admin settings. The ID is shown next to the transition. (required)
     * @param workflowMode The workflow status. Set to &#x60;live&#x60; for inactive workflows or &#x60;draft&#x60; for draft workflows. Active workflows cannot be edited. (optional)
     * @return ResponseEntity&lt;WorkflowTransitionProperty&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowTransitionProperty> updateWorkflowTransitionPropertyWithHttpInfo(Map<String, Object> body, String key, String workflowName, Long transitionId, String workflowMode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateWorkflowTransitionProperty");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'key' when calling updateWorkflowTransitionProperty");
        }
        // verify the required parameter 'workflowName' is set
        if (workflowName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowName' when calling updateWorkflowTransitionProperty");
        }
        // verify the required parameter 'transitionId' is set
        if (transitionId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'transitionId' when calling updateWorkflowTransitionProperty");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("transitionId", transitionId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/transitions/{transitionId}/properties").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key", key));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowName", workflowName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowMode", workflowMode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowTransitionProperty> returnType = new ParameterizedTypeReference<WorkflowTransitionProperty>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}