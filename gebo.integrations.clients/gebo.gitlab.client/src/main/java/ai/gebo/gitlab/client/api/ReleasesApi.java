package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesRelease;
import ai.gebo.gitlab.client.model.APIEntitiesReleasesLink;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdReleasesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdReleasesTagNameAssetsLinksRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdReleasesTagNameRequest;

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
public class ReleasesApi {
    private ApiClient apiClient;

    public ReleasesApi() {
        this(new ApiClient());
    }

    public ReleasesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a release
     * Delete a release. Deleting a release doesn&#39;t delete the associated tag. Requires at least the Developer role for the project. This feature was introduced in GitLab 11.7.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdReleasesTagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdReleasesTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling deleteApiV4ProjectsIdReleasesTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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

        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a release
     * Delete a release. Deleting a release doesn&#39;t delete the associated tag. Requires at least the Developer role for the project. This feature was introduced in GitLab 11.7.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRelease deleteApiV4ProjectsIdReleasesTagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName).body(localVarReturnType);
    }

    /**
     * Delete a release
     * Delete a release. Deleting a release doesn&#39;t delete the associated tag. Requires at least the Developer role for the project. This feature was introduced in GitLab 11.7.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @return ResponseEntity&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRelease> deleteApiV4ProjectsIdReleasesTagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName).toEntity(localVarReturnType);
    }

    /**
     * Delete a release
     * Delete a release. Deleting a release doesn&#39;t delete the associated tag. Requires at least the Developer role for the project. This feature was introduced in GitLab 11.7.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdReleasesTagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName) throws RestClientResponseException {
        return deleteApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName);
    }

    /**
     * Delete a release link
     * Deletes a specified asset link from a release.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @return APIEntitiesReleasesLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'linkId' is set
        if (linkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkId' when calling deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);
        pathParams.put("link_id", linkId);

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

        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}/assets/links/{link_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a release link
     * Deletes a specified asset link from a release.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @return APIEntitiesReleasesLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesReleasesLink deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId).body(localVarReturnType);
    }

    /**
     * Delete a release link
     * Deletes a specified asset link from a release.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @return ResponseEntity&lt;APIEntitiesReleasesLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesReleasesLink> deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId).toEntity(localVarReturnType);
    }

    /**
     * Delete a release link
     * Deletes a specified asset link from a release.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId);
    }

    /**
     * List all releases in a group
     * Lists all releases for projects in a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, only returns limited fields for each release.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdReleasesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdReleases", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "simple", simple));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesRelease>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/releases", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all releases in a group
     * Lists all releases for projects in a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, only returns limited fields for each release.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesRelease> getApiV4GroupsIdReleases(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelease>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdReleasesRequestCreation(id, sort, simple, page, perPage).body(localVarReturnType);
    }

    /**
     * List all releases in a group
     * Lists all releases for projects in a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, only returns limited fields for each release.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesRelease&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesRelease>> getApiV4GroupsIdReleasesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelease>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdReleasesRequestCreation(id, sort, simple, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all releases in a group
     * Lists all releases for projects in a specified group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param sort Sort results in ascending or descending order.
     * @param simple If &#x60;true&#x60;, only returns limited fields for each release.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdReleasesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean simple, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdReleasesRequestCreation(id, sort, simple, page, perPage);
    }

    /**
     * List all releases in a project
     * Lists all releases for a specified project. Sorted by &#x60;released_at&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param includeHtmlDescription If &#x60;true&#x60;, the response includes HTML-rendered Markdown of the release description.
     * @param updatedBefore Return releases updated on or before the specified time.
     * @param updatedAfter Return releases updated on or after the specified time.
     * @return List&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdReleasesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean includeHtmlDescription, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdReleases", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_html_description", includeHtmlDescription));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesRelease>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all releases in a project
     * Lists all releases for a specified project. Sorted by &#x60;released_at&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param includeHtmlDescription If &#x60;true&#x60;, the response includes HTML-rendered Markdown of the release description.
     * @param updatedBefore Return releases updated on or before the specified time.
     * @param updatedAfter Return releases updated on or after the specified time.
     * @return List&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesRelease> getApiV4ProjectsIdReleases(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean includeHtmlDescription, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelease>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesRequestCreation(id, page, perPage, orderBy, sort, includeHtmlDescription, updatedBefore, updatedAfter).body(localVarReturnType);
    }

    /**
     * List all releases in a project
     * Lists all releases for a specified project. Sorted by &#x60;released_at&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param includeHtmlDescription If &#x60;true&#x60;, the response includes HTML-rendered Markdown of the release description.
     * @param updatedBefore Return releases updated on or before the specified time.
     * @param updatedAfter Return releases updated on or after the specified time.
     * @return ResponseEntity&lt;List&lt;APIEntitiesRelease&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesRelease>> getApiV4ProjectsIdReleasesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean includeHtmlDescription, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelease>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesRequestCreation(id, page, perPage, orderBy, sort, includeHtmlDescription, updatedBefore, updatedAfter).toEntity(localVarReturnType);
    }

    /**
     * List all releases in a project
     * Lists all releases for a specified project. Sorted by &#x60;released_at&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param includeHtmlDescription If &#x60;true&#x60;, the response includes HTML-rendered Markdown of the release description.
     * @param updatedBefore Return releases updated on or before the specified time.
     * @param updatedAfter Return releases updated on or after the specified time.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdReleasesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean includeHtmlDescription, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter) throws RestClientResponseException {
        return getApiV4ProjectsIdReleasesRequestCreation(id, page, perPage, orderBy, sort, includeHtmlDescription, updatedBefore, updatedAfter);
    }

    /**
     * Get the latest project release
     * Retrieves the latest release through a permanent API URL. To call any other GET endpoint that requires a release tag, append a suffix to the &#x60;permalink/latest&#x60; path, for example &#x60;permalink/latest/evidence&#x60;, or &#x60;permalink/latest/downloads/bin/asset.exe&#x60; to download an asset of the latest release. The request redirects to the endpoint for the tag of the latest release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdReleasesPermalinkLatestRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdReleasesPermalinkLatest", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/permalink/latest", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the latest project release
     * Retrieves the latest release through a permanent API URL. To call any other GET endpoint that requires a release tag, append a suffix to the &#x60;permalink/latest&#x60; path, for example &#x60;permalink/latest/evidence&#x60;, or &#x60;permalink/latest/downloads/bin/asset.exe&#x60; to download an asset of the latest release. The request redirects to the endpoint for the tag of the latest release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdReleasesPermalinkLatest(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdReleasesPermalinkLatestRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get the latest project release
     * Retrieves the latest release through a permanent API URL. To call any other GET endpoint that requires a release tag, append a suffix to the &#x60;permalink/latest&#x60; path, for example &#x60;permalink/latest/evidence&#x60;, or &#x60;permalink/latest/downloads/bin/asset.exe&#x60; to download an asset of the latest release. The request redirects to the endpoint for the tag of the latest release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdReleasesPermalinkLatestWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesPermalinkLatestRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get the latest project release
     * Retrieves the latest release through a permanent API URL. To call any other GET endpoint that requires a release tag, append a suffix to the &#x60;permalink/latest&#x60; path, for example &#x60;permalink/latest/evidence&#x60;, or &#x60;permalink/latest/downloads/bin/asset.exe&#x60; to download an asset of the latest release. The request redirects to the endpoint for the tag of the latest release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdReleasesPermalinkLatestWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdReleasesPermalinkLatestRequestCreation(id);
    }

    /**
     * Get the latest project release
     * Retrieves the latest release through a permanent API URL. To call any other GET endpoint that requires a release tag, append a suffix to the &#x60;permalink/latest&#x60; path, for example &#x60;permalink/latest/evidence&#x60;, or &#x60;permalink/latest/downloads/bin/asset.exe&#x60; to download an asset of the latest release. The request redirects to the endpoint for the tag of the latest release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param suffixPath The path to be suffixed to the latest release
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdReleasesPermalinkLatestSuffixPathRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String suffixPath) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdReleasesPermalinkLatestSuffixPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'suffixPath' is set
        if (suffixPath == null) {
            throw new RestClientResponseException("Missing the required parameter 'suffixPath' when calling getApiV4ProjectsIdReleasesPermalinkLatestSuffixPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("suffix_path", suffixPath);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/permalink/latest/{suffix_path}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the latest project release
     * Retrieves the latest release through a permanent API URL. To call any other GET endpoint that requires a release tag, append a suffix to the &#x60;permalink/latest&#x60; path, for example &#x60;permalink/latest/evidence&#x60;, or &#x60;permalink/latest/downloads/bin/asset.exe&#x60; to download an asset of the latest release. The request redirects to the endpoint for the tag of the latest release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param suffixPath The path to be suffixed to the latest release
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdReleasesPermalinkLatestSuffixPath(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String suffixPath) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdReleasesPermalinkLatestSuffixPathRequestCreation(id, suffixPath).body(localVarReturnType);
    }

    /**
     * Get the latest project release
     * Retrieves the latest release through a permanent API URL. To call any other GET endpoint that requires a release tag, append a suffix to the &#x60;permalink/latest&#x60; path, for example &#x60;permalink/latest/evidence&#x60;, or &#x60;permalink/latest/downloads/bin/asset.exe&#x60; to download an asset of the latest release. The request redirects to the endpoint for the tag of the latest release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param suffixPath The path to be suffixed to the latest release
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdReleasesPermalinkLatestSuffixPathWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String suffixPath) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesPermalinkLatestSuffixPathRequestCreation(id, suffixPath).toEntity(localVarReturnType);
    }

    /**
     * Get the latest project release
     * Retrieves the latest release through a permanent API URL. To call any other GET endpoint that requires a release tag, append a suffix to the &#x60;permalink/latest&#x60; path, for example &#x60;permalink/latest/evidence&#x60;, or &#x60;permalink/latest/downloads/bin/asset.exe&#x60; to download an asset of the latest release. The request redirects to the endpoint for the tag of the latest release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param suffixPath The path to be suffixed to the latest release
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdReleasesPermalinkLatestSuffixPathWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String suffixPath) throws RestClientResponseException {
        return getApiV4ProjectsIdReleasesPermalinkLatestSuffixPathRequestCreation(id, suffixPath);
    }

    /**
     * Retrieve a release by tag name
     * Retrieves a release with a specified tag name.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param includeHtmlDescription If &#x60;true&#x60;, the response includes HTML-rendered Markdown of the release description.
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdReleasesTagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable Boolean includeHtmlDescription) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdReleasesTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling getApiV4ProjectsIdReleasesTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_html_description", includeHtmlDescription));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a release by tag name
     * Retrieves a release with a specified tag name.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param includeHtmlDescription If &#x60;true&#x60;, the response includes HTML-rendered Markdown of the release description.
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRelease getApiV4ProjectsIdReleasesTagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable Boolean includeHtmlDescription) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName, includeHtmlDescription).body(localVarReturnType);
    }

    /**
     * Retrieve a release by tag name
     * Retrieves a release with a specified tag name.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param includeHtmlDescription If &#x60;true&#x60;, the response includes HTML-rendered Markdown of the release description.
     * @return ResponseEntity&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRelease> getApiV4ProjectsIdReleasesTagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable Boolean includeHtmlDescription) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName, includeHtmlDescription).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a release by tag name
     * Retrieves a release with a specified tag name.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param includeHtmlDescription If &#x60;true&#x60;, the response includes HTML-rendered Markdown of the release description.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdReleasesTagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable Boolean includeHtmlDescription) throws RestClientResponseException {
        return getApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName, includeHtmlDescription);
    }

    /**
     * List all release links
     * Lists all assets as links from a release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesReleasesLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdReleasesTagNameAssetsLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdReleasesTagNameAssetsLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling getApiV4ProjectsIdReleasesTagNameAssetsLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);

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

        ParameterizedTypeReference<List<APIEntitiesReleasesLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}/assets/links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all release links
     * Lists all assets as links from a release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesReleasesLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesReleasesLink> getApiV4ProjectsIdReleasesTagNameAssetsLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesReleasesLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesTagNameAssetsLinksRequestCreation(id, tagName, page, perPage).body(localVarReturnType);
    }

    /**
     * List all release links
     * Lists all assets as links from a release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesReleasesLink&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesReleasesLink>> getApiV4ProjectsIdReleasesTagNameAssetsLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesReleasesLink>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesTagNameAssetsLinksRequestCreation(id, tagName, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all release links
     * Lists all assets as links from a release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdReleasesTagNameAssetsLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdReleasesTagNameAssetsLinksRequestCreation(id, tagName, page, perPage);
    }

    /**
     * Retrieve a release link
     * Retrieves a specified asset as a link from a release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @return APIEntitiesReleasesLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'linkId' is set
        if (linkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkId' when calling getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);
        pathParams.put("link_id", linkId);

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

        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}/assets/links/{link_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a release link
     * Retrieves a specified asset as a link from a release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @return APIEntitiesReleasesLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesReleasesLink getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId).body(localVarReturnType);
    }

    /**
     * Retrieve a release link
     * Retrieves a specified asset as a link from a release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @return ResponseEntity&lt;APIEntitiesReleasesLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesReleasesLink> getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a release link
     * Retrieves a specified asset as a link from a release.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId) throws RestClientResponseException {
        return getApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId);
    }

    /**
     * Download a project release asset file
     * Downloads a release asset file. The request redirects to the URL of the release link that has the specified direct asset path. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param directAssetPath The path to the file to download, as specified when creating the release asset
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPathRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull String directAssetPath) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'directAssetPath' is set
        if (directAssetPath == null) {
            throw new RestClientResponseException("Missing the required parameter 'directAssetPath' when calling getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);
        pathParams.put("direct_asset_path", directAssetPath);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}/downloads/{direct_asset_path}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a project release asset file
     * Downloads a release asset file. The request redirects to the URL of the release link that has the specified direct asset path. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param directAssetPath The path to the file to download, as specified when creating the release asset
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPath(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull String directAssetPath) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPathRequestCreation(id, tagName, directAssetPath).body(localVarReturnType);
    }

    /**
     * Download a project release asset file
     * Downloads a release asset file. The request redirects to the URL of the release link that has the specified direct asset path. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param directAssetPath The path to the file to download, as specified when creating the release asset
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPathWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull String directAssetPath) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPathRequestCreation(id, tagName, directAssetPath).toEntity(localVarReturnType);
    }

    /**
     * Download a project release asset file
     * Downloads a release asset file. The request redirects to the URL of the release link that has the specified direct asset path. This feature was introduced in GitLab 15.4.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param directAssetPath The path to the file to download, as specified when creating the release asset
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPathWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull String directAssetPath) throws RestClientResponseException {
        return getApiV4ProjectsIdReleasesTagNameDownloadsDirectAssetPathRequestCreation(id, tagName, directAssetPath);
    }

    /**
     * Create a release
     * Creates a release. Developer level access to the project is required to create a release. Release creates and updates are rate limited per project (&#x60;release_writes_limit_per_project&#x60; application setting) and per user (&#x60;release_writes_limit_per_user&#x60; application setting). Requests over a limit are blocked when its feature flag (&#x60;rate_limit_release_writes_per_project&#x60; or &#x60;rate_limit_release_writes_per_user&#x60;) is enabled, and only logged otherwise. When the &#x60;release_evidence_size_limit&#x60; feature flag is enabled, the response includes &#x60;warnings&#x60; if the release evidence would exceed the &#x60;release_evidence_max_size_bytes&#x60; application setting even without its issues.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdReleasesRequest The postApiV4ProjectsIdReleasesRequest parameter
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdReleasesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdReleasesRequest postApiV4ProjectsIdReleasesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdReleases", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdReleasesRequest' is set
        if (postApiV4ProjectsIdReleasesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdReleasesRequest' when calling postApiV4ProjectsIdReleases", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdReleasesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a release
     * Creates a release. Developer level access to the project is required to create a release. Release creates and updates are rate limited per project (&#x60;release_writes_limit_per_project&#x60; application setting) and per user (&#x60;release_writes_limit_per_user&#x60; application setting). Requests over a limit are blocked when its feature flag (&#x60;rate_limit_release_writes_per_project&#x60; or &#x60;rate_limit_release_writes_per_user&#x60;) is enabled, and only logged otherwise. When the &#x60;release_evidence_size_limit&#x60; feature flag is enabled, the response includes &#x60;warnings&#x60; if the release evidence would exceed the &#x60;release_evidence_max_size_bytes&#x60; application setting even without its issues.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdReleasesRequest The postApiV4ProjectsIdReleasesRequest parameter
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRelease postApiV4ProjectsIdReleases(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdReleasesRequest postApiV4ProjectsIdReleasesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdReleasesRequestCreation(id, postApiV4ProjectsIdReleasesRequest).body(localVarReturnType);
    }

    /**
     * Create a release
     * Creates a release. Developer level access to the project is required to create a release. Release creates and updates are rate limited per project (&#x60;release_writes_limit_per_project&#x60; application setting) and per user (&#x60;release_writes_limit_per_user&#x60; application setting). Requests over a limit are blocked when its feature flag (&#x60;rate_limit_release_writes_per_project&#x60; or &#x60;rate_limit_release_writes_per_user&#x60;) is enabled, and only logged otherwise. When the &#x60;release_evidence_size_limit&#x60; feature flag is enabled, the response includes &#x60;warnings&#x60; if the release evidence would exceed the &#x60;release_evidence_max_size_bytes&#x60; application setting even without its issues.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdReleasesRequest The postApiV4ProjectsIdReleasesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRelease> postApiV4ProjectsIdReleasesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdReleasesRequest postApiV4ProjectsIdReleasesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdReleasesRequestCreation(id, postApiV4ProjectsIdReleasesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a release
     * Creates a release. Developer level access to the project is required to create a release. Release creates and updates are rate limited per project (&#x60;release_writes_limit_per_project&#x60; application setting) and per user (&#x60;release_writes_limit_per_user&#x60; application setting). Requests over a limit are blocked when its feature flag (&#x60;rate_limit_release_writes_per_project&#x60; or &#x60;rate_limit_release_writes_per_user&#x60;) is enabled, and only logged otherwise. When the &#x60;release_evidence_size_limit&#x60; feature flag is enabled, the response includes &#x60;warnings&#x60; if the release evidence would exceed the &#x60;release_evidence_max_size_bytes&#x60; application setting even without its issues.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>409</b> - Conflict
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdReleasesRequest The postApiV4ProjectsIdReleasesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdReleasesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdReleasesRequest postApiV4ProjectsIdReleasesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdReleasesRequestCreation(id, postApiV4ProjectsIdReleasesRequest);
    }

    /**
     * Create a release link
     * Creates an asset link for a specified release.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest The postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest parameter
     * @return APIEntitiesReleasesLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdReleasesTagNameAssetsLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull PostApiV4ProjectsIdReleasesTagNameAssetsLinksRequest postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdReleasesTagNameAssetsLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling postApiV4ProjectsIdReleasesTagNameAssetsLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest' is set
        if (postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest' when calling postApiV4ProjectsIdReleasesTagNameAssetsLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);

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

        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}/assets/links", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a release link
     * Creates an asset link for a specified release.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest The postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest parameter
     * @return APIEntitiesReleasesLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesReleasesLink postApiV4ProjectsIdReleasesTagNameAssetsLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull PostApiV4ProjectsIdReleasesTagNameAssetsLinksRequest postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdReleasesTagNameAssetsLinksRequestCreation(id, tagName, postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest).body(localVarReturnType);
    }

    /**
     * Create a release link
     * Creates an asset link for a specified release.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest The postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesReleasesLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesReleasesLink> postApiV4ProjectsIdReleasesTagNameAssetsLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull PostApiV4ProjectsIdReleasesTagNameAssetsLinksRequest postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdReleasesTagNameAssetsLinksRequestCreation(id, tagName, postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a release link
     * Creates an asset link for a specified release.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest The postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdReleasesTagNameAssetsLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull PostApiV4ProjectsIdReleasesTagNameAssetsLinksRequest postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdReleasesTagNameAssetsLinksRequestCreation(id, tagName, postApiV4ProjectsIdReleasesTagNameAssetsLinksRequest);
    }

    /**
     * Generate release evidence
     * Generates an evidence for an existing release. Requests are rate limited per user (&#x60;release_evidence_collections_limit_per_user&#x60; application setting). Requests over the limit are blocked when the &#x60;rate_limit_release_evidence_collections_per_user&#x60; feature flag is enabled, and only logged otherwise. When the &#x60;release_evidence_size_limit&#x60; feature flag is enabled, returns &#x60;422&#x60; if the evidence would exceed the &#x60;release_evidence_max_size_bytes&#x60; application setting.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param tagName Git tag the release is associated with.
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdReleasesTagNameEvidenceRequestCreation(@jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling postApiV4ProjectsIdReleasesTagNameEvidence", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdReleasesTagNameEvidence", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("tag_name", tagName);
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

        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}/evidence", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Generate release evidence
     * Generates an evidence for an existing release. Requests are rate limited per user (&#x60;release_evidence_collections_limit_per_user&#x60; application setting). Requests over the limit are blocked when the &#x60;rate_limit_release_evidence_collections_per_user&#x60; feature flag is enabled, and only logged otherwise. When the &#x60;release_evidence_size_limit&#x60; feature flag is enabled, returns &#x60;422&#x60; if the evidence would exceed the &#x60;release_evidence_max_size_bytes&#x60; application setting.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param tagName Git tag the release is associated with.
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRelease postApiV4ProjectsIdReleasesTagNameEvidence(@jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdReleasesTagNameEvidenceRequestCreation(tagName, id).body(localVarReturnType);
    }

    /**
     * Generate release evidence
     * Generates an evidence for an existing release. Requests are rate limited per user (&#x60;release_evidence_collections_limit_per_user&#x60; application setting). Requests over the limit are blocked when the &#x60;rate_limit_release_evidence_collections_per_user&#x60; feature flag is enabled, and only logged otherwise. When the &#x60;release_evidence_size_limit&#x60; feature flag is enabled, returns &#x60;422&#x60; if the evidence would exceed the &#x60;release_evidence_max_size_bytes&#x60; application setting.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param tagName Git tag the release is associated with.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRelease> postApiV4ProjectsIdReleasesTagNameEvidenceWithHttpInfo(@jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdReleasesTagNameEvidenceRequestCreation(tagName, id).toEntity(localVarReturnType);
    }

    /**
     * Generate release evidence
     * Generates an evidence for an existing release. Requests are rate limited per user (&#x60;release_evidence_collections_limit_per_user&#x60; application setting). Requests over the limit are blocked when the &#x60;rate_limit_release_evidence_collections_per_user&#x60; feature flag is enabled, and only logged otherwise. When the &#x60;release_evidence_size_limit&#x60; feature flag is enabled, returns &#x60;422&#x60; if the evidence would exceed the &#x60;release_evidence_max_size_bytes&#x60; application setting.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>429</b> - Too many requests
     * <p><b>400</b> - Bad Request
     * @param tagName Git tag the release is associated with.
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdReleasesTagNameEvidenceWithResponseSpec(@jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdReleasesTagNameEvidenceRequestCreation(tagName, id);
    }

    /**
     * Update a release
     * Updates a release. Developer level access to the project is required to update a release. Release creates and updates are rate limited per project (&#x60;release_writes_limit_per_project&#x60; application setting) and per user (&#x60;release_writes_limit_per_user&#x60; application setting). Requests over a limit are blocked when its feature flag (&#x60;rate_limit_release_writes_per_project&#x60; or &#x60;rate_limit_release_writes_per_user&#x60;) is enabled, and only logged otherwise.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param putApiV4ProjectsIdReleasesTagNameRequest The putApiV4ProjectsIdReleasesTagNameRequest parameter
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdReleasesTagNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable PutApiV4ProjectsIdReleasesTagNameRequest putApiV4ProjectsIdReleasesTagNameRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdReleasesTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling putApiV4ProjectsIdReleasesTagName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);

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

        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdReleasesTagNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a release
     * Updates a release. Developer level access to the project is required to update a release. Release creates and updates are rate limited per project (&#x60;release_writes_limit_per_project&#x60; application setting) and per user (&#x60;release_writes_limit_per_user&#x60; application setting). Requests over a limit are blocked when its feature flag (&#x60;rate_limit_release_writes_per_project&#x60; or &#x60;rate_limit_release_writes_per_user&#x60;) is enabled, and only logged otherwise.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param putApiV4ProjectsIdReleasesTagNameRequest The putApiV4ProjectsIdReleasesTagNameRequest parameter
     * @return APIEntitiesRelease
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesRelease putApiV4ProjectsIdReleasesTagName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable PutApiV4ProjectsIdReleasesTagNameRequest putApiV4ProjectsIdReleasesTagNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName, putApiV4ProjectsIdReleasesTagNameRequest).body(localVarReturnType);
    }

    /**
     * Update a release
     * Updates a release. Developer level access to the project is required to update a release. Release creates and updates are rate limited per project (&#x60;release_writes_limit_per_project&#x60; application setting) and per user (&#x60;release_writes_limit_per_user&#x60; application setting). Requests over a limit are blocked when its feature flag (&#x60;rate_limit_release_writes_per_project&#x60; or &#x60;rate_limit_release_writes_per_user&#x60;) is enabled, and only logged otherwise.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param putApiV4ProjectsIdReleasesTagNameRequest The putApiV4ProjectsIdReleasesTagNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesRelease&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesRelease> putApiV4ProjectsIdReleasesTagNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable PutApiV4ProjectsIdReleasesTagNameRequest putApiV4ProjectsIdReleasesTagNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesRelease> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName, putApiV4ProjectsIdReleasesTagNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a release
     * Updates a release. Developer level access to the project is required to update a release. Release creates and updates are rate limited per project (&#x60;release_writes_limit_per_project&#x60; application setting) and per user (&#x60;release_writes_limit_per_user&#x60; application setting). Requests over a limit are blocked when its feature flag (&#x60;rate_limit_release_writes_per_project&#x60; or &#x60;rate_limit_release_writes_per_user&#x60;) is enabled, and only logged otherwise.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param id ID or URL-encoded path of the project.
     * @param tagName Git tag the release is associated with.
     * @param putApiV4ProjectsIdReleasesTagNameRequest The putApiV4ProjectsIdReleasesTagNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdReleasesTagNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nullable PutApiV4ProjectsIdReleasesTagNameRequest putApiV4ProjectsIdReleasesTagNameRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdReleasesTagNameRequestCreation(id, tagName, putApiV4ProjectsIdReleasesTagNameRequest);
    }

    /**
     * Update a release link
     * Updates a specified asset link for a release.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @param putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest The putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest parameter
     * @return APIEntitiesReleasesLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId, @jakarta.annotation.Nullable PutApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tagName' is set
        if (tagName == null) {
            throw new RestClientResponseException("Missing the required parameter 'tagName' when calling putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'linkId' is set
        if (linkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkId' when calling putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("tag_name", tagName);
        pathParams.put("link_id", linkId);

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

        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/releases/{tag_name}/assets/links/{link_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a release link
     * Updates a specified asset link for a release.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @param putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest The putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest parameter
     * @return APIEntitiesReleasesLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesReleasesLink putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId, @jakarta.annotation.Nullable PutApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId, putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest).body(localVarReturnType);
    }

    /**
     * Update a release link
     * Updates a specified asset link for a release.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @param putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest The putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesReleasesLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesReleasesLink> putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId, @jakarta.annotation.Nullable PutApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesReleasesLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId, putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a release link
     * Updates a specified asset link for a release.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param tagName Tag associated with the release.
     * @param linkId ID of the link.
     * @param putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest The putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String tagName, @jakarta.annotation.Nonnull Integer linkId, @jakarta.annotation.Nullable PutApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequestCreation(id, tagName, linkId, putApiV4ProjectsIdReleasesTagNameAssetsLinksLinkIdRequest);
    }
}
