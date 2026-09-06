package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.ConfigurationEntryGBaseTextToSpeachModelConfig;
import ai.gebo.monolithic.api.client.model.GTextToSpeechModelType;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-09-06T14:48:17.016141500+02:00[Europe/Rome]")

public class TextToSpeechModelsControllerApi {
    private ApiClient apiClient;

     public TextToSpeechModelsControllerApi() {
        this(new ApiClient());
    }
    public TextToSpeechModelsControllerApi(ApiClient apiClient) {
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
     * @param modelTypeCode  (optional)
     * @return List&lt;ConfigurationEntryGBaseTextToSpeachModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ConfigurationEntryGBaseTextToSpeachModelConfig> getRuntimeConfiguredTextToSpeechModels(String modelTypeCode) throws RestClientException {
        return getRuntimeConfiguredTextToSpeechModelsWithHttpInfo(modelTypeCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelTypeCode  (optional)
     * @return ResponseEntity&lt;List&lt;ConfigurationEntryGBaseTextToSpeachModelConfig&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ConfigurationEntryGBaseTextToSpeachModelConfig>> getRuntimeConfiguredTextToSpeechModelsWithHttpInfo(String modelTypeCode) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/TextToSpeechModelsController/getRuntimeConfiguredTextToSpeechModels").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "modelTypeCode", modelTypeCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<ConfigurationEntryGBaseTextToSpeachModelConfig>> returnType = new ParameterizedTypeReference<List<ConfigurationEntryGBaseTextToSpeachModelConfig>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GTextToSpeechModelType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GTextToSpeechModelType> getTextToSpeechModelTypes() throws RestClientException {
        return getTextToSpeechModelTypesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GTextToSpeechModelType&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GTextToSpeechModelType>> getTextToSpeechModelTypesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/TextToSpeechModelsController/getTextToSpeechModelTypes").build().toUriString();
        
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

        ParameterizedTypeReference<List<GTextToSpeechModelType>> returnType = new ParameterizedTypeReference<List<GTextToSpeechModelType>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
