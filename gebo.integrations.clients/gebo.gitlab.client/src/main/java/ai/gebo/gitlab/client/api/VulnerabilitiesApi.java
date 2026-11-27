package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesVulnerability;
import ai.gebo.gitlab.client.model.APIEntitiesVulnerabilityArchiveExport;
import ai.gebo.gitlab.client.model.APIEntitiesVulnerabilityExport;
import ai.gebo.gitlab.client.model.APIEntitiesVulnerabilityIssueLink;
import ai.gebo.gitlab.client.model.APIEntitiesVulnerabilityRelatedIssue;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdVulnerabilitiesRequest;
import ai.gebo.gitlab.client.model.PostApiV4SecurityGroupsIdVulnerabilityExportsRequest;
import ai.gebo.gitlab.client.model.PostApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest;
import ai.gebo.gitlab.client.model.PostApiV4SecurityProjectsIdVulnerabilityExportsRequest;
import ai.gebo.gitlab.client.model.PostApiV4SecurityVulnerabilityExportsRequest;
import ai.gebo.gitlab.client.model.PostApiV4VulnerabilitiesIdConfirmRequest;
import ai.gebo.gitlab.client.model.PostApiV4VulnerabilitiesIdIssueLinksRequest;
import ai.gebo.gitlab.client.model.PostApiV4VulnerabilitiesIdResolveRequest;
import ai.gebo.gitlab.client.model.PostApiV4VulnerabilitiesIdRevertRequest;
import ai.gebo.gitlab.client.model.PostApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest;
import ai.gebo.gitlab.client.model.VulnerabilitiesFindingEntity;

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
public class VulnerabilitiesApi {
    private ApiClient apiClient;

    public VulnerabilitiesApi() {
        this(new ApiClient());
    }

