package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.UserNonWorkingTimeCollectionModel;
import ai.gebo.openproject.client.model.UserNonWorkingTimeModel;
import ai.gebo.openproject.client.model.UserWorkingHoursCollectionModel;
import ai.gebo.openproject.client.model.UserWorkingHoursModel;

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
public class UserWorkingTimesApi {
    private ApiClient apiClient;

    public UserWorkingTimesApi() {
        this(new ApiClient());
    }

    public UserWorkingTimesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create a personal non-working day for a user
     * Creates a personal non-working time range for the given user.  The date range must not overlap with an existing non-working time record for the same user.  **Required permissions:** - Administrators can create non-working days for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can create records for themselves. - Users with the global &#x60;manage_working_times&#x60; permission can create non-working days for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the date range overlaps with an existing non-working time record for the user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param userNonWorkingTimeModel The userNonWorkingTimeModel parameter
     * @return UserNonWorkingTimeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createUserNonWorkingTimeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserNonWorkingTimeModel userNonWorkingTimeModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createUserNonWorkingTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/non_working_times", HttpMethod.POST, pathParams, localVarQueryParams, userNonWorkingTimeModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a personal non-working day for a user
     * Creates a personal non-working time range for the given user.  The date range must not overlap with an existing non-working time record for the same user.  **Required permissions:** - Administrators can create non-working days for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can create records for themselves. - Users with the global &#x60;manage_working_times&#x60; permission can create non-working days for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the date range overlaps with an existing non-working time record for the user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param userNonWorkingTimeModel The userNonWorkingTimeModel parameter
     * @return UserNonWorkingTimeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserNonWorkingTimeModel createUserNonWorkingTime(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserNonWorkingTimeModel userNonWorkingTimeModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createUserNonWorkingTimeRequestCreation(id, userNonWorkingTimeModel).body(localVarReturnType);
    }

