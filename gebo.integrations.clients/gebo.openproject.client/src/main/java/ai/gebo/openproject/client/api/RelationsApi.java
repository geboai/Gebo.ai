package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.RelationCollectionModel;
import ai.gebo.openproject.client.model.RelationReadModel;
import ai.gebo.openproject.client.model.RelationWriteModel;

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
public class RelationsApi {
    private ApiClient apiClient;

    public RelationsApi() {
        this(new ApiClient());
    }

    public RelationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create relation
     * Create a work package relation on the given work package. A successful creation will result in a relation between two work packages, thus appearing on both involved work package resources.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage work package relations
     * <p><b>409</b> - Returned if there already exists a relation between the given work packages of **any** type or if the relation is not allowed.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to write a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Work package id
     * @param relationWriteModel The relationWriteModel parameter
     * @return RelationReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createRelationRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RelationWriteModel relationWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createRelation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/relations", HttpMethod.POST, pathParams, localVarQueryParams, relationWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create relation
     * Create a work package relation on the given work package. A successful creation will result in a relation between two work packages, thus appearing on both involved work package resources.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage work package relations
     * <p><b>409</b> - Returned if there already exists a relation between the given work packages of **any** type or if the relation is not allowed.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to write a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Work package id
     * @param relationWriteModel The relationWriteModel parameter
     * @return RelationReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RelationReadModel createRelation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RelationWriteModel relationWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createRelationRequestCreation(id, relationWriteModel).body(localVarReturnType);
    }

    /**
     * Create relation
     * Create a work package relation on the given work package. A successful creation will result in a relation between two work packages, thus appearing on both involved work package resources.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage work package relations
     * <p><b>409</b> - Returned if there already exists a relation between the given work packages of **any** type or if the relation is not allowed.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to write a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Work package id
     * @param relationWriteModel The relationWriteModel parameter
     * @return ResponseEntity&lt;RelationReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RelationReadModel> createRelationWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RelationWriteModel relationWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createRelationRequestCreation(id, relationWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create relation
     * Create a work package relation on the given work package. A successful creation will result in a relation between two work packages, thus appearing on both involved work package resources.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage work package relations
     * <p><b>409</b> - Returned if there already exists a relation between the given work packages of **any** type or if the relation is not allowed.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to write a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Work package id
     * @param relationWriteModel The relationWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createRelationWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RelationWriteModel relationWriteModel) throws RestClientResponseException {
        return createRelationRequestCreation(id, relationWriteModel);
    }

    /**
     * Delete Relation
     * Deletes the relation.
     * <p><b>204</b> - Returned if the relation was deleted successfully. The response body is empty.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage work package relations
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id The unique identifier of the relation resource
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteRelationRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteRelation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/relations/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete Relation
     * Deletes the relation.
     * <p><b>204</b> - Returned if the relation was deleted successfully. The response body is empty.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage work package relations
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id The unique identifier of the relation resource
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteRelation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteRelationRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete Relation
     * Deletes the relation.
     * <p><b>204</b> - Returned if the relation was deleted successfully. The response body is empty.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage work package relations
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id The unique identifier of the relation resource
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteRelationWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteRelationRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete Relation
     * Deletes the relation.
     * <p><b>204</b> - Returned if the relation was deleted successfully. The response body is empty.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage work package relations
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id The unique identifier of the relation resource
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteRelationWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteRelationRequestCreation(id);
    }

    /**
     * Get Relation
     * Get a single relation specified by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages for the involved work packages
     * @param id Relation id
     * @return RelationReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRelationRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRelation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/relations/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get Relation
     * Get a single relation specified by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages for the involved work packages
     * @param id Relation id
     * @return RelationReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RelationReadModel getRelation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRelationRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get Relation
     * Get a single relation specified by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages for the involved work packages
     * @param id Relation id
     * @return ResponseEntity&lt;RelationReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RelationReadModel> getRelationWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRelationRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get Relation
     * Get a single relation specified by its unique identifier.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages for the involved work packages
     * @param id Relation id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRelationWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getRelationRequestCreation(id);
    }

    /**
     * List Relations
     * Lists all relations according to the given (optional, logically conjunctive) filters and ordered by ID. The response only includes relations between work packages which the user is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client provides invalid filter parameters.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Valid fields to filter by are:  - id - ID of relation - from - ID of work package from which the filtered relations emanates. - to - ID of work package to which this related points. - involved - ID of either the &#x60;from&#x60; or the &#x60;to&#x60; work package. - type - The type of relation to filter by, e.g. \&quot;follows\&quot;.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @return RelationCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listRelationsRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
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

        ParameterizedTypeReference<RelationCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/relations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List Relations
     * Lists all relations according to the given (optional, logically conjunctive) filters and ordered by ID. The response only includes relations between work packages which the user is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client provides invalid filter parameters.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Valid fields to filter by are:  - id - ID of relation - from - ID of work package from which the filtered relations emanates. - to - ID of work package to which this related points. - involved - ID of either the &#x60;from&#x60; or the &#x60;to&#x60; work package. - type - The type of relation to filter by, e.g. \&quot;follows\&quot;.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @return RelationCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RelationCollectionModel listRelations(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<RelationCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRelationsRequestCreation(filters, sortBy).body(localVarReturnType);
    }

    /**
     * List Relations
     * Lists all relations according to the given (optional, logically conjunctive) filters and ordered by ID. The response only includes relations between work packages which the user is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client provides invalid filter parameters.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Valid fields to filter by are:  - id - ID of relation - from - ID of work package from which the filtered relations emanates. - to - ID of work package to which this related points. - involved - ID of either the &#x60;from&#x60; or the &#x60;to&#x60; work package. - type - The type of relation to filter by, e.g. \&quot;follows\&quot;.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @return ResponseEntity&lt;RelationCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RelationCollectionModel> listRelationsWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<RelationCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listRelationsRequestCreation(filters, sortBy).toEntity(localVarReturnType);
    }

    /**
     * List Relations
     * Lists all relations according to the given (optional, logically conjunctive) filters and ordered by ID. The response only includes relations between work packages which the user is allowed to see.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client provides invalid filter parameters.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Valid fields to filter by are:  - id - ID of relation - from - ID of work package from which the filtered relations emanates. - to - ID of work package to which this related points. - involved - ID of either the &#x60;from&#x60; or the &#x60;to&#x60; work package. - type - The type of relation to filter by, e.g. \&quot;follows\&quot;.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listRelationsWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        return listRelationsRequestCreation(filters, sortBy);
    }

    /**
     * Update Relation
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. It is only allowed to provide properties or links supporting the **write** operation.  Note that changing the &#x60;type&#x60; of a relation invariably also changes the respective &#x60;reverseType&#x60; as well as the \&quot;name\&quot; of it. The returned Relation object will reflect that change. For instance if you change a Relation&#39;s &#x60;type&#x60; to \&quot;follows\&quot; then the &#x60;reverseType&#x60; will be changed to &#x60;precedes&#x60;.  It is not allowed to change a relation&#39;s involved work packages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;) or a   work package that does not exist or for which the client does not have sufficient permissions   to see it (**required permissions**: &#x60;view work packages&#x60; for the involved work packages).
     * @param id Relation ID
     * @param relationWriteModel The relationWriteModel parameter
     * @return RelationReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateRelationRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RelationWriteModel relationWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateRelation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/relations/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, relationWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update Relation
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. It is only allowed to provide properties or links supporting the **write** operation.  Note that changing the &#x60;type&#x60; of a relation invariably also changes the respective &#x60;reverseType&#x60; as well as the \&quot;name\&quot; of it. The returned Relation object will reflect that change. For instance if you change a Relation&#39;s &#x60;type&#x60; to \&quot;follows\&quot; then the &#x60;reverseType&#x60; will be changed to &#x60;precedes&#x60;.  It is not allowed to change a relation&#39;s involved work packages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;) or a   work package that does not exist or for which the client does not have sufficient permissions   to see it (**required permissions**: &#x60;view work packages&#x60; for the involved work packages).
     * @param id Relation ID
     * @param relationWriteModel The relationWriteModel parameter
     * @return RelationReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RelationReadModel updateRelation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RelationWriteModel relationWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateRelationRequestCreation(id, relationWriteModel).body(localVarReturnType);
    }

    /**
     * Update Relation
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. It is only allowed to provide properties or links supporting the **write** operation.  Note that changing the &#x60;type&#x60; of a relation invariably also changes the respective &#x60;reverseType&#x60; as well as the \&quot;name\&quot; of it. The returned Relation object will reflect that change. For instance if you change a Relation&#39;s &#x60;type&#x60; to \&quot;follows\&quot; then the &#x60;reverseType&#x60; will be changed to &#x60;precedes&#x60;.  It is not allowed to change a relation&#39;s involved work packages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;) or a   work package that does not exist or for which the client does not have sufficient permissions   to see it (**required permissions**: &#x60;view work packages&#x60; for the involved work packages).
     * @param id Relation ID
     * @param relationWriteModel The relationWriteModel parameter
     * @return ResponseEntity&lt;RelationReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RelationReadModel> updateRelationWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RelationWriteModel relationWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<RelationReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateRelationRequestCreation(id, relationWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update Relation
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. It is only allowed to provide properties or links supporting the **write** operation.  Note that changing the &#x60;type&#x60; of a relation invariably also changes the respective &#x60;reverseType&#x60; as well as the \&quot;name\&quot; of it. The returned Relation object will reflect that change. For instance if you change a Relation&#39;s &#x60;type&#x60; to \&quot;follows\&quot; then the &#x60;reverseType&#x60; will be changed to &#x60;precedes&#x60;.  It is not allowed to change a relation&#39;s involved work packages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the relation does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;) or a   work package that does not exist or for which the client does not have sufficient permissions   to see it (**required permissions**: &#x60;view work packages&#x60; for the involved work packages).
     * @param id Relation ID
     * @param relationWriteModel The relationWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateRelationWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable RelationWriteModel relationWriteModel) throws RestClientResponseException {
        return updateRelationRequestCreation(id, relationWriteModel);
    }
}
