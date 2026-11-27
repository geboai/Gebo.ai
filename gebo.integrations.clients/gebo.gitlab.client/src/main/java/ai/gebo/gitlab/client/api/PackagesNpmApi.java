package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesNpmPackage;
import ai.gebo.gitlab.client.model.APIEntitiesNpmPackageTag;
import java.io.File;
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
public class PackagesNpmApi {
    private ApiClient apiClient;

    public PackagesNpmApi() {
        this(new ApiClient());
    }

    public PackagesNpmApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tag' is set
        if (tag == null) {
            throw new RestClientResponseException("Missing the required parameter 'tag' when calling deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("tag", tag);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/npm/-/package/{package_name}/dist-tags/{tag}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTag(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag).body(localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag).toEntity(localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        return deleteApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tag' is set
        if (tag == null) {
            throw new RestClientResponseException("Missing the required parameter 'tag' when calling deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("tag", tag);

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
        return apiClient.invokeAPI("/api/v4/packages/npm/-/package/{package_name}/dist-tags/{tag}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTag(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(packageName, tag).body(localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTagWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(packageName, tag).toEntity(localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @param tag Package dist-tag
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTagWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        return deleteApiV4PackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(packageName, tag);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tag' is set
        if (tag == null) {
            throw new RestClientResponseException("Missing the required parameter 'tag' when calling deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("tag", tag);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/npm/-/package/{package_name}/dist-tags/{tag}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTag(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag).body(localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag).toEntity(localVarReturnType);
    }

    /**
     * Deletes the given tag
     * Deletes a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @return APIEntitiesNpmPackageTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/npm/-/package/{package_name}/dist-tags", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @return APIEntitiesNpmPackageTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNpmPackageTag getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @return ResponseEntity&lt;APIEntitiesNpmPackageTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNpmPackageTag> getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsRequestCreation(id, packageName);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @return APIEntitiesNpmPackage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesNpmPackageNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesNpmPackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4GroupsIdDashPackagesNpmPackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/npm/{package_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @return APIEntitiesNpmPackage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNpmPackage getApiV4GroupsIdDashPackagesNpmPackageName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNpmPackageNameRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @return ResponseEntity&lt;APIEntitiesNpmPackage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNpmPackage> getApiV4GroupsIdDashPackagesNpmPackageNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesNpmPackageNameRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesNpmPackageNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesNpmPackageNameRequestCreation(id, packageName);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @return APIEntitiesNpmPackageTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesNpmDashPackagePackageNameDistTagsRequestCreation(@jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesNpmDashPackagePackageNameDistTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/npm/-/package/{package_name}/dist-tags", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @return APIEntitiesNpmPackageTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNpmPackageTag getApiV4PackagesNpmDashPackagePackageNameDistTags(@jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesNpmDashPackagePackageNameDistTagsRequestCreation(packageName).body(localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @return ResponseEntity&lt;APIEntitiesNpmPackageTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNpmPackageTag> getApiV4PackagesNpmDashPackagePackageNameDistTagsWithHttpInfo(@jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesNpmDashPackagePackageNameDistTagsRequestCreation(packageName).toEntity(localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesNpmDashPackagePackageNameDistTagsWithResponseSpec(@jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4PackagesNpmDashPackagePackageNameDistTagsRequestCreation(packageName);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @return APIEntitiesNpmPackage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesNpmPackageNameRequestCreation(@jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesNpmPackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/npm/{package_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @return APIEntitiesNpmPackage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNpmPackage getApiV4PackagesNpmPackageName(@jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesNpmPackageNameRequestCreation(packageName).body(localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @return ResponseEntity&lt;APIEntitiesNpmPackage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNpmPackage> getApiV4PackagesNpmPackageNameWithHttpInfo(@jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesNpmPackageNameRequestCreation(packageName).toEntity(localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesNpmPackageNameWithResponseSpec(@jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4PackagesNpmPackageNameRequestCreation(packageName);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @return APIEntitiesNpmPackageTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/npm/-/package/{package_name}/dist-tags", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @return APIEntitiesNpmPackageTag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNpmPackageTag getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @return ResponseEntity&lt;APIEntitiesNpmPackageTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNpmPackageTag> getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackageTag> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * Get all tags for a given NPM package
     * Lists all dist-tags for a specified package. This feature was introduced in GitLab 12.7
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsRequestCreation(id, packageName);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @return APIEntitiesNpmPackage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNpmPackageNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNpmPackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesNpmPackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/npm/{package_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @return APIEntitiesNpmPackage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesNpmPackage getApiV4ProjectsIdPackagesNpmPackageName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNpmPackageNameRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @return ResponseEntity&lt;APIEntitiesNpmPackage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesNpmPackage> getApiV4ProjectsIdPackagesNpmPackageNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesNpmPackage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNpmPackageNameRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * NPM registry metadata endpoint
     * Retrieves the metadata for a specified package. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - Ok
     * <p><b>302</b> - Found (redirect)
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNpmPackageNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNpmPackageNameRequestCreation(id, packageName);
    }

    /**
     * Download the NPM tarball
     * Downloads a specified npm package file for a project. The metadata endpoint provides this URL. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param fileName Package file name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesNpmPackageNameDashFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesNpmPackageNameDashFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesNpmPackageNameDashFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesNpmPackageNameDashFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/npm/{package_name}/-/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download the NPM tarball
     * Downloads a specified npm package file for a project. The metadata endpoint provides this URL. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param fileName Package file name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesNpmPackageNameDashFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesNpmPackageNameDashFileNameRequestCreation(id, packageName, fileName).body(localVarReturnType);
    }

    /**
     * Download the NPM tarball
     * Downloads a specified npm package file for a project. The metadata endpoint provides this URL. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param fileName Package file name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesNpmPackageNameDashFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesNpmPackageNameDashFileNameRequestCreation(id, packageName, fileName).toEntity(localVarReturnType);
    }

    /**
     * Download the NPM tarball
     * Downloads a specified npm package file for a project. The metadata endpoint provides this URL. This feature was introduced in GitLab 11.8
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param fileName Package file name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesNpmPackageNameDashFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesNpmPackageNameDashFileNameRequestCreation(id, packageName, fileName);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAdvisoriesBulk", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/npm/-/npm/v1/security/advisories/bulk", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAdvisoriesBulk(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation(id).body(localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAdvisoriesBulkWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAdvisoriesBulkWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation(id);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAuditsQuick", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/npm/-/npm/v1/security/audits/quick", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAuditsQuick(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation(id).body(localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAuditsQuickWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAuditsQuickWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4GroupsIdDashPackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation(id);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4PackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/packages/npm/-/npm/v1/security/advisories/bulk", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4PackagesNpmDashNpmV1SecurityAdvisoriesBulk() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4PackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation().body(localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4PackagesNpmDashNpmV1SecurityAdvisoriesBulkWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4PackagesNpmDashNpmV1SecurityAdvisoriesBulkWithResponseSpec() throws RestClientResponseException {
        return postApiV4PackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation();
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4PackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/packages/npm/-/npm/v1/security/audits/quick", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4PackagesNpmDashNpmV1SecurityAuditsQuick() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4PackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation().body(localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4PackagesNpmDashNpmV1SecurityAuditsQuickWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4PackagesNpmDashNpmV1SecurityAuditsQuickWithResponseSpec() throws RestClientResponseException {
        return postApiV4PackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation();
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAdvisoriesBulk", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/npm/-/npm/v1/security/advisories/bulk", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAdvisoriesBulk(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation(id).body(localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAdvisoriesBulkWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * NPM registry bulk advisory endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAdvisoriesBulkWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAdvisoriesBulkRequestCreation(id);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAuditsQuick", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/npm/-/npm/v1/security/audits/quick", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAuditsQuick(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation(id).body(localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAuditsQuickWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * NPM registry quick audit endpoint
     * This feature was introduced in GitLab 15.6
     * <p><b>200</b> - Ok
     * <p><b>307</b> - Temporary Redirect
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAuditsQuickWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdPackagesNpmDashNpmV1SecurityAuditsQuickRequestCreation(id);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tag' is set
        if (tag == null) {
            throw new RestClientResponseException("Missing the required parameter 'tag' when calling putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("tag", tag);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/npm/-/package/{package_name}/dist-tags/{tag}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTag(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag).body(localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag).toEntity(localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        return putApiV4GroupsIdDashPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4PackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4PackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tag' is set
        if (tag == null) {
            throw new RestClientResponseException("Missing the required parameter 'tag' when calling putApiV4PackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("tag", tag);

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
        return apiClient.invokeAPI("/api/v4/packages/npm/-/package/{package_name}/dist-tags/{tag}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4PackagesNpmDashPackagePackageNameDistTagsTag(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4PackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(packageName, tag).body(localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4PackagesNpmDashPackagePackageNameDistTagsTagWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4PackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(packageName, tag).toEntity(localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Package name
     * @param tag Package dist-tag
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4PackagesNpmDashPackagePackageNameDistTagsTagWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        return putApiV4PackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(packageName, tag);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tag' is set
        if (tag == null) {
            throw new RestClientResponseException("Missing the required parameter 'tag' when calling putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTag", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("tag", tag);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/npm/-/package/{package_name}/dist-tags/{tag}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTag(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag).body(localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag).toEntity(localVarReturnType);
    }

    /**
     * Create or Update the given tag for the given NPM package and version
     * Creates or updates a specified dist-tag for a package. This feature was introduced in GitLab 12.7
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Package name
     * @param tag Package dist-tag
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String tag) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesNpmDashPackagePackageNameDistTagsTagRequestCreation(id, packageName, tag);
    }

    /**
     * Create or deprecate an NPM package
     * Creates or deprecates an NPM package for a specified project. Deprecate support was added in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesNpmPackageNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesNpmPackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesNpmPackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesNpmPackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/npm/{package_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or deprecate an NPM package
     * Creates or deprecates an NPM package for a specified project. Deprecate support was added in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesNpmPackageName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesNpmPackageNameRequestCreation(id, packageName, _file).body(localVarReturnType);
    }

    /**
     * Create or deprecate an NPM package
     * Creates or deprecates an NPM package for a specified project. Deprecate support was added in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesNpmPackageNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesNpmPackageNameRequestCreation(id, packageName, _file).toEntity(localVarReturnType);
    }

    /**
     * Create or deprecate an NPM package
     * Creates or deprecates an NPM package for a specified project. Deprecate support was added in GitLab 16.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesNpmPackageNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesNpmPackageNameRequestCreation(id, packageName, _file);
    }
}
