package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.TypeModel;
import ai.gebo.openproject.client.model.TypesByWorkspaceModel;

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
public class TypesApi {
    private ApiClient apiClient;

    public TypesApi() {
        this(new ApiClient());
    }

    public TypesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View Type
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package or manage types (on any project)
     * @param id Type id
     * @return TypeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTypeRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getType", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<TypeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/types/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Type
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package or manage types (on any project)
     * @param id Type id
     * @return TypeModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public TypeModel getType(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TypeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTypeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View Type
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package or manage types (on any project)
     * @param id Type id
     * @return ResponseEntity&lt;TypeModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<TypeModel> getTypeWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TypeModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getTypeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View Type
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package or manage types (on any project)
     * @param id Type id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTypeWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getTypeRequestCreation(id);
    }

    /**
     * List all Types
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package or manage types (on any project)
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAllTypesRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/types", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Types
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package or manage types (on any project)
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listAllTypes() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAllTypesRequestCreation().body(localVarReturnType);
    }

    /**
     * List all Types
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package or manage types (on any project)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listAllTypesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAllTypesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all Types
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package or manage types (on any project)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAllTypesWithResponseSpec() throws RestClientResponseException {
        return listAllTypesRequestCreation();
    }

    /**
     * List types available in a project
     * This endpoint lists the types that are *available* in a given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/types&#x60;](https://www.openproject.org/docs/api/endpoints/types/#list-types-available-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage types (on given project)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose types will be listed
     * @return TypesByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listTypesAvailableInAProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listTypesAvailableInAProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/projects/{id}/types", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List types available in a project
     * This endpoint lists the types that are *available* in a given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/types&#x60;](https://www.openproject.org/docs/api/endpoints/types/#list-types-available-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage types (on given project)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose types will be listed
     * @return TypesByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public TypesByWorkspaceModel listTypesAvailableInAProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TypesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTypesAvailableInAProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List types available in a project
     * This endpoint lists the types that are *available* in a given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/types&#x60;](https://www.openproject.org/docs/api/endpoints/types/#list-types-available-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage types (on given project)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose types will be listed
     * @return ResponseEntity&lt;TypesByWorkspaceModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<TypesByWorkspaceModel> listTypesAvailableInAProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<TypesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTypesAvailableInAProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List types available in a project
     * This endpoint lists the types that are *available* in a given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/types&#x60;](https://www.openproject.org/docs/api/endpoints/types/#list-types-available-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages **or** manage types (on given project)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose types will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec listTypesAvailableInAProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listTypesAvailableInAProjectRequestCreation(id);
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
}
