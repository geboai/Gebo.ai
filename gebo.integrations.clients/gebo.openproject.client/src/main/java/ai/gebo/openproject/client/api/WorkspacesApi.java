package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.TypesByWorkspaceModel;
import ai.gebo.openproject.client.model.WorkspacesSchemaModel;

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
public class WorkspacesApi {
    private ApiClient apiClient;

    public WorkspacesApi() {
        this(new ApiClient());
    }

    public WorkspacesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Favorite Workspace
     * Adds the workspace to the current user&#39;s favorites.
     * <p><b>204</b> - Returned if the workspace was successfully added to favorites.
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec favoriteWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling favoriteWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/favorite", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Favorite Workspace
     * Adds the workspace to the current user&#39;s favorites.
     * <p><b>204</b> - Returned if the workspace was successfully added to favorites.
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void favoriteWorkspace(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        favoriteWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Favorite Workspace
     * Adds the workspace to the current user&#39;s favorites.
     * <p><b>204</b> - Returned if the workspace was successfully added to favorites.
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> favoriteWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return favoriteWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Favorite Workspace
     * Adds the workspace to the current user&#39;s favorites.
     * <p><b>204</b> - Returned if the workspace was successfully added to favorites.
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Workspace id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec favoriteWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return favoriteWorkspaceRequestCreation(id);
    }

    /**
     * View workspace schema
     * 
     * <p><b>200</b> - OK
     * @return WorkspacesSchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getWorkspaceSchemaRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<WorkspacesSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View workspace schema
     * 
     * <p><b>200</b> - OK
     * @return WorkspacesSchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkspacesSchemaModel getWorkspaceSchema() throws RestClientResponseException {
        ParameterizedTypeReference<WorkspacesSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkspaceSchemaRequestCreation().body(localVarReturnType);
    }

    /**
     * View workspace schema
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;WorkspacesSchemaModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkspacesSchemaModel> getWorkspaceSchemaWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<WorkspacesSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkspaceSchemaRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * View workspace schema
     * 
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWorkspaceSchemaWithResponseSpec() throws RestClientResponseException {
        return getWorkspaceSchemaRequestCreation();
    }

    /**
     * List types available in a workspace
     * This endpoint lists the types that are *available* in a given workspace.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage types (on given workspace)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose types will be listed
     * @return TypesByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listTypesAvailableInAWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listTypesAvailableInAWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<TypesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/types", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List types available in a workspace
     * This endpoint lists the types that are *available* in a given workspace.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage types (on given workspace)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose types will be listed
     * @return TypesByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TypesByWorkspaceModel listTypesAvailableInAWorkspace(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TypesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTypesAvailableInAWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List types available in a workspace
     * This endpoint lists the types that are *available* in a given workspace.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage types (on given workspace)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose types will be listed
     * @return ResponseEntity&lt;TypesByWorkspaceModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TypesByWorkspaceModel> listTypesAvailableInAWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TypesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTypesAvailableInAWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List types available in a workspace
     * This endpoint lists the types that are *available* in a given workspace.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage types (on given workspace)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose types will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listTypesAvailableInAWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listTypesAvailableInAWorkspaceRequestCreation(id);
    }

    /**
     * Unfavorite Workspace
     * Removes the workspace from the current user&#39;s favorites.
     * <p><b>204</b> - Returned if the workspace was successfully removed from favorites.
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unfavoriteWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling unfavoriteWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/favorite", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unfavorite Workspace
     * Removes the workspace from the current user&#39;s favorites.
     * <p><b>204</b> - Returned if the workspace was successfully removed from favorites.
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void unfavoriteWorkspace(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        unfavoriteWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unfavorite Workspace
     * Removes the workspace from the current user&#39;s favorites.
     * <p><b>204</b> - Returned if the workspace was successfully removed from favorites.
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Workspace id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> unfavoriteWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unfavoriteWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unfavorite Workspace
     * Removes the workspace from the current user&#39;s favorites.
     * <p><b>204</b> - Returned if the workspace was successfully removed from favorites.
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Workspace id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec unfavoriteWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return unfavoriteWorkspaceRequestCreation(id);
    }
}
