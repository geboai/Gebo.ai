package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;

import java.io.File;
import gebo.microservices.api.client.brain.model.GChatUploadFileTypes;
import gebo.microservices.api.client.brain.model.OperationStatusListUserUploadedContent;
import gebo.microservices.api.client.brain.model.OperationStatusUploadsWithNewChat;
import gebo.microservices.api.client.brain.model.UserUploadedContent;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-28T08:23:41.358449505+01:00[Europe/Rome]")

public class GeboUserChatUploadsControllerApi {
    private ApiClient apiClient;

     public GeboUserChatUploadsControllerApi() {
        this(new ApiClient());
    }
    public GeboUserChatUploadsControllerApi(ApiClient apiClient) {
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
     * @param userSessionCode  (optional)
     * @param chatProfileCode  (optional)
     * @return GChatUploadFileTypes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GChatUploadFileTypes acceptedFileTypes(String userSessionCode, String chatProfileCode) throws RestClientException {
        return acceptedFileTypesWithHttpInfo(userSessionCode, chatProfileCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userSessionCode  (optional)
     * @param chatProfileCode  (optional)
     * @return ResponseEntity&lt;GChatUploadFileTypes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GChatUploadFileTypes> acceptedFileTypesWithHttpInfo(String userSessionCode, String chatProfileCode) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/acceptedFileTypes").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userSessionCode", userSessionCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "chatProfileCode", chatProfileCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GChatUploadFileTypes> returnType = new ParameterizedTypeReference<GChatUploadFileTypes>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatModelCode  (required)
     * @param files  (optional)
     * @return OperationStatusUploadsWithNewChat
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusUploadsWithNewChat chatSessionCreateWithUpload(String chatModelCode, List<File> files) throws RestClientException {
        return chatSessionCreateWithUploadWithHttpInfo(chatModelCode, files).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatModelCode  (required)
     * @param files  (optional)
     * @return ResponseEntity&lt;OperationStatusUploadsWithNewChat&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusUploadsWithNewChat> chatSessionCreateWithUploadWithHttpInfo(String chatModelCode, List<File> files) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatModelCode' is set
        if (chatModelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatModelCode' when calling chatSessionCreateWithUpload");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("chatModelCode", chatModelCode);
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/chatSessionCreateWithUpload/{chatModelCode}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (files != null)
            formParams.add("files[]", files);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusUploadsWithNewChat> returnType = new ParameterizedTypeReference<OperationStatusUploadsWithNewChat>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userSessionCode  (required)
     * @param files  (optional)
     * @return OperationStatusListUserUploadedContent
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListUserUploadedContent chatSessionUpload(String userSessionCode, List<File> files) throws RestClientException {
        return chatSessionUploadWithHttpInfo(userSessionCode, files).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userSessionCode  (required)
     * @param files  (optional)
     * @return ResponseEntity&lt;OperationStatusListUserUploadedContent&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListUserUploadedContent> chatSessionUploadWithHttpInfo(String userSessionCode, List<File> files) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userSessionCode' is set
        if (userSessionCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userSessionCode' when calling chatSessionUpload");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userSessionCode", userSessionCode);
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/chatSessionUpload/{userSessionCode}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (files != null)
            formParams.add("files[]", files);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusListUserUploadedContent> returnType = new ParameterizedTypeReference<OperationStatusListUserUploadedContent>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param userSessionCode  (required)
     * @return OperationStatusListUserUploadedContent
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListUserUploadedContent deleteSessionUploads(List<UserUploadedContent> body, String userSessionCode) throws RestClientException {
        return deleteSessionUploadsWithHttpInfo(body, userSessionCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param userSessionCode  (required)
     * @return ResponseEntity&lt;OperationStatusListUserUploadedContent&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListUserUploadedContent> deleteSessionUploadsWithHttpInfo(List<UserUploadedContent> body, String userSessionCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteSessionUploads");
        }
        // verify the required parameter 'userSessionCode' is set
        if (userSessionCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userSessionCode' when calling deleteSessionUploads");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userSessionCode", userSessionCode);
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/deleteSessionUploads/{userSessionCode}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListUserUploadedContent> returnType = new ParameterizedTypeReference<OperationStatusListUserUploadedContent>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @param files  (optional)
     * @return OperationStatusUploadsWithNewChat
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusUploadsWithNewChat ragChatSessionCreateWithUpload(String chatProfileCode, List<File> files) throws RestClientException {
        return ragChatSessionCreateWithUploadWithHttpInfo(chatProfileCode, files).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @param files  (optional)
     * @return ResponseEntity&lt;OperationStatusUploadsWithNewChat&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusUploadsWithNewChat> ragChatSessionCreateWithUploadWithHttpInfo(String chatProfileCode, List<File> files) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatProfileCode' is set
        if (chatProfileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatProfileCode' when calling ragChatSessionCreateWithUpload");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("chatProfileCode", chatProfileCode);
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/ragChatSessionCreateWithUpload/{chatProfileCode}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (files != null)
            formParams.add("files[]", files);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusUploadsWithNewChat> returnType = new ParameterizedTypeReference<OperationStatusUploadsWithNewChat>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userSessionCode  (required)
     * @param uploadedContentId  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void serveContent(String userSessionCode, String uploadedContentId) throws RestClientException {
        serveContentWithHttpInfo(userSessionCode, uploadedContentId);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userSessionCode  (required)
     * @param uploadedContentId  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> serveContentWithHttpInfo(String userSessionCode, String uploadedContentId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userSessionCode' is set
        if (userSessionCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userSessionCode' when calling serveContent");
        }
        // verify the required parameter 'uploadedContentId' is set
        if (uploadedContentId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'uploadedContentId' when calling serveContent");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userSessionCode", userSessionCode);
        uriVariables.put("uploadedContentId", uploadedContentId);
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/serveContent/{userSessionCode}/{uploadedContentId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
