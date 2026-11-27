package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesContainerRegistryRepository;
import ai.gebo.gitlab.client.model.APIEntitiesContainerRegistryTag;
import ai.gebo.gitlab.client.model.APIEntitiesContainerRegistryTagDetails;
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
public class ContainerRegistryApi {
    private ApiClient apiClient;

    public ContainerRegistryApi() {
        this(new ApiClient());
    }

    public ContainerRegistryApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete registry repository
     * Deletes a specified repository in the registry. This operation is executed asynchronously and might take some time to execute.
     * <p><b>204</b> - Success
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRegistryRepositoriesRepositoryId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryId' is set
        if (repositoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryId' when calling deleteApiV4ProjectsIdRegistryRepositoriesRepositoryId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("repository_id", repositoryId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/registry/repositories/{repository_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete registry repository
     * Deletes a specified repository in the registry. This operation is executed asynchronously and might take some time to execute.
     * <p><b>204</b> - Success
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdRegistryRepositoriesRepositoryId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdRequestCreation(id, repositoryId).body(localVarReturnType);
    }

    /**
     * Delete registry repository
     * Deletes a specified repository in the registry. This operation is executed asynchronously and might take some time to execute.
     * <p><b>204</b> - Success
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdRequestCreation(id, repositoryId).toEntity(localVarReturnType);
    }

    /**
     * Delete registry repository
     * Deletes a specified repository in the registry. This operation is executed asynchronously and might take some time to execute.
     * <p><b>204</b> - Success
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdRequestCreation(id, repositoryId);
    }

    /**
     * Delete multiple registry repository tags
     * Deletes multiple registry repository tags based on the specified criteria. The request is accepted and the tags are deleted asynchronously in the background. Tags that match &#x60;name_regex_delete&#x60; (or the deprecated &#x60;name_regex&#x60;) are deleted, except tags that match &#x60;name_regex_keep&#x60;, the &#x60;keep_n&#x60; most recent matching tags, tags newer than &#x60;older_than&#x60;, protected tags, and the tag named &#x60;latest&#x60;. You can run this at most once an hour for a given container repository. The number of tags deleted by one request is limited, so if the repository has many tags to delete, you might need to call this endpoint more than once. This operation does not delete blobs. To reclaim disk space, run garbage collection.
     * <p><b>202</b> - Success
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param nameRegexDelete [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to delete. Specify &#x60;.*&#x60; to delete all tags. At least one of &#x60;name_regex&#x60;, &#x60;name_regex_delete&#x60; must be provided.
     * @param nameRegex [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to delete. Specify &#x60;.*&#x60; to delete all tags. Deprecated. Use &#x60;name_regex_delete&#x60; instead. At least one of &#x60;name_regex&#x60;, &#x60;name_regex_delete&#x60; must be provided.
     * @param nameRegexKeep [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to keep. Overrides any matches from &#x60;name_regex_delete&#x60;. Setting to &#x60;.*&#x60; results in a no-op.
     * @param keepN Number of most recent tags with a matching name to keep.
     * @param olderThan Delete tags older than this age, such as &#x60;1h&#x60;, &#x60;1d&#x60;, or &#x60;1month&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nullable String nameRegexDelete, @jakarta.annotation.Nullable String nameRegex, @jakarta.annotation.Nullable String nameRegexKeep, @jakarta.annotation.Nullable Integer keepN, @jakarta.annotation.Nullable String olderThan) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryId' is set
        if (repositoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryId' when calling deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("repository_id", repositoryId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name_regex_delete", nameRegexDelete));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name_regex", nameRegex));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name_regex_keep", nameRegexKeep));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "keep_n", keepN));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "older_than", olderThan));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/registry/repositories/{repository_id}/tags", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete multiple registry repository tags
     * Deletes multiple registry repository tags based on the specified criteria. The request is accepted and the tags are deleted asynchronously in the background. Tags that match &#x60;name_regex_delete&#x60; (or the deprecated &#x60;name_regex&#x60;) are deleted, except tags that match &#x60;name_regex_keep&#x60;, the &#x60;keep_n&#x60; most recent matching tags, tags newer than &#x60;older_than&#x60;, protected tags, and the tag named &#x60;latest&#x60;. You can run this at most once an hour for a given container repository. The number of tags deleted by one request is limited, so if the repository has many tags to delete, you might need to call this endpoint more than once. This operation does not delete blobs. To reclaim disk space, run garbage collection.
     * <p><b>202</b> - Success
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param nameRegexDelete [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to delete. Specify &#x60;.*&#x60; to delete all tags. At least one of &#x60;name_regex&#x60;, &#x60;name_regex_delete&#x60; must be provided.
     * @param nameRegex [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to delete. Specify &#x60;.*&#x60; to delete all tags. Deprecated. Use &#x60;name_regex_delete&#x60; instead. At least one of &#x60;name_regex&#x60;, &#x60;name_regex_delete&#x60; must be provided.
     * @param nameRegexKeep [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to keep. Overrides any matches from &#x60;name_regex_delete&#x60;. Setting to &#x60;.*&#x60; results in a no-op.
     * @param keepN Number of most recent tags with a matching name to keep.
     * @param olderThan Delete tags older than this age, such as &#x60;1h&#x60;, &#x60;1d&#x60;, or &#x60;1month&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nullable String nameRegexDelete, @jakarta.annotation.Nullable String nameRegex, @jakarta.annotation.Nullable String nameRegexKeep, @jakarta.annotation.Nullable Integer keepN, @jakarta.annotation.Nullable String olderThan) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsRequestCreation(id, repositoryId, nameRegexDelete, nameRegex, nameRegexKeep, keepN, olderThan).body(localVarReturnType);
    }

    /**
     * Delete multiple registry repository tags
     * Deletes multiple registry repository tags based on the specified criteria. The request is accepted and the tags are deleted asynchronously in the background. Tags that match &#x60;name_regex_delete&#x60; (or the deprecated &#x60;name_regex&#x60;) are deleted, except tags that match &#x60;name_regex_keep&#x60;, the &#x60;keep_n&#x60; most recent matching tags, tags newer than &#x60;older_than&#x60;, protected tags, and the tag named &#x60;latest&#x60;. You can run this at most once an hour for a given container repository. The number of tags deleted by one request is limited, so if the repository has many tags to delete, you might need to call this endpoint more than once. This operation does not delete blobs. To reclaim disk space, run garbage collection.
     * <p><b>202</b> - Success
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param nameRegexDelete [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to delete. Specify &#x60;.*&#x60; to delete all tags. At least one of &#x60;name_regex&#x60;, &#x60;name_regex_delete&#x60; must be provided.
     * @param nameRegex [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to delete. Specify &#x60;.*&#x60; to delete all tags. Deprecated. Use &#x60;name_regex_delete&#x60; instead. At least one of &#x60;name_regex&#x60;, &#x60;name_regex_delete&#x60; must be provided.
     * @param nameRegexKeep [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to keep. Overrides any matches from &#x60;name_regex_delete&#x60;. Setting to &#x60;.*&#x60; results in a no-op.
     * @param keepN Number of most recent tags with a matching name to keep.
     * @param olderThan Delete tags older than this age, such as &#x60;1h&#x60;, &#x60;1d&#x60;, or &#x60;1month&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nullable String nameRegexDelete, @jakarta.annotation.Nullable String nameRegex, @jakarta.annotation.Nullable String nameRegexKeep, @jakarta.annotation.Nullable Integer keepN, @jakarta.annotation.Nullable String olderThan) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsRequestCreation(id, repositoryId, nameRegexDelete, nameRegex, nameRegexKeep, keepN, olderThan).toEntity(localVarReturnType);
    }

    /**
     * Delete multiple registry repository tags
     * Deletes multiple registry repository tags based on the specified criteria. The request is accepted and the tags are deleted asynchronously in the background. Tags that match &#x60;name_regex_delete&#x60; (or the deprecated &#x60;name_regex&#x60;) are deleted, except tags that match &#x60;name_regex_keep&#x60;, the &#x60;keep_n&#x60; most recent matching tags, tags newer than &#x60;older_than&#x60;, protected tags, and the tag named &#x60;latest&#x60;. You can run this at most once an hour for a given container repository. The number of tags deleted by one request is limited, so if the repository has many tags to delete, you might need to call this endpoint more than once. This operation does not delete blobs. To reclaim disk space, run garbage collection.
     * <p><b>202</b> - Success
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param nameRegexDelete [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to delete. Specify &#x60;.*&#x60; to delete all tags. At least one of &#x60;name_regex&#x60;, &#x60;name_regex_delete&#x60; must be provided.
     * @param nameRegex [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to delete. Specify &#x60;.*&#x60; to delete all tags. Deprecated. Use &#x60;name_regex_delete&#x60; instead. At least one of &#x60;name_regex&#x60;, &#x60;name_regex_delete&#x60; must be provided.
     * @param nameRegexKeep [RE2](https://github.com/google/re2/wiki/Syntax) regular expression of the tag names to keep. Overrides any matches from &#x60;name_regex_delete&#x60;. Setting to &#x60;.*&#x60; results in a no-op.
     * @param keepN Number of most recent tags with a matching name to keep.
     * @param olderThan Delete tags older than this age, such as &#x60;1h&#x60;, &#x60;1d&#x60;, or &#x60;1month&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nullable String nameRegexDelete, @jakarta.annotation.Nullable String nameRegex, @jakarta.annotation.Nullable String nameRegexKeep, @jakarta.annotation.Nullable Integer keepN, @jakarta.annotation.Nullable String olderThan) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsRequestCreation(id, repositoryId, nameRegexDelete, nameRegex, nameRegexKeep, keepN, olderThan);
    }

    /**
     * Delete a registry repository tag
     * Deletes a specified container registry repository tag. This operation does not delete blobs. To reclaim disk space, run garbage collection.
     * <p><b>204</b> - Success
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param tagName Name of the tag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryId' is set
        if (repositoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryId' when calling deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("repository_id", repositoryId);
        pathParams.put("tag_name", tagName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/registry/repositories/{repository_id}/tags/{tag_name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a registry repository tag
     * Deletes a specified container registry repository tag. This operation does not delete blobs. To reclaim disk space, run garbage collection.
     * <p><b>204</b> - Success
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param tagName Name of the tag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameRequestCreation(id, repositoryId, tagName).body(localVarReturnType);
    }

    /**
     * Delete a registry repository tag
     * Deletes a specified container registry repository tag. This operation does not delete blobs. To reclaim disk space, run garbage collection.
     * <p><b>204</b> - Success
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param tagName Name of the tag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameRequestCreation(id, repositoryId, tagName).toEntity(localVarReturnType);
    }

    /**
     * Delete a registry repository tag
     * Deletes a specified container registry repository tag. This operation does not delete blobs. To reclaim disk space, run garbage collection.
     * <p><b>204</b> - Success
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param tagName Name of the tag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        return deleteApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameRequestCreation(id, repositoryId, tagName);
    }

    /**
     * List all registry repositories for a group
     * Lists all registry repositories for a specified group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesContainerRegistryRepository&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdRegistryRepositoriesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdRegistryRepositories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesContainerRegistryRepository>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/registry/repositories", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all registry repositories for a group
     * Lists all registry repositories for a specified group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesContainerRegistryRepository&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesContainerRegistryRepository> getApiV4GroupsIdRegistryRepositories(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesContainerRegistryRepository>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRegistryRepositoriesRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all registry repositories for a group
     * Lists all registry repositories for a specified group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesContainerRegistryRepository&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesContainerRegistryRepository>> getApiV4GroupsIdRegistryRepositoriesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesContainerRegistryRepository>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdRegistryRepositoriesRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all registry repositories for a group
     * Lists all registry repositories for a specified group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Group Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdRegistryRepositoriesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdRegistryRepositoriesRequestCreation(id, page, perPage);
    }

    /**
     * List all registry repositories for a project
     * Lists all registry repositories for a specified project. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param tags If &#x60;true&#x60;, includes an array of &#x60;tags&#x60; in the response.
     * @param tagsCount If &#x60;true&#x60;, includes &#x60;tags_count&#x60; in the response.
     * @return List&lt;APIEntitiesContainerRegistryRepository&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRegistryRepositoriesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean tags, @jakarta.annotation.Nullable Boolean tagsCount) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRegistryRepositories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tags", tags));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tags_count", tagsCount));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesContainerRegistryRepository>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/registry/repositories", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all registry repositories for a project
     * Lists all registry repositories for a specified project. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param tags If &#x60;true&#x60;, includes an array of &#x60;tags&#x60; in the response.
     * @param tagsCount If &#x60;true&#x60;, includes &#x60;tags_count&#x60; in the response.
     * @return List&lt;APIEntitiesContainerRegistryRepository&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesContainerRegistryRepository> getApiV4ProjectsIdRegistryRepositories(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean tags, @jakarta.annotation.Nullable Boolean tagsCount) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesContainerRegistryRepository>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRegistryRepositoriesRequestCreation(id, page, perPage, tags, tagsCount).body(localVarReturnType);
    }

    /**
     * List all registry repositories for a project
     * Lists all registry repositories for a specified project. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param tags If &#x60;true&#x60;, includes an array of &#x60;tags&#x60; in the response.
     * @param tagsCount If &#x60;true&#x60;, includes &#x60;tags_count&#x60; in the response.
     * @return ResponseEntity&lt;List&lt;APIEntitiesContainerRegistryRepository&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesContainerRegistryRepository>> getApiV4ProjectsIdRegistryRepositoriesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean tags, @jakarta.annotation.Nullable Boolean tagsCount) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesContainerRegistryRepository>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRegistryRepositoriesRequestCreation(id, page, perPage, tags, tagsCount).toEntity(localVarReturnType);
    }

    /**
     * List all registry repositories for a project
     * Lists all registry repositories for a specified project. Responses are paginated and return 20 results by default.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param tags If &#x60;true&#x60;, includes an array of &#x60;tags&#x60; in the response.
     * @param tagsCount If &#x60;true&#x60;, includes &#x60;tags_count&#x60; in the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRegistryRepositoriesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean tags, @jakarta.annotation.Nullable Boolean tagsCount) throws RestClientResponseException {
        return getApiV4ProjectsIdRegistryRepositoriesRequestCreation(id, page, perPage, tags, tagsCount);
    }

    /**
     * List all registry repository tags for a project
     * Lists all tags for a specified registry repository. Responses are paginated and return 20 results by default. Offset pagination is deprecated. Use keyset pagination instead. Keyset pagination requires the container registry metadata database. Otherwise, &#x60;405 Method Not Allowed&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>405</b> - Method Not Allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param orderBy Field to order tags by. &#x60;published_at&#x60; requires keyset pagination. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/573326) in GitLab 19.5.
     * @param sort Direction to sort tags in, &#x60;asc&#x60; or &#x60;desc&#x60;. Default is &#x60;asc&#x60;. Only applies to keyset pagination.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesContainerRegistryTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryId' is set
        if (repositoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryId' when calling getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTags", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("repository_id", repositoryId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesContainerRegistryTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/registry/repositories/{repository_id}/tags", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all registry repository tags for a project
     * Lists all tags for a specified registry repository. Responses are paginated and return 20 results by default. Offset pagination is deprecated. Use keyset pagination instead. Keyset pagination requires the container registry metadata database. Otherwise, &#x60;405 Method Not Allowed&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>405</b> - Method Not Allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param orderBy Field to order tags by. &#x60;published_at&#x60; requires keyset pagination. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/573326) in GitLab 19.5.
     * @param sort Direction to sort tags in, &#x60;asc&#x60; or &#x60;desc&#x60;. Default is &#x60;asc&#x60;. Only applies to keyset pagination.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesContainerRegistryTag&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesContainerRegistryTag> getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTags(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesContainerRegistryTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsRequestCreation(id, repositoryId, orderBy, sort, page, perPage).body(localVarReturnType);
    }

    /**
     * List all registry repository tags for a project
     * Lists all tags for a specified registry repository. Responses are paginated and return 20 results by default. Offset pagination is deprecated. Use keyset pagination instead. Keyset pagination requires the container registry metadata database. Otherwise, &#x60;405 Method Not Allowed&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>405</b> - Method Not Allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param orderBy Field to order tags by. &#x60;published_at&#x60; requires keyset pagination. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/573326) in GitLab 19.5.
     * @param sort Direction to sort tags in, &#x60;asc&#x60; or &#x60;desc&#x60;. Default is &#x60;asc&#x60;. Only applies to keyset pagination.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesContainerRegistryTag&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesContainerRegistryTag>> getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesContainerRegistryTag>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsRequestCreation(id, repositoryId, orderBy, sort, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all registry repository tags for a project
     * Lists all tags for a specified registry repository. Responses are paginated and return 20 results by default. Offset pagination is deprecated. Use keyset pagination instead. Keyset pagination requires the container registry metadata database. Otherwise, &#x60;405 Method Not Allowed&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>405</b> - Method Not Allowed
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param orderBy Field to order tags by. &#x60;published_at&#x60; requires keyset pagination. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/573326) in GitLab 19.5.
     * @param sort Direction to sort tags in, &#x60;asc&#x60; or &#x60;desc&#x60;. Default is &#x60;asc&#x60;. Only applies to keyset pagination.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsRequestCreation(id, repositoryId, orderBy, sort, page, perPage);
    }

    /**
     * Retrieve details of a registry repository tag
     * Retrieves details of a specified registry repository tag.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param tagName Name of the tag.
     * @return APIEntitiesContainerRegistryTagDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'repositoryId' is set
        if (repositoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'repositoryId' when calling getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("repository_id", repositoryId);
        pathParams.put("tag_name", tagName);

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

        ParameterizedTypeReference<APIEntitiesContainerRegistryTagDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/registry/repositories/{repository_id}/tags/{tag_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve details of a registry repository tag
     * Retrieves details of a specified registry repository tag.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param tagName Name of the tag.
     * @return APIEntitiesContainerRegistryTagDetails
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesContainerRegistryTagDetails getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesContainerRegistryTagDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameRequestCreation(id, repositoryId, tagName).body(localVarReturnType);
    }

    /**
     * Retrieve details of a registry repository tag
     * Retrieves details of a specified registry repository tag.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param tagName Name of the tag.
     * @return ResponseEntity&lt;APIEntitiesContainerRegistryTagDetails&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesContainerRegistryTagDetails> getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesContainerRegistryTagDetails> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameRequestCreation(id, repositoryId, tagName).toEntity(localVarReturnType);
    }

    /**
     * Retrieve details of a registry repository tag
     * Retrieves details of a specified registry repository tag.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param repositoryId ID of the container registry repository.
     * @param tagName Name of the tag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer repositoryId, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        return getApiV4ProjectsIdRegistryRepositoriesRepositoryIdTagsTagNameRequestCreation(id, repositoryId, tagName);
    }

    /**
     * Retrieve details of a container registry repository
     * Retrieves details of a specified container registry repository.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Repository Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the repository.
     * @param tags If &#x60;true&#x60;, includes an array of &#x60;tags&#x60; in the response.
     * @param tagsCount If &#x60;true&#x60;, includes &#x60;tags_count&#x60; in the response.
     * @param size If &#x60;true&#x60;, response includes the deduplicated size of all images in the repository. Only available on GitLab.com for repositories created after &#x60;2021-11-04&#x60;.
     * @return APIEntitiesContainerRegistryRepository
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4RegistryRepositoriesIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean tags, @jakarta.annotation.Nullable Boolean tagsCount, @jakarta.annotation.Nullable Boolean size) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4RegistryRepositoriesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tags", tags));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tags_count", tagsCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesContainerRegistryRepository> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/registry/repositories/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve details of a container registry repository
     * Retrieves details of a specified container registry repository.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Repository Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the repository.
     * @param tags If &#x60;true&#x60;, includes an array of &#x60;tags&#x60; in the response.
     * @param tagsCount If &#x60;true&#x60;, includes &#x60;tags_count&#x60; in the response.
     * @param size If &#x60;true&#x60;, response includes the deduplicated size of all images in the repository. Only available on GitLab.com for repositories created after &#x60;2021-11-04&#x60;.
     * @return APIEntitiesContainerRegistryRepository
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesContainerRegistryRepository getApiV4RegistryRepositoriesId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean tags, @jakarta.annotation.Nullable Boolean tagsCount, @jakarta.annotation.Nullable Boolean size) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesContainerRegistryRepository> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RegistryRepositoriesIdRequestCreation(id, tags, tagsCount, size).body(localVarReturnType);
    }

    /**
     * Retrieve details of a container registry repository
     * Retrieves details of a specified container registry repository.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Repository Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the repository.
     * @param tags If &#x60;true&#x60;, includes an array of &#x60;tags&#x60; in the response.
     * @param tagsCount If &#x60;true&#x60;, includes &#x60;tags_count&#x60; in the response.
     * @param size If &#x60;true&#x60;, response includes the deduplicated size of all images in the repository. Only available on GitLab.com for repositories created after &#x60;2021-11-04&#x60;.
     * @return ResponseEntity&lt;APIEntitiesContainerRegistryRepository&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesContainerRegistryRepository> getApiV4RegistryRepositoriesIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean tags, @jakarta.annotation.Nullable Boolean tagsCount, @jakarta.annotation.Nullable Boolean size) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesContainerRegistryRepository> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4RegistryRepositoriesIdRequestCreation(id, tags, tagsCount, size).toEntity(localVarReturnType);
    }

    /**
     * Retrieve details of a container registry repository
     * Retrieves details of a specified container registry repository.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Repository Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the repository.
     * @param tags If &#x60;true&#x60;, includes an array of &#x60;tags&#x60; in the response.
     * @param tagsCount If &#x60;true&#x60;, includes &#x60;tags_count&#x60; in the response.
     * @param size If &#x60;true&#x60;, response includes the deduplicated size of all images in the repository. Only available on GitLab.com for repositories created after &#x60;2021-11-04&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4RegistryRepositoriesIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean tags, @jakarta.annotation.Nullable Boolean tagsCount, @jakarta.annotation.Nullable Boolean size) throws RestClientResponseException {
        return getApiV4RegistryRepositoriesIdRequestCreation(id, tags, tagsCount, size);
    }

    /**
     * Receives notifications from the container registry when an operation occurs
     * This feature was introduced in GitLab 12.10
     * <p><b>200</b> - Success
     * <p><b>401</b> - Invalid Token
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ContainerRegistryEventEventsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/container_registry_event/events", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Receives notifications from the container registry when an operation occurs
     * This feature was introduced in GitLab 12.10
     * <p><b>200</b> - Success
     * <p><b>401</b> - Invalid Token
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ContainerRegistryEventEvents() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ContainerRegistryEventEventsRequestCreation().body(localVarReturnType);
    }

    /**
     * Receives notifications from the container registry when an operation occurs
     * This feature was introduced in GitLab 12.10
     * <p><b>200</b> - Success
     * <p><b>401</b> - Invalid Token
     * <p><b>400</b> - Bad Request
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ContainerRegistryEventEventsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ContainerRegistryEventEventsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Receives notifications from the container registry when an operation occurs
     * This feature was introduced in GitLab 12.10
     * <p><b>200</b> - Success
     * <p><b>401</b> - Invalid Token
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ContainerRegistryEventEventsWithResponseSpec() throws RestClientResponseException {
        return postApiV4ContainerRegistryEventEventsRequestCreation();
    }
}
