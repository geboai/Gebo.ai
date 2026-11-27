package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesSecurityVulnerabilityScanningSbomScan;
import java.io.File;
import ai.gebo.gitlab.client.model.PostApiV4JobsIdSbomScansAuthorizeRequest;
import ai.gebo.gitlab.client.model.PostApiV4JobsIdSbomScansSbomDigestRequest;

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
public class SoftwareCompositionAnalysisApi {
    private ApiClient apiClient;

    public SoftwareCompositionAnalysisApi() {
        this(new ApiClient());
    }

    public SoftwareCompositionAnalysisApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Download an SBOM scan result
     * Downloads an SBOM scan result file for a specified job.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Sbom Scan in progress
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>410</b> - Sbom Scan failed
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param sbomScanId ID of the SBOM scan.
     * @return APIEntitiesSecurityVulnerabilityScanningSbomScan
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4JobsIdSbomScansSbomScanIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer sbomScanId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4JobsIdSbomScansSbomScanId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sbomScanId' is set
        if (sbomScanId == null) {
            throw new RestClientResponseException("Missing the required parameter 'sbomScanId' when calling getApiV4JobsIdSbomScansSbomScanId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sbom_scan_id", sbomScanId);

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

        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/jobs/{id}/sbom_scans/{sbom_digest}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download an SBOM scan result
     * Downloads an SBOM scan result file for a specified job.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Sbom Scan in progress
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>410</b> - Sbom Scan failed
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param sbomScanId ID of the SBOM scan.
     * @return APIEntitiesSecurityVulnerabilityScanningSbomScan
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSecurityVulnerabilityScanningSbomScan getApiV4JobsIdSbomScansSbomScanId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer sbomScanId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4JobsIdSbomScansSbomScanIdRequestCreation(id, sbomScanId).body(localVarReturnType);
    }

