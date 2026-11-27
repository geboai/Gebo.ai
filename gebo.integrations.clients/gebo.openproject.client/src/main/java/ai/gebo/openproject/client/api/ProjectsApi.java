package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.AITextActionCollectionModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.ListAvailableParentProjectCandidatesModel;
import ai.gebo.openproject.client.model.ProjectCollectionModel;
import ai.gebo.openproject.client.model.ProjectConfigurationModel;
import ai.gebo.openproject.client.model.ProjectModel;
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
public class ProjectsApi {
    private ApiClient apiClient;

    public ProjectsApi() {
        this(new ApiClient());
    }

    public ProjectsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create project
     * Creates a new project, applying the attributes provided in the body.  You can use the form and schema to be retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add project which is a global permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param projectModel The projectModel parameter
     * @return ProjectModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createProjectRequestCreation(@jakarta.annotation.Nullable ProjectModel projectModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects", HttpMethod.POST, pathParams, localVarQueryParams, projectModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create project
     * Creates a new project, applying the attributes provided in the body.  You can use the form and schema to be retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add project which is a global permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param projectModel The projectModel parameter
     * @return ProjectModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectModel createProject(@jakarta.annotation.Nullable ProjectModel projectModel) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createProjectRequestCreation(projectModel).body(localVarReturnType);
    }

