package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.AddUserToGroupRequest;
import ai.gebo.redmine.client.model.CreateGroup201Response;
import ai.gebo.redmine.client.model.CreateGroupRequest;
import ai.gebo.redmine.client.model.Errors;
import ai.gebo.redmine.client.model.GetGroups200Response;
import ai.gebo.redmine.client.model.UpdateGroupRequest;

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
public class GroupsApi {
    private ApiClient apiClient;

    public GroupsApi() {
        this(new ApiClient());
    }

    public GroupsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add user to group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param addUserToGroupRequest The addUserToGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#POST-2">Add user to group Documentation</a>
     */
    private ResponseSpec addUserToGroupRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable AddUserToGroupRequest addUserToGroupRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling addUserToGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling addUserToGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/groups/{group_id}/users.{format}", HttpMethod.POST, pathParams, localVarQueryParams, addUserToGroupRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add user to group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param addUserToGroupRequest The addUserToGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#POST-2">Add user to group Documentation</a>
     */
    public void addUserToGroup(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable AddUserToGroupRequest addUserToGroupRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        addUserToGroupRequestCreation(format, groupId, xRedmineSwitchUser, addUserToGroupRequest).body(localVarReturnType);
    }

    /**
     * Add user to group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param addUserToGroupRequest The addUserToGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#POST-2">Add user to group Documentation</a>
     */
    public ResponseEntity<Void> addUserToGroupWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable AddUserToGroupRequest addUserToGroupRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addUserToGroupRequestCreation(format, groupId, xRedmineSwitchUser, addUserToGroupRequest).toEntity(localVarReturnType);
    }

    /**
     * Add user to group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param addUserToGroupRequest The addUserToGroupRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#POST-2">Add user to group Documentation</a>
     */
    public ResponseSpec addUserToGroupWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable AddUserToGroupRequest addUserToGroupRequest) throws RestClientResponseException {
        return addUserToGroupRequestCreation(format, groupId, xRedmineSwitchUser, addUserToGroupRequest);
    }

    /**
     * Create group
     * Requires an administrator.
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createGroupRequest The createGroupRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateGroup201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#POST">Create group Documentation</a>
     */
    private ResponseSpec createGroupRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateGroupRequest createGroupRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createGroupRequest' is set
        if (createGroupRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createGroupRequest' when calling createGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CreateGroup201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/groups.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createGroupRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create group
     * Requires an administrator.
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createGroupRequest The createGroupRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateGroup201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#POST">Create group Documentation</a>
     */
    public CreateGroup201Response createGroup(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateGroupRequest createGroupRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateGroup201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createGroupRequestCreation(format, createGroupRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create group
     * Requires an administrator.
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createGroupRequest The createGroupRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateGroup201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#POST">Create group Documentation</a>
     */
    public ResponseEntity<CreateGroup201Response> createGroupWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateGroupRequest createGroupRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateGroup201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createGroupRequestCreation(format, createGroupRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create group
     * Requires an administrator.
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createGroupRequest The createGroupRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#POST">Create group Documentation</a>
     */
    public ResponseSpec createGroupWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateGroupRequest createGroupRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createGroupRequestCreation(format, createGroupRequest, xRedmineSwitchUser);
    }

    /**
     * Delete group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#DELETE">Delete group Documentation</a>
     */
    private ResponseSpec deleteGroupRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling deleteGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/groups/{group_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#DELETE">Delete group Documentation</a>
     */
    public void deleteGroup(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteGroupRequestCreation(format, groupId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Delete group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#DELETE">Delete group Documentation</a>
     */
    public ResponseEntity<Void> deleteGroupWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteGroupRequestCreation(format, groupId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Delete group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#DELETE">Delete group Documentation</a>
     */
    public ResponseSpec deleteGroupWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return deleteGroupRequestCreation(format, groupId, xRedmineSwitchUser);
    }

    /**
     * Show group
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;users&#x60; (list of users in the group), &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user)
     * @return CreateGroup201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#GET-2">Show group Documentation</a>
     */
    private ResponseSpec getGroupRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling getGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("group_id", groupId);

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

        ParameterizedTypeReference<CreateGroup201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/groups/{group_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show group
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;users&#x60; (list of users in the group), &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user)
     * @return CreateGroup201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#GET-2">Show group Documentation</a>
     */
    public CreateGroup201Response getGroup(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<CreateGroup201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGroupRequestCreation(format, groupId, xRedmineSwitchUser, include).body(localVarReturnType);
    }

    /**
     * Show group
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;users&#x60; (list of users in the group), &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user)
     * @return ResponseEntity&lt;CreateGroup201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#GET-2">Show group Documentation</a>
     */
    public ResponseEntity<CreateGroup201Response> getGroupWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<CreateGroup201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGroupRequestCreation(format, groupId, xRedmineSwitchUser, include).toEntity(localVarReturnType);
    }

    /**
     * Show group
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;users&#x60; (list of users in the group), &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#GET-2">Show group Documentation</a>
     */
    public ResponseSpec getGroupWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        return getGroupRequestCreation(format, groupId, xRedmineSwitchUser, include);
    }

    /**
     * List groups
     * Requires an administrator.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param builtin Include built-in groups (e.g., \&quot;Anonymous\&quot;, \&quot;Non member\&quot;) in the response. Set to &#x60;1&#x60; to include built-in groups. Without this parameter, only user-created groups are returned.
     * @return GetGroups200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#GET">List groups Documentation</a>
     */
    private ResponseSpec getGroupsRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String builtin) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getGroups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "builtin", builtin));

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

        ParameterizedTypeReference<GetGroups200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/groups.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List groups
     * Requires an administrator.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param builtin Include built-in groups (e.g., \&quot;Anonymous\&quot;, \&quot;Non member\&quot;) in the response. Set to &#x60;1&#x60; to include built-in groups. Without this parameter, only user-created groups are returned.
     * @return GetGroups200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#GET">List groups Documentation</a>
     */
    public GetGroups200Response getGroups(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String builtin) throws RestClientResponseException {
        ParameterizedTypeReference<GetGroups200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGroupsRequestCreation(format, xRedmineSwitchUser, builtin).body(localVarReturnType);
    }

    /**
     * List groups
     * Requires an administrator.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param builtin Include built-in groups (e.g., \&quot;Anonymous\&quot;, \&quot;Non member\&quot;) in the response. Set to &#x60;1&#x60; to include built-in groups. Without this parameter, only user-created groups are returned.
     * @return ResponseEntity&lt;GetGroups200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#GET">List groups Documentation</a>
     */
    public ResponseEntity<GetGroups200Response> getGroupsWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String builtin) throws RestClientResponseException {
        ParameterizedTypeReference<GetGroups200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGroupsRequestCreation(format, xRedmineSwitchUser, builtin).toEntity(localVarReturnType);
    }

    /**
     * List groups
     * Requires an administrator.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param builtin Include built-in groups (e.g., \&quot;Anonymous\&quot;, \&quot;Non member\&quot;) in the response. Set to &#x60;1&#x60; to include built-in groups. Without this parameter, only user-created groups are returned.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#GET">List groups Documentation</a>
     */
    public ResponseSpec getGroupsWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable String builtin) throws RestClientResponseException {
        return getGroupsRequestCreation(format, xRedmineSwitchUser, builtin);
    }

    /**
     * Remove user from group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#DELETE-2">Remove user from group Documentation</a>
     */
    private ResponseSpec removeUserFromGroupRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling removeUserFromGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling removeUserFromGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling removeUserFromGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("group_id", groupId);
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
        return apiClient.invokeAPI("/groups/{group_id}/users/{user_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove user from group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#DELETE-2">Remove user from group Documentation</a>
     */
    public void removeUserFromGroup(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        removeUserFromGroupRequestCreation(format, groupId, userId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Remove user from group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#DELETE-2">Remove user from group Documentation</a>
     */
    public ResponseEntity<Void> removeUserFromGroupWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return removeUserFromGroupRequestCreation(format, groupId, userId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Remove user from group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#DELETE-2">Remove user from group Documentation</a>
     */
    public ResponseSpec removeUserFromGroupWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return removeUserFromGroupRequestCreation(format, groupId, userId, xRedmineSwitchUser);
    }

    /**
     * Remove users from group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Group not found, or none of the given users are members of the group.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param userIds The userIds parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups">Remove users from group Documentation</a>
     */
    private ResponseSpec removeUsersFromGroupRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nonnull List<Integer> userIds, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling removeUsersFromGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling removeUsersFromGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userIds' is set
        if (userIds == null) {
            throw new RestClientResponseException("Missing the required parameter 'userIds' when calling removeUsersFromGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("group_id", groupId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "user_ids[]", userIds));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/groups/{group_id}/users.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove users from group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Group not found, or none of the given users are members of the group.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param userIds The userIds parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups">Remove users from group Documentation</a>
     */
    public void removeUsersFromGroup(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nonnull List<Integer> userIds, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        removeUsersFromGroupRequestCreation(format, groupId, userIds, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Remove users from group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Group not found, or none of the given users are members of the group.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param userIds The userIds parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups">Remove users from group Documentation</a>
     */
    public ResponseEntity<Void> removeUsersFromGroupWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nonnull List<Integer> userIds, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return removeUsersFromGroupRequestCreation(format, groupId, userIds, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Remove users from group
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - Group not found, or none of the given users are members of the group.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param userIds The userIds parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups">Remove users from group Documentation</a>
     */
    public ResponseSpec removeUsersFromGroupWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nonnull List<Integer> userIds, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return removeUsersFromGroupRequestCreation(format, groupId, userIds, xRedmineSwitchUser);
    }

    /**
     * Update group
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateGroupRequest The updateGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#PUT">Update group Documentation</a>
     */
    private ResponseSpec updateGroupRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateGroupRequest updateGroupRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling updateGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/groups/{group_id}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateGroupRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update group
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateGroupRequest The updateGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#PUT">Update group Documentation</a>
     */
    public void updateGroup(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateGroupRequest updateGroupRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateGroupRequestCreation(format, groupId, xRedmineSwitchUser, updateGroupRequest).body(localVarReturnType);
    }

    /**
     * Update group
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateGroupRequest The updateGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#PUT">Update group Documentation</a>
     */
    public ResponseEntity<Void> updateGroupWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateGroupRequest updateGroupRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateGroupRequestCreation(format, groupId, xRedmineSwitchUser, updateGroupRequest).toEntity(localVarReturnType);
    }

    /**
     * Update group
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateGroupRequest The updateGroupRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#PUT">Update group Documentation</a>
     */
    public ResponseSpec updateGroupWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateGroupRequest updateGroupRequest) throws RestClientResponseException {
        return updateGroupRequestCreation(format, groupId, xRedmineSwitchUser, updateGroupRequest);
    }

    /**
     * Update group (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateGroupRequest The updateGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#PUT">Update group (PATCH) Documentation</a>
     */
    private ResponseSpec updateGroupViaPatchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateGroupRequest updateGroupRequest) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateGroupViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling updateGroupViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/groups/{group_id}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateGroupRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update group (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateGroupRequest The updateGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#PUT">Update group (PATCH) Documentation</a>
     */
    public void updateGroupViaPatch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateGroupRequest updateGroupRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateGroupViaPatchRequestCreation(format, groupId, xRedmineSwitchUser, updateGroupRequest).body(localVarReturnType);
    }

    /**
     * Update group (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateGroupRequest The updateGroupRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#PUT">Update group (PATCH) Documentation</a>
     */
    public ResponseEntity<Void> updateGroupViaPatchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateGroupRequest updateGroupRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateGroupViaPatchRequestCreation(format, groupId, xRedmineSwitchUser, updateGroupRequest).toEntity(localVarReturnType);
    }

    /**
     * Update group (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param groupId The groupId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param updateGroupRequest The updateGroupRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Groups#PUT">Update group (PATCH) Documentation</a>
     */
    public ResponseSpec updateGroupViaPatchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer groupId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable UpdateGroupRequest updateGroupRequest) throws RestClientResponseException {
        return updateGroupViaPatchRequestCreation(format, groupId, xRedmineSwitchUser, updateGroupRequest);
    }
}
