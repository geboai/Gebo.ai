package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.CustomFieldModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.HierarchyItemCollectionModel;
import ai.gebo.openproject.client.model.HierarchyItemReadModel;

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
public class CustomFieldsApi {
    private ApiClient apiClient;

    public CustomFieldsApi() {
        this(new ApiClient());
    }

    public CustomFieldsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get custom field
     * Retrieves the custom field with the given ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks the permission to view it.
     * @param id The custom field&#39;s unique identifier
     * @return CustomFieldModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCustomFieldRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getCustomField", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CustomFieldModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/custom_fields/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get custom field
     * Retrieves the custom field with the given ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks the permission to view it.
     * @param id The custom field&#39;s unique identifier
     * @return CustomFieldModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public CustomFieldModel getCustomField(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CustomFieldModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomFieldRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get custom field
     * Retrieves the custom field with the given ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks the permission to view it.
     * @param id The custom field&#39;s unique identifier
     * @return ResponseEntity&lt;CustomFieldModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CustomFieldModel> getCustomFieldWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<CustomFieldModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomFieldRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get custom field
     * Retrieves the custom field with the given ID.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks the permission to view it.
     * @param id The custom field&#39;s unique identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCustomFieldWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getCustomFieldRequestCreation(id);
    }

