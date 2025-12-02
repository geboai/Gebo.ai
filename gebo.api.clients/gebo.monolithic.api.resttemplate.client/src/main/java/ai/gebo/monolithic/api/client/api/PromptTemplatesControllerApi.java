package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.DefaultPromptForChatModelParam;
import ai.gebo.monolithic.api.client.model.DefaultPromptForChatModelReferenceParam;
import ai.gebo.monolithic.api.client.model.GPromptConfig;

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

public class PromptTemplatesControllerApi {
    private ApiClient apiClient;

     public PromptTemplatesControllerApi() {
        this(new ApiClient());
    }
    public PromptTemplatesControllerApi(ApiClient apiClient) {
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
     * @param ragPrompt  (required)
     * @return GPromptConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GPromptConfig getDefaultPrompt(Boolean ragPrompt) throws RestClientException {
        return getDefaultPromptWithHttpInfo(ragPrompt).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param ragPrompt  (required)
     * @return ResponseEntity&lt;GPromptConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GPromptConfig> getDefaultPromptWithHttpInfo(Boolean ragPrompt) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'ragPrompt' is set
        if (ragPrompt == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ragPrompt' when calling getDefaultPrompt");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/PromptTemplatesController/getDefaultPrompt").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ragPrompt", ragPrompt));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GPromptConfig> returnType = new ParameterizedTypeReference<GPromptConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GPromptConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GPromptConfig getDefaultPromptForChatModel(DefaultPromptForChatModelParam body) throws RestClientException {
        return getDefaultPromptForChatModelWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GPromptConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GPromptConfig> getDefaultPromptForChatModelWithHttpInfo(DefaultPromptForChatModelParam body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getDefaultPromptForChatModel");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/PromptTemplatesController/getDefaultPromptForChatModel").build().toUriString();
        
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

        ParameterizedTypeReference<GPromptConfig> returnType = new ParameterizedTypeReference<GPromptConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GPromptConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GPromptConfig getDefaultPromptForChatModelReference(DefaultPromptForChatModelReferenceParam body) throws RestClientException {
        return getDefaultPromptForChatModelReferenceWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GPromptConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GPromptConfig> getDefaultPromptForChatModelReferenceWithHttpInfo(DefaultPromptForChatModelReferenceParam body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getDefaultPromptForChatModelReference");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/PromptTemplatesController/getDefaultPromptForChatModelReference").build().toUriString();
        
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

        ParameterizedTypeReference<GPromptConfig> returnType = new ParameterizedTypeReference<GPromptConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
