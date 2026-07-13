package gebo.microservices.api.client.mcpclient.api;

import gebo.microservices.api.client.mcpclient.invoker.ApiClient;

import gebo.microservices.api.client.mcpclient.model.GObjectRefGProjectEndpoint;
import gebo.microservices.api.client.mcpclient.model.HasRunningJobs;
import gebo.microservices.api.client.mcpclient.model.OperationStatusGJobStatus;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-13T14:55:49.337582+02:00[Europe/Rome]")

public class JobLauncherControllerApi {
    private ApiClient apiClient;

     public JobLauncherControllerApi() {
        this(new ApiClient());
    }
    public JobLauncherControllerApi(ApiClient apiClient) {
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
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void abortJob(Object jobCode) throws RestClientException {
        abortJobWithHttpInfo(jobCode);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param jobCode  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> abortJobWithHttpInfo(Object jobCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'jobCode' is set
        if (jobCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'jobCode' when calling abortJob");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JobLauncherController/abortJob").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "jobCode", jobCode));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGJobStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGJobStatus createJob(GObjectRefGProjectEndpoint body) throws RestClientException {
        return createJobWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGJobStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGJobStatus> createJobWithHttpInfo(GObjectRefGProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createJob");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JobLauncherController/createJob").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGJobStatus> returnType = new ParameterizedTypeReference<OperationStatusGJobStatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return HasRunningJobs
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public HasRunningJobs getHasRunningJobs(GObjectRefGProjectEndpoint body) throws RestClientException {
        return getHasRunningJobsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;HasRunningJobs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HasRunningJobs> getHasRunningJobsWithHttpInfo(GObjectRefGProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getHasRunningJobs");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JobLauncherController/getHasRunningJobs").build().toUriString();
        
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

        ParameterizedTypeReference<HasRunningJobs> returnType = new ParameterizedTypeReference<HasRunningJobs>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
