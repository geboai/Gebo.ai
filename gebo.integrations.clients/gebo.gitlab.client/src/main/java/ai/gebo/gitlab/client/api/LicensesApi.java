package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBasicSuccess;
import ai.gebo.gitlab.client.model.APIEntitiesGitlabLicense;
import ai.gebo.gitlab.client.model.APIEntitiesGitlabLicenseWithActiveUsers;
import ai.gebo.gitlab.client.model.APIEntitiesManagedLicense;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest;
import ai.gebo.gitlab.client.model.PostApiV4LicenseRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdManagedLicensesRequest;

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
public class LicensesApi {
    private ApiClient apiClient;

    public LicensesApi() {
        this(new ApiClient());
    }

    public LicensesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a license
     * Deletes a specified license.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4LicenseIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4LicenseId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/license/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a license
     * Deletes a specified license.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4LicenseId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4LicenseIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a license
     * Deletes a specified license.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4LicenseIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4LicenseIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a license
     * Deletes a specified license.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4LicenseIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4LicenseIdRequestCreation(id);
    }

    /**
     * Delete a software license policy
     * Deletes a specified software license policy from a project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdManagedLicensesManagedLicenseId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'managedLicenseId' is set
        if (managedLicenseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'managedLicenseId' when calling deleteApiV4ProjectsIdManagedLicensesManagedLicenseId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("managed_license_id", managedLicenseId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/managed_licenses/{managed_license_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a software license policy
     * Deletes a specified software license policy from a project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdManagedLicensesManagedLicenseId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId).body(localVarReturnType);
    }

    /**
     * Delete a software license policy
     * Deletes a specified software license policy from a project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdManagedLicensesManagedLicenseIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId).toEntity(localVarReturnType);
    }

    /**
     * Delete a software license policy
     * Deletes a specified software license policy from a project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdManagedLicensesManagedLicenseIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId);
    }

    /**
     * Retrieve license information
     * Retrieves information about the current license.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesGitlabLicenseWithActiveUsers
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4LicenseRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/license", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve license information
     * Retrieves information about the current license.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesGitlabLicenseWithActiveUsers
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGitlabLicenseWithActiveUsers getApiV4License() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LicenseRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve license information
     * Retrieves information about the current license.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;APIEntitiesGitlabLicenseWithActiveUsers&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGitlabLicenseWithActiveUsers> getApiV4LicenseWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LicenseRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve license information
     * Retrieves information about the current license.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4LicenseWithResponseSpec() throws RestClientResponseException {
        return getApiV4LicenseRequestCreation();
    }

    /**
     * Retrieve a license
     * Retrieves information about a specified license.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return APIEntitiesGitlabLicenseWithActiveUsers
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4LicenseIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4LicenseId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/license/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a license
     * Retrieves information about a specified license.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return APIEntitiesGitlabLicenseWithActiveUsers
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGitlabLicenseWithActiveUsers getApiV4LicenseId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LicenseIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a license
     * Retrieves information about a specified license.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return ResponseEntity&lt;APIEntitiesGitlabLicenseWithActiveUsers&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGitlabLicenseWithActiveUsers> getApiV4LicenseIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LicenseIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a license
     * Retrieves information about a specified license.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4LicenseIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4LicenseIdRequestCreation(id);
    }

    /**
     * Download license usage data
     * Downloads usage data for the currently active license in CSV format.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4LicenseUsageExportRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/license/usage_export", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download license usage data
     * Downloads usage data for the currently active license in CSV format.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4LicenseUsageExport() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4LicenseUsageExportRequestCreation().body(localVarReturnType);
    }

    /**
     * Download license usage data
     * Downloads usage data for the currently active license in CSV format.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4LicenseUsageExportWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LicenseUsageExportRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Download license usage data
     * Downloads usage data for the currently active license in CSV format.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4LicenseUsageExportWithResponseSpec() throws RestClientResponseException {
        return getApiV4LicenseUsageExportRequestCreation();
    }

    /**
     * List all licenses
     * Lists all information about all licenses. &#x60;overage&#x60; is the difference between the number of billable users and the licensed number of users. For an expired license, it uses the historical maximum billable user count (&#x60;historical_max&#x60;). Otherwise, it uses the current billable user count.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesGitlabLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4LicensesRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesGitlabLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/licenses", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all licenses
     * Lists all information about all licenses. &#x60;overage&#x60; is the difference between the number of billable users and the licensed number of users. For an expired license, it uses the historical maximum billable user count (&#x60;historical_max&#x60;). Otherwise, it uses the current billable user count.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesGitlabLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGitlabLicense> getApiV4Licenses() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGitlabLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LicensesRequestCreation().body(localVarReturnType);
    }

    /**
     * List all licenses
     * Lists all information about all licenses. &#x60;overage&#x60; is the difference between the number of billable users and the licensed number of users. For an expired license, it uses the historical maximum billable user count (&#x60;historical_max&#x60;). Otherwise, it uses the current billable user count.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesGitlabLicense&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGitlabLicense>> getApiV4LicensesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGitlabLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LicensesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all licenses
     * Lists all information about all licenses. &#x60;overage&#x60; is the difference between the number of billable users and the licensed number of users. For an expired license, it uses the historical maximum billable user count (&#x60;historical_max&#x60;). Otherwise, it uses the current billable user count.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4LicensesWithResponseSpec() throws RestClientResponseException {
        return getApiV4LicensesRequestCreation();
    }

    /**
     * List all software license policies
     * Lists all software license policies for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesManagedLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdManagedLicensesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdManagedLicenses", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesManagedLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/managed_licenses", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all software license policies
     * Lists all software license policies for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesManagedLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesManagedLicense> getApiV4ProjectsIdManagedLicenses(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesManagedLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdManagedLicensesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all software license policies
     * Lists all software license policies for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesManagedLicense&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesManagedLicense>> getApiV4ProjectsIdManagedLicensesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesManagedLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdManagedLicensesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all software license policies
     * Lists all software license policies for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdManagedLicensesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdManagedLicensesRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a software license policy
     * Retrieves a specified software license policy from a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @return List&lt;APIEntitiesManagedLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdManagedLicensesManagedLicenseId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'managedLicenseId' is set
        if (managedLicenseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'managedLicenseId' when calling getApiV4ProjectsIdManagedLicensesManagedLicenseId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("managed_license_id", managedLicenseId);

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

        ParameterizedTypeReference<List<APIEntitiesManagedLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/managed_licenses/{managed_license_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a software license policy
     * Retrieves a specified software license policy from a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @return List&lt;APIEntitiesManagedLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesManagedLicense> getApiV4ProjectsIdManagedLicensesManagedLicenseId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesManagedLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId).body(localVarReturnType);
    }

    /**
     * Retrieve a software license policy
     * Retrieves a specified software license policy from a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @return ResponseEntity&lt;List&lt;APIEntitiesManagedLicense&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesManagedLicense>> getApiV4ProjectsIdManagedLicensesManagedLicenseIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesManagedLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a software license policy
     * Retrieves a specified software license policy from a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdManagedLicensesManagedLicenseIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId) throws RestClientResponseException {
        return getApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId);
    }

    /**
     * Update a software license policy
     * Updates a specified software license policy for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @param patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest The patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest parameter
     * @return APIEntitiesManagedLicense
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId, @jakarta.annotation.Nullable PatchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdManagedLicensesManagedLicenseId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'managedLicenseId' is set
        if (managedLicenseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'managedLicenseId' when calling patchApiV4ProjectsIdManagedLicensesManagedLicenseId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("managed_license_id", managedLicenseId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesManagedLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/managed_licenses/{managed_license_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a software license policy
     * Updates a specified software license policy for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @param patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest The patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest parameter
     * @return APIEntitiesManagedLicense
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesManagedLicense patchApiV4ProjectsIdManagedLicensesManagedLicenseId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId, @jakarta.annotation.Nullable PatchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesManagedLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId, patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest).body(localVarReturnType);
    }

    /**
     * Update a software license policy
     * Updates a specified software license policy for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @param patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest The patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesManagedLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesManagedLicense> patchApiV4ProjectsIdManagedLicensesManagedLicenseIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId, @jakarta.annotation.Nullable PatchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesManagedLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId, patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a software license policy
     * Updates a specified software license policy for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param managedLicenseId ID of the managed license.
     * @param patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest The patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdManagedLicensesManagedLicenseIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String managedLicenseId, @jakarta.annotation.Nullable PatchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequestCreation(id, managedLicenseId, patchApiV4ProjectsIdManagedLicensesManagedLicenseIdRequest);
    }

    /**
     * Create a license
     * Creates a license.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * @param postApiV4LicenseRequest The postApiV4LicenseRequest parameter
     * @return APIEntitiesGitlabLicenseWithActiveUsers
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4LicenseRequestCreation(@jakarta.annotation.Nonnull PostApiV4LicenseRequest postApiV4LicenseRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4LicenseRequest' is set
        if (postApiV4LicenseRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4LicenseRequest' when calling postApiV4License", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/license", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4LicenseRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a license
     * Creates a license.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * @param postApiV4LicenseRequest The postApiV4LicenseRequest parameter
     * @return APIEntitiesGitlabLicenseWithActiveUsers
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGitlabLicenseWithActiveUsers postApiV4License(@jakarta.annotation.Nonnull PostApiV4LicenseRequest postApiV4LicenseRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4LicenseRequestCreation(postApiV4LicenseRequest).body(localVarReturnType);
    }

    /**
     * Create a license
     * Creates a license.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * @param postApiV4LicenseRequest The postApiV4LicenseRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGitlabLicenseWithActiveUsers&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGitlabLicenseWithActiveUsers> postApiV4LicenseWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4LicenseRequest postApiV4LicenseRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGitlabLicenseWithActiveUsers> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4LicenseRequestCreation(postApiV4LicenseRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a license
     * Creates a license.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * @param postApiV4LicenseRequest The postApiV4LicenseRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4LicenseWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4LicenseRequest postApiV4LicenseRequest) throws RestClientResponseException {
        return postApiV4LicenseRequestCreation(postApiV4LicenseRequest);
    }

    /**
     * Create a software license policy
     * Creates a software license policy for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdManagedLicensesRequest The postApiV4ProjectsIdManagedLicensesRequest parameter
     * @return APIEntitiesManagedLicense
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdManagedLicensesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdManagedLicensesRequest postApiV4ProjectsIdManagedLicensesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdManagedLicenses", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdManagedLicensesRequest' is set
        if (postApiV4ProjectsIdManagedLicensesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdManagedLicensesRequest' when calling postApiV4ProjectsIdManagedLicenses", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesManagedLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/managed_licenses", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdManagedLicensesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a software license policy
     * Creates a software license policy for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdManagedLicensesRequest The postApiV4ProjectsIdManagedLicensesRequest parameter
     * @return APIEntitiesManagedLicense
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesManagedLicense postApiV4ProjectsIdManagedLicenses(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdManagedLicensesRequest postApiV4ProjectsIdManagedLicensesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesManagedLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdManagedLicensesRequestCreation(id, postApiV4ProjectsIdManagedLicensesRequest).body(localVarReturnType);
    }

    /**
     * Create a software license policy
     * Creates a software license policy for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdManagedLicensesRequest The postApiV4ProjectsIdManagedLicensesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesManagedLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesManagedLicense> postApiV4ProjectsIdManagedLicensesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdManagedLicensesRequest postApiV4ProjectsIdManagedLicensesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesManagedLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdManagedLicensesRequestCreation(id, postApiV4ProjectsIdManagedLicensesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a software license policy
     * Creates a software license policy for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdManagedLicensesRequest The postApiV4ProjectsIdManagedLicensesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdManagedLicensesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdManagedLicensesRequest postApiV4ProjectsIdManagedLicensesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdManagedLicensesRequestCreation(id, postApiV4ProjectsIdManagedLicensesRequest);
    }

    /**
     * Trigger recalculation of billable users
     * Triggers recalculation of billable users for a specified license.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4LicenseIdRefreshBillableUsersRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4LicenseIdRefreshBillableUsers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/license/{id}/refresh_billable_users", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Trigger recalculation of billable users
     * Triggers recalculation of billable users for a specified license.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicSuccess putApiV4LicenseIdRefreshBillableUsers(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4LicenseIdRefreshBillableUsersRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Trigger recalculation of billable users
     * Triggers recalculation of billable users for a specified license.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return ResponseEntity&lt;APIEntitiesBasicSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicSuccess> putApiV4LicenseIdRefreshBillableUsersWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4LicenseIdRefreshBillableUsersRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Trigger recalculation of billable users
     * Triggers recalculation of billable users for a specified license.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param id ID of the GitLab license.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4LicenseIdRefreshBillableUsersWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return putApiV4LicenseIdRefreshBillableUsersRequestCreation(id);
    }
}
