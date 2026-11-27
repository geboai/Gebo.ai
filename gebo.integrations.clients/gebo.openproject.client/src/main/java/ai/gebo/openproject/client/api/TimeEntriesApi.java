package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.TimeEntryCollectionModel;
import ai.gebo.openproject.client.model.TimeEntryModel;

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
     * Creates a new time entry applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a time entries&#39; attributes and are accepted by the endpoint.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Log time
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param timeEntryModel The timeEntryModel parameter
     * @return TimeEntryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createTimeEntryRequestCreation(@jakarta.annotation.Nullable TimeEntryModel timeEntryModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/time_entries", HttpMethod.POST, pathParams, localVarQueryParams, timeEntryModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create time entry
     * Creates a new time entry applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a time entries&#39; attributes and are accepted by the endpoint.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Log time
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param timeEntryModel The timeEntryModel parameter
     * @return TimeEntryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TimeEntryModel createTimeEntry(@jakarta.annotation.Nullable TimeEntryModel timeEntryModel) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTimeEntryRequestCreation(timeEntryModel).body(localVarReturnType);
    }

    /**
     * Create time entry
     * Creates a new time entry applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a time entries&#39; attributes and are accepted by the endpoint.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Log time
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param timeEntryModel The timeEntryModel parameter
     * @return ResponseEntity&lt;TimeEntryModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TimeEntryModel> createTimeEntryWithHttpInfo(@jakarta.annotation.Nullable TimeEntryModel timeEntryModel) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createTimeEntryRequestCreation(timeEntryModel).toEntity(localVarReturnType);
    }

    /**
     * Create time entry
     * Creates a new time entry applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a time entries&#39; attributes and are accepted by the endpoint.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Log time
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param timeEntryModel The timeEntryModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createTimeEntryWithResponseSpec(@jakarta.annotation.Nullable TimeEntryModel timeEntryModel) throws RestClientResponseException {
        return createTimeEntryRequestCreation(timeEntryModel);
    }

    /**
     * Delete time entry
     * Permanently deletes the specified time entry.
     * <p><b>204</b> - Returned if the time entry was deleted successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions
     * <p><b>404</b> - Returned if the time entry does not exist or if the user does not have sufficient permissions to see the time entry.  **Required permission** &#x60;view time entries&#x60; in the project the time entry is assigned to or &#x60;view own time entries&#x60; for time entries belonging to the user
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Time entry id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteTimeEntryRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/time_entries/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete time entry
     * Permanently deletes the specified time entry.
     * <p><b>204</b> - Returned if the time entry was deleted successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions
     * <p><b>404</b> - Returned if the time entry does not exist or if the user does not have sufficient permissions to see the time entry.  **Required permission** &#x60;view time entries&#x60; in the project the time entry is assigned to or &#x60;view own time entries&#x60; for time entries belonging to the user
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Time entry id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteTimeEntry(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteTimeEntryRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete time entry
     * Permanently deletes the specified time entry.
     * <p><b>204</b> - Returned if the time entry was deleted successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions
     * <p><b>404</b> - Returned if the time entry does not exist or if the user does not have sufficient permissions to see the time entry.  **Required permission** &#x60;view time entries&#x60; in the project the time entry is assigned to or &#x60;view own time entries&#x60; for time entries belonging to the user
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Time entry id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteTimeEntryWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteTimeEntryRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete time entry
     * Permanently deletes the specified time entry.
     * <p><b>204</b> - Returned if the time entry was deleted successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions
     * <p><b>404</b> - Returned if the time entry does not exist or if the user does not have sufficient permissions to see the time entry.  **Required permission** &#x60;view time entries&#x60; in the project the time entry is assigned to or &#x60;view own time entries&#x60; for time entries belonging to the user
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Time entry id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteTimeEntryWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteTimeEntryRequestCreation(id);
    }

    /**
     * Get time entry
     * Retrieves a single time entry identified by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the time entry does not exist or if the user does not have permission to view them.  **Required permission** - &#x60;view time entries&#x60; in the project the time entry is assigned to or - &#x60;view own time entries&#x60; for time entries belonging to the user
     * @param id time entry id
     * @return TimeEntryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTimeEntryRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/time_entries/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get time entry
     * Retrieves a single time entry identified by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the time entry does not exist or if the user does not have permission to view them.  **Required permission** - &#x60;view time entries&#x60; in the project the time entry is assigned to or - &#x60;view own time entries&#x60; for time entries belonging to the user
     * @param id time entry id
     * @return TimeEntryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TimeEntryModel getTimeEntry(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntryRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get time entry
     * Retrieves a single time entry identified by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the time entry does not exist or if the user does not have permission to view them.  **Required permission** - &#x60;view time entries&#x60; in the project the time entry is assigned to or - &#x60;view own time entries&#x60; for time entries belonging to the user
     * @param id time entry id
     * @return ResponseEntity&lt;TimeEntryModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TimeEntryModel> getTimeEntryWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntryRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get time entry
     * Retrieves a single time entry identified by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the time entry does not exist or if the user does not have permission to view them.  **Required permission** - &#x60;view time entries&#x60; in the project the time entry is assigned to or - &#x60;view own time entries&#x60; for time entries belonging to the user
     * @param id time entry id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTimeEntryWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getTimeEntryRequestCreation(id);
    }

    /**
     * View time entry schema
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** *log time* or *view time entries* or *edit time entries* or *edit own time entries* on any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTimeEntrySchemaRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/time_entries/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View time entry schema
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** *log time* or *view time entries* or *edit time entries* or *edit own time entries* on any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getTimeEntrySchema() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntrySchemaRequestCreation().body(localVarReturnType);
    }

    /**
     * View time entry schema
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** *log time* or *view time entries* or *edit time entries* or *edit own time entries* on any project
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getTimeEntrySchemaWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTimeEntrySchemaRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * View time entry schema
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** *log time* or *view time entries* or *edit time entries* or *edit own time entries* on any project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTimeEntrySchemaWithResponseSpec() throws RestClientResponseException {
        return getTimeEntrySchemaRequestCreation();
    }

    /**
     * Available projects for time entries
     * Gets a list of projects in which a time entry can be created in or be assigned to on update. The list contains all projects in which the user issuing the request has the necessary permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** *log time*, *edit time entries* or *edit own time entries* in any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableProjectsForTimeEntriesRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/time_entries/available_projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Available projects for time entries
     * Gets a list of projects in which a time entry can be created in or be assigned to on update. The list contains all projects in which the user issuing the request has the necessary permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** *log time*, *edit time entries* or *edit own time entries* in any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listAvailableProjectsForTimeEntries() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForTimeEntriesRequestCreation().body(localVarReturnType);
    }

    /**
     * Available projects for time entries
     * Gets a list of projects in which a time entry can be created in or be assigned to on update. The list contains all projects in which the user issuing the request has the necessary permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** *log time*, *edit time entries* or *edit own time entries* in any project
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listAvailableProjectsForTimeEntriesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForTimeEntriesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Available projects for time entries
     * Gets a list of projects in which a time entry can be created in or be assigned to on update. The list contains all projects in which the user issuing the request has the necessary permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** *log time*, *edit time entries* or *edit own time entries* in any project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableProjectsForTimeEntriesWithResponseSpec() throws RestClientResponseException {
        return listAvailableProjectsForTimeEntriesRequestCreation();
    }

    /**
     * List time entries
     * Lists time entries. The time entries returned depend on the filters provided and also on the permission of the requesting user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + hours: Sort by logged hours  + spent_on: Sort by spent on date  + created_at: Sort by time entry creation datetime  + updated_at: Sort by the time the time entry was updated last
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + entity_type: Filter time entries depending on the entity they are logged on. Can either be &#x60;WorkPackage&#x60; or &#x60;Meeting&#x60;.  + entity_id: Filter time entries for the specified entity IDs.  + project_id: Filter time entries by project  + user_id: Filter time entries by users  + ongoing: Filter to only receive ongoing timers  + spent_on: Filter time entries by spent on date  + created_at: Filter time entries by creation datetime  + updated_at: Filter time entries by the last time they where updated  + activity_id: Filter time entries by time entry activity
     * @return TimeEntryCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listTimeEntriesRequestCreation(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<TimeEntryCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/time_entries", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List time entries
     * Lists time entries. The time entries returned depend on the filters provided and also on the permission of the requesting user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + hours: Sort by logged hours  + spent_on: Sort by spent on date  + created_at: Sort by time entry creation datetime  + updated_at: Sort by the time the time entry was updated last
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + entity_type: Filter time entries depending on the entity they are logged on. Can either be &#x60;WorkPackage&#x60; or &#x60;Meeting&#x60;.  + entity_id: Filter time entries for the specified entity IDs.  + project_id: Filter time entries by project  + user_id: Filter time entries by users  + ongoing: Filter to only receive ongoing timers  + spent_on: Filter time entries by spent on date  + created_at: Filter time entries by creation datetime  + updated_at: Filter time entries by the last time they where updated  + activity_id: Filter time entries by time entry activity
     * @return TimeEntryCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TimeEntryCollectionModel listTimeEntries(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTimeEntriesRequestCreation(offset, pageSize, sortBy, filters).body(localVarReturnType);
    }

    /**
     * List time entries
     * Lists time entries. The time entries returned depend on the filters provided and also on the permission of the requesting user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + hours: Sort by logged hours  + spent_on: Sort by spent on date  + created_at: Sort by time entry creation datetime  + updated_at: Sort by the time the time entry was updated last
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + entity_type: Filter time entries depending on the entity they are logged on. Can either be &#x60;WorkPackage&#x60; or &#x60;Meeting&#x60;.  + entity_id: Filter time entries for the specified entity IDs.  + project_id: Filter time entries by project  + user_id: Filter time entries by users  + ongoing: Filter to only receive ongoing timers  + spent_on: Filter time entries by spent on date  + created_at: Filter time entries by creation datetime  + updated_at: Filter time entries by the last time they where updated  + activity_id: Filter time entries by time entry activity
     * @return ResponseEntity&lt;TimeEntryCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TimeEntryCollectionModel> listTimeEntriesWithHttpInfo(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTimeEntriesRequestCreation(offset, pageSize, sortBy, filters).toEntity(localVarReturnType);
    }

    /**
     * List time entries
     * Lists time entries. The time entries returned depend on the filters provided and also on the permission of the requesting user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + hours: Sort by logged hours  + spent_on: Sort by spent on date  + created_at: Sort by time entry creation datetime  + updated_at: Sort by the time the time entry was updated last
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + entity_type: Filter time entries depending on the entity they are logged on. Can either be &#x60;WorkPackage&#x60; or &#x60;Meeting&#x60;.  + entity_id: Filter time entries for the specified entity IDs.  + project_id: Filter time entries by project  + user_id: Filter time entries by users  + ongoing: Filter to only receive ongoing timers  + spent_on: Filter time entries by spent on date  + created_at: Filter time entries by creation datetime  + updated_at: Filter time entries by the last time they where updated  + activity_id: Filter time entries by time entry activity
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listTimeEntriesWithResponseSpec(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listTimeEntriesRequestCreation(offset, pageSize, sortBy, filters);
    }

    /**
     * update time entry
     * Updates the given time entry by applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a time entries&#39; attributes and are accepted by the endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Edit (own) time entries, depending on what time entry is being modified.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Time entry id
     * @return TimeEntryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateTimeEntryRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateTimeEntry", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/time_entries/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * update time entry
     * Updates the given time entry by applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a time entries&#39; attributes and are accepted by the endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Edit (own) time entries, depending on what time entry is being modified.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Time entry id
     * @return TimeEntryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TimeEntryModel updateTimeEntry(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTimeEntryRequestCreation(id).body(localVarReturnType);
    }

    /**
     * update time entry
     * Updates the given time entry by applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a time entries&#39; attributes and are accepted by the endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Edit (own) time entries, depending on what time entry is being modified.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Time entry id
     * @return ResponseEntity&lt;TimeEntryModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TimeEntryModel> updateTimeEntryWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TimeEntryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateTimeEntryRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * update time entry
     * Updates the given time entry by applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a time entries&#39; attributes and are accepted by the endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Edit (own) time entries, depending on what time entry is being modified.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Time entry id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateTimeEntryWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return updateTimeEntryRequestCreation(id);
    }

    /**
     * Time entry create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** *log time* in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateTimeEntryCreateFormRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/time_entries/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Time entry create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** *log time* in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateTimeEntryCreateForm() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateTimeEntryCreateFormRequestCreation().body(localVarReturnType);
    }

    /**
     * Time entry create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** *log time* in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateTimeEntryCreateFormWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateTimeEntryCreateFormRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Time entry create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** *log time* in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateTimeEntryCreateFormWithResponseSpec() throws RestClientResponseException {
        return validateTimeEntryCreateFormRequestCreation();
    }

    /**
     * Time entry update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to edit the time entry.  **Required permission:** *edit time entries* for every time entry of a project, or *edit own time entries* for time entries belonging to the user.
     * <p><b>404</b> - Returned if the time entry does not exist or if the client does not have sufficient permissions to view it.  **Required permission** &#x60;view time entries&#x60; in the project the time entry is assigned to or &#x60;view own time entries&#x60; for time entries belonging to the user
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Time entries activity id
     * @param body Time entries activity id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateTimeEntryUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer body) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateTimeEntryUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new RestClientResponseException("Missing the required parameter 'body' when calling validateTimeEntryUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/time_entries/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Time entry update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to edit the time entry.  **Required permission:** *edit time entries* for every time entry of a project, or *edit own time entries* for time entries belonging to the user.
     * <p><b>404</b> - Returned if the time entry does not exist or if the client does not have sufficient permissions to view it.  **Required permission** &#x60;view time entries&#x60; in the project the time entry is assigned to or &#x60;view own time entries&#x60; for time entries belonging to the user
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Time entries activity id
     * @param body Time entries activity id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateTimeEntryUpdateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer body) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateTimeEntryUpdateFormRequestCreation(id, body).body(localVarReturnType);
    }

    /**
     * Time entry update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to edit the time entry.  **Required permission:** *edit time entries* for every time entry of a project, or *edit own time entries* for time entries belonging to the user.
     * <p><b>404</b> - Returned if the time entry does not exist or if the client does not have sufficient permissions to view it.  **Required permission** &#x60;view time entries&#x60; in the project the time entry is assigned to or &#x60;view own time entries&#x60; for time entries belonging to the user
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Time entries activity id
     * @param body Time entries activity id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateTimeEntryUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer body) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateTimeEntryUpdateFormRequestCreation(id, body).toEntity(localVarReturnType);
    }

    /**
     * Time entry update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to edit the time entry.  **Required permission:** *edit time entries* for every time entry of a project, or *edit own time entries* for time entries belonging to the user.
     * <p><b>404</b> - Returned if the time entry does not exist or if the client does not have sufficient permissions to view it.  **Required permission** &#x60;view time entries&#x60; in the project the time entry is assigned to or &#x60;view own time entries&#x60; for time entries belonging to the user
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Time entries activity id
     * @param body Time entries activity id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateTimeEntryUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer body) throws RestClientResponseException {
        return validateTimeEntryUpdateFormRequestCreation(id, body);
    }
}
