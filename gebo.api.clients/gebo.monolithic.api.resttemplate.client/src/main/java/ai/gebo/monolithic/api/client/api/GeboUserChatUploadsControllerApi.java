package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import java.io.File;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-10T21:14:52.278827200+02:00[Europe/Rome]")

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
            "application/json"
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
     * @return OperationStatusListUserUploadedContent
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListUserUploadedContent deleteSessionUploads() throws RestClientException {
        return deleteSessionUploadsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;OperationStatusListUserUploadedContent&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListUserUploadedContent> deleteSessionUploadsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/users/GeboUserChatUploadsController/deleteSessionUploads").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

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
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
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
