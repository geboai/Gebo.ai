package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.QueryFilterInstanceSchemaModel;

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
public class QueryFilterInstanceSchemaApi {
    private ApiClient apiClient;

    public QueryFilterInstanceSchemaApi() {
        this(new ApiClient());
    }

    public QueryFilterInstanceSchemaApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View Query Filter Instance Schema
     * Retrieve an individual QueryFilterInstanceSchema as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * <p><b>404</b> - Returned if the QueryFilterInstanceSchema does not exist.
     * @param id QueryFilterInstanceSchema identifier. The identifier is the filter identifier.
     * @return QueryFilterInstanceSchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getQueryFilterInstanceSchemaRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getQueryFilterInstanceSchema", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<QueryFilterInstanceSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/filter_instance_schemas/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Query Filter Instance Schema
     * Retrieve an individual QueryFilterInstanceSchema as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * <p><b>404</b> - Returned if the QueryFilterInstanceSchema does not exist.
     * @param id QueryFilterInstanceSchema identifier. The identifier is the filter identifier.
     * @return QueryFilterInstanceSchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public QueryFilterInstanceSchemaModel getQueryFilterInstanceSchema(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<QueryFilterInstanceSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getQueryFilterInstanceSchemaRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View Query Filter Instance Schema
     * Retrieve an individual QueryFilterInstanceSchema as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * <p><b>404</b> - Returned if the QueryFilterInstanceSchema does not exist.
     * @param id QueryFilterInstanceSchema identifier. The identifier is the filter identifier.
     * @return ResponseEntity&lt;QueryFilterInstanceSchemaModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<QueryFilterInstanceSchemaModel> getQueryFilterInstanceSchemaWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<QueryFilterInstanceSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getQueryFilterInstanceSchemaRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View Query Filter Instance Schema
     * Retrieve an individual QueryFilterInstanceSchema as identified by the id parameter.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * <p><b>404</b> - Returned if the QueryFilterInstanceSchema does not exist.
     * @param id QueryFilterInstanceSchema identifier. The identifier is the filter identifier.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getQueryFilterInstanceSchemaWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getQueryFilterInstanceSchemaRequestCreation(id);
    }

    /**
     * List Query Filter Instance Schemas
     * Returns the list of QueryFilterInstanceSchemas defined for a global query. That is a query not assigned to a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listQueryFilterInstanceSchemasRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/queries/filter_instance_schemas", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas
     * Returns the list of QueryFilterInstanceSchemas defined for a global query. That is a query not assigned to a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listQueryFilterInstanceSchemas() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listQueryFilterInstanceSchemasRequestCreation().body(localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas
     * Returns the list of QueryFilterInstanceSchemas defined for a global query. That is a query not assigned to a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listQueryFilterInstanceSchemasWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listQueryFilterInstanceSchemasRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas
     * Returns the list of QueryFilterInstanceSchemas defined for a global query. That is a query not assigned to a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listQueryFilterInstanceSchemasWithResponseSpec() throws RestClientResponseException {
        return listQueryFilterInstanceSchemasRequestCreation();
    }

    /**
     * List Query Filter Instance Schemas for Project
     * Returns the list of QueryFilterInstanceSchemas defined for a query of the specified project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/queries/filter_instance_schemas&#x60;](https://www.openproject.org/docs/api/endpoints/query-filter-instance-schema/#list-query-filter-instance-schemas-for-workspace)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * @param id Project id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listQueryFilterInstanceSchemasForProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listQueryFilterInstanceSchemasForProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/queries/filter_instance_schemas", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas for Project
     * Returns the list of QueryFilterInstanceSchemas defined for a query of the specified project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/queries/filter_instance_schemas&#x60;](https://www.openproject.org/docs/api/endpoints/query-filter-instance-schema/#list-query-filter-instance-schemas-for-workspace)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * @param id Project id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public Object listQueryFilterInstanceSchemasForProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listQueryFilterInstanceSchemasForProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas for Project
     * Returns the list of QueryFilterInstanceSchemas defined for a query of the specified project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/queries/filter_instance_schemas&#x60;](https://www.openproject.org/docs/api/endpoints/query-filter-instance-schema/#list-query-filter-instance-schemas-for-workspace)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * @param id Project id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<Object> listQueryFilterInstanceSchemasForProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listQueryFilterInstanceSchemasForProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas for Project
     * Returns the list of QueryFilterInstanceSchemas defined for a query of the specified project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/queries/filter_instance_schemas&#x60;](https://www.openproject.org/docs/api/endpoints/query-filter-instance-schema/#list-query-filter-instance-schemas-for-workspace)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any project
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec listQueryFilterInstanceSchemasForProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listQueryFilterInstanceSchemasForProjectRequestCreation(id);
    }

    /**
     * List Query Filter Instance Schemas for Workspace
     * Returns the list of QueryFilterInstanceSchemas defined for a query of the specified workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any workspace
     * @param id Workspace id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listQueryFilterInstanceSchemasForWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listQueryFilterInstanceSchemasForWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/queries/filter_instance_schemas", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas for Workspace
     * Returns the list of QueryFilterInstanceSchemas defined for a query of the specified workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any workspace
     * @param id Workspace id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listQueryFilterInstanceSchemasForWorkspace(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listQueryFilterInstanceSchemasForWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas for Workspace
     * Returns the list of QueryFilterInstanceSchemas defined for a query of the specified workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any workspace
     * @param id Workspace id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listQueryFilterInstanceSchemasForWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listQueryFilterInstanceSchemasForWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List Query Filter Instance Schemas for Workspace
     * Returns the list of QueryFilterInstanceSchemas defined for a query of the specified workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see it.  **Required permission:** view work package in any workspace
     * @param id Workspace id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listQueryFilterInstanceSchemasForWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listQueryFilterInstanceSchemasForWorkspaceRequestCreation(id);
    }
}
