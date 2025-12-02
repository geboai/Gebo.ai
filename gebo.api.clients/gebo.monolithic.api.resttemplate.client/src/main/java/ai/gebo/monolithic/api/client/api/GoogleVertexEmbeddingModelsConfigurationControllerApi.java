package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GGoogleVertexEmbeddingModelConfig;
import ai.gebo.monolithic.api.client.model.OperationStatusBoolean;
import ai.gebo.monolithic.api.client.model.OperationStatusGGoogleVertexEmbeddingModelConfig;
import ai.gebo.monolithic.api.client.model.OperationStatusListGGoogleVertexEmbeddingModelChoice;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-12-02T06:59:38.467683700+01:00[Europe/Rome]")

public class GoogleVertexEmbeddingModelsConfigurationControllerApi {
    private ApiClient apiClient;

     public GoogleVertexEmbeddingModelsConfigurationControllerApi() {
        this(new ApiClient());
    }
    public GoogleVertexEmbeddingModelsConfigurationControllerApi(ApiClient apiClient) {
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
    public OperationStatusBoolean deleteGoogleVertexEmbeddingModelConfig(GGoogleVertexEmbeddingModelConfig body) throws RestClientException {
        return deleteGoogleVertexEmbeddingModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusBoolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusBoolean> deleteGoogleVertexEmbeddingModelConfigWithHttpInfo(GGoogleVertexEmbeddingModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteGoogleVertexEmbeddingModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GoogleVertexEmbeddingModelsConfigurationController/deleteGoogleVertexEmbeddingModelConfig").build().toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return GGoogleVertexEmbeddingModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GGoogleVertexEmbeddingModelConfig findGoogleVertexEmbeddingModelConfigByCode(String code) throws RestClientException {
        return findGoogleVertexEmbeddingModelConfigByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GGoogleVertexEmbeddingModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GGoogleVertexEmbeddingModelConfig> findGoogleVertexEmbeddingModelConfigByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findGoogleVertexEmbeddingModelConfigByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GoogleVertexEmbeddingModelsConfigurationController/findGoogleVertexEmbeddingModelConfigByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GGoogleVertexEmbeddingModelConfig> returnType = new ParameterizedTypeReference<GGoogleVertexEmbeddingModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusListGGoogleVertexEmbeddingModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGGoogleVertexEmbeddingModelChoice getGoogleVertexEmbeddingModels(GGoogleVertexEmbeddingModelConfig body) throws RestClientException {
        return getGoogleVertexEmbeddingModelsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusListGGoogleVertexEmbeddingModelChoice&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGGoogleVertexEmbeddingModelChoice> getGoogleVertexEmbeddingModelsWithHttpInfo(GGoogleVertexEmbeddingModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getGoogleVertexEmbeddingModels");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GoogleVertexEmbeddingModelsConfigurationController/getGoogleVertexEmbeddingModels").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListGGoogleVertexEmbeddingModelChoice> returnType = new ParameterizedTypeReference<OperationStatusListGGoogleVertexEmbeddingModelChoice>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGGoogleVertexEmbeddingModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGGoogleVertexEmbeddingModelConfig insertGoogleVertexEmbeddingModelConfig(GGoogleVertexEmbeddingModelConfig body) throws RestClientException {
        return insertGoogleVertexEmbeddingModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGGoogleVertexEmbeddingModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGGoogleVertexEmbeddingModelConfig> insertGoogleVertexEmbeddingModelConfigWithHttpInfo(GGoogleVertexEmbeddingModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertGoogleVertexEmbeddingModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GoogleVertexEmbeddingModelsConfigurationController/insertGoogleVertexEmbeddingModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGGoogleVertexEmbeddingModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGGoogleVertexEmbeddingModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGGoogleVertexEmbeddingModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGGoogleVertexEmbeddingModelConfig updateGoogleVertexEmbeddingModelConfig(GGoogleVertexEmbeddingModelConfig body) throws RestClientException {
        return updateGoogleVertexEmbeddingModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGGoogleVertexEmbeddingModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGGoogleVertexEmbeddingModelConfig> updateGoogleVertexEmbeddingModelConfigWithHttpInfo(GGoogleVertexEmbeddingModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateGoogleVertexEmbeddingModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GoogleVertexEmbeddingModelsConfigurationController/updateGoogleVertexEmbeddingModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGGoogleVertexEmbeddingModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGGoogleVertexEmbeddingModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
