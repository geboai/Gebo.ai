package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;

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
public class FavoritesApi {
    private ApiClient apiClient;

    public FavoritesApi() {
        this(new ApiClient());
    }

    public FavoritesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Favorite Project
     * Adds the project to the current user&#39;s favorites.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/favorite&#x60;](https://www.openproject.org/docs/api/endpoints/workspaces/#favorite-workspace)
     * <p><b>204</b> - Returned if the project was successfully added to favorites.
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec favoriteProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling favoriteProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/projects/{id}/favorite", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Favorite Project
     * Adds the project to the current user&#39;s favorites.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/favorite&#x60;](https://www.openproject.org/docs/api/endpoints/workspaces/#favorite-workspace)
     * <p><b>204</b> - Returned if the project was successfully added to favorites.
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public void favoriteProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        favoriteProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Favorite Project
     * Adds the project to the current user&#39;s favorites.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/favorite&#x60;](https://www.openproject.org/docs/api/endpoints/workspaces/#favorite-workspace)
     * <p><b>204</b> - Returned if the project was successfully added to favorites.
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<Void> favoriteProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return favoriteProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Favorite Project
     * Adds the project to the current user&#39;s favorites.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/favorite&#x60;](https://www.openproject.org/docs/api/endpoints/workspaces/#favorite-workspace)
     * <p><b>204</b> - Returned if the project was successfully added to favorites.
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec favoriteProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return favoriteProjectRequestCreation(id);
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
     * Unfavorite Project
     * Removes the project from the current user&#39;s favorites.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/favorite&#x60;](https://www.openproject.org/docs/api/endpoints/workspaces/#unfavorite-workspace)
     * <p><b>204</b> - Returned if the project was successfully removed from favorites.
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unfavoriteProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling unfavoriteProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/projects/{id}/favorite", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unfavorite Project
     * Removes the project from the current user&#39;s favorites.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/favorite&#x60;](https://www.openproject.org/docs/api/endpoints/workspaces/#unfavorite-workspace)
     * <p><b>204</b> - Returned if the project was successfully removed from favorites.
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public void unfavoriteProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        unfavoriteProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unfavorite Project
     * Removes the project from the current user&#39;s favorites.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/favorite&#x60;](https://www.openproject.org/docs/api/endpoints/workspaces/#unfavorite-workspace)
     * <p><b>204</b> - Returned if the project was successfully removed from favorites.
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<Void> unfavoriteProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unfavoriteProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unfavorite Project
     * Removes the project from the current user&#39;s favorites.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/favorite&#x60;](https://www.openproject.org/docs/api/endpoints/workspaces/#unfavorite-workspace)
     * <p><b>204</b> - Returned if the project was successfully removed from favorites.
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec unfavoriteProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return unfavoriteProjectRequestCreation(id);
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
