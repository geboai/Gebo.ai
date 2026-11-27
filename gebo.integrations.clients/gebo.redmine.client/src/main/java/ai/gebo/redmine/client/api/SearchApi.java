package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.CreateIssueRequestIssueProjectId;
import ai.gebo.redmine.client.model.GetSearch200Response;

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
     * Search
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param q Search query split into at most five unique tokens; quoted phrases form one token. Non-Han tokens need at least two characters, while a Han character can be a token. If the query is omitted or has no usable tokens, Redmine returns an empty result and &#x60;total_count: null&#x60;. By default, all tokens must match; use &#x60;all_words&#x60; to allow any token to match.
     * @param scope Search scope. Values: &#x60;all&#x60; (all projects), &#x60;my_projects&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects), &#x60;subprojects&#x60; (include subprojects, only effective for project-scoped search)
     * @param allWords Require all words to match. Default is &#x60;true&#x60; (all words must match). Set to empty string to disable (any word matches). Any non-empty value enables this option.
     * @param titlesOnly Search titles only. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param issues Include issues in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param news Include news in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param wikiPages Include wiki pages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param projects Include matching projects when the effective scope covers multiple projects or is global. Any non-empty value enables project results. If no search types are specified, all available types are searched.
     * @param documents Include documents in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param changesets Include changesets in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param messages Include forum messages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param openIssues Only return open issues. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param attachments Search attachment metadata (filenames and descriptions) associated with searchable records; file contents are not searched. &#x60;0&#x60; (default) searches record fields only, &#x60;1&#x60; searches record fields and attachment metadata, and &#x60;only&#x60; searches attachment metadata only. If &#x60;titles_only&#x60; is enabled, attachment metadata is searched only when this parameter is &#x60;only&#x60;.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @return GetSearch200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Search#GET">Search Documentation</a>
     */
    private ResponseSpec getSearchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String allWords, @jakarta.annotation.Nullable String titlesOnly, @jakarta.annotation.Nullable Integer issues, @jakarta.annotation.Nullable Integer news, @jakarta.annotation.Nullable Integer wikiPages, @jakarta.annotation.Nullable Integer projects, @jakarta.annotation.Nullable Integer documents, @jakarta.annotation.Nullable Integer changesets, @jakarta.annotation.Nullable Integer messages, @jakarta.annotation.Nullable String openIssues, @jakarta.annotation.Nullable String attachments, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "nometa", nometa));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "all_words", allWords));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "titles_only", titlesOnly));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issues", issues));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "news", news));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "wiki_pages", wikiPages));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "projects", projects));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "documents", documents));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "changesets", changesets));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "messages", messages));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "open_issues", openIssues));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "attachments", attachments));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }
        if (xRedmineNometa != null) {
            headerParams.add("X-Redmine-Nometa", apiClient.parameterToString(xRedmineNometa));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetSearch200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/search.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param q Search query split into at most five unique tokens; quoted phrases form one token. Non-Han tokens need at least two characters, while a Han character can be a token. If the query is omitted or has no usable tokens, Redmine returns an empty result and &#x60;total_count: null&#x60;. By default, all tokens must match; use &#x60;all_words&#x60; to allow any token to match.
     * @param scope Search scope. Values: &#x60;all&#x60; (all projects), &#x60;my_projects&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects), &#x60;subprojects&#x60; (include subprojects, only effective for project-scoped search)
     * @param allWords Require all words to match. Default is &#x60;true&#x60; (all words must match). Set to empty string to disable (any word matches). Any non-empty value enables this option.
     * @param titlesOnly Search titles only. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param issues Include issues in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param news Include news in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param wikiPages Include wiki pages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param projects Include matching projects when the effective scope covers multiple projects or is global. Any non-empty value enables project results. If no search types are specified, all available types are searched.
     * @param documents Include documents in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param changesets Include changesets in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param messages Include forum messages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param openIssues Only return open issues. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param attachments Search attachment metadata (filenames and descriptions) associated with searchable records; file contents are not searched. &#x60;0&#x60; (default) searches record fields only, &#x60;1&#x60; searches record fields and attachment metadata, and &#x60;only&#x60; searches attachment metadata only. If &#x60;titles_only&#x60; is enabled, attachment metadata is searched only when this parameter is &#x60;only&#x60;.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @return GetSearch200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Search#GET">Search Documentation</a>
     */
    public GetSearch200Response getSearch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String allWords, @jakarta.annotation.Nullable String titlesOnly, @jakarta.annotation.Nullable Integer issues, @jakarta.annotation.Nullable Integer news, @jakarta.annotation.Nullable Integer wikiPages, @jakarta.annotation.Nullable Integer projects, @jakarta.annotation.Nullable Integer documents, @jakarta.annotation.Nullable Integer changesets, @jakarta.annotation.Nullable Integer messages, @jakarta.annotation.Nullable String openIssues, @jakarta.annotation.Nullable String attachments, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        ParameterizedTypeReference<GetSearch200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSearchRequestCreation(format, limit, offset, nometa, xRedmineSwitchUser, xRedmineNometa, q, scope, allWords, titlesOnly, issues, news, wikiPages, projects, documents, changesets, messages, openIssues, attachments, page).body(localVarReturnType);
    }

    /**
     * Search
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param q Search query split into at most five unique tokens; quoted phrases form one token. Non-Han tokens need at least two characters, while a Han character can be a token. If the query is omitted or has no usable tokens, Redmine returns an empty result and &#x60;total_count: null&#x60;. By default, all tokens must match; use &#x60;all_words&#x60; to allow any token to match.
     * @param scope Search scope. Values: &#x60;all&#x60; (all projects), &#x60;my_projects&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects), &#x60;subprojects&#x60; (include subprojects, only effective for project-scoped search)
     * @param allWords Require all words to match. Default is &#x60;true&#x60; (all words must match). Set to empty string to disable (any word matches). Any non-empty value enables this option.
     * @param titlesOnly Search titles only. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param issues Include issues in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param news Include news in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param wikiPages Include wiki pages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param projects Include matching projects when the effective scope covers multiple projects or is global. Any non-empty value enables project results. If no search types are specified, all available types are searched.
     * @param documents Include documents in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param changesets Include changesets in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param messages Include forum messages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param openIssues Only return open issues. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param attachments Search attachment metadata (filenames and descriptions) associated with searchable records; file contents are not searched. &#x60;0&#x60; (default) searches record fields only, &#x60;1&#x60; searches record fields and attachment metadata, and &#x60;only&#x60; searches attachment metadata only. If &#x60;titles_only&#x60; is enabled, attachment metadata is searched only when this parameter is &#x60;only&#x60;.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @return ResponseEntity&lt;GetSearch200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Search#GET">Search Documentation</a>
     */
    public ResponseEntity<GetSearch200Response> getSearchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String allWords, @jakarta.annotation.Nullable String titlesOnly, @jakarta.annotation.Nullable Integer issues, @jakarta.annotation.Nullable Integer news, @jakarta.annotation.Nullable Integer wikiPages, @jakarta.annotation.Nullable Integer projects, @jakarta.annotation.Nullable Integer documents, @jakarta.annotation.Nullable Integer changesets, @jakarta.annotation.Nullable Integer messages, @jakarta.annotation.Nullable String openIssues, @jakarta.annotation.Nullable String attachments, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        ParameterizedTypeReference<GetSearch200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSearchRequestCreation(format, limit, offset, nometa, xRedmineSwitchUser, xRedmineNometa, q, scope, allWords, titlesOnly, issues, news, wikiPages, projects, documents, changesets, messages, openIssues, attachments, page).toEntity(localVarReturnType);
    }

    /**
     * Search
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param q Search query split into at most five unique tokens; quoted phrases form one token. Non-Han tokens need at least two characters, while a Han character can be a token. If the query is omitted or has no usable tokens, Redmine returns an empty result and &#x60;total_count: null&#x60;. By default, all tokens must match; use &#x60;all_words&#x60; to allow any token to match.
     * @param scope Search scope. Values: &#x60;all&#x60; (all projects), &#x60;my_projects&#x60; (projects where current user is a member), &#x60;bookmarks&#x60; (bookmarked projects), &#x60;subprojects&#x60; (include subprojects, only effective for project-scoped search)
     * @param allWords Require all words to match. Default is &#x60;true&#x60; (all words must match). Set to empty string to disable (any word matches). Any non-empty value enables this option.
     * @param titlesOnly Search titles only. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param issues Include issues in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param news Include news in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param wikiPages Include wiki pages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param projects Include matching projects when the effective scope covers multiple projects or is global. Any non-empty value enables project results. If no search types are specified, all available types are searched.
     * @param documents Include documents in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param changesets Include changesets in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param messages Include forum messages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param openIssues Only return open issues. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param attachments Search attachment metadata (filenames and descriptions) associated with searchable records; file contents are not searched. &#x60;0&#x60; (default) searches record fields only, &#x60;1&#x60; searches record fields and attachment metadata, and &#x60;only&#x60; searches attachment metadata only. If &#x60;titles_only&#x60; is enabled, attachment metadata is searched only when this parameter is &#x60;only&#x60;.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Search#GET">Search Documentation</a>
     */
    public ResponseSpec getSearchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String allWords, @jakarta.annotation.Nullable String titlesOnly, @jakarta.annotation.Nullable Integer issues, @jakarta.annotation.Nullable Integer news, @jakarta.annotation.Nullable Integer wikiPages, @jakarta.annotation.Nullable Integer projects, @jakarta.annotation.Nullable Integer documents, @jakarta.annotation.Nullable Integer changesets, @jakarta.annotation.Nullable Integer messages, @jakarta.annotation.Nullable String openIssues, @jakarta.annotation.Nullable String attachments, @jakarta.annotation.Nullable Integer page) throws RestClientResponseException {
        return getSearchRequestCreation(format, limit, offset, nometa, xRedmineSwitchUser, xRedmineNometa, q, scope, allWords, titlesOnly, issues, news, wikiPages, projects, documents, changesets, messages, openIssues, attachments, page);
    }

    /**
     * Search within project
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param q Search query split into at most five unique tokens; quoted phrases form one token. Non-Han tokens need at least two characters, while a Han character can be a token. If the query is omitted or has no usable tokens, Redmine returns an empty result and &#x60;total_count: null&#x60;. By default, all tokens must match; use &#x60;all_words&#x60; to allow any token to match.
     * @param scope Defaults to the project in the path. &#x60;subprojects&#x60; includes that project and its descendants. &#x60;all&#x60;, &#x60;my_projects&#x60;, and &#x60;bookmarks&#x60; replace this context with all visible projects, projects the current user belongs to, or the current user&#39;s bookmarked projects, respectively.
     * @param allWords Require all words to match. Default is &#x60;true&#x60; (all words must match). Set to empty string to disable (any word matches). Any non-empty value enables this option.
     * @param titlesOnly Search titles only. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param issues Include issues in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param news Include news in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param wikiPages Include wiki pages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param documents Include documents in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param changesets Include changesets in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param messages Include forum messages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param openIssues Only return open issues. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param attachments Search attachment metadata (filenames and descriptions) associated with searchable records; file contents are not searched. &#x60;0&#x60; (default) searches record fields only, &#x60;1&#x60; searches record fields and attachment metadata, and &#x60;only&#x60; searches attachment metadata only. If &#x60;titles_only&#x60; is enabled, attachment metadata is searched only when this parameter is &#x60;only&#x60;.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param projects Include matching projects when the effective scope covers multiple projects or is global. Any non-empty value enables project results.
     * @return GetSearch200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Search#GET">Search within project Documentation</a>
     */
    private ResponseSpec getSearchByProjectRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String allWords, @jakarta.annotation.Nullable String titlesOnly, @jakarta.annotation.Nullable Integer issues, @jakarta.annotation.Nullable Integer news, @jakarta.annotation.Nullable Integer wikiPages, @jakarta.annotation.Nullable Integer documents, @jakarta.annotation.Nullable Integer changesets, @jakarta.annotation.Nullable Integer messages, @jakarta.annotation.Nullable String openIssues, @jakarta.annotation.Nullable String attachments, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer projects) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getSearchByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getSearchByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "nometa", nometa));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "all_words", allWords));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "titles_only", titlesOnly));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issues", issues));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "news", news));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "wiki_pages", wikiPages));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "documents", documents));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "changesets", changesets));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "messages", messages));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "open_issues", openIssues));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "attachments", attachments));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "projects", projects));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }
        if (xRedmineNometa != null) {
            headerParams.add("X-Redmine-Nometa", apiClient.parameterToString(xRedmineNometa));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetSearch200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/search.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search within project
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param q Search query split into at most five unique tokens; quoted phrases form one token. Non-Han tokens need at least two characters, while a Han character can be a token. If the query is omitted or has no usable tokens, Redmine returns an empty result and &#x60;total_count: null&#x60;. By default, all tokens must match; use &#x60;all_words&#x60; to allow any token to match.
     * @param scope Defaults to the project in the path. &#x60;subprojects&#x60; includes that project and its descendants. &#x60;all&#x60;, &#x60;my_projects&#x60;, and &#x60;bookmarks&#x60; replace this context with all visible projects, projects the current user belongs to, or the current user&#39;s bookmarked projects, respectively.
     * @param allWords Require all words to match. Default is &#x60;true&#x60; (all words must match). Set to empty string to disable (any word matches). Any non-empty value enables this option.
     * @param titlesOnly Search titles only. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param issues Include issues in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param news Include news in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param wikiPages Include wiki pages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param documents Include documents in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param changesets Include changesets in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param messages Include forum messages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param openIssues Only return open issues. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param attachments Search attachment metadata (filenames and descriptions) associated with searchable records; file contents are not searched. &#x60;0&#x60; (default) searches record fields only, &#x60;1&#x60; searches record fields and attachment metadata, and &#x60;only&#x60; searches attachment metadata only. If &#x60;titles_only&#x60; is enabled, attachment metadata is searched only when this parameter is &#x60;only&#x60;.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param projects Include matching projects when the effective scope covers multiple projects or is global. Any non-empty value enables project results.
     * @return GetSearch200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Search#GET">Search within project Documentation</a>
     */
    public GetSearch200Response getSearchByProject(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String allWords, @jakarta.annotation.Nullable String titlesOnly, @jakarta.annotation.Nullable Integer issues, @jakarta.annotation.Nullable Integer news, @jakarta.annotation.Nullable Integer wikiPages, @jakarta.annotation.Nullable Integer documents, @jakarta.annotation.Nullable Integer changesets, @jakarta.annotation.Nullable Integer messages, @jakarta.annotation.Nullable String openIssues, @jakarta.annotation.Nullable String attachments, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer projects) throws RestClientResponseException {
        ParameterizedTypeReference<GetSearch200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSearchByProjectRequestCreation(format, projectId, limit, offset, nometa, xRedmineSwitchUser, xRedmineNometa, q, scope, allWords, titlesOnly, issues, news, wikiPages, documents, changesets, messages, openIssues, attachments, page, projects).body(localVarReturnType);
    }

    /**
     * Search within project
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param q Search query split into at most five unique tokens; quoted phrases form one token. Non-Han tokens need at least two characters, while a Han character can be a token. If the query is omitted or has no usable tokens, Redmine returns an empty result and &#x60;total_count: null&#x60;. By default, all tokens must match; use &#x60;all_words&#x60; to allow any token to match.
     * @param scope Defaults to the project in the path. &#x60;subprojects&#x60; includes that project and its descendants. &#x60;all&#x60;, &#x60;my_projects&#x60;, and &#x60;bookmarks&#x60; replace this context with all visible projects, projects the current user belongs to, or the current user&#39;s bookmarked projects, respectively.
     * @param allWords Require all words to match. Default is &#x60;true&#x60; (all words must match). Set to empty string to disable (any word matches). Any non-empty value enables this option.
     * @param titlesOnly Search titles only. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param issues Include issues in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param news Include news in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param wikiPages Include wiki pages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param documents Include documents in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param changesets Include changesets in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param messages Include forum messages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param openIssues Only return open issues. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param attachments Search attachment metadata (filenames and descriptions) associated with searchable records; file contents are not searched. &#x60;0&#x60; (default) searches record fields only, &#x60;1&#x60; searches record fields and attachment metadata, and &#x60;only&#x60; searches attachment metadata only. If &#x60;titles_only&#x60; is enabled, attachment metadata is searched only when this parameter is &#x60;only&#x60;.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param projects Include matching projects when the effective scope covers multiple projects or is global. Any non-empty value enables project results.
     * @return ResponseEntity&lt;GetSearch200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Search#GET">Search within project Documentation</a>
     */
    public ResponseEntity<GetSearch200Response> getSearchByProjectWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String allWords, @jakarta.annotation.Nullable String titlesOnly, @jakarta.annotation.Nullable Integer issues, @jakarta.annotation.Nullable Integer news, @jakarta.annotation.Nullable Integer wikiPages, @jakarta.annotation.Nullable Integer documents, @jakarta.annotation.Nullable Integer changesets, @jakarta.annotation.Nullable Integer messages, @jakarta.annotation.Nullable String openIssues, @jakarta.annotation.Nullable String attachments, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer projects) throws RestClientResponseException {
        ParameterizedTypeReference<GetSearch200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSearchByProjectRequestCreation(format, projectId, limit, offset, nometa, xRedmineSwitchUser, xRedmineNometa, q, scope, allWords, titlesOnly, issues, news, wikiPages, documents, changesets, messages, openIssues, attachments, page, projects).toEntity(localVarReturnType);
    }

    /**
     * Search within project
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param limit Maximum records per page. Defaults to 25 when omitted or less than 1; values above 100 are capped at 100.
     * @param offset Number of records to skip. Negative values are treated as 0. Takes precedence over &#x60;page&#x60;; if neither is supplied, the first page is returned.
     * @param nometa Send &#x60;1&#x60; to omit &#x60;total_count&#x60;, &#x60;offset&#x60;, and &#x60;limit&#x60; from the response. The &#x60;X-Redmine-Nometa&#x60; header is an alternative.
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param xRedmineNometa The xRedmineNometa parameter
     * @param q Search query split into at most five unique tokens; quoted phrases form one token. Non-Han tokens need at least two characters, while a Han character can be a token. If the query is omitted or has no usable tokens, Redmine returns an empty result and &#x60;total_count: null&#x60;. By default, all tokens must match; use &#x60;all_words&#x60; to allow any token to match.
     * @param scope Defaults to the project in the path. &#x60;subprojects&#x60; includes that project and its descendants. &#x60;all&#x60;, &#x60;my_projects&#x60;, and &#x60;bookmarks&#x60; replace this context with all visible projects, projects the current user belongs to, or the current user&#39;s bookmarked projects, respectively.
     * @param allWords Require all words to match. Default is &#x60;true&#x60; (all words must match). Set to empty string to disable (any word matches). Any non-empty value enables this option.
     * @param titlesOnly Search titles only. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param issues Include issues in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param news Include news in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param wikiPages Include wiki pages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param documents Include documents in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param changesets Include changesets in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param messages Include forum messages in search results. Any non-empty value enables this search type. If no search type parameters are specified, all types are searched.
     * @param openIssues Only return open issues. Default is &#x60;false&#x60;. Any non-empty value enables this option.
     * @param attachments Search attachment metadata (filenames and descriptions) associated with searchable records; file contents are not searched. &#x60;0&#x60; (default) searches record fields only, &#x60;1&#x60; searches record fields and attachment metadata, and &#x60;only&#x60; searches attachment metadata only. If &#x60;titles_only&#x60; is enabled, attachment metadata is searched only when this parameter is &#x60;only&#x60;.
     * @param page Page number, starting at 1. Used only when &#x60;offset&#x60; is absent; the offset is &#x60;(page - 1) * limit&#x60;, with negative results treated as 0.
     * @param projects Include matching projects when the effective scope covers multiple projects or is global. Any non-empty value enables project results.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_Search#GET">Search within project Documentation</a>
     */
    public ResponseSpec getSearchByProjectWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable Integer limit, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer nometa, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable Integer xRedmineNometa, @jakarta.annotation.Nullable String q, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String allWords, @jakarta.annotation.Nullable String titlesOnly, @jakarta.annotation.Nullable Integer issues, @jakarta.annotation.Nullable Integer news, @jakarta.annotation.Nullable Integer wikiPages, @jakarta.annotation.Nullable Integer documents, @jakarta.annotation.Nullable Integer changesets, @jakarta.annotation.Nullable Integer messages, @jakarta.annotation.Nullable String openIssues, @jakarta.annotation.Nullable String attachments, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer projects) throws RestClientResponseException {
        return getSearchByProjectRequestCreation(format, projectId, limit, offset, nometa, xRedmineSwitchUser, xRedmineNometa, q, scope, allWords, titlesOnly, issues, news, wikiPages, documents, changesets, messages, openIssues, attachments, page, projects);
    }
}
