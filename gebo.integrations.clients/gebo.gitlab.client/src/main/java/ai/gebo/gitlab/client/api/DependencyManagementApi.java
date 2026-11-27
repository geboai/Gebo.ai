package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesDependenciesVulnerabilities;
import ai.gebo.gitlab.client.model.APIEntitiesDependency;
import java.io.File;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdDependencyListExportsRequest;
import ai.gebo.gitlab.client.model.PostApiV4PipelinesIdDependencyListExportsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdDependencyListExportsRequest;

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
public class DependencyManagementApi {
    private ApiClient apiClient;

    public DependencyManagementApi() {
        this(new ApiClient());
    }

    public DependencyManagementApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve a single dependency list export
     * Retrieves a dependency list export.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param exportId ID of the dependency list export.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4DependencyListExportsExportIdRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter exportId) throws RestClientResponseException {
        // verify the required parameter 'exportId' is set
        if (exportId == null) {
            throw new RestClientResponseException("Missing the required parameter 'exportId' when calling getApiV4DependencyListExportsExportId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("export_id", exportId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/dependency_list_exports/{export_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a single dependency list export
     * Retrieves a dependency list export.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param exportId ID of the dependency list export.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4DependencyListExportsExportId(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter exportId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4DependencyListExportsExportIdRequestCreation(exportId).body(localVarReturnType);
    }

    /**
     * Retrieve a single dependency list export
     * Retrieves a dependency list export.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param exportId ID of the dependency list export.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4DependencyListExportsExportIdWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter exportId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DependencyListExportsExportIdRequestCreation(exportId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a single dependency list export
     * Retrieves a dependency list export.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param exportId ID of the dependency list export.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4DependencyListExportsExportIdWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter exportId) throws RestClientResponseException {
        return getApiV4DependencyListExportsExportIdRequestCreation(exportId);
    }

    /**
     * Download dependency list export
     * Downloads a dependency list export. In CycloneDX exports, a component with an SPDX license expression, for example &#x60;MIT AND Zlib&#x60;, has the full expression in &#x60;licenses[].license.name&#x60;, and that entry has no &#x60;id&#x60; field.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @param exportId ID of the dependency list export.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4DependencyListExportsExportIdDownloadRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter exportId) throws RestClientResponseException {
        // verify the required parameter 'exportId' is set
        if (exportId == null) {
            throw new RestClientResponseException("Missing the required parameter 'exportId' when calling getApiV4DependencyListExportsExportIdDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("export_id", exportId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/dependency_list_exports/{export_id}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download dependency list export
     * Downloads a dependency list export. In CycloneDX exports, a component with an SPDX license expression, for example &#x60;MIT AND Zlib&#x60;, has the full expression in &#x60;licenses[].license.name&#x60;, and that entry has no &#x60;id&#x60; field.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @param exportId ID of the dependency list export.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4DependencyListExportsExportIdDownload(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter exportId) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DependencyListExportsExportIdDownloadRequestCreation(exportId).body(localVarReturnType);
    }

    /**
     * Download dependency list export
     * Downloads a dependency list export. In CycloneDX exports, a component with an SPDX license expression, for example &#x60;MIT AND Zlib&#x60;, has the full expression in &#x60;licenses[].license.name&#x60;, and that entry has no &#x60;id&#x60; field.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @param exportId ID of the dependency list export.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4DependencyListExportsExportIdDownloadWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter exportId) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DependencyListExportsExportIdDownloadRequestCreation(exportId).toEntity(localVarReturnType);
    }

    /**
     * Download dependency list export
     * Downloads a dependency list export. In CycloneDX exports, a component with an SPDX license expression, for example &#x60;MIT AND Zlib&#x60;, has the full expression in &#x60;licenses[].license.name&#x60;, and that entry has no &#x60;id&#x60; field.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @param exportId ID of the dependency list export.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4DependencyListExportsExportIdDownloadWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter exportId) throws RestClientResponseException {
        return getApiV4DependencyListExportsExportIdDownloadRequestCreation(exportId);
    }

    /**
     * Get vulnerabilities
     * Returns vulnerabilities related to an occurrence.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param id ID of the occurrence.
     * @return List&lt;APIEntitiesDependenciesVulnerabilities&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4OccurrencesVulnerabilitiesRequestCreation(@jakarta.annotation.Nullable String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4OccurrencesVulnerabilities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "id", id));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDependenciesVulnerabilities>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/occurrences/vulnerabilities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get vulnerabilities
     * Returns vulnerabilities related to an occurrence.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param id ID of the occurrence.
     * @return List&lt;APIEntitiesDependenciesVulnerabilities&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDependenciesVulnerabilities> getApiV4OccurrencesVulnerabilities(@jakarta.annotation.Nullable String id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDependenciesVulnerabilities>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OccurrencesVulnerabilitiesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get vulnerabilities
     * Returns vulnerabilities related to an occurrence.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param id ID of the occurrence.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDependenciesVulnerabilities&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDependenciesVulnerabilities>> getApiV4OccurrencesVulnerabilitiesWithHttpInfo(@jakarta.annotation.Nullable String id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDependenciesVulnerabilities>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OccurrencesVulnerabilitiesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get vulnerabilities
     * Returns vulnerabilities related to an occurrence.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param id ID of the occurrence.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4OccurrencesVulnerabilitiesWithResponseSpec(@jakarta.annotation.Nullable String id) throws RestClientResponseException {
        return getApiV4OccurrencesVulnerabilitiesRequestCreation(id);
    }

    /**
     * List all project dependencies
     * Lists all dependencies for a specified project. This operation partially mirrors the dependency list feature, which is available only for languages and package managers supported by Gemnasium. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageManager Filter results by the specified package manager.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDependency&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDependenciesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> packageManager, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDependencies", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "package_manager", packageManager));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/dependencies", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project dependencies
     * Lists all dependencies for a specified project. This operation partially mirrors the dependency list feature, which is available only for languages and package managers supported by Gemnasium. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageManager Filter results by the specified package manager.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDependency&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDependency> getApiV4ProjectsIdDependencies(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> packageManager, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDependenciesRequestCreation(id, packageManager, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project dependencies
     * Lists all dependencies for a specified project. This operation partially mirrors the dependency list feature, which is available only for languages and package managers supported by Gemnasium. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageManager Filter results by the specified package manager.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDependency&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDependency>> getApiV4ProjectsIdDependenciesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> packageManager, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDependency>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDependenciesRequestCreation(id, packageManager, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project dependencies
     * Lists all dependencies for a specified project. This operation partially mirrors the dependency list feature, which is available only for languages and package managers supported by Gemnasium. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageManager Filter results by the specified package manager.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDependenciesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> packageManager, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdDependenciesRequestCreation(id, packageManager, page, perPage);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDependencyListExportsRequest The postApiV4GroupsIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDependencyListExportsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4GroupsIdDependencyListExportsRequest postApiV4GroupsIdDependencyListExportsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDependencyListExports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/dependency_list_exports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdDependencyListExportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDependencyListExportsRequest The postApiV4GroupsIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdDependencyListExports(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4GroupsIdDependencyListExportsRequest postApiV4GroupsIdDependencyListExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdDependencyListExportsRequestCreation(id, postApiV4GroupsIdDependencyListExportsRequest).body(localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDependencyListExportsRequest The postApiV4GroupsIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdDependencyListExportsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4GroupsIdDependencyListExportsRequest postApiV4GroupsIdDependencyListExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDependencyListExportsRequestCreation(id, postApiV4GroupsIdDependencyListExportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDependencyListExportsRequest The postApiV4GroupsIdDependencyListExportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDependencyListExportsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4GroupsIdDependencyListExportsRequest postApiV4GroupsIdDependencyListExportsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdDependencyListExportsRequestCreation(id, postApiV4GroupsIdDependencyListExportsRequest);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the pipeline.
     * @param postApiV4PipelinesIdDependencyListExportsRequest The postApiV4PipelinesIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4PipelinesIdDependencyListExportsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4PipelinesIdDependencyListExportsRequest postApiV4PipelinesIdDependencyListExportsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4PipelinesIdDependencyListExports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/pipelines/{id}/dependency_list_exports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4PipelinesIdDependencyListExportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the pipeline.
     * @param postApiV4PipelinesIdDependencyListExportsRequest The postApiV4PipelinesIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4PipelinesIdDependencyListExports(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4PipelinesIdDependencyListExportsRequest postApiV4PipelinesIdDependencyListExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4PipelinesIdDependencyListExportsRequestCreation(id, postApiV4PipelinesIdDependencyListExportsRequest).body(localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the pipeline.
     * @param postApiV4PipelinesIdDependencyListExportsRequest The postApiV4PipelinesIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4PipelinesIdDependencyListExportsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4PipelinesIdDependencyListExportsRequest postApiV4PipelinesIdDependencyListExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PipelinesIdDependencyListExportsRequestCreation(id, postApiV4PipelinesIdDependencyListExportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the pipeline.
     * @param postApiV4PipelinesIdDependencyListExportsRequest The postApiV4PipelinesIdDependencyListExportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4PipelinesIdDependencyListExportsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4PipelinesIdDependencyListExportsRequest postApiV4PipelinesIdDependencyListExportsRequest) throws RestClientResponseException {
        return postApiV4PipelinesIdDependencyListExportsRequestCreation(id, postApiV4PipelinesIdDependencyListExportsRequest);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDependencyListExportsRequest The postApiV4ProjectsIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdDependencyListExportsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdDependencyListExportsRequest postApiV4ProjectsIdDependencyListExportsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdDependencyListExports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/dependency_list_exports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdDependencyListExportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDependencyListExportsRequest The postApiV4ProjectsIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdDependencyListExports(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdDependencyListExportsRequest postApiV4ProjectsIdDependencyListExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdDependencyListExportsRequestCreation(id, postApiV4ProjectsIdDependencyListExportsRequest).body(localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDependencyListExportsRequest The postApiV4ProjectsIdDependencyListExportsRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdDependencyListExportsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdDependencyListExportsRequest postApiV4ProjectsIdDependencyListExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDependencyListExportsRequestCreation(id, postApiV4ProjectsIdDependencyListExportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a dependency list export
     * Creates a CycloneDX JSON export for all the project dependencies detected in a pipeline. If an authenticated user does not have the read_dependency permission, this request returns a &#x60;403 Forbidden&#x60; status code. Only the author of an export can access it. The export is automatically deleted at the time specified in its &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDependencyListExportsRequest The postApiV4ProjectsIdDependencyListExportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdDependencyListExportsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdDependencyListExportsRequest postApiV4ProjectsIdDependencyListExportsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdDependencyListExportsRequestCreation(id, postApiV4ProjectsIdDependencyListExportsRequest);
    }
}
