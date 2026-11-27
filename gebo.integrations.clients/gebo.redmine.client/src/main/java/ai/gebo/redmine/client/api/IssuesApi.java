package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.AddWatcher;
import ai.gebo.redmine.client.model.CreateIssue201Response;
import ai.gebo.redmine.client.model.CreateIssueRequest;
import ai.gebo.redmine.client.model.CreateIssueRequestIssueProjectId;
import ai.gebo.redmine.client.model.CreateIssueUnderProjectRequest;
import ai.gebo.redmine.client.model.Errors;
import ai.gebo.redmine.client.model.GetIssue200Response;
import ai.gebo.redmine.client.model.GetIssues200Response;
import ai.gebo.redmine.client.model.UpdateIssueRequest;

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
public class IssuesApi {
    private ApiClient apiClient;

    public IssuesApi() {
        this(new ApiClient());
    }

    public IssuesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add watcher
     * Adds a user or regular group as a watcher. Unknown IDs are ignored and still return 204. A nonexistent issue returns 403.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param addWatcher The addWatcher parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Adding-a-watcher">Add watcher Documentation</a>
     */
    private ResponseSpec addWatcherRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable AddWatcher addWatcher) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling addWatcher", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling addWatcher", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issues/{issue_id}/watchers.{format}", HttpMethod.POST, pathParams, localVarQueryParams, addWatcher, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add watcher
     * Adds a user or regular group as a watcher. Unknown IDs are ignored and still return 204. A nonexistent issue returns 403.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param addWatcher The addWatcher parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Adding-a-watcher">Add watcher Documentation</a>
     */
    public void addWatcher(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable AddWatcher addWatcher) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        addWatcherRequestCreation(format, issueId, xRedmineSwitchUser, addWatcher).body(localVarReturnType);
    }

    /**
     * Add watcher
     * Adds a user or regular group as a watcher. Unknown IDs are ignored and still return 204. A nonexistent issue returns 403.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param addWatcher The addWatcher parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Adding-a-watcher">Add watcher Documentation</a>
     */
    public ResponseEntity<Void> addWatcherWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable AddWatcher addWatcher) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addWatcherRequestCreation(format, issueId, xRedmineSwitchUser, addWatcher).toEntity(localVarReturnType);
    }

    /**
     * Add watcher
     * Adds a user or regular group as a watcher. Unknown IDs are ignored and still return 204. A nonexistent issue returns 403.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param addWatcher The addWatcher parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Adding-a-watcher">Add watcher Documentation</a>
     */
    public ResponseSpec addWatcherWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable AddWatcher addWatcher) throws RestClientResponseException {
        return addWatcherRequestCreation(format, issueId, xRedmineSwitchUser, addWatcher);
    }

    /**
     * Create issue
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createIssueRequest The createIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param copyFrom ID of a visible issue to copy. The current user needs permission to copy issues.
     * @param linkCopy Link the new issue to its source with a copied relation. Used with &#x60;copy_from&#x60;.
     * @param copyAttachments Copy attachments from the source issue. Used with &#x60;copy_from&#x60;.
     * @param copySubtasks Copy visible subtasks from the source issue. Used with &#x60;copy_from&#x60;.
     * @return CreateIssue201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Creating-an-issue">Create issue Documentation</a>
     */
    private ResponseSpec createIssueRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequest createIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer copyFrom, @jakarta.annotation.Nullable Integer linkCopy, @jakarta.annotation.Nullable Integer copyAttachments, @jakarta.annotation.Nullable Integer copySubtasks) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createIssueRequest' is set
        if (createIssueRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createIssueRequest' when calling createIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copy_from", copyFrom));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "link_copy", linkCopy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copy_attachments", copyAttachments));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copy_subtasks", copySubtasks));

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

        ParameterizedTypeReference<CreateIssue201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issues.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createIssueRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create issue
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createIssueRequest The createIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param copyFrom ID of a visible issue to copy. The current user needs permission to copy issues.
     * @param linkCopy Link the new issue to its source with a copied relation. Used with &#x60;copy_from&#x60;.
     * @param copyAttachments Copy attachments from the source issue. Used with &#x60;copy_from&#x60;.
     * @param copySubtasks Copy visible subtasks from the source issue. Used with &#x60;copy_from&#x60;.
     * @return CreateIssue201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Creating-an-issue">Create issue Documentation</a>
     */
    public CreateIssue201Response createIssue(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequest createIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer copyFrom, @jakarta.annotation.Nullable Integer linkCopy, @jakarta.annotation.Nullable Integer copyAttachments, @jakarta.annotation.Nullable Integer copySubtasks) throws RestClientResponseException {
        ParameterizedTypeReference<CreateIssue201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createIssueRequestCreation(format, createIssueRequest, xRedmineSwitchUser, copyFrom, linkCopy, copyAttachments, copySubtasks).body(localVarReturnType);
    }

    /**
     * Create issue
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createIssueRequest The createIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param copyFrom ID of a visible issue to copy. The current user needs permission to copy issues.
     * @param linkCopy Link the new issue to its source with a copied relation. Used with &#x60;copy_from&#x60;.
     * @param copyAttachments Copy attachments from the source issue. Used with &#x60;copy_from&#x60;.
     * @param copySubtasks Copy visible subtasks from the source issue. Used with &#x60;copy_from&#x60;.
     * @return ResponseEntity&lt;CreateIssue201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Creating-an-issue">Create issue Documentation</a>
     */
    public ResponseEntity<CreateIssue201Response> createIssueWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequest createIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer copyFrom, @jakarta.annotation.Nullable Integer linkCopy, @jakarta.annotation.Nullable Integer copyAttachments, @jakarta.annotation.Nullable Integer copySubtasks) throws RestClientResponseException {
        ParameterizedTypeReference<CreateIssue201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createIssueRequestCreation(format, createIssueRequest, xRedmineSwitchUser, copyFrom, linkCopy, copyAttachments, copySubtasks).toEntity(localVarReturnType);
    }

    /**
     * Create issue
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createIssueRequest The createIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param copyFrom ID of a visible issue to copy. The current user needs permission to copy issues.
     * @param linkCopy Link the new issue to its source with a copied relation. Used with &#x60;copy_from&#x60;.
     * @param copyAttachments Copy attachments from the source issue. Used with &#x60;copy_from&#x60;.
     * @param copySubtasks Copy visible subtasks from the source issue. Used with &#x60;copy_from&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Creating-an-issue">Create issue Documentation</a>
     */
    public ResponseSpec createIssueWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequest createIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer copyFrom, @jakarta.annotation.Nullable Integer linkCopy, @jakarta.annotation.Nullable Integer copyAttachments, @jakarta.annotation.Nullable Integer copySubtasks) throws RestClientResponseException {
        return createIssueRequestCreation(format, createIssueRequest, xRedmineSwitchUser, copyFrom, linkCopy, copyAttachments, copySubtasks);
    }

    /**
     * Create issue under project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param copyFrom ID of a visible issue to copy. The current user needs permission to copy issues.
     * @param linkCopy Link the new issue to its source with a copied relation. Used with &#x60;copy_from&#x60;.
     * @param copyAttachments Copy attachments from the source issue. Used with &#x60;copy_from&#x60;.
     * @param copySubtasks Copy visible subtasks from the source issue. Used with &#x60;copy_from&#x60;.
     * @param createIssueUnderProjectRequest Required when &#x60;copy_from&#x60; is omitted. Omit the body to copy the source issue into this project without overriding fields.
     * @return CreateIssue201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Creating-an-issue">Create issue under project Documentation</a>
     */
    private ResponseSpec createIssueUnderProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer copyFrom, @jakarta.annotation.Nullable Integer linkCopy, @jakarta.annotation.Nullable Integer copyAttachments, @jakarta.annotation.Nullable Integer copySubtasks, @jakarta.annotation.Nullable CreateIssueUnderProjectRequest createIssueUnderProjectRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createIssueUnderProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling createIssueUnderProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copy_from", copyFrom));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "link_copy", linkCopy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copy_attachments", copyAttachments));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copy_subtasks", copySubtasks));

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

        ParameterizedTypeReference<CreateIssue201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/issues.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createIssueUnderProjectRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create issue under project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param copyFrom ID of a visible issue to copy. The current user needs permission to copy issues.
     * @param linkCopy Link the new issue to its source with a copied relation. Used with &#x60;copy_from&#x60;.
     * @param copyAttachments Copy attachments from the source issue. Used with &#x60;copy_from&#x60;.
     * @param copySubtasks Copy visible subtasks from the source issue. Used with &#x60;copy_from&#x60;.
     * @param createIssueUnderProjectRequest Required when &#x60;copy_from&#x60; is omitted. Omit the body to copy the source issue into this project without overriding fields.
     * @return CreateIssue201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Creating-an-issue">Create issue under project Documentation</a>
     */
    public CreateIssue201Response createIssueUnderProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer copyFrom, @jakarta.annotation.Nullable Integer linkCopy, @jakarta.annotation.Nullable Integer copyAttachments, @jakarta.annotation.Nullable Integer copySubtasks, @jakarta.annotation.Nullable CreateIssueUnderProjectRequest createIssueUnderProjectRequest) throws RestClientResponseException {
        ParameterizedTypeReference<CreateIssue201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createIssueUnderProjectRequestCreation(format, projectId, xRedmineSwitchUser, copyFrom, linkCopy, copyAttachments, copySubtasks, createIssueUnderProjectRequest).body(localVarReturnType);
    }

    /**
     * Create issue under project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param copyFrom ID of a visible issue to copy. The current user needs permission to copy issues.
     * @param linkCopy Link the new issue to its source with a copied relation. Used with &#x60;copy_from&#x60;.
     * @param copyAttachments Copy attachments from the source issue. Used with &#x60;copy_from&#x60;.
     * @param copySubtasks Copy visible subtasks from the source issue. Used with &#x60;copy_from&#x60;.
     * @param createIssueUnderProjectRequest Required when &#x60;copy_from&#x60; is omitted. Omit the body to copy the source issue into this project without overriding fields.
     * @return ResponseEntity&lt;CreateIssue201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Creating-an-issue">Create issue under project Documentation</a>
     */
    public ResponseEntity<CreateIssue201Response> createIssueUnderProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer copyFrom, @jakarta.annotation.Nullable Integer linkCopy, @jakarta.annotation.Nullable Integer copyAttachments, @jakarta.annotation.Nullable Integer copySubtasks, @jakarta.annotation.Nullable CreateIssueUnderProjectRequest createIssueUnderProjectRequest) throws RestClientResponseException {
        ParameterizedTypeReference<CreateIssue201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createIssueUnderProjectRequestCreation(format, projectId, xRedmineSwitchUser, copyFrom, linkCopy, copyAttachments, copySubtasks, createIssueUnderProjectRequest).toEntity(localVarReturnType);
    }

    /**
     * Create issue under project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param copyFrom ID of a visible issue to copy. The current user needs permission to copy issues.
     * @param linkCopy Link the new issue to its source with a copied relation. Used with &#x60;copy_from&#x60;.
     * @param copyAttachments Copy attachments from the source issue. Used with &#x60;copy_from&#x60;.
     * @param copySubtasks Copy visible subtasks from the source issue. Used with &#x60;copy_from&#x60;.
     * @param createIssueUnderProjectRequest Required when &#x60;copy_from&#x60; is omitted. Omit the body to copy the source issue into this project without overriding fields.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Creating-an-issue">Create issue under project Documentation</a>
     */
    public ResponseSpec createIssueUnderProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer copyFrom, @jakarta.annotation.Nullable Integer linkCopy, @jakarta.annotation.Nullable Integer copyAttachments, @jakarta.annotation.Nullable Integer copySubtasks, @jakarta.annotation.Nullable CreateIssueUnderProjectRequest createIssueUnderProjectRequest) throws RestClientResponseException {
        return createIssueUnderProjectRequestCreation(format, projectId, xRedmineSwitchUser, copyFrom, linkCopy, copyAttachments, copySubtasks, createIssueUnderProjectRequest);
    }

    /**
     * Delete issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param todo How to handle time entries on the deleted issues: &#x60;destroy&#x60; (default) deletes them, &#x60;nullify&#x60; keeps them without an issue when the time-tracking settings allow it, and &#x60;reassign&#x60; moves them to &#x60;reassign_to_id&#x60;. Redmine uses this option when the selected issues have positive total spent time.
     * @param reassignToId Destination issue ID for &#x60;todo&#x3D;reassign&#x60;. The destination must not be one of the issues being deleted and must belong to the same project as the time entries being moved.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Deleting-an-issue">Delete issue Documentation</a>
     */
    private ResponseSpec deleteIssueRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String todo, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling deleteIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_id", issueId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "todo", todo));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reassign_to_id", reassignToId));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issues/{issue_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param todo How to handle time entries on the deleted issues: &#x60;destroy&#x60; (default) deletes them, &#x60;nullify&#x60; keeps them without an issue when the time-tracking settings allow it, and &#x60;reassign&#x60; moves them to &#x60;reassign_to_id&#x60;. Redmine uses this option when the selected issues have positive total spent time.
     * @param reassignToId Destination issue ID for &#x60;todo&#x3D;reassign&#x60;. The destination must not be one of the issues being deleted and must belong to the same project as the time entries being moved.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Deleting-an-issue">Delete issue Documentation</a>
     */
    public void deleteIssue(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String todo, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteIssueRequestCreation(format, issueId, xRedmineSwitchUser, todo, reassignToId).body(localVarReturnType);
    }

    /**
     * Delete issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param todo How to handle time entries on the deleted issues: &#x60;destroy&#x60; (default) deletes them, &#x60;nullify&#x60; keeps them without an issue when the time-tracking settings allow it, and &#x60;reassign&#x60; moves them to &#x60;reassign_to_id&#x60;. Redmine uses this option when the selected issues have positive total spent time.
     * @param reassignToId Destination issue ID for &#x60;todo&#x3D;reassign&#x60;. The destination must not be one of the issues being deleted and must belong to the same project as the time entries being moved.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Deleting-an-issue">Delete issue Documentation</a>
     */
    public ResponseEntity<Void> deleteIssueWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String todo, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteIssueRequestCreation(format, issueId, xRedmineSwitchUser, todo, reassignToId).toEntity(localVarReturnType);
    }

    /**
     * Delete issue
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param todo How to handle time entries on the deleted issues: &#x60;destroy&#x60; (default) deletes them, &#x60;nullify&#x60; keeps them without an issue when the time-tracking settings allow it, and &#x60;reassign&#x60; moves them to &#x60;reassign_to_id&#x60;. Redmine uses this option when the selected issues have positive total spent time.
     * @param reassignToId Destination issue ID for &#x60;todo&#x3D;reassign&#x60;. The destination must not be one of the issues being deleted and must belong to the same project as the time entries being moved.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Deleting-an-issue">Delete issue Documentation</a>
     */
    public ResponseSpec deleteIssueWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String todo, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        return deleteIssueRequestCreation(format, issueId, xRedmineSwitchUser, todo, reassignToId);
    }

    /**
     * Delete issues
     * Deletes the selected issues and their subtasks. Unknown IDs are ignored if at least one issue is found. The current user must be allowed to delete every found issue.
     * <p><b>204</b> - The issues were deleted.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - None of the selected issues were found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param ids IDs of the records to delete.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param todo How to handle time entries on the deleted issues: &#x60;destroy&#x60; (default) deletes them, &#x60;nullify&#x60; keeps them without an issue when the time-tracking settings allow it, and &#x60;reassign&#x60; moves them to &#x60;reassign_to_id&#x60;. Redmine uses this option when the selected issues have positive total spent time.
     * @param reassignToId Destination issue ID for &#x60;todo&#x3D;reassign&#x60;. The destination must not be one of the issues being deleted and must belong to the same project as the time entries being moved.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteIssuesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull List<Integer> ids, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String todo, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ids' is set
        if (ids == null) {
            throw new RestClientResponseException("Missing the required parameter 'ids' when calling deleteIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "ids[]", ids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "todo", todo));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reassign_to_id", reassignToId));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issues.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete issues
     * Deletes the selected issues and their subtasks. Unknown IDs are ignored if at least one issue is found. The current user must be allowed to delete every found issue.
     * <p><b>204</b> - The issues were deleted.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - None of the selected issues were found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param ids IDs of the records to delete.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param todo How to handle time entries on the deleted issues: &#x60;destroy&#x60; (default) deletes them, &#x60;nullify&#x60; keeps them without an issue when the time-tracking settings allow it, and &#x60;reassign&#x60; moves them to &#x60;reassign_to_id&#x60;. Redmine uses this option when the selected issues have positive total spent time.
     * @param reassignToId Destination issue ID for &#x60;todo&#x3D;reassign&#x60;. The destination must not be one of the issues being deleted and must belong to the same project as the time entries being moved.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteIssues(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull List<Integer> ids, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String todo, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteIssuesRequestCreation(format, ids, xRedmineSwitchUser, todo, reassignToId).body(localVarReturnType);
    }

    /**
     * Delete issues
     * Deletes the selected issues and their subtasks. Unknown IDs are ignored if at least one issue is found. The current user must be allowed to delete every found issue.
     * <p><b>204</b> - The issues were deleted.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - None of the selected issues were found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param ids IDs of the records to delete.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param todo How to handle time entries on the deleted issues: &#x60;destroy&#x60; (default) deletes them, &#x60;nullify&#x60; keeps them without an issue when the time-tracking settings allow it, and &#x60;reassign&#x60; moves them to &#x60;reassign_to_id&#x60;. Redmine uses this option when the selected issues have positive total spent time.
     * @param reassignToId Destination issue ID for &#x60;todo&#x3D;reassign&#x60;. The destination must not be one of the issues being deleted and must belong to the same project as the time entries being moved.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteIssuesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull List<Integer> ids, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String todo, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteIssuesRequestCreation(format, ids, xRedmineSwitchUser, todo, reassignToId).toEntity(localVarReturnType);
    }

    /**
     * Delete issues
     * Deletes the selected issues and their subtasks. Unknown IDs are ignored if at least one issue is found. The current user must be allowed to delete every found issue.
     * <p><b>204</b> - The issues were deleted.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - None of the selected issues were found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param ids IDs of the records to delete.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param todo How to handle time entries on the deleted issues: &#x60;destroy&#x60; (default) deletes them, &#x60;nullify&#x60; keeps them without an issue when the time-tracking settings allow it, and &#x60;reassign&#x60; moves them to &#x60;reassign_to_id&#x60;. Redmine uses this option when the selected issues have positive total spent time.
     * @param reassignToId Destination issue ID for &#x60;todo&#x3D;reassign&#x60;. The destination must not be one of the issues being deleted and must belong to the same project as the time entries being moved.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteIssuesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull List<Integer> ids, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String todo, @jakarta.annotation.Nullable Integer reassignToId) throws RestClientResponseException {
        return deleteIssuesRequestCreation(format, ids, xRedmineSwitchUser, todo, reassignToId);
    }

    /**
     * Show issue
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;children&#x60; (child issues and their descendants, limited to issues visible to the current user), &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations), &#x60;changesets&#x60; (associated VCS changesets), &#x60;journals&#x60; (change history and notes), &#x60;watchers&#x60; (users watching the issue), &#x60;allowed_statuses&#x60; (status transitions available for the current user) The &#x60;children&#x60; field is omitted for leaf issues. For non-leaf issues, it may be empty when none of their children are visible.
     * @return GetIssue200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Showing-an-issue">Show issue Documentation</a>
     */
    private ResponseSpec getIssueRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling getIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_id", issueId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetIssue200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issues/{issue_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show issue
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;children&#x60; (child issues and their descendants, limited to issues visible to the current user), &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations), &#x60;changesets&#x60; (associated VCS changesets), &#x60;journals&#x60; (change history and notes), &#x60;watchers&#x60; (users watching the issue), &#x60;allowed_statuses&#x60; (status transitions available for the current user) The &#x60;children&#x60; field is omitted for leaf issues. For non-leaf issues, it may be empty when none of their children are visible.
     * @return GetIssue200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Showing-an-issue">Show issue Documentation</a>
     */
    public GetIssue200Response getIssue(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssue200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssueRequestCreation(format, issueId, xRedmineSwitchUser, include).body(localVarReturnType);
    }

    /**
     * Show issue
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;children&#x60; (child issues and their descendants, limited to issues visible to the current user), &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations), &#x60;changesets&#x60; (associated VCS changesets), &#x60;journals&#x60; (change history and notes), &#x60;watchers&#x60; (users watching the issue), &#x60;allowed_statuses&#x60; (status transitions available for the current user) The &#x60;children&#x60; field is omitted for leaf issues. For non-leaf issues, it may be empty when none of their children are visible.
     * @return ResponseEntity&lt;GetIssue200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Showing-an-issue">Show issue Documentation</a>
     */
    public ResponseEntity<GetIssue200Response> getIssueWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssue200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssueRequestCreation(format, issueId, xRedmineSwitchUser, include).toEntity(localVarReturnType);
    }

    /**
     * Show issue
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;children&#x60; (child issues and their descendants, limited to issues visible to the current user), &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations), &#x60;changesets&#x60; (associated VCS changesets), &#x60;journals&#x60; (change history and notes), &#x60;watchers&#x60; (users watching the issue), &#x60;allowed_statuses&#x60; (status transitions available for the current user) The &#x60;children&#x60; field is omitted for leaf issues. For non-leaf issues, it may be empty when none of their children are visible.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Showing-an-issue">Show issue Documentation</a>
     */
    public ResponseSpec getIssueWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        return getIssueRequestCreation(format, issueId, xRedmineSwitchUser, include);
    }

    /**
     * List issues
     * Lists visible issues. Without a saved query or explicit status filter, only open issues are returned. Available filters depend on the project modules, enabled core fields, custom fields and the current user&#39;s permissions.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Returned when the query_id parameter refers to a nonexistent query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60; or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;id:desc&#x60;, &#x60;status:desc,id&#x60;.
     * @param include Comma-separated list of associated data to include for each issue. Values: &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations)
     * @param issueId Filter by issue ID. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) For equality, separate multiple IDs with commas. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;&gt;&#x3D;100&#x60;, &#x60;&gt;&lt;1|100&#x60;
     * @param projectId Project ID or identifier selecting the project context. Query operators and multiple IDs are not accepted here. Subprojects are included according to the installation setting unless &#x60;subproject_id&#x60; overrides it. To select multiple projects, omit this parameter and use explicit &#x60;f[]&#x60;, &#x60;op&#x60;, and &#x60;v&#x60; filters.
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param trackerId Filter by tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param statusId Filter by issue status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;o&#x60; (open), &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;c&#x60; (closed), &#x60;*&#x60; (any), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;o&#x60;, &#x60;1|2&#x60;, &#x60;c&#x60;, &#x60;!3&#x60;
     * @param priorityId Filter by priority. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param assignedToId Filter by assignee. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;, &#x60;!me&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60;
     * @param authorGroup Filter by author&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param authorRole Filter by author&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param memberOfGroup Filter by assignee&#39;s group membership. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param assignedToRole Filter by assignee&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param fixedVersionId Filter by target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param fixedVersionDueDate Filter by target version&#39;s due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param fixedVersionStatus Filter by target version&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;open&#x60;, &#x60;locked&#x60;, &#x60;closed&#x60; Examples: &#x60;open&#x60;, &#x60;open|locked&#x60;, &#x60;!closed&#x60;
     * @param categoryId Filter by category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only in a project context.
     * @param parentId Filter by parent issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified parent, default), &#x60;~&#x60; (is a descendant of the specified issue), &#x60;*&#x60; (any parent), &#x60;!*&#x60; (no parent) For &#x60;&#x3D;&#x60; and &#x60;~&#x60;, separate multiple IDs with commas. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123,456&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param childId Filter by child issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified child, default), &#x60;~&#x60; (is an ancestor of the specified issue), &#x60;*&#x60; (any child), &#x60;!*&#x60; (no children) For &#x60;&#x3D;&#x60;, separate multiple IDs with commas. For &#x60;~&#x60;, use one ID. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param subject Filter by subject. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param notes Filter by journal notes. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last updated date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60; For this field, &#x60;*&#x60; means updated after creation and &#x60;!*&#x60; means never updated after creation.
     * @param closedOn Filter by closed date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param startDate Filter by start date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param dueDate Filter by due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param estimatedHours Filter by estimated hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60;
     * @param spentTime Filter by total spent time. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60; Requires permission to view spent time.
     * @param doneRatio Filter by done ratio (percentage). Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;50&#x60;, &#x60;&gt;&lt;0|99&#x60;, &#x60;100&#x60;
     * @param isPrivate Filter by private flag. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (private), &#x60;0&#x60; (not private) Examples: &#x60;1&#x60;, &#x60;0&#x60; Available only when the current user can view private issues.
     * @param attachment Filter by attachment filename. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~report&#x60;, &#x60;$.pdf&#x60;
     * @param attachmentDescription Filter by attachment description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~screenshot&#x60;
     * @param watcherId Filter by watcher. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60; Available only to logged-in users.
     * @param updatedBy Filter by user who updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param lastUpdatedBy Filter by user who last updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param projectStatus Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param relates Filter by the &#x60;relates&#x60; relation type.
     * @param duplicates Filter by the &#x60;duplicates&#x60; relation type.
     * @param duplicated Filter by the &#x60;duplicated&#x60; relation type.
     * @param blocks Filter by the &#x60;blocks&#x60; relation type.
     * @param blocked Filter by the &#x60;blocked&#x60; relation type.
     * @param precedes Filter by the &#x60;precedes&#x60; relation type.
     * @param follows Filter by the &#x60;follows&#x60; relation type.
     * @param copiedTo Filter by the &#x60;copied_to&#x60; relation type.
     * @param copiedFrom Filter by the &#x60;copied_from&#x60; relation type.
     * @param anySearchable Full-text search across all searchable fields. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains all words), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (not contains) Examples: &#x60;~keyword&#x60;, &#x60;*~foo bar&#x60;
     * @param queryId ID of a saved query to apply. When specified, the saved query&#39;s filter criteria are used. Other filter parameters in the same request are ignored. Use GET /queries.json to retrieve available saved queries.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @return GetIssues200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Listing-issues">List issues Documentation</a>
     */
    private ResponseSpec getIssuesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String trackerId, @jakarta.annotation.Nullable String statusId, @jakarta.annotation.Nullable String priorityId, @jakarta.annotation.Nullable String assignedToId, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String authorGroup, @jakarta.annotation.Nullable String authorRole, @jakarta.annotation.Nullable String memberOfGroup, @jakarta.annotation.Nullable String assignedToRole, @jakarta.annotation.Nullable String fixedVersionId, @jakarta.annotation.Nullable String fixedVersionDueDate, @jakarta.annotation.Nullable String fixedVersionStatus, @jakarta.annotation.Nullable String categoryId, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String childId, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String subject, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String notes, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable String closedOn, @jakarta.annotation.Nullable String startDate, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String estimatedHours, @jakarta.annotation.Nullable String spentTime, @jakarta.annotation.Nullable String doneRatio, @jakarta.annotation.Nullable String isPrivate, @jakarta.annotation.Nullable String attachment, @jakarta.annotation.Nullable String attachmentDescription, @jakarta.annotation.Nullable String watcherId, @jakarta.annotation.Nullable String updatedBy, @jakarta.annotation.Nullable String lastUpdatedBy, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String relates, @jakarta.annotation.Nullable String duplicates, @jakarta.annotation.Nullable String duplicated, @jakarta.annotation.Nullable String blocks, @jakarta.annotation.Nullable String blocked, @jakarta.annotation.Nullable String precedes, @jakarta.annotation.Nullable String follows, @jakarta.annotation.Nullable String copiedTo, @jakarta.annotation.Nullable String copiedFrom, @jakarta.annotation.Nullable String anySearchable, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "nometa", nometa));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue_id", issueId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project_id", projectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "subproject_id", subprojectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tracker_id", trackerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status_id", statusId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "priority_id", priorityId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assigned_to_id", assignedToId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author.group", authorGroup));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author.role", authorRole));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_of_group", memberOfGroup));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assigned_to_role", assignedToRole));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "fixed_version_id", fixedVersionId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "fixed_version.due_date", fixedVersionDueDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "fixed_version.status", fixedVersionStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "category_id", categoryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "parent_id", parentId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "child_id", childId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cf_x", cfX));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "subject", subject));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "description", description));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "notes", notes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_on", createdOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_on", updatedOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "closed_on", closedOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_date", startDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "due_date", dueDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "estimated_hours", estimatedHours));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "spent_time", spentTime));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "done_ratio", doneRatio));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "is_private", isPrivate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "attachment", attachment));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "attachment_description", attachmentDescription));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "watcher_id", watcherId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_by", updatedBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "last_updated_by", lastUpdatedBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project.status", projectStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "relates", relates));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "duplicates", duplicates));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "duplicated", duplicated));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "blocks", blocks));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "blocked", blocked));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "precedes", precedes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "follows", follows));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copied_to", copiedTo));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copied_from", copiedFrom));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "any_searchable", anySearchable));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query_id", queryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "f[]", f));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "op", op));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "v", v));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }
        if (xRedmineNometa != null) {
            headerParams.add("X-Redmine-Nometa", apiClient.parameterToString(xRedmineNometa));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetIssues200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issues.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List issues
     * Lists visible issues. Without a saved query or explicit status filter, only open issues are returned. Available filters depend on the project modules, enabled core fields, custom fields and the current user&#39;s permissions.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Returned when the query_id parameter refers to a nonexistent query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60; or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;id:desc&#x60;, &#x60;status:desc,id&#x60;.
     * @param include Comma-separated list of associated data to include for each issue. Values: &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations)
     * @param issueId Filter by issue ID. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) For equality, separate multiple IDs with commas. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;&gt;&#x3D;100&#x60;, &#x60;&gt;&lt;1|100&#x60;
     * @param projectId Project ID or identifier selecting the project context. Query operators and multiple IDs are not accepted here. Subprojects are included according to the installation setting unless &#x60;subproject_id&#x60; overrides it. To select multiple projects, omit this parameter and use explicit &#x60;f[]&#x60;, &#x60;op&#x60;, and &#x60;v&#x60; filters.
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param trackerId Filter by tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param statusId Filter by issue status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;o&#x60; (open), &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;c&#x60; (closed), &#x60;*&#x60; (any), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;o&#x60;, &#x60;1|2&#x60;, &#x60;c&#x60;, &#x60;!3&#x60;
     * @param priorityId Filter by priority. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param assignedToId Filter by assignee. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;, &#x60;!me&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60;
     * @param authorGroup Filter by author&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param authorRole Filter by author&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param memberOfGroup Filter by assignee&#39;s group membership. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param assignedToRole Filter by assignee&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param fixedVersionId Filter by target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param fixedVersionDueDate Filter by target version&#39;s due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param fixedVersionStatus Filter by target version&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;open&#x60;, &#x60;locked&#x60;, &#x60;closed&#x60; Examples: &#x60;open&#x60;, &#x60;open|locked&#x60;, &#x60;!closed&#x60;
     * @param categoryId Filter by category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only in a project context.
     * @param parentId Filter by parent issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified parent, default), &#x60;~&#x60; (is a descendant of the specified issue), &#x60;*&#x60; (any parent), &#x60;!*&#x60; (no parent) For &#x60;&#x3D;&#x60; and &#x60;~&#x60;, separate multiple IDs with commas. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123,456&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param childId Filter by child issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified child, default), &#x60;~&#x60; (is an ancestor of the specified issue), &#x60;*&#x60; (any child), &#x60;!*&#x60; (no children) For &#x60;&#x3D;&#x60;, separate multiple IDs with commas. For &#x60;~&#x60;, use one ID. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param subject Filter by subject. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param notes Filter by journal notes. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last updated date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60; For this field, &#x60;*&#x60; means updated after creation and &#x60;!*&#x60; means never updated after creation.
     * @param closedOn Filter by closed date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param startDate Filter by start date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param dueDate Filter by due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param estimatedHours Filter by estimated hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60;
     * @param spentTime Filter by total spent time. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60; Requires permission to view spent time.
     * @param doneRatio Filter by done ratio (percentage). Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;50&#x60;, &#x60;&gt;&lt;0|99&#x60;, &#x60;100&#x60;
     * @param isPrivate Filter by private flag. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (private), &#x60;0&#x60; (not private) Examples: &#x60;1&#x60;, &#x60;0&#x60; Available only when the current user can view private issues.
     * @param attachment Filter by attachment filename. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~report&#x60;, &#x60;$.pdf&#x60;
     * @param attachmentDescription Filter by attachment description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~screenshot&#x60;
     * @param watcherId Filter by watcher. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60; Available only to logged-in users.
     * @param updatedBy Filter by user who updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param lastUpdatedBy Filter by user who last updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param projectStatus Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param relates Filter by the &#x60;relates&#x60; relation type.
     * @param duplicates Filter by the &#x60;duplicates&#x60; relation type.
     * @param duplicated Filter by the &#x60;duplicated&#x60; relation type.
     * @param blocks Filter by the &#x60;blocks&#x60; relation type.
     * @param blocked Filter by the &#x60;blocked&#x60; relation type.
     * @param precedes Filter by the &#x60;precedes&#x60; relation type.
     * @param follows Filter by the &#x60;follows&#x60; relation type.
     * @param copiedTo Filter by the &#x60;copied_to&#x60; relation type.
     * @param copiedFrom Filter by the &#x60;copied_from&#x60; relation type.
     * @param anySearchable Full-text search across all searchable fields. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains all words), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (not contains) Examples: &#x60;~keyword&#x60;, &#x60;*~foo bar&#x60;
     * @param queryId ID of a saved query to apply. When specified, the saved query&#39;s filter criteria are used. Other filter parameters in the same request are ignored. Use GET /queries.json to retrieve available saved queries.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @return GetIssues200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Listing-issues">List issues Documentation</a>
     */
    public GetIssues200Response getIssues(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String trackerId, @jakarta.annotation.Nullable String statusId, @jakarta.annotation.Nullable String priorityId, @jakarta.annotation.Nullable String assignedToId, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String authorGroup, @jakarta.annotation.Nullable String authorRole, @jakarta.annotation.Nullable String memberOfGroup, @jakarta.annotation.Nullable String assignedToRole, @jakarta.annotation.Nullable String fixedVersionId, @jakarta.annotation.Nullable String fixedVersionDueDate, @jakarta.annotation.Nullable String fixedVersionStatus, @jakarta.annotation.Nullable String categoryId, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String childId, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String subject, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String notes, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable String closedOn, @jakarta.annotation.Nullable String startDate, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String estimatedHours, @jakarta.annotation.Nullable String spentTime, @jakarta.annotation.Nullable String doneRatio, @jakarta.annotation.Nullable String isPrivate, @jakarta.annotation.Nullable String attachment, @jakarta.annotation.Nullable String attachmentDescription, @jakarta.annotation.Nullable String watcherId, @jakarta.annotation.Nullable String updatedBy, @jakarta.annotation.Nullable String lastUpdatedBy, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String relates, @jakarta.annotation.Nullable String duplicates, @jakarta.annotation.Nullable String duplicated, @jakarta.annotation.Nullable String blocks, @jakarta.annotation.Nullable String blocked, @jakarta.annotation.Nullable String precedes, @jakarta.annotation.Nullable String follows, @jakarta.annotation.Nullable String copiedTo, @jakarta.annotation.Nullable String copiedFrom, @jakarta.annotation.Nullable String anySearchable, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssues200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssuesRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, sort, include, issueId, projectId, subprojectId, trackerId, statusId, priorityId, assignedToId, authorId, authorGroup, authorRole, memberOfGroup, assignedToRole, fixedVersionId, fixedVersionDueDate, fixedVersionStatus, categoryId, parentId, childId, cfX, subject, description, notes, createdOn, updatedOn, closedOn, startDate, dueDate, estimatedHours, spentTime, doneRatio, isPrivate, attachment, attachmentDescription, watcherId, updatedBy, lastUpdatedBy, projectStatus, relates, duplicates, duplicated, blocks, blocked, precedes, follows, copiedTo, copiedFrom, anySearchable, queryId, page, f, op, v).body(localVarReturnType);
    }

    /**
     * List issues
     * Lists visible issues. Without a saved query or explicit status filter, only open issues are returned. Available filters depend on the project modules, enabled core fields, custom fields and the current user&#39;s permissions.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Returned when the query_id parameter refers to a nonexistent query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60; or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;id:desc&#x60;, &#x60;status:desc,id&#x60;.
     * @param include Comma-separated list of associated data to include for each issue. Values: &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations)
     * @param issueId Filter by issue ID. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) For equality, separate multiple IDs with commas. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;&gt;&#x3D;100&#x60;, &#x60;&gt;&lt;1|100&#x60;
     * @param projectId Project ID or identifier selecting the project context. Query operators and multiple IDs are not accepted here. Subprojects are included according to the installation setting unless &#x60;subproject_id&#x60; overrides it. To select multiple projects, omit this parameter and use explicit &#x60;f[]&#x60;, &#x60;op&#x60;, and &#x60;v&#x60; filters.
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param trackerId Filter by tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param statusId Filter by issue status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;o&#x60; (open), &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;c&#x60; (closed), &#x60;*&#x60; (any), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;o&#x60;, &#x60;1|2&#x60;, &#x60;c&#x60;, &#x60;!3&#x60;
     * @param priorityId Filter by priority. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param assignedToId Filter by assignee. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;, &#x60;!me&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60;
     * @param authorGroup Filter by author&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param authorRole Filter by author&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param memberOfGroup Filter by assignee&#39;s group membership. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param assignedToRole Filter by assignee&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param fixedVersionId Filter by target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param fixedVersionDueDate Filter by target version&#39;s due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param fixedVersionStatus Filter by target version&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;open&#x60;, &#x60;locked&#x60;, &#x60;closed&#x60; Examples: &#x60;open&#x60;, &#x60;open|locked&#x60;, &#x60;!closed&#x60;
     * @param categoryId Filter by category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only in a project context.
     * @param parentId Filter by parent issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified parent, default), &#x60;~&#x60; (is a descendant of the specified issue), &#x60;*&#x60; (any parent), &#x60;!*&#x60; (no parent) For &#x60;&#x3D;&#x60; and &#x60;~&#x60;, separate multiple IDs with commas. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123,456&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param childId Filter by child issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified child, default), &#x60;~&#x60; (is an ancestor of the specified issue), &#x60;*&#x60; (any child), &#x60;!*&#x60; (no children) For &#x60;&#x3D;&#x60;, separate multiple IDs with commas. For &#x60;~&#x60;, use one ID. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param subject Filter by subject. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param notes Filter by journal notes. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last updated date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60; For this field, &#x60;*&#x60; means updated after creation and &#x60;!*&#x60; means never updated after creation.
     * @param closedOn Filter by closed date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param startDate Filter by start date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param dueDate Filter by due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param estimatedHours Filter by estimated hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60;
     * @param spentTime Filter by total spent time. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60; Requires permission to view spent time.
     * @param doneRatio Filter by done ratio (percentage). Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;50&#x60;, &#x60;&gt;&lt;0|99&#x60;, &#x60;100&#x60;
     * @param isPrivate Filter by private flag. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (private), &#x60;0&#x60; (not private) Examples: &#x60;1&#x60;, &#x60;0&#x60; Available only when the current user can view private issues.
     * @param attachment Filter by attachment filename. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~report&#x60;, &#x60;$.pdf&#x60;
     * @param attachmentDescription Filter by attachment description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~screenshot&#x60;
     * @param watcherId Filter by watcher. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60; Available only to logged-in users.
     * @param updatedBy Filter by user who updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param lastUpdatedBy Filter by user who last updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param projectStatus Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param relates Filter by the &#x60;relates&#x60; relation type.
     * @param duplicates Filter by the &#x60;duplicates&#x60; relation type.
     * @param duplicated Filter by the &#x60;duplicated&#x60; relation type.
     * @param blocks Filter by the &#x60;blocks&#x60; relation type.
     * @param blocked Filter by the &#x60;blocked&#x60; relation type.
     * @param precedes Filter by the &#x60;precedes&#x60; relation type.
     * @param follows Filter by the &#x60;follows&#x60; relation type.
     * @param copiedTo Filter by the &#x60;copied_to&#x60; relation type.
     * @param copiedFrom Filter by the &#x60;copied_from&#x60; relation type.
     * @param anySearchable Full-text search across all searchable fields. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains all words), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (not contains) Examples: &#x60;~keyword&#x60;, &#x60;*~foo bar&#x60;
     * @param queryId ID of a saved query to apply. When specified, the saved query&#39;s filter criteria are used. Other filter parameters in the same request are ignored. Use GET /queries.json to retrieve available saved queries.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @return ResponseEntity&lt;GetIssues200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Listing-issues">List issues Documentation</a>
     */
    public ResponseEntity<GetIssues200Response> getIssuesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String trackerId, @jakarta.annotation.Nullable String statusId, @jakarta.annotation.Nullable String priorityId, @jakarta.annotation.Nullable String assignedToId, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String authorGroup, @jakarta.annotation.Nullable String authorRole, @jakarta.annotation.Nullable String memberOfGroup, @jakarta.annotation.Nullable String assignedToRole, @jakarta.annotation.Nullable String fixedVersionId, @jakarta.annotation.Nullable String fixedVersionDueDate, @jakarta.annotation.Nullable String fixedVersionStatus, @jakarta.annotation.Nullable String categoryId, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String childId, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String subject, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String notes, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable String closedOn, @jakarta.annotation.Nullable String startDate, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String estimatedHours, @jakarta.annotation.Nullable String spentTime, @jakarta.annotation.Nullable String doneRatio, @jakarta.annotation.Nullable String isPrivate, @jakarta.annotation.Nullable String attachment, @jakarta.annotation.Nullable String attachmentDescription, @jakarta.annotation.Nullable String watcherId, @jakarta.annotation.Nullable String updatedBy, @jakarta.annotation.Nullable String lastUpdatedBy, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String relates, @jakarta.annotation.Nullable String duplicates, @jakarta.annotation.Nullable String duplicated, @jakarta.annotation.Nullable String blocks, @jakarta.annotation.Nullable String blocked, @jakarta.annotation.Nullable String precedes, @jakarta.annotation.Nullable String follows, @jakarta.annotation.Nullable String copiedTo, @jakarta.annotation.Nullable String copiedFrom, @jakarta.annotation.Nullable String anySearchable, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssues200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssuesRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, sort, include, issueId, projectId, subprojectId, trackerId, statusId, priorityId, assignedToId, authorId, authorGroup, authorRole, memberOfGroup, assignedToRole, fixedVersionId, fixedVersionDueDate, fixedVersionStatus, categoryId, parentId, childId, cfX, subject, description, notes, createdOn, updatedOn, closedOn, startDate, dueDate, estimatedHours, spentTime, doneRatio, isPrivate, attachment, attachmentDescription, watcherId, updatedBy, lastUpdatedBy, projectStatus, relates, duplicates, duplicated, blocks, blocked, precedes, follows, copiedTo, copiedFrom, anySearchable, queryId, page, f, op, v).toEntity(localVarReturnType);
    }

    /**
     * List issues
     * Lists visible issues. Without a saved query or explicit status filter, only open issues are returned. Available filters depend on the project modules, enabled core fields, custom fields and the current user&#39;s permissions.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Returned when the query_id parameter refers to a nonexistent query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60; or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;id:desc&#x60;, &#x60;status:desc,id&#x60;.
     * @param include Comma-separated list of associated data to include for each issue. Values: &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations)
     * @param issueId Filter by issue ID. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) For equality, separate multiple IDs with commas. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;&gt;&#x3D;100&#x60;, &#x60;&gt;&lt;1|100&#x60;
     * @param projectId Project ID or identifier selecting the project context. Query operators and multiple IDs are not accepted here. Subprojects are included according to the installation setting unless &#x60;subproject_id&#x60; overrides it. To select multiple projects, omit this parameter and use explicit &#x60;f[]&#x60;, &#x60;op&#x60;, and &#x60;v&#x60; filters.
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param trackerId Filter by tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param statusId Filter by issue status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;o&#x60; (open), &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;c&#x60; (closed), &#x60;*&#x60; (any), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;o&#x60;, &#x60;1|2&#x60;, &#x60;c&#x60;, &#x60;!3&#x60;
     * @param priorityId Filter by priority. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param assignedToId Filter by assignee. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;, &#x60;!me&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60;
     * @param authorGroup Filter by author&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param authorRole Filter by author&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param memberOfGroup Filter by assignee&#39;s group membership. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param assignedToRole Filter by assignee&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param fixedVersionId Filter by target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param fixedVersionDueDate Filter by target version&#39;s due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param fixedVersionStatus Filter by target version&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;open&#x60;, &#x60;locked&#x60;, &#x60;closed&#x60; Examples: &#x60;open&#x60;, &#x60;open|locked&#x60;, &#x60;!closed&#x60;
     * @param categoryId Filter by category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only in a project context.
     * @param parentId Filter by parent issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified parent, default), &#x60;~&#x60; (is a descendant of the specified issue), &#x60;*&#x60; (any parent), &#x60;!*&#x60; (no parent) For &#x60;&#x3D;&#x60; and &#x60;~&#x60;, separate multiple IDs with commas. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123,456&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param childId Filter by child issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified child, default), &#x60;~&#x60; (is an ancestor of the specified issue), &#x60;*&#x60; (any child), &#x60;!*&#x60; (no children) For &#x60;&#x3D;&#x60;, separate multiple IDs with commas. For &#x60;~&#x60;, use one ID. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param subject Filter by subject. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param notes Filter by journal notes. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last updated date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60; For this field, &#x60;*&#x60; means updated after creation and &#x60;!*&#x60; means never updated after creation.
     * @param closedOn Filter by closed date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param startDate Filter by start date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param dueDate Filter by due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param estimatedHours Filter by estimated hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60;
     * @param spentTime Filter by total spent time. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60; Requires permission to view spent time.
     * @param doneRatio Filter by done ratio (percentage). Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;50&#x60;, &#x60;&gt;&lt;0|99&#x60;, &#x60;100&#x60;
     * @param isPrivate Filter by private flag. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (private), &#x60;0&#x60; (not private) Examples: &#x60;1&#x60;, &#x60;0&#x60; Available only when the current user can view private issues.
     * @param attachment Filter by attachment filename. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~report&#x60;, &#x60;$.pdf&#x60;
     * @param attachmentDescription Filter by attachment description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~screenshot&#x60;
     * @param watcherId Filter by watcher. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60; Available only to logged-in users.
     * @param updatedBy Filter by user who updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param lastUpdatedBy Filter by user who last updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param projectStatus Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param relates Filter by the &#x60;relates&#x60; relation type.
     * @param duplicates Filter by the &#x60;duplicates&#x60; relation type.
     * @param duplicated Filter by the &#x60;duplicated&#x60; relation type.
     * @param blocks Filter by the &#x60;blocks&#x60; relation type.
     * @param blocked Filter by the &#x60;blocked&#x60; relation type.
     * @param precedes Filter by the &#x60;precedes&#x60; relation type.
     * @param follows Filter by the &#x60;follows&#x60; relation type.
     * @param copiedTo Filter by the &#x60;copied_to&#x60; relation type.
     * @param copiedFrom Filter by the &#x60;copied_from&#x60; relation type.
     * @param anySearchable Full-text search across all searchable fields. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains all words), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (not contains) Examples: &#x60;~keyword&#x60;, &#x60;*~foo bar&#x60;
     * @param queryId ID of a saved query to apply. When specified, the saved query&#39;s filter criteria are used. Other filter parameters in the same request are ignored. Use GET /queries.json to retrieve available saved queries.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Listing-issues">List issues Documentation</a>
     */
    public ResponseSpec getIssuesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String trackerId, @jakarta.annotation.Nullable String statusId, @jakarta.annotation.Nullable String priorityId, @jakarta.annotation.Nullable String assignedToId, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String authorGroup, @jakarta.annotation.Nullable String authorRole, @jakarta.annotation.Nullable String memberOfGroup, @jakarta.annotation.Nullable String assignedToRole, @jakarta.annotation.Nullable String fixedVersionId, @jakarta.annotation.Nullable String fixedVersionDueDate, @jakarta.annotation.Nullable String fixedVersionStatus, @jakarta.annotation.Nullable String categoryId, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String childId, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String subject, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String notes, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable String closedOn, @jakarta.annotation.Nullable String startDate, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String estimatedHours, @jakarta.annotation.Nullable String spentTime, @jakarta.annotation.Nullable String doneRatio, @jakarta.annotation.Nullable String isPrivate, @jakarta.annotation.Nullable String attachment, @jakarta.annotation.Nullable String attachmentDescription, @jakarta.annotation.Nullable String watcherId, @jakarta.annotation.Nullable String updatedBy, @jakarta.annotation.Nullable String lastUpdatedBy, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String relates, @jakarta.annotation.Nullable String duplicates, @jakarta.annotation.Nullable String duplicated, @jakarta.annotation.Nullable String blocks, @jakarta.annotation.Nullable String blocked, @jakarta.annotation.Nullable String precedes, @jakarta.annotation.Nullable String follows, @jakarta.annotation.Nullable String copiedTo, @jakarta.annotation.Nullable String copiedFrom, @jakarta.annotation.Nullable String anySearchable, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v) throws RestClientResponseException {
        return getIssuesRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, sort, include, issueId, projectId, subprojectId, trackerId, statusId, priorityId, assignedToId, authorId, authorGroup, authorRole, memberOfGroup, assignedToRole, fixedVersionId, fixedVersionDueDate, fixedVersionStatus, categoryId, parentId, childId, cfX, subject, description, notes, createdOn, updatedOn, closedOn, startDate, dueDate, estimatedHours, spentTime, doneRatio, isPrivate, attachment, attachmentDescription, watcherId, updatedBy, lastUpdatedBy, projectStatus, relates, duplicates, duplicated, blocks, blocked, precedes, follows, copiedTo, copiedFrom, anySearchable, queryId, page, f, op, v);
    }

    /**
     * List issues by project
     * Lists visible issues. Without a saved query or explicit status filter, only open issues are returned. Available filters depend on the project modules, enabled core fields, custom fields and the current user&#39;s permissions.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60; or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;id:desc&#x60;, &#x60;status:desc,id&#x60;.
     * @param include Comma-separated list of associated data to include for each issue. Values: &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations)
     * @param issueId Filter by issue ID. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) For equality, separate multiple IDs with commas. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;&gt;&#x3D;100&#x60;, &#x60;&gt;&lt;1|100&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param trackerId Filter by tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param statusId Filter by issue status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;o&#x60; (open), &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;c&#x60; (closed), &#x60;*&#x60; (any), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;o&#x60;, &#x60;1|2&#x60;, &#x60;c&#x60;, &#x60;!3&#x60;
     * @param priorityId Filter by priority. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param assignedToId Filter by assignee. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;, &#x60;!me&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60;
     * @param authorGroup Filter by author&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param authorRole Filter by author&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param memberOfGroup Filter by assignee&#39;s group membership. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param assignedToRole Filter by assignee&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param fixedVersionId Filter by target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param fixedVersionDueDate Filter by target version&#39;s due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param fixedVersionStatus Filter by target version&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;open&#x60;, &#x60;locked&#x60;, &#x60;closed&#x60; Examples: &#x60;open&#x60;, &#x60;open|locked&#x60;, &#x60;!closed&#x60;
     * @param categoryId Filter by category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only in a project context.
     * @param parentId Filter by parent issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified parent, default), &#x60;~&#x60; (is a descendant of the specified issue), &#x60;*&#x60; (any parent), &#x60;!*&#x60; (no parent) For &#x60;&#x3D;&#x60; and &#x60;~&#x60;, separate multiple IDs with commas. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123,456&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param childId Filter by child issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified child, default), &#x60;~&#x60; (is an ancestor of the specified issue), &#x60;*&#x60; (any child), &#x60;!*&#x60; (no children) For &#x60;&#x3D;&#x60;, separate multiple IDs with commas. For &#x60;~&#x60;, use one ID. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param subject Filter by subject. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param notes Filter by journal notes. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last updated date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60; For this field, &#x60;*&#x60; means updated after creation and &#x60;!*&#x60; means never updated after creation.
     * @param closedOn Filter by closed date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param startDate Filter by start date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param dueDate Filter by due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param estimatedHours Filter by estimated hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60;
     * @param spentTime Filter by total spent time. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60; Requires permission to view spent time.
     * @param doneRatio Filter by done ratio (percentage). Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;50&#x60;, &#x60;&gt;&lt;0|99&#x60;, &#x60;100&#x60;
     * @param isPrivate Filter by private flag. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (private), &#x60;0&#x60; (not private) Examples: &#x60;1&#x60;, &#x60;0&#x60; Available only when the current user can view private issues.
     * @param attachment Filter by attachment filename. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~report&#x60;, &#x60;$.pdf&#x60;
     * @param attachmentDescription Filter by attachment description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~screenshot&#x60;
     * @param watcherId Filter by watcher. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60; Available only to logged-in users.
     * @param updatedBy Filter by user who updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param lastUpdatedBy Filter by user who last updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param projectStatus Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param relates Filter by the &#x60;relates&#x60; relation type.
     * @param duplicates Filter by the &#x60;duplicates&#x60; relation type.
     * @param duplicated Filter by the &#x60;duplicated&#x60; relation type.
     * @param blocks Filter by the &#x60;blocks&#x60; relation type.
     * @param blocked Filter by the &#x60;blocked&#x60; relation type.
     * @param precedes Filter by the &#x60;precedes&#x60; relation type.
     * @param follows Filter by the &#x60;follows&#x60; relation type.
     * @param copiedTo Filter by the &#x60;copied_to&#x60; relation type.
     * @param copiedFrom Filter by the &#x60;copied_from&#x60; relation type.
     * @param anySearchable Full-text search across all searchable fields. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains all words), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (not contains) Examples: &#x60;~keyword&#x60;, &#x60;*~foo bar&#x60;
     * @param queryId ID of a saved query to apply. When specified, the saved query&#39;s filter criteria are used. Other filter parameters in the same request are ignored. Use GET /queries.json to retrieve available saved queries.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @return GetIssues200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Listing-issues">List issues by project Documentation</a>
     */
    private ResponseSpec getIssuesByProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String trackerId, @jakarta.annotation.Nullable String statusId, @jakarta.annotation.Nullable String priorityId, @jakarta.annotation.Nullable String assignedToId, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String authorGroup, @jakarta.annotation.Nullable String authorRole, @jakarta.annotation.Nullable String memberOfGroup, @jakarta.annotation.Nullable String assignedToRole, @jakarta.annotation.Nullable String fixedVersionId, @jakarta.annotation.Nullable String fixedVersionDueDate, @jakarta.annotation.Nullable String fixedVersionStatus, @jakarta.annotation.Nullable String categoryId, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String childId, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String subject, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String notes, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable String closedOn, @jakarta.annotation.Nullable String startDate, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String estimatedHours, @jakarta.annotation.Nullable String spentTime, @jakarta.annotation.Nullable String doneRatio, @jakarta.annotation.Nullable String isPrivate, @jakarta.annotation.Nullable String attachment, @jakarta.annotation.Nullable String attachmentDescription, @jakarta.annotation.Nullable String watcherId, @jakarta.annotation.Nullable String updatedBy, @jakarta.annotation.Nullable String lastUpdatedBy, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String relates, @jakarta.annotation.Nullable String duplicates, @jakarta.annotation.Nullable String duplicated, @jakarta.annotation.Nullable String blocks, @jakarta.annotation.Nullable String blocked, @jakarta.annotation.Nullable String precedes, @jakarta.annotation.Nullable String follows, @jakarta.annotation.Nullable String copiedTo, @jakarta.annotation.Nullable String copiedFrom, @jakarta.annotation.Nullable String anySearchable, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getIssuesByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getIssuesByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "nometa", nometa));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue_id", issueId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "subproject_id", subprojectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tracker_id", trackerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status_id", statusId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "priority_id", priorityId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assigned_to_id", assignedToId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author.group", authorGroup));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author.role", authorRole));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_of_group", memberOfGroup));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assigned_to_role", assignedToRole));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "fixed_version_id", fixedVersionId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "fixed_version.due_date", fixedVersionDueDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "fixed_version.status", fixedVersionStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "category_id", categoryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "parent_id", parentId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "child_id", childId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cf_x", cfX));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "subject", subject));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "description", description));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "notes", notes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_on", createdOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_on", updatedOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "closed_on", closedOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_date", startDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "due_date", dueDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "estimated_hours", estimatedHours));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "spent_time", spentTime));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "done_ratio", doneRatio));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "is_private", isPrivate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "attachment", attachment));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "attachment_description", attachmentDescription));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "watcher_id", watcherId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_by", updatedBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "last_updated_by", lastUpdatedBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project.status", projectStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "relates", relates));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "duplicates", duplicates));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "duplicated", duplicated));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "blocks", blocks));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "blocked", blocked));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "precedes", precedes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "follows", follows));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copied_to", copiedTo));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "copied_from", copiedFrom));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "any_searchable", anySearchable));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query_id", queryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "f[]", f));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "op", op));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "v", v));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }
        if (xRedmineNometa != null) {
            headerParams.add("X-Redmine-Nometa", apiClient.parameterToString(xRedmineNometa));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetIssues200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/issues.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List issues by project
     * Lists visible issues. Without a saved query or explicit status filter, only open issues are returned. Available filters depend on the project modules, enabled core fields, custom fields and the current user&#39;s permissions.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60; or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;id:desc&#x60;, &#x60;status:desc,id&#x60;.
     * @param include Comma-separated list of associated data to include for each issue. Values: &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations)
     * @param issueId Filter by issue ID. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) For equality, separate multiple IDs with commas. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;&gt;&#x3D;100&#x60;, &#x60;&gt;&lt;1|100&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param trackerId Filter by tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param statusId Filter by issue status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;o&#x60; (open), &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;c&#x60; (closed), &#x60;*&#x60; (any), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;o&#x60;, &#x60;1|2&#x60;, &#x60;c&#x60;, &#x60;!3&#x60;
     * @param priorityId Filter by priority. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param assignedToId Filter by assignee. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;, &#x60;!me&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60;
     * @param authorGroup Filter by author&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param authorRole Filter by author&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param memberOfGroup Filter by assignee&#39;s group membership. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param assignedToRole Filter by assignee&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param fixedVersionId Filter by target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param fixedVersionDueDate Filter by target version&#39;s due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param fixedVersionStatus Filter by target version&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;open&#x60;, &#x60;locked&#x60;, &#x60;closed&#x60; Examples: &#x60;open&#x60;, &#x60;open|locked&#x60;, &#x60;!closed&#x60;
     * @param categoryId Filter by category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only in a project context.
     * @param parentId Filter by parent issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified parent, default), &#x60;~&#x60; (is a descendant of the specified issue), &#x60;*&#x60; (any parent), &#x60;!*&#x60; (no parent) For &#x60;&#x3D;&#x60; and &#x60;~&#x60;, separate multiple IDs with commas. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123,456&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param childId Filter by child issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified child, default), &#x60;~&#x60; (is an ancestor of the specified issue), &#x60;*&#x60; (any child), &#x60;!*&#x60; (no children) For &#x60;&#x3D;&#x60;, separate multiple IDs with commas. For &#x60;~&#x60;, use one ID. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param subject Filter by subject. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param notes Filter by journal notes. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last updated date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60; For this field, &#x60;*&#x60; means updated after creation and &#x60;!*&#x60; means never updated after creation.
     * @param closedOn Filter by closed date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param startDate Filter by start date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param dueDate Filter by due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param estimatedHours Filter by estimated hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60;
     * @param spentTime Filter by total spent time. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60; Requires permission to view spent time.
     * @param doneRatio Filter by done ratio (percentage). Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;50&#x60;, &#x60;&gt;&lt;0|99&#x60;, &#x60;100&#x60;
     * @param isPrivate Filter by private flag. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (private), &#x60;0&#x60; (not private) Examples: &#x60;1&#x60;, &#x60;0&#x60; Available only when the current user can view private issues.
     * @param attachment Filter by attachment filename. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~report&#x60;, &#x60;$.pdf&#x60;
     * @param attachmentDescription Filter by attachment description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~screenshot&#x60;
     * @param watcherId Filter by watcher. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60; Available only to logged-in users.
     * @param updatedBy Filter by user who updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param lastUpdatedBy Filter by user who last updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param projectStatus Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param relates Filter by the &#x60;relates&#x60; relation type.
     * @param duplicates Filter by the &#x60;duplicates&#x60; relation type.
     * @param duplicated Filter by the &#x60;duplicated&#x60; relation type.
     * @param blocks Filter by the &#x60;blocks&#x60; relation type.
     * @param blocked Filter by the &#x60;blocked&#x60; relation type.
     * @param precedes Filter by the &#x60;precedes&#x60; relation type.
     * @param follows Filter by the &#x60;follows&#x60; relation type.
     * @param copiedTo Filter by the &#x60;copied_to&#x60; relation type.
     * @param copiedFrom Filter by the &#x60;copied_from&#x60; relation type.
     * @param anySearchable Full-text search across all searchable fields. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains all words), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (not contains) Examples: &#x60;~keyword&#x60;, &#x60;*~foo bar&#x60;
     * @param queryId ID of a saved query to apply. When specified, the saved query&#39;s filter criteria are used. Other filter parameters in the same request are ignored. Use GET /queries.json to retrieve available saved queries.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @return GetIssues200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Listing-issues">List issues by project Documentation</a>
     */
    public GetIssues200Response getIssuesByProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String trackerId, @jakarta.annotation.Nullable String statusId, @jakarta.annotation.Nullable String priorityId, @jakarta.annotation.Nullable String assignedToId, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String authorGroup, @jakarta.annotation.Nullable String authorRole, @jakarta.annotation.Nullable String memberOfGroup, @jakarta.annotation.Nullable String assignedToRole, @jakarta.annotation.Nullable String fixedVersionId, @jakarta.annotation.Nullable String fixedVersionDueDate, @jakarta.annotation.Nullable String fixedVersionStatus, @jakarta.annotation.Nullable String categoryId, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String childId, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String subject, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String notes, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable String closedOn, @jakarta.annotation.Nullable String startDate, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String estimatedHours, @jakarta.annotation.Nullable String spentTime, @jakarta.annotation.Nullable String doneRatio, @jakarta.annotation.Nullable String isPrivate, @jakarta.annotation.Nullable String attachment, @jakarta.annotation.Nullable String attachmentDescription, @jakarta.annotation.Nullable String watcherId, @jakarta.annotation.Nullable String updatedBy, @jakarta.annotation.Nullable String lastUpdatedBy, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String relates, @jakarta.annotation.Nullable String duplicates, @jakarta.annotation.Nullable String duplicated, @jakarta.annotation.Nullable String blocks, @jakarta.annotation.Nullable String blocked, @jakarta.annotation.Nullable String precedes, @jakarta.annotation.Nullable String follows, @jakarta.annotation.Nullable String copiedTo, @jakarta.annotation.Nullable String copiedFrom, @jakarta.annotation.Nullable String anySearchable, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssues200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssuesByProjectRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, sort, include, issueId, subprojectId, trackerId, statusId, priorityId, assignedToId, authorId, authorGroup, authorRole, memberOfGroup, assignedToRole, fixedVersionId, fixedVersionDueDate, fixedVersionStatus, categoryId, parentId, childId, cfX, subject, description, notes, createdOn, updatedOn, closedOn, startDate, dueDate, estimatedHours, spentTime, doneRatio, isPrivate, attachment, attachmentDescription, watcherId, updatedBy, lastUpdatedBy, projectStatus, relates, duplicates, duplicated, blocks, blocked, precedes, follows, copiedTo, copiedFrom, anySearchable, queryId, page, f, op, v).body(localVarReturnType);
    }

    /**
     * List issues by project
     * Lists visible issues. Without a saved query or explicit status filter, only open issues are returned. Available filters depend on the project modules, enabled core fields, custom fields and the current user&#39;s permissions.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60; or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;id:desc&#x60;, &#x60;status:desc,id&#x60;.
     * @param include Comma-separated list of associated data to include for each issue. Values: &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations)
     * @param issueId Filter by issue ID. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) For equality, separate multiple IDs with commas. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;&gt;&#x3D;100&#x60;, &#x60;&gt;&lt;1|100&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param trackerId Filter by tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param statusId Filter by issue status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;o&#x60; (open), &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;c&#x60; (closed), &#x60;*&#x60; (any), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;o&#x60;, &#x60;1|2&#x60;, &#x60;c&#x60;, &#x60;!3&#x60;
     * @param priorityId Filter by priority. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param assignedToId Filter by assignee. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;, &#x60;!me&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60;
     * @param authorGroup Filter by author&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param authorRole Filter by author&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param memberOfGroup Filter by assignee&#39;s group membership. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param assignedToRole Filter by assignee&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param fixedVersionId Filter by target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param fixedVersionDueDate Filter by target version&#39;s due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param fixedVersionStatus Filter by target version&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;open&#x60;, &#x60;locked&#x60;, &#x60;closed&#x60; Examples: &#x60;open&#x60;, &#x60;open|locked&#x60;, &#x60;!closed&#x60;
     * @param categoryId Filter by category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only in a project context.
     * @param parentId Filter by parent issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified parent, default), &#x60;~&#x60; (is a descendant of the specified issue), &#x60;*&#x60; (any parent), &#x60;!*&#x60; (no parent) For &#x60;&#x3D;&#x60; and &#x60;~&#x60;, separate multiple IDs with commas. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123,456&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param childId Filter by child issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified child, default), &#x60;~&#x60; (is an ancestor of the specified issue), &#x60;*&#x60; (any child), &#x60;!*&#x60; (no children) For &#x60;&#x3D;&#x60;, separate multiple IDs with commas. For &#x60;~&#x60;, use one ID. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param subject Filter by subject. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param notes Filter by journal notes. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last updated date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60; For this field, &#x60;*&#x60; means updated after creation and &#x60;!*&#x60; means never updated after creation.
     * @param closedOn Filter by closed date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param startDate Filter by start date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param dueDate Filter by due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param estimatedHours Filter by estimated hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60;
     * @param spentTime Filter by total spent time. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60; Requires permission to view spent time.
     * @param doneRatio Filter by done ratio (percentage). Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;50&#x60;, &#x60;&gt;&lt;0|99&#x60;, &#x60;100&#x60;
     * @param isPrivate Filter by private flag. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (private), &#x60;0&#x60; (not private) Examples: &#x60;1&#x60;, &#x60;0&#x60; Available only when the current user can view private issues.
     * @param attachment Filter by attachment filename. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~report&#x60;, &#x60;$.pdf&#x60;
     * @param attachmentDescription Filter by attachment description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~screenshot&#x60;
     * @param watcherId Filter by watcher. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60; Available only to logged-in users.
     * @param updatedBy Filter by user who updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param lastUpdatedBy Filter by user who last updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param projectStatus Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param relates Filter by the &#x60;relates&#x60; relation type.
     * @param duplicates Filter by the &#x60;duplicates&#x60; relation type.
     * @param duplicated Filter by the &#x60;duplicated&#x60; relation type.
     * @param blocks Filter by the &#x60;blocks&#x60; relation type.
     * @param blocked Filter by the &#x60;blocked&#x60; relation type.
     * @param precedes Filter by the &#x60;precedes&#x60; relation type.
     * @param follows Filter by the &#x60;follows&#x60; relation type.
     * @param copiedTo Filter by the &#x60;copied_to&#x60; relation type.
     * @param copiedFrom Filter by the &#x60;copied_from&#x60; relation type.
     * @param anySearchable Full-text search across all searchable fields. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains all words), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (not contains) Examples: &#x60;~keyword&#x60;, &#x60;*~foo bar&#x60;
     * @param queryId ID of a saved query to apply. When specified, the saved query&#39;s filter criteria are used. Other filter parameters in the same request are ignored. Use GET /queries.json to retrieve available saved queries.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @return ResponseEntity&lt;GetIssues200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Listing-issues">List issues by project Documentation</a>
     */
    public ResponseEntity<GetIssues200Response> getIssuesByProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String trackerId, @jakarta.annotation.Nullable String statusId, @jakarta.annotation.Nullable String priorityId, @jakarta.annotation.Nullable String assignedToId, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String authorGroup, @jakarta.annotation.Nullable String authorRole, @jakarta.annotation.Nullable String memberOfGroup, @jakarta.annotation.Nullable String assignedToRole, @jakarta.annotation.Nullable String fixedVersionId, @jakarta.annotation.Nullable String fixedVersionDueDate, @jakarta.annotation.Nullable String fixedVersionStatus, @jakarta.annotation.Nullable String categoryId, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String childId, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String subject, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String notes, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable String closedOn, @jakarta.annotation.Nullable String startDate, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String estimatedHours, @jakarta.annotation.Nullable String spentTime, @jakarta.annotation.Nullable String doneRatio, @jakarta.annotation.Nullable String isPrivate, @jakarta.annotation.Nullable String attachment, @jakarta.annotation.Nullable String attachmentDescription, @jakarta.annotation.Nullable String watcherId, @jakarta.annotation.Nullable String updatedBy, @jakarta.annotation.Nullable String lastUpdatedBy, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String relates, @jakarta.annotation.Nullable String duplicates, @jakarta.annotation.Nullable String duplicated, @jakarta.annotation.Nullable String blocks, @jakarta.annotation.Nullable String blocked, @jakarta.annotation.Nullable String precedes, @jakarta.annotation.Nullable String follows, @jakarta.annotation.Nullable String copiedTo, @jakarta.annotation.Nullable String copiedFrom, @jakarta.annotation.Nullable String anySearchable, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v) throws RestClientResponseException {
        ParameterizedTypeReference<GetIssues200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getIssuesByProjectRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, sort, include, issueId, subprojectId, trackerId, statusId, priorityId, assignedToId, authorId, authorGroup, authorRole, memberOfGroup, assignedToRole, fixedVersionId, fixedVersionDueDate, fixedVersionStatus, categoryId, parentId, childId, cfX, subject, description, notes, createdOn, updatedOn, closedOn, startDate, dueDate, estimatedHours, spentTime, doneRatio, isPrivate, attachment, attachmentDescription, watcherId, updatedBy, lastUpdatedBy, projectStatus, relates, duplicates, duplicated, blocks, blocked, precedes, follows, copiedTo, copiedFrom, anySearchable, queryId, page, f, op, v).toEntity(localVarReturnType);
    }

    /**
     * List issues by project
     * Lists visible issues. Without a saved query or explicit status filter, only open issues are returned. Available filters depend on the project modules, enabled core fields, custom fields and the current user&#39;s permissions.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60; or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;id:desc&#x60;, &#x60;status:desc,id&#x60;.
     * @param include Comma-separated list of associated data to include for each issue. Values: &#x60;attachments&#x60; (file attachments), &#x60;relations&#x60; (issue relations)
     * @param issueId Filter by issue ID. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) For equality, separate multiple IDs with commas. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;&gt;&#x3D;100&#x60;, &#x60;&gt;&lt;1|100&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param trackerId Filter by tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param statusId Filter by issue status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;o&#x60; (open), &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;c&#x60; (closed), &#x60;*&#x60; (any), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;o&#x60;, &#x60;1|2&#x60;, &#x60;c&#x60;, &#x60;!3&#x60;
     * @param priorityId Filter by priority. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param assignedToId Filter by assignee. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;, &#x60;!me&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60;
     * @param authorGroup Filter by author&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param authorRole Filter by author&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;
     * @param memberOfGroup Filter by assignee&#39;s group membership. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param assignedToRole Filter by assignee&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;*&#x60;
     * @param fixedVersionId Filter by target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param fixedVersionDueDate Filter by target version&#39;s due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param fixedVersionStatus Filter by target version&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;open&#x60;, &#x60;locked&#x60;, &#x60;closed&#x60; Examples: &#x60;open&#x60;, &#x60;open|locked&#x60;, &#x60;!closed&#x60;
     * @param categoryId Filter by category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none), &#x60;ev&#x60; (has ever been), &#x60;!ev&#x60; (has never been), &#x60;cf&#x60; (changed from) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only in a project context.
     * @param parentId Filter by parent issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified parent, default), &#x60;~&#x60; (is a descendant of the specified issue), &#x60;*&#x60; (any parent), &#x60;!*&#x60; (no parent) For &#x60;&#x3D;&#x60; and &#x60;~&#x60;, separate multiple IDs with commas. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123,456&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param childId Filter by child issue. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (has the specified child, default), &#x60;~&#x60; (is an ancestor of the specified issue), &#x60;*&#x60; (any child), &#x60;!*&#x60; (no children) For &#x60;&#x3D;&#x60;, separate multiple IDs with commas. For &#x60;~&#x60;, use one ID. Examples: &#x60;123&#x60;, &#x60;123,456&#x60;, &#x60;~123&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param subject Filter by subject. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param description Filter by description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;!~excluded&#x60;
     * @param notes Filter by journal notes. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param updatedOn Filter by last updated date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60; For this field, &#x60;*&#x60; means updated after creation and &#x60;!*&#x60; means never updated after creation.
     * @param closedOn Filter by closed date or timestamp. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Timestamp filters with a lower bound may also return issues from the preceding second. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;, &#x60;lm&#x60;
     * @param startDate Filter by start date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param dueDate Filter by due date. Format: &#x60;&lt;operator&gt;[value]&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;nd&#x60; (tomorrow), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;nw&#x60; (next week), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;nm&#x60; (next month), &#x60;y&#x60; (this year), &#x60;t-&#x60; (n days ago), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t+&#x60; (in n days), &#x60;&gt;t+&#x60; (on or after n days from now), &#x60;&lt;t+&#x60; (on or before n days from now), &#x60;&gt;&lt;t+&#x60; (from today through n days from now), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;t&#x60;, &#x60;&gt;t-7&#x60;
     * @param estimatedHours Filter by estimated hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60;
     * @param spentTime Filter by total spent time. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&gt;&#x3D;5&#x60;, &#x60;&gt;&lt;1:30|2:30&#x60;, &#x60;!*&#x60; Requires permission to view spent time.
     * @param doneRatio Filter by done ratio (percentage). Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&gt;&#x3D;50&#x60;, &#x60;&gt;&lt;0|99&#x60;, &#x60;100&#x60;
     * @param isPrivate Filter by private flag. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (private), &#x60;0&#x60; (not private) Examples: &#x60;1&#x60;, &#x60;0&#x60; Available only when the current user can view private issues.
     * @param attachment Filter by attachment filename. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~report&#x60;, &#x60;$.pdf&#x60;
     * @param attachmentDescription Filter by attachment description. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains), &#x60;!~&#x60; (not contains), &#x60;*~&#x60; (contains any word), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~screenshot&#x60;
     * @param watcherId Filter by watcher. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user and their groups) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!me&#x60; Available only to logged-in users.
     * @param updatedBy Filter by user who updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param lastUpdatedBy Filter by user who last updated the issue. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;
     * @param projectStatus Filter by project status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param relates Filter by the &#x60;relates&#x60; relation type.
     * @param duplicates Filter by the &#x60;duplicates&#x60; relation type.
     * @param duplicated Filter by the &#x60;duplicated&#x60; relation type.
     * @param blocks Filter by the &#x60;blocks&#x60; relation type.
     * @param blocked Filter by the &#x60;blocked&#x60; relation type.
     * @param precedes Filter by the &#x60;precedes&#x60; relation type.
     * @param follows Filter by the &#x60;follows&#x60; relation type.
     * @param copiedTo Filter by the &#x60;copied_to&#x60; relation type.
     * @param copiedFrom Filter by the &#x60;copied_from&#x60; relation type.
     * @param anySearchable Full-text search across all searchable fields. Format: &#x60;&lt;operator&gt;value&#x60; Operators: &#x60;~&#x60; (contains all words), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (not contains) Examples: &#x60;~keyword&#x60;, &#x60;*~foo bar&#x60;
     * @param queryId ID of a saved query to apply. When specified, the saved query&#39;s filter criteria are used. Other filter parameters in the same request are ignored. Use GET /queries.json to retrieve available saved queries.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Listing-issues">List issues by project Documentation</a>
     */
    public ResponseSpec getIssuesByProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String trackerId, @jakarta.annotation.Nullable String statusId, @jakarta.annotation.Nullable String priorityId, @jakarta.annotation.Nullable String assignedToId, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String authorGroup, @jakarta.annotation.Nullable String authorRole, @jakarta.annotation.Nullable String memberOfGroup, @jakarta.annotation.Nullable String assignedToRole, @jakarta.annotation.Nullable String fixedVersionId, @jakarta.annotation.Nullable String fixedVersionDueDate, @jakarta.annotation.Nullable String fixedVersionStatus, @jakarta.annotation.Nullable String categoryId, @jakarta.annotation.Nullable String parentId, @jakarta.annotation.Nullable String childId, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String subject, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String notes, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String updatedOn, @jakarta.annotation.Nullable String closedOn, @jakarta.annotation.Nullable String startDate, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String estimatedHours, @jakarta.annotation.Nullable String spentTime, @jakarta.annotation.Nullable String doneRatio, @jakarta.annotation.Nullable String isPrivate, @jakarta.annotation.Nullable String attachment, @jakarta.annotation.Nullable String attachmentDescription, @jakarta.annotation.Nullable String watcherId, @jakarta.annotation.Nullable String updatedBy, @jakarta.annotation.Nullable String lastUpdatedBy, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String relates, @jakarta.annotation.Nullable String duplicates, @jakarta.annotation.Nullable String duplicated, @jakarta.annotation.Nullable String blocks, @jakarta.annotation.Nullable String blocked, @jakarta.annotation.Nullable String precedes, @jakarta.annotation.Nullable String follows, @jakarta.annotation.Nullable String copiedTo, @jakarta.annotation.Nullable String copiedFrom, @jakarta.annotation.Nullable String anySearchable, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v) throws RestClientResponseException {
        return getIssuesByProjectRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, sort, include, issueId, subprojectId, trackerId, statusId, priorityId, assignedToId, authorId, authorGroup, authorRole, memberOfGroup, assignedToRole, fixedVersionId, fixedVersionDueDate, fixedVersionStatus, categoryId, parentId, childId, cfX, subject, description, notes, createdOn, updatedOn, closedOn, startDate, dueDate, estimatedHours, spentTime, doneRatio, isPrivate, attachment, attachmentDescription, watcherId, updatedBy, lastUpdatedBy, projectStatus, relates, duplicates, duplicated, blocks, blocked, precedes, follows, copiedTo, copiedFrom, anySearchable, queryId, page, f, op, v);
    }

    /**
     * Remove watcher
     * Removes the user or regular group identified by &#x60;user_id&#x60; as a watcher of the issue.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Removing-a-watcher">Remove watcher Documentation</a>
     */
    private ResponseSpec removeWatcherRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling removeWatcher", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling removeWatcher", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling removeWatcher", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_id", issueId);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/issues/{issue_id}/watchers/{user_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove watcher
     * Removes the user or regular group identified by &#x60;user_id&#x60; as a watcher of the issue.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Removing-a-watcher">Remove watcher Documentation</a>
     */
    public void removeWatcher(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        removeWatcherRequestCreation(format, issueId, userId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Remove watcher
     * Removes the user or regular group identified by &#x60;user_id&#x60; as a watcher of the issue.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Removing-a-watcher">Remove watcher Documentation</a>
     */
    public ResponseEntity<Void> removeWatcherWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return removeWatcherRequestCreation(format, issueId, userId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Remove watcher
     * Removes the user or regular group identified by &#x60;user_id&#x60; as a watcher of the issue.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Removing-a-watcher">Remove watcher Documentation</a>
     */
    public ResponseSpec removeWatcherWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return removeWatcherRequestCreation(format, issueId, userId, xRedmineSwitchUser);
    }

    /**
     * Update issue
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueRequest The updateIssueRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Updating-an-issue">Update issue Documentation</a>
     */
    private ResponseSpec updateIssueRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueRequest updateIssueRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling updateIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_id", issueId);

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
        return apiClient.invokeAPI("/issues/{issue_id}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateIssueRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update issue
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueRequest The updateIssueRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Updating-an-issue">Update issue Documentation</a>
     */
    public void updateIssue(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueRequest updateIssueRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateIssueRequestCreation(format, issueId, xRedmineSwitchUser, updateIssueRequest).body(localVarReturnType);
    }

    /**
     * Update issue
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueRequest The updateIssueRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Updating-an-issue">Update issue Documentation</a>
     */
    public ResponseEntity<Void> updateIssueWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueRequest updateIssueRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateIssueRequestCreation(format, issueId, xRedmineSwitchUser, updateIssueRequest).toEntity(localVarReturnType);
    }

    /**
     * Update issue
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueRequest The updateIssueRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Updating-an-issue">Update issue Documentation</a>
     */
    public ResponseSpec updateIssueWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueRequest updateIssueRequest) throws RestClientResponseException {
        return updateIssueRequestCreation(format, issueId, xRedmineSwitchUser, updateIssueRequest);
    }

    /**
     * Update issue (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueRequest The updateIssueRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Updating-an-issue">Update issue (PATCH) Documentation</a>
     */
    private ResponseSpec updateIssueViaPatchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueRequest updateIssueRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateIssueViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling updateIssueViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("issue_id", issueId);

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
        return apiClient.invokeAPI("/issues/{issue_id}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateIssueRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update issue (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueRequest The updateIssueRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Updating-an-issue">Update issue (PATCH) Documentation</a>
     */
    public void updateIssueViaPatch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueRequest updateIssueRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateIssueViaPatchRequestCreation(format, issueId, xRedmineSwitchUser, updateIssueRequest).body(localVarReturnType);
    }

    /**
     * Update issue (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueRequest The updateIssueRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Updating-an-issue">Update issue (PATCH) Documentation</a>
     */
    public ResponseEntity<Void> updateIssueViaPatchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueRequest updateIssueRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateIssueViaPatchRequestCreation(format, issueId, xRedmineSwitchUser, updateIssueRequest).toEntity(localVarReturnType);
    }

    /**
     * Update issue (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateIssueRequest The updateIssueRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Issues#Updating-an-issue">Update issue (PATCH) Documentation</a>
     */
    public ResponseSpec updateIssueViaPatchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateIssueRequest updateIssueRequest) throws RestClientResponseException {
        return updateIssueViaPatchRequestCreation(format, issueId, xRedmineSwitchUser, updateIssueRequest);
    }
}
