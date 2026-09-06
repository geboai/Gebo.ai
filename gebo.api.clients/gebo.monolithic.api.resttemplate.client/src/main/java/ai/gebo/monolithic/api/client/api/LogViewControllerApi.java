package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GetJobMessagesParam;
import ai.gebo.monolithic.api.client.model.JobsEntriesForClassNameFilter;
import ai.gebo.monolithic.api.client.model.JobsEntriesForJobType;
import ai.gebo.monolithic.api.client.model.JobsEntriesForProjectEndpointFilter;
import ai.gebo.monolithic.api.client.model.PagedModelGJobStatusItem;
import ai.gebo.monolithic.api.client.model.PagedModelGUserMessage;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-09-06T14:48:17.016141500+02:00[Europe/Rome]")

public class LogViewControllerApi {
    private ApiClient apiClient;

     public LogViewControllerApi() {
        this(new ApiClient());
    }
    public LogViewControllerApi(ApiClient apiClient) {
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
    public void deleteJobStatus(List<String> body) throws RestClientException {
        deleteJobStatusWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteJobStatusWithHttpInfo(List<String> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteJobStatus");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/LogViewController/deleteJobStatus").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PagedModelGUserMessage
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PagedModelGUserMessage getJobMessagesPaged(GetJobMessagesParam body) throws RestClientException {
        return getJobMessagesPagedWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PagedModelGUserMessage&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PagedModelGUserMessage> getJobMessagesPagedWithHttpInfo(GetJobMessagesParam body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getJobMessagesPaged");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/LogViewController/getJobMessagesPaged").build().toUriString();
        
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

        ParameterizedTypeReference<PagedModelGUserMessage> returnType = new ParameterizedTypeReference<PagedModelGUserMessage>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PagedModelGJobStatusItem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PagedModelGJobStatusItem getJobsEntriesForClassName(JobsEntriesForClassNameFilter body) throws RestClientException {
        return getJobsEntriesForClassNameWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PagedModelGJobStatusItem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PagedModelGJobStatusItem> getJobsEntriesForClassNameWithHttpInfo(JobsEntriesForClassNameFilter body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getJobsEntriesForClassName");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/LogViewController/getJobsEntriesForClassName").build().toUriString();
        
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

        ParameterizedTypeReference<PagedModelGJobStatusItem> returnType = new ParameterizedTypeReference<PagedModelGJobStatusItem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PagedModelGJobStatusItem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PagedModelGJobStatusItem getJobsEntriesForJobType(JobsEntriesForJobType body) throws RestClientException {
        return getJobsEntriesForJobTypeWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PagedModelGJobStatusItem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PagedModelGJobStatusItem> getJobsEntriesForJobTypeWithHttpInfo(JobsEntriesForJobType body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getJobsEntriesForJobType");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/LogViewController/getJobsEntriesForJobType").build().toUriString();
        
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

        ParameterizedTypeReference<PagedModelGJobStatusItem> returnType = new ParameterizedTypeReference<PagedModelGJobStatusItem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PagedModelGJobStatusItem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PagedModelGJobStatusItem getJobsEntriesForProjectEndpoint(JobsEntriesForProjectEndpointFilter body) throws RestClientException {
        return getJobsEntriesForProjectEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PagedModelGJobStatusItem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PagedModelGJobStatusItem> getJobsEntriesForProjectEndpointWithHttpInfo(JobsEntriesForProjectEndpointFilter body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getJobsEntriesForProjectEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/LogViewController/getJobsEntriesForProjectEndpoint").build().toUriString();
        
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

        ParameterizedTypeReference<PagedModelGJobStatusItem> returnType = new ParameterizedTypeReference<PagedModelGJobStatusItem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
