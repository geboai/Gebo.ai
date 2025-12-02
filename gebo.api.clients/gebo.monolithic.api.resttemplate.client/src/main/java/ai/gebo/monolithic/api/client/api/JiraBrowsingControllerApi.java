package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.BrowseParam;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-12-02T07:42:58.505542900+01:00[Europe/Rome]")

public class JiraBrowsingControllerApi {
    private ApiClient apiClient;

     public JiraBrowsingControllerApi() {
        this(new ApiClient());
    }
    public JiraBrowsingControllerApi(ApiClient apiClient) {
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
     * @param systemCode  (required)
     * @return OperationStatusListPathInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListPathInfo browseJiraPath(BrowseParam body, String systemCode) throws RestClientException {
        return browseJiraPathWithHttpInfo(body, systemCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param systemCode  (required)
     * @return ResponseEntity&lt;OperationStatusListPathInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListPathInfo> browseJiraPathWithHttpInfo(BrowseParam body, String systemCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling browseJiraPath");
        }
        // verify the required parameter 'systemCode' is set
        if (systemCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemCode' when calling browseJiraPath");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JiraBrowsingController/browseJiraPath").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemCode", systemCode));

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
     * @param systemCode  (required)
     * @return OperationStatusListVirtualFilesystemNavigationTreeStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListVirtualFilesystemNavigationTreeStatus getJiraNavigationStatus(List<VFilesystemReference> body, String systemCode) throws RestClientException {
        return getJiraNavigationStatusWithHttpInfo(body, systemCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param systemCode  (required)
     * @return ResponseEntity&lt;OperationStatusListVirtualFilesystemNavigationTreeStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListVirtualFilesystemNavigationTreeStatus> getJiraNavigationStatusWithHttpInfo(List<VFilesystemReference> body, String systemCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getJiraNavigationStatus");
        }
        // verify the required parameter 'systemCode' is set
        if (systemCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemCode' when calling getJiraNavigationStatus");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JiraBrowsingController/getJiraNavigationStatus").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemCode", systemCode));

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
     * @param systemCode  (required)
     * @return OperationStatusListGVirtualFilesystemRoot
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGVirtualFilesystemRoot getJiraRoots(String systemCode) throws RestClientException {
        return getJiraRootsWithHttpInfo(systemCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemCode  (required)
     * @return ResponseEntity&lt;OperationStatusListGVirtualFilesystemRoot&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGVirtualFilesystemRoot> getJiraRootsWithHttpInfo(String systemCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'systemCode' is set
        if (systemCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemCode' when calling getJiraRoots");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/JiraBrowsingController/getJiraRoots").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemCode", systemCode));

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
