package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.QueryCreateForm;
import ai.gebo.openproject.client.model.QueryModel;
import ai.gebo.openproject.client.model.QueryUpdateForm;

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
public class QueriesApi {
    private ApiClient apiClient;

    public QueriesApi() {
        this(new ApiClient());
    }

    public QueriesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create query
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a Query can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;),   e.g. a user, project or operator not found
     * @param queryCreateForm The queryCreateForm parameter
     * @return QueryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createQueryRequestCreation(@jakarta.annotation.Nullable QueryCreateForm queryCreateForm) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries", HttpMethod.POST, pathParams, localVarQueryParams, queryCreateForm, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create query
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a Query can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;),   e.g. a user, project or operator not found
     * @param queryCreateForm The queryCreateForm parameter
     * @return QueryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public QueryModel createQuery(@jakarta.annotation.Nullable QueryCreateForm queryCreateForm) throws RestClientResponseException {
        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createQueryRequestCreation(queryCreateForm).body(localVarReturnType);
    }

    /**
     * Create query
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a Query can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;),   e.g. a user, project or operator not found
     * @param queryCreateForm The queryCreateForm parameter
     * @return ResponseEntity&lt;QueryModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<QueryModel> createQueryWithHttpInfo(@jakarta.annotation.Nullable QueryCreateForm queryCreateForm) throws RestClientResponseException {
        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createQueryRequestCreation(queryCreateForm).toEntity(localVarReturnType);
    }

    /**
     * Create query
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a Query can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;),   e.g. a user, project or operator not found
     * @param queryCreateForm The queryCreateForm parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createQueryWithResponseSpec(@jakarta.annotation.Nullable QueryCreateForm queryCreateForm) throws RestClientResponseException {
        return createQueryRequestCreation(queryCreateForm);
    }

    /**
     * Delete query
     * Delete the query identified by the id parameter
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteQueryRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteQuery", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete query
     * Delete the query identified by the id parameter
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteQuery(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteQueryRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete query
     * Delete the query identified by the id parameter
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteQueryWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteQueryRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete query
     * Delete the query identified by the id parameter
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteQueryWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteQueryRequestCreation(id);
    }

    /**
     * View default query
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in any project
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param timelineZoomLevel Indicates in what zoom level the timeline should be shown. Valid values are  &#x60;days&#x60;, &#x60;weeks&#x60;, &#x60;months&#x60;, &#x60;quarters&#x60;, and &#x60;years&#x60;.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getDefaultQueryRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable String timelineZoomLevel, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupBy", groupBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSums", showSums));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timestamps", timestamps));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timelineVisible", timelineVisible));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timelineZoomLevel", timelineZoomLevel));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showHierarchies", showHierarchies));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/default", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View default query
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in any project
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param timelineZoomLevel Indicates in what zoom level the timeline should be shown. Valid values are  &#x60;days&#x60;, &#x60;weeks&#x60;, &#x60;months&#x60;, &#x60;quarters&#x60;, and &#x60;years&#x60;.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getDefaultQuery(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable String timelineZoomLevel, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDefaultQueryRequestCreation(filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, timelineZoomLevel, showHierarchies).body(localVarReturnType);
    }

    /**
     * View default query
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in any project
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param timelineZoomLevel Indicates in what zoom level the timeline should be shown. Valid values are  &#x60;days&#x60;, &#x60;weeks&#x60;, &#x60;months&#x60;, &#x60;quarters&#x60;, and &#x60;years&#x60;.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getDefaultQueryWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable String timelineZoomLevel, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDefaultQueryRequestCreation(filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, timelineZoomLevel, showHierarchies).toEntity(localVarReturnType);
    }

    /**
     * View default query
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in any project
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param timelineZoomLevel Indicates in what zoom level the timeline should be shown. Valid values are  &#x60;days&#x60;, &#x60;weeks&#x60;, &#x60;months&#x60;, &#x60;quarters&#x60;, and &#x60;years&#x60;.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getDefaultQueryWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable String timelineZoomLevel, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        return getDefaultQueryRequestCreation(filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, timelineZoomLevel, showHierarchies);
    }

    /**
     * View default query for project
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query. The query will already be scoped to the project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/queries/default&#x60;](https://www.openproject.org/docs/api/endpoints/queries/#view-default-query-for-workspace)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in the project
     * <p><b>404</b> - Returned if the client does not have sufficient permissions to see the project.  **Required permission:** any permission in the project
     * @param id Id of the project the default query is requested for
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getDefaultQueryForProjectRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getDefaultQueryForProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupBy", groupBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSums", showSums));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timestamps", timestamps));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timelineVisible", timelineVisible));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showHierarchies", showHierarchies));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/queries/default", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View default query for project
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query. The query will already be scoped to the project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/queries/default&#x60;](https://www.openproject.org/docs/api/endpoints/queries/#view-default-query-for-workspace)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in the project
     * <p><b>404</b> - Returned if the client does not have sufficient permissions to see the project.  **Required permission:** any permission in the project
     * @param id Id of the project the default query is requested for
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public Object getDefaultQueryForProject(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDefaultQueryForProjectRequestCreation(id, filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, showHierarchies).body(localVarReturnType);
    }

    /**
     * View default query for project
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query. The query will already be scoped to the project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/queries/default&#x60;](https://www.openproject.org/docs/api/endpoints/queries/#view-default-query-for-workspace)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in the project
     * <p><b>404</b> - Returned if the client does not have sufficient permissions to see the project.  **Required permission:** any permission in the project
     * @param id Id of the project the default query is requested for
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<Object> getDefaultQueryForProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDefaultQueryForProjectRequestCreation(id, filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, showHierarchies).toEntity(localVarReturnType);
    }

    /**
     * View default query for project
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query. The query will already be scoped to the project.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/queries/default&#x60;](https://www.openproject.org/docs/api/endpoints/queries/#view-default-query-for-workspace)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in the project
     * <p><b>404</b> - Returned if the client does not have sufficient permissions to see the project.  **Required permission:** any permission in the project
     * @param id Id of the project the default query is requested for
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec getDefaultQueryForProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        return getDefaultQueryForProjectRequestCreation(id, filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, showHierarchies);
    }

    /**
     * View default query for workspace
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query. The query will already be scoped to the workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in the workspace
     * <p><b>404</b> - Returned if the client does not have sufficient permissions to see the workspace.  **Required permission:** any permission in the workspace
     * @param id Id of the workspace the default query is requested for
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getDefaultQueryForWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getDefaultQueryForWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupBy", groupBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSums", showSums));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timestamps", timestamps));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timelineVisible", timelineVisible));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showHierarchies", showHierarchies));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/queries/default", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View default query for workspace
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query. The query will already be scoped to the workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in the workspace
     * <p><b>404</b> - Returned if the client does not have sufficient permissions to see the workspace.  **Required permission:** any permission in the workspace
     * @param id Id of the workspace the default query is requested for
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getDefaultQueryForWorkspace(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDefaultQueryForWorkspaceRequestCreation(id, filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, showHierarchies).body(localVarReturnType);
    }

    /**
     * View default query for workspace
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query. The query will already be scoped to the workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in the workspace
     * <p><b>404</b> - Returned if the client does not have sufficient permissions to see the workspace.  **Required permission:** any permission in the workspace
     * @param id Id of the workspace the default query is requested for
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getDefaultQueryForWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDefaultQueryForWorkspaceRequestCreation(id, filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, showHierarchies).toEntity(localVarReturnType);
    }

    /**
     * View default query for workspace
     * Same as [viewing an existing, persisted Query](https://www.openproject.org/docs/api/endpoints/queries/#list-queries) in its response, this resource returns an unpersisted query and by that allows to get the default query configuration. The client may also provide additional parameters which will modify the default query. The query will already be scoped to the workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the default query.  **Required permission:** view work packages in the workspace
     * <p><b>404</b> - Returned if the client does not have sufficient permissions to see the workspace.  **Required permission:** any permission in the workspace
     * @param id Id of the workspace the default query is requested for
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getDefaultQueryForWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        return getDefaultQueryForWorkspaceRequestCreation(id, filters, offset, pageSize, sortBy, groupBy, showSums, timestamps, timelineVisible, showHierarchies);
    }

    /**
     * View query
     * Retrieve an individual query as identified by the id parameter. Then endpoint accepts a number of parameters that can be used to override the resources&#39; persisted parameters.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param columns Selected columns for the table view. Repeat the parameter for every column, e.g. &#x60;columns[]&#x3D;type&amp;columns[]&#x3D;priority&#x60;.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param timelineLabels Overridden labels in the timeline view
     * @param highlightingMode Highlighting mode for the table view.
     * @param highlightedAttributes Highlighted attributes mode for the table view when &#x60;highlightingMode&#x60; is &#x60;inline&#x60;. Repeat the parameter for every attribute, e.g. &#x60;highlightedAttributes[]&#x3D;type&amp;highlightedAttributes[]&#x3D;priority&#x60;. When omitted, all highlightable attributes will be returned as &#x60;highlightedAttributes&#x60;.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return QueryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getQueryRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable List<String> columns, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable String timelineLabels, @jakarta.annotation.Nullable String highlightingMode, @jakarta.annotation.Nullable List<String> highlightedAttributes, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getQuery", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "columns", columns));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupBy", groupBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSums", showSums));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timestamps", timestamps));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timelineVisible", timelineVisible));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timelineLabels", timelineLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "highlightingMode", highlightingMode));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "highlightedAttributes", highlightedAttributes));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showHierarchies", showHierarchies));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View query
     * Retrieve an individual query as identified by the id parameter. Then endpoint accepts a number of parameters that can be used to override the resources&#39; persisted parameters.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param columns Selected columns for the table view. Repeat the parameter for every column, e.g. &#x60;columns[]&#x3D;type&amp;columns[]&#x3D;priority&#x60;.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param timelineLabels Overridden labels in the timeline view
     * @param highlightingMode Highlighting mode for the table view.
     * @param highlightedAttributes Highlighted attributes mode for the table view when &#x60;highlightingMode&#x60; is &#x60;inline&#x60;. Repeat the parameter for every attribute, e.g. &#x60;highlightedAttributes[]&#x3D;type&amp;highlightedAttributes[]&#x3D;priority&#x60;. When omitted, all highlightable attributes will be returned as &#x60;highlightedAttributes&#x60;.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return QueryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public QueryModel getQuery(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable List<String> columns, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable String timelineLabels, @jakarta.annotation.Nullable String highlightingMode, @jakarta.annotation.Nullable List<String> highlightedAttributes, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getQueryRequestCreation(id, filters, offset, pageSize, columns, sortBy, groupBy, showSums, timestamps, timelineVisible, timelineLabels, highlightingMode, highlightedAttributes, showHierarchies).body(localVarReturnType);
    }

    /**
     * View query
     * Retrieve an individual query as identified by the id parameter. Then endpoint accepts a number of parameters that can be used to override the resources&#39; persisted parameters.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param columns Selected columns for the table view. Repeat the parameter for every column, e.g. &#x60;columns[]&#x3D;type&amp;columns[]&#x3D;priority&#x60;.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param timelineLabels Overridden labels in the timeline view
     * @param highlightingMode Highlighting mode for the table view.
     * @param highlightedAttributes Highlighted attributes mode for the table view when &#x60;highlightingMode&#x60; is &#x60;inline&#x60;. Repeat the parameter for every attribute, e.g. &#x60;highlightedAttributes[]&#x3D;type&amp;highlightedAttributes[]&#x3D;priority&#x60;. When omitted, all highlightable attributes will be returned as &#x60;highlightedAttributes&#x60;.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return ResponseEntity&lt;QueryModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<QueryModel> getQueryWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable List<String> columns, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable String timelineLabels, @jakarta.annotation.Nullable String highlightingMode, @jakarta.annotation.Nullable List<String> highlightedAttributes, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getQueryRequestCreation(id, filters, offset, pageSize, columns, sortBy, groupBy, showSums, timestamps, timelineVisible, timelineLabels, highlightingMode, highlightedAttributes, showHierarchies).toEntity(localVarReturnType);
    }

    /**
     * View query
     * Retrieve an individual query as identified by the id parameter. Then endpoint accepts a number of parameters that can be used to override the resources&#39; persisted parameters.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @param filters JSON specifying filter conditions. The filters provided as parameters are not applied to the query but are instead used to override the query&#39;s persisted filters. All filters also accepted by the work packages endpoint are accepted. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param offset Page number inside the queries&#39; result collection of work packages.
     * @param pageSize Number of elements to display per page for the queries&#39; result collection of work packages.
     * @param columns Selected columns for the table view. Repeat the parameter for every column, e.g. &#x60;columns[]&#x3D;type&amp;columns[]&#x3D;priority&#x60;.
     * @param sortBy JSON specifying sort criteria. The sort criteria is applied to the query&#39;s result collection of work packages overriding the query&#39;s persisted sort criteria.
     * @param groupBy The column to group by. The grouping criteria is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted group criteria.
     * @param showSums Indicates whether properties should be summed up if they support it. The showSums parameter is applied to the to the query&#39;s result collection of work packages overriding the query&#39;s persisted sums property.
     * @param timestamps Indicates the timestamps to filter by when showing changed attributes on work packages. Values can be either ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;. Values older than 1 day are accepted only with valid Enterprise Token available. 
     * @param timelineVisible Indicates whether the timeline should be shown.
     * @param timelineLabels Overridden labels in the timeline view
     * @param highlightingMode Highlighting mode for the table view.
     * @param highlightedAttributes Highlighted attributes mode for the table view when &#x60;highlightingMode&#x60; is &#x60;inline&#x60;. Repeat the parameter for every attribute, e.g. &#x60;highlightedAttributes[]&#x3D;type&amp;highlightedAttributes[]&#x3D;priority&#x60;. When omitted, all highlightable attributes will be returned as &#x60;highlightedAttributes&#x60;.
     * @param showHierarchies Indicates whether the hierarchy mode should be enabled.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getQueryWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable List<String> columns, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String timestamps, @jakarta.annotation.Nullable Boolean timelineVisible, @jakarta.annotation.Nullable String timelineLabels, @jakarta.annotation.Nullable String highlightingMode, @jakarta.annotation.Nullable List<String> highlightedAttributes, @jakarta.annotation.Nullable Boolean showHierarchies) throws RestClientResponseException {
        return getQueryRequestCreation(id, filters, offset, pageSize, columns, sortBy, groupBy, showSums, timestamps, timelineVisible, timelineLabels, highlightingMode, highlightedAttributes, showHierarchies);
    }

    /**
     * View schema for global queries
     * Retrieve the schema for global queries, those, that are not assigned to a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSchemaForGlobalQueriesRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View schema for global queries
     * Retrieve the schema for global queries, those, that are not assigned to a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in any project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getSchemaForGlobalQueries() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSchemaForGlobalQueriesRequestCreation().body(localVarReturnType);
    }

    /**
     * View schema for global queries
     * Retrieve the schema for global queries, those, that are not assigned to a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in any project
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getSchemaForGlobalQueriesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSchemaForGlobalQueriesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * View schema for global queries
     * Retrieve the schema for global queries, those, that are not assigned to a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in any project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSchemaForGlobalQueriesWithResponseSpec() throws RestClientResponseException {
        return getSchemaForGlobalQueriesRequestCreation();
    }

    /**
     * View schema for project queries
     * Retrieve the schema for project queries.  This endpoint is deprecated and replaced by [&#39;/api/v3/workspaces/{id}/queries/schema&#x60;](https://www.openproject.org/docs/api/endpoints/queries/#view-schema-for-workspace-queries)
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in the project
     * @param id Project id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSchemaForProjectQueriesRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getSchemaForProjectQueries", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/queries/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View schema for project queries
     * Retrieve the schema for project queries.  This endpoint is deprecated and replaced by [&#39;/api/v3/workspaces/{id}/queries/schema&#x60;](https://www.openproject.org/docs/api/endpoints/queries/#view-schema-for-workspace-queries)
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in the project
     * @param id Project id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public Object getSchemaForProjectQueries(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSchemaForProjectQueriesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View schema for project queries
     * Retrieve the schema for project queries.  This endpoint is deprecated and replaced by [&#39;/api/v3/workspaces/{id}/queries/schema&#x60;](https://www.openproject.org/docs/api/endpoints/queries/#view-schema-for-workspace-queries)
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in the project
     * @param id Project id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<Object> getSchemaForProjectQueriesWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSchemaForProjectQueriesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View schema for project queries
     * Retrieve the schema for project queries.  This endpoint is deprecated and replaced by [&#39;/api/v3/workspaces/{id}/queries/schema&#x60;](https://www.openproject.org/docs/api/endpoints/queries/#view-schema-for-workspace-queries)
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in the project
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec getSchemaForProjectQueriesWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getSchemaForProjectQueriesRequestCreation(id);
    }

    /**
     * View schema for workspace queries
     * Retrieve the schema for workspace queries.
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in the workspace
     * @param id Project id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSchemaForWorkspaceQueriesRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getSchemaForWorkspaceQueries", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/queries/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View schema for workspace queries
     * Retrieve the schema for workspace queries.
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in the workspace
     * @param id Project id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getSchemaForWorkspaceQueries(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSchemaForWorkspaceQueriesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View schema for workspace queries
     * Retrieve the schema for workspace queries.
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in the workspace
     * @param id Project id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getSchemaForWorkspaceQueriesWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getSchemaForWorkspaceQueriesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View schema for workspace queries
     * Retrieve the schema for workspace queries.
     * <p><b>200</b> - OK
     * <p><b>403</b> - **Required permission:** view work package in the workspace
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSchemaForWorkspaceQueriesWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getSchemaForWorkspaceQueriesRequestCreation(id);
    }

    /**
     * Available projects for query
     * Gets a list of projects that are available as projects a query can be assigned to.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableProjectsForQueryRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/available_projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Available projects for query
     * Gets a list of projects that are available as projects a query can be assigned to.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listAvailableProjectsForQuery() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForQueryRequestCreation().body(localVarReturnType);
    }

    /**
     * Available projects for query
     * Gets a list of projects that are available as projects a query can be assigned to.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listAvailableProjectsForQueryWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForQueryRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Available projects for query
     * Gets a list of projects that are available as projects a query can be assigned to.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableProjectsForQueryWithResponseSpec() throws RestClientResponseException {
        return listAvailableProjectsForQueryRequestCreation();
    }

    /**
     * List queries
     * Returns a collection of queries. The collection can be filtered via query parameters similar to how work packages are filtered. Please note however, that the filters are applied to the queries and not to the work packages the queries in turn might return.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see queries.  **Required permission:** view work packages or manage public queries in any project
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + project: filters queries by the project they are assigned to. If the project filter is passed with the &#x60;!*&#x60; (not any) operator, global queries are returned.  + id: filters queries based on their id  + updated_at: filters queries based on the last time they where updated
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listQueriesRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List queries
     * Returns a collection of queries. The collection can be filtered via query parameters similar to how work packages are filtered. Please note however, that the filters are applied to the queries and not to the work packages the queries in turn might return.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see queries.  **Required permission:** view work packages or manage public queries in any project
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + project: filters queries by the project they are assigned to. If the project filter is passed with the &#x60;!*&#x60; (not any) operator, global queries are returned.  + id: filters queries based on their id  + updated_at: filters queries based on the last time they where updated
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listQueries(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listQueriesRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * List queries
     * Returns a collection of queries. The collection can be filtered via query parameters similar to how work packages are filtered. Please note however, that the filters are applied to the queries and not to the work packages the queries in turn might return.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see queries.  **Required permission:** view work packages or manage public queries in any project
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + project: filters queries by the project they are assigned to. If the project filter is passed with the &#x60;!*&#x60; (not any) operator, global queries are returned.  + id: filters queries based on their id  + updated_at: filters queries based on the last time they where updated
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listQueriesWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listQueriesRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * List queries
     * Returns a collection of queries. The collection can be filtered via query parameters similar to how work packages are filtered. Please note however, that the filters are applied to the queries and not to the work packages the queries in turn might return.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see queries.  **Required permission:** view work packages or manage public queries in any project
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + project: filters queries by the project they are assigned to. If the project filter is passed with the &#x60;!*&#x60; (not any) operator, global queries are returned.  + id: filters queries based on their id  + updated_at: filters queries based on the last time they where updated
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listQueriesWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listQueriesRequestCreation(filters);
    }

    /**
     * Star query
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec starQueryRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling starQuery", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/{id}/star", HttpMethod.PATCH, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Star query
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object starQuery(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return starQueryRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Star query
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> starQueryWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return starQueryRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Star query
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * @param id Query id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec starQueryWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return starQueryRequestCreation(id);
    }

    /**
     * Unstar query
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Query id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unstarQueryRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling unstarQuery", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/{id}/unstar", HttpMethod.PATCH, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unstar query
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Query id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object unstarQuery(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unstarQueryRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unstar query
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Query id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> unstarQueryWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unstarQueryRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unstar query
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** for own queries none; for public queries: manage public queries  *Note that you will only receive this error, if you are at least allowed to see the corresponding query.*
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required condition:** query belongs to user or query is public  **Required permission:** view work package in queries project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Query id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec unstarQueryWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return unstarQueryRequestCreation(id);
    }

    /**
     * Edit Query
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. Note that it is only allowed to provide properties or links supporting the **write** operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages in the query&#39;s project (unless global)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Query id
     * @param queryUpdateForm The queryUpdateForm parameter
     * @return QueryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateQueryRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable QueryUpdateForm queryUpdateForm) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateQuery", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, queryUpdateForm, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Edit Query
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. Note that it is only allowed to provide properties or links supporting the **write** operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages in the query&#39;s project (unless global)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Query id
     * @param queryUpdateForm The queryUpdateForm parameter
     * @return QueryModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public QueryModel updateQuery(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable QueryUpdateForm queryUpdateForm) throws RestClientResponseException {
        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateQueryRequestCreation(id, queryUpdateForm).body(localVarReturnType);
    }

    /**
     * Edit Query
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. Note that it is only allowed to provide properties or links supporting the **write** operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages in the query&#39;s project (unless global)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Query id
     * @param queryUpdateForm The queryUpdateForm parameter
     * @return ResponseEntity&lt;QueryModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<QueryModel> updateQueryWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable QueryUpdateForm queryUpdateForm) throws RestClientResponseException {
        ParameterizedTypeReference<QueryModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateQueryRequestCreation(id, queryUpdateForm).toEntity(localVarReturnType);
    }

    /**
     * Edit Query
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. Note that it is only allowed to provide properties or links supporting the **write** operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package
     * <p><b>404</b> - Returned if the query does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages in the query&#39;s project (unless global)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Query id
     * @param queryUpdateForm The queryUpdateForm parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateQueryWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable QueryUpdateForm queryUpdateForm) throws RestClientResponseException {
        return updateQueryRequestCreation(id, queryUpdateForm);
    }

    /**
     * Query Create Form
     * 
     * <p><b>200</b> - OK
     * @param queryCreateForm The queryCreateForm parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateQueryCreateFormRequestCreation(@jakarta.annotation.Nullable QueryCreateForm queryCreateForm) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/form", HttpMethod.POST, pathParams, localVarQueryParams, queryCreateForm, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Query Create Form
     * 
     * <p><b>200</b> - OK
     * @param queryCreateForm The queryCreateForm parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateQueryCreateForm(@jakarta.annotation.Nullable QueryCreateForm queryCreateForm) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateQueryCreateFormRequestCreation(queryCreateForm).body(localVarReturnType);
    }

    /**
     * Query Create Form
     * 
     * <p><b>200</b> - OK
     * @param queryCreateForm The queryCreateForm parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateQueryCreateFormWithHttpInfo(@jakarta.annotation.Nullable QueryCreateForm queryCreateForm) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateQueryCreateFormRequestCreation(queryCreateForm).toEntity(localVarReturnType);
    }

    /**
     * Query Create Form
     * 
     * <p><b>200</b> - OK
     * @param queryCreateForm The queryCreateForm parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateQueryCreateFormWithResponseSpec(@jakarta.annotation.Nullable QueryCreateForm queryCreateForm) throws RestClientResponseException {
        return validateQueryCreateFormRequestCreation(queryCreateForm);
    }

    /**
     * Query Update Form
     * 
     * <p><b>200</b> - OK
     * @param id Query id
     * @param queryUpdateForm The queryUpdateForm parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateQueryUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable QueryUpdateForm queryUpdateForm) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateQueryUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/queries/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, queryUpdateForm, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Query Update Form
     * 
     * <p><b>200</b> - OK
     * @param id Query id
     * @param queryUpdateForm The queryUpdateForm parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validateQueryUpdateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable QueryUpdateForm queryUpdateForm) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validateQueryUpdateFormRequestCreation(id, queryUpdateForm).body(localVarReturnType);
    }

    /**
     * Query Update Form
     * 
     * <p><b>200</b> - OK
     * @param id Query id
     * @param queryUpdateForm The queryUpdateForm parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validateQueryUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable QueryUpdateForm queryUpdateForm) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateQueryUpdateFormRequestCreation(id, queryUpdateForm).toEntity(localVarReturnType);
    }

    /**
     * Query Update Form
     * 
     * <p><b>200</b> - OK
     * @param id Query id
     * @param queryUpdateForm The queryUpdateForm parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateQueryUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable QueryUpdateForm queryUpdateForm) throws RestClientResponseException {
        return validateQueryUpdateFormRequestCreation(id, queryUpdateForm);
    }
}
