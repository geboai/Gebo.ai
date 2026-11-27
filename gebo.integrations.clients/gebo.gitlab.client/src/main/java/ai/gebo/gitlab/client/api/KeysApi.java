package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGpgKey;
import ai.gebo.gitlab.client.model.APIEntitiesSSHKey;
import ai.gebo.gitlab.client.model.APIEntitiesSSHKeyWithUser;
import ai.gebo.gitlab.client.model.APIEntitiesSshCertificate;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdSshCertificatesRequest;
import ai.gebo.gitlab.client.model.PostApiV4UserGpgKeysRequest;
import ai.gebo.gitlab.client.model.PostApiV4UserKeysRequest;
import ai.gebo.gitlab.client.model.PostApiV4UsersIdGpgKeysRequest;
import ai.gebo.gitlab.client.model.PostApiV4UsersUserIdKeysRequest;

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
public class KeysApi {
    private ApiClient apiClient;

    public KeysApi() {
        this(new ApiClient());
    }

    public KeysApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a group SSH certificate
     * Deletes a specified group SSH certificate. This feature was introduced in GitLab 16.4.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * @param sshCertificatesId ID of the group SSH certificate.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdSshCertificatesSshCertificatesIdRequestCreation(@jakarta.annotation.Nonnull Integer sshCertificatesId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'sshCertificatesId' is set
        if (sshCertificatesId == null) {
            throw new RestClientResponseException("Missing the required parameter 'sshCertificatesId' when calling deleteApiV4GroupsIdSshCertificatesSshCertificatesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdSshCertificatesSshCertificatesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("ssh_certificates_id", sshCertificatesId);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/ssh_certificates/{ssh_certificates_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group SSH certificate
     * Deletes a specified group SSH certificate. This feature was introduced in GitLab 16.4.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * @param sshCertificatesId ID of the group SSH certificate.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdSshCertificatesSshCertificatesId(@jakarta.annotation.Nonnull Integer sshCertificatesId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdSshCertificatesSshCertificatesIdRequestCreation(sshCertificatesId, id).body(localVarReturnType);
    }

    /**
     * Delete a group SSH certificate
     * Deletes a specified group SSH certificate. This feature was introduced in GitLab 16.4.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * @param sshCertificatesId ID of the group SSH certificate.
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdSshCertificatesSshCertificatesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer sshCertificatesId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdSshCertificatesSshCertificatesIdRequestCreation(sshCertificatesId, id).toEntity(localVarReturnType);
    }

    /**
     * Delete a group SSH certificate
     * Deletes a specified group SSH certificate. This feature was introduced in GitLab 16.4.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * @param sshCertificatesId ID of the group SSH certificate.
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdSshCertificatesSshCertificatesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer sshCertificatesId, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4GroupsIdSshCertificatesSshCertificatesIdRequestCreation(sshCertificatesId, id);
    }

    /**
     * Delete a GPG key
     * Deletes a GPG key from your user account.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UserGpgKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling deleteApiV4UserGpgKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key_id", keyId);

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
        return apiClient.invokeAPI("/api/v4/user/gpg_keys/{key_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a GPG key
     * Deletes a GPG key from your user account.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4UserGpgKeysKeyId(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4UserGpgKeysKeyIdRequestCreation(keyId).body(localVarReturnType);
    }

    /**
     * Delete a GPG key
     * Deletes a GPG key from your user account.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4UserGpgKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UserGpgKeysKeyIdRequestCreation(keyId).toEntity(localVarReturnType);
    }

    /**
     * Delete a GPG key
     * Deletes a GPG key from your user account.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UserGpgKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return deleteApiV4UserGpgKeysKeyIdRequestCreation(keyId);
    }

    /**
     * Delete an SSH key
     * Deletes a specified SSH key from the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the SSH key.
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UserKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling deleteApiV4UserKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/keys/{key_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an SSH key
     * Deletes a specified SSH key from the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the SSH key.
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSSHKey deleteApiV4UserKeysKeyId(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UserKeysKeyIdRequestCreation(keyId).body(localVarReturnType);
    }

    /**
     * Delete an SSH key
     * Deletes a specified SSH key from the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the SSH key.
     * @return ResponseEntity&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSSHKey> deleteApiV4UserKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UserKeysKeyIdRequestCreation(keyId).toEntity(localVarReturnType);
    }

    /**
     * Delete an SSH key
     * Deletes a specified SSH key from the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the SSH key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UserKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return deleteApiV4UserKeysKeyIdRequestCreation(keyId);
    }

    /**
     * Delete a GPG key for a user
     * Deletes a GPG key from a specified user account. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UsersIdGpgKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4UsersIdGpgKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling deleteApiV4UsersIdGpgKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/gpg_keys/{key_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a GPG key for a user
     * Deletes a GPG key from a specified user account. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4UsersIdGpgKeysKeyId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4UsersIdGpgKeysKeyIdRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Delete a GPG key for a user
     * Deletes a GPG key from a specified user account. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4UsersIdGpgKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdGpgKeysKeyIdRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Delete a GPG key for a user
     * Deletes a GPG key from a specified user account. Administrators only.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UsersIdGpgKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return deleteApiV4UsersIdGpgKeysKeyIdRequestCreation(id, keyId);
    }

    /**
     * Delete an SSH key for a user
     * Deletes an SSH key from a specified user account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the SSH key.
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UsersIdKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4UsersIdKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling deleteApiV4UsersIdKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/keys/{key_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an SSH key for a user
     * Deletes an SSH key from a specified user account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the SSH key.
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSSHKey deleteApiV4UsersIdKeysKeyId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdKeysKeyIdRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Delete an SSH key for a user
     * Deletes an SSH key from a specified user account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the SSH key.
     * @return ResponseEntity&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSSHKey> deleteApiV4UsersIdKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UsersIdKeysKeyIdRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Delete an SSH key for a user
     * Deletes an SSH key from a specified user account. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the SSH key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UsersIdKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return deleteApiV4UsersIdKeysKeyIdRequestCreation(id, keyId);
    }

    /**
     * List all group SSH certificates
     * Lists all SSH certificates for a specified group. This feature was introduced in GitLab 16.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - 404 Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSshCertificate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdSshCertificatesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdSshCertificates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesSshCertificate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/ssh_certificates", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group SSH certificates
     * Lists all SSH certificates for a specified group. This feature was introduced in GitLab 16.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - 404 Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSshCertificate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSshCertificate> getApiV4GroupsIdSshCertificates(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSshCertificate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSshCertificatesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group SSH certificates
     * Lists all SSH certificates for a specified group. This feature was introduced in GitLab 16.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - 404 Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSshCertificate&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSshCertificate>> getApiV4GroupsIdSshCertificatesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSshCertificate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdSshCertificatesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group SSH certificates
     * Lists all SSH certificates for a specified group. This feature was introduced in GitLab 16.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - 404 Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdSshCertificatesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdSshCertificatesRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve user by SSH key fingerprint
     * Retrieves user by SSH key or deploy key fingerprint. For a deploy key, the response also includes the projects that use the key. Deploy keys are bound to the creating user. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param fingerprint Fingerprint of an SSH key or deploy key. URL-encode SHA256 fingerprints: &#x60;/&#x60; is &#x60;%2F&#x60; and &#x60;:&#x60; is &#x60;%3A&#x60;.
     * @return APIEntitiesSSHKeyWithUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4KeysRequestCreation(@jakarta.annotation.Nullable String fingerprint) throws RestClientResponseException {
        // verify the required parameter 'fingerprint' is set
        if (fingerprint == null) {
            throw new RestClientResponseException("Missing the required parameter 'fingerprint' when calling getApiV4Keys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "fingerprint", fingerprint));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesSSHKeyWithUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve user by SSH key fingerprint
     * Retrieves user by SSH key or deploy key fingerprint. For a deploy key, the response also includes the projects that use the key. Deploy keys are bound to the creating user. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param fingerprint Fingerprint of an SSH key or deploy key. URL-encode SHA256 fingerprints: &#x60;/&#x60; is &#x60;%2F&#x60; and &#x60;:&#x60; is &#x60;%3A&#x60;.
     * @return APIEntitiesSSHKeyWithUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSSHKeyWithUser getApiV4Keys(@jakarta.annotation.Nullable String fingerprint) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKeyWithUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4KeysRequestCreation(fingerprint).body(localVarReturnType);
    }

    /**
     * Retrieve user by SSH key fingerprint
     * Retrieves user by SSH key or deploy key fingerprint. For a deploy key, the response also includes the projects that use the key. Deploy keys are bound to the creating user. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param fingerprint Fingerprint of an SSH key or deploy key. URL-encode SHA256 fingerprints: &#x60;/&#x60; is &#x60;%2F&#x60; and &#x60;:&#x60; is &#x60;%3A&#x60;.
     * @return ResponseEntity&lt;APIEntitiesSSHKeyWithUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSSHKeyWithUser> getApiV4KeysWithHttpInfo(@jakarta.annotation.Nullable String fingerprint) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKeyWithUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4KeysRequestCreation(fingerprint).toEntity(localVarReturnType);
    }

    /**
     * Retrieve user by SSH key fingerprint
     * Retrieves user by SSH key or deploy key fingerprint. For a deploy key, the response also includes the projects that use the key. Deploy keys are bound to the creating user. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param fingerprint Fingerprint of an SSH key or deploy key. URL-encode SHA256 fingerprints: &#x60;/&#x60; is &#x60;%2F&#x60; and &#x60;:&#x60; is &#x60;%3A&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4KeysWithResponseSpec(@jakarta.annotation.Nullable String fingerprint) throws RestClientResponseException {
        return getApiV4KeysRequestCreation(fingerprint);
    }

    /**
     * Retrieve user by SSH key ID
     * Retrieves user by SSH key ID. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the SSH key.
     * @return APIEntitiesSSHKeyWithUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4KeysIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4KeysId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSSHKeyWithUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/keys/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve user by SSH key ID
     * Retrieves user by SSH key ID. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the SSH key.
     * @return APIEntitiesSSHKeyWithUser
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSSHKeyWithUser getApiV4KeysId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKeyWithUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4KeysIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve user by SSH key ID
     * Retrieves user by SSH key ID. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the SSH key.
     * @return ResponseEntity&lt;APIEntitiesSSHKeyWithUser&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSSHKeyWithUser> getApiV4KeysIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKeyWithUser> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4KeysIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve user by SSH key ID
     * Retrieves user by SSH key ID. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the SSH key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4KeysIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4KeysIdRequestCreation(id);
    }

    /**
     * List all GPG keys
     * Lists all GPG keys for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGpgKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserGpgKeysRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesGpgKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/gpg_keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all GPG keys
     * Lists all GPG keys for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGpgKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGpgKey> getApiV4UserGpgKeys(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGpgKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserGpgKeysRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all GPG keys
     * Lists all GPG keys for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGpgKey&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGpgKey>> getApiV4UserGpgKeysWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGpgKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserGpgKeysRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all GPG keys
     * Lists all GPG keys for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserGpgKeysWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UserGpgKeysRequestCreation(page, perPage);
    }

    /**
     * Retrieve a GPG key
     * Retrieves a GPG key for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @return APIEntitiesGpgKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserGpgKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling getApiV4UserGpgKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/gpg_keys/{key_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a GPG key
     * Retrieves a GPG key for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @return APIEntitiesGpgKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGpgKey getApiV4UserGpgKeysKeyId(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserGpgKeysKeyIdRequestCreation(keyId).body(localVarReturnType);
    }

    /**
     * Retrieve a GPG key
     * Retrieves a GPG key for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @return ResponseEntity&lt;APIEntitiesGpgKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGpgKey> getApiV4UserGpgKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserGpgKeysKeyIdRequestCreation(keyId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a GPG key
     * Retrieves a GPG key for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserGpgKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return getApiV4UserGpgKeysKeyIdRequestCreation(keyId);
    }

    /**
     * List all SSH keys
     * Lists all SSH keys for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserKeysRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesSSHKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all SSH keys
     * Lists all SSH keys for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSSHKey> getApiV4UserKeys(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSSHKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserKeysRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all SSH keys
     * Lists all SSH keys for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSSHKey&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSSHKey>> getApiV4UserKeysWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSSHKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserKeysRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all SSH keys
     * Lists all SSH keys for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserKeysWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UserKeysRequestCreation(page, perPage);
    }

    /**
     * Retrieve an SSH key
     * Retrieves a specified SSH key for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the SSH key.
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling getApiV4UserKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/keys/{key_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an SSH key
     * Retrieves a specified SSH key for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the SSH key.
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSSHKey getApiV4UserKeysKeyId(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserKeysKeyIdRequestCreation(keyId).body(localVarReturnType);
    }

    /**
     * Retrieve an SSH key
     * Retrieves a specified SSH key for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the SSH key.
     * @return ResponseEntity&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSSHKey> getApiV4UserKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserKeysKeyIdRequestCreation(keyId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an SSH key
     * Retrieves a specified SSH key for the currently authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the SSH key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return getApiV4UserKeysKeyIdRequestCreation(keyId);
    }

    /**
     * List all GPG keys for a user
     * Lists all GPG keys for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGpgKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdGpgKeysRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdGpgKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesGpgKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/gpg_keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all GPG keys for a user
     * Lists all GPG keys for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGpgKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGpgKey> getApiV4UsersIdGpgKeys(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGpgKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdGpgKeysRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all GPG keys for a user
     * Lists all GPG keys for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGpgKey&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGpgKey>> getApiV4UsersIdGpgKeysWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGpgKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdGpgKeysRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all GPG keys for a user
     * Lists all GPG keys for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdGpgKeysWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UsersIdGpgKeysRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a GPG key for a user
     * Retrieves a GPG key for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @return APIEntitiesGpgKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdGpgKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdGpgKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling getApiV4UsersIdGpgKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/gpg_keys/{key_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a GPG key for a user
     * Retrieves a GPG key for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @return APIEntitiesGpgKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGpgKey getApiV4UsersIdGpgKeysKeyId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdGpgKeysKeyIdRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Retrieve a GPG key for a user
     * Retrieves a GPG key for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @return ResponseEntity&lt;APIEntitiesGpgKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGpgKey> getApiV4UsersIdGpgKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdGpgKeysKeyIdRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a GPG key for a user
     * Retrieves a GPG key for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdGpgKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return getApiV4UsersIdGpgKeysKeyIdRequestCreation(id, keyId);
    }

    /**
     * Retrieve an SSH key for a user
     * Retrieves an SSH key for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the SSH key.
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersIdKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UsersIdKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling getApiV4UsersIdKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/keys/{key_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an SSH key for a user
     * Retrieves an SSH key for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the SSH key.
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSSHKey getApiV4UsersIdKeysKeyId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdKeysKeyIdRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Retrieve an SSH key for a user
     * Retrieves an SSH key for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the SSH key.
     * @return ResponseEntity&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSSHKey> getApiV4UsersIdKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersIdKeysKeyIdRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an SSH key for a user
     * Retrieves an SSH key for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the SSH key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersIdKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return getApiV4UsersIdKeysKeyIdRequestCreation(id, keyId);
    }

    /**
     * List all SSH keys for a user
     * Lists all SSH keys for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersUserIdKeysRequestCreation(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4UsersUserIdKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<List<APIEntitiesSSHKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all SSH keys for a user
     * Lists all SSH keys for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSSHKey> getApiV4UsersUserIdKeys(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSSHKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdKeysRequestCreation(userId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all SSH keys for a user
     * Lists all SSH keys for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesSSHKey&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSSHKey>> getApiV4UsersUserIdKeysWithHttpInfo(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSSHKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdKeysRequestCreation(userId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all SSH keys for a user
     * Lists all SSH keys for a specified user account. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersUserIdKeysWithResponseSpec(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UsersUserIdKeysRequestCreation(userId, page, perPage);
    }

    /**
     * Add a group SSH certificate
     * Adds a group SSH certificate for a specified group. This feature was introduced in GitLab 16.4.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdSshCertificatesRequest The postApiV4GroupsIdSshCertificatesRequest parameter
     * @return APIEntitiesSshCertificate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdSshCertificatesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdSshCertificatesRequest postApiV4GroupsIdSshCertificatesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdSshCertificates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdSshCertificatesRequest' is set
        if (postApiV4GroupsIdSshCertificatesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdSshCertificatesRequest' when calling postApiV4GroupsIdSshCertificates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSshCertificate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/ssh_certificates", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdSshCertificatesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a group SSH certificate
     * Adds a group SSH certificate for a specified group. This feature was introduced in GitLab 16.4.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdSshCertificatesRequest The postApiV4GroupsIdSshCertificatesRequest parameter
     * @return APIEntitiesSshCertificate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSshCertificate postApiV4GroupsIdSshCertificates(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdSshCertificatesRequest postApiV4GroupsIdSshCertificatesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSshCertificate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdSshCertificatesRequestCreation(id, postApiV4GroupsIdSshCertificatesRequest).body(localVarReturnType);
    }

    /**
     * Add a group SSH certificate
     * Adds a group SSH certificate for a specified group. This feature was introduced in GitLab 16.4.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdSshCertificatesRequest The postApiV4GroupsIdSshCertificatesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSshCertificate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSshCertificate> postApiV4GroupsIdSshCertificatesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdSshCertificatesRequest postApiV4GroupsIdSshCertificatesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSshCertificate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdSshCertificatesRequestCreation(id, postApiV4GroupsIdSshCertificatesRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a group SSH certificate
     * Adds a group SSH certificate for a specified group. This feature was introduced in GitLab 16.4.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdSshCertificatesRequest The postApiV4GroupsIdSshCertificatesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdSshCertificatesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdSshCertificatesRequest postApiV4GroupsIdSshCertificatesRequest) throws RestClientResponseException {
        return postApiV4GroupsIdSshCertificatesRequestCreation(id, postApiV4GroupsIdSshCertificatesRequest);
    }

    /**
     * Add a GPG key
     * Adds a GPG key for the currently authenticated user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserGpgKeysRequest The postApiV4UserGpgKeysRequest parameter
     * @return APIEntitiesGpgKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserGpgKeysRequestCreation(@jakarta.annotation.Nonnull PostApiV4UserGpgKeysRequest postApiV4UserGpgKeysRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UserGpgKeysRequest' is set
        if (postApiV4UserGpgKeysRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UserGpgKeysRequest' when calling postApiV4UserGpgKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/gpg_keys", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UserGpgKeysRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a GPG key
     * Adds a GPG key for the currently authenticated user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserGpgKeysRequest The postApiV4UserGpgKeysRequest parameter
     * @return APIEntitiesGpgKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGpgKey postApiV4UserGpgKeys(@jakarta.annotation.Nonnull PostApiV4UserGpgKeysRequest postApiV4UserGpgKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserGpgKeysRequestCreation(postApiV4UserGpgKeysRequest).body(localVarReturnType);
    }

    /**
     * Add a GPG key
     * Adds a GPG key for the currently authenticated user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserGpgKeysRequest The postApiV4UserGpgKeysRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGpgKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGpgKey> postApiV4UserGpgKeysWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UserGpgKeysRequest postApiV4UserGpgKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserGpgKeysRequestCreation(postApiV4UserGpgKeysRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a GPG key
     * Adds a GPG key for the currently authenticated user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserGpgKeysRequest The postApiV4UserGpgKeysRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserGpgKeysWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UserGpgKeysRequest postApiV4UserGpgKeysRequest) throws RestClientResponseException {
        return postApiV4UserGpgKeysRequestCreation(postApiV4UserGpgKeysRequest);
    }

    /**
     * Revoke a GPG key
     * This feature was added in GitLab 10.0
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserGpgKeysKeyIdRevokeRequestCreation(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling postApiV4UserGpgKeysKeyIdRevoke", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("key_id", keyId);

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
        return apiClient.invokeAPI("/api/v4/user/gpg_keys/{key_id}/revoke", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a GPG key
     * This feature was added in GitLab 10.0
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UserGpgKeysKeyIdRevoke(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UserGpgKeysKeyIdRevokeRequestCreation(keyId).body(localVarReturnType);
    }

    /**
     * Revoke a GPG key
     * This feature was added in GitLab 10.0
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UserGpgKeysKeyIdRevokeWithHttpInfo(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserGpgKeysKeyIdRevokeRequestCreation(keyId).toEntity(localVarReturnType);
    }

    /**
     * Revoke a GPG key
     * This feature was added in GitLab 10.0
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param keyId ID of the GPG key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserGpgKeysKeyIdRevokeWithResponseSpec(@jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return postApiV4UserGpgKeysKeyIdRevokeRequestCreation(keyId);
    }

    /**
     * Add an SSH key
     * Adds an SSH key for the currently authenticated user. If the key or its fingerprint is already in use, returns a validation error with the message &#x60;has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserKeysRequest The postApiV4UserKeysRequest parameter
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserKeysRequestCreation(@jakarta.annotation.Nonnull PostApiV4UserKeysRequest postApiV4UserKeysRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UserKeysRequest' is set
        if (postApiV4UserKeysRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UserKeysRequest' when calling postApiV4UserKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/keys", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UserKeysRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an SSH key
     * Adds an SSH key for the currently authenticated user. If the key or its fingerprint is already in use, returns a validation error with the message &#x60;has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserKeysRequest The postApiV4UserKeysRequest parameter
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSSHKey postApiV4UserKeys(@jakarta.annotation.Nonnull PostApiV4UserKeysRequest postApiV4UserKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserKeysRequestCreation(postApiV4UserKeysRequest).body(localVarReturnType);
    }

    /**
     * Add an SSH key
     * Adds an SSH key for the currently authenticated user. If the key or its fingerprint is already in use, returns a validation error with the message &#x60;has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserKeysRequest The postApiV4UserKeysRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSSHKey> postApiV4UserKeysWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UserKeysRequest postApiV4UserKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserKeysRequestCreation(postApiV4UserKeysRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an SSH key
     * Adds an SSH key for the currently authenticated user. If the key or its fingerprint is already in use, returns a validation error with the message &#x60;has already been taken&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserKeysRequest The postApiV4UserKeysRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserKeysWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UserKeysRequest postApiV4UserKeysRequest) throws RestClientResponseException {
        return postApiV4UserKeysRequestCreation(postApiV4UserKeysRequest);
    }

    /**
     * Add a GPG key for a user
     * Adds a GPG key for a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param postApiV4UsersIdGpgKeysRequest The postApiV4UsersIdGpgKeysRequest parameter
     * @return APIEntitiesGpgKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdGpgKeysRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4UsersIdGpgKeysRequest postApiV4UsersIdGpgKeysRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdGpgKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4UsersIdGpgKeysRequest' is set
        if (postApiV4UsersIdGpgKeysRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsersIdGpgKeysRequest' when calling postApiV4UsersIdGpgKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{id}/gpg_keys", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsersIdGpgKeysRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a GPG key for a user
     * Adds a GPG key for a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param postApiV4UsersIdGpgKeysRequest The postApiV4UsersIdGpgKeysRequest parameter
     * @return APIEntitiesGpgKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGpgKey postApiV4UsersIdGpgKeys(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4UsersIdGpgKeysRequest postApiV4UsersIdGpgKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdGpgKeysRequestCreation(id, postApiV4UsersIdGpgKeysRequest).body(localVarReturnType);
    }

    /**
     * Add a GPG key for a user
     * Adds a GPG key for a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param postApiV4UsersIdGpgKeysRequest The postApiV4UsersIdGpgKeysRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGpgKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGpgKey> postApiV4UsersIdGpgKeysWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4UsersIdGpgKeysRequest postApiV4UsersIdGpgKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGpgKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdGpgKeysRequestCreation(id, postApiV4UsersIdGpgKeysRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a GPG key for a user
     * Adds a GPG key for a specified user account. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param postApiV4UsersIdGpgKeysRequest The postApiV4UsersIdGpgKeysRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdGpgKeysWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull PostApiV4UsersIdGpgKeysRequest postApiV4UsersIdGpgKeysRequest) throws RestClientResponseException {
        return postApiV4UsersIdGpgKeysRequestCreation(id, postApiV4UsersIdGpgKeysRequest);
    }

    /**
     * Revoke a GPG key for a user.
     * Deletes a GPG key from a specified user account. Administrators only.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersIdGpgKeysKeyIdRevokeRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4UsersIdGpgKeysKeyIdRevoke", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling postApiV4UsersIdGpgKeysKeyIdRevoke", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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
        return apiClient.invokeAPI("/api/v4/users/{id}/gpg_keys/{key_id}/revoke", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke a GPG key for a user.
     * Deletes a GPG key from a specified user account. Administrators only.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4UsersIdGpgKeysKeyIdRevoke(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4UsersIdGpgKeysKeyIdRevokeRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Revoke a GPG key for a user.
     * Deletes a GPG key from a specified user account. Administrators only.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4UsersIdGpgKeysKeyIdRevokeWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersIdGpgKeysKeyIdRevokeRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Revoke a GPG key for a user.
     * Deletes a GPG key from a specified user account. Administrators only.
     * <p><b>202</b> - Accepted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the user.
     * @param keyId ID of the GPG key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersIdGpgKeysKeyIdRevokeWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return postApiV4UsersIdGpgKeysKeyIdRevokeRequestCreation(id, keyId);
    }

    /**
     * Add an SSH key for a user
     * Adds an SSH key for a specified user account. This also adds an audit event. If the key or its fingerprint is already in use, returns a validation error with the message &#x60;has already been taken&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdKeysRequest The postApiV4UsersUserIdKeysRequest parameter
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UsersUserIdKeysRequestCreation(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdKeysRequest postApiV4UsersUserIdKeysRequest) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling postApiV4UsersUserIdKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4UsersUserIdKeysRequest' is set
        if (postApiV4UsersUserIdKeysRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UsersUserIdKeysRequest' when calling postApiV4UsersUserIdKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/keys", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UsersUserIdKeysRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add an SSH key for a user
     * Adds an SSH key for a specified user account. This also adds an audit event. If the key or its fingerprint is already in use, returns a validation error with the message &#x60;has already been taken&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdKeysRequest The postApiV4UsersUserIdKeysRequest parameter
     * @return APIEntitiesSSHKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSSHKey postApiV4UsersUserIdKeys(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdKeysRequest postApiV4UsersUserIdKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersUserIdKeysRequestCreation(userId, postApiV4UsersUserIdKeysRequest).body(localVarReturnType);
    }

    /**
     * Add an SSH key for a user
     * Adds an SSH key for a specified user account. This also adds an audit event. If the key or its fingerprint is already in use, returns a validation error with the message &#x60;has already been taken&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdKeysRequest The postApiV4UsersUserIdKeysRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSSHKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSSHKey> postApiV4UsersUserIdKeysWithHttpInfo(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdKeysRequest postApiV4UsersUserIdKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSSHKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UsersUserIdKeysRequestCreation(userId, postApiV4UsersUserIdKeysRequest).toEntity(localVarReturnType);
    }

    /**
     * Add an SSH key for a user
     * Adds an SSH key for a specified user account. This also adds an audit event. If the key or its fingerprint is already in use, returns a validation error with the message &#x60;has already been taken&#x60;. Administrators only.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID of the user.
     * @param postApiV4UsersUserIdKeysRequest The postApiV4UsersUserIdKeysRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UsersUserIdKeysWithResponseSpec(@jakarta.annotation.Nonnull Integer userId, @jakarta.annotation.Nonnull PostApiV4UsersUserIdKeysRequest postApiV4UsersUserIdKeysRequest) throws RestClientResponseException {
        return postApiV4UsersUserIdKeysRequestCreation(userId, postApiV4UsersUserIdKeysRequest);
    }
}
