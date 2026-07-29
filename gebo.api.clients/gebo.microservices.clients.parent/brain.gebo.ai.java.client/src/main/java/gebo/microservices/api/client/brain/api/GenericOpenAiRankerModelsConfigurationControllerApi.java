package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;

import gebo.microservices.api.client.brain.model.GenericOpenAIAPIRankerModelConfig;
import gebo.microservices.api.client.brain.model.OperationStatusBoolean;
import gebo.microservices.api.client.brain.model.OperationStatusGenericOpenAIAPIRankerModelConfig;
import gebo.microservices.api.client.brain.model.OperationStatusListGenericOpenAIAPIRankerModelChoice;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-29T09:42:38.770164403+02:00[Europe/Rome]")

public class GenericOpenAiRankerModelsConfigurationControllerApi {
    private ApiClient apiClient;

     public GenericOpenAiRankerModelsConfigurationControllerApi() {
        this(new ApiClient());
    }
    public GenericOpenAiRankerModelsConfigurationControllerApi(ApiClient apiClient) {
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
    public OperationStatusBoolean deleteGenericOpenAIAPIRankerModelConfig(GenericOpenAIAPIRankerModelConfig body) throws RestClientException {
        return deleteGenericOpenAIAPIRankerModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusBoolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusBoolean> deleteGenericOpenAIAPIRankerModelConfigWithHttpInfo(GenericOpenAIAPIRankerModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteGenericOpenAIAPIRankerModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenerigOpenAIRankerModelsConfigurationController/deleteGenericOpenAIAPIRankerModelConfig").build().toUriString();
        
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
     * @return GenericOpenAIAPIRankerModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GenericOpenAIAPIRankerModelConfig findGenericOpenAIAPIRankerModelConfigByCode(Object code) throws RestClientException {
        return findGenericOpenAIAPIRankerModelConfigByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GenericOpenAIAPIRankerModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericOpenAIAPIRankerModelConfig> findGenericOpenAIAPIRankerModelConfigByCodeWithHttpInfo(Object code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findGenericOpenAIAPIRankerModelConfigByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenerigOpenAIRankerModelsConfigurationController/findGenericOpenAIAPIRankerModelConfigByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GenericOpenAIAPIRankerModelConfig> returnType = new ParameterizedTypeReference<GenericOpenAIAPIRankerModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusListGenericOpenAIAPIRankerModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGenericOpenAIAPIRankerModelChoice getGenericOpenAIAPIRankerModels(GenericOpenAIAPIRankerModelConfig body) throws RestClientException {
        return getGenericOpenAIAPIRankerModelsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusListGenericOpenAIAPIRankerModelChoice&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGenericOpenAIAPIRankerModelChoice> getGenericOpenAIAPIRankerModelsWithHttpInfo(GenericOpenAIAPIRankerModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getGenericOpenAIAPIRankerModels");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIAPIRankerModels").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListGenericOpenAIAPIRankerModelChoice> returnType = new ParameterizedTypeReference<OperationStatusListGenericOpenAIAPIRankerModelChoice>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object getGenericOpenAIRankerModelConfigs() throws RestClientException {
        return getGenericOpenAIRankerModelConfigsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getGenericOpenAIRankerModelConfigsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelConfigs").build().toUriString();
        
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
    public Object getGenericOpenAIRankerModelTypes() throws RestClientException {
        return getGenericOpenAIRankerModelTypesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getGenericOpenAIRankerModelTypesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelTypes").build().toUriString();
        
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
     * @return OperationStatusGenericOpenAIAPIRankerModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGenericOpenAIAPIRankerModelConfig insertGenericOpenAIAPIRankerModelConfig(GenericOpenAIAPIRankerModelConfig body) throws RestClientException {
        return insertGenericOpenAIAPIRankerModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGenericOpenAIAPIRankerModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGenericOpenAIAPIRankerModelConfig> insertGenericOpenAIAPIRankerModelConfigWithHttpInfo(GenericOpenAIAPIRankerModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertGenericOpenAIAPIRankerModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenerigOpenAIRankerModelsConfigurationController/insertGenericOpenAIAPIRankerModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGenericOpenAIAPIRankerModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGenericOpenAIAPIRankerModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGenericOpenAIAPIRankerModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGenericOpenAIAPIRankerModelConfig updateGenericOpenAIAPIRankerModelConfig(GenericOpenAIAPIRankerModelConfig body) throws RestClientException {
        return updateGenericOpenAIAPIRankerModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGenericOpenAIAPIRankerModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGenericOpenAIAPIRankerModelConfig> updateGenericOpenAIAPIRankerModelConfigWithHttpInfo(GenericOpenAIAPIRankerModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateGenericOpenAIAPIRankerModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GenerigOpenAIRankerModelsConfigurationController/updateGenericOpenAIAPIRankerModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGenericOpenAIAPIRankerModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGenericOpenAIAPIRankerModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
