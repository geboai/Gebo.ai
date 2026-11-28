package gebo.microservices.api.client.openproject.api;

import gebo.microservices.api.client.openproject.invoker.ApiClient;

import gebo.microservices.api.client.openproject.model.BrowseParam;
import gebo.microservices.api.client.openproject.model.OperationStatusListGVirtualFilesystemRoot;
import gebo.microservices.api.client.openproject.model.OperationStatusListPathInfo;
import gebo.microservices.api.client.openproject.model.OperationStatusListVirtualFilesystemNavigationTreeStatus;
import gebo.microservices.api.client.openproject.model.VFilesystemReference;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-28T07:05:58.118588109+01:00[Europe/Rome]")

public class OpenProjectBrowsingControllerApi {
    private ApiClient apiClient;

     public OpenProjectBrowsingControllerApi() {
        this(new ApiClient());
    }
    public OpenProjectBrowsingControllerApi(ApiClient apiClient) {
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
    public OperationStatusListPathInfo browseOpenProjectPath(BrowseParam body, String systemCode) throws RestClientException {
        return browseOpenProjectPathWithHttpInfo(body, systemCode).getBody();
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
    public ResponseEntity<OperationStatusListPathInfo> browseOpenProjectPathWithHttpInfo(BrowseParam body, String systemCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling browseOpenProjectPath");
        }
        // verify the required parameter 'systemCode' is set
        if (systemCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemCode' when calling browseOpenProjectPath");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/OpenProjectBrowsingController/browseOpenProjectPath").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
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
    public OperationStatusListVirtualFilesystemNavigationTreeStatus getOpenProjectNavigationStatus(List<VFilesystemReference> body, String systemCode) throws RestClientException {
        return getOpenProjectNavigationStatusWithHttpInfo(body, systemCode).getBody();
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
    public ResponseEntity<OperationStatusListVirtualFilesystemNavigationTreeStatus> getOpenProjectNavigationStatusWithHttpInfo(List<VFilesystemReference> body, String systemCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getOpenProjectNavigationStatus");
        }
        // verify the required parameter 'systemCode' is set
        if (systemCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemCode' when calling getOpenProjectNavigationStatus");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/OpenProjectBrowsingController/getOpenProjectNavigationStatus").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemCode  (required)
     * @return OperationStatusListGVirtualFilesystemRoot
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGVirtualFilesystemRoot getOpenProjectRoots(String systemCode) throws RestClientException {
        return getOpenProjectRootsWithHttpInfo(systemCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemCode  (required)
     * @return ResponseEntity&lt;OperationStatusListGVirtualFilesystemRoot&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGVirtualFilesystemRoot> getOpenProjectRootsWithHttpInfo(String systemCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'systemCode' is set
        if (systemCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemCode' when calling getOpenProjectRoots");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/OpenProjectBrowsingController/getOpenProjectRoots").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
