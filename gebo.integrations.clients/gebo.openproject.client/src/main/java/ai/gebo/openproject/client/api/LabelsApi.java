package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.LabelCollectionModel;
import ai.gebo.openproject.client.model.LabelModel;
import ai.gebo.openproject.client.model.LabelWriteModel;

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
public class LabelsApi {
    private ApiClient apiClient;

    public LabelsApi() {
        this(new ApiClient());
    }

    public LabelsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create Label
     * Creates a label with the given name, or returns the existing label whose name matches case-insensitively. Leading, trailing and repeated whitespace in the name is collapsed before matching.
     * <p><b>200</b> - OK. A label with that name already exists and is returned unchanged.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages (on any project or work package)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param labelWriteModel The labelWriteModel parameter
     * @return LabelModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createLabelRequestCreation(@jakarta.annotation.Nullable LabelWriteModel labelWriteModel) throws RestClientResponseException {
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

        ParameterizedTypeReference<LabelModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/labels", HttpMethod.POST, pathParams, localVarQueryParams, labelWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create Label
     * Creates a label with the given name, or returns the existing label whose name matches case-insensitively. Leading, trailing and repeated whitespace in the name is collapsed before matching.
     * <p><b>200</b> - OK. A label with that name already exists and is returned unchanged.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages (on any project or work package)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param labelWriteModel The labelWriteModel parameter
     * @return LabelModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public LabelModel createLabel(@jakarta.annotation.Nullable LabelWriteModel labelWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<LabelModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createLabelRequestCreation(labelWriteModel).body(localVarReturnType);
    }

    /**
     * Create Label
     * Creates a label with the given name, or returns the existing label whose name matches case-insensitively. Leading, trailing and repeated whitespace in the name is collapsed before matching.
     * <p><b>200</b> - OK. A label with that name already exists and is returned unchanged.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages (on any project or work package)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param labelWriteModel The labelWriteModel parameter
     * @return ResponseEntity&lt;LabelModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<LabelModel> createLabelWithHttpInfo(@jakarta.annotation.Nullable LabelWriteModel labelWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<LabelModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createLabelRequestCreation(labelWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create Label
     * Creates a label with the given name, or returns the existing label whose name matches case-insensitively. Leading, trailing and repeated whitespace in the name is collapsed before matching.
     * <p><b>200</b> - OK. A label with that name already exists and is returned unchanged.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages (on any project or work package)
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param labelWriteModel The labelWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createLabelWithResponseSpec(@jakarta.annotation.Nullable LabelWriteModel labelWriteModel) throws RestClientResponseException {
        return createLabelRequestCreation(labelWriteModel);
    }

    /**
     * View Label
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * <p><b>404</b> - Returned if the label does not exist.
     * @param id Label id
     * @return LabelModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getLabelRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getLabel", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<LabelModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/labels/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Label
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * <p><b>404</b> - Returned if the label does not exist.
     * @param id Label id
     * @return LabelModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public LabelModel getLabel(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<LabelModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getLabelRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View Label
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * <p><b>404</b> - Returned if the label does not exist.
     * @param id Label id
     * @return ResponseEntity&lt;LabelModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<LabelModel> getLabelWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<LabelModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getLabelRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View Label
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * <p><b>404</b> - Returned if the label does not exist.
     * @param id Label id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getLabelWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getLabelRequestCreation(id);
    }

    /**
     * List all Labels
     * Returns a paginated collection of labels, sorted by name.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + name: filters labels by name, with the operators &#x60;~&#x60; (contains), &#x60;!~&#x60; (does not contain) and &#x60;**&#x60; (all)
     * @return LabelCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAllLabelsRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
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

        ParameterizedTypeReference<LabelCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/labels", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Labels
     * Returns a paginated collection of labels, sorted by name.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + name: filters labels by name, with the operators &#x60;~&#x60; (contains), &#x60;!~&#x60; (does not contain) and &#x60;**&#x60; (all)
     * @return LabelCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public LabelCollectionModel listAllLabels(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<LabelCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAllLabelsRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * List all Labels
     * Returns a paginated collection of labels, sorted by name.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + name: filters labels by name, with the operators &#x60;~&#x60; (contains), &#x60;!~&#x60; (does not contain) and &#x60;**&#x60; (all)
     * @return ResponseEntity&lt;LabelCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<LabelCollectionModel> listAllLabelsWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<LabelCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAllLabelsRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * List all Labels
     * Returns a paginated collection of labels, sorted by name.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package (on any project)
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + name: filters labels by name, with the operators &#x60;~&#x60; (contains), &#x60;!~&#x60; (does not contain) and &#x60;**&#x60; (all)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAllLabelsWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listAllLabelsRequestCreation(filters);
    }

    /**
     * List labels by workspace
     * Returns a paginated collection of all labels, ordered by relevance for the given workspace: labels already used on work packages of the workspace come first, followed by the remaining labels. Within each group, labels are ordered by how often they are used overall and then by name.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages (on given workspace or any of its work packages)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose labels will be listed
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + name: filters labels by name, with the operators &#x60;~&#x60; (contains), &#x60;!~&#x60; (does not contain) and &#x60;**&#x60; (all)
     * @return LabelCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listLabelsByWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listLabelsByWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<LabelCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/labels", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List labels by workspace
     * Returns a paginated collection of all labels, ordered by relevance for the given workspace: labels already used on work packages of the workspace come first, followed by the remaining labels. Within each group, labels are ordered by how often they are used overall and then by name.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages (on given workspace or any of its work packages)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose labels will be listed
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + name: filters labels by name, with the operators &#x60;~&#x60; (contains), &#x60;!~&#x60; (does not contain) and &#x60;**&#x60; (all)
     * @return LabelCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public LabelCollectionModel listLabelsByWorkspace(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<LabelCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listLabelsByWorkspaceRequestCreation(id, filters).body(localVarReturnType);
    }

    /**
     * List labels by workspace
     * Returns a paginated collection of all labels, ordered by relevance for the given workspace: labels already used on work packages of the workspace come first, followed by the remaining labels. Within each group, labels are ordered by how often they are used overall and then by name.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages (on given workspace or any of its work packages)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose labels will be listed
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + name: filters labels by name, with the operators &#x60;~&#x60; (contains), &#x60;!~&#x60; (does not contain) and &#x60;**&#x60; (all)
     * @return ResponseEntity&lt;LabelCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<LabelCollectionModel> listLabelsByWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<LabelCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listLabelsByWorkspaceRequestCreation(id, filters).toEntity(localVarReturnType);
    }

    /**
     * List labels by workspace
     * Returns a paginated collection of all labels, ordered by relevance for the given workspace: labels already used on work packages of the workspace come first, followed by the remaining labels. Within each group, labels are ordered by how often they are used overall and then by name.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages (on given workspace or any of its work packages)  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the workspace whose labels will be listed
     * @param filters JSON specifying filter conditions. Currently supported filters are:  + name: filters labels by name, with the operators &#x60;~&#x60; (contains), &#x60;!~&#x60; (does not contain) and &#x60;**&#x60; (all)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listLabelsByWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listLabelsByWorkspaceRequestCreation(id, filters);
    }
}
