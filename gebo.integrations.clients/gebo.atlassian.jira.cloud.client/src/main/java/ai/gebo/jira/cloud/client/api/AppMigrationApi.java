/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import ai.gebo.jira.cloud.client.model.ConnectCustomFieldValues;
import ai.gebo.jira.cloud.client.model.EntityPropertyDetails;
import ai.gebo.jira.cloud.client.model.WorkflowRulesSearch;
import ai.gebo.jira.cloud.client.model.WorkflowRulesSearchDetails;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
// @Component("ai.gebo.jira.cloud.client.api.AppMigrationApi")
/**
 * AI generated comments
 * The AppMigrationApi class provides methods for handling app migration processes such as updating custom field values, entity properties, and retrieving workflow transition rule configurations for Connect apps.
 */
public class AppMigrationApi {
    private ApiClient apiClient;

    /**
     * Default constructor that initializes the AppMigrationApi with a new ApiClient instance.
     */
    public AppMigrationApi() {
        this(new ApiClient());
    }

    //@Autowired
    /**
     * Constructor that accepts an ApiClient instance to allow for dependency injection.
     * @param apiClient The ApiClient instance to be used by this API.
     */
    public AppMigrationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Returns the current ApiClient instance.
     * @return the current ApiClient instance.
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the ApiClient for this instance.
     * @param apiClient The ApiClient instance to be set.
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Bulk update custom field value
     * Updates the value of a custom field added by Connect apps on one or more issues. The values of up to 200 custom fields can be updated.  **[Permissions](#permissions) required:** Only Connect apps can make this request
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>403</b> - Returned if: * the transfer ID is not found. * the authorisation credentials are incorrect or missing.
     * @param body  (required)
     * @param atlassianTransferId The ID of the transfer. (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object appIssueFieldValueUpdateResourceUpdateIssueFieldsPut(ConnectCustomFieldValues body, UUID atlassianTransferId) throws RestClientException {
        return appIssueFieldValueUpdateResourceUpdateIssueFieldsPutWithHttpInfo(body, atlassianTransferId).getBody();
    }

    /**
     * Bulk update custom field value
     * Updates the value of a custom field added by Connect apps on one or more issues. The values of up to 200 custom fields can be updated.  **[Permissions](#permissions) required:** Only Connect apps can make this request
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>403</b> - Returned if: * the transfer ID is not found. * the authorisation credentials are incorrect or missing.
     * @param body  (required)
     * @param atlassianTransferId The ID of the transfer. (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> appIssueFieldValueUpdateResourceUpdateIssueFieldsPutWithHttpInfo(ConnectCustomFieldValues body, UUID atlassianTransferId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling appIssueFieldValueUpdateResourceUpdateIssueFieldsPut");
        }
        // verify the required parameter 'atlassianTransferId' is set
        if (atlassianTransferId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'atlassianTransferId' when calling appIssueFieldValueUpdateResourceUpdateIssueFieldsPut");
        }
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/migration/field").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (atlassianTransferId != null)
            headerParams.add("Atlassian-Transfer-Id", apiClient.parameterToString(atlassianTransferId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk update entity properties
     * Updates the values of multiple entity properties for an object, up to 50 updates per request. This operation is for use by Connect apps during app migration.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>403</b> - Returned if the authorisation credentials are incorrect or missing.
     * @param body  (required)
     * @param atlassianTransferId The app migration transfer ID. (required)
     * @param entityType The type indicating the object that contains the entity properties. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void migrationResourceUpdateEntityPropertiesValuePut(List<EntityPropertyDetails> body, UUID atlassianTransferId, String entityType) throws RestClientException {
        migrationResourceUpdateEntityPropertiesValuePutWithHttpInfo(body, atlassianTransferId, entityType);
    }

    /**
     * Bulk update entity properties
     * Updates the values of multiple entity properties for an object, up to 50 updates per request. This operation is for use by Connect apps during app migration.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>403</b> - Returned if the authorisation credentials are incorrect or missing.
     * @param body  (required)
     * @param atlassianTransferId The app migration transfer ID. (required)
     * @param entityType The type indicating the object that contains the entity properties. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> migrationResourceUpdateEntityPropertiesValuePutWithHttpInfo(List<EntityPropertyDetails> body, UUID atlassianTransferId, String entityType) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling migrationResourceUpdateEntityPropertiesValuePut");
        }
        // verify the required parameter 'atlassianTransferId' is set
        if (atlassianTransferId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'atlassianTransferId' when calling migrationResourceUpdateEntityPropertiesValuePut");
        }
        // verify the required parameter 'entityType' is set
        if (entityType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'entityType' when calling migrationResourceUpdateEntityPropertiesValuePut");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("entityType", entityType);
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/migration/properties/{entityType}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (atlassianTransferId != null)
            headerParams.add("Atlassian-Transfer-Id", apiClient.parameterToString(atlassianTransferId));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get workflow transition rule configurations
     * Returns configurations for workflow transition rules migrated from server to cloud and owned by the calling Connect app.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>403</b> - Returned if the authorisation credentials are incorrect or missing.
     * @param body  (required)
     * @param atlassianTransferId The app migration transfer ID. (required)
     * @return WorkflowRulesSearchDetails
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowRulesSearchDetails migrationResourceWorkflowRuleSearchPost(WorkflowRulesSearch body, UUID atlassianTransferId) throws RestClientException {
        return migrationResourceWorkflowRuleSearchPostWithHttpInfo(body, atlassianTransferId).getBody();
    }

    /**
     * Get workflow transition rule configurations
     * Returns configurations for workflow transition rules migrated from server to cloud and owned by the calling Connect app.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>403</b> - Returned if the authorisation credentials are incorrect or missing.
     * @param body  (required)
     * @param atlassianTransferId The app migration transfer ID. (required)
     * @return ResponseEntity&lt;WorkflowRulesSearchDetails&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowRulesSearchDetails> migrationResourceWorkflowRuleSearchPostWithHttpInfo(WorkflowRulesSearch body, UUID atlassianTransferId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling migrationResourceWorkflowRuleSearchPost");
        }
        // verify the required parameter 'atlassianTransferId' is set
        if (atlassianTransferId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'atlassianTransferId' when calling migrationResourceWorkflowRuleSearchPost");
        }
        String path = UriComponentsBuilder.fromPath("/rest/atlassian-connect/1/migration/workflow/rule/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (atlassianTransferId != null)
            headerParams.add("Atlassian-Transfer-Id", apiClient.parameterToString(atlassianTransferId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<WorkflowRulesSearchDetails> returnType = new ParameterizedTypeReference<WorkflowRulesSearchDetails>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}