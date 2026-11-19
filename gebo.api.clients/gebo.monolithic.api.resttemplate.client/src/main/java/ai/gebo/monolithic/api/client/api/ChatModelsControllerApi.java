package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.ChatModelInputs;
import ai.gebo.monolithic.api.client.model.ConfigurationEntryGBaseChatModelConfig;
import ai.gebo.monolithic.api.client.model.GChatModelType;
import ai.gebo.monolithic.api.client.model.GModelCarriedMediaTypes;
import ai.gebo.monolithic.api.client.model.OperationStatusListChatModelInputs;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-19T22:39:31.008494278+01:00[Europe/Rome]")

public class ChatModelsControllerApi {
    private ApiClient apiClient;

     public ChatModelsControllerApi() {
        this(new ApiClient());
    }
    public ChatModelsControllerApi(ApiClient apiClient) {
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
     * @return OperationStatusListChatModelInputs
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListChatModelInputs applyModelsMetadataInputModalities() throws RestClientException {
        return applyModelsMetadataInputModalitiesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;OperationStatusListChatModelInputs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListChatModelInputs> applyModelsMetadataInputModalitiesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ChatModelsController/applyModelsMetadataInputModalities").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListChatModelInputs> returnType = new ParameterizedTypeReference<OperationStatusListChatModelInputs>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelTypeCode  (required)
     * @param modelCode  (optional)
     * @return GModelCarriedMediaTypes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GModelCarriedMediaTypes getCarriedMediaTypes(String modelTypeCode, String modelCode) throws RestClientException {
        return getCarriedMediaTypesWithHttpInfo(modelTypeCode, modelCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelTypeCode  (required)
     * @param modelCode  (optional)
     * @return ResponseEntity&lt;GModelCarriedMediaTypes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GModelCarriedMediaTypes> getCarriedMediaTypesWithHttpInfo(String modelTypeCode, String modelCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'modelTypeCode' is set
        if (modelTypeCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelTypeCode' when calling getCarriedMediaTypes");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ChatModelsController/getCarriedMediaTypes").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "modelTypeCode", modelTypeCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "modelCode", modelCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GModelCarriedMediaTypes> returnType = new ParameterizedTypeReference<GModelCarriedMediaTypes>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GChatModelType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GChatModelType> getChatModelTypes() throws RestClientException {
        return getChatModelTypesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GChatModelType&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GChatModelType>> getChatModelTypesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ChatModelsController/getChatModelTypes").build().toUriString();
        
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

        ParameterizedTypeReference<List<GChatModelType>> returnType = new ParameterizedTypeReference<List<GChatModelType>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;ChatModelInputs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ChatModelInputs> getChatModelsInputs() throws RestClientException {
        return getChatModelsInputsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;ChatModelInputs&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ChatModelInputs>> getChatModelsInputsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ChatModelsController/getChatModelsInputs").build().toUriString();
        
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

        ParameterizedTypeReference<List<ChatModelInputs>> returnType = new ParameterizedTypeReference<List<ChatModelInputs>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelTypeCode  (optional)
     * @return List&lt;ConfigurationEntryGBaseChatModelConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ConfigurationEntryGBaseChatModelConfig> getRuntimeConfiguredChatModels(String modelTypeCode) throws RestClientException {
        return getRuntimeConfiguredChatModelsWithHttpInfo(modelTypeCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelTypeCode  (optional)
     * @return ResponseEntity&lt;List&lt;ConfigurationEntryGBaseChatModelConfig&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ConfigurationEntryGBaseChatModelConfig>> getRuntimeConfiguredChatModelsWithHttpInfo(String modelTypeCode) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ChatModelsController/getRuntimeConfiguredChatModels").build().toUriString();
        
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

        ParameterizedTypeReference<List<ConfigurationEntryGBaseChatModelConfig>> returnType = new ParameterizedTypeReference<List<ConfigurationEntryGBaseChatModelConfig>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
