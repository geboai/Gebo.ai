package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.VersionCollectionModel;
import ai.gebo.openproject.client.model.VersionReadModel;
import ai.gebo.openproject.client.model.VersionWriteModel;
import ai.gebo.openproject.client.model.VersionsByWorkspaceModel;

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
public class VersionsApi {
    private ApiClient apiClient;

    public VersionsApi() {
        this(new ApiClient());
    }

    public VersionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create version
     * Creates a new version applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a version&#39;s attributes and are accepted by the endpoint.  You can use the form and schema to be retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage versions
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param versionWriteModel The versionWriteModel parameter
     * @return VersionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createVersionRequestCreation(@jakarta.annotation.Nullable VersionWriteModel versionWriteModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/versions", HttpMethod.POST, pathParams, localVarQueryParams, versionWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create version
     * Creates a new version applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a version&#39;s attributes and are accepted by the endpoint.  You can use the form and schema to be retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage versions
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param versionWriteModel The versionWriteModel parameter
     * @return VersionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public VersionReadModel createVersion(@jakarta.annotation.Nullable VersionWriteModel versionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createVersionRequestCreation(versionWriteModel).body(localVarReturnType);
    }

    /**
     * Create version
     * Creates a new version applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a version&#39;s attributes and are accepted by the endpoint.  You can use the form and schema to be retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage versions
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param versionWriteModel The versionWriteModel parameter
     * @return ResponseEntity&lt;VersionReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<VersionReadModel> createVersionWithHttpInfo(@jakarta.annotation.Nullable VersionWriteModel versionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createVersionRequestCreation(versionWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create version
     * Creates a new version applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a version&#39;s attributes and are accepted by the endpoint.  You can use the form and schema to be retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage versions
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param versionWriteModel The versionWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createVersionWithResponseSpec(@jakarta.annotation.Nullable VersionWriteModel versionWriteModel) throws RestClientResponseException {
        return createVersionRequestCreation(versionWriteModel);
    }

    /**
     * Delete version
     * Deletes the version. Entities associated to the version will no longer be assigned to it.
     * <p><b>204</b> - Returned if the version was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Version id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteVersionRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/versions/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete version
     * Deletes the version. Entities associated to the version will no longer be assigned to it.
     * <p><b>204</b> - Returned if the version was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Version id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteVersion(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteVersionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete version
     * Deletes the version. Entities associated to the version will no longer be assigned to it.
     * <p><b>204</b> - Returned if the version was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Version id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteVersionWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteVersionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete version
     * Deletes the version. Entities associated to the version will no longer be assigned to it.
     * <p><b>204</b> - Returned if the version was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Version id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteVersionWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteVersionRequestCreation(id);
    }

    /**
     * Get version
     * Retrieves a version defined by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return VersionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getVersionRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/versions/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get version
     * Retrieves a version defined by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return VersionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public VersionReadModel getVersion(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getVersionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get version
     * Retrieves a version defined by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return ResponseEntity&lt;VersionReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<VersionReadModel> getVersionWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getVersionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get version
     * Retrieves a version defined by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getVersionWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getVersionRequestCreation(id);
    }

    /**
     * View version schema
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** view work packages or manage versions on any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getVersionSchemaRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/versions/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View version schema
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** view work packages or manage versions on any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getVersionSchema() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getVersionSchemaRequestCreation().body(localVarReturnType);
    }

    /**
     * View version schema
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** view work packages or manage versions on any project
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getVersionSchemaWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getVersionSchemaRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * View version schema
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** view work packages or manage versions on any project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getVersionSchemaWithResponseSpec() throws RestClientResponseException {
        return getVersionSchemaRequestCreation();
    }

    /**
     * Available projects for versions
     * Gets a list of projects in which a version can be created in. The list contains all projects in which the user issuing the request has the manage versions permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableProjectsForVersionsRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/versions/available_projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Available projects for versions
     * Gets a list of projects in which a version can be created in. The list contains all projects in which the user issuing the request has the manage versions permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listAvailableProjectsForVersions() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForVersionsRequestCreation().body(localVarReturnType);
    }

    /**
     * Available projects for versions
     * Gets a list of projects in which a version can be created in. The list contains all projects in which the user issuing the request has the manage versions permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listAvailableProjectsForVersionsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForVersionsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Available projects for versions
     * Gets a list of projects in which a version can be created in. The list contains all projects in which the user issuing the request has the manage versions permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableProjectsForVersionsWithResponseSpec() throws RestClientResponseException {
        return listAvailableProjectsForVersionsRequestCreation();
    }

    /**
     * List versions
     * Returns a collection of versions. The client can choose to filter the versions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain versions, for which the requesting client has sufficient permissions (*view_work_packages*).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - sharing: filters versions by how they are shared within the server (*none*, *descendants*, *hierarchy*, *tree*, *system*). - name: filters versions by their name.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported attributes are:  - id: Sort by the version id - name: Sort by the version name using numeric collation, comparing sequences of decimal digits by their numeric value
     * @return VersionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listVersionsRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<VersionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/versions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List versions
     * Returns a collection of versions. The client can choose to filter the versions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain versions, for which the requesting client has sufficient permissions (*view_work_packages*).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - sharing: filters versions by how they are shared within the server (*none*, *descendants*, *hierarchy*, *tree*, *system*). - name: filters versions by their name.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported attributes are:  - id: Sort by the version id - name: Sort by the version name using numeric collation, comparing sequences of decimal digits by their numeric value
     * @return VersionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public VersionCollectionModel listVersions(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<VersionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listVersionsRequestCreation(filters, sortBy).body(localVarReturnType);
    }

    /**
     * List versions
     * Returns a collection of versions. The client can choose to filter the versions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain versions, for which the requesting client has sufficient permissions (*view_work_packages*).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - sharing: filters versions by how they are shared within the server (*none*, *descendants*, *hierarchy*, *tree*, *system*). - name: filters versions by their name.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported attributes are:  - id: Sort by the version id - name: Sort by the version name using numeric collation, comparing sequences of decimal digits by their numeric value
     * @return ResponseEntity&lt;VersionCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<VersionCollectionModel> listVersionsWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<VersionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listVersionsRequestCreation(filters, sortBy).toEntity(localVarReturnType);
    }

    /**
     * List versions
     * Returns a collection of versions. The client can choose to filter the versions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain versions, for which the requesting client has sufficient permissions (*view_work_packages*).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - sharing: filters versions by how they are shared within the server (*none*, *descendants*, *hierarchy*, *tree*, *system*). - name: filters versions by their name.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported attributes are:  - id: Sort by the version id - name: Sort by the version name using numeric collation, comparing sequences of decimal digits by their numeric value
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listVersionsWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        return listVersionsRequestCreation(filters, sortBy);
    }

    /**
     * List versions available in a project
     * This endpoint lists the versions that are *available* in a given project. Note that due to sharing this might be more than the versions *defined* by that project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/versions&#x60;](https://www.openproject.org/docs/api/endpoints/versions/#list-versions-available-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (on given project)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose versions will be listed
     * @return VersionsByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listVersionsAvailableInAProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listVersionsAvailableInAProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<VersionsByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/versions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List versions available in a project
     * This endpoint lists the versions that are *available* in a given project. Note that due to sharing this might be more than the versions *defined* by that project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/versions&#x60;](https://www.openproject.org/docs/api/endpoints/versions/#list-versions-available-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (on given project)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose versions will be listed
     * @return VersionsByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public VersionsByWorkspaceModel listVersionsAvailableInAProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<VersionsByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listVersionsAvailableInAProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List versions available in a project
     * This endpoint lists the versions that are *available* in a given project. Note that due to sharing this might be more than the versions *defined* by that project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/versions&#x60;](https://www.openproject.org/docs/api/endpoints/versions/#list-versions-available-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (on given project)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose versions will be listed
     * @return ResponseEntity&lt;VersionsByWorkspaceModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<VersionsByWorkspaceModel> listVersionsAvailableInAProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<VersionsByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listVersionsAvailableInAProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List versions available in a project
     * This endpoint lists the versions that are *available* in a given project. Note that due to sharing this might be more than the versions *defined* by that project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/versions&#x60;](https://www.openproject.org/docs/api/endpoints/versions/#list-versions-available-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (on given project)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose versions will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec listVersionsAvailableInAProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listVersionsAvailableInAProjectRequestCreation(id);
    }

    /**
     * List versions available in a workspace
     * This endpoint lists the versions that are *available* in a given workspace. Note that due to sharing this might be more than the versions *defined* by that workspace.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (on given workspace)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose versions will be listed
     * @return VersionsByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listVersionsAvailableInAWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listVersionsAvailableInAWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<VersionsByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/versions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List versions available in a workspace
     * This endpoint lists the versions that are *available* in a given workspace. Note that due to sharing this might be more than the versions *defined* by that workspace.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (on given workspace)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose versions will be listed
     * @return VersionsByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public VersionsByWorkspaceModel listVersionsAvailableInAWorkspace(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<VersionsByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listVersionsAvailableInAWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List versions available in a workspace
     * This endpoint lists the versions that are *available* in a given workspace. Note that due to sharing this might be more than the versions *defined* by that workspace.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (on given workspace)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose versions will be listed
     * @return ResponseEntity&lt;VersionsByWorkspaceModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<VersionsByWorkspaceModel> listVersionsAvailableInAWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<VersionsByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listVersionsAvailableInAWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List versions available in a workspace
     * This endpoint lists the versions that are *available* in a given workspace. Note that due to sharing this might be more than the versions *defined* by that workspace.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (on given workspace)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose versions will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listVersionsAvailableInAWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listVersionsAvailableInAWorkspaceRequestCreation(id);
    }

    /**
     * Update Version
     * Updates the given version by applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a version&#39;s attributes and are accepted by the endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage versions in the version&#39;s project.
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Version id
     * @param versionWriteModel The versionWriteModel parameter
     * @return VersionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateVersionRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable VersionWriteModel versionWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/versions/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, versionWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update Version
     * Updates the given version by applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a version&#39;s attributes and are accepted by the endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage versions in the version&#39;s project.
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Version id
     * @param versionWriteModel The versionWriteModel parameter
     * @return VersionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public VersionReadModel updateVersion(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable VersionWriteModel versionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateVersionRequestCreation(id, versionWriteModel).body(localVarReturnType);
    }

    /**
     * Update Version
     * Updates the given version by applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a version&#39;s attributes and are accepted by the endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage versions in the version&#39;s project.
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Version id
     * @param versionWriteModel The versionWriteModel parameter
     * @return ResponseEntity&lt;VersionReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<VersionReadModel> updateVersionWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable VersionWriteModel versionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<VersionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateVersionRequestCreation(id, versionWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update Version
     * Updates the given version by applying the attributes provided in the body. Please note that while there is a fixed set of attributes, custom fields can extend a version&#39;s attributes and are accepted by the endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage versions in the version&#39;s project.
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Version id
     * @param versionWriteModel The versionWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateVersionWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable VersionWriteModel versionWriteModel) throws RestClientResponseException {
        return updateVersionRequestCreation(id, versionWriteModel);
    }

    /**
     * Version create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateVersionCreateFormRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/versions/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Version create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateVersionCreateForm() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateVersionCreateFormRequestCreation().body(localVarReturnType);
    }

    /**
     * Version create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateVersionCreateFormWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateVersionCreateFormRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Version create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateVersionCreateFormWithResponseSpec() throws RestClientResponseException {
        return validateVersionCreateFormRequestCreation();
    }

    /**
     * Version update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in the version&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateVersionUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateVersionUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/versions/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Version update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in the version&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateVersionUpdateForm(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateVersionUpdateFormRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Version update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in the version&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateVersionUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateVersionUpdateFormRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Version update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in the version&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateVersionUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return validateVersionUpdateFormRequestCreation(id);
    }
}
