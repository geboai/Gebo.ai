package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GBedrockTranscriptModelConfig;
import ai.gebo.monolithic.api.client.model.OperationStatusBoolean;
import ai.gebo.monolithic.api.client.model.OperationStatusGBedrockTranscriptModelConfig;
import ai.gebo.monolithic.api.client.model.OperationStatusListGBedrockTranscriptModelChoice;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-08-08T08:46:40.992038400+02:00[Europe/Rome]")

public class BedrockTranscriptModelsConfigurationControllerApi {
    private ApiClient apiClient;

     public BedrockTranscriptModelsConfigurationControllerApi() {
        this(new ApiClient());
    }
    public BedrockTranscriptModelsConfigurationControllerApi(ApiClient apiClient) {
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
    public OperationStatusBoolean deleteBedrockTranscriptModelConfig(GBedrockTranscriptModelConfig body) throws RestClientException {
        return deleteBedrockTranscriptModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusBoolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusBoolean> deleteBedrockTranscriptModelConfigWithHttpInfo(GBedrockTranscriptModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteBedrockTranscriptModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/BedrockTranscriptModelsConfigurationController/deleteBedrockTranscriptModelConfig").build().toUriString();
        
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
     * @return GBedrockTranscriptModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GBedrockTranscriptModelConfig findBedrockTranscriptModelConfigByCode(String code) throws RestClientException {
        return findBedrockTranscriptModelConfigByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GBedrockTranscriptModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GBedrockTranscriptModelConfig> findBedrockTranscriptModelConfigByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findBedrockTranscriptModelConfigByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/BedrockTranscriptModelsConfigurationController/findBedrockTranscriptModelConfigByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GBedrockTranscriptModelConfig> returnType = new ParameterizedTypeReference<GBedrockTranscriptModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusListGBedrockTranscriptModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGBedrockTranscriptModelChoice getBedrockTranscriptModels(GBedrockTranscriptModelConfig body) throws RestClientException {
        return getBedrockTranscriptModelsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusListGBedrockTranscriptModelChoice&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGBedrockTranscriptModelChoice> getBedrockTranscriptModelsWithHttpInfo(GBedrockTranscriptModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getBedrockTranscriptModels");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/BedrockTranscriptModelsConfigurationController/getBedrockTranscriptModels").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListGBedrockTranscriptModelChoice> returnType = new ParameterizedTypeReference<OperationStatusListGBedrockTranscriptModelChoice>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGBedrockTranscriptModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGBedrockTranscriptModelConfig insertBedrockTranscriptModelConfig(GBedrockTranscriptModelConfig body) throws RestClientException {
        return insertBedrockTranscriptModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGBedrockTranscriptModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGBedrockTranscriptModelConfig> insertBedrockTranscriptModelConfigWithHttpInfo(GBedrockTranscriptModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertBedrockTranscriptModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/BedrockTranscriptModelsConfigurationController/insertBedrockTranscriptModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGBedrockTranscriptModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGBedrockTranscriptModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGBedrockTranscriptModelConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGBedrockTranscriptModelConfig updateBedrockTranscriptModelConfig(GBedrockTranscriptModelConfig body) throws RestClientException {
        return updateBedrockTranscriptModelConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGBedrockTranscriptModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGBedrockTranscriptModelConfig> updateBedrockTranscriptModelConfigWithHttpInfo(GBedrockTranscriptModelConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateBedrockTranscriptModelConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/BedrockTranscriptModelsConfigurationController/updateBedrockTranscriptModelConfig").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGBedrockTranscriptModelConfig> returnType = new ParameterizedTypeReference<OperationStatusGBedrockTranscriptModelConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
