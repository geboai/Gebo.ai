package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;


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
public class AuthnApi {
    private ApiClient apiClient;

    public AuthnApi() {
        this(new ApiClient());
    }

    public AuthnApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Return the OIDC ID-token claims for the caller identified by an IAM OAuth token
     * Authenticates exclusively via an IAM-issued OAuth JWT and returns the subset of OIDC claims documented as \&quot;Included in ID Token\&quot; in the OpenID Connect provider docs
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4IamUserinfoRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/iam/userinfo", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Return the OIDC ID-token claims for the caller identified by an IAM OAuth token
     * Authenticates exclusively via an IAM-issued OAuth JWT and returns the subset of OIDC claims documented as \&quot;Included in ID Token\&quot; in the OpenID Connect provider docs
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4IamUserinfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4IamUserinfoRequestCreation().body(localVarReturnType);
    }

    /**
     * Return the OIDC ID-token claims for the caller identified by an IAM OAuth token
     * Authenticates exclusively via an IAM-issued OAuth JWT and returns the subset of OIDC claims documented as \&quot;Included in ID Token\&quot; in the OpenID Connect provider docs
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4IamUserinfoWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4IamUserinfoRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Return the OIDC ID-token claims for the caller identified by an IAM OAuth token
     * Authenticates exclusively via an IAM-issued OAuth JWT and returns the subset of OIDC claims documented as \&quot;Included in ID Token\&quot; in the OpenID Connect provider docs
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4IamUserinfoWithResponseSpec() throws RestClientResponseException {
        return getApiV4IamUserinfoRequestCreation();
    }
}
