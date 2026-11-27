package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesSupplyChainAttestation;
import ai.gebo.gitlab.client.model.APIEntitiesSupplyChainSigningCertificate;
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
public class AttestationsApi {
    private ApiClient apiClient;

    public AttestationsApi() {
        this(new ApiClient());
    }

    public AttestationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve an attestation bundle
     * Retrieves a specified attestation provenance bundle. The Sigstore bundle is returned in the response body. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Artifact SHA-256 not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param attestationIid Internal ID of the attestation, as returned by the list attestations API endpoint.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAttestationsAttestationIidDownloadRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter attestationIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAttestationsAttestationIidDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attestationIid' is set
        if (attestationIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'attestationIid' when calling getApiV4ProjectsIdAttestationsAttestationIidDownload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("attestation_iid", attestationIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/attestations/{attestation_iid}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an attestation bundle
     * Retrieves a specified attestation provenance bundle. The Sigstore bundle is returned in the response body. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Artifact SHA-256 not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param attestationIid Internal ID of the attestation, as returned by the list attestations API endpoint.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdAttestationsAttestationIidDownload(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter attestationIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdAttestationsAttestationIidDownloadRequestCreation(id, attestationIid).body(localVarReturnType);
    }

    /**
     * Retrieve an attestation bundle
     * Retrieves a specified attestation provenance bundle. The Sigstore bundle is returned in the response body. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Artifact SHA-256 not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param attestationIid Internal ID of the attestation, as returned by the list attestations API endpoint.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdAttestationsAttestationIidDownloadWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter attestationIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAttestationsAttestationIidDownloadRequestCreation(id, attestationIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an attestation bundle
     * Retrieves a specified attestation provenance bundle. The Sigstore bundle is returned in the response body. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Artifact SHA-256 not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param attestationIid Internal ID of the attestation, as returned by the list attestations API endpoint.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAttestationsAttestationIidDownloadWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter attestationIid) throws RestClientResponseException {
        return getApiV4ProjectsIdAttestationsAttestationIidDownloadRequestCreation(id, attestationIid);
    }

    /**
     * List all signing certificates for a project
     * Lists the unexpired certificate authority (CA) certificates of a project. GitLab uses these CAs to issue the certificates that sign attestations with the LOCAL_CA attestation method. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSupplyChainSigningCertificate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAttestationsSigningCertificatesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAttestationsSigningCertificates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesSupplyChainSigningCertificate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/attestations/signing_certificates", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all signing certificates for a project
     * Lists the unexpired certificate authority (CA) certificates of a project. GitLab uses these CAs to issue the certificates that sign attestations with the LOCAL_CA attestation method. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSupplyChainSigningCertificate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSupplyChainSigningCertificate> getApiV4ProjectsIdAttestationsSigningCertificates(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSupplyChainSigningCertificate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAttestationsSigningCertificatesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all signing certificates for a project
     * Lists the unexpired certificate authority (CA) certificates of a project. GitLab uses these CAs to issue the certificates that sign attestations with the LOCAL_CA attestation method. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSupplyChainSigningCertificate&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSupplyChainSigningCertificate>> getApiV4ProjectsIdAttestationsSigningCertificatesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSupplyChainSigningCertificate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAttestationsSigningCertificatesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all signing certificates for a project
     * Lists the unexpired certificate authority (CA) certificates of a project. GitLab uses these CAs to issue the certificates that sign attestations with the LOCAL_CA attestation method. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAttestationsSigningCertificatesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdAttestationsSigningCertificatesRequestCreation(id, page, perPage);
    }

    /**
     * List all attestations for a project
     * Lists all attestations for a specified project and artifact SHA-256 hash. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Artifact SHA-256 not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subjectDigest Hex-encoded SHA-256 hash of the artifact.
     * @return List&lt;APIEntitiesSupplyChainAttestation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAttestationsSubjectDigestRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String subjectDigest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAttestationsSubjectDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'subjectDigest' is set
        if (subjectDigest == null) {
            throw new RestClientResponseException("Missing the required parameter 'subjectDigest' when calling getApiV4ProjectsIdAttestationsSubjectDigest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("subject_digest", subjectDigest);

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

        ParameterizedTypeReference<List<APIEntitiesSupplyChainAttestation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/attestations/{subject_digest}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all attestations for a project
     * Lists all attestations for a specified project and artifact SHA-256 hash. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Artifact SHA-256 not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subjectDigest Hex-encoded SHA-256 hash of the artifact.
     * @return List&lt;APIEntitiesSupplyChainAttestation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSupplyChainAttestation> getApiV4ProjectsIdAttestationsSubjectDigest(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String subjectDigest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSupplyChainAttestation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAttestationsSubjectDigestRequestCreation(id, subjectDigest).body(localVarReturnType);
    }

    /**
     * List all attestations for a project
     * Lists all attestations for a specified project and artifact SHA-256 hash. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Artifact SHA-256 not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subjectDigest Hex-encoded SHA-256 hash of the artifact.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSupplyChainAttestation&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSupplyChainAttestation>> getApiV4ProjectsIdAttestationsSubjectDigestWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String subjectDigest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSupplyChainAttestation>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAttestationsSubjectDigestRequestCreation(id, subjectDigest).toEntity(localVarReturnType);
    }

    /**
     * List all attestations for a project
     * Lists all attestations for a specified project and artifact SHA-256 hash. This feature was introduced in GitLab 18.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Artifact SHA-256 not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param subjectDigest Hex-encoded SHA-256 hash of the artifact.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAttestationsSubjectDigestWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String subjectDigest) throws RestClientResponseException {
        return getApiV4ProjectsIdAttestationsSubjectDigestRequestCreation(id, subjectDigest);
    }
}
