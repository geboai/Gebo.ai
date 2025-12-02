package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.OperationStatusPublishingStatus;
import ai.gebo.monolithic.api.client.model.PublishingStatus;
import ai.gebo.monolithic.api.client.model.UserUploadToUserSpaceParam;
import ai.gebo.monolithic.api.client.model.UserspaceFileDto;
import ai.gebo.monolithic.api.client.model.UserspaceFolderDto;
import ai.gebo.monolithic.api.client.model.UserspaceKnowledgebaseDto;

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

public class UserspaceControllerApi {
    private ApiClient apiClient;

     public UserspaceControllerApi() {
        this(new ApiClient());
    }
    public UserspaceControllerApi(ApiClient apiClient) {
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
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUserKnowledgebase(UserspaceKnowledgebaseDto body) throws RestClientException {
        deleteUserKnowledgebaseWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserKnowledgebaseWithHttpInfo(UserspaceKnowledgebaseDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteUserKnowledgebase");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/deleteUserKnowledgebase").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUserspaceFiles(List<UserspaceFileDto> body) throws RestClientException {
        deleteUserspaceFilesWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserspaceFilesWithHttpInfo(List<UserspaceFileDto> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteUserspaceFiles");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/deleteUserspaceFiles").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUserspaceFolder(UserspaceFolderDto body) throws RestClientException {
        deleteUserspaceFolderWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserspaceFolderWithHttpInfo(UserspaceFolderDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteUserspaceFolder");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/deleteUserspaceFolder").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return UserspaceKnowledgebaseDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserspaceKnowledgebaseDto findUserKnowledgebaseByCode(String code) throws RestClientException {
        return findUserKnowledgebaseByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;UserspaceKnowledgebaseDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserspaceKnowledgebaseDto> findUserKnowledgebaseByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findUserKnowledgebaseByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/findUserKnowledgebaseByCode").build().toUriString();
        
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

        ParameterizedTypeReference<UserspaceKnowledgebaseDto> returnType = new ParameterizedTypeReference<UserspaceKnowledgebaseDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;UserspaceFileDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<UserspaceFileDto> findUserspaceFileByCodes(List<String> body) throws RestClientException {
        return findUserspaceFileByCodesWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;UserspaceFileDto&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<UserspaceFileDto>> findUserspaceFileByCodesWithHttpInfo(List<String> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling findUserspaceFileByCodes");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/findUserspaceFileByCodes").build().toUriString();
        
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

        ParameterizedTypeReference<List<UserspaceFileDto>> returnType = new ParameterizedTypeReference<List<UserspaceFileDto>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return UserspaceFolderDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserspaceFolderDto findUserspaceFolderByCode(String code) throws RestClientException {
        return findUserspaceFolderByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;UserspaceFolderDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserspaceFolderDto> findUserspaceFolderByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findUserspaceFolderByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/findUserspaceFolderByCode").build().toUriString();
        
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

        ParameterizedTypeReference<UserspaceFolderDto> returnType = new ParameterizedTypeReference<UserspaceFolderDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;UserspaceKnowledgebaseDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<UserspaceKnowledgebaseDto> getPersonalKnowledgebases() throws RestClientException {
        return getPersonalKnowledgebasesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;UserspaceKnowledgebaseDto&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<UserspaceKnowledgebaseDto>> getPersonalKnowledgebasesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/getPersonalKnowledgebases").build().toUriString();
        
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

        ParameterizedTypeReference<List<UserspaceKnowledgebaseDto>> returnType = new ParameterizedTypeReference<List<UserspaceKnowledgebaseDto>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PublishingStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PublishingStatus getPublishingStatus(UserspaceFolderDto body) throws RestClientException {
        return getPublishingStatusWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PublishingStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PublishingStatus> getPublishingStatusWithHttpInfo(UserspaceFolderDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getPublishingStatus");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/getPublishingStatus").build().toUriString();
        
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

        ParameterizedTypeReference<PublishingStatus> returnType = new ParameterizedTypeReference<PublishingStatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;UserspaceKnowledgebaseDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<UserspaceKnowledgebaseDto> listChildPersonalKnowledgebases(List<String> body) throws RestClientException {
        return listChildPersonalKnowledgebasesWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;UserspaceKnowledgebaseDto&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<UserspaceKnowledgebaseDto>> listChildPersonalKnowledgebasesWithHttpInfo(List<String> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling listChildPersonalKnowledgebases");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/listChildPersonalKnowledgebases").build().toUriString();
        
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

        ParameterizedTypeReference<List<UserspaceKnowledgebaseDto>> returnType = new ParameterizedTypeReference<List<UserspaceKnowledgebaseDto>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userspaceUploadCode  (required)
     * @return List&lt;UserspaceFileDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<UserspaceFileDto> listUserspaceFiles(String userspaceUploadCode) throws RestClientException {
        return listUserspaceFilesWithHttpInfo(userspaceUploadCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userspaceUploadCode  (required)
     * @return ResponseEntity&lt;List&lt;UserspaceFileDto&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<UserspaceFileDto>> listUserspaceFilesWithHttpInfo(String userspaceUploadCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userspaceUploadCode' is set
        if (userspaceUploadCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userspaceUploadCode' when calling listUserspaceFiles");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/listUserspaceFiles").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userspaceUploadCode", userspaceUploadCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<UserspaceFileDto>> returnType = new ParameterizedTypeReference<List<UserspaceFileDto>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userspaceKnowledgeBase  (required)
     * @return List&lt;UserspaceFolderDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<UserspaceFolderDto> listUserspaceFolders(String userspaceKnowledgeBase) throws RestClientException {
        return listUserspaceFoldersWithHttpInfo(userspaceKnowledgeBase).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userspaceKnowledgeBase  (required)
     * @return ResponseEntity&lt;List&lt;UserspaceFolderDto&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<UserspaceFolderDto>> listUserspaceFoldersWithHttpInfo(String userspaceKnowledgeBase) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userspaceKnowledgeBase' is set
        if (userspaceKnowledgeBase == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userspaceKnowledgeBase' when calling listUserspaceFolders");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/listUserspaceFolders").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userspaceKnowledgeBase", userspaceKnowledgeBase));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<UserspaceFolderDto>> returnType = new ParameterizedTypeReference<List<UserspaceFolderDto>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return UserspaceKnowledgebaseDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserspaceKnowledgebaseDto newUserKnowledgebase(UserspaceKnowledgebaseDto body) throws RestClientException {
        return newUserKnowledgebaseWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;UserspaceKnowledgebaseDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserspaceKnowledgebaseDto> newUserKnowledgebaseWithHttpInfo(UserspaceKnowledgebaseDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling newUserKnowledgebase");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/newUserKnowledgebase").build().toUriString();
        
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

        ParameterizedTypeReference<UserspaceKnowledgebaseDto> returnType = new ParameterizedTypeReference<UserspaceKnowledgebaseDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return UserspaceFolderDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserspaceFolderDto newUserspaceFolder(UserspaceFolderDto body) throws RestClientException {
        return newUserspaceFolderWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;UserspaceFolderDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserspaceFolderDto> newUserspaceFolderWithHttpInfo(UserspaceFolderDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling newUserspaceFolder");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/newUserspaceFolder").build().toUriString();
        
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

        ParameterizedTypeReference<UserspaceFolderDto> returnType = new ParameterizedTypeReference<UserspaceFolderDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusPublishingStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusPublishingStatus publishFolder(UserspaceFolderDto body) throws RestClientException {
        return publishFolderWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusPublishingStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusPublishingStatus> publishFolderWithHttpInfo(UserspaceFolderDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling publishFolder");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/publishFolder").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusPublishingStatus> returnType = new ParameterizedTypeReference<OperationStatusPublishingStatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusPublishingStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusPublishingStatus transferUploadsToUserSpaceAndPublish(UserUploadToUserSpaceParam body) throws RestClientException {
        return transferUploadsToUserSpaceAndPublishWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusPublishingStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusPublishingStatus> transferUploadsToUserSpaceAndPublishWithHttpInfo(UserUploadToUserSpaceParam body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling transferUploadsToUserSpaceAndPublish");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/transferUploadsToUserSpaceAndPublish").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusPublishingStatus> returnType = new ParameterizedTypeReference<OperationStatusPublishingStatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return UserspaceKnowledgebaseDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserspaceKnowledgebaseDto updateUserKnowledgebase(UserspaceKnowledgebaseDto body) throws RestClientException {
        return updateUserKnowledgebaseWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;UserspaceKnowledgebaseDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserspaceKnowledgebaseDto> updateUserKnowledgebaseWithHttpInfo(UserspaceKnowledgebaseDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateUserKnowledgebase");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/updateUserKnowledgebase").build().toUriString();
        
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

        ParameterizedTypeReference<UserspaceKnowledgebaseDto> returnType = new ParameterizedTypeReference<UserspaceKnowledgebaseDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return UserspaceFolderDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserspaceFolderDto updateUserspaceFolder(UserspaceFolderDto body) throws RestClientException {
        return updateUserspaceFolderWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;UserspaceFolderDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserspaceFolderDto> updateUserspaceFolderWithHttpInfo(UserspaceFolderDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateUserspaceFolder");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/UserspaceController/updateUserspaceFolder").build().toUriString();
        
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

        ParameterizedTypeReference<UserspaceFolderDto> returnType = new ParameterizedTypeReference<UserspaceFolderDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
