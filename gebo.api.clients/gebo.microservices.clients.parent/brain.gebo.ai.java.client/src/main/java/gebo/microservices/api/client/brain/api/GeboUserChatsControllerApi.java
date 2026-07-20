package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;

import gebo.microservices.api.client.brain.model.ChatInfosByQbeParam;
import gebo.microservices.api.client.brain.model.ChatUIOptions;
import gebo.microservices.api.client.brain.model.GLookupEntry;
import gebo.microservices.api.client.brain.model.GUserChatInfo;
import gebo.microservices.api.client.brain.model.PageGUserChatInfo;
import gebo.microservices.api.client.brain.model.UserChatHistory;

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

public class GeboUserChatsControllerApi {
    private ApiClient apiClient;

     public GeboUserChatsControllerApi() {
        this(new ApiClient());
    }
    public GeboUserChatsControllerApi(ApiClient apiClient) {
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
     * @return GLookupEntry
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GLookupEntry changeChatDescription(GLookupEntry body) throws RestClientException {
        return changeChatDescriptionWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GLookupEntry&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GLookupEntry> changeChatDescriptionWithHttpInfo(GLookupEntry body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling changeChatDescription");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/changeChatDescription").build().toUriString();
        
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

        ParameterizedTypeReference<GLookupEntry> returnType = new ParameterizedTypeReference<GLookupEntry>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return GUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GUserChatInfo createCleanChatByChatProfileCode(Object chatProfileCode) throws RestClientException {
        return createCleanChatByChatProfileCodeWithHttpInfo(chatProfileCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return ResponseEntity&lt;GUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GUserChatInfo> createCleanChatByChatProfileCodeWithHttpInfo(Object chatProfileCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatProfileCode' is set
        if (chatProfileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatProfileCode' when calling createCleanChatByChatProfileCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/createCleanChatByChatProfileCode").build().toUriString();
        
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

        ParameterizedTypeReference<GUserChatInfo> returnType = new ParameterizedTypeReference<GUserChatInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return GUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GUserChatInfo createCleanChatByModelCode(Object modelCode) throws RestClientException {
        return createCleanChatByModelCodeWithHttpInfo(modelCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return ResponseEntity&lt;GUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GUserChatInfo> createCleanChatByModelCodeWithHttpInfo(Object modelCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'modelCode' is set
        if (modelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelCode' when calling createCleanChatByModelCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/createCleanChatByModelCode").build().toUriString();
        
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

        ParameterizedTypeReference<GUserChatInfo> returnType = new ParameterizedTypeReference<GUserChatInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userChatContextCode  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteChat(Object userChatContextCode) throws RestClientException {
        deleteChatWithHttpInfo(userChatContextCode);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userChatContextCode  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteChatWithHttpInfo(Object userChatContextCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userChatContextCode' is set
        if (userChatContextCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userChatContextCode' when calling deleteChat");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/deleteChat").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userChatContextCode", userChatContextCode));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userContextCode  (required)
     * @param responseId  (required)
     * @param format  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void exportResponse2file(Object userContextCode, Object responseId, Object format) throws RestClientException {
        exportResponse2fileWithHttpInfo(userContextCode, responseId, format);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userContextCode  (required)
     * @param responseId  (required)
     * @param format  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> exportResponse2fileWithHttpInfo(Object userContextCode, Object responseId, Object format) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userContextCode' is set
        if (userContextCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userContextCode' when calling exportResponse2file");
        }
        // verify the required parameter 'responseId' is set
        if (responseId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'responseId' when calling exportResponse2file");
        }
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'format' when calling exportResponse2file");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/exportResponse2file").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userContextCode", userContextCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "responseId", responseId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "format", format));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return UserChatHistory
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserChatHistory getChatHistory(Object code) throws RestClientException {
        return getChatHistoryWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;UserChatHistory&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserChatHistory> getChatHistoryWithHttpInfo(Object code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling getChatHistory");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/getChatHistory").build().toUriString();
        
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

        ParameterizedTypeReference<UserChatHistory> returnType = new ParameterizedTypeReference<UserChatHistory>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param id  (required)
     * @return GUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GUserChatInfo getChatInfosByCode(Object id) throws RestClientException {
        return getChatInfosByCodeWithHttpInfo(id).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param id  (required)
     * @return ResponseEntity&lt;GUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GUserChatInfo> getChatInfosByCodeWithHttpInfo(Object id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getChatInfosByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/getChatInfosByCode").build().toUriString();
        
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
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PageGUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageGUserChatInfo getChatInfosByQbe(ChatInfosByQbeParam body) throws RestClientException {
        return getChatInfosByQbeWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PageGUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageGUserChatInfo> getChatInfosByQbeWithHttpInfo(ChatInfosByQbeParam body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getChatInfosByQbe");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/getChatInfosByQbe").build().toUriString();
        
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

        ParameterizedTypeReference<PageGUserChatInfo> returnType = new ParameterizedTypeReference<PageGUserChatInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object getMyChats() throws RestClientException {
        return getMyChatsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getMyChatsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/getMyChats").build().toUriString();
        
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
     * @param page  (required)
     * @param pageSize  (required)
     * @return PageGUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageGUserChatInfo getMyChatsPaged(Object page, Object pageSize) throws RestClientException {
        return getMyChatsPagedWithHttpInfo(page, pageSize).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param page  (required)
     * @param pageSize  (required)
     * @return ResponseEntity&lt;PageGUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageGUserChatInfo> getMyChatsPagedWithHttpInfo(Object page, Object pageSize) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'page' is set
        if (page == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'page' when calling getMyChatsPaged");
        }
        // verify the required parameter 'pageSize' is set
        if (pageSize == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pageSize' when calling getMyChatsPaged");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/getMyChatsPaged").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PageGUserChatInfo> returnType = new ParameterizedTypeReference<PageGUserChatInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ChatUIOptions
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ChatUIOptions getUIConfig() throws RestClientException {
        return getUIConfigWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;ChatUIOptions&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ChatUIOptions> getUIConfigWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/getUIConfig").build().toUriString();
        
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

        ParameterizedTypeReference<ChatUIOptions> returnType = new ParameterizedTypeReference<ChatUIOptions>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userChatContextCode  (required)
     * @return GUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GUserChatInfo suggestChatDescription(Object userChatContextCode) throws RestClientException {
        return suggestChatDescriptionWithHttpInfo(userChatContextCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userChatContextCode  (required)
     * @return ResponseEntity&lt;GUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GUserChatInfo> suggestChatDescriptionWithHttpInfo(Object userChatContextCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userChatContextCode' is set
        if (userChatContextCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userChatContextCode' when calling suggestChatDescription");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatsController/suggestChatDescription").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userChatContextCode", userChatContextCode));

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
