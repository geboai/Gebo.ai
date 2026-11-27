package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.ProgramCollectionModel;
import ai.gebo.openproject.client.model.ProgramModel;

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
public class ProgramsApi {
    private ApiClient apiClient;

    public ProgramsApi() {
        this(new ApiClient());
    }

    public ProgramsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete Program
     * Deletes the program permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the program might exist well after the request has returned successfully. To prevent unwanted changes to the program scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the program was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the program  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the program cannot be deleted. This can happen when there are still references to the program in other workspaces that need to be severed at first.
     * @param id Program id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteProgramRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteProgram", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/programs/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete Program
     * Deletes the program permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the program might exist well after the request has returned successfully. To prevent unwanted changes to the program scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the program was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the program  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the program cannot be deleted. This can happen when there are still references to the program in other workspaces that need to be severed at first.
     * @param id Program id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteProgram(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteProgramRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete Program
     * Deletes the program permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the program might exist well after the request has returned successfully. To prevent unwanted changes to the program scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the program was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the program  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the program cannot be deleted. This can happen when there are still references to the program in other workspaces that need to be severed at first.
     * @param id Program id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteProgramWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteProgramRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete Program
     * Deletes the program permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the program might exist well after the request has returned successfully. To prevent unwanted changes to the program scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the program was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the program  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the program cannot be deleted. This can happen when there are still references to the program in other workspaces that need to be severed at first.
     * @param id Program id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteProgramWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteProgramRequestCreation(id);
    }

