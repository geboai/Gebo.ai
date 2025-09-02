/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * API client for Jira Cloud JQL Functions Apps operations.
 * AI generated comments
 * 
 * This class provides methods to interact with Jira's JQL functions precomputation APIs,
 * allowing apps to retrieve, search for, and update precomputation values used in JQL functions.
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
import ai.gebo.jira.cloud.client.model.JqlFunctionPrecomputationGetByIdRequest;
import ai.gebo.jira.cloud.client.model.JqlFunctionPrecomputationGetByIdResponse;
import ai.gebo.jira.cloud.client.model.JqlFunctionPrecomputationUpdateRequestBean;
import ai.gebo.jira.cloud.client.model.JqlFunctionPrecomputationUpdateResponse;
import ai.gebo.jira.cloud.client.model.PageBean2JqlFunctionPrecomputationBean;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.JqlFunctionsAppsApi")
public class JqlFunctionsAppsApi {
    private ApiClient apiClient;

    /**
     * Constructor for JqlFunctionsAppsApi that takes an ApiClient instance.
     * 
     * @param apiClient The API client to use for making requests
     */
    public JqlFunctionsAppsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Returns the current API client instance.
     * 
     * @return ApiClient instance being used by this API
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the API client to be used for requests.
     * 
     * @param apiClient The API client to use
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get precomputations (apps)
     * Returns the list of a function&#x27;s precomputations along with information about when they were created, updated, and last used. Each precomputation has a &#x60;value&#x60; \\- the JQL fragment to replace the custom function clause with.  **[Permissions](#permissions) required:** This API is only accessible to apps and apps can only inspect their own functions.  The new &#x60;read:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the request is not authenticated as the app that provided the function.
     * <p><b>404</b> - Returned if the function is not found.
     * @param functionKey The function key in format:   *  Forge: &#x60;ari:cloud:ecosystem::extension/[App ID]/[Environment ID]/static/[Function key from manifest]&#x60;  *  Connect: &#x60;[App key]__[Module key]&#x60; (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 100)
     * @param orderBy [Order](#ordering) the results by a field:   *  &#x60;functionKey&#x60; Sorts by the functionKey.  *  &#x60;used&#x60; Sorts by the used timestamp.  *  &#x60;created&#x60; Sorts by the created timestamp.  *  &#x60;updated&#x60; Sorts by the updated timestamp. (optional)
     * @return PageBean2JqlFunctionPrecomputationBean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBean2JqlFunctionPrecomputationBean getPrecomputations(List<String> functionKey, Long startAt, Integer maxResults, String orderBy) throws RestClientException {
        return getPrecomputationsWithHttpInfo(functionKey, startAt, maxResults, orderBy).getBody();
    }

    /**
     * Get precomputations (apps)
     * Returns the list of a function&#x27;s precomputations along with information about when they were created, updated, and last used. Each precomputation has a &#x60;value&#x60; \\- the JQL fragment to replace the custom function clause with.  **[Permissions](#permissions) required:** This API is only accessible to apps and apps can only inspect their own functions.  The new &#x60;read:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the request is not authenticated as the app that provided the function.
     * <p><b>404</b> - Returned if the function is not found.
     * @param functionKey The function key in format:   *  Forge: &#x60;ari:cloud:ecosystem::extension/[App ID]/[Environment ID]/static/[Function key from manifest]&#x60;  *  Connect: &#x60;[App key]__[Module key]&#x60; (optional)
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 100)
     * @param orderBy [Order](#ordering) the results by a field:   *  &#x60;functionKey&#x60; Sorts by the functionKey.  *  &#x60;used&#x60; Sorts by the used timestamp.  *  &#x60;created&#x60; Sorts by the created timestamp.  *  &#x60;updated&#x60; Sorts by the updated timestamp. (optional)
     * @return ResponseEntity&lt;PageBean2JqlFunctionPrecomputationBean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBean2JqlFunctionPrecomputationBean> getPrecomputationsWithHttpInfo(List<String> functionKey, Long startAt, Integer maxResults, String orderBy) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/jql/function/computation").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "functionKey", functionKey));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "orderBy", orderBy));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBean2JqlFunctionPrecomputationBean> returnType = new ParameterizedTypeReference<PageBean2JqlFunctionPrecomputationBean>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get precomputations by ID (apps)
     * Returns function precomputations by IDs, along with information about when they were created, updated, and last used. Each precomputation has a &#x60;value&#x60; \\- the JQL fragment to replace the custom function clause with.  **[Permissions](#permissions) required:** This API is only accessible to apps and apps can only inspect their own functions.  The new &#x60;read:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the request is not authenticated as the app that provided the function.
     * <p><b>404</b> - Returned if the function is not found.
     * @param body  (required)
     * @param orderBy [Order](#ordering) the results by a field:   *  &#x60;functionKey&#x60; Sorts by the functionKey.  *  &#x60;used&#x60; Sorts by the used timestamp.  *  &#x60;created&#x60; Sorts by the created timestamp.  *  &#x60;updated&#x60; Sorts by the updated timestamp. (optional)
     * @return JqlFunctionPrecomputationGetByIdResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JqlFunctionPrecomputationGetByIdResponse getPrecomputationsByID(JqlFunctionPrecomputationGetByIdRequest body, String orderBy) throws RestClientException {
        return getPrecomputationsByIDWithHttpInfo(body, orderBy).getBody();
    }

    /**
     * Get precomputations by ID (apps)
     * Returns function precomputations by IDs, along with information about when they were created, updated, and last used. Each precomputation has a &#x60;value&#x60; \\- the JQL fragment to replace the custom function clause with.  **[Permissions](#permissions) required:** This API is only accessible to apps and apps can only inspect their own functions.  The new &#x60;read:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the request is not authenticated as the app that provided the function.
     * <p><b>404</b> - Returned if the function is not found.
     * @param body  (required)
     * @param orderBy [Order](#ordering) the results by a field:   *  &#x60;functionKey&#x60; Sorts by the functionKey.  *  &#x60;used&#x60; Sorts by the used timestamp.  *  &#x60;created&#x60; Sorts by the created timestamp.  *  &#x60;updated&#x60; Sorts by the updated timestamp. (optional)
     * @return ResponseEntity&lt;JqlFunctionPrecomputationGetByIdResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JqlFunctionPrecomputationGetByIdResponse> getPrecomputationsByIDWithHttpInfo(JqlFunctionPrecomputationGetByIdRequest body, String orderBy) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getPrecomputationsByID");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/jql/function/computation/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "orderBy", orderBy));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<JqlFunctionPrecomputationGetByIdResponse> returnType = new ParameterizedTypeReference<JqlFunctionPrecomputationGetByIdResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Update precomputations (apps)
     * Update the precomputation value of a function created by a Forge/Connect app.  **[Permissions](#permissions) required:** An API for apps to update their own precomputations.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>200</b> - 200 response
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>403</b> - Returned if the request is not authenticated as the app that provided the function.
     * <p><b>404</b> - Returned if the function is not found.
     * @param body  (required)
     * @param skipNotFoundPrecomputations  (optional, default to false)
     * @return JqlFunctionPrecomputationUpdateResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JqlFunctionPrecomputationUpdateResponse updatePrecomputations(JqlFunctionPrecomputationUpdateRequestBean body, Boolean skipNotFoundPrecomputations) throws RestClientException {
        return updatePrecomputationsWithHttpInfo(body, skipNotFoundPrecomputations).getBody();
    }

    /**
     * Update precomputations (apps)
     * Update the precomputation value of a function created by a Forge/Connect app.  **[Permissions](#permissions) required:** An API for apps to update their own precomputations.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.
     * <p><b>200</b> - 200 response
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>403</b> - Returned if the request is not authenticated as the app that provided the function.
     * <p><b>404</b> - Returned if the function is not found.
     * @param body  (required)
     * @param skipNotFoundPrecomputations  (optional, default to false)
     * @return ResponseEntity&lt;JqlFunctionPrecomputationUpdateResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JqlFunctionPrecomputationUpdateResponse> updatePrecomputationsWithHttpInfo(JqlFunctionPrecomputationUpdateRequestBean body, Boolean skipNotFoundPrecomputations) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updatePrecomputations");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/jql/function/computation").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "skipNotFoundPrecomputations", skipNotFoundPrecomputations));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<JqlFunctionPrecomputationUpdateResponse> returnType = new ParameterizedTypeReference<JqlFunctionPrecomputationUpdateResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}