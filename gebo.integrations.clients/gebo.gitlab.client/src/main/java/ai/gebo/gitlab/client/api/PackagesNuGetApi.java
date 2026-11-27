package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesNugetPackageMetadata;
import ai.gebo.gitlab.client.model.APIEntitiesNugetPackagesMetadata;
import ai.gebo.gitlab.client.model.APIEntitiesNugetPackagesVersions;
import ai.gebo.gitlab.client.model.APIEntitiesNugetSearchResults;
import ai.gebo.gitlab.client.model.APIEntitiesNugetServiceIndex;
import java.io.File;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;

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
public class PackagesNuGetApi {
    private ApiClient apiClient;

    public PackagesNuGetApi() {
        this(new ApiClient());
    }

    public PackagesNuGetApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * The NuGet Package Delete endpoint
     * Deletes a specified NuGet package. This feature was introduced in GitLab 16.5
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/{package_name}/{package_version}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Package Delete endpoint
     * Deletes a specified NuGet package. This feature was introduced in GitLab 16.5
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersion(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersionRequestCreation(id, packageName, packageVersion).body(localVarReturnType);
    }

    /**
     * The NuGet Package Delete endpoint
     * Deletes a specified NuGet package. This feature was introduced in GitLab 16.5
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersionRequestCreation(id, packageName, packageVersion).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Package Delete endpoint
     * Deletes a specified NuGet package. This feature was introduced in GitLab 16.5
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPackagesNugetPackageNamePackageVersionRequestCreation(id, packageName, packageVersion);
    }

    /**
     * The NuGet V3 Feed Service Index
     * Retrieves a list of available API resources. This endpoint can be accessed without authentication. The URLs in the response use the same route prefix as the request. With the group-level prefix, the URLs contain &#x60;/groups/:id/-&#x60;. This feature was introduced in GitLab 12.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @return APIEntitiesNugetServiceIndex
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNugetIndexRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNugetIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<APIEntitiesNugetServiceIndex> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/nuget/index", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V3 Feed Service Index
     * Retrieves a list of available API resources. This endpoint can be accessed without authentication. The URLs in the response use the same route prefix as the request. With the group-level prefix, the URLs contain &#x60;/groups/:id/-&#x60;. This feature was introduced in GitLab 12.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @return APIEntitiesNugetServiceIndex
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetServiceIndex getApiV4GroupsIdDashPackagesNugetIndex(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetServiceIndex> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetIndexRequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet V3 Feed Service Index
     * Retrieves a list of available API resources. This endpoint can be accessed without authentication. The URLs in the response use the same route prefix as the request. With the group-level prefix, the URLs contain &#x60;/groups/:id/-&#x60;. This feature was introduced in GitLab 12.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @return ResponseEntity&lt;APIEntitiesNugetServiceIndex&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetServiceIndex> getApiV4GroupsIdDashPackagesNugetIndexWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetServiceIndex> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetIndexRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V3 Feed Service Index
     * Retrieves a list of available API resources. This endpoint can be accessed without authentication. The URLs in the response use the same route prefix as the request. With the group-level prefix, the URLs contain &#x60;/groups/:id/-&#x60;. This feature was introduced in GitLab 12.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNugetIndexWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNugetIndexRequestCreation(id);
    }

    /**
     * The NuGet Metadata Service - Package name level
     * Retrieves metadata for a specified package. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param packageName The NuGet package name
     * @return APIEntitiesNugetPackagesMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndexRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);

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

        ParameterizedTypeReference<APIEntitiesNugetPackagesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/nuget/metadata/{package_name}/index", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name level
     * Retrieves metadata for a specified package. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param packageName The NuGet package name
     * @return APIEntitiesNugetPackagesMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetPackagesMetadata getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndex(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackagesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndexRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name level
     * Retrieves metadata for a specified package. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param packageName The NuGet package name
     * @return ResponseEntity&lt;APIEntitiesNugetPackagesMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetPackagesMetadata> getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndexWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackagesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndexRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name level
     * Retrieves metadata for a specified package. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param packageName The NuGet package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndexWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNugetMetadataPackageNameIndexRequestCreation(id, packageName);
    }

    /**
     * The NuGet Metadata Service - Package name and version level
     * Retrieves metadata for a specified package version. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return APIEntitiesNugetPackageMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersionRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);

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

        ParameterizedTypeReference<APIEntitiesNugetPackageMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/nuget/metadata/{package_name}/{package_version}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name and version level
     * Retrieves metadata for a specified package version. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return APIEntitiesNugetPackageMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetPackageMetadata getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersion(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackageMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersionRequestCreation(id, packageName, packageVersion).body(localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name and version level
     * Retrieves metadata for a specified package version. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return ResponseEntity&lt;APIEntitiesNugetPackageMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetPackageMetadata> getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersionWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackageMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersionRequestCreation(id, packageName, packageVersion).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name and version level
     * Retrieves metadata for a specified package version. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersionWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNugetMetadataPackageNamePackageVersionRequestCreation(id, packageName, packageVersion);
    }

    /**
     * The NuGet Search Service
     * Searches for NuGet packages in the repository based on a specified query. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param q Search term.
     * @param skip Number of results to skip.
     * @param take Number of results to return.
     * @param prerelease If &#x60;true&#x60;, includes prerelease versions in the results.
     * @return APIEntitiesNugetSearchResults
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNugetQueryRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Integer skip, @jakarta.annotation.Nullable Integer take, @jakarta.annotation.Nullable Boolean prerelease) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNugetQuery", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "skip", skip));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "take", take));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "prerelease", prerelease));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesNugetSearchResults> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/nuget/query", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Search Service
     * Searches for NuGet packages in the repository based on a specified query. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param q Search term.
     * @param skip Number of results to skip.
     * @param take Number of results to return.
     * @param prerelease If &#x60;true&#x60;, includes prerelease versions in the results.
     * @return APIEntitiesNugetSearchResults
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetSearchResults getApiV4GroupsIdDashPackagesNugetQuery(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Integer skip, @jakarta.annotation.Nullable Integer take, @jakarta.annotation.Nullable Boolean prerelease) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetSearchResults> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetQueryRequestCreation(id, q, skip, take, prerelease).body(localVarReturnType);
    }

    /**
     * The NuGet Search Service
     * Searches for NuGet packages in the repository based on a specified query. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param q Search term.
     * @param skip Number of results to skip.
     * @param take Number of results to return.
     * @param prerelease If &#x60;true&#x60;, includes prerelease versions in the results.
     * @return ResponseEntity&lt;APIEntitiesNugetSearchResults&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetSearchResults> getApiV4GroupsIdDashPackagesNugetQueryWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Integer skip, @jakarta.annotation.Nullable Integer take, @jakarta.annotation.Nullable Boolean prerelease) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetSearchResults> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetQueryRequestCreation(id, q, skip, take, prerelease).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Search Service
     * Searches for NuGet packages in the repository based on a specified query. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @param q Search term.
     * @param skip Number of results to skip.
     * @param take Number of results to return.
     * @param prerelease If &#x60;true&#x60;, includes prerelease versions in the results.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNugetQueryWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Integer skip, @jakarta.annotation.Nullable Integer take, @jakarta.annotation.Nullable Boolean prerelease) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNugetQueryRequestCreation(id, q, skip, take, prerelease);
    }

    /**
     * The NuGet Symbol File Download Endpoint
     * Downloads a specified debugging symbol file (&#x60;.pdb&#x60;). Requires the &#x60;Symbolchecksum&#x60; header with the SHA256 checksum of the file, in the format &#x60;SHA256:&lt;checksum&gt;&#x60;. This feature was introduced in GitLab 16.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the group.
     * @param fileName The symbol file name
     * @param signature The symbol file signature
     * @param sameFileName The symbol file name. Must match the &#39;file_name&#39; parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileNameRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String signature, @jakarta.annotation.Nonnull String sameFileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'signature' is set
        if (signature == null) {
            throw new RestClientResponseException("Missing the required parameter 'signature' when calling getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sameFileName' is set
        if (sameFileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'sameFileName' when calling getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("signature", signature);
        pathParams.put("same_file_name", sameFileName);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/nuget/symbolfiles/{file_name}/{signature}/{same_file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Symbol File Download Endpoint
     * Downloads a specified debugging symbol file (&#x60;.pdb&#x60;). Requires the &#x60;Symbolchecksum&#x60; header with the SHA256 checksum of the file, in the format &#x60;SHA256:&lt;checksum&gt;&#x60;. This feature was introduced in GitLab 16.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the group.
     * @param fileName The symbol file name
     * @param signature The symbol file signature
     * @param sameFileName The symbol file name. Must match the &#39;file_name&#39; parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileName(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String signature, @jakarta.annotation.Nonnull String sameFileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileNameRequestCreation(id, fileName, signature, sameFileName).body(localVarReturnType);
    }

    /**
     * The NuGet Symbol File Download Endpoint
     * Downloads a specified debugging symbol file (&#x60;.pdb&#x60;). Requires the &#x60;Symbolchecksum&#x60; header with the SHA256 checksum of the file, in the format &#x60;SHA256:&lt;checksum&gt;&#x60;. This feature was introduced in GitLab 16.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the group.
     * @param fileName The symbol file name
     * @param signature The symbol file signature
     * @param sameFileName The symbol file name. Must match the &#39;file_name&#39; parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileNameWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String signature, @jakarta.annotation.Nonnull String sameFileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileNameRequestCreation(id, fileName, signature, sameFileName).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Symbol File Download Endpoint
     * Downloads a specified debugging symbol file (&#x60;.pdb&#x60;). Requires the &#x60;Symbolchecksum&#x60; header with the SHA256 checksum of the file, in the format &#x60;SHA256:&lt;checksum&gt;&#x60;. This feature was introduced in GitLab 16.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the group.
     * @param fileName The symbol file name
     * @param signature The symbol file signature
     * @param sameFileName The symbol file name. Must match the &#39;file_name&#39; parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileNameWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String signature, @jakarta.annotation.Nonnull String sameFileName) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNugetSymbolfilesFileNameSignatureSameFileNameRequestCreation(id, fileName, signature, sameFileName);
    }

    /**
     * The NuGet V2 Feed Service Index
     * Retrieves an XML document that represents the service index of the NuGet v2 source feed. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNugetV2RequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNugetV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/nuget/v2", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Service Index
     * Retrieves an XML document that represents the service index of the NuGet v2 source feed. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesNugetV2(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesNugetV2RequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Service Index
     * Retrieves an XML document that represents the service index of the NuGet v2 source feed. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesNugetV2WithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetV2RequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Service Index
     * Retrieves an XML document that represents the service index of the NuGet v2 source feed. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNugetV2WithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNugetV2RequestCreation(id);
    }

    /**
     * The NuGet V2 Feed Package $metadata endpoint
     * Returns metadata for the available endpoints in a NuGet v2 feed, as an OData EDMX XML document. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.3
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNugetV2MetadataRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNugetV2Metadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/nuget/v2/$metadata", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package $metadata endpoint
     * Returns metadata for the available endpoints in a NuGet v2 feed, as an OData EDMX XML document. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.3
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesNugetV2Metadata(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesNugetV2MetadataRequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package $metadata endpoint
     * Returns metadata for the available endpoints in a NuGet v2 feed, as an OData EDMX XML document. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.3
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesNugetV2MetadataWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNugetV2MetadataRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package $metadata endpoint
     * Returns metadata for the available endpoints in a NuGet v2 feed, as an OData EDMX XML document. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.3
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNugetV2MetadataWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNugetV2MetadataRequestCreation(id);
    }

    /**
     * The NuGet Content Service - index request
     * Retrieves the index for a specified package, which includes a list of available versions. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @return APIEntitiesNugetPackagesVersions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndexRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);

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

        ParameterizedTypeReference<APIEntitiesNugetPackagesVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/download/{package_name}/index", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Content Service - index request
     * Retrieves the index for a specified package, which includes a list of available versions. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @return APIEntitiesNugetPackagesVersions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetPackagesVersions getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndex(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackagesVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndexRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * The NuGet Content Service - index request
     * Retrieves the index for a specified package, which includes a list of available versions. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @return ResponseEntity&lt;APIEntitiesNugetPackagesVersions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetPackagesVersions> getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndexWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackagesVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndexRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Content Service - index request
     * Retrieves the index for a specified package, which includes a list of available versions. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndexWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetDownloadPackageNameIndexRequestCreation(id, packageName);
    }

    /**
     * The NuGet Content Service - content request
     * Downloads a specified NuGet package file for a project. The package metadata service provides this URL. This endpoint exists only at the project-level route prefix, so a request with the group-level prefix returns &#x60;404&#x60;. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @param packageFilename The NuGet package filename
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageFilename) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageFilename' is set
        if (packageFilename == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageFilename' when calling getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_filename", packageFilename);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/download/{package_name}/{package_version}/{package_filename}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Content Service - content request
     * Downloads a specified NuGet package file for a project. The package metadata service provides this URL. This endpoint exists only at the project-level route prefix, so a request with the group-level prefix returns &#x60;404&#x60;. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @param packageFilename The NuGet package filename
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageFilename) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilenameRequestCreation(id, packageName, packageVersion, packageFilename).body(localVarReturnType);
    }

    /**
     * The NuGet Content Service - content request
     * Downloads a specified NuGet package file for a project. The package metadata service provides this URL. This endpoint exists only at the project-level route prefix, so a request with the group-level prefix returns &#x60;404&#x60;. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @param packageFilename The NuGet package filename
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageFilename) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilenameRequestCreation(id, packageName, packageVersion, packageFilename).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Content Service - content request
     * Downloads a specified NuGet package file for a project. The package metadata service provides this URL. This endpoint exists only at the project-level route prefix, so a request with the group-level prefix returns &#x60;404&#x60;. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @param packageFilename The NuGet package filename
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageFilename) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetDownloadPackageNamePackageVersionPackageFilenameRequestCreation(id, packageName, packageVersion, packageFilename);
    }

    /**
     * The NuGet V3 Feed Service Index
     * Retrieves a list of available API resources. This endpoint can be accessed without authentication. The URLs in the response use the same route prefix as the request. With the group-level prefix, the URLs contain &#x60;/groups/:id/-&#x60;. This feature was introduced in GitLab 12.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return APIEntitiesNugetServiceIndex
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetIndexRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<APIEntitiesNugetServiceIndex> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/index", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V3 Feed Service Index
     * Retrieves a list of available API resources. This endpoint can be accessed without authentication. The URLs in the response use the same route prefix as the request. With the group-level prefix, the URLs contain &#x60;/groups/:id/-&#x60;. This feature was introduced in GitLab 12.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return APIEntitiesNugetServiceIndex
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetServiceIndex getApiV4ProjectsIdPackagesNugetIndex(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetServiceIndex> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetIndexRequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet V3 Feed Service Index
     * Retrieves a list of available API resources. This endpoint can be accessed without authentication. The URLs in the response use the same route prefix as the request. With the group-level prefix, the URLs contain &#x60;/groups/:id/-&#x60;. This feature was introduced in GitLab 12.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return ResponseEntity&lt;APIEntitiesNugetServiceIndex&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetServiceIndex> getApiV4ProjectsIdPackagesNugetIndexWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetServiceIndex> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetIndexRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V3 Feed Service Index
     * Retrieves a list of available API resources. This endpoint can be accessed without authentication. The URLs in the response use the same route prefix as the request. With the group-level prefix, the URLs contain &#x60;/groups/:id/-&#x60;. This feature was introduced in GitLab 12.6
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetIndexWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetIndexRequestCreation(id);
    }

    /**
     * The NuGet Metadata Service - Package name level
     * Retrieves metadata for a specified package. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @return APIEntitiesNugetPackagesMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndexRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndex", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);

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

        ParameterizedTypeReference<APIEntitiesNugetPackagesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/metadata/{package_name}/index", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name level
     * Retrieves metadata for a specified package. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @return APIEntitiesNugetPackagesMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetPackagesMetadata getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndex(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackagesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndexRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name level
     * Retrieves metadata for a specified package. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @return ResponseEntity&lt;APIEntitiesNugetPackagesMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetPackagesMetadata> getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndexWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackagesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndexRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name level
     * Retrieves metadata for a specified package. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndexWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetMetadataPackageNameIndexRequestCreation(id, packageName);
    }

    /**
     * The NuGet Metadata Service - Package name and version level
     * Retrieves metadata for a specified package version. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return APIEntitiesNugetPackageMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);

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

        ParameterizedTypeReference<APIEntitiesNugetPackageMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/metadata/{package_name}/{package_version}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name and version level
     * Retrieves metadata for a specified package version. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return APIEntitiesNugetPackageMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetPackageMetadata getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersion(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackageMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersionRequestCreation(id, packageName, packageVersion).body(localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name and version level
     * Retrieves metadata for a specified package version. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return ResponseEntity&lt;APIEntitiesNugetPackageMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetPackageMetadata> getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetPackageMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersionRequestCreation(id, packageName, packageVersion).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Metadata Service - Package name and version level
     * Retrieves metadata for a specified package version. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetMetadataPackageNamePackageVersionRequestCreation(id, packageName, packageVersion);
    }

    /**
     * The NuGet Search Service
     * Searches for NuGet packages in the repository based on a specified query. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param q Search term.
     * @param skip Number of results to skip.
     * @param take Number of results to return.
     * @param prerelease If &#x60;true&#x60;, includes prerelease versions in the results.
     * @return APIEntitiesNugetSearchResults
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetQueryRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Integer skip, @jakarta.annotation.Nullable Integer take, @jakarta.annotation.Nullable Boolean prerelease) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetQuery", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "skip", skip));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "take", take));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "prerelease", prerelease));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesNugetSearchResults> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/query", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Search Service
     * Searches for NuGet packages in the repository based on a specified query. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param q Search term.
     * @param skip Number of results to skip.
     * @param take Number of results to return.
     * @param prerelease If &#x60;true&#x60;, includes prerelease versions in the results.
     * @return APIEntitiesNugetSearchResults
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNugetSearchResults getApiV4ProjectsIdPackagesNugetQuery(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Integer skip, @jakarta.annotation.Nullable Integer take, @jakarta.annotation.Nullable Boolean prerelease) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetSearchResults> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetQueryRequestCreation(id, q, skip, take, prerelease).body(localVarReturnType);
    }

    /**
     * The NuGet Search Service
     * Searches for NuGet packages in the repository based on a specified query. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param q Search term.
     * @param skip Number of results to skip.
     * @param take Number of results to return.
     * @param prerelease If &#x60;true&#x60;, includes prerelease versions in the results.
     * @return ResponseEntity&lt;APIEntitiesNugetSearchResults&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNugetSearchResults> getApiV4ProjectsIdPackagesNugetQueryWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Integer skip, @jakarta.annotation.Nullable Integer take, @jakarta.annotation.Nullable Boolean prerelease) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNugetSearchResults> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetQueryRequestCreation(id, q, skip, take, prerelease).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Search Service
     * Searches for NuGet packages in the repository based on a specified query. This feature was introduced in GitLab 12.8
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @param q Search term.
     * @param skip Number of results to skip.
     * @param take Number of results to return.
     * @param prerelease If &#x60;true&#x60;, includes prerelease versions in the results.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetQueryWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Integer skip, @jakarta.annotation.Nullable Integer take, @jakarta.annotation.Nullable Boolean prerelease) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetQueryRequestCreation(id, q, skip, take, prerelease);
    }

    /**
     * The NuGet Symbol File Download Endpoint
     * Downloads a specified debugging symbol file (&#x60;.pdb&#x60;). Requires the &#x60;Symbolchecksum&#x60; header with the SHA256 checksum of the file, in the format &#x60;SHA256:&lt;checksum&gt;&#x60;. This feature was introduced in GitLab 16.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param fileName The symbol file name
     * @param signature The symbol file signature
     * @param sameFileName The symbol file name. Must match the &#39;file_name&#39; parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String signature, @jakarta.annotation.Nonnull String sameFileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'signature' is set
        if (signature == null) {
            throw new RestClientResponseException("Missing the required parameter 'signature' when calling getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sameFileName' is set
        if (sameFileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'sameFileName' when calling getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);
        pathParams.put("signature", signature);
        pathParams.put("same_file_name", sameFileName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/symbolfiles/{file_name}/{signature}/{same_file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Symbol File Download Endpoint
     * Downloads a specified debugging symbol file (&#x60;.pdb&#x60;). Requires the &#x60;Symbolchecksum&#x60; header with the SHA256 checksum of the file, in the format &#x60;SHA256:&lt;checksum&gt;&#x60;. This feature was introduced in GitLab 16.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param fileName The symbol file name
     * @param signature The symbol file signature
     * @param sameFileName The symbol file name. Must match the &#39;file_name&#39; parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String signature, @jakarta.annotation.Nonnull String sameFileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileNameRequestCreation(id, fileName, signature, sameFileName).body(localVarReturnType);
    }

    /**
     * The NuGet Symbol File Download Endpoint
     * Downloads a specified debugging symbol file (&#x60;.pdb&#x60;). Requires the &#x60;Symbolchecksum&#x60; header with the SHA256 checksum of the file, in the format &#x60;SHA256:&lt;checksum&gt;&#x60;. This feature was introduced in GitLab 16.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param fileName The symbol file name
     * @param signature The symbol file signature
     * @param sameFileName The symbol file name. Must match the &#39;file_name&#39; parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String signature, @jakarta.annotation.Nonnull String sameFileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileNameRequestCreation(id, fileName, signature, sameFileName).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Symbol File Download Endpoint
     * Downloads a specified debugging symbol file (&#x60;.pdb&#x60;). Requires the &#x60;Symbolchecksum&#x60; header with the SHA256 checksum of the file, in the format &#x60;SHA256:&lt;checksum&gt;&#x60;. This feature was introduced in GitLab 16.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param fileName The symbol file name
     * @param signature The symbol file signature
     * @param sameFileName The symbol file name. Must match the &#39;file_name&#39; parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull String signature, @jakarta.annotation.Nonnull String sameFileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetSymbolfilesFileNameSignatureSameFileNameRequestCreation(id, fileName, signature, sameFileName);
    }

    /**
     * The NuGet V2 Feed Service Index
     * Retrieves an XML document that represents the service index of the NuGet v2 source feed. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetV2RequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/v2", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Service Index
     * Retrieves an XML document that represents the service index of the NuGet v2 source feed. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesNugetV2(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesNugetV2RequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Service Index
     * Retrieves an XML document that represents the service index of the NuGet v2 source feed. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesNugetV2WithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetV2RequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Service Index
     * Retrieves an XML document that represents the service index of the NuGet v2 source feed. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetV2WithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetV2RequestCreation(id);
    }

    /**
     * The NuGet V2 Feed Package $metadata endpoint
     * Returns metadata for the available endpoints in a NuGet v2 feed, as an OData EDMX XML document. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.3
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNugetV2MetadataRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNugetV2Metadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/v2/$metadata", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package $metadata endpoint
     * Returns metadata for the available endpoints in a NuGet v2 feed, as an OData EDMX XML document. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.3
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesNugetV2Metadata(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesNugetV2MetadataRequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package $metadata endpoint
     * Returns metadata for the available endpoints in a NuGet v2 feed, as an OData EDMX XML document. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.3
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesNugetV2MetadataWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNugetV2MetadataRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package $metadata endpoint
     * Returns metadata for the available endpoints in a NuGet v2 feed, as an OData EDMX XML document. This endpoint can be accessed without authentication. This feature was introduced in GitLab 16.3
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNugetV2MetadataWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNugetV2MetadataRequestCreation(id);
    }

    /**
     * The NuGet V2 Feed Find Packages by ID endpoint
     * Returns an OData XML document that contains information about the package with the given name. The endpoint does not receive an authentication token, so it cannot return the latest version of the package. The version must be provided when a package is installed or upgraded with the NuGet v2 feed. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param id Name of the NuGet package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsProjectIdPackagesNugetV2FindpackagesbyidRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4ProjectsProjectIdPackagesNugetV2Findpackagesbyid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsProjectIdPackagesNugetV2Findpackagesbyid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "id", id));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{project_id}/packages/nuget/v2/FindPackagesById()", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Find Packages by ID endpoint
     * Returns an OData XML document that contains information about the package with the given name. The endpoint does not receive an authentication token, so it cannot return the latest version of the package. The version must be provided when a package is installed or upgraded with the NuGet v2 feed. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param id Name of the NuGet package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsProjectIdPackagesNugetV2Findpackagesbyid(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsProjectIdPackagesNugetV2FindpackagesbyidRequestCreation(projectId, id).body(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Find Packages by ID endpoint
     * Returns an OData XML document that contains information about the package with the given name. The endpoint does not receive an authentication token, so it cannot return the latest version of the package. The version must be provided when a package is installed or upgraded with the NuGet v2 feed. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param id Name of the NuGet package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsProjectIdPackagesNugetV2FindpackagesbyidWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsProjectIdPackagesNugetV2FindpackagesbyidRequestCreation(projectId, id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Find Packages by ID endpoint
     * Returns an OData XML document that contains information about the package with the given name. The endpoint does not receive an authentication token, so it cannot return the latest version of the package. The version must be provided when a package is installed or upgraded with the NuGet v2 feed. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param id Name of the NuGet package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsProjectIdPackagesNugetV2FindpackagesbyidWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4ProjectsProjectIdPackagesNugetV2FindpackagesbyidRequestCreation(projectId, id);
    }

    /**
     * The NuGet V2 Feed Enumerate Packages endpoint
     * Returns an OData XML document that contains information about the package with the given name. The endpoint does not receive an authentication token, so it cannot return the latest version of the package. The version must be provided when a package is installed or upgraded with the NuGet v2 feed. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param $filter OData filter expression with the NuGet package name, in the form &#x60;tolower(Id) eq &#39;&lt;package_name&gt;&#39;&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsProjectIdPackagesNugetV2PackagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String $filter) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4ProjectsProjectIdPackagesNugetV2Packages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '$filter' is set
        if ($filter == null) {
            throw new RestClientResponseException("Missing the required parameter '$filter' when calling getApiV4ProjectsProjectIdPackagesNugetV2Packages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$filter", $filter));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{project_id}/packages/nuget/v2/Packages()", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Enumerate Packages endpoint
     * Returns an OData XML document that contains information about the package with the given name. The endpoint does not receive an authentication token, so it cannot return the latest version of the package. The version must be provided when a package is installed or upgraded with the NuGet v2 feed. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param $filter OData filter expression with the NuGet package name, in the form &#x60;tolower(Id) eq &#39;&lt;package_name&gt;&#39;&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsProjectIdPackagesNugetV2Packages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String $filter) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsProjectIdPackagesNugetV2PackagesRequestCreation(projectId, $filter).body(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Enumerate Packages endpoint
     * Returns an OData XML document that contains information about the package with the given name. The endpoint does not receive an authentication token, so it cannot return the latest version of the package. The version must be provided when a package is installed or upgraded with the NuGet v2 feed. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param $filter OData filter expression with the NuGet package name, in the form &#x60;tolower(Id) eq &#39;&lt;package_name&gt;&#39;&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsProjectIdPackagesNugetV2PackagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String $filter) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsProjectIdPackagesNugetV2PackagesRequestCreation(projectId, $filter).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Enumerate Packages endpoint
     * Returns an OData XML document that contains information about the package with the given name. The endpoint does not receive an authentication token, so it cannot return the latest version of the package. The version must be provided when a package is installed or upgraded with the NuGet v2 feed. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param $filter OData filter expression with the NuGet package name, in the form &#x60;tolower(Id) eq &#39;&lt;package_name&gt;&#39;&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsProjectIdPackagesNugetV2PackagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String $filter) throws RestClientResponseException {
        return getApiV4ProjectsProjectIdPackagesNugetV2PackagesRequestCreation(projectId, $filter);
    }

    /**
     * The NuGet V2 Feed Single Package Metadata endpoint
     * Returns an OData XML document that contains information about the package with the given name and version. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("project_id", projectId);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);

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
        return apiClient.invokeAPI("/api/v4/projects/{project_id}/packages/nuget/v2/Packages(Id='{package_name}',Version='{package_version}')", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Single Package Metadata endpoint
     * Returns an OData XML document that contains information about the package with the given name and version. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersion(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersionRequestCreation(projectId, packageName, packageVersion).body(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Single Package Metadata endpoint
     * Returns an OData XML document that contains information about the package with the given name and version. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersionRequestCreation(projectId, packageName, packageVersion).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Single Package Metadata endpoint
     * Returns an OData XML document that contains information about the package with the given name and version. This feature was introduced in GitLab 16.4
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param projectId ID of the project.
     * @param packageName The NuGet package name
     * @param packageVersion The NuGet package version
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter projectId, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion) throws RestClientResponseException {
        return getApiV4ProjectsProjectIdPackagesNugetV2PackagesidPackageNameVersionPackageVersionRequestCreation(projectId, packageName, packageVersion);
    }

    /**
     * Upload a NuGet v3 package file for a project
     * Uploads a NuGet v3 package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesNugetRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesNuget", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_package' is set
        if (_package == null) {
            throw new RestClientResponseException("Missing the required parameter '_package' when calling putApiV4ProjectsIdPackagesNuget", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("package", new FileSystemResource(_package));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a NuGet v3 package file for a project
     * Uploads a NuGet v3 package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesNuget(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesNugetRequestCreation(id, _package).body(localVarReturnType);
    }

    /**
     * Upload a NuGet v3 package file for a project
     * Uploads a NuGet v3 package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesNugetWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesNugetRequestCreation(id, _package).toEntity(localVarReturnType);
    }

    /**
     * Upload a NuGet v3 package file for a project
     * Uploads a NuGet v3 package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesNugetWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesNugetRequestCreation(id, _package);
    }

    /**
     * The NuGet Package Authorize endpoint
     * This feature was introduced in GitLab 14.1
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesNugetAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesNugetAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Package Authorize endpoint
     * This feature was introduced in GitLab 14.1
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesNugetAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesNugetAuthorizeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet Package Authorize endpoint
     * This feature was introduced in GitLab 14.1
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesNugetAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesNugetAuthorizeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Package Authorize endpoint
     * This feature was introduced in GitLab 14.1
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesNugetAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesNugetAuthorizeRequestCreation(id);
    }

    /**
     * Upload a NuGet symbol package file
     * Uploads a specified NuGet symbol package file (&#x60;.snupkg&#x60;) for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesNugetSymbolpackageRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesNugetSymbolpackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_package' is set
        if (_package == null) {
            throw new RestClientResponseException("Missing the required parameter '_package' when calling putApiV4ProjectsIdPackagesNugetSymbolpackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("package", new FileSystemResource(_package));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/symbolpackage", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a NuGet symbol package file
     * Uploads a specified NuGet symbol package file (&#x60;.snupkg&#x60;) for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesNugetSymbolpackage(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesNugetSymbolpackageRequestCreation(id, _package).body(localVarReturnType);
    }

    /**
     * Upload a NuGet symbol package file
     * Uploads a specified NuGet symbol package file (&#x60;.snupkg&#x60;) for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesNugetSymbolpackageWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesNugetSymbolpackageRequestCreation(id, _package).toEntity(localVarReturnType);
    }

    /**
     * Upload a NuGet symbol package file
     * Uploads a specified NuGet symbol package file (&#x60;.snupkg&#x60;) for a project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesNugetSymbolpackageWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesNugetSymbolpackageRequestCreation(id, _package);
    }

    /**
     * The NuGet Symbol Package Authorize endpoint
     * This feature was introduced in GitLab 14.1
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesNugetSymbolpackageAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesNugetSymbolpackageAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/symbolpackage/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet Symbol Package Authorize endpoint
     * This feature was introduced in GitLab 14.1
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesNugetSymbolpackageAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesNugetSymbolpackageAuthorizeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet Symbol Package Authorize endpoint
     * This feature was introduced in GitLab 14.1
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesNugetSymbolpackageAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesNugetSymbolpackageAuthorizeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet Symbol Package Authorize endpoint
     * This feature was introduced in GitLab 14.1
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesNugetSymbolpackageAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesNugetSymbolpackageAuthorizeRequestCreation(id);
    }

    /**
     * Upload a NuGet v2 package file for a project
     * Uploads a NuGet v2 package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesNugetV2RequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesNugetV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_package' is set
        if (_package == null) {
            throw new RestClientResponseException("Missing the required parameter '_package' when calling putApiV4ProjectsIdPackagesNugetV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("package", new FileSystemResource(_package));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/v2", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a NuGet v2 package file for a project
     * Uploads a NuGet v2 package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesNugetV2(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesNugetV2RequestCreation(id, _package).body(localVarReturnType);
    }

    /**
     * Upload a NuGet v2 package file for a project
     * Uploads a NuGet v2 package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesNugetV2WithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesNugetV2RequestCreation(id, _package).toEntity(localVarReturnType);
    }

    /**
     * Upload a NuGet v2 package file for a project
     * Uploads a NuGet v2 package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID of the project.
     * @param _package The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesNugetV2WithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _package) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesNugetV2RequestCreation(id, _package);
    }

    /**
     * The NuGet V2 Feed Package Authorize endpoint
     * This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesNugetV2AuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesNugetV2Authorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/nuget/v2/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package Authorize endpoint
     * This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesNugetV2Authorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesNugetV2AuthorizeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package Authorize endpoint
     * This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesNugetV2AuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesNugetV2AuthorizeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * The NuGet V2 Feed Package Authorize endpoint
     * This feature was introduced in GitLab 16.2
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesNugetV2AuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesNugetV2AuthorizeRequestCreation(id);
    }
}
