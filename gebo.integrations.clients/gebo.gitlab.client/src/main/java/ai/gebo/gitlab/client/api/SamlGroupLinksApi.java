package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesSamlGroupLink;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdSamlGroupLinksRequest;

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
public class SamlGroupLinksApi {
    private ApiClient apiClient;

    public SamlGroupLinksApi() {
        this(new ApiClient());
    }

    public SamlGroupLinksApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete SAML group link
     * Deletes a SAML group link for the group
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Failed to delete SamlGroupLink record
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Multiple links found, provider parameter required
     * @param id ID or URL-encoded path of the group.
     * @param samlGroupName Name of the SAML group.
     * @param provider Unique [provider name](https://docs.gitlab.com/integration/saml/#configure-saml-support-in-gitlab) used to disambiguate when multiple links exist with the same name. Required when multiple links exist with the same &#x60;saml_group_name&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/548725) in GitLab 18.2.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdSamlGroupLinksSamlGroupNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String samlGroupName, @jakarta.annotation.Nullable String provider) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdSamlGroupLinksSamlGroupName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'samlGroupName' is set
        if (samlGroupName == null) {
            throw new RestClientResponseException("Missing the required parameter 'samlGroupName' when calling deleteApiV4GroupsIdSamlGroupLinksSamlGroupName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("saml_group_name", samlGroupName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "provider", provider));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml_group_links/{saml_group_name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete SAML group link
     * Deletes a SAML group link for the group
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Failed to delete SamlGroupLink record
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Multiple links found, provider parameter required
     * @param id ID or URL-encoded path of the group.
     * @param samlGroupName Name of the SAML group.
     * @param provider Unique [provider name](https://docs.gitlab.com/integration/saml/#configure-saml-support-in-gitlab) used to disambiguate when multiple links exist with the same name. Required when multiple links exist with the same &#x60;saml_group_name&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/548725) in GitLab 18.2.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdSamlGroupLinksSamlGroupName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String samlGroupName, @jakarta.annotation.Nullable String provider) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdSamlGroupLinksSamlGroupNameRequestCreation(id, samlGroupName, provider).body(localVarReturnType);
    }

    /**
     * Delete SAML group link
     * Deletes a SAML group link for the group
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Failed to delete SamlGroupLink record
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Multiple links found, provider parameter required
     * @param id ID or URL-encoded path of the group.
     * @param samlGroupName Name of the SAML group.
     * @param provider Unique [provider name](https://docs.gitlab.com/integration/saml/#configure-saml-support-in-gitlab) used to disambiguate when multiple links exist with the same name. Required when multiple links exist with the same &#x60;saml_group_name&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/548725) in GitLab 18.2.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdSamlGroupLinksSamlGroupNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String samlGroupName, @jakarta.annotation.Nullable String provider) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdSamlGroupLinksSamlGroupNameRequestCreation(id, samlGroupName, provider).toEntity(localVarReturnType);
    }

    /**
     * Delete SAML group link
     * Deletes a SAML group link for the group
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Failed to delete SamlGroupLink record
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Multiple links found, provider parameter required
     * @param id ID or URL-encoded path of the group.
     * @param samlGroupName Name of the SAML group.
     * @param provider Unique [provider name](https://docs.gitlab.com/integration/saml/#configure-saml-support-in-gitlab) used to disambiguate when multiple links exist with the same name. Required when multiple links exist with the same &#x60;saml_group_name&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/548725) in GitLab 18.2.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdSamlGroupLinksSamlGroupNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String samlGroupName, @jakarta.annotation.Nullable String provider) throws RestClientResponseException {
        return deleteApiV4GroupsIdSamlGroupLinksSamlGroupNameRequestCreation(id, samlGroupName, provider);
    }

    /**
     * Lists SAML group links
     * Get SAML group links for a group
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesSamlGroupLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdSamlGroupLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdSamlGroupLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesSamlGroupLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml_group_links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Lists SAML group links
     * Get SAML group links for a group
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesSamlGroupLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSamlGroupLink> getApiV4GroupsIdSamlGroupLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSamlGroupLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlGroupLinksRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Lists SAML group links
     * Get SAML group links for a group
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSamlGroupLink&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSamlGroupLink>> getApiV4GroupsIdSamlGroupLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSamlGroupLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlGroupLinksRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Lists SAML group links
     * Get SAML group links for a group
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdSamlGroupLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdSamlGroupLinksRequestCreation(id);
    }

    /**
     * Get SAML group link
     * Get a SAML group link for the group
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Multiple links found, provider parameter required
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param samlGroupName Name of the SAML group.
     * @param provider Unique [provider name](https://docs.gitlab.com/integration/saml/#configure-saml-support-in-gitlab) used to disambiguate when multiple links exist with the same name. Required when multiple links exist with the same &#x60;saml_group_name&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/548725) in GitLab 18.2.
     * @return APIEntitiesSamlGroupLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdSamlGroupLinksSamlGroupNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String samlGroupName, @jakarta.annotation.Nullable String provider) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdSamlGroupLinksSamlGroupName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'samlGroupName' is set
        if (samlGroupName == null) {
            throw new RestClientResponseException("Missing the required parameter 'samlGroupName' when calling getApiV4GroupsIdSamlGroupLinksSamlGroupName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("saml_group_name", samlGroupName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "provider", provider));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesSamlGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml_group_links/{saml_group_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get SAML group link
     * Get a SAML group link for the group
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Multiple links found, provider parameter required
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param samlGroupName Name of the SAML group.
     * @param provider Unique [provider name](https://docs.gitlab.com/integration/saml/#configure-saml-support-in-gitlab) used to disambiguate when multiple links exist with the same name. Required when multiple links exist with the same &#x60;saml_group_name&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/548725) in GitLab 18.2.
     * @return APIEntitiesSamlGroupLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSamlGroupLink getApiV4GroupsIdSamlGroupLinksSamlGroupName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String samlGroupName, @jakarta.annotation.Nullable String provider) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSamlGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlGroupLinksSamlGroupNameRequestCreation(id, samlGroupName, provider).body(localVarReturnType);
    }

    /**
     * Get SAML group link
     * Get a SAML group link for the group
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Multiple links found, provider parameter required
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param samlGroupName Name of the SAML group.
     * @param provider Unique [provider name](https://docs.gitlab.com/integration/saml/#configure-saml-support-in-gitlab) used to disambiguate when multiple links exist with the same name. Required when multiple links exist with the same &#x60;saml_group_name&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/548725) in GitLab 18.2.
     * @return ResponseEntity&lt;APIEntitiesSamlGroupLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSamlGroupLink> getApiV4GroupsIdSamlGroupLinksSamlGroupNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String samlGroupName, @jakarta.annotation.Nullable String provider) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSamlGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlGroupLinksSamlGroupNameRequestCreation(id, samlGroupName, provider).toEntity(localVarReturnType);
    }

    /**
     * Get SAML group link
     * Get a SAML group link for the group
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Multiple links found, provider parameter required
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param samlGroupName Name of the SAML group.
     * @param provider Unique [provider name](https://docs.gitlab.com/integration/saml/#configure-saml-support-in-gitlab) used to disambiguate when multiple links exist with the same name. Required when multiple links exist with the same &#x60;saml_group_name&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/548725) in GitLab 18.2.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdSamlGroupLinksSamlGroupNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String samlGroupName, @jakarta.annotation.Nullable String provider) throws RestClientResponseException {
        return getApiV4GroupsIdSamlGroupLinksSamlGroupNameRequestCreation(id, samlGroupName, provider);
    }

    /**
     * Add SAML group link
     * Add a SAML group link for a group
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdSamlGroupLinksRequest The postApiV4GroupsIdSamlGroupLinksRequest parameter
     * @return APIEntitiesSamlGroupLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdSamlGroupLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdSamlGroupLinksRequest postApiV4GroupsIdSamlGroupLinksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdSamlGroupLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdSamlGroupLinksRequest' is set
        if (postApiV4GroupsIdSamlGroupLinksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdSamlGroupLinksRequest' when calling postApiV4GroupsIdSamlGroupLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSamlGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml_group_links", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdSamlGroupLinksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add SAML group link
     * Add a SAML group link for a group
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdSamlGroupLinksRequest The postApiV4GroupsIdSamlGroupLinksRequest parameter
     * @return APIEntitiesSamlGroupLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSamlGroupLink postApiV4GroupsIdSamlGroupLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdSamlGroupLinksRequest postApiV4GroupsIdSamlGroupLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSamlGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdSamlGroupLinksRequestCreation(id, postApiV4GroupsIdSamlGroupLinksRequest).body(localVarReturnType);
    }

    /**
     * Add SAML group link
     * Add a SAML group link for a group
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdSamlGroupLinksRequest The postApiV4GroupsIdSamlGroupLinksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSamlGroupLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSamlGroupLink> postApiV4GroupsIdSamlGroupLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdSamlGroupLinksRequest postApiV4GroupsIdSamlGroupLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSamlGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdSamlGroupLinksRequestCreation(id, postApiV4GroupsIdSamlGroupLinksRequest).toEntity(localVarReturnType);
    }

    /**
     * Add SAML group link
     * Add a SAML group link for a group
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdSamlGroupLinksRequest The postApiV4GroupsIdSamlGroupLinksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdSamlGroupLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdSamlGroupLinksRequest postApiV4GroupsIdSamlGroupLinksRequest) throws RestClientResponseException {
        return postApiV4GroupsIdSamlGroupLinksRequestCreation(id, postApiV4GroupsIdSamlGroupLinksRequest);
    }
}
