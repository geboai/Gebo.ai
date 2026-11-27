package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.CreateUser201Response;
import ai.gebo.redmine.client.model.CreateUserRequest;
import ai.gebo.redmine.client.model.Errors;
import ai.gebo.redmine.client.model.FrameworkError;
import ai.gebo.redmine.client.model.GetUsers200Response;
import ai.gebo.redmine.client.model.UpdateUserRequest;

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
public class UsersApi {
    private ApiClient apiClient;

    public UsersApi() {
        this(new ApiClient());
    }

    public UsersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create user
     * Requires an administrator.
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createUserRequest The createUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateUser201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#POST">Create user Documentation</a>
     */
    private ResponseSpec createUserRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateUserRequest createUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling createUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createUserRequest' is set
        if (createUserRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createUserRequest' when calling createUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/users.{format}", HttpMethod.POST, pathParams, localVarQueryParams, createUserRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create user
     * Requires an administrator.
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createUserRequest The createUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return CreateUser201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#POST">Create user Documentation</a>
     */
    public CreateUser201Response createUser(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateUserRequest createUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createUserRequestCreation(format, createUserRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create user
     * Requires an administrator.
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createUserRequest The createUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;CreateUser201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#POST">Create user Documentation</a>
     */
    public ResponseEntity<CreateUser201Response> createUserWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateUserRequest createUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createUserRequestCreation(format, createUserRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create user
     * Requires an administrator.
     * <p><b>201</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - 
     * @param format The format parameter
     * @param createUserRequest The createUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#POST">Create user Documentation</a>
     */
    public ResponseSpec createUserWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateUserRequest createUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return createUserRequestCreation(format, createUserRequest, xRedmineSwitchUser);
    }

    /**
     * Delete user
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned without a body when the user tries to delete their own account and it is not deletable (e.g. the only active administrator).
     * @param format The format parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#DELETE">Delete user Documentation</a>
     */
    private ResponseSpec deleteUserRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling deleteUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
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
        return apiClient.invokeAPI("/users/{user_id}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete user
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned without a body when the user tries to delete their own account and it is not deletable (e.g. the only active administrator).
     * @param format The format parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#DELETE">Delete user Documentation</a>
     */
    public void deleteUser(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteUserRequestCreation(format, userId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Delete user
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned without a body when the user tries to delete their own account and it is not deletable (e.g. the only active administrator).
     * @param format The format parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#DELETE">Delete user Documentation</a>
     */
    public ResponseEntity<Void> deleteUserWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteUserRequestCreation(format, userId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Delete user
     * Requires an administrator.
     * <p><b>204</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned without a body when the user tries to delete their own account and it is not deletable (e.g. the only active administrator).
     * @param format The format parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#DELETE">Delete user Documentation</a>
     */
    public ResponseSpec deleteUserWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return deleteUserRequestCreation(format, userId, xRedmineSwitchUser);
    }

    /**
     * Show current user
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user), &#x60;groups&#x60; (groups the user belongs to), &#x60;auth_source&#x60; (authentication source information)
     * @return CreateUser201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET-2">Show current user Documentation</a>
     */
    private ResponseSpec getCurrentUserRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getCurrentUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

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

        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/users/current.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show current user
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user), &#x60;groups&#x60; (groups the user belongs to), &#x60;auth_source&#x60; (authentication source information)
     * @return CreateUser201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET-2">Show current user Documentation</a>
     */
    public CreateUser201Response getCurrentUser(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCurrentUserRequestCreation(format, xRedmineSwitchUser, include).body(localVarReturnType);
    }

    /**
     * Show current user
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user), &#x60;groups&#x60; (groups the user belongs to), &#x60;auth_source&#x60; (authentication source information)
     * @return ResponseEntity&lt;CreateUser201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET-2">Show current user Documentation</a>
     */
    public ResponseEntity<CreateUser201Response> getCurrentUserWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCurrentUserRequestCreation(format, xRedmineSwitchUser, include).toEntity(localVarReturnType);
    }

    /**
     * Show current user
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user), &#x60;groups&#x60; (groups the user belongs to), &#x60;auth_source&#x60; (authentication source information)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET-2">Show current user Documentation</a>
     */
    public ResponseSpec getCurrentUserWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        return getCurrentUserRequestCreation(format, xRedmineSwitchUser, include);
    }

    /**
     * Show user
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user), &#x60;groups&#x60; (groups the user belongs to), &#x60;auth_source&#x60; (authentication source information)
     * @return CreateUser201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET-2">Show user Documentation</a>
     */
    private ResponseSpec getUserRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/users/{user_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show user
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user), &#x60;groups&#x60; (groups the user belongs to), &#x60;auth_source&#x60; (authentication source information)
     * @return CreateUser201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET-2">Show user Documentation</a>
     */
    public CreateUser201Response getUser(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserRequestCreation(format, userId, xRedmineSwitchUser, include).body(localVarReturnType);
    }

    /**
     * Show user
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user), &#x60;groups&#x60; (groups the user belongs to), &#x60;auth_source&#x60; (authentication source information)
     * @return ResponseEntity&lt;CreateUser201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET-2">Show user Documentation</a>
     */
    public ResponseEntity<CreateUser201Response> getUserWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<CreateUser201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserRequestCreation(format, userId, xRedmineSwitchUser, include).toEntity(localVarReturnType);
    }

    /**
     * Show user
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;memberships&#x60; (project memberships with roles, limited to projects visible to the current user), &#x60;groups&#x60; (groups the user belongs to), &#x60;auth_source&#x60; (authentication source information)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET-2">Show user Documentation</a>
     */
    public ResponseSpec getUserWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        return getUserRequestCreation(format, userId, xRedmineSwitchUser, include);
    }

    /**
     * List users
     * Lists visible users. The default status filter selects active users. A saved query or explicit filters can select other statuses. Requires an administrator.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The saved query was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param include Comma-separated list of associated data to include for each user. Values: &#x60;auth_source&#x60; (authentication source information)
     * @param status Filter by user status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values: &#x60;1&#x60; (active), &#x60;2&#x60; (registered), &#x60;3&#x60; (locked) Examples: &#x60;1&#x60;, &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param name Filter by name, login, or email (contains search). The API always uses the &#x60;~&#x60; (contains) operator regardless of any operator prefix. Examples: &#x60;john&#x60;, &#x60;admin&#x60;
     * @param groupId Filter by group membership (legacy parameter). Internally converted to &#x60;is_member_of_group&#x60; filter with &#x60;&#x3D;&#x60; operator. Value is the group ID.
     * @param authSourceId Filter by authentication source. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param twofaScheme Filter by two-factor authentication scheme. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Only available when two-factor authentication is enabled in Redmine settings.
     * @param login Filter by login. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~admin&#x60;, &#x60;^john&#x60;
     * @param firstname Filter by first name. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~John&#x60;, &#x60;&#x3D;John&#x60;
     * @param lastname Filter by last name. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~Doe&#x60;, &#x60;&#x3D;Doe&#x60;
     * @param mail Filter by email address. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~example.com&#x60;, &#x60;$@example.com&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;t-30&#x60;, &#x60;lm&#x60;
     * @param lastLoginOn Filter by last login date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;t-30&#x60;, &#x60;!*&#x60;
     * @param admin Filter by whether the user is an administrator. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (admin), &#x60;0&#x60; (non-admin) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param isMemberOfGroup Filter by group membership. Supports &#x60;&#x3D;&#x60; (default), &#x60;!&#x60;, &#x60;*&#x60; and &#x60;!*&#x60;; separate group IDs with pipes. Unlike the legacy &#x60;group_id&#x60; shortcut, this filter accepts operators.
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Available fields: &#x60;login&#x60;, &#x60;firstname&#x60;, &#x60;lastname&#x60;, &#x60;mail&#x60;, &#x60;admin&#x60;, &#x60;created_on&#x60;, &#x60;updated_on&#x60;, &#x60;last_login_on&#x60;, &#x60;passwd_changed_on&#x60;, &#x60;status&#x60;, &#x60;auth_source.name&#x60;. Sortable custom-field columns use &#x60;cf_&lt;id&gt;&#x60;. The &#x60;twofa_scheme&#x60; field is available when two-factor authentication is enabled. Example: &#x60;login:desc&#x60;.
     * @return GetUsers200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET">List users Documentation</a>
     */
    private ResponseSpec getUsersRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String groupId, @jakarta.annotation.Nullable String authSourceId, @jakarta.annotation.Nullable String twofaScheme, @jakarta.annotation.Nullable String login, @jakarta.annotation.Nullable String firstname, @jakarta.annotation.Nullable String lastname, @jakarta.annotation.Nullable String mail, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String lastLoginOn, @jakarta.annotation.Nullable String admin, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String isMemberOfGroup, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "group_id", groupId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "auth_source_id", authSourceId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "twofa_scheme", twofaScheme));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "login", login));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "firstname", firstname));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "lastname", lastname));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "mail", mail));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_on", createdOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "last_login_on", lastLoginOn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "admin", admin));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query_id", queryId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "f[]", f));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "op", op));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "v", v));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cf_x", cfX));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "is_member_of_group", isMemberOfGroup));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

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

        ParameterizedTypeReference<GetUsers200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/users.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List users
     * Lists visible users. The default status filter selects active users. A saved query or explicit filters can select other statuses. Requires an administrator.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The saved query was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param include Comma-separated list of associated data to include for each user. Values: &#x60;auth_source&#x60; (authentication source information)
     * @param status Filter by user status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values: &#x60;1&#x60; (active), &#x60;2&#x60; (registered), &#x60;3&#x60; (locked) Examples: &#x60;1&#x60;, &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param name Filter by name, login, or email (contains search). The API always uses the &#x60;~&#x60; (contains) operator regardless of any operator prefix. Examples: &#x60;john&#x60;, &#x60;admin&#x60;
     * @param groupId Filter by group membership (legacy parameter). Internally converted to &#x60;is_member_of_group&#x60; filter with &#x60;&#x3D;&#x60; operator. Value is the group ID.
     * @param authSourceId Filter by authentication source. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param twofaScheme Filter by two-factor authentication scheme. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Only available when two-factor authentication is enabled in Redmine settings.
     * @param login Filter by login. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~admin&#x60;, &#x60;^john&#x60;
     * @param firstname Filter by first name. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~John&#x60;, &#x60;&#x3D;John&#x60;
     * @param lastname Filter by last name. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~Doe&#x60;, &#x60;&#x3D;Doe&#x60;
     * @param mail Filter by email address. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~example.com&#x60;, &#x60;$@example.com&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;t-30&#x60;, &#x60;lm&#x60;
     * @param lastLoginOn Filter by last login date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;t-30&#x60;, &#x60;!*&#x60;
     * @param admin Filter by whether the user is an administrator. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (admin), &#x60;0&#x60; (non-admin) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param isMemberOfGroup Filter by group membership. Supports &#x60;&#x3D;&#x60; (default), &#x60;!&#x60;, &#x60;*&#x60; and &#x60;!*&#x60;; separate group IDs with pipes. Unlike the legacy &#x60;group_id&#x60; shortcut, this filter accepts operators.
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Available fields: &#x60;login&#x60;, &#x60;firstname&#x60;, &#x60;lastname&#x60;, &#x60;mail&#x60;, &#x60;admin&#x60;, &#x60;created_on&#x60;, &#x60;updated_on&#x60;, &#x60;last_login_on&#x60;, &#x60;passwd_changed_on&#x60;, &#x60;status&#x60;, &#x60;auth_source.name&#x60;. Sortable custom-field columns use &#x60;cf_&lt;id&gt;&#x60;. The &#x60;twofa_scheme&#x60; field is available when two-factor authentication is enabled. Example: &#x60;login:desc&#x60;.
     * @return GetUsers200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET">List users Documentation</a>
     */
    public GetUsers200Response getUsers(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String groupId, @jakarta.annotation.Nullable String authSourceId, @jakarta.annotation.Nullable String twofaScheme, @jakarta.annotation.Nullable String login, @jakarta.annotation.Nullable String firstname, @jakarta.annotation.Nullable String lastname, @jakarta.annotation.Nullable String mail, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String lastLoginOn, @jakarta.annotation.Nullable String admin, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String isMemberOfGroup, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<GetUsers200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUsersRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, include, status, name, groupId, authSourceId, twofaScheme, login, firstname, lastname, mail, createdOn, lastLoginOn, admin, page, queryId, f, op, v, cfX, isMemberOfGroup, sort).body(localVarReturnType);
    }

    /**
     * List users
     * Lists visible users. The default status filter selects active users. A saved query or explicit filters can select other statuses. Requires an administrator.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The saved query was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param include Comma-separated list of associated data to include for each user. Values: &#x60;auth_source&#x60; (authentication source information)
     * @param status Filter by user status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values: &#x60;1&#x60; (active), &#x60;2&#x60; (registered), &#x60;3&#x60; (locked) Examples: &#x60;1&#x60;, &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param name Filter by name, login, or email (contains search). The API always uses the &#x60;~&#x60; (contains) operator regardless of any operator prefix. Examples: &#x60;john&#x60;, &#x60;admin&#x60;
     * @param groupId Filter by group membership (legacy parameter). Internally converted to &#x60;is_member_of_group&#x60; filter with &#x60;&#x3D;&#x60; operator. Value is the group ID.
     * @param authSourceId Filter by authentication source. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param twofaScheme Filter by two-factor authentication scheme. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Only available when two-factor authentication is enabled in Redmine settings.
     * @param login Filter by login. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~admin&#x60;, &#x60;^john&#x60;
     * @param firstname Filter by first name. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~John&#x60;, &#x60;&#x3D;John&#x60;
     * @param lastname Filter by last name. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~Doe&#x60;, &#x60;&#x3D;Doe&#x60;
     * @param mail Filter by email address. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~example.com&#x60;, &#x60;$@example.com&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;t-30&#x60;, &#x60;lm&#x60;
     * @param lastLoginOn Filter by last login date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;t-30&#x60;, &#x60;!*&#x60;
     * @param admin Filter by whether the user is an administrator. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (admin), &#x60;0&#x60; (non-admin) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param isMemberOfGroup Filter by group membership. Supports &#x60;&#x3D;&#x60; (default), &#x60;!&#x60;, &#x60;*&#x60; and &#x60;!*&#x60;; separate group IDs with pipes. Unlike the legacy &#x60;group_id&#x60; shortcut, this filter accepts operators.
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Available fields: &#x60;login&#x60;, &#x60;firstname&#x60;, &#x60;lastname&#x60;, &#x60;mail&#x60;, &#x60;admin&#x60;, &#x60;created_on&#x60;, &#x60;updated_on&#x60;, &#x60;last_login_on&#x60;, &#x60;passwd_changed_on&#x60;, &#x60;status&#x60;, &#x60;auth_source.name&#x60;. Sortable custom-field columns use &#x60;cf_&lt;id&gt;&#x60;. The &#x60;twofa_scheme&#x60; field is available when two-factor authentication is enabled. Example: &#x60;login:desc&#x60;.
     * @return ResponseEntity&lt;GetUsers200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET">List users Documentation</a>
     */
    public ResponseEntity<GetUsers200Response> getUsersWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String groupId, @jakarta.annotation.Nullable String authSourceId, @jakarta.annotation.Nullable String twofaScheme, @jakarta.annotation.Nullable String login, @jakarta.annotation.Nullable String firstname, @jakarta.annotation.Nullable String lastname, @jakarta.annotation.Nullable String mail, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String lastLoginOn, @jakarta.annotation.Nullable String admin, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String isMemberOfGroup, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<GetUsers200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUsersRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, include, status, name, groupId, authSourceId, twofaScheme, login, firstname, lastname, mail, createdOn, lastLoginOn, admin, page, queryId, f, op, v, cfX, isMemberOfGroup, sort).toEntity(localVarReturnType);
    }

    /**
     * List users
     * Lists visible users. The default status filter selects active users. A saved query or explicit filters can select other statuses. Requires an administrator.
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The saved query was not found.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - Returned when a filter parameter has an invalid value.
     * @param format The format parameter
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param include Comma-separated list of associated data to include for each user. Values: &#x60;auth_source&#x60; (authentication source information)
     * @param status Filter by user status. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Values: &#x60;1&#x60; (active), &#x60;2&#x60; (registered), &#x60;3&#x60; (locked) Examples: &#x60;1&#x60;, &#x60;1|2&#x60;, &#x60;!3&#x60;
     * @param name Filter by name, login, or email (contains search). The API always uses the &#x60;~&#x60; (contains) operator regardless of any operator prefix. Examples: &#x60;john&#x60;, &#x60;admin&#x60;
     * @param groupId Filter by group membership (legacy parameter). Internally converted to &#x60;is_member_of_group&#x60; filter with &#x60;&#x3D;&#x60; operator. Value is the group ID.
     * @param authSourceId Filter by authentication source. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param twofaScheme Filter by two-factor authentication scheme. Format: &#x60;[operator]value[|value2|...]&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Only available when two-factor authentication is enabled in Redmine settings.
     * @param login Filter by login. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~admin&#x60;, &#x60;^john&#x60;
     * @param firstname Filter by first name. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~John&#x60;, &#x60;&#x3D;John&#x60;
     * @param lastname Filter by last name. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~Doe&#x60;, &#x60;&#x3D;Doe&#x60;
     * @param mail Filter by email address. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (equality, default), &#x60;~&#x60; (contains), &#x60;*~&#x60; (contains any word), &#x60;!~&#x60; (does not contain), &#x60;!&#x60; (not equal), &#x60;^&#x60; (starts with), &#x60;$&#x60; (ends with), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) Examples: &#x60;~example.com&#x60;, &#x60;$@example.com&#x60;
     * @param createdOn Filter by creation date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;t-30&#x60;, &#x60;lm&#x60;
     * @param lastLoginOn Filter by last login date or timestamp. Format: &#x60;[operator]value&#x60; or a standalone operator such as &#x60;t&#x60; Operators: &#x60;&#x3D;&#x60; (on a date or timestamp, default), &#x60;&gt;&#x3D;&#x60; (on or after), &#x60;&lt;&#x3D;&#x60; (on or before), &#x60;&gt;&lt;&#x60; (between, pipe-separated), &#x60;&gt;t-&#x60; (on or after n days ago), &#x60;&lt;t-&#x60; (on or before n days ago), &#x60;&gt;&lt;t-&#x60; (from n days ago through today), &#x60;t-&#x60; (n days ago), &#x60;t&#x60; (today), &#x60;ld&#x60; (yesterday), &#x60;w&#x60; (this week), &#x60;lw&#x60; (last week), &#x60;l2w&#x60; (last 2 weeks), &#x60;m&#x60; (this month), &#x60;lm&#x60; (last month), &#x60;y&#x60; (this year), &#x60;*&#x60; (any), &#x60;!*&#x60; (none) A timestamp lower bound can also match values less than one second before the specified time. Examples: &#x60;&gt;&#x3D;2024-01-01&#x60;, &#x60;&gt;&#x3D;2024-01-01T08:00:00Z&#x60;, &#x60;&gt;t-30&#x60;, &#x60;!*&#x60;
     * @param admin Filter by whether the user is an administrator. Format: &#x60;[operator]value&#x60; Operators: &#x60;&#x3D;&#x60; (default), &#x60;!&#x60; (not equal) Values: &#x60;1&#x60; (admin), &#x60;0&#x60; (non-admin) Examples: &#x60;1&#x60;, &#x60;!1&#x60;
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param queryId ID of a visible saved query for this list type. Redmine applies the saved query&#39;s filters and sort order. Other filter parameters, including &#x60;f[]&#x60;, are ignored; &#x60;sort&#x60; can override the saved sort order.
     * @param f Explicit filter field names. Without &#x60;query_id&#x60;, these replace the default filters. Pair each field with &#x60;op[field]&#x60; and, where needed, repeated &#x60;v[field][]&#x60; values. Available fields depend on permissions, project context and enabled custom fields.
     * @param op Operator for each field in &#x60;f[]&#x60;, such as &#x60;op[status_id]&#x3D;o&#x60;. Each field supports the same operators as its corresponding shortcut filter.
     * @param v Values for each field in &#x60;f[]&#x60;. Encode each value as &#x60;v[field][]&#x3D;value&#x60;; for example, send &#x60;op[project_id]&#x3D;%3D&amp;v[project_id][]&#x3D;1&amp;v[project_id][]&#x3D;2&#x60; to filter for records in projects 1 and 2. OpenAPI 3.0 does not standardize nested array serialization in deep objects; configure the client serializer to send these repeated bracketed keys.
     * @param cfX Custom-field filters, using actual field names as keys. Use &#x60;cf_12&#x60; for custom field 12 on the listed records. Issue lists also support &#x60;project.cf_12&#x60;, &#x60;author.cf_12&#x60;, &#x60;assigned_to.cf_12&#x60; and &#x60;fixed_version.cf_12&#x60;; time-entry lists support &#x60;project.cf_12&#x60;, &#x60;user.cf_12&#x60; and &#x60;issue.cf_12&#x60;. Reference fields can expose chained names such as &#x60;cf_12.cf_34&#x60;, or version attributes &#x60;cf_12.due_date&#x60; and &#x60;cf_12.status&#x60;. Only registered, visible fields enabled as filters are available. Operators and values depend on the field type. Use explicit &#x60;f[]&#x60;, &#x60;op&#x60; and &#x60;v&#x60; for repeated values without shortcut parsing.
     * @param isMemberOfGroup Filter by group membership. Supports &#x60;&#x3D;&#x60; (default), &#x60;!&#x60;, &#x60;*&#x60; and &#x60;!*&#x60;; separate group IDs with pipes. Unlike the legacy &#x60;group_id&#x60; shortcut, this filter accepts operators.
     * @param sort Sort order: up to three comma-separated criteria of &#x60;field&#x60;, &#x60;field:asc&#x60;, or &#x60;field:desc&#x60;. The default direction is ascending. Available fields: &#x60;login&#x60;, &#x60;firstname&#x60;, &#x60;lastname&#x60;, &#x60;mail&#x60;, &#x60;admin&#x60;, &#x60;created_on&#x60;, &#x60;updated_on&#x60;, &#x60;last_login_on&#x60;, &#x60;passwd_changed_on&#x60;, &#x60;status&#x60;, &#x60;auth_source.name&#x60;. Sortable custom-field columns use &#x60;cf_&lt;id&gt;&#x60;. The &#x60;twofa_scheme&#x60; field is available when two-factor authentication is enabled. Example: &#x60;login:desc&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#GET">List users Documentation</a>
     */
    public ResponseSpec getUsersWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String groupId, @jakarta.annotation.Nullable String authSourceId, @jakarta.annotation.Nullable String twofaScheme, @jakarta.annotation.Nullable String login, @jakarta.annotation.Nullable String firstname, @jakarta.annotation.Nullable String lastname, @jakarta.annotation.Nullable String mail, @jakarta.annotation.Nullable String createdOn, @jakarta.annotation.Nullable String lastLoginOn, @jakarta.annotation.Nullable String admin, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer queryId, @jakarta.annotation.Nullable List<String> f, @jakarta.annotation.Nullable Map<String, String> op, @jakarta.annotation.Nullable Map<String, List<String>> v, @jakarta.annotation.Nullable Map<String, String> cfX, @jakarta.annotation.Nullable String isMemberOfGroup, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getUsersRequestCreation(format, offset, limit, nometa, xRedmineSwitchUser, xRedmineNometa, include, status, name, groupId, authSourceId, twofaScheme, login, firstname, lastname, mail, createdOn, lastLoginOn, admin, page, queryId, f, op, v, cfX, isMemberOfGroup, sort);
    }

    /**
     * Update user
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param updateUserRequest The updateUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#PUT">Update user Documentation</a>
     */
    private ResponseSpec updateUserRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull UpdateUserRequest updateUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling updateUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'updateUserRequest' is set
        if (updateUserRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'updateUserRequest' when calling updateUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/users/{user_id}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateUserRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update user
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param updateUserRequest The updateUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#PUT">Update user Documentation</a>
     */
    public void updateUser(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull UpdateUserRequest updateUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateUserRequestCreation(format, userId, updateUserRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Update user
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param updateUserRequest The updateUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#PUT">Update user Documentation</a>
     */
    public ResponseEntity<Void> updateUserWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull UpdateUserRequest updateUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateUserRequestCreation(format, userId, updateUserRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Update user
     * Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param updateUserRequest The updateUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#PUT">Update user Documentation</a>
     */
    public ResponseSpec updateUserWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull UpdateUserRequest updateUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return updateUserRequestCreation(format, userId, updateUserRequest, xRedmineSwitchUser);
    }

    /**
     * Update user (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param updateUserRequest The updateUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#PUT">Update user (PATCH) Documentation</a>
     */
    private ResponseSpec updateUserViaPatchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull UpdateUserRequest updateUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateUserViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling updateUserViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'updateUserRequest' is set
        if (updateUserRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'updateUserRequest' when calling updateUserViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/users/{user_id}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateUserRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update user (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param updateUserRequest The updateUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#PUT">Update user (PATCH) Documentation</a>
     */
    public void updateUserViaPatch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull UpdateUserRequest updateUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        updateUserViaPatchRequestCreation(format, userId, updateUserRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Update user (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param updateUserRequest The updateUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#PUT">Update user (PATCH) Documentation</a>
     */
    public ResponseEntity<Void> updateUserViaPatchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull UpdateUserRequest updateUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateUserViaPatchRequestCreation(format, userId, updateUserRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Update user (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior. Requires an administrator.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param userId The userId parameter
     * @param updateUserRequest The updateUserRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Users#PUT">Update user (PATCH) Documentation</a>
     */
    public ResponseSpec updateUserViaPatchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull UpdateUserRequest updateUserRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return updateUserViaPatchRequestCreation(format, userId, updateUserRequest, xRedmineSwitchUser);
    }
}
