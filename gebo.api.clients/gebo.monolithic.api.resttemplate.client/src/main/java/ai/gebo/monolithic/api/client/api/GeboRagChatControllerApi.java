package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GBaseChatModelChoice;
import ai.gebo.monolithic.api.client.model.GChatProfileConfiguration;
import ai.gebo.monolithic.api.client.model.GUserChatInfo;
import ai.gebo.monolithic.api.client.model.GeboChatRequest;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.api.client.model.GeboChatUserInfo;
import ai.gebo.monolithic.api.client.model.GeboTemplatedChatResponseRichResponse;
import ai.gebo.monolithic.api.client.model.ModelProviderCapabilities;
import ai.gebo.monolithic.api.client.model.ServerSentEventString;

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

public class GeboRagChatControllerApi {
    private ApiClient apiClient;

     public GeboRagChatControllerApi() {
        this(new ApiClient());
    }
    public GeboRagChatControllerApi(ApiClient apiClient) {
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
     * @param profileCode  (required)
     * @return GUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GUserChatInfo createCleanRagChatByProfileCode(String profileCode) throws RestClientException {
        return createCleanRagChatByProfileCodeWithHttpInfo(profileCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param profileCode  (required)
     * @return ResponseEntity&lt;GUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GUserChatInfo> createCleanRagChatByProfileCodeWithHttpInfo(String profileCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'profileCode' is set
        if (profileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'profileCode' when calling createCleanRagChatByProfileCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/createCleanRagChatByProfileCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "profileCode", profileCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GUserChatInfo> returnType = new ParameterizedTypeReference<GUserChatInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return GeboChatUserInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GeboChatUserInfo getChatModelUserInfoByChatProfileCode(String chatProfileCode) throws RestClientException {
        return getChatModelUserInfoByChatProfileCodeWithHttpInfo(chatProfileCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return ResponseEntity&lt;GeboChatUserInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GeboChatUserInfo> getChatModelUserInfoByChatProfileCodeWithHttpInfo(String chatProfileCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatProfileCode' is set
        if (chatProfileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatProfileCode' when calling getChatModelUserInfoByChatProfileCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/getChatModelUserInfoByChatProfileCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "chatProfileCode", chatProfileCode));

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
     * @param chatProfileCode  (required)
     * @return GBaseChatModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GBaseChatModelChoice getChatProfileModelMetaInfos(String chatProfileCode) throws RestClientException {
        return getChatProfileModelMetaInfosWithHttpInfo(chatProfileCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return ResponseEntity&lt;GBaseChatModelChoice&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GBaseChatModelChoice> getChatProfileModelMetaInfosWithHttpInfo(String chatProfileCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatProfileCode' is set
        if (chatProfileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatProfileCode' when calling getChatProfileModelMetaInfos");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/getChatProfileModelMetaInfos").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "chatProfileCode", chatProfileCode));

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
     * @return List&lt;GChatProfileConfiguration&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GChatProfileConfiguration> getChatProfiles() throws RestClientException {
        return getChatProfilesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GChatProfileConfiguration&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GChatProfileConfiguration>> getChatProfilesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/profiles").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GChatProfileConfiguration>> returnType = new ParameterizedTypeReference<List<GChatProfileConfiguration>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return ModelProviderCapabilities
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ModelProviderCapabilities getProfileProviderModelCapabilities(String chatProfileCode) throws RestClientException {
        return getProfileProviderModelCapabilitiesWithHttpInfo(chatProfileCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return ResponseEntity&lt;ModelProviderCapabilities&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ModelProviderCapabilities> getProfileProviderModelCapabilitiesWithHttpInfo(String chatProfileCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatProfileCode' is set
        if (chatProfileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatProfileCode' when calling getProfileProviderModelCapabilities");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/getProfileProviderModelCapabilities").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "chatProfileCode", chatProfileCode));

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
     * @param body  (required)
     * @return GeboChatResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GeboChatResponse ragChat(GeboChatRequest body) throws RestClientException {
        return ragChatWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GeboChatResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GeboChatResponse> ragChatWithHttpInfo(GeboChatRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling ragChat");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/ragChat").build().toUriString();
        
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
     * @param body  (required)
     * @return GeboTemplatedChatResponseRichResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GeboTemplatedChatResponseRichResponse richRagChat(GeboChatRequest body) throws RestClientException {
        return richRagChatWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GeboTemplatedChatResponseRichResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GeboTemplatedChatResponseRichResponse> richRagChatWithHttpInfo(GeboChatRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling richRagChat");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/richRagChat").build().toUriString();
        
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

        ParameterizedTypeReference<GeboTemplatedChatResponseRichResponse> returnType = new ParameterizedTypeReference<GeboTemplatedChatResponseRichResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;ServerSentEventString&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ServerSentEventString> streamRagResponse(GeboChatRequest body) throws RestClientException {
        return streamRagResponseWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;ServerSentEventString&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ServerSentEventString>> streamRagResponseWithHttpInfo(GeboChatRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling streamRagResponse");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/streamRagResponse").build().toUriString();
        
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

        ParameterizedTypeReference<List<ServerSentEventString>> returnType = new ParameterizedTypeReference<List<ServerSentEventString>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param id  (required)
     * @return GUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GUserChatInfo suggestRagChatDescription(String id) throws RestClientException {
        return suggestRagChatDescriptionWithHttpInfo(id).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param id  (required)
     * @return ResponseEntity&lt;GUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GUserChatInfo> suggestRagChatDescriptionWithHttpInfo(String id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling suggestRagChatDescription");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboChatController/suggestRagChatDescription").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "id", id));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GUserChatInfo> returnType = new ParameterizedTypeReference<GUserChatInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
