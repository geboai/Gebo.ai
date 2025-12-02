package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.AuthProviderDto;
import ai.gebo.monolithic.api.client.model.Oauth2ProviderModifiableData;

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

public class OAuth2AdminControllerApi {
    private ApiClient apiClient;

     public OAuth2AdminControllerApi() {
        this(new ApiClient());
    }
    public OAuth2AdminControllerApi(ApiClient apiClient) {
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
    public void deleteOauth2ProviderRegistration(Oauth2ProviderModifiableData body) throws RestClientException {
        deleteOauth2ProviderRegistrationWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteOauth2ProviderRegistrationWithHttpInfo(Oauth2ProviderModifiableData body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteOauth2ProviderRegistration");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OAuth2AdminController/deleteOauth2ProviderRegistration").build().toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param registrationId  (required)
     * @return Oauth2ProviderModifiableData
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Oauth2ProviderModifiableData findOauth2ProviderRegistrationByRegistrationId(String registrationId) throws RestClientException {
        return findOauth2ProviderRegistrationByRegistrationIdWithHttpInfo(registrationId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param registrationId  (required)
     * @return ResponseEntity&lt;Oauth2ProviderModifiableData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Oauth2ProviderModifiableData> findOauth2ProviderRegistrationByRegistrationIdWithHttpInfo(String registrationId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'registrationId' is set
        if (registrationId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'registrationId' when calling findOauth2ProviderRegistrationByRegistrationId");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OAuth2AdminController/findOauth2ProviderRegistrationByRegistrationId").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "registrationId", registrationId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Oauth2ProviderModifiableData> returnType = new ParameterizedTypeReference<Oauth2ProviderModifiableData>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;AuthProviderDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AuthProviderDto> getProviders() throws RestClientException {
        return getProvidersWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;AuthProviderDto&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AuthProviderDto>> getProvidersWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/OAuth2AdminController/getProviders").build().toUriString();
        
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

        ParameterizedTypeReference<List<AuthProviderDto>> returnType = new ParameterizedTypeReference<List<AuthProviderDto>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return Oauth2ProviderModifiableData
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Oauth2ProviderModifiableData insertOauth2ProviderRegistration(Oauth2ProviderModifiableData body) throws RestClientException {
        return insertOauth2ProviderRegistrationWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Oauth2ProviderModifiableData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Oauth2ProviderModifiableData> insertOauth2ProviderRegistrationWithHttpInfo(Oauth2ProviderModifiableData body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling insertOauth2ProviderRegistration");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OAuth2AdminController/insertOauth2ProviderRegistration").build().toUriString();
        
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

        ParameterizedTypeReference<Oauth2ProviderModifiableData> returnType = new ParameterizedTypeReference<Oauth2ProviderModifiableData>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return Oauth2ProviderModifiableData
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Oauth2ProviderModifiableData updateOauth2ProviderRegistration(Oauth2ProviderModifiableData body) throws RestClientException {
        return updateOauth2ProviderRegistrationWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Oauth2ProviderModifiableData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Oauth2ProviderModifiableData> updateOauth2ProviderRegistrationWithHttpInfo(Oauth2ProviderModifiableData body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateOauth2ProviderRegistration");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/OAuth2AdminController/updateOauth2ProviderRegistration").build().toUriString();
        
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

        ParameterizedTypeReference<Oauth2ProviderModifiableData> returnType = new ParameterizedTypeReference<Oauth2ProviderModifiableData>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
