package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import java.io.File;
import ai.gebo.monolithic.api.client.model.OperationStatusChatSessionCreationWithUpload;
import ai.gebo.monolithic.api.client.model.OperationStatusListUserUploadedContent;

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
     * @param modelCode  (required)
     * @param files  (optional)
     * @return OperationStatusChatSessionCreationWithUpload
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusChatSessionCreationWithUpload chatSessionCreateWithUpload(String modelCode, List<File> files) throws RestClientException {
        return chatSessionCreateWithUploadWithHttpInfo(modelCode, files).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param modelCode  (required)
     * @param files  (optional)
     * @return ResponseEntity&lt;OperationStatusChatSessionCreationWithUpload&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusChatSessionCreationWithUpload> chatSessionCreateWithUploadWithHttpInfo(String modelCode, List<File> files) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'modelCode' is set
        if (modelCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'modelCode' when calling chatSessionCreateWithUpload");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("modelCode", modelCode);
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/chatSessionCreateWithUpload/{modelCode}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (files != null)
            formParams.add("files[]", files);

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusChatSessionCreationWithUpload> returnType = new ParameterizedTypeReference<OperationStatusChatSessionCreationWithUpload>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
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
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/chatSessionUpload/{userSessionCode}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (files != null)
            formParams.add("files[]", files);

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusListUserUploadedContent> returnType = new ParameterizedTypeReference<OperationStatusListUserUploadedContent>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userSessionCode  (required)
     * @return OperationStatusListUserUploadedContent
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListUserUploadedContent deleteSessionUploads(String userSessionCode) throws RestClientException {
        return deleteSessionUploadsWithHttpInfo(userSessionCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userSessionCode  (required)
     * @return ResponseEntity&lt;OperationStatusListUserUploadedContent&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListUserUploadedContent> deleteSessionUploadsWithHttpInfo(String userSessionCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userSessionCode' is set
        if (userSessionCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userSessionCode' when calling deleteSessionUploads");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userSessionCode", userSessionCode);
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/deleteSessionUploads").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "multipart/form-data"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusListUserUploadedContent> returnType = new ParameterizedTypeReference<OperationStatusListUserUploadedContent>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @param files  (optional)
     * @return OperationStatusChatSessionCreationWithUpload
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusChatSessionCreationWithUpload ragChatSessionCreateWithUpload(String chatProfileCode, List<File> files) throws RestClientException {
        return ragChatSessionCreateWithUploadWithHttpInfo(chatProfileCode, files).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatProfileCode  (required)
     * @param files  (optional)
     * @return ResponseEntity&lt;OperationStatusChatSessionCreationWithUpload&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusChatSessionCreationWithUpload> ragChatSessionCreateWithUploadWithHttpInfo(String chatProfileCode, List<File> files) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatProfileCode' is set
        if (chatProfileCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatProfileCode' when calling ragChatSessionCreateWithUpload");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("chatProfileCode", chatProfileCode);
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/ragChatSessionCreateWithUpload/{chatProfileCode}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (files != null)
            formParams.add("files[]", files);

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusChatSessionCreationWithUpload> returnType = new ParameterizedTypeReference<OperationStatusChatSessionCreationWithUpload>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
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
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/serveContent/{userSessionCode}/{uploadedContentId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
