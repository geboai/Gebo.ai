package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesLdapGroup;
import ai.gebo.gitlab.client.model.APIEntitiesLdapGroupLink;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdLdapGroupLinksRequest;

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
public class LdapApi {
    private ApiClient apiClient;

    public LdapApi() {
        this(new ApiClient());
    }

    public LdapApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an LDAP group link with CN or filter
     * Deletes an LDAP group link using a CN or filter.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param provider Name of the provider for the LDAP group link.
     * @param cn CN of an LDAP group. Exactly one of &#x60;cn&#x60;, &#x60;filter&#x60; must be provided.
     * @param filter LDAP filter for the group. Exactly one of &#x60;cn&#x60;, &#x60;filter&#x60; must be provided.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdLdapGroupLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String cn, @jakarta.annotation.Nullable String filter) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdLdapGroupLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'provider' is set
        if (provider == null) {
            throw new RestClientResponseException("Missing the required parameter 'provider' when calling deleteApiV4GroupsIdLdapGroupLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cn", cn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "provider", provider));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/ldap_group_links", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an LDAP group link with CN or filter
     * Deletes an LDAP group link using a CN or filter.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param provider Name of the provider for the LDAP group link.
     * @param cn CN of an LDAP group. Exactly one of &#x60;cn&#x60;, &#x60;filter&#x60; must be provided.
     * @param filter LDAP filter for the group. Exactly one of &#x60;cn&#x60;, &#x60;filter&#x60; must be provided.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdLdapGroupLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String cn, @jakarta.annotation.Nullable String filter) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdLdapGroupLinksRequestCreation(id, provider, cn, filter).body(localVarReturnType);
    }

    /**
     * Delete an LDAP group link with CN or filter
     * Deletes an LDAP group link using a CN or filter.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param provider Name of the provider for the LDAP group link.
     * @param cn CN of an LDAP group. Exactly one of &#x60;cn&#x60;, &#x60;filter&#x60; must be provided.
     * @param filter LDAP filter for the group. Exactly one of &#x60;cn&#x60;, &#x60;filter&#x60; must be provided.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdLdapGroupLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String cn, @jakarta.annotation.Nullable String filter) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdLdapGroupLinksRequestCreation(id, provider, cn, filter).toEntity(localVarReturnType);
    }

    /**
     * Delete an LDAP group link with CN or filter
     * Deletes an LDAP group link using a CN or filter.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param provider Name of the provider for the LDAP group link.
     * @param cn CN of an LDAP group. Exactly one of &#x60;cn&#x60;, &#x60;filter&#x60; must be provided.
     * @param filter LDAP filter for the group. Exactly one of &#x60;cn&#x60;, &#x60;filter&#x60; must be provided.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdLdapGroupLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String provider, @jakarta.annotation.Nullable String cn, @jakarta.annotation.Nullable String filter) throws RestClientResponseException {
        return deleteApiV4GroupsIdLdapGroupLinksRequestCreation(id, provider, cn, filter);
    }

    /**
     * Delete LDAP group link
     * Deletes an LDAP group link. Deprecated. Scheduled for removal in a future release. Use &#x60;DELETE /groups/:id/ldap_group_links&#x60; instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param cn CN of an LDAP group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdLdapGroupLinksCnRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String cn) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdLdapGroupLinksCn", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'cn' is set
        if (cn == null) {
            throw new RestClientResponseException("Missing the required parameter 'cn' when calling deleteApiV4GroupsIdLdapGroupLinksCn", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("cn", cn);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/ldap_group_links/{cn}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete LDAP group link
     * Deletes an LDAP group link. Deprecated. Scheduled for removal in a future release. Use &#x60;DELETE /groups/:id/ldap_group_links&#x60; instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param cn CN of an LDAP group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdLdapGroupLinksCn(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String cn) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdLdapGroupLinksCnRequestCreation(id, cn).body(localVarReturnType);
    }

    /**
     * Delete LDAP group link
     * Deletes an LDAP group link. Deprecated. Scheduled for removal in a future release. Use &#x60;DELETE /groups/:id/ldap_group_links&#x60; instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param cn CN of an LDAP group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdLdapGroupLinksCnWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String cn) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdLdapGroupLinksCnRequestCreation(id, cn).toEntity(localVarReturnType);
    }

    /**
     * Delete LDAP group link
     * Deletes an LDAP group link. Deprecated. Scheduled for removal in a future release. Use &#x60;DELETE /groups/:id/ldap_group_links&#x60; instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param cn CN of an LDAP group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdLdapGroupLinksCnWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String cn) throws RestClientResponseException {
        return deleteApiV4GroupsIdLdapGroupLinksCnRequestCreation(id, cn);
    }

    /**
     * Delete LDAP group link
     * Deletes an LDAP group link for a specific LDAP provider. Deprecated. Scheduled for removal in a future release. Use &#x60;DELETE /groups/:id/ldap_group_links&#x60; instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param cn CN of an LDAP group.
     * @param provider Name of the provider for the LDAP group link.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdLdapGroupLinksProviderCnRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String cn, @jakarta.annotation.Nonnull String provider) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdLdapGroupLinksProviderCn", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'cn' is set
        if (cn == null) {
            throw new RestClientResponseException("Missing the required parameter 'cn' when calling deleteApiV4GroupsIdLdapGroupLinksProviderCn", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'provider' is set
        if (provider == null) {
            throw new RestClientResponseException("Missing the required parameter 'provider' when calling deleteApiV4GroupsIdLdapGroupLinksProviderCn", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("cn", cn);
        pathParams.put("provider", provider);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/ldap_group_links/{provider}/{cn}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete LDAP group link
     * Deletes an LDAP group link for a specific LDAP provider. Deprecated. Scheduled for removal in a future release. Use &#x60;DELETE /groups/:id/ldap_group_links&#x60; instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param cn CN of an LDAP group.
     * @param provider Name of the provider for the LDAP group link.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdLdapGroupLinksProviderCn(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String cn, @jakarta.annotation.Nonnull String provider) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdLdapGroupLinksProviderCnRequestCreation(id, cn, provider).body(localVarReturnType);
    }

    /**
     * Delete LDAP group link
     * Deletes an LDAP group link for a specific LDAP provider. Deprecated. Scheduled for removal in a future release. Use &#x60;DELETE /groups/:id/ldap_group_links&#x60; instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param cn CN of an LDAP group.
     * @param provider Name of the provider for the LDAP group link.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdLdapGroupLinksProviderCnWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String cn, @jakarta.annotation.Nonnull String provider) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdLdapGroupLinksProviderCnRequestCreation(id, cn, provider).toEntity(localVarReturnType);
    }

    /**
     * Delete LDAP group link
     * Deletes an LDAP group link for a specific LDAP provider. Deprecated. Scheduled for removal in a future release. Use &#x60;DELETE /groups/:id/ldap_group_links&#x60; instead.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param cn CN of an LDAP group.
     * @param provider Name of the provider for the LDAP group link.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdLdapGroupLinksProviderCnWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String cn, @jakarta.annotation.Nonnull String provider) throws RestClientResponseException {
        return deleteApiV4GroupsIdLdapGroupLinksProviderCnRequestCreation(id, cn, provider);
    }

    /**
     * List all LDAP group links
     * Lists all LDAP group links.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesLdapGroupLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdLdapGroupLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdLdapGroupLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesLdapGroupLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/ldap_group_links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all LDAP group links
     * Lists all LDAP group links.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesLdapGroupLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesLdapGroupLink> getApiV4GroupsIdLdapGroupLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesLdapGroupLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdLdapGroupLinksRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all LDAP group links
     * Lists all LDAP group links.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;List&lt;APIEntitiesLdapGroupLink&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesLdapGroupLink>> getApiV4GroupsIdLdapGroupLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesLdapGroupLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdLdapGroupLinksRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all LDAP group links
     * Lists all LDAP group links.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdLdapGroupLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdLdapGroupLinksRequestCreation(id);
    }

    /**
     * List all LDAP groups
     * Lists all LDAP groups on the GitLab instance. Limited to 20 results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search for a specific LDAP group.
     * @return List&lt;APIEntitiesLdapGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4LdapGroupsRequestCreation(@jakarta.annotation.Nullable String search) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesLdapGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ldap/groups", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all LDAP groups
     * Lists all LDAP groups on the GitLab instance. Limited to 20 results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search for a specific LDAP group.
     * @return List&lt;APIEntitiesLdapGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesLdapGroup> getApiV4LdapGroups(@jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesLdapGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LdapGroupsRequestCreation(search).body(localVarReturnType);
    }

    /**
     * List all LDAP groups
     * Lists all LDAP groups on the GitLab instance. Limited to 20 results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search for a specific LDAP group.
     * @return ResponseEntity&lt;List&lt;APIEntitiesLdapGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesLdapGroup>> getApiV4LdapGroupsWithHttpInfo(@jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesLdapGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LdapGroupsRequestCreation(search).toEntity(localVarReturnType);
    }

    /**
     * List all LDAP groups
     * Lists all LDAP groups on the GitLab instance. Limited to 20 results.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search for a specific LDAP group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4LdapGroupsWithResponseSpec(@jakarta.annotation.Nullable String search) throws RestClientResponseException {
        return getApiV4LdapGroupsRequestCreation(search);
    }

    /**
     * List all LDAP groups by provider
     * Lists all LDAP groups for the specified provider. Limited to 20 results.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param provider Name of the LDAP provider.
     * @param search Search for a specific LDAP group.
     * @return List&lt;APIEntitiesLdapGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4LdapProviderGroupsRequestCreation(@jakarta.annotation.Nonnull String provider, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        // verify the required parameter 'provider' is set
        if (provider == null) {
            throw new RestClientResponseException("Missing the required parameter 'provider' when calling getApiV4LdapProviderGroups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("provider", provider);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesLdapGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/ldap/{provider}/groups", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all LDAP groups by provider
     * Lists all LDAP groups for the specified provider. Limited to 20 results.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param provider Name of the LDAP provider.
     * @param search Search for a specific LDAP group.
     * @return List&lt;APIEntitiesLdapGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesLdapGroup> getApiV4LdapProviderGroups(@jakarta.annotation.Nonnull String provider, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesLdapGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LdapProviderGroupsRequestCreation(provider, search).body(localVarReturnType);
    }

    /**
     * List all LDAP groups by provider
     * Lists all LDAP groups for the specified provider. Limited to 20 results.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param provider Name of the LDAP provider.
     * @param search Search for a specific LDAP group.
     * @return ResponseEntity&lt;List&lt;APIEntitiesLdapGroup&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesLdapGroup>> getApiV4LdapProviderGroupsWithHttpInfo(@jakarta.annotation.Nonnull String provider, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesLdapGroup>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4LdapProviderGroupsRequestCreation(provider, search).toEntity(localVarReturnType);
    }

    /**
     * List all LDAP groups by provider
     * Lists all LDAP groups for the specified provider. Limited to 20 results.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param provider Name of the LDAP provider.
     * @param search Search for a specific LDAP group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4LdapProviderGroupsWithResponseSpec(@jakarta.annotation.Nonnull String provider, @jakarta.annotation.Nullable String search) throws RestClientResponseException {
        return getApiV4LdapProviderGroupsRequestCreation(provider, search);
    }

    /**
     * Add an LDAP group link with CN or filter
     * Adds an LDAP group link using a CN or filter.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdLdapGroupLinksRequest The postApiV4GroupsIdLdapGroupLinksRequest parameter
     * @return APIEntitiesLdapGroupLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdLdapGroupLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdLdapGroupLinksRequest postApiV4GroupsIdLdapGroupLinksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdLdapGroupLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdLdapGroupLinksRequest' is set
        if (postApiV4GroupsIdLdapGroupLinksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdLdapGroupLinksRequest' when calling postApiV4GroupsIdLdapGroupLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesLdapGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/ldap_group_links", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdLdapGroupLinksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an LDAP group link with CN or filter
     * Adds an LDAP group link using a CN or filter.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdLdapGroupLinksRequest The postApiV4GroupsIdLdapGroupLinksRequest parameter
     * @return APIEntitiesLdapGroupLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesLdapGroupLink postApiV4GroupsIdLdapGroupLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdLdapGroupLinksRequest postApiV4GroupsIdLdapGroupLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesLdapGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLdapGroupLinksRequestCreation(id, postApiV4GroupsIdLdapGroupLinksRequest).body(localVarReturnType);
    }

    /**
     * Add an LDAP group link with CN or filter
     * Adds an LDAP group link using a CN or filter.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdLdapGroupLinksRequest The postApiV4GroupsIdLdapGroupLinksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesLdapGroupLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesLdapGroupLink> postApiV4GroupsIdLdapGroupLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdLdapGroupLinksRequest postApiV4GroupsIdLdapGroupLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesLdapGroupLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLdapGroupLinksRequestCreation(id, postApiV4GroupsIdLdapGroupLinksRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an LDAP group link with CN or filter
     * Adds an LDAP group link using a CN or filter.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdLdapGroupLinksRequest The postApiV4GroupsIdLdapGroupLinksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdLdapGroupLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdLdapGroupLinksRequest postApiV4GroupsIdLdapGroupLinksRequest) throws RestClientResponseException {
        return postApiV4GroupsIdLdapGroupLinksRequestCreation(id, postApiV4GroupsIdLdapGroupLinksRequest);
    }

    /**
     * Sync a group with LDAP
     * Syncs a specified group with its linked LDAP group. You must be an administrator or have the Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdLdapSyncRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdLdapSync", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/ldap_sync", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Sync a group with LDAP
     * Syncs a specified group with its linked LDAP group. You must be an administrator or have the Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GroupsIdLdapSync(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GroupsIdLdapSyncRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Sync a group with LDAP
     * Syncs a specified group with its linked LDAP group. You must be an administrator or have the Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GroupsIdLdapSyncWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdLdapSyncRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Sync a group with LDAP
     * Syncs a specified group with its linked LDAP group. You must be an administrator or have the Owner role for the group.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdLdapSyncWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4GroupsIdLdapSyncRequestCreation(id);
    }
}
