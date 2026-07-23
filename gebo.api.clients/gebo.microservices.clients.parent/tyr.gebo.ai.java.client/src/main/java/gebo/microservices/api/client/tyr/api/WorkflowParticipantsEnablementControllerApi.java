package gebo.microservices.api.client.tyr.api;

import gebo.microservices.api.client.tyr.invoker.ApiClient;


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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-22T11:47:54.334477201+02:00[Europe/Rome]")

public class WorkflowParticipantsEnablementControllerApi {
    private ApiClient apiClient;

     public WorkflowParticipantsEnablementControllerApi() {
        this(new ApiClient());
    }
    public WorkflowParticipantsEnablementControllerApi(ApiClient apiClient) {
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
     * @param workflowType  (required)
     * @param workflowId  (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object enabledSteps(Object workflowType, Object workflowId) throws RestClientException {
        return enabledStepsWithHttpInfo(workflowType, workflowId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param workflowType  (required)
     * @param workflowId  (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> enabledStepsWithHttpInfo(Object workflowType, Object workflowId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'workflowType' is set
        if (workflowType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowType' when calling enabledSteps");
        }
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowId' when calling enabledSteps");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/WorkflowParticipantsEnablementController/enabledSteps").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowType", workflowType));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowId", workflowId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
