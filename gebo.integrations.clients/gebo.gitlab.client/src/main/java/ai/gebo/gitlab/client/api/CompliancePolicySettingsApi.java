package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAdminSecurityPolicySetting;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminSecurityCompliancePolicySettingsRequest;

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
public class CompliancePolicySettingsApi {
    private ApiClient apiClient;

    public CompliancePolicySettingsApi() {
        this(new ApiClient());
    }

    public CompliancePolicySettingsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve security policy settings
     * Retrieves the current security policy settings for this GitLab instance. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - 400 Bad Request
     * @return APIEntitiesAdminSecurityPolicySetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminSecurityCompliancePolicySettingsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesAdminSecurityPolicySetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/security/compliance_policy_settings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve security policy settings
     * Retrieves the current security policy settings for this GitLab instance. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - 400 Bad Request
     * @return APIEntitiesAdminSecurityPolicySetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAdminSecurityPolicySetting getApiV4AdminSecurityCompliancePolicySettings() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminSecurityPolicySetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminSecurityCompliancePolicySettingsRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve security policy settings
     * Retrieves the current security policy settings for this GitLab instance. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - 400 Bad Request
     * @return ResponseEntity&lt;APIEntitiesAdminSecurityPolicySetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAdminSecurityPolicySetting> getApiV4AdminSecurityCompliancePolicySettingsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminSecurityPolicySetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminSecurityCompliancePolicySettingsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve security policy settings
     * Retrieves the current security policy settings for this GitLab instance. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - 400 Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminSecurityCompliancePolicySettingsWithResponseSpec() throws RestClientResponseException {
        return getApiV4AdminSecurityCompliancePolicySettingsRequestCreation();
    }

