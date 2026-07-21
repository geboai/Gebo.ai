package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;

import gebo.microservices.api.client.brain.model.GBaseChatModelChoice;
import gebo.microservices.api.client.brain.model.GeboChatRequest;
import gebo.microservices.api.client.brain.model.GeboChatResponse;
import gebo.microservices.api.client.brain.model.GeboChatUserInfo;
import gebo.microservices.api.client.brain.model.ModelProviderCapabilities;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-21T14:57:36.399842475+02:00[Europe/Rome]")

public class GeboChatControllerApi {
    private ApiClient apiClient;

     public GeboChatControllerApi() {
        this(new ApiClient());
    }
    public GeboChatControllerApi(ApiClient apiClient) {
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
     * @return GeboChatResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GeboChatResponse chat(GeboChatRequest body) throws RestClientException {
        return chatWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GeboChatResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GeboChatResponse> chatWithHttpInfo(GeboChatRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling chat");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/chat").build().toUriString();
        
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

        ParameterizedTypeReference<GeboChatResponse> returnType = new ParameterizedTypeReference<GeboChatResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return GBaseChatModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GBaseChatModelChoice getChatModelMetaInfos(Object modelCode) throws RestClientException {
        return getChatModelMetaInfosWithHttpInfo(modelCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return ResponseEntity&lt;GBaseChatModelChoice&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GBaseChatModelChoice> getChatModelMetaInfosWithHttpInfo(Object modelCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'modelCode' is set
        if (modelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelCode' when calling getChatModelMetaInfos");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/getChatModelMetaInfos").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "modelCode", modelCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GBaseChatModelChoice> returnType = new ParameterizedTypeReference<GBaseChatModelChoice>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return GeboChatUserInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GeboChatUserInfo getChatModelUserInfo(Object modelCode) throws RestClientException {
        return getChatModelUserInfoWithHttpInfo(modelCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return ResponseEntity&lt;GeboChatUserInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GeboChatUserInfo> getChatModelUserInfoWithHttpInfo(Object modelCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'modelCode' is set
        if (modelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelCode' when calling getChatModelUserInfo");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/getChatModelUserInfo").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "modelCode", modelCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GeboChatUserInfo> returnType = new ParameterizedTypeReference<GeboChatUserInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return ModelProviderCapabilities
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ModelProviderCapabilities getProviderCapabilities(Object modelCode) throws RestClientException {
        return getProviderCapabilitiesWithHttpInfo(modelCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return ResponseEntity&lt;ModelProviderCapabilities&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ModelProviderCapabilities> getProviderCapabilitiesWithHttpInfo(Object modelCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'modelCode' is set
        if (modelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelCode' when calling getProviderCapabilities");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/getProviderCapabilities").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "modelCode", modelCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ModelProviderCapabilities> returnType = new ParameterizedTypeReference<ModelProviderCapabilities>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object getVisibleKnowledgeBases() throws RestClientException {
        return getVisibleKnowledgeBasesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getVisibleKnowledgeBasesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/getVisibleKnowledgeBases").build().toUriString();
        
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
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object streamResponse(GeboChatRequest body) throws RestClientException {
        return streamResponseWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> streamResponseWithHttpInfo(GeboChatRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling streamResponse");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/streamResponse").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "text/event-stream"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
