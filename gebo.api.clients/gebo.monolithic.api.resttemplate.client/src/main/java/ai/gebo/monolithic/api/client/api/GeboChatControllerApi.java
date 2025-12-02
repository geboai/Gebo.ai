package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import java.io.File;
import ai.gebo.monolithic.api.client.model.GBaseChatModelChoice;
import ai.gebo.monolithic.api.client.model.GUserChatInfo;
import ai.gebo.monolithic.api.client.model.GeboChatRequest;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.api.client.model.GeboChatUserInfo;
import ai.gebo.monolithic.api.client.model.GeboTemplatedChatResponseRichResponse;
import ai.gebo.monolithic.api.client.model.ModelProviderCapabilities;
import ai.gebo.monolithic.api.client.model.ServerSentEventString;
import ai.gebo.monolithic.api.client.model.SpeechRequest;
import ai.gebo.monolithic.api.client.model.TranscriptResponse;

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
     * @return GUserChatInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GUserChatInfo createCleanChatByModelCode(String modelCode) throws RestClientException {
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
    public ResponseEntity<GUserChatInfo> createCleanChatByModelCodeWithHttpInfo(String modelCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'modelCode' is set
        if (modelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelCode' when calling createCleanChatByModelCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/createCleanChatByModelCode").build().toUriString();
        
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
     * @param modelCode  (required)
     * @return GBaseChatModelChoice
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GBaseChatModelChoice getChatModelMetaInfos(String modelCode) throws RestClientException {
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
    public ResponseEntity<GBaseChatModelChoice> getChatModelMetaInfosWithHttpInfo(String modelCode) throws RestClientException {
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
    public GeboChatUserInfo getChatModelUserInfo(String modelCode) throws RestClientException {
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
    public ResponseEntity<GeboChatUserInfo> getChatModelUserInfoWithHttpInfo(String modelCode) throws RestClientException {
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
    public ModelProviderCapabilities getProviderCapabilities(String modelCode) throws RestClientException {
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
    public ResponseEntity<ModelProviderCapabilities> getProviderCapabilitiesWithHttpInfo(String modelCode) throws RestClientException {
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
     * @param body  (required)
     * @return GeboTemplatedChatResponseRichResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GeboTemplatedChatResponseRichResponse richChat(GeboChatRequest body) throws RestClientException {
        return richChatWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GeboTemplatedChatResponseRichResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GeboTemplatedChatResponseRichResponse> richChatWithHttpInfo(GeboChatRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling richChat");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/richChat").build().toUriString();
        
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
     * @param modelCode  (required)
     * @return File
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public File speechText(SpeechRequest body, String modelCode) throws RestClientException {
        return speechTextWithHttpInfo(body, modelCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param modelCode  (required)
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> speechTextWithHttpInfo(SpeechRequest body, String modelCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling speechText");
        }
        // verify the required parameter 'modelCode' is set
        if (modelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelCode' when calling speechText");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/speechText").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "modelCode", modelCode));

        final String[] accepts = { 
            "application/octet-stream"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<File> returnType = new ParameterizedTypeReference<File>() {};
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
    public List<ServerSentEventString> streamResponse(GeboChatRequest body) throws RestClientException {
        return streamResponseWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;ServerSentEventString&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ServerSentEventString>> streamResponseWithHttpInfo(GeboChatRequest body) throws RestClientException {
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
    public GUserChatInfo suggestChatDescription(String id) throws RestClientException {
        return suggestChatDescriptionWithHttpInfo(id).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param id  (required)
     * @return ResponseEntity&lt;GUserChatInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GUserChatInfo> suggestChatDescriptionWithHttpInfo(String id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling suggestChatDescription");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/suggestChatDescription").build().toUriString();
        
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
     * @param modelCode  (required)
     * @return TranscriptResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public TranscriptResponse transcriptText(String modelCode) throws RestClientException {
        return transcriptTextWithHttpInfo(modelCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @return ResponseEntity&lt;TranscriptResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TranscriptResponse> transcriptTextWithHttpInfo(String modelCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'modelCode' is set
        if (modelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelCode' when calling transcriptText");
        }
        String path = UriComponentsBuilder.fromPath("/api/users/GeboDirectModelChatController/transcriptText").build().toUriString();
        
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

        ParameterizedTypeReference<TranscriptResponse> returnType = new ParameterizedTypeReference<TranscriptResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
