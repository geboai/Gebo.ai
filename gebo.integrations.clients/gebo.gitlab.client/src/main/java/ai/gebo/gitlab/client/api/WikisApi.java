package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesWikiAttachment;
import ai.gebo.gitlab.client.model.APIEntitiesWikiPage;
import ai.gebo.gitlab.client.model.APIEntitiesWikiPageBasic;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdWikisRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdWikisRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdWikisSlugRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdWikisSlugRequest;

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
public class WikisApi {
    private ApiClient apiClient;

    public WikisApi() {
        this(new ApiClient());
    }

    public WikisApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a wiki page for a group
     * Deletes a specified wiki page from a group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdWikisSlugRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'slug' is set
        if (slug == null) {
            throw new RestClientResponseException("Missing the required parameter 'slug' when calling deleteApiV4GroupsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("slug", slug);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/wikis/{slug}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a wiki page for a group
     * Deletes a specified wiki page from a group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdWikisSlug(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdWikisSlugRequestCreation(id, slug).body(localVarReturnType);
    }

    /**
     * Delete a wiki page for a group
     * Deletes a specified wiki page from a group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdWikisSlugWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdWikisSlugRequestCreation(id, slug).toEntity(localVarReturnType);
    }

    /**
     * Delete a wiki page for a group
     * Deletes a specified wiki page from a group.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdWikisSlugWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug) throws RestClientResponseException {
        return deleteApiV4GroupsIdWikisSlugRequestCreation(id, slug);
    }

    /**
     * Delete a wiki page for a project
     * Deletes a specified wiki page from a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdWikisSlugRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'slug' is set
        if (slug == null) {
            throw new RestClientResponseException("Missing the required parameter 'slug' when calling deleteApiV4ProjectsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("slug", slug);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/wikis/{slug}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a wiki page for a project
     * Deletes a specified wiki page from a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdWikisSlug(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdWikisSlugRequestCreation(id, slug).body(localVarReturnType);
    }

    /**
     * Delete a wiki page for a project
     * Deletes a specified wiki page from a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdWikisSlugWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdWikisSlugRequestCreation(id, slug).toEntity(localVarReturnType);
    }

    /**
     * Delete a wiki page for a project
     * Deletes a specified wiki page from a project.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdWikisSlugWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug) throws RestClientResponseException {
        return deleteApiV4ProjectsIdWikisSlugRequestCreation(id, slug);
    }

    /**
     * List all wiki pages for a group
     * Lists all wiki pages for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param withContent If &#x60;true&#x60;, includes the content of each wiki page.
     * @return List&lt;APIEntitiesWikiPageBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdWikisRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withContent) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdWikis", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_content", withContent));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesWikiPageBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/wikis", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all wiki pages for a group
     * Lists all wiki pages for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param withContent If &#x60;true&#x60;, includes the content of each wiki page.
     * @return List&lt;APIEntitiesWikiPageBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesWikiPageBasic> getApiV4GroupsIdWikis(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withContent) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesWikiPageBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdWikisRequestCreation(id, withContent).body(localVarReturnType);
    }

    /**
     * List all wiki pages for a group
     * Lists all wiki pages for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param withContent If &#x60;true&#x60;, includes the content of each wiki page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesWikiPageBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesWikiPageBasic>> getApiV4GroupsIdWikisWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withContent) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesWikiPageBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdWikisRequestCreation(id, withContent).toEntity(localVarReturnType);
    }

    /**
     * List all wiki pages for a group
     * Lists all wiki pages for a specified group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param withContent If &#x60;true&#x60;, includes the content of each wiki page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdWikisWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withContent) throws RestClientResponseException {
        return getApiV4GroupsIdWikisRequestCreation(id, withContent);
    }

    /**
     * Retrieve a wiki page for a group
     * Retrieves a specified wiki page for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param version Version SHA of the wiki page.
     * @param renderHtml If &#x60;true&#x60;, returns the rendered HTML of the wiki page.
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdWikisSlugRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable Boolean renderHtml) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'slug' is set
        if (slug == null) {
            throw new RestClientResponseException("Missing the required parameter 'slug' when calling getApiV4GroupsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("slug", slug);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "render_html", renderHtml));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/wikis/{slug}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a wiki page for a group
     * Retrieves a specified wiki page for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param version Version SHA of the wiki page.
     * @param renderHtml If &#x60;true&#x60;, returns the rendered HTML of the wiki page.
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesWikiPage getApiV4GroupsIdWikisSlug(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable Boolean renderHtml) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdWikisSlugRequestCreation(id, slug, version, renderHtml).body(localVarReturnType);
    }

    /**
     * Retrieve a wiki page for a group
     * Retrieves a specified wiki page for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param version Version SHA of the wiki page.
     * @param renderHtml If &#x60;true&#x60;, returns the rendered HTML of the wiki page.
     * @return ResponseEntity&lt;APIEntitiesWikiPage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesWikiPage> getApiV4GroupsIdWikisSlugWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable Boolean renderHtml) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdWikisSlugRequestCreation(id, slug, version, renderHtml).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a wiki page for a group
     * Retrieves a specified wiki page for a group.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param version Version SHA of the wiki page.
     * @param renderHtml If &#x60;true&#x60;, returns the rendered HTML of the wiki page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdWikisSlugWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable Boolean renderHtml) throws RestClientResponseException {
        return getApiV4GroupsIdWikisSlugRequestCreation(id, slug, version, renderHtml);
    }

    /**
     * List all wiki pages for a project
     * Lists all wiki pages for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param withContent If &#x60;true&#x60;, includes the content of each wiki page.
     * @return List&lt;APIEntitiesWikiPageBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdWikisRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withContent) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdWikis", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_content", withContent));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesWikiPageBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/wikis", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all wiki pages for a project
     * Lists all wiki pages for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param withContent If &#x60;true&#x60;, includes the content of each wiki page.
     * @return List&lt;APIEntitiesWikiPageBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesWikiPageBasic> getApiV4ProjectsIdWikis(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withContent) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesWikiPageBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdWikisRequestCreation(id, withContent).body(localVarReturnType);
    }

    /**
     * List all wiki pages for a project
     * Lists all wiki pages for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param withContent If &#x60;true&#x60;, includes the content of each wiki page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesWikiPageBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesWikiPageBasic>> getApiV4ProjectsIdWikisWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withContent) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesWikiPageBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdWikisRequestCreation(id, withContent).toEntity(localVarReturnType);
    }

    /**
     * List all wiki pages for a project
     * Lists all wiki pages for a specified project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param withContent If &#x60;true&#x60;, includes the content of each wiki page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdWikisWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean withContent) throws RestClientResponseException {
        return getApiV4ProjectsIdWikisRequestCreation(id, withContent);
    }

    /**
     * Retrieve a wiki page for a project
     * Retrieves a specified wiki page for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param version Version SHA of the wiki page.
     * @param renderHtml If &#x60;true&#x60;, returns the rendered HTML of the wiki page.
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdWikisSlugRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable Boolean renderHtml) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'slug' is set
        if (slug == null) {
            throw new RestClientResponseException("Missing the required parameter 'slug' when calling getApiV4ProjectsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("slug", slug);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "render_html", renderHtml));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/wikis/{slug}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a wiki page for a project
     * Retrieves a specified wiki page for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param version Version SHA of the wiki page.
     * @param renderHtml If &#x60;true&#x60;, returns the rendered HTML of the wiki page.
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesWikiPage getApiV4ProjectsIdWikisSlug(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable Boolean renderHtml) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdWikisSlugRequestCreation(id, slug, version, renderHtml).body(localVarReturnType);
    }

    /**
     * Retrieve a wiki page for a project
     * Retrieves a specified wiki page for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param version Version SHA of the wiki page.
     * @param renderHtml If &#x60;true&#x60;, returns the rendered HTML of the wiki page.
     * @return ResponseEntity&lt;APIEntitiesWikiPage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesWikiPage> getApiV4ProjectsIdWikisSlugWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable Boolean renderHtml) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdWikisSlugRequestCreation(id, slug, version, renderHtml).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a wiki page for a project
     * Retrieves a specified wiki page for a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param version Version SHA of the wiki page.
     * @param renderHtml If &#x60;true&#x60;, returns the rendered HTML of the wiki page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdWikisSlugWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable String version, @jakarta.annotation.Nullable Boolean renderHtml) throws RestClientResponseException {
        return getApiV4ProjectsIdWikisSlugRequestCreation(id, slug, version, renderHtml);
    }

    /**
     * Create a wiki page for a group
     * Creates a wiki page for a specified group. Requests can define the title, slug, and content.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param postApiV4GroupsIdWikisRequest The postApiV4GroupsIdWikisRequest parameter
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdWikisRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdWikisRequest postApiV4GroupsIdWikisRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdWikis", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdWikisRequest' is set
        if (postApiV4GroupsIdWikisRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdWikisRequest' when calling postApiV4GroupsIdWikis", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/wikis", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdWikisRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a wiki page for a group
     * Creates a wiki page for a specified group. Requests can define the title, slug, and content.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param postApiV4GroupsIdWikisRequest The postApiV4GroupsIdWikisRequest parameter
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesWikiPage postApiV4GroupsIdWikis(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdWikisRequest postApiV4GroupsIdWikisRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdWikisRequestCreation(id, postApiV4GroupsIdWikisRequest).body(localVarReturnType);
    }

    /**
     * Create a wiki page for a group
     * Creates a wiki page for a specified group. Requests can define the title, slug, and content.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param postApiV4GroupsIdWikisRequest The postApiV4GroupsIdWikisRequest parameter
     * @return ResponseEntity&lt;APIEntitiesWikiPage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesWikiPage> postApiV4GroupsIdWikisWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdWikisRequest postApiV4GroupsIdWikisRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdWikisRequestCreation(id, postApiV4GroupsIdWikisRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a wiki page for a group
     * Creates a wiki page for a specified group. Requests can define the title, slug, and content.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param postApiV4GroupsIdWikisRequest The postApiV4GroupsIdWikisRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdWikisWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdWikisRequest postApiV4GroupsIdWikisRequest) throws RestClientResponseException {
        return postApiV4GroupsIdWikisRequestCreation(id, postApiV4GroupsIdWikisRequest);
    }

    /**
     * Upload an attachment to a group wiki
     * Uploads a file to the &#x60;uploads&#x60; directory in a specified group wiki.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param _file The attachment file to be uploaded
     * @param branch The name of the branch
     * @return APIEntitiesWikiAttachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdWikisAttachmentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String branch) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdWikisAttachments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4GroupsIdWikisAttachments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));
        if (branch != null) {
            formParams.add("branch", branch);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesWikiAttachment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/wikis/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload an attachment to a group wiki
     * Uploads a file to the &#x60;uploads&#x60; directory in a specified group wiki.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param _file The attachment file to be uploaded
     * @param branch The name of the branch
     * @return APIEntitiesWikiAttachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesWikiAttachment postApiV4GroupsIdWikisAttachments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String branch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiAttachment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdWikisAttachmentsRequestCreation(id, _file, branch).body(localVarReturnType);
    }

    /**
     * Upload an attachment to a group wiki
     * Uploads a file to the &#x60;uploads&#x60; directory in a specified group wiki.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param _file The attachment file to be uploaded
     * @param branch The name of the branch
     * @return ResponseEntity&lt;APIEntitiesWikiAttachment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesWikiAttachment> postApiV4GroupsIdWikisAttachmentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String branch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiAttachment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdWikisAttachmentsRequestCreation(id, _file, branch).toEntity(localVarReturnType);
    }

    /**
     * Upload an attachment to a group wiki
     * Uploads a file to the &#x60;uploads&#x60; directory in a specified group wiki.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param _file The attachment file to be uploaded
     * @param branch The name of the branch
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdWikisAttachmentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String branch) throws RestClientResponseException {
        return postApiV4GroupsIdWikisAttachmentsRequestCreation(id, _file, branch);
    }

    /**
     * Create a wiki page for a project
     * Creates a wiki page for a specified project. Requests can define the title, slug, and content.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param postApiV4ProjectsIdWikisRequest The postApiV4ProjectsIdWikisRequest parameter
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdWikisRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdWikisRequest postApiV4ProjectsIdWikisRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdWikis", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdWikisRequest' is set
        if (postApiV4ProjectsIdWikisRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdWikisRequest' when calling postApiV4ProjectsIdWikis", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/wikis", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdWikisRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a wiki page for a project
     * Creates a wiki page for a specified project. Requests can define the title, slug, and content.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param postApiV4ProjectsIdWikisRequest The postApiV4ProjectsIdWikisRequest parameter
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesWikiPage postApiV4ProjectsIdWikis(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdWikisRequest postApiV4ProjectsIdWikisRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdWikisRequestCreation(id, postApiV4ProjectsIdWikisRequest).body(localVarReturnType);
    }

    /**
     * Create a wiki page for a project
     * Creates a wiki page for a specified project. Requests can define the title, slug, and content.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param postApiV4ProjectsIdWikisRequest The postApiV4ProjectsIdWikisRequest parameter
     * @return ResponseEntity&lt;APIEntitiesWikiPage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesWikiPage> postApiV4ProjectsIdWikisWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdWikisRequest postApiV4ProjectsIdWikisRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdWikisRequestCreation(id, postApiV4ProjectsIdWikisRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a wiki page for a project
     * Creates a wiki page for a specified project. Requests can define the title, slug, and content.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param postApiV4ProjectsIdWikisRequest The postApiV4ProjectsIdWikisRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdWikisWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdWikisRequest postApiV4ProjectsIdWikisRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdWikisRequestCreation(id, postApiV4ProjectsIdWikisRequest);
    }

    /**
     * Upload an attachment to a project wiki
     * Uploads a file to the &#x60;uploads&#x60; directory in a specified project wiki.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param _file The attachment file to be uploaded
     * @param branch The name of the branch
     * @return APIEntitiesWikiAttachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdWikisAttachmentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String branch) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdWikisAttachments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsIdWikisAttachments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));
        if (branch != null) {
            formParams.add("branch", branch);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesWikiAttachment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/wikis/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload an attachment to a project wiki
     * Uploads a file to the &#x60;uploads&#x60; directory in a specified project wiki.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param _file The attachment file to be uploaded
     * @param branch The name of the branch
     * @return APIEntitiesWikiAttachment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesWikiAttachment postApiV4ProjectsIdWikisAttachments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String branch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiAttachment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdWikisAttachmentsRequestCreation(id, _file, branch).body(localVarReturnType);
    }

    /**
     * Upload an attachment to a project wiki
     * Uploads a file to the &#x60;uploads&#x60; directory in a specified project wiki.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param _file The attachment file to be uploaded
     * @param branch The name of the branch
     * @return ResponseEntity&lt;APIEntitiesWikiAttachment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesWikiAttachment> postApiV4ProjectsIdWikisAttachmentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String branch) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiAttachment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdWikisAttachmentsRequestCreation(id, _file, branch).toEntity(localVarReturnType);
    }

    /**
     * Upload an attachment to a project wiki
     * Uploads a file to the &#x60;uploads&#x60; directory in a specified project wiki.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group or project.
     * @param _file The attachment file to be uploaded
     * @param branch The name of the branch
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdWikisAttachmentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String branch) throws RestClientResponseException {
        return postApiV4ProjectsIdWikisAttachmentsRequestCreation(id, _file, branch);
    }

    /**
     * Update a wiki page for a group
     * Updates a specified wiki page for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param putApiV4GroupsIdWikisSlugRequest The putApiV4GroupsIdWikisSlugRequest parameter
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdWikisSlugRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable PutApiV4GroupsIdWikisSlugRequest putApiV4GroupsIdWikisSlugRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'slug' is set
        if (slug == null) {
            throw new RestClientResponseException("Missing the required parameter 'slug' when calling putApiV4GroupsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("slug", slug);

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

        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/wikis/{slug}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdWikisSlugRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a wiki page for a group
     * Updates a specified wiki page for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param putApiV4GroupsIdWikisSlugRequest The putApiV4GroupsIdWikisSlugRequest parameter
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesWikiPage putApiV4GroupsIdWikisSlug(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable PutApiV4GroupsIdWikisSlugRequest putApiV4GroupsIdWikisSlugRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdWikisSlugRequestCreation(id, slug, putApiV4GroupsIdWikisSlugRequest).body(localVarReturnType);
    }

    /**
     * Update a wiki page for a group
     * Updates a specified wiki page for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param putApiV4GroupsIdWikisSlugRequest The putApiV4GroupsIdWikisSlugRequest parameter
     * @return ResponseEntity&lt;APIEntitiesWikiPage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesWikiPage> putApiV4GroupsIdWikisSlugWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable PutApiV4GroupsIdWikisSlugRequest putApiV4GroupsIdWikisSlugRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdWikisSlugRequestCreation(id, slug, putApiV4GroupsIdWikisSlugRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a wiki page for a group
     * Updates a specified wiki page for a group.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param putApiV4GroupsIdWikisSlugRequest The putApiV4GroupsIdWikisSlugRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdWikisSlugWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable PutApiV4GroupsIdWikisSlugRequest putApiV4GroupsIdWikisSlugRequest) throws RestClientResponseException {
        return putApiV4GroupsIdWikisSlugRequestCreation(id, slug, putApiV4GroupsIdWikisSlugRequest);
    }

    /**
     * Update a wiki page for a project
     * Updates a specified wiki page for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param putApiV4ProjectsIdWikisSlugRequest The putApiV4ProjectsIdWikisSlugRequest parameter
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdWikisSlugRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable PutApiV4ProjectsIdWikisSlugRequest putApiV4ProjectsIdWikisSlugRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'slug' is set
        if (slug == null) {
            throw new RestClientResponseException("Missing the required parameter 'slug' when calling putApiV4ProjectsIdWikisSlug", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("slug", slug);

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

        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/wikis/{slug}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdWikisSlugRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a wiki page for a project
     * Updates a specified wiki page for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param putApiV4ProjectsIdWikisSlugRequest The putApiV4ProjectsIdWikisSlugRequest parameter
     * @return APIEntitiesWikiPage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesWikiPage putApiV4ProjectsIdWikisSlug(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable PutApiV4ProjectsIdWikisSlugRequest putApiV4ProjectsIdWikisSlugRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdWikisSlugRequestCreation(id, slug, putApiV4ProjectsIdWikisSlugRequest).body(localVarReturnType);
    }

    /**
     * Update a wiki page for a project
     * Updates a specified wiki page for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param putApiV4ProjectsIdWikisSlugRequest The putApiV4ProjectsIdWikisSlugRequest parameter
     * @return ResponseEntity&lt;APIEntitiesWikiPage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesWikiPage> putApiV4ProjectsIdWikisSlugWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable PutApiV4ProjectsIdWikisSlugRequest putApiV4ProjectsIdWikisSlugRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesWikiPage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdWikisSlugRequestCreation(id, slug, putApiV4ProjectsIdWikisSlugRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a wiki page for a project
     * Updates a specified wiki page for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * @param id ID or URL-encoded path of the group or project.
     * @param slug URL-encoded slug of the wiki page, a unique string such as &#x60;dir%2Fpage_name&#x60;.
     * @param putApiV4ProjectsIdWikisSlugRequest The putApiV4ProjectsIdWikisSlugRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdWikisSlugWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String slug, @jakarta.annotation.Nullable PutApiV4ProjectsIdWikisSlugRequest putApiV4ProjectsIdWikisSlugRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdWikisSlugRequestCreation(id, slug, putApiV4ProjectsIdWikisSlugRequest);
    }
}
