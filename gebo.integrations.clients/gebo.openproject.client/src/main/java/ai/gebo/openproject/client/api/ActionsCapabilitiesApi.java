package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

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
public class ActionsCapabilitiesApi {
    private ApiClient apiClient;

    public ActionsCapabilitiesApi() {
        this(new ApiClient());
    }

    public ActionsCapabilitiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View action
     * Returns an individual action.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the action does not exist.
     * @param id action id which is the name of the action
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getActionRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getAction", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/actions/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View action
     * Returns an individual action.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the action does not exist.
     * @param id action id which is the name of the action
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getAction(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getActionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View action
     * Returns an individual action.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the action does not exist.
     * @param id action id which is the name of the action
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getActionWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getActionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View action
     * Returns an individual action.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the action does not exist.
     * @param id action id which is the name of the action
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getActionWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getActionRequestCreation(id);
    }

    /**
     * View capabilities
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the capability does not exist.
     * @param id capability id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCapabilitiesRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getCapabilities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/capabilities/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View capabilities
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the capability does not exist.
     * @param id capability id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getCapabilities(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCapabilitiesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View capabilities
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the capability does not exist.
     * @param id capability id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getCapabilitiesWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCapabilitiesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View capabilities
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the capability does not exist.
     * @param id capability id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCapabilitiesWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getCapabilitiesRequestCreation(id);
    }

    /**
     * View global context
     * Returns the global capability context. This context is necessary to consistently link to a context even if the context is not a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the action does not exist.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getGlobalContextRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/capabilities/contexts/global", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View global context
     * Returns the global capability context. This context is necessary to consistently link to a context even if the context is not a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the action does not exist.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getGlobalContext() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGlobalContextRequestCreation().body(localVarReturnType);
    }

    /**
     * View global context
     * Returns the global capability context. This context is necessary to consistently link to a context even if the context is not a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the action does not exist.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getGlobalContextWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getGlobalContextRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * View global context
     * Returns the global capability context. This context is necessary to consistently link to a context even if the context is not a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the action does not exist.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getGlobalContextWithResponseSpec() throws RestClientResponseException {
        return getGlobalContextRequestCreation();
    }

    /**
     * List actions
     * Returns a collection of actions. The client can choose to filter the actions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain actions, for which the requesting client has sufficient permissions.
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Returns only the action having the id or all actions except those having the id(s).
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listActionsRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v3/actions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List actions
     * Returns a collection of actions. The client can choose to filter the actions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain actions, for which the requesting client has sufficient permissions.
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Returns only the action having the id or all actions except those having the id(s).
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listActions(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listActionsRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * List actions
     * Returns a collection of actions. The client can choose to filter the actions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain actions, for which the requesting client has sufficient permissions.
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Returns only the action having the id or all actions except those having the id(s).
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listActionsWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listActionsRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * List actions
     * Returns a collection of actions. The client can choose to filter the actions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain actions, for which the requesting client has sufficient permissions.
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: Returns only the action having the id or all actions except those having the id(s).
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listActionsWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listActionsRequestCreation(filters);
    }

    /**
     * List capabilities
     * Returns a collection of actions assigned to a principal in a context. The client can choose to filter the actions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain actions, for which the requesting client has sufficient permissions
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.  + action: Get all capabilities of a certain action  + principal: Get all capabilities of a principal  + context: Get all capabilities within a context. Note that for a workspace context the client needs to   provide &#x60;w{id}&#x60;, e.g. &#x60;w5&#x60; and for the global context a &#x60;g&#x60;.    + **Deprecation**: The now deprecated context &#x60;p&#x60; for project still works, but must eventually be replaced     with the &#x60;w&#x60; for the workspace context.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by the capabilities id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listCapabilitiesRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/capabilities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List capabilities
     * Returns a collection of actions assigned to a principal in a context. The client can choose to filter the actions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain actions, for which the requesting client has sufficient permissions
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.  + action: Get all capabilities of a certain action  + principal: Get all capabilities of a principal  + context: Get all capabilities within a context. Note that for a workspace context the client needs to   provide &#x60;w{id}&#x60;, e.g. &#x60;w5&#x60; and for the global context a &#x60;g&#x60;.    + **Deprecation**: The now deprecated context &#x60;p&#x60; for project still works, but must eventually be replaced     with the &#x60;w&#x60; for the workspace context.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by the capabilities id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listCapabilities(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listCapabilitiesRequestCreation(filters, sortBy).body(localVarReturnType);
    }

    /**
     * List capabilities
     * Returns a collection of actions assigned to a principal in a context. The client can choose to filter the actions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain actions, for which the requesting client has sufficient permissions
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.  + action: Get all capabilities of a certain action  + principal: Get all capabilities of a principal  + context: Get all capabilities within a context. Note that for a workspace context the client needs to   provide &#x60;w{id}&#x60;, e.g. &#x60;w5&#x60; and for the global context a &#x60;g&#x60;.    + **Deprecation**: The now deprecated context &#x60;p&#x60; for project still works, but must eventually be replaced     with the &#x60;w&#x60; for the workspace context.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by the capabilities id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listCapabilitiesWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listCapabilitiesRequestCreation(filters, sortBy).toEntity(localVarReturnType);
    }

    /**
     * List capabilities
     * Returns a collection of actions assigned to a principal in a context. The client can choose to filter the actions similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain actions, for which the requesting client has sufficient permissions
     * <p><b>200</b> - OK
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.  + action: Get all capabilities of a certain action  + principal: Get all capabilities of a principal  + context: Get all capabilities within a context. Note that for a workspace context the client needs to   provide &#x60;w{id}&#x60;, e.g. &#x60;w5&#x60; and for the global context a &#x60;g&#x60;.    + **Deprecation**: The now deprecated context &#x60;p&#x60; for project still works, but must eventually be replaced     with the &#x60;w&#x60; for the workspace context.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by the capabilities id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listCapabilitiesWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        return listCapabilitiesRequestCreation(filters, sortBy);
    }
}
