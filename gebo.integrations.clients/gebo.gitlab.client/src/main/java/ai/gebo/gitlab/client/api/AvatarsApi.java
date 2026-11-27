package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAvatar;
import java.io.File;
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
public class AvatarsApi {
    private ApiClient apiClient;

    public AvatarsApi() {
        this(new ApiClient());
    }

    public AvatarsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Return avatar url for a user
     * Returns the avatar URL for the user with the given public email address. If no account is associated with the email address, returns the URL from an external avatar service. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Public visibility is restricted and the request is not authenticated
     * <p><b>400</b> - Bad Request
     * @param email Public email address of the user.
     * @param size Single pixel dimension for the avatar image. Only used for avatar lookups at Gravatar or a configured Libravatar server.
     * @return APIEntitiesAvatar
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AvatarRequestCreation(@jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable Integer size) throws RestClientResponseException {
        // verify the required parameter 'email' is set
        if (email == null) {
            throw new RestClientResponseException("Missing the required parameter 'email' when calling getApiV4Avatar", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "email", email));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAvatar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/avatar", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Return avatar url for a user
     * Returns the avatar URL for the user with the given public email address. If no account is associated with the email address, returns the URL from an external avatar service. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Public visibility is restricted and the request is not authenticated
     * <p><b>400</b> - Bad Request
     * @param email Public email address of the user.
     * @param size Single pixel dimension for the avatar image. Only used for avatar lookups at Gravatar or a configured Libravatar server.
     * @return APIEntitiesAvatar
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAvatar getApiV4Avatar(@jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable Integer size) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAvatar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AvatarRequestCreation(email, size).body(localVarReturnType);
    }

    /**
     * Return avatar url for a user
     * Returns the avatar URL for the user with the given public email address. If no account is associated with the email address, returns the URL from an external avatar service. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Public visibility is restricted and the request is not authenticated
     * <p><b>400</b> - Bad Request
     * @param email Public email address of the user.
     * @param size Single pixel dimension for the avatar image. Only used for avatar lookups at Gravatar or a configured Libravatar server.
     * @return ResponseEntity&lt;APIEntitiesAvatar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAvatar> getApiV4AvatarWithHttpInfo(@jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable Integer size) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAvatar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AvatarRequestCreation(email, size).toEntity(localVarReturnType);
    }

    /**
     * Return avatar url for a user
     * Returns the avatar URL for the user with the given public email address. If no account is associated with the email address, returns the URL from an external avatar service. This endpoint does not require authentication.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Public visibility is restricted and the request is not authenticated
     * <p><b>400</b> - Bad Request
     * @param email Public email address of the user.
     * @param size Single pixel dimension for the avatar image. Only used for avatar lookups at Gravatar or a configured Libravatar server.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AvatarWithResponseSpec(@jakarta.annotation.Nullable String email, @jakarta.annotation.Nullable Integer size) throws RestClientResponseException {
        return getApiV4AvatarRequestCreation(email, size);
    }

    /**
     * Download a group avatar
     * Downloads a group avatar image. You can access this endpoint without authentication if the group is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdAvatarRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdAvatar", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/avatar", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a group avatar
     * Downloads a group avatar image. You can access this endpoint without authentication if the group is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4GroupsIdAvatar(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAvatarRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Download a group avatar
     * Downloads a group avatar image. You can access this endpoint without authentication if the group is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4GroupsIdAvatarWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdAvatarRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Download a group avatar
     * Downloads a group avatar image. You can access this endpoint without authentication if the group is publicly accessible.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdAvatarWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4GroupsIdAvatarRequestCreation(id);
    }

    /**
     * Download a project avatar
     * Downloads a project avatar. You can access this endpoint without authentication if the project is publicly accessible. This feature was introduced in GitLab 16.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAvatarRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAvatar", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/avatar", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a project avatar
     * Downloads a project avatar. You can access this endpoint without authentication if the project is publicly accessible. This feature was introduced in GitLab 16.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdAvatar(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAvatarRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Download a project avatar
     * Downloads a project avatar. You can access this endpoint without authentication if the project is publicly accessible. This feature was introduced in GitLab 16.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdAvatarWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAvatarRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Download a project avatar
     * Downloads a project avatar. You can access this endpoint without authentication if the project is publicly accessible. This feature was introduced in GitLab 16.9.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAvatarWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdAvatarRequestCreation(id);
    }

    /**
     * Upload an avatar
     * Uploads an avatar for the currently authenticated user. The file must be 200 KB or smaller. The ideal image size is 192 x 192 pixels. Supported file types are &#x60;.avif&#x60;, &#x60;.bmp&#x60;, &#x60;.gif&#x60;, &#x60;.ico&#x60;, &#x60;.jpeg&#x60;, &#x60;.jpg&#x60;, &#x60;.png&#x60;, &#x60;.tiff&#x60;, and &#x60;.webp&#x60;. This feature was introduced in GitLab 17.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param avatar The avatar file (generated by Multipart middleware)
     * @return APIEntitiesAvatar
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4UserAvatarRequestCreation(@jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        // verify the required parameter 'avatar' is set
        if (avatar == null) {
            throw new RestClientResponseException("Missing the required parameter 'avatar' when calling putApiV4UserAvatar", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("avatar", new FileSystemResource(avatar));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAvatar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/avatar", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload an avatar
     * Uploads an avatar for the currently authenticated user. The file must be 200 KB or smaller. The ideal image size is 192 x 192 pixels. Supported file types are &#x60;.avif&#x60;, &#x60;.bmp&#x60;, &#x60;.gif&#x60;, &#x60;.ico&#x60;, &#x60;.jpeg&#x60;, &#x60;.jpg&#x60;, &#x60;.png&#x60;, &#x60;.tiff&#x60;, and &#x60;.webp&#x60;. This feature was introduced in GitLab 17.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param avatar The avatar file (generated by Multipart middleware)
     * @return APIEntitiesAvatar
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAvatar putApiV4UserAvatar(@jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAvatar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserAvatarRequestCreation(avatar).body(localVarReturnType);
    }

    /**
     * Upload an avatar
     * Uploads an avatar for the currently authenticated user. The file must be 200 KB or smaller. The ideal image size is 192 x 192 pixels. Supported file types are &#x60;.avif&#x60;, &#x60;.bmp&#x60;, &#x60;.gif&#x60;, &#x60;.ico&#x60;, &#x60;.jpeg&#x60;, &#x60;.jpg&#x60;, &#x60;.png&#x60;, &#x60;.tiff&#x60;, and &#x60;.webp&#x60;. This feature was introduced in GitLab 17.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param avatar The avatar file (generated by Multipart middleware)
     * @return ResponseEntity&lt;APIEntitiesAvatar&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAvatar> putApiV4UserAvatarWithHttpInfo(@jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAvatar> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserAvatarRequestCreation(avatar).toEntity(localVarReturnType);
    }

    /**
     * Upload an avatar
     * Uploads an avatar for the currently authenticated user. The file must be 200 KB or smaller. The ideal image size is 192 x 192 pixels. Supported file types are &#x60;.avif&#x60;, &#x60;.bmp&#x60;, &#x60;.gif&#x60;, &#x60;.ico&#x60;, &#x60;.jpeg&#x60;, &#x60;.jpg&#x60;, &#x60;.png&#x60;, &#x60;.tiff&#x60;, and &#x60;.webp&#x60;. This feature was introduced in GitLab 17.0.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param avatar The avatar file (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4UserAvatarWithResponseSpec(@jakarta.annotation.Nullable File avatar) throws RestClientResponseException {
        return putApiV4UserAvatarRequestCreation(avatar);
    }
}
