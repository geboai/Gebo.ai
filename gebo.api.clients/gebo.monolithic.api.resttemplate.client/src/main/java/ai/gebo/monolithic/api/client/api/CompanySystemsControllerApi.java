package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GContentManagementSystem;
import ai.gebo.monolithic.api.client.model.GContentManagementSystemType;
import ai.gebo.monolithic.api.client.model.GObjectRefGProjectEndpoint;
import ai.gebo.monolithic.api.client.model.GProjectEndpoint;
import ai.gebo.monolithic.api.client.model.SystemInfos;

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

public class CompanySystemsControllerApi {
    private ApiClient apiClient;

     public CompanySystemsControllerApi() {
        this(new ApiClient());
    }
    public CompanySystemsControllerApi(ApiClient apiClient) {
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
     * @param systemTypeCode  (required)
     * @param systemCode  (required)
     * @return GContentManagementSystem
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GContentManagementSystem getContentSystem(String systemTypeCode, String systemCode) throws RestClientException {
        return getContentSystemWithHttpInfo(systemTypeCode, systemCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemTypeCode  (required)
     * @param systemCode  (required)
     * @return ResponseEntity&lt;GContentManagementSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GContentManagementSystem> getContentSystemWithHttpInfo(String systemTypeCode, String systemCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'systemTypeCode' is set
        if (systemTypeCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemTypeCode' when calling getContentSystem");
        }
        // verify the required parameter 'systemCode' is set
        if (systemCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemCode' when calling getContentSystem");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/CompanySystemsController/getContentSystem").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemTypeCode", systemTypeCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemCode", systemCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GContentManagementSystem> returnType = new ParameterizedTypeReference<GContentManagementSystem>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemTypeCode  (required)
     * @return GContentManagementSystemType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GContentManagementSystemType getContentSystemType(String systemTypeCode) throws RestClientException {
        return getContentSystemTypeWithHttpInfo(systemTypeCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemTypeCode  (required)
     * @return ResponseEntity&lt;GContentManagementSystemType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GContentManagementSystemType> getContentSystemTypeWithHttpInfo(String systemTypeCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'systemTypeCode' is set
        if (systemTypeCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemTypeCode' when calling getContentSystemType");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/CompanySystemsController/getContentSystemType").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemTypeCode", systemTypeCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GContentManagementSystemType> returnType = new ParameterizedTypeReference<GContentManagementSystemType>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GContentManagementSystemType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GContentManagementSystemType> getContentSystemTypes() throws RestClientException {
        return getContentSystemTypesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GContentManagementSystemType&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GContentManagementSystemType>> getContentSystemTypesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/CompanySystemsController/getContentSystemTypes()").build().toUriString();
        
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

        ParameterizedTypeReference<List<GContentManagementSystemType>> returnType = new ParameterizedTypeReference<List<GContentManagementSystemType>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GContentManagementSystem&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GContentManagementSystem> getContentSystems() throws RestClientException {
        return getContentSystemsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GContentManagementSystem&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GContentManagementSystem>> getContentSystemsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/CompanySystemsController/getContentSystems").build().toUriString();
        
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

        ParameterizedTypeReference<List<GContentManagementSystem>> returnType = new ParameterizedTypeReference<List<GContentManagementSystem>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemTypeCode  (required)
     * @param systemCode  (required)
     * @param projectEndpointCode  (required)
     * @return GProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GProjectEndpoint getProjectEndpoint(String systemTypeCode, String systemCode, String projectEndpointCode) throws RestClientException {
        return getProjectEndpointWithHttpInfo(systemTypeCode, systemCode, projectEndpointCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemTypeCode  (required)
     * @param systemCode  (required)
     * @param projectEndpointCode  (required)
     * @return ResponseEntity&lt;GProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GProjectEndpoint> getProjectEndpointWithHttpInfo(String systemTypeCode, String systemCode, String projectEndpointCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'systemTypeCode' is set
        if (systemTypeCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemTypeCode' when calling getProjectEndpoint");
        }
        // verify the required parameter 'systemCode' is set
        if (systemCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemCode' when calling getProjectEndpoint");
        }
        // verify the required parameter 'projectEndpointCode' is set
        if (projectEndpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectEndpointCode' when calling getProjectEndpoint");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/CompanySystemsController/getProjectEndpoint").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemTypeCode", systemTypeCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemCode", systemCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "projectEndpointCode", projectEndpointCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GProjectEndpoint> returnType = new ParameterizedTypeReference<GProjectEndpoint>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GProjectEndpoint
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GProjectEndpoint getProjectEndpointByObjectRef(GObjectRefGProjectEndpoint body) throws RestClientException {
        return getProjectEndpointByObjectRefWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GProjectEndpoint&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GProjectEndpoint> getProjectEndpointByObjectRefWithHttpInfo(GObjectRefGProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getProjectEndpointByObjectRef");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/CompanySystemsController/getProjectEndpointByObjectRef").build().toUriString();
        
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

        ParameterizedTypeReference<GProjectEndpoint> returnType = new ParameterizedTypeReference<GProjectEndpoint>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return SystemInfos
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SystemInfos getProjectEndpointSystemInfos(GObjectRefGProjectEndpoint body) throws RestClientException {
        return getProjectEndpointSystemInfosWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SystemInfos&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SystemInfos> getProjectEndpointSystemInfosWithHttpInfo(GObjectRefGProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling getProjectEndpointSystemInfos");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/CompanySystemsController/getProjectEndpointSystemInfos").build().toUriString();
        
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

        ParameterizedTypeReference<SystemInfos> returnType = new ParameterizedTypeReference<SystemInfos>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
