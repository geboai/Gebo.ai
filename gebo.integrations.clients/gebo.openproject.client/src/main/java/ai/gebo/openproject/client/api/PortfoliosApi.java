package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.PortfolioCollectionModel;
import ai.gebo.openproject.client.model.PortfolioModel;

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
public class PortfoliosApi {
    private ApiClient apiClient;

    public PortfoliosApi() {
        this(new ApiClient());
    }

    public PortfoliosApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete Portfolio
     * Deletes the portfolio permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the portfolio might exist well after the request has returned successfully. To prevent unwanted changes to the portfolio scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the portfolio was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the portfolio  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the portfolio cannot be deleted. This can happen when there are still references to the portfolio in other workspaces that need to be severed at first.
     * @param id Portfolio id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deletePortfolioRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deletePortfolio", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/portfolios/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete Portfolio
     * Deletes the portfolio permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the portfolio might exist well after the request has returned successfully. To prevent unwanted changes to the portfolio scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the portfolio was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the portfolio  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the portfolio cannot be deleted. This can happen when there are still references to the portfolio in other workspaces that need to be severed at first.
     * @param id Portfolio id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deletePortfolio(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deletePortfolioRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete Portfolio
     * Deletes the portfolio permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the portfolio might exist well after the request has returned successfully. To prevent unwanted changes to the portfolio scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the portfolio was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the portfolio  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the portfolio cannot be deleted. This can happen when there are still references to the portfolio in other workspaces that need to be severed at first.
     * @param id Portfolio id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deletePortfolioWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deletePortfolioRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete Portfolio
     * Deletes the portfolio permanently. As this is a lengthy process, the actual deletion is carried out asynchronously. So the portfolio might exist well after the request has returned successfully. To prevent unwanted changes to the portfolio scheduled for deletion, it is archived at once.
     * <p><b>204</b> - Returned if the portfolio was successfully deleted. There is currently no endpoint to query for the actual deletion status. Such an endpoint _might_ be added in the future.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the portfolio  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the portfolio cannot be deleted. This can happen when there are still references to the portfolio in other workspaces that need to be severed at first.
     * @param id Portfolio id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deletePortfolioWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deletePortfolioRequestCreation(id);
    }

