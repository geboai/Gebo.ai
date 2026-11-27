package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesIdentityDetail;
import ai.gebo.gitlab.client.model.PatchApiV4GroupsIdSamlUidRequest;
import ai.gebo.gitlab.client.model.PatchApiV4GroupsIdScimUidRequest;

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
public class ProviderIdentitiesApi {
    private ApiClient apiClient;

    public ProviderIdentitiesApi() {
        this(new ApiClient());
    }

    public ProviderIdentitiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a SAML identity
     * Deletes a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdSamlUidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdSamlUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uid' is set
        if (uid == null) {
            throw new RestClientResponseException("Missing the required parameter 'uid' when calling deleteApiV4GroupsIdSamlUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("uid", uid);

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

        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml/{uid}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a SAML identity
     * Deletes a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIdentityDetail deleteApiV4GroupsIdSamlUid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdSamlUidRequestCreation(id, uid).body(localVarReturnType);
    }

    /**
     * Delete a SAML identity
     * Deletes a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return ResponseEntity&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIdentityDetail> deleteApiV4GroupsIdSamlUidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdSamlUidRequestCreation(id, uid).toEntity(localVarReturnType);
    }

    /**
     * Delete a SAML identity
     * Deletes a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdSamlUidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        return deleteApiV4GroupsIdSamlUidRequestCreation(id, uid);
    }

    /**
     * Delete a SCIM identity
     * Deletes a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdScimUidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdScimUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uid' is set
        if (uid == null) {
            throw new RestClientResponseException("Missing the required parameter 'uid' when calling deleteApiV4GroupsIdScimUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("uid", uid);

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

        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/scim/{uid}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a SCIM identity
     * Deletes a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIdentityDetail deleteApiV4GroupsIdScimUid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdScimUidRequestCreation(id, uid).body(localVarReturnType);
    }

    /**
     * Delete a SCIM identity
     * Deletes a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return ResponseEntity&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIdentityDetail> deleteApiV4GroupsIdScimUidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdScimUidRequestCreation(id, uid).toEntity(localVarReturnType);
    }

    /**
     * Delete a SCIM identity
     * Deletes a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdScimUidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        return deleteApiV4GroupsIdScimUidRequestCreation(id, uid);
    }

    /**
     * Retrieve SAML identities for a group
     * Retrieves SAML identities for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdSamlIdentitiesRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdSamlIdentities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesIdentityDetail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml/identities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve SAML identities for a group
     * Retrieves SAML identities for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIdentityDetail> getApiV4GroupsIdSamlIdentities(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIdentityDetail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlIdentitiesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve SAML identities for a group
     * Retrieves SAML identities for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;List&lt;APIEntitiesIdentityDetail&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIdentityDetail>> getApiV4GroupsIdSamlIdentitiesWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIdentityDetail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlIdentitiesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve SAML identities for a group
     * Retrieves SAML identities for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdSamlIdentitiesWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4GroupsIdSamlIdentitiesRequestCreation(id);
    }

    /**
     * Retrieve a SAML identity
     * Retrieves a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdSamlUidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdSamlUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uid' is set
        if (uid == null) {
            throw new RestClientResponseException("Missing the required parameter 'uid' when calling getApiV4GroupsIdSamlUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("uid", uid);

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

        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml/{uid}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a SAML identity
     * Retrieves a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIdentityDetail getApiV4GroupsIdSamlUid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlUidRequestCreation(id, uid).body(localVarReturnType);
    }

    /**
     * Retrieve a SAML identity
     * Retrieves a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return ResponseEntity&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIdentityDetail> getApiV4GroupsIdSamlUidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSamlUidRequestCreation(id, uid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a SAML identity
     * Retrieves a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdSamlUidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        return getApiV4GroupsIdSamlUidRequestCreation(id, uid);
    }

    /**
     * Retrieve SCIM identities for a group
     * Retrieves SCIM identities for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdScimIdentitiesRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdScimIdentities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesIdentityDetail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/scim/identities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve SCIM identities for a group
     * Retrieves SCIM identities for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return List&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIdentityDetail> getApiV4GroupsIdScimIdentities(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIdentityDetail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdScimIdentitiesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve SCIM identities for a group
     * Retrieves SCIM identities for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;List&lt;APIEntitiesIdentityDetail&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIdentityDetail>> getApiV4GroupsIdScimIdentitiesWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIdentityDetail>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdScimIdentitiesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve SCIM identities for a group
     * Retrieves SCIM identities for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdScimIdentitiesWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4GroupsIdScimIdentitiesRequestCreation(id);
    }

    /**
     * Retrieve a SCIM identity
     * Retrieves a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdScimUidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdScimUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uid' is set
        if (uid == null) {
            throw new RestClientResponseException("Missing the required parameter 'uid' when calling getApiV4GroupsIdScimUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("uid", uid);

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

        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/scim/{uid}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a SCIM identity
     * Retrieves a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIdentityDetail getApiV4GroupsIdScimUid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdScimUidRequestCreation(id, uid).body(localVarReturnType);
    }

    /**
     * Retrieve a SCIM identity
     * Retrieves a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return ResponseEntity&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIdentityDetail> getApiV4GroupsIdScimUidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdScimUidRequestCreation(id, uid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a SCIM identity
     * Retrieves a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdScimUidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid) throws RestClientResponseException {
        return getApiV4GroupsIdScimUidRequestCreation(id, uid);
    }

    /**
     * Update &#x60;extern_uid&#x60; field for a SAML identity
     * Updates the &#x60;extern_uid&#x60; field for a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @param patchApiV4GroupsIdSamlUidRequest The patchApiV4GroupsIdSamlUidRequest parameter
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4GroupsIdSamlUidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid, @jakarta.annotation.Nonnull PatchApiV4GroupsIdSamlUidRequest patchApiV4GroupsIdSamlUidRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4GroupsIdSamlUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uid' is set
        if (uid == null) {
            throw new RestClientResponseException("Missing the required parameter 'uid' when calling patchApiV4GroupsIdSamlUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patchApiV4GroupsIdSamlUidRequest' is set
        if (patchApiV4GroupsIdSamlUidRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'patchApiV4GroupsIdSamlUidRequest' when calling patchApiV4GroupsIdSamlUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("uid", uid);

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

        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/saml/{uid}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4GroupsIdSamlUidRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update &#x60;extern_uid&#x60; field for a SAML identity
     * Updates the &#x60;extern_uid&#x60; field for a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @param patchApiV4GroupsIdSamlUidRequest The patchApiV4GroupsIdSamlUidRequest parameter
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIdentityDetail patchApiV4GroupsIdSamlUid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid, @jakarta.annotation.Nonnull PatchApiV4GroupsIdSamlUidRequest patchApiV4GroupsIdSamlUidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdSamlUidRequestCreation(id, uid, patchApiV4GroupsIdSamlUidRequest).body(localVarReturnType);
    }

    /**
     * Update &#x60;extern_uid&#x60; field for a SAML identity
     * Updates the &#x60;extern_uid&#x60; field for a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @param patchApiV4GroupsIdSamlUidRequest The patchApiV4GroupsIdSamlUidRequest parameter
     * @return ResponseEntity&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIdentityDetail> patchApiV4GroupsIdSamlUidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid, @jakarta.annotation.Nonnull PatchApiV4GroupsIdSamlUidRequest patchApiV4GroupsIdSamlUidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdSamlUidRequestCreation(id, uid, patchApiV4GroupsIdSamlUidRequest).toEntity(localVarReturnType);
    }

    /**
     * Update &#x60;extern_uid&#x60; field for a SAML identity
     * Updates the &#x60;extern_uid&#x60; field for a specified SAML identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @param patchApiV4GroupsIdSamlUidRequest The patchApiV4GroupsIdSamlUidRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4GroupsIdSamlUidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid, @jakarta.annotation.Nonnull PatchApiV4GroupsIdSamlUidRequest patchApiV4GroupsIdSamlUidRequest) throws RestClientResponseException {
        return patchApiV4GroupsIdSamlUidRequestCreation(id, uid, patchApiV4GroupsIdSamlUidRequest);
    }

    /**
     * Update &#x60;extern_uid&#x60; field for a SCIM identity
     * Updates the &#x60;extern_uid&#x60; field for a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @param patchApiV4GroupsIdScimUidRequest The patchApiV4GroupsIdScimUidRequest parameter
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4GroupsIdScimUidRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid, @jakarta.annotation.Nonnull PatchApiV4GroupsIdScimUidRequest patchApiV4GroupsIdScimUidRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4GroupsIdScimUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uid' is set
        if (uid == null) {
            throw new RestClientResponseException("Missing the required parameter 'uid' when calling patchApiV4GroupsIdScimUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patchApiV4GroupsIdScimUidRequest' is set
        if (patchApiV4GroupsIdScimUidRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'patchApiV4GroupsIdScimUidRequest' when calling patchApiV4GroupsIdScimUid", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("uid", uid);

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

        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/scim/{uid}", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4GroupsIdScimUidRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update &#x60;extern_uid&#x60; field for a SCIM identity
     * Updates the &#x60;extern_uid&#x60; field for a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @param patchApiV4GroupsIdScimUidRequest The patchApiV4GroupsIdScimUidRequest parameter
     * @return APIEntitiesIdentityDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIdentityDetail patchApiV4GroupsIdScimUid(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid, @jakarta.annotation.Nonnull PatchApiV4GroupsIdScimUidRequest patchApiV4GroupsIdScimUidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdScimUidRequestCreation(id, uid, patchApiV4GroupsIdScimUidRequest).body(localVarReturnType);
    }

    /**
     * Update &#x60;extern_uid&#x60; field for a SCIM identity
     * Updates the &#x60;extern_uid&#x60; field for a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @param patchApiV4GroupsIdScimUidRequest The patchApiV4GroupsIdScimUidRequest parameter
     * @return ResponseEntity&lt;APIEntitiesIdentityDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIdentityDetail> patchApiV4GroupsIdScimUidWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid, @jakarta.annotation.Nonnull PatchApiV4GroupsIdScimUidRequest patchApiV4GroupsIdScimUidRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIdentityDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4GroupsIdScimUidRequestCreation(id, uid, patchApiV4GroupsIdScimUidRequest).toEntity(localVarReturnType);
    }

    /**
     * Update &#x60;extern_uid&#x60; field for a SCIM identity
     * Updates the &#x60;extern_uid&#x60; field for a specified SCIM identity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param uid External UID of the user.
     * @param patchApiV4GroupsIdScimUidRequest The patchApiV4GroupsIdScimUidRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4GroupsIdScimUidWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String uid, @jakarta.annotation.Nonnull PatchApiV4GroupsIdScimUidRequest patchApiV4GroupsIdScimUidRequest) throws RestClientResponseException {
        return patchApiV4GroupsIdScimUidRequestCreation(id, uid, patchApiV4GroupsIdScimUidRequest);
    }
}
