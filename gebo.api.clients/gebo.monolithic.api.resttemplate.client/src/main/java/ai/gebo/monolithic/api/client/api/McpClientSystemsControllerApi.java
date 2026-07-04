package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GContentManagementSystemType;
import ai.gebo.monolithic.api.client.model.MCPClientProjectEndpoint;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-04T10:49:15.119457200+02:00[Europe/Rome]")

public class McpClientSystemsControllerApi {
    private ApiClient apiClient;

     public McpClientSystemsControllerApi() {
        this(new ApiClient());
    }
    public McpClientSystemsControllerApi(ApiClient apiClient) {
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
    public void deleteMCPClientEndpoint(MCPClientProjectEndpoint body) throws RestClientException {
        deleteMCPClientEndpointWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteMCPClientEndpointWithHttpInfo(MCPClientProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteMCPClientEndpoint");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/MCPClientSystemsController/deleteMCPClientEndpoint").build().toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return MCPClientProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public MCPClientProjectEndpoint findMCPClientEndpointsByCode(String code) throws RestClientException {
        return findMCPClientEndpointsByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;MCPClientProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MCPClientProjectEndpoint> findMCPClientEndpointsByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findMCPClientEndpointsByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/MCPClientSystemsController/findMCPClientEndpointsByCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "code", code));

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<MCPClientProjectEndpoint> returnType = new ParameterizedTypeReference<MCPClientProjectEndpoint>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param parentProjectCode  (required)
     * @return List&lt;MCPClientProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<MCPClientProjectEndpoint> findMCPClientEndpointsByProject(String parentProjectCode) throws RestClientException {
        return findMCPClientEndpointsByProjectWithHttpInfo(parentProjectCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param parentProjectCode  (required)
     * @return ResponseEntity&lt;List&lt;MCPClientProjectEndpoint&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<MCPClientProjectEndpoint>> findMCPClientEndpointsByProjectWithHttpInfo(String parentProjectCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'parentProjectCode' is set
        if (parentProjectCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'parentProjectCode' when calling findMCPClientEndpointsByProject");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/MCPClientSystemsController/findMCPClientEndpointsByProject").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "parentProjectCode", parentProjectCode));

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<MCPClientProjectEndpoint>> returnType = new ParameterizedTypeReference<List<MCPClientProjectEndpoint>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;MCPClientProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<MCPClientProjectEndpoint> findMCPClientEndpointsByQbe(MCPClientProjectEndpoint body) throws RestClientException {
        return findMCPClientEndpointsByQbeWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;MCPClientProjectEndpoint&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<MCPClientProjectEndpoint>> findMCPClientEndpointsByQbeWithHttpInfo(MCPClientProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling findMCPClientEndpointsByQbe");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/MCPClientSystemsController/findMCPClientEndpointsByQbe").build().toUriString();
        
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

        ParameterizedTypeReference<List<MCPClientProjectEndpoint>> returnType = new ParameterizedTypeReference<List<MCPClientProjectEndpoint>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return GContentManagementSystemType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GContentManagementSystemType getMCPClientSystemType() throws RestClientException {
        return getMCPClientSystemTypeWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;GContentManagementSystemType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GContentManagementSystemType> getMCPClientSystemTypeWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/MCPClientSystemsController/getMCPClientSystemType").build().toUriString();
        
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

        ParameterizedTypeReference<GContentManagementSystemType> returnType = new ParameterizedTypeReference<GContentManagementSystemType>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return MCPClientProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public MCPClientProjectEndpoint insertMCPClientEndpoint(MCPClientProjectEndpoint body) throws RestClientException {
        return insertMCPClientEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;MCPClientProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MCPClientProjectEndpoint> insertMCPClientEndpointWithHttpInfo(MCPClientProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertMCPClientEndpoint");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/MCPClientSystemsController/insertMCPClientEndpoint").build().toUriString();
        
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

        ParameterizedTypeReference<MCPClientProjectEndpoint> returnType = new ParameterizedTypeReference<MCPClientProjectEndpoint>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return MCPClientProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public MCPClientProjectEndpoint updateMCPClientEndpoint(MCPClientProjectEndpoint body) throws RestClientException {
        return updateMCPClientEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;MCPClientProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MCPClientProjectEndpoint> updateMCPClientEndpointWithHttpInfo(MCPClientProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateMCPClientEndpoint");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/MCPClientSystemsController/updateMCPClientEndpoint").build().toUriString();
        
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

        ParameterizedTypeReference<MCPClientProjectEndpoint> returnType = new ParameterizedTypeReference<MCPClientProjectEndpoint>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
