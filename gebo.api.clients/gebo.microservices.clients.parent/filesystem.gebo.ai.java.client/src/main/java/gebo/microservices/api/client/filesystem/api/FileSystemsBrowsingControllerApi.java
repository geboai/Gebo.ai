package gebo.microservices.api.client.filesystem.api;

import gebo.microservices.api.client.filesystem.invoker.ApiClient;

import gebo.microservices.api.client.filesystem.model.BrowseParam;
import gebo.microservices.api.client.filesystem.model.OperationStatusListGVirtualFilesystemRoot;
import gebo.microservices.api.client.filesystem.model.OperationStatusListPathInfo;
import gebo.microservices.api.client.filesystem.model.OperationStatusListVirtualFilesystemNavigationTreeStatus;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-21T14:42:49.386121215+02:00[Europe/Rome]")

public class FileSystemsBrowsingControllerApi {
    private ApiClient apiClient;

     public FileSystemsBrowsingControllerApi() {
        this(new ApiClient());
    }
    public FileSystemsBrowsingControllerApi(ApiClient apiClient) {
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
     * @return OperationStatusListPathInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListPathInfo browseSharedFilesystemRootsPath(BrowseParam body) throws RestClientException {
        return browseSharedFilesystemRootsPathWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusListPathInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListPathInfo> browseSharedFilesystemRootsPathWithHttpInfo(BrowseParam body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling browseSharedFilesystemRootsPath");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/FileSystemsBrowsingController/browseSharedFilesystemRootsPath").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListPathInfo> returnType = new ParameterizedTypeReference<OperationStatusListPathInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusListVirtualFilesystemNavigationTreeStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListVirtualFilesystemNavigationTreeStatus getSharedFilesystemNavigationStatus(Object body) throws RestClientException {
        return getSharedFilesystemNavigationStatusWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusListVirtualFilesystemNavigationTreeStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListVirtualFilesystemNavigationTreeStatus> getSharedFilesystemNavigationStatusWithHttpInfo(Object body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getSharedFilesystemNavigationStatus");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/FileSystemsBrowsingController/getSharedFilesystemNavigationStatus").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListVirtualFilesystemNavigationTreeStatus> returnType = new ParameterizedTypeReference<OperationStatusListVirtualFilesystemNavigationTreeStatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return OperationStatusListGVirtualFilesystemRoot
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGVirtualFilesystemRoot getSharedFilesystemRoots() throws RestClientException {
        return getSharedFilesystemRootsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;OperationStatusListGVirtualFilesystemRoot&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGVirtualFilesystemRoot> getSharedFilesystemRootsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/FileSystemsBrowsingController/getSharedFilesystemRoots").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusListGVirtualFilesystemRoot> returnType = new ParameterizedTypeReference<OperationStatusListGVirtualFilesystemRoot>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
