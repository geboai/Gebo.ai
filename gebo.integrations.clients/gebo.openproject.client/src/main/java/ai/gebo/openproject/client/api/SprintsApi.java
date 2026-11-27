package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.SprintCollectionModel;
import ai.gebo.openproject.client.model.SprintModel;

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
public class SprintsApi {
    private ApiClient apiClient;

    public SprintsApi() {
        this(new ApiClient());
    }

    public SprintsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get sprint
     * Retrieves a sprint defined by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the sprint does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view master backlogs (this will change to view sprints)
     * @param id Sprint id
     * @return SprintModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSprintRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getSprint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<SprintModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/sprints/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get sprint
     * Retrieves a sprint defined by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the sprint does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view master backlogs (this will change to view sprints)
     * @param id Sprint id
     * @return SprintModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SprintModel getSprint(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<SprintModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSprintRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get sprint
     * Retrieves a sprint defined by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the sprint does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view master backlogs (this will change to view sprints)
     * @param id Sprint id
     * @return ResponseEntity&lt;SprintModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SprintModel> getSprintWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<SprintModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSprintRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get sprint
     * Retrieves a sprint defined by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the sprint does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view master backlogs (this will change to view sprints)
     * @param id Sprint id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSprintWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getSprintRequestCreation(id);
    }

    /**
     * List sprints
     * List sprints visible to the user. This endpoint supports pagination and filtering.
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + definingWorkspace: filters sprints based on the project they are defined in. This is opposed to the projects they are shared with.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return SprintCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listSprintsRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<SprintCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/sprints", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List sprints
     * List sprints visible to the user. This endpoint supports pagination and filtering.
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + definingWorkspace: filters sprints based on the project they are defined in. This is opposed to the projects they are shared with.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return SprintCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SprintCollectionModel listSprints(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<SprintCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listSprintsRequestCreation(filters, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List sprints
     * List sprints visible to the user. This endpoint supports pagination and filtering.
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + definingWorkspace: filters sprints based on the project they are defined in. This is opposed to the projects they are shared with.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;SprintCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SprintCollectionModel> listSprintsWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<SprintCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listSprintsRequestCreation(filters, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List sprints
     * List sprints visible to the user. This endpoint supports pagination and filtering.
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + definingWorkspace: filters sprints based on the project they are defined in. This is opposed to the projects they are shared with.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listSprintsWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listSprintsRequestCreation(filters, offset, pageSize);
    }

    /**
     * List sprints in project
     * List sprints in the project. The sprints might be ones native to the project or shared with it. This endpoint supports pagination and filtering.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user lacks permission to list sprints in the queried for project.  **Required permission:** view sprints in the project.
     * <p><b>404</b> - Returned if the project does not exist.  **Required permission:** view sprints in the queried for project.
     * @param id ID of the project whose sprints will be listed
     * @param filters JSON specifying filter conditions.  Accepts the same format and filters as returned by the [sprints](https://www.openproject.org/docs/api/endpoints/sprints/) endpoint.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return SprintCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listSprintsInProjectRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listSprintsInProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<SprintCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/sprints", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List sprints in project
     * List sprints in the project. The sprints might be ones native to the project or shared with it. This endpoint supports pagination and filtering.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user lacks permission to list sprints in the queried for project.  **Required permission:** view sprints in the project.
     * <p><b>404</b> - Returned if the project does not exist.  **Required permission:** view sprints in the queried for project.
     * @param id ID of the project whose sprints will be listed
     * @param filters JSON specifying filter conditions.  Accepts the same format and filters as returned by the [sprints](https://www.openproject.org/docs/api/endpoints/sprints/) endpoint.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return SprintCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SprintCollectionModel listSprintsInProject(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<SprintCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listSprintsInProjectRequestCreation(id, filters, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List sprints in project
     * List sprints in the project. The sprints might be ones native to the project or shared with it. This endpoint supports pagination and filtering.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user lacks permission to list sprints in the queried for project.  **Required permission:** view sprints in the project.
     * <p><b>404</b> - Returned if the project does not exist.  **Required permission:** view sprints in the queried for project.
     * @param id ID of the project whose sprints will be listed
     * @param filters JSON specifying filter conditions.  Accepts the same format and filters as returned by the [sprints](https://www.openproject.org/docs/api/endpoints/sprints/) endpoint.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;SprintCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SprintCollectionModel> listSprintsInProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<SprintCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listSprintsInProjectRequestCreation(id, filters, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List sprints in project
     * List sprints in the project. The sprints might be ones native to the project or shared with it. This endpoint supports pagination and filtering.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user lacks permission to list sprints in the queried for project.  **Required permission:** view sprints in the project.
     * <p><b>404</b> - Returned if the project does not exist.  **Required permission:** view sprints in the queried for project.
     * @param id ID of the project whose sprints will be listed
     * @param filters JSON specifying filter conditions.  Accepts the same format and filters as returned by the [sprints](https://www.openproject.org/docs/api/endpoints/sprints/) endpoint.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listSprintsInProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listSprintsInProjectRequestCreation(id, filters, offset, pageSize);
    }
}
