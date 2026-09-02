package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.ConfigurationEntryGBaseTranscriptModelConfig;
import ai.gebo.monolithic.api.client.model.GTranscriptModelType;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-08-29T21:00:52.028175694+02:00[Europe/Rome]")

public class TranscriptModelsControllerApi {
    private ApiClient apiClient;

     public TranscriptModelsControllerApi() {
        this(new ApiClient());
    }
    public TranscriptModelsControllerApi(ApiClient apiClient) {
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
     * @return List&lt;ConfigurationEntryGBaseTranscriptModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ConfigurationEntryGBaseTranscriptModelConfig> getRuntimeConfiguredTranscriptModels(String modelTypeCode) throws RestClientException {
        return getRuntimeConfiguredTranscriptModelsWithHttpInfo(modelTypeCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelTypeCode  (optional)
     * @return ResponseEntity&lt;List&lt;ConfigurationEntryGBaseTranscriptModelConfig&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ConfigurationEntryGBaseTranscriptModelConfig>> getRuntimeConfiguredTranscriptModelsWithHttpInfo(String modelTypeCode) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/TranscriptModelsController/getRuntimeConfiguredTranscriptModels").build().toUriString();
        
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

        ParameterizedTypeReference<List<ConfigurationEntryGBaseTranscriptModelConfig>> returnType = new ParameterizedTypeReference<List<ConfigurationEntryGBaseTranscriptModelConfig>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GTranscriptModelType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GTranscriptModelType> getTranscriptModelTypes() throws RestClientException {
        return getTranscriptModelTypesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GTranscriptModelType&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GTranscriptModelType>> getTranscriptModelTypesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/TranscriptModelsController/getTranscriptModelTypes").build().toUriString();
        
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

        ParameterizedTypeReference<List<GTranscriptModelType>> returnType = new ParameterizedTypeReference<List<GTranscriptModelType>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
