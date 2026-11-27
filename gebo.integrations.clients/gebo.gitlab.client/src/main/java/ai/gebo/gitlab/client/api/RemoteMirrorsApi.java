package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesRemoteMirror;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdRemoteMirrorsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdRemoteMirrorsMirrorIdRequest;

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
public class RemoteMirrorsApi {
    private ApiClient apiClient;

    public RemoteMirrorsApi() {
        this(new ApiClient());
    }

    public RemoteMirrorsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a remote mirror from a project
     * Deletes a specified remote mirror from a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRemoteMirrorsMirrorId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mirrorId' is set
        if (mirrorId == null) {
            throw new RestClientResponseException("Missing the required parameter 'mirrorId' when calling deleteApiV4ProjectsIdRemoteMirrorsMirrorId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("mirror_id", mirrorId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/remote_mirrors/{mirror_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a remote mirror from a project
     * Deletes a specified remote mirror from a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdRemoteMirrorsMirrorId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId).body(localVarReturnType);
    }

    /**
     * Delete a remote mirror from a project
     * Deletes a specified remote mirror from a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdRemoteMirrorsMirrorIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId).toEntity(localVarReturnType);
    }

    /**
     * Delete a remote mirror from a project
     * Deletes a specified remote mirror from a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRemoteMirrorsMirrorIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId);
    }

    /**
     * List all remote mirrors for a project
     * Lists all remote mirrors for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesRemoteMirror&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRemoteMirrorsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRemoteMirrors", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesRemoteMirror>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/remote_mirrors", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all remote mirrors for a project
     * Lists all remote mirrors for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesRemoteMirror&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesRemoteMirror> getApiV4ProjectsIdRemoteMirrors(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRemoteMirror>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRemoteMirrorsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all remote mirrors for a project
     * Lists all remote mirrors for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesRemoteMirror&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesRemoteMirror>> getApiV4ProjectsIdRemoteMirrorsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRemoteMirror>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRemoteMirrorsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all remote mirrors for a project
     * Lists all remote mirrors for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRemoteMirrorsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRemoteMirrorsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a remote mirror for a project
     * Retrieves a specified remote mirror for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @return APIEntitiesRemoteMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRemoteMirrorsMirrorId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mirrorId' is set
        if (mirrorId == null) {
            throw new RestClientResponseException("Missing the required parameter 'mirrorId' when calling getApiV4ProjectsIdRemoteMirrorsMirrorId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("mirror_id", mirrorId);

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

        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/remote_mirrors/{mirror_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a remote mirror for a project
     * Retrieves a specified remote mirror for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @return APIEntitiesRemoteMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRemoteMirror getApiV4ProjectsIdRemoteMirrorsMirrorId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId).body(localVarReturnType);
    }

    /**
     * Retrieve a remote mirror for a project
     * Retrieves a specified remote mirror for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @return ResponseEntity&lt;APIEntitiesRemoteMirror&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRemoteMirror> getApiV4ProjectsIdRemoteMirrorsMirrorIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a remote mirror for a project
     * Retrieves a specified remote mirror for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRemoteMirrorsMirrorIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        return getApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId);
    }

    /**
     * Retrieve a public key for a remote mirror
     * Retrieves the public key of a specified remote mirror that uses SSH authentication. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKeyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mirrorId' is set
        if (mirrorId == null) {
            throw new RestClientResponseException("Missing the required parameter 'mirrorId' when calling getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("mirror_id", mirrorId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/remote_mirrors/{mirror_id}/public_key", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a public key for a remote mirror
     * Retrieves the public key of a specified remote mirror that uses SSH authentication. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKey(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKeyRequestCreation(id, mirrorId).body(localVarReturnType);
    }

    /**
     * Retrieve a public key for a remote mirror
     * Retrieves the public key of a specified remote mirror that uses SSH authentication. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKeyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKeyRequestCreation(id, mirrorId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a public key for a remote mirror
     * Retrieves the public key of a specified remote mirror that uses SSH authentication. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKeyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        return getApiV4ProjectsIdRemoteMirrorsMirrorIdPublicKeyRequestCreation(id, mirrorId);
    }

    /**
     * Create a push mirror
     * Creates a push mirror. Each project can have a maximum of 10 enabled push mirrors.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRemoteMirrorsRequest The postApiV4ProjectsIdRemoteMirrorsRequest parameter
     * @return APIEntitiesRemoteMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRemoteMirrorsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRemoteMirrorsRequest postApiV4ProjectsIdRemoteMirrorsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRemoteMirrors", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdRemoteMirrorsRequest' is set
        if (postApiV4ProjectsIdRemoteMirrorsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdRemoteMirrorsRequest' when calling postApiV4ProjectsIdRemoteMirrors", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/remote_mirrors", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdRemoteMirrorsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a push mirror
     * Creates a push mirror. Each project can have a maximum of 10 enabled push mirrors.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRemoteMirrorsRequest The postApiV4ProjectsIdRemoteMirrorsRequest parameter
     * @return APIEntitiesRemoteMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRemoteMirror postApiV4ProjectsIdRemoteMirrors(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRemoteMirrorsRequest postApiV4ProjectsIdRemoteMirrorsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRemoteMirrorsRequestCreation(id, postApiV4ProjectsIdRemoteMirrorsRequest).body(localVarReturnType);
    }

    /**
     * Create a push mirror
     * Creates a push mirror. Each project can have a maximum of 10 enabled push mirrors.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRemoteMirrorsRequest The postApiV4ProjectsIdRemoteMirrorsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesRemoteMirror&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRemoteMirror> postApiV4ProjectsIdRemoteMirrorsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRemoteMirrorsRequest postApiV4ProjectsIdRemoteMirrorsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRemoteMirrorsRequestCreation(id, postApiV4ProjectsIdRemoteMirrorsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a push mirror
     * Creates a push mirror. Each project can have a maximum of 10 enabled push mirrors.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdRemoteMirrorsRequest The postApiV4ProjectsIdRemoteMirrorsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRemoteMirrorsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdRemoteMirrorsRequest postApiV4ProjectsIdRemoteMirrorsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdRemoteMirrorsRequestCreation(id, postApiV4ProjectsIdRemoteMirrorsRequest);
    }

    /**
     * Force push mirror update
     * Forces an update to a push mirror.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdRemoteMirrorsMirrorIdSyncRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdRemoteMirrorsMirrorIdSync", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mirrorId' is set
        if (mirrorId == null) {
            throw new RestClientResponseException("Missing the required parameter 'mirrorId' when calling postApiV4ProjectsIdRemoteMirrorsMirrorIdSync", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("mirror_id", mirrorId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/remote_mirrors/{mirror_id}/sync", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Force push mirror update
     * Forces an update to a push mirror.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdRemoteMirrorsMirrorIdSync(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdRemoteMirrorsMirrorIdSyncRequestCreation(id, mirrorId).body(localVarReturnType);
    }

    /**
     * Force push mirror update
     * Forces an update to a push mirror.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdRemoteMirrorsMirrorIdSyncWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdRemoteMirrorsMirrorIdSyncRequestCreation(id, mirrorId).toEntity(localVarReturnType);
    }

    /**
     * Force push mirror update
     * Forces an update to a push mirror.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdRemoteMirrorsMirrorIdSyncWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId) throws RestClientResponseException {
        return postApiV4ProjectsIdRemoteMirrorsMirrorIdSyncRequestCreation(id, mirrorId);
    }

    /**
     * Update a remote mirror in a project
     * Updates the configuration or operational status of a specified remote mirror.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @param putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest The putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest parameter
     * @return APIEntitiesRemoteMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId, @jakarta.annotation.Nullable PutApiV4ProjectsIdRemoteMirrorsMirrorIdRequest putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdRemoteMirrorsMirrorId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mirrorId' is set
        if (mirrorId == null) {
            throw new RestClientResponseException("Missing the required parameter 'mirrorId' when calling putApiV4ProjectsIdRemoteMirrorsMirrorId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("mirror_id", mirrorId);

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

        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/remote_mirrors/{mirror_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a remote mirror in a project
     * Updates the configuration or operational status of a specified remote mirror.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @param putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest The putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest parameter
     * @return APIEntitiesRemoteMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRemoteMirror putApiV4ProjectsIdRemoteMirrorsMirrorId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId, @jakarta.annotation.Nullable PutApiV4ProjectsIdRemoteMirrorsMirrorIdRequest putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId, putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest).body(localVarReturnType);
    }

    /**
     * Update a remote mirror in a project
     * Updates the configuration or operational status of a specified remote mirror.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @param putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest The putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesRemoteMirror&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRemoteMirror> putApiV4ProjectsIdRemoteMirrorsMirrorIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId, @jakarta.annotation.Nullable PutApiV4ProjectsIdRemoteMirrorsMirrorIdRequest putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRemoteMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId, putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a remote mirror in a project
     * Updates the configuration or operational status of a specified remote mirror.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param mirrorId ID of the remote mirror.
     * @param putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest The putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdRemoteMirrorsMirrorIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String mirrorId, @jakarta.annotation.Nullable PutApiV4ProjectsIdRemoteMirrorsMirrorIdRequest putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdRemoteMirrorsMirrorIdRequestCreation(id, mirrorId, putApiV4ProjectsIdRemoteMirrorsMirrorIdRequest);
    }
}
