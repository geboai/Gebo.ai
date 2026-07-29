package gebo.microservices.api.client.tyr.api;

import gebo.microservices.api.client.tyr.invoker.ApiClient;

import gebo.microservices.api.client.tyr.model.GJobStatus;
import gebo.microservices.api.client.tyr.model.JobSummary;
import gebo.microservices.api.client.tyr.model.JobsEntriesForProjectEndpointFilter;
import gebo.microservices.api.client.tyr.model.PageGJobStatusItem;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-28T18:11:41.737866346+02:00[Europe/Rome]")

public class JobStatusControllerApi {
    private ApiClient apiClient;

     public JobStatusControllerApi() {
        this(new ApiClient());
    }
    public JobStatusControllerApi(ApiClient apiClient) {
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
     * @param jobCode  (required)
     * @return GJobStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GJobStatus getJobStatus(Object jobCode) throws RestClientException {
        return getJobStatusWithHttpInfo(jobCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param jobCode  (required)
     * @return ResponseEntity&lt;GJobStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GJobStatus> getJobStatusWithHttpInfo(Object jobCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'jobCode' is set
        if (jobCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'jobCode' when calling getJobStatus");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JobStatusController/getJobStatus").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "jobCode", jobCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GJobStatus> returnType = new ParameterizedTypeReference<GJobStatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param jobCode  (required)
     * @return JobSummary
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JobSummary getJobSummary(Object jobCode) throws RestClientException {
        return getJobSummaryWithHttpInfo(jobCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param jobCode  (required)
     * @return ResponseEntity&lt;JobSummary&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JobSummary> getJobSummaryWithHttpInfo(Object jobCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'jobCode' is set
        if (jobCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'jobCode' when calling getJobSummary");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JobStatusController/getJobSummary").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "jobCode", jobCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<JobSummary> returnType = new ParameterizedTypeReference<JobSummary>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PageGJobStatusItem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageGJobStatusItem getJobsEntriesForProjectEndpoint(JobsEntriesForProjectEndpointFilter body) throws RestClientException {
        return getJobsEntriesForProjectEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PageGJobStatusItem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageGJobStatusItem> getJobsEntriesForProjectEndpointWithHttpInfo(JobsEntriesForProjectEndpointFilter body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getJobsEntriesForProjectEndpoint");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JobStatusController/getJobsEntriesForProjectEndpoint").build().toUriString();
        
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

        ParameterizedTypeReference<PageGJobStatusItem> returnType = new ParameterizedTypeReference<PageGJobStatusItem>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
