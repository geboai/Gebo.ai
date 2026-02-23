package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.monolithic.api.client.model.DeepSearchDataSourceResponse;
import ai.gebo.monolithic.api.client.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.monolithic.api.client.model.DeepSearchRequest;
import ai.gebo.monolithic.api.client.model.DeepSearchResponse;
import ai.gebo.monolithic.api.client.model.DeepSearchUISettings;
import ai.gebo.monolithic.api.client.model.GBaseObject;
import ai.gebo.monolithic.api.client.model.GeboChatRequest;
import ai.gebo.monolithic.api.client.model.PageDeepSearchDocumentAnalisysResultStep;
import ai.gebo.monolithic.api.client.model.PageDeepSearchRequest;
import ai.gebo.monolithic.api.client.model.ServerSentEventString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-02-23T08:37:45.114718+01:00[Europe/Rome]")

public class GeboDeepSearchControllerApi {
    private ApiClient apiClient;

     public GeboDeepSearchControllerApi() {
        this(new ApiClient());
    }
    public GeboDeepSearchControllerApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteDeepSearch(DeepSearchRequest body) throws RestClientException {
        deleteDeepSearchWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteDeepSearchWithHttpInfo(DeepSearchRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteDeepSearch");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/deleteDeepSearch").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return DeepSearchResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DeepSearchResponse doDeepSearch(DeepSearchRequest body) throws RestClientException {
        return doDeepSearchWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;DeepSearchResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DeepSearchResponse> doDeepSearchWithHttpInfo(DeepSearchRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling doDeepSearch");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/doDeepSearch").build().toUriString();
        
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

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<DeepSearchResponse> returnType = new ParameterizedTypeReference<DeepSearchResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GBaseObject&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GBaseObject> getDeepSearchDataSources() throws RestClientException {
        return getDeepSearchDataSourcesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GBaseObject&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GBaseObject>> getDeepSearchDataSourcesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getDeepSearchDataSources").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GBaseObject>> returnType = new ParameterizedTypeReference<List<GBaseObject>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return Long
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Long getDeepSearchDocumentsCount(String deepSearchCode) throws RestClientException {
        return getDeepSearchDocumentsCountWithHttpInfo(deepSearchCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return ResponseEntity&lt;Long&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Long> getDeepSearchDocumentsCountWithHttpInfo(String deepSearchCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'deepSearchCode' is set
        if (deepSearchCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'deepSearchCode' when calling getDeepSearchDocumentsCount");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getDeepSearchDocumentsCount").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "deepSearchCode", deepSearchCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Long> returnType = new ParameterizedTypeReference<Long>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return DeepSearchUISettings
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DeepSearchUISettings getDeepSearchUISettings() throws RestClientException {
        return getDeepSearchUISettingsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;DeepSearchUISettings&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DeepSearchUISettings> getDeepSearchUISettingsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getDeepSearchUISettings").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<DeepSearchUISettings> returnType = new ParameterizedTypeReference<DeepSearchUISettings>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return DeepSearchRequest
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DeepSearchRequest getMyDeepSearchById(String deepSearchCode) throws RestClientException {
        return getMyDeepSearchByIdWithHttpInfo(deepSearchCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return ResponseEntity&lt;DeepSearchRequest&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DeepSearchRequest> getMyDeepSearchByIdWithHttpInfo(String deepSearchCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'deepSearchCode' is set
        if (deepSearchCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'deepSearchCode' when calling getMyDeepSearchById");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getMyDeepSearchById").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "deepSearchCode", deepSearchCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<DeepSearchRequest> returnType = new ParameterizedTypeReference<DeepSearchRequest>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return List&lt;DeepSearchDataSourceDocumentResult&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<DeepSearchDataSourceDocumentResult> getMyDeepSearchDataSourceDocumentResultsByRequestCode(String deepSearchCode) throws RestClientException {
        return getMyDeepSearchDataSourceDocumentResultsByRequestCodeWithHttpInfo(deepSearchCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return ResponseEntity&lt;List&lt;DeepSearchDataSourceDocumentResult&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<DeepSearchDataSourceDocumentResult>> getMyDeepSearchDataSourceDocumentResultsByRequestCodeWithHttpInfo(String deepSearchCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'deepSearchCode' is set
        if (deepSearchCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'deepSearchCode' when calling getMyDeepSearchDataSourceDocumentResultsByRequestCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getMyDeepSearchDataSourceDocumentResultsByRequestCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "deepSearchCode", deepSearchCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<DeepSearchDataSourceDocumentResult>> returnType = new ParameterizedTypeReference<List<DeepSearchDataSourceDocumentResult>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return List&lt;DeepSearchDataSourceResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<DeepSearchDataSourceResponse> getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode(String deepSearchCode) throws RestClientException {
        return getMyDeepSearchDeepSearchDataSourceResponsesByRequestCodeWithHttpInfo(deepSearchCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return ResponseEntity&lt;List&lt;DeepSearchDataSourceResponse&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<DeepSearchDataSourceResponse>> getMyDeepSearchDeepSearchDataSourceResponsesByRequestCodeWithHttpInfo(String deepSearchCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'deepSearchCode' is set
        if (deepSearchCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'deepSearchCode' when calling getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "deepSearchCode", deepSearchCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<DeepSearchDataSourceResponse>> returnType = new ParameterizedTypeReference<List<DeepSearchDataSourceResponse>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return DeepSearchResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DeepSearchResponse getMyDeepSearchResponseByRequestCode(String deepSearchCode) throws RestClientException {
        return getMyDeepSearchResponseByRequestCodeWithHttpInfo(deepSearchCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return ResponseEntity&lt;DeepSearchResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DeepSearchResponse> getMyDeepSearchResponseByRequestCodeWithHttpInfo(String deepSearchCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'deepSearchCode' is set
        if (deepSearchCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'deepSearchCode' when calling getMyDeepSearchResponseByRequestCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getMyDeepSearchResponseByRequestCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "deepSearchCode", deepSearchCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<DeepSearchResponse> returnType = new ParameterizedTypeReference<DeepSearchResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;DeepSearchRequest&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<DeepSearchRequest> getMyDeepSearches() throws RestClientException {
        return getMyDeepSearchesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;DeepSearchRequest&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<DeepSearchRequest>> getMyDeepSearchesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getMyDeepSearches").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<DeepSearchRequest>> returnType = new ParameterizedTypeReference<List<DeepSearchRequest>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param page  (required)
     * @param pageSize  (required)
     * @return PageDeepSearchRequest
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageDeepSearchRequest getMyDeepSearchesPaged(Integer page, Integer pageSize) throws RestClientException {
        return getMyDeepSearchesPagedWithHttpInfo(page, pageSize).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param page  (required)
     * @param pageSize  (required)
     * @return ResponseEntity&lt;PageDeepSearchRequest&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageDeepSearchRequest> getMyDeepSearchesPagedWithHttpInfo(Integer page, Integer pageSize) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'page' is set
        if (page == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'page' when calling getMyDeepSearchesPaged");
        }
        // verify the required parameter 'pageSize' is set
        if (pageSize == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pageSize' when calling getMyDeepSearchesPaged");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getMyDeepSearchesPaged").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PageDeepSearchRequest> returnType = new ParameterizedTypeReference<PageDeepSearchRequest>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return List&lt;DeepSearchDocumentAnalisysResultStep&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<DeepSearchDocumentAnalisysResultStep> getMyDeepSearchesSteps(String deepSearchCode) throws RestClientException {
        return getMyDeepSearchesStepsWithHttpInfo(deepSearchCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return ResponseEntity&lt;List&lt;DeepSearchDocumentAnalisysResultStep&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<DeepSearchDocumentAnalisysResultStep>> getMyDeepSearchesStepsWithHttpInfo(String deepSearchCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'deepSearchCode' is set
        if (deepSearchCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'deepSearchCode' when calling getMyDeepSearchesSteps");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getMyDeepSearchesSteps").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "deepSearchCode", deepSearchCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<DeepSearchDocumentAnalisysResultStep>> returnType = new ParameterizedTypeReference<List<DeepSearchDocumentAnalisysResultStep>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @param page  (required)
     * @param pageSize  (required)
     * @return PageDeepSearchDocumentAnalisysResultStep
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageDeepSearchDocumentAnalisysResultStep getMyDeepSearchesStepsPaged(String deepSearchCode, Integer page, Integer pageSize) throws RestClientException {
        return getMyDeepSearchesStepsPagedWithHttpInfo(deepSearchCode, page, pageSize).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @param page  (required)
     * @param pageSize  (required)
     * @return ResponseEntity&lt;PageDeepSearchDocumentAnalisysResultStep&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageDeepSearchDocumentAnalisysResultStep> getMyDeepSearchesStepsPagedWithHttpInfo(String deepSearchCode, Integer page, Integer pageSize) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'deepSearchCode' is set
        if (deepSearchCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'deepSearchCode' when calling getMyDeepSearchesStepsPaged");
        }
        // verify the required parameter 'page' is set
        if (page == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'page' when calling getMyDeepSearchesStepsPaged");
        }
        // verify the required parameter 'pageSize' is set
        if (pageSize == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pageSize' when calling getMyDeepSearchesStepsPaged");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/getMyDeepSearchesStepsPaged").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "deepSearchCode", deepSearchCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PageDeepSearchDocumentAnalisysResultStep> returnType = new ParameterizedTypeReference<PageDeepSearchDocumentAnalisysResultStep>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void stopDeepSearch(String deepSearchCode) throws RestClientException {
        stopDeepSearchWithHttpInfo(deepSearchCode);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param deepSearchCode  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> stopDeepSearchWithHttpInfo(String deepSearchCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'deepSearchCode' is set
        if (deepSearchCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'deepSearchCode' when calling stopDeepSearch");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/stopDeepSearch").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "deepSearchCode", deepSearchCode));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;ServerSentEventString&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ServerSentEventString> streamDeepSearch(DeepSearchRequest body) throws RestClientException {
        return streamDeepSearchWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;ServerSentEventString&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ServerSentEventString>> streamDeepSearchWithHttpInfo(DeepSearchRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling streamDeepSearch");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/streamDeepSearch").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "text/event-stream"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<ServerSentEventString>> returnType = new ParameterizedTypeReference<List<ServerSentEventString>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;ServerSentEventString&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ServerSentEventString> streamDeepSearchWithChatContext(GeboChatRequest body) throws RestClientException {
        return streamDeepSearchWithChatContextWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;ServerSentEventString&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ServerSentEventString>> streamDeepSearchWithChatContextWithHttpInfo(GeboChatRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling streamDeepSearchWithChatContext");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDeepSearchController/streamDeepSearchWithChatContext").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "text/event-stream"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<ServerSentEventString>> returnType = new ParameterizedTypeReference<List<ServerSentEventString>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
