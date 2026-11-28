package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.AddRelatedIssueRequest;
import ai.gebo.redmine.client.model.Errors;

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
public class RepositoriesApi {
    private ApiClient apiClient;

    public RepositoriesApi() {
        this(new ApiClient());
    }

    public RepositoriesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add related issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param repositoryId The repositoryId parameter
     * @param revision The revision parameter
     * @param addRelatedIssueRequest The addRelatedIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addRelatedIssueRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull java.lang.String repositoryId, @jakarta.annotation.Nonnull String revision, @jakarta.annotation.Nonnull AddRelatedIssueRequest addRelatedIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling addRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling addRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryId' is set
        if (repositoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryId' when calling addRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'revision' is set
        if (revision == null) {
            throw new RestClientResponseException("Missing the required parameter 'revision' when calling addRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'addRelatedIssueRequest' is set
        if (addRelatedIssueRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'addRelatedIssueRequest' when calling addRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);
        pathParams.put("repository_id", repositoryId);
        pathParams.put("revision", revision);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/repository/{repository_id}/revisions/{revision}/issues.{format}", HttpMethod.POST, pathParams, localVarQueryParams, addRelatedIssueRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add related issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param repositoryId The repositoryId parameter
     * @param revision The revision parameter
     * @param addRelatedIssueRequest The addRelatedIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void addRelatedIssue(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull java.lang.String repositoryId, @jakarta.annotation.Nonnull String revision, @jakarta.annotation.Nonnull AddRelatedIssueRequest addRelatedIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        addRelatedIssueRequestCreation(format, projectId, repositoryId, revision, addRelatedIssueRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Add related issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param repositoryId The repositoryId parameter
     * @param revision The revision parameter
     * @param addRelatedIssueRequest The addRelatedIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> addRelatedIssueWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull java.lang.String repositoryId, @jakarta.annotation.Nonnull String revision, @jakarta.annotation.Nonnull AddRelatedIssueRequest addRelatedIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addRelatedIssueRequestCreation(format, projectId, repositoryId, revision, addRelatedIssueRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Add related issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param repositoryId The repositoryId parameter
     * @param revision The revision parameter
     * @param addRelatedIssueRequest The addRelatedIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addRelatedIssueWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull java.lang.String repositoryId, @jakarta.annotation.Nonnull String revision, @jakarta.annotation.Nonnull AddRelatedIssueRequest addRelatedIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return addRelatedIssueRequestCreation(format, projectId, repositoryId, revision, addRelatedIssueRequest, xRedmineSwitchUser);
    }

    /**
     * Remove related issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param repositoryId The repositoryId parameter
     * @param revision The revision parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec removeRelatedIssueRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull java.lang.String repositoryId, @jakarta.annotation.Nonnull String revision, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling removeRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling removeRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryId' is set
        if (repositoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryId' when calling removeRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'revision' is set
        if (revision == null) {
            throw new RestClientResponseException("Missing the required parameter 'revision' when calling removeRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling removeRelatedIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);
        pathParams.put("repository_id", repositoryId);
        pathParams.put("revision", revision);
        pathParams.put("issue_id", issueId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/repository/{repository_id}/revisions/{revision}/issues/{issue_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove related issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param repositoryId The repositoryId parameter
     * @param revision The revision parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void removeRelatedIssue(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull java.lang.String repositoryId, @jakarta.annotation.Nonnull String revision, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        removeRelatedIssueRequestCreation(format, projectId, repositoryId, revision, issueId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Remove related issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param repositoryId The repositoryId parameter
     * @param revision The revision parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> removeRelatedIssueWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull java.lang.String repositoryId, @jakarta.annotation.Nonnull String revision, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return removeRelatedIssueRequestCreation(format, projectId, repositoryId, revision, issueId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Remove related issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param repositoryId The repositoryId parameter
     * @param revision The revision parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec removeRelatedIssueWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull java.lang.String repositoryId, @jakarta.annotation.Nonnull String revision, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return removeRelatedIssueRequestCreation(format, projectId, repositoryId, revision, issueId, xRedmineSwitchUser);
    }
}
