package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PostApiV4CodeSuggestionsCompletionsRequest;
import ai.gebo.gitlab.client.model.PostApiV4CodeSuggestionsDirectAccessRequest;
import ai.gebo.gitlab.client.model.PostApiV4CodeSuggestionsEnabledRequest;

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
public class CodeSuggestionsApi {
    private ApiClient apiClient;

    public CodeSuggestionsApi() {
        this(new ApiClient());
    }

    public CodeSuggestionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Generate code completions
     * Generates code completions. Uses the AI abstraction layer to generate code completions. Requests to this endpoint are proxied to the AI Gateway. This endpoint has a rate limit of 60 requests per minute for each user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>413</b> - Payload too large
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4CodeSuggestionsCompletionsRequest The postApiV4CodeSuggestionsCompletionsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4CodeSuggestionsCompletionsRequestCreation(@jakarta.annotation.Nonnull PostApiV4CodeSuggestionsCompletionsRequest postApiV4CodeSuggestionsCompletionsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4CodeSuggestionsCompletionsRequest' is set
        if (postApiV4CodeSuggestionsCompletionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4CodeSuggestionsCompletionsRequest' when calling postApiV4CodeSuggestionsCompletions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/code_suggestions/completions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4CodeSuggestionsCompletionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Generate code completions
     * Generates code completions. Uses the AI abstraction layer to generate code completions. Requests to this endpoint are proxied to the AI Gateway. This endpoint has a rate limit of 60 requests per minute for each user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>413</b> - Payload too large
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4CodeSuggestionsCompletionsRequest The postApiV4CodeSuggestionsCompletionsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4CodeSuggestionsCompletions(@jakarta.annotation.Nonnull PostApiV4CodeSuggestionsCompletionsRequest postApiV4CodeSuggestionsCompletionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4CodeSuggestionsCompletionsRequestCreation(postApiV4CodeSuggestionsCompletionsRequest).body(localVarReturnType);
    }

    /**
     * Generate code completions
     * Generates code completions. Uses the AI abstraction layer to generate code completions. Requests to this endpoint are proxied to the AI Gateway. This endpoint has a rate limit of 60 requests per minute for each user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>413</b> - Payload too large
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4CodeSuggestionsCompletionsRequest The postApiV4CodeSuggestionsCompletionsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4CodeSuggestionsCompletionsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4CodeSuggestionsCompletionsRequest postApiV4CodeSuggestionsCompletionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4CodeSuggestionsCompletionsRequestCreation(postApiV4CodeSuggestionsCompletionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Generate code completions
     * Generates code completions. Uses the AI abstraction layer to generate code completions. Requests to this endpoint are proxied to the AI Gateway. This endpoint has a rate limit of 60 requests per minute for each user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>413</b> - Payload too large
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param postApiV4CodeSuggestionsCompletionsRequest The postApiV4CodeSuggestionsCompletionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4CodeSuggestionsCompletionsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4CodeSuggestionsCompletionsRequest postApiV4CodeSuggestionsCompletionsRequest) throws RestClientResponseException {
        return postApiV4CodeSuggestionsCompletionsRequestCreation(postApiV4CodeSuggestionsCompletionsRequest);
    }

    /**
     * Retrieve connection details
     * Retrieves user-specific connection details which can be used by IDEs or clients for telemetry, including the instance ID, instance version, realm, global user ID, host name, feature enablement type, and the IDs of namespaces with GitLab Duo access (GitLab.com only, empty elsewhere). This endpoint has a rate limit of 10 requests per minute for each user. This feature was introduced in GitLab 18.3.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4CodeSuggestionsConnectionDetailsRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/code_suggestions/connection_details", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve connection details
     * Retrieves user-specific connection details which can be used by IDEs or clients for telemetry, including the instance ID, instance version, realm, global user ID, host name, feature enablement type, and the IDs of namespaces with GitLab Duo access (GitLab.com only, empty elsewhere). This endpoint has a rate limit of 10 requests per minute for each user. This feature was introduced in GitLab 18.3.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4CodeSuggestionsConnectionDetails() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4CodeSuggestionsConnectionDetailsRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve connection details
     * Retrieves user-specific connection details which can be used by IDEs or clients for telemetry, including the instance ID, instance version, realm, global user ID, host name, feature enablement type, and the IDs of namespaces with GitLab Duo access (GitLab.com only, empty elsewhere). This endpoint has a rate limit of 10 requests per minute for each user. This feature was introduced in GitLab 18.3.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4CodeSuggestionsConnectionDetailsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4CodeSuggestionsConnectionDetailsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve connection details
     * Retrieves user-specific connection details which can be used by IDEs or clients for telemetry, including the instance ID, instance version, realm, global user ID, host name, feature enablement type, and the IDs of namespaces with GitLab Duo access (GitLab.com only, empty elsewhere). This endpoint has a rate limit of 10 requests per minute for each user. This feature was introduced in GitLab 18.3.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4CodeSuggestionsConnectionDetailsWithResponseSpec() throws RestClientResponseException {
        return postApiV4CodeSuggestionsConnectionDetailsRequestCreation();
    }

    /**
     * Retrieve direct connection details for the AI Gateway
     * Retrieves user-specific connection details which can be used by IDEs or clients to send &#x60;completion&#x60; requests to Code Suggestions directly through the AI Gateway. The response includes the headers that must be proxied to the AI Gateway and the required authentication token. This endpoint has a rate limit of 50 requests per minute for each user. This feature was introduced in GitLab 17.0 with a feature flag named &#x60;code_suggestions_direct_completions&#x60;, disabled by default, and made generally available in GitLab 17.2.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4CodeSuggestionsDirectAccessRequest The postApiV4CodeSuggestionsDirectAccessRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4CodeSuggestionsDirectAccessRequestCreation(@jakarta.annotation.Nullable PostApiV4CodeSuggestionsDirectAccessRequest postApiV4CodeSuggestionsDirectAccessRequest) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/code_suggestions/direct_access", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4CodeSuggestionsDirectAccessRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve direct connection details for the AI Gateway
     * Retrieves user-specific connection details which can be used by IDEs or clients to send &#x60;completion&#x60; requests to Code Suggestions directly through the AI Gateway. The response includes the headers that must be proxied to the AI Gateway and the required authentication token. This endpoint has a rate limit of 50 requests per minute for each user. This feature was introduced in GitLab 17.0 with a feature flag named &#x60;code_suggestions_direct_completions&#x60;, disabled by default, and made generally available in GitLab 17.2.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4CodeSuggestionsDirectAccessRequest The postApiV4CodeSuggestionsDirectAccessRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4CodeSuggestionsDirectAccess(@jakarta.annotation.Nullable PostApiV4CodeSuggestionsDirectAccessRequest postApiV4CodeSuggestionsDirectAccessRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4CodeSuggestionsDirectAccessRequestCreation(postApiV4CodeSuggestionsDirectAccessRequest).body(localVarReturnType);
    }

    /**
     * Retrieve direct connection details for the AI Gateway
     * Retrieves user-specific connection details which can be used by IDEs or clients to send &#x60;completion&#x60; requests to Code Suggestions directly through the AI Gateway. The response includes the headers that must be proxied to the AI Gateway and the required authentication token. This endpoint has a rate limit of 50 requests per minute for each user. This feature was introduced in GitLab 17.0 with a feature flag named &#x60;code_suggestions_direct_completions&#x60;, disabled by default, and made generally available in GitLab 17.2.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4CodeSuggestionsDirectAccessRequest The postApiV4CodeSuggestionsDirectAccessRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4CodeSuggestionsDirectAccessWithHttpInfo(@jakarta.annotation.Nullable PostApiV4CodeSuggestionsDirectAccessRequest postApiV4CodeSuggestionsDirectAccessRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4CodeSuggestionsDirectAccessRequestCreation(postApiV4CodeSuggestionsDirectAccessRequest).toEntity(localVarReturnType);
    }

    /**
     * Retrieve direct connection details for the AI Gateway
     * Retrieves user-specific connection details which can be used by IDEs or clients to send &#x60;completion&#x60; requests to Code Suggestions directly through the AI Gateway. The response includes the headers that must be proxied to the AI Gateway and the required authentication token. This endpoint has a rate limit of 50 requests per minute for each user. This feature was introduced in GitLab 17.0 with a feature flag named &#x60;code_suggestions_direct_completions&#x60;, disabled by default, and made generally available in GitLab 17.2.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param postApiV4CodeSuggestionsDirectAccessRequest The postApiV4CodeSuggestionsDirectAccessRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4CodeSuggestionsDirectAccessWithResponseSpec(@jakarta.annotation.Nullable PostApiV4CodeSuggestionsDirectAccessRequest postApiV4CodeSuggestionsDirectAccessRequest) throws RestClientResponseException {
        return postApiV4CodeSuggestionsDirectAccessRequestCreation(postApiV4CodeSuggestionsDirectAccessRequest);
    }

    /**
     * Validate status of Code Suggestions
     * Validates the status Code Suggestions for a specified project. This checks if the project has &#x60;code_suggestions&#x60; enabled or if an ancestor group has the Code Suggestions add-on enabled.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Code Suggestions Disabled
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param postApiV4CodeSuggestionsEnabledRequest The postApiV4CodeSuggestionsEnabledRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4CodeSuggestionsEnabledRequestCreation(@jakarta.annotation.Nonnull PostApiV4CodeSuggestionsEnabledRequest postApiV4CodeSuggestionsEnabledRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4CodeSuggestionsEnabledRequest' is set
        if (postApiV4CodeSuggestionsEnabledRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4CodeSuggestionsEnabledRequest' when calling postApiV4CodeSuggestionsEnabled", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/code_suggestions/enabled", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4CodeSuggestionsEnabledRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Validate status of Code Suggestions
     * Validates the status Code Suggestions for a specified project. This checks if the project has &#x60;code_suggestions&#x60; enabled or if an ancestor group has the Code Suggestions add-on enabled.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Code Suggestions Disabled
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param postApiV4CodeSuggestionsEnabledRequest The postApiV4CodeSuggestionsEnabledRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4CodeSuggestionsEnabled(@jakarta.annotation.Nonnull PostApiV4CodeSuggestionsEnabledRequest postApiV4CodeSuggestionsEnabledRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4CodeSuggestionsEnabledRequestCreation(postApiV4CodeSuggestionsEnabledRequest).body(localVarReturnType);
    }

    /**
     * Validate status of Code Suggestions
     * Validates the status Code Suggestions for a specified project. This checks if the project has &#x60;code_suggestions&#x60; enabled or if an ancestor group has the Code Suggestions add-on enabled.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Code Suggestions Disabled
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param postApiV4CodeSuggestionsEnabledRequest The postApiV4CodeSuggestionsEnabledRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4CodeSuggestionsEnabledWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4CodeSuggestionsEnabledRequest postApiV4CodeSuggestionsEnabledRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4CodeSuggestionsEnabledRequestCreation(postApiV4CodeSuggestionsEnabledRequest).toEntity(localVarReturnType);
    }

    /**
     * Validate status of Code Suggestions
     * Validates the status Code Suggestions for a specified project. This checks if the project has &#x60;code_suggestions&#x60; enabled or if an ancestor group has the Code Suggestions add-on enabled.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - 403 Code Suggestions Disabled
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param postApiV4CodeSuggestionsEnabledRequest The postApiV4CodeSuggestionsEnabledRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4CodeSuggestionsEnabledWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4CodeSuggestionsEnabledRequest postApiV4CodeSuggestionsEnabledRequest) throws RestClientResponseException {
        return postApiV4CodeSuggestionsEnabledRequestCreation(postApiV4CodeSuggestionsEnabledRequest);
    }
}
