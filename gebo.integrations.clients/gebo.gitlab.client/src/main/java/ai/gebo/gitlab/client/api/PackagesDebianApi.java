package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesPackagesDebianDistribution;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdDashDebianDistributionsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdDebianDistributionsRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdDashDebianDistributionsCodenameRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdDebianDistributionsCodenameRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest;

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
public class PackagesDebianApi {
    private ApiClient apiClient;

    public PackagesDebianApi() {
        this(new ApiClient());
    }

    public PackagesDebianApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a Debian group distribution
     * Deletes a specified Debian group distribution for a group.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdDashDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'codename' is set
        if (codename == null) {
            throw new RestClientResponseException("Missing the required parameter 'codename' when calling deleteApiV4GroupsIdDashDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("codename", codename);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "suite", suite));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "origin", origin));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "label", label));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "description", description));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "valid_time_duration_seconds", validTimeDurationSeconds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "components", components));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "architectures", architectures));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/debian_distributions/{codename}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a Debian group distribution
     * Deletes a specified Debian group distribution for a group.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdDashDebianDistributionsCodename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures).body(localVarReturnType);
    }

    /**
     * Delete a Debian group distribution
     * Deletes a specified Debian group distribution for a group.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdDashDebianDistributionsCodenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures).toEntity(localVarReturnType);
    }

    /**
     * Delete a Debian group distribution
     * Deletes a specified Debian group distribution for a group.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdDashDebianDistributionsCodenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        return deleteApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures);
    }

    /**
     * Delete a Debian project distribution
     * Deletes a specified Debian project distribution for a project.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'codename' is set
        if (codename == null) {
            throw new RestClientResponseException("Missing the required parameter 'codename' when calling deleteApiV4ProjectsIdDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("codename", codename);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "suite", suite));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "origin", origin));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "label", label));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "description", description));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "valid_time_duration_seconds", validTimeDurationSeconds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "components", components));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "architectures", architectures));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/debian_distributions/{codename}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a Debian project distribution
     * Deletes a specified Debian project distribution for a project.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdDebianDistributionsCodename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures).body(localVarReturnType);
    }

    /**
     * Delete a Debian project distribution
     * Deletes a specified Debian project distribution for a project.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdDebianDistributionsCodenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures).toEntity(localVarReturnType);
    }

    /**
     * Delete a Debian project distribution
     * Deletes a specified Debian project distribution for a project.
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdDebianDistributionsCodenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        return deleteApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures);
    }

    /**
     * List all Debian group distributions
     * Lists all Debian distributions for a specified group. &#x60;suite&#x60; filters the results by suite.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param codename Codename of the Debian distribution. Filters the results by codename.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return List&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashDebianDistributionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashDebianDistributions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "codename", codename));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "suite", suite));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "origin", origin));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "label", label));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "description", description));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "valid_time_duration_seconds", validTimeDurationSeconds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "components", components));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "architectures", architectures));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPackagesDebianDistribution>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/debian_distributions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Debian group distributions
     * Lists all Debian distributions for a specified group. &#x60;suite&#x60; filters the results by suite.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param codename Codename of the Debian distribution. Filters the results by codename.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return List&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPackagesDebianDistribution> getApiV4GroupsIdDashDebianDistributions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackagesDebianDistribution>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashDebianDistributionsRequestCreation(id, page, perPage, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures).body(localVarReturnType);
    }

    /**
     * List all Debian group distributions
     * Lists all Debian distributions for a specified group. &#x60;suite&#x60; filters the results by suite.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param codename Codename of the Debian distribution. Filters the results by codename.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPackagesDebianDistribution&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPackagesDebianDistribution>> getApiV4GroupsIdDashDebianDistributionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackagesDebianDistribution>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashDebianDistributionsRequestCreation(id, page, perPage, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures).toEntity(localVarReturnType);
    }

    /**
     * List all Debian group distributions
     * Lists all Debian distributions for a specified group. &#x60;suite&#x60; filters the results by suite.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param codename Codename of the Debian distribution. Filters the results by codename.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashDebianDistributionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        return getApiV4GroupsIdDashDebianDistributionsRequestCreation(id, page, perPage, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures);
    }

    /**
     * Retrieve a Debian group distribution
     * Retrieves a specified Debian group distribution for a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'codename' is set
        if (codename == null) {
            throw new RestClientResponseException("Missing the required parameter 'codename' when calling getApiV4GroupsIdDashDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("codename", codename);

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

        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/debian_distributions/{codename}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Debian group distribution
     * Retrieves a specified Debian group distribution for a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesDebianDistribution getApiV4GroupsIdDashDebianDistributionsCodename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename).body(localVarReturnType);
    }

    /**
     * Retrieve a Debian group distribution
     * Retrieves a specified Debian group distribution for a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @return ResponseEntity&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesDebianDistribution> getApiV4GroupsIdDashDebianDistributionsCodenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Debian group distribution
     * Retrieves a specified Debian group distribution for a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashDebianDistributionsCodenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        return getApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename);
    }

    /**
     * Retrieve a Debian group distribution key
     * Retrieves a specified Debian group distribution key for a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashDebianDistributionsCodenameKeyAscRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashDebianDistributionsCodenameKeyAsc", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'codename' is set
        if (codename == null) {
            throw new RestClientResponseException("Missing the required parameter 'codename' when calling getApiV4GroupsIdDashDebianDistributionsCodenameKeyAsc", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("codename", codename);

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

        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/debian_distributions/{codename}/key.asc", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Debian group distribution key
     * Retrieves a specified Debian group distribution key for a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesDebianDistribution getApiV4GroupsIdDashDebianDistributionsCodenameKeyAsc(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashDebianDistributionsCodenameKeyAscRequestCreation(id, codename).body(localVarReturnType);
    }

    /**
     * Retrieve a Debian group distribution key
     * Retrieves a specified Debian group distribution key for a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @return ResponseEntity&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesDebianDistribution> getApiV4GroupsIdDashDebianDistributionsCodenameKeyAscWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashDebianDistributionsCodenameKeyAscRequestCreation(id, codename).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Debian group distribution key
     * Retrieves a specified Debian group distribution key for a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashDebianDistributionsCodenameKeyAscWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        return getApiV4GroupsIdDashDebianDistributionsCodenameKeyAscRequestCreation(id, codename);
    }

    /**
     * The binary files index by hash
     * Downloads a specified packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256RequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'architecture' is set
        if (architecture == null) {
            throw new RestClientResponseException("Missing the required parameter 'architecture' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileSha256' is set
        if (fileSha256 == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileSha256' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("architecture", architecture);
        pathParams.put("file_sha256", fileSha256);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/{component}/binary-{architecture}/by-hash/SHA256/{file_sha256}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The binary files index by hash
     * Downloads a specified packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256).body(localVarReturnType);
    }

    /**
     * The binary files index by hash
     * Downloads a specified packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256WithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256).toEntity(localVarReturnType);
    }

    /**
     * The binary files index by hash
     * Downloads a specified packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256WithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256);
    }

    /**
     * The binary files index
     * Downloads a specified packages index. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'architecture' is set
        if (architecture == null) {
            throw new RestClientResponseException("Missing the required parameter 'architecture' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("architecture", architecture);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/{component}/binary-{architecture}/Packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The binary files index
     * Downloads a specified packages index. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture).body(localVarReturnType);
    }

    /**
     * The binary files index
     * Downloads a specified packages index. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture).toEntity(localVarReturnType);
    }

    /**
     * The binary files index
     * Downloads a specified packages index. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture);
    }

    /**
     * The installer (udeb) binary files index by hash
     * Downloads a specified Debian Installer packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256RequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'architecture' is set
        if (architecture == null) {
            throw new RestClientResponseException("Missing the required parameter 'architecture' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileSha256' is set
        if (fileSha256 == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileSha256' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("architecture", architecture);
        pathParams.put("file_sha256", fileSha256);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/{component}/debian-installer/binary-{architecture}/by-hash/SHA256/{file_sha256}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index by hash
     * Downloads a specified Debian Installer packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256).body(localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index by hash
     * Downloads a specified Debian Installer packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256WithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256).toEntity(localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index by hash
     * Downloads a specified Debian Installer packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256WithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256);
    }

    /**
     * The installer (udeb) binary files index
     * Downloads a specified Debian Installer packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'architecture' is set
        if (architecture == null) {
            throw new RestClientResponseException("Missing the required parameter 'architecture' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("architecture", architecture);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/{component}/debian-installer/binary-{architecture}/Packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index
     * Downloads a specified Debian Installer packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture).body(localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index
     * Downloads a specified Debian Installer packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture).toEntity(localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index
     * Downloads a specified Debian Installer packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture);
    }

    /**
     * The source files index by hash
     * Downloads a specified source packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256RequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileSha256' is set
        if (fileSha256 == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileSha256' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("file_sha256", fileSha256);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/{component}/source/by-hash/SHA256/{file_sha256}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The source files index by hash
     * Downloads a specified source packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256RequestCreation(id, distribution, component, fileSha256).body(localVarReturnType);
    }

    /**
     * The source files index by hash
     * Downloads a specified source packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256WithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256RequestCreation(id, distribution, component, fileSha256).toEntity(localVarReturnType);
    }

    /**
     * The source files index by hash
     * Downloads a specified source packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param fileSha256 The SHA256 checksum of the index file
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256WithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256RequestCreation(id, distribution, component, fileSha256);
    }

    /**
     * The source files index
     * Downloads a specified source packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSourcesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/{component}/source/Sources", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The source files index
     * Downloads a specified source packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSources(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSourcesRequestCreation(id, distribution, component).body(localVarReturnType);
    }

    /**
     * The source files index
     * Downloads a specified source packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSourcesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSourcesRequestCreation(id, distribution, component).toEntity(localVarReturnType);
    }

    /**
     * The source files index
     * Downloads a specified source packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSourcesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionComponentSourceSourcesRequestCreation(id, distribution, component);
    }

    /**
     * The signed Release file
     * Downloads a specified signed Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionInreleaseRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionInrelease", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionInrelease", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/InRelease", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The signed Release file
     * Downloads a specified signed Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionInrelease(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionInreleaseRequestCreation(id, distribution).body(localVarReturnType);
    }

    /**
     * The signed Release file
     * Downloads a specified signed Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionInreleaseWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionInreleaseRequestCreation(id, distribution).toEntity(localVarReturnType);
    }

    /**
     * The signed Release file
     * Downloads a specified signed Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionInreleaseWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionInreleaseRequestCreation(id, distribution);
    }

    /**
     * The unsigned Release file
     * Downloads a specified Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionRelease", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionRelease", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/Release", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The unsigned Release file
     * Downloads a specified Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionRelease(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseRequestCreation(id, distribution).body(localVarReturnType);
    }

    /**
     * The unsigned Release file
     * Downloads a specified Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseRequestCreation(id, distribution).toEntity(localVarReturnType);
    }

    /**
     * The unsigned Release file
     * Downloads a specified Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseRequestCreation(id, distribution);
    }

    /**
     * The Release file signature
     * Downloads a specified Debian release file signature. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpgRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpg", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpg", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/dists/{distribution}/Release.gpg", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The Release file signature
     * Downloads a specified Debian release file signature. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpg(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpgRequestCreation(id, distribution).body(localVarReturnType);
    }

    /**
     * The Release file signature
     * Downloads a specified Debian release file signature. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpgWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpgRequestCreation(id, distribution).toEntity(localVarReturnType);
    }

    /**
     * The Release file signature
     * Downloads a specified Debian release file signature. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param distribution The Debian Codename or Suite
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpgWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianDistsDistributionReleaseGpgRequestCreation(id, distribution);
    }

    /**
     * Download Debian package
     * This feature was introduced in GitLab 14.2
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID of the project.
     * @param distribution Codename or suite of the Debian distribution.
     * @param letter Debian classification of the package, either first-letter or lib-first-letter.
     * @param packageName Name of the source package.
     * @param packageVersion Version of the source package.
     * @param fileName Name of the package file.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer projectId, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String letter, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'letter' is set
        if (letter == null) {
            throw new RestClientResponseException("Missing the required parameter 'letter' when calling getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("project_id", projectId);
        pathParams.put("distribution", distribution);
        pathParams.put("letter", letter);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("file_name", fileName);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/debian/pool/{distribution}/{project_id}/{letter}/{package_name}/{package_version}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download Debian package
     * This feature was introduced in GitLab 14.2
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID of the project.
     * @param distribution Codename or suite of the Debian distribution.
     * @param letter Debian classification of the package, either first-letter or lib-first-letter.
     * @param packageName Name of the source package.
     * @param packageVersion Version of the source package.
     * @param fileName Name of the package file.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer projectId, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String letter, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileNameRequestCreation(id, projectId, distribution, letter, packageName, packageVersion, fileName).body(localVarReturnType);
    }

    /**
     * Download Debian package
     * This feature was introduced in GitLab 14.2
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID of the project.
     * @param distribution Codename or suite of the Debian distribution.
     * @param letter Debian classification of the package, either first-letter or lib-first-letter.
     * @param packageName Name of the source package.
     * @param packageVersion Version of the source package.
     * @param fileName Name of the package file.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer projectId, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String letter, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileNameRequestCreation(id, projectId, distribution, letter, packageName, packageVersion, fileName).toEntity(localVarReturnType);
    }

    /**
     * Download Debian package
     * This feature was introduced in GitLab 14.2
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param projectId ID of the project.
     * @param distribution Codename or suite of the Debian distribution.
     * @param letter Debian classification of the package, either first-letter or lib-first-letter.
     * @param packageName Name of the source package.
     * @param packageVersion Version of the source package.
     * @param fileName Name of the package file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer projectId, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String letter, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesDebianPoolDistributionProjectIdLetterPackageNamePackageVersionFileNameRequestCreation(id, projectId, distribution, letter, packageName, packageVersion, fileName);
    }

    /**
     * List all Debian project distributions
     * Lists all Debian distributions for a specified project. &#x60;suite&#x60; filters the results by suite.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param codename Codename of the Debian distribution. Filters the results by codename.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return List&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDebianDistributionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDebianDistributions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "codename", codename));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "suite", suite));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "origin", origin));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "label", label));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "description", description));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "valid_time_duration_seconds", validTimeDurationSeconds));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "components", components));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "architectures", architectures));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPackagesDebianDistribution>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/debian_distributions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Debian project distributions
     * Lists all Debian distributions for a specified project. &#x60;suite&#x60; filters the results by suite.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param codename Codename of the Debian distribution. Filters the results by codename.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return List&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPackagesDebianDistribution> getApiV4ProjectsIdDebianDistributions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackagesDebianDistribution>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDebianDistributionsRequestCreation(id, page, perPage, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures).body(localVarReturnType);
    }

    /**
     * List all Debian project distributions
     * Lists all Debian distributions for a specified project. &#x60;suite&#x60; filters the results by suite.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param codename Codename of the Debian distribution. Filters the results by codename.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPackagesDebianDistribution&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPackagesDebianDistribution>> getApiV4ProjectsIdDebianDistributionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPackagesDebianDistribution>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDebianDistributionsRequestCreation(id, page, perPage, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures).toEntity(localVarReturnType);
    }

    /**
     * List all Debian project distributions
     * Lists all Debian distributions for a specified project. &#x60;suite&#x60; filters the results by suite.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param codename Codename of the Debian distribution. Filters the results by codename.
     * @param suite Suite of the Debian distribution.
     * @param origin Origin of the Debian distribution.
     * @param label Label of the Debian distribution.
     * @param version Version of the Debian distribution.
     * @param description Description of the Debian distribution.
     * @param validTimeDurationSeconds Duration, in seconds, before the Release file is considered expired by the client.
     * @param components List of components of the Debian distribution.
     * @param architectures List of architectures of the Debian distribution.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDebianDistributionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String codename, @jakarta.annotation.Nullable String suite, @jakarta.annotation.Nullable String origin, @jakarta.annotation.Nullable String label, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable Integer validTimeDurationSeconds, @jakarta.annotation.Nullable List<String> components, @jakarta.annotation.Nullable List<String> architectures) throws RestClientResponseException {
        return getApiV4ProjectsIdDebianDistributionsRequestCreation(id, page, perPage, codename, suite, origin, label, version, description, validTimeDurationSeconds, components, architectures);
    }

    /**
     * Retrieve a Debian project distribution
     * Retrieves a specified Debian project distribution for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'codename' is set
        if (codename == null) {
            throw new RestClientResponseException("Missing the required parameter 'codename' when calling getApiV4ProjectsIdDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("codename", codename);

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

        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/debian_distributions/{codename}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Debian project distribution
     * Retrieves a specified Debian project distribution for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesDebianDistribution getApiV4ProjectsIdDebianDistributionsCodename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename).body(localVarReturnType);
    }

    /**
     * Retrieve a Debian project distribution
     * Retrieves a specified Debian project distribution for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @return ResponseEntity&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesDebianDistribution> getApiV4ProjectsIdDebianDistributionsCodenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Debian project distribution
     * Retrieves a specified Debian project distribution for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDebianDistributionsCodenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        return getApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename);
    }

    /**
     * Retrieve a Debian project distribution key
     * Retrieves a specified Debian project distribution key for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDebianDistributionsCodenameKeyAscRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDebianDistributionsCodenameKeyAsc", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'codename' is set
        if (codename == null) {
            throw new RestClientResponseException("Missing the required parameter 'codename' when calling getApiV4ProjectsIdDebianDistributionsCodenameKeyAsc", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("codename", codename);

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

        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/debian_distributions/{codename}/key.asc", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Debian project distribution key
     * Retrieves a specified Debian project distribution key for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesDebianDistribution getApiV4ProjectsIdDebianDistributionsCodenameKeyAsc(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDebianDistributionsCodenameKeyAscRequestCreation(id, codename).body(localVarReturnType);
    }

    /**
     * Retrieve a Debian project distribution key
     * Retrieves a specified Debian project distribution key for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @return ResponseEntity&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesDebianDistribution> getApiV4ProjectsIdDebianDistributionsCodenameKeyAscWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDebianDistributionsCodenameKeyAscRequestCreation(id, codename).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Debian project distribution key
     * Retrieves a specified Debian project distribution key for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDebianDistributionsCodenameKeyAscWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename) throws RestClientResponseException {
        return getApiV4ProjectsIdDebianDistributionsCodenameKeyAscRequestCreation(id, codename);
    }

    /**
     * The binary files index by hash
     * Downloads a specified packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256RequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'architecture' is set
        if (architecture == null) {
            throw new RestClientResponseException("Missing the required parameter 'architecture' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileSha256' is set
        if (fileSha256 == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileSha256' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("architecture", architecture);
        pathParams.put("file_sha256", fileSha256);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/{component}/binary-{architecture}/by-hash/SHA256/{file_sha256}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The binary files index by hash
     * Downloads a specified packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256).body(localVarReturnType);
    }

    /**
     * The binary files index by hash
     * Downloads a specified packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256WithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256).toEntity(localVarReturnType);
    }

    /**
     * The binary files index by hash
     * Downloads a specified packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256WithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256);
    }

    /**
     * The binary files index
     * Downloads a specified packages index. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'architecture' is set
        if (architecture == null) {
            throw new RestClientResponseException("Missing the required parameter 'architecture' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("architecture", architecture);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/{component}/binary-{architecture}/Packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The binary files index
     * Downloads a specified packages index. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture).body(localVarReturnType);
    }

    /**
     * The binary files index
     * Downloads a specified packages index. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture).toEntity(localVarReturnType);
    }

    /**
     * The binary files index
     * Downloads a specified packages index. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture);
    }

    /**
     * The installer (udeb) binary files index by hash
     * Downloads a specified Debian Installer packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256RequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'architecture' is set
        if (architecture == null) {
            throw new RestClientResponseException("Missing the required parameter 'architecture' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileSha256' is set
        if (fileSha256 == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileSha256' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("architecture", architecture);
        pathParams.put("file_sha256", fileSha256);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/{component}/debian-installer/binary-{architecture}/by-hash/SHA256/{file_sha256}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index by hash
     * Downloads a specified Debian Installer packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256).body(localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index by hash
     * Downloads a specified Debian Installer packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256WithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256).toEntity(localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index by hash
     * Downloads a specified Debian Installer packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @param fileSha256 The SHA256 checksum of the index file
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256WithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitectureByHashSha256FileSha256RequestCreation(id, distribution, component, architecture, fileSha256);
    }

    /**
     * The installer (udeb) binary files index
     * Downloads a specified Debian Installer packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'architecture' is set
        if (architecture == null) {
            throw new RestClientResponseException("Missing the required parameter 'architecture' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("architecture", architecture);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/{component}/debian-installer/binary-{architecture}/Packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index
     * Downloads a specified Debian Installer packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture).body(localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index
     * Downloads a specified Debian Installer packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture).toEntity(localVarReturnType);
    }

    /**
     * The installer (udeb) binary files index
     * Downloads a specified Debian Installer packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param architecture The Debian Architecture
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String architecture) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentDebianInstallerBinaryArchitecturePackagesRequestCreation(id, distribution, component, architecture);
    }

    /**
     * The source files index by hash
     * Downloads a specified source packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256RequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileSha256' is set
        if (fileSha256 == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileSha256' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);
        pathParams.put("file_sha256", fileSha256);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/{component}/source/by-hash/SHA256/{file_sha256}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The source files index by hash
     * Downloads a specified source packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256RequestCreation(id, distribution, component, fileSha256).body(localVarReturnType);
    }

    /**
     * The source files index by hash
     * Downloads a specified source packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param fileSha256 The SHA256 checksum of the index file
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256WithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256RequestCreation(id, distribution, component, fileSha256).toEntity(localVarReturnType);
    }

    /**
     * The source files index by hash
     * Downloads a specified source packages index by hash. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @param fileSha256 The SHA256 checksum of the index file
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256WithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component, @jakarta.annotation.Nonnull String fileSha256) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceByHashSha256FileSha256RequestCreation(id, distribution, component, fileSha256);
    }

    /**
     * The source files index
     * Downloads a specified source packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSourcesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSources", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("component", component);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/{component}/source/Sources", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The source files index
     * Downloads a specified source packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSources(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSourcesRequestCreation(id, distribution, component).body(localVarReturnType);
    }

    /**
     * The source files index
     * Downloads a specified source packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSourcesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSourcesRequestCreation(id, distribution, component).toEntity(localVarReturnType);
    }

    /**
     * The source files index
     * Downloads a specified source packages index. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param component The Debian Component
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSourcesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String component) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionComponentSourceSourcesRequestCreation(id, distribution, component);
    }

    /**
     * The signed Release file
     * Downloads a specified signed Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionInreleaseRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionInrelease", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionInrelease", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/InRelease", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The signed Release file
     * Downloads a specified signed Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionInrelease(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionInreleaseRequestCreation(id, distribution).body(localVarReturnType);
    }

    /**
     * The signed Release file
     * Downloads a specified signed Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionInreleaseWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionInreleaseRequestCreation(id, distribution).toEntity(localVarReturnType);
    }

    /**
     * The signed Release file
     * Downloads a specified signed Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionInreleaseWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionInreleaseRequestCreation(id, distribution);
    }

    /**
     * The unsigned Release file
     * Downloads a specified Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionRelease", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionRelease", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/Release", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The unsigned Release file
     * Downloads a specified Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionRelease(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseRequestCreation(id, distribution).body(localVarReturnType);
    }

    /**
     * The unsigned Release file
     * Downloads a specified Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseRequestCreation(id, distribution).toEntity(localVarReturnType);
    }

    /**
     * The unsigned Release file
     * Downloads a specified Debian distribution Release file. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseRequestCreation(id, distribution);
    }

    /**
     * The Release file signature
     * Downloads a specified Debian release file signature. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpgRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpg", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpg", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/dists/{distribution}/Release.gpg", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The Release file signature
     * Downloads a specified Debian release file signature. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpg(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpgRequestCreation(id, distribution).body(localVarReturnType);
    }

    /**
     * The Release file signature
     * Downloads a specified Debian release file signature. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpgWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpgRequestCreation(id, distribution).toEntity(localVarReturnType);
    }

    /**
     * The Release file signature
     * Downloads a specified Debian release file signature. This feature was introduced in GitLab 13.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpgWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianDistsDistributionReleaseGpgRequestCreation(id, distribution);
    }

    /**
     * Download a Debian package file
     * Downloads a specified Debian package file for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param letter The Debian Classification (first-letter or lib-first-letter)
     * @param packageName The Debian Source Package Name
     * @param packageVersion The Debian Source Package Version
     * @param fileName The Debian File Name
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String letter, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'distribution' is set
        if (distribution == null) {
            throw new RestClientResponseException("Missing the required parameter 'distribution' when calling getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'letter' is set
        if (letter == null) {
            throw new RestClientResponseException("Missing the required parameter 'letter' when calling getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageVersion' is set
        if (packageVersion == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageVersion' when calling getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("distribution", distribution);
        pathParams.put("letter", letter);
        pathParams.put("package_name", packageName);
        pathParams.put("package_version", packageVersion);
        pathParams.put("file_name", fileName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/pool/{distribution}/{letter}/{package_name}/{package_version}/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a Debian package file
     * Downloads a specified Debian package file for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param letter The Debian Classification (first-letter or lib-first-letter)
     * @param packageName The Debian Source Package Name
     * @param packageVersion The Debian Source Package Version
     * @param fileName The Debian File Name
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String letter, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileNameRequestCreation(id, distribution, letter, packageName, packageVersion, fileName).body(localVarReturnType);
    }

    /**
     * Download a Debian package file
     * Downloads a specified Debian package file for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param letter The Debian Classification (first-letter or lib-first-letter)
     * @param packageName The Debian Source Package Name
     * @param packageVersion The Debian Source Package Version
     * @param fileName The Debian File Name
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String letter, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileNameRequestCreation(id, distribution, letter, packageName, packageVersion, fileName).toEntity(localVarReturnType);
    }

    /**
     * Download a Debian package file
     * Downloads a specified Debian package file for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param distribution The Debian Codename or Suite
     * @param letter The Debian Classification (first-letter or lib-first-letter)
     * @param packageName The Debian Source Package Name
     * @param packageVersion The Debian Source Package Version
     * @param fileName The Debian File Name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String distribution, @jakarta.annotation.Nonnull String letter, @jakarta.annotation.Nonnull String packageName, @jakarta.annotation.Nonnull String packageVersion, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesDebianPoolDistributionLetterPackageNamePackageVersionFileNameRequestCreation(id, distribution, letter, packageName, packageVersion, fileName);
    }

    /**
     * Create a Debian group distribution
     * Creates a Debian group distribution for a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDashDebianDistributionsRequest The postApiV4GroupsIdDashDebianDistributionsRequest parameter
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDashDebianDistributionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashDebianDistributionsRequest postApiV4GroupsIdDashDebianDistributionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDashDebianDistributions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdDashDebianDistributionsRequest' is set
        if (postApiV4GroupsIdDashDebianDistributionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdDashDebianDistributionsRequest' when calling postApiV4GroupsIdDashDebianDistributions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/debian_distributions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdDashDebianDistributionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a Debian group distribution
     * Creates a Debian group distribution for a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDashDebianDistributionsRequest The postApiV4GroupsIdDashDebianDistributionsRequest parameter
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesDebianDistribution postApiV4GroupsIdDashDebianDistributions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashDebianDistributionsRequest postApiV4GroupsIdDashDebianDistributionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashDebianDistributionsRequestCreation(id, postApiV4GroupsIdDashDebianDistributionsRequest).body(localVarReturnType);
    }

    /**
     * Create a Debian group distribution
     * Creates a Debian group distribution for a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDashDebianDistributionsRequest The postApiV4GroupsIdDashDebianDistributionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesDebianDistribution> postApiV4GroupsIdDashDebianDistributionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashDebianDistributionsRequest postApiV4GroupsIdDashDebianDistributionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDashDebianDistributionsRequestCreation(id, postApiV4GroupsIdDashDebianDistributionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a Debian group distribution
     * Creates a Debian group distribution for a specified group.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDashDebianDistributionsRequest The postApiV4GroupsIdDashDebianDistributionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDashDebianDistributionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDashDebianDistributionsRequest postApiV4GroupsIdDashDebianDistributionsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdDashDebianDistributionsRequestCreation(id, postApiV4GroupsIdDashDebianDistributionsRequest);
    }

    /**
     * Create a Debian project distribution
     * Creates a Debian project distribution for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDebianDistributionsRequest The postApiV4ProjectsIdDebianDistributionsRequest parameter
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdDebianDistributionsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDebianDistributionsRequest postApiV4ProjectsIdDebianDistributionsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdDebianDistributions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdDebianDistributionsRequest' is set
        if (postApiV4ProjectsIdDebianDistributionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdDebianDistributionsRequest' when calling postApiV4ProjectsIdDebianDistributions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/debian_distributions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdDebianDistributionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a Debian project distribution
     * Creates a Debian project distribution for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDebianDistributionsRequest The postApiV4ProjectsIdDebianDistributionsRequest parameter
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesDebianDistribution postApiV4ProjectsIdDebianDistributions(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDebianDistributionsRequest postApiV4ProjectsIdDebianDistributionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDebianDistributionsRequestCreation(id, postApiV4ProjectsIdDebianDistributionsRequest).body(localVarReturnType);
    }

    /**
     * Create a Debian project distribution
     * Creates a Debian project distribution for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDebianDistributionsRequest The postApiV4ProjectsIdDebianDistributionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesDebianDistribution> postApiV4ProjectsIdDebianDistributionsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDebianDistributionsRequest postApiV4ProjectsIdDebianDistributionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDebianDistributionsRequestCreation(id, postApiV4ProjectsIdDebianDistributionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a Debian project distribution
     * Creates a Debian project distribution for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDebianDistributionsRequest The postApiV4ProjectsIdDebianDistributionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdDebianDistributionsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDebianDistributionsRequest postApiV4ProjectsIdDebianDistributionsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdDebianDistributionsRequestCreation(id, postApiV4ProjectsIdDebianDistributionsRequest);
    }

    /**
     * Update a Debian group distribution
     * Updates a specified Debian group distribution for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @param putApiV4GroupsIdDashDebianDistributionsCodenameRequest The putApiV4GroupsIdDashDebianDistributionsCodenameRequest parameter
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable PutApiV4GroupsIdDashDebianDistributionsCodenameRequest putApiV4GroupsIdDashDebianDistributionsCodenameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdDashDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'codename' is set
        if (codename == null) {
            throw new RestClientResponseException("Missing the required parameter 'codename' when calling putApiV4GroupsIdDashDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("codename", codename);

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

        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/debian_distributions/{codename}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdDashDebianDistributionsCodenameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a Debian group distribution
     * Updates a specified Debian group distribution for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @param putApiV4GroupsIdDashDebianDistributionsCodenameRequest The putApiV4GroupsIdDashDebianDistributionsCodenameRequest parameter
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesDebianDistribution putApiV4GroupsIdDashDebianDistributionsCodename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable PutApiV4GroupsIdDashDebianDistributionsCodenameRequest putApiV4GroupsIdDashDebianDistributionsCodenameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename, putApiV4GroupsIdDashDebianDistributionsCodenameRequest).body(localVarReturnType);
    }

    /**
     * Update a Debian group distribution
     * Updates a specified Debian group distribution for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @param putApiV4GroupsIdDashDebianDistributionsCodenameRequest The putApiV4GroupsIdDashDebianDistributionsCodenameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesDebianDistribution> putApiV4GroupsIdDashDebianDistributionsCodenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable PutApiV4GroupsIdDashDebianDistributionsCodenameRequest putApiV4GroupsIdDashDebianDistributionsCodenameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename, putApiV4GroupsIdDashDebianDistributionsCodenameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a Debian group distribution
     * Updates a specified Debian group distribution for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the group.
     * @param codename Codename of the Debian distribution.
     * @param putApiV4GroupsIdDashDebianDistributionsCodenameRequest The putApiV4GroupsIdDashDebianDistributionsCodenameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdDashDebianDistributionsCodenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable PutApiV4GroupsIdDashDebianDistributionsCodenameRequest putApiV4GroupsIdDashDebianDistributionsCodenameRequest) throws RestClientResponseException {
        return putApiV4GroupsIdDashDebianDistributionsCodenameRequestCreation(id, codename, putApiV4GroupsIdDashDebianDistributionsCodenameRequest);
    }

    /**
     * Update a Debian project distribution
     * Updates a specified Debian project distribution for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @param putApiV4ProjectsIdDebianDistributionsCodenameRequest The putApiV4ProjectsIdDebianDistributionsCodenameRequest parameter
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable PutApiV4ProjectsIdDebianDistributionsCodenameRequest putApiV4ProjectsIdDebianDistributionsCodenameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'codename' is set
        if (codename == null) {
            throw new RestClientResponseException("Missing the required parameter 'codename' when calling putApiV4ProjectsIdDebianDistributionsCodename", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("codename", codename);

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

        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/debian_distributions/{codename}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdDebianDistributionsCodenameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a Debian project distribution
     * Updates a specified Debian project distribution for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @param putApiV4ProjectsIdDebianDistributionsCodenameRequest The putApiV4ProjectsIdDebianDistributionsCodenameRequest parameter
     * @return APIEntitiesPackagesDebianDistribution
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPackagesDebianDistribution putApiV4ProjectsIdDebianDistributionsCodename(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable PutApiV4ProjectsIdDebianDistributionsCodenameRequest putApiV4ProjectsIdDebianDistributionsCodenameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename, putApiV4ProjectsIdDebianDistributionsCodenameRequest).body(localVarReturnType);
    }

    /**
     * Update a Debian project distribution
     * Updates a specified Debian project distribution for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @param putApiV4ProjectsIdDebianDistributionsCodenameRequest The putApiV4ProjectsIdDebianDistributionsCodenameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPackagesDebianDistribution&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPackagesDebianDistribution> putApiV4ProjectsIdDebianDistributionsCodenameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable PutApiV4ProjectsIdDebianDistributionsCodenameRequest putApiV4ProjectsIdDebianDistributionsCodenameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPackagesDebianDistribution> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename, putApiV4ProjectsIdDebianDistributionsCodenameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a Debian project distribution
     * Updates a specified Debian project distribution for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param codename Codename of the Debian distribution.
     * @param putApiV4ProjectsIdDebianDistributionsCodenameRequest The putApiV4ProjectsIdDebianDistributionsCodenameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdDebianDistributionsCodenameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String codename, @jakarta.annotation.Nullable PutApiV4ProjectsIdDebianDistributionsCodenameRequest putApiV4ProjectsIdDebianDistributionsCodenameRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdDebianDistributionsCodenameRequestCreation(id, codename, putApiV4ProjectsIdDebianDistributionsCodenameRequest);
    }

    /**
     * Upload a Debian package file
     * Uploads a Debian package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param component The Debian Component
     * @param distribution The Debian Codename or Suite. Used with &#x60;component&#x60; to upload with an explicit distribution and component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesDebianFileNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String component, @jakarta.annotation.Nullable String distribution) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesDebianFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesDebianFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling putApiV4ProjectsIdPackagesDebianFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'component' is set
        if (component == null) {
            throw new RestClientResponseException("Missing the required parameter 'component' when calling putApiV4ProjectsIdPackagesDebianFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));
        if (distribution != null) {
            formParams.add("distribution", distribution);
        }
        formParams.add("component", component);

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/{file_name}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a Debian package file
     * Uploads a Debian package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param component The Debian Component
     * @param distribution The Debian Codename or Suite. Used with &#x60;component&#x60; to upload with an explicit distribution and component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesDebianFileName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String component, @jakarta.annotation.Nullable String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesDebianFileNameRequestCreation(id, fileName, _file, component, distribution).body(localVarReturnType);
    }

    /**
     * Upload a Debian package file
     * Uploads a Debian package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param component The Debian Component
     * @param distribution The Debian Codename or Suite. Used with &#x60;component&#x60; to upload with an explicit distribution and component
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesDebianFileNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String component, @jakarta.annotation.Nullable String distribution) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesDebianFileNameRequestCreation(id, fileName, _file, component, distribution).toEntity(localVarReturnType);
    }

    /**
     * Upload a Debian package file
     * Uploads a Debian package file for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the package file.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @param component The Debian Component
     * @param distribution The Debian Codename or Suite. Used with &#x60;component&#x60; to upload with an explicit distribution and component
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesDebianFileNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String component, @jakarta.annotation.Nullable String distribution) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesDebianFileNameRequestCreation(id, fileName, _file, component, distribution);
    }

    /**
     * Authorize Debian package upload
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the package file.
     * @param putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull PutApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPackagesDebianFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling putApiV4ProjectsIdPackagesDebianFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest' is set
        if (putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest' when calling putApiV4ProjectsIdPackagesDebianFileNameAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/debian/{file_name}/authorize", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize Debian package upload
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the package file.
     * @param putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ProjectsIdPackagesDebianFileNameAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull PutApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequestCreation(id, fileName, putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest).body(localVarReturnType);
    }

    /**
     * Authorize Debian package upload
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the package file.
     * @param putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ProjectsIdPackagesDebianFileNameAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull PutApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequestCreation(id, fileName, putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest).toEntity(localVarReturnType);
    }

    /**
     * Authorize Debian package upload
     * This feature was introduced in GitLab 13.5
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the package file.
     * @param putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest The putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPackagesDebianFileNameAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileName, @jakarta.annotation.Nonnull PutApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequestCreation(id, fileName, putApiV4ProjectsIdPackagesDebianFileNameAuthorizeRequest);
    }
}
