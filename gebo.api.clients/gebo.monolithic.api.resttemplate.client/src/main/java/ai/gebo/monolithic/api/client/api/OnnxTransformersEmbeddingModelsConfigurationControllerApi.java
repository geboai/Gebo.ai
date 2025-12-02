package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GONNXTransformersEmbeddingModelConfig;
import ai.gebo.monolithic.api.client.model.OperationStatusBoolean;
import ai.gebo.monolithic.api.client.model.OperationStatusGONNXTransformersEmbeddingModelConfig;
import ai.gebo.monolithic.api.client.model.OperationStatusListGONNXTransformersEmbeddingModelChoice;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-12-02T07:42:58.505542900+01:00[Europe/Rome]")

public class OnnxTransformersEmbeddingModelsConfigurationControllerApi {
    private ApiClient apiClient;

     public OnnxTransformersEmbeddingModelsConfigurationControllerApi() {
        this(new ApiClient());
    }
    public OnnxTransformersEmbeddingModelsConfigurationControllerApi(ApiClient apiClient) {
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
    public OperationStatusBoolean deleteONNXTransformersEmbeddingModelConfig(GONNXTransformersEmbeddingModelConfig body) throws RestClientException {
        return deleteONNXTransformersEmbeddingModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusBoolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusBoolean> deleteONNXTransformersEmbeddingModelConfigWithHttpInfo(GONNXTransformersEmbeddingModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteONNXTransformersEmbeddingModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/deleteONNXTransformersEmbeddingModelConfig").build().toUriString();
        
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
     * @return GONNXTransformersEmbeddingModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GONNXTransformersEmbeddingModelConfig findONNXTransformersEmbeddingModelConfigByCode(String code) throws RestClientException {
        return findONNXTransformersEmbeddingModelConfigByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GONNXTransformersEmbeddingModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GONNXTransformersEmbeddingModelConfig> findONNXTransformersEmbeddingModelConfigByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findONNXTransformersEmbeddingModelConfigByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/findONNXTransformersEmbeddingModelConfigByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GONNXTransformersEmbeddingModelConfig> returnType = new ParameterizedTypeReference<GONNXTransformersEmbeddingModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusListGONNXTransformersEmbeddingModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGONNXTransformersEmbeddingModelChoice getONNXTransformersEmbeddingModels(GONNXTransformersEmbeddingModelConfig body) throws RestClientException {
        return getONNXTransformersEmbeddingModelsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusListGONNXTransformersEmbeddingModelChoice&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGONNXTransformersEmbeddingModelChoice> getONNXTransformersEmbeddingModelsWithHttpInfo(GONNXTransformersEmbeddingModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getONNXTransformersEmbeddingModels");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/getONNXTransformersEmbeddingModels").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListGONNXTransformersEmbeddingModelChoice> returnType = new ParameterizedTypeReference<OperationStatusListGONNXTransformersEmbeddingModelChoice>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGONNXTransformersEmbeddingModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGONNXTransformersEmbeddingModelConfig insertONNXTransformersEmbeddingModelConfig(GONNXTransformersEmbeddingModelConfig body) throws RestClientException {
        return insertONNXTransformersEmbeddingModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGONNXTransformersEmbeddingModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGONNXTransformersEmbeddingModelConfig> insertONNXTransformersEmbeddingModelConfigWithHttpInfo(GONNXTransformersEmbeddingModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertONNXTransformersEmbeddingModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/insertONNXTransformersEmbeddingModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGONNXTransformersEmbeddingModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGONNXTransformersEmbeddingModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGONNXTransformersEmbeddingModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGONNXTransformersEmbeddingModelConfig updateONNXTransformersEmbeddingModelConfig(GONNXTransformersEmbeddingModelConfig body) throws RestClientException {
        return updateONNXTransformersEmbeddingModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGONNXTransformersEmbeddingModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGONNXTransformersEmbeddingModelConfig> updateONNXTransformersEmbeddingModelConfigWithHttpInfo(GONNXTransformersEmbeddingModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateONNXTransformersEmbeddingModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/updateONNXTransformersEmbeddingModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGONNXTransformersEmbeddingModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGONNXTransformersEmbeddingModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
