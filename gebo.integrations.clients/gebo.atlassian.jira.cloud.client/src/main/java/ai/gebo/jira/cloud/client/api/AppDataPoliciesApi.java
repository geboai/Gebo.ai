/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.api;

import ai.gebo.jira.cloud.client.invoker.ApiClient;

import ai.gebo.jira.cloud.client.model.ErrorCollection;
import ai.gebo.jira.cloud.client.model.ProjectDataPolicies;
import ai.gebo.jira.cloud.client.model.WorkspaceDataPolicy;

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
// AI generated comments
//@Component("ai.gebo.jira.cloud.client.api.AppDataPoliciesApi")
public class AppDataPoliciesApi {
    private ApiClient apiClient;

    /**
     * Default constructor.
     * Initializes a new instance of the AppDataPoliciesApi class with a default ApiClient.
     */
    public AppDataPoliciesApi() {
        this(new ApiClient());
    }

    /**
     * Constructor with ApiClient parameter.
     * Initializes a new instance of the AppDataPoliciesApi class using the provided ApiClient.
     * 
     * @param apiClient the ApiClient to be used by this API instance
     */
    //@Autowired
    public AppDataPoliciesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the ApiClient currently being used by this API.
     * 
     * @return the current ApiClient
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the ApiClient to be used by this API.
     * 
     * @param apiClient the ApiClient to set
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get data policy for projects
     * Returns data policies for the projects specified in the request.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid or includes invalid or not-permitted project identifiers.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the client is not authorized to make the request.
     * 
     * @param ids A list of project identifiers. This parameter accepts a comma-separated list. (optional)
     * @return ProjectDataPolicies
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ProjectDataPolicies getPolicies(String ids) throws RestClientException {
        return getPoliciesWithHttpInfo(ids).getBody();
    }

    /**
     * Get data policy for projects
     * Returns data policies for the projects specified in the request.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid or includes invalid or not-permitted project identifiers.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the client is not authorized to make the request.
     * 
     * @param ids A list of project identifiers. This parameter accepts a comma-separated list. (optional)
     * @return ResponseEntity&lt;ProjectDataPolicies&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectDataPolicies> getPoliciesWithHttpInfo(String ids) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/data-policy/project").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ids", ids));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<ProjectDataPolicies> returnType = new ParameterizedTypeReference<ProjectDataPolicies>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    /**
     * Get data policy for the workspace
     * Returns data policy for the workspace.
     * <p><b>200</b> - Returned if the request is successful
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the client is not authorized to make the request.
     * 
     * @return WorkspaceDataPolicy
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkspaceDataPolicy getPolicy() throws RestClientException {
        return getPolicyWithHttpInfo().getBody();
    }

    /**
     * Get data policy for the workspace
     * Returns data policy for the workspace.
     * <p><b>200</b> - Returned if the request is successful
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the client is not authorized to make the request.
     * 
     * @return ResponseEntity&lt;WorkspaceDataPolicy&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkspaceDataPolicy> getPolicyWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/data-policy").build().toUriString();
        
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

        ParameterizedTypeReference<WorkspaceDataPolicy> returnType = new ParameterizedTypeReference<WorkspaceDataPolicy>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}