    /**
     * Set status of an external control
     * Sets the status of a specified external control. Use this operation to inform GitLab that a control has passed, failed, or does not apply to the project. Controls with the &#x60;not_applicable&#x60; status do not count as passed or failed.  Requests must use HMAC, timestamp, and nonce authentication, with these headers:  | Header | Required | Description | |--------|----------|-------------| | &#x60;X-Gitlab-Timestamp&#x60; | Yes | Current Unix timestamp. Requests 15 seconds old or older are rejected. | | &#x60;X-Gitlab-Nonce&#x60; | Yes | Random string of exactly 32 characters, to prevent replay attacks. | | &#x60;X-Gitlab-Hmac-Sha256&#x60; | Yes | HMAC-SHA256 signature of the request. |  To compute the HMAC-SHA256 signature:  1. Concatenate these values in this order: the &#x60;X-Gitlab-Timestamp&#x60; value, the &#x60;X-Gitlab-Nonce&#x60; value,    the entire path of the request, and the &#x60;status&#x60; value formatted as &#x60;status&#x3D;&lt;status&gt;&#x60;. 1. Compute the HMAC-SHA256 of the concatenated string with your secret key.  This feature was introduced in GitLab 17.11. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param controlId ID of the control.
     * @param patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest The patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer controlId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdComplianceExternalControlsControlIdStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'controlId' is set
        if (controlId == null) {
            throw new RestClientResponseException("Missing the required parameter 'controlId' when calling patchApiV4ProjectsIdComplianceExternalControlsControlIdStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest' is set
        if (patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest' when calling patchApiV4ProjectsIdComplianceExternalControlsControlIdStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("control_id", controlId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/compliance_external_controls/{control_id}/status", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Set status of an external control
     * Sets the status of a specified external control. Use this operation to inform GitLab that a control has passed, failed, or does not apply to the project. Controls with the &#x60;not_applicable&#x60; status do not count as passed or failed.  Requests must use HMAC, timestamp, and nonce authentication, with these headers:  | Header | Required | Description | |--------|----------|-------------| | &#x60;X-Gitlab-Timestamp&#x60; | Yes | Current Unix timestamp. Requests 15 seconds old or older are rejected. | | &#x60;X-Gitlab-Nonce&#x60; | Yes | Random string of exactly 32 characters, to prevent replay attacks. | | &#x60;X-Gitlab-Hmac-Sha256&#x60; | Yes | HMAC-SHA256 signature of the request. |  To compute the HMAC-SHA256 signature:  1. Concatenate these values in this order: the &#x60;X-Gitlab-Timestamp&#x60; value, the &#x60;X-Gitlab-Nonce&#x60; value,    the entire path of the request, and the &#x60;status&#x60; value formatted as &#x60;status&#x3D;&lt;status&gt;&#x60;. 1. Compute the HMAC-SHA256 of the concatenated string with your secret key.  This feature was introduced in GitLab 17.11. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param controlId ID of the control.
     * @param patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest The patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void patchApiV4ProjectsIdComplianceExternalControlsControlIdStatus(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer controlId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequestCreation(id, controlId, patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest).body(localVarReturnType);
    }

    /**
     * Set status of an external control
     * Sets the status of a specified external control. Use this operation to inform GitLab that a control has passed, failed, or does not apply to the project. Controls with the &#x60;not_applicable&#x60; status do not count as passed or failed.  Requests must use HMAC, timestamp, and nonce authentication, with these headers:  | Header | Required | Description | |--------|----------|-------------| | &#x60;X-Gitlab-Timestamp&#x60; | Yes | Current Unix timestamp. Requests 15 seconds old or older are rejected. | | &#x60;X-Gitlab-Nonce&#x60; | Yes | Random string of exactly 32 characters, to prevent replay attacks. | | &#x60;X-Gitlab-Hmac-Sha256&#x60; | Yes | HMAC-SHA256 signature of the request. |  To compute the HMAC-SHA256 signature:  1. Concatenate these values in this order: the &#x60;X-Gitlab-Timestamp&#x60; value, the &#x60;X-Gitlab-Nonce&#x60; value,    the entire path of the request, and the &#x60;status&#x60; value formatted as &#x60;status&#x3D;&lt;status&gt;&#x60;. 1. Compute the HMAC-SHA256 of the concatenated string with your secret key.  This feature was introduced in GitLab 17.11. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param controlId ID of the control.
     * @param patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest The patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer controlId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequestCreation(id, controlId, patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest).toEntity(localVarReturnType);
    }

    /**
     * Set status of an external control
     * Sets the status of a specified external control. Use this operation to inform GitLab that a control has passed, failed, or does not apply to the project. Controls with the &#x60;not_applicable&#x60; status do not count as passed or failed.  Requests must use HMAC, timestamp, and nonce authentication, with these headers:  | Header | Required | Description | |--------|----------|-------------| | &#x60;X-Gitlab-Timestamp&#x60; | Yes | Current Unix timestamp. Requests 15 seconds old or older are rejected. | | &#x60;X-Gitlab-Nonce&#x60; | Yes | Random string of exactly 32 characters, to prevent replay attacks. | | &#x60;X-Gitlab-Hmac-Sha256&#x60; | Yes | HMAC-SHA256 signature of the request. |  To compute the HMAC-SHA256 signature:  1. Concatenate these values in this order: the &#x60;X-Gitlab-Timestamp&#x60; value, the &#x60;X-Gitlab-Nonce&#x60; value,    the entire path of the request, and the &#x60;status&#x60; value formatted as &#x60;status&#x3D;&lt;status&gt;&#x60;. 1. Compute the HMAC-SHA256 of the concatenated string with your secret key.  This feature was introduced in GitLab 17.11. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param controlId ID of the control.
     * @param patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest The patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer controlId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequestCreation(id, controlId, patchApiV4ProjectsIdComplianceExternalControlsControlIdStatusRequest);
    }

    /**
     * Update security policy settings
     * Updates the security policy settings for this GitLab instance. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - 400 Bad Request
     * <p><b>422</b> - 422 Unprocessable Entity
     * @param putApiV4AdminSecurityCompliancePolicySettingsRequest The putApiV4AdminSecurityCompliancePolicySettingsRequest parameter
     * @return APIEntitiesAdminSecurityPolicySetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminSecurityCompliancePolicySettingsRequestCreation(@jakarta.annotation.Nonnull PutApiV4AdminSecurityCompliancePolicySettingsRequest putApiV4AdminSecurityCompliancePolicySettingsRequest) throws RestClientResponseException {
        // verify the required parameter 'putApiV4AdminSecurityCompliancePolicySettingsRequest' is set
        if (putApiV4AdminSecurityCompliancePolicySettingsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4AdminSecurityCompliancePolicySettingsRequest' when calling putApiV4AdminSecurityCompliancePolicySettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAdminSecurityPolicySetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/security/compliance_policy_settings", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminSecurityCompliancePolicySettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update security policy settings
     * Updates the security policy settings for this GitLab instance. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - 400 Bad Request
     * <p><b>422</b> - 422 Unprocessable Entity
     * @param putApiV4AdminSecurityCompliancePolicySettingsRequest The putApiV4AdminSecurityCompliancePolicySettingsRequest parameter
     * @return APIEntitiesAdminSecurityPolicySetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAdminSecurityPolicySetting putApiV4AdminSecurityCompliancePolicySettings(@jakarta.annotation.Nonnull PutApiV4AdminSecurityCompliancePolicySettingsRequest putApiV4AdminSecurityCompliancePolicySettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminSecurityPolicySetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminSecurityCompliancePolicySettingsRequestCreation(putApiV4AdminSecurityCompliancePolicySettingsRequest).body(localVarReturnType);
    }

    /**
     * Update security policy settings
     * Updates the security policy settings for this GitLab instance. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - 400 Bad Request
     * <p><b>422</b> - 422 Unprocessable Entity
     * @param putApiV4AdminSecurityCompliancePolicySettingsRequest The putApiV4AdminSecurityCompliancePolicySettingsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAdminSecurityPolicySetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAdminSecurityPolicySetting> putApiV4AdminSecurityCompliancePolicySettingsWithHttpInfo(@jakarta.annotation.Nonnull PutApiV4AdminSecurityCompliancePolicySettingsRequest putApiV4AdminSecurityCompliancePolicySettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAdminSecurityPolicySetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminSecurityCompliancePolicySettingsRequestCreation(putApiV4AdminSecurityCompliancePolicySettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update security policy settings
     * Updates the security policy settings for this GitLab instance. This feature was introduced in GitLab 18.2.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>400</b> - 400 Bad Request
     * <p><b>422</b> - 422 Unprocessable Entity
     * @param putApiV4AdminSecurityCompliancePolicySettingsRequest The putApiV4AdminSecurityCompliancePolicySettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminSecurityCompliancePolicySettingsWithResponseSpec(@jakarta.annotation.Nonnull PutApiV4AdminSecurityCompliancePolicySettingsRequest putApiV4AdminSecurityCompliancePolicySettingsRequest) throws RestClientResponseException {
        return putApiV4AdminSecurityCompliancePolicySettingsRequestCreation(putApiV4AdminSecurityCompliancePolicySettingsRequest);
    }
}