    /**
     * Download an SBOM scan result
     * Downloads an SBOM scan result file for a specified job.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Sbom Scan in progress
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>410</b> - Sbom Scan failed
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param sbomScanId ID of the SBOM scan.
     * @return ResponseEntity&lt;APIEntitiesSecurityVulnerabilityScanningSbomScan&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSecurityVulnerabilityScanningSbomScan> getApiV4JobsIdSbomScansSbomScanIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer sbomScanId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4JobsIdSbomScansSbomScanIdRequestCreation(id, sbomScanId).toEntity(localVarReturnType);
    }

    /**
     * Download an SBOM scan result
     * Downloads an SBOM scan result file for a specified job.
     * <p><b>200</b> - OK
     * <p><b>202</b> - Sbom Scan in progress
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>410</b> - Sbom Scan failed
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param sbomScanId ID of the SBOM scan.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4JobsIdSbomScansSbomScanIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer sbomScanId) throws RestClientResponseException {
        return getApiV4JobsIdSbomScansSbomScanIdRequestCreation(id, sbomScanId);
    }

    /**
     * Upload an SBOM file
     * Uploads an SBOM file to be scanned for a specified job.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - File too large
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param _file The sbom file to upload
     * @param sbomDigest Digest corresponding to uploaded SBOM
     * @return APIEntitiesSecurityVulnerabilityScanningSbomScan
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4JobsIdSbomScansRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String sbomDigest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4JobsIdSbomScans", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4JobsIdSbomScans", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));
        if (sbomDigest != null) {
            formParams.add("sbom_digest", sbomDigest);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/jobs/{id}/sbom_scans", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload an SBOM file
     * Uploads an SBOM file to be scanned for a specified job.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - File too large
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param _file The sbom file to upload
     * @param sbomDigest Digest corresponding to uploaded SBOM
     * @return APIEntitiesSecurityVulnerabilityScanningSbomScan
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSecurityVulnerabilityScanningSbomScan postApiV4JobsIdSbomScans(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String sbomDigest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsIdSbomScansRequestCreation(id, _file, sbomDigest).body(localVarReturnType);
    }

    /**
     * Upload an SBOM file
     * Uploads an SBOM file to be scanned for a specified job.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - File too large
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param _file The sbom file to upload
     * @param sbomDigest Digest corresponding to uploaded SBOM
     * @return ResponseEntity&lt;APIEntitiesSecurityVulnerabilityScanningSbomScan&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSecurityVulnerabilityScanningSbomScan> postApiV4JobsIdSbomScansWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String sbomDigest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsIdSbomScansRequestCreation(id, _file, sbomDigest).toEntity(localVarReturnType);
    }

    /**
     * Upload an SBOM file
     * Uploads an SBOM file to be scanned for a specified job.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - File too large
     * <p><b>404</b> - Not Found
     * @param id ID of the job.
     * @param _file The sbom file to upload
     * @param sbomDigest Digest corresponding to uploaded SBOM
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4JobsIdSbomScansWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String sbomDigest) throws RestClientResponseException {
        return postApiV4JobsIdSbomScansRequestCreation(id, _file, sbomDigest);
    }

    /**
     * Authorize SBOM file upload
     * Authorizes uploading an SBOM file to be scanned for a specified job.
     * <p><b>200</b> - SBOM Upload allowed
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - File too large
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param postApiV4JobsIdSbomScansAuthorizeRequest The postApiV4JobsIdSbomScansAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4JobsIdSbomScansAuthorizeRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4JobsIdSbomScansAuthorizeRequest postApiV4JobsIdSbomScansAuthorizeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4JobsIdSbomScansAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/jobs/{id}/sbom_scans/authorize", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4JobsIdSbomScansAuthorizeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize SBOM file upload
     * Authorizes uploading an SBOM file to be scanned for a specified job.
     * <p><b>200</b> - SBOM Upload allowed
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - File too large
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param postApiV4JobsIdSbomScansAuthorizeRequest The postApiV4JobsIdSbomScansAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4JobsIdSbomScansAuthorize(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4JobsIdSbomScansAuthorizeRequest postApiV4JobsIdSbomScansAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4JobsIdSbomScansAuthorizeRequestCreation(id, postApiV4JobsIdSbomScansAuthorizeRequest).body(localVarReturnType);
    }

    /**
     * Authorize SBOM file upload
     * Authorizes uploading an SBOM file to be scanned for a specified job.
     * <p><b>200</b> - SBOM Upload allowed
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - File too large
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param postApiV4JobsIdSbomScansAuthorizeRequest The postApiV4JobsIdSbomScansAuthorizeRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4JobsIdSbomScansAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4JobsIdSbomScansAuthorizeRequest postApiV4JobsIdSbomScansAuthorizeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsIdSbomScansAuthorizeRequestCreation(id, postApiV4JobsIdSbomScansAuthorizeRequest).toEntity(localVarReturnType);
    }

    /**
     * Authorize SBOM file upload
     * Authorizes uploading an SBOM file to be scanned for a specified job.
     * <p><b>200</b> - SBOM Upload allowed
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - File too large
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the job.
     * @param postApiV4JobsIdSbomScansAuthorizeRequest The postApiV4JobsIdSbomScansAuthorizeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4JobsIdSbomScansAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4JobsIdSbomScansAuthorizeRequest postApiV4JobsIdSbomScansAuthorizeRequest) throws RestClientResponseException {
        return postApiV4JobsIdSbomScansAuthorizeRequestCreation(id, postApiV4JobsIdSbomScansAuthorizeRequest);
    }

    /**
     * Reuse SBOM scan results
     * Reuses existing SBOM scan results if scans for the specified digest already exist.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>410</b> - Result no longer available
     * @param id ID of the job.
     * @param sbomDigest SBOM digest that identifies an SBOM scan uniquely.
     * @param postApiV4JobsIdSbomScansSbomDigestRequest The postApiV4JobsIdSbomScansSbomDigestRequest parameter
     * @return APIEntitiesSecurityVulnerabilityScanningSbomScan
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4JobsIdSbomScansSbomDigestRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String sbomDigest, @jakarta.annotation.Nonnull PostApiV4JobsIdSbomScansSbomDigestRequest postApiV4JobsIdSbomScansSbomDigestRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4JobsIdSbomScansSbomDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sbomDigest' is set
        if (sbomDigest == null) {
            throw new RestClientResponseException("Missing the required parameter 'sbomDigest' when calling postApiV4JobsIdSbomScansSbomDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4JobsIdSbomScansSbomDigestRequest' is set
        if (postApiV4JobsIdSbomScansSbomDigestRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4JobsIdSbomScansSbomDigestRequest' when calling postApiV4JobsIdSbomScansSbomDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sbom_digest", sbomDigest);

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

        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/jobs/{id}/sbom_scans/{sbom_digest}", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4JobsIdSbomScansSbomDigestRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reuse SBOM scan results
     * Reuses existing SBOM scan results if scans for the specified digest already exist.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>410</b> - Result no longer available
     * @param id ID of the job.
     * @param sbomDigest SBOM digest that identifies an SBOM scan uniquely.
     * @param postApiV4JobsIdSbomScansSbomDigestRequest The postApiV4JobsIdSbomScansSbomDigestRequest parameter
     * @return APIEntitiesSecurityVulnerabilityScanningSbomScan
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSecurityVulnerabilityScanningSbomScan postApiV4JobsIdSbomScansSbomDigest(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String sbomDigest, @jakarta.annotation.Nonnull PostApiV4JobsIdSbomScansSbomDigestRequest postApiV4JobsIdSbomScansSbomDigestRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsIdSbomScansSbomDigestRequestCreation(id, sbomDigest, postApiV4JobsIdSbomScansSbomDigestRequest).body(localVarReturnType);
    }

    /**
     * Reuse SBOM scan results
     * Reuses existing SBOM scan results if scans for the specified digest already exist.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>410</b> - Result no longer available
     * @param id ID of the job.
     * @param sbomDigest SBOM digest that identifies an SBOM scan uniquely.
     * @param postApiV4JobsIdSbomScansSbomDigestRequest The postApiV4JobsIdSbomScansSbomDigestRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSecurityVulnerabilityScanningSbomScan&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSecurityVulnerabilityScanningSbomScan> postApiV4JobsIdSbomScansSbomDigestWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String sbomDigest, @jakarta.annotation.Nonnull PostApiV4JobsIdSbomScansSbomDigestRequest postApiV4JobsIdSbomScansSbomDigestRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSecurityVulnerabilityScanningSbomScan> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4JobsIdSbomScansSbomDigestRequestCreation(id, sbomDigest, postApiV4JobsIdSbomScansSbomDigestRequest).toEntity(localVarReturnType);
    }

    /**
     * Reuse SBOM scan results
     * Reuses existing SBOM scan results if scans for the specified digest already exist.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>410</b> - Result no longer available
     * @param id ID of the job.
     * @param sbomDigest SBOM digest that identifies an SBOM scan uniquely.
     * @param postApiV4JobsIdSbomScansSbomDigestRequest The postApiV4JobsIdSbomScansSbomDigestRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4JobsIdSbomScansSbomDigestWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String sbomDigest, @jakarta.annotation.Nonnull PostApiV4JobsIdSbomScansSbomDigestRequest postApiV4JobsIdSbomScansSbomDigestRequest) throws RestClientResponseException {
        return postApiV4JobsIdSbomScansSbomDigestRequestCreation(id, sbomDigest, postApiV4JobsIdSbomScansSbomDigestRequest);
    }
}
