package gebo.microservices.api.client.heimdall.api;

import gebo.microservices.api.client.heimdall.invoker.ApiClient;

import gebo.microservices.api.client.heimdall.model.StartWorkflowData;
import gebo.microservices.api.client.heimdall.model.UserChangePasswordWithTicket;
import gebo.microservices.api.client.heimdall.model.UserWorkFlowChangePasswordResponse;
import gebo.microservices.api.client.heimdall.model.UserWorkFlowStartResponse;
import gebo.microservices.api.client.heimdall.model.UserWorkflows;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-14T21:08:19.068930600+02:00[Europe/Rome]")

public class UserWorkflowsControllerApi {
    private ApiClient apiClient;

     public UserWorkflowsControllerApi() {
        this(new ApiClient());
    }
    public UserWorkflowsControllerApi(ApiClient apiClient) {
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
     * @return UserWorkflows
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserWorkflows getUserWorkflowsConfig() throws RestClientException {
        return getUserWorkflowsConfigWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;UserWorkflows&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserWorkflows> getUserWorkflowsConfigWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/public/UserWorkflowsController/getUserWorkflowsConfig").build().toUriString();
        
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

        ParameterizedTypeReference<UserWorkflows> returnType = new ParameterizedTypeReference<UserWorkflows>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return UserWorkFlowStartResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserWorkFlowStartResponse startUserWorkflow(StartWorkflowData body) throws RestClientException {
        return startUserWorkflowWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;UserWorkFlowStartResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserWorkFlowStartResponse> startUserWorkflowWithHttpInfo(StartWorkflowData body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling startUserWorkflow");
        }
        String path = UriComponentsBuilder.fromPath("/public/UserWorkflowsController/startUserWorkflow").build().toUriString();
        
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

        ParameterizedTypeReference<UserWorkFlowStartResponse> returnType = new ParameterizedTypeReference<UserWorkFlowStartResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return UserWorkFlowChangePasswordResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserWorkFlowChangePasswordResponse userChangePasswordWithTicket(UserChangePasswordWithTicket body) throws RestClientException {
        return userChangePasswordWithTicketWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;UserWorkFlowChangePasswordResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserWorkFlowChangePasswordResponse> userChangePasswordWithTicketWithHttpInfo(UserChangePasswordWithTicket body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling userChangePasswordWithTicket");
        }
        String path = UriComponentsBuilder.fromPath("/public/UserWorkflowsController/userChangePasswordWithTicket").build().toUriString();
        
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

        ParameterizedTypeReference<UserWorkFlowChangePasswordResponse> returnType = new ParameterizedTypeReference<UserWorkFlowChangePasswordResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