    /**
     * Get a custom field hierarchy item
     * Retrieves a single custom field item specified by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field item does not exist or the user lacks permission to see it.  The permission required to view the item depends on the custom field it belongs to.
     * @param id The custom field item&#39;s unique identifier
     * @return HierarchyItemReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCustomFieldItemRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getCustomFieldItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<HierarchyItemReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/custom_field_items/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a custom field hierarchy item
     * Retrieves a single custom field item specified by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field item does not exist or the user lacks permission to see it.  The permission required to view the item depends on the custom field it belongs to.
     * @param id The custom field item&#39;s unique identifier
     * @return HierarchyItemReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HierarchyItemReadModel getCustomFieldItem(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<HierarchyItemReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomFieldItemRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a custom field hierarchy item
     * Retrieves a single custom field item specified by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field item does not exist or the user lacks permission to see it.  The permission required to view the item depends on the custom field it belongs to.
     * @param id The custom field item&#39;s unique identifier
     * @return ResponseEntity&lt;HierarchyItemReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HierarchyItemReadModel> getCustomFieldItemWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<HierarchyItemReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomFieldItemRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a custom field hierarchy item
     * Retrieves a single custom field item specified by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field item does not exist or the user lacks permission to see it.  The permission required to view the item depends on the custom field it belongs to.
     * @param id The custom field item&#39;s unique identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCustomFieldItemWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getCustomFieldItemRequestCreation(id);
    }

    /**
     * Get a custom field hierarchy item&#39;s branch
     * Retrieves the branch of a single custom field item specified by its unique identifier.  A branch is list of all ancestors, starting with the root item and finishing with the item itself.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks permission to view it.
     * <p><b>422</b> - Returned if the custom field is not of type hierarchy.
     * @param id The custom field item&#39;s unique identifier
     * @return HierarchyItemCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCustomFieldItemBranchRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getCustomFieldItemBranch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<HierarchyItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/custom_field_items/{id}/branch", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a custom field hierarchy item&#39;s branch
     * Retrieves the branch of a single custom field item specified by its unique identifier.  A branch is list of all ancestors, starting with the root item and finishing with the item itself.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks permission to view it.
     * <p><b>422</b> - Returned if the custom field is not of type hierarchy.
     * @param id The custom field item&#39;s unique identifier
     * @return HierarchyItemCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HierarchyItemCollectionModel getCustomFieldItemBranch(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<HierarchyItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomFieldItemBranchRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a custom field hierarchy item&#39;s branch
     * Retrieves the branch of a single custom field item specified by its unique identifier.  A branch is list of all ancestors, starting with the root item and finishing with the item itself.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks permission to view it.
     * <p><b>422</b> - Returned if the custom field is not of type hierarchy.
     * @param id The custom field item&#39;s unique identifier
     * @return ResponseEntity&lt;HierarchyItemCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HierarchyItemCollectionModel> getCustomFieldItemBranchWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<HierarchyItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomFieldItemBranchRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a custom field hierarchy item&#39;s branch
     * Retrieves the branch of a single custom field item specified by its unique identifier.  A branch is list of all ancestors, starting with the root item and finishing with the item itself.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks permission to view it.
     * <p><b>422</b> - Returned if the custom field is not of type hierarchy.
     * @param id The custom field item&#39;s unique identifier
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCustomFieldItemBranchWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getCustomFieldItemBranchRequestCreation(id);
    }

    /**
     * Get the custom field hierarchy items
     * Retrieves the hierarchy of custom fields.  The hierarchy is a tree structure of hierarchy items. It is represented as a flat list of items, where each item has a reference to its parent and children. The list is ordered in a depth-first manner. The first item is the requested parent. If parent was unset, the root item is returned as first element.  Passing the &#x60;depth&#x60; query parameter allows to limit the depth of the hierarchy. If the depth is unset, the full hierarchy tree is returned. If the depth is set to &#x60;0&#x60;, only the requested parent is returned. Any other positive integer will return the number of children levels specified by this value.  This endpoint only returns, if the custom field is of type &#x60;hierarchy&#x60;.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks the permission to view it.
     * <p><b>422</b> - Returned if the custom field is not of type hierarchy.
     * @param id The custom field&#39;s unique identifier
     * @param parent The identifier of the parent hierarchy item
     * @param depth The level of hierarchy depth
     * @return HierarchyItemCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCustomFieldItemsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer parent, @jakarta.annotation.Nullable Integer depth) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getCustomFieldItems", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "parent", parent));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "depth", depth));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<HierarchyItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/custom_fields/{id}/items", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the custom field hierarchy items
     * Retrieves the hierarchy of custom fields.  The hierarchy is a tree structure of hierarchy items. It is represented as a flat list of items, where each item has a reference to its parent and children. The list is ordered in a depth-first manner. The first item is the requested parent. If parent was unset, the root item is returned as first element.  Passing the &#x60;depth&#x60; query parameter allows to limit the depth of the hierarchy. If the depth is unset, the full hierarchy tree is returned. If the depth is set to &#x60;0&#x60;, only the requested parent is returned. Any other positive integer will return the number of children levels specified by this value.  This endpoint only returns, if the custom field is of type &#x60;hierarchy&#x60;.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks the permission to view it.
     * <p><b>422</b> - Returned if the custom field is not of type hierarchy.
     * @param id The custom field&#39;s unique identifier
     * @param parent The identifier of the parent hierarchy item
     * @param depth The level of hierarchy depth
     * @return HierarchyItemCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HierarchyItemCollectionModel getCustomFieldItems(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer parent, @jakarta.annotation.Nullable Integer depth) throws RestClientResponseException {
        ParameterizedTypeReference<HierarchyItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomFieldItemsRequestCreation(id, parent, depth).body(localVarReturnType);
    }

    /**
     * Get the custom field hierarchy items
     * Retrieves the hierarchy of custom fields.  The hierarchy is a tree structure of hierarchy items. It is represented as a flat list of items, where each item has a reference to its parent and children. The list is ordered in a depth-first manner. The first item is the requested parent. If parent was unset, the root item is returned as first element.  Passing the &#x60;depth&#x60; query parameter allows to limit the depth of the hierarchy. If the depth is unset, the full hierarchy tree is returned. If the depth is set to &#x60;0&#x60;, only the requested parent is returned. Any other positive integer will return the number of children levels specified by this value.  This endpoint only returns, if the custom field is of type &#x60;hierarchy&#x60;.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks the permission to view it.
     * <p><b>422</b> - Returned if the custom field is not of type hierarchy.
     * @param id The custom field&#39;s unique identifier
     * @param parent The identifier of the parent hierarchy item
     * @param depth The level of hierarchy depth
     * @return ResponseEntity&lt;HierarchyItemCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HierarchyItemCollectionModel> getCustomFieldItemsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer parent, @jakarta.annotation.Nullable Integer depth) throws RestClientResponseException {
        ParameterizedTypeReference<HierarchyItemCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getCustomFieldItemsRequestCreation(id, parent, depth).toEntity(localVarReturnType);
    }

    /**
     * Get the custom field hierarchy items
     * Retrieves the hierarchy of custom fields.  The hierarchy is a tree structure of hierarchy items. It is represented as a flat list of items, where each item has a reference to its parent and children. The list is ordered in a depth-first manner. The first item is the requested parent. If parent was unset, the root item is returned as first element.  Passing the &#x60;depth&#x60; query parameter allows to limit the depth of the hierarchy. If the depth is unset, the full hierarchy tree is returned. If the depth is set to &#x60;0&#x60;, only the requested parent is returned. Any other positive integer will return the number of children levels specified by this value.  This endpoint only returns, if the custom field is of type &#x60;hierarchy&#x60;.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the user is not logged in.
     * <p><b>404</b> - Returned if the custom field does not exist or the user lacks the permission to view it.
     * <p><b>422</b> - Returned if the custom field is not of type hierarchy.
     * @param id The custom field&#39;s unique identifier
     * @param parent The identifier of the parent hierarchy item
     * @param depth The level of hierarchy depth
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCustomFieldItemsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer parent, @jakarta.annotation.Nullable Integer depth) throws RestClientResponseException {
        return getCustomFieldItemsRequestCreation(id, parent, depth);
    }
}
