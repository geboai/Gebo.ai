package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAiSemanticCodeResponse;
import ai.gebo.gitlab.client.model.APIEntitiesSearchMigration;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;
import ai.gebo.gitlab.client.model.GetApiV4GroupsIdDashSearch200ResponseInner;
import ai.gebo.gitlab.client.model.GetApiV4ProjectsIdDashSearch200ResponseInner;
import ai.gebo.gitlab.client.model.GetApiV4Search200ResponseInner;
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
public class SearchApi {
    private ApiClient apiClient;

    public SearchApi() {
        this(new ApiClient());
    }

    public SearchApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all advanced search migrations
     * Lists all advanced search migrations for the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @return List&lt;APIEntitiesSearchMigration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminSearchMigrationsRequestCreation() throws RestClientResponseException {
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesSearchMigration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/search/migrations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all advanced search migrations
     * Lists all advanced search migrations for the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @return List&lt;APIEntitiesSearchMigration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSearchMigration> getApiV4AdminSearchMigrations() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSearchMigration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminSearchMigrationsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all advanced search migrations
     * Lists all advanced search migrations for the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @return ResponseEntity&lt;List&lt;APIEntitiesSearchMigration&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSearchMigration>> getApiV4AdminSearchMigrationsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSearchMigration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminSearchMigrationsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all advanced search migrations
     * Lists all advanced search migrations for the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminSearchMigrationsWithResponseSpec() throws RestClientResponseException {
        return getApiV4AdminSearchMigrationsRequestCreation();
    }

    /**
     * Retrieve an advanced search migration
     * Retrieves a specified advanced search migration by migration version or name.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param migrationId Version or name of the search migration.
     * @return APIEntitiesSearchMigration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminSearchMigrationsMigrationIdRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter migrationId) throws RestClientResponseException {
        // verify the required parameter 'migrationId' is set
        if (migrationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'migrationId' when calling getApiV4AdminSearchMigrationsMigrationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("migration_id", migrationId);

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

        ParameterizedTypeReference<APIEntitiesSearchMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/search/migrations/{migration_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an advanced search migration
     * Retrieves a specified advanced search migration by migration version or name.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param migrationId Version or name of the search migration.
     * @return APIEntitiesSearchMigration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSearchMigration getApiV4AdminSearchMigrationsMigrationId(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter migrationId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSearchMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminSearchMigrationsMigrationIdRequestCreation(migrationId).body(localVarReturnType);
    }

    /**
     * Retrieve an advanced search migration
     * Retrieves a specified advanced search migration by migration version or name.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param migrationId Version or name of the search migration.
     * @return ResponseEntity&lt;APIEntitiesSearchMigration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSearchMigration> getApiV4AdminSearchMigrationsMigrationIdWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter migrationId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSearchMigration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminSearchMigrationsMigrationIdRequestCreation(migrationId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an advanced search migration
     * Retrieves a specified advanced search migration by migration version or name.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param migrationId Version or name of the search migration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminSearchMigrationsMigrationIdWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter migrationId) throws RestClientResponseException {
        return getApiV4AdminSearchMigrationsMigrationIdRequestCreation(migrationId);
    }

    /**
     * Search on GitLab within a group
     * The &#x60;groups&#x60; scope returns the subgroups of the specified group, not the group itself.  The &#x60;blobs&#x60; scope searches code. It is available only when advanced search or exact code search is enabled. The &#x60;wiki_blobs&#x60; scope searches wikis. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes are available only when advanced search is enabled.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param search Search query.
     * @param scope Scope of the search.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param includeArchived If &#x60;true&#x60;, includes archived projects in the search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param excludeForks If &#x60;true&#x60;, excludes forked projects from the search. If omitted, forks are excluded. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4GroupsIdDashSearch200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable Boolean includeArchived, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Boolean excludeForks, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'search' is set
        if (search == null) {
            throw new RestClientResponseException("Missing the required parameter 'search' when calling getApiV4GroupsIdDashSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'scope' is set
        if (scope == null) {
            throw new RestClientResponseException("Missing the required parameter 'scope' when calling getApiV4GroupsIdDashSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_archived", includeArchived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "fields", fields));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "exclude_forks", excludeForks));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "num_context_lines", numContextLines));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "regex", regex));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_branch", sourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_branch", targetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "label_name", labelName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[source_branch]", notSourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[target_branch]", notTargetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<GetApiV4GroupsIdDashSearch200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/(-/)search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search on GitLab within a group
     * The &#x60;groups&#x60; scope returns the subgroups of the specified group, not the group itself.  The &#x60;blobs&#x60; scope searches code. It is available only when advanced search or exact code search is enabled. The &#x60;wiki_blobs&#x60; scope searches wikis. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes are available only when advanced search is enabled.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param search Search query.
     * @param scope Scope of the search.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param includeArchived If &#x60;true&#x60;, includes archived projects in the search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param excludeForks If &#x60;true&#x60;, excludes forked projects from the search. If omitted, forks are excluded. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4GroupsIdDashSearch200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<GetApiV4GroupsIdDashSearch200ResponseInner> getApiV4GroupsIdDashSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable Boolean includeArchived, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Boolean excludeForks, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4GroupsIdDashSearch200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashSearchRequestCreation(id, search, scope, state, confidential, type, includeArchived, fields, excludeForks, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage).body(localVarReturnType);
    }

    /**
     * Search on GitLab within a group
     * The &#x60;groups&#x60; scope returns the subgroups of the specified group, not the group itself.  The &#x60;blobs&#x60; scope searches code. It is available only when advanced search or exact code search is enabled. The &#x60;wiki_blobs&#x60; scope searches wikis. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes are available only when advanced search is enabled.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param search Search query.
     * @param scope Scope of the search.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param includeArchived If &#x60;true&#x60;, includes archived projects in the search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param excludeForks If &#x60;true&#x60;, excludes forked projects from the search. If omitted, forks are excluded. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;GetApiV4GroupsIdDashSearch200ResponseInner&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GetApiV4GroupsIdDashSearch200ResponseInner>> getApiV4GroupsIdDashSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable Boolean includeArchived, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Boolean excludeForks, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4GroupsIdDashSearch200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashSearchRequestCreation(id, search, scope, state, confidential, type, includeArchived, fields, excludeForks, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search on GitLab within a group
     * The &#x60;groups&#x60; scope returns the subgroups of the specified group, not the group itself.  The &#x60;blobs&#x60; scope searches code. It is available only when advanced search or exact code search is enabled. The &#x60;wiki_blobs&#x60; scope searches wikis. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes are available only when advanced search is enabled.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param search Search query.
     * @param scope Scope of the search.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param includeArchived If &#x60;true&#x60;, includes archived projects in the search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param excludeForks If &#x60;true&#x60;, excludes forked projects from the search. If omitted, forks are excluded. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable Boolean includeArchived, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Boolean excludeForks, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdDashSearchRequestCreation(id, search, scope, state, confidential, type, includeArchived, fields, excludeForks, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage);
    }

    /**
     * Search a project
     * Searches for content in a specified project.  The &#x60;blobs&#x60; scope searches code and the &#x60;wiki_blobs&#x60; scope searches wikis. Both search filenames and contents. Results found in filenames are listed before results found in contents. A blob can appear in more than one result, because the search string can match both its filename and its content, or appear several times in its content.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param search Search query.
     * @param scope Scope of the search.
     * @param ref Name of the repository branch or tag. If omitted, uses the default branch.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4ProjectsIdDashSearch200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDashSearchRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDashSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'search' is set
        if (search == null) {
            throw new RestClientResponseException("Missing the required parameter 'search' when calling getApiV4ProjectsIdDashSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'scope' is set
        if (scope == null) {
            throw new RestClientResponseException("Missing the required parameter 'scope' when calling getApiV4ProjectsIdDashSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ref", ref));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "fields", fields));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "num_context_lines", numContextLines));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "regex", regex));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_branch", sourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_branch", targetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "label_name", labelName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[source_branch]", notSourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[target_branch]", notTargetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<GetApiV4ProjectsIdDashSearch200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/(-/)search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search a project
     * Searches for content in a specified project.  The &#x60;blobs&#x60; scope searches code and the &#x60;wiki_blobs&#x60; scope searches wikis. Both search filenames and contents. Results found in filenames are listed before results found in contents. A blob can appear in more than one result, because the search string can match both its filename and its content, or appear several times in its content.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param search Search query.
     * @param scope Scope of the search.
     * @param ref Name of the repository branch or tag. If omitted, uses the default branch.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4ProjectsIdDashSearch200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<GetApiV4ProjectsIdDashSearch200ResponseInner> getApiV4ProjectsIdDashSearch(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4ProjectsIdDashSearch200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDashSearchRequestCreation(id, search, scope, ref, state, confidential, type, fields, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage).body(localVarReturnType);
    }

    /**
     * Search a project
     * Searches for content in a specified project.  The &#x60;blobs&#x60; scope searches code and the &#x60;wiki_blobs&#x60; scope searches wikis. Both search filenames and contents. Results found in filenames are listed before results found in contents. A blob can appear in more than one result, because the search string can match both its filename and its content, or appear several times in its content.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param search Search query.
     * @param scope Scope of the search.
     * @param ref Name of the repository branch or tag. If omitted, uses the default branch.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;GetApiV4ProjectsIdDashSearch200ResponseInner&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GetApiV4ProjectsIdDashSearch200ResponseInner>> getApiV4ProjectsIdDashSearchWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4ProjectsIdDashSearch200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDashSearchRequestCreation(id, search, scope, ref, state, confidential, type, fields, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search a project
     * Searches for content in a specified project.  The &#x60;blobs&#x60; scope searches code and the &#x60;wiki_blobs&#x60; scope searches wikis. Both search filenames and contents. Results found in filenames are listed before results found in contents. A blob can appear in more than one result, because the search string can match both its filename and its content, or appear several times in its content.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param search Search query.
     * @param scope Scope of the search.
     * @param ref Name of the repository branch or tag. If omitted, uses the default branch.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDashSearchWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String ref, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdDashSearchRequestCreation(id, search, scope, ref, state, confidential, type, fields, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage);
    }

    /**
     * Search project code using natural language
     * Introduced in GitLab 18.11.  Searches indexed project code using semantic (meaning-based) similarity rather than keyword matching. Use this when you do not know the exact symbol or file name, or to discover how a behavior is implemented across the codebase.  Primary use cases: - When you do not know the exact symbol or file path - To see how a behavior or feature is implemented across the codebase - To discover related implementations (clients, jobs, workers)  How to use: - Provide a concise, specific query with concrete keywords - Use directory_path to narrow scope (e.g. \&quot;app/services/\&quot;) - Prefer precise intent over broad terms  Results are grouped by file. Each file includes merged line ranges with content and a relevance score (0.0-1.0). The response includes an overall confidence level (high/medium/low/unknown) based on score distribution. Results are filtered by Duo context exclusion settings.  Requires semantic code search to be enabled and indexed for the project namespace. 
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted - project has no embeddings yet, indexing started or in progress
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found - project not found or semantic code search unavailable
     * <p><b>422</b> - Unprocessable entity - project has no embeddings
     * <p><b>429</b> - Too many requests - search or ad-hoc indexing rate limited
     * <p><b>500</b> - Internal server error - ad-hoc indexing could not be started
     * <p><b>503</b> - Service unavailable - search backend timed out
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param q Natural language search query, for example &#x60;authentication middleware&#x60; or &#x60;rate limiting logic&#x60;.
     * @param directoryPath Restrict search to files under this directory path, for example &#x60;app/services/&#x60;. Must be a relative path with no leading slash and no &#x60;..&#x60; segments.
     * @param knn Number of nearest neighbours to retrieve internally (default: 64). Higher values improve recall at the cost of latency.
     * @param limit Maximum number of results to return (default: 20).
     * @return APIEntitiesAiSemanticCodeResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDashSearchSemanticRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String q, @jakarta.annotation.Nullable String directoryPath, @jakarta.annotation.Nullable Integer knn, @jakarta.annotation.Nullable Integer limit) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDashSearchSemantic", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'q' is set
        if (q == null) {
            throw new RestClientResponseException("Missing the required parameter 'q' when calling getApiV4ProjectsIdDashSearchSemantic", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "directory_path", directoryPath));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "knn", knn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAiSemanticCodeResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/(-/)search/semantic", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search project code using natural language
     * Introduced in GitLab 18.11.  Searches indexed project code using semantic (meaning-based) similarity rather than keyword matching. Use this when you do not know the exact symbol or file name, or to discover how a behavior is implemented across the codebase.  Primary use cases: - When you do not know the exact symbol or file path - To see how a behavior or feature is implemented across the codebase - To discover related implementations (clients, jobs, workers)  How to use: - Provide a concise, specific query with concrete keywords - Use directory_path to narrow scope (e.g. \&quot;app/services/\&quot;) - Prefer precise intent over broad terms  Results are grouped by file. Each file includes merged line ranges with content and a relevance score (0.0-1.0). The response includes an overall confidence level (high/medium/low/unknown) based on score distribution. Results are filtered by Duo context exclusion settings.  Requires semantic code search to be enabled and indexed for the project namespace. 
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted - project has no embeddings yet, indexing started or in progress
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found - project not found or semantic code search unavailable
     * <p><b>422</b> - Unprocessable entity - project has no embeddings
     * <p><b>429</b> - Too many requests - search or ad-hoc indexing rate limited
     * <p><b>500</b> - Internal server error - ad-hoc indexing could not be started
     * <p><b>503</b> - Service unavailable - search backend timed out
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param q Natural language search query, for example &#x60;authentication middleware&#x60; or &#x60;rate limiting logic&#x60;.
     * @param directoryPath Restrict search to files under this directory path, for example &#x60;app/services/&#x60;. Must be a relative path with no leading slash and no &#x60;..&#x60; segments.
     * @param knn Number of nearest neighbours to retrieve internally (default: 64). Higher values improve recall at the cost of latency.
     * @param limit Maximum number of results to return (default: 20).
     * @return APIEntitiesAiSemanticCodeResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiSemanticCodeResponse getApiV4ProjectsIdDashSearchSemantic(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String q, @jakarta.annotation.Nullable String directoryPath, @jakarta.annotation.Nullable Integer knn, @jakarta.annotation.Nullable Integer limit) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiSemanticCodeResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDashSearchSemanticRequestCreation(id, q, directoryPath, knn, limit).body(localVarReturnType);
    }

    /**
     * Search project code using natural language
     * Introduced in GitLab 18.11.  Searches indexed project code using semantic (meaning-based) similarity rather than keyword matching. Use this when you do not know the exact symbol or file name, or to discover how a behavior is implemented across the codebase.  Primary use cases: - When you do not know the exact symbol or file path - To see how a behavior or feature is implemented across the codebase - To discover related implementations (clients, jobs, workers)  How to use: - Provide a concise, specific query with concrete keywords - Use directory_path to narrow scope (e.g. \&quot;app/services/\&quot;) - Prefer precise intent over broad terms  Results are grouped by file. Each file includes merged line ranges with content and a relevance score (0.0-1.0). The response includes an overall confidence level (high/medium/low/unknown) based on score distribution. Results are filtered by Duo context exclusion settings.  Requires semantic code search to be enabled and indexed for the project namespace. 
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted - project has no embeddings yet, indexing started or in progress
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found - project not found or semantic code search unavailable
     * <p><b>422</b> - Unprocessable entity - project has no embeddings
     * <p><b>429</b> - Too many requests - search or ad-hoc indexing rate limited
     * <p><b>500</b> - Internal server error - ad-hoc indexing could not be started
     * <p><b>503</b> - Service unavailable - search backend timed out
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param q Natural language search query, for example &#x60;authentication middleware&#x60; or &#x60;rate limiting logic&#x60;.
     * @param directoryPath Restrict search to files under this directory path, for example &#x60;app/services/&#x60;. Must be a relative path with no leading slash and no &#x60;..&#x60; segments.
     * @param knn Number of nearest neighbours to retrieve internally (default: 64). Higher values improve recall at the cost of latency.
     * @param limit Maximum number of results to return (default: 20).
     * @return ResponseEntity&lt;APIEntitiesAiSemanticCodeResponse&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiSemanticCodeResponse> getApiV4ProjectsIdDashSearchSemanticWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String q, @jakarta.annotation.Nullable String directoryPath, @jakarta.annotation.Nullable Integer knn, @jakarta.annotation.Nullable Integer limit) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiSemanticCodeResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDashSearchSemanticRequestCreation(id, q, directoryPath, knn, limit).toEntity(localVarReturnType);
    }

    /**
     * Search project code using natural language
     * Introduced in GitLab 18.11.  Searches indexed project code using semantic (meaning-based) similarity rather than keyword matching. Use this when you do not know the exact symbol or file name, or to discover how a behavior is implemented across the codebase.  Primary use cases: - When you do not know the exact symbol or file path - To see how a behavior or feature is implemented across the codebase - To discover related implementations (clients, jobs, workers)  How to use: - Provide a concise, specific query with concrete keywords - Use directory_path to narrow scope (e.g. \&quot;app/services/\&quot;) - Prefer precise intent over broad terms  Results are grouped by file. Each file includes merged line ranges with content and a relevance score (0.0-1.0). The response includes an overall confidence level (high/medium/low/unknown) based on score distribution. Results are filtered by Duo context exclusion settings.  Requires semantic code search to be enabled and indexed for the project namespace. 
     * <p><b>200</b> - OK
     * <p><b>202</b> - Accepted - project has no embeddings yet, indexing started or in progress
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found - project not found or semantic code search unavailable
     * <p><b>422</b> - Unprocessable entity - project has no embeddings
     * <p><b>429</b> - Too many requests - search or ad-hoc indexing rate limited
     * <p><b>500</b> - Internal server error - ad-hoc indexing could not be started
     * <p><b>503</b> - Service unavailable - search backend timed out
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param q Natural language search query, for example &#x60;authentication middleware&#x60; or &#x60;rate limiting logic&#x60;.
     * @param directoryPath Restrict search to files under this directory path, for example &#x60;app/services/&#x60;. Must be a relative path with no leading slash and no &#x60;..&#x60; segments.
     * @param knn Number of nearest neighbours to retrieve internally (default: 64). Higher values improve recall at the cost of latency.
     * @param limit Maximum number of results to return (default: 20).
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDashSearchSemanticWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String q, @jakarta.annotation.Nullable String directoryPath, @jakarta.annotation.Nullable Integer knn, @jakarta.annotation.Nullable Integer limit) throws RestClientResponseException {
        return getApiV4ProjectsIdDashSearchSemanticRequestCreation(id, q, directoryPath, knn, limit);
    }

    /**
     * Search an instance
     * Searches for a term across the entire GitLab instance. The response depends on the requested scope.  The &#x60;blobs&#x60; scope searches code. It is available only when advanced search or exact code search is enabled. The &#x60;wiki_blobs&#x60; scope searches wikis. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes are available only when advanced search is enabled.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search query.
     * @param scope Scope of the search. The &#x60;blobs&#x60; scope requires advanced search or exact code search. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes require advanced search.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param includeArchived If &#x60;true&#x60;, includes archived projects in the search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param excludeForks If &#x60;true&#x60;, excludes forked projects from the search. If omitted, forks are excluded. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4Search200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SearchRequestCreation(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable Boolean includeArchived, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Boolean excludeForks, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'search' is set
        if (search == null) {
            throw new RestClientResponseException("Missing the required parameter 'search' when calling getApiV4Search", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'scope' is set
        if (scope == null) {
            throw new RestClientResponseException("Missing the required parameter 'scope' when calling getApiV4Search", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_archived", includeArchived));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "fields", fields));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "exclude_forks", excludeForks));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "num_context_lines", numContextLines));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "regex", regex));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_branch", sourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_branch", targetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "label_name", labelName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[source_branch]", notSourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[target_branch]", notTargetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<GetApiV4Search200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search an instance
     * Searches for a term across the entire GitLab instance. The response depends on the requested scope.  The &#x60;blobs&#x60; scope searches code. It is available only when advanced search or exact code search is enabled. The &#x60;wiki_blobs&#x60; scope searches wikis. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes are available only when advanced search is enabled.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search query.
     * @param scope Scope of the search. The &#x60;blobs&#x60; scope requires advanced search or exact code search. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes require advanced search.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param includeArchived If &#x60;true&#x60;, includes archived projects in the search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param excludeForks If &#x60;true&#x60;, excludes forked projects from the search. If omitted, forks are excluded. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;GetApiV4Search200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<GetApiV4Search200ResponseInner> getApiV4Search(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable Boolean includeArchived, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Boolean excludeForks, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4Search200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SearchRequestCreation(search, scope, state, confidential, type, includeArchived, fields, excludeForks, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage).body(localVarReturnType);
    }

    /**
     * Search an instance
     * Searches for a term across the entire GitLab instance. The response depends on the requested scope.  The &#x60;blobs&#x60; scope searches code. It is available only when advanced search or exact code search is enabled. The &#x60;wiki_blobs&#x60; scope searches wikis. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes are available only when advanced search is enabled.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search query.
     * @param scope Scope of the search. The &#x60;blobs&#x60; scope requires advanced search or exact code search. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes require advanced search.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param includeArchived If &#x60;true&#x60;, includes archived projects in the search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param excludeForks If &#x60;true&#x60;, excludes forked projects from the search. If omitted, forks are excluded. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;GetApiV4Search200ResponseInner&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GetApiV4Search200ResponseInner>> getApiV4SearchWithHttpInfo(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable Boolean includeArchived, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Boolean excludeForks, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<GetApiV4Search200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SearchRequestCreation(search, scope, state, confidential, type, includeArchived, fields, excludeForks, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search an instance
     * Searches for a term across the entire GitLab instance. The response depends on the requested scope.  The &#x60;blobs&#x60; scope searches code. It is available only when advanced search or exact code search is enabled. The &#x60;wiki_blobs&#x60; scope searches wikis. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes are available only when advanced search is enabled.  The &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; scopes support the &#x60;filename&#x60;, &#x60;path&#x60;, and &#x60;extension&#x60; filters. To use a filter, include it in the search query, for example &#x60;a query filename:some_name*&#x60;. Filters support wildcards (&#x60;*&#x60;) for glob matching. Elasticsearch syntax might not work with exact code search. For exact code search, use regular expressions instead of Elasticsearch wildcard queries.  In &#x60;blobs&#x60; and &#x60;wiki_blobs&#x60; results, &#x60;filename&#x60; is deprecated in favor of &#x60;path&#x60;. Both return the full path of the file in the repository. In a future release, &#x60;filename&#x60; is planned to return only the file name.  In &#x60;issues&#x60; results, &#x60;assignee&#x60; is deprecated in favor of &#x60;assignees&#x60;.  To filter &#x60;work_items&#x60; results by more than one type, pass &#x60;type&#x60; as an array, for example &#x60;type[]&#x3D;task&amp;type[]&#x3D;issue&#x60;. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param search Search query.
     * @param scope Scope of the search. The &#x60;blobs&#x60; scope requires advanced search or exact code search. The &#x60;commits&#x60;, &#x60;notes&#x60;, and &#x60;wiki_blobs&#x60; scopes require advanced search.
     * @param state Filter results by state. Applies only to the &#x60;issues&#x60;, &#x60;work_items&#x60;, and &#x60;merge_requests&#x60; scopes.
     * @param confidential If &#x60;true&#x60;, returns only confidential results. If &#x60;false&#x60;, returns only non-confidential results. Applies only to the &#x60;issues&#x60; and &#x60;work_items&#x60; scopes.
     * @param type Filter work items by type. Only applies to work_items scope. Available types: issue, task, epic, incident, test_case, requirement, objective, key_result, ticket.
     * @param includeArchived If &#x60;true&#x60;, includes archived projects in the search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param fields Fields to search. Supports only &#x60;issues&#x60; and &#x60;merge_requests&#x60; scopes. Premium and Ultimate only.
     * @param excludeForks If &#x60;true&#x60;, excludes forked projects from the search. If omitted, forks are excluded. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/493281) in GitLab 18.7.
     * @param numContextLines Number of context lines to include around each match in the results. Available for advanced and exact code search only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/583217) in GitLab 18.11.
     * @param regex If &#x60;true&#x60;, uses regular expressions to search for code. If omitted, regular expressions are used. Available for exact code search. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/work_items/521686) in GitLab 18.9.
     * @param sourceBranch Filter merge requests by source branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param targetBranch Filter merge requests by target branch. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param authorUsername Filter by author username. A username that matches no user is ignored. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param labelName Filter by label name. A result must carry every name that resolves to an existing label; names that match no label are ignored. Maximum 30 names. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param not Filter out results matching the parameters supplied. Supplying a filter together with its negation matches either condition, which widens the result set.
     * @param notSourceBranch Exclude merge requests with this source branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notTargetBranch Exclude merge requests with this target branch. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the merge_requests scope. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param notAuthorUsername Exclude results authored by this username. Combining a filter with its own negation matches either condition, which widens the result set. Available with advanced search and the issues, merge_requests and work_items scopes. Premium and Ultimate only. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/issues/493214) in GitLab 19.5.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SearchWithResponseSpec(@jakarta.annotation.Nullable String search, @jakarta.annotation.Nonnull String scope, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable List<String> type, @jakarta.annotation.Nullable Boolean includeArchived, @jakarta.annotation.Nullable List<String> fields, @jakarta.annotation.Nullable Boolean excludeForks, @jakarta.annotation.Nullable Integer numContextLines, @jakarta.annotation.Nullable Boolean regex, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable List<String> labelName, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable String notSourceBranch, @jakarta.annotation.Nullable String notTargetBranch, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4SearchRequestCreation(search, scope, state, confidential, type, includeArchived, fields, excludeForks, numContextLines, regex, sourceBranch, targetBranch, authorUsername, labelName, not, notSourceBranch, notTargetBranch, notAuthorUsername, page, perPage);
    }
}
