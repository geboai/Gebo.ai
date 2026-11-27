package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGoModuleVersion;
import ai.gebo.gitlab.client.model.APIEntitiesPackage;
import ai.gebo.gitlab.client.model.APIEntitiesPackageFile;
import ai.gebo.gitlab.client.model.APIEntitiesPackagePipeline;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest;

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
public class PackagesApi {
    private ApiClient apiClient;

    public PackagesApi() {
        this(new ApiClient());
    }

    public PackagesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a project package
     * Deletes a specified project package. If request forwarding is enabled, deleting a package can introduce a dependency confusion risk. Deleting a package protected by a protection rule is forbidden. The protection check is behind a feature flag named &#x60;packages_protected_packages_delete&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPackagesPackageIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPackagesPackageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageId' is set
        if (packageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageId' when calling deleteApiV4ProjectsIdPackagesPackageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_id", packageId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/{package_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project package
     * Deletes a specified project package. If request forwarding is enabled, deleting a package can introduce a dependency confusion risk. Deleting a package protected by a protection rule is forbidden. The protection check is behind a feature flag named &#x60;packages_protected_packages_delete&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPackagesPackageId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPackagesPackageIdRequestCreation(id, packageId).body(localVarReturnType);
    }

    /**
     * Delete a project package
     * Deletes a specified project package. If request forwarding is enabled, deleting a package can introduce a dependency confusion risk. Deleting a package protected by a protection rule is forbidden. The protection check is behind a feature flag named &#x60;packages_protected_packages_delete&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPackagesPackageIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPackagesPackageIdRequestCreation(id, packageId).toEntity(localVarReturnType);
    }

    /**
     * Delete a project package
     * Deletes a specified project package. If request forwarding is enabled, deleting a package can introduce a dependency confusion risk. Deleting a package protected by a protection rule is forbidden. The protection check is behind a feature flag named &#x60;packages_protected_packages_delete&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPackagesPackageIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPackagesPackageIdRequestCreation(id, packageId);
    }

    /**
     * Delete a package file
     * Deletes a specified package file. Deleting a package file can corrupt the package, making it unusable or unpullable from your package manager client. Deleting a file of a package protected by a protection rule is forbidden. The protection check is behind a feature flag named &#x60;packages_protected_packages_delete&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param packageFileId ID of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nonnull Integer packageFileId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageId' is set
        if (packageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageId' when calling deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageFileId' is set
        if (packageFileId == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageFileId' when calling deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_id", packageId);
        pathParams.put("package_file_id", packageFileId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/{package_id}/package_files/{package_file_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a package file
     * Deletes a specified package file. Deleting a package file can corrupt the package, making it unusable or unpullable from your package manager client. Deleting a file of a package protected by a protection rule is forbidden. The protection check is behind a feature flag named &#x60;packages_protected_packages_delete&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param packageFileId ID of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nonnull Integer packageFileId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdRequestCreation(id, packageId, packageFileId).body(localVarReturnType);
    }

    /**
     * Delete a package file
     * Deletes a specified package file. Deleting a package file can corrupt the package, making it unusable or unpullable from your package manager client. Deleting a file of a package protected by a protection rule is forbidden. The protection check is behind a feature flag named &#x60;packages_protected_packages_delete&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param packageFileId ID of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nonnull Integer packageFileId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdRequestCreation(id, packageId, packageFileId).toEntity(localVarReturnType);
    }

    /**
     * Delete a package file
     * Deletes a specified package file. Deleting a package file can corrupt the package, making it unusable or unpullable from your package manager client. Deleting a file of a package protected by a protection rule is forbidden. The protection check is behind a feature flag named &#x60;packages_protected_packages_delete&#x60;, disabled by default.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param packageFileId ID of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nonnull Integer packageFileId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdRequestCreation(id, packageId, packageFileId);
    }

    /**
     * Download the maven package file for a group
     * This feature was introduced in GitLab 11.7
     * <p><b>200</b> - OK
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesMavenPathFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4GroupsIdDashPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling getApiV4GroupsIdDashPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("path", path);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/maven/{path}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download the maven package file for a group
     * This feature was introduced in GitLab 11.7
     * <p><b>200</b> - OK
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesMavenPathFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesMavenPathFileNameRequestCreation(id, fileName, path).body(localVarReturnType);
    }

    /**
     * Download the maven package file for a group
     * This feature was introduced in GitLab 11.7
     * <p><b>200</b> - OK
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesMavenPathFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesMavenPathFileNameRequestCreation(id, fileName, path).toEntity(localVarReturnType);
    }

    /**
     * Download the maven package file for a group
     * This feature was introduced in GitLab 11.7
     * <p><b>200</b> - OK
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param fileName Package file name
     * @param path Package path
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesMavenPathFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesMavenPathFileNameRequestCreation(id, fileName, path);
    }

    /**
     * List all packages for a group
     * Lists all packages for a specified group. When accessed without authentication, only packages of public projects are returned. By default, packages with &#x60;default&#x60;, &#x60;deprecated&#x60;, and &#x60;error&#x60; status are returned. Use the &#x60;status&#x60; parameter to view other packages. Packages of non-public projects are returned only for projects where you have at least the Reporter role. Packages with &#x60;processing&#x60; status can contain malformed data or be broken. In the response, &#x60;creator_id&#x60; is the ID of the user who created the package, and is &#x60;null&#x60; when a deploy token created the package. For a Conan package, &#x60;name&#x60; contains the recipe, and &#x60;conan_package_name&#x60; contains the package name. Other package types do not have this field. In &#x60;_links&#x60;, &#x60;web_path&#x60; is the path to the package details page in GitLab, returned only if the package has status &#x60;default&#x60; or &#x60;deprecated&#x60;, and &#x60;delete_api_path&#x60; is the API path to delete the package, returned only if you have permission to delete it.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param excludeSubgroups If &#x60;true&#x60;, excludes packages from subgroup projects.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param packageType Filter packages by type.
     * @param packageName Filter packages by name, using a fuzzy search.
     * @param packageVersion Filter packages by version. When used together with &#x60;include_versionless&#x60;, versionless packages are not returned.
     * @param includeVersionless If &#x60;true&#x60;, includes versionless packages in the response.
     * @param status Filter packages by status.
     * @return List&lt;APIEntitiesPackage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdPackagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean excludeSubgroups, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String packageType, @jakarta.annotation.Nullable String packageName, @jakarta.annotation.Nullable String packageVersion, @jakarta.annotation.Nullable Boolean includeVersionless, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdPackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "exclude_subgroups", excludeSubgroups));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "package_type", packageType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "package_name", packageName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "package_version", packageVersion));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_versionless", includeVersionless));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPackage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all packages for a group
     * Lists all packages for a specified group. When accessed without authentication, only packages of public projects are returned. By default, packages with &#x60;default&#x60;, &#x60;deprecated&#x60;, and &#x60;error&#x60; status are returned. Use the &#x60;status&#x60; parameter to view other packages. Packages of non-public projects are returned only for projects where you have at least the Reporter role. Packages with &#x60;processing&#x60; status can contain malformed data or be broken. In the response, &#x60;creator_id&#x60; is the ID of the user who created the package, and is &#x60;null&#x60; when a deploy token created the package. For a Conan package, &#x60;name&#x60; contains the recipe, and &#x60;conan_package_name&#x60; contains the package name. Other package types do not have this field. In &#x60;_links&#x60;, &#x60;web_path&#x60; is the path to the package details page in GitLab, returned only if the package has status &#x60;default&#x60; or &#x60;deprecated&#x60;, and &#x60;delete_api_path&#x60; is the API path to delete the package, returned only if you have permission to delete it.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param excludeSubgroups If &#x60;true&#x60;, excludes packages from subgroup projects.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param packageType Filter packages by type.
     * @param packageName Filter packages by name, using a fuzzy search.
     * @param packageVersion Filter packages by version. When used together with &#x60;include_versionless&#x60;, versionless packages are not returned.
     * @param includeVersionless If &#x60;true&#x60;, includes versionless packages in the response.
     * @param status Filter packages by status.
     * @return List&lt;APIEntitiesPackage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPackage> getApiV4GroupsIdPackages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean excludeSubgroups, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String packageType, @jakarta.annotation.Nullable String packageName, @jakarta.annotation.Nullable String packageVersion, @jakarta.annotation.Nullable Boolean includeVersionless, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdPackagesRequestCreation(id, excludeSubgroups, page, perPage, orderBy, sort, packageType, packageName, packageVersion, includeVersionless, status).body(localVarReturnType);
    }

    /**
     * List all packages for a group
     * Lists all packages for a specified group. When accessed without authentication, only packages of public projects are returned. By default, packages with &#x60;default&#x60;, &#x60;deprecated&#x60;, and &#x60;error&#x60; status are returned. Use the &#x60;status&#x60; parameter to view other packages. Packages of non-public projects are returned only for projects where you have at least the Reporter role. Packages with &#x60;processing&#x60; status can contain malformed data or be broken. In the response, &#x60;creator_id&#x60; is the ID of the user who created the package, and is &#x60;null&#x60; when a deploy token created the package. For a Conan package, &#x60;name&#x60; contains the recipe, and &#x60;conan_package_name&#x60; contains the package name. Other package types do not have this field. In &#x60;_links&#x60;, &#x60;web_path&#x60; is the path to the package details page in GitLab, returned only if the package has status &#x60;default&#x60; or &#x60;deprecated&#x60;, and &#x60;delete_api_path&#x60; is the API path to delete the package, returned only if you have permission to delete it.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param excludeSubgroups If &#x60;true&#x60;, excludes packages from subgroup projects.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param packageType Filter packages by type.
     * @param packageName Filter packages by name, using a fuzzy search.
     * @param packageVersion Filter packages by version. When used together with &#x60;include_versionless&#x60;, versionless packages are not returned.
     * @param includeVersionless If &#x60;true&#x60;, includes versionless packages in the response.
     * @param status Filter packages by status.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPackage&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPackage>> getApiV4GroupsIdPackagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean excludeSubgroups, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String packageType, @jakarta.annotation.Nullable String packageName, @jakarta.annotation.Nullable String packageVersion, @jakarta.annotation.Nullable Boolean includeVersionless, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdPackagesRequestCreation(id, excludeSubgroups, page, perPage, orderBy, sort, packageType, packageName, packageVersion, includeVersionless, status).toEntity(localVarReturnType);
    }

    /**
     * List all packages for a group
     * Lists all packages for a specified group. When accessed without authentication, only packages of public projects are returned. By default, packages with &#x60;default&#x60;, &#x60;deprecated&#x60;, and &#x60;error&#x60; status are returned. Use the &#x60;status&#x60; parameter to view other packages. Packages of non-public projects are returned only for projects where you have at least the Reporter role. Packages with &#x60;processing&#x60; status can contain malformed data or be broken. In the response, &#x60;creator_id&#x60; is the ID of the user who created the package, and is &#x60;null&#x60; when a deploy token created the package. For a Conan package, &#x60;name&#x60; contains the recipe, and &#x60;conan_package_name&#x60; contains the package name. Other package types do not have this field. In &#x60;_links&#x60;, &#x60;web_path&#x60; is the path to the package details page in GitLab, returned only if the package has status &#x60;default&#x60; or &#x60;deprecated&#x60;, and &#x60;delete_api_path&#x60; is the API path to delete the package, returned only if you have permission to delete it.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param excludeSubgroups If &#x60;true&#x60;, excludes packages from subgroup projects.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param packageType Filter packages by type.
     * @param packageName Filter packages by name, using a fuzzy search.
     * @param packageVersion Filter packages by version. When used together with &#x60;include_versionless&#x60;, versionless packages are not returned.
     * @param includeVersionless If &#x60;true&#x60;, includes versionless packages in the response.
     * @param status Filter packages by status.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdPackagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean excludeSubgroups, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String packageType, @jakarta.annotation.Nullable String packageName, @jakarta.annotation.Nullable String packageVersion, @jakarta.annotation.Nullable Boolean includeVersionless, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return getApiV4GroupsIdPackagesRequestCreation(id, excludeSubgroups, page, perPage, orderBy, sort, packageType, packageName, packageVersion, includeVersionless, status);
    }

    /**
     * Download the maven package file for the instance
     * This feature was introduced in GitLab 11.6
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesMavenPathFileNameRequestCreation(@jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4PackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling getApiV4PackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("file_name", fileName);
        pathParams.put("path", path);

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
        return apiClient.invokeAPI("/api/v4/packages/maven/{path}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download the maven package file for the instance
     * This feature was introduced in GitLab 11.6
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesMavenPathFileName(@jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesMavenPathFileNameRequestCreation(fileName, path).body(localVarReturnType);
    }

    /**
     * Download the maven package file for the instance
     * This feature was introduced in GitLab 11.6
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesMavenPathFileNameWithHttpInfo(@jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesMavenPathFileNameRequestCreation(fileName, path).toEntity(localVarReturnType);
    }

    /**
     * Download the maven package file for the instance
     * This feature was introduced in GitLab 11.6
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param fileName Package file name
     * @param path Package path
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesMavenPathFileNameWithResponseSpec(@jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        return getApiV4PackagesMavenPathFileNameRequestCreation(fileName, path);
    }

    /**
     * List all packages for a project
     * Lists all packages for a specified project. All package types are included in results. Unauthenticated requests return only packages of public projects. By default, packages with &#x60;default&#x60;, &#x60;deprecated&#x60;, and &#x60;error&#x60; status are returned. Use the &#x60;status&#x60; parameter to view other packages. Packages with &#x60;processing&#x60; status can contain malformed data or be broken.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param packageType Filter packages by type.
     * @param packageName Filter packages by name, using a fuzzy search.
     * @param packageVersion Filter packages by version. When used together with &#x60;include_versionless&#x60;, versionless packages are not returned.
     * @param includeVersionless If &#x60;true&#x60;, includes versionless packages in the response.
     * @param status Filter packages by status.
     * @return List&lt;APIEntitiesPackage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String packageType, @jakarta.annotation.Nullable String packageName, @jakarta.annotation.Nullable String packageVersion, @jakarta.annotation.Nullable Boolean includeVersionless, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "package_type", packageType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "package_name", packageName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "package_version", packageVersion));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_versionless", includeVersionless));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPackage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all packages for a project
     * Lists all packages for a specified project. All package types are included in results. Unauthenticated requests return only packages of public projects. By default, packages with &#x60;default&#x60;, &#x60;deprecated&#x60;, and &#x60;error&#x60; status are returned. Use the &#x60;status&#x60; parameter to view other packages. Packages with &#x60;processing&#x60; status can contain malformed data or be broken.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param packageType Filter packages by type.
     * @param packageName Filter packages by name, using a fuzzy search.
     * @param packageVersion Filter packages by version. When used together with &#x60;include_versionless&#x60;, versionless packages are not returned.
     * @param includeVersionless If &#x60;true&#x60;, includes versionless packages in the response.
     * @param status Filter packages by status.
     * @return List&lt;APIEntitiesPackage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPackage> getApiV4ProjectsIdPackages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String packageType, @jakarta.annotation.Nullable String packageName, @jakarta.annotation.Nullable String packageVersion, @jakarta.annotation.Nullable Boolean includeVersionless, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRequestCreation(id, page, perPage, orderBy, sort, packageType, packageName, packageVersion, includeVersionless, status).body(localVarReturnType);
    }

    /**
     * List all packages for a project
     * Lists all packages for a specified project. All package types are included in results. Unauthenticated requests return only packages of public projects. By default, packages with &#x60;default&#x60;, &#x60;deprecated&#x60;, and &#x60;error&#x60; status are returned. Use the &#x60;status&#x60; parameter to view other packages. Packages with &#x60;processing&#x60; status can contain malformed data or be broken.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param packageType Filter packages by type.
     * @param packageName Filter packages by name, using a fuzzy search.
     * @param packageVersion Filter packages by version. When used together with &#x60;include_versionless&#x60;, versionless packages are not returned.
     * @param includeVersionless If &#x60;true&#x60;, includes versionless packages in the response.
     * @param status Filter packages by status.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPackage&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPackage>> getApiV4ProjectsIdPackagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String packageType, @jakarta.annotation.Nullable String packageName, @jakarta.annotation.Nullable String packageVersion, @jakarta.annotation.Nullable Boolean includeVersionless, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRequestCreation(id, page, perPage, orderBy, sort, packageType, packageName, packageVersion, includeVersionless, status).toEntity(localVarReturnType);
    }

    /**
     * List all packages for a project
     * Lists all packages for a specified project. All package types are included in results. Unauthenticated requests return only packages of public projects. By default, packages with &#x60;default&#x60;, &#x60;deprecated&#x60;, and &#x60;error&#x60; status are returned. Use the &#x60;status&#x60; parameter to view other packages. Packages with &#x60;processing&#x60; status can contain malformed data or be broken.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Project Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param packageType Filter packages by type.
     * @param packageName Filter packages by name, using a fuzzy search.
     * @param packageVersion Filter packages by version. When used together with &#x60;include_versionless&#x60;, versionless packages are not returned.
     * @param includeVersionless If &#x60;true&#x60;, includes versionless packages in the response.
     * @param status Filter packages by status.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String packageType, @jakarta.annotation.Nullable String packageName, @jakarta.annotation.Nullable String packageVersion, @jakarta.annotation.Nullable Boolean includeVersionless, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesRequestCreation(id, page, perPage, orderBy, sort, packageType, packageName, packageVersion, includeVersionless, status);
    }

    /**
     * Download package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("file_name", fileName);
        pathParams.put("package_version", packageVersion);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "download_mode", downloadMode));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/generic/{package_name}/{package_version}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameRequestCreation(id, packageName, fileName, packageVersion, downloadMode).body(localVarReturnType);
    }

    /**
     * Download package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameRequestCreation(id, packageName, fileName, packageVersion, downloadMode).toEntity(localVarReturnType);
    }

    /**
     * Download package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameRequestCreation(id, packageName, fileName, packageVersion, downloadMode);
    }

    /**
     * Download package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path File directory path
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("file_name", fileName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("path", path);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "download_mode", downloadMode));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/generic/{package_name}/{package_version}/{path}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path File directory path
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameRequestCreation(id, packageName, fileName, packageVersion, path, downloadMode).body(localVarReturnType);
    }

    /**
     * Download package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path File directory path
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameRequestCreation(id, packageName, fileName, packageVersion, path, downloadMode).toEntity(localVarReturnType);
    }

    /**
     * Download package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path File directory path
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameRequestCreation(id, packageName, fileName, packageVersion, path, downloadMode);
    }

    /**
     * List
     * Get all tagged versions for a given Go module. Returns the versions as plain text, one per line. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/list. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesGoModuleNameVListRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesGoModuleNameVList", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4ProjectsIdPackagesGoModuleNameVList", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("module_name", moduleName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/go/{module_name}/@v/list", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List
     * Get all tagged versions for a given Go module. Returns the versions as plain text, one per line. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/list. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesGoModuleNameVList(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesGoModuleNameVListRequestCreation(id, moduleName).body(localVarReturnType);
    }

    /**
     * List
     * Get all tagged versions for a given Go module. Returns the versions as plain text, one per line. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/list. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesGoModuleNameVListWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesGoModuleNameVListRequestCreation(id, moduleName).toEntity(localVarReturnType);
    }

    /**
     * List
     * Get all tagged versions for a given Go module. Returns the versions as plain text, one per line. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/list. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesGoModuleNameVListWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesGoModuleNameVListRequestCreation(id, moduleName);
    }

    /**
     * Version metadata
     * Get the metadata of a given Go module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.info. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @return APIEntitiesGoModuleVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfoRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_version", moduleVersion);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGoModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/go/{module_name}/@v/{module_version}.info", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Version metadata
     * Get the metadata of a given Go module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.info. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @return APIEntitiesGoModuleVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGoModuleVersion getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGoModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfoRequestCreation(id, moduleName, moduleVersion).body(localVarReturnType);
    }

    /**
     * Version metadata
     * Get the metadata of a given Go module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.info. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @return ResponseEntity&lt;APIEntitiesGoModuleVersion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGoModuleVersion> getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfoWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGoModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfoRequestCreation(id, moduleName, moduleVersion).toEntity(localVarReturnType);
    }

    /**
     * Version metadata
     * Get the metadata of a given Go module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.info. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfoWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionInfoRequestCreation(id, moduleName, moduleVersion);
    }

    /**
     * Download module file
     * Get the module file of a given module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.mod. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionModRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionMod", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionMod", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionMod", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_version", moduleVersion);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/go/{module_name}/@v/{module_version}.mod", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download module file
     * Get the module file of a given module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.mod. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionMod(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionModRequestCreation(id, moduleName, moduleVersion).body(localVarReturnType);
    }

    /**
     * Download module file
     * Get the module file of a given module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.mod. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionModWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionModRequestCreation(id, moduleName, moduleVersion).toEntity(localVarReturnType);
    }

    /**
     * Download module file
     * Get the module file of a given module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.mod. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionModWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionModRequestCreation(id, moduleName, moduleVersion);
    }

    /**
     * Download module source
     * Get a zip of the source of the given module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.zip. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZipRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZip", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZip", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZip", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_version", moduleVersion);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/go/{module_name}/@v/{module_version}.zip", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download module source
     * Get a zip of the source of the given module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.zip. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZip(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZipRequestCreation(id, moduleName, moduleVersion).body(localVarReturnType);
    }

    /**
     * Download module source
     * Get a zip of the source of the given module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.zip. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZipWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZipRequestCreation(id, moduleName, moduleVersion).toEntity(localVarReturnType);
    }

    /**
     * Download module source
     * Get a zip of the source of the given module version. See &#x60;go help goproxy&#x60;, GET $GOPROXY/&lt;module&gt;/@v/&lt;version&gt;.zip. This endpoint is used by the &#x60;go&#x60; command and is not meant for manual consumption. It does not follow the standard API authentication methods. Undocumented authentication methods might be removed in the future. This feature was introduced in GitLab 13.1.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The project ID or full path of a project
     * @param moduleName The name of the Go module
     * @param moduleVersion The version of the Go module
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZipWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesGoModuleNameVModuleVersionZipRequestCreation(id, moduleName, moduleVersion);
    }

    /**
     * Download the maven package file for a project
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesMavenPathFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling getApiV4ProjectsIdPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("path", path);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/maven/{path}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download the maven package file for a project
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesMavenPathFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesMavenPathFileNameRequestCreation(id, fileName, path).body(localVarReturnType);
    }

    /**
     * Download the maven package file for a project
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName Package file name
     * @param path Package path
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesMavenPathFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesMavenPathFileNameRequestCreation(id, fileName, path).toEntity(localVarReturnType);
    }

    /**
     * Download the maven package file for a project
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>302</b> - Found
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param fileName Package file name
     * @param path Package path
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesMavenPathFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesMavenPathFileNameRequestCreation(id, fileName, path);
    }

    /**
     * Retrieve a project package
     * Retrieves a specified project package. Only packages with status &#x60;default&#x60; or &#x60;deprecated&#x60; are returned. In the response, &#x60;creator_id&#x60; is the ID of the user who created the package, and is &#x60;null&#x60; when a deploy token created the package. For a Conan package, &#x60;name&#x60; contains the recipe, and &#x60;conan_package_name&#x60; contains the package name. Other package types do not have this field. In &#x60;_links&#x60;, &#x60;web_path&#x60; is the path to the package details page in GitLab, and &#x60;delete_api_path&#x60; is the API path to delete the package, returned only if you have permission to delete it.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @return APIEntitiesPackage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesPackageIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesPackageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageId' is set
        if (packageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageId' when calling getApiV4ProjectsIdPackagesPackageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_id", packageId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/{package_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project package
     * Retrieves a specified project package. Only packages with status &#x60;default&#x60; or &#x60;deprecated&#x60; are returned. In the response, &#x60;creator_id&#x60; is the ID of the user who created the package, and is &#x60;null&#x60; when a deploy token created the package. For a Conan package, &#x60;name&#x60; contains the recipe, and &#x60;conan_package_name&#x60; contains the package name. Other package types do not have this field. In &#x60;_links&#x60;, &#x60;web_path&#x60; is the path to the package details page in GitLab, and &#x60;delete_api_path&#x60; is the API path to delete the package, returned only if you have permission to delete it.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @return APIEntitiesPackage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackage getApiV4ProjectsIdPackagesPackageId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPackageIdRequestCreation(id, packageId).body(localVarReturnType);
    }

    /**
     * Retrieve a project package
     * Retrieves a specified project package. Only packages with status &#x60;default&#x60; or &#x60;deprecated&#x60; are returned. In the response, &#x60;creator_id&#x60; is the ID of the user who created the package, and is &#x60;null&#x60; when a deploy token created the package. For a Conan package, &#x60;name&#x60; contains the recipe, and &#x60;conan_package_name&#x60; contains the package name. Other package types do not have this field. In &#x60;_links&#x60;, &#x60;web_path&#x60; is the path to the package details page in GitLab, and &#x60;delete_api_path&#x60; is the API path to delete the package, returned only if you have permission to delete it.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @return ResponseEntity&lt;APIEntitiesPackage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackage> getApiV4ProjectsIdPackagesPackageIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPackageIdRequestCreation(id, packageId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project package
     * Retrieves a specified project package. Only packages with status &#x60;default&#x60; or &#x60;deprecated&#x60; are returned. In the response, &#x60;creator_id&#x60; is the ID of the user who created the package, and is &#x60;null&#x60; when a deploy token created the package. For a Conan package, &#x60;name&#x60; contains the recipe, and &#x60;conan_package_name&#x60; contains the package name. Other package types do not have this field. In &#x60;_links&#x60;, &#x60;web_path&#x60; is the path to the package details page in GitLab, and &#x60;delete_api_path&#x60; is the API path to delete the package, returned only if you have permission to delete it.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesPackageIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesPackageIdRequestCreation(id, packageId);
    }

    /**
     * List all package files
     * Lists all package files for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesPackageFile&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesPackageIdPackageFilesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesPackageIdPackageFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageId' is set
        if (packageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageId' when calling getApiV4ProjectsIdPackagesPackageIdPackageFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_id", packageId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPackageFile>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/{package_id}/package_files", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all package files
     * Lists all package files for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return List&lt;APIEntitiesPackageFile&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPackageFile> getApiV4ProjectsIdPackagesPackageIdPackageFiles(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackageFile>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPackageIdPackageFilesRequestCreation(id, packageId, page, perPage, orderBy, sort).body(localVarReturnType);
    }

    /**
     * List all package files
     * Lists all package files for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPackageFile&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPackageFile>> getApiV4ProjectsIdPackagesPackageIdPackageFilesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackageFile>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPackageIdPackageFilesRequestCreation(id, packageId, page, perPage, orderBy, sort).toEntity(localVarReturnType);
    }

    /**
     * List all package files
     * Lists all package files for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesPackageIdPackageFilesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesPackageIdPackageFilesRequestCreation(id, packageId, page, perPage, orderBy, sort);
    }

    /**
     * Download a package file
     * This feature was introduced in GitLab 18.4
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param packageFileId ID of the package file.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownloadRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nonnull Integer packageFileId, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageId' is set
        if (packageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageId' when calling getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageFileId' is set
        if (packageFileId == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageFileId' when calling getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_id", packageId);
        pathParams.put("package_file_id", packageFileId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "download_mode", downloadMode));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/{package_id}/package_files/{package_file_id}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a package file
     * This feature was introduced in GitLab 18.4
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param packageFileId ID of the package file.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownload(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nonnull Integer packageFileId, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownloadRequestCreation(id, packageId, packageFileId, downloadMode).body(localVarReturnType);
    }

    /**
     * Download a package file
     * This feature was introduced in GitLab 18.4
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param packageFileId ID of the package file.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownloadWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nonnull Integer packageFileId, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownloadRequestCreation(id, packageId, packageFileId, downloadMode).toEntity(localVarReturnType);
    }

    /**
     * Download a package file
     * This feature was introduced in GitLab 18.4
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param packageFileId ID of the package file.
     * @param downloadMode Requested download transfer mode (&#x60;proxy&#x60; or &#x60;direct&#x60;). Only honored when allowed by the object storage configuration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownloadWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nonnull Integer packageFileId, @jakarta.annotation.Nullable String downloadMode) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesPackageIdPackageFilesPackageFileIdDownloadRequestCreation(id, packageId, packageFileId, downloadMode);
    }

    /**
     * List all package pipelines
     * Lists all pipelines for a specified package. The results are sorted by &#x60;id&#x60; in descending order. The results are paginated and return up to 20 records per page. This feature was introduced in GitLab 16.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items per page
     * @param cursor Cursor for obtaining the next set of records.
     * @return List&lt;APIEntitiesPackagePipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesPackageIdPipelinesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String cursor) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesPackageIdPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageId' is set
        if (packageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageId' when calling getApiV4ProjectsIdPackagesPackageIdPipelines", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_id", packageId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPackagePipeline>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/{package_id}/pipelines", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all package pipelines
     * Lists all pipelines for a specified package. The results are sorted by &#x60;id&#x60; in descending order. The results are paginated and return up to 20 records per page. This feature was introduced in GitLab 16.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items per page
     * @param cursor Cursor for obtaining the next set of records.
     * @return List&lt;APIEntitiesPackagePipeline&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPackagePipeline> getApiV4ProjectsIdPackagesPackageIdPipelines(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String cursor) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackagePipeline>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPackageIdPipelinesRequestCreation(id, packageId, page, perPage, cursor).body(localVarReturnType);
    }

    /**
     * List all package pipelines
     * Lists all pipelines for a specified package. The results are sorted by &#x60;id&#x60; in descending order. The results are paginated and return up to 20 records per page. This feature was introduced in GitLab 16.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items per page
     * @param cursor Cursor for obtaining the next set of records.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPackagePipeline&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPackagePipeline>> getApiV4ProjectsIdPackagesPackageIdPipelinesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String cursor) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackagePipeline>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPackageIdPipelinesRequestCreation(id, packageId, page, perPage, cursor).toEntity(localVarReturnType);
    }

    /**
     * List all package pipelines
     * Lists all pipelines for a specified package. The results are sorted by &#x60;id&#x60; in descending order. The results are paginated and return up to 20 records per page. This feature was introduced in GitLab 16.1.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageId ID of the package.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items per page
     * @param cursor Cursor for obtaining the next set of records.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesPackageIdPipelinesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer packageId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String cursor) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesPackageIdPipelinesRequestCreation(id, packageId, page, perPage, cursor);
    }

    /**
     * Upload package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param _file The package file to publish (generated by Multipart middleware)
     * @param status Package status
     * @param select Response format selector. If set to \\\&quot;package_file\\\&quot;, returns the created package file object in the response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("file_name", fileName);
        pathParams.put("package_version", packageVersion);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (status != null) {
            formParams.add("status", status);
        }
        formParams.add("file", new FileSystemResource(_file));
        if (select != null) {
            formParams.add("select", select);
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/generic/{package_name}/{package_version}/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param _file The package file to publish (generated by Multipart middleware)
     * @param status Package status
     * @param select Response format selector. If set to \\\&quot;package_file\\\&quot;, returns the created package file object in the response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameRequestCreation(id, packageName, fileName, packageVersion, _file, status, select).body(localVarReturnType);
    }

    /**
     * Upload package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param _file The package file to publish (generated by Multipart middleware)
     * @param status Package status
     * @param select Response format selector. If set to \\\&quot;package_file\\\&quot;, returns the created package file object in the response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameRequestCreation(id, packageName, fileName, packageVersion, _file, status, select).toEntity(localVarReturnType);
    }

    /**
     * Upload package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param _file The package file to publish (generated by Multipart middleware)
     * @param status Package status
     * @param select Response format selector. If set to \\\&quot;package_file\\\&quot;, returns the created package file object in the response
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameRequestCreation(id, packageName, fileName, packageVersion, _file, status, select);
    }

    /**
     * Workhorse authorize generic package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("file_name", fileName);
        pathParams.put("package_version", packageVersion);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/generic/{package_name}/{package_version}/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize generic package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequestCreation(id, packageName, fileName, packageVersion, putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest).body(localVarReturnType);
    }

    /**
     * Workhorse authorize generic package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequestCreation(id, packageName, fileName, packageVersion, putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize generic package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequestCreation(id, packageName, fileName, packageVersion, putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionFileNameAuthorizeRequest);
    }

    /**
     * Upload package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path File directory path
     * @param _file The package file to publish (generated by Multipart middleware)
     * @param status Package status
     * @param select Response format selector. If set to \\\&quot;package_file\\\&quot;, returns the created package file object in the response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("file_name", fileName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("path", path);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (status != null) {
            formParams.add("status", status);
        }
        formParams.add("file", new FileSystemResource(_file));
        if (select != null) {
            formParams.add("select", select);
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/generic/{package_name}/{package_version}/{path}/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path File directory path
     * @param _file The package file to publish (generated by Multipart middleware)
     * @param status Package status
     * @param select Response format selector. If set to \\\&quot;package_file\\\&quot;, returns the created package file object in the response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameRequestCreation(id, packageName, fileName, packageVersion, path, _file, status, select).body(localVarReturnType);
    }

    /**
     * Upload package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path File directory path
     * @param _file The package file to publish (generated by Multipart middleware)
     * @param status Package status
     * @param select Response format selector. If set to \\\&quot;package_file\\\&quot;, returns the created package file object in the response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameRequestCreation(id, packageName, fileName, packageVersion, path, _file, status, select).toEntity(localVarReturnType);
    }

    /**
     * Upload package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path File directory path
     * @param _file The package file to publish (generated by Multipart middleware)
     * @param status Package status
     * @param select Response format selector. If set to \\\&quot;package_file\\\&quot;, returns the created package file object in the response
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String status, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameRequestCreation(id, packageName, fileName, packageVersion, path, _file, status, select);
    }

    /**
     * Workhorse authorize generic package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path The path parameter
     * @param putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull Object path, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("file_name", fileName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("path", path);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/generic/{package_name}/{package_version}/{path}/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize generic package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path The path parameter
     * @param putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull Object path, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequestCreation(id, packageName, fileName, packageVersion, path, putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest).body(localVarReturnType);
    }

    /**
     * Workhorse authorize generic package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path The path parameter
     * @param putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull Object path, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequestCreation(id, packageName, fileName, packageVersion, path, putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize generic package file
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the project
     * @param packageName Package name
     * @param fileName Package file name
     * @param packageVersion Package version
     * @param path The path parameter
     * @param putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull Object path, @jakarta.annotation.Nullable PutApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequestCreation(id, packageName, fileName, packageVersion, path, putApiV4ProjectsIdPackagesGenericPackageNamePackageVersionPathFileNameAuthorizeRequest);
    }

    /**
     * Upload the maven package file
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable Entity
     * @param id The ID or URL-encoded path of the project
     * @param path Package path
     * @param fileName Package file name
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesMavenPathFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling putApiV4ProjectsIdPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesMavenPathFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("path", path);
        pathParams.put("file_name", fileName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/maven/{path}/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload the maven package file
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable Entity
     * @param id The ID or URL-encoded path of the project
     * @param path Package path
     * @param fileName Package file name
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesMavenPathFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesMavenPathFileNameRequestCreation(id, path, fileName, _file).body(localVarReturnType);
    }

    /**
     * Upload the maven package file
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable Entity
     * @param id The ID or URL-encoded path of the project
     * @param path Package path
     * @param fileName Package file name
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesMavenPathFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesMavenPathFileNameRequestCreation(id, path, fileName, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload the maven package file
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable Entity
     * @param id The ID or URL-encoded path of the project
     * @param path Package path
     * @param fileName Package file name
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesMavenPathFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesMavenPathFileNameRequestCreation(id, path, fileName, _file);
    }

    /**
     * Workhorse authorize the maven package file upload
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param path Package path
     * @param fileName Package file name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesMavenPathFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesMavenPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling putApiV4ProjectsIdPackagesMavenPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesMavenPathFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("path", path);
        pathParams.put("file_name", fileName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/maven/{path}/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the maven package file upload
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param path Package path
     * @param fileName Package file name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesMavenPathFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesMavenPathFileNameAuthorizeRequestCreation(id, path, fileName).body(localVarReturnType);
    }

    /**
     * Workhorse authorize the maven package file upload
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param path Package path
     * @param fileName Package file name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesMavenPathFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesMavenPathFileNameAuthorizeRequestCreation(id, path, fileName).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the maven package file upload
     * This feature was introduced in GitLab 11.3
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id The ID or URL-encoded path of the project
     * @param path Package path
     * @param fileName Package file name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesMavenPathFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String path, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesMavenPathFileNameAuthorizeRequestCreation(id, path, fileName);
    }
}
