package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.BrowseParam;
import java.io.File;
import ai.gebo.monolithic.api.client.model.OperationStatusListGVirtualFilesystemRoot;
import ai.gebo.monolithic.api.client.model.OperationStatusListPathInfo;
import ai.gebo.monolithic.api.client.model.OperationStatusListVirtualFilesystemNavigationTreeStatus;
import ai.gebo.monolithic.api.client.model.VFilesystemReference;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-08-29T21:00:52.028175694+02:00[Europe/Rome]")

public class UploadsBrowsingControllerApi {
    private ApiClient apiClient;

     public UploadsBrowsingControllerApi() {
        this(new ApiClient());
    }
    public UploadsBrowsingControllerApi(ApiClient apiClient) {
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
     * @param endpointCode  (required)
     * @return OperationStatusListPathInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListPathInfo browseUploadsEndpointPath(BrowseParam body, String endpointCode) throws RestClientException {
        return browseUploadsEndpointPathWithHttpInfo(body, endpointCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param endpointCode  (required)
     * @return ResponseEntity&lt;OperationStatusListPathInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListPathInfo> browseUploadsEndpointPathWithHttpInfo(BrowseParam body, String endpointCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling browseUploadsEndpointPath");
        }
        // verify the required parameter 'endpointCode' is set
        if (endpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endpointCode' when calling browseUploadsEndpointPath");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/UploadsBrowsingController/browseUploadsEndpointPath").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endpointCode", endpointCode));

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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param endpointCode  (required)
     * @return OperationStatusListVirtualFilesystemNavigationTreeStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListVirtualFilesystemNavigationTreeStatus getUploadsEndpointNavigationStatus(List<VFilesystemReference> body, String endpointCode) throws RestClientException {
        return getUploadsEndpointNavigationStatusWithHttpInfo(body, endpointCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param endpointCode  (required)
     * @return ResponseEntity&lt;OperationStatusListVirtualFilesystemNavigationTreeStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListVirtualFilesystemNavigationTreeStatus> getUploadsEndpointNavigationStatusWithHttpInfo(List<VFilesystemReference> body, String endpointCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getUploadsEndpointNavigationStatus");
        }
        // verify the required parameter 'endpointCode' is set
        if (endpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endpointCode' when calling getUploadsEndpointNavigationStatus");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/UploadsBrowsingController/getUploadsEndpointNavigationStatus").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endpointCode", endpointCode));

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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param endpointCode  (required)
     * @return OperationStatusListGVirtualFilesystemRoot
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGVirtualFilesystemRoot getUploadsEndpointRoots(String endpointCode) throws RestClientException {
        return getUploadsEndpointRootsWithHttpInfo(endpointCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param endpointCode  (required)
     * @return ResponseEntity&lt;OperationStatusListGVirtualFilesystemRoot&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGVirtualFilesystemRoot> getUploadsEndpointRootsWithHttpInfo(String endpointCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'endpointCode' is set
        if (endpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endpointCode' when calling getUploadsEndpointRoots");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/UploadsBrowsingController/getUploadsEndpointRoots").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endpointCode", endpointCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusListGVirtualFilesystemRoot> returnType = new ParameterizedTypeReference<OperationStatusListGVirtualFilesystemRoot>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param endpointCode  (required)
     * @param path  (required)
     * @return File
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public File serveUploadsEndpointFile(String endpointCode, String path) throws RestClientException {
        return serveUploadsEndpointFileWithHttpInfo(endpointCode, path).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param endpointCode  (required)
     * @param path  (required)
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> serveUploadsEndpointFileWithHttpInfo(String endpointCode, String path) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'endpointCode' is set
        if (endpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endpointCode' when calling serveUploadsEndpointFile");
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'path' when calling serveUploadsEndpointFile");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/UploadsBrowsingController/serveUploadsEndpointFile").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endpointCode", endpointCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "path", path));

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<File> returnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