    /**
     * View portfolio
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the portfolio  *Note: A client without sufficient permissions shall not be able to test for the existence of a portfolio. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Portfolio id
     * @return PortfolioModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPortfolioRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getPortfolio", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<PortfolioModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/portfolios/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View portfolio
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the portfolio  *Note: A client without sufficient permissions shall not be able to test for the existence of a portfolio. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Portfolio id
     * @return PortfolioModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public PortfolioModel getPortfolio(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<PortfolioModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getPortfolioRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View portfolio
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the portfolio  *Note: A client without sufficient permissions shall not be able to test for the existence of a portfolio. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Portfolio id
     * @return ResponseEntity&lt;PortfolioModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PortfolioModel> getPortfolioWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<PortfolioModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getPortfolioRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View portfolio
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** any permission in the portfolio  *Note: A client without sufficient permissions shall not be able to test for the existence of a portfolio. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Portfolio id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPortfolioWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getPortfolioRequestCreation(id);
    }

    /**
     * List portfolios
     * Returns a collection of portfolios. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain portfolios the client is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openportfolio.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the portfolio + ancestor: filters portfolios by their ancestor. A portfolio is not considered to be its own ancestor. + available_project_attributes: filters portfolios based on the activated project attributes. + created_at: based on the time the portfolio was created + favorited: based on the favorited property of the portfolio + id: based on portfolios&#39; id. + latest_activity_at: based on the time the last activity was registered on a portfolio. + name_and_identifier: based on both the name and the identifier. + parent_id: filters portfolios by their parent. + principal: based on members of the portfolio. + project_phase_any: based on the project phases active in a portfolio. + project_status_code: based on status code of the portfolio + storage_id: filters portfolios by linked storages + storage_url: filters portfolios by linked storages identified by the host url + type_id: based on the types active in a portfolio. + user_action: based on the actions the current user has in the portfolio. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the portfolios visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a portfolio. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a portfolio. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a portfolio. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return PortfolioCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listPortfoliosRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "select", select));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<PortfolioCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/portfolios", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List portfolios
     * Returns a collection of portfolios. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain portfolios the client is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openportfolio.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the portfolio + ancestor: filters portfolios by their ancestor. A portfolio is not considered to be its own ancestor. + available_project_attributes: filters portfolios based on the activated project attributes. + created_at: based on the time the portfolio was created + favorited: based on the favorited property of the portfolio + id: based on portfolios&#39; id. + latest_activity_at: based on the time the last activity was registered on a portfolio. + name_and_identifier: based on both the name and the identifier. + parent_id: filters portfolios by their parent. + principal: based on members of the portfolio. + project_phase_any: based on the project phases active in a portfolio. + project_status_code: based on status code of the portfolio + storage_id: filters portfolios by linked storages + storage_url: filters portfolios by linked storages identified by the host url + type_id: based on the types active in a portfolio. + user_action: based on the actions the current user has in the portfolio. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the portfolios visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a portfolio. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a portfolio. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a portfolio. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return PortfolioCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public PortfolioCollectionModel listPortfolios(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<PortfolioCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listPortfoliosRequestCreation(filters, sortBy, select).body(localVarReturnType);
    }

    /**
     * List portfolios
     * Returns a collection of portfolios. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain portfolios the client is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openportfolio.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the portfolio + ancestor: filters portfolios by their ancestor. A portfolio is not considered to be its own ancestor. + available_project_attributes: filters portfolios based on the activated project attributes. + created_at: based on the time the portfolio was created + favorited: based on the favorited property of the portfolio + id: based on portfolios&#39; id. + latest_activity_at: based on the time the last activity was registered on a portfolio. + name_and_identifier: based on both the name and the identifier. + parent_id: filters portfolios by their parent. + principal: based on members of the portfolio. + project_phase_any: based on the project phases active in a portfolio. + project_status_code: based on status code of the portfolio + storage_id: filters portfolios by linked storages + storage_url: filters portfolios by linked storages identified by the host url + type_id: based on the types active in a portfolio. + user_action: based on the actions the current user has in the portfolio. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the portfolios visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a portfolio. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a portfolio. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a portfolio. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ResponseEntity&lt;PortfolioCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PortfolioCollectionModel> listPortfoliosWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<PortfolioCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listPortfoliosRequestCreation(filters, sortBy, select).toEntity(localVarReturnType);
    }

    /**
     * List portfolios
     * Returns a collection of portfolios. The collection can be filtered via query parameters similar to how work packages are filtered. In addition to the provided filter, the result set is always limited to only contain portfolios the client is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openportfolio.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + active: based on the active property of the portfolio + ancestor: filters portfolios by their ancestor. A portfolio is not considered to be its own ancestor. + available_project_attributes: filters portfolios based on the activated project attributes. + created_at: based on the time the portfolio was created + favorited: based on the favorited property of the portfolio + id: based on portfolios&#39; id. + latest_activity_at: based on the time the last activity was registered on a portfolio. + name_and_identifier: based on both the name and the identifier. + parent_id: filters portfolios by their parent. + principal: based on members of the portfolio. + project_phase_any: based on the project phases active in a portfolio. + project_status_code: based on status code of the portfolio + storage_id: filters portfolios by linked storages + storage_url: filters portfolios by linked storages identified by the host url + type_id: based on the types active in a portfolio. + user_action: based on the actions the current user has in the portfolio. + visible: based on the visibility for the user (id) provided as the filter value. This filter is useful for admins to identify the portfolios visible to a user.  There might also be additional filters based on the custom fields that have been configured.  Each defined lifecycle step will also define a filter in this list endpoint. Given that the elements are not static but rather dynamically created on each OpenProject instance, a list cannot be provided. Those filters follow the schema: + project_start_gate_[id]: a filter on a project phase&#39;s start gate active in a portfolio. The id is the id of the phase the gate belongs to. + project_finish_gate_[id]: a filter on a project phase&#39;s finish gate active in a portfolio. The id is the id of the phase the gate belongs to. + project_phase_[id]: a filter on a project phase active in a portfolio. The id is the id of the phase queried for.
     * @param sortBy JSON specifying sort criteria. Currently supported orders are:  + id + name + typeahead (sorting by hierarchy and name) + created_at + public + latest_activity_at + required_disk_space  There might also be additional orders based on the custom fields that have been configured.
     * @param select Comma separated list of properties to include.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listPortfoliosWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return listPortfoliosRequestCreation(filters, sortBy, select);
    }

    /**
     * Update Portfolio
     * Updates the given portfolio by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the portfolio to be altered
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Portfolio id
     * @param portfolioModel The portfolioModel parameter
     * @return PortfolioModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updatePortfolioRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PortfolioModel portfolioModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updatePortfolio", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<PortfolioModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/portfolios/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, portfolioModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update Portfolio
     * Updates the given portfolio by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the portfolio to be altered
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Portfolio id
     * @param portfolioModel The portfolioModel parameter
     * @return PortfolioModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public PortfolioModel updatePortfolio(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PortfolioModel portfolioModel) throws RestClientResponseException {
        ParameterizedTypeReference<PortfolioModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updatePortfolioRequestCreation(id, portfolioModel).body(localVarReturnType);
    }

    /**
     * Update Portfolio
     * Updates the given portfolio by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the portfolio to be altered
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Portfolio id
     * @param portfolioModel The portfolioModel parameter
     * @return ResponseEntity&lt;PortfolioModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PortfolioModel> updatePortfolioWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PortfolioModel portfolioModel) throws RestClientResponseException {
        ParameterizedTypeReference<PortfolioModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updatePortfolioRequestCreation(id, portfolioModel).toEntity(localVarReturnType);
    }

    /**
     * Update Portfolio
     * Updates the given portfolio by applying the attributes provided in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit project for the portfolio to be altered
     * <p><b>404</b> - Returned if the portfolio does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Portfolio id
     * @param portfolioModel The portfolioModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updatePortfolioWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PortfolioModel portfolioModel) throws RestClientResponseException {
        return updatePortfolioRequestCreation(id, portfolioModel);
    }

    /**
     * Portfolio update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit workspace in the portfolio
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Portfolio id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validatePortfolioUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validatePortfolioUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/portfolios/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Portfolio update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit workspace in the portfolio
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Portfolio id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void validatePortfolioUpdateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        validatePortfolioUpdateFormRequestCreation(id, body).body(localVarReturnType);
    }

    /**
     * Portfolio update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit workspace in the portfolio
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Portfolio id
     * @param body The body parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> validatePortfolioUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validatePortfolioUpdateFormRequestCreation(id, body).toEntity(localVarReturnType);
    }

    /**
     * Portfolio update form
     * 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit workspace in the portfolio
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Portfolio id
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validatePortfolioUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return validatePortfolioUpdateFormRequestCreation(id, body);
    }
}
