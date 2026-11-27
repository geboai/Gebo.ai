package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.CreateWorkPackageReminderRequest;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.ListReminders200Response;
import ai.gebo.openproject.client.model.ReminderModel;
import ai.gebo.openproject.client.model.UpdateReminderRequest;

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
public class RemindersApi {
    private ApiClient apiClient;

    public RemindersApi() {
        this(new ApiClient());
    }

    public RemindersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create a work package reminder
     * Creates a new reminder for the specified work package.  **Note:** A user can only have one **active** reminder at a time for a given work package.
     * <p><b>201</b> - Reminder created successfully
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>409</b> - Returned if the user already has an active reminder for this work package.  **Error message**: You can only set one reminder at a time for a work package. Please delete or update the existing reminder.
     * @param id Work package id
     * @param createWorkPackageReminderRequest The createWorkPackageReminderRequest parameter
     * @return ReminderModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createWorkPackageReminderRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull CreateWorkPackageReminderRequest createWorkPackageReminderRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createWorkPackageReminder", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createWorkPackageReminderRequest' is set
        if (createWorkPackageReminderRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createWorkPackageReminderRequest' when calling createWorkPackageReminder", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/reminders", HttpMethod.POST, pathParams, localVarQueryParams, createWorkPackageReminderRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a work package reminder
     * Creates a new reminder for the specified work package.  **Note:** A user can only have one **active** reminder at a time for a given work package.
     * <p><b>201</b> - Reminder created successfully
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>409</b> - Returned if the user already has an active reminder for this work package.  **Error message**: You can only set one reminder at a time for a work package. Please delete or update the existing reminder.
     * @param id Work package id
     * @param createWorkPackageReminderRequest The createWorkPackageReminderRequest parameter
     * @return ReminderModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReminderModel createWorkPackageReminder(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull CreateWorkPackageReminderRequest createWorkPackageReminderRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageReminderRequestCreation(id, createWorkPackageReminderRequest).body(localVarReturnType);
    }

    /**
     * Create a work package reminder
     * Creates a new reminder for the specified work package.  **Note:** A user can only have one **active** reminder at a time for a given work package.
     * <p><b>201</b> - Reminder created successfully
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>409</b> - Returned if the user already has an active reminder for this work package.  **Error message**: You can only set one reminder at a time for a work package. Please delete or update the existing reminder.
     * @param id Work package id
     * @param createWorkPackageReminderRequest The createWorkPackageReminderRequest parameter
     * @return ResponseEntity&lt;ReminderModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReminderModel> createWorkPackageReminderWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull CreateWorkPackageReminderRequest createWorkPackageReminderRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageReminderRequestCreation(id, createWorkPackageReminderRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a work package reminder
     * Creates a new reminder for the specified work package.  **Note:** A user can only have one **active** reminder at a time for a given work package.
     * <p><b>201</b> - Reminder created successfully
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>409</b> - Returned if the user already has an active reminder for this work package.  **Error message**: You can only set one reminder at a time for a work package. Please delete or update the existing reminder.
     * @param id Work package id
     * @param createWorkPackageReminderRequest The createWorkPackageReminderRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWorkPackageReminderWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull CreateWorkPackageReminderRequest createWorkPackageReminderRequest) throws RestClientResponseException {
        return createWorkPackageReminderRequestCreation(id, createWorkPackageReminderRequest);
    }

    /**
     * Delete a reminder
     * Deletes an existing reminder.  A user can only delete their own active reminder.  **Required permission:** view work packages for the project the reminder is contained in.
     * <p><b>204</b> - Reminder deleted successfully
     * <p><b>404</b> - Returned if the reminder does not exist or the client does not have sufficient permissions to see it.
     * @param id Reminder ID
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteReminderRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteReminder", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/reminders/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a reminder
     * Deletes an existing reminder.  A user can only delete their own active reminder.  **Required permission:** view work packages for the project the reminder is contained in.
     * <p><b>204</b> - Reminder deleted successfully
     * <p><b>404</b> - Returned if the reminder does not exist or the client does not have sufficient permissions to see it.
     * @param id Reminder ID
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteReminder(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteReminderRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a reminder
     * Deletes an existing reminder.  A user can only delete their own active reminder.  **Required permission:** view work packages for the project the reminder is contained in.
     * <p><b>204</b> - Reminder deleted successfully
     * <p><b>404</b> - Returned if the reminder does not exist or the client does not have sufficient permissions to see it.
     * @param id Reminder ID
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteReminderWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteReminderRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a reminder
     * Deletes an existing reminder.  A user can only delete their own active reminder.  **Required permission:** view work packages for the project the reminder is contained in.
     * <p><b>204</b> - Reminder deleted successfully
     * <p><b>404</b> - Returned if the reminder does not exist or the client does not have sufficient permissions to see it.
     * @param id Reminder ID
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteReminderWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteReminderRequestCreation(id);
    }

    /**
     * List all active reminders
     * Gets a list of all active reminders for the user.
     * <p><b>200</b> - OK
     * @return ListReminders200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRemindersRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/reminders", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all active reminders
     * Gets a list of all active reminders for the user.
     * <p><b>200</b> - OK
     * @return ListReminders200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ListReminders200Response listReminders() throws RestClientResponseException {
        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRemindersRequestCreation().body(localVarReturnType);
    }

    /**
     * List all active reminders
     * Gets a list of all active reminders for the user.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;ListReminders200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ListReminders200Response> listRemindersWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRemindersRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all active reminders
     * Gets a list of all active reminders for the user.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRemindersWithResponseSpec() throws RestClientResponseException {
        return listRemindersRequestCreation();
    }

    /**
     * List work package reminders
     * Gets a list of your upcoming reminders for this work package.  Only active reminders that belong to the current user are returned.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ListReminders200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRemindersRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageReminders", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/reminders", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List work package reminders
     * Gets a list of your upcoming reminders for this work package.  Only active reminders that belong to the current user are returned.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ListReminders200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ListReminders200Response listWorkPackageReminders(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRemindersRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List work package reminders
     * Gets a list of your upcoming reminders for this work package.  Only active reminders that belong to the current user are returned.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ResponseEntity&lt;ListReminders200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ListReminders200Response> listWorkPackageRemindersWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRemindersRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List work package reminders
     * Gets a list of your upcoming reminders for this work package.  Only active reminders that belong to the current user are returned.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRemindersWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkPackageRemindersRequestCreation(id);
    }

    /**
     * Update a reminder
     * Updates an existing reminder.  A user can only update their own active reminder.  **Required permission:** view work packages for the project the reminder is contained in.
     * <p><b>200</b> - Reminder updated successfully
     * <p><b>404</b> - Returned if the reminder does not exist or the client does not have sufficient permissions to see it.
     * @param id Reminder ID
     * @param updateReminderRequest The updateReminderRequest parameter
     * @return ReminderModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateReminderRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull UpdateReminderRequest updateReminderRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateReminder", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'updateReminderRequest' is set
        if (updateReminderRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'updateReminderRequest' when calling updateReminder", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/reminders/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateReminderRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a reminder
     * Updates an existing reminder.  A user can only update their own active reminder.  **Required permission:** view work packages for the project the reminder is contained in.
     * <p><b>200</b> - Reminder updated successfully
     * <p><b>404</b> - Returned if the reminder does not exist or the client does not have sufficient permissions to see it.
     * @param id Reminder ID
     * @param updateReminderRequest The updateReminderRequest parameter
     * @return ReminderModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReminderModel updateReminder(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull UpdateReminderRequest updateReminderRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateReminderRequestCreation(id, updateReminderRequest).body(localVarReturnType);
    }

    /**
     * Update a reminder
     * Updates an existing reminder.  A user can only update their own active reminder.  **Required permission:** view work packages for the project the reminder is contained in.
     * <p><b>200</b> - Reminder updated successfully
     * <p><b>404</b> - Returned if the reminder does not exist or the client does not have sufficient permissions to see it.
     * @param id Reminder ID
     * @param updateReminderRequest The updateReminderRequest parameter
     * @return ResponseEntity&lt;ReminderModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReminderModel> updateReminderWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull UpdateReminderRequest updateReminderRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateReminderRequestCreation(id, updateReminderRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a reminder
     * Updates an existing reminder.  A user can only update their own active reminder.  **Required permission:** view work packages for the project the reminder is contained in.
     * <p><b>200</b> - Reminder updated successfully
     * <p><b>404</b> - Returned if the reminder does not exist or the client does not have sufficient permissions to see it.
     * @param id Reminder ID
     * @param updateReminderRequest The updateReminderRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateReminderWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull UpdateReminderRequest updateReminderRequest) throws RestClientResponseException {
        return updateReminderRequestCreation(id, updateReminderRequest);
    }
}
