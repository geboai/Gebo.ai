package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;

import gebo.microservices.api.client.brain.model.GOpenAITextToSpeechModelConfig;
import gebo.microservices.api.client.brain.model.OperationStatusBoolean;
import gebo.microservices.api.client.brain.model.OperationStatusGOpenAITextToSpeechModelConfig;
import gebo.microservices.api.client.brain.model.OperationStatusListGOpenAITextToSpeechModelChoice;

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

public class OpenAiTextToSpeechModelsConfigurationControllerApi {
    private ApiClient apiClient;

     public OpenAiTextToSpeechModelsConfigurationControllerApi() {
        this(new ApiClient());
    }
    public OpenAiTextToSpeechModelsConfigurationControllerApi(ApiClient apiClient) {
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
    public OperationStatusBoolean deleteOpenAITextToSpeechModelConfig(GOpenAITextToSpeechModelConfig body) throws RestClientException {
        return deleteOpenAITextToSpeechModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusBoolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusBoolean> deleteOpenAITextToSpeechModelConfigWithHttpInfo(GOpenAITextToSpeechModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteOpenAITextToSpeechModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OpenAITextToSpeechModelsConfigurationController/deleteOpenAITextToSpeechModelConfig").build().toUriString();
        
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
     * @return GOpenAITextToSpeechModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GOpenAITextToSpeechModelConfig findOpenAITextToSpeechModelConfigByCode(Object code) throws RestClientException {
        return findOpenAITextToSpeechModelConfigByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GOpenAITextToSpeechModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GOpenAITextToSpeechModelConfig> findOpenAITextToSpeechModelConfigByCodeWithHttpInfo(Object code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findOpenAITextToSpeechModelConfigByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OpenAITextToSpeechModelsConfigurationController/findOpenAITextToSpeechModelConfigByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GOpenAITextToSpeechModelConfig> returnType = new ParameterizedTypeReference<GOpenAITextToSpeechModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusListGOpenAITextToSpeechModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGOpenAITextToSpeechModelChoice getOpenAITextToSpeechModels(GOpenAITextToSpeechModelConfig body) throws RestClientException {
        return getOpenAITextToSpeechModelsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusListGOpenAITextToSpeechModelChoice&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGOpenAITextToSpeechModelChoice> getOpenAITextToSpeechModelsWithHttpInfo(GOpenAITextToSpeechModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getOpenAITextToSpeechModels");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OpenAITextToSpeechModelsConfigurationController/getOpenAITextToSpeechModels").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListGOpenAITextToSpeechModelChoice> returnType = new ParameterizedTypeReference<OperationStatusListGOpenAITextToSpeechModelChoice>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGOpenAITextToSpeechModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGOpenAITextToSpeechModelConfig insertOpenAITextToSpeechModelConfig(GOpenAITextToSpeechModelConfig body) throws RestClientException {
        return insertOpenAITextToSpeechModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGOpenAITextToSpeechModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGOpenAITextToSpeechModelConfig> insertOpenAITextToSpeechModelConfigWithHttpInfo(GOpenAITextToSpeechModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertOpenAITextToSpeechModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OpenAITextToSpeechModelsConfigurationController/insertOpenAITextToSpeechModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGOpenAITextToSpeechModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGOpenAITextToSpeechModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGOpenAITextToSpeechModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGOpenAITextToSpeechModelConfig updateOpenAITextToSpeechModelConfig(GOpenAITextToSpeechModelConfig body) throws RestClientException {
        return updateOpenAITextToSpeechModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGOpenAITextToSpeechModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGOpenAITextToSpeechModelConfig> updateOpenAITextToSpeechModelConfigWithHttpInfo(GOpenAITextToSpeechModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateOpenAITextToSpeechModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OpenAITextToSpeechModelsConfigurationController/updateOpenAITextToSpeechModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGOpenAITextToSpeechModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGOpenAITextToSpeechModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
