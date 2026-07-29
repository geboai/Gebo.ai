package gebo.microservices.api.client.tyr.api;

import gebo.microservices.api.client.tyr.invoker.ApiClient;

import gebo.microservices.api.client.tyr.model.WorkflowStatsDrillDownLevel;
import gebo.microservices.api.client.tyr.model.WorkflowStatsDrillDownResult;

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

public class WorkflowStatsAdminLevelControllerApi {
    private ApiClient apiClient;

     public WorkflowStatsAdminLevelControllerApi() {
        this(new ApiClient());
    }
    public WorkflowStatsAdminLevelControllerApi(ApiClient apiClient) {
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
     * @return WorkflowStatsDrillDownResult
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowStatsDrillDownResult workflowDrillDown(WorkflowStatsDrillDownLevel body) throws RestClientException {
        return workflowDrillDownWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;WorkflowStatsDrillDownResult&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowStatsDrillDownResult> workflowDrillDownWithHttpInfo(WorkflowStatsDrillDownLevel body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling workflowDrillDown");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/WorkflowStatsAdminLevelController/drillDown").build().toUriString();
        
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

        ParameterizedTypeReference<WorkflowStatsDrillDownResult> returnType = new ParameterizedTypeReference<WorkflowStatsDrillDownResult>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
