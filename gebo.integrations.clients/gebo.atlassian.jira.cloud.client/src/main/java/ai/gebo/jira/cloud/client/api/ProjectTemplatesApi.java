/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * API client for working with Jira project templates.
 * AI generated comments
 */
package ai.gebo.jira.cloud.client.api;

import java.util.List;

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
import ai.gebo.jira.cloud.client.model.ProjectCustomTemplateCreateRequestDTO;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

/**
 * API client for working with Jira project templates.
 * Provides methods to create projects based on custom templates.
 */
public class ProjectTemplatesApi {
    /**
     * The API client instance used for making HTTP requests
     */
    private ApiClient apiClient;

    /**
     * Constructor for ProjectTemplatesApi using a specific ApiClient
     * 
     * @param apiClient The ApiClient instance to use for API calls
     */
    public ProjectTemplatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the ApiClient instance used by this API
     * 
     * @return The ApiClient instance
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the ApiClient instance to be used by this API
     * 
     * @param apiClient The ApiClient instance to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create custom project
     * Creates a project based on a custom template provided in the request.  The request body should contain the project details and the capabilities that comprise the project:   *  &#x60;details&#x60; \\- represents the project details settings  *  &#x60;template&#x60; \\- represents a list of capabilities responsible for creating specific parts of a project  A capability is defined as a unit of configuration for the project you want to create.  This operation is:   *  [asynchronous](#async). Follow the &#x60;Location&#x60; link in the response header to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  ***Note: This API is only supported for Jira Enterprise edition.***
     * <p><b>303</b> - The project creation task has been queued for execution
     * @param body The JSON payload containing the project details and capabilities (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void createProjectWithCustomTemplate(ProjectCustomTemplateCreateRequestDTO body) throws RestClientException {
        createProjectWithCustomTemplateWithHttpInfo(body);
    }

    /**
     * Create custom project
     * Creates a project based on a custom template provided in the request.  The request body should contain the project details and the capabilities that comprise the project:   *  &#x60;details&#x60; \\- represents the project details settings  *  &#x60;template&#x60; \\- represents a list of capabilities responsible for creating specific parts of a project  A capability is defined as a unit of configuration for the project you want to create.  This operation is:   *  [asynchronous](#async). Follow the &#x60;Location&#x60; link in the response header to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  ***Note: This API is only supported for Jira Enterprise edition.***
     * <p><b>303</b> - The project creation task has been queued for execution
     * @param body The JSON payload containing the project details and capabilities (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> createProjectWithCustomTemplateWithHttpInfo(ProjectCustomTemplateCreateRequestDTO body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createProjectWithCustomTemplate");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/project-template").build().toUriString();
        
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

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}