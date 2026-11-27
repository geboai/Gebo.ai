package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.GridCollectionModel;
import ai.gebo.openproject.client.model.GridReadModel;
import ai.gebo.openproject.client.model.GridWriteModel;

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
public class GridsApi {
    private ApiClient apiClient;

    public GridsApi() {
        this(new ApiClient());
    }

    public GridsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create a grid
     * Creates a new grid applying the attributes provided in the body. The constraints applied to the grid depend on the page the grid is placed in which is why the create form endpoint should be used to be guided when wanting to create a grid.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Depends on the page the grid is defined for.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param gridWriteModel The gridWriteModel parameter
     * @return GridReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createGridRequestCreation(@jakarta.annotation.Nullable GridWriteModel gridWriteModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/grids", HttpMethod.POST, pathParams, localVarQueryParams, gridWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a grid
     * Creates a new grid applying the attributes provided in the body. The constraints applied to the grid depend on the page the grid is placed in which is why the create form endpoint should be used to be guided when wanting to create a grid.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Depends on the page the grid is defined for.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param gridWriteModel The gridWriteModel parameter
     * @return GridReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GridReadModel createGrid(@jakarta.annotation.Nullable GridWriteModel gridWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createGridRequestCreation(gridWriteModel).body(localVarReturnType);
    }

    /**
     * Create a grid
     * Creates a new grid applying the attributes provided in the body. The constraints applied to the grid depend on the page the grid is placed in which is why the create form endpoint should be used to be guided when wanting to create a grid.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Depends on the page the grid is defined for.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param gridWriteModel The gridWriteModel parameter
     * @return ResponseEntity&lt;GridReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GridReadModel> createGridWithHttpInfo(@jakarta.annotation.Nullable GridWriteModel gridWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createGridRequestCreation(gridWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create a grid
     * Creates a new grid applying the attributes provided in the body. The constraints applied to the grid depend on the page the grid is placed in which is why the create form endpoint should be used to be guided when wanting to create a grid.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Depends on the page the grid is defined for.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param gridWriteModel The gridWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createGridWithResponseSpec(@jakarta.annotation.Nullable GridWriteModel gridWriteModel) throws RestClientResponseException {
        return createGridRequestCreation(gridWriteModel);
    }

    /**
     * Get a grid
     * Fetches a single grid identified by its id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the Grid does not exist or if the user does not have permission to view it.  **Required permission** depends on the page the grid is defined for
     * @param id Grid id
     * @return GridReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getGridRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getGrid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/grids/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a grid
     * Fetches a single grid identified by its id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the Grid does not exist or if the user does not have permission to view it.  **Required permission** depends on the page the grid is defined for
     * @param id Grid id
     * @return GridReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GridReadModel getGrid(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGridRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a grid
     * Fetches a single grid identified by its id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the Grid does not exist or if the user does not have permission to view it.  **Required permission** depends on the page the grid is defined for
     * @param id Grid id
     * @return ResponseEntity&lt;GridReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GridReadModel> getGridWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGridRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a grid
     * Fetches a single grid identified by its id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the Grid does not exist or if the user does not have permission to view it.  **Required permission** depends on the page the grid is defined for
     * @param id Grid id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getGridWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getGridRequestCreation(id);
    }

    /**
     * List grids
     * Lists all grids matching the provided filters and being part of the selected query page. The grids returned will also depend on the permissions of the requesting user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - page: Filter grid by work package
     * @return GridCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listGridsRequestCreation(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<GridCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/grids", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List grids
     * Lists all grids matching the provided filters and being part of the selected query page. The grids returned will also depend on the permissions of the requesting user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - page: Filter grid by work package
     * @return GridCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GridCollectionModel listGrids(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<GridCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listGridsRequestCreation(offset, pageSize, filters).body(localVarReturnType);
    }

    /**
     * List grids
     * Lists all grids matching the provided filters and being part of the selected query page. The grids returned will also depend on the permissions of the requesting user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - page: Filter grid by work package
     * @return ResponseEntity&lt;GridCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GridCollectionModel> listGridsWithHttpInfo(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<GridCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listGridsRequestCreation(offset, pageSize, filters).toEntity(localVarReturnType);
    }

    /**
     * List grids
     * Lists all grids matching the provided filters and being part of the selected query page. The grids returned will also depend on the permissions of the requesting user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - page: Filter grid by work package
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listGridsWithResponseSpec(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listGridsRequestCreation(offset, pageSize, filters);
    }

    /**
     * Update a grid
     * Updates the given grid by applying the attributes provided in the body. The constraints applied to the grid depend on the page the grid is placed in which is why the create form endpoint should be used to be guided when wanting to update a grid.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** The permission depends on the page the grid is placed in.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Grid id
     * @param gridWriteModel The gridWriteModel parameter
     * @return GridReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateGridRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable GridWriteModel gridWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateGrid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/grids/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, gridWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a grid
     * Updates the given grid by applying the attributes provided in the body. The constraints applied to the grid depend on the page the grid is placed in which is why the create form endpoint should be used to be guided when wanting to update a grid.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** The permission depends on the page the grid is placed in.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Grid id
     * @param gridWriteModel The gridWriteModel parameter
     * @return GridReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GridReadModel updateGrid(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable GridWriteModel gridWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateGridRequestCreation(id, gridWriteModel).body(localVarReturnType);
    }

    /**
     * Update a grid
     * Updates the given grid by applying the attributes provided in the body. The constraints applied to the grid depend on the page the grid is placed in which is why the create form endpoint should be used to be guided when wanting to update a grid.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** The permission depends on the page the grid is placed in.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Grid id
     * @param gridWriteModel The gridWriteModel parameter
     * @return ResponseEntity&lt;GridReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GridReadModel> updateGridWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable GridWriteModel gridWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<GridReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateGridRequestCreation(id, gridWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update a grid
     * Updates the given grid by applying the attributes provided in the body. The constraints applied to the grid depend on the page the grid is placed in which is why the create form endpoint should be used to be guided when wanting to update a grid.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** The permission depends on the page the grid is placed in.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Grid id
     * @param gridWriteModel The gridWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateGridWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable GridWriteModel gridWriteModel) throws RestClientResponseException {
        return updateGridRequestCreation(id, gridWriteModel);
    }

    /**
     * Grid Create Form
     * 
     * <p><b>200</b> - OK
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateGridCreateFormRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/grids/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Grid Create Form
     * 
     * <p><b>200</b> - OK
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateGridCreateForm() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateGridCreateFormRequestCreation().body(localVarReturnType);
    }

    /**
     * Grid Create Form
     * 
     * <p><b>200</b> - OK
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateGridCreateFormWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateGridCreateFormRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Grid Create Form
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateGridCreateFormWithResponseSpec() throws RestClientResponseException {
        return validateGridCreateFormRequestCreation();
    }

    /**
     * Grid Update Form
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** depends on the page the grid is defined for.  *Note that you will only receive this error, if you are at least allowed to see the corresponding grid.*
     * <p><b>404</b> - Returned if the grid does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the grid being modified
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateGridUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateGridUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/grids/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Grid Update Form
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** depends on the page the grid is defined for.  *Note that you will only receive this error, if you are at least allowed to see the corresponding grid.*
     * <p><b>404</b> - Returned if the grid does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the grid being modified
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object validateGridUpdateForm(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateGridUpdateFormRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Grid Update Form
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** depends on the page the grid is defined for.  *Note that you will only receive this error, if you are at least allowed to see the corresponding grid.*
     * <p><b>404</b> - Returned if the grid does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the grid being modified
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> validateGridUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateGridUpdateFormRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Grid Update Form
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** depends on the page the grid is defined for.  *Note that you will only receive this error, if you are at least allowed to see the corresponding grid.*
     * <p><b>404</b> - Returned if the grid does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the grid being modified
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateGridUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return validateGridUpdateFormRequestCreation(id);
    }
}
