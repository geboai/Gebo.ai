package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.CreateTimeEntry201Response;
import ai.gebo.redmine.client.model.CreateTimeEntryRequest;
import ai.gebo.redmine.client.model.CreateTimeEntryUnderIssue404Response;
import ai.gebo.redmine.client.model.CreateTimeEntryUnderIssueRequest;
import ai.gebo.redmine.client.model.CreateTimeEntryUnderProjectRequest;
import ai.gebo.redmine.client.model.Errors;
import ai.gebo.redmine.client.model.GetTimeEntries200Response;
import ai.gebo.redmine.client.model.GetTimeEntries404Response;
import java.time.LocalDate;
import ai.gebo.redmine.client.model.UpdateTimeEntryRequest;

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
public class TimeEntriesApi {
    private ApiClient apiClient;

    public TimeEntriesApi() {
        this(new ApiClient());
    }

    public TimeEntriesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create time entry
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createTimeEntryRequest The createTimeEntryRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateTimeEntry201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry Documentation</a>
     */
    private ResponseSpec createTimeEntryRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateTimeEntryRequest createTimeEntryRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createTimeEntryRequest' is set
        if (createTimeEntryRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createTimeEntryRequest' when calling createTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

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

        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/time_entries.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createTimeEntryRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create time entry
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createTimeEntryRequest The createTimeEntryRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateTimeEntry201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry Documentation</a>
     */
    public CreateTimeEntry201Response createTimeEntry(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateTimeEntryRequest createTimeEntryRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTimeEntryRequestCreation(format, createTimeEntryRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create time entry
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createTimeEntryRequest The createTimeEntryRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateTimeEntry201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry Documentation</a>
     */
    public ResponseEntity<CreateTimeEntry201Response> createTimeEntryWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateTimeEntryRequest createTimeEntryRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTimeEntryRequestCreation(format, createTimeEntryRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create time entry
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createTimeEntryRequest The createTimeEntryRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry Documentation</a>
     */
    public ResponseSpec createTimeEntryWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateTimeEntryRequest createTimeEntryRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createTimeEntryRequestCreation(format, createTimeEntryRequest, xRedmineSwitchUser);
    }

    /**
     * Create time entry under issue
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Returned with a framework-generated body when the issue does not exist (the issue lookup is not rescued by the controller).
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param createTimeEntryUnderIssueRequest The createTimeEntryUnderIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateTimeEntry201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry under issue Documentation</a>
     */
    private ResponseSpec createTimeEntryUnderIssueRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nonnull CreateTimeEntryUnderIssueRequest createTimeEntryUnderIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createTimeEntryUnderIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueId' is set
        if (issueId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueId' when calling createTimeEntryUnderIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createTimeEntryUnderIssueRequest' is set
        if (createTimeEntryUnderIssueRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createTimeEntryUnderIssueRequest' when calling createTimeEntryUnderIssue", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/issues/{issue_id}/time_entries.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createTimeEntryUnderIssueRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create time entry under issue
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Returned with a framework-generated body when the issue does not exist (the issue lookup is not rescued by the controller).
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param createTimeEntryUnderIssueRequest The createTimeEntryUnderIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateTimeEntry201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry under issue Documentation</a>
     */
    public CreateTimeEntry201Response createTimeEntryUnderIssue(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nonnull CreateTimeEntryUnderIssueRequest createTimeEntryUnderIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTimeEntryUnderIssueRequestCreation(format, issueId, createTimeEntryUnderIssueRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create time entry under issue
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Returned with a framework-generated body when the issue does not exist (the issue lookup is not rescued by the controller).
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param createTimeEntryUnderIssueRequest The createTimeEntryUnderIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateTimeEntry201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry under issue Documentation</a>
     */
    public ResponseEntity<CreateTimeEntry201Response> createTimeEntryUnderIssueWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nonnull CreateTimeEntryUnderIssueRequest createTimeEntryUnderIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTimeEntryUnderIssueRequestCreation(format, issueId, createTimeEntryUnderIssueRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create time entry under issue
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Returned with a framework-generated body when the issue does not exist (the issue lookup is not rescued by the controller).
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param issueId The issueId parameter
     * @param createTimeEntryUnderIssueRequest The createTimeEntryUnderIssueRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry under issue Documentation</a>
     */
    public ResponseSpec createTimeEntryUnderIssueWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer issueId, @jakarta.annotation.Nonnull CreateTimeEntryUnderIssueRequest createTimeEntryUnderIssueRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createTimeEntryUnderIssueRequestCreation(format, issueId, createTimeEntryUnderIssueRequest, xRedmineSwitchUser);
    }

    /**
     * Create time entry under project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createTimeEntryUnderProjectRequest The createTimeEntryUnderProjectRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateTimeEntry201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry under project Documentation</a>
     */
    private ResponseSpec createTimeEntryUnderProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateTimeEntryUnderProjectRequest createTimeEntryUnderProjectRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createTimeEntryUnderProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling createTimeEntryUnderProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createTimeEntryUnderProjectRequest' is set
        if (createTimeEntryUnderProjectRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createTimeEntryUnderProjectRequest' when calling createTimeEntryUnderProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

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

        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/time_entries.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createTimeEntryUnderProjectRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create time entry under project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createTimeEntryUnderProjectRequest The createTimeEntryUnderProjectRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateTimeEntry201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry under project Documentation</a>
     */
    public CreateTimeEntry201Response createTimeEntryUnderProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateTimeEntryUnderProjectRequest createTimeEntryUnderProjectRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTimeEntryUnderProjectRequestCreation(format, projectId, createTimeEntryUnderProjectRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create time entry under project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createTimeEntryUnderProjectRequest The createTimeEntryUnderProjectRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateTimeEntry201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry under project Documentation</a>
     */
    public ResponseEntity<CreateTimeEntry201Response> createTimeEntryUnderProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateTimeEntryUnderProjectRequest createTimeEntryUnderProjectRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTimeEntryUnderProjectRequestCreation(format, projectId, createTimeEntryUnderProjectRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create time entry under project
     * 
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param createTimeEntryUnderProjectRequest The createTimeEntryUnderProjectRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Creating-a-time-entry">Create time entry under project Documentation</a>
     */
    public ResponseSpec createTimeEntryUnderProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nonnull CreateTimeEntryUnderProjectRequest createTimeEntryUnderProjectRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createTimeEntryUnderProjectRequestCreation(format, projectId, createTimeEntryUnderProjectRequest, xRedmineSwitchUser);
    }

    /**
     * Delete time entries
     * Deletes the selected time entries. Unknown IDs are ignored if at least one entry is found. The current user must be allowed to delete every found entry.
     * <p><b>204</b> - The entries were deleted.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - None of the selected entries were found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The entries could not be deleted.
     * @param format The format parameter
     * @param ids IDs of the records to delete.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteTimeEntriesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull List<Integer> ids, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteTimeEntries", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ids' is set
        if (ids == null) {
            throw new RestClientResponseException("Missing the required parameter 'ids' when calling deleteTimeEntries", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "ids[]", ids));

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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/time_entries/destroy.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete time entries
     * Deletes the selected time entries. Unknown IDs are ignored if at least one entry is found. The current user must be allowed to delete every found entry.
     * <p><b>204</b> - The entries were deleted.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - None of the selected entries were found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The entries could not be deleted.
     * @param format The format parameter
     * @param ids IDs of the records to delete.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteTimeEntries(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull List<Integer> ids, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteTimeEntriesRequestCreation(format, ids, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Delete time entries
     * Deletes the selected time entries. Unknown IDs are ignored if at least one entry is found. The current user must be allowed to delete every found entry.
     * <p><b>204</b> - The entries were deleted.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - None of the selected entries were found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The entries could not be deleted.
     * @param format The format parameter
     * @param ids IDs of the records to delete.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteTimeEntriesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull List<Integer> ids, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTimeEntriesRequestCreation(format, ids, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Delete time entries
     * Deletes the selected time entries. Unknown IDs are ignored if at least one entry is found. The current user must be allowed to delete every found entry.
     * <p><b>204</b> - The entries were deleted.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - None of the selected entries were found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The entries could not be deleted.
     * @param format The format parameter
     * @param ids IDs of the records to delete.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteTimeEntriesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull List<Integer> ids, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return deleteTimeEntriesRequestCreation(format, ids, xRedmineSwitchUser);
    }

    /**
     * Delete time entry
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Deleting-a-time-entry">Delete time entry Documentation</a>
     */
    private ResponseSpec deleteTimeEntryRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeEntryId' is set
        if (timeEntryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeEntryId' when calling deleteTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("time_entry_id", timeEntryId);

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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/time_entries/{time_entry_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete time entry
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Deleting-a-time-entry">Delete time entry Documentation</a>
     */
    public void deleteTimeEntry(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Delete time entry
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Deleting-a-time-entry">Delete time entry Documentation</a>
     */
    public ResponseEntity<Void> deleteTimeEntryWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Delete time entry
     * 
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Deleting-a-time-entry">Delete time entry Documentation</a>
     */
    public ResponseSpec deleteTimeEntryWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return deleteTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser);
    }

    /**
     * List time entries
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Not found. The body is empty for an unknown project, or contains a framework error object for an unknown saved query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param userId Filter by user. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param projectId Project ID or identifier that scopes results to a project. Use &#x60;subproject_id&#x60; to control which subprojects to include. To filter by multiple project IDs or use an operator, omit this parameter and use &#x60;f[]&#x60;, &#x60;op&#x60;, and &#x60;v&#x60;.
     * @param spentOn Filter by date spent. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;&gt;t-7&#x60;, &#x60;t&#x60;, &#x60;lw&#x60;
     * @param from Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;to&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&gt;&#x3D;from&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param to Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;from&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&lt;&#x3D;to&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (selected project only) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only when &#x60;project_id&#x60; selects a project with subprojects.
     * @param issueId Filter by issue. Format: &#x60;[operator]ID&#x60; Operators: &#x60;&#x3D;&#x60; (default, one exact issue ID), &#x60;~&#x60; (the issue and all its descendants), &#x60;*&#x60; (any issue), &#x60;!*&#x60; (no issue). Examples: &#x60;1&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueTrackerId Filter by issue&#39;s tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueStatusId Filter by issue&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueFixedVersionId Filter by issue&#39;s target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueParentId Filter time entries by the issue&#39;s parent or ancestry. With &#x60;&#x3D;&#x60; (default), provide one parent issue ID or comma-separated IDs to select entries on their immediate children. With &#x60;~ID&#x60;, select entries on descendants of that issue, excluding the issue itself. Use &#x60;issue_id&#x3D;~ID&#x60; to include that issue as well. &#x60;*&#x60; selects entries whose issue has a parent; &#x60;!*&#x60; selects entries on root issues or without an issue. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueCategoryId Filter by the issue&#39;s category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only when &#x60;project_id&#x60; selects a project.
     * @param issueSubject Filter by issue&#39;s subject. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;, &#x60;$suffix&#x60;
     * @param userGroup Filter by user&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param userRole Filter by user&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param activityId Filter by activity. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param projectStatus Filter by project&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param comments Filter by comments. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param hours Filter by hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&#x3D;1:30&#x60;, &#x60;&gt;&#x3D;4&#x60;, &#x60;&gt;&lt;1|8&#x60;
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;spent_on:desc&#x60;, &#x60;hours:desc,spent_on&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @return GetTimeEntries200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Listing-time-entries">List time entries Documentation</a>
     */
    private ResponseSpec getTimeEntriesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable String spentOn, @jakarta.annotation.Nullable LocalDate from, @jakarta.annotation.Nullable LocalDate to, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String issueTrackerId, @jakarta.annotation.Nullable String issueStatusId, @jakarta.annotation.Nullable String issueFixedVersionId, @jakarta.annotation.Nullable String issueParentId, @jakarta.annotation.Nullable String issueCategoryId, @jakarta.annotation.Nullable String issueSubject, @jakarta.annotation.Nullable String userGroup, @jakarta.annotation.Nullable String userRole, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String activityId, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String comments, @jakarta.annotation.Nullable String hours, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getTimeEntries", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project_id", projectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "spent_on", spentOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "to", to));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "subproject_id", subprojectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue_id", issueId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.tracker_id", issueTrackerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.status_id", issueStatusId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.fixed_version_id", issueFixedVersionId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.parent_id", issueParentId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.category_id", issueCategoryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.subject", issueSubject));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user.group", userGroup));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user.role", userRole));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_id", activityId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project.status", projectStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "comments", comments));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "hours", hours));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query_id", queryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "f[]", f));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "op", op));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "v", v));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cf_x", cfX));

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

