package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.AuthProviderDto;
import ai.gebo.monolithic.api.client.model.Oauth2ClientAuthorizativeInfo;
import ai.gebo.monolithic.api.client.model.Oauth2ClientConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-12-01T17:06:13.417468800+01:00[Europe/Rome]")
@Component("ai.gebo.monolithic.api.client.api.AuthProvidersControllerApi")
public class AuthProvidersControllerApi {
    private ApiClient apiClient;

    public AuthProvidersControllerApi() {
        this(new ApiClient());
    }

    @Autowired
    public AuthProvidersControllerApi(ApiClient apiClient) {
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
     * @param registrationId  (required)
     * @return Oauth2ClientConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Oauth2ClientConfig getProviderClientConfig(String registrationId) throws RestClientException {
        return getProviderClientConfigWithHttpInfo(registrationId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param registrationId  (required)
     * @return ResponseEntity&lt;Oauth2ClientConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Oauth2ClientConfig> getProviderClientConfigWithHttpInfo(String registrationId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'registrationId' is set
        if (registrationId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'registrationId' when calling getProviderClientConfig");
        }
        String path = UriComponentsBuilder.fromPath("/public/AuthProvidersController/getProviderClientConfig").build().toUriString();
        
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

        ParameterizedTypeReference<Oauth2ClientConfig> returnType = new ParameterizedTypeReference<Oauth2ClientConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;AuthProviderDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AuthProviderDto> listAuthProviders() throws RestClientException {
        return listAuthProvidersWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;AuthProviderDto&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AuthProviderDto>> listAuthProvidersWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/public/AuthProvidersController/listAuthProviders").build().toUriString();
        
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
     * @return List&lt;Oauth2ClientAuthorizativeInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Oauth2ClientAuthorizativeInfo> listAvailableProvidersConfig() throws RestClientException {
        return listAvailableProvidersConfigWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;Oauth2ClientAuthorizativeInfo&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Oauth2ClientAuthorizativeInfo>> listAvailableProvidersConfigWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/public/AuthProvidersController/listAvailableProvidersConfig").build().toUriString();
        
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

        ParameterizedTypeReference<List<Oauth2ClientAuthorizativeInfo>> returnType = new ParameterizedTypeReference<List<Oauth2ClientAuthorizativeInfo>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
