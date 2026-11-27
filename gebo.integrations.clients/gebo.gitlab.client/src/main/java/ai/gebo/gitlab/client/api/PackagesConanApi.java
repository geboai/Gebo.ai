package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanFilesList;
import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanPackageManifest;
import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanPackageRevisions;
import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanPackageSnapshot;
import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanRecipeManifest;
import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanRecipeRevisions;
import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanRecipeSnapshot;
import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanRevision;
import ai.gebo.gitlab.client.model.APIEntitiesPackagesConanUploadUrls;
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
public class PackagesConanApi {
    private ApiClient apiClient;

    public PackagesConanApi() {
        this(new ApiClient());
    }

    public PackagesConanApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a recipe and package
     * Deletes a specified Conan recipe and associated package files from the package registry.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a recipe and package
     * Deletes a specified Conan recipe and associated package files from the package registry.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Delete a recipe and package
     * Deletes a specified Conan recipe and associated package files from the package registry.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Delete a recipe and package
     * Deletes a specified Conan recipe and associated package files from the package registry.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return deleteApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Delete a recipe and package
     * Deletes a specified Conan recipe and associated package files from the package registry.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a recipe and package
     * Deletes a specified Conan recipe and associated package files from the package registry.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Delete a recipe and package
     * Deletes a specified Conan recipe and associated package files from the package registry.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Delete a recipe and package
     * Deletes a specified Conan recipe and associated package files from the package registry.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Delete recipe revision
     * Deletes a specified recipe revision from the registry. If the recipe revision is the only one, the package is deleted as well. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete recipe revision
     * Deletes a specified recipe revision from the registry. If the recipe revision is the only one, the package is deleted as well. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevision(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision).body(localVarReturnType);
    }

    /**
     * Delete recipe revision
     * Deletes a specified recipe revision from the registry. If the recipe revision is the only one, the package is deleted as well. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision).toEntity(localVarReturnType);
    }

    /**
     * Delete recipe revision
     * Deletes a specified recipe revision from the registry. If the recipe revision is the only one, the package is deleted as well. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision);
    }

    /**
     * Delete a package revision
     * Deletes a specified package revision from the registry. If the package reference has only one package revision, the package reference is deleted as well. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/packages/{conan_package_reference}/revisions/{package_revision}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a package revision
     * Deletes a specified package revision from the registry. If the package reference has only one package revision, the package reference is deleted as well. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevision(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision).body(localVarReturnType);
    }

    /**
     * Delete a package revision
     * Deletes a specified package revision from the registry. If the package reference has only one package revision, the package reference is deleted as well. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision).toEntity(localVarReturnType);
    }

    /**
     * Delete a package revision
     * Deletes a specified package revision from the registry. If the package reference has only one package revision, the package reference is deleted as well. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision);
    }

    /**
     * Retrieve a recipe snapshot
     * Retrieves a snapshot of the files for a specified Conan recipe. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeSnapshot
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a recipe snapshot
     * Retrieves a snapshot of the files for a specified Conan recipe. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeSnapshot
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRecipeSnapshot getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Retrieve a recipe snapshot
     * Retrieves a snapshot of the files for a specified Conan recipe. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRecipeSnapshot&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRecipeSnapshot> getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a recipe snapshot
     * Retrieves a snapshot of the files for a specified Conan recipe. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Retrieve a recipe manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified recipe.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/digest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a recipe manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified recipe.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRecipeManifest getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestRequestCreation(packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Retrieve a recipe manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified recipe.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRecipeManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRecipeManifest> getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestRequestCreation(packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a recipe manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified recipe.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestRequestCreation(packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * List all recipe download URLs
     * Lists all files and associated download URLs for a specified recipe in the package registry. Returns the same payload as the recipe manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/download_urls", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all recipe download URLs
     * Lists all files and associated download URLs for a specified recipe in the package registry. Returns the same payload as the recipe manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRecipeManifest getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * List all recipe download URLs
     * Lists all files and associated download URLs for a specified recipe in the package registry. Returns the same payload as the recipe manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRecipeManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRecipeManifest> getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * List all recipe download URLs
     * Lists all files and associated download URLs for a specified recipe in the package registry. Returns the same payload as the recipe manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Retrieve a package snapshot
     * Retrieves a snapshot of the files for a specified Conan package and reference. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageSnapshot
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanPackageSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/packages/{conan_package_reference}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a package snapshot
     * Retrieves a snapshot of the files for a specified Conan package and reference. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageSnapshot
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanPackageSnapshot getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).body(localVarReturnType);
    }

    /**
     * Retrieve a package snapshot
     * Retrieves a snapshot of the files for a specified Conan package and reference. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanPackageSnapshot&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanPackageSnapshot> getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a package snapshot
     * Retrieves a snapshot of the files for a specified Conan package and reference. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference);
    }

    /**
     * Retrieve a package manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/packages/{conan_package_reference}/digest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a package manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanPackageManifest getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).body(localVarReturnType);
    }

    /**
     * Retrieve a package manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanPackageManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanPackageManifest> getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a package manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference);
    }

    /**
     * List all package download URLs
     * Lists all files and associated download URLs for a specified package in the package registry. Returns the same payload as the package manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/packages/{conan_package_reference}/download_urls", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all package download URLs
     * Lists all files and associated download URLs for a specified package in the package registry. Returns the same payload as the package manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanPackageManifest getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).body(localVarReturnType);
    }

    /**
     * List all package download URLs
     * Lists all files and associated download URLs for a specified package in the package registry. Returns the same payload as the package manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanPackageManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanPackageManifest> getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * List all package download URLs
     * Lists all files and associated download URLs for a specified package in the package registry. Returns the same payload as the package manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1ConansSearchRequestCreation(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        // verify the required parameter 'q' is set
        if (q == null) {
            throw new RestClientResponseException("Missing the required parameter 'q' when calling getApiV4PackagesConanV1ConansSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ignorecase", ignorecase));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesConanV1ConansSearch(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesConanV1ConansSearchRequestCreation(q, ignorecase).body(localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesConanV1ConansSearchWithHttpInfo(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1ConansSearchRequestCreation(q, ignorecase).toEntity(localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1ConansSearchWithResponseSpec(@jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        return getApiV4PackagesConanV1ConansSearchRequestCreation(q, ignorecase);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. You must use the download URL returned from the recipe download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/export/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. You must use the download URL returned from the recipe download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).body(localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. You must use the download URL returned from the recipe download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. You must use the download URL returned from the recipe download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. You must use the download URL returned from the package download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/package/{conan_package_reference}/{package_revision}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. You must use the download URL returned from the package download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).body(localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. You must use the download URL returned from the package download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. You must use the download URL returned from the package download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName);
    }

    /**
     * Verify availability of a Conan repository
     * Verifies availability of a Conan repository.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1PingRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/ping", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Verify availability of a Conan repository
     * Verifies availability of a Conan repository.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesConanV1Ping() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesConanV1PingRequestCreation().body(localVarReturnType);
    }

    /**
     * Verify availability of a Conan repository
     * Verifies availability of a Conan repository.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesConanV1PingWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1PingRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Verify availability of a Conan repository
     * Verifies availability of a Conan repository.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1PingWithResponseSpec() throws RestClientResponseException {
        return getApiV4PackagesConanV1PingRequestCreation();
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1UsersAuthenticateRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/users/authenticate", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesConanV1UsersAuthenticate() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesConanV1UsersAuthenticateRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesConanV1UsersAuthenticateWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1UsersAuthenticateRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1UsersAuthenticateWithResponseSpec() throws RestClientResponseException {
        return getApiV4PackagesConanV1UsersAuthenticateRequestCreation();
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesConanV1UsersCheckCredentialsRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/users/check_credentials", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesConanV1UsersCheckCredentials() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesConanV1UsersCheckCredentialsRequestCreation().body(localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesConanV1UsersCheckCredentialsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesConanV1UsersCheckCredentialsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesConanV1UsersCheckCredentialsWithResponseSpec() throws RestClientResponseException {
        return getApiV4PackagesConanV1UsersCheckCredentialsRequestCreation();
    }

    /**
     * Retrieve a recipe snapshot
     * Retrieves a snapshot of the files for a specified Conan recipe. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeSnapshot
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a recipe snapshot
     * Retrieves a snapshot of the files for a specified Conan recipe. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeSnapshot
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRecipeSnapshot getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannel(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Retrieve a recipe snapshot
     * Retrieves a snapshot of the files for a specified Conan recipe. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRecipeSnapshot&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRecipeSnapshot> getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a recipe snapshot
     * Retrieves a snapshot of the files for a specified Conan recipe. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Retrieve a recipe manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified recipe.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/digest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a recipe manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified recipe.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRecipeManifest getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigest(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Retrieve a recipe manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified recipe.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRecipeManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRecipeManifest> getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a recipe manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified recipe.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDigestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * List all recipe download URLs
     * Lists all files and associated download URLs for a specified recipe in the package registry. Returns the same payload as the recipe manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/download_urls", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all recipe download URLs
     * Lists all files and associated download URLs for a specified recipe in the package registry. Returns the same payload as the recipe manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRecipeManifest getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrls(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * List all recipe download URLs
     * Lists all files and associated download URLs for a specified recipe in the package registry. Returns the same payload as the recipe manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRecipeManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRecipeManifest> getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * List all recipe download URLs
     * Lists all files and associated download URLs for a specified recipe in the package registry. Returns the same payload as the recipe manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelDownloadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Retrieve a package snapshot
     * Retrieves a snapshot of the files for a specified Conan package and reference. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageSnapshot
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanPackageSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/packages/{conan_package_reference}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a package snapshot
     * Retrieves a snapshot of the files for a specified Conan package and reference. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageSnapshot
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanPackageSnapshot getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReference(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).body(localVarReturnType);
    }

    /**
     * Retrieve a package snapshot
     * Retrieves a snapshot of the files for a specified Conan package and reference. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanPackageSnapshot&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanPackageSnapshot> getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageSnapshot> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a package snapshot
     * Retrieves a snapshot of the files for a specified Conan package and reference. The snapshot is a list of filenames with their associated MD5 hash.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference);
    }

    /**
     * Retrieve a package manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/packages/{conan_package_reference}/digest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a package manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanPackageManifest getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigest(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).body(localVarReturnType);
    }

    /**
     * Retrieve a package manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanPackageManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanPackageManifest> getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a package manifest
     * Retrieves a manifest that includes a list of files and associated download URLs for a specified package.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDigestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference);
    }

    /**
     * List all package download URLs
     * Lists all files and associated download URLs for a specified package in the package registry. Returns the same payload as the package manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/packages/{conan_package_reference}/download_urls", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all package download URLs
     * Lists all files and associated download URLs for a specified package in the package registry. Returns the same payload as the package manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageManifest
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanPackageManifest getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrls(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).body(localVarReturnType);
    }

    /**
     * List all package download URLs
     * Lists all files and associated download URLs for a specified package in the package registry. Returns the same payload as the package manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanPackageManifest&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanPackageManifest> getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageManifest> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * List all package download URLs
     * Lists all files and associated download URLs for a specified package in the package registry. Returns the same payload as the package manifest endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceDownloadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1ConansSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'q' is set
        if (q == null) {
            throw new RestClientResponseException("Missing the required parameter 'q' when calling getApiV4ProjectsIdPackagesConanV1ConansSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ignorecase", ignorecase));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV1ConansSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV1ConansSearchRequestCreation(id, q, ignorecase).body(localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV1ConansSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1ConansSearchRequestCreation(id, q, ignorecase).toEntity(localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1ConansSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1ConansSearchRequestCreation(id, q, ignorecase);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. You must use the download URL returned from the recipe download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/export/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. You must use the download URL returned from the recipe download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).body(localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. You must use the download URL returned from the recipe download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. You must use the download URL returned from the recipe download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. You must use the download URL returned from the package download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/package/{conan_package_reference}/{package_revision}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. You must use the download URL returned from the package download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).body(localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. You must use the download URL returned from the package download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. You must use the download URL returned from the package download URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName);
    }

    /**
     * Verify availability of a Conan repository
     * Verifies availability of a Conan repository.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1PingRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1Ping", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/ping", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Verify availability of a Conan repository
     * Verifies availability of a Conan repository.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV1Ping(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV1PingRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Verify availability of a Conan repository
     * Verifies availability of a Conan repository.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV1PingWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1PingRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Verify availability of a Conan repository
     * Verifies availability of a Conan repository.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1PingWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1PingRequestCreation(id);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1UsersAuthenticateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1UsersAuthenticate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/users/authenticate", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV1UsersAuthenticate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV1UsersAuthenticateRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV1UsersAuthenticateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1UsersAuthenticateRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1UsersAuthenticateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1UsersAuthenticateRequestCreation(id);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV1UsersCheckCredentialsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV1UsersCheckCredentials", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/users/check_credentials", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV1UsersCheckCredentials(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV1UsersCheckCredentialsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV1UsersCheckCredentialsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV1UsersCheckCredentialsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV1UsersCheckCredentialsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV1UsersCheckCredentialsRequestCreation(id);
    }

    /**
     * Retrieve latest recipe revision
     * Retrieves the revision hash and creation date of the latest package recipe. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRevision
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatestRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/latest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve latest recipe revision
     * Retrieves the revision hash and creation date of the latest package recipe. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRevision
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRevision getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatest(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Retrieve latest recipe revision
     * Retrieves the revision hash and creation date of the latest package recipe. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRevision&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRevision> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatestWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Retrieve latest recipe revision
     * Retrieves the revision hash and creation date of the latest package recipe. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatestWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelLatestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * List all recipe revisions
     * Lists all revisions for a package recipe. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeRevisions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeRevisions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all recipe revisions
     * Lists all revisions for a package recipe. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanRecipeRevisions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRecipeRevisions getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeRevisions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * List all recipe revisions
     * Lists all revisions for a package recipe. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRecipeRevisions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRecipeRevisions> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRecipeRevisions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * List all recipe revisions
     * Lists all revisions for a package recipe. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * List all recipe files
     * Lists all recipe files from the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @return APIEntitiesPackagesConanFilesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanFilesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/files", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all recipe files
     * Lists all recipe files from the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @return APIEntitiesPackagesConanFilesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanFilesList getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFiles(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanFilesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision).body(localVarReturnType);
    }

    /**
     * List all recipe files
     * Lists all recipe files from the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanFilesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanFilesList> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanFilesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision).toEntity(localVarReturnType);
    }

    /**
     * List all recipe files
     * Lists all recipe files from the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/files/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).body(localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a recipe file
     * Retrieves a specified recipe file from the package registry. This feature was introduced in GitLab 17.8.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName);
    }

    /**
     * Retrieve latest package revision
     * Retrieves the revision hash and creation date of the latest package revision for a specified recipe revision and package reference. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanRevision
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatestRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/packages/{conan_package_reference}/latest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve latest package revision
     * Retrieves the revision hash and creation date of the latest package revision for a specified recipe revision and package reference. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanRevision
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanRevision getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatest(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference).body(localVarReturnType);
    }

    /**
     * Retrieve latest package revision
     * Retrieves the revision hash and creation date of the latest package revision for a specified recipe revision and package reference. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanRevision&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanRevision> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatestWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * Retrieve latest package revision
     * Retrieves the revision hash and creation date of the latest package revision for a specified recipe revision and package reference. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatestWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceLatestRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference);
    }

    /**
     * List all package revisions
     * Lists all package revisions for a specified recipe revision and package reference. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageRevisions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanPackageRevisions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/packages/{conan_package_reference}/revisions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all package revisions
     * Lists all package revisions for a specified recipe revision and package reference. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanPackageRevisions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanPackageRevisions getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageRevisions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference).body(localVarReturnType);
    }

    /**
     * List all package revisions
     * Lists all package revisions for a specified recipe revision and package reference. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanPackageRevisions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanPackageRevisions> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanPackageRevisions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * List all package revisions
     * Lists all package revisions for a specified recipe revision and package reference. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference);
    }

    /**
     * List all package files
     * Lists all package files. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @return APIEntitiesPackagesConanFilesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanFilesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/packages/{conan_package_reference}/revisions/{package_revision}/files", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all package files
     * Lists all package files. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @return APIEntitiesPackagesConanFilesList
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanFilesList getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFiles(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanFilesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision).body(localVarReturnType);
    }

    /**
     * List all package files
     * Lists all package files. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanFilesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanFilesList> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanFilesList> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision).toEntity(localVarReturnType);
    }

    /**
     * List all package files
     * Lists all package files. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/packages/{conan_package_reference}/revisions/{package_revision}/files/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).body(localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a package file
     * Retrieves a specified package file from the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName);
    }

    /**
     * Retrieve package references metadata by recipe revision
     * Retrieves the metadata for all package references associated with a specified recipe revision. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve package references metadata by recipe revision
     * Retrieves the metadata for all package references associated with a specified recipe revision. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision).body(localVarReturnType);
    }

    /**
     * Retrieve package references metadata by recipe revision
     * Retrieves the metadata for all package references associated with a specified recipe revision. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision).toEntity(localVarReturnType);
    }

    /**
     * Retrieve package references metadata by recipe revision
     * Retrieves the metadata for all package references associated with a specified recipe revision. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.1.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * Retrieve package references metadata
     * Retrieves the metadata for all package references of a specified package. The response maps each package reference to its metadata: &#x60;settings&#x60;, the build settings; &#x60;options&#x60;, the package options; &#x60;requires&#x60;, the required dependencies; and &#x60;recipe_hash&#x60;, the hash of the recipe. This feature was introduced in GitLab 18.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelSearchRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2ConansSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'q' is set
        if (q == null) {
            throw new RestClientResponseException("Missing the required parameter 'q' when calling getApiV4ProjectsIdPackagesConanV2ConansSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ignorecase", ignorecase));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV2ConansSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV2ConansSearchRequestCreation(id, q, ignorecase).body(localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV2ConansSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2ConansSearchRequestCreation(id, q, ignorecase).toEntity(localVarReturnType);
    }

    /**
     * Search for a Conan package
     * Searches the instance for a specified Conan package. The project endpoint searches only the specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param q Search query. Use &#x60;*&#x60; as a wildcard.
     * @param ignorecase If &#x60;true&#x60;, ignores case when searching.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2ConansSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable Boolean ignorecase) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2ConansSearchRequestCreation(id, q, ignorecase);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2UsersAuthenticateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2UsersAuthenticate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/users/authenticate", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV2UsersAuthenticate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV2UsersAuthenticateRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV2UsersAuthenticateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2UsersAuthenticateRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an authentication token
     * Retrieves an authentication token. Creates a JSON Web Token (JWT) for use as a Bearer header in other requests to the package registry. The request authenticates with Basic Auth, sending the base64-encoded &#x60;&lt;username&gt;:&lt;access_token&gt;&#x60; pair in the &#x60;Authorization: Basic&#x60; header.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2UsersAuthenticateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2UsersAuthenticateRequestCreation(id);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesConanV2UsersCheckCredentialsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesConanV2UsersCheckCredentials", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/users/check_credentials", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesConanV2UsersCheckCredentials(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesConanV2UsersCheckCredentialsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesConanV2UsersCheckCredentialsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesConanV2UsersCheckCredentialsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Verify authentication credentials
     * Verifies authentication credentials for a Conan package registry. Accepts Basic Auth credentials or a Conan JWT generated by the &#x60;users/authenticate&#x60; endpoint.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesConanV2UsersCheckCredentialsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesConanV2UsersCheckCredentialsRequestCreation(id);
    }

    /**
     * List all package upload URLs
     * Lists all the upload URLs for a specified collection of package files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanUploadUrls
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/packages/{conan_package_reference}/upload_urls", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all package upload URLs
     * Lists all the upload URLs for a specified collection of package files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanUploadUrls
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanUploadUrls postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).body(localVarReturnType);
    }

    /**
     * List all package upload URLs
     * Lists all the upload URLs for a specified collection of package files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanUploadUrls&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanUploadUrls> postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * List all package upload URLs
     * Lists all the upload URLs for a specified collection of package files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel, conanPackageReference);
    }

    /**
     * List all recipe upload URLs
     * Lists all the upload URLs for a specified collection of recipe files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanUploadUrls
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/upload_urls", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all recipe upload URLs
     * Lists all the upload URLs for a specified collection of recipe files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanUploadUrls
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanUploadUrls postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * List all recipe upload URLs
     * Lists all the upload URLs for a specified collection of recipe files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanUploadUrls&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanUploadUrls> postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * List all recipe upload URLs
     * Lists all the upload URLs for a specified collection of recipe files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return postApiV4PackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsRequestCreation(packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * List all package upload URLs
     * Lists all the upload URLs for a specified collection of package files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanUploadUrls
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("conan_package_reference", conanPackageReference);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/packages/{conan_package_reference}/upload_urls", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all package upload URLs
     * Lists all the upload URLs for a specified collection of package files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return APIEntitiesPackagesConanUploadUrls
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanUploadUrls postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrls(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).body(localVarReturnType);
    }

    /**
     * List all package upload URLs
     * Lists all the upload URLs for a specified collection of package files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanUploadUrls&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanUploadUrls> postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference).toEntity(localVarReturnType);
    }

    /**
     * List all package upload URLs
     * Lists all the upload URLs for a specified collection of package files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String conanPackageReference) throws RestClientResponseException {
        return postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelPackagesConanPackageReferenceUploadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, conanPackageReference);
    }

    /**
     * List all recipe upload URLs
     * Lists all the upload URLs for a specified collection of recipe files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanUploadUrls
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);

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

        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/conans/{package_name}/{package_version}/{package_username}/{package_channel}/upload_urls", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all recipe upload URLs
     * Lists all the upload URLs for a specified collection of recipe files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return APIEntitiesPackagesConanUploadUrls
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesConanUploadUrls postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrls(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).body(localVarReturnType);
    }

    /**
     * List all recipe upload URLs
     * Lists all the upload URLs for a specified collection of recipe files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseEntity&lt;APIEntitiesPackagesConanUploadUrls&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesConanUploadUrls> postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesConanUploadUrls> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel).toEntity(localVarReturnType);
    }

    /**
     * List all recipe upload URLs
     * Lists all the upload URLs for a specified collection of recipe files. The request must include a JSON object with the name and size of the individual files. The response maps each file name to its upload URL.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel) throws RestClientResponseException {
        return postApiV4ProjectsIdPackagesConanV1ConansPackageNamePackageVersionPackageUsernamePackageChannelUploadUrlsRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. You must use the upload URL returned from the recipe upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/export/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. You must use the upload URL returned from the recipe upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file).body(localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. You must use the upload URL returned from the recipe upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. You must use the upload URL returned from the recipe upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/export/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).body(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. You must use the upload URL returned from the package upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/package/{conan_package_reference}/{package_revision}/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. You must use the upload URL returned from the package upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file).body(localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. You must use the upload URL returned from the package upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. You must use the upload URL returned from the package upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/package/{conan_package_reference}/{package_revision}/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).body(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return putApiV4PackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeRequestCreation(packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. You must use the upload URL returned from the recipe upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/export/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. You must use the upload URL returned from the recipe upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file).body(localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. You must use the upload URL returned from the recipe upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. You must use the upload URL returned from the recipe upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/export/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).body(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionExportFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. You must use the upload URL returned from the package upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/package/{conan_package_reference}/{package_revision}/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. You must use the upload URL returned from the package upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file).body(localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. You must use the upload URL returned from the package upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. You must use the upload URL returned from the package upload URLs endpoint.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v1/files/{package_name}/{package_version}/{package_username}/{package_channel}/{recipe_revision}/package/{conan_package_reference}/{package_revision}/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).body(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package. GitLab does not yet support Conan revisions, so the default value of &#x60;0&#x60; is always used.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesConanV1FilesPackageNamePackageVersionPackageUsernamePackageChannelRecipeRevisionPackageConanPackageReferencePackageRevisionFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/files/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file).body(localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a recipe file
     * Uploads a specified recipe file to the package registry. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName, _file);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/files/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).body(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan recipe file
     * Authorizes the Conan recipe file. This feature was introduced in GitLab 17.10.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionFilesFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, fileName);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/packages/{conan_package_reference}/revisions/{package_revision}/files/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file).body(localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a package file
     * Uploads a specified package file to the package registry. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName, _file);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageUsername' is set
        if (packageUsername == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageUsername' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageChannel' is set
        if (packageChannel == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageChannel' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'recipeRevision' is set
        if (recipeRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'recipeRevision' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'conanPackageReference' is set
        if (conanPackageReference == null) {
            throw new RestClientResponseException("Missing the required parameter 'conanPackageReference' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageRevision' is set
        if (packageRevision == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageRevision' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("package_username", packageUsername);
        pathParams.put("package_channel", packageChannel);
        pathParams.put("recipe_revision", recipeRevision);
        pathParams.put("conan_package_reference", conanPackageReference);
        pathParams.put("package_revision", packageRevision);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/conan/v2/conans/{package_name}/{package_version}/{package_username}/{package_channel}/revisions/{recipe_revision}/packages/{conan_package_reference}/revisions/{package_revision}/files/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).body(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize the Conan package file
     * Authorizes the Conan package file. This feature was introduced in GitLab 17.11.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param packageName Name of the package.
     * @param packageVersion Version of the package.
     * @param packageUsername Conan username of the package. This is the &#x60;+&#x60;-separated full path of the project.
     * @param packageChannel Channel of the package.
     * @param recipeRevision Revision of the recipe.
     * @param conanPackageReference Reference hash of the Conan package. Conan generates this value.
     * @param packageRevision Revision of the package.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String packageUsername, @jakarta.annotation.Nonnull String packageChannel, @jakarta.annotation.Nonnull String recipeRevision, @jakarta.annotation.Nonnull String conanPackageReference, @jakarta.annotation.Nonnull String packageRevision, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesConanV2ConansPackageNamePackageVersionPackageUsernamePackageChannelRevisionsRecipeRevisionPackagesConanPackageReferenceRevisionsPackageRevisionFilesFileNameAuthorizeRequestCreation(id, packageName, packageVersion, packageUsername, packageChannel, recipeRevision, conanPackageReference, packageRevision, fileName);
    }
}
