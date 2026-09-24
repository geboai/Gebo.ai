package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;

import gebo.microservices.api.client.brain.model.A2ARemoteAgentConfig;
import gebo.microservices.api.client.brain.model.OperationStatusA2ARemoteAgentConfig;
import gebo.microservices.api.client.brain.model.OperationStatusBoolean;
import gebo.microservices.api.client.brain.model.PageA2ARemoteAgentConfig;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-09-24T08:46:23.233601555+02:00[Europe/Rome]")

public class A2AClientConfigControllerApi {
    private ApiClient apiClient;

     public A2AClientConfigControllerApi() {
        this(new ApiClient());
    }
    public A2AClientConfigControllerApi(ApiClient apiClient) {
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
     * @return OperationStatusBoolean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusBoolean deleteA2AClientConfig(A2ARemoteAgentConfig body) throws RestClientException {
        return deleteA2AClientConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusBoolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusBoolean> deleteA2AClientConfigWithHttpInfo(A2ARemoteAgentConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteA2AClientConfig");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/A2AClientConfigController/deleteA2AAgent").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusBoolean> returnType = new ParameterizedTypeReference<OperationStatusBoolean>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return OperationStatusA2ARemoteAgentConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusA2ARemoteAgentConfig findByCodeA2AClientConfig(String code) throws RestClientException {
        return findByCodeA2AClientConfigWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;OperationStatusA2ARemoteAgentConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusA2ARemoteAgentConfig> findByCodeA2AClientConfigWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findByCodeA2AClientConfig");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/A2AClientConfigController/findByCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "code", code));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusA2ARemoteAgentConfig> returnType = new ParameterizedTypeReference<OperationStatusA2ARemoteAgentConfig>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusA2ARemoteAgentConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusA2ARemoteAgentConfig insertA2AClientConfig(A2ARemoteAgentConfig body) throws RestClientException {
        return insertA2AClientConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusA2ARemoteAgentConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusA2ARemoteAgentConfig> insertA2AClientConfigWithHttpInfo(A2ARemoteAgentConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertA2AClientConfig");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/A2AClientConfigController/insertA2AAgent").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusA2ARemoteAgentConfig> returnType = new ParameterizedTypeReference<OperationStatusA2ARemoteAgentConfig>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return PageA2ARemoteAgentConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageA2ARemoteAgentConfig listA2AClientConfig(Integer page, Integer size) throws RestClientException {
        return listA2AClientConfigWithHttpInfo(page, size).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return ResponseEntity&lt;PageA2ARemoteAgentConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageA2ARemoteAgentConfig> listA2AClientConfigWithHttpInfo(Integer page, Integer size) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/A2AClientConfigController/list").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PageA2ARemoteAgentConfig> returnType = new ParameterizedTypeReference<PageA2ARemoteAgentConfig>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusA2ARemoteAgentConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusA2ARemoteAgentConfig testAndDiscoveryA2AClientConfig(A2ARemoteAgentConfig body) throws RestClientException {
        return testAndDiscoveryA2AClientConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusA2ARemoteAgentConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusA2ARemoteAgentConfig> testAndDiscoveryA2AClientConfigWithHttpInfo(A2ARemoteAgentConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling testAndDiscoveryA2AClientConfig");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/A2AClientConfigController/testAndDiscovery").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusA2ARemoteAgentConfig> returnType = new ParameterizedTypeReference<OperationStatusA2ARemoteAgentConfig>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusA2ARemoteAgentConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusA2ARemoteAgentConfig updateA2AClientConfig(A2ARemoteAgentConfig body) throws RestClientException {
        return updateA2AClientConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusA2ARemoteAgentConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusA2ARemoteAgentConfig> updateA2AClientConfigWithHttpInfo(A2ARemoteAgentConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateA2AClientConfig");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/A2AClientConfigController/updateA2AAgent").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusA2ARemoteAgentConfig> returnType = new ParameterizedTypeReference<OperationStatusA2ARemoteAgentConfig>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
