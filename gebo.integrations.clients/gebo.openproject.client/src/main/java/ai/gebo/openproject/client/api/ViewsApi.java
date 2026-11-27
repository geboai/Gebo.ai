package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.CreateViewsRequest;
import ai.gebo.openproject.client.model.ErrorResponse;

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
public class ViewsApi {
    private ApiClient apiClient;

    public ViewsApi() {
        this(new ApiClient());
    }

    public ViewsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create view
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a View can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  There are different subtypes of &#x60;Views&#x60; (e.g. &#x60;Views::WorkPackagesTable&#x60;) with each having its own endpoint for creating that subtype e.g.  * &#x60;/api/v3/views/work_packages_table&#x60; for &#x60;Views::WorkPackagesTable&#x60; * &#x60;/api/v3/views/team_planner&#x60; for &#x60;Views::TeamPlanner&#x60; * &#x60;/api/v3/views/work_packages_calendar&#x60; for &#x60;Views::WorkPackagesCalendar&#x60;  **Not yet implemented** To get the list of available subtypes and by that the endpoints for creating a subtype, use the &#x60;&#x60;&#x60;   /api/v3/views/schemas &#x60;&#x60;&#x60; endpoint.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;),   e.g. a query not found
     * @param id The view identifier
     * @param createViewsRequest The createViewsRequest parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createViewsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable CreateViewsRequest createViewsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createViews", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/views/{id}", HttpMethod.POST, pathParams, localVarQueryParams, createViewsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create view
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a View can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  There are different subtypes of &#x60;Views&#x60; (e.g. &#x60;Views::WorkPackagesTable&#x60;) with each having its own endpoint for creating that subtype e.g.  * &#x60;/api/v3/views/work_packages_table&#x60; for &#x60;Views::WorkPackagesTable&#x60; * &#x60;/api/v3/views/team_planner&#x60; for &#x60;Views::TeamPlanner&#x60; * &#x60;/api/v3/views/work_packages_calendar&#x60; for &#x60;Views::WorkPackagesCalendar&#x60;  **Not yet implemented** To get the list of available subtypes and by that the endpoints for creating a subtype, use the &#x60;&#x60;&#x60;   /api/v3/views/schemas &#x60;&#x60;&#x60; endpoint.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;),   e.g. a query not found
     * @param id The view identifier
     * @param createViewsRequest The createViewsRequest parameter
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object createViews(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable CreateViewsRequest createViewsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createViewsRequestCreation(id, createViewsRequest).body(localVarReturnType);
    }

    /**
     * Create view
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a View can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  There are different subtypes of &#x60;Views&#x60; (e.g. &#x60;Views::WorkPackagesTable&#x60;) with each having its own endpoint for creating that subtype e.g.  * &#x60;/api/v3/views/work_packages_table&#x60; for &#x60;Views::WorkPackagesTable&#x60; * &#x60;/api/v3/views/team_planner&#x60; for &#x60;Views::TeamPlanner&#x60; * &#x60;/api/v3/views/work_packages_calendar&#x60; for &#x60;Views::WorkPackagesCalendar&#x60;  **Not yet implemented** To get the list of available subtypes and by that the endpoints for creating a subtype, use the &#x60;&#x60;&#x60;   /api/v3/views/schemas &#x60;&#x60;&#x60; endpoint.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;),   e.g. a query not found
     * @param id The view identifier
     * @param createViewsRequest The createViewsRequest parameter
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> createViewsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable CreateViewsRequest createViewsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createViewsRequestCreation(id, createViewsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create view
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a View can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  There are different subtypes of &#x60;Views&#x60; (e.g. &#x60;Views::WorkPackagesTable&#x60;) with each having its own endpoint for creating that subtype e.g.  * &#x60;/api/v3/views/work_packages_table&#x60; for &#x60;Views::WorkPackagesTable&#x60; * &#x60;/api/v3/views/team_planner&#x60; for &#x60;Views::TeamPlanner&#x60; * &#x60;/api/v3/views/work_packages_calendar&#x60; for &#x60;Views::WorkPackagesCalendar&#x60;  **Not yet implemented** To get the list of available subtypes and by that the endpoints for creating a subtype, use the &#x60;&#x60;&#x60;   /api/v3/views/schemas &#x60;&#x60;&#x60; endpoint.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)  * the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;),   e.g. a query not found
     * @param id The view identifier
     * @param createViewsRequest The createViewsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createViewsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable CreateViewsRequest createViewsRequest) throws RestClientResponseException {
        return createViewsRequestCreation(id, createViewsRequest);
    }

    /**
     * View view
     * 
     * <p><b>200</b> - Returns the result of a single view, dependent of the view type.
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** The required permission depends on the type of the view.
     * <p><b>404</b> - Returned if the resource can not be found.  *Note: A client without sufficient permissions shall not be able to test for the existence of a view. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id View id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getViewRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getView", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/views/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View view
     * 
     * <p><b>200</b> - Returns the result of a single view, dependent of the view type.
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** The required permission depends on the type of the view.
     * <p><b>404</b> - Returned if the resource can not be found.  *Note: A client without sufficient permissions shall not be able to test for the existence of a view. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id View id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getView(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getViewRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View view
     * 
     * <p><b>200</b> - Returns the result of a single view, dependent of the view type.
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** The required permission depends on the type of the view.
     * <p><b>404</b> - Returned if the resource can not be found.  *Note: A client without sufficient permissions shall not be able to test for the existence of a view. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id View id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getViewWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getViewRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View view
     * 
     * <p><b>200</b> - Returns the result of a single view, dependent of the view type.
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** The required permission depends on the type of the view.
     * <p><b>404</b> - Returned if the resource can not be found.  *Note: A client without sufficient permissions shall not be able to test for the existence of a view. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id View id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getViewWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getViewRequestCreation(id);
    }

    /**
     * List views
     * Returns a collection of Views. The collection can be filtered via query parameters similar to how work packages are filtered.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + project: filters views by the project their associated query is assigned to. If the project filter is passed with the &#x60;!*&#x60; (not any) operator, global views are returned.  + id: filters views based on their id  + type: filters views based on their type
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listViewsRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/views", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List views
     * Returns a collection of Views. The collection can be filtered via query parameters similar to how work packages are filtered.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + project: filters views by the project their associated query is assigned to. If the project filter is passed with the &#x60;!*&#x60; (not any) operator, global views are returned.  + id: filters views based on their id  + type: filters views based on their type
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void listViews(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        listViewsRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * List views
     * Returns a collection of Views. The collection can be filtered via query parameters similar to how work packages are filtered.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + project: filters views by the project their associated query is assigned to. If the project filter is passed with the &#x60;!*&#x60; (not any) operator, global views are returned.  + id: filters views based on their id  + type: filters views based on their type
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> listViewsWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listViewsRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * List views
     * Returns a collection of Views. The collection can be filtered via query parameters similar to how work packages are filtered.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + project: filters views by the project their associated query is assigned to. If the project filter is passed with the &#x60;!*&#x60; (not any) operator, global views are returned.  + id: filters views based on their id  + type: filters views based on their type
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listViewsWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listViewsRequestCreation(filters);
    }
}