    /**
     * Create a personal non-working day for a user
     * Creates a personal non-working time range for the given user.  The date range must not overlap with an existing non-working time record for the same user.  **Required permissions:** - Administrators can create non-working days for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can create records for themselves. - Users with the global &#x60;manage_working_times&#x60; permission can create non-working days for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the date range overlaps with an existing non-working time record for the user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param userNonWorkingTimeModel The userNonWorkingTimeModel parameter
     * @return ResponseEntity&lt;UserNonWorkingTimeModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserNonWorkingTimeModel> createUserNonWorkingTimeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserNonWorkingTimeModel userNonWorkingTimeModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createUserNonWorkingTimeRequestCreation(id, userNonWorkingTimeModel).toEntity(localVarReturnType);
    }

    /**
     * Create a personal non-working day for a user
     * Creates a personal non-working time range for the given user.  The date range must not overlap with an existing non-working time record for the same user.  **Required permissions:** - Administrators can create non-working days for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can create records for themselves. - Users with the global &#x60;manage_working_times&#x60; permission can create non-working days for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the date range overlaps with an existing non-working time record for the user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param userNonWorkingTimeModel The userNonWorkingTimeModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createUserNonWorkingTimeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserNonWorkingTimeModel userNonWorkingTimeModel) throws RestClientResponseException {
        return createUserNonWorkingTimeRequestCreation(id, userNonWorkingTimeModel);
    }

    /**
     * Create a working hours record for a user
     * Creates a new working hours record for the given user, effective from the given date.  **Required permissions:** - Administrators can create working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can create records for themselves. - Users with the global &#x60;manage_working_times&#x60; permission can create working hours records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param userWorkingHoursModel The userWorkingHoursModel parameter
     * @return UserWorkingHoursModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createUserWorkingHoursRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserWorkingHoursModel userWorkingHoursModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createUserWorkingHours", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/working_hours", HttpMethod.POST, pathParams, localVarQueryParams, userWorkingHoursModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a working hours record for a user
     * Creates a new working hours record for the given user, effective from the given date.  **Required permissions:** - Administrators can create working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can create records for themselves. - Users with the global &#x60;manage_working_times&#x60; permission can create working hours records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param userWorkingHoursModel The userWorkingHoursModel parameter
     * @return UserWorkingHoursModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserWorkingHoursModel createUserWorkingHours(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserWorkingHoursModel userWorkingHoursModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createUserWorkingHoursRequestCreation(id, userWorkingHoursModel).body(localVarReturnType);
    }

    /**
     * Create a working hours record for a user
     * Creates a new working hours record for the given user, effective from the given date.  **Required permissions:** - Administrators can create working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can create records for themselves. - Users with the global &#x60;manage_working_times&#x60; permission can create working hours records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param userWorkingHoursModel The userWorkingHoursModel parameter
     * @return ResponseEntity&lt;UserWorkingHoursModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserWorkingHoursModel> createUserWorkingHoursWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserWorkingHoursModel userWorkingHoursModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createUserWorkingHoursRequestCreation(id, userWorkingHoursModel).toEntity(localVarReturnType);
    }

    /**
     * Create a working hours record for a user
     * Creates a new working hours record for the given user, effective from the given date.  **Required permissions:** - Administrators can create working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can create records for themselves. - Users with the global &#x60;manage_working_times&#x60; permission can create working hours records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param userWorkingHoursModel The userWorkingHoursModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createUserWorkingHoursWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable UserWorkingHoursModel userWorkingHoursModel) throws RestClientResponseException {
        return createUserWorkingHoursRequestCreation(id, userWorkingHoursModel);
    }

    /**
     * Delete a personal non-working time record
     * Removes the personal non-working time record for the given user.  **Required permissions:** - Administrators can delete non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can delete their own records. - Users with the global &#x60;manage_working_times&#x60; permission can delete non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>204</b> - No Content. The record was deleted successfully.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteUserNonWorkingTimeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteUserNonWorkingTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'nonWorkingTimeId' is set
        if (nonWorkingTimeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'nonWorkingTimeId' when calling deleteUserNonWorkingTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("non_working_time_id", nonWorkingTimeId);

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
        return apiClient.invokeAPI("/api/v3/users/{id}/non_working_times/{non_working_time_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a personal non-working time record
     * Removes the personal non-working time record for the given user.  **Required permissions:** - Administrators can delete non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can delete their own records. - Users with the global &#x60;manage_working_times&#x60; permission can delete non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>204</b> - No Content. The record was deleted successfully.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteUserNonWorkingTime(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId).body(localVarReturnType);
    }

    /**
     * Delete a personal non-working time record
     * Removes the personal non-working time record for the given user.  **Required permissions:** - Administrators can delete non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can delete their own records. - Users with the global &#x60;manage_working_times&#x60; permission can delete non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>204</b> - No Content. The record was deleted successfully.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserNonWorkingTimeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId).toEntity(localVarReturnType);
    }

    /**
     * Delete a personal non-working time record
     * Removes the personal non-working time record for the given user.  **Required permissions:** - Administrators can delete non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can delete their own records. - Users with the global &#x60;manage_working_times&#x60; permission can delete non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>204</b> - No Content. The record was deleted successfully.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteUserNonWorkingTimeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId) throws RestClientResponseException {
        return deleteUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId);
    }

    /**
     * Delete a working hours record
     * Deletes the given working hours record.  **Required permissions:** - Administrators can delete working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can delete their own records. - Users with the global &#x60;manage_working_times&#x60; permission can delete working hours records for any user.
     * <p><b>204</b> - No Content. The record was deleted successfully.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteUserWorkingHoursRecordRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteUserWorkingHoursRecord", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'workingHoursId' is set
        if (workingHoursId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workingHoursId' when calling deleteUserWorkingHoursRecord", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("working_hours_id", workingHoursId);

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
        return apiClient.invokeAPI("/api/v3/users/{id}/working_hours/{working_hours_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a working hours record
     * Deletes the given working hours record.  **Required permissions:** - Administrators can delete working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can delete their own records. - Users with the global &#x60;manage_working_times&#x60; permission can delete working hours records for any user.
     * <p><b>204</b> - No Content. The record was deleted successfully.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteUserWorkingHoursRecord(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteUserWorkingHoursRecordRequestCreation(id, workingHoursId).body(localVarReturnType);
    }

    /**
     * Delete a working hours record
     * Deletes the given working hours record.  **Required permissions:** - Administrators can delete working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can delete their own records. - Users with the global &#x60;manage_working_times&#x60; permission can delete working hours records for any user.
     * <p><b>204</b> - No Content. The record was deleted successfully.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUserWorkingHoursRecordWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteUserWorkingHoursRecordRequestCreation(id, workingHoursId).toEntity(localVarReturnType);
    }

    /**
     * Delete a working hours record
     * Deletes the given working hours record.  **Required permissions:** - Administrators can delete working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can delete their own records. - Users with the global &#x60;manage_working_times&#x60; permission can delete working hours records for any user.
     * <p><b>204</b> - No Content. The record was deleted successfully.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteUserWorkingHoursRecordWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId) throws RestClientResponseException {
        return deleteUserWorkingHoursRecordRequestCreation(id, workingHoursId);
    }

    /**
     * View a personal non-working time record
     * Returns a single personal non-working time record for the given user.  **Required permissions:** - Administrators can view non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can view their own records. - Users with the global &#x60;manage_working_times&#x60; permission can view non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @return UserNonWorkingTimeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getUserNonWorkingTimeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getUserNonWorkingTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'nonWorkingTimeId' is set
        if (nonWorkingTimeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'nonWorkingTimeId' when calling getUserNonWorkingTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("non_working_time_id", nonWorkingTimeId);

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

        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/non_working_times/{non_working_time_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View a personal non-working time record
     * Returns a single personal non-working time record for the given user.  **Required permissions:** - Administrators can view non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can view their own records. - Users with the global &#x60;manage_working_times&#x60; permission can view non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @return UserNonWorkingTimeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserNonWorkingTimeModel getUserNonWorkingTime(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId) throws RestClientResponseException {
        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId).body(localVarReturnType);
    }

    /**
     * View a personal non-working time record
     * Returns a single personal non-working time record for the given user.  **Required permissions:** - Administrators can view non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can view their own records. - Users with the global &#x60;manage_working_times&#x60; permission can view non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @return ResponseEntity&lt;UserNonWorkingTimeModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserNonWorkingTimeModel> getUserNonWorkingTimeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId) throws RestClientResponseException {
        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId).toEntity(localVarReturnType);
    }

    /**
     * View a personal non-working time record
     * Returns a single personal non-working time record for the given user.  **Required permissions:** - Administrators can view non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can view their own records. - Users with the global &#x60;manage_working_times&#x60; permission can view non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getUserNonWorkingTimeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId) throws RestClientResponseException {
        return getUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId);
    }

    /**
     * View a working hours record
     * Returns a single working hours record for the given user.  **Required permissions:** - Administrators can view working hours records for any user. - Users with the global &#x60;manage_working_times&#x60; permission can view working hours for any user. - Any user can view their own working hours records.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @return UserWorkingHoursModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getUserWorkingHoursRecordRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getUserWorkingHoursRecord", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'workingHoursId' is set
        if (workingHoursId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workingHoursId' when calling getUserWorkingHoursRecord", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("working_hours_id", workingHoursId);

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

        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/working_hours/{working_hours_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View a working hours record
     * Returns a single working hours record for the given user.  **Required permissions:** - Administrators can view working hours records for any user. - Users with the global &#x60;manage_working_times&#x60; permission can view working hours for any user. - Any user can view their own working hours records.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @return UserWorkingHoursModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserWorkingHoursModel getUserWorkingHoursRecord(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId) throws RestClientResponseException {
        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserWorkingHoursRecordRequestCreation(id, workingHoursId).body(localVarReturnType);
    }

    /**
     * View a working hours record
     * Returns a single working hours record for the given user.  **Required permissions:** - Administrators can view working hours records for any user. - Users with the global &#x60;manage_working_times&#x60; permission can view working hours for any user. - Any user can view their own working hours records.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @return ResponseEntity&lt;UserWorkingHoursModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserWorkingHoursModel> getUserWorkingHoursRecordWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId) throws RestClientResponseException {
        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getUserWorkingHoursRecordRequestCreation(id, workingHoursId).toEntity(localVarReturnType);
    }

    /**
     * View a working hours record
     * Returns a single working hours record for the given user.  **Required permissions:** - Administrators can view working hours records for any user. - Users with the global &#x60;manage_working_times&#x60; permission can view working hours for any user. - Any user can view their own working hours records.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getUserWorkingHoursRecordWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId) throws RestClientResponseException {
        return getUserWorkingHoursRecordRequestCreation(id, workingHoursId);
    }

    /**
     * List personal non-working times for a user
     * Returns all personal non-working times for the given user, ordered by start date ascending.  Personal non-working times mark date ranges as non-working for a user (e.g., a local holiday or personal day off not covered by the system-wide non-working days).  **Required permissions:** - Administrators can view non-working days for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can view their own records. - Users with the global &#x60;manage_working_times&#x60; permission can view non-working days for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param year Filter results to the given year. Defaults to the current year if not provided.
     * @return UserNonWorkingTimeCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listUserNonWorkingTimesRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer year) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listUserNonWorkingTimes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "year", year));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<UserNonWorkingTimeCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/non_working_times", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List personal non-working times for a user
     * Returns all personal non-working times for the given user, ordered by start date ascending.  Personal non-working times mark date ranges as non-working for a user (e.g., a local holiday or personal day off not covered by the system-wide non-working days).  **Required permissions:** - Administrators can view non-working days for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can view their own records. - Users with the global &#x60;manage_working_times&#x60; permission can view non-working days for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param year Filter results to the given year. Defaults to the current year if not provided.
     * @return UserNonWorkingTimeCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserNonWorkingTimeCollectionModel listUserNonWorkingTimes(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer year) throws RestClientResponseException {
        ParameterizedTypeReference<UserNonWorkingTimeCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listUserNonWorkingTimesRequestCreation(id, year).body(localVarReturnType);
    }

    /**
     * List personal non-working times for a user
     * Returns all personal non-working times for the given user, ordered by start date ascending.  Personal non-working times mark date ranges as non-working for a user (e.g., a local holiday or personal day off not covered by the system-wide non-working days).  **Required permissions:** - Administrators can view non-working days for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can view their own records. - Users with the global &#x60;manage_working_times&#x60; permission can view non-working days for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param year Filter results to the given year. Defaults to the current year if not provided.
     * @return ResponseEntity&lt;UserNonWorkingTimeCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserNonWorkingTimeCollectionModel> listUserNonWorkingTimesWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer year) throws RestClientResponseException {
        ParameterizedTypeReference<UserNonWorkingTimeCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listUserNonWorkingTimesRequestCreation(id, year).toEntity(localVarReturnType);
    }

    /**
     * List personal non-working times for a user
     * Returns all personal non-working times for the given user, ordered by start date ascending.  Personal non-working times mark date ranges as non-working for a user (e.g., a local holiday or personal day off not covered by the system-wide non-working days).  **Required permissions:** - Administrators can view non-working days for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can view their own records. - Users with the global &#x60;manage_working_times&#x60; permission can view non-working days for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param year Filter results to the given year. Defaults to the current year if not provided.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listUserNonWorkingTimesWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer year) throws RestClientResponseException {
        return listUserNonWorkingTimesRequestCreation(id, year);
    }

    /**
     * List working hours for a user
     * Returns all working hours records for the given user, ordered by &#x60;validFrom&#x60; descending.  Multiple records may exist for a user; each represents a period of their working time configuration. The most recently effective record (the one with the latest &#x60;validFrom&#x60; that is not in the future) is used for capacity calculations.  **Required permissions:** - Administrators can view working hours for any user. - Users with the global &#x60;manage_working_times&#x60; permission can view working hours for any user. - Any user can view their own working hours records.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @return UserWorkingHoursCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listUserWorkingHoursRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listUserWorkingHours", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<UserWorkingHoursCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/working_hours", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List working hours for a user
     * Returns all working hours records for the given user, ordered by &#x60;validFrom&#x60; descending.  Multiple records may exist for a user; each represents a period of their working time configuration. The most recently effective record (the one with the latest &#x60;validFrom&#x60; that is not in the future) is used for capacity calculations.  **Required permissions:** - Administrators can view working hours for any user. - Users with the global &#x60;manage_working_times&#x60; permission can view working hours for any user. - Any user can view their own working hours records.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @return UserWorkingHoursCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserWorkingHoursCollectionModel listUserWorkingHours(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<UserWorkingHoursCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listUserWorkingHoursRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List working hours for a user
     * Returns all working hours records for the given user, ordered by &#x60;validFrom&#x60; descending.  Multiple records may exist for a user; each represents a period of their working time configuration. The most recently effective record (the one with the latest &#x60;validFrom&#x60; that is not in the future) is used for capacity calculations.  **Required permissions:** - Administrators can view working hours for any user. - Users with the global &#x60;manage_working_times&#x60; permission can view working hours for any user. - Any user can view their own working hours records.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @return ResponseEntity&lt;UserWorkingHoursCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserWorkingHoursCollectionModel> listUserWorkingHoursWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<UserWorkingHoursCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listUserWorkingHoursRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List working hours for a user
     * Returns all working hours records for the given user, ordered by &#x60;validFrom&#x60; descending.  Multiple records may exist for a user; each represents a period of their working time configuration. The most recently effective record (the one with the latest &#x60;validFrom&#x60; that is not in the future) is used for capacity calculations.  **Required permissions:** - Administrators can view working hours for any user. - Users with the global &#x60;manage_working_times&#x60; permission can view working hours for any user. - Any user can view their own working hours records.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>404</b> - Returned if the user does not exist or is not visible to the requesting user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listUserWorkingHoursWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return listUserWorkingHoursRequestCreation(id);
    }

    /**
     * Update a personal non-working time record
     * Updates the given personal non-working time record.  **Required permissions:** - Administrators can update non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can update their own records. - Users with the global &#x60;manage_working_times&#x60; permission can update non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the date range overlaps with an existing non-working time record for the user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @param userNonWorkingTimeModel The userNonWorkingTimeModel parameter
     * @return UserNonWorkingTimeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateUserNonWorkingTimeRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId, @jakarta.annotation.Nullable UserNonWorkingTimeModel userNonWorkingTimeModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateUserNonWorkingTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'nonWorkingTimeId' is set
        if (nonWorkingTimeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'nonWorkingTimeId' when calling updateUserNonWorkingTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("non_working_time_id", nonWorkingTimeId);

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

        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/non_working_times/{non_working_time_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, userNonWorkingTimeModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a personal non-working time record
     * Updates the given personal non-working time record.  **Required permissions:** - Administrators can update non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can update their own records. - Users with the global &#x60;manage_working_times&#x60; permission can update non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the date range overlaps with an existing non-working time record for the user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @param userNonWorkingTimeModel The userNonWorkingTimeModel parameter
     * @return UserNonWorkingTimeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserNonWorkingTimeModel updateUserNonWorkingTime(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId, @jakarta.annotation.Nullable UserNonWorkingTimeModel userNonWorkingTimeModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId, userNonWorkingTimeModel).body(localVarReturnType);
    }

    /**
     * Update a personal non-working time record
     * Updates the given personal non-working time record.  **Required permissions:** - Administrators can update non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can update their own records. - Users with the global &#x60;manage_working_times&#x60; permission can update non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the date range overlaps with an existing non-working time record for the user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @param userNonWorkingTimeModel The userNonWorkingTimeModel parameter
     * @return ResponseEntity&lt;UserNonWorkingTimeModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserNonWorkingTimeModel> updateUserNonWorkingTimeWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId, @jakarta.annotation.Nullable UserNonWorkingTimeModel userNonWorkingTimeModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserNonWorkingTimeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId, userNonWorkingTimeModel).toEntity(localVarReturnType);
    }

    /**
     * Update a personal non-working time record
     * Updates the given personal non-working time record.  **Required permissions:** - Administrators can update non-working time records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can update their own records. - Users with the global &#x60;manage_working_times&#x60; permission can update non-working time records for any user.  Use &#x60;me&#x60; as the &#x60;id&#x60; to reference the current user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or non-working time record does not exist, or if the requesting user does not have permission to view it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the date range overlaps with an existing non-working time record for the user.
     * @param id User id. Use &#x60;me&#x60; to reference the current user.
     * @param nonWorkingTimeId The id of the personal non-working time record.
     * @param userNonWorkingTimeModel The userNonWorkingTimeModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateUserNonWorkingTimeWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer nonWorkingTimeId, @jakarta.annotation.Nullable UserNonWorkingTimeModel userNonWorkingTimeModel) throws RestClientResponseException {
        return updateUserNonWorkingTimeRequestCreation(id, nonWorkingTimeId, userNonWorkingTimeModel);
    }

    /**
     * Update a working hours record
     * Updates the given working hours record.  Only records that have not yet taken effect (i.e., &#x60;validFrom&#x60; is in the future) can be updated. Attempting to update a record that is already in effect will return a &#x60;422&#x60; error.  **Required permissions:** - Administrators can update working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can update their own records. - Users with the global &#x60;manage_working_times&#x60; permission can update working hours for any user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the record has already taken effect and cannot be modified.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @param userWorkingHoursModel The userWorkingHoursModel parameter
     * @return UserWorkingHoursModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateUserWorkingHoursRecordRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId, @jakarta.annotation.Nullable UserWorkingHoursModel userWorkingHoursModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateUserWorkingHoursRecord", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'workingHoursId' is set
        if (workingHoursId == null) {
            throw new RestClientResponseException("Missing the required parameter 'workingHoursId' when calling updateUserWorkingHoursRecord", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("working_hours_id", workingHoursId);

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

        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/users/{id}/working_hours/{working_hours_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, userWorkingHoursModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a working hours record
     * Updates the given working hours record.  Only records that have not yet taken effect (i.e., &#x60;validFrom&#x60; is in the future) can be updated. Attempting to update a record that is already in effect will return a &#x60;422&#x60; error.  **Required permissions:** - Administrators can update working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can update their own records. - Users with the global &#x60;manage_working_times&#x60; permission can update working hours for any user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the record has already taken effect and cannot be modified.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @param userWorkingHoursModel The userWorkingHoursModel parameter
     * @return UserWorkingHoursModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public UserWorkingHoursModel updateUserWorkingHoursRecord(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId, @jakarta.annotation.Nullable UserWorkingHoursModel userWorkingHoursModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateUserWorkingHoursRecordRequestCreation(id, workingHoursId, userWorkingHoursModel).body(localVarReturnType);
    }

    /**
     * Update a working hours record
     * Updates the given working hours record.  Only records that have not yet taken effect (i.e., &#x60;validFrom&#x60; is in the future) can be updated. Attempting to update a record that is already in effect will return a &#x60;422&#x60; error.  **Required permissions:** - Administrators can update working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can update their own records. - Users with the global &#x60;manage_working_times&#x60; permission can update working hours for any user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the record has already taken effect and cannot be modified.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @param userWorkingHoursModel The userWorkingHoursModel parameter
     * @return ResponseEntity&lt;UserWorkingHoursModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<UserWorkingHoursModel> updateUserWorkingHoursRecordWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId, @jakarta.annotation.Nullable UserWorkingHoursModel userWorkingHoursModel) throws RestClientResponseException {
        ParameterizedTypeReference<UserWorkingHoursModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateUserWorkingHoursRecordRequestCreation(id, workingHoursId, userWorkingHoursModel).toEntity(localVarReturnType);
    }

    /**
     * Update a working hours record
     * Updates the given working hours record.  Only records that have not yet taken effect (i.e., &#x60;validFrom&#x60; is in the future) can be updated. Attempting to update a record that is already in effect will return a &#x60;422&#x60; error.  **Required permissions:** - Administrators can update working hours records for any user. - Users with the global &#x60;manage_own_working_times&#x60; permission can update their own records. - Users with the global &#x60;manage_working_times&#x60; permission can update working hours for any user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>401</b> - Returned if the client is not authenticated.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;manage_working_times&#x60; globally (for other users) or &#x60;manage_own_working_times&#x60; globally (for own records).
     * <p><b>404</b> - Returned if the user or working hours record does not exist, or if the requesting user does not have permission to view it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if the record has already taken effect and cannot be modified.
     * @param id User id.
     * @param workingHoursId Working hours record id.
     * @param userWorkingHoursModel The userWorkingHoursModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateUserWorkingHoursRecordWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer workingHoursId, @jakarta.annotation.Nullable UserWorkingHoursModel userWorkingHoursModel) throws RestClientResponseException {
        return updateUserWorkingHoursRecordRequestCreation(id, workingHoursId, userWorkingHoursModel);
    }
}
