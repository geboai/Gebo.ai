package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.UserAccessibleMcpServerView;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-07T14:58:38.267884300+02:00[Europe/Rome]")

public class GeboMcpServerUserControllerApi {
    private ApiClient apiClient;

     public GeboMcpServerUserControllerApi() {
        this(new ApiClient());
    }
    public GeboMcpServerUserControllerApi(ApiClient apiClient) {
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
     * @param code  (required)
     * @return UserAccessibleMcpServerView
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public UserAccessibleMcpServerView findAccessibleMcpServerByCode(String code) throws RestClientException {
        return findAccessibleMcpServerByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;UserAccessibleMcpServerView&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserAccessibleMcpServerView> findAccessibleMcpServerByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findAccessibleMcpServerByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/user/GeboMCPServerUserController/findAccessibleMcpServerByCode").build().toUriString();
        
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

        ParameterizedTypeReference<UserAccessibleMcpServerView> returnType = new ParameterizedTypeReference<UserAccessibleMcpServerView>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Boolean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Boolean getUsersCanAccessMcpServersList() throws RestClientException {
        return getUsersCanAccessMcpServersListWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Boolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Boolean> getUsersCanAccessMcpServersListWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/user/GeboMCPServerUserController/getUsersCanAccessMcpServersList").build().toUriString();
        
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

        ParameterizedTypeReference<Boolean> returnType = new ParameterizedTypeReference<Boolean>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;UserAccessibleMcpServerView&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<UserAccessibleMcpServerView> listAccessibleMcpServers() throws RestClientException {
        return listAccessibleMcpServersWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;UserAccessibleMcpServerView&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<UserAccessibleMcpServerView>> listAccessibleMcpServersWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/user/GeboMCPServerUserController/listAccessibleMcpServers").build().toUriString();
        
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

        ParameterizedTypeReference<List<UserAccessibleMcpServerView>> returnType = new ParameterizedTypeReference<List<UserAccessibleMcpServerView>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
