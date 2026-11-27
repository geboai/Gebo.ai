package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiRunnerRegistrationDetails;
import ai.gebo.gitlab.client.model.APIEntitiesEmail;
import ai.gebo.gitlab.client.model.APIEntitiesMembership;
import ai.gebo.gitlab.client.model.APIEntitiesUser;
import ai.gebo.gitlab.client.model.APIEntitiesUserActivity;
import ai.gebo.gitlab.client.model.APIEntitiesUserAssociationsCount;
import ai.gebo.gitlab.client.model.APIEntitiesUserBasic;
import ai.gebo.gitlab.client.model.APIEntitiesUserCounts;
import ai.gebo.gitlab.client.model.APIEntitiesUserCreditCardValidations;
import ai.gebo.gitlab.client.model.APIEntitiesUserPreferences;
import ai.gebo.gitlab.client.model.APIEntitiesUserProfile;
import ai.gebo.gitlab.client.model.APIEntitiesUserPublic;
import ai.gebo.gitlab.client.model.APIEntitiesUserStatus;
import ai.gebo.gitlab.client.model.APIEntitiesUserSupportPin;
import ai.gebo.gitlab.client.model.APIEntitiesUserWithAdmin;
import java.io.File;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4UserStatusRequest;
import ai.gebo.gitlab.client.model.PostApiV4UserEmailsRequest;
import ai.gebo.gitlab.client.model.PostApiV4UserRunnersRequest;
import ai.gebo.gitlab.client.model.PostApiV4UsersIdEmailsRequest;
import ai.gebo.gitlab.client.model.PutApiV4UserPreferencesRequest;
import ai.gebo.gitlab.client.model.PutApiV4UserStatusRequest;
import ai.gebo.gitlab.client.model.PutApiV4UserUserIdCreditCardValidationRequest;

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
     * Delete an email address
     * Deletes the specified email address. You cannot delete a primary email address. Any future emails sent to the deleted email address are sent to the primary email address instead.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param emailId ID of the email.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UserEmailsEmailIdRequestCreation(@jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        // verify the required parameter 'emailId' is set
        if (emailId == null) {
            throw new RestClientResponseException("Missing the required parameter 'emailId' when calling deleteApiV4UserEmailsEmailId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("email_id", emailId);

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
        return apiClient.invokeAPI("/api/v4/user/emails/{email_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an email address
     * Deletes the specified email address. You cannot delete a primary email address. Any future emails sent to the deleted email address are sent to the primary email address instead.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param emailId ID of the email.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4UserEmailsEmailId(@jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4UserEmailsEmailIdRequestCreation(emailId).body(localVarReturnType);
    }

    /**
     * Delete an email address
     * Deletes the specified email address. You cannot delete a primary email address. Any future emails sent to the deleted email address are sent to the primary email address instead.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param emailId ID of the email.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4UserEmailsEmailIdWithHttpInfo(@jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UserEmailsEmailIdRequestCreation(emailId).toEntity(localVarReturnType);
    }

    /**
     * Delete an email address
     * Deletes the specified email address. You cannot delete a primary email address. Any future emails sent to the deleted email address are sent to the primary email address instead.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param emailId ID of the email.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UserEmailsEmailIdWithResponseSpec(@jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        return deleteApiV4UserEmailsEmailIdRequestCreation(emailId);
    }

    /**
     * Delete a user
     * Deletes a user. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this user.
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UsersIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4UsersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "hard_delete", hardDelete));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a user
     * Deletes a user. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this user.
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEmail deleteApiV4UsersId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdRequestCreation(id, hardDelete).body(localVarReturnType);
    }

    /**
     * Delete a user
     * Deletes a user. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this user.
     * @return ResponseEntity&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEmail> deleteApiV4UsersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdRequestCreation(id, hardDelete).toEntity(localVarReturnType);
    }

    /**
     * Delete a user
     * Deletes a user. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param hardDelete If &#x60;true&#x60;, contributions that would usually be [moved to a ghost user](https://docs.gitlab.com/user/profile/account/delete_account/#associated-records) are deleted instead, along with groups owned solely by this user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UsersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean hardDelete) throws RestClientResponseException {
        return deleteApiV4UsersIdRequestCreation(id, hardDelete);
    }

    /**
     * Delete an email address for a user
     * Deletes a specified email address for a user account. You cannot delete a primary email address. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param emailId ID of the email.
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UsersIdEmailsEmailIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4UsersIdEmailsEmailId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'emailId' is set
        if (emailId == null) {
            throw new RestClientResponseException("Missing the required parameter 'emailId' when calling deleteApiV4UsersIdEmailsEmailId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("email_id", emailId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/emails/{email_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an email address for a user
     * Deletes a specified email address for a user account. You cannot delete a primary email address. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param emailId ID of the email.
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEmail deleteApiV4UsersIdEmailsEmailId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdEmailsEmailIdRequestCreation(id, emailId).body(localVarReturnType);
    }

    /**
     * Delete an email address for a user
     * Deletes a specified email address for a user account. You cannot delete a primary email address. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param emailId ID of the email.
     * @return ResponseEntity&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEmail> deleteApiV4UsersIdEmailsEmailIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdEmailsEmailIdRequestCreation(id, emailId).toEntity(localVarReturnType);
    }

    /**
     * Delete an email address for a user
     * Deletes a specified email address for a user account. You cannot delete a primary email address. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param emailId ID of the email.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UsersIdEmailsEmailIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        return deleteApiV4UsersIdEmailsEmailIdRequestCreation(id, emailId);
    }

    /**
     * Delete authentication identity from a user
     * Deletes a user authentication identity using the provider name associated with that identity. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param provider Name of the external authentication provider.
     * @return APIEntitiesUserWithAdmin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UsersIdIdentitiesProviderRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String provider) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4UsersIdIdentitiesProvider", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'provider' is set
        if (provider == null) {
            throw new RestClientResponseException("Missing the required parameter 'provider' when calling deleteApiV4UsersIdIdentitiesProvider", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("provider", provider);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/identities/{provider}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete authentication identity from a user
     * Deletes a user authentication identity using the provider name associated with that identity. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param provider Name of the external authentication provider.
     * @return APIEntitiesUserWithAdmin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserWithAdmin deleteApiV4UsersIdIdentitiesProvider(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String provider) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdIdentitiesProviderRequestCreation(id, provider).body(localVarReturnType);
    }

    /**
     * Delete authentication identity from a user
     * Deletes a user authentication identity using the provider name associated with that identity. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param provider Name of the external authentication provider.
     * @return ResponseEntity&lt;APIEntitiesUserWithAdmin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserWithAdmin> deleteApiV4UsersIdIdentitiesProviderWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String provider) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdIdentitiesProviderRequestCreation(id, provider).toEntity(localVarReturnType);
    }

    /**
     * Delete authentication identity from a user
     * Deletes a user authentication identity using the provider name associated with that identity. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param provider Name of the external authentication provider.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UsersIdIdentitiesProviderWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String provider) throws RestClientResponseException {
        return deleteApiV4UsersIdIdentitiesProviderRequestCreation(id, provider);
    }

    /**
     * Retrieve current user details
     * Retrieves details about your user account.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesUserPublic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve current user details
     * Retrieves details about your user account.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesUserPublic
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserPublic getApiV4User() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve current user details
     * Retrieves details about your user account.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;APIEntitiesUserPublic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserPublic> getApiV4UserWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPublic> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve current user details
     * Retrieves details about your user account.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserWithResponseSpec() throws RestClientResponseException {
        return getApiV4UserRequestCreation();
    }

    /**
     * List all activity for a user
     * Lists the last activity date of users, sorted from oldest to newest. By default, lists users active in the last six months. Use &#x60;from&#x60; to change the start date. Only users with public profiles are listed, unless you are an administrator or auditor.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param from Date string in the format &#x60;YEAR-MM-DD&#x60;, for example &#x60;2016-03-11&#x60;. Default is six months ago.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserActivity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserActivitiesRequestCreation(@jakarta.annotation.Nullable OffsetDateTime from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "from", from));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesUserActivity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/activities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all activity for a user
     * Lists the last activity date of users, sorted from oldest to newest. By default, lists users active in the last six months. Use &#x60;from&#x60; to change the start date. Only users with public profiles are listed, unless you are an administrator or auditor.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param from Date string in the format &#x60;YEAR-MM-DD&#x60;, for example &#x60;2016-03-11&#x60;. Default is six months ago.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserActivity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserActivity> getApiV4UserActivities(@jakarta.annotation.Nullable OffsetDateTime from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserActivity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserActivitiesRequestCreation(from, page, perPage).body(localVarReturnType);
    }

    /**
     * List all activity for a user
     * Lists the last activity date of users, sorted from oldest to newest. By default, lists users active in the last six months. Use &#x60;from&#x60; to change the start date. Only users with public profiles are listed, unless you are an administrator or auditor.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param from Date string in the format &#x60;YEAR-MM-DD&#x60;, for example &#x60;2016-03-11&#x60;. Default is six months ago.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserActivity&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserActivity>> getApiV4UserActivitiesWithHttpInfo(@jakarta.annotation.Nullable OffsetDateTime from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserActivity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserActivitiesRequestCreation(from, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all activity for a user
     * Lists the last activity date of users, sorted from oldest to newest. By default, lists users active in the last six months. Use &#x60;from&#x60; to change the start date. Only users with public profiles are listed, unless you are an administrator or auditor.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param from Date string in the format &#x60;YEAR-MM-DD&#x60;, for example &#x60;2016-03-11&#x60;. Default is six months ago.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserActivitiesWithResponseSpec(@jakarta.annotation.Nullable OffsetDateTime from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UserActivitiesRequestCreation(from, page, perPage);
    }

    /**
     * Return the user specific counts
     * Returns counts for the current user: open issues and active merge requests assigned to the user, merge requests the user is requested to review, and pending to-do items.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesUserCounts
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserCountsRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserCounts> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user_counts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Return the user specific counts
     * Returns counts for the current user: open issues and active merge requests assigned to the user, merge requests the user is requested to review, and pending to-do items.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesUserCounts
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserCounts getApiV4UserCounts() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserCounts> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserCountsRequestCreation().body(localVarReturnType);
    }

    /**
     * Return the user specific counts
     * Returns counts for the current user: open issues and active merge requests assigned to the user, merge requests the user is requested to review, and pending to-do items.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseEntity&lt;APIEntitiesUserCounts&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserCounts> getApiV4UserCountsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserCounts> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserCountsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Return the user specific counts
     * Returns counts for the current user: open issues and active merge requests assigned to the user, merge requests the user is requested to review, and pending to-do items.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserCountsWithResponseSpec() throws RestClientResponseException {
        return getApiV4UserCountsRequestCreation();
    }

    /**
     * List all email addresses for a user
     * Lists all email addresses for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserEmailsRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEmail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/emails", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all email addresses for a user
     * Lists all email addresses for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEmail> getApiV4UserEmails(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEmail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserEmailsRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all email addresses for a user
     * Lists all email addresses for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEmail&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEmail>> getApiV4UserEmailsWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEmail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserEmailsRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all email addresses for a user
     * Lists all email addresses for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserEmailsWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UserEmailsRequestCreation(page, perPage);
    }

    /**
     * Retrieve details on an email address
     * Retrieves details on a specified email address for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param emailId ID of the email.
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserEmailsEmailIdRequestCreation(@jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        // verify the required parameter 'emailId' is set
        if (emailId == null) {
            throw new RestClientResponseException("Missing the required parameter 'emailId' when calling getApiV4UserEmailsEmailId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("email_id", emailId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/emails/{email_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve details on an email address
     * Retrieves details on a specified email address for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param emailId ID of the email.
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEmail getApiV4UserEmailsEmailId(@jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserEmailsEmailIdRequestCreation(emailId).body(localVarReturnType);
    }

    /**
     * Retrieve details on an email address
     * Retrieves details on a specified email address for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param emailId ID of the email.
     * @return ResponseEntity&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEmail> getApiV4UserEmailsEmailIdWithHttpInfo(@jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserEmailsEmailIdRequestCreation(emailId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve details on an email address
     * Retrieves details on a specified email address for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param emailId ID of the email.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserEmailsEmailIdWithResponseSpec(@jakarta.annotation.Nonnull Integer emailId) throws RestClientResponseException {
        return getApiV4UserEmailsEmailIdRequestCreation(emailId);
    }

    /**
     * Retrieve your user preferences
     * Retrieves your user preferences.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesUserPreferences
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserPreferencesRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserPreferences> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/preferences", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve your user preferences
     * Retrieves your user preferences.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesUserPreferences
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserPreferences getApiV4UserPreferences() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPreferences> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserPreferencesRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve your user preferences
     * Retrieves your user preferences.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;APIEntitiesUserPreferences&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserPreferences> getApiV4UserPreferencesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPreferences> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserPreferencesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve your user preferences
     * Retrieves your user preferences.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserPreferencesWithResponseSpec() throws RestClientResponseException {
        return getApiV4UserPreferencesRequestCreation();
    }

    /**
     * Retrieve your user status
     * Retrieves your user status.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesUserStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserStatusRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve your user status
     * Retrieves your user status.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesUserStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserStatus getApiV4UserStatus() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserStatusRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve your user status
     * Retrieves your user status.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;APIEntitiesUserStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserStatus> getApiV4UserStatusWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserStatusRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve your user status
     * Retrieves your user status.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserStatusWithResponseSpec() throws RestClientResponseException {
        return getApiV4UserStatusRequestCreation();
    }

    /**
     * Retrieve Support PIN
     * Retrieves a Support PIN for the currently authenticated user. GitLab Support may ask for this PIN to validate your identity. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @return APIEntitiesUserSupportPin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserSupportPinRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/support_pin", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve Support PIN
     * Retrieves a Support PIN for the currently authenticated user. GitLab Support may ask for this PIN to validate your identity. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @return APIEntitiesUserSupportPin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserSupportPin getApiV4UserSupportPin() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserSupportPinRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve Support PIN
     * Retrieves a Support PIN for the currently authenticated user. GitLab Support may ask for this PIN to validate your identity. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @return ResponseEntity&lt;APIEntitiesUserSupportPin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserSupportPin> getApiV4UserSupportPinWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserSupportPinRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve Support PIN
     * Retrieves a Support PIN for the currently authenticated user. GitLab Support may ask for this PIN to validate your identity. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserSupportPinWithResponseSpec() throws RestClientResponseException {
        return getApiV4UserSupportPinRequestCreation();
    }

    /**
     * List all users
     * Lists all users on the instance. This endpoint supports keyset pagination. By default, offset pagination is limited to 50,000 results. Use keyset pagination to retrieve more. Only administrators and auditors can use the &#x60;created_after&#x60;, &#x60;created_before&#x60;, &#x60;two_factor&#x60;, and &#x60;without_projects&#x60; parameters. For other users, these parameters are ignored.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param username Return a single user with the specified username. The match is case-insensitive.
     * @param externUid Return a single user with the specified external authentication provider UID. Use with &#x60;provider&#x60;. Administrators only. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param publicEmail Return a single user with the specified public email.
     * @param provider Name of the external authentication provider. The &#x60;scim&#x60; provider is available in Premium and Ultimate only. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param search Search for users by name, username, or public email. Administrators can also search private email addresses. Email matches must be exact. Name and username matches are fuzzy.
     * @param active If &#x60;true&#x60;, returns only active users. &#x60;false&#x60; has no effect.
     * @param humans If &#x60;true&#x60;, returns only regular users that are not bot or internal users.
     * @param external If &#x60;true&#x60;, returns only external users. &#x60;false&#x60; has no effect.
     * @param blocked If &#x60;true&#x60;, returns only blocked users. &#x60;false&#x60; has no effect.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param withoutProjects If &#x60;true&#x60;, returns only users that do not have any projects.
     * @param withoutProjectBots If &#x60;true&#x60;, excludes bot users for projects and groups from the results.
     * @param admins If &#x60;true&#x60;, returns only administrators.
     * @param twoFactor Filter users by two-factor authentication. Possible values are &#x60;enabled&#x60; and &#x60;disabled&#x60;. By default, returns all users.
     * @param excludeActive If &#x60;true&#x60;, returns only users that are not active.
     * @param excludeExternal If &#x60;true&#x60;, returns only users that are not external.
     * @param excludeHumans If &#x60;true&#x60;, returns only bot or internal users.
     * @param excludeInternal If &#x60;true&#x60;, returns only users that are not internal. Internal users include the alert bot and the support bot. Bot users for projects and groups are not excluded.
     * @param orderBy Sort results by the specified field. Default is &#x60;id&#x60;.
     * @param sort Sort results in ascending or descending order. Default is &#x60;desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param skipLdap If &#x60;true&#x60;, excludes LDAP users from the results.
     * @param auditors If &#x60;true&#x60;, returns only auditor users. Premium and Ultimate only.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersRequestCreation(@jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean humans, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Boolean withoutProjects, @jakarta.annotation.Nullable Boolean withoutProjectBots, @jakarta.annotation.Nullable Boolean admins, @jakarta.annotation.Nullable String twoFactor, @jakarta.annotation.Nullable Boolean excludeActive, @jakarta.annotation.Nullable Boolean excludeExternal, @jakarta.annotation.Nullable Boolean excludeHumans, @jakarta.annotation.Nullable Boolean excludeInternal, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean skipLdap, @jakarta.annotation.Nullable Boolean auditors) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "username", username));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "extern_uid", externUid));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "public_email", publicEmail));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "provider", provider));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "humans", humans));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "external", external));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "blocked", blocked));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "without_projects", withoutProjects));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "without_project_bots", withoutProjectBots));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "admins", admins));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "two_factor", twoFactor));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "exclude_active", excludeActive));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "exclude_external", excludeExternal));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "exclude_humans", excludeHumans));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "exclude_internal", excludeInternal));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "skip_ldap", skipLdap));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "auditors", auditors));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all users
     * Lists all users on the instance. This endpoint supports keyset pagination. By default, offset pagination is limited to 50,000 results. Use keyset pagination to retrieve more. Only administrators and auditors can use the &#x60;created_after&#x60;, &#x60;created_before&#x60;, &#x60;two_factor&#x60;, and &#x60;without_projects&#x60; parameters. For other users, these parameters are ignored.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param username Return a single user with the specified username. The match is case-insensitive.
     * @param externUid Return a single user with the specified external authentication provider UID. Use with &#x60;provider&#x60;. Administrators only. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param publicEmail Return a single user with the specified public email.
     * @param provider Name of the external authentication provider. The &#x60;scim&#x60; provider is available in Premium and Ultimate only. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param search Search for users by name, username, or public email. Administrators can also search private email addresses. Email matches must be exact. Name and username matches are fuzzy.
     * @param active If &#x60;true&#x60;, returns only active users. &#x60;false&#x60; has no effect.
     * @param humans If &#x60;true&#x60;, returns only regular users that are not bot or internal users.
     * @param external If &#x60;true&#x60;, returns only external users. &#x60;false&#x60; has no effect.
     * @param blocked If &#x60;true&#x60;, returns only blocked users. &#x60;false&#x60; has no effect.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param withoutProjects If &#x60;true&#x60;, returns only users that do not have any projects.
     * @param withoutProjectBots If &#x60;true&#x60;, excludes bot users for projects and groups from the results.
     * @param admins If &#x60;true&#x60;, returns only administrators.
     * @param twoFactor Filter users by two-factor authentication. Possible values are &#x60;enabled&#x60; and &#x60;disabled&#x60;. By default, returns all users.
     * @param excludeActive If &#x60;true&#x60;, returns only users that are not active.
     * @param excludeExternal If &#x60;true&#x60;, returns only users that are not external.
     * @param excludeHumans If &#x60;true&#x60;, returns only bot or internal users.
     * @param excludeInternal If &#x60;true&#x60;, returns only users that are not internal. Internal users include the alert bot and the support bot. Bot users for projects and groups are not excluded.
     * @param orderBy Sort results by the specified field. Default is &#x60;id&#x60;.
     * @param sort Sort results in ascending or descending order. Default is &#x60;desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param skipLdap If &#x60;true&#x60;, excludes LDAP users from the results.
     * @param auditors If &#x60;true&#x60;, returns only auditor users. Premium and Ultimate only.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserBasic> getApiV4Users(@jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean humans, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Boolean withoutProjects, @jakarta.annotation.Nullable Boolean withoutProjectBots, @jakarta.annotation.Nullable Boolean admins, @jakarta.annotation.Nullable String twoFactor, @jakarta.annotation.Nullable Boolean excludeActive, @jakarta.annotation.Nullable Boolean excludeExternal, @jakarta.annotation.Nullable Boolean excludeHumans, @jakarta.annotation.Nullable Boolean excludeInternal, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean skipLdap, @jakarta.annotation.Nullable Boolean auditors) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersRequestCreation(username, externUid, publicEmail, provider, search, active, humans, external, blocked, createdAfter, createdBefore, withoutProjects, withoutProjectBots, admins, twoFactor, excludeActive, excludeExternal, excludeHumans, excludeInternal, orderBy, sort, page, perPage, withCustomAttributes, customAttributes, skipLdap, auditors).body(localVarReturnType);
    }

    /**
     * List all users
     * Lists all users on the instance. This endpoint supports keyset pagination. By default, offset pagination is limited to 50,000 results. Use keyset pagination to retrieve more. Only administrators and auditors can use the &#x60;created_after&#x60;, &#x60;created_before&#x60;, &#x60;two_factor&#x60;, and &#x60;without_projects&#x60; parameters. For other users, these parameters are ignored.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param username Return a single user with the specified username. The match is case-insensitive.
     * @param externUid Return a single user with the specified external authentication provider UID. Use with &#x60;provider&#x60;. Administrators only. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param publicEmail Return a single user with the specified public email.
     * @param provider Name of the external authentication provider. The &#x60;scim&#x60; provider is available in Premium and Ultimate only. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param search Search for users by name, username, or public email. Administrators can also search private email addresses. Email matches must be exact. Name and username matches are fuzzy.
     * @param active If &#x60;true&#x60;, returns only active users. &#x60;false&#x60; has no effect.
     * @param humans If &#x60;true&#x60;, returns only regular users that are not bot or internal users.
     * @param external If &#x60;true&#x60;, returns only external users. &#x60;false&#x60; has no effect.
     * @param blocked If &#x60;true&#x60;, returns only blocked users. &#x60;false&#x60; has no effect.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param withoutProjects If &#x60;true&#x60;, returns only users that do not have any projects.
     * @param withoutProjectBots If &#x60;true&#x60;, excludes bot users for projects and groups from the results.
     * @param admins If &#x60;true&#x60;, returns only administrators.
     * @param twoFactor Filter users by two-factor authentication. Possible values are &#x60;enabled&#x60; and &#x60;disabled&#x60;. By default, returns all users.
     * @param excludeActive If &#x60;true&#x60;, returns only users that are not active.
     * @param excludeExternal If &#x60;true&#x60;, returns only users that are not external.
     * @param excludeHumans If &#x60;true&#x60;, returns only bot or internal users.
     * @param excludeInternal If &#x60;true&#x60;, returns only users that are not internal. Internal users include the alert bot and the support bot. Bot users for projects and groups are not excluded.
     * @param orderBy Sort results by the specified field. Default is &#x60;id&#x60;.
     * @param sort Sort results in ascending or descending order. Default is &#x60;desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param skipLdap If &#x60;true&#x60;, excludes LDAP users from the results.
     * @param auditors If &#x60;true&#x60;, returns only auditor users. Premium and Ultimate only.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserBasic>> getApiV4UsersWithHttpInfo(@jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean humans, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Boolean withoutProjects, @jakarta.annotation.Nullable Boolean withoutProjectBots, @jakarta.annotation.Nullable Boolean admins, @jakarta.annotation.Nullable String twoFactor, @jakarta.annotation.Nullable Boolean excludeActive, @jakarta.annotation.Nullable Boolean excludeExternal, @jakarta.annotation.Nullable Boolean excludeHumans, @jakarta.annotation.Nullable Boolean excludeInternal, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean skipLdap, @jakarta.annotation.Nullable Boolean auditors) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersRequestCreation(username, externUid, publicEmail, provider, search, active, humans, external, blocked, createdAfter, createdBefore, withoutProjects, withoutProjectBots, admins, twoFactor, excludeActive, excludeExternal, excludeHumans, excludeInternal, orderBy, sort, page, perPage, withCustomAttributes, customAttributes, skipLdap, auditors).toEntity(localVarReturnType);
    }

    /**
     * List all users
     * Lists all users on the instance. This endpoint supports keyset pagination. By default, offset pagination is limited to 50,000 results. Use keyset pagination to retrieve more. Only administrators and auditors can use the &#x60;created_after&#x60;, &#x60;created_before&#x60;, &#x60;two_factor&#x60;, and &#x60;without_projects&#x60; parameters. For other users, these parameters are ignored.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param username Return a single user with the specified username. The match is case-insensitive.
     * @param externUid Return a single user with the specified external authentication provider UID. Use with &#x60;provider&#x60;. Administrators only. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param publicEmail Return a single user with the specified public email.
     * @param provider Name of the external authentication provider. The &#x60;scim&#x60; provider is available in Premium and Ultimate only. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param search Search for users by name, username, or public email. Administrators can also search private email addresses. Email matches must be exact. Name and username matches are fuzzy.
     * @param active If &#x60;true&#x60;, returns only active users. &#x60;false&#x60; has no effect.
     * @param humans If &#x60;true&#x60;, returns only regular users that are not bot or internal users.
     * @param external If &#x60;true&#x60;, returns only external users. &#x60;false&#x60; has no effect.
     * @param blocked If &#x60;true&#x60;, returns only blocked users. &#x60;false&#x60; has no effect.
     * @param createdAfter Return users created on or after the specified time.
     * @param createdBefore Return users created on or before the specified time.
     * @param withoutProjects If &#x60;true&#x60;, returns only users that do not have any projects.
     * @param withoutProjectBots If &#x60;true&#x60;, excludes bot users for projects and groups from the results.
     * @param admins If &#x60;true&#x60;, returns only administrators.
     * @param twoFactor Filter users by two-factor authentication. Possible values are &#x60;enabled&#x60; and &#x60;disabled&#x60;. By default, returns all users.
     * @param excludeActive If &#x60;true&#x60;, returns only users that are not active.
     * @param excludeExternal If &#x60;true&#x60;, returns only users that are not external.
     * @param excludeHumans If &#x60;true&#x60;, returns only bot or internal users.
     * @param excludeInternal If &#x60;true&#x60;, returns only users that are not internal. Internal users include the alert bot and the support bot. Bot users for projects and groups are not excluded.
     * @param orderBy Sort results by the specified field. Default is &#x60;id&#x60;.
     * @param sort Sort results in ascending or descending order. Default is &#x60;desc&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @param skipLdap If &#x60;true&#x60;, excludes LDAP users from the results.
     * @param auditors If &#x60;true&#x60;, returns only auditor users. Premium and Ultimate only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersWithResponseSpec(@jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable Boolean active, @jakarta.annotation.Nullable Boolean humans, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable Boolean blocked, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable Boolean withoutProjects, @jakarta.annotation.Nullable Boolean withoutProjectBots, @jakarta.annotation.Nullable Boolean admins, @jakarta.annotation.Nullable String twoFactor, @jakarta.annotation.Nullable Boolean excludeActive, @jakarta.annotation.Nullable Boolean excludeExternal, @jakarta.annotation.Nullable Boolean excludeHumans, @jakarta.annotation.Nullable Boolean excludeInternal, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes, @jakarta.annotation.Nullable Boolean skipLdap, @jakarta.annotation.Nullable Boolean auditors) throws RestClientResponseException {
        return getApiV4UsersRequestCreation(username, externUid, publicEmail, provider, search, active, humans, external, blocked, createdAfter, createdBefore, withoutProjects, withoutProjectBots, admins, twoFactor, excludeActive, excludeExternal, excludeHumans, excludeInternal, orderBy, sort, page, perPage, withCustomAttributes, customAttributes, skipLdap, auditors);
    }

    /**
     * Retrieve a user
     * Retrieves a specified user. Requires authentication. Administrators and auditors see more attributes in the response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return APIEntitiesUserProfile
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_custom_attributes", withCustomAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "custom_attributes", customAttributes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserProfile> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a user
     * Retrieves a specified user. Requires authentication. Administrators and auditors see more attributes in the response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return APIEntitiesUserProfile
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserProfile getApiV4UsersId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserProfile> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdRequestCreation(id, withCustomAttributes, customAttributes).body(localVarReturnType);
    }

    /**
     * Retrieve a user
     * Retrieves a specified user. Requires authentication. Administrators and auditors see more attributes in the response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseEntity&lt;APIEntitiesUserProfile&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserProfile> getApiV4UsersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserProfile> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdRequestCreation(id, withCustomAttributes, customAttributes).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a user
     * Retrieves a specified user. Requires authentication. Administrators and auditors see more attributes in the response.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param withCustomAttributes If &#x60;true&#x60;, includes [custom attributes](https://docs.gitlab.com/api/custom_attributes/) in the response. Administrators only.
     * @param customAttributes Filter by [custom attributes](https://docs.gitlab.com/api/custom_attributes/) that match all of the specified key and value pairs. Administrators only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean withCustomAttributes, @jakarta.annotation.Nullable Object customAttributes) throws RestClientResponseException {
        return getApiV4UsersIdRequestCreation(id, withCustomAttributes, customAttributes);
    }

    /**
     * Retrieve association counts for a user
     * Retrieves the number of groups, projects, issues, and merge requests associated with a specified user. Administrators can query any user. Other users can query only themselves.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUserAssociationsCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdAssociationsCountRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdAssociationsCount", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserAssociationsCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/associations_count", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve association counts for a user
     * Retrieves the number of groups, projects, issues, and merge requests associated with a specified user. Administrators can query any user. Other users can query only themselves.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUserAssociationsCount
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserAssociationsCount getApiV4UsersIdAssociationsCount(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserAssociationsCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdAssociationsCountRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve association counts for a user
     * Retrieves the number of groups, projects, issues, and merge requests associated with a specified user. Administrators can query any user. Other users can query only themselves.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseEntity&lt;APIEntitiesUserAssociationsCount&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserAssociationsCount> getApiV4UsersIdAssociationsCountWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserAssociationsCount> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdAssociationsCountRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve association counts for a user
     * Retrieves the number of groups, projects, issues, and merge requests associated with a specified user. Administrators can query any user. Other users can query only themselves.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdAssociationsCountWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4UsersIdAssociationsCountRequestCreation(id);
    }

    /**
     * List all email addresses for a user
     * Lists all email addresses for a specified user account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdEmailsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdEmails", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesEmail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/emails", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all email addresses for a user
     * Lists all email addresses for a specified user account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesEmail> getApiV4UsersIdEmails(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEmail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdEmailsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all email addresses for a user
     * Lists all email addresses for a specified user account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesEmail&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesEmail>> getApiV4UsersIdEmailsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesEmail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdEmailsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all email addresses for a user
     * Lists all email addresses for a specified user account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdEmailsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UsersIdEmailsRequestCreation(id, page, perPage);
    }

    /**
     * List all accounts that follow a user
     * Lists all user accounts that follow a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdFollowersRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdFollowers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/followers", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all accounts that follow a user
     * Lists all user accounts that follow a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserBasic> getApiV4UsersIdFollowers(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdFollowersRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all accounts that follow a user
     * Lists all user accounts that follow a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserBasic>> getApiV4UsersIdFollowersWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdFollowersRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all accounts that follow a user
     * Lists all user accounts that follow a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdFollowersWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UsersIdFollowersRequestCreation(id, page, perPage);
    }

    /**
     * List all accounts followed by a user
     * Lists all user accounts being followed by a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdFollowingRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdFollowing", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/following", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all accounts followed by a user
     * Lists all user accounts being followed by a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserBasic> getApiV4UsersIdFollowing(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdFollowingRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all accounts followed by a user
     * Lists all user accounts being followed by a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserBasic>> getApiV4UsersIdFollowingWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdFollowingRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all accounts followed by a user
     * Lists all user accounts being followed by a specified user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdFollowingWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UsersIdFollowingRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a Support PIN for a user
     * Retrieves a Support PIN for a specified user. GitLab Support may ask for this PIN to validate your identity. Administrators only. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUserSupportPin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdSupportPinRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdSupportPin", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/support_pin", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Support PIN for a user
     * Retrieves a Support PIN for a specified user. GitLab Support may ask for this PIN to validate your identity. Administrators only. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUserSupportPin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserSupportPin getApiV4UsersIdSupportPin(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdSupportPinRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a Support PIN for a user
     * Retrieves a Support PIN for a specified user. GitLab Support may ask for this PIN to validate your identity. Administrators only. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseEntity&lt;APIEntitiesUserSupportPin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserSupportPin> getApiV4UsersIdSupportPinWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdSupportPinRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Support PIN for a user
     * Retrieves a Support PIN for a specified user. GitLab Support may ask for this PIN to validate your identity. Administrators only. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdSupportPinWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4UsersIdSupportPinRequestCreation(id);
    }

    /**
     * List all project and group memberships for a user
     * Lists all project and group memberships for a specified user. Only direct memberships are returned. Inherited memberships, for example in subgroups, are not included. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param type Filter memberships by type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMembership&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersUserIdMembershipsRequestCreation(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4UsersUserIdMemberships", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/memberships", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project and group memberships for a user
     * Lists all project and group memberships for a specified user. Only direct memberships are returned. Inherited memberships, for example in subgroups, are not included. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param type Filter memberships by type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesMembership&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMembership> getApiV4UsersUserIdMemberships(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdMembershipsRequestCreation(userId, type, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project and group memberships for a user
     * Lists all project and group memberships for a specified user. Only direct memberships are returned. Inherited memberships, for example in subgroups, are not included. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param type Filter memberships by type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMembership&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMembership>> getApiV4UsersUserIdMembershipsWithHttpInfo(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMembership>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdMembershipsRequestCreation(userId, type, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project and group memberships for a user
     * Lists all project and group memberships for a specified user. Only direct memberships are returned. Inherited memberships, for example in subgroups, are not included. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param type Filter memberships by type.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersUserIdMembershipsWithResponseSpec(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UsersUserIdMembershipsRequestCreation(userId, type, page, perPage);
    }

    /**
     * Retrieve the status of a user
     * Retrieves the status of a user. You can access this endpoint without authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @return APIEntitiesUserStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersUserIdStatusRequestCreation(@jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4UsersUserIdStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve the status of a user
     * Retrieves the status of a user. You can access this endpoint without authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @return APIEntitiesUserStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserStatus getApiV4UsersUserIdStatus(@jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdStatusRequestCreation(userId).body(localVarReturnType);
    }

    /**
     * Retrieve the status of a user
     * Retrieves the status of a user. You can access this endpoint without authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @return ResponseEntity&lt;APIEntitiesUserStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserStatus> getApiV4UsersUserIdStatusWithHttpInfo(@jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdStatusRequestCreation(userId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve the status of a user
     * Retrieves the status of a user. You can access this endpoint without authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersUserIdStatusWithResponseSpec(@jakarta.annotation.Nonnull String userId) throws RestClientResponseException {
        return getApiV4UsersUserIdStatusRequestCreation(userId);
    }

    /**
     * Update a user status
     * Sets the status of the currently authenticated user. Any parameters that are not passed are ignored. To clear a field, pass &#x60;null&#x60; explicitly. This operation is similar but distinct from the Set a user status operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param patchApiV4UserStatusRequest The patchApiV4UserStatusRequest parameter
     * @return APIEntitiesUserStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4UserStatusRequestCreation(@jakarta.annotation.Nullable PatchApiV4UserStatusRequest patchApiV4UserStatusRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/status", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4UserStatusRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a user status
     * Sets the status of the currently authenticated user. Any parameters that are not passed are ignored. To clear a field, pass &#x60;null&#x60; explicitly. This operation is similar but distinct from the Set a user status operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param patchApiV4UserStatusRequest The patchApiV4UserStatusRequest parameter
     * @return APIEntitiesUserStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserStatus patchApiV4UserStatus(@jakarta.annotation.Nullable PatchApiV4UserStatusRequest patchApiV4UserStatusRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4UserStatusRequestCreation(patchApiV4UserStatusRequest).body(localVarReturnType);
    }

    /**
     * Update a user status
     * Sets the status of the currently authenticated user. Any parameters that are not passed are ignored. To clear a field, pass &#x60;null&#x60; explicitly. This operation is similar but distinct from the Set a user status operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param patchApiV4UserStatusRequest The patchApiV4UserStatusRequest parameter
     * @return ResponseEntity&lt;APIEntitiesUserStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserStatus> patchApiV4UserStatusWithHttpInfo(@jakarta.annotation.Nullable PatchApiV4UserStatusRequest patchApiV4UserStatusRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4UserStatusRequestCreation(patchApiV4UserStatusRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a user status
     * Sets the status of the currently authenticated user. Any parameters that are not passed are ignored. To clear a field, pass &#x60;null&#x60; explicitly. This operation is similar but distinct from the Set a user status operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param patchApiV4UserStatusRequest The patchApiV4UserStatusRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4UserStatusWithResponseSpec(@jakarta.annotation.Nullable PatchApiV4UserStatusRequest patchApiV4UserStatusRequest) throws RestClientResponseException {
        return patchApiV4UserStatusRequestCreation(patchApiV4UserStatusRequest);
    }

    /**
     * Disable two-factor authentication for a user
     * Disables two-factor authentication for a specified user account. Two-factor authentication cannot be disabled for administrators, including your own account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUserWithAdmin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4UsersIdDisableTwoFactorRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4UsersIdDisableTwoFactor", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/disable_two_factor", HttpMethod.PATCH, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Disable two-factor authentication for a user
     * Disables two-factor authentication for a specified user account. Two-factor authentication cannot be disabled for administrators, including your own account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUserWithAdmin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserWithAdmin patchApiV4UsersIdDisableTwoFactor(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4UsersIdDisableTwoFactorRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Disable two-factor authentication for a user
     * Disables two-factor authentication for a specified user account. Two-factor authentication cannot be disabled for administrators, including your own account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseEntity&lt;APIEntitiesUserWithAdmin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserWithAdmin> patchApiV4UsersIdDisableTwoFactorWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4UsersIdDisableTwoFactorRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Disable two-factor authentication for a user
     * Disables two-factor authentication for a specified user account. Two-factor authentication cannot be disabled for administrators, including your own account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4UsersIdDisableTwoFactorWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return patchApiV4UsersIdDisableTwoFactorRequestCreation(id);
    }

    /**
     * Add an email address
     * Adds an email address for the currently authenticated user. If the email address is already in use, returns a validation error with the message &#x60;has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserEmailsRequest The postApiV4UserEmailsRequest parameter
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserEmailsRequestCreation(@jakarta.annotation.Nonnull PostApiV4UserEmailsRequest postApiV4UserEmailsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UserEmailsRequest' is set
        if (postApiV4UserEmailsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UserEmailsRequest' when calling postApiV4UserEmails", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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

        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/emails", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UserEmailsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an email address
     * Adds an email address for the currently authenticated user. If the email address is already in use, returns a validation error with the message &#x60;has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserEmailsRequest The postApiV4UserEmailsRequest parameter
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEmail postApiV4UserEmails(@jakarta.annotation.Nonnull PostApiV4UserEmailsRequest postApiV4UserEmailsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserEmailsRequestCreation(postApiV4UserEmailsRequest).body(localVarReturnType);
    }

    /**
     * Add an email address
     * Adds an email address for the currently authenticated user. If the email address is already in use, returns a validation error with the message &#x60;has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserEmailsRequest The postApiV4UserEmailsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEmail> postApiV4UserEmailsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UserEmailsRequest postApiV4UserEmailsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserEmailsRequestCreation(postApiV4UserEmailsRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an email address
     * Adds an email address for the currently authenticated user. If the email address is already in use, returns a validation error with the message &#x60;has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserEmailsRequest The postApiV4UserEmailsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserEmailsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UserEmailsRequest postApiV4UserEmailsRequest) throws RestClientResponseException {
        return postApiV4UserEmailsRequestCreation(postApiV4UserEmailsRequest);
    }

    /**
     * Create a runner owned by currently authenticated user
     * Creates a runner linked to the current user. The user is listed as the owner for audit purposes, but runner availability is based on &#x60;runner_type&#x60;. The &#x60;token&#x60; value is included in the response, but cannot be retrieved later. For &#x60;instance_type&#x60;, requires an administrator. For &#x60;group_type&#x60;, requires an administrator, the Owner role for the group, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;project_type&#x60;, requires an administrator, at least the Maintainer role for the project, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;group_type&#x60; and &#x60;project_type&#x60;, users who are not administrators can create the runner only when runner registration is allowed for the group or project. Requires a token with the &#x60;create_runner&#x60; or &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * @param postApiV4UserRunnersRequest The postApiV4UserRunnersRequest parameter
     * @return APIEntitiesCiRunnerRegistrationDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserRunnersRequestCreation(@jakarta.annotation.Nonnull PostApiV4UserRunnersRequest postApiV4UserRunnersRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UserRunnersRequest' is set
        if (postApiV4UserRunnersRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UserRunnersRequest' when calling postApiV4UserRunners", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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

        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/runners", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UserRunnersRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a runner owned by currently authenticated user
     * Creates a runner linked to the current user. The user is listed as the owner for audit purposes, but runner availability is based on &#x60;runner_type&#x60;. The &#x60;token&#x60; value is included in the response, but cannot be retrieved later. For &#x60;instance_type&#x60;, requires an administrator. For &#x60;group_type&#x60;, requires an administrator, the Owner role for the group, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;project_type&#x60;, requires an administrator, at least the Maintainer role for the project, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;group_type&#x60; and &#x60;project_type&#x60;, users who are not administrators can create the runner only when runner registration is allowed for the group or project. Requires a token with the &#x60;create_runner&#x60; or &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * @param postApiV4UserRunnersRequest The postApiV4UserRunnersRequest parameter
     * @return APIEntitiesCiRunnerRegistrationDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiRunnerRegistrationDetails postApiV4UserRunners(@jakarta.annotation.Nonnull PostApiV4UserRunnersRequest postApiV4UserRunnersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserRunnersRequestCreation(postApiV4UserRunnersRequest).body(localVarReturnType);
    }

    /**
     * Create a runner owned by currently authenticated user
     * Creates a runner linked to the current user. The user is listed as the owner for audit purposes, but runner availability is based on &#x60;runner_type&#x60;. The &#x60;token&#x60; value is included in the response, but cannot be retrieved later. For &#x60;instance_type&#x60;, requires an administrator. For &#x60;group_type&#x60;, requires an administrator, the Owner role for the group, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;project_type&#x60;, requires an administrator, at least the Maintainer role for the project, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;group_type&#x60; and &#x60;project_type&#x60;, users who are not administrators can create the runner only when runner registration is allowed for the group or project. Requires a token with the &#x60;create_runner&#x60; or &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * @param postApiV4UserRunnersRequest The postApiV4UserRunnersRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiRunnerRegistrationDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiRunnerRegistrationDetails> postApiV4UserRunnersWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UserRunnersRequest postApiV4UserRunnersRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiRunnerRegistrationDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserRunnersRequestCreation(postApiV4UserRunnersRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a runner owned by currently authenticated user
     * Creates a runner linked to the current user. The user is listed as the owner for audit purposes, but runner availability is based on &#x60;runner_type&#x60;. The &#x60;token&#x60; value is included in the response, but cannot be retrieved later. For &#x60;instance_type&#x60;, requires an administrator. For &#x60;group_type&#x60;, requires an administrator, the Owner role for the group, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;project_type&#x60;, requires an administrator, at least the Maintainer role for the project, or a custom role with the &#x60;admin_runners&#x60; permission. For &#x60;group_type&#x60; and &#x60;project_type&#x60;, users who are not administrators can create the runner only when runner registration is allowed for the group or project. Requires a token with the &#x60;create_runner&#x60; or &#x60;api&#x60; scope.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * @param postApiV4UserRunnersRequest The postApiV4UserRunnersRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserRunnersWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UserRunnersRequest postApiV4UserRunnersRequest) throws RestClientResponseException {
        return postApiV4UserRunnersRequestCreation(postApiV4UserRunnersRequest);
    }

    /**
     * Create a Support PIN
     * Creates a Support PIN for your user account. The PIN expires seven days after creation. GitLab Support may ask for this PIN to validate your identity. This feature was introduced in GitLab 17.8.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return APIEntitiesUserSupportPin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserSupportPinRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/support_pin", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a Support PIN
     * Creates a Support PIN for your user account. The PIN expires seven days after creation. GitLab Support may ask for this PIN to validate your identity. This feature was introduced in GitLab 17.8.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return APIEntitiesUserSupportPin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserSupportPin postApiV4UserSupportPin() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserSupportPinRequestCreation().body(localVarReturnType);
    }

    /**
     * Create a Support PIN
     * Creates a Support PIN for your user account. The PIN expires seven days after creation. GitLab Support may ask for this PIN to validate your identity. This feature was introduced in GitLab 17.8.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return ResponseEntity&lt;APIEntitiesUserSupportPin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserSupportPin> postApiV4UserSupportPinWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserSupportPin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserSupportPinRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Create a Support PIN
     * Creates a Support PIN for your user account. The PIN expires seven days after creation. GitLab Support may ask for this PIN to validate your identity. This feature was introduced in GitLab 17.8.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserSupportPinWithResponseSpec() throws RestClientResponseException {
        return postApiV4UserSupportPinRequestCreation();
    }

    /**
     * Create a user
     * Creates a user. If &#x60;private_profile&#x60; is not set, it defaults to the instance setting for new user profiles. If &#x60;bio&#x60; is not set, it defaults to an empty string. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param email Email address of the user.
     * @param name The name of the user
     * @param username The username of the user
     * @param password The password of the new user. If &#x60;force_random_password&#x60; or &#x60;reset_password&#x60; is &#x60;true&#x60;, it takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param resetPassword Flag indicating the user will be sent a password reset token. Can be used with &#x60;force_random_password&#x60;. Takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param skipConfirmation Flag indicating the account is confirmed. Default is &#x60;false&#x60;.
     * @param forceRandomPassword Flag indicating a random password will be set. Can be used with &#x60;reset_password&#x60;. Takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param linkedin The LinkedIn username
     * @param twitter The Twitter username
     * @param discord The Discord user ID
     * @param websiteUrl The website of the user
     * @param github The GitHub username
     * @param organization The organization of the user. Empty string or nil clears the field.
     * @param projectsLimit The number of projects a user can create
     * @param externUid The external authentication provider UID. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param provider Name of the external authentication provider. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param bio The biography of the user
     * @param location The location of the user
     * @param pronouns The pronouns of the user
     * @param publicEmail The public email of the user. Must be a verified email address of the user.
     * @param commitEmail The commit email, _private for private commit email
     * @param admin Flag indicating the user is an administrator. Default is &#x60;false&#x60; for new users.
     * @param canCreateGroup Flag indicating the user can create top-level groups
     * @param external Flag indicating the user is an external user. For new users, the default comes from the instance setting for new external users.
     * @param avatar Avatar image for user
     * @param themeId The GitLab theme for the user
     * @param colorSchemeId The color scheme for the file viewer
     * @param privateProfile Flag indicating the user has a private profile. For new users, the default comes from the instance setting for new user profiles.
     * @param note Admin note for this user
     * @param viewDiffsFileByFile If &#x60;true&#x60;, the user sees only one file diff per page.
     * @param policyAdvancedEditor Flag indicating that advanced editor is enabled
     * @param sharedRunnersMinutesLimit Maximum number of monthly compute minutes for this user. &#x60;nil&#x60; inherits the instance default, &#x60;0&#x60; means unlimited, and a value greater than &#x60;0&#x60; sets the limit. Can be set by administrators only. Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this user. Premium and Ultimate only.
     * @param groupIdForSaml ID for group where SAML has been configured
     * @param auditor Flag indicating auditor status of the user. Default is &#x60;false&#x60; for new users. Premium and Ultimate only.
     * @return APIEntitiesUserWithAdmin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersRequestCreation(@jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String password, @jakarta.annotation.Nullable Boolean resetPassword, @jakarta.annotation.Nullable Boolean skipConfirmation, @jakarta.annotation.Nullable Boolean forceRandomPassword, @jakarta.annotation.Nullable String linkedin, @jakarta.annotation.Nullable String twitter, @jakarta.annotation.Nullable String discord, @jakarta.annotation.Nullable String websiteUrl, @jakarta.annotation.Nullable String github, @jakarta.annotation.Nullable String organization, @jakarta.annotation.Nullable Integer projectsLimit, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String bio, @jakarta.annotation.Nullable String location, @jakarta.annotation.Nullable String pronouns, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String commitEmail, @jakarta.annotation.Nullable Boolean admin, @jakarta.annotation.Nullable Boolean canCreateGroup, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer themeId, @jakarta.annotation.Nullable Integer colorSchemeId, @jakarta.annotation.Nullable Boolean privateProfile, @jakarta.annotation.Nullable String note, @jakarta.annotation.Nullable Boolean viewDiffsFileByFile, @jakarta.annotation.Nullable Boolean policyAdvancedEditor, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer groupIdForSaml, @jakarta.annotation.Nullable Boolean auditor) throws RestClientResponseException {
        // verify the required parameter 'email' is set
        if (email == null) {
            throw new RestClientResponseException("Missing the required parameter 'email' when calling postApiV4Users", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4Users", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'username' is set
        if (username == null) {
            throw new RestClientResponseException("Missing the required parameter 'username' when calling postApiV4Users", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("email", email);
        if (password != null) {
            formParams.add("password", password);
        }
        if (resetPassword != null) {
            formParams.add("reset_password", resetPassword);
        }
        if (skipConfirmation != null) {
            formParams.add("skip_confirmation", skipConfirmation);
        }
        if (forceRandomPassword != null) {
            formParams.add("force_random_password", forceRandomPassword);
        }
        formParams.add("name", name);
        formParams.add("username", username);
        if (linkedin != null) {
            formParams.add("linkedin", linkedin);
        }
        if (twitter != null) {
            formParams.add("twitter", twitter);
        }
        if (discord != null) {
            formParams.add("discord", discord);
        }
        if (websiteUrl != null) {
            formParams.add("website_url", websiteUrl);
        }
        if (github != null) {
            formParams.add("github", github);
        }
        if (organization != null) {
            formParams.add("organization", organization);
        }
        if (projectsLimit != null) {
            formParams.add("projects_limit", projectsLimit);
        }
        if (externUid != null) {
            formParams.add("extern_uid", externUid);
        }
        if (provider != null) {
            formParams.add("provider", provider);
        }
        if (bio != null) {
            formParams.add("bio", bio);
        }
        if (location != null) {
            formParams.add("location", location);
        }
        if (pronouns != null) {
            formParams.add("pronouns", pronouns);
        }
        if (publicEmail != null) {
            formParams.add("public_email", publicEmail);
        }
        if (commitEmail != null) {
            formParams.add("commit_email", commitEmail);
        }
        if (admin != null) {
            formParams.add("admin", admin);
        }
        if (canCreateGroup != null) {
            formParams.add("can_create_group", canCreateGroup);
        }
        if (external != null) {
            formParams.add("external", external);
        }
        if (avatar != null) {
            formParams.add("avatar", new FileSystemResource(avatar));
        }
        if (themeId != null) {
            formParams.add("theme_id", themeId);
        }
        if (colorSchemeId != null) {
            formParams.add("color_scheme_id", colorSchemeId);
        }
        if (privateProfile != null) {
            formParams.add("private_profile", privateProfile);
        }
        if (note != null) {
            formParams.add("note", note);
        }
        if (viewDiffsFileByFile != null) {
            formParams.add("view_diffs_file_by_file", viewDiffsFileByFile);
        }
        if (policyAdvancedEditor != null) {
            formParams.add("policy_advanced_editor", policyAdvancedEditor);
        }
        if (sharedRunnersMinutesLimit != null) {
            formParams.add("shared_runners_minutes_limit", sharedRunnersMinutesLimit);
        }
        if (extraSharedRunnersMinutesLimit != null) {
            formParams.add("extra_shared_runners_minutes_limit", extraSharedRunnersMinutesLimit);
        }
        if (groupIdForSaml != null) {
            formParams.add("group_id_for_saml", groupIdForSaml);
        }
        if (auditor != null) {
            formParams.add("auditor", auditor);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a user
     * Creates a user. If &#x60;private_profile&#x60; is not set, it defaults to the instance setting for new user profiles. If &#x60;bio&#x60; is not set, it defaults to an empty string. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param email Email address of the user.
     * @param name The name of the user
     * @param username The username of the user
     * @param password The password of the new user. If &#x60;force_random_password&#x60; or &#x60;reset_password&#x60; is &#x60;true&#x60;, it takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param resetPassword Flag indicating the user will be sent a password reset token. Can be used with &#x60;force_random_password&#x60;. Takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param skipConfirmation Flag indicating the account is confirmed. Default is &#x60;false&#x60;.
     * @param forceRandomPassword Flag indicating a random password will be set. Can be used with &#x60;reset_password&#x60;. Takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param linkedin The LinkedIn username
     * @param twitter The Twitter username
     * @param discord The Discord user ID
     * @param websiteUrl The website of the user
     * @param github The GitHub username
     * @param organization The organization of the user. Empty string or nil clears the field.
     * @param projectsLimit The number of projects a user can create
     * @param externUid The external authentication provider UID. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param provider Name of the external authentication provider. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param bio The biography of the user
     * @param location The location of the user
     * @param pronouns The pronouns of the user
     * @param publicEmail The public email of the user. Must be a verified email address of the user.
     * @param commitEmail The commit email, _private for private commit email
     * @param admin Flag indicating the user is an administrator. Default is &#x60;false&#x60; for new users.
     * @param canCreateGroup Flag indicating the user can create top-level groups
     * @param external Flag indicating the user is an external user. For new users, the default comes from the instance setting for new external users.
     * @param avatar Avatar image for user
     * @param themeId The GitLab theme for the user
     * @param colorSchemeId The color scheme for the file viewer
     * @param privateProfile Flag indicating the user has a private profile. For new users, the default comes from the instance setting for new user profiles.
     * @param note Admin note for this user
     * @param viewDiffsFileByFile If &#x60;true&#x60;, the user sees only one file diff per page.
     * @param policyAdvancedEditor Flag indicating that advanced editor is enabled
     * @param sharedRunnersMinutesLimit Maximum number of monthly compute minutes for this user. &#x60;nil&#x60; inherits the instance default, &#x60;0&#x60; means unlimited, and a value greater than &#x60;0&#x60; sets the limit. Can be set by administrators only. Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this user. Premium and Ultimate only.
     * @param groupIdForSaml ID for group where SAML has been configured
     * @param auditor Flag indicating auditor status of the user. Default is &#x60;false&#x60; for new users. Premium and Ultimate only.
     * @return APIEntitiesUserWithAdmin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserWithAdmin postApiV4Users(@jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String password, @jakarta.annotation.Nullable Boolean resetPassword, @jakarta.annotation.Nullable Boolean skipConfirmation, @jakarta.annotation.Nullable Boolean forceRandomPassword, @jakarta.annotation.Nullable String linkedin, @jakarta.annotation.Nullable String twitter, @jakarta.annotation.Nullable String discord, @jakarta.annotation.Nullable String websiteUrl, @jakarta.annotation.Nullable String github, @jakarta.annotation.Nullable String organization, @jakarta.annotation.Nullable Integer projectsLimit, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String bio, @jakarta.annotation.Nullable String location, @jakarta.annotation.Nullable String pronouns, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String commitEmail, @jakarta.annotation.Nullable Boolean admin, @jakarta.annotation.Nullable Boolean canCreateGroup, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer themeId, @jakarta.annotation.Nullable Integer colorSchemeId, @jakarta.annotation.Nullable Boolean privateProfile, @jakarta.annotation.Nullable String note, @jakarta.annotation.Nullable Boolean viewDiffsFileByFile, @jakarta.annotation.Nullable Boolean policyAdvancedEditor, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer groupIdForSaml, @jakarta.annotation.Nullable Boolean auditor) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersRequestCreation(email, name, username, password, resetPassword, skipConfirmation, forceRandomPassword, linkedin, twitter, discord, websiteUrl, github, organization, projectsLimit, externUid, provider, bio, location, pronouns, publicEmail, commitEmail, admin, canCreateGroup, external, avatar, themeId, colorSchemeId, privateProfile, note, viewDiffsFileByFile, policyAdvancedEditor, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, groupIdForSaml, auditor).body(localVarReturnType);
    }

    /**
     * Create a user
     * Creates a user. If &#x60;private_profile&#x60; is not set, it defaults to the instance setting for new user profiles. If &#x60;bio&#x60; is not set, it defaults to an empty string. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param email Email address of the user.
     * @param name The name of the user
     * @param username The username of the user
     * @param password The password of the new user. If &#x60;force_random_password&#x60; or &#x60;reset_password&#x60; is &#x60;true&#x60;, it takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param resetPassword Flag indicating the user will be sent a password reset token. Can be used with &#x60;force_random_password&#x60;. Takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param skipConfirmation Flag indicating the account is confirmed. Default is &#x60;false&#x60;.
     * @param forceRandomPassword Flag indicating a random password will be set. Can be used with &#x60;reset_password&#x60;. Takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param linkedin The LinkedIn username
     * @param twitter The Twitter username
     * @param discord The Discord user ID
     * @param websiteUrl The website of the user
     * @param github The GitHub username
     * @param organization The organization of the user. Empty string or nil clears the field.
     * @param projectsLimit The number of projects a user can create
     * @param externUid The external authentication provider UID. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param provider Name of the external authentication provider. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param bio The biography of the user
     * @param location The location of the user
     * @param pronouns The pronouns of the user
     * @param publicEmail The public email of the user. Must be a verified email address of the user.
     * @param commitEmail The commit email, _private for private commit email
     * @param admin Flag indicating the user is an administrator. Default is &#x60;false&#x60; for new users.
     * @param canCreateGroup Flag indicating the user can create top-level groups
     * @param external Flag indicating the user is an external user. For new users, the default comes from the instance setting for new external users.
     * @param avatar Avatar image for user
     * @param themeId The GitLab theme for the user
     * @param colorSchemeId The color scheme for the file viewer
     * @param privateProfile Flag indicating the user has a private profile. For new users, the default comes from the instance setting for new user profiles.
     * @param note Admin note for this user
     * @param viewDiffsFileByFile If &#x60;true&#x60;, the user sees only one file diff per page.
     * @param policyAdvancedEditor Flag indicating that advanced editor is enabled
     * @param sharedRunnersMinutesLimit Maximum number of monthly compute minutes for this user. &#x60;nil&#x60; inherits the instance default, &#x60;0&#x60; means unlimited, and a value greater than &#x60;0&#x60; sets the limit. Can be set by administrators only. Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this user. Premium and Ultimate only.
     * @param groupIdForSaml ID for group where SAML has been configured
     * @param auditor Flag indicating auditor status of the user. Default is &#x60;false&#x60; for new users. Premium and Ultimate only.
     * @return ResponseEntity&lt;APIEntitiesUserWithAdmin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserWithAdmin> postApiV4UsersWithHttpInfo(@jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String password, @jakarta.annotation.Nullable Boolean resetPassword, @jakarta.annotation.Nullable Boolean skipConfirmation, @jakarta.annotation.Nullable Boolean forceRandomPassword, @jakarta.annotation.Nullable String linkedin, @jakarta.annotation.Nullable String twitter, @jakarta.annotation.Nullable String discord, @jakarta.annotation.Nullable String websiteUrl, @jakarta.annotation.Nullable String github, @jakarta.annotation.Nullable String organization, @jakarta.annotation.Nullable Integer projectsLimit, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String bio, @jakarta.annotation.Nullable String location, @jakarta.annotation.Nullable String pronouns, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String commitEmail, @jakarta.annotation.Nullable Boolean admin, @jakarta.annotation.Nullable Boolean canCreateGroup, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer themeId, @jakarta.annotation.Nullable Integer colorSchemeId, @jakarta.annotation.Nullable Boolean privateProfile, @jakarta.annotation.Nullable String note, @jakarta.annotation.Nullable Boolean viewDiffsFileByFile, @jakarta.annotation.Nullable Boolean policyAdvancedEditor, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer groupIdForSaml, @jakarta.annotation.Nullable Boolean auditor) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersRequestCreation(email, name, username, password, resetPassword, skipConfirmation, forceRandomPassword, linkedin, twitter, discord, websiteUrl, github, organization, projectsLimit, externUid, provider, bio, location, pronouns, publicEmail, commitEmail, admin, canCreateGroup, external, avatar, themeId, colorSchemeId, privateProfile, note, viewDiffsFileByFile, policyAdvancedEditor, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, groupIdForSaml, auditor).toEntity(localVarReturnType);
    }

    /**
     * Create a user
     * Creates a user. If &#x60;private_profile&#x60; is not set, it defaults to the instance setting for new user profiles. If &#x60;bio&#x60; is not set, it defaults to an empty string. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param email Email address of the user.
     * @param name The name of the user
     * @param username The username of the user
     * @param password The password of the new user. If &#x60;force_random_password&#x60; or &#x60;reset_password&#x60; is &#x60;true&#x60;, it takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param resetPassword Flag indicating the user will be sent a password reset token. Can be used with &#x60;force_random_password&#x60;. Takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param skipConfirmation Flag indicating the account is confirmed. Default is &#x60;false&#x60;.
     * @param forceRandomPassword Flag indicating a random password will be set. Can be used with &#x60;reset_password&#x60;. Takes priority over &#x60;password&#x60;. At least one of &#x60;password&#x60;, &#x60;reset_password&#x60;, &#x60;force_random_password&#x60; must be provided.
     * @param linkedin The LinkedIn username
     * @param twitter The Twitter username
     * @param discord The Discord user ID
     * @param websiteUrl The website of the user
     * @param github The GitHub username
     * @param organization The organization of the user. Empty string or nil clears the field.
     * @param projectsLimit The number of projects a user can create
     * @param externUid The external authentication provider UID. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param provider Name of the external authentication provider. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param bio The biography of the user
     * @param location The location of the user
     * @param pronouns The pronouns of the user
     * @param publicEmail The public email of the user. Must be a verified email address of the user.
     * @param commitEmail The commit email, _private for private commit email
     * @param admin Flag indicating the user is an administrator. Default is &#x60;false&#x60; for new users.
     * @param canCreateGroup Flag indicating the user can create top-level groups
     * @param external Flag indicating the user is an external user. For new users, the default comes from the instance setting for new external users.
     * @param avatar Avatar image for user
     * @param themeId The GitLab theme for the user
     * @param colorSchemeId The color scheme for the file viewer
     * @param privateProfile Flag indicating the user has a private profile. For new users, the default comes from the instance setting for new user profiles.
     * @param note Admin note for this user
     * @param viewDiffsFileByFile If &#x60;true&#x60;, the user sees only one file diff per page.
     * @param policyAdvancedEditor Flag indicating that advanced editor is enabled
     * @param sharedRunnersMinutesLimit Maximum number of monthly compute minutes for this user. &#x60;nil&#x60; inherits the instance default, &#x60;0&#x60; means unlimited, and a value greater than &#x60;0&#x60; sets the limit. Can be set by administrators only. Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this user. Premium and Ultimate only.
     * @param groupIdForSaml ID for group where SAML has been configured
     * @param auditor Flag indicating auditor status of the user. Default is &#x60;false&#x60; for new users. Premium and Ultimate only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersWithResponseSpec(@jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String password, @jakarta.annotation.Nullable Boolean resetPassword, @jakarta.annotation.Nullable Boolean skipConfirmation, @jakarta.annotation.Nullable Boolean forceRandomPassword, @jakarta.annotation.Nullable String linkedin, @jakarta.annotation.Nullable String twitter, @jakarta.annotation.Nullable String discord, @jakarta.annotation.Nullable String websiteUrl, @jakarta.annotation.Nullable String github, @jakarta.annotation.Nullable String organization, @jakarta.annotation.Nullable Integer projectsLimit, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String bio, @jakarta.annotation.Nullable String location, @jakarta.annotation.Nullable String pronouns, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String commitEmail, @jakarta.annotation.Nullable Boolean admin, @jakarta.annotation.Nullable Boolean canCreateGroup, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer themeId, @jakarta.annotation.Nullable Integer colorSchemeId, @jakarta.annotation.Nullable Boolean privateProfile, @jakarta.annotation.Nullable String note, @jakarta.annotation.Nullable Boolean viewDiffsFileByFile, @jakarta.annotation.Nullable Boolean policyAdvancedEditor, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer groupIdForSaml, @jakarta.annotation.Nullable Boolean auditor) throws RestClientResponseException {
        return postApiV4UsersRequestCreation(email, name, username, password, resetPassword, skipConfirmation, forceRandomPassword, linkedin, twitter, discord, websiteUrl, github, organization, projectsLimit, externUid, provider, bio, location, pronouns, publicEmail, commitEmail, admin, canCreateGroup, external, avatar, themeId, colorSchemeId, privateProfile, note, viewDiffsFileByFile, policyAdvancedEditor, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, groupIdForSaml, auditor);
    }

    /**
     * Reactivate a user
     * Reactivates a specified user account that was previously deactivated. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdActivateRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdActivate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/activate", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reactivate a user
     * Reactivates a specified user account that was previously deactivated. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdActivate(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdActivateRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Reactivate a user
     * Reactivates a specified user account that was previously deactivated. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdActivateWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdActivateRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Reactivate a user
     * Reactivates a specified user account that was previously deactivated. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdActivateWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdActivateRequestCreation(id);
    }

    /**
     * Approve access to a user
     * Approves access to a specified user account that is pending approval. If the user is not pending approval, returns the message &#x60;The user you are trying to approve is not pending approval&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdApproveRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdApprove", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/approve", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Approve access to a user
     * Approves access to a specified user account that is pending approval. If the user is not pending approval, returns the message &#x60;The user you are trying to approve is not pending approval&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdApprove(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdApproveRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Approve access to a user
     * Approves access to a specified user account that is pending approval. If the user is not pending approval, returns the message &#x60;The user you are trying to approve is not pending approval&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdApproveWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdApproveRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Approve access to a user
     * Approves access to a specified user account that is pending approval. If the user is not pending approval, returns the message &#x60;The user you are trying to approve is not pending approval&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdApproveWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdApproveRequestCreation(id);
    }

    /**
     * Ban a user
     * Bans a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdBanRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdBan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/ban", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Ban a user
     * Bans a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdBan(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdBanRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Ban a user
     * Bans a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdBanWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdBanRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Ban a user
     * Bans a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdBanWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdBanRequestCreation(id);
    }

    /**
     * Block access to a user
     * Blocks a specified user account. A user cannot be blocked if they are blocked through LDAP, are an internal user, or are the user making the request. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdBlockRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdBlock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/block", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Block access to a user
     * Blocks a specified user account. A user cannot be blocked if they are blocked through LDAP, are an internal user, or are the user making the request. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdBlock(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdBlockRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Block access to a user
     * Blocks a specified user account. A user cannot be blocked if they are blocked through LDAP, are an internal user, or are the user making the request. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdBlockWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdBlockRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Block access to a user
     * Blocks a specified user account. A user cannot be blocked if they are blocked through LDAP, are an internal user, or are the user making the request. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdBlockWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdBlockRequestCreation(id);
    }

    /**
     * Deactivate a user
     * Deactivates a specified user account. A user cannot be deactivated if they are blocked by an administrator or by LDAP synchronization, are banned or pending approval, are not dormant, or are an internal user. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdDeactivateRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdDeactivate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/deactivate", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Deactivate a user
     * Deactivates a specified user account. A user cannot be deactivated if they are blocked by an administrator or by LDAP synchronization, are banned or pending approval, are not dormant, or are an internal user. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdDeactivate(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdDeactivateRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Deactivate a user
     * Deactivates a specified user account. A user cannot be deactivated if they are blocked by an administrator or by LDAP synchronization, are banned or pending approval, are not dormant, or are an internal user. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdDeactivateWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdDeactivateRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Deactivate a user
     * Deactivates a specified user account. A user cannot be deactivated if they are blocked by an administrator or by LDAP synchronization, are banned or pending approval, are not dormant, or are an internal user. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdDeactivateWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdDeactivateRequestCreation(id);
    }

    /**
     * Add an email address for a user
     * Adds an email address for a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param postApiV4UsersIdEmailsRequest The postApiV4UsersIdEmailsRequest parameter
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdEmailsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4UsersIdEmailsRequest postApiV4UsersIdEmailsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdEmails", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4UsersIdEmailsRequest' is set
        if (postApiV4UsersIdEmailsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsersIdEmailsRequest' when calling postApiV4UsersIdEmails", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/emails", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsersIdEmailsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an email address for a user
     * Adds an email address for a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param postApiV4UsersIdEmailsRequest The postApiV4UsersIdEmailsRequest parameter
     * @return APIEntitiesEmail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesEmail postApiV4UsersIdEmails(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4UsersIdEmailsRequest postApiV4UsersIdEmailsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdEmailsRequestCreation(id, postApiV4UsersIdEmailsRequest).body(localVarReturnType);
    }

    /**
     * Add an email address for a user
     * Adds an email address for a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param postApiV4UsersIdEmailsRequest The postApiV4UsersIdEmailsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesEmail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesEmail> postApiV4UsersIdEmailsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4UsersIdEmailsRequest postApiV4UsersIdEmailsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesEmail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdEmailsRequestCreation(id, postApiV4UsersIdEmailsRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an email address for a user
     * Adds an email address for a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param postApiV4UsersIdEmailsRequest The postApiV4UsersIdEmailsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdEmailsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4UsersIdEmailsRequest postApiV4UsersIdEmailsRequest) throws RestClientResponseException {
        return postApiV4UsersIdEmailsRequestCreation(id, postApiV4UsersIdEmailsRequest);
    }

    /**
     * Follow a user
     * Follows a specified user account.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdFollowRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdFollow", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/follow", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Follow a user
     * Follows a specified user account.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUser postApiV4UsersIdFollow(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdFollowRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Follow a user
     * Follows a specified user account.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseEntity&lt;APIEntitiesUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUser> postApiV4UsersIdFollowWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdFollowRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Follow a user
     * Follows a specified user account.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdFollowWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdFollowRequestCreation(id);
    }

    /**
     * Reject access to a user
     * Rejects access to a specified user account that is pending approval. If the user is not pending approval, returns the message &#x60;User does not have a pending request&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdRejectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdReject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/reject", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reject access to a user
     * Rejects access to a specified user account that is pending approval. If the user is not pending approval, returns the message &#x60;User does not have a pending request&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdReject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdRejectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Reject access to a user
     * Rejects access to a specified user account that is pending approval. If the user is not pending approval, returns the message &#x60;User does not have a pending request&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdRejectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdRejectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Reject access to a user
     * Rejects access to a specified user account that is pending approval. If the user is not pending approval, returns the message &#x60;User does not have a pending request&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdRejectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdRejectRequestCreation(id);
    }

    /**
     * Revoke a Support PIN for a user
     * Revokes a Support PIN for a specified user before its natural expiration. This immediately expires and removes the PIN. Administrators only. This feature was introduced in GitLab 17.11.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdSupportPinRevokeRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdSupportPinRevoke", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/support_pin/revoke", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a Support PIN for a user
     * Revokes a Support PIN for a specified user before its natural expiration. This immediately expires and removes the PIN. Administrators only. This feature was introduced in GitLab 17.11.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdSupportPinRevoke(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdSupportPinRevokeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Revoke a Support PIN for a user
     * Revokes a Support PIN for a specified user before its natural expiration. This immediately expires and removes the PIN. Administrators only. This feature was introduced in GitLab 17.11.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdSupportPinRevokeWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdSupportPinRevokeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Revoke a Support PIN for a user
     * Revokes a Support PIN for a specified user before its natural expiration. This immediately expires and removes the PIN. Administrators only. This feature was introduced in GitLab 17.11.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdSupportPinRevokeWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdSupportPinRevokeRequestCreation(id);
    }

    /**
     * Unban a user
     * Unbans a specified user account that was previously banned. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdUnbanRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdUnban", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/unban", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unban a user
     * Unbans a specified user account that was previously banned. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdUnban(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdUnbanRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unban a user
     * Unbans a specified user account that was previously banned. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdUnbanWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdUnbanRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unban a user
     * Unbans a specified user account that was previously banned. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdUnbanWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdUnbanRequestCreation(id);
    }

    /**
     * Unblock access to a user
     * Unblocks a specified user account that was previously blocked. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdUnblockRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdUnblock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/unblock", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unblock access to a user
     * Unblocks a specified user account that was previously blocked. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdUnblock(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdUnblockRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unblock access to a user
     * Unblocks a specified user account that was previously blocked. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdUnblockWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdUnblockRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unblock access to a user
     * Unblocks a specified user account that was previously blocked. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdUnblockWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdUnblockRequestCreation(id);
    }

    /**
     * Unfollow a user
     * Unfollows a specified user account.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdUnfollowRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdUnfollow", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/unfollow", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unfollow a user
     * Unfollows a specified user account.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return APIEntitiesUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUser postApiV4UsersIdUnfollow(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdUnfollowRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unfollow a user
     * Unfollows a specified user account.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseEntity&lt;APIEntitiesUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUser> postApiV4UsersIdUnfollowWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdUnfollowRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unfollow a user
     * Unfollows a specified user account.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdUnfollowWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4UsersIdUnfollowRequestCreation(id);
    }

    /**
     * Update your user preferences
     * Updates your user preferences.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4UserPreferencesRequest The putApiV4UserPreferencesRequest parameter
     * @return APIEntitiesUserPreferences
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4UserPreferencesRequestCreation(@jakarta.annotation.Nullable PutApiV4UserPreferencesRequest putApiV4UserPreferencesRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesUserPreferences> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/preferences", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4UserPreferencesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update your user preferences
     * Updates your user preferences.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4UserPreferencesRequest The putApiV4UserPreferencesRequest parameter
     * @return APIEntitiesUserPreferences
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserPreferences putApiV4UserPreferences(@jakarta.annotation.Nullable PutApiV4UserPreferencesRequest putApiV4UserPreferencesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPreferences> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserPreferencesRequestCreation(putApiV4UserPreferencesRequest).body(localVarReturnType);
    }

    /**
     * Update your user preferences
     * Updates your user preferences.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4UserPreferencesRequest The putApiV4UserPreferencesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesUserPreferences&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserPreferences> putApiV4UserPreferencesWithHttpInfo(@jakarta.annotation.Nullable PutApiV4UserPreferencesRequest putApiV4UserPreferencesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserPreferences> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserPreferencesRequestCreation(putApiV4UserPreferencesRequest).toEntity(localVarReturnType);
    }

    /**
     * Update your user preferences
     * Updates your user preferences.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4UserPreferencesRequest The putApiV4UserPreferencesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4UserPreferencesWithResponseSpec(@jakarta.annotation.Nullable PutApiV4UserPreferencesRequest putApiV4UserPreferencesRequest) throws RestClientResponseException {
        return putApiV4UserPreferencesRequestCreation(putApiV4UserPreferencesRequest);
    }

    /**
     * Set a user status
     * Sets the status of the currently authenticated user. Any parameters that are not passed are nullified. This operation is similar but distinct from the Update a user status operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4UserStatusRequest The putApiV4UserStatusRequest parameter
     * @return APIEntitiesUserStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4UserStatusRequestCreation(@jakarta.annotation.Nullable PutApiV4UserStatusRequest putApiV4UserStatusRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/status", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4UserStatusRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Set a user status
     * Sets the status of the currently authenticated user. Any parameters that are not passed are nullified. This operation is similar but distinct from the Update a user status operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4UserStatusRequest The putApiV4UserStatusRequest parameter
     * @return APIEntitiesUserStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserStatus putApiV4UserStatus(@jakarta.annotation.Nullable PutApiV4UserStatusRequest putApiV4UserStatusRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserStatusRequestCreation(putApiV4UserStatusRequest).body(localVarReturnType);
    }

    /**
     * Set a user status
     * Sets the status of the currently authenticated user. Any parameters that are not passed are nullified. This operation is similar but distinct from the Update a user status operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4UserStatusRequest The putApiV4UserStatusRequest parameter
     * @return ResponseEntity&lt;APIEntitiesUserStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserStatus> putApiV4UserStatusWithHttpInfo(@jakarta.annotation.Nullable PutApiV4UserStatusRequest putApiV4UserStatusRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserStatusRequestCreation(putApiV4UserStatusRequest).toEntity(localVarReturnType);
    }

    /**
     * Set a user status
     * Sets the status of the currently authenticated user. Any parameters that are not passed are nullified. This operation is similar but distinct from the Update a user status operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4UserStatusRequest The putApiV4UserStatusRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4UserStatusWithResponseSpec(@jakarta.annotation.Nullable PutApiV4UserStatusRequest putApiV4UserStatusRequest) throws RestClientResponseException {
        return putApiV4UserStatusRequestCreation(putApiV4UserStatusRequest);
    }

    /**
     * Update a user&#39;s credit_card_validation
     * Deprecated in 17.7
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param putApiV4UserUserIdCreditCardValidationRequest The putApiV4UserUserIdCreditCardValidationRequest parameter
     * @return APIEntitiesUserCreditCardValidations
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4UserUserIdCreditCardValidationRequestCreation(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nonnull PutApiV4UserUserIdCreditCardValidationRequest putApiV4UserUserIdCreditCardValidationRequest) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling putApiV4UserUserIdCreditCardValidation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4UserUserIdCreditCardValidationRequest' is set
        if (putApiV4UserUserIdCreditCardValidationRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4UserUserIdCreditCardValidationRequest' when calling putApiV4UserUserIdCreditCardValidation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesUserCreditCardValidations> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/{user_id}/credit_card_validation", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4UserUserIdCreditCardValidationRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a user&#39;s credit_card_validation
     * Deprecated in 17.7
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param putApiV4UserUserIdCreditCardValidationRequest The putApiV4UserUserIdCreditCardValidationRequest parameter
     * @return APIEntitiesUserCreditCardValidations
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public APIEntitiesUserCreditCardValidations putApiV4UserUserIdCreditCardValidation(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nonnull PutApiV4UserUserIdCreditCardValidationRequest putApiV4UserUserIdCreditCardValidationRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserCreditCardValidations> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserUserIdCreditCardValidationRequestCreation(userId, putApiV4UserUserIdCreditCardValidationRequest).body(localVarReturnType);
    }

    /**
     * Update a user&#39;s credit_card_validation
     * Deprecated in 17.7
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param putApiV4UserUserIdCreditCardValidationRequest The putApiV4UserUserIdCreditCardValidationRequest parameter
     * @return ResponseEntity&lt;APIEntitiesUserCreditCardValidations&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<APIEntitiesUserCreditCardValidations> putApiV4UserUserIdCreditCardValidationWithHttpInfo(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nonnull PutApiV4UserUserIdCreditCardValidationRequest putApiV4UserUserIdCreditCardValidationRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserCreditCardValidations> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserUserIdCreditCardValidationRequestCreation(userId, putApiV4UserUserIdCreditCardValidationRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a user&#39;s credit_card_validation
     * Deprecated in 17.7
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param putApiV4UserUserIdCreditCardValidationRequest The putApiV4UserUserIdCreditCardValidationRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec putApiV4UserUserIdCreditCardValidationWithResponseSpec(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nonnull PutApiV4UserUserIdCreditCardValidationRequest putApiV4UserUserIdCreditCardValidationRequest) throws RestClientResponseException {
        return putApiV4UserUserIdCreditCardValidationRequestCreation(userId, putApiV4UserUserIdCreditCardValidationRequest);
    }

    /**
     * Update a user
     * Updates the details of a specified user. If an administrator updates the password of another user, that user must change it when they next sign in. Administrators only. If &#x60;email&#x60; is a confirmed secondary email address of the user, it becomes the primary email address at once. Any other address is stored as unconfirmed and becomes the primary email address only after the user confirms it, unless &#x60;skip_reconfirmation&#x60; is &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param email Primary email address of the user.
     * @param password The password of the user
     * @param skipReconfirmation Flag indicating the account skips the confirmation by email. Default is &#x60;false&#x60;.
     * @param name The name of the user
     * @param username The username of the user
     * @param linkedin The LinkedIn username
     * @param twitter The Twitter username
     * @param discord The Discord user ID
     * @param websiteUrl The website of the user
     * @param github The GitHub username
     * @param organization The organization of the user. Empty string or nil clears the field.
     * @param projectsLimit The number of projects a user can create
     * @param externUid The external authentication provider UID. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param provider Name of the external authentication provider. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param bio The biography of the user
     * @param location The location of the user
     * @param pronouns The pronouns of the user
     * @param publicEmail The public email of the user. Must be a verified email address of the user.
     * @param commitEmail The commit email, _private for private commit email
     * @param admin Flag indicating the user is an administrator. Default is &#x60;false&#x60; for new users.
     * @param canCreateGroup Flag indicating the user can create top-level groups
     * @param external Flag indicating the user is an external user. For new users, the default comes from the instance setting for new external users.
     * @param avatar Avatar image for user
     * @param themeId The GitLab theme for the user
     * @param colorSchemeId The color scheme for the file viewer
     * @param privateProfile Flag indicating the user has a private profile. For new users, the default comes from the instance setting for new user profiles.
     * @param note Admin note for this user
     * @param viewDiffsFileByFile If &#x60;true&#x60;, the user sees only one file diff per page.
     * @param policyAdvancedEditor Flag indicating that advanced editor is enabled
     * @param sharedRunnersMinutesLimit Maximum number of monthly compute minutes for this user. &#x60;nil&#x60; inherits the instance default, &#x60;0&#x60; means unlimited, and a value greater than &#x60;0&#x60; sets the limit. Can be set by administrators only. Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this user. Premium and Ultimate only.
     * @param groupIdForSaml ID for group where SAML has been configured
     * @param auditor Flag indicating auditor status of the user. Default is &#x60;false&#x60; for new users. Premium and Ultimate only.
     * @return APIEntitiesUserWithAdmin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4UsersIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable String password, @jakarta.annotation.Nullable Boolean skipReconfirmation, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String linkedin, @jakarta.annotation.Nullable String twitter, @jakarta.annotation.Nullable String discord, @jakarta.annotation.Nullable String websiteUrl, @jakarta.annotation.Nullable String github, @jakarta.annotation.Nullable String organization, @jakarta.annotation.Nullable Integer projectsLimit, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String bio, @jakarta.annotation.Nullable String location, @jakarta.annotation.Nullable String pronouns, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String commitEmail, @jakarta.annotation.Nullable Boolean admin, @jakarta.annotation.Nullable Boolean canCreateGroup, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer themeId, @jakarta.annotation.Nullable Integer colorSchemeId, @jakarta.annotation.Nullable Boolean privateProfile, @jakarta.annotation.Nullable String note, @jakarta.annotation.Nullable Boolean viewDiffsFileByFile, @jakarta.annotation.Nullable Boolean policyAdvancedEditor, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer groupIdForSaml, @jakarta.annotation.Nullable Boolean auditor) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4UsersId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (email != null) {
            formParams.add("email", email);
        }
        if (password != null) {
            formParams.add("password", password);
        }
        if (skipReconfirmation != null) {
            formParams.add("skip_reconfirmation", skipReconfirmation);
        }
        if (name != null) {
            formParams.add("name", name);
        }
        if (username != null) {
            formParams.add("username", username);
        }
        if (linkedin != null) {
            formParams.add("linkedin", linkedin);
        }
        if (twitter != null) {
            formParams.add("twitter", twitter);
        }
        if (discord != null) {
            formParams.add("discord", discord);
        }
        if (websiteUrl != null) {
            formParams.add("website_url", websiteUrl);
        }
        if (github != null) {
            formParams.add("github", github);
        }
        if (organization != null) {
            formParams.add("organization", organization);
        }
        if (projectsLimit != null) {
            formParams.add("projects_limit", projectsLimit);
        }
        if (externUid != null) {
            formParams.add("extern_uid", externUid);
        }
        if (provider != null) {
            formParams.add("provider", provider);
        }
        if (bio != null) {
            formParams.add("bio", bio);
        }
        if (location != null) {
            formParams.add("location", location);
        }
        if (pronouns != null) {
            formParams.add("pronouns", pronouns);
        }
        if (publicEmail != null) {
            formParams.add("public_email", publicEmail);
        }
        if (commitEmail != null) {
            formParams.add("commit_email", commitEmail);
        }
        if (admin != null) {
            formParams.add("admin", admin);
        }
        if (canCreateGroup != null) {
            formParams.add("can_create_group", canCreateGroup);
        }
        if (external != null) {
            formParams.add("external", external);
        }
        if (avatar != null) {
            formParams.add("avatar", new FileSystemResource(avatar));
        }
        if (themeId != null) {
            formParams.add("theme_id", themeId);
        }
        if (colorSchemeId != null) {
            formParams.add("color_scheme_id", colorSchemeId);
        }
        if (privateProfile != null) {
            formParams.add("private_profile", privateProfile);
        }
        if (note != null) {
            formParams.add("note", note);
        }
        if (viewDiffsFileByFile != null) {
            formParams.add("view_diffs_file_by_file", viewDiffsFileByFile);
        }
        if (policyAdvancedEditor != null) {
            formParams.add("policy_advanced_editor", policyAdvancedEditor);
        }
        if (sharedRunnersMinutesLimit != null) {
            formParams.add("shared_runners_minutes_limit", sharedRunnersMinutesLimit);
        }
        if (extraSharedRunnersMinutesLimit != null) {
            formParams.add("extra_shared_runners_minutes_limit", extraSharedRunnersMinutesLimit);
        }
        if (groupIdForSaml != null) {
            formParams.add("group_id_for_saml", groupIdForSaml);
        }
        if (auditor != null) {
            formParams.add("auditor", auditor);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a user
     * Updates the details of a specified user. If an administrator updates the password of another user, that user must change it when they next sign in. Administrators only. If &#x60;email&#x60; is a confirmed secondary email address of the user, it becomes the primary email address at once. Any other address is stored as unconfirmed and becomes the primary email address only after the user confirms it, unless &#x60;skip_reconfirmation&#x60; is &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param email Primary email address of the user.
     * @param password The password of the user
     * @param skipReconfirmation Flag indicating the account skips the confirmation by email. Default is &#x60;false&#x60;.
     * @param name The name of the user
     * @param username The username of the user
     * @param linkedin The LinkedIn username
     * @param twitter The Twitter username
     * @param discord The Discord user ID
     * @param websiteUrl The website of the user
     * @param github The GitHub username
     * @param organization The organization of the user. Empty string or nil clears the field.
     * @param projectsLimit The number of projects a user can create
     * @param externUid The external authentication provider UID. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param provider Name of the external authentication provider. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param bio The biography of the user
     * @param location The location of the user
     * @param pronouns The pronouns of the user
     * @param publicEmail The public email of the user. Must be a verified email address of the user.
     * @param commitEmail The commit email, _private for private commit email
     * @param admin Flag indicating the user is an administrator. Default is &#x60;false&#x60; for new users.
     * @param canCreateGroup Flag indicating the user can create top-level groups
     * @param external Flag indicating the user is an external user. For new users, the default comes from the instance setting for new external users.
     * @param avatar Avatar image for user
     * @param themeId The GitLab theme for the user
     * @param colorSchemeId The color scheme for the file viewer
     * @param privateProfile Flag indicating the user has a private profile. For new users, the default comes from the instance setting for new user profiles.
     * @param note Admin note for this user
     * @param viewDiffsFileByFile If &#x60;true&#x60;, the user sees only one file diff per page.
     * @param policyAdvancedEditor Flag indicating that advanced editor is enabled
     * @param sharedRunnersMinutesLimit Maximum number of monthly compute minutes for this user. &#x60;nil&#x60; inherits the instance default, &#x60;0&#x60; means unlimited, and a value greater than &#x60;0&#x60; sets the limit. Can be set by administrators only. Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this user. Premium and Ultimate only.
     * @param groupIdForSaml ID for group where SAML has been configured
     * @param auditor Flag indicating auditor status of the user. Default is &#x60;false&#x60; for new users. Premium and Ultimate only.
     * @return APIEntitiesUserWithAdmin
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserWithAdmin putApiV4UsersId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable String password, @jakarta.annotation.Nullable Boolean skipReconfirmation, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String linkedin, @jakarta.annotation.Nullable String twitter, @jakarta.annotation.Nullable String discord, @jakarta.annotation.Nullable String websiteUrl, @jakarta.annotation.Nullable String github, @jakarta.annotation.Nullable String organization, @jakarta.annotation.Nullable Integer projectsLimit, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String bio, @jakarta.annotation.Nullable String location, @jakarta.annotation.Nullable String pronouns, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String commitEmail, @jakarta.annotation.Nullable Boolean admin, @jakarta.annotation.Nullable Boolean canCreateGroup, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer themeId, @jakarta.annotation.Nullable Integer colorSchemeId, @jakarta.annotation.Nullable Boolean privateProfile, @jakarta.annotation.Nullable String note, @jakarta.annotation.Nullable Boolean viewDiffsFileByFile, @jakarta.annotation.Nullable Boolean policyAdvancedEditor, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer groupIdForSaml, @jakarta.annotation.Nullable Boolean auditor) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UsersIdRequestCreation(id, email, password, skipReconfirmation, name, username, linkedin, twitter, discord, websiteUrl, github, organization, projectsLimit, externUid, provider, bio, location, pronouns, publicEmail, commitEmail, admin, canCreateGroup, external, avatar, themeId, colorSchemeId, privateProfile, note, viewDiffsFileByFile, policyAdvancedEditor, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, groupIdForSaml, auditor).body(localVarReturnType);
    }

    /**
     * Update a user
     * Updates the details of a specified user. If an administrator updates the password of another user, that user must change it when they next sign in. Administrators only. If &#x60;email&#x60; is a confirmed secondary email address of the user, it becomes the primary email address at once. Any other address is stored as unconfirmed and becomes the primary email address only after the user confirms it, unless &#x60;skip_reconfirmation&#x60; is &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param email Primary email address of the user.
     * @param password The password of the user
     * @param skipReconfirmation Flag indicating the account skips the confirmation by email. Default is &#x60;false&#x60;.
     * @param name The name of the user
     * @param username The username of the user
     * @param linkedin The LinkedIn username
     * @param twitter The Twitter username
     * @param discord The Discord user ID
     * @param websiteUrl The website of the user
     * @param github The GitHub username
     * @param organization The organization of the user. Empty string or nil clears the field.
     * @param projectsLimit The number of projects a user can create
     * @param externUid The external authentication provider UID. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param provider Name of the external authentication provider. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param bio The biography of the user
     * @param location The location of the user
     * @param pronouns The pronouns of the user
     * @param publicEmail The public email of the user. Must be a verified email address of the user.
     * @param commitEmail The commit email, _private for private commit email
     * @param admin Flag indicating the user is an administrator. Default is &#x60;false&#x60; for new users.
     * @param canCreateGroup Flag indicating the user can create top-level groups
     * @param external Flag indicating the user is an external user. For new users, the default comes from the instance setting for new external users.
     * @param avatar Avatar image for user
     * @param themeId The GitLab theme for the user
     * @param colorSchemeId The color scheme for the file viewer
     * @param privateProfile Flag indicating the user has a private profile. For new users, the default comes from the instance setting for new user profiles.
     * @param note Admin note for this user
     * @param viewDiffsFileByFile If &#x60;true&#x60;, the user sees only one file diff per page.
     * @param policyAdvancedEditor Flag indicating that advanced editor is enabled
     * @param sharedRunnersMinutesLimit Maximum number of monthly compute minutes for this user. &#x60;nil&#x60; inherits the instance default, &#x60;0&#x60; means unlimited, and a value greater than &#x60;0&#x60; sets the limit. Can be set by administrators only. Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this user. Premium and Ultimate only.
     * @param groupIdForSaml ID for group where SAML has been configured
     * @param auditor Flag indicating auditor status of the user. Default is &#x60;false&#x60; for new users. Premium and Ultimate only.
     * @return ResponseEntity&lt;APIEntitiesUserWithAdmin&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserWithAdmin> putApiV4UsersIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable String password, @jakarta.annotation.Nullable Boolean skipReconfirmation, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String linkedin, @jakarta.annotation.Nullable String twitter, @jakarta.annotation.Nullable String discord, @jakarta.annotation.Nullable String websiteUrl, @jakarta.annotation.Nullable String github, @jakarta.annotation.Nullable String organization, @jakarta.annotation.Nullable Integer projectsLimit, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String bio, @jakarta.annotation.Nullable String location, @jakarta.annotation.Nullable String pronouns, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String commitEmail, @jakarta.annotation.Nullable Boolean admin, @jakarta.annotation.Nullable Boolean canCreateGroup, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer themeId, @jakarta.annotation.Nullable Integer colorSchemeId, @jakarta.annotation.Nullable Boolean privateProfile, @jakarta.annotation.Nullable String note, @jakarta.annotation.Nullable Boolean viewDiffsFileByFile, @jakarta.annotation.Nullable Boolean policyAdvancedEditor, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer groupIdForSaml, @jakarta.annotation.Nullable Boolean auditor) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserWithAdmin> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UsersIdRequestCreation(id, email, password, skipReconfirmation, name, username, linkedin, twitter, discord, websiteUrl, github, organization, projectsLimit, externUid, provider, bio, location, pronouns, publicEmail, commitEmail, admin, canCreateGroup, external, avatar, themeId, colorSchemeId, privateProfile, note, viewDiffsFileByFile, policyAdvancedEditor, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, groupIdForSaml, auditor).toEntity(localVarReturnType);
    }

    /**
     * Update a user
     * Updates the details of a specified user. If an administrator updates the password of another user, that user must change it when they next sign in. Administrators only. If &#x60;email&#x60; is a confirmed secondary email address of the user, it becomes the primary email address at once. Any other address is stored as unconfirmed and becomes the primary email address only after the user confirms it, unless &#x60;skip_reconfirmation&#x60; is &#x60;true&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param email Primary email address of the user.
     * @param password The password of the user
     * @param skipReconfirmation Flag indicating the account skips the confirmation by email. Default is &#x60;false&#x60;.
     * @param name The name of the user
     * @param username The username of the user
     * @param linkedin The LinkedIn username
     * @param twitter The Twitter username
     * @param discord The Discord user ID
     * @param websiteUrl The website of the user
     * @param github The GitHub username
     * @param organization The organization of the user. Empty string or nil clears the field.
     * @param projectsLimit The number of projects a user can create
     * @param externUid The external authentication provider UID. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param provider Name of the external authentication provider. All or none of &#x60;extern_uid&#x60;, &#x60;provider&#x60; must be provided.
     * @param bio The biography of the user
     * @param location The location of the user
     * @param pronouns The pronouns of the user
     * @param publicEmail The public email of the user. Must be a verified email address of the user.
     * @param commitEmail The commit email, _private for private commit email
     * @param admin Flag indicating the user is an administrator. Default is &#x60;false&#x60; for new users.
     * @param canCreateGroup Flag indicating the user can create top-level groups
     * @param external Flag indicating the user is an external user. For new users, the default comes from the instance setting for new external users.
     * @param avatar Avatar image for user
     * @param themeId The GitLab theme for the user
     * @param colorSchemeId The color scheme for the file viewer
     * @param privateProfile Flag indicating the user has a private profile. For new users, the default comes from the instance setting for new user profiles.
     * @param note Admin note for this user
     * @param viewDiffsFileByFile If &#x60;true&#x60;, the user sees only one file diff per page.
     * @param policyAdvancedEditor Flag indicating that advanced editor is enabled
     * @param sharedRunnersMinutesLimit Maximum number of monthly compute minutes for this user. &#x60;nil&#x60; inherits the instance default, &#x60;0&#x60; means unlimited, and a value greater than &#x60;0&#x60; sets the limit. Can be set by administrators only. Premium and Ultimate only.
     * @param extraSharedRunnersMinutesLimit (admin-only) Extra compute minutes quota for this user. Premium and Ultimate only.
     * @param groupIdForSaml ID for group where SAML has been configured
     * @param auditor Flag indicating auditor status of the user. Default is &#x60;false&#x60; for new users. Premium and Ultimate only.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4UsersIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable String password, @jakarta.annotation.Nullable Boolean skipReconfirmation, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String username, @jakarta.annotation.Nullable String linkedin, @jakarta.annotation.Nullable String twitter, @jakarta.annotation.Nullable String discord, @jakarta.annotation.Nullable String websiteUrl, @jakarta.annotation.Nullable String github, @jakarta.annotation.Nullable String organization, @jakarta.annotation.Nullable Integer projectsLimit, @jakarta.annotation.Nullable String externUid, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String bio, @jakarta.annotation.Nullable String location, @jakarta.annotation.Nullable String pronouns, @jakarta.annotation.Nullable String publicEmail, @jakarta.annotation.Nullable String commitEmail, @jakarta.annotation.Nullable Boolean admin, @jakarta.annotation.Nullable Boolean canCreateGroup, @jakarta.annotation.Nullable Boolean external, @jakarta.annotation.Nullable File avatar, @jakarta.annotation.Nullable Integer themeId, @jakarta.annotation.Nullable Integer colorSchemeId, @jakarta.annotation.Nullable Boolean privateProfile, @jakarta.annotation.Nullable String note, @jakarta.annotation.Nullable Boolean viewDiffsFileByFile, @jakarta.annotation.Nullable Boolean policyAdvancedEditor, @jakarta.annotation.Nullable Integer sharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer extraSharedRunnersMinutesLimit, @jakarta.annotation.Nullable Integer groupIdForSaml, @jakarta.annotation.Nullable Boolean auditor) throws RestClientResponseException {
        return putApiV4UsersIdRequestCreation(id, email, password, skipReconfirmation, name, username, linkedin, twitter, discord, websiteUrl, github, organization, projectsLimit, externUid, provider, bio, location, pronouns, publicEmail, commitEmail, admin, canCreateGroup, external, avatar, themeId, colorSchemeId, privateProfile, note, viewDiffsFileByFile, policyAdvancedEditor, sharedRunnersMinutesLimit, extraSharedRunnersMinutesLimit, groupIdForSaml, auditor);
    }
}
