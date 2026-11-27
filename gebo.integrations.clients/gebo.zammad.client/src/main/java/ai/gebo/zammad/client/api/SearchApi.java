package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ConditionSearchRequest;
import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.GenericSearchResult;
import ai.gebo.zammad.client.model.GlobalSearchResult;
import ai.gebo.zammad.client.model.SearchGroupsOrderByParameter;

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
     * Search globally
     * Search users, tickets, organizations, Knowledge Base content, and chats visible to the current user.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param objects Optional comma-separated object types.
     * @return GlobalSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec globalSearchRequestCreation(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String objects) throws RestClientResponseException {
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new RestClientResponseException("Missing the required parameter 'query' when calling globalSearch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "objects", objects));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GlobalSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search globally
     * Search users, tickets, organizations, Knowledge Base content, and chats visible to the current user.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param objects Optional comma-separated object types.
     * @return GlobalSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GlobalSearchResult globalSearch(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String objects) throws RestClientResponseException {
        ParameterizedTypeReference<GlobalSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return globalSearchRequestCreation(query, from, page, perPage, objects).body(localVarReturnType);
    }

    /**
     * Search globally
     * Search users, tickets, organizations, Knowledge Base content, and chats visible to the current user.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param objects Optional comma-separated object types.
     * @return ResponseEntity&lt;GlobalSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GlobalSearchResult> globalSearchWithHttpInfo(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String objects) throws RestClientResponseException {
        ParameterizedTypeReference<GlobalSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return globalSearchRequestCreation(query, from, page, perPage, objects).toEntity(localVarReturnType);
    }

    /**
     * Search globally
     * Search users, tickets, organizations, Knowledge Base content, and chats visible to the current user.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param objects Optional comma-separated object types.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec globalSearchWithResponseSpec(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String objects) throws RestClientResponseException {
        return globalSearchRequestCreation(query, from, page, perPage, objects);
    }

    /**
     * Search macros
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchMacrosRequestCreation(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new RestClientResponseException("Missing the required parameter 'query' when calling searchMacros", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort_by", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/macros/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search macros
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GenericSearchResult searchMacros(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchMacrosRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).body(localVarReturnType);
    }

    /**
     * Search macros
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseEntity&lt;GenericSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericSearchResult> searchMacrosWithHttpInfo(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchMacrosRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).toEntity(localVarReturnType);
    }

    /**
     * Search macros
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchMacrosWithResponseSpec(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        return searchMacrosRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy);
    }

    /**
     * Search macros by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchMacrosByConditionRequestCreation(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'conditionSearchRequest' is set
        if (conditionSearchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'conditionSearchRequest' when calling searchMacrosByCondition", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/macros/search", HttpMethod.POST, pathParams, localVarQueryParams, conditionSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search macros by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GenericSearchResult searchMacrosByCondition(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchMacrosByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).body(localVarReturnType);
    }

    /**
     * Search macros by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;GenericSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericSearchResult> searchMacrosByConditionWithHttpInfo(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchMacrosByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search macros by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchMacrosByConditionWithResponseSpec(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return searchMacrosByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage);
    }

    /**
     * Search overviews
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchOverviewsRequestCreation(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new RestClientResponseException("Missing the required parameter 'query' when calling searchOverviews", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort_by", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/overviews/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search overviews
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GenericSearchResult searchOverviews(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchOverviewsRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).body(localVarReturnType);
    }

    /**
     * Search overviews
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseEntity&lt;GenericSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericSearchResult> searchOverviewsWithHttpInfo(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchOverviewsRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).toEntity(localVarReturnType);
    }

    /**
     * Search overviews
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchOverviewsWithResponseSpec(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        return searchOverviewsRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy);
    }

    /**
     * Search overviews by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchOverviewsByConditionRequestCreation(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'conditionSearchRequest' is set
        if (conditionSearchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'conditionSearchRequest' when calling searchOverviewsByCondition", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/overviews/search", HttpMethod.POST, pathParams, localVarQueryParams, conditionSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search overviews by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GenericSearchResult searchOverviewsByCondition(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchOverviewsByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).body(localVarReturnType);
    }

    /**
     * Search overviews by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;GenericSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericSearchResult> searchOverviewsByConditionWithHttpInfo(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchOverviewsByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search overviews by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchOverviewsByConditionWithResponseSpec(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return searchOverviewsByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage);
    }

    /**
     * Search templates
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchTemplatesRequestCreation(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new RestClientResponseException("Missing the required parameter 'query' when calling searchTemplates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort_by", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/templates/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search templates
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GenericSearchResult searchTemplates(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTemplatesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).body(localVarReturnType);
    }

    /**
     * Search templates
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseEntity&lt;GenericSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericSearchResult> searchTemplatesWithHttpInfo(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTemplatesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).toEntity(localVarReturnType);
    }

    /**
     * Search templates
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchTemplatesWithResponseSpec(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        return searchTemplatesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy);
    }

    /**
     * Search templates by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchTemplatesByConditionRequestCreation(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'conditionSearchRequest' is set
        if (conditionSearchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'conditionSearchRequest' when calling searchTemplatesByCondition", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/templates/search", HttpMethod.POST, pathParams, localVarQueryParams, conditionSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search templates by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GenericSearchResult searchTemplatesByCondition(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTemplatesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).body(localVarReturnType);
    }

    /**
     * Search templates by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;GenericSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericSearchResult> searchTemplatesByConditionWithHttpInfo(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTemplatesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search templates by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchTemplatesByConditionWithResponseSpec(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return searchTemplatesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage);
    }

    /**
     * Search text modules
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchTextModulesRequestCreation(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new RestClientResponseException("Missing the required parameter 'query' when calling searchTextModules", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort_by", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/text_modules/search", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search text modules
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GenericSearchResult searchTextModules(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTextModulesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).body(localVarReturnType);
    }

    /**
     * Search text modules
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseEntity&lt;GenericSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericSearchResult> searchTextModulesWithHttpInfo(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTextModulesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy).toEntity(localVarReturnType);
    }

    /**
     * Search text modules
     * Search by a UTF-8 query. Response shape depends on &#x60;full&#x60;, &#x60;expand&#x60;, &#x60;with_total_count&#x60;, and &#x60;only_total_count&#x60;. The official introduction confirms search support, while resource-specific payload details are installation-dependent.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param query UTF-8 search expression.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @param sortBy Response field used for sorting.
     * @param orderBy Sort direction.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchTextModulesWithResponseSpec(@jakarta.annotation.Nonnull String query, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable SearchGroupsOrderByParameter orderBy) throws RestClientResponseException {
        return searchTextModulesRequestCreation(query, from, expand, full, withTotalCount, onlyTotalCount, page, perPage, sortBy, orderBy);
    }

    /**
     * Search text modules by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchTextModulesByConditionRequestCreation(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'conditionSearchRequest' is set
        if (conditionSearchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'conditionSearchRequest' when calling searchTextModulesByCondition", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_total_count", withTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "only_total_count", onlyTotalCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/text_modules/search", HttpMethod.POST, pathParams, localVarQueryParams, conditionSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search text modules by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return GenericSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public GenericSearchResult searchTextModulesByCondition(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTextModulesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).body(localVarReturnType);
    }

    /**
     * Search text modules by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;GenericSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GenericSearchResult> searchTextModulesByConditionWithHttpInfo(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<GenericSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchTextModulesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * Search text modules by condition
     * Condition-based search using the same condition format as Zammad triggers and overviews.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param conditionSearchRequest The conditionSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param expand Include resolved relation names in the response.
     * @param full Return record IDs and related assets.
     * @param withTotalCount Include total result count.
     * @param onlyTotalCount Return only the total result count.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchTextModulesByConditionWithResponseSpec(@jakarta.annotation.Nonnull ConditionSearchRequest conditionSearchRequest, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean expand, @jakarta.annotation.Nullable Boolean full, @jakarta.annotation.Nullable Boolean withTotalCount, @jakarta.annotation.Nullable Boolean onlyTotalCount, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return searchTextModulesByConditionRequestCreation(conditionSearchRequest, from, expand, full, withTotalCount, onlyTotalCount, page, perPage);
    }
}
