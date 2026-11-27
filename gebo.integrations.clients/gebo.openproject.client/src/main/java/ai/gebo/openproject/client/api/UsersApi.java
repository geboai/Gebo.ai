package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.UserCollectionModel;
import ai.gebo.openproject.client.model.UserCreateModel;
import ai.gebo.openproject.client.model.UserModel;

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
     * Create User
     * Creates a new user. Only administrators and users with manage_user global permission are allowed to do so. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.  Valid values for &#x60;status&#x60;:  1) \&quot;active\&quot; - In this case a password has to be provided in addition to the other attributes.  2) \&quot;invited\&quot; - In this case nothing but the email address is required. The rest is optional. An invitation will be sent to the user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param userCreateModel The userCreateModel parameter
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createUserRequestCreation(@jakarta.annotation.Nullable UserCreateModel userCreateModel) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users", HttpMethod.POST, pathParams, localVarQueryParams, userCreateModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create User
     * Creates a new user. Only administrators and users with manage_user global permission are allowed to do so. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.  Valid values for &#x60;status&#x60;:  1) \&quot;active\&quot; - In this case a password has to be provided in addition to the other attributes.  2) \&quot;invited\&quot; - In this case nothing but the email address is required. The rest is optional. An invitation will be sent to the user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param userCreateModel The userCreateModel parameter
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserModel createUser(@jakarta.annotation.Nullable UserCreateModel userCreateModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createUserRequestCreation(userCreateModel).body(localVarReturnType);
    }

    /**
     * Create User
     * Creates a new user. Only administrators and users with manage_user global permission are allowed to do so. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.  Valid values for &#x60;status&#x60;:  1) \&quot;active\&quot; - In this case a password has to be provided in addition to the other attributes.  2) \&quot;invited\&quot; - In this case nothing but the email address is required. The rest is optional. An invitation will be sent to the user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param userCreateModel The userCreateModel parameter
     * @return ResponseEntity&lt;UserModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserModel> createUserWithHttpInfo(@jakarta.annotation.Nullable UserCreateModel userCreateModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createUserRequestCreation(userCreateModel).toEntity(localVarReturnType);
    }

    /**
     * Create User
     * Creates a new user. Only administrators and users with manage_user global permission are allowed to do so. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.  Valid values for &#x60;status&#x60;:  1) \&quot;active\&quot; - In this case a password has to be provided in addition to the other attributes.  2) \&quot;invited\&quot; - In this case nothing but the email address is required. The rest is optional. An invitation will be sent to the user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param userCreateModel The userCreateModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createUserWithResponseSpec(@jakarta.annotation.Nullable UserCreateModel userCreateModel) throws RestClientResponseException {
        return createUserRequestCreation(userCreateModel);
    }

    /**
     * Delete user
     * Permanently deletes the specified user account.
     * <p><b>202</b> - Returned if the account was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions or if deletion of users was disabled in the instance wide settings.  **Required permission:** Administrators only (exception: users might be able to delete their own accounts)
     * <p><b>404</b> - Returned if the user does not exist.
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteUserRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete user
     * Permanently deletes the specified user account.
     * <p><b>202</b> - Returned if the account was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions or if deletion of users was disabled in the instance wide settings.  **Required permission:** Administrators only (exception: users might be able to delete their own accounts)
     * <p><b>404</b> - Returned if the user does not exist.
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteUser(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteUserRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete user
     * Permanently deletes the specified user account.
     * <p><b>202</b> - Returned if the account was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions or if deletion of users was disabled in the instance wide settings.  **Required permission:** Administrators only (exception: users might be able to delete their own accounts)
     * <p><b>404</b> - Returned if the user does not exist.
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteUserRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete user
     * Permanently deletes the specified user account.
     * <p><b>202</b> - Returned if the account was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions or if deletion of users was disabled in the instance wide settings.  **Required permission:** Administrators only (exception: users might be able to delete their own accounts)
     * <p><b>404</b> - Returned if the user does not exist.
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteUserWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return deleteUserRequestCreation(id);
    }

    /**
     * View user
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the user does not exist or if the API user does not have permission to view them.  **Required permission** The user needs to be locked in if the installation is configured to prevent anonymous access
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getUserRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View user
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the user does not exist or if the API user does not have permission to view them.  **Required permission** The user needs to be locked in if the installation is configured to prevent anonymous access
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserModel getUser(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View user
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the user does not exist or if the API user does not have permission to view them.  **Required permission** The user needs to be locked in if the installation is configured to prevent anonymous access
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @return ResponseEntity&lt;UserModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserModel> getUserWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View user
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the user does not exist or if the API user does not have permission to view them.  **Required permission** The user needs to be locked in if the installation is configured to prevent anonymous access
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getUserWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getUserRequestCreation(id);
    }

    /**
     * View user schema
     * The schema response use two exemplary custom fields that extend the schema response. Depending on your instance and custom field configuration, the response will look somewhat different.
     * <p><b>200</b> - OK
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getUserSchemaRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View user schema
     * The schema response use two exemplary custom fields that extend the schema response. Depending on your instance and custom field configuration, the response will look somewhat different.
     * <p><b>200</b> - OK
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getUserSchema() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserSchemaRequestCreation().body(localVarReturnType);
    }

    /**
     * View user schema
     * The schema response use two exemplary custom fields that extend the schema response. Depending on your instance and custom field configuration, the response will look somewhat different.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getUserSchemaWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserSchemaRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * View user schema
     * The schema response use two exemplary custom fields that extend the schema response. Depending on your instance and custom field configuration, the response will look somewhat different.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getUserSchemaWithResponseSpec() throws RestClientResponseException {
        return getUserSchemaRequestCreation();
    }

    /**
     * List Users
     * Lists users. Only administrators or users with the following global permission can access this resource: - &#x60;manage_user&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator or any of: &#39;manage_members&#39;, &#39;manage_user&#39;, &#39;share_work_packages&#39;.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + status: Status the user has  + group: Name of the group in which to-be-listed users are members.  + name: Filter users in whose first or last names, or email addresses the given string occurs.  + login: User&#39;s login
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param select Comma separated list of properties to include.
     * @return UserCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listUsersRequestCreation(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "select", select));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<UserCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List Users
     * Lists users. Only administrators or users with the following global permission can access this resource: - &#x60;manage_user&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator or any of: &#39;manage_members&#39;, &#39;manage_user&#39;, &#39;share_work_packages&#39;.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + status: Status the user has  + group: Name of the group in which to-be-listed users are members.  + name: Filter users in whose first or last names, or email addresses the given string occurs.  + login: User&#39;s login
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param select Comma separated list of properties to include.
     * @return UserCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserCollectionModel listUsers(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<UserCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listUsersRequestCreation(offset, pageSize, filters, sortBy, select).body(localVarReturnType);
    }

    /**
     * List Users
     * Lists users. Only administrators or users with the following global permission can access this resource: - &#x60;manage_user&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator or any of: &#39;manage_members&#39;, &#39;manage_user&#39;, &#39;share_work_packages&#39;.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + status: Status the user has  + group: Name of the group in which to-be-listed users are members.  + name: Filter users in whose first or last names, or email addresses the given string occurs.  + login: User&#39;s login
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param select Comma separated list of properties to include.
     * @return ResponseEntity&lt;UserCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserCollectionModel> listUsersWithHttpInfo(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<UserCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listUsersRequestCreation(offset, pageSize, filters, sortBy, select).toEntity(localVarReturnType);
    }

    /**
     * List Users
     * Lists users. Only administrators or users with the following global permission can access this resource: - &#x60;manage_user&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator or any of: &#39;manage_members&#39;, &#39;manage_user&#39;, &#39;share_work_packages&#39;.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + status: Status the user has  + group: Name of the group in which to-be-listed users are members.  + name: Filter users in whose first or last names, or email addresses the given string occurs.  + login: User&#39;s login
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param select Comma separated list of properties to include.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listUsersWithResponseSpec(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return listUsersRequestCreation(offset, pageSize, filters, sortBy, select);
    }

    /**
     * Lock user
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client tries to lock a user account whose current status does not allow this transition.  **Required permission:** Administrators only
     * <p><b>403</b> - Returned if the client does not have sufficient permissions for locking a user.  **Required permission:** Administrators only
     * <p><b>404</b> - Returned if the user does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec lockUserRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling lockUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/lock", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Lock user
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client tries to lock a user account whose current status does not allow this transition.  **Required permission:** Administrators only
     * <p><b>403</b> - Returned if the client does not have sufficient permissions for locking a user.  **Required permission:** Administrators only
     * <p><b>404</b> - Returned if the user does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserModel lockUser(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return lockUserRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Lock user
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client tries to lock a user account whose current status does not allow this transition.  **Required permission:** Administrators only
     * <p><b>403</b> - Returned if the client does not have sufficient permissions for locking a user.  **Required permission:** Administrators only
     * <p><b>404</b> - Returned if the user does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id
     * @return ResponseEntity&lt;UserModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserModel> lockUserWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return lockUserRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Lock user
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client tries to lock a user account whose current status does not allow this transition.  **Required permission:** Administrators only
     * <p><b>403</b> - Returned if the client does not have sufficient permissions for locking a user.  **Required permission:** Administrators only
     * <p><b>404</b> - Returned if the user does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec lockUserWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return lockUserRequestCreation(id);
    }

    /**
     * Unlock user
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client tries to unlock a user account whose current status does not allow this transition.  **Required permission:** Administrators only
     * <p><b>403</b> - Returned if the client does not have sufficient permissions for unlocking a user.  **Required permission:** Administrators only
     * <p><b>404</b> - Returned if the user does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unlockUserRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling unlockUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/lock", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unlock user
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client tries to unlock a user account whose current status does not allow this transition.  **Required permission:** Administrators only
     * <p><b>403</b> - Returned if the client does not have sufficient permissions for unlocking a user.  **Required permission:** Administrators only
     * <p><b>404</b> - Returned if the user does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserModel unlockUser(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unlockUserRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unlock user
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client tries to unlock a user account whose current status does not allow this transition.  **Required permission:** Administrators only
     * <p><b>403</b> - Returned if the client does not have sufficient permissions for unlocking a user.  **Required permission:** Administrators only
     * <p><b>404</b> - Returned if the user does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id
     * @return ResponseEntity&lt;UserModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserModel> unlockUserWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unlockUserRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unlock user
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client tries to unlock a user account whose current status does not allow this transition.  **Required permission:** Administrators only
     * <p><b>403</b> - Returned if the client does not have sufficient permissions for unlocking a user.  **Required permission:** Administrators only
     * <p><b>404</b> - Returned if the user does not exist.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec unlockUserWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return unlockUserRequestCreation(id);
    }

    /**
     * Update user
     * Updates the user&#39;s writable attributes. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.  Password updates for self-service account changes require both &#x60;password&#x60; and &#x60;currentPassword&#x60;.  Email updates for self-service account changes require &#x60;currentPassword&#x60; when password confirmation is enabled (the default). After an email change, all sessions, remember-me tokens, password reset links, and invitation links of the user become invalid.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators, manage_user global permission
     * <p><b>404</b> - Returned if the user does not exist or if the API user does not have the necessary permissions to update it.  **Required permission:** Administrators only (exception: users may update their own accounts)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @param userCreateModel The userCreateModel parameter
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateUserRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserCreateModel userCreateModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, userCreateModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update user
     * Updates the user&#39;s writable attributes. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.  Password updates for self-service account changes require both &#x60;password&#x60; and &#x60;currentPassword&#x60;.  Email updates for self-service account changes require &#x60;currentPassword&#x60; when password confirmation is enabled (the default). After an email change, all sessions, remember-me tokens, password reset links, and invitation links of the user become invalid.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators, manage_user global permission
     * <p><b>404</b> - Returned if the user does not exist or if the API user does not have the necessary permissions to update it.  **Required permission:** Administrators only (exception: users may update their own accounts)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @param userCreateModel The userCreateModel parameter
     * @return UserModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserModel updateUser(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserCreateModel userCreateModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateUserRequestCreation(id, userCreateModel).body(localVarReturnType);
    }

    /**
     * Update user
     * Updates the user&#39;s writable attributes. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.  Password updates for self-service account changes require both &#x60;password&#x60; and &#x60;currentPassword&#x60;.  Email updates for self-service account changes require &#x60;currentPassword&#x60; when password confirmation is enabled (the default). After an email change, all sessions, remember-me tokens, password reset links, and invitation links of the user become invalid.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators, manage_user global permission
     * <p><b>404</b> - Returned if the user does not exist or if the API user does not have the necessary permissions to update it.  **Required permission:** Administrators only (exception: users may update their own accounts)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @param userCreateModel The userCreateModel parameter
     * @return ResponseEntity&lt;UserModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserModel> updateUserWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserCreateModel userCreateModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateUserRequestCreation(id, userCreateModel).toEntity(localVarReturnType);
    }

    /**
     * Update user
     * Updates the user&#39;s writable attributes. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.  Password updates for self-service account changes require both &#x60;password&#x60; and &#x60;currentPassword&#x60;.  Email updates for self-service account changes require &#x60;currentPassword&#x60; when password confirmation is enabled (the default). After an email change, all sessions, remember-me tokens, password reset links, and invitation links of the user become invalid.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators, manage_user global permission
     * <p><b>404</b> - Returned if the user does not exist or if the API user does not have the necessary permissions to update it.  **Required permission:** Administrators only (exception: users may update their own accounts)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @param userCreateModel The userCreateModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateUserWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserCreateModel userCreateModel) throws RestClientResponseException {
        return updateUserRequestCreation(id, userCreateModel);
    }

    /**
     * User update form
     * Validates a user update payload.  For self password changes, provide both &#x60;password&#x60; and &#x60;currentPassword&#x60;. For self email changes, provide &#x60;currentPassword&#x60; when password confirmation is enabled (the default).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage_user global permission
     * <p><b>404</b> - Returned if the request user can not be found.  *Note: A client without sufficient permissions shall not be able to test for the existence of a membership. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateUserUpdateFormRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateUserUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * User update form
     * Validates a user update payload.  For self password changes, provide both &#x60;password&#x60; and &#x60;currentPassword&#x60;. For self email changes, provide &#x60;currentPassword&#x60; when password confirmation is enabled (the default).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage_user global permission
     * <p><b>404</b> - Returned if the request user can not be found.  *Note: A client without sufficient permissions shall not be able to test for the existence of a membership. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateUserUpdateForm(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateUserUpdateFormRequestCreation(id).body(localVarReturnType);
    }

    /**
     * User update form
     * Validates a user update payload.  For self password changes, provide both &#x60;password&#x60; and &#x60;currentPassword&#x60;. For self email changes, provide &#x60;currentPassword&#x60; when password confirmation is enabled (the default).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage_user global permission
     * <p><b>404</b> - Returned if the request user can not be found.  *Note: A client without sufficient permissions shall not be able to test for the existence of a membership. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateUserUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateUserUpdateFormRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * User update form
     * Validates a user update payload.  For self password changes, provide both &#x60;password&#x60; and &#x60;currentPassword&#x60;. For self email changes, provide &#x60;currentPassword&#x60; when password confirmation is enabled (the default).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage_user global permission
     * <p><b>404</b> - Returned if the request user can not be found.  *Note: A client without sufficient permissions shall not be able to test for the existence of a membership. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id User id. Use &#x60;me&#x60; to reference current user, if any.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateUserUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return validateUserUpdateFormRequestCreation(id);
    }
}
