package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;

import gebo.microservices.api.client.brain.model.AgenticChatDefaultNetworkInfo;
import gebo.microservices.api.client.brain.model.AgenticChatDefaultNetworkRequest;
import gebo.microservices.api.client.brain.model.GAgentsNetwork;
import gebo.microservices.api.client.brain.model.OperationStatusAgenticChatDefaultNetworkInfo;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-10-02T12:21:20.194305186+02:00[Europe/Rome]")

public class AgenticChatDefaultNetworksAdminControllerApi {
    private ApiClient apiClient;

     public AgenticChatDefaultNetworksAdminControllerApi() {
        this(new ApiClient());
    }
    public AgenticChatDefaultNetworksAdminControllerApi(ApiClient apiClient) {
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
     * @return List&lt;AgenticChatDefaultNetworkInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AgenticChatDefaultNetworkInfo> getAgenticChatDefaultNetworks() throws RestClientException {
        return getAgenticChatDefaultNetworksWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;AgenticChatDefaultNetworkInfo&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AgenticChatDefaultNetworkInfo>> getAgenticChatDefaultNetworksWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/AgenticChatDefaultNetworksAdminController/getAgenticChatDefaultNetworks").build().toUriString();
        
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

        ParameterizedTypeReference<List<AgenticChatDefaultNetworkInfo>> returnType = new ParameterizedTypeReference<List<AgenticChatDefaultNetworkInfo>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param pipelineType  (required)
     * @return List&lt;GAgentsNetwork&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GAgentsNetwork> getChoosableChatNetworksOfAgents(String pipelineType) throws RestClientException {
        return getChoosableChatNetworksOfAgentsWithHttpInfo(pipelineType).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param pipelineType  (required)
     * @return ResponseEntity&lt;List&lt;GAgentsNetwork&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GAgentsNetwork>> getChoosableChatNetworksOfAgentsWithHttpInfo(String pipelineType) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'pipelineType' is set
        if (pipelineType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pipelineType' when calling getChoosableChatNetworksOfAgents");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/AgenticChatDefaultNetworksAdminController/getChoosableChatNetworksOfAgents").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pipelineType", pipelineType));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GAgentsNetwork>> returnType = new ParameterizedTypeReference<List<GAgentsNetwork>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Boolean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Boolean isAgenticChatNetworksEnabled() throws RestClientException {
        return isAgenticChatNetworksEnabledWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Boolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Boolean> isAgenticChatNetworksEnabledWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/AgenticChatDefaultNetworksAdminController/isAgenticChatNetworksEnabled").build().toUriString();
        
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

        ParameterizedTypeReference<Boolean> returnType = new ParameterizedTypeReference<Boolean>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusAgenticChatDefaultNetworkInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusAgenticChatDefaultNetworkInfo resetAgenticChatDefaultNetwork(AgenticChatDefaultNetworkRequest body) throws RestClientException {
        return resetAgenticChatDefaultNetworkWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusAgenticChatDefaultNetworkInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusAgenticChatDefaultNetworkInfo> resetAgenticChatDefaultNetworkWithHttpInfo(AgenticChatDefaultNetworkRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling resetAgenticChatDefaultNetwork");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/AgenticChatDefaultNetworksAdminController/resetAgenticChatDefaultNetwork").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusAgenticChatDefaultNetworkInfo> returnType = new ParameterizedTypeReference<OperationStatusAgenticChatDefaultNetworkInfo>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusAgenticChatDefaultNetworkInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusAgenticChatDefaultNetworkInfo setAgenticChatDefaultNetwork(AgenticChatDefaultNetworkRequest body) throws RestClientException {
        return setAgenticChatDefaultNetworkWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusAgenticChatDefaultNetworkInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusAgenticChatDefaultNetworkInfo> setAgenticChatDefaultNetworkWithHttpInfo(AgenticChatDefaultNetworkRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling setAgenticChatDefaultNetwork");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/AgenticChatDefaultNetworksAdminController/setAgenticChatDefaultNetwork").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusAgenticChatDefaultNetworkInfo> returnType = new ParameterizedTypeReference<OperationStatusAgenticChatDefaultNetworkInfo>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
