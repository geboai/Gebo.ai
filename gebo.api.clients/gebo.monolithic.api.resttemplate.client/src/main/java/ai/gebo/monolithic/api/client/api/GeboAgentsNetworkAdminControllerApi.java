package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.AgentServiceDescriptor;
import ai.gebo.monolithic.api.client.model.GAgentConfig;
import ai.gebo.monolithic.api.client.model.GAgentsNetwork;
import ai.gebo.monolithic.api.client.model.GBaseObject;
import ai.gebo.monolithic.api.client.model.OperationStatusGAgentsNetwork;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-08-22T15:15:57.119207400+02:00[Europe/Rome]")

public class GeboAgentsNetworkAdminControllerApi {
    private ApiClient apiClient;

     public GeboAgentsNetworkAdminControllerApi() {
        this(new ApiClient());
    }
    public GeboAgentsNetworkAdminControllerApi(ApiClient apiClient) {
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
     * @return OperationStatusGAgentsNetwork
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGAgentsNetwork deleteAgentsNetwork(GAgentsNetwork body) throws RestClientException {
        return deleteAgentsNetworkWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGAgentsNetwork&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGAgentsNetwork> deleteAgentsNetworkWithHttpInfo(GAgentsNetwork body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteAgentsNetwork");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/deleteAgentsNetwork").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGAgentsNetwork> returnType = new ParameterizedTypeReference<OperationStatusGAgentsNetwork>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GBaseObject&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GBaseObject> getAgentConfigs() throws RestClientException {
        return getAgentConfigsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GBaseObject&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GBaseObject>> getAgentConfigsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/getAgentConfigs").build().toUriString();
        
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

        ParameterizedTypeReference<List<GBaseObject>> returnType = new ParameterizedTypeReference<List<GBaseObject>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param serviceId  (required)
     * @return List&lt;GAgentConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GAgentConfig> getAgentConfigsByServiceId(String serviceId) throws RestClientException {
        return getAgentConfigsByServiceIdWithHttpInfo(serviceId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param serviceId  (required)
     * @return ResponseEntity&lt;List&lt;GAgentConfig&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GAgentConfig>> getAgentConfigsByServiceIdWithHttpInfo(String serviceId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'serviceId' is set
        if (serviceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'serviceId' when calling getAgentConfigsByServiceId");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/getAgentConfigsByServiceId").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serviceId", serviceId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GAgentConfig>> returnType = new ParameterizedTypeReference<List<GAgentConfig>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;AgentServiceDescriptor&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AgentServiceDescriptor> getAgentServices() throws RestClientException {
        return getAgentServicesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;AgentServiceDescriptor&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AgentServiceDescriptor>> getAgentServicesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/getAgentServices").build().toUriString();
        
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

        ParameterizedTypeReference<List<AgentServiceDescriptor>> returnType = new ParameterizedTypeReference<List<AgentServiceDescriptor>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GBaseObject&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GBaseObject> getAgentsNetwork() throws RestClientException {
        return getAgentsNetworkWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GBaseObject&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GBaseObject>> getAgentsNetworkWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/getAgentsNetwork").build().toUriString();
        
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

        ParameterizedTypeReference<List<GBaseObject>> returnType = new ParameterizedTypeReference<List<GBaseObject>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return GAgentsNetwork
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GAgentsNetwork getAgentsNetworkByCode(String code) throws RestClientException {
        return getAgentsNetworkByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GAgentsNetwork&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GAgentsNetwork> getAgentsNetworkByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling getAgentsNetworkByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/getAgentsNetworkByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GAgentsNetwork> returnType = new ParameterizedTypeReference<GAgentsNetwork>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param serviceId  (required)
     * @return List&lt;AgentServiceDescriptor&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AgentServiceDescriptor> getCompatibleNextServices(String serviceId) throws RestClientException {
        return getCompatibleNextServicesWithHttpInfo(serviceId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param serviceId  (required)
     * @return ResponseEntity&lt;List&lt;AgentServiceDescriptor&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AgentServiceDescriptor>> getCompatibleNextServicesWithHttpInfo(String serviceId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'serviceId' is set
        if (serviceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'serviceId' when calling getCompatibleNextServices");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/getCompatibleNextServices").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serviceId", serviceId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<AgentServiceDescriptor>> returnType = new ParameterizedTypeReference<List<AgentServiceDescriptor>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param serviceId  (required)
     * @return List&lt;AgentServiceDescriptor&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AgentServiceDescriptor> getCompatiblePreviousServices(String serviceId) throws RestClientException {
        return getCompatiblePreviousServicesWithHttpInfo(serviceId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param serviceId  (required)
     * @return ResponseEntity&lt;List&lt;AgentServiceDescriptor&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AgentServiceDescriptor>> getCompatiblePreviousServicesWithHttpInfo(String serviceId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'serviceId' is set
        if (serviceId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'serviceId' when calling getCompatiblePreviousServices");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/getCompatiblePreviousServices").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serviceId", serviceId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<AgentServiceDescriptor>> returnType = new ParameterizedTypeReference<List<AgentServiceDescriptor>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;AgentServiceDescriptor&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AgentServiceDescriptor> getNetworkAdapterServices() throws RestClientException {
        return getNetworkAdapterServicesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;AgentServiceDescriptor&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AgentServiceDescriptor>> getNetworkAdapterServicesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/getNetworkAdapterServices").build().toUriString();
        
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

        ParameterizedTypeReference<List<AgentServiceDescriptor>> returnType = new ParameterizedTypeReference<List<AgentServiceDescriptor>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGAgentsNetwork
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGAgentsNetwork insertAgentsNetwork(GAgentsNetwork body) throws RestClientException {
        return insertAgentsNetworkWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGAgentsNetwork&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGAgentsNetwork> insertAgentsNetworkWithHttpInfo(GAgentsNetwork body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertAgentsNetwork");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/insertAgentsNetwork").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGAgentsNetwork> returnType = new ParameterizedTypeReference<OperationStatusGAgentsNetwork>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGAgentsNetwork
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGAgentsNetwork updateAgentsNetwork(GAgentsNetwork body) throws RestClientException {
        return updateAgentsNetworkWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGAgentsNetwork&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGAgentsNetwork> updateAgentsNetworkWithHttpInfo(GAgentsNetwork body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateAgentsNetwork");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/updateAgentsNetwork").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGAgentsNetwork> returnType = new ParameterizedTypeReference<OperationStatusGAgentsNetwork>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGAgentsNetwork
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGAgentsNetwork validateAgentsNetwork(GAgentsNetwork body) throws RestClientException {
        return validateAgentsNetworkWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGAgentsNetwork&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGAgentsNetwork> validateAgentsNetworkWithHttpInfo(GAgentsNetwork body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling validateAgentsNetwork");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GeboAgentsNetworkAdminController/validateAgentsNetwork").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGAgentsNetwork> returnType = new ParameterizedTypeReference<OperationStatusGAgentsNetwork>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
