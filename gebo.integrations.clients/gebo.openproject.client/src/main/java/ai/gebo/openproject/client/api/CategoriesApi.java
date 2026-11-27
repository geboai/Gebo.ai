package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.CategoriesByWorkspaceModel;
import ai.gebo.openproject.client.model.CategoryModel;
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
public class CategoriesApi {
    private ApiClient apiClient;

    public CategoriesApi() {
        this(new ApiClient());
    }

    public CategoriesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View Category
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the category does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project (defining the category)  *Note: A client without sufficient permissions shall not be able to test for the existence of a category. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Category id
     * @return CategoryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCategoryRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CategoryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/categories/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Category
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the category does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project (defining the category)  *Note: A client without sufficient permissions shall not be able to test for the existence of a category. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Category id
     * @return CategoryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public CategoryModel getCategory(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CategoryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCategoryRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View Category
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the category does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project (defining the category)  *Note: A client without sufficient permissions shall not be able to test for the existence of a category. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Category id
     * @return ResponseEntity&lt;CategoryModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CategoryModel> getCategoryWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CategoryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCategoryRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View Category
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the category does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project (defining the category)  *Note: A client without sufficient permissions shall not be able to test for the existence of a category. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Category id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCategoryWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getCategoryRequestCreation(id);
    }

    /**
     * List categories of a project
     * List all categories of a project This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/categories&#x60;](https://www.openproject.org/docs/api/endpoints/categories/#list-categories-of-a-workspace) 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose categories will be listed
     * @return CategoriesByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listCategoriesOfAProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listCategoriesOfAProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CategoriesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/categories", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List categories of a project
     * List all categories of a project This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/categories&#x60;](https://www.openproject.org/docs/api/endpoints/categories/#list-categories-of-a-workspace) 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose categories will be listed
     * @return CategoriesByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public CategoriesByWorkspaceModel listCategoriesOfAProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CategoriesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listCategoriesOfAProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List categories of a project
     * List all categories of a project This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/categories&#x60;](https://www.openproject.org/docs/api/endpoints/categories/#list-categories-of-a-workspace) 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose categories will be listed
     * @return ResponseEntity&lt;CategoriesByWorkspaceModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<CategoriesByWorkspaceModel> listCategoriesOfAProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CategoriesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listCategoriesOfAProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List categories of a project
     * List all categories of a project This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/categories&#x60;](https://www.openproject.org/docs/api/endpoints/categories/#list-categories-of-a-workspace) 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the project whose categories will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec listCategoriesOfAProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listCategoriesOfAProjectRequestCreation(id);
    }

    /**
     * List categories of a workspace
     * List all categories of a project
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose categories will be listed
     * @return CategoriesByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listCategoriesOfAWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listCategoriesOfAWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CategoriesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/categories", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List categories of a workspace
     * List all categories of a project
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose categories will be listed
     * @return CategoriesByWorkspaceModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public CategoriesByWorkspaceModel listCategoriesOfAWorkspace(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CategoriesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listCategoriesOfAWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List categories of a workspace
     * List all categories of a project
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose categories will be listed
     * @return ResponseEntity&lt;CategoriesByWorkspaceModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CategoriesByWorkspaceModel> listCategoriesOfAWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CategoriesByWorkspaceModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listCategoriesOfAWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List categories of a workspace
     * List all categories of a project
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose categories will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listCategoriesOfAWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listCategoriesOfAWorkspaceRequestCreation(id);
    }
}
