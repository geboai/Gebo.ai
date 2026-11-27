package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesTerraformModuleVersion;
import ai.gebo.gitlab.client.model.APIEntitiesTerraformModuleVersions;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdTerraformStateNameLockRequest;

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
public class TerraformApi {
    private ApiClient apiClient;

    public TerraformApi() {
        this(new ApiClient());
    }

    public TerraformApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a Terraform state
     * Deletes a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdTerraformStateNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdTerraformStateName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ProjectsIdTerraformStateName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/terraform/state/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a Terraform state
     * Deletes a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdTerraformStateName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdTerraformStateNameRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Delete a Terraform state
     * Deletes a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdTerraformStateNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdTerraformStateNameRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Delete a Terraform state
     * Deletes a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdTerraformStateNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4ProjectsIdTerraformStateNameRequestCreation(id, name);
    }

    /**
     * Unlock a Terraform state
     * Unlocks a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param ID Terraform state lock ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdTerraformStateNameLockRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String ID) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdTerraformStateNameLock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ProjectsIdTerraformStateNameLock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ID", ID));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/terraform/state/{name}/lock", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unlock a Terraform state
     * Unlocks a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param ID Terraform state lock ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdTerraformStateNameLock(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String ID) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdTerraformStateNameLockRequestCreation(id, name, ID).body(localVarReturnType);
    }

    /**
     * Unlock a Terraform state
     * Unlocks a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param ID Terraform state lock ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdTerraformStateNameLockWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String ID) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdTerraformStateNameLockRequestCreation(id, name, ID).toEntity(localVarReturnType);
    }

    /**
     * Unlock a Terraform state
     * Unlocks a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param ID Terraform state lock ID.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdTerraformStateNameLockWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String ID) throws RestClientResponseException {
        return deleteApiV4ProjectsIdTerraformStateNameLockRequestCreation(id, name, ID);
    }

    /**
     * Delete a Terraform state version
     * Deletes a specified Terraform state version.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param serial Version number of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdTerraformStateNameVersionsSerialRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull Integer serial) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdTerraformStateNameVersionsSerial", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ProjectsIdTerraformStateNameVersionsSerial", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'serial' is set
        if (serial == null) {
            throw new RestClientResponseException("Missing the required parameter 'serial' when calling deleteApiV4ProjectsIdTerraformStateNameVersionsSerial", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);
        pathParams.put("serial", serial);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/terraform/state/{name}/versions/{serial}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a Terraform state version
     * Deletes a specified Terraform state version.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param serial Version number of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdTerraformStateNameVersionsSerial(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull Integer serial) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdTerraformStateNameVersionsSerialRequestCreation(id, name, serial).body(localVarReturnType);
    }

    /**
     * Delete a Terraform state version
     * Deletes a specified Terraform state version.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param serial Version number of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdTerraformStateNameVersionsSerialWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull Integer serial) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdTerraformStateNameVersionsSerialRequestCreation(id, name, serial).toEntity(localVarReturnType);
    }

    /**
     * Delete a Terraform state version
     * Deletes a specified Terraform state version.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param serial Version number of the Terraform state.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdTerraformStateNameVersionsSerialWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull Integer serial) throws RestClientResponseException {
        return deleteApiV4ProjectsIdTerraformStateNameVersionsSerialRequestCreation(id, name, serial);
    }

    /**
     * Retrieve latest version for a module
     * Retrieves latest version for a specified module.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return APIEntitiesTerraformModuleVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemRequestCreation(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        // verify the required parameter 'moduleNamespace' is set
        if (moduleNamespace == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleNamespace' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("module_namespace", moduleNamespace);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);

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

        ParameterizedTypeReference<APIEntitiesTerraformModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/terraform/modules/v1/{module_namespace}/{module_name}/{module_system}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve latest version for a module
     * Retrieves latest version for a specified module.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return APIEntitiesTerraformModuleVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTerraformModuleVersion getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystem(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTerraformModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemRequestCreation(moduleNamespace, moduleName, moduleSystem).body(localVarReturnType);
    }

    /**
     * Retrieve latest version for a module
     * Retrieves latest version for a specified module.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return ResponseEntity&lt;APIEntitiesTerraformModuleVersion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTerraformModuleVersion> getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemWithHttpInfo(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTerraformModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemRequestCreation(moduleNamespace, moduleName, moduleSystem).toEntity(localVarReturnType);
    }

    /**
     * Retrieve latest version for a module
     * Retrieves latest version for a specified module.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemWithResponseSpec(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemRequestCreation(moduleNamespace, moduleName, moduleSystem);
    }

    /**
     * Retrieve download URL for latest module version
     * Retrieves download URL for latest module version. Redirects to &#x60;packages/terraform/modules/v1/:module_namespace/:module_name/:module_system/:module_version/download&#x60; for the latest version, which returns the URL in the &#x60;X-Terraform-Get&#x60; header with an empty &#x60;204 No Content&#x60; response.
     * <p><b>302</b> - Found
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownloadRequestCreation(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        // verify the required parameter 'moduleNamespace' is set
        if (moduleNamespace == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleNamespace' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("module_namespace", moduleNamespace);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);

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
        return apiClient.invokeAPI("/api/v4/packages/terraform/modules/v1/{module_namespace}/{module_name}/{module_system}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve download URL for latest module version
     * Retrieves download URL for latest module version. Redirects to &#x60;packages/terraform/modules/v1/:module_namespace/:module_name/:module_system/:module_version/download&#x60; for the latest version, which returns the URL in the &#x60;X-Terraform-Get&#x60; header with an empty &#x60;204 No Content&#x60; response.
     * <p><b>302</b> - Found
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownload(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownloadRequestCreation(moduleNamespace, moduleName, moduleSystem).body(localVarReturnType);
    }

    /**
     * Retrieve download URL for latest module version
     * Retrieves download URL for latest module version. Redirects to &#x60;packages/terraform/modules/v1/:module_namespace/:module_name/:module_system/:module_version/download&#x60; for the latest version, which returns the URL in the &#x60;X-Terraform-Get&#x60; header with an empty &#x60;204 No Content&#x60; response.
     * <p><b>302</b> - Found
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownloadWithHttpInfo(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownloadRequestCreation(moduleNamespace, moduleName, moduleSystem).toEntity(localVarReturnType);
    }

    /**
     * Retrieve download URL for latest module version
     * Retrieves download URL for latest module version. Redirects to &#x60;packages/terraform/modules/v1/:module_namespace/:module_name/:module_system/:module_version/download&#x60; for the latest version, which returns the URL in the &#x60;X-Terraform-Get&#x60; header with an empty &#x60;204 No Content&#x60; response.
     * <p><b>302</b> - Found
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownloadWithResponseSpec(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemDownloadRequestCreation(moduleNamespace, moduleName, moduleSystem);
    }

    /**
     * Get details about specific version of a module
     * Get details about specific version of a module
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return APIEntitiesTerraformModuleVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionRequestCreation(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        // verify the required parameter 'moduleNamespace' is set
        if (moduleNamespace == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleNamespace' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("module_namespace", moduleNamespace);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);
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

        ParameterizedTypeReference<APIEntitiesTerraformModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/terraform/modules/v1/{module_namespace}/{module_name}/{module_system}/{module_version}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get details about specific version of a module
     * Get details about specific version of a module
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return APIEntitiesTerraformModuleVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTerraformModuleVersion getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersion(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTerraformModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion).body(localVarReturnType);
    }

    /**
     * Get details about specific version of a module
     * Get details about specific version of a module
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return ResponseEntity&lt;APIEntitiesTerraformModuleVersion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTerraformModuleVersion> getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionWithHttpInfo(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTerraformModuleVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion).toEntity(localVarReturnType);
    }

    /**
     * Get details about specific version of a module
     * Get details about specific version of a module
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionWithResponseSpec(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion);
    }

    /**
     * Get download location for specific version of a module
     * Returns the download URL for a specified module version in the &#x60;X-Terraform-Get&#x60; header, with an empty &#x60;204 No Content&#x60; response.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownloadRequestCreation(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        // verify the required parameter 'moduleNamespace' is set
        if (moduleNamespace == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleNamespace' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("module_namespace", moduleNamespace);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);
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
        return apiClient.invokeAPI("/api/v4/packages/terraform/modules/v1/{module_namespace}/{module_name}/{module_system}/{module_version}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get download location for specific version of a module
     * Returns the download URL for a specified module version in the &#x60;X-Terraform-Get&#x60; header, with an empty &#x60;204 No Content&#x60; response.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownload(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownloadRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion).body(localVarReturnType);
    }

    /**
     * Get download location for specific version of a module
     * Returns the download URL for a specified module version in the &#x60;X-Terraform-Get&#x60; header, with an empty &#x60;204 No Content&#x60; response.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownloadWithHttpInfo(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownloadRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion).toEntity(localVarReturnType);
    }

    /**
     * Get download location for specific version of a module
     * Returns the download URL for a specified module version in the &#x60;X-Terraform-Get&#x60; header, with an empty &#x60;204 No Content&#x60; response.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownloadWithResponseSpec(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionDownloadRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion);
    }

    /**
     * Download specific version of a module
     * Download specific version of a module
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFileRequestCreation(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        // verify the required parameter 'moduleNamespace' is set
        if (moduleNamespace == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleNamespace' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("module_namespace", moduleNamespace);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);
        pathParams.put("module_version", moduleVersion);

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
        return apiClient.invokeAPI("/api/v4/packages/terraform/modules/v1/{module_namespace}/{module_name}/{module_system}/{module_version}/file", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download specific version of a module
     * Download specific version of a module
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFile(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFileRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion).body(localVarReturnType);
    }

    /**
     * Download specific version of a module
     * Download specific version of a module
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFileWithHttpInfo(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFileRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion).toEntity(localVarReturnType);
    }

    /**
     * Download specific version of a module
     * Download specific version of a module
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Version of the module
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFileWithResponseSpec(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemModuleVersionFileRequestCreation(moduleNamespace, moduleName, moduleSystem, moduleVersion);
    }

    /**
     * List all available versions for a module
     * Lists all available versions for a specified module.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return APIEntitiesTerraformModuleVersions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersionsRequestCreation(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        // verify the required parameter 'moduleNamespace' is set
        if (moduleNamespace == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleNamespace' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("module_namespace", moduleNamespace);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);

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

        ParameterizedTypeReference<APIEntitiesTerraformModuleVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/packages/terraform/modules/v1/{module_namespace}/{module_name}/{module_system}/versions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all available versions for a module
     * Lists all available versions for a specified module.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return APIEntitiesTerraformModuleVersions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTerraformModuleVersions getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersions(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTerraformModuleVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersionsRequestCreation(moduleNamespace, moduleName, moduleSystem).body(localVarReturnType);
    }

    /**
     * List all available versions for a module
     * Lists all available versions for a specified module.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return ResponseEntity&lt;APIEntitiesTerraformModuleVersions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTerraformModuleVersions> getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersionsWithHttpInfo(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTerraformModuleVersions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersionsRequestCreation(moduleNamespace, moduleName, moduleSystem).toEntity(localVarReturnType);
    }

    /**
     * List all available versions for a module
     * Lists all available versions for a specified module.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param moduleNamespace Group&#39;s ID or slug. The slug is the path of the top-level group (namespace) that the module&#39;s project or subgroup belongs to.
     * @param moduleName Name of the module.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersionsWithResponseSpec(@jakarta.annotation.Nonnull String moduleNamespace, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem) throws RestClientResponseException {
        return getApiV4PackagesTerraformModulesV1ModuleNamespaceModuleNameModuleSystemVersionsRequestCreation(moduleNamespace, moduleName, moduleSystem);
    }

    /**
     * Download the latest version of a module
     * Downloads the latest version of a specified module. This feature was introduced in GitLab 16.7.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param terraformGet Terraform get redirection flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nullable String terraformGet) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "terraform-get", terraformGet));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/terraform/modules/{module_name}/{module_system}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download the latest version of a module
     * Downloads the latest version of a specified module. This feature was introduced in GitLab 16.7.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param terraformGet Terraform get redirection flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystem(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nullable String terraformGet) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemRequestCreation(id, moduleName, moduleSystem, terraformGet).body(localVarReturnType);
    }

    /**
     * Download the latest version of a module
     * Downloads the latest version of a specified module. This feature was introduced in GitLab 16.7.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param terraformGet Terraform get redirection flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nullable String terraformGet) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemRequestCreation(id, moduleName, moduleSystem, terraformGet).toEntity(localVarReturnType);
    }

    /**
     * Download the latest version of a module
     * Downloads the latest version of a specified module. This feature was introduced in GitLab 16.7.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param terraformGet Terraform get redirection flag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nullable String terraformGet) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemRequestCreation(id, moduleName, moduleSystem, terraformGet);
    }

    /**
     * Download a specific version of a module
     * This feature was introduced in GitLab 16.7
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @param terraformGet Terraform get redirection flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion, @jakarta.annotation.Nullable String terraformGet) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);
        pathParams.put("module_version", moduleVersion);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "terraform-get", terraformGet));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/terraform/modules/{module_name}/{module_system}/{module_version}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a specific version of a module
     * This feature was introduced in GitLab 16.7
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @param terraformGet Terraform get redirection flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersion(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion, @jakarta.annotation.Nullable String terraformGet) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionRequestCreation(id, moduleName, moduleSystem, moduleVersion, terraformGet).body(localVarReturnType);
    }

    /**
     * Download a specific version of a module
     * This feature was introduced in GitLab 16.7
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @param terraformGet Terraform get redirection flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion, @jakarta.annotation.Nullable String terraformGet) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionRequestCreation(id, moduleName, moduleSystem, moduleVersion, terraformGet).toEntity(localVarReturnType);
    }

    /**
     * Download a specific version of a module
     * This feature was introduced in GitLab 16.7
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @param terraformGet Terraform get redirection flag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion, @jakarta.annotation.Nullable String terraformGet) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionRequestCreation(id, moduleName, moduleSystem, moduleVersion, terraformGet);
    }

    /**
     * Retrieve a Terraform state
     * Retrieves a Terraform state by name for a specified project.
     * <p><b>200</b> - OK
     * <p><b>204</b> - Empty state
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param ID Terraform state lock ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdTerraformStateNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String ID) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdTerraformStateName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdTerraformStateName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ID", ID));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/terraform/state/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Terraform state
     * Retrieves a Terraform state by name for a specified project.
     * <p><b>200</b> - OK
     * <p><b>204</b> - Empty state
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param ID Terraform state lock ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdTerraformStateName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String ID) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdTerraformStateNameRequestCreation(id, name, ID).body(localVarReturnType);
    }

    /**
     * Retrieve a Terraform state
     * Retrieves a Terraform state by name for a specified project.
     * <p><b>200</b> - OK
     * <p><b>204</b> - Empty state
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param ID Terraform state lock ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdTerraformStateNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String ID) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTerraformStateNameRequestCreation(id, name, ID).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Terraform state
     * Retrieves a Terraform state by name for a specified project.
     * <p><b>200</b> - OK
     * <p><b>204</b> - Empty state
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param ID Terraform state lock ID.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdTerraformStateNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String ID) throws RestClientResponseException {
        return getApiV4ProjectsIdTerraformStateNameRequestCreation(id, name, ID);
    }

    /**
     * Retrieve a Terraform state version
     * Retrieves a specified Terraform state version.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param serial Version number of the Terraform state.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdTerraformStateNameVersionsSerialRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull Integer serial) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdTerraformStateNameVersionsSerial", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdTerraformStateNameVersionsSerial", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'serial' is set
        if (serial == null) {
            throw new RestClientResponseException("Missing the required parameter 'serial' when calling getApiV4ProjectsIdTerraformStateNameVersionsSerial", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);
        pathParams.put("serial", serial);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/terraform/state/{name}/versions/{serial}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Terraform state version
     * Retrieves a specified Terraform state version.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param serial Version number of the Terraform state.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdTerraformStateNameVersionsSerial(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull Integer serial) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTerraformStateNameVersionsSerialRequestCreation(id, name, serial).body(localVarReturnType);
    }

    /**
     * Retrieve a Terraform state version
     * Retrieves a specified Terraform state version.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param serial Version number of the Terraform state.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdTerraformStateNameVersionsSerialWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull Integer serial) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTerraformStateNameVersionsSerialRequestCreation(id, name, serial).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Terraform state version
     * Retrieves a specified Terraform state version.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param serial Version number of the Terraform state.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdTerraformStateNameVersionsSerialWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull Integer serial) throws RestClientResponseException {
        return getApiV4ProjectsIdTerraformStateNameVersionsSerialRequestCreation(id, name, serial);
    }

    /**
     * Create or update a Terraform state
     * Creates or updates a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No data provided
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Validation failure
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdTerraformStateNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdTerraformStateName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4ProjectsIdTerraformStateName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsIdTerraformStateName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/terraform/state/{name}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or update a Terraform state
     * Creates or updates a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No data provided
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Validation failure
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdTerraformStateName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdTerraformStateNameRequestCreation(id, name, _file).body(localVarReturnType);
    }

    /**
     * Create or update a Terraform state
     * Creates or updates a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No data provided
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Validation failure
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdTerraformStateNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdTerraformStateNameRequestCreation(id, name, _file).toEntity(localVarReturnType);
    }

    /**
     * Create or update a Terraform state
     * Creates or updates a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>204</b> - No data provided
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Validation failure
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdTerraformStateNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return postApiV4ProjectsIdTerraformStateNameRequestCreation(id, name, _file);
    }

    /**
     * Authorize Terraform state upload
     * Authorizes uploading a Terraform state for a specified project. This feature was introduced in GitLab 18.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdTerraformStateNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdTerraformStateNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4ProjectsIdTerraformStateNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/terraform/state/{name}/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize Terraform state upload
     * Authorizes uploading a Terraform state for a specified project. This feature was introduced in GitLab 18.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdTerraformStateNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdTerraformStateNameAuthorizeRequestCreation(id, name).body(localVarReturnType);
    }

    /**
     * Authorize Terraform state upload
     * Authorizes uploading a Terraform state for a specified project. This feature was introduced in GitLab 18.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdTerraformStateNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdTerraformStateNameAuthorizeRequestCreation(id, name).toEntity(localVarReturnType);
    }

    /**
     * Authorize Terraform state upload
     * Authorizes uploading a Terraform state for a specified project. This feature was introduced in GitLab 18.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdTerraformStateNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return postApiV4ProjectsIdTerraformStateNameAuthorizeRequestCreation(id, name);
    }

    /**
     * Lock a Terraform state
     * Locks a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param postApiV4ProjectsIdTerraformStateNameLockRequest The postApiV4ProjectsIdTerraformStateNameLockRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdTerraformStateNameLockRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTerraformStateNameLockRequest postApiV4ProjectsIdTerraformStateNameLockRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdTerraformStateNameLock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4ProjectsIdTerraformStateNameLock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdTerraformStateNameLockRequest' is set
        if (postApiV4ProjectsIdTerraformStateNameLockRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdTerraformStateNameLockRequest' when calling postApiV4ProjectsIdTerraformStateNameLock", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("name", name);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/terraform/state/{name}/lock", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdTerraformStateNameLockRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Lock a Terraform state
     * Locks a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param postApiV4ProjectsIdTerraformStateNameLockRequest The postApiV4ProjectsIdTerraformStateNameLockRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdTerraformStateNameLock(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTerraformStateNameLockRequest postApiV4ProjectsIdTerraformStateNameLockRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdTerraformStateNameLockRequestCreation(id, name, postApiV4ProjectsIdTerraformStateNameLockRequest).body(localVarReturnType);
    }

    /**
     * Lock a Terraform state
     * Locks a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param postApiV4ProjectsIdTerraformStateNameLockRequest The postApiV4ProjectsIdTerraformStateNameLockRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdTerraformStateNameLockWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTerraformStateNameLockRequest postApiV4ProjectsIdTerraformStateNameLockRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdTerraformStateNameLockRequestCreation(id, name, postApiV4ProjectsIdTerraformStateNameLockRequest).toEntity(localVarReturnType);
    }

    /**
     * Lock a Terraform state
     * Locks a Terraform state for a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Validation failure
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param name Name of the Terraform state.
     * @param postApiV4ProjectsIdTerraformStateNameLockRequest The postApiV4ProjectsIdTerraformStateNameLockRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdTerraformStateNameLockWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull PostApiV4ProjectsIdTerraformStateNameLockRequest postApiV4ProjectsIdTerraformStateNameLockRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdTerraformStateNameLockRequestCreation(id, name, postApiV4ProjectsIdTerraformStateNameLockRequest);
    }

    /**
     * Upload Terraform Module package file
     * Uploads a module for a specified project. This feature was introduced in GitLab 13.11
     * <p><b>201</b> - Created
     * <p><b>400</b> - Invalid file
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);
        pathParams.put("module_version", moduleVersion);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/terraform/modules/{module_name}/{module_system}/{module_version}/file", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload Terraform Module package file
     * Uploads a module for a specified project. This feature was introduced in GitLab 13.11
     * <p><b>201</b> - Created
     * <p><b>400</b> - Invalid file
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFile(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileRequestCreation(id, moduleName, moduleSystem, moduleVersion, _file).body(localVarReturnType);
    }

    /**
     * Upload Terraform Module package file
     * Uploads a module for a specified project. This feature was introduced in GitLab 13.11
     * <p><b>201</b> - Created
     * <p><b>400</b> - Invalid file
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileRequestCreation(id, moduleName, moduleSystem, moduleVersion, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload Terraform Module package file
     * Uploads a module for a specified project. This feature was introduced in GitLab 13.11
     * <p><b>201</b> - Created
     * <p><b>400</b> - Invalid file
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileRequestCreation(id, moduleName, moduleSystem, moduleVersion, _file);
    }

    /**
     * Workhorse authorize Terraform Module package file
     * This feature was introduced in GitLab 13.11
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleName' is set
        if (moduleName == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleName' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleSystem' is set
        if (moduleSystem == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleSystem' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'moduleVersion' is set
        if (moduleVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'moduleVersion' when calling putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("module_name", moduleName);
        pathParams.put("module_system", moduleSystem);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/terraform/modules/{module_name}/{module_system}/{module_version}/file/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize Terraform Module package file
     * This feature was introduced in GitLab 13.11
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorizeRequestCreation(id, moduleName, moduleSystem, moduleVersion).body(localVarReturnType);
    }

    /**
     * Workhorse authorize Terraform Module package file
     * This feature was introduced in GitLab 13.11
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorizeRequestCreation(id, moduleName, moduleSystem, moduleVersion).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize Terraform Module package file
     * This feature was introduced in GitLab 13.11
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param moduleName Module name.
     * @param moduleSystem Name of the module system, or [provider](https://developer.hashicorp.com/terraform/registry/providers).
     * @param moduleVersion Module version
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String moduleName, @jakarta.annotation.Nonnull String moduleSystem, @jakarta.annotation.Nonnull String moduleVersion) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesTerraformModulesModuleNameModuleSystemModuleVersionFileAuthorizeRequestCreation(id, moduleName, moduleSystem, moduleVersion);
    }
}
