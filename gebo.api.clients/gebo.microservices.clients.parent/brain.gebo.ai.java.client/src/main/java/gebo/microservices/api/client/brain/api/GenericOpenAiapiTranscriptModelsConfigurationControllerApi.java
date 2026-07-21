package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;

import gebo.microservices.api.client.brain.model.GenericOpenAIAPITranscriptModelConfig;
import gebo.microservices.api.client.brain.model.OperationStatusBoolean;
import gebo.microservices.api.client.brain.model.OperationStatusGenericOpenAIAPITranscriptModelConfig;
import gebo.microservices.api.client.brain.model.OperationStatusListGenericOpenAIAPITranscriptModelChoice;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-20T23:48:43.793671417+02:00[Europe/Rome]")

public class GenericOpenAiapiTranscriptModelsConfigurationControllerApi {
    private ApiClient apiClient;

     public GenericOpenAiapiTranscriptModelsConfigurationControllerApi() {
        this(new ApiClient());
    }
    public GenericOpenAiapiTranscriptModelsConfigurationControllerApi(ApiClient apiClient) {
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
    public OperationStatusBoolean deleteGenericOpenAIAPITranscriptModelConfig(GenericOpenAIAPITranscriptModelConfig body) throws RestClientException {
        return deleteGenericOpenAIAPITranscriptModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusBoolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusBoolean> deleteGenericOpenAIAPITranscriptModelConfigWithHttpInfo(GenericOpenAIAPITranscriptModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteGenericOpenAIAPITranscriptModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/deleteGenericOpenAIAPITranscriptModelConfig").build().toUriString();
        
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
     * @return GenericOpenAIAPITranscriptModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GenericOpenAIAPITranscriptModelConfig findGenericOpenAIAPITranscriptModelConfigByCode(Object code) throws RestClientException {
        return findGenericOpenAIAPITranscriptModelConfigByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GenericOpenAIAPITranscriptModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericOpenAIAPITranscriptModelConfig> findGenericOpenAIAPITranscriptModelConfigByCodeWithHttpInfo(Object code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findGenericOpenAIAPITranscriptModelConfigByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/findGenericOpenAIAPITranscriptModelConfigByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GenericOpenAIAPITranscriptModelConfig> returnType = new ParameterizedTypeReference<GenericOpenAIAPITranscriptModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusListGenericOpenAIAPITranscriptModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGenericOpenAIAPITranscriptModelChoice getGenericOpenAIAPITranscriptModels(GenericOpenAIAPITranscriptModelConfig body) throws RestClientException {
        return getGenericOpenAIAPITranscriptModelsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusListGenericOpenAIAPITranscriptModelChoice&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGenericOpenAIAPITranscriptModelChoice> getGenericOpenAIAPITranscriptModelsWithHttpInfo(GenericOpenAIAPITranscriptModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getGenericOpenAIAPITranscriptModels");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAIAPITranscriptModels").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListGenericOpenAIAPITranscriptModelChoice> returnType = new ParameterizedTypeReference<OperationStatusListGenericOpenAIAPITranscriptModelChoice>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object getGenericOpenAITranscriptModelConfigs() throws RestClientException {
        return getGenericOpenAITranscriptModelConfigsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getGenericOpenAITranscriptModelConfigsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelConfigs").build().toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object getGenericOpenAITranscriptModelTypes() throws RestClientException {
        return getGenericOpenAITranscriptModelTypesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getGenericOpenAITranscriptModelTypesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelTypes").build().toUriString();
        
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

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGenericOpenAIAPITranscriptModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGenericOpenAIAPITranscriptModelConfig insertGenericOpenAIAPITranscriptModelConfig(GenericOpenAIAPITranscriptModelConfig body) throws RestClientException {
        return insertGenericOpenAIAPITranscriptModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGenericOpenAIAPITranscriptModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGenericOpenAIAPITranscriptModelConfig> insertGenericOpenAIAPITranscriptModelConfigWithHttpInfo(GenericOpenAIAPITranscriptModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertGenericOpenAIAPITranscriptModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/insertGenericOpenAIAPITranscriptModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGenericOpenAIAPITranscriptModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGenericOpenAIAPITranscriptModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGenericOpenAIAPITranscriptModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGenericOpenAIAPITranscriptModelConfig updateGenericOpenAIAPITranscriptModelConfig(GenericOpenAIAPITranscriptModelConfig body) throws RestClientException {
        return updateGenericOpenAIAPITranscriptModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGenericOpenAIAPITranscriptModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGenericOpenAIAPITranscriptModelConfig> updateGenericOpenAIAPITranscriptModelConfigWithHttpInfo(GenericOpenAIAPITranscriptModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateGenericOpenAIAPITranscriptModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/updateGenericOpenAIAPITranscriptModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGenericOpenAIAPITranscriptModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGenericOpenAIAPITranscriptModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
