package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesPullMirror;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdMirrorPullRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdMirrorPullRequest;

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
public class ProjectMirrorsApi {
    private ApiClient apiClient;

    public ProjectMirrorsApi() {
        this(new ApiClient());
    }

    public ProjectMirrorsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve project pull mirror details
     * Retrieves pull mirror details for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - The project is not mirrored
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesPullMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdMirrorPullRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdMirrorPull", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPullMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/mirror/pull", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve project pull mirror details
     * Retrieves pull mirror details for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - The project is not mirrored
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesPullMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPullMirror getApiV4ProjectsIdMirrorPull(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPullMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMirrorPullRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve project pull mirror details
     * Retrieves pull mirror details for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - The project is not mirrored
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesPullMirror&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPullMirror> getApiV4ProjectsIdMirrorPullWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPullMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdMirrorPullRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve project pull mirror details
     * Retrieves pull mirror details for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - The project is not mirrored
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdMirrorPullWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdMirrorPullRequestCreation(id);
    }

    /**
     * Start the pull mirroring process for a project
     * Starts the pull mirroring process for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - The project is not mirrored
     * <p><b>403</b> - Mirroring for the project is on pause
     * <p><b>422</b> - The pull request event is not processable
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMirrorPullRequest The postApiV4ProjectsIdMirrorPullRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdMirrorPullRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMirrorPullRequest postApiV4ProjectsIdMirrorPullRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdMirrorPull", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/mirror/pull", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdMirrorPullRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Start the pull mirroring process for a project
     * Starts the pull mirroring process for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - The project is not mirrored
     * <p><b>403</b> - Mirroring for the project is on pause
     * <p><b>422</b> - The pull request event is not processable
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMirrorPullRequest The postApiV4ProjectsIdMirrorPullRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdMirrorPull(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMirrorPullRequest postApiV4ProjectsIdMirrorPullRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdMirrorPullRequestCreation(id, postApiV4ProjectsIdMirrorPullRequest).body(localVarReturnType);
    }

    /**
     * Start the pull mirroring process for a project
     * Starts the pull mirroring process for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - The project is not mirrored
     * <p><b>403</b> - Mirroring for the project is on pause
     * <p><b>422</b> - The pull request event is not processable
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMirrorPullRequest The postApiV4ProjectsIdMirrorPullRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdMirrorPullWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMirrorPullRequest postApiV4ProjectsIdMirrorPullRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdMirrorPullRequestCreation(id, postApiV4ProjectsIdMirrorPullRequest).toEntity(localVarReturnType);
    }

    /**
     * Start the pull mirroring process for a project
     * Starts the pull mirroring process for a specified project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - The project is not mirrored
     * <p><b>403</b> - Mirroring for the project is on pause
     * <p><b>422</b> - The pull request event is not processable
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdMirrorPullRequest The postApiV4ProjectsIdMirrorPullRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdMirrorPullWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PostApiV4ProjectsIdMirrorPullRequest postApiV4ProjectsIdMirrorPullRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdMirrorPullRequestCreation(id, postApiV4ProjectsIdMirrorPullRequest);
    }

    /**
     * Update project pull mirroring settings
     * Updates pull mirroring settings for a specified project. This feature was introduced in GitLab 17.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Url is blocked: Only allowed schemes are http, https, ssh, git
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdMirrorPullRequest The putApiV4ProjectsIdMirrorPullRequest parameter
     * @return APIEntitiesPullMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdMirrorPullRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdMirrorPullRequest putApiV4ProjectsIdMirrorPullRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdMirrorPull", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesPullMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/mirror/pull", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdMirrorPullRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update project pull mirroring settings
     * Updates pull mirroring settings for a specified project. This feature was introduced in GitLab 17.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Url is blocked: Only allowed schemes are http, https, ssh, git
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdMirrorPullRequest The putApiV4ProjectsIdMirrorPullRequest parameter
     * @return APIEntitiesPullMirror
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPullMirror putApiV4ProjectsIdMirrorPull(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdMirrorPullRequest putApiV4ProjectsIdMirrorPullRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPullMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMirrorPullRequestCreation(id, putApiV4ProjectsIdMirrorPullRequest).body(localVarReturnType);
    }

    /**
     * Update project pull mirroring settings
     * Updates pull mirroring settings for a specified project. This feature was introduced in GitLab 17.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Url is blocked: Only allowed schemes are http, https, ssh, git
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdMirrorPullRequest The putApiV4ProjectsIdMirrorPullRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPullMirror&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPullMirror> putApiV4ProjectsIdMirrorPullWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdMirrorPullRequest putApiV4ProjectsIdMirrorPullRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPullMirror> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdMirrorPullRequestCreation(id, putApiV4ProjectsIdMirrorPullRequest).toEntity(localVarReturnType);
    }

    /**
     * Update project pull mirroring settings
     * Updates pull mirroring settings for a specified project. This feature was introduced in GitLab 17.5.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Url is blocked: Only allowed schemes are http, https, ssh, git
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param putApiV4ProjectsIdMirrorPullRequest The putApiV4ProjectsIdMirrorPullRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdMirrorPullWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PutApiV4ProjectsIdMirrorPullRequest putApiV4ProjectsIdMirrorPullRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdMirrorPullRequestCreation(id, putApiV4ProjectsIdMirrorPullRequest);
    }
}
