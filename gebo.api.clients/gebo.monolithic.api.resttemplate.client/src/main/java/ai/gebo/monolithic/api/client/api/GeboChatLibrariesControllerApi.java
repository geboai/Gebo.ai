package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.AIToolsAndResourcesLibraryInstance;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-10-29T09:28:48.471184600+01:00[Europe/Rome]")

public class GeboChatLibrariesControllerApi {
    private ApiClient apiClient;

     public GeboChatLibrariesControllerApi() {
        this(new ApiClient());
    }
    public GeboChatLibrariesControllerApi(ApiClient apiClient) {
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
     * @param chatCode  (required)
     * @param libraryTypeId  (required)
     * @param resourceId  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void downloadChatLibraryResource(String chatCode, String libraryTypeId, String resourceId) throws RestClientException {
        downloadChatLibraryResourceWithHttpInfo(chatCode, libraryTypeId, resourceId);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatCode  (required)
     * @param libraryTypeId  (required)
     * @param resourceId  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> downloadChatLibraryResourceWithHttpInfo(String chatCode, String libraryTypeId, String resourceId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatCode' is set
        if (chatCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatCode' when calling downloadChatLibraryResource");
        }
        // verify the required parameter 'libraryTypeId' is set
        if (libraryTypeId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'libraryTypeId' when calling downloadChatLibraryResource");
        }
        // verify the required parameter 'resourceId' is set
        if (resourceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'resourceId' when calling downloadChatLibraryResource");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatLibrariesController/downloadChatLibraryResource").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "chatCode", chatCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "libraryTypeId", libraryTypeId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "resourceId", resourceId));

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
     * @param chatCode  (required)
     * @return List&lt;AIToolsAndResourcesLibraryInstance&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AIToolsAndResourcesLibraryInstance> getChatLibraryInstance(String chatCode) throws RestClientException {
        return getChatLibraryInstanceWithHttpInfo(chatCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param chatCode  (required)
     * @return ResponseEntity&lt;List&lt;AIToolsAndResourcesLibraryInstance&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AIToolsAndResourcesLibraryInstance>> getChatLibraryInstanceWithHttpInfo(String chatCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'chatCode' is set
        if (chatCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'chatCode' when calling getChatLibraryInstance");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/GeboChatLibrariesController/getChatLibraryInstance").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "chatCode", chatCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<AIToolsAndResourcesLibraryInstance>> returnType = new ParameterizedTypeReference<List<AIToolsAndResourcesLibraryInstance>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
