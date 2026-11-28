package gebo.microservices.api.client.redmine.api;

import gebo.microservices.api.client.redmine.invoker.ApiClient;

import gebo.microservices.api.client.redmine.model.FastRedmineSystemInsertRequest;
import gebo.microservices.api.client.redmine.model.GContentManagementSystemType;
import gebo.microservices.api.client.redmine.model.GRedmineProjectEndpoint;
import gebo.microservices.api.client.redmine.model.GRedmineSystem;
import gebo.microservices.api.client.redmine.model.OperationStatusGJobStatus;
import gebo.microservices.api.client.redmine.model.OperationStatusGRedmineSystem;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-28T07:05:54.288542252+01:00[Europe/Rome]")

public class RedmineSystemsControllerApi {
    private ApiClient apiClient;

     public RedmineSystemsControllerApi() {
        this(new ApiClient());
    }
    public RedmineSystemsControllerApi(ApiClient apiClient) {
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
    public void deleteRedmineEndpoint(GRedmineProjectEndpoint body) throws RestClientException {
        deleteRedmineEndpointWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteRedmineEndpointWithHttpInfo(GRedmineProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteRedmineEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/deleteRedmineEndpoint").build().toUriString();
        
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
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteRedmineSystem(GRedmineSystem body) throws RestClientException {
        deleteRedmineSystemWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteRedmineSystemWithHttpInfo(GRedmineSystem body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteRedmineSystem");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/deleteRedmineSystem").build().toUriString();
        
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
     * @return OperationStatusGRedmineSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGRedmineSystem fastRedmineConfig(FastRedmineSystemInsertRequest body) throws RestClientException {
        return fastRedmineConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGRedmineSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGRedmineSystem> fastRedmineConfigWithHttpInfo(FastRedmineSystemInsertRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling fastRedmineConfig");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/fastRedmineConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGRedmineSystem> returnType = new ParameterizedTypeReference<OperationStatusGRedmineSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return GRedmineProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GRedmineProjectEndpoint findRedmineEndpointByCode(String code) throws RestClientException {
        return findRedmineEndpointByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GRedmineProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GRedmineProjectEndpoint> findRedmineEndpointByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findRedmineEndpointByCode");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/findRedmineEndpointByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GRedmineProjectEndpoint> returnType = new ParameterizedTypeReference<GRedmineProjectEndpoint>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param parentProjectCode  (required)
     * @return List&lt;GRedmineProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GRedmineProjectEndpoint> findRedmineEndpointsByProject(String parentProjectCode) throws RestClientException {
        return findRedmineEndpointsByProjectWithHttpInfo(parentProjectCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param parentProjectCode  (required)
     * @return ResponseEntity&lt;List&lt;GRedmineProjectEndpoint&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GRedmineProjectEndpoint>> findRedmineEndpointsByProjectWithHttpInfo(String parentProjectCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'parentProjectCode' is set
        if (parentProjectCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'parentProjectCode' when calling findRedmineEndpointsByProject");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/findRedmineEndpointsByProject").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "parentProjectCode", parentProjectCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GRedmineProjectEndpoint>> returnType = new ParameterizedTypeReference<List<GRedmineProjectEndpoint>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;GRedmineProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GRedmineProjectEndpoint> findRedmineEndpointsByQbe(GRedmineProjectEndpoint body) throws RestClientException {
        return findRedmineEndpointsByQbeWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;GRedmineProjectEndpoint&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GRedmineProjectEndpoint>> findRedmineEndpointsByQbeWithHttpInfo(GRedmineProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling findRedmineEndpointsByQbe");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/findRedmineEndpointsByQbe").build().toUriString();
        
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

        ParameterizedTypeReference<List<GRedmineProjectEndpoint>> returnType = new ParameterizedTypeReference<List<GRedmineProjectEndpoint>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return GRedmineSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GRedmineSystem findRedmineSystemByCode(String code) throws RestClientException {
        return findRedmineSystemByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GRedmineSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GRedmineSystem> findRedmineSystemByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findRedmineSystemByCode");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/findRedmineSystemByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GRedmineSystem> returnType = new ParameterizedTypeReference<GRedmineSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return GContentManagementSystemType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GContentManagementSystemType getRedmineSystemType() throws RestClientException {
        return getRedmineSystemTypeWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;GContentManagementSystemType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GContentManagementSystemType> getRedmineSystemTypeWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/getRedmineSystemType").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GRedmineSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GRedmineSystem> getRedmineSystems() throws RestClientException {
        return getRedmineSystemsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GRedmineSystem&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GRedmineSystem>> getRedmineSystemsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/getRedmineSystems").build().toUriString();
        
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

        ParameterizedTypeReference<List<GRedmineSystem>> returnType = new ParameterizedTypeReference<List<GRedmineSystem>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GRedmineProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GRedmineProjectEndpoint insertRedmineEndpoint(GRedmineProjectEndpoint body) throws RestClientException {
        return insertRedmineEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GRedmineProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GRedmineProjectEndpoint> insertRedmineEndpointWithHttpInfo(GRedmineProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertRedmineEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/insertRedmineEndpoint").build().toUriString();
        
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

        ParameterizedTypeReference<GRedmineProjectEndpoint> returnType = new ParameterizedTypeReference<GRedmineProjectEndpoint>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGRedmineSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGRedmineSystem insertRedmineSystem(GRedmineSystem body) throws RestClientException {
        return insertRedmineSystemWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGRedmineSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGRedmineSystem> insertRedmineSystemWithHttpInfo(GRedmineSystem body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertRedmineSystem");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/insertRedmineSystem").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGRedmineSystem> returnType = new ParameterizedTypeReference<OperationStatusGRedmineSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGJobStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGJobStatus publishRedmineEndpoint(GRedmineProjectEndpoint body) throws RestClientException {
        return publishRedmineEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGJobStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGJobStatus> publishRedmineEndpointWithHttpInfo(GRedmineProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling publishRedmineEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/publishRedmineEndpoint").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGRedmineSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGRedmineSystem testRedmineSystem(GRedmineSystem body) throws RestClientException {
        return testRedmineSystemWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGRedmineSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGRedmineSystem> testRedmineSystemWithHttpInfo(GRedmineSystem body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling testRedmineSystem");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/testRedmineSystem").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGRedmineSystem> returnType = new ParameterizedTypeReference<OperationStatusGRedmineSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GRedmineProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GRedmineProjectEndpoint updateRedmineEndpoint(GRedmineProjectEndpoint body) throws RestClientException {
        return updateRedmineEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GRedmineProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GRedmineProjectEndpoint> updateRedmineEndpointWithHttpInfo(GRedmineProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateRedmineEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/updateRedmineEndpoint").build().toUriString();
        
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

        ParameterizedTypeReference<GRedmineProjectEndpoint> returnType = new ParameterizedTypeReference<GRedmineProjectEndpoint>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGRedmineSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGRedmineSystem updateRedmineSystem(GRedmineSystem body) throws RestClientException {
        return updateRedmineSystemWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGRedmineSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGRedmineSystem> updateRedmineSystemWithHttpInfo(GRedmineSystem body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateRedmineSystem");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/RedmineSystemsController/updateRedmineSystem").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGRedmineSystem> returnType = new ParameterizedTypeReference<OperationStatusGRedmineSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
