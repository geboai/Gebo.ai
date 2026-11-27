package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.FastGlpiSystemInsertRequest;
import ai.gebo.monolithic.api.client.model.GContentManagementSystemType;
import ai.gebo.monolithic.api.client.model.GGlpiProjectEndpoint;
import ai.gebo.monolithic.api.client.model.GGlpiSystem;
import ai.gebo.monolithic.api.client.model.OperationStatusGGlpiSystem;
import ai.gebo.monolithic.api.client.model.OperationStatusGJobStatus;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-27T18:16:29.997698904+01:00[Europe/Rome]")

public class GlpiSystemsControllerApi {
    private ApiClient apiClient;

     public GlpiSystemsControllerApi() {
        this(new ApiClient());
    }
    public GlpiSystemsControllerApi(ApiClient apiClient) {
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
    public void deleteGlpiEndpoint(GGlpiProjectEndpoint body) throws RestClientException {
        deleteGlpiEndpointWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteGlpiEndpointWithHttpInfo(GGlpiProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteGlpiEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/deleteGlpiEndpoint").build().toUriString();
        
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
    public void deleteGlpiSystem(GGlpiSystem body) throws RestClientException {
        deleteGlpiSystemWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteGlpiSystemWithHttpInfo(GGlpiSystem body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteGlpiSystem");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/deleteGlpiSystem").build().toUriString();
        
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
     * @return OperationStatusGGlpiSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGGlpiSystem fastGlpiConfig(FastGlpiSystemInsertRequest body) throws RestClientException {
        return fastGlpiConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGGlpiSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGGlpiSystem> fastGlpiConfigWithHttpInfo(FastGlpiSystemInsertRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling fastGlpiConfig");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/fastGlpiConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGGlpiSystem> returnType = new ParameterizedTypeReference<OperationStatusGGlpiSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return GGlpiProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GGlpiProjectEndpoint findGlpiEndpointsByCode(String code) throws RestClientException {
        return findGlpiEndpointsByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GGlpiProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GGlpiProjectEndpoint> findGlpiEndpointsByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findGlpiEndpointsByCode");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/findGlpiEndpointsByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GGlpiProjectEndpoint> returnType = new ParameterizedTypeReference<GGlpiProjectEndpoint>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param parentProjectCode  (required)
     * @return List&lt;GGlpiProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GGlpiProjectEndpoint> findGlpiEndpointsByProject(String parentProjectCode) throws RestClientException {
        return findGlpiEndpointsByProjectWithHttpInfo(parentProjectCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param parentProjectCode  (required)
     * @return ResponseEntity&lt;List&lt;GGlpiProjectEndpoint&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GGlpiProjectEndpoint>> findGlpiEndpointsByProjectWithHttpInfo(String parentProjectCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'parentProjectCode' is set
        if (parentProjectCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'parentProjectCode' when calling findGlpiEndpointsByProject");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/findGlpiEndpointsByProject").build().toUriString();
        
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

        ParameterizedTypeReference<List<GGlpiProjectEndpoint>> returnType = new ParameterizedTypeReference<List<GGlpiProjectEndpoint>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;GGlpiProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GGlpiProjectEndpoint> findGlpiEndpointsByQbe(GGlpiProjectEndpoint body) throws RestClientException {
        return findGlpiEndpointsByQbeWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;GGlpiProjectEndpoint&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GGlpiProjectEndpoint>> findGlpiEndpointsByQbeWithHttpInfo(GGlpiProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling findGlpiEndpointsByQbe");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/findGlpiEndpointsByQbe").build().toUriString();
        
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

        ParameterizedTypeReference<List<GGlpiProjectEndpoint>> returnType = new ParameterizedTypeReference<List<GGlpiProjectEndpoint>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return GGlpiSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GGlpiSystem findGlpiSystemByCode(String code) throws RestClientException {
        return findGlpiSystemByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GGlpiSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GGlpiSystem> findGlpiSystemByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findGlpiSystemByCode");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/findGlpiSystemByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GGlpiSystem> returnType = new ParameterizedTypeReference<GGlpiSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return GContentManagementSystemType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GContentManagementSystemType getGlpiSystemType() throws RestClientException {
        return getGlpiSystemTypeWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;GContentManagementSystemType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GContentManagementSystemType> getGlpiSystemTypeWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/getGlpiSystemType").build().toUriString();
        
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
     * @return List&lt;GGlpiSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GGlpiSystem> getGlpiSystems() throws RestClientException {
        return getGlpiSystemsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GGlpiSystem&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GGlpiSystem>> getGlpiSystemsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/getGlpiSystems").build().toUriString();
        
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

        ParameterizedTypeReference<List<GGlpiSystem>> returnType = new ParameterizedTypeReference<List<GGlpiSystem>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GGlpiProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GGlpiProjectEndpoint insertGlpiEndpoint(GGlpiProjectEndpoint body) throws RestClientException {
        return insertGlpiEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GGlpiProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GGlpiProjectEndpoint> insertGlpiEndpointWithHttpInfo(GGlpiProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertGlpiEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/insertGlpiEndpoint").build().toUriString();
        
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

        ParameterizedTypeReference<GGlpiProjectEndpoint> returnType = new ParameterizedTypeReference<GGlpiProjectEndpoint>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGGlpiSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGGlpiSystem insertGlpiSystem(GGlpiSystem body) throws RestClientException {
        return insertGlpiSystemWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGGlpiSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGGlpiSystem> insertGlpiSystemWithHttpInfo(GGlpiSystem body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertGlpiSystem");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/insertGlpiSystem").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGGlpiSystem> returnType = new ParameterizedTypeReference<OperationStatusGGlpiSystem>() {};
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
    public OperationStatusGJobStatus publishGlpiEndpoint(GGlpiProjectEndpoint body) throws RestClientException {
        return publishGlpiEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGJobStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGJobStatus> publishGlpiEndpointWithHttpInfo(GGlpiProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling publishGlpiEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/publishGlpiEndpoint").build().toUriString();
        
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
     * @return OperationStatusGGlpiSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGGlpiSystem testGlpiSystem(GGlpiSystem body) throws RestClientException {
        return testGlpiSystemWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGGlpiSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGGlpiSystem> testGlpiSystemWithHttpInfo(GGlpiSystem body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling testGlpiSystem");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/testGlpiSystem").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGGlpiSystem> returnType = new ParameterizedTypeReference<OperationStatusGGlpiSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GGlpiProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GGlpiProjectEndpoint updateGlpiEndpoint(GGlpiProjectEndpoint body) throws RestClientException {
        return updateGlpiEndpointWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GGlpiProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GGlpiProjectEndpoint> updateGlpiEndpointWithHttpInfo(GGlpiProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateGlpiEndpoint");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/updateGlpiEndpoint").build().toUriString();
        
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

        ParameterizedTypeReference<GGlpiProjectEndpoint> returnType = new ParameterizedTypeReference<GGlpiProjectEndpoint>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGGlpiSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGGlpiSystem updateGlpiSystem(GGlpiSystem body) throws RestClientException {
        return updateGlpiSystemWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGGlpiSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGGlpiSystem> updateGlpiSystemWithHttpInfo(GGlpiSystem body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateGlpiSystem");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GlpiSystemsController/updateGlpiSystem").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGGlpiSystem> returnType = new ParameterizedTypeReference<OperationStatusGGlpiSystem>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
