package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.ComponentSetupStatus;
import ai.gebo.monolithic.api.client.model.GSerpapiSearchApiCredentials;
import ai.gebo.monolithic.api.client.model.SerpapiSearchConfig;

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

public class SerpapiSearchConfigurationControllerApi {
    private ApiClient apiClient;

     public SerpapiSearchConfigurationControllerApi() {
        this(new ApiClient());
    }
    public SerpapiSearchConfigurationControllerApi(ApiClient apiClient) {
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
    public void deleteGSerpapiSearchApiCredentials(GSerpapiSearchApiCredentials body) throws RestClientException {
        deleteGSerpapiSearchApiCredentialsWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteGSerpapiSearchApiCredentialsWithHttpInfo(GSerpapiSearchApiCredentials body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteGSerpapiSearchApiCredentials");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/SerpapiSearchConfigurationController/deleteGSerpapiSearchApiCredentials").build().toUriString();
        
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
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GSerpapiSearchApiCredentials
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GSerpapiSearchApiCredentials fastInsertSerpapiSearchApiCredentials(SerpapiSearchConfig body) throws RestClientException {
        return fastInsertSerpapiSearchApiCredentialsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GSerpapiSearchApiCredentials&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GSerpapiSearchApiCredentials> fastInsertSerpapiSearchApiCredentialsWithHttpInfo(SerpapiSearchConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling fastInsertSerpapiSearchApiCredentials");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/SerpapiSearchConfigurationController/fastInsertSerpapiSearchApiCredentials").build().toUriString();
        
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

        ParameterizedTypeReference<GSerpapiSearchApiCredentials> returnType = new ParameterizedTypeReference<GSerpapiSearchApiCredentials>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GSerpapiSearchApiCredentials&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GSerpapiSearchApiCredentials> getSerpapiSearchApiCredentials() throws RestClientException {
        return getSerpapiSearchApiCredentialsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GSerpapiSearchApiCredentials&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GSerpapiSearchApiCredentials>> getSerpapiSearchApiCredentialsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/SerpapiSearchConfigurationController/getSerpapiSearchApiCredentials").build().toUriString();
        
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

        ParameterizedTypeReference<List<GSerpapiSearchApiCredentials>> returnType = new ParameterizedTypeReference<List<GSerpapiSearchApiCredentials>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ComponentSetupStatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ComponentSetupStatus getSerpapiSearchStatus() throws RestClientException {
        return getSerpapiSearchStatusWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;ComponentSetupStatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ComponentSetupStatus> getSerpapiSearchStatusWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/SerpapiSearchConfigurationController/getSerpapiSearchStatus").build().toUriString();
        
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

        ParameterizedTypeReference<ComponentSetupStatus> returnType = new ParameterizedTypeReference<ComponentSetupStatus>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GSerpapiSearchApiCredentials
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GSerpapiSearchApiCredentials insertGSerpapiSearchApiCredentials(GSerpapiSearchApiCredentials body) throws RestClientException {
        return insertGSerpapiSearchApiCredentialsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GSerpapiSearchApiCredentials&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GSerpapiSearchApiCredentials> insertGSerpapiSearchApiCredentialsWithHttpInfo(GSerpapiSearchApiCredentials body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertGSerpapiSearchApiCredentials");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/SerpapiSearchConfigurationController/insertGSerpapiSearchApiCredentials").build().toUriString();
        
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

        ParameterizedTypeReference<GSerpapiSearchApiCredentials> returnType = new ParameterizedTypeReference<GSerpapiSearchApiCredentials>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return GSerpapiSearchApiCredentials
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GSerpapiSearchApiCredentials searchGSerpapiSearchApiCredentialsByCode(String code) throws RestClientException {
        return searchGSerpapiSearchApiCredentialsByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GSerpapiSearchApiCredentials&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GSerpapiSearchApiCredentials> searchGSerpapiSearchApiCredentialsByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling searchGSerpapiSearchApiCredentialsByCode");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/SerpapiSearchConfigurationController/searchGSerpapiSearchApiCredentialsByCode").build().toUriString();
        
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

        ParameterizedTypeReference<GSerpapiSearchApiCredentials> returnType = new ParameterizedTypeReference<GSerpapiSearchApiCredentials>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GSerpapiSearchApiCredentials
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GSerpapiSearchApiCredentials updateGSerpapiSearchApiCredentials(GSerpapiSearchApiCredentials body) throws RestClientException {
        return updateGSerpapiSearchApiCredentialsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GSerpapiSearchApiCredentials&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GSerpapiSearchApiCredentials> updateGSerpapiSearchApiCredentialsWithHttpInfo(GSerpapiSearchApiCredentials body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateGSerpapiSearchApiCredentials");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/SerpapiSearchConfigurationController/updateGSerpapiSearchApiCredentials").build().toUriString();
        
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

        ParameterizedTypeReference<GSerpapiSearchApiCredentials> returnType = new ParameterizedTypeReference<GSerpapiSearchApiCredentials>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
