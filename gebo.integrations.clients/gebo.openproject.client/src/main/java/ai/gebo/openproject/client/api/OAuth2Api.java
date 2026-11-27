package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.OAuthApplicationReadModel;
import ai.gebo.openproject.client.model.OAuthClientCredentialsReadModel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.springframework.web.client.RestClientResponseException;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class OAuth2Api {
    private ApiClient apiClient;

    public OAuth2Api() {
        this(new ApiClient());
    }

    public OAuth2Api(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get the oauth application.
     * Retrieves the OAuth 2 provider application for the given identifier. The secret will not be part of the response, instead a &#x60;confidential&#x60; flag is indicating, whether there is a secret or not.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the application does not exist.
     * @param id OAuth application id
     * @return OAuthApplicationReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOauthApplicationRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getOauthApplication", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<OAuthApplicationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/oauth_applications/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the oauth application.
     * Retrieves the OAuth 2 provider application for the given identifier. The secret will not be part of the response, instead a &#x60;confidential&#x60; flag is indicating, whether there is a secret or not.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the application does not exist.
     * @param id OAuth application id
     * @return OAuthApplicationReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public OAuthApplicationReadModel getOauthApplication(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<OAuthApplicationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getOauthApplicationRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get the oauth application.
     * Retrieves the OAuth 2 provider application for the given identifier. The secret will not be part of the response, instead a &#x60;confidential&#x60; flag is indicating, whether there is a secret or not.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the application does not exist.
     * @param id OAuth application id
     * @return ResponseEntity&lt;OAuthApplicationReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OAuthApplicationReadModel> getOauthApplicationWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<OAuthApplicationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getOauthApplicationRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get the oauth application.
     * Retrieves the OAuth 2 provider application for the given identifier. The secret will not be part of the response, instead a &#x60;confidential&#x60; flag is indicating, whether there is a secret or not.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the application does not exist.
     * @param id OAuth application id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOauthApplicationWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getOauthApplicationRequestCreation(id);
    }

    /**
     * Get the oauth client credentials object.
     * Retrieves the OAuth 2 client credentials for the given identifier. The secret will not be part of the response, instead a &#x60;confidential&#x60; flag is indicating, whether there is a secret or not.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the object does not exist.
     * @param id OAuth Client Credentials id
     * @return OAuthClientCredentialsReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOauthClientCredentialsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getOauthClientCredentials", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<OAuthClientCredentialsReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/oauth_client_credentials/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the oauth client credentials object.
     * Retrieves the OAuth 2 client credentials for the given identifier. The secret will not be part of the response, instead a &#x60;confidential&#x60; flag is indicating, whether there is a secret or not.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the object does not exist.
     * @param id OAuth Client Credentials id
     * @return OAuthClientCredentialsReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public OAuthClientCredentialsReadModel getOauthClientCredentials(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<OAuthClientCredentialsReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getOauthClientCredentialsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get the oauth client credentials object.
     * Retrieves the OAuth 2 client credentials for the given identifier. The secret will not be part of the response, instead a &#x60;confidential&#x60; flag is indicating, whether there is a secret or not.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the object does not exist.
     * @param id OAuth Client Credentials id
     * @return ResponseEntity&lt;OAuthClientCredentialsReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OAuthClientCredentialsReadModel> getOauthClientCredentialsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<OAuthClientCredentialsReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getOauthClientCredentialsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get the oauth client credentials object.
     * Retrieves the OAuth 2 client credentials for the given identifier. The secret will not be part of the response, instead a &#x60;confidential&#x60; flag is indicating, whether there is a secret or not.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the object does not exist.
     * @param id OAuth Client Credentials id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOauthClientCredentialsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getOauthClientCredentialsRequestCreation(id);
    }
}
