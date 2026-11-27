package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAuthnTokenExchange;
import ai.gebo.gitlab.client.model.PostApiV4TokenExchangeRequest;

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
public class TokenExchangeApi {
    private ApiClient apiClient;

    public TokenExchangeApi() {
        this(new ApiClient());
    }

    public TokenExchangeApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Issue a short-lived JWT for a single modular-service audience
     * Issues a short-lived RS256 JWT scoped to one modular-service audience (such as the Artifact Registry). Claims include the requesting user id (sub), organization id, and deployment realm (saas / self-managed). When called with a CI/CD ID token, the claims also carry its build metadata, a target_audience claim in the ID token names the audience, and an ID token older than 15 minutes is refused. Presented to the corresponding backend and verified against the instance JWKS. The response uses the RFC 8693 names. token and access_token have the same value, with access_token preferred where possible. For an audience bound to a resource, such as gitlab-repositories, pass the resource and optionally a scope. The token then stores the grant in authorization_details (RFC 9396). The request is refused unless the caller and the credential allow every requested action. A resource that is missing, not visible to the caller or of the wrong type returns 400 with error set to invalid_target and an error_description (RFC 8693).
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too many requests
     * @param postApiV4TokenExchangeRequest The postApiV4TokenExchangeRequest parameter
     * @return APIEntitiesAuthnTokenExchange
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4TokenExchangeRequestCreation(@jakarta.annotation.Nullable PostApiV4TokenExchangeRequest postApiV4TokenExchangeRequest) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAuthnTokenExchange> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/token_exchange", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4TokenExchangeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Issue a short-lived JWT for a single modular-service audience
     * Issues a short-lived RS256 JWT scoped to one modular-service audience (such as the Artifact Registry). Claims include the requesting user id (sub), organization id, and deployment realm (saas / self-managed). When called with a CI/CD ID token, the claims also carry its build metadata, a target_audience claim in the ID token names the audience, and an ID token older than 15 minutes is refused. Presented to the corresponding backend and verified against the instance JWKS. The response uses the RFC 8693 names. token and access_token have the same value, with access_token preferred where possible. For an audience bound to a resource, such as gitlab-repositories, pass the resource and optionally a scope. The token then stores the grant in authorization_details (RFC 9396). The request is refused unless the caller and the credential allow every requested action. A resource that is missing, not visible to the caller or of the wrong type returns 400 with error set to invalid_target and an error_description (RFC 8693).
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too many requests
     * @param postApiV4TokenExchangeRequest The postApiV4TokenExchangeRequest parameter
     * @return APIEntitiesAuthnTokenExchange
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAuthnTokenExchange postApiV4TokenExchange(@jakarta.annotation.Nullable PostApiV4TokenExchangeRequest postApiV4TokenExchangeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAuthnTokenExchange> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TokenExchangeRequestCreation(postApiV4TokenExchangeRequest).body(localVarReturnType);
    }

    /**
     * Issue a short-lived JWT for a single modular-service audience
     * Issues a short-lived RS256 JWT scoped to one modular-service audience (such as the Artifact Registry). Claims include the requesting user id (sub), organization id, and deployment realm (saas / self-managed). When called with a CI/CD ID token, the claims also carry its build metadata, a target_audience claim in the ID token names the audience, and an ID token older than 15 minutes is refused. Presented to the corresponding backend and verified against the instance JWKS. The response uses the RFC 8693 names. token and access_token have the same value, with access_token preferred where possible. For an audience bound to a resource, such as gitlab-repositories, pass the resource and optionally a scope. The token then stores the grant in authorization_details (RFC 9396). The request is refused unless the caller and the credential allow every requested action. A resource that is missing, not visible to the caller or of the wrong type returns 400 with error set to invalid_target and an error_description (RFC 8693).
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too many requests
     * @param postApiV4TokenExchangeRequest The postApiV4TokenExchangeRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAuthnTokenExchange&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAuthnTokenExchange> postApiV4TokenExchangeWithHttpInfo(@jakarta.annotation.Nullable PostApiV4TokenExchangeRequest postApiV4TokenExchangeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAuthnTokenExchange> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4TokenExchangeRequestCreation(postApiV4TokenExchangeRequest).toEntity(localVarReturnType);
    }

    /**
     * Issue a short-lived JWT for a single modular-service audience
     * Issues a short-lived RS256 JWT scoped to one modular-service audience (such as the Artifact Registry). Claims include the requesting user id (sub), organization id, and deployment realm (saas / self-managed). When called with a CI/CD ID token, the claims also carry its build metadata, a target_audience claim in the ID token names the audience, and an ID token older than 15 minutes is refused. Presented to the corresponding backend and verified against the instance JWKS. The response uses the RFC 8693 names. token and access_token have the same value, with access_token preferred where possible. For an audience bound to a resource, such as gitlab-repositories, pass the resource and optionally a scope. The token then stores the grant in authorization_details (RFC 9396). The request is refused unless the caller and the credential allow every requested action. A resource that is missing, not visible to the caller or of the wrong type returns 400 with error set to invalid_target and an error_description (RFC 8693).
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>429</b> - Too many requests
     * @param postApiV4TokenExchangeRequest The postApiV4TokenExchangeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4TokenExchangeWithResponseSpec(@jakarta.annotation.Nullable PostApiV4TokenExchangeRequest postApiV4TokenExchangeRequest) throws RestClientResponseException {
        return postApiV4TokenExchangeRequestCreation(postApiV4TokenExchangeRequest);
    }
}