    /**
     * Create project
     * Creates a new project, applying the attributes provided in the body.  You can use the form and schema to be retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add project which is a global permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param projectModel The projectModel parameter
     * @return ResponseEntity&lt;ProjectModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectModel> createProjectWithHttpInfo(@jakarta.annotation.Nullable ProjectModel projectModel) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createProjectRequestCreation(projectModel).toEntity(localVarReturnType);
    }

    /**
     * Create project
     * Creates a new project, applying the attributes provided in the body.  You can use the form and schema to be retrieve the valid attribute values and by that be guided towards successful creation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add project which is a global permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param projectModel The projectModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createProjectWithResponseSpec(@jakarta.annotation.Nullable ProjectModel projectModel) throws RestClientResponseException {
        return createProjectRequestCreation(projectModel);
    }

    /**
     * Create project copy
     * 
     * <p><b>302</b> - Returned if the request is successful. It will redirect to the job statuses API with the backend job that got created. You can query that endpoint to check the status of the copy, and eventually get the created project.
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** copy projects in the source project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createProjectCopyRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createProjectCopy", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/projects/{id}/copy", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create project copy
     * 
     * <p><b>302</b> - Returned if the request is successful. It will redirect to the job statuses API with the backend job that got created. You can query that endpoint to check the status of the copy, and eventually get the created project.
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** copy projects in the source project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void createProjectCopy(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        createProjectCopyRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Create project copy
     * 
     * <p><b>302</b> - Returned if the request is successful. It will redirect to the job statuses API with the backend job that got created. You can query that endpoint to check the status of the copy, and eventually get the created project.
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** copy projects in the source project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> createProjectCopyWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createProjectCopyRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Create project copy
     * 
     * <p><b>302</b> - Returned if the request is successful. It will redirect to the job statuses API with the backend job that got created. You can query that endpoint to check the status of the copy, and eventually get the created project.
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** copy projects in the source project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createProjectCopyWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return createProjectCopyRequestCreation(id);
    }

    /**
     * Delete Project
     * Deletes the project permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the project might exist well after the request has returned successfully. To prevent unwanted changes to the project scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the project was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the project cannot be deleted. This can happen when there are still references to the project in other workspaces that need to be severed at first.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/projects/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete Project
     * Deletes the project permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the project might exist well after the request has returned successfully. To prevent unwanted changes to the project scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the project was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the project cannot be deleted. This can happen when there are still references to the project in other workspaces that need to be severed at first.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete Project
     * Deletes the project permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the project might exist well after the request has returned successfully. To prevent unwanted changes to the project scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the project was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the project cannot be deleted. This can happen when there are still references to the project in other workspaces that need to be severed at first.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete Project
     * Deletes the project permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the project might exist well after the request has returned successfully. To prevent unwanted changes to the project scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the project was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the project cannot be deleted. This can happen when there are still references to the project in other workspaces that need to be severed at first.
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteProjectRequestCreation(id);
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
     * View project
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Project id
     * @return ProjectModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View project
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Project id
     * @return ProjectModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectModel getProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View project
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Project id
     * @return ResponseEntity&lt;ProjectModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectModel> getProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View project
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getProjectRequestCreation(id);
    }

    /**
     * View project configuration
     * Returns the configuration scoped to a specific project, including all global configuration properties plus project-specific settings.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the user cannot view it.
     * @param id Project id
     * @return ProjectConfigurationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProjectConfigurationRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getProjectConfiguration", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/configuration", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View project configuration
     * Returns the configuration scoped to a specific project, including all global configuration properties plus project-specific settings.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the user cannot view it.
     * @param id Project id
     * @return ProjectConfigurationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectConfigurationModel getProjectConfiguration(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectConfigurationRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View project configuration
     * Returns the configuration scoped to a specific project, including all global configuration properties plus project-specific settings.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the user cannot view it.
     * @param id Project id
     * @return ResponseEntity&lt;ProjectConfigurationModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectConfigurationModel> getProjectConfigurationWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectConfigurationRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View project configuration
     * Returns the configuration scoped to a specific project, including all global configuration properties plus project-specific settings.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the user cannot view it.
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getProjectConfigurationWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getProjectConfigurationRequestCreation(id);
    }

    /**
     * View project schema
     * Provides the schema describing a project resource. This endpoint is deprecated. As projects are workspaces, an equivalent schema can be found fetching &#x60;/api/v3/workspaces/schema&#x60;.
     * <p><b>200</b> - OK
     * @return WorkspacesSchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProjectSchemaRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/projects/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View project schema
     * Provides the schema describing a project resource. This endpoint is deprecated. As projects are workspaces, an equivalent schema can be found fetching &#x60;/api/v3/workspaces/schema&#x60;.
     * <p><b>200</b> - OK
     * @return WorkspacesSchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public WorkspacesSchemaModel getProjectSchema() throws RestClientResponseException {
        ParameterizedTypeReference<WorkspacesSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectSchemaRequestCreation().body(localVarReturnType);
    }

    /**
     * View project schema
     * Provides the schema describing a project resource. This endpoint is deprecated. As projects are workspaces, an equivalent schema can be found fetching &#x60;/api/v3/workspaces/schema&#x60;.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;WorkspacesSchemaModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<WorkspacesSchemaModel> getProjectSchemaWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<WorkspacesSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectSchemaRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * View project schema
     * Provides the schema describing a project resource. This endpoint is deprecated. As projects are workspaces, an equivalent schema can be found fetching &#x60;/api/v3/workspaces/schema&#x60;.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec getProjectSchemaWithResponseSpec() throws RestClientResponseException {
        return getProjectSchemaRequestCreation();
    }

    /**
     * View project status
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project status does not exist.
     * @param id Project status id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProjectStatusRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getProjectStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/project_statuses/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View project status
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project status does not exist.
     * @param id Project status id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getProjectStatus(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectStatusRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View project status
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project status does not exist.
     * @param id Project status id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getProjectStatusWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectStatusRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View project status
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project status does not exist.
     * @param id Project status id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getProjectStatusWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getProjectStatusRequestCreation(id);
    }

    /**
     * List AI text actions for a new work package
     * Lists the active AI text actions that can be run on the description of a work package about to be created in the given project with the given type: actions usable everywhere, actions scoped to all work package types and actions scoped to that type. Actions that inject the type template are listed only when the type, as configured in the project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** add work packages
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the &#x60;typeId&#x60; query parameter is missing or not an integer.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist, the client does not have sufficient permissions to see it, or the type is not enabled in the project.  **Required permission:** view project
     * @param id Project id
     * @param typeId Id of the type the work package is about to be created with. The type has to be enabled in the project.
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAiTextActionsByProjectRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer typeId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAiTextActionsByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'typeId' is set
        if (typeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'typeId' when calling listAiTextActionsByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "typeId", typeId));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/ai_text_actions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List AI text actions for a new work package
     * Lists the active AI text actions that can be run on the description of a work package about to be created in the given project with the given type: actions usable everywhere, actions scoped to all work package types and actions scoped to that type. Actions that inject the type template are listed only when the type, as configured in the project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** add work packages
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the &#x60;typeId&#x60; query parameter is missing or not an integer.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist, the client does not have sufficient permissions to see it, or the type is not enabled in the project.  **Required permission:** view project
     * @param id Project id
     * @param typeId Id of the type the work package is about to be created with. The type has to be enabled in the project.
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AITextActionCollectionModel listAiTextActionsByProject(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer typeId) throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsByProjectRequestCreation(id, typeId).body(localVarReturnType);
    }

    /**
     * List AI text actions for a new work package
     * Lists the active AI text actions that can be run on the description of a work package about to be created in the given project with the given type: actions usable everywhere, actions scoped to all work package types and actions scoped to that type. Actions that inject the type template are listed only when the type, as configured in the project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** add work packages
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the &#x60;typeId&#x60; query parameter is missing or not an integer.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist, the client does not have sufficient permissions to see it, or the type is not enabled in the project.  **Required permission:** view project
     * @param id Project id
     * @param typeId Id of the type the work package is about to be created with. The type has to be enabled in the project.
     * @return ResponseEntity&lt;AITextActionCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AITextActionCollectionModel> listAiTextActionsByProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer typeId) throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsByProjectRequestCreation(id, typeId).toEntity(localVarReturnType);
    }

    /**
     * List AI text actions for a new work package
     * Lists the active AI text actions that can be run on the description of a work package about to be created in the given project with the given type: actions usable everywhere, actions scoped to all work package types and actions scoped to that type. Actions that inject the type template are listed only when the type, as configured in the project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** add work packages
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the &#x60;typeId&#x60; query parameter is missing or not an integer.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist, the client does not have sufficient permissions to see it, or the type is not enabled in the project.  **Required permission:** view project
     * @param id Project id
     * @param typeId Id of the type the work package is about to be created with. The type has to be enabled in the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAiTextActionsByProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer typeId) throws RestClientResponseException {
        return listAiTextActionsByProjectRequestCreation(id, typeId);
    }

    /**
     * List available parent project candidates
     * Lists projects which can become parent to another project. Only sound candidates are returned. For instance a project cannot become parent of itself or its children.  To specify the project for which a parent is queried for, the &#x60;of&#x60; parameter can be provided. If no &#x60;of&#x60; parameter is provided, a new project is assumed. Then, the check for the hierarchy is omitted as a new project cannot be part of a hierarchy yet, instead &#x60;workspace_type&#x60; parameter can be passed defining it for new project.  Candidates can be filtered. Most commonly one will want to filter by name or identifier. You can do this through the &#x60;filters&#x60; parameter which works just like the work package index.  For instance to find all parent candidates with \&quot;rollout\&quot; in their name:  &#x60;&#x60;&#x60; ?filters&#x3D;[{\&quot;name_and_identifier\&quot;:{\&quot;operator\&quot;:\&quot;~\&quot;,\&quot;values\&quot;:[\&quot;rollout\&quot;]}}] &#x60;&#x60;&#x60;
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project in a project or the global add project permission
     * @param filters JSON specifying filter conditions.
     * @param of The id or identifier of the project the parent candidate is determined for
     * @param workspaceType The workspace type of the new project the parent candidate is determined for. Ignored when &#x60;of&#x60; parameter is provided. Note that while &#39;portfolio&#39; is supported as a type (since it is a type of Workspace), the endpoint will currently always return an empty resultset as portfolios cannot have parents.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint and allows all the filters and sortBy supported by the project list endpoint.
     * @return ListAvailableParentProjectCandidatesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableParentProjectCandidatesRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String of, @jakarta.annotation.Nullable String workspaceType, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "of", of));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "workspace_type", workspaceType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ListAvailableParentProjectCandidatesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/available_parent_projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List available parent project candidates
     * Lists projects which can become parent to another project. Only sound candidates are returned. For instance a project cannot become parent of itself or its children.  To specify the project for which a parent is queried for, the &#x60;of&#x60; parameter can be provided. If no &#x60;of&#x60; parameter is provided, a new project is assumed. Then, the check for the hierarchy is omitted as a new project cannot be part of a hierarchy yet, instead &#x60;workspace_type&#x60; parameter can be passed defining it for new project.  Candidates can be filtered. Most commonly one will want to filter by name or identifier. You can do this through the &#x60;filters&#x60; parameter which works just like the work package index.  For instance to find all parent candidates with \&quot;rollout\&quot; in their name:  &#x60;&#x60;&#x60; ?filters&#x3D;[{\&quot;name_and_identifier\&quot;:{\&quot;operator\&quot;:\&quot;~\&quot;,\&quot;values\&quot;:[\&quot;rollout\&quot;]}}] &#x60;&#x60;&#x60;
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project in a project or the global add project permission
     * @param filters JSON specifying filter conditions.
     * @param of The id or identifier of the project the parent candidate is determined for
     * @param workspaceType The workspace type of the new project the parent candidate is determined for. Ignored when &#x60;of&#x60; parameter is provided. Note that while &#39;portfolio&#39; is supported as a type (since it is a type of Workspace), the endpoint will currently always return an empty resultset as portfolios cannot have parents.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint and allows all the filters and sortBy supported by the project list endpoint.
     * @return ListAvailableParentProjectCandidatesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ListAvailableParentProjectCandidatesModel listAvailableParentProjectCandidates(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String of, @jakarta.annotation.Nullable String workspaceType, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<ListAvailableParentProjectCandidatesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableParentProjectCandidatesRequestCreation(filters, of, workspaceType, sortBy).body(localVarReturnType);
    }

    /**
     * List available parent project candidates
     * Lists projects which can become parent to another project. Only sound candidates are returned. For instance a project cannot become parent of itself or its children.  To specify the project for which a parent is queried for, the &#x60;of&#x60; parameter can be provided. If no &#x60;of&#x60; parameter is provided, a new project is assumed. Then, the check for the hierarchy is omitted as a new project cannot be part of a hierarchy yet, instead &#x60;workspace_type&#x60; parameter can be passed defining it for new project.  Candidates can be filtered. Most commonly one will want to filter by name or identifier. You can do this through the &#x60;filters&#x60; parameter which works just like the work package index.  For instance to find all parent candidates with \&quot;rollout\&quot; in their name:  &#x60;&#x60;&#x60; ?filters&#x3D;[{\&quot;name_and_identifier\&quot;:{\&quot;operator\&quot;:\&quot;~\&quot;,\&quot;values\&quot;:[\&quot;rollout\&quot;]}}] &#x60;&#x60;&#x60;
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project in a project or the global add project permission
     * @param filters JSON specifying filter conditions.
     * @param of The id or identifier of the project the parent candidate is determined for
     * @param workspaceType The workspace type of the new project the parent candidate is determined for. Ignored when &#x60;of&#x60; parameter is provided. Note that while &#39;portfolio&#39; is supported as a type (since it is a type of Workspace), the endpoint will currently always return an empty resultset as portfolios cannot have parents.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint and allows all the filters and sortBy supported by the project list endpoint.
     * @return ResponseEntity&lt;ListAvailableParentProjectCandidatesModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ListAvailableParentProjectCandidatesModel> listAvailableParentProjectCandidatesWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String of, @jakarta.annotation.Nullable String workspaceType, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<ListAvailableParentProjectCandidatesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableParentProjectCandidatesRequestCreation(filters, of, workspaceType, sortBy).toEntity(localVarReturnType);
    }

    /**
     * List available parent project candidates
     * Lists projects which can become parent to another project. Only sound candidates are returned. For instance a project cannot become parent of itself or its children.  To specify the project for which a parent is queried for, the &#x60;of&#x60; parameter can be provided. If no &#x60;of&#x60; parameter is provided, a new project is assumed. Then, the check for the hierarchy is omitted as a new project cannot be part of a hierarchy yet, instead &#x60;workspace_type&#x60; parameter can be passed defining it for new project.  Candidates can be filtered. Most commonly one will want to filter by name or identifier. You can do this through the &#x60;filters&#x60; parameter which works just like the work package index.  For instance to find all parent candidates with \&quot;rollout\&quot; in their name:  &#x60;&#x60;&#x60; ?filters&#x3D;[{\&quot;name_and_identifier\&quot;:{\&quot;operator\&quot;:\&quot;~\&quot;,\&quot;values\&quot;:[\&quot;rollout\&quot;]}}] &#x60;&#x60;&#x60;
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project in a project or the global add project permission
     * @param filters JSON specifying filter conditions.
     * @param of The id or identifier of the project the parent candidate is determined for
     * @param workspaceType The workspace type of the new project the parent candidate is determined for. Ignored when &#x60;of&#x60; parameter is provided. Note that while &#39;portfolio&#39; is supported as a type (since it is a type of Workspace), the endpoint will currently always return an empty resultset as portfolios cannot have parents.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint and allows all the filters and sortBy supported by the project list endpoint.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableParentProjectCandidatesWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String of, @jakarta.annotation.Nullable String workspaceType, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        return listAvailableParentProjectCandidatesRequestCreation(filters, of, workspaceType, sortBy);
    }

    /**
     * List projects
     * Returns a collection of projects. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain projects the client is allowed to see. Prior to OpenProject 17.0, only projects existed and the concept of workspaces wasn&#39;t implemented in the API. With 17.0 the other workspace types (program and portfolio) exist and will be returned alongside projects by this endpoint. This might surprise typed clients.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the project + ancestor: filters projects by their ancestor. A project is not considered to be its own ancestor. + available_project_attributes: filters projects based on the activated project project attributes. + created_at: based on the time the project was created + favorited: based on the favorited property of the project + id: based on projects&#39; id. + latest_activity_at: based on the time the last activity was registered on a project. + name_and_identifier: based on both the name and the identifier. + parent_id: filters projects by their parent. + principal: based on members of the project. + project_phase_any: based on the project phases active in a project. + project_status_code: based on status code of the project + storage_id: filters projects by linked storages + storage_url: filters projects by linked storages identified by the host url + type_id: based on the types active in a project. + user_action: based on the actions the current user has in the project. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the projects visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a project. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a project. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a project. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ProjectCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listProjectsRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
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

        ParameterizedTypeReference<ProjectCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List projects
     * Returns a collection of projects. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain projects the client is allowed to see. Prior to OpenProject 17.0, only projects existed and the concept of workspaces wasn&#39;t implemented in the API. With 17.0 the other workspace types (program and portfolio) exist and will be returned alongside projects by this endpoint. This might surprise typed clients.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the project + ancestor: filters projects by their ancestor. A project is not considered to be its own ancestor. + available_project_attributes: filters projects based on the activated project project attributes. + created_at: based on the time the project was created + favorited: based on the favorited property of the project + id: based on projects&#39; id. + latest_activity_at: based on the time the last activity was registered on a project. + name_and_identifier: based on both the name and the identifier. + parent_id: filters projects by their parent. + principal: based on members of the project. + project_phase_any: based on the project phases active in a project. + project_status_code: based on status code of the project + storage_id: filters projects by linked storages + storage_url: filters projects by linked storages identified by the host url + type_id: based on the types active in a project. + user_action: based on the actions the current user has in the project. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the projects visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a project. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a project. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a project. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ProjectCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectCollectionModel listProjects(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listProjectsRequestCreation(filters, sortBy, select).body(localVarReturnType);
    }

    /**
     * List projects
     * Returns a collection of projects. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain projects the client is allowed to see. Prior to OpenProject 17.0, only projects existed and the concept of workspaces wasn&#39;t implemented in the API. With 17.0 the other workspace types (program and portfolio) exist and will be returned alongside projects by this endpoint. This might surprise typed clients.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the project + ancestor: filters projects by their ancestor. A project is not considered to be its own ancestor. + available_project_attributes: filters projects based on the activated project project attributes. + created_at: based on the time the project was created + favorited: based on the favorited property of the project + id: based on projects&#39; id. + latest_activity_at: based on the time the last activity was registered on a project. + name_and_identifier: based on both the name and the identifier. + parent_id: filters projects by their parent. + principal: based on members of the project. + project_phase_any: based on the project phases active in a project. + project_status_code: based on status code of the project + storage_id: filters projects by linked storages + storage_url: filters projects by linked storages identified by the host url + type_id: based on the types active in a project. + user_action: based on the actions the current user has in the project. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the projects visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a project. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a project. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a project. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ResponseEntity&lt;ProjectCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectCollectionModel> listProjectsWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listProjectsRequestCreation(filters, sortBy, select).toEntity(localVarReturnType);
    }

    /**
     * List projects
     * Returns a collection of projects. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain projects the client is allowed to see. Prior to OpenProject 17.0, only projects existed and the concept of workspaces wasn&#39;t implemented in the API. With 17.0 the other workspace types (program and portfolio) exist and will be returned alongside projects by this endpoint. This might surprise typed clients.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the project + ancestor: filters projects by their ancestor. A project is not considered to be its own ancestor. + available_project_attributes: filters projects based on the activated project project attributes. + created_at: based on the time the project was created + favorited: based on the favorited property of the project + id: based on projects&#39; id. + latest_activity_at: based on the time the last activity was registered on a project. + name_and_identifier: based on both the name and the identifier. + parent_id: filters projects by their parent. + principal: based on members of the project. + project_phase_any: based on the project phases active in a project. + project_status_code: based on status code of the project + storage_id: filters projects by linked storages + storage_url: filters projects by linked storages identified by the host url + type_id: based on the types active in a project. + user_action: based on the actions the current user has in the project. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the projects visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a project. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a project. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a project. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listProjectsWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return listProjectsRequestCreation(filters, sortBy, select);
    }

    /**
     * List projects having version
     * This endpoint lists the projects where the given version is available.  The projects returned depend on the sharing settings of the given version, but are also limited to the projects that the current user is allowed to see.  This endpoint is deprecated and replaced by [&#x60;/api/v3/versions/{id}/workspaces&#x60;](https://www.openproject.org/docs/api/endpoints/projects/#list-workspaces-having-version)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the given version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listProjectsWithVersionRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listProjectsWithVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/versions/{id}/projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List projects having version
     * This endpoint lists the projects where the given version is available.  The projects returned depend on the sharing settings of the given version, but are also limited to the projects that the current user is allowed to see.  This endpoint is deprecated and replaced by [&#x60;/api/v3/versions/{id}/workspaces&#x60;](https://www.openproject.org/docs/api/endpoints/projects/#list-workspaces-having-version)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the given version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public Object listProjectsWithVersion(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listProjectsWithVersionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List projects having version
     * This endpoint lists the projects where the given version is available.  The projects returned depend on the sharing settings of the given version, but are also limited to the projects that the current user is allowed to see.  This endpoint is deprecated and replaced by [&#x60;/api/v3/versions/{id}/workspaces&#x60;](https://www.openproject.org/docs/api/endpoints/projects/#list-workspaces-having-version)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the given version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<Object> listProjectsWithVersionWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listProjectsWithVersionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List projects having version
     * This endpoint lists the projects where the given version is available.  The projects returned depend on the sharing settings of the given version, but are also limited to the projects that the current user is allowed to see.  This endpoint is deprecated and replaced by [&#x60;/api/v3/versions/{id}/workspaces&#x60;](https://www.openproject.org/docs/api/endpoints/projects/#list-workspaces-having-version)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any project where the given version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec listProjectsWithVersionWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listProjectsWithVersionRequestCreation(id);
    }

    /**
     * List workspaces having version
     * This endpoint lists the workspaces where the given version is available.  The workspaces returned depend on the sharing settings of the given version, but are also limited to the workspaces that the current user is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any workspace where the given version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkspacesWithVersionRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkspacesWithVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/versions/{id}/workspaces", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List workspaces having version
     * This endpoint lists the workspaces where the given version is available.  The workspaces returned depend on the sharing settings of the given version, but are also limited to the workspaces that the current user is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any workspace where the given version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listWorkspacesWithVersion(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkspacesWithVersionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List workspaces having version
     * This endpoint lists the workspaces where the given version is available.  The workspaces returned depend on the sharing settings of the given version, but are also limited to the workspaces that the current user is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any workspace where the given version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listWorkspacesWithVersionWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkspacesWithVersionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List workspaces having version
     * This endpoint lists the workspaces where the given version is available.  The workspaces returned depend on the sharing settings of the given version, but are also limited to the workspaces that the current user is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the version does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage versions (any workspace where the given version is available)  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Version id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkspacesWithVersionWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkspacesWithVersionRequestCreation(id);
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
     * Update Project
     * Updates the given project by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the project to be altered
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Project id
     * @param projectModel The projectModel parameter
     * @return ProjectModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateProjectRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ProjectModel projectModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, projectModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update Project
     * Updates the given project by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the project to be altered
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Project id
     * @param projectModel The projectModel parameter
     * @return ProjectModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectModel updateProject(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ProjectModel projectModel) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateProjectRequestCreation(id, projectModel).body(localVarReturnType);
    }

    /**
     * Update Project
     * Updates the given project by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the project to be altered
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Project id
     * @param projectModel The projectModel parameter
     * @return ResponseEntity&lt;ProjectModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectModel> updateProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ProjectModel projectModel) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateProjectRequestCreation(id, projectModel).toEntity(localVarReturnType);
    }

    /**
     * Update Project
     * Updates the given project by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the project to be altered
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Project id
     * @param projectModel The projectModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable ProjectModel projectModel) throws RestClientResponseException {
        return updateProjectRequestCreation(id, projectModel);
    }

    /**
     * Project copy form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** copy projects in the source project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateProjectCopyFormRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateProjectCopyForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/projects/{id}/copy/form", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Project copy form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** copy projects in the source project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateProjectCopyForm(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateProjectCopyFormRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Project copy form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** copy projects in the source project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateProjectCopyFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateProjectCopyFormRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Project copy form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** copy projects in the source project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateProjectCopyFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return validateProjectCopyFormRequestCreation(id);
    }

    /**
     * Project create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add project which is a global permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param body The body parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateProjectCreateFormRequestCreation(@jakarta.annotation.Nullable Object body) throws RestClientResponseException {
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/form", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Project create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add project which is a global permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param body The body parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object validateProjectCreateForm(@jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateProjectCreateFormRequestCreation(body).body(localVarReturnType);
    }

    /**
     * Project create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add project which is a global permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param body The body parameter
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> validateProjectCreateFormWithHttpInfo(@jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateProjectCreateFormRequestCreation(body).toEntity(localVarReturnType);
    }

    /**
     * Project create form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add project which is a global permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateProjectCreateFormWithResponseSpec(@jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return validateProjectCreateFormRequestCreation(body);
    }

    /**
     * Project update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit projects in the project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateProjectUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateProjectUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/projects/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Project update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit projects in the project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateProjectUpdateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateProjectUpdateFormRequestCreation(id, body).body(localVarReturnType);
    }

    /**
     * Project update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit projects in the project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateProjectUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateProjectUpdateFormRequestCreation(id, body).toEntity(localVarReturnType);
    }

    /**
     * Project update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit projects in the project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Project id
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateProjectUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return validateProjectUpdateFormRequestCreation(id, body);
    }
}
