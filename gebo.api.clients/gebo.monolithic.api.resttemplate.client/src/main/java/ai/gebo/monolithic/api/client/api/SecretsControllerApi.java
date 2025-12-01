package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.SecretInfo;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboCustomSecretContent;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboGoogleJsonSecretContent;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboGoogleOauth2SecretContent;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboOauth2SecretContent;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboSshKeySecretContent;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboTokenContent;
import ai.gebo.monolithic.api.client.model.SecretWrapperGeboUsernamePasswordContent;

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
@Component("ai.gebo.monolithic.api.client.api.SecretsControllerApi")
public class SecretsControllerApi {
    private ApiClient apiClient;

    public SecretsControllerApi() {
        this(new ApiClient());
    }

    @Autowired
    public SecretsControllerApi(ApiClient apiClient) {
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
     * @return SecretInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecretInfo createCustomSecret(SecretWrapperGeboCustomSecretContent body) throws RestClientException {
        return createCustomSecretWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SecretInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecretInfo> createCustomSecretWithHttpInfo(SecretWrapperGeboCustomSecretContent body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createCustomSecret");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/SecretsController/createCustomSecret").build().toUriString();
        
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

        ParameterizedTypeReference<SecretInfo> returnType = new ParameterizedTypeReference<SecretInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return SecretInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecretInfo createGoogleJsonCredentialsSecret(SecretWrapperGeboGoogleJsonSecretContent body) throws RestClientException {
        return createGoogleJsonCredentialsSecretWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SecretInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecretInfo> createGoogleJsonCredentialsSecretWithHttpInfo(SecretWrapperGeboGoogleJsonSecretContent body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createGoogleJsonCredentialsSecret");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/SecretsController/createGoogleJsonCredentialsSecret").build().toUriString();
        
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

        ParameterizedTypeReference<SecretInfo> returnType = new ParameterizedTypeReference<SecretInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return SecretInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecretInfo createGoogleOauth2Secret(SecretWrapperGeboGoogleOauth2SecretContent body) throws RestClientException {
        return createGoogleOauth2SecretWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SecretInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecretInfo> createGoogleOauth2SecretWithHttpInfo(SecretWrapperGeboGoogleOauth2SecretContent body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createGoogleOauth2Secret");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/SecretsController/createGoogleOauth2Secret").build().toUriString();
        
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

        ParameterizedTypeReference<SecretInfo> returnType = new ParameterizedTypeReference<SecretInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return SecretInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecretInfo createOauth2StandardSecret(SecretWrapperGeboOauth2SecretContent body) throws RestClientException {
        return createOauth2StandardSecretWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SecretInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecretInfo> createOauth2StandardSecretWithHttpInfo(SecretWrapperGeboOauth2SecretContent body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createOauth2StandardSecret");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/SecretsController/createOauth2StandardSecret").build().toUriString();
        
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

        ParameterizedTypeReference<SecretInfo> returnType = new ParameterizedTypeReference<SecretInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return SecretInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecretInfo createSshKeySecret(SecretWrapperGeboSshKeySecretContent body) throws RestClientException {
        return createSshKeySecretWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SecretInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecretInfo> createSshKeySecretWithHttpInfo(SecretWrapperGeboSshKeySecretContent body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createSshKeySecret");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/SecretsController/createSshKeySecret").build().toUriString();
        
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

        ParameterizedTypeReference<SecretInfo> returnType = new ParameterizedTypeReference<SecretInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return SecretInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecretInfo createTokenSecret(SecretWrapperGeboTokenContent body) throws RestClientException {
        return createTokenSecretWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SecretInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecretInfo> createTokenSecretWithHttpInfo(SecretWrapperGeboTokenContent body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createTokenSecret");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/SecretsController/createTokenSecret").build().toUriString();
        
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

        ParameterizedTypeReference<SecretInfo> returnType = new ParameterizedTypeReference<SecretInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return SecretInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SecretInfo createUsernamePasswordSecret(SecretWrapperGeboUsernamePasswordContent body) throws RestClientException {
        return createUsernamePasswordSecretWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SecretInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SecretInfo> createUsernamePasswordSecretWithHttpInfo(SecretWrapperGeboUsernamePasswordContent body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createUsernamePasswordSecret");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/SecretsController/createUsernamePasswordSecret").build().toUriString();
        
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

        ParameterizedTypeReference<SecretInfo> returnType = new ParameterizedTypeReference<SecretInfo>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param context  (required)
     * @return List&lt;SecretInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<SecretInfo> getSecretsByContextCode(String context) throws RestClientException {
        return getSecretsByContextCodeWithHttpInfo(context).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param context  (required)
     * @return ResponseEntity&lt;List&lt;SecretInfo&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<SecretInfo>> getSecretsByContextCodeWithHttpInfo(String context) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'context' is set
        if (context == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'context' when calling getSecretsByContextCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/SecretsController/getSecretsByContextCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "context", context));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<SecretInfo>> returnType = new ParameterizedTypeReference<List<SecretInfo>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
