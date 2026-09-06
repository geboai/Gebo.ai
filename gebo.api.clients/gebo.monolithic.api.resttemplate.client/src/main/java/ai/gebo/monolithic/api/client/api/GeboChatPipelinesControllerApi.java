package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GeboChatMessageEnvelope;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.api.client.model.PipelineChatMenu;
import ai.gebo.monolithic.api.client.model.PipelineRequestBody;

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

public class GeboChatPipelinesControllerApi {
    private ApiClient apiClient;

     public GeboChatPipelinesControllerApi() {
        this(new ApiClient());
    }
    public GeboChatPipelinesControllerApi(ApiClient apiClient) {
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
     * @param pipelineCode  (optional)
     * @return GeboChatResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GeboChatResponse executeChatPipeline(PipelineRequestBody body, String pipelineCode) throws RestClientException {
        return executeChatPipelineWithHttpInfo(body, pipelineCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param pipelineCode  (optional)
     * @return ResponseEntity&lt;GeboChatResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GeboChatResponse> executeChatPipelineWithHttpInfo(PipelineRequestBody body, String pipelineCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling executeChatPipeline");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatPipelinesController/executeChatPipeline").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pipelineCode", pipelineCode));

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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GeboChatResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GeboChatResponse executeDefaultChatPipeline(PipelineRequestBody body) throws RestClientException {
        return executeDefaultChatPipelineWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GeboChatResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GeboChatResponse> executeDefaultChatPipelineWithHttpInfo(PipelineRequestBody body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling executeDefaultChatPipeline");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatPipelinesController/executeDefaultChatPipeline").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return List&lt;PipelineChatMenu&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<PipelineChatMenu> getDefaultPersonalPipelinesChatMenu(String chatProfileCode) throws RestClientException {
        return getDefaultPersonalPipelinesChatMenuWithHttpInfo(chatProfileCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @return ResponseEntity&lt;List&lt;PipelineChatMenu&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<PipelineChatMenu>> getDefaultPersonalPipelinesChatMenuWithHttpInfo(String chatProfileCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatProfileCode' is set
        if (chatProfileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatProfileCode' when calling getDefaultPersonalPipelinesChatMenu");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatPipelinesController/defaultPersonalPipelinesChatMenu").build().toUriString();
        
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

        ParameterizedTypeReference<List<PipelineChatMenu>> returnType = new ParameterizedTypeReference<List<PipelineChatMenu>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @param pipelineCode  (optional)
     * @return List&lt;PipelineChatMenu&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<PipelineChatMenu> getPersonalPipelinesChatMenu(String chatProfileCode, String pipelineCode) throws RestClientException {
        return getPersonalPipelinesChatMenuWithHttpInfo(chatProfileCode, pipelineCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @param pipelineCode  (optional)
     * @return ResponseEntity&lt;List&lt;PipelineChatMenu&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<PipelineChatMenu>> getPersonalPipelinesChatMenuWithHttpInfo(String chatProfileCode, String pipelineCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatProfileCode' is set
        if (chatProfileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatProfileCode' when calling getPersonalPipelinesChatMenu");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatPipelinesController/personalPipelinesChatMenu").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pipelineCode", pipelineCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "chatProfileCode", chatProfileCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<PipelineChatMenu>> returnType = new ParameterizedTypeReference<List<PipelineChatMenu>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userChatContextCode  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void stopChatPipeline(String userChatContextCode) throws RestClientException {
        stopChatPipelineWithHttpInfo(userChatContextCode);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userChatContextCode  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> stopChatPipelineWithHttpInfo(String userChatContextCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userChatContextCode' is set
        if (userChatContextCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userChatContextCode' when calling stopChatPipeline");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatPipelinesController/stopChatPipeline").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param pipelineCode  (optional)
     * @return List&lt;GeboChatMessageEnvelope&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GeboChatMessageEnvelope> streamChatPipeline(PipelineRequestBody body, String pipelineCode) throws RestClientException {
        return streamChatPipelineWithHttpInfo(body, pipelineCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param pipelineCode  (optional)
     * @return ResponseEntity&lt;List&lt;GeboChatMessageEnvelope&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GeboChatMessageEnvelope>> streamChatPipelineWithHttpInfo(PipelineRequestBody body, String pipelineCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling streamChatPipeline");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatPipelinesController/streamChatPipeline").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pipelineCode", pipelineCode));

        final String[] accepts = { 
            "text/event-stream"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GeboChatMessageEnvelope>> returnType = new ParameterizedTypeReference<List<GeboChatMessageEnvelope>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;GeboChatMessageEnvelope&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GeboChatMessageEnvelope> streamDefaultChatPipeline(PipelineRequestBody body) throws RestClientException {
        return streamDefaultChatPipelineWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;GeboChatMessageEnvelope&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GeboChatMessageEnvelope>> streamDefaultChatPipelineWithHttpInfo(PipelineRequestBody body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling streamDefaultChatPipeline");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatPipelinesController/streamDefaultChatPipeline").build().toUriString();
        
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

        ParameterizedTypeReference<List<GeboChatMessageEnvelope>> returnType = new ParameterizedTypeReference<List<GeboChatMessageEnvelope>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
