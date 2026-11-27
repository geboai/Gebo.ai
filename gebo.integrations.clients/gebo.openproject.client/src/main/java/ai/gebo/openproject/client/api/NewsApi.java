package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.NewsCreateModel;
import ai.gebo.openproject.client.model.NewsModel;

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
public class NewsApi {
    private ApiClient apiClient;

    public NewsApi() {
        this(new ApiClient());
    }

    public NewsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create News
     * Creates a news entry. Only administrators and users with \&quot;Manage news\&quot; permission in the given project are eligible. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator, Manage news permission in the project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param newsCreateModel The newsCreateModel parameter
     * @return NewsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createNewsRequestCreation(@jakarta.annotation.Nullable NewsCreateModel newsCreateModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/news", HttpMethod.POST, pathParams, localVarQueryParams, newsCreateModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create News
     * Creates a news entry. Only administrators and users with \&quot;Manage news\&quot; permission in the given project are eligible. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator, Manage news permission in the project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param newsCreateModel The newsCreateModel parameter
     * @return NewsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public NewsModel createNews(@jakarta.annotation.Nullable NewsCreateModel newsCreateModel) throws RestClientResponseException {
        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createNewsRequestCreation(newsCreateModel).body(localVarReturnType);
    }

    /**
     * Create News
     * Creates a news entry. Only administrators and users with \&quot;Manage news\&quot; permission in the given project are eligible. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator, Manage news permission in the project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param newsCreateModel The newsCreateModel parameter
     * @return ResponseEntity&lt;NewsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<NewsModel> createNewsWithHttpInfo(@jakarta.annotation.Nullable NewsCreateModel newsCreateModel) throws RestClientResponseException {
        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createNewsRequestCreation(newsCreateModel).toEntity(localVarReturnType);
    }

    /**
     * Create News
     * Creates a news entry. Only administrators and users with \&quot;Manage news\&quot; permission in the given project are eligible. When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrator, Manage news permission in the project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param newsCreateModel The newsCreateModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createNewsWithResponseSpec(@jakarta.annotation.Nullable NewsCreateModel newsCreateModel) throws RestClientResponseException {
        return createNewsRequestCreation(newsCreateModel);
    }

    /**
     * Delete news
     * Permanently deletes the specified news entry.
     * <p><b>202</b> - Returned if the news was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators and Manage news permission
     * <p><b>404</b> - Returned if the news does not exist.
     * @param id News id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteNewsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteNews", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/news/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete news
     * Permanently deletes the specified news entry.
     * <p><b>202</b> - Returned if the news was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators and Manage news permission
     * <p><b>404</b> - Returned if the news does not exist.
     * @param id News id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteNews(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteNewsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete news
     * Permanently deletes the specified news entry.
     * <p><b>202</b> - Returned if the news was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators and Manage news permission
     * <p><b>404</b> - Returned if the news does not exist.
     * @param id News id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteNewsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteNewsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete news
     * Permanently deletes the specified news entry.
     * <p><b>202</b> - Returned if the news was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned, indicating the progress of deletion.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators and Manage news permission
     * <p><b>404</b> - Returned if the news does not exist.
     * @param id News id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteNewsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteNewsRequestCreation(id);
    }

    /**
     * View news
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the news does not exist or if the user does not have permission to view it.  **Required permission** being member of the project the news belongs to
     * @param id news id
     * @return NewsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getNewsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getNews", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/news/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View news
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the news does not exist or if the user does not have permission to view it.  **Required permission** being member of the project the news belongs to
     * @param id news id
     * @return NewsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public NewsModel getNews(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNewsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View news
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the news does not exist or if the user does not have permission to view it.  **Required permission** being member of the project the news belongs to
     * @param id news id
     * @return ResponseEntity&lt;NewsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<NewsModel> getNewsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNewsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View news
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the news does not exist or if the user does not have permission to view it.  **Required permission** being member of the project the news belongs to
     * @param id news id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getNewsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getNewsRequestCreation(id);
    }

    /**
     * List News
     * Lists news. The news returned depend on the provided parameters and also on the requesting user&#39;s permissions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by news creation datetime
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + project_id: Filter news by project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listNewsRequestCreation(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/news", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List News
     * Lists news. The news returned depend on the provided parameters and also on the requesting user&#39;s permissions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by news creation datetime
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + project_id: Filter news by project
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listNews(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listNewsRequestCreation(offset, pageSize, sortBy, filters).body(localVarReturnType);
    }

    /**
     * List News
     * Lists news. The news returned depend on the provided parameters and also on the requesting user&#39;s permissions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by news creation datetime
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + project_id: Filter news by project
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listNewsWithHttpInfo(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listNewsRequestCreation(offset, pageSize, sortBy, filters).toEntity(localVarReturnType);
    }

    /**
     * List News
     * Lists news. The news returned depend on the provided parameters and also on the requesting user&#39;s permissions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by news creation datetime
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + project_id: Filter news by project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listNewsWithResponseSpec(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listNewsRequestCreation(offset, pageSize, sortBy, filters);
    }

    /**
     * Update news
     * Updates the news&#39;s writable attributes. When calling this endpoint the client provides a single object, containing the properties and links to be updated, in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators, Manage news permission
     * <p><b>404</b> - Returned if the news entry does not exist or if the API user does not have the necessary permissions to update it.  **Required permission:** Administrators, Manage news permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id News id
     * @param newsCreateModel The newsCreateModel parameter
     * @return NewsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateNewsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable NewsCreateModel newsCreateModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateNews", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/news/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, newsCreateModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update news
     * Updates the news&#39;s writable attributes. When calling this endpoint the client provides a single object, containing the properties and links to be updated, in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators, Manage news permission
     * <p><b>404</b> - Returned if the news entry does not exist or if the API user does not have the necessary permissions to update it.  **Required permission:** Administrators, Manage news permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id News id
     * @param newsCreateModel The newsCreateModel parameter
     * @return NewsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public NewsModel updateNews(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable NewsCreateModel newsCreateModel) throws RestClientResponseException {
        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateNewsRequestCreation(id, newsCreateModel).body(localVarReturnType);
    }

    /**
     * Update news
     * Updates the news&#39;s writable attributes. When calling this endpoint the client provides a single object, containing the properties and links to be updated, in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators, Manage news permission
     * <p><b>404</b> - Returned if the news entry does not exist or if the API user does not have the necessary permissions to update it.  **Required permission:** Administrators, Manage news permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id News id
     * @param newsCreateModel The newsCreateModel parameter
     * @return ResponseEntity&lt;NewsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<NewsModel> updateNewsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable NewsCreateModel newsCreateModel) throws RestClientResponseException {
        ParameterizedTypeReference<NewsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateNewsRequestCreation(id, newsCreateModel).toEntity(localVarReturnType);
    }

    /**
     * Update news
     * Updates the news&#39;s writable attributes. When calling this endpoint the client provides a single object, containing the properties and links to be updated, in the body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Administrators, Manage news permission
     * <p><b>404</b> - Returned if the news entry does not exist or if the API user does not have the necessary permissions to update it.  **Required permission:** Administrators, Manage news permission
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;)  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id News id
     * @param newsCreateModel The newsCreateModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateNewsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable NewsCreateModel newsCreateModel) throws RestClientResponseException {
        return updateNewsRequestCreation(id, newsCreateModel);
    }
}