        ParameterizedTypeReference<GetTimeEntries200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/time_entries.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List time entries
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Not found. The body is empty for an unknown project, or contains a framework error object for an unknown saved query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param userId Filter by user. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param projectId Project ID or identifier that scopes results to a project. Use &#x60;subproject_id&#x60; to control which subprojects to include. To filter by multiple project IDs or use an operator, omit this parameter and use &#x60;f[]&#x60;, &#x60;op&#x60;, and &#x60;v&#x60;.
     * @param spentOn Filter by date spent. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;&gt;t-7&#x60;, &#x60;t&#x60;, &#x60;lw&#x60;
     * @param from Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;to&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&gt;&#x3D;from&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param to Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;from&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&lt;&#x3D;to&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (selected project only) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only when &#x60;project_id&#x60; selects a project with subprojects.
     * @param issueId Filter by issue. Format: &#x60;[operator]ID&#x60; Operators: &#x60;&#x3D;&#x60; (default, one exact issue ID), &#x60;~&#x60; (the issue and all its descendants), &#x60;*&#x60; (any issue), &#x60;!*&#x60; (no issue). Examples: &#x60;1&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueTrackerId Filter by issue&#39;s tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueStatusId Filter by issue&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueFixedVersionId Filter by issue&#39;s target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueParentId Filter time entries by the issue&#39;s parent or ancestry. With &#x60;&#x3D;&#x60; (default), provide one parent issue ID or comma-separated IDs to select entries on their immediate children. With &#x60;~ID&#x60;, select entries on descendants of that issue, excluding the issue itself. Use &#x60;issue_id&#x3D;~ID&#x60; to include that issue as well. &#x60;*&#x60; selects entries whose issue has a parent; &#x60;!*&#x60; selects entries on root issues or without an issue. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueCategoryId Filter by the issue&#39;s category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only when &#x60;project_id&#x60; selects a project.
     * @param issueSubject Filter by issue&#39;s subject. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;, &#x60;$suffix&#x60;
     * @param userGroup Filter by user&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param userRole Filter by user&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param activityId Filter by activity. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param projectStatus Filter by project&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param comments Filter by comments. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param hours Filter by hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&#x3D;1:30&#x60;, &#x60;&gt;&#x3D;4&#x60;, &#x60;&gt;&lt;1|8&#x60;
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;spent_on:desc&#x60;, &#x60;hours:desc,spent_on&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @return GetTimeEntries200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Listing-time-entries">List time entries Documentation</a>
     */
    public GetTimeEntries200Response getTimeEntries(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable String spentOn, @jakarta.annotation.Nullable LocalDate from, @jakarta.annotation.Nullable LocalDate to, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String issueTrackerId, @jakarta.annotation.Nullable String issueStatusId, @jakarta.annotation.Nullable String issueFixedVersionId, @jakarta.annotation.Nullable String issueParentId, @jakarta.annotation.Nullable String issueCategoryId, @jakarta.annotation.Nullable String issueSubject, @jakarta.annotation.Nullable String userGroup, @jakarta.annotation.Nullable String userRole, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String activityId, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String comments, @jakarta.annotation.Nullable String hours, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX) throws RestClientResponseException {
        ParameterizedTypeReference<GetTimeEntries200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntriesRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, userId, projectId, spentOn, from, to, subprojectId, issueId, issueTrackerId, issueStatusId, issueFixedVersionId, issueParentId, issueCategoryId, issueSubject, userGroup, userRole, authorId, activityId, projectStatus, comments, hours, sort, page, queryId, f, op, v, cfX).body(localVarReturnType);
    }

    /**
     * List time entries
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Not found. The body is empty for an unknown project, or contains a framework error object for an unknown saved query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param userId Filter by user. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param projectId Project ID or identifier that scopes results to a project. Use &#x60;subproject_id&#x60; to control which subprojects to include. To filter by multiple project IDs or use an operator, omit this parameter and use &#x60;f[]&#x60;, &#x60;op&#x60;, and &#x60;v&#x60;.
     * @param spentOn Filter by date spent. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;&gt;t-7&#x60;, &#x60;t&#x60;, &#x60;lw&#x60;
     * @param from Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;to&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&gt;&#x3D;from&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param to Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;from&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&lt;&#x3D;to&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (selected project only) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only when &#x60;project_id&#x60; selects a project with subprojects.
     * @param issueId Filter by issue. Format: &#x60;[operator]ID&#x60; Operators: &#x60;&#x3D;&#x60; (default, one exact issue ID), &#x60;~&#x60; (the issue and all its descendants), &#x60;*&#x60; (any issue), &#x60;!*&#x60; (no issue). Examples: &#x60;1&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueTrackerId Filter by issue&#39;s tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueStatusId Filter by issue&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueFixedVersionId Filter by issue&#39;s target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueParentId Filter time entries by the issue&#39;s parent or ancestry. With &#x60;&#x3D;&#x60; (default), provide one parent issue ID or comma-separated IDs to select entries on their immediate children. With &#x60;~ID&#x60;, select entries on descendants of that issue, excluding the issue itself. Use &#x60;issue_id&#x3D;~ID&#x60; to include that issue as well. &#x60;*&#x60; selects entries whose issue has a parent; &#x60;!*&#x60; selects entries on root issues or without an issue. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueCategoryId Filter by the issue&#39;s category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only when &#x60;project_id&#x60; selects a project.
     * @param issueSubject Filter by issue&#39;s subject. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;, &#x60;$suffix&#x60;
     * @param userGroup Filter by user&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param userRole Filter by user&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param activityId Filter by activity. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param projectStatus Filter by project&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param comments Filter by comments. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param hours Filter by hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&#x3D;1:30&#x60;, &#x60;&gt;&#x3D;4&#x60;, &#x60;&gt;&lt;1|8&#x60;
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;spent_on:desc&#x60;, &#x60;hours:desc,spent_on&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @return ResponseEntity&lt;GetTimeEntries200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Listing-time-entries">List time entries Documentation</a>
     */
    public ResponseEntity<GetTimeEntries200Response> getTimeEntriesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable String spentOn, @jakarta.annotation.Nullable LocalDate from, @jakarta.annotation.Nullable LocalDate to, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String issueTrackerId, @jakarta.annotation.Nullable String issueStatusId, @jakarta.annotation.Nullable String issueFixedVersionId, @jakarta.annotation.Nullable String issueParentId, @jakarta.annotation.Nullable String issueCategoryId, @jakarta.annotation.Nullable String issueSubject, @jakarta.annotation.Nullable String userGroup, @jakarta.annotation.Nullable String userRole, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String activityId, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String comments, @jakarta.annotation.Nullable String hours, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX) throws RestClientResponseException {
        ParameterizedTypeReference<GetTimeEntries200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntriesRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, userId, projectId, spentOn, from, to, subprojectId, issueId, issueTrackerId, issueStatusId, issueFixedVersionId, issueParentId, issueCategoryId, issueSubject, userGroup, userRole, authorId, activityId, projectStatus, comments, hours, sort, page, queryId, f, op, v, cfX).toEntity(localVarReturnType);
    }

    /**
     * List time entries
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Not found. The body is empty for an unknown project, or contains a framework error object for an unknown saved query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param userId Filter by user. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param projectId Project ID or identifier that scopes results to a project. Use &#x60;subproject_id&#x60; to control which subprojects to include. To filter by multiple project IDs or use an operator, omit this parameter and use &#x60;f[]&#x60;, &#x60;op&#x60;, and &#x60;v&#x60;.
     * @param spentOn Filter by date spent. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;&gt;t-7&#x60;, &#x60;t&#x60;, &#x60;lw&#x60;
     * @param from Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;to&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&gt;&#x3D;from&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param to Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;from&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&lt;&#x3D;to&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (selected project only) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only when &#x60;project_id&#x60; selects a project with subprojects.
     * @param issueId Filter by issue. Format: &#x60;[operator]ID&#x60; Operators: &#x60;&#x3D;&#x60; (default, one exact issue ID), &#x60;~&#x60; (the issue and all its descendants), &#x60;*&#x60; (any issue), &#x60;!*&#x60; (no issue). Examples: &#x60;1&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueTrackerId Filter by issue&#39;s tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueStatusId Filter by issue&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueFixedVersionId Filter by issue&#39;s target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueParentId Filter time entries by the issue&#39;s parent or ancestry. With &#x60;&#x3D;&#x60; (default), provide one parent issue ID or comma-separated IDs to select entries on their immediate children. With &#x60;~ID&#x60;, select entries on descendants of that issue, excluding the issue itself. Use &#x60;issue_id&#x3D;~ID&#x60; to include that issue as well. &#x60;*&#x60; selects entries whose issue has a parent; &#x60;!*&#x60; selects entries on root issues or without an issue. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueCategoryId Filter by the issue&#39;s category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60; Available only when &#x60;project_id&#x60; selects a project.
     * @param issueSubject Filter by issue&#39;s subject. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;, &#x60;$suffix&#x60;
     * @param userGroup Filter by user&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param userRole Filter by user&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param activityId Filter by activity. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param projectStatus Filter by project&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param comments Filter by comments. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param hours Filter by hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&#x3D;1:30&#x60;, &#x60;&gt;&#x3D;4&#x60;, &#x60;&gt;&lt;1|8&#x60;
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;spent_on:desc&#x60;, &#x60;hours:desc,spent_on&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Listing-time-entries">List time entries Documentation</a>
     */
    public ResponseSpec getTimeEntriesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String projectId, @jakarta.annotation.Nullable String spentOn, @jakarta.annotation.Nullable LocalDate from, @jakarta.annotation.Nullable LocalDate to, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String issueTrackerId, @jakarta.annotation.Nullable String issueStatusId, @jakarta.annotation.Nullable String issueFixedVersionId, @jakarta.annotation.Nullable String issueParentId, @jakarta.annotation.Nullable String issueCategoryId, @jakarta.annotation.Nullable String issueSubject, @jakarta.annotation.Nullable String userGroup, @jakarta.annotation.Nullable String userRole, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String activityId, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String comments, @jakarta.annotation.Nullable String hours, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX) throws RestClientResponseException {
        return getTimeEntriesRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, userId, projectId, spentOn, from, to, subprojectId, issueId, issueTrackerId, issueStatusId, issueFixedVersionId, issueParentId, issueCategoryId, issueSubject, userGroup, userRole, authorId, activityId, projectStatus, comments, hours, sort, page, queryId, f, op, v, cfX);
    }

    /**
     * List time entries by project
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Not found. The body is empty for an unknown project, or contains a framework error object for an unknown saved query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param userId Filter by user. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param spentOn Filter by date spent. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;&gt;t-7&#x60;, &#x60;t&#x60;, &#x60;lw&#x60;
     * @param from Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;to&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&gt;&#x3D;from&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param to Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;from&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&lt;&#x3D;to&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (selected project only) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param issueId Filter by issue. Format: &#x60;[operator]ID&#x60; Operators: &#x60;&#x3D;&#x60; (default, one exact issue ID), &#x60;~&#x60; (the issue and all its descendants), &#x60;*&#x60; (any issue), &#x60;!*&#x60; (no issue). Examples: &#x60;1&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueTrackerId Filter by issue&#39;s tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueStatusId Filter by issue&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueFixedVersionId Filter by issue&#39;s target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueParentId Filter time entries by the issue&#39;s parent or ancestry. With &#x60;&#x3D;&#x60; (default), provide one parent issue ID or comma-separated IDs to select entries on their immediate children. With &#x60;~ID&#x60;, select entries on descendants of that issue, excluding the issue itself. Use &#x60;issue_id&#x3D;~ID&#x60; to include that issue as well. &#x60;*&#x60; selects entries whose issue has a parent; &#x60;!*&#x60; selects entries on root issues or without an issue. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueCategoryId Filter by the issue&#39;s category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param issueSubject Filter by issue&#39;s subject. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;, &#x60;$suffix&#x60;
     * @param userGroup Filter by user&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param userRole Filter by user&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param activityId Filter by activity. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param projectStatus Filter by project&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param comments Filter by comments. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param hours Filter by hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&#x3D;1:30&#x60;, &#x60;&gt;&#x3D;4&#x60;, &#x60;&gt;&lt;1|8&#x60;
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;spent_on:desc&#x60;, &#x60;hours:desc,spent_on&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @return GetTimeEntries200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Listing-time-entries">List time entries by project Documentation</a>
     */
    private ResponseSpec getTimeEntriesByProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String spentOn, @jakarta.annotation.Nullable LocalDate from, @jakarta.annotation.Nullable LocalDate to, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String issueTrackerId, @jakarta.annotation.Nullable String issueStatusId, @jakarta.annotation.Nullable String issueFixedVersionId, @jakarta.annotation.Nullable String issueParentId, @jakarta.annotation.Nullable String issueCategoryId, @jakarta.annotation.Nullable String issueSubject, @jakarta.annotation.Nullable String userGroup, @jakarta.annotation.Nullable String userRole, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String activityId, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String comments, @jakarta.annotation.Nullable String hours, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getTimeEntriesByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getTimeEntriesByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "spent_on", spentOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "to", to));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "subproject_id", subprojectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue_id", issueId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.tracker_id", issueTrackerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.status_id", issueStatusId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.fixed_version_id", issueFixedVersionId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.parent_id", issueParentId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.category_id", issueCategoryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue.subject", issueSubject));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user.group", userGroup));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user.role", userRole));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activity_id", activityId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project.status", projectStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "comments", comments));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "hours", hours));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query_id", queryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "f[]", f));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "op", op));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "v", v));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cf_x", cfX));

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

        ParameterizedTypeReference<GetTimeEntries200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/time_entries.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List time entries by project
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Not found. The body is empty for an unknown project, or contains a framework error object for an unknown saved query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param userId Filter by user. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param spentOn Filter by date spent. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;&gt;t-7&#x60;, &#x60;t&#x60;, &#x60;lw&#x60;
     * @param from Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;to&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&gt;&#x3D;from&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param to Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;from&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&lt;&#x3D;to&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (selected project only) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param issueId Filter by issue. Format: &#x60;[operator]ID&#x60; Operators: &#x60;&#x3D;&#x60; (default, one exact issue ID), &#x60;~&#x60; (the issue and all its descendants), &#x60;*&#x60; (any issue), &#x60;!*&#x60; (no issue). Examples: &#x60;1&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueTrackerId Filter by issue&#39;s tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueStatusId Filter by issue&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueFixedVersionId Filter by issue&#39;s target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueParentId Filter time entries by the issue&#39;s parent or ancestry. With &#x60;&#x3D;&#x60; (default), provide one parent issue ID or comma-separated IDs to select entries on their immediate children. With &#x60;~ID&#x60;, select entries on descendants of that issue, excluding the issue itself. Use &#x60;issue_id&#x3D;~ID&#x60; to include that issue as well. &#x60;*&#x60; selects entries whose issue has a parent; &#x60;!*&#x60; selects entries on root issues or without an issue. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueCategoryId Filter by the issue&#39;s category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param issueSubject Filter by issue&#39;s subject. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;, &#x60;$suffix&#x60;
     * @param userGroup Filter by user&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param userRole Filter by user&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param activityId Filter by activity. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param projectStatus Filter by project&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param comments Filter by comments. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param hours Filter by hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&#x3D;1:30&#x60;, &#x60;&gt;&#x3D;4&#x60;, &#x60;&gt;&lt;1|8&#x60;
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;spent_on:desc&#x60;, &#x60;hours:desc,spent_on&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @return GetTimeEntries200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Listing-time-entries">List time entries by project Documentation</a>
     */
    public GetTimeEntries200Response getTimeEntriesByProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String spentOn, @jakarta.annotation.Nullable LocalDate from, @jakarta.annotation.Nullable LocalDate to, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String issueTrackerId, @jakarta.annotation.Nullable String issueStatusId, @jakarta.annotation.Nullable String issueFixedVersionId, @jakarta.annotation.Nullable String issueParentId, @jakarta.annotation.Nullable String issueCategoryId, @jakarta.annotation.Nullable String issueSubject, @jakarta.annotation.Nullable String userGroup, @jakarta.annotation.Nullable String userRole, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String activityId, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String comments, @jakarta.annotation.Nullable String hours, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX) throws RestClientResponseException {
        ParameterizedTypeReference<GetTimeEntries200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntriesByProjectRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, userId, spentOn, from, to, subprojectId, issueId, issueTrackerId, issueStatusId, issueFixedVersionId, issueParentId, issueCategoryId, issueSubject, userGroup, userRole, authorId, activityId, projectStatus, comments, hours, sort, page, queryId, f, op, v, cfX).body(localVarReturnType);
    }

    /**
     * List time entries by project
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Not found. The body is empty for an unknown project, or contains a framework error object for an unknown saved query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param userId Filter by user. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param spentOn Filter by date spent. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;&gt;t-7&#x60;, &#x60;t&#x60;, &#x60;lw&#x60;
     * @param from Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;to&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&gt;&#x3D;from&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param to Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;from&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&lt;&#x3D;to&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (selected project only) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param issueId Filter by issue. Format: &#x60;[operator]ID&#x60; Operators: &#x60;&#x3D;&#x60; (default, one exact issue ID), &#x60;~&#x60; (the issue and all its descendants), &#x60;*&#x60; (any issue), &#x60;!*&#x60; (no issue). Examples: &#x60;1&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueTrackerId Filter by issue&#39;s tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueStatusId Filter by issue&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueFixedVersionId Filter by issue&#39;s target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueParentId Filter time entries by the issue&#39;s parent or ancestry. With &#x60;&#x3D;&#x60; (default), provide one parent issue ID or comma-separated IDs to select entries on their immediate children. With &#x60;~ID&#x60;, select entries on descendants of that issue, excluding the issue itself. Use &#x60;issue_id&#x3D;~ID&#x60; to include that issue as well. &#x60;*&#x60; selects entries whose issue has a parent; &#x60;!*&#x60; selects entries on root issues or without an issue. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueCategoryId Filter by the issue&#39;s category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param issueSubject Filter by issue&#39;s subject. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;, &#x60;$suffix&#x60;
     * @param userGroup Filter by user&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param userRole Filter by user&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param activityId Filter by activity. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param projectStatus Filter by project&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param comments Filter by comments. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param hours Filter by hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&#x3D;1:30&#x60;, &#x60;&gt;&#x3D;4&#x60;, &#x60;&gt;&lt;1|8&#x60;
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;spent_on:desc&#x60;, &#x60;hours:desc,spent_on&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @return ResponseEntity&lt;GetTimeEntries200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Listing-time-entries">List time entries by project Documentation</a>
     */
    public ResponseEntity<GetTimeEntries200Response> getTimeEntriesByProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String spentOn, @jakarta.annotation.Nullable LocalDate from, @jakarta.annotation.Nullable LocalDate to, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String issueTrackerId, @jakarta.annotation.Nullable String issueStatusId, @jakarta.annotation.Nullable String issueFixedVersionId, @jakarta.annotation.Nullable String issueParentId, @jakarta.annotation.Nullable String issueCategoryId, @jakarta.annotation.Nullable String issueSubject, @jakarta.annotation.Nullable String userGroup, @jakarta.annotation.Nullable String userRole, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String activityId, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String comments, @jakarta.annotation.Nullable String hours, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX) throws RestClientResponseException {
        ParameterizedTypeReference<GetTimeEntries200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntriesByProjectRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, userId, spentOn, from, to, subprojectId, issueId, issueTrackerId, issueStatusId, issueFixedVersionId, issueParentId, issueCategoryId, issueSubject, userGroup, userRole, authorId, activityId, projectStatus, comments, hours, sort, page, queryId, f, op, v, cfX).toEntity(localVarReturnType);
    }

    /**
     * List time entries by project
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Not found. The body is empty for an unknown project, or contains a framework error object for an unknown saved query.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param userId Filter by user. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param spentOn Filter by date spent. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (on date), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&lt;2024-01-01|2024-12-31&#x60;, &#x60;&gt;t-7&#x60;, &#x60;t&#x60;, &#x60;lw&#x60;
     * @param from Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;to&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&gt;&#x3D;from&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param to Shortcut for &#x60;spent_on&#x60; filter. When used with &#x60;from&#x60;, filters as between (&#x60;&gt;&#x3D;from&#x60; and &#x60;&lt;&#x3D;to&#x60;). When used alone, filters as &#x60;&lt;&#x3D;to&#x60;. Format: &#x60;YYYY-MM-DD&#x60;
     * @param subprojectId Filter by subproject. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (selected project only) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;, &#x60;!*&#x60; Available only for a project with subprojects.
     * @param issueId Filter by issue. Format: &#x60;[operator]ID&#x60; Operators: &#x60;&#x3D;&#x60; (default, one exact issue ID), &#x60;~&#x60; (the issue and all its descendants), &#x60;*&#x60; (any issue), &#x60;!*&#x60; (no issue). Examples: &#x60;1&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueTrackerId Filter by issue&#39;s tracker. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueStatusId Filter by issue&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueFixedVersionId Filter by issue&#39;s target version. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param issueParentId Filter time entries by the issue&#39;s parent or ancestry. With &#x60;&#x3D;&#x60; (default), provide one parent issue ID or comma-separated IDs to select entries on their immediate children. With &#x60;~ID&#x60;, select entries on descendants of that issue, excluding the issue itself. Use &#x60;issue_id&#x3D;~ID&#x60; to include that issue as well. &#x60;*&#x60; selects entries whose issue has a parent; &#x60;!*&#x60; selects entries on root issues or without an issue. Examples: &#x60;1&#x60;, &#x60;1,2&#x60;, &#x60;~1&#x60;, &#x60;*&#x60;, &#x60;!*&#x60;.
     * @param issueCategoryId Filter by the issue&#39;s category. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;!*&#x60;
     * @param issueSubject Filter by issue&#39;s subject. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;, &#x60;$suffix&#x60;
     * @param userGroup Filter by user&#39;s group. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param userRole Filter by user&#39;s role. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1|2&#x60;, &#x60;*&#x60;
     * @param authorId Filter by author. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Special values: &#x60;me&#x60; (current user) Examples: &#x60;1&#x60;, &#x60;me&#x60;, &#x60;!*&#x60;
     * @param activityId Filter by activity. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Examples: &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param projectStatus Filter by project&#39;s status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (active), &#x60;5&#x60; (closed) Examples: &#x60;1&#x60;, &#x60;!5&#x60;
     * @param comments Filter by comments. Format: &#x60;[operator]value&#x60; A value without an operator uses exact matching. A leading &#x60;&#x3D;&#x60; is part of the literal value. Operators: &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~keyword&#x60;, &#x60;^prefix&#x60;
     * @param hours Filter by hours. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;&gt;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values can be decimal hours (&#x60;1.5&#x60;), hours and minutes (&#x60;1:30&#x60; or &#x60;1h30&#x60;), or minutes (&#x60;90m&#x60;). Examples: &#x60;&#x3D;1:30&#x60;, &#x60;&gt;&#x3D;4&#x60;, &#x60;&gt;&lt;1|8&#x60;
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Examples: &#x60;spent_on:desc&#x60;, &#x60;hours:desc,spent_on&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Listing-time-entries">List time entries by project Documentation</a>
     */
    public ResponseSpec getTimeEntriesByProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull java.lang.String projectId, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String spentOn, @jakarta.annotation.Nullable LocalDate from, @jakarta.annotation.Nullable LocalDate to, @jakarta.annotation.Nullable String subprojectId, @jakarta.annotation.Nullable String issueId, @jakarta.annotation.Nullable String issueTrackerId, @jakarta.annotation.Nullable String issueStatusId, @jakarta.annotation.Nullable String issueFixedVersionId, @jakarta.annotation.Nullable String issueParentId, @jakarta.annotation.Nullable String issueCategoryId, @jakarta.annotation.Nullable String issueSubject, @jakarta.annotation.Nullable String userGroup, @jakarta.annotation.Nullable String userRole, @jakarta.annotation.Nullable String authorId, @jakarta.annotation.Nullable String activityId, @jakarta.annotation.Nullable String projectStatus, @jakarta.annotation.Nullable String comments, @jakarta.annotation.Nullable String hours, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX) throws RestClientResponseException {
        return getTimeEntriesByProjectRequestCreation(format, projectId, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, userId, spentOn, from, to, subprojectId, issueId, issueTrackerId, issueStatusId, issueFixedVersionId, issueParentId, issueCategoryId, issueSubject, userGroup, userRole, authorId, activityId, projectStatus, comments, hours, sort, page, queryId, f, op, v, cfX);
    }

    /**
     * Show time entry
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateTimeEntry201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Showing-a-time-entry">Show time entry Documentation</a>
     */
    private ResponseSpec getTimeEntryRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeEntryId' is set
        if (timeEntryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeEntryId' when calling getTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("time_entry_id", timeEntryId);

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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/time_entries/{time_entry_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show time entry
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateTimeEntry201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Showing-a-time-entry">Show time entry Documentation</a>
     */
    public CreateTimeEntry201Response getTimeEntry(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Show time entry
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateTimeEntry201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Showing-a-time-entry">Show time entry Documentation</a>
     */
    public ResponseEntity<CreateTimeEntry201Response> getTimeEntryWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateTimeEntry201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Show time entry
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Showing-a-time-entry">Show time entry Documentation</a>
     */
    public ResponseSpec getTimeEntryWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return getTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser);
    }

    /**
     * Update time entry
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateTimeEntryRequest The updateTimeEntryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Updating-a-time-entry">Update time entry Documentation</a>
     */
    private ResponseSpec updateTimeEntryRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateTimeEntryRequest updateTimeEntryRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeEntryId' is set
        if (timeEntryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeEntryId' when calling updateTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("time_entry_id", timeEntryId);

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
        return apiClient.invokeAPI("/time_entries/{time_entry_id}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateTimeEntryRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update time entry
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateTimeEntryRequest The updateTimeEntryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Updating-a-time-entry">Update time entry Documentation</a>
     */
    public void updateTimeEntry(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateTimeEntryRequest updateTimeEntryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser, updateTimeEntryRequest).body(localVarReturnType);
    }

    /**
     * Update time entry
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateTimeEntryRequest The updateTimeEntryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Updating-a-time-entry">Update time entry Documentation</a>
     */
    public ResponseEntity<Void> updateTimeEntryWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateTimeEntryRequest updateTimeEntryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser, updateTimeEntryRequest).toEntity(localVarReturnType);
    }

    /**
     * Update time entry
     * 
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateTimeEntryRequest The updateTimeEntryRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Updating-a-time-entry">Update time entry Documentation</a>
     */
    public ResponseSpec updateTimeEntryWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateTimeEntryRequest updateTimeEntryRequest) throws RestClientResponseException {
        return updateTimeEntryRequestCreation(format, timeEntryId, xRedmineSwitchUser, updateTimeEntryRequest);
    }

    /**
     * Update time entry (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateTimeEntryRequest The updateTimeEntryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Updating-a-time-entry">Update time entry (PATCH) Documentation</a>
     */
    private ResponseSpec updateTimeEntryViaPatchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateTimeEntryRequest updateTimeEntryRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateTimeEntryViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeEntryId' is set
        if (timeEntryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'timeEntryId' when calling updateTimeEntryViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("time_entry_id", timeEntryId);

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
        return apiClient.invokeAPI("/time_entries/{time_entry_id}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateTimeEntryRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update time entry (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateTimeEntryRequest The updateTimeEntryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Updating-a-time-entry">Update time entry (PATCH) Documentation</a>
     */
    public void updateTimeEntryViaPatch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateTimeEntryRequest updateTimeEntryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateTimeEntryViaPatchRequestCreation(format, timeEntryId, xRedmineSwitchUser, updateTimeEntryRequest).body(localVarReturnType);
    }

    /**
     * Update time entry (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateTimeEntryRequest The updateTimeEntryRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Updating-a-time-entry">Update time entry (PATCH) Documentation</a>
     */
    public ResponseEntity<Void> updateTimeEntryViaPatchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateTimeEntryRequest updateTimeEntryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTimeEntryViaPatchRequestCreation(format, timeEntryId, xRedmineSwitchUser, updateTimeEntryRequest).toEntity(localVarReturnType);
    }

    /**
     * Update time entry (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param timeEntryId The timeEntryId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateTimeEntryRequest The updateTimeEntryRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries#Updating-a-time-entry">Update time entry (PATCH) Documentation</a>
     */
    public ResponseSpec updateTimeEntryViaPatchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer timeEntryId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateTimeEntryRequest updateTimeEntryRequest) throws RestClientResponseException {
        return updateTimeEntryViaPatchRequestCreation(format, timeEntryId, xRedmineSwitchUser, updateTimeEntryRequest);
    }
}
