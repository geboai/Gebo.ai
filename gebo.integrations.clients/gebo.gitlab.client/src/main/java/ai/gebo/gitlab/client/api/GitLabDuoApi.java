package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PostApiV4AiLlmGitCommandRequest;
import ai.gebo.gitlab.client.model.PostApiV4ChatCompletionsRequest;
import ai.gebo.gitlab.client.model.PostApiV4DuoCodeReviewEvaluationsRequest;

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
public class GitLabDuoApi {
    private ApiClient apiClient;

    public GitLabDuoApi() {
        this(new ApiClient());
    }

    public GitLabDuoApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Generate Git commands from natural text
     * Generates Git commands from natural language input
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4AiLlmGitCommandRequest The postApiV4AiLlmGitCommandRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiLlmGitCommandRequestCreation(@jakarta.annotation.Nonnull PostApiV4AiLlmGitCommandRequest postApiV4AiLlmGitCommandRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4AiLlmGitCommandRequest' is set
        if (postApiV4AiLlmGitCommandRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AiLlmGitCommandRequest' when calling postApiV4AiLlmGitCommand", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/ai/llm/git_command", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AiLlmGitCommandRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Generate Git commands from natural text
     * Generates Git commands from natural language input
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4AiLlmGitCommandRequest The postApiV4AiLlmGitCommandRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiLlmGitCommand(@jakarta.annotation.Nonnull PostApiV4AiLlmGitCommandRequest postApiV4AiLlmGitCommandRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiLlmGitCommandRequestCreation(postApiV4AiLlmGitCommandRequest).body(localVarReturnType);
    }

    /**
     * Generate Git commands from natural text
     * Generates Git commands from natural language input
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4AiLlmGitCommandRequest The postApiV4AiLlmGitCommandRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiLlmGitCommandWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4AiLlmGitCommandRequest postApiV4AiLlmGitCommandRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiLlmGitCommandRequestCreation(postApiV4AiLlmGitCommandRequest).toEntity(localVarReturnType);
    }

    /**
     * Generate Git commands from natural text
     * Generates Git commands from natural language input
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4AiLlmGitCommandRequest The postApiV4AiLlmGitCommandRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiLlmGitCommandWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4AiLlmGitCommandRequest postApiV4AiLlmGitCommandRequest) throws RestClientResponseException {
        return postApiV4AiLlmGitCommandRequestCreation(postApiV4AiLlmGitCommandRequest);
    }

    /**
     * Get connection details for third party agents
     * Returns connection details including tokens for accessing third-party agents.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>402</b> - Payment required
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AiThirdPartyAgentsDirectAccessRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/ai/third_party_agents/direct_access", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get connection details for third party agents
     * Returns connection details including tokens for accessing third-party agents.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>402</b> - Payment required
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4AiThirdPartyAgentsDirectAccess() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4AiThirdPartyAgentsDirectAccessRequestCreation().body(localVarReturnType);
    }

    /**
     * Get connection details for third party agents
     * Returns connection details including tokens for accessing third-party agents.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>402</b> - Payment required
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4AiThirdPartyAgentsDirectAccessWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AiThirdPartyAgentsDirectAccessRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get connection details for third party agents
     * Returns connection details including tokens for accessing third-party agents.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>402</b> - Payment required
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>503</b> - Service unavailable
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AiThirdPartyAgentsDirectAccessWithResponseSpec() throws RestClientResponseException {
        return postApiV4AiThirdPartyAgentsDirectAccessRequestCreation();
    }

    /**
     * Generate a Chat response
     * Generates a response for a GitLab Duo Chat question. Requests to this endpoint are proxied to the AI Gateway. This feature was introduced in GitLab 16.7.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4ChatCompletionsRequest The postApiV4ChatCompletionsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ChatCompletionsRequestCreation(@jakarta.annotation.Nonnull PostApiV4ChatCompletionsRequest postApiV4ChatCompletionsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ChatCompletionsRequest' is set
        if (postApiV4ChatCompletionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ChatCompletionsRequest' when calling postApiV4ChatCompletions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/chat/completions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ChatCompletionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Generate a Chat response
     * Generates a response for a GitLab Duo Chat question. Requests to this endpoint are proxied to the AI Gateway. This feature was introduced in GitLab 16.7.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4ChatCompletionsRequest The postApiV4ChatCompletionsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ChatCompletions(@jakarta.annotation.Nonnull PostApiV4ChatCompletionsRequest postApiV4ChatCompletionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ChatCompletionsRequestCreation(postApiV4ChatCompletionsRequest).body(localVarReturnType);
    }

    /**
     * Generate a Chat response
     * Generates a response for a GitLab Duo Chat question. Requests to this endpoint are proxied to the AI Gateway. This feature was introduced in GitLab 16.7.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4ChatCompletionsRequest The postApiV4ChatCompletionsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ChatCompletionsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ChatCompletionsRequest postApiV4ChatCompletionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ChatCompletionsRequestCreation(postApiV4ChatCompletionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Generate a Chat response
     * Generates a response for a GitLab Duo Chat question. Requests to this endpoint are proxied to the AI Gateway. This feature was introduced in GitLab 16.7.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4ChatCompletionsRequest The postApiV4ChatCompletionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ChatCompletionsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ChatCompletionsRequest postApiV4ChatCompletionsRequest) throws RestClientResponseException {
        return postApiV4ChatCompletionsRequestCreation(postApiV4ChatCompletionsRequest);
    }

    /**
     * Evaluate code review
     * Evaluates code changes using GitLab Duo code review
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4DuoCodeReviewEvaluationsRequest The postApiV4DuoCodeReviewEvaluationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4DuoCodeReviewEvaluationsRequestCreation(@jakarta.annotation.Nonnull PostApiV4DuoCodeReviewEvaluationsRequest postApiV4DuoCodeReviewEvaluationsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4DuoCodeReviewEvaluationsRequest' is set
        if (postApiV4DuoCodeReviewEvaluationsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4DuoCodeReviewEvaluationsRequest' when calling postApiV4DuoCodeReviewEvaluations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/duo_code_review/evaluations", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4DuoCodeReviewEvaluationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Evaluate code review
     * Evaluates code changes using GitLab Duo code review
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4DuoCodeReviewEvaluationsRequest The postApiV4DuoCodeReviewEvaluationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4DuoCodeReviewEvaluations(@jakarta.annotation.Nonnull PostApiV4DuoCodeReviewEvaluationsRequest postApiV4DuoCodeReviewEvaluationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4DuoCodeReviewEvaluationsRequestCreation(postApiV4DuoCodeReviewEvaluationsRequest).body(localVarReturnType);
    }

    /**
     * Evaluate code review
     * Evaluates code changes using GitLab Duo code review
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4DuoCodeReviewEvaluationsRequest The postApiV4DuoCodeReviewEvaluationsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4DuoCodeReviewEvaluationsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4DuoCodeReviewEvaluationsRequest postApiV4DuoCodeReviewEvaluationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4DuoCodeReviewEvaluationsRequestCreation(postApiV4DuoCodeReviewEvaluationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Evaluate code review
     * Evaluates code changes using GitLab Duo code review
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4DuoCodeReviewEvaluationsRequest The postApiV4DuoCodeReviewEvaluationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4DuoCodeReviewEvaluationsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4DuoCodeReviewEvaluationsRequest postApiV4DuoCodeReviewEvaluationsRequest) throws RestClientResponseException {
        return postApiV4DuoCodeReviewEvaluationsRequestCreation(postApiV4DuoCodeReviewEvaluationsRequest);
    }
}