    public VulnerabilitiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a link between an issue and a vulnerability
     * Deletes a link between an issue and a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param issueLinkId ID of the vulnerability-issue link.
     * @return APIEntitiesVulnerabilityIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer issueLinkId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueLinkId' is set
        if (issueLinkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueLinkId' when calling deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_link_id", issueLinkId);

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

        ParameterizedTypeReference<APIEntitiesVulnerabilityIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{id}/issue_links/{issue_link_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a link between an issue and a vulnerability
     * Deletes a link between an issue and a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param issueLinkId ID of the vulnerability-issue link.
     * @return APIEntitiesVulnerabilityIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerabilityIssueLink deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer issueLinkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkIdRequestCreation(id, issueLinkId).body(localVarReturnType);
    }

    /**
     * Delete a link between an issue and a vulnerability
     * Deletes a link between an issue and a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param issueLinkId ID of the vulnerability-issue link.
     * @return ResponseEntity&lt;APIEntitiesVulnerabilityIssueLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerabilityIssueLink> deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer issueLinkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkIdRequestCreation(id, issueLinkId).toEntity(localVarReturnType);
    }

    /**
     * Delete a link between an issue and a vulnerability
     * Deletes a link between an issue and a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param issueLinkId ID of the vulnerability-issue link.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer issueLinkId) throws RestClientResponseException {
        return deleteApiV4VulnerabilitiesIdIssueLinksIssueLinkIdRequestCreation(id, issueLinkId);
    }

    /**
     * List all project vulnerabilities
     * Lists all vulnerabilities for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project. The authenticated user must be a member of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesVulnerability&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdVulnerabilitiesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdVulnerabilities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesVulnerability>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/vulnerabilities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project vulnerabilities
     * Lists all vulnerabilities for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project. The authenticated user must be a member of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesVulnerability&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesVulnerability> getApiV4ProjectsIdVulnerabilities(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesVulnerability>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVulnerabilitiesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project vulnerabilities
     * Lists all vulnerabilities for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project. The authenticated user must be a member of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesVulnerability&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesVulnerability>> getApiV4ProjectsIdVulnerabilitiesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesVulnerability>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVulnerabilitiesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project vulnerabilities
     * Lists all vulnerabilities for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project. The authenticated user must be a member of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdVulnerabilitiesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdVulnerabilitiesRequestCreation(id, page, perPage);
    }

    /**
     * List all project vulnerability findings
     * Lists all vulnerability findings for a specified project. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param reportType Return vulnerability findings belonging to the specified report type, such as &#x60;sast&#x60;, &#x60;dast&#x60;, &#x60;dependency_scanning&#x60;, or &#x60;container_scanning&#x60;.
     * @param scope Return vulnerability findings for the given scope.
     * @param severity Return vulnerability findings belonging to the specified severity level: &#x60;info&#x60;, &#x60;unknown&#x60;, &#x60;low&#x60;, &#x60;medium&#x60;, &#x60;high&#x60;, or &#x60;critical&#x60;.
     * @param pipelineId Filter vulnerability findings by pipeline.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;VulnerabilitiesFindingEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdVulnerabilityFindingsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> reportType, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable List<String> severity, @jakarta.annotation.Nullable String pipelineId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdVulnerabilityFindings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "report_type", reportType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "severity", severity));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pipeline_id", pipelineId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<VulnerabilitiesFindingEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/vulnerability_findings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project vulnerability findings
     * Lists all vulnerability findings for a specified project. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param reportType Return vulnerability findings belonging to the specified report type, such as &#x60;sast&#x60;, &#x60;dast&#x60;, &#x60;dependency_scanning&#x60;, or &#x60;container_scanning&#x60;.
     * @param scope Return vulnerability findings for the given scope.
     * @param severity Return vulnerability findings belonging to the specified severity level: &#x60;info&#x60;, &#x60;unknown&#x60;, &#x60;low&#x60;, &#x60;medium&#x60;, &#x60;high&#x60;, or &#x60;critical&#x60;.
     * @param pipelineId Filter vulnerability findings by pipeline.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;VulnerabilitiesFindingEntity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<VulnerabilitiesFindingEntity> getApiV4ProjectsIdVulnerabilityFindings(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> reportType, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable List<String> severity, @jakarta.annotation.Nullable String pipelineId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<VulnerabilitiesFindingEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVulnerabilityFindingsRequestCreation(id, reportType, scope, severity, pipelineId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project vulnerability findings
     * Lists all vulnerability findings for a specified project. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param reportType Return vulnerability findings belonging to the specified report type, such as &#x60;sast&#x60;, &#x60;dast&#x60;, &#x60;dependency_scanning&#x60;, or &#x60;container_scanning&#x60;.
     * @param scope Return vulnerability findings for the given scope.
     * @param severity Return vulnerability findings belonging to the specified severity level: &#x60;info&#x60;, &#x60;unknown&#x60;, &#x60;low&#x60;, &#x60;medium&#x60;, &#x60;high&#x60;, or &#x60;critical&#x60;.
     * @param pipelineId Filter vulnerability findings by pipeline.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;VulnerabilitiesFindingEntity&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<VulnerabilitiesFindingEntity>> getApiV4ProjectsIdVulnerabilityFindingsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> reportType, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable List<String> severity, @jakarta.annotation.Nullable String pipelineId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<VulnerabilitiesFindingEntity>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdVulnerabilityFindingsRequestCreation(id, reportType, scope, severity, pipelineId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project vulnerability findings
     * Lists all vulnerability findings for a specified project. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param reportType Return vulnerability findings belonging to the specified report type, such as &#x60;sast&#x60;, &#x60;dast&#x60;, &#x60;dependency_scanning&#x60;, or &#x60;container_scanning&#x60;.
     * @param scope Return vulnerability findings for the given scope.
     * @param severity Return vulnerability findings belonging to the specified severity level: &#x60;info&#x60;, &#x60;unknown&#x60;, &#x60;low&#x60;, &#x60;medium&#x60;, &#x60;high&#x60;, or &#x60;critical&#x60;.
     * @param pipelineId Filter vulnerability findings by pipeline.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdVulnerabilityFindingsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable List<String> reportType, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable List<String> severity, @jakarta.annotation.Nullable String pipelineId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdVulnerabilityFindingsRequestCreation(id, reportType, scope, severity, pipelineId, page, perPage);
    }

    /**
     * Retrieve a vulnerability archive export
     * Retrieves the attributes for a specified vulnerability archive export. You can use this endpoint to poll the export status until it is &#x60;finished&#x60;. Clients should use the &#x60;Poll-Interval&#x60; response header to determine how long to wait before sending the next poll. The &#x60;Poll-Interval&#x60; represents the time in milliseconds. The &#x60;status&#x60; is one of: &#x60;created&#x60; (the export has not started running yet), &#x60;running&#x60; (the export is being generated), &#x60;finished&#x60; (the export is available for download), &#x60;failed&#x60; (there was an error while generating the export and it could not be completed), or &#x60;purged&#x60; (the export is queued for deletion and the file is no longer available for download).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability archive export.
     * @return APIEntitiesVulnerabilityArchiveExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SecurityVulnerabilityArchiveExportsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SecurityVulnerabilityArchiveExportsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerabilityArchiveExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/security/vulnerability_archive_exports/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a vulnerability archive export
     * Retrieves the attributes for a specified vulnerability archive export. You can use this endpoint to poll the export status until it is &#x60;finished&#x60;. Clients should use the &#x60;Poll-Interval&#x60; response header to determine how long to wait before sending the next poll. The &#x60;Poll-Interval&#x60; represents the time in milliseconds. The &#x60;status&#x60; is one of: &#x60;created&#x60; (the export has not started running yet), &#x60;running&#x60; (the export is being generated), &#x60;finished&#x60; (the export is available for download), &#x60;failed&#x60; (there was an error while generating the export and it could not be completed), or &#x60;purged&#x60; (the export is queued for deletion and the file is no longer available for download).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability archive export.
     * @return APIEntitiesVulnerabilityArchiveExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerabilityArchiveExport getApiV4SecurityVulnerabilityArchiveExportsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityArchiveExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SecurityVulnerabilityArchiveExportsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a vulnerability archive export
     * Retrieves the attributes for a specified vulnerability archive export. You can use this endpoint to poll the export status until it is &#x60;finished&#x60;. Clients should use the &#x60;Poll-Interval&#x60; response header to determine how long to wait before sending the next poll. The &#x60;Poll-Interval&#x60; represents the time in milliseconds. The &#x60;status&#x60; is one of: &#x60;created&#x60; (the export has not started running yet), &#x60;running&#x60; (the export is being generated), &#x60;finished&#x60; (the export is available for download), &#x60;failed&#x60; (there was an error while generating the export and it could not be completed), or &#x60;purged&#x60; (the export is queued for deletion and the file is no longer available for download).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability archive export.
     * @return ResponseEntity&lt;APIEntitiesVulnerabilityArchiveExport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerabilityArchiveExport> getApiV4SecurityVulnerabilityArchiveExportsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityArchiveExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SecurityVulnerabilityArchiveExportsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a vulnerability archive export
     * Retrieves the attributes for a specified vulnerability archive export. You can use this endpoint to poll the export status until it is &#x60;finished&#x60;. Clients should use the &#x60;Poll-Interval&#x60; response header to determine how long to wait before sending the next poll. The &#x60;Poll-Interval&#x60; represents the time in milliseconds. The &#x60;status&#x60; is one of: &#x60;created&#x60; (the export has not started running yet), &#x60;running&#x60; (the export is being generated), &#x60;finished&#x60; (the export is available for download), &#x60;failed&#x60; (there was an error while generating the export and it could not be completed), or &#x60;purged&#x60; (the export is queued for deletion and the file is no longer available for download).
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability archive export.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SecurityVulnerabilityArchiveExportsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4SecurityVulnerabilityArchiveExportsIdRequestCreation(id);
    }

    /**
     * Download a vulnerability archive export
     * Downloads a vulnerability archive export. The export must have the status &#x60;finished&#x60;. Before you call this endpoint, verify the export status with the retrieve endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability archive export.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SecurityVulnerabilityArchiveExportsIdDownloadRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SecurityVulnerabilityArchiveExportsIdDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/security/vulnerability_archive_exports/{id}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a vulnerability archive export
     * Downloads a vulnerability archive export. The export must have the status &#x60;finished&#x60;. Before you call this endpoint, verify the export status with the retrieve endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability archive export.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4SecurityVulnerabilityArchiveExportsIdDownload(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SecurityVulnerabilityArchiveExportsIdDownloadRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Download a vulnerability archive export
     * Downloads a vulnerability archive export. The export must have the status &#x60;finished&#x60;. Before you call this endpoint, verify the export status with the retrieve endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability archive export.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4SecurityVulnerabilityArchiveExportsIdDownloadWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SecurityVulnerabilityArchiveExportsIdDownloadRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Download a vulnerability archive export
     * Downloads a vulnerability archive export. The export must have the status &#x60;finished&#x60;. Before you call this endpoint, verify the export status with the retrieve endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability archive export.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SecurityVulnerabilityArchiveExportsIdDownloadWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4SecurityVulnerabilityArchiveExportsIdDownloadRequestCreation(id);
    }

    /**
     * Retrieve a vulnerability export
     * Retrieves a specified vulnerability export.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability export.
     * @return APIEntitiesVulnerabilityExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SecurityVulnerabilityExportsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SecurityVulnerabilityExportsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/security/vulnerability_exports/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a vulnerability export
     * Retrieves a specified vulnerability export.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability export.
     * @return APIEntitiesVulnerabilityExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerabilityExport getApiV4SecurityVulnerabilityExportsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SecurityVulnerabilityExportsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a vulnerability export
     * Retrieves a specified vulnerability export.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability export.
     * @return ResponseEntity&lt;APIEntitiesVulnerabilityExport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerabilityExport> getApiV4SecurityVulnerabilityExportsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SecurityVulnerabilityExportsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a vulnerability export
     * Retrieves a specified vulnerability export.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability export.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SecurityVulnerabilityExportsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4SecurityVulnerabilityExportsIdRequestCreation(id);
    }

    /**
     * Download a vulnerability export
     * Downloads a specified vulnerability export. A CSV export has these columns: Group Name, Project Name, Tool, Scanner Name, Status, Vulnerability, Details, Additional Info, Severity, CVE, CWE, Other Identifiers, Detected At, Location, Activity, Comments, Full Path, CVSS Vectors, Dismissal Reason, Vulnerability ID, and Tracked Context Name.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability export.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SecurityVulnerabilityExportsIdDownloadRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4SecurityVulnerabilityExportsIdDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/security/vulnerability_exports/{id}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a vulnerability export
     * Downloads a specified vulnerability export. A CSV export has these columns: Group Name, Project Name, Tool, Scanner Name, Status, Vulnerability, Details, Additional Info, Severity, CVE, CWE, Other Identifiers, Detected At, Location, Activity, Comments, Full Path, CVSS Vectors, Dismissal Reason, Vulnerability ID, and Tracked Context Name.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability export.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4SecurityVulnerabilityExportsIdDownload(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SecurityVulnerabilityExportsIdDownloadRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Download a vulnerability export
     * Downloads a specified vulnerability export. A CSV export has these columns: Group Name, Project Name, Tool, Scanner Name, Status, Vulnerability, Details, Additional Info, Severity, CVE, CWE, Other Identifiers, Detected At, Location, Activity, Comments, Full Path, CVSS Vectors, Dismissal Reason, Vulnerability ID, and Tracked Context Name.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability export.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4SecurityVulnerabilityExportsIdDownloadWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SecurityVulnerabilityExportsIdDownloadRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Download a vulnerability export
     * Downloads a specified vulnerability export. A CSV export has these columns: Group Name, Project Name, Tool, Scanner Name, Status, Vulnerability, Details, Additional Info, Severity, CVE, CWE, Other Identifiers, Detected At, Location, Activity, Comments, Full Path, CVSS Vectors, Dismissal Reason, Vulnerability ID, and Tracked Context Name.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability export.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SecurityVulnerabilityExportsIdDownloadWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4SecurityVulnerabilityExportsIdDownloadRequestCreation(id);
    }

    /**
     * Retrieve a vulnerability
     * Retrieves a specified vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VulnerabilitiesIdRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4VulnerabilitiesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a vulnerability
     * Retrieves a specified vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerability getApiV4VulnerabilitiesId(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VulnerabilitiesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a vulnerability
     * Retrieves a specified vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return ResponseEntity&lt;APIEntitiesVulnerability&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerability> getApiV4VulnerabilitiesIdWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VulnerabilitiesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a vulnerability
     * Retrieves a specified vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VulnerabilitiesIdWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4VulnerabilitiesIdRequestCreation(id);
    }

    /**
     * Get related issues for a vulnerability
     * Lists related issues for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return List&lt;APIEntitiesVulnerabilityRelatedIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VulnerabilitiesIdIssueLinksRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4VulnerabilitiesIdIssueLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesVulnerabilityRelatedIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{id}/issue_links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get related issues for a vulnerability
     * Lists related issues for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return List&lt;APIEntitiesVulnerabilityRelatedIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesVulnerabilityRelatedIssue> getApiV4VulnerabilitiesIdIssueLinks(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesVulnerabilityRelatedIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VulnerabilitiesIdIssueLinksRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get related issues for a vulnerability
     * Lists related issues for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return ResponseEntity&lt;List&lt;APIEntitiesVulnerabilityRelatedIssue&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesVulnerabilityRelatedIssue>> getApiV4VulnerabilitiesIdIssueLinksWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesVulnerabilityRelatedIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VulnerabilitiesIdIssueLinksRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get related issues for a vulnerability
     * Lists related issues for a vulnerability.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VulnerabilitiesIdIssueLinksWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4VulnerabilitiesIdIssueLinksRequestCreation(id);
    }

    /**
     * Create a new vulnerability
     * Creates a new vulnerability from a confirmed finding. The new vulnerability takes its &#x60;title&#x60; from the &#x60;name&#x60; of the finding, and its &#x60;severity&#x60; from the finding. Its author is the authenticated user. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Returns status code &#x60;400&#x60; if the finding is not found or is already attached to a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project. The authenticated user must be a member of the project.
     * @param postApiV4ProjectsIdVulnerabilitiesRequest The postApiV4ProjectsIdVulnerabilitiesRequest parameter
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdVulnerabilitiesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVulnerabilitiesRequest postApiV4ProjectsIdVulnerabilitiesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdVulnerabilities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdVulnerabilitiesRequest' is set
        if (postApiV4ProjectsIdVulnerabilitiesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdVulnerabilitiesRequest' when calling postApiV4ProjectsIdVulnerabilities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/vulnerabilities", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdVulnerabilitiesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a new vulnerability
     * Creates a new vulnerability from a confirmed finding. The new vulnerability takes its &#x60;title&#x60; from the &#x60;name&#x60; of the finding, and its &#x60;severity&#x60; from the finding. Its author is the authenticated user. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Returns status code &#x60;400&#x60; if the finding is not found or is already attached to a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project. The authenticated user must be a member of the project.
     * @param postApiV4ProjectsIdVulnerabilitiesRequest The postApiV4ProjectsIdVulnerabilitiesRequest parameter
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerability postApiV4ProjectsIdVulnerabilities(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVulnerabilitiesRequest postApiV4ProjectsIdVulnerabilitiesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdVulnerabilitiesRequestCreation(id, postApiV4ProjectsIdVulnerabilitiesRequest).body(localVarReturnType);
    }

    /**
     * Create a new vulnerability
     * Creates a new vulnerability from a confirmed finding. The new vulnerability takes its &#x60;title&#x60; from the &#x60;name&#x60; of the finding, and its &#x60;severity&#x60; from the finding. Its author is the authenticated user. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Returns status code &#x60;400&#x60; if the finding is not found or is already attached to a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project. The authenticated user must be a member of the project.
     * @param postApiV4ProjectsIdVulnerabilitiesRequest The postApiV4ProjectsIdVulnerabilitiesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerability&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerability> postApiV4ProjectsIdVulnerabilitiesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVulnerabilitiesRequest postApiV4ProjectsIdVulnerabilitiesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdVulnerabilitiesRequestCreation(id, postApiV4ProjectsIdVulnerabilitiesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a new vulnerability
     * Creates a new vulnerability from a confirmed finding. The new vulnerability takes its &#x60;title&#x60; from the &#x60;name&#x60; of the finding, and its &#x60;severity&#x60; from the finding. Its author is the authenticated user. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Returns status code &#x60;400&#x60; if the finding is not found or is already attached to a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project. The authenticated user must be a member of the project.
     * @param postApiV4ProjectsIdVulnerabilitiesRequest The postApiV4ProjectsIdVulnerabilitiesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdVulnerabilitiesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdVulnerabilitiesRequest postApiV4ProjectsIdVulnerabilitiesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdVulnerabilitiesRequestCreation(id, postApiV4ProjectsIdVulnerabilitiesRequest);
    }

    /**
     * Create a group-level vulnerability export
     * Creates a vulnerability export for a group. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Each user can create only one vulnerability export at a time for a specific group. Only the author of an export can access it. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4SecurityGroupsIdVulnerabilityExportsRequest The postApiV4SecurityGroupsIdVulnerabilityExportsRequest parameter
     * @return APIEntitiesVulnerabilityExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4SecurityGroupsIdVulnerabilityExportsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4SecurityGroupsIdVulnerabilityExportsRequest postApiV4SecurityGroupsIdVulnerabilityExportsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4SecurityGroupsIdVulnerabilityExports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/security/groups/{id}/vulnerability_exports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4SecurityGroupsIdVulnerabilityExportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group-level vulnerability export
     * Creates a vulnerability export for a group. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Each user can create only one vulnerability export at a time for a specific group. Only the author of an export can access it. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4SecurityGroupsIdVulnerabilityExportsRequest The postApiV4SecurityGroupsIdVulnerabilityExportsRequest parameter
     * @return APIEntitiesVulnerabilityExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerabilityExport postApiV4SecurityGroupsIdVulnerabilityExports(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4SecurityGroupsIdVulnerabilityExportsRequest postApiV4SecurityGroupsIdVulnerabilityExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SecurityGroupsIdVulnerabilityExportsRequestCreation(id, postApiV4SecurityGroupsIdVulnerabilityExportsRequest).body(localVarReturnType);
    }

    /**
     * Create a group-level vulnerability export
     * Creates a vulnerability export for a group. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Each user can create only one vulnerability export at a time for a specific group. Only the author of an export can access it. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4SecurityGroupsIdVulnerabilityExportsRequest The postApiV4SecurityGroupsIdVulnerabilityExportsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerabilityExport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerabilityExport> postApiV4SecurityGroupsIdVulnerabilityExportsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4SecurityGroupsIdVulnerabilityExportsRequest postApiV4SecurityGroupsIdVulnerabilityExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SecurityGroupsIdVulnerabilityExportsRequestCreation(id, postApiV4SecurityGroupsIdVulnerabilityExportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group-level vulnerability export
     * Creates a vulnerability export for a group. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Each user can create only one vulnerability export at a time for a specific group. Only the author of an export can access it. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4SecurityGroupsIdVulnerabilityExportsRequest The postApiV4SecurityGroupsIdVulnerabilityExportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4SecurityGroupsIdVulnerabilityExportsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4SecurityGroupsIdVulnerabilityExportsRequest postApiV4SecurityGroupsIdVulnerabilityExportsRequest) throws RestClientResponseException {
        return postApiV4SecurityGroupsIdVulnerabilityExportsRequestCreation(id, postApiV4SecurityGroupsIdVulnerabilityExportsRequest);
    }

    /**
     * Create an export for archived vulnerabilities
     * Creates an export for archived vulnerabilities in a specified project. If an authenticated user does not have permission to read vulnerabilities, returns a &#x60;403 Forbidden&#x60; status code. The export file is deleted 24 hours after the export finishes.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest The postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest parameter
     * @return APIEntitiesVulnerabilityArchiveExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4SecurityProjectsIdVulnerabilityArchiveExports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest' is set
        if (postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest' when calling postApiV4SecurityProjectsIdVulnerabilityArchiveExports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerabilityArchiveExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/security/projects/{id}/vulnerability_archive_exports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an export for archived vulnerabilities
     * Creates an export for archived vulnerabilities in a specified project. If an authenticated user does not have permission to read vulnerabilities, returns a &#x60;403 Forbidden&#x60; status code. The export file is deleted 24 hours after the export finishes.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest The postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest parameter
     * @return APIEntitiesVulnerabilityArchiveExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerabilityArchiveExport postApiV4SecurityProjectsIdVulnerabilityArchiveExports(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityArchiveExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequestCreation(id, postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest).body(localVarReturnType);
    }

    /**
     * Create an export for archived vulnerabilities
     * Creates an export for archived vulnerabilities in a specified project. If an authenticated user does not have permission to read vulnerabilities, returns a &#x60;403 Forbidden&#x60; status code. The export file is deleted 24 hours after the export finishes.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest The postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerabilityArchiveExport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerabilityArchiveExport> postApiV4SecurityProjectsIdVulnerabilityArchiveExportsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityArchiveExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequestCreation(id, postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an export for archived vulnerabilities
     * Creates an export for archived vulnerabilities in a specified project. If an authenticated user does not have permission to read vulnerabilities, returns a &#x60;403 Forbidden&#x60; status code. The export file is deleted 24 hours after the export finishes.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest The postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4SecurityProjectsIdVulnerabilityArchiveExportsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest) throws RestClientResponseException {
        return postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequestCreation(id, postApiV4SecurityProjectsIdVulnerabilityArchiveExportsRequest);
    }

    /**
     * Create a project-level vulnerability export
     * Creates a vulnerability export for a project. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Each user can create only one vulnerability export at a time for a specific project. Only the author of an export can access it. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4SecurityProjectsIdVulnerabilityExportsRequest The postApiV4SecurityProjectsIdVulnerabilityExportsRequest parameter
     * @return APIEntitiesVulnerabilityExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4SecurityProjectsIdVulnerabilityExportsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4SecurityProjectsIdVulnerabilityExportsRequest postApiV4SecurityProjectsIdVulnerabilityExportsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4SecurityProjectsIdVulnerabilityExports", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/security/projects/{id}/vulnerability_exports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4SecurityProjectsIdVulnerabilityExportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project-level vulnerability export
     * Creates a vulnerability export for a project. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Each user can create only one vulnerability export at a time for a specific project. Only the author of an export can access it. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4SecurityProjectsIdVulnerabilityExportsRequest The postApiV4SecurityProjectsIdVulnerabilityExportsRequest parameter
     * @return APIEntitiesVulnerabilityExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerabilityExport postApiV4SecurityProjectsIdVulnerabilityExports(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4SecurityProjectsIdVulnerabilityExportsRequest postApiV4SecurityProjectsIdVulnerabilityExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SecurityProjectsIdVulnerabilityExportsRequestCreation(id, postApiV4SecurityProjectsIdVulnerabilityExportsRequest).body(localVarReturnType);
    }

    /**
     * Create a project-level vulnerability export
     * Creates a vulnerability export for a project. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Each user can create only one vulnerability export at a time for a specific project. Only the author of an export can access it. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4SecurityProjectsIdVulnerabilityExportsRequest The postApiV4SecurityProjectsIdVulnerabilityExportsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerabilityExport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerabilityExport> postApiV4SecurityProjectsIdVulnerabilityExportsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4SecurityProjectsIdVulnerabilityExportsRequest postApiV4SecurityProjectsIdVulnerabilityExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SecurityProjectsIdVulnerabilityExportsRequestCreation(id, postApiV4SecurityProjectsIdVulnerabilityExportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a project-level vulnerability export
     * Creates a vulnerability export for a project. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to create a vulnerability. Each user can create only one vulnerability export at a time for a specific project. Only the author of an export can access it. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4SecurityProjectsIdVulnerabilityExportsRequest The postApiV4SecurityProjectsIdVulnerabilityExportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4SecurityProjectsIdVulnerabilityExportsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4SecurityProjectsIdVulnerabilityExportsRequest postApiV4SecurityProjectsIdVulnerabilityExportsRequest) throws RestClientResponseException {
        return postApiV4SecurityProjectsIdVulnerabilityExportsRequestCreation(id, postApiV4SecurityProjectsIdVulnerabilityExportsRequest);
    }

    /**
     * Create an instance-level vulnerability export
     * Creates a vulnerability export for the projects of the user selected in the Security Dashboard. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4SecurityVulnerabilityExportsRequest The postApiV4SecurityVulnerabilityExportsRequest parameter
     * @return APIEntitiesVulnerabilityExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4SecurityVulnerabilityExportsRequestCreation(@jakarta.annotation.Nullable PostApiV4SecurityVulnerabilityExportsRequest postApiV4SecurityVulnerabilityExportsRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/security/vulnerability_exports", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4SecurityVulnerabilityExportsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an instance-level vulnerability export
     * Creates a vulnerability export for the projects of the user selected in the Security Dashboard. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4SecurityVulnerabilityExportsRequest The postApiV4SecurityVulnerabilityExportsRequest parameter
     * @return APIEntitiesVulnerabilityExport
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerabilityExport postApiV4SecurityVulnerabilityExports(@jakarta.annotation.Nullable PostApiV4SecurityVulnerabilityExportsRequest postApiV4SecurityVulnerabilityExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SecurityVulnerabilityExportsRequestCreation(postApiV4SecurityVulnerabilityExportsRequest).body(localVarReturnType);
    }

    /**
     * Create an instance-level vulnerability export
     * Creates a vulnerability export for the projects of the user selected in the Security Dashboard. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4SecurityVulnerabilityExportsRequest The postApiV4SecurityVulnerabilityExportsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerabilityExport&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerabilityExport> postApiV4SecurityVulnerabilityExportsWithHttpInfo(@jakarta.annotation.Nullable PostApiV4SecurityVulnerabilityExportsRequest postApiV4SecurityVulnerabilityExportsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityExport> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4SecurityVulnerabilityExportsRequestCreation(postApiV4SecurityVulnerabilityExportsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an instance-level vulnerability export
     * Creates a vulnerability export for the projects of the user selected in the Security Dashboard. The created vulnerability export is automatically deleted at the time specified in the &#x60;expires_at&#x60; field.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4SecurityVulnerabilityExportsRequest The postApiV4SecurityVulnerabilityExportsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4SecurityVulnerabilityExportsWithResponseSpec(@jakarta.annotation.Nullable PostApiV4SecurityVulnerabilityExportsRequest postApiV4SecurityVulnerabilityExportsRequest) throws RestClientResponseException {
        return postApiV4SecurityVulnerabilityExportsRequestCreation(postApiV4SecurityVulnerabilityExportsRequest);
    }

    /**
     * Confirm a vulnerability
     * Confirms a specified vulnerability, marking it as genuine and requiring action. Returns status code &#x60;304&#x60; if the vulnerability is already confirmed. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdConfirmRequest The postApiV4VulnerabilitiesIdConfirmRequest parameter
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4VulnerabilitiesIdConfirmRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdConfirmRequest postApiV4VulnerabilitiesIdConfirmRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4VulnerabilitiesIdConfirm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{id}/confirm", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4VulnerabilitiesIdConfirmRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Confirm a vulnerability
     * Confirms a specified vulnerability, marking it as genuine and requiring action. Returns status code &#x60;304&#x60; if the vulnerability is already confirmed. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdConfirmRequest The postApiV4VulnerabilitiesIdConfirmRequest parameter
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerability postApiV4VulnerabilitiesIdConfirm(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdConfirmRequest postApiV4VulnerabilitiesIdConfirmRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdConfirmRequestCreation(id, postApiV4VulnerabilitiesIdConfirmRequest).body(localVarReturnType);
    }

    /**
     * Confirm a vulnerability
     * Confirms a specified vulnerability, marking it as genuine and requiring action. Returns status code &#x60;304&#x60; if the vulnerability is already confirmed. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdConfirmRequest The postApiV4VulnerabilitiesIdConfirmRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerability&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerability> postApiV4VulnerabilitiesIdConfirmWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdConfirmRequest postApiV4VulnerabilitiesIdConfirmRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdConfirmRequestCreation(id, postApiV4VulnerabilitiesIdConfirmRequest).toEntity(localVarReturnType);
    }

    /**
     * Confirm a vulnerability
     * Confirms a specified vulnerability, marking it as genuine and requiring action. Returns status code &#x60;304&#x60; if the vulnerability is already confirmed. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdConfirmRequest The postApiV4VulnerabilitiesIdConfirmRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4VulnerabilitiesIdConfirmWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdConfirmRequest postApiV4VulnerabilitiesIdConfirmRequest) throws RestClientResponseException {
        return postApiV4VulnerabilitiesIdConfirmRequestCreation(id, postApiV4VulnerabilitiesIdConfirmRequest);
    }

    /**
     * Dismiss a vulnerability
     * Dismisses a specified vulnerability. Returns status code &#x60;304&#x60; if the vulnerability is already dismissed. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4VulnerabilitiesIdDismissRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4VulnerabilitiesIdDismiss", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{id}/dismiss", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Dismiss a vulnerability
     * Dismisses a specified vulnerability. Returns status code &#x60;304&#x60; if the vulnerability is already dismissed. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerability postApiV4VulnerabilitiesIdDismiss(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdDismissRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Dismiss a vulnerability
     * Dismisses a specified vulnerability. Returns status code &#x60;304&#x60; if the vulnerability is already dismissed. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return ResponseEntity&lt;APIEntitiesVulnerability&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerability> postApiV4VulnerabilitiesIdDismissWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdDismissRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Dismiss a vulnerability
     * Dismisses a specified vulnerability. Returns status code &#x60;304&#x60; if the vulnerability is already dismissed. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4VulnerabilitiesIdDismissWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return postApiV4VulnerabilitiesIdDismissRequestCreation(id);
    }

    /**
     * Relate an issue to a vulnerability
     * Relates an issue to a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdIssueLinksRequest The postApiV4VulnerabilitiesIdIssueLinksRequest parameter
     * @return APIEntitiesVulnerabilityIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4VulnerabilitiesIdIssueLinksRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4VulnerabilitiesIdIssueLinksRequest postApiV4VulnerabilitiesIdIssueLinksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4VulnerabilitiesIdIssueLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4VulnerabilitiesIdIssueLinksRequest' is set
        if (postApiV4VulnerabilitiesIdIssueLinksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4VulnerabilitiesIdIssueLinksRequest' when calling postApiV4VulnerabilitiesIdIssueLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerabilityIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{id}/issue_links", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4VulnerabilitiesIdIssueLinksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Relate an issue to a vulnerability
     * Relates an issue to a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdIssueLinksRequest The postApiV4VulnerabilitiesIdIssueLinksRequest parameter
     * @return APIEntitiesVulnerabilityIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerabilityIssueLink postApiV4VulnerabilitiesIdIssueLinks(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4VulnerabilitiesIdIssueLinksRequest postApiV4VulnerabilitiesIdIssueLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdIssueLinksRequestCreation(id, postApiV4VulnerabilitiesIdIssueLinksRequest).body(localVarReturnType);
    }

    /**
     * Relate an issue to a vulnerability
     * Relates an issue to a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdIssueLinksRequest The postApiV4VulnerabilitiesIdIssueLinksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerabilityIssueLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerabilityIssueLink> postApiV4VulnerabilitiesIdIssueLinksWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4VulnerabilitiesIdIssueLinksRequest postApiV4VulnerabilitiesIdIssueLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerabilityIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdIssueLinksRequestCreation(id, postApiV4VulnerabilitiesIdIssueLinksRequest).toEntity(localVarReturnType);
    }

    /**
     * Relate an issue to a vulnerability
     * Relates an issue to a vulnerability.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdIssueLinksRequest The postApiV4VulnerabilitiesIdIssueLinksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4VulnerabilitiesIdIssueLinksWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4VulnerabilitiesIdIssueLinksRequest postApiV4VulnerabilitiesIdIssueLinksRequest) throws RestClientResponseException {
        return postApiV4VulnerabilitiesIdIssueLinksRequestCreation(id, postApiV4VulnerabilitiesIdIssueLinksRequest);
    }

    /**
     * Resolve a vulnerability
     * Resolves a specified vulnerability. Returns status code &#x60;304&#x60; if the vulnerability is already resolved. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdResolveRequest The postApiV4VulnerabilitiesIdResolveRequest parameter
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4VulnerabilitiesIdResolveRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdResolveRequest postApiV4VulnerabilitiesIdResolveRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4VulnerabilitiesIdResolve", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{id}/resolve", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4VulnerabilitiesIdResolveRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Resolve a vulnerability
     * Resolves a specified vulnerability. Returns status code &#x60;304&#x60; if the vulnerability is already resolved. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdResolveRequest The postApiV4VulnerabilitiesIdResolveRequest parameter
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerability postApiV4VulnerabilitiesIdResolve(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdResolveRequest postApiV4VulnerabilitiesIdResolveRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdResolveRequestCreation(id, postApiV4VulnerabilitiesIdResolveRequest).body(localVarReturnType);
    }

    /**
     * Resolve a vulnerability
     * Resolves a specified vulnerability. Returns status code &#x60;304&#x60; if the vulnerability is already resolved. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdResolveRequest The postApiV4VulnerabilitiesIdResolveRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerability&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerability> postApiV4VulnerabilitiesIdResolveWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdResolveRequest postApiV4VulnerabilitiesIdResolveRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdResolveRequestCreation(id, postApiV4VulnerabilitiesIdResolveRequest).toEntity(localVarReturnType);
    }

    /**
     * Resolve a vulnerability
     * Resolves a specified vulnerability. Returns status code &#x60;304&#x60; if the vulnerability is already resolved. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change the vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdResolveRequest The postApiV4VulnerabilitiesIdResolveRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4VulnerabilitiesIdResolveWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdResolveRequest postApiV4VulnerabilitiesIdResolveRequest) throws RestClientResponseException {
        return postApiV4VulnerabilitiesIdResolveRequestCreation(id, postApiV4VulnerabilitiesIdResolveRequest);
    }

    /**
     * Revert a vulnerability to detected state
     * Reverts a specified vulnerability to detected state. Returns status code &#x60;304&#x60; if the vulnerability is already in detected state. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdRevertRequest The postApiV4VulnerabilitiesIdRevertRequest parameter
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4VulnerabilitiesIdRevertRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdRevertRequest postApiV4VulnerabilitiesIdRevertRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4VulnerabilitiesIdRevert", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{id}/revert", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4VulnerabilitiesIdRevertRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revert a vulnerability to detected state
     * Reverts a specified vulnerability to detected state. Returns status code &#x60;304&#x60; if the vulnerability is already in detected state. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdRevertRequest The postApiV4VulnerabilitiesIdRevertRequest parameter
     * @return APIEntitiesVulnerability
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesVulnerability postApiV4VulnerabilitiesIdRevert(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdRevertRequest postApiV4VulnerabilitiesIdRevertRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdRevertRequestCreation(id, postApiV4VulnerabilitiesIdRevertRequest).body(localVarReturnType);
    }

    /**
     * Revert a vulnerability to detected state
     * Reverts a specified vulnerability to detected state. Returns status code &#x60;304&#x60; if the vulnerability is already in detected state. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdRevertRequest The postApiV4VulnerabilitiesIdRevertRequest parameter
     * @return ResponseEntity&lt;APIEntitiesVulnerability&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesVulnerability> postApiV4VulnerabilitiesIdRevertWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdRevertRequest postApiV4VulnerabilitiesIdRevertRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesVulnerability> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesIdRevertRequestCreation(id, postApiV4VulnerabilitiesIdRevertRequest).toEntity(localVarReturnType);
    }

    /**
     * Revert a vulnerability to detected state
     * Reverts a specified vulnerability to detected state. Returns status code &#x60;304&#x60; if the vulnerability is already in detected state. Returns status code &#x60;403&#x60; if the authenticated user does not have permission to change vulnerability status.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the vulnerability.
     * @param postApiV4VulnerabilitiesIdRevertRequest The postApiV4VulnerabilitiesIdRevertRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4VulnerabilitiesIdRevertWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable PostApiV4VulnerabilitiesIdRevertRequest postApiV4VulnerabilitiesIdRevertRequest) throws RestClientResponseException {
        return postApiV4VulnerabilitiesIdRevertRequestCreation(id, postApiV4VulnerabilitiesIdRevertRequest);
    }

    /**
     * Update AI False Positive Detection results
     * Updates the confidence score and explanation for AI-based False Positive Detection flows.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param vulnerabilityId ID of the vulnerability.
     * @param postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest The postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequestCreation(@jakarta.annotation.Nonnull String vulnerabilityId, @jakarta.annotation.Nonnull PostApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest) throws RestClientResponseException {
        // verify the required parameter 'vulnerabilityId' is set
        if (vulnerabilityId == null) {
            throw new RestClientResponseException("Missing the required parameter 'vulnerabilityId' when calling postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetection", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest' is set
        if (postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest' when calling postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetection", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("vulnerability_id", vulnerabilityId);

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
        return apiClient.invokeAPI("/api/v4/vulnerabilities/{vulnerability_id}/flags/ai_detection", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update AI False Positive Detection results
     * Updates the confidence score and explanation for AI-based False Positive Detection flows.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param vulnerabilityId ID of the vulnerability.
     * @param postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest The postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetection(@jakarta.annotation.Nonnull String vulnerabilityId, @jakarta.annotation.Nonnull PostApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequestCreation(vulnerabilityId, postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest).body(localVarReturnType);
    }

    /**
     * Update AI False Positive Detection results
     * Updates the confidence score and explanation for AI-based False Positive Detection flows.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param vulnerabilityId ID of the vulnerability.
     * @param postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest The postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionWithHttpInfo(@jakarta.annotation.Nonnull String vulnerabilityId, @jakarta.annotation.Nonnull PostApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequestCreation(vulnerabilityId, postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest).toEntity(localVarReturnType);
    }

    /**
     * Update AI False Positive Detection results
     * Updates the confidence score and explanation for AI-based False Positive Detection flows.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param vulnerabilityId ID of the vulnerability.
     * @param postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest The postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionWithResponseSpec(@jakarta.annotation.Nonnull String vulnerabilityId, @jakarta.annotation.Nonnull PostApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest) throws RestClientResponseException {
        return postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequestCreation(vulnerabilityId, postApiV4VulnerabilitiesVulnerabilityIdFlagsAiDetectionRequest);
    }
}