    /**
     * View program
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the program  *Note: A client without sufficient permissions shall not be able to test for the existence of a program. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Program id
     * @return ProgramModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProgramRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getProgram", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProgramModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/programs/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View program
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the program  *Note: A client without sufficient permissions shall not be able to test for the existence of a program. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Program id
     * @return ProgramModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProgramModel getProgram(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProgramModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProgramRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View program
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the program  *Note: A client without sufficient permissions shall not be able to test for the existence of a program. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Program id
     * @return ResponseEntity&lt;ProgramModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProgramModel> getProgramWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProgramModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProgramRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View program
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the program  *Note: A client without sufficient permissions shall not be able to test for the existence of a program. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Program id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getProgramWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getProgramRequestCreation(id);
    }

    /**
     * List programs
     * Returns a collection of programs. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain programs the client is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the program + ancestor: filters programs by their ancestor. A program is not considered to be its own ancestor. + available_project_attributes: filters programs based on the activated project attributes. + created_at: based on the time the program was created + favorited: based on the favorited property of the program + id: based on programs&#39; id. + latest_activity_at: based on the time the last activity was registered on a program. + name_and_identifier: based on both the name and the identifier. + parent_id: filters programs by their parent. + principal: based on members of the program. + project_phase_any: based on the project phases active in a program. + project_status_code: based on status code of the program + storage_id: filters programs by linked storages + storage_url: filters programs by linked storages identified by the host url + type_id: based on the types active in a program. + user_action: based on the actions the current user has in the program. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the programs visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a program. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a program. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a program. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ProgramCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listProgramsRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

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

        ParameterizedTypeReference<ProgramCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/programs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List programs
     * Returns a collection of programs. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain programs the client is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the program + ancestor: filters programs by their ancestor. A program is not considered to be its own ancestor. + available_project_attributes: filters programs based on the activated project attributes. + created_at: based on the time the program was created + favorited: based on the favorited property of the program + id: based on programs&#39; id. + latest_activity_at: based on the time the last activity was registered on a program. + name_and_identifier: based on both the name and the identifier. + parent_id: filters programs by their parent. + principal: based on members of the program. + project_phase_any: based on the project phases active in a program. + project_status_code: based on status code of the program + storage_id: filters programs by linked storages + storage_url: filters programs by linked storages identified by the host url + type_id: based on the types active in a program. + user_action: based on the actions the current user has in the program. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the programs visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a program. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a program. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a program. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ProgramCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProgramCollectionModel listPrograms(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<ProgramCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listProgramsRequestCreation(filters, sortBy, select).body(localVarReturnType);
    }

    /**
     * List programs
     * Returns a collection of programs. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain programs the client is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the program + ancestor: filters programs by their ancestor. A program is not considered to be its own ancestor. + available_project_attributes: filters programs based on the activated project attributes. + created_at: based on the time the program was created + favorited: based on the favorited property of the program + id: based on programs&#39; id. + latest_activity_at: based on the time the last activity was registered on a program. + name_and_identifier: based on both the name and the identifier. + parent_id: filters programs by their parent. + principal: based on members of the program. + project_phase_any: based on the project phases active in a program. + project_status_code: based on status code of the program + storage_id: filters programs by linked storages + storage_url: filters programs by linked storages identified by the host url + type_id: based on the types active in a program. + user_action: based on the actions the current user has in the program. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the programs visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a program. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a program. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a program. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ResponseEntity&lt;ProgramCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProgramCollectionModel> listProgramsWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<ProgramCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listProgramsRequestCreation(filters, sortBy, select).toEntity(localVarReturnType);
    }

    /**
     * List programs
     * Returns a collection of programs. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain programs the client is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the program + ancestor: filters programs by their ancestor. A program is not considered to be its own ancestor. + available_project_attributes: filters programs based on the activated project attributes. + created_at: based on the time the program was created + favorited: based on the favorited property of the program + id: based on programs&#39; id. + latest_activity_at: based on the time the last activity was registered on a program. + name_and_identifier: based on both the name and the identifier. + parent_id: filters programs by their parent. + principal: based on members of the program. + project_phase_any: based on the project phases active in a program. + project_status_code: based on status code of the program + storage_id: filters programs by linked storages + storage_url: filters programs by linked storages identified by the host url + type_id: based on the types active in a program. + user_action: based on the actions the current user has in the program. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the programs visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a program. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a program. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a program. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listProgramsWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return listProgramsRequestCreation(filters, sortBy, select);
    }

    /**
     * Update Program
     * Updates the given program by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the program to be altered
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Program id
     * @param programModel The programModel parameter
     * @return ProgramModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateProgramRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ProgramModel programModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateProgram", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProgramModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/programs/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, programModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update Program
     * Updates the given program by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the program to be altered
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Program id
     * @param programModel The programModel parameter
     * @return ProgramModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProgramModel updateProgram(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ProgramModel programModel) throws RestClientResponseException {
        ParameterizedTypeReference<ProgramModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateProgramRequestCreation(id, programModel).body(localVarReturnType);
    }

    /**
     * Update Program
     * Updates the given program by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the program to be altered
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Program id
     * @param programModel The programModel parameter
     * @return ResponseEntity&lt;ProgramModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProgramModel> updateProgramWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ProgramModel programModel) throws RestClientResponseException {
        ParameterizedTypeReference<ProgramModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateProgramRequestCreation(id, programModel).toEntity(localVarReturnType);
    }

    /**
     * Update Program
     * Updates the given program by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the program to be altered
     * <p><b>404</b> - Returned if the program does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Program id
     * @param programModel The programModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateProgramWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ProgramModel programModel) throws RestClientResponseException {
        return updateProgramRequestCreation(id, programModel);
    }

    /**
     * Program update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit workspace in the program
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Program id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateProgramUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateProgramUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/programs/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Program update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit workspace in the program
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Program id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateProgramUpdateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateProgramUpdateFormRequestCreation(id, body).body(localVarReturnType);
    }

    /**
     * Program update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit workspace in the program
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Program id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateProgramUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateProgramUpdateFormRequestCreation(id, body).toEntity(localVarReturnType);
    }

    /**
     * Program update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit workspace in the program
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Program id
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateProgramUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return validateProgramUpdateFormRequestCreation(id, body);
    }
}